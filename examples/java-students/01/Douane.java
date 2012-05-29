import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.lang.Object;

class Douane {
    //static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Scanner in = new Scanner(System.in);
    static ConnectionStartUp connecte;
    
    /** Imprime le menu a l'ecran.*/
    public static int printMenu() {
	Statement st;
	ResultSet result;

	int c = -1; // le choix de l'utilisateur
	System.out.print("\033c"); //nettoyage de l'ecran
	// -------------------
	// Impression du menu
	// -------------------
	System.out.println("Veuillez entrer votre choix :");
	System.out.println("-------------------------------------------------------------");
	System.out.println("0 - fin");
	System.out.println("1 - Renvoyer un colis avec un motif"); 
	System.out.println("2 - Lister les palettes d'un conteneur.");
	System.out.println("3 - Lister les colis d'une palette.");
	System.out.println("4 - Lister les produits d'une colis.");
	System.out.println("5 - Acceder au prix des produits transportee.");
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
		    System.out.println("Entrer l'identifiant du colis a rejetter");
		    String colis = readString();
		    query = "UPDATE colis SET rejet_douane = 'False' WHERE id_colis = '"+colis+"';";
		    st.executeUpdate(query);
		    break;
		case 2 :
		    System.out.println("Liste des palettes d'un conteneur");
		    String conteneur = readString();
		    String query = "SELECT id_conteneur, id_palette FROM conteneur,palette ON id_conteneur = '"+conteneur+"';";
		    
		    result = st.executeQuery(query); 

		    print("ID_CONTENEUR", 40);
		    print("ID_PALETTE", 40);

		    while(result != null && result.next()){
			print(String.valueOf(result.getString("id_conteneur")),40);
			print(result.getString("id_palette"),40);
			System.out.println();
		    }
		    break;
		case 3 :
		    System.out.println("Liste des colis d'une palette");
		    String palette = readString();
		    String query = "SELECT id_palette,id_colis FROM palette,colis ON id_palette = '"+palette+"';";
		    
		    result = st.executeQuery(query); 

		    print("ID_PALETTE", 40);
		    print("ID_COLIS", 40);

		    while(result != null && result.next()){
			print(String.valueOf(result.getString("id_palette")),40);
			print(result.getString("id_colis"),40);
			System.out.println();
		    }
		    break;
		    
		case 4 :
		    System.out.println("Liste des produits d'un colis");
		    String colis = readString();
		    String query = "SELECT id_colis,id_produit FROM colis,produit ON id_colis = '"+colis+"';";
		    
		    result = st.executeQuery(query);

		    print("ID_COLIS", 40);
		    print("ID_PRODUIT", 40);
		 
		    while (result != null && result.next()) {
			print(String.valueOf(result.getString("id_colis")), 40);
			print(contenu.getString("id_produit"), 40);
			System.out.println();
		    }
		    break;

		case 5 :
		    System.out.println("Liste de prix des produits transportes");
		    System.out.println("A implementer");
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
	for (i -= s.length(); i >= 0; i--)
	    System.out.print(" ");
    }
    
    /**Imprime les recommandations d'usage de la classe Douane a l'ecran.*/
    public static void usage() {
	System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
	System.out.println("usage : java Douane <nomUtilisateur>");
	System.exit(1);
    }
    
    /**Cree la connexion a la base et attend les instructions de l'utilisateur.*/
    public static void main(String[] args) {
	// ---------------------------
	// Verification des parametres
	// ---------------------------
	
	if (args.length != 1)
	    usage();
	
	
	try{
	    String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
	    connecte = new ConnectionStartUp(args[0], password);
	    
	    int c = -1;
	    while(c != 0){
		c = printMenu();
		if (c != 0){
		    System.out.println("Appuyez sur entree.");
		    System.in.read();
		}
	    }
	    connecte.close();
	    in.close();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
}
