import java.sql.*;

public class Emballeur {
	ConnectionB conn;
	String nom;
	ResultSet res;

	public Emballeur(String log, ConnectionB c) {
		nom = log;
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
		System.out.println("1 - List de colis prepare ");
		System.out.println("2 - list de commande non prepare");
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
				System.out.println("1 - List de colis prepare ");
				res = conn.lis_colis(nom);
				Gestion.print("ID", 10);
				Gestion.print("num_colis", 20);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("num_colis"), 30);
					System.out.println();
				}
				System.out.println();
				break;

			case 2:
				System.out.println("2 - list de commande non prepare");
				res = conn.lis_cmd();
				Gestion.print("ID", 10);
				Gestion.print("num_cmd", 30);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("num_cmd"), 30);
					System.out.println();
				}
				System.out.println();
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
