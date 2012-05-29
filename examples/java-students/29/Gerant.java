import java.sql.*;
import java.util.ArrayList;


public class Gerant extends Utilisateur{

	public Gerant(BDD bdd) {
		super(bdd);
	}

	public final ArrayList<String> getMenu() {
		this.menu = new ArrayList<String>();
		menu.add(0, "\n\nActions disponibles:");
		menu.add(1, "\t1 - Voir un résumé de toutes les commandes");
		menu.add(2, "\t2 - Changer le prix d'un produit");
		menu.add(3, "\t3 - Voir la liste des produits");
		menu.add(4, "\t4 - (Licencier un emballeur)");
		menu.add(5, "\t5 - Voir la liste des emballeurs");
		menu.add(6, "\t6 - Voir la liste des clients");
		menu.add(7, "\t7 - Voir le nombre de colis par emballeur");
		menu.add(8, "\t8 - Voir les produits les plus vendus");
		menu.add(9, "\t9 - Voir les meilleurs clients");
		menu.add(10, "\t0 - Fermer la session");
		return menu;
	}

	public boolean identifier(String login, String motPasse) throws SQLException{
		return idRequest(login, motPasse, "ADMIN", "(prenom||' '||nom)");
	}

	public boolean traiterChoix(int choix) throws SQLException{
		switch(choix) {
			case 1:
				afficherLigne();
				viewOrders();
				break;

			case 2:
				afficherLigne();
				afficher("--- Saisissez le code du produit dont vous voulez modifier le prix :");
				afficher("\tNUMERO\t\t\tTYPE\tPRIX\tSTOCK\tDESCRIPTION");
				afficher(getProducts());
				updateProductPrice() ;
				break;

			case 3:
				afficherLigne();
				afficher("--- Liste des Produits :");
				afficher("\tNUMERO\t\t\tTYPE\tPRIX\tSTOCK\tDESCRIPTION");
				afficher(getProducts());
				break;

			case 4:
				afficherLigne();
				afficher("--- Saisissez le numéro de l'emballeur à licencier :");
				afficher("\tNUMERO\tNOM\tPRENOM\tTAUX ERREUR%");
				fireEmployee();
				afficher(getEmployees());
				break;

			case 5:
				afficherLigne();
				afficher("--- Liste des Emballeurs :");
				afficher("\tNUMERO\tNOM\tPRENOM\tTAUX ERREUR%\tSORTIE\tLICENCIEMENT");
				afficher(getEmployees());
				break;

			case 6:
				afficherLigne();
				afficher("--- Liste des Clients :");
				afficher("\tNUMERO\t\tNOM\t\tPAYS\t\tVILLE");
				afficher(getCustomers());
				break;

			case 7:
				afficherLigne();
				afficher("--- Liste des Emballeurs Par nombre de Colis:");
				afficher("\tCOLIS\tNUMERO\tNOM\tPRENOM\tTAUX ERREUR");
				getParcelsByEmployee();
				break;

			case 8:
				afficherLigne();
				afficher("--- Les 20 Produits les plus vendus (nombre de produits vendus):");
				afficher("\tNB\tPRIX\t\tNUMERO\t\tDESCRIPTION");
				afficher(getTopSales());
				break;

			case 9:
				afficherLigne();
				afficher("--- Les 20 Clients qui ont dépensé le plus (en montant cumulé de commandes) :");
				afficher("\tN CLIENT\t\tNOM\t\tDEPENSES\tCOMMANDES\tPANIER MOYEN");
				afficher(getTopCustomers());
				break;
			
			default:
				afficherLigne();
				afficher("Option "+choix+" non disponible");
		}
		return true;
	}

