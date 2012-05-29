import java.util.Scanner;

public class Gestion {

	static Scanner in = new Scanner(System.in);
	static ConnectionB connecte;
	static Client c;
	static Gerant g;
	static Transporteur t;
	static Douane d;
	static Emballeur e;

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

	/** Cree la connexion a la base et attend les instructions de l'utilisateur. */
	public static void main(String[] args) {
		try {
			// -------------------
			// Connexion a la base
			// --------------------
			connecte = new ConnectionB("rymex", "azerty");// à modifier
			System.out.println("Voulez-vous importer data.csv dans la base de donnée");
			int su = readInt();
			if (su == 1) {
				new Remplisse(connecte);
			}

			System.out
					.println("entre votre login pour acceder a votre compte ");
			String log = readString();
			String mdp = PasswordField
					.readPassword("Entrer votre mot de passe pour vous connecter a votre compte: ");
			String fc = connecte.Verif_fonc(log, mdp);
			System.out.println("Vous etre connecte etant " + fc);

			if (fc.equals("client")) {
				String num = connecte.getnum(log, mdp);
				c = new Client(connecte, num);
				int i = -1;
				while (i != 0) {
					i = c.printMenu();
					if (i != 0) {
						System.out.println("Appuyez sur entree.");
						System.in.read();
					}
				}
			} else if (fc.equals("gerant")) {
				g = new Gerant(connecte);
				int i = -1;
				while (i != 0) {
					i = g.printMenu();
					if (i != 0) {
						System.out.println("Appuyez sur entree.");
						System.in.read();
					}
				}

			} else if (fc.equals("emballeur")) {
				e = new Emballeur(log, connecte);
				int i = -1;
				while (i != 0) {
					i = e.printMenu();
					if (i != 0) {
						System.out.println("Appuyez sur entree.");
						System.in.read();
					}
				}

			} else if (fc.equals("douane")) {
				d = new Douane(connecte);
				int i = -1;
				while (i != 0) {
					i = d.printMenu();
					if (i != 0) {
						System.out.println("Appuyez sur entree.");
						System.in.read();
					}
				}
			} else if (fc.equals("transporteur")) {
				t = new Transporteur(connecte);
				int i = -1;
				while (i != 0) {
					i = t.printMenu();
					if (i != 0) {
						System.out.println("Appuyez sur entree.");
						System.in.read();
					}
				}

			} else {
				System.out.println("Mot de passe OU/ET login est incorrect.");
				System.out.println("Veuillez Recommence!!!!!!!!!!!!");
				System.exit(1);
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
