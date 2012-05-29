package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Acces_bdd {
	static Connection conn; // la connexion a la base
	static Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;

	public static boolean coonectDB() {
		try {
			Class.forName("org.postgresql.Driver");

			String url = "jdbc:postgresql://localhost:5432/booktown";
			String user = "postgres";
			String passwd = "fatima";

			conn = DriverManager.getConnection(url, user, passwd);
			st = conn.createStatement();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Verifie auth ok
	public static String verif_auth(String login, String passwd) {

		String id_user = "";
		String[][] mobj = null;

		try {

			// pour l'instant douanier seulement
			String requete = "SELECT id_douanier FROM douanier WHERE id_douanier='"
					+ login + "' AND password='" + passwd + "';";

			mobj = aexecuteRequete(requete);

			if (mobj[0][0] != null)
				id_user = mobj[0][0];
			else
				id_user = "";

			if (id_user.isEmpty())
				return "-1";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id_user;
	}

	// Recupere la liste des commandes
	public static String[][] liste_commande(String id_user, int idlist) {

		String[][] mobj = null;

		try {

			String requete;

			if (idlist == 0)
				return new String[][] { { null, null } };
			else if (idlist == 1)
				requete = "select * from commande where destination IN (select pays from douanier where id_douanier='"
						+ id_user + "');";
			else if (idlist == 2)
				requete = "select commande.* "
						+ "from (commande natural join colis) natural join control_colis "
						+ "where id_douanier = '" + id_user + "' ;";
			else
				requete = "select * "
						+ "from commande "
						+ "where destination IN (select pays from douanier where id_douanier='"
						+ id_user
						+ "') "
						+ " except "
						+ "select commande.* "
						+ "from (commande natural join colis) natural join control_colis "
						+ "where id_douanier = '" + id_user + "' ;";

			mobj = aexecuteRequete(requete);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mobj;
	}

	// Recherche multi-critere
	public static String[][] recherche_multi(String id_user, String scommande,
			String sdestination, String scontenu) {

		String[][] mobj = null;

		try {

			String requete;
			String requete_complement = " 1=1 ";
			String pre_requete = "";

			if (!scommande.isEmpty())
				requete_complement = " id_commande = '" + scommande + "'";
			if (!sdestination.isEmpty())
				requete_complement = requete_complement
						+ " AND destination = '" + sdestination + "'";
			if (!scontenu.isEmpty()) {
				pre_requete = " NATURAL JOIN produit_commande NATURAL JOIN produit";
				requete_complement = requete_complement
						+ " AND description like '%" + scontenu + "%'";
			}

			if (scommande.isEmpty() && sdestination.isEmpty()
					&& scontenu.isEmpty())
				return new String[][] { { null, null } };

			requete = "SELECT id_commande , id_client , destination , prix_com , date_commande , date_souhait_livraison "
					+ "FROM commande" + pre_requete + " WHERE " +
					requete_complement + ";";

			System.out.print("Requete recherche : " + requete);

			mobj = aexecuteRequete(requete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mobj;
	}

	// Recherche controle
	public static String[][] controle(String id_user, String id_colis,
			String sdestination, String scontenu) {

		String[][] mobj = null;

		try {

			String requete;
			String requete_complement = " 1=1 ";
			String pre_requete = "";

			if (!id_colis.isEmpty())
				requete_complement = " id_colis= '" + id_colis + "'";
			if (!sdestination.isEmpty()) {
				pre_requete = " NATURAL JOIN commande";
				requete_complement = requete_complement
						+ " AND destination = '" + sdestination + "'";
			}
			if (!scontenu.isEmpty()) {
				pre_requete = " NATURAL JOIN produit_commande NATURAL JOIN produit";
				requete_complement = requete_complement
						+ " AND description like '%" + scontenu + "%'";
			}

			if (id_colis.isEmpty() && sdestination.isEmpty()
					&& scontenu.isEmpty())
				return new String[][] { { null, null } };

			requete = "SELECT * " + "FROM colis" + pre_requete + " WHERE " +
					requete_complement + ";";

			System.out.print("Requete recherche : " + requete);

			mobj = aexecuteRequete(requete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mobj;
	}

	public static String[][] recherche_multi_produits(String id_user,
			String id_conteneur, String id_palette, String id_colis) {

		String[][] mobj = null;

		try {

			String requete = "SELECT * FROM Colis";

			if (!id_conteneur.isEmpty())
				requete = "SELECT id_palette FROM Pal_Cont  WHERE  id_conteneur= '"
						+ id_conteneur + "'";
			else {
				if (!id_palette.isEmpty())
					requete = "SELECT * FROM Colis_palette  WHERE  id_palette= '"
							+ id_palette + "'";
				else {
					if (!id_colis.isEmpty()) {
						requete = "SELECT * FROM (Colis natural join commande) natural join (produit_commande natural join produit) " +
								"natural join Colis_palette "
								+ "  WHERE  commande.id_commande= (select id_commande from colis where id_colis = '"
								+ id_colis + "')";
					}
				}
			}

			if (id_conteneur.isEmpty() && id_palette.isEmpty()
					&& id_colis.isEmpty())
				return new String[][] { { null, null } };

			System.out.print("Requete recherche : " + requete);

			mobj = aexecuteRequete(requete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mobj;
	}

	public static void insertTable(String req) {
		try {

			boolean connectdb = coonectDB();

			// Création d'un objet Statement
			Statement state = conn.createStatement();
			state.executeUpdate(req);
			state.close();

			closedb();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String[][] aexecuteRequete(String req) {

		String[][] tab = null;

		try {

			boolean connectdb = coonectDB();

			// Création d'un objet Statement
			Statement state = conn.createStatement();
			// L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery(req);
			// On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			int nbrlignes = result.getRow();
			tab = new String[50][50];

			int k = 0;
			while (result.next()) {
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					tab[k][i - 1] = result.getObject(i).toString();
				}

				k++;

			}

			result.close();
			state.close();

			closedb();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tab;

	}

	// Recupere les details d'une commande
	public static String[][] detail_commande(String id_user, String id_commande) {

		String[][] mobj = null;

		try {

			String requete;

			if (id_commande.isEmpty())
				return new String[][] { { null, null } };
			else
				requete = "SELECT * FROM commande " + "NATURAL JOIN produit "
						+ "NATURAL JOIN produit_commande "
						+ "NATURAL JOIN colis " + "NATURAL JOIN emballeur "
						+ "WHERE id_commande = '" + id_commande + "';";
			
			System.out.print("Requete detail : " + requete);

			mobj = aexecuteRequete(requete);

			System.out.print("Requete detail : " + mobj[0][0]);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mobj;
	}

	public static void updateControl(String id_user, String id_colis,
			String motif, int b) {

		String[][] mobj = null;
		try {

			String requete;
			String update;
			
			//colis rejete par douane
			if (b == 1) {
				requete = "INSERT INTO control_colis (id_colis,id_douanier,motif_rejet,rejet,date_control)"
						+ "values ('"
						+ id_colis
						+ "', '"
						+ id_user + "', '" + motif + "', 'true', now());";
				//rejet donc mise a jour etat commande
				update = "UPDATE commande set etat_commande='NE' " +
						"where id_commande = (SELECT id_commande " +
												"from colis natural join commande " +
												"where id_colis= '"+id_colis+"')";
			//colis non rejete par douane	
			} else
				requete = "INSERT INTO control_colis (id_colis,id_douanier,date_control)"
						+ "values ('"
						+ id_colis
						+ "', '"
						+ id_user + "', now());";
			//mise a jour etat commande en preparation
			update = "UPDATE commande set etat_commande='PE' " +
			"where id_commande = (SELECT id_commande " +
									"from colis natural join commande " +
									"where id_colis= '"+id_colis+"')";
			System.out.print("Requete detail : " + requete);

			insertTable(requete);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static int UpdateMdp(String id_user, String oldmdp, String newmdp){
		String[][] mobj = null;
		try {

			String requete;

			mobj = aexecuteRequete("SELECT password from douanier where id_douanier = '"+id_user+"';");
			String pwd = mobj[0][0];
			if(oldmdp.equals(pwd)){
				requete = "UPDATE douanier set password='" +newmdp+ "' " +
						"where id_douanier = '" +id_user+ "';";
				insertTable(requete);
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// fermeture de la connection
	public static void closedb() throws SQLException {
		st.close();
		conn.close();
	}

}
