import java.sql.*;

public class Gerant {
	ConnectionB conn;
	ResultSet res;

	public Gerant(ConnectionB c) {
		conn = c;

	}

	public int printMenu() {
		int c = -1;
		System.out.print("\033c"); // nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - pour chager le prix des produit");
		System.out.println("2 - afficher la list des employés");
		System.out.println("3 - afficher la list des client");
		System.out.println("4 - les produits les plus vendus");
		System.out.println("5 - afficher les clients les plus depensier");
		System.out.println("6 - licencier les emballeur");
		System.out
				.println("------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Gestion.readInt();

		System.out.print("\033c"); // nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------

		try {
			switch (c) {
			case 1:
				System.out.println("Changer le prix des produit");
				System.out
						.println("-------------------------------------------------------------");
				System.out
						.println("Entre le num de produit que vous voulez change le prix!!");
				String nom = Gestion.readString();
				System.out.println("la nouvelle prix ?");
				int prix = Gestion.readInt();
				conn.MAJ(nom, prix);
				break;

			case 2:
				System.out
						.println("liste des employes avec (login, nom, prenom)");
				System.out
						.println("-------------------------------------------------------------");
				res = conn.list_employe();
				Gestion.print("ID", 10);
				Gestion.print("NOM", 30);
				Gestion.print("PRENOM", 30);
				Gestion.print("eurreur", 10);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("nom"), 30);
					Gestion.print(res.getString("PRENOM"), 30);
					Gestion.print(String.valueOf(res.getInt("err")), 10);
					System.out.println();
				}
				break;

			case 3:
				System.out.println("liste des clients");
				System.out
						.println("-------------------------------------------------------------");
				res = conn.list_client();
				Gestion.print("ID", 10);
				Gestion.print("NUM_CLIENT", 30);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("NUM_CLIENT"), 30);
					System.out.println();
				}
				break;

			case 4:
				System.out.println("produit le plus vendus");
				System.out
						.println("-------------------------------------------------------------");
				res = conn.max_vend();
				Gestion.print("id", 10);
				Gestion.print("num_produit", 30);
				Gestion.print("dispo", 10);
				Gestion.print("vendu", 10);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("num_produit"), 30);
					Gestion.print(String.valueOf(res.getInt("dispo")), 10);
					Gestion.print(String.valueOf(res.getInt("vendu")), 10);
					System.out.println();
				}
				break;

			case 5:
				System.out.println("les clients les plus dépensiers");
				System.out
						.println("-------------------------------------------------------------");
				res = conn.max_dep();
				Gestion.print("ID", 5);
				Gestion.print("NUM_CLIENT", 30);
				Gestion.print("SUM", 10);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 5);
					Gestion.print(res.getString("NUM_CLIENT"), 30);
					Gestion.print(String.valueOf(res.getInt("SUM")), 10);
					System.out.println();
				}
				break;
			case 6:
				System.out.println("6 - licencier les emballeur");

				System.out
						.println("-------------------------------------------------------------");
				System.out.println("entre l'erreur maximun autorise");
				int i = Gestion.readInt();
				conn.licencier(i);
				break;
			case 0:
				System.out.println("FIN");
				break;

			default:
				System.out.println("ERREUR!");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return c;
	}
}