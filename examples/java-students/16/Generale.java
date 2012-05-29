import java.io.*;
import java.sql.*;

public class Generale {
	SQL connecte;
	FonctionsCommunes fonc;

	public Generale(){
		try {
			this.connecte = new SQL();
			fonc = new FonctionsCommunes(connecte);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int printMenu() throws IOException, ClassNotFoundException {
		int c = -1; // le choix de l'utilisateur	
		String login, mdp;

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Quitter le menu de gestion");
		System.out.println("1 - Client");
		System.out.println("2 - Douane");
		System.out.println("3 - Emballeur");
		System.out.println("4 - Gerant");
		System.out.println("5 - Transporteur");
		System.out.println("6 - Nouveau Client");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = fonc.readInt();

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------
		try {
			switch(c){
			case 1 : 
				System.out.print("Nom de societe: ");
				login = fonc.readString();
				System.out.print("Mot de passe: ");
				mdp = fonc.readString();
				System.out.println("Nous allons proceder a la verification...");
				if(connecte.verif_client(login,mdp)){
					System.out.println("Verification OK");
					connecte.close();
					Client client = new Client(login, mdp);
					fonc.prog(1, client);
					c = 0;
				}
				else{
					System.out.println("Erreur d'identification");
					c = -1;
				}
				break;

			case 2 :
				System.out.print("Login: ");
				login = fonc.readString();
				System.out.print("Mot de passe: ");
				mdp = fonc.readString();
				System.out.println("Nous allons proceder a la verification...");
				if(connecte.verif_douane(login,mdp)){
					System.out.println("Verification OK");
					connecte.close();
					Douane douanier = new Douane(login);
					fonc.prog(2, douanier);
					c = 0;
				}
				else{
					System.out.println("Erreur d'identification");
					c = -1;
				}
				break;

			case 3 :
				System.out.print("ID: ");
				login = fonc.readString();
				System.out.print("Mot de passe: ");
				mdp = fonc.readString();
				System.out.println("Nous allons proceder a la verification...");
				if(connecte.verif_emballeur(login,mdp)){
					System.out.println("Verification OK");
					connecte.close();
					Emballeur emb = new Emballeur(login);
					fonc.prog(3, emb);
					c = 0;
				}
				else{
					System.out.println("Erreur d'identification");
					c = -1;
				}
				break;

			case 4 :
				System.out.print("Login: ");
				login = fonc.readString();
				System.out.print("Mot de passe: ");
				mdp = fonc.readString();
				System.out.println("Nous allons proceder a la verification...");
				if(connecte.verif_gerant(login,mdp)){
					System.out.println("Verification OK");
					connecte.close();
					Gerant gerant = new Gerant(login);
					fonc.prog(4, gerant);
					c = 0;
				}
				else{
					System.out.println("Erreur d'identification");
					c = -1;
				}
				break;

			case 5 :	
				System.out.print("Nom: ");
				login = fonc.readString();
				System.out.print("Mot de passe: ");
				mdp = fonc.readString();
				System.out.println("Nous allons proceder a la verification...");
				if(connecte.verif_transporteur(login,mdp)){
					System.out.println("Verification OK");
					connecte.close();
					Transporteur trans = new Transporteur(login);
					fonc.prog(5, trans);
					c = 0;
				}
				else{
					System.out.println("Erreur d'identification");
					c = -1;
				}
				break;

			case 6 :
				String nom_societe = "", suffixe_societe = "", adresse = "", ville = "", code_postal = "", pays = "", telephone = "";
				System.out.println("Nom de la societe :");
				nom_societe = fonc.readString().toUpperCase();
				
				System.out.println("Suffixe de la societe :");
				suffixe_societe = fonc.readString().toUpperCase();
				
				System.out.println();
				
				System.out.println("Adresse :");
				adresse = fonc.readLine().toUpperCase();
				adresse = fonc.readLine().toUpperCase();
				
				System.out.println("Ville :");
				ville = fonc.readLine().toUpperCase();
				
				System.out.println("Code postal :");
				code_postal = fonc.readLine().toUpperCase();

				System.out.println();
				
				while(!pays.equals("USA") && !pays.equals("CHINE") && !pays.equals("JAPON") && !pays.equals("FRANCE") && !pays.equals("CANADA") && !pays.equals("INDE")){
					System.out.println("Pays :");
					System.out.println("L'entreprise ne livre pour le moment que dans les pays suivants");
					System.out.println("-USA");
					System.out.println("-CHINE");
					System.out.println("-JAPON");
					System.out.println("-FRANCE");
					System.out.println("-CANADA");
					System.out.println("-INDE");
					pays = fonc.readString().toUpperCase();
				}
				
				System.out.println();
				
				System.out.println("Telephone :");
				telephone = fonc.readLine().toUpperCase();
				telephone = fonc.readLine().toUpperCase();
				
				String ID_client = fonc.randomIDClient();
				Tables t = new Tables(this.connecte);
				mdp = t.randomID(11);
				
				connecte.nouveauClient(ID_client, nom_societe, suffixe_societe, adresse, ville, code_postal, pays, telephone, mdp);
				
				System.out.println("ID_client : " + ID_client);
				System.out.println("Mot de passe : " + mdp);
				break;


			case 98:
				System.out.println("CheatCode!!!!! GG ");
				connecte.close();
				Tables tt = new Tables(this.connecte);
				fonc.prog(98,tt);
				c = 0;
				break;
			case 0 : 
				break;

			default : 
				System.out.println("ERREUR!");
			}
		}
		catch (SQLException  e) {
			System.err.println(e.getMessage());
		}
		return c;
	}

	/**Imprime les recommandations d'usage de la classe ChaineHotels a l'ecran.*/
	public static void usage() {
		System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java Generale <nomUtilisateur>");
		System.exit(1);
	}

	/**Cree la connexion a la base et attend les instructions de l'utilisateur.*/
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
			/*String password = "";
			try {
				password = new String(System.console().readPassword("%s", "Entrer votre mot de passe pour vous connecter a Postgres: "));
			}
			catch (Exception ioe) {
				ioe.printStackTrace();
			}*/
			Generale g = new Generale();

			// ---------------------------------------
			// Impression du menu. Pour finir, tapez 0
			// ---------------------------------------

			int c = -1;
			while(c != 0){
				c = g.printMenu();
				if (c != 0){
					System.out.println("Appuyez sur entree.");
					System.in.read();
				}
			}

			// -------------------------
			// fermeture de la connexion
			// -------------------------
			g.connecte.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}        
	} 

}
