import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Menu {
    //static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Scanner in = new Scanner(System.in);
    static ConnectionHotel connecte;


    /** Imprime le menu a l'ecran.*/
    public static int printMenu() {
	int c = -1; // le choix de l'utilisateur
	
	System.out.print("\033c"); //nettoyage de l'ecran
	
	// -------------------
	// Impression du menu
	// -------------------
	
	System.out.println("Veuillez entrer votre choix :");
	System.out.println("-------------------------------------------------------------");
	
	System.out.println("0 - fin");
	System.out.println("1 - voir votre profil : Gerant ");
	System.out.println("2 - voir tous les clients");
	System.out.println("3 - voir douane");
	System.out.println("4- voir tous les produits");
	System.out.println("5- changer le prix d'un produit");
	//System.out.println("5 - ");
	//System.out.println("6- modifier le mot de passe ( mise à jour)");

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
		case 1 : // -- voir profil du gerant 
		    System.out.println("Votre profil : ");
		    ResultSet contenuG = connecte.afficherProfilGerant();
		    System.out.println();
		    System.out.println("prenom:    nom:            login:     password:  ");
		    System.out.println("-------    ----            ------     ---------");
		    System.out.println();
		      while (contenuG != null && contenuG.next()) {
			print(contenuG.getString("prenom"), 10);
			print(contenuG.getString("nom"), 15);
			print(contenuG.getString("login"), 10);
			print(contenuG.getString("mot_passe"), 10);
			System.out.println();
			}
		    break;

		case 2 ://-- voir table client
		    System.out.println("Visualisation du contenu de la table client : ");
		    ResultSet contenuC = connecte.affichertousClients();
		    System.out.println();									      
												     
		    System.out.println("login:     nom_soc:   adresse:                                 c_postal:           tel:                  mot_passe:");
		    System.out.println("------     --------   --------                                 ---------           ----                  ----------");
		    	System.out.println();
		      while (contenuC != null && contenuC.next()) {
			print(contenuC.getString("login"), 15);
			print(contenuC.getString("nom_soc"), 10);
			print(contenuC.getString("adresse"), 40);
			print(contenuC.getString("c_postal"), 15);
			print(contenuC.getString("tel"), 20);
			print(contenuC.getString("mot_passe"), 15);
			System.out.println();
			}
		  
		    break;
		    
		case 3 : //--- voir table douane
		    System.out.println("Visualisation de la table Douane");	
		    ResultSet contenuD = connecte.afficherDouane();
		    System.out.println();
		    System.out.println("pays:           login:               password:");
		    System.out.println("-----           ------               ---------");
		    System.out.println();
		    while (contenuD != null && contenuD.next()) {
			print(contenuD.getString("pays_d"), 15);
			print(contenuD.getString("login"), 20);
			print(contenuD.getString("mot_passe"), 20);
			System.out.println();
		    }
		    break;

		case 4 : //--- voir table poduit
		    System.out.println("Visualisation de la table Douane");	
		    ResultSet contenuP = connecte.afficherProduits();
		    System.out.println();
		    System.out.println("code_pr:        type_pr         prix_u:             stock:");
		    System.out.println("-----           -------         -------             ------");
		    System.out.println();
		    while (contenuP != null && contenuP.next()) {
			print(contenuP.getString("code_pr"), 20);
			print(contenuP.getString("type_pr"), 10);
			print(contenuP.getString("prix_u"), 20);
			print(contenuP.getString("stock"), 20);
			System.out.println();
		    }
		    break;
		case 5 : //-- changer le prix d'un produit
		    System.out.println("changement du prix d'un produit");
		    System.out.println("entrer le code du produit souhaité ");
		    String code_pr = readString();
		    System.out.println("entrer le nouveau prix ");
		    float prix_u = readFloat();			
		    connecte.updatePriceProduit(code_pr,prix_u);
		    break;
		
		case 6: //--- changer de password ( pour quiconque ! )
		    System.out.println("vous êtes : gerant , client , douane , emballeur , transporteur ?");
		    String quoi = readString();
		    System.out.println("entrer votre login");
		    String login = readString();
		    System.out.println("entrer votre password");
		    String password = readString();	 
		    connecte.updatePasswordUser(quoi,login,password);
		    break;
 
		case 7: //--- se connecter ( pr quiconque ! ) 
		    System.out.println("");
		    System.out.println("vous êtes : gerant , client , douane , emballeur , transporteur ?");
		    String qui = readString();
			 System.out.println("entrer votre login");
		    String log = readString();
		    System.out.println("entrer votre password");
	            String pass = readString();	
		    connecte.seconnecter(qui,log,pass);
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
	static public float readFloat(){
	try{
	    return in.nextFloat();
	}
	catch(Exception e){
	    e.printStackTrace();
	    return 0;
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
		connecte = new ConnectionHotel("infou012", password);
		
		
		// ---------------------------------------
		// Impression du menu. Pour finir, tapez 0
		// ---------------------------------------
		
		int c = -1;
		while(c != 0){
		    c = printMenu();
		    if (c != 0){
			System.out.println();
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
