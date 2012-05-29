import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InterfaceSimple {
	static Scanner in = new Scanner(System.in);
	static Statement st;
	static Connection conn;

	
	
	public static String lectureFichierSql(){
		String s ="";
		File table = new File("creaTable.sql");
		try {
			Scanner sc = new Scanner(table);
			while( sc.hasNextLine()){
				s +=sc.nextLine()+"\n";
			}
			System.out.println(s);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}
	/** Imprime le menu a l'ecran. */
	public static int printMenu() {
		int c = -1; // le choix de l'utilisateur

		System.out.print("\033c"); // nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - gerant - afficher la liste des clients");
		System.out.println("2 - gerant - afficher la liste des employes");
		System.out.println("3 - gerant - afficher la liste des produits les plus vendus");
		System.out.println("4 - gerant - afficher la liste des clients les plus depensiers");
		System.out.println("5 - gerant - lister le nombre de colis que les emballeurs traitent par jour");
		System.out.println("6 - gerant - changer le prix d'un produit");
		System.out.println("7 - douane - lister les palettes d'un conteneur");
		System.out.println("8 - douane - lister les colis d'une palette");
		System.out.println("9 - douane - lister les produits d'un colis");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = readInt();

		System.out.print("\033c"); // nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------

		switch (c) {
		case 1:
			try {
				System.out.println("Affichage la liste des clients");

				st = conn.createStatement();
				String adresse;
				String sql = "select * from client order by nom_societe;";
				ResultSet contenu = st.executeQuery(sql);
				print("  NUM_CLIENT", 30);
				print("  SOCIETE", 20);
				print("  ADRESSE", 50);
				print("  VILLE", 20);
				print("  PAYS", 10);
				System.out.println();
				while (contenu != null && contenu.next()) {
					print(contenu.getString("num_client"), 30);
					print(" | " + contenu.getString("nom_societe"), 20);
					adresse = contenu.getString("adresse");
					print(" | " + adresse.substring(0, Math.min(adresse.length()-1,40)), 50);
					print(" | " + contenu.getString("ville"), 20);
					print(" | " + contenu.getString("pays"), 10);
					System.out.println();
				}
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		case 2:
			
			try {
				System.out.println("Affichage de la liste des employes");

				st = conn.createStatement();
				String sql = "select nom from emballeur union select nom from transporteur;";
				ResultSet contenu = st.executeQuery(sql);
				print("  NOMS", 40);
				System.out.println();
				while (contenu != null && contenu.next()) {
					print(contenu.getString("nom"), 40);
					System.out.println();
				}
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		case 3:
			try {
				System.out.println("Affichage de la liste des 20 produits les plus vendus");

				st = conn.createStatement();
				String sql = "SELECT num_produit, quantite_vendue FROM produit ORDER BY quantite_vendue  DESC;";
				ResultSet contenu = st.executeQuery(sql);
				int count =0;
				
				print("  NUM_PRODUIT", 30);
				print("  QUANTITE VENDUE", 20);
				System.out.println();
				while (contenu != null && contenu.next() && count<20) {
					print(contenu.getString("num_produit"), 30);
					print(" | " + contenu.getString("quantite_vendue"), 20);
					count++;
					System.out.println();
				}
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		case 4:
			try {
				System.out.println("Affichage de la liste des 20 clients les plus depensiers");
				st = conn.createStatement();
				String sql = "SELECT num_client, SUM(prix) as total FROM commande GROUP BY num_client ORDER BY total DESC;";
				ResultSet contenu = st.executeQuery(sql);
				int count =0;
				print("  NUM_CLIENT", 30);
				print("  TOTAL", 20);
				System.out.println();
				while (contenu != null && contenu.next() && count<20) {
					print(contenu.getString("num_client"), 30);
					print(" | " + contenu.getString("total"), 20);
					count++;
					System.out.println();
				}
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		case 5:	
			try {
				System.out.println("Affichage du nombre de colis que chaque emballeur traite par jour");
				st = conn.createStatement();
				String sql = "	select num_emballeur,( count (*) /(max(date_emballage) -min(date_emballage))) as colis_par_jour from colis group by num_emballeur order by colis_par_jour desc;";
				ResultSet contenu = st.executeQuery(sql);
				int count =0;
				print("  NUM_EMBALLEUR", 30);
				print("  NOMBRE DE COLIS TRAITE PAR JOUR", 20);
				System.out.println();
				while (contenu != null && contenu.next() && count<20) {
					print(contenu.getString("num_emballeur"), 30);
					print(" | " + contenu.getString("colis_par_jour"), 20);
					count++;
					System.out.println();
				}
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		case 6:
			try {
				System.out.println("Chagement du prix d'un produit : \n");
				System.out.println("veuillez entrer le numero du produit :");
				String numProd = readString();
				st = conn.createStatement();
				String sql = "	select prix from produit where num_produit = '"+numProd+"' ;";
				ResultSet contenu = st.executeQuery(sql);
				contenu.next();
				System.out.println("prix actuel = " + contenu.getInt("prix"));
				System.out.println("veuillez entrer le nouveau prix :");
				int nouveau_prix = readInt();
				st.close();
				st = conn.createStatement();
				sql = "update produit set prix = "+nouveau_prix+" where num_produit = '"+numProd+"' ;";
				st.executeQuery(sql);
				st.close();
				break;

			} catch (SQLException e) {
				e.printStackTrace();
				break;
			}
			
		case 7:
			try {
				System.out.println("lister les palettes d'un conteneur");
				System.out.println("veuillez entrer le numero du conteneur :");
				int numCont = readInt();
				st = conn.createStatement();
				String sql = "	select num_palette from palette where num_conteneur = "+numCont+" ;";
				ResultSet contenu = st.executeQuery(sql);
				print("  NUM_PALETTE", 30);
				System.out.println();
				while (contenu != null && contenu.next()) {
					print(contenu.getString("num_palette"), 30);
					System.out.println();
				}
				st.close();
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		case 8:
			try {
				System.out.println("lister les colis d'une palette");
				System.out.println("veuillez entrer le numero de la palette :");
				int numPal = readInt();
				st = conn.createStatement();
				String sql = "	select num_colis from colis where num_palette = "+numPal+" ;";
				ResultSet contenu = st.executeQuery(sql);
				print("  NUM_COLIS", 30);
				System.out.println();
				while (contenu != null && contenu.next()) {
					print(contenu.getString("num_colis"), 30);
					System.out.println();
				}
				st.close();
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		case 9:
			try {
				System.out.println("lister les produits d'un colis");
				System.out.println("veuillez entrer le numero du colis");
				int numCol = readInt();
				st = conn.createStatement();
				String sql = "	select num_commande from colis where num_colis = "+numCol+" ;";
				ResultSet contenu = st.executeQuery(sql);
				contenu.next();
				int numCom = contenu.getInt("num_commande");
				st.close();
				st = conn.createStatement();
				sql = "	select num_produit,quantite from achat where num_commande = "+numCom+" ;";
				contenu = st.executeQuery(sql);
				print("  NUM_PRODUIT", 30);
				print("  QUANTITE", 30);
				System.out.println();
				while (contenu != null && contenu.next()) {
					print(contenu.getString("num_produit"), 30);
					print(" | " + contenu.getInt("quantite"), 20);
					System.out.println();
				}
				st.close();
				break;

			} catch (SQLException e) {
				e.printStackTrace();
			}	
		case 0:
			System.out.println("FIN");
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

	public static void print(String s, int i) {
		System.out.print(s);
		for (i -= s.length(); i >= 0; i--)
			System.out.print(" ");
	}

	/** Imprime les recommandations d'usage de la classe ChaineHotels a l'ecran. */
	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java ChaineHotels <nomUtilisateur>");
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
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"
					+ args[0], args[0], password);

			// ---------------------------------------
			// Impression du menu. Pour finir, tapez 0
			// ---------------------------------------

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
			conn.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
