import java.io.*;
import java.sql.*;
import java.util.Scanner;

class Gerant {
    //static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Scanner in = new Scanner(System.in);
    static ConnectionStartUp connecte;
 
    public static int printMenu(){
	ResultSet result;
	Statement st;
	String query;

	int c = -1; // le choix de l'utilisateur
	System.out.print("\033c"); //nettoyage de l'ecran
	
	System.out.println("Veuillez entrer votre choix :");
	System.out.println("-------------------------------------------------------------");
	System.out.println("0 - Fin");
	System.out.println("1 - Changer le prix du produit.");
	System.out.println("X2 - .");
	System.out.println("X3 - Lister les clients.");
	System.out.println("X4 - Lister les employees.");
	System.out.println("X5 - le nombre de colis traite par un emballeur par jour.");
	System.out.println("X6 - Liste des produits les plus vendus.");
	System.out.println("X7 - Liste des clients les plus depensiers.");
	System.out.println("-------------------------------------------------------------");
	
	c = readInt();
	System.out.print("\033c"); //nettoyage de l'ecran
		
	try{
	    switch(c){
	    case 1 :
		System.out.println("Changer le prix du produit : Choississer le produit:");
		String product = readString();
		System.out.println("Choississer le prix");
		double price = Double.parseDouble();
		query = "UPDATE produit SET prix = "+price+" WHERE id_produit = '"+product+"';"; 
		st.executeUpdate(query);
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
    static public double readDouble(){
	try{
	    return in.nextDouble();   //lecture du choix utilisateur
	}
	catch(Exception e){
	    in.nextLine();
	    e.printStackTrace();
	    return -1;
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
	if (args.length != 1)
	    usage();
	try {
	    String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
	    connecte = new ConnectionDouane(args[0], password);
		
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