	private void viewOrders() throws SQLException{
		ResultSet rs = this.getSelect("SELECT count(numero_commande) AS total, ROUND(AVG(montant)) AS montant_moyen, MIN(montant) AS montant_min, MAX(montant) AS montant_max, MIN(date_commande) AS debut, MAX(date_commande) AS fin FROM commandes;");
		int total;

		while (rs.next()) {
			total = rs.getInt("total");
			int montant_moyen = rs.getInt("montant_moyen");
			int montant_min = rs.getInt("montant_min");
			int montant_max = rs.getInt("montant_max");
			String debut = rs.getString("debut");
			String fin = rs.getString("fin");

			afficher("Total de "+total+" commandes enregistrées entre le "+debut+ " et le "+fin);
			afficher("Montant moyen d'une commande : "+montant_moyen);
			afficher("Montant minimum d'une commande : "+montant_min);
			afficher("Montant maximum d'une commande : "+montant_max);
		}

		afficher("Sur un total de 250 commandes, on a, plus en détail :");

		rs = this.getSelect("SELECT count(numero_commande) AS nb FROM commandes WHERE etat='total'");
		while (rs.next()) {
			int nb = rs.getInt("nb");
			afficher("\t- "+nb+" commandes totalement expédiées");
		}

		rs = this.getSelect("SELECT count(numero_commande) AS nb FROM commandes WHERE etat='partiel'");
		while (rs.next()) {
			int nb = rs.getInt("nb");
			afficher("\t- "+nb+" commandes partiellement expédiées");
		}

		rs = this.getSelect("SELECT count(numero_commande) AS nb FROM commandes WHERE etat='ouvert'");
		while (rs.next()) {
			int nb = rs.getInt("nb");
			afficher("\t- "+nb+" commandes ouvertes");
		}
	}

	private void updateProductPrice() throws SQLException {
		java.util.Scanner sc = new java.util.Scanner(System.in);
		String num = sc.next();

		ResultSet rs = this.getSelect("SELECT numero_produit, description, prix FROM PRODUITS WHERE numero_produit='"+num+"'");

		while (rs.next()) {
			String numero = rs.getString("numero_produit");
			String description = rs.getString("description");
			int prix = rs.getInt("prix");
			afficher("Ancien prix pour numéro "+numero+" ("+description+") :"+prix);
		}

		afficher("Donnez le nouveau prix :");
		int nouveau_prix = sc.nextInt();
		String query = "UPDATE PRODUITS SET prix="+nouveau_prix+" WHERE numero_produit='"+num+"'";
		bdd.execute(query);

		rs = this.getSelect("SELECT numero_produit, description, q, prix, reserve FROM PRODUITS WHERE numero_produit='"+num+"'");
		while (rs.next()) {
			String numero = rs.getString("numero_produit");
			String description = rs.getString("description");
			String q = rs.getString("q");
			int prix = rs.getInt("prix");
			int reserve = rs.getInt("reserve");
			afficher("Nouvel enregistrement : ");
			afficher("\t"+numero+"\t"+q+"\t"+prix+"\t"+reserve+"\t"+ description);
		}
	}
	private void fireEmployee() throws SQLException {
		String query = "SELECT numero_emballeur, nom, prenom, taux_erreur  FROM emballeurs WHERE motif_licenciement IS NULL ORDER BY nom, prenom";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_emballeur");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String taux_erreur = rs.getString("taux_erreur");
			l.add("\t"+numero+"\t"+nom+"\t"+prenom+"\t"+taux_erreur+"%");
		}
		afficher(l);

		java.util.Scanner sc = new java.util.Scanner(System.in);
		String num = sc.next();

		rs = this.getSelect("SELECT numero_emballeur, nom, prenom, taux_erreur, motif_sortie, motif_licenciement FROM emballeurs WHERE numero_emballeur='"+num+"'");

		while (rs.next()) {
			String numero = rs.getString("numero_emballeur");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			afficher("Licenciement de "+prenom+" "+nom+" ("+numero+")");
		}

		afficher("Saisissez un motif de licenciement :");
		String motif_licenciement = sc.next();

