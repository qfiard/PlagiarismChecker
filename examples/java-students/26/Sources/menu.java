import java.io.IOException;
import java.sql.SQLException;


public class menu {
	static ConnectionBase connection;

	public static int printMenu() throws SQLException, ClassNotFoundException, IOException {
		int c = -1; // le choix de l'utilisateur
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------
		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - Connection au compte gerant.");
		System.out.println("2 - Connection a un compte client.");
		System.out.println("3 - Connection a un compte emballeur.");
		System.out.println("4 - Connection a un compte transporteur.");
		System.out.println("5 - Connection a un compte de douane.");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();


		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------

		switch(c){
		case(0):
		break;
		case(1):
			new gerant(connection);
		break;
		case(2):
			new Clients(connection);
		break;
		case(3):
			new Emballeur(connection);
		break;
		case(4):
			new Transporteur(connection);
		break;
		case(5):
			new Douane(connection);
		break;
		default : 
			System.out.println("ERREUR!");
		}
		return c;
	}

	public menu(ConnectionBase conn) throws IOException, SQLException, ClassNotFoundException {
		connection=conn;
		int c = -1;
		while(c != 0){
			c = printMenu();
			if (c != 0){
				System.out.println("Appuyez sur entree.");
				System.in.read();
			}
		}
	}

}
