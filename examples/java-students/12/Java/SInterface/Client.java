import java.sql.*;

public class Client {
	ConnectionB conn;
	ResultSet res;
	String num;

	public Client(ConnectionB c, String n) {
		conn = c;
		num = n;
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
		System.out.println("1- afficher liste de produits disponibles");
		System.out.println("2- etat de vos commandes");
		System.out.println("3- change le mot de pass et login");
		System.out
				.println("-------------------------------------------------------------");

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
				System.out.println(" Liste de tout les produits disponible");
				System.out
						.println("-------------------------------------------------------------");
				res = conn.list_prod();
				Gestion.print("id", 5);
				Gestion.print("Num_produit", 30);
				Gestion.print("DISPO", 30);
				Gestion.print("PRIX", 10);
				Gestion.print("DESCRIPTION", 100);
				Gestion.print("POID", 50);
				Gestion.print("VENDU", 20);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("Num_produit"), 30);
					Gestion.print(String.valueOf(res.getInt("DISPO")), 10);
					Gestion.print(String.valueOf(res.getInt("PRIX")), 10);
					Gestion.print(res.getString("DESCR"), 100);
					Gestion.print(String.valueOf(res.getInt("POIDS")), 10);
					Gestion.print(String.valueOf(res.getInt("VENDU")), 10);
					System.out.println();
				}
				break;
				
			case 2:
				System.out.println(" Etat de vos commnde");
				System.out
						.println("-------------------------------------------------------------");
				System.out.println("dans le champs traite -0 signifie"
						+ " non traité, 1 traité, 2 en cours");
				res = conn.cmd(num);
				Gestion.print("ID", 5);
				Gestion.print("num_cmd", 30);
				Gestion.print("trait", 2);
				System.out.println();

				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 5);
					Gestion.print(res.getString("num_cmd"), 30);
					Gestion.print(String.valueOf(res.getInt("triate")), 2);
					System.out.println();
				}
				System.out.println();
				break;
				
			case 3:
				System.out.println("Change le mot de pass et login");
				System.out.println("Veillez entre la nouvelle login:");
				String l = Gestion.readString();
				System.out.println("veuillez enter votre nouvelle mot pass");
				String m = Gestion.readString();
				conn.changer_login(num, l, m);

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
