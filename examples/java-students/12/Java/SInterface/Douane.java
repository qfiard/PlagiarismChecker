import java.sql.*;

public class Douane {
	ConnectionB conn;
	ResultSet res;

	public Douane(ConnectionB c) {
		conn = c;
	}

	public int printMenu() {
		int c = -1;
		int i = 0;
		System.out.print("\033c"); // nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 -Contenu d'un palette");
		System.out.println("2 -Contenu d'un contenneur:");
		// System.out.println("3 -Prix total d'un commande:");
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
				System.out.println("List de colis:");
				System.out.println("Entre l'id de palette que vous "
						+ "voulez voir les colis:");
				i = Gestion.readInt();
				res = conn.list_colis_c(i);
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

			case 2:
				System.out.println("List de palette:");
				System.out.println("Entre l'id de contenneur que vous "
						+ "voulez voir les palettes:");
				i = Gestion.readInt();
				res = conn.list_pal(i);
				Gestion.print("ID", 10);
				Gestion.print("num_palette", 30);
				System.out.println();
				while (res != null && res.next()) {
					Gestion.print(String.valueOf(res.getInt("ID")), 10);
					Gestion.print(res.getString("num_pal"), 30);
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