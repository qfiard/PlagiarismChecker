import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Chaines {
	static Scanner in = new Scanner(System.in);
	static ConnectionBase connecte;


	/** Imprime le menu a l'ecran.
	 * @throws IOException 
	 * @throws ClassNotFoundException */
	public static int printMenu() throws IOException, ClassNotFoundException {
		int c = -1; // le choix de l'utilisateur

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - Creation de toutes les tables.");
		System.out.println("2 - suppression de toutes les tables crees");
		System.out.println("3 - Insertion des commandes, exemplaires et autres (NE FONCTIONNE PAS)");
		System.out.println("4 - Connection a un compte utilisateur.");
		System.out.println("5 - Remplir la base depuis un fichier");
		System.out.println("6 - Mise a jour du nombre de produits vendus.");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = readInt();

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------

		try 
		{
			switch(c){
			case 1 : 
				System.out.println("Creation des tables: \n");
				connecte.creationTable();
				break;

			case 2 :
				System.out.println("Suppression des tables.");
				connecte.suppressionTable();
				break;

			case 3 :
				System.out.println("Insertion de quelques tuples exemples dans la base.");	
				connecte.insertionTuplesPredefinis();
				break;

			case 4 :
				new menu(connecte);
				break;

			case 5 :	
				System.out.println("Nom du fichier?");
				String result= in.next();
				RemplissageBase parseur = new RemplissageBase(result);
				String lol =parseur.recupereTuple();
				System.out.println(lol);
				connecte.inserer(lol);
				
				break;

			case 6 :
				ResultSet list = connecte.test("produit");
				while (list != null && list.next()){
					String ref = list.getString("ref");
					ResultSet resu = connecte.connutil("exemplaire WHERE ref_produit ='"+ref+"'");
					resu.last();
					int i = resu.getRow();  
					resu.beforeFirst();
					connecte.majVendus(i, ref);
				}
				break;

			case 0 : 
				System.out.println("FIN");
				break;

			default : 
				System.out.println("ERREUR!");
			}
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return c;
	}

	static public String readString(){
		try{
			return in.next();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	static public int readInt(){
		try{
			return in.nextInt();   //lecture du choix utilisateur    
		}
		catch(Exception e){
			in.nextLine();
			e.printStackTrace();
			return -1;
		}
	}

	public static void print(String s, int i) {
		System.out.print(s);
		for (i -= s.length(); i >= 0; i --)
			System.out.print(" ");
	}

	/**Imprime les recommandations d'usage de la classe ChaineHotels a l'ecran.*/
	public static void usage() {
		System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java ChaineHotels <nomUtilisateur>");
		System.exit(1);
	}

	/**Cree la connexion a la base et attend les instructions de l'utilisateur.*/
	public static void main(String[] args) {
		// ---------------------------
		// Verification des parametres
		// ---------------------------

		if (args.length != 1)
			usage();


		try 
		{
			// -------------------
			// Connexion a la base
			// --------------------
			String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connecte = new ConnectionBase(args[0], password);


			// ---------------------------------------
			// Impression du menu. Pour finir, tapez 0
			// ---------------------------------------

			int c = -1;
			while(c != 0){
				c = printMenu();
				if (c != 0){
					System.out.println("Appuyez sur entree.");
					System.in.read();
				}
			}


			// -------------------------
			// fermeture de la connexion
			// -------------------------
			connecte.close();
			in.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

} 