		query = "UPDATE EMBALLEURS SET motif_sortie='licenciement', motif_licenciement='"+motif_licenciement+"' WHERE numero_emballeur='"+num+"'";
		bdd.execute(query);
	}

	
	private ArrayList<String> getEmployees() throws SQLException {
		String query = "SELECT numero_emballeur, nom, prenom, taux_erreur, motif_sortie, motif_licenciement FROM emballeurs ORDER BY nom, prenom";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_emballeur");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int taux_erreur = rs.getInt("taux_erreur");
			String motif_sortie= rs.getString("motif_sortie");
			String motif_licenciement= rs.getString("motif_licenciement");
			l.add("\t"+numero+"\t"+nom+"\t"+prenom+"\t"+taux_erreur+"%"+"\t"+motif_sortie+"\t"+motif_licenciement);
		}
		return l;
	}

	private ArrayList<String> getParcelsByEmployee() throws SQLException {
		String query = "select E1.numero_emballeur, count(E1.numero_emballage) AS nb, E2.nom, E2.prenom, E2.taux_erreur FROM emballages E1, emballeurs E2 WHERE E1.numero_emballeur=E2.numero_emballeur GROUP BY E1.numero_emballeur, E2.nom, E2.prenom, E2.taux_erreur ORDER BY nb;";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_emballeur");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int nb= rs.getInt("nb");
			int taux_erreur= rs.getInt("taux_erreur");
			l.add("\t"+nb+"\t"+numero+"\t"+nom+"\t"+prenom+"\t"+taux_erreur+"%");
		}
		return l;
	}
	// 
	private ResultSet getSelect(String query) throws SQLException {
		return bdd.getSelect(query);
	}

	private ArrayList<String> getProducts() throws SQLException {
		String query = "SELECT numero_produit, description, q, prix, reserve FROM PRODUITS ORDER BY numero_produit LIMIT 20;";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_produit");
			String description = rs.getString("description");
			String q = rs.getString("q");
			int prix = rs.getInt("prix");
			int reserve = rs.getInt("reserve");
			l.add("\t"+numero+"\t"+q+"\t"+prix+"\t"+reserve+"\t"+ description);
		}
		return l;
	}	

	private ArrayList<String> getCustomers() throws SQLException {
		String query = "SELECT numero_client, (nom_societe||' '||suffixe_societe) AS nom, ville, P.nom_pays AS pays FROM CLIENTS C, PAYS P WHERE C.pays=P.pays ORDER BY nom_societe, suffixe_societe LIMIT 50;";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_client");
			String nom = rs.getString("nom");
			String ville = rs.getString("ville");
			String pays = rs.getString("pays");
			l.add("\t"+numero+"\t"+nom+"\t"+pays+"\t\t"+ville);
		}
		return l;
	}
	
	private ArrayList<String> getTopSales() throws SQLException {
		String query = "SELECT  L.numero_produit, P.description, P.prix, SUM(L.quantite) AS ventes FROM ligne_commande L, produits P WHERE L.numero_produit=P.numero_produit GROUP BY L.numero_produit, P.prix, P.description ORDER BY ventes DESC LIMIT 30;";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_produit");
			String description = rs.getString("description");
			int prix = rs.getInt("prix");
			int ventes = rs.getInt("ventes");
			l.add("\t"+ventes+"\t"+prix+"\t"+numero+"\t"+ description);
		}
		return l;
	}

	private ArrayList<String> getTopCustomers() throws SQLException {
		String query = "SELECT C1.numero_client, (C2.nom_societe||' '||C2.suffixe_societe) AS client, SUM(montant) AS depenses, COUNT(numero_commande) AS nbcommandes, ROUND(AVG(montant)) AS panier FROM commandes C1, clients C2 WHERE C1.numero_client=C2.numero_client  GROUP BY C1.numero_client, C2.nom_societe, C2.suffixe_societe  ORDER BY depenses DESC LIMIT 20 ;";
		ResultSet rs = this.getSelect(query);
		ArrayList<String> l = new ArrayList<String>();

		while (rs.next()) {
			String numero = rs.getString("numero_client");
			String nom = rs.getString("client");
			int depenses = rs.getInt("depenses");
			int nbcommandes = rs.getInt("nbcommandes");
			int panier = rs.getInt("panier");
			l.add("\t"+numero+"\t"+nom+"\t"+depenses+"\t"+ nbcommandes+"\t\t"+panier);
		}
		return l;
	}
}
