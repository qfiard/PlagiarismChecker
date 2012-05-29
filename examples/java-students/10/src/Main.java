import java.awt.HeadlessException;
import java.sql.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {
	// static BufferedReader in = new BufferedReader(new
	// InputStreamReader(System.in));
	static Scanner in = new Scanner(System.in);
	static Connecte connecte;

	/**
	 * Imprime le menu a l'ecran.
	 * 
	 * @throws SQLException
	 */
	public static int printMenu() throws SQLException {
		int c = -1; // le choix de l'utilisateur

		String[] choix1 = { "1 - Gerant", "2 - Client", "3 - Emballeur",
				"4 - Douane", "5 - Transporteur", "0 - Fin" };
		try {
			String resultat = (String) JOptionPane.showInputDialog(null,
				"Choississez une action", "Choix",
				JOptionPane.QUESTION_MESSAGE, null, choix1, choix1[0]);
			c = (resultat == null) ? 0 : Integer.parseInt(resultat.charAt(0)+"");
		}catch (HeadlessException e){
			System.out.println("Erreur d'affichage!\nSi vous etes en SSH, n'oubliez pas l'ioption -X");
			System.out.println("ssh -X nivose");
			System.exit(1);
		}

		if (c == 0)
			return 0;

		String readS1 = interfaceGraphique.readString("Votre login ?");
		String readS2 = interfaceGraphique.readString("Votre mot de passe ?");

		String query;

		if (c == 1 || c == 3) {
			query = "SELECT mdp FROM employe WHERE type='" + intToNomTable(c)
					+ "' AND login='";
			query += readS1;
			query += "';";
		} else {
			query = "SELECT mdp FROM " + intToNomTable(c) + " WHERE login='";
			query += readS1;
			query += "';";
		}

		Statement st = connecte.conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs;
		String mdp = null;
		rs = st.executeQuery(query);
		if (rs != null && rs.next()) {
			mdp = rs.getString("mdp");
		}
		if (mdp == null || readS2.compareToIgnoreCase(mdp) != 0) {
			interfaceGraphique.affiche("Login ou mdp incorect !");
			return printMenu();
		}

		new interfaceGraphique(c, connecte, readS1);

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

	public static void print(String s, int i) {
		System.out.print(s);
		for (i -= s.length(); i >= 0; i--)
			System.out.print(" ");
	}

	private static String intToNomTable(int c) {
		switch (c) {
		case 1:
			return "gerant";
		case 2:
			return "client";
		case 3:
			return "emballeur";
		case 4:
			return "douane";
		case 5:
			return "transporteur";
		default:
			return null;
		}
	}

	/** Imprime les recommandations d'usage de la classe ChaineHotels a l'ecran. */
	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java Main <nomUtilisateur>");
		System.exit(1);
	}

	/** Cree la connexion a la base et attend les instructions de l'utilisateur. */
	public static void main(String[] args) {
		// ---------------------------
		// Verification des parametres
		// ---------------------------

		if (args.length != 1)
			usage();

		try {
			// -------------------
			// Connexion a la base
			// --------------------
			String password = PasswordField
					.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connecte = new Connecte(args[0], password);

			// ---------------------------------------
			// Impression du menu. Pour finir, tapez 0
			// ---------------------------------------

			int c = -1;
			while (c != 0) {
				c = printMenu();
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
