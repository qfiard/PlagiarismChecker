import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import javax.swing.*;

public class interfaceGraphique {
	static int g, d, t, p, cc, e;

	boolean debug = false;

	String id;

	int c;
	Statement st;
	JFrame f;
	Container content;

	public interfaceGraphique(){
	}

	public interfaceGraphique(int c, Connecte connecte, String id)
			throws SQLException {
		this.c = c;
		this.id = id;
		st = connecte.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		affiche("Vous etes connecte comme " + cToString() + ".");
		main();
	}

	private void main() {
		f = new JFrame("DataBase");
		content = f.getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());
		int c2 = -1;
		while (c2 != 0) {
			try{
				c2 = Integer.parseInt(afficheMenu().charAt(0) + "");
			}catch(NumberFormatException e){
				c2 = 0;
			}
			effectuerActions(c2);
		}
	}

	private String cToString() {
		switch (c) {
		case 1:
			return "un gerant";
		case 2:
			return "un client";
		case 3:
			return "un emballeur";
		case 4:
			return "la douane";
		case 5:
			return "un transporteur";
		default:
			return null;
		}
	}

	private String afficheMenu() {
		String resultat;
		switch (c) {
		case 1:
			String[] choix1 = { "1 - Changer le prix d'un produit",
					"2 - Licencier un employe", "3 - Liste des employes",
					"4 - Liste des clients",
					"5 - Nombre de colis traites par un employe par jour",
					"6 - Produit les plus vendus",
					"7 - Clients les plus depensiers", "0 - Retour" };
			resultat = (String) JOptionPane.showInputDialog(null,
					"Choississez une action", "Choix",
					JOptionPane.QUESTION_MESSAGE, null, choix1, choix1[0]);
			return (resultat == null) ? "0" : resultat;
		case 2:
			String[] choix2 = { "1 - Vos commandes", "2 - Suivi d'un colis",
					"3 - Liste des produits disponibles",
					"4 - Modifier une date de livraison",
					"5 - Changer votre login", "6 - Changer votre mdp",
					"0 - Retour" };
			resultat = (String) JOptionPane.showInputDialog(null,
					"Choississez une action", "Choix",
					JOptionPane.QUESTION_MESSAGE, null, choix2, choix2[0]);
			return (resultat == null) ? "0" : resultat;
		case 3:
			String[] choix3 = { "1 - Liste des commandes d'un client",
					"2 - Liste des produits d'une commande",
					"3 - Noter un colis comme emballe",
					"4 - Noter une palette comme emballee", "0 - Retour" };
			resultat = (String) JOptionPane.showInputDialog(null,
					"Choississez une action", "Choix",
					JOptionPane.QUESTION_MESSAGE, null, choix3, choix3[0]);
			return (resultat == null) ? "0" : resultat;
		case 4:
			String[] choix4 = { "1 - Resultat du controle d'un colis",
					"2 - Lister les palettes d'un conteneur",
					"3 - Lister les colis d'une palette",
					"4 - Lister les produits d'un colis",
					"5 - Prix d'un produit",
					"6 - Commandes expediees vers mon pays",
					"7 - Commandes expediees vers mon pays et non controlees",
					"8 - Commandes deja controlees",
					"9 - Recherche de commande", "0 - Retour" };
			resultat = (String) JOptionPane.showInputDialog(null,
					"Choississez une action", "Choix",
					JOptionPane.QUESTION_MESSAGE, null, choix4, choix4[0]);
			return (resultat == null) ? "0" : resultat;
		case 5:
			String[] choix5 = { "1 - Type d'un colis",
					"2 - Date limite de livraison d'un colis", "0 - Retour" };
			resultat = (String) JOptionPane.showInputDialog(null,
					"Choississez une action", "Choix",
					JOptionPane.QUESTION_MESSAGE, null, choix5, choix5[0]);
			return (resultat == null) ? "0" : resultat;
		case 0:
			affiche("FIN");
			return "0";

		default:
			affiche("ERREUR!");
			return "0";
		}

	}

	private void effectuerActions(int c2) {
		Tableau t;
		String readS1, readS2, query;
		int readI1, readI2, readI3, readI4, readI5, readI6;
		ResultSet rs;
		char readC;
		String choix[] = { "Oui", "Non" };
		String[][] donnees;
		String pays;
		try {
			switch (c) {
			case 1:
				switch (c2) {
				case 1:
					readS1 = readString("Reference du produit dont le prix est a modifier :");
					query = "SELECT * FROM produit WHERE ref='";
					query += readS1;
					query += "';";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						readI2 = readInt("Prix actuel : "
								+ String.valueOf(rs.getInt("prix"))
								+ ", changer en : ");
						query = "UPDATE produit SET prix=";
						query += readI2;
						query += " WHERE ref='";
						query += readS1;
						query += "';";
						if (debug)
							System.out.println(query);
						st.execute(query);
					} else {
						affiche("Aucun resultat");
						rs.close();
						break;
					}
					rs.close();
					break;
				case 2:
					readS1 = ((String) JOptionPane
							.showInputDialog(null,
									"Rechercher grace a son nom ?", "Choix",
									JOptionPane.QUESTION_MESSAGE, null, choix,
									choix[0]));
					readC = (readS1 == null) ? ' ' : readS1.charAt(0);
					if (readC == 'O') {
						readS1 = readString("Nom de l'emballeur :");
						query = "SELECT * FROM employe WHERE type='emballeur' AND nom LIKE '%";
						query += readS1;
						query += "%';";
						rs = st.executeQuery(query);
						if (debug)
							System.out.println(query);
						String[] entetes12 = { "Login", "Nom", "Prenom", "Type" };
						donnees = new String[length(rs)][4];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs
									.getString("login"));
							donnees[i][1] = String.valueOf(rs.getString("nom"));
							donnees[i][2] = String.valueOf(rs
									.getString("prenom"));
							donnees[i][3] = String
									.valueOf(rs.getString("type"));
						}
						rs.close();
						t = new Tableau("Liste des employes correspondants",
								entetes12, donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					readS1 = readString("Login de l'employe a licencier :");
					query = "DELETE FROM employe WHERE login='";
					query += readS1;
					query += "';";
					if (debug)
						System.out.println(query);
					st.execute(query);
					break;
				case 3:
					query = "SELECT * FROM employe;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes13 = { "Login", "Nom", "Prenom", "Type" };
					donnees = new String[length(rs)][4];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("login"));
						donnees[i][1] = String.valueOf(rs.getString("nom"));
						donnees[i][2] = String.valueOf(rs.getString("prenom"));
						donnees[i][3] = String.valueOf(rs.getString("type"));
					}
					rs.close();
					t = new Tableau("Liste des employes", entetes13, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
				case 4:
					query = "SELECT * FROM client ;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes14 = { "Login", "Societe", "Ville", "Pays" };
					donnees = new String[length(rs)][4];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("login"));
						donnees[i][1] = String.valueOf(rs.getString("societe"));
						donnees[i][2] = String.valueOf(rs.getString("ville"));
						donnees[i][3] = String.valueOf(rs.getString("pays"));
					}
					rs.close();
					t = new Tableau("Liste des clients", entetes14, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
				case 5:
					readS1 = ((String) JOptionPane
							.showInputDialog(null,
									"Rechercher grace a son nom ?", "Choix",
									JOptionPane.QUESTION_MESSAGE, null, choix,
									choix[0]));
					readC = (readS1 == null) ? ' ' : readS1.charAt(0);
					if (readC == 'O') {
						readS1 = readString("Nom de l'emballeur :");
						query = "SELECT * FROM employe WHERE type='emballeur' AND nom LIKE '%";
						query += readS1;
						query += "%';";
						rs = st.executeQuery(query);
						if (debug)
							System.out.println(query);
						String[] entetes15 = { "Login", "Nom", "Prenom" };
						donnees = new String[length(rs)][3];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs
									.getString("login"));
							donnees[i][1] = String.valueOf(rs.getString("nom"));
							donnees[i][2] = String.valueOf(rs
									.getString("prenom"));
						}
						rs.close();
						t = new Tableau("Liste des emballeurs correspondants",
								entetes15, donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					readS1 = readString("Login de l'employe ?");
					readI2 = readInt("Annee ?");
					readI3 = readInt("Mois (1 pour Janvier etc.) ?");
					readI4 = readInt("Jour ?");
					query = "SELECT COUNT(*) AS nb FROM colis WHERE emballeur_id='";
					query += readS1;
					query += "' AND emballage_date BETWEEN timestamp '"
							+ readI2 + "-" + readI3 + "-" + readI4
							+ " 00:01' AND timestamp '" + readI2 + "-" + readI3
							+ "-" + readI4 + " 23:59';";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						affiche("Nombre de colis traites par " + readS1
								+ " le " + readI4 + "/" + readI3 + "/" + readI2
								+ " : " + String.valueOf(rs.getInt("nb")));
					} else {
						affiche("Aucun resultat");
					}
					rs.close();
					break;
				case 6:
					query = "SELECT produit_ref,COUNT(*)" + " FROM exemplaire"
							+ " GROUP BY produit_ref" + " HAVING COUNT(*) > 0"
							+ " ORDER BY COUNT(*) DESC;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes16 = { "Produit", "Nombre Vendus" };
					donnees = new String[length(rs)][2];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs
								.getString("produit_ref"));
						donnees[i][1] = String.valueOf(rs.getInt("count"));
					}
					t = new Tableau("Produit les plus vendus", entetes16,
							donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 7:
					query = "SELECT client,SUM(prix)" + " FROM commande"
							+ " GROUP BY client" + " HAVING SUM(prix) > 0"
							+ " ORDER BY SUM(prix) DESC;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes17 = { "Client", "Argent Depense" };
					donnees = new String[length(rs)][2];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("client"));
						donnees[i][1] = String.valueOf(rs.getInt("sum"));
					}
					t = new Tableau("Clients les plus depensiers", entetes17,
							donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 0:
					break;
				default:
					affiche("ERREUR!");
				}
				break;
			case 2:
				switch (c2) {
				case 1:
					query = "SELECT * FROM commande WHERE client='";
					query += id;
					query += "';";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes21 = { "ID", "Prix", "Date de livraison" };
					donnees = new String[length(rs)][3];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("id"));
						donnees[i][1] = String.valueOf(rs.getInt("prix"));
						donnees[i][2] = rs.getTimestamp("livraison_date")
								.toString();
					}
					t = new Tableau("Vos commandes", entetes21, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 2:
					readS1 = ((String) JOptionPane
							.showInputDialog(null, "Afficher tous vos colis ?",
									"Choix", JOptionPane.QUESTION_MESSAGE,
									null, choix, choix[0]));
					readC = (readS1 == null) ? ' ' : readS1.charAt(0);
					if (readC == 'O') {
						query = "SELECT * FROM colis WHERE commande_id IN (SELECT id FROM commande WHERE client='";
						query += id;
						query += "');";
						rs = st.executeQuery(query);
						if (debug)
							System.out.println(query);
						String[] entetes22 = { "ID", "Commande", "Etat" };
						donnees = new String[length(rs)][3];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs.getString("id"));
							donnees[i][1] = String.valueOf(rs
									.getInt("commande_id"));
							donnees[i][2] = String
									.valueOf(rs.getString("etat"));
						}
						t = new Tableau("Vos colis", entetes22, donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						rs.close();
					}
					readI1 = readInt("ID du colis :");
					query = "SELECT * FROM colis WHERE commande_id IN (SELECT id FROM commande WHERE client='";
					query += id;
					query += "') AND id=";
					query += readI1;
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						affiche("Suivi du colis " + readI1 + " : "
								+ String.valueOf(rs.getString("etat")));
					} else {
						affiche("Aucun resultat");
					}
					rs.close();
					break;
				case 3:
					query = "SELECT * FROM produit WHERE nb_dispo>0 ;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes23 = { "ref", "Description", "Prix",
							"Nombre disponibles" };
					donnees = new String[length(rs)][4];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("ref"));
						donnees[i][1] = String.valueOf(rs
								.getString("description"));
						donnees[i][2] = String.valueOf(rs.getInt("prix"));
						donnees[i][3] = String.valueOf(rs.getInt("nb_dispo"));
					}
					t = new Tableau("Liste des produits disponibles",
							entetes23, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 4:
					readS1 = ((String) JOptionPane
							.showInputDialog(null,
									"Afficher toutes vos commandes ?", "Choix",
									JOptionPane.QUESTION_MESSAGE, null, choix,
									choix[0]));
					readC = (readS1 == null) ? ' ' : readS1.charAt(0);
					if (readC == 'O') {
						query = "SELECT * FROM commande WHERE client='";
						query += id;
						query += "';";
						if (debug)
							System.out.println(query);
						rs = st.executeQuery(query);
						String[] entetes24 = { "ID", "Prix",
								"Date de livraison" };
						donnees = new String[length(rs)][3];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs.getString("id"));
							donnees[i][1] = String.valueOf(rs.getInt("prix"));
							donnees[i][2] = rs.getTimestamp("livraison_date")
									.toString();
						}
						t = new Tableau("Vos commandes", entetes24, donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						rs.close();
					}
					readI1 = readInt("ID de la commande :");
					query = "SELECT * FROM commande WHERE client='";
					query += id;
					query += "' AND id=";
					query += readI1;
					query += ";";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						affiche("Date actuelle : "
								+ rs.getTimestamp("livraison_date").toString());
					} else {
						affiche("Aucun resultat");
						rs.close();
						break;
					}
					readI2 = readInt("Changer en : Jour : ");
					readI3 = readInt("Mois : ");
					readI4 = readInt("Annee : ");
					readI5 = readInt("Heure : ");
					readI6 = readInt("Minute : ");
					query = "UPDATE commande SET livraison_date=timestamp '"
							+ readI4 + "-" + readI3 + "-" + readI2 + " "
							+ readI5 + ":" + readI6 + "'";
					query += " WHERE id=";
					query += readI1;
					query += ";";
					if (debug)
						System.out.println(query);
					st.execute(query);
					rs.close();
					break;
				case 5:
					readS1 = readString("Login actuel : " + id
							+ ", changer en : ");
					query = "UPDATE client SET login='";
					query += readS1;
					query += "' WHERE login='";
					query += id;
					query += "';";
					if (debug)
						System.out.println(query);
					st.execute(query);
					rs = st.executeQuery("SELECT * FROM client WHERE login='" + readS1 + "';");
					if (rs != null && rs.next())
						id = readS1;
					else
						affiche("Impossible");
					rs.close();
					break;
				case 6:
					readS1 = readString("Nouveau mot de passe : ");
					query = "UPDATE client SET mdp='";
					query += readS1;
					query += "' WHERE login='";
					query += id;
					query += "';";
					if (debug)
						System.out.println(query);
					st.execute(query);
					break;
				case 0:
					break;
				default:
					affiche("ERREUR!");
				}
				break;
			case 3:
				switch (c2) {
				case 1:
					readS1 = readString("ID du client : ");
					query = "SELECT * FROM commande WHERE client='";
					query += readS1;
					query += "';";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes31 = { "ID", "Date de livraison" };
					donnees = new String[length(rs)][2];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("id"));
						donnees[i][1] = rs.getTimestamp("livraison_date")
								.toString();
					}
					t = new Tableau("Commandes de " + readS1, entetes31,
							donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 2:
					readI1 = readInt("ID de la commande : ");
					query = "SELECT * FROM produit "
							+ "WHERE ref IN"
							+ "(SELECT produit_ref FROM exemplaire WHERE colis_id IN"
							+ "(SELECT id FROM colis WHERE commande_id="
							+ readI1 + "));";
					rs = st.executeQuery(query);
					if (debug)
						System.out.println(query);

					String[] entetes32 = { "ref", "Poids", "Type" };
					donnees = new String[length(rs)][3];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("ref"));
						donnees[i][1] = String.valueOf(rs.getInt("poids"));
						donnees[i][2] = String.valueOf(rs.getString("type"));
					}
					t = new Tableau("Liste des produits de la commande "
							+ readI1, entetes32, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 3:
					readI1 = readInt("Reference du colis a emballer :");
					query = "UPDATE colis SET etat='Emballe'";
					query += ",emballeur_id='" + id + "'";
					query += ",emballage_date=localtimestamp";
					query += " WHERE id=";
					query += readI1;
					query += ";";
					if (debug)
						System.out.println(query);
					st.execute(query);
					break;
				case 4:
					readI1 = readInt("Reference de la palette a emballer :");
					query = "UPDATE palette SET etat='Emballe'";
					query += ",emballeur_id='" + id + "'";
					query += ",emballage_date=localtimestamp";
					query += " WHERE id=";
					query += readI1;
					query += ";";
					if (debug)
						System.out.println(query);
					st.execute(query);
					break;
				case 0:
					break;
				default:
					affiche("ERREUR!");
				}
				break;
			case 4:
				query = "SELECT pays FROM douane WHERE login='" + id + "';";
				if (debug)
					System.out.println(query);
				rs = st.executeQuery(query);
				rs.next();
				pays = String.valueOf(rs.getString("pays"));
				rs.close();
				switch (c2) {
				case 1:
					readI1 = readInt("Reference du colis a controler :");
					String[] controle = { "Renvoi", "Valider" };
					readS1 = ((String) JOptionPane.showInputDialog(null,
							"Resultat ?", "Choix",
							JOptionPane.QUESTION_MESSAGE, null, controle,
							choix[0]));
					readC = (readS1 == null) ? ' ' : readS1.charAt(0);
					query = "SELECT * FROM colis WHERE id=";
					query += readI1;
					query += ";";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						readS2 = rs.getString("emballeur_id");
					} else {
						affiche("Aucun resultat");
						rs.close();
						break;
					}
					rs.close();
					query = "SELECT MAX(id) FROM controle;";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						readI2 = rs.getInt("max");
					} else {
						readI2 = 0;
					}
					if (debug)
						System.out.println("max=" + readI2);
					rs.close();
					if (readC == 'R') {
						readS1 = readString("Motif ?");
						query = "UPDATE colis SET etat='Retourne' WHERE pays='"
								+ pays + "' AND id=";
						query += readI1;
						query += ";";
						if (debug)
							System.out.println(query);
						
						st.execute(query);
							query = "INSERT INTO controle(id, pays, colis_id, emballeur_id, resultat, motif_renvoi) VALUES ("
									+ (readI2 + 1)
									+ ",'"
									+ pays
									+ "',"
									+ readI1
									+ ",'"
									+ readS2
									+ "','Renvoi','"
									+ readS1 + "');";

					} else {
						query = "SELECT colis WHERE pays='" + pays
								+ "' AND id=";
						query += readI1;
						query += ";";
						st.execute(query);
							query = "INSERT INTO controle(id, pays, colis_id, emballeur_id, resultat) VALUES ("
									+ (readI2 + 1)
									+ ",'"
									+ pays
									+ "',"
									+ readI1 + ",'" + readS2 + "','Valide');";
	
					}
					if (debug)
						System.out.println(query);
					st.execute(query);
					break;
				case 2:
					readI1 = readInt("Reference du conteneur pour lister les palettes :");
					query = "SELECT * FROM palette WHERE transport_id=";
					query += readI1;
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes42 = { "ID" };
					donnees = new String[length(rs)][1];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getInt("id"));
					}
					t = new Tableau("Palettes dans le conteneur " + readI1,
							entetes42, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 3:
					readI1 = readInt("Reference de la palette pour lister les colis:");
					query = "SELECT * FROM colis WHERE palette_id=";
					query += readI1;
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes43 = { "ID" };
					donnees = new String[length(rs)][1];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getInt("id"));
					}
					t = new Tableau("Colis dans la palette " + readI1,
							entetes43, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 4:
					readI1 = readInt("Reference du colis pour lister les produits :");
					query = "SELECT * FROM produit WHERE ref IN(SELECT produit_ref FROM exemplaire WHERE colis_id="
							+ readI1 + ");";
					rs = st.executeQuery(query);
					if (debug)
						System.out.println(query);
					String[] entetes44 = { "ref", "Poids", "Prix", "Type" };
					donnees = new String[length(rs)][4];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("ref"));
						donnees[i][1] = String.valueOf(rs.getInt("poids"));
						donnees[i][2] = String.valueOf(rs.getInt("prix"));
						donnees[i][3] = String.valueOf(rs.getString("type"));
					}
					t = new Tableau("Produits dans le colis " + readI1,
							entetes44, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 5:
					readS1 = readString("Reference du produit pour recuperer le prix:");
					query = "SELECT * FROM produit WHERE ref='";
					query += readS1;
					query += "';";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						affiche("Prix de " + readS1 + " : "
								+ String.valueOf(rs.getInt("prix")));
					} else {
						affiche("Aucun resultat");
					}
					rs.close();
					break;
				case 6:
					query = "SELECT * FROM colis WHERE commande_id IN";
					query += "(SELECT id FROM commande WHERE client IN";
					query += "(SELECT login FROM client WHERE pays='";
					query += pays;
					query += "'));";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes46 = { "id" };
					donnees = new String[length(rs)][1];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getInt("id"));
					}
					t = new Tableau("Colis vers " + pays, entetes46, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
				case 7:
					query = "SELECT * FROM colis WHERE commande_id IN";
					query += "(SELECT id FROM commande WHERE client IN";
					query += "(SELECT login FROM client WHERE pays='";
					query += pays;
					query += "')) ";
					query += "AND id NOT IN(SELECT colis_id FROM controle);";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes47 = { "id" };
					donnees = new String[length(rs)][1];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getInt("id"));
					}
					t = new Tableau("Colis vers " + pays + " et non controles",
							entetes47, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
				case 8:

					query = "SELECT * FROM colis WHERE id IN";
					query += "(SELECT colis_id FROM controle WHERE pays='";
					query += pays;
					query += "');";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					String[] entetes48 = { "id" };
					donnees = new String[length(rs)][1];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getInt("id"));
					}
					t = new Tableau("Colis controles", entetes48, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
				case 9:
					String[] recherche = { "Destination", "Contenu" };
					readS1 = ((String) JOptionPane.showInputDialog(null,
							"Rechercher grace a quel critere ?", "Choix",
							JOptionPane.QUESTION_MESSAGE, null, recherche,
							recherche[0]));
					readC = (readS1 == null) ? 'D' : readS1.charAt(0);
					switch (readC) {
					case 'D':
						String[] destinationChoix = { "adresse", "ville", "cp",
								"pays" };
						String champ = ((String) JOptionPane.showInputDialog(
								null, "Rechercher dans ?", "Choix",
								JOptionPane.QUESTION_MESSAGE, null,
								destinationChoix, destinationChoix[0]));
						if (champ == null)
							champ = "pays";
						String destination = readString("Motif a recherche");
						query ="SELECT colis.id,client.pays " +
								"FROM colis,client " +
								"WHERE client.login=" +
								"(SELECT client FROM commande WHERE id=commande_id)" +
								" AND commande_id IN" +
								"(SELECT id FROM commande WHERE client IN" +
								"(SELECT login FROM client WHERE " + champ + " LIKE '%" + destination + "%')" +
								");";
						if (debug)
							System.out.println(query);
						rs = st.executeQuery(query);
						String[] entetes49D = { "id", "Destination" };
						donnees = new String[length(rs)][1];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs.getInt("id"));
							donnees[i][0] = String.valueOf(rs
									.getString("destination"));
						}
						t = new Tableau("Colis vers " + donnees, entetes49D,
								donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						break;
					case 'C':
						readS1 = readString("Reference du produit ?");
						query = "SELECT id,ref " +
								"FROM colis C,produit " +
								"WHERE ref IN" +
								"(SELECT produit_ref FROM exemplaire WHERE colis_id=C.id)" +
								" AND ref" +
								" LIKE '%" + readS1 + "%';";
						if (debug)
							System.out.println(query);
						rs = st.executeQuery(query);
						String[] entetes49C = { "id", "ref" };
						donnees = new String[length(rs)][1];
						for (int i = 0; rs != null && rs.next(); i++) {
							donnees[i][0] = String.valueOf(rs.getInt("id"));
							donnees[i][0] = String.valueOf(rs.getString("ref"));
						}
						t = new Tableau("Colis vers " + donnees, entetes49C,
								donnees);
						t.setVisible(true);
						while (t.isVisible()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						break;
					default:
						break;
					}
					break;
				case 0:
					break;
				default:
					affiche("Erreur !");
				}
				break;
			case 5:
				switch (c2) {
				case 1:
					readI1 = readInt("Reference du colis pour savoir le type des produits:");
					query = "SELECT * FROM produit WHERE ref IN(SELECT produit_ref FROM exemplaire WHERE colis_id="
							+ readI1 + ");";
					rs = st.executeQuery(query);
					if (debug)
						System.out.println(query);
					String[] entetes51 = { "ref", "Poids", "Type" };
					donnees = new String[length(rs)][3];
					for (int i = 0; rs != null && rs.next(); i++) {
						donnees[i][0] = String.valueOf(rs.getString("ref"));
						donnees[i][1] = String.valueOf(rs.getInt("poids"));
						donnees[i][2] = String.valueOf(rs.getString("type"));
					}
					t = new Tableau("Produits dans le colis " + readI1,
							entetes51, donnees);
					t.setVisible(true);
					while (t.isVisible()) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					rs.close();
					break;
				case 2:
					readI1 = readInt("ID du colis pour recuperer la date de livraison ");
					query = "SELECT * FROM commande WHERE id=" +
							"(SELECT commande_id FROM colis WHERE id=" + readI1 + ");";
					if (debug)
						System.out.println(query);
					rs = st.executeQuery(query);
					if (rs != null && rs.next()) {
						affiche("Date de livraison : "
								+ rs.getTimestamp("livraison_date").toString());
					} else {
						affiche("Aucun resultat");
						rs.close();
						break;
					}
				case 0:
					break;
				default:
					affiche("Erreur !");
				}
				break;
			case 0:
				affiche("Fin");
				break;
			default:
				affiche("Erreur !");
			}
		} catch (SQLException e) {
			affiche("Erreur SQL !");
		}
	}

	public static void affiche(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	private int length(ResultSet rs) throws SQLException {
		rs.last();
		int i = rs.getRow();
		rs.first();
		return i;
	}

	public static String readString(String question) {
		String temp = (String) JOptionPane.showInputDialog(null, question);
		return (temp == null) ? "" : temp;
	}

	public int readInt(String question) {
		try{
			return Integer.parseInt(JOptionPane.showInputDialog(null, question));			
		}catch(NumberFormatException e){
			return 0;
		}
	}
}