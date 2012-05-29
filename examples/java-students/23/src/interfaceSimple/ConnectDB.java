package interfaceSimple;
import java.sql.*;
import java.util.Scanner;


public class ConnectDB {
	// static BufferedReader in = new BufferedReader(new
	// InputStreamReader(System.in));
	static Scanner in = new Scanner(System.in);
	static RequeteEnonce connecte;

	/** Imprime le menu a l'ecran. */
	public static int printMenu() throws SQLException {
		int c = -1; // le choix de l'utilisateur
		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - Liste des clients");
		System.out.println("2 - Liste des transporteurs");
		System.out.println("3 - Liste des emballeurs");
		System.out.println("4 - Produits les plus vendus");
		System.out.println("5 - Clients les plus depensiers");
		System.out.println("6 - Nombre de colis traité par un emballeur par jour");
		System.out.println("7 - Liste des emballeurs qui traine dans les delais");
		System.out.println("8 - Emballeur dont les colis sont rejetes trop souvent");
		System.out.println("9 - Etat d'une commande pour un client");
		System.out.println("10 - Liste des produits disponible");
		System.out.println("11 - Liste des commandes d'un client");
		System.out.println("12 - Liste des colis fragile,dangereux");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = readInt();

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------

		switch (c) {
		case 1:
			RequeteEnonce.requete(1);
			break;

		case 2:
			RequeteEnonce.requete(2);
			break;

		case 3:
			RequeteEnonce.requete(3);
			break;

		case 4:
			RequeteEnonce.requete(4);
			break;

		case 5:
			RequeteEnonce.requete(5);
			break;

		case 6:
			RequeteEnonce.requete(6);
			break;
			
		case 7:
			RequeteEnonce.requete(7);
			break;
			
		case 8:
			RequeteEnonce.requete(8);
			break;
			
		case 9:
			System.out.println("Etat de la commande n°21 du client UJOXK14368");
			RequeteEnonce.requete(9);
			break;
			
		case 10:
			RequeteEnonce.requete(10);
			break;
			
		case 11:
			System.out.println("Liste des commandes du client SUROK77336");
			RequeteEnonce.requete(11);
			break;
			
		case 12:
			RequeteEnonce.requete(12);
			break;

		case 0:
			System.out.println("A bientot");
			break;

		default:
			System.out.println("ERREUR!");
		}
		
		return c;
	}

	
	static public String readString() {
		try {
			return in.next();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static public int readInt() {
		try {
			return in.nextInt(); // lecture du choix utilisateur
		} catch (Exception e) {
			in.nextLine();
			e.printStackTrace();
			return -1;
		}
	}

	/** Cree la connexion a la base et attend les instructions de l'utilisateur. */
	public static void main(String[] args) {
		try {
			// -------------------
			// Connexion a la base
			// --------------------
			String user = "postgres";
			String passwd = "fatima";
			connecte = new RequeteEnonce(user, passwd);

			int c = -1;
			while (c != 0) {
				c = printMenu();
				if (c != 0) {
					System.out.println("Appuyez sur entree.");
					System.in.read();
				}
			}

			// -------------------------
			// fermeture de la connexion
			// -------------------------
			connecte.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}