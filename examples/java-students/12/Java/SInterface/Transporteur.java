import java.sql.*;

public class Transporteur {
	ConnectionB conn;
	Gestion s;
	ResultSet res;

	public Transporteur(ConnectionB c) {
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
		System.out.println("1 - List de colis avec son etat:");
		System.out.println("2 - List de colis non expediee::");
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
				System.out.println("List de colis avec  son etat :");
				res = conn.etat_coli();
				Gestion.print("ID", 10);
				Gestion.print("qualif", 30);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("qualif"), 30);
					System.out.println();
				}
				System.out.println();
				break;

			case 2:
				System.out.println("List de colis non expediee:");
				res = conn.coli_nE();
				Gestion.print("ID", 10);
				Gestion.print("num_colis", 30);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("num_colis"), 30);
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
