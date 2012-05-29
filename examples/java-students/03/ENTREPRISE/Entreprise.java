import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Entreprise {
    static Scanner in = new Scanner(System.in);
    static PersonneConnexion conn;

    /**Imprime les recommandations d'usage de la classe Entreprise a l'ecran.*/
    public static void usage() {
	System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
	System.out.println("usage : java Entreprise <nomUtilisateur>");
	System.exit(1);
    }
    
    public static String readPassword(String prompt) {
	String password = "";
	try {
	    password = new String(System.console().readPassword("%s", prompt));
	} catch(Exception ioe) {
	    ioe.printStackTrace();
	}
	return password;
    }
    
    /**Cree la connexion a la base et attend les instructions de l'utilisateur.*/
    public static void main(String[] args) {
	// Verification des parametres
	if (args.length != 1)
	    usage();
		
	try {
	    // Connexion a la base
	    String password = readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
	    conn = new PersonneConnexion(args[0], password);	
	    in.close();

	    // affichage de la fenetre de connexion a un compte
	    SwingUtilities.invokeLater(new Runnable(){
		    public void run() {
			Login fenetre = new Login(conn);
			fenetre.setVisible(true);
		    }
		});		
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }    
} 
