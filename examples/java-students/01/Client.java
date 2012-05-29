import java.io.*;
import java.sql.*;
import java.util.Scanner;

class Client {
    //static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Scanner in = new Scanner(System.in);
    static ConnectionStartUp connecte;
    
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
	System.out.println("1 - lister la liste de tout les produit."); 
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
		    // On crée un canal de communication
Statement st= db.createStatement();
// On envoie une requête
ResultSet res= st.executeQuery("select * from Etud");
// Tant qu’il y a des lignes dans le résultat..
while (res.next()) {
// on lit les valeurs des champs
System.out.println("col 1 = " + rs.getString("Nom"));
}
res.close();
st.close();
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
	
	
	try
	    {
		// -------------------
		// Connexion a la base
		// --------------------
		String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
		connecte = new ConnectionStartUp(args[0], password);
		
		
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
