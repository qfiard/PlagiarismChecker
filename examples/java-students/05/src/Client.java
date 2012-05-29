import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.lang.*;


public class Client{
    
    static Scanner in = new Scanner(System.in);
    static ConnectionClient connecte;

    public static int printMenu() {

	System.out.println("Veuillez entrer votre choix :");
	System.out.println("0 - fin");
	System.out.println("1 - Vous êtes un client");
	System.out.println("2 - Vous êtes un emballeur");
	System.out.println("3 - Vous êtes un transporteur");
	System.out.println("4 - Vous êtes de la douane");
	System.out.println("5 - Vous êtes le gérant ");
	int a = readInt();
	
	switch(a){
	    case 1: 

		//int c = -1;
 	System.out.println("\033c");

	System.out.println("Veuillez entrer votre choix :");
	System.out.println("0 - fin");
	System.out.println("1 - Suivi du colis");
	System.out.println("2 - Voir les produits disponibles");
	System.out.println("3 - Choisir une date de livraison");
	System.out.println("4 - Changer votre login et votre mot de passe");

	int c = readInt();
	System.out.println("\033c");

	try{
	    switch(c){
	    case 1:
		System.out.println("Suivi du colis");
		connecte.suiviColis();
		break;
	    case 2:
		System.out.println("Produit disponibles");
		connecte.produitDisponible();
		break;
	    case 3:
		connecte.changementDate();
		break;
	    case 0:
		System.out.println("FIN");
		break;
	    default:
		System.out.println("ERREUR");
	    }
	}
	    catch(SQLException e){
		System.err.println(e.getMessage());
	    }
	case 2:
	    System.out.println("Veuillez entrer votre choix :");
	    System.out.println("0 - fin");
	    System.out.println("1 - Commande d'un client");
	    //System.out.println("2 - Colis et palettes conditionné");
	
	int c1 = readInt();
	System.out.println("\033c");

	try{
	    switch(c1){
	    case 1:
		System.out.println("Commande du client");
		connecte.listeCommand();
		break;		
	    case 2:
		System.out.println("Les ");
		
	    case 0:
		System.out.println("FIN");
		break;

	    }

	}
	catch(SQLException e){
	    System.err.println(e.getMessage());
	}
	//TRANSPORTEUR
	case 3:
	    System.out.println("1 - Etats du colis");
	    System.out.println("2 - Date limite de livraison");

	    int c2 = readInt();
	    System.out.println("\033c");
	    try{
		switch(c2){
		case 1:
		    connecte.etatColis();
		    break;
		case 2:
		    connecte.dateLivraison();
		    break;
		}
	    }
	    catch(SQLException e){
		    System.err.println(e.getMessage());
	    }
	    //DOUANE
	case 4:
	    System.out.println("1 - Connexion a un compte de douane");
	    
	    int c3 = readInt();
	    System.out.println("\033c");
	    
	    try{
		switch(c3){
		case 1:
		    System.out.println("Entrez le login : ");
		    String login = in.next();
		    System.out.println("Entrez le mot de passe : ");
		    String mdp =  in.next();
		    if(login.equals("Douane") && mdp.equals("jesuisledouanier")){
			System.out.println("1 - Lister les commandes expédiées aux pays de la douane");
			System.out.println("2 - Lister les commandes controlées");
			System.out.println("3 - Liste des commmandes expédiées mais non controlées");
			System.out.println("4 - Rechercher une commandes par le numéro de commande ou le contenu");
			System.out.println("5 - Visualiser les détails d'un commande");
			System.out.println("6 - Entrer les résultat d'un controle");
			System.out.println("7 - Statistique du contrôle ");
			
			int c4 = readInt();
			System.out.println("\033c");
			
			switch(c4){
			case 1: 
			    connecte.listeDesCommandes();
			    break;
			case 2:
			    connecte.commandeCon();
			    break;
			case 3:
			    connecte.commandeNONCon();
			    break;
			case 4:
			    System.out.println("1 - Recherche par numero de commande ");
			    System.out.println("2 - Recherche par le contenu ");
			    int choix1 = readInt();
			    System.out.println("\033c");
			    try {
				switch(choix1){
				case 1:
				    connecte.rechercheParCommande();
				    break;
				case 2:
				    connecte.rechercheParContenu();
				    break;
				}
			    }
			    catch(SQLException e){
				System.err.println(e.getMessage());
			    }

			case 5:
			    
			case 6:
			    
			case 7:
			}
			
		    }
		    else{
			System.out.println("RECOMMENCEZ");
			break;
		    }

		}



	    }
	    catch(Exception e){
		  //System.err.println(e.getMessage());
	 
	    }
	    //Gerant
	case 5:
	    System.out.println("1 - Changer le prix d'un produit");
	    System.out.println("2 - Licencier un employé qui a un taux d'erreur élevé");
	    System.out.println("3 - Licencier un employé ");
	    System.out.println("4 - Liste les employés ");
	    System.out.println("5 - Lister les clients ");
	    System.out.println("6 - Nombre de colis traité par un emballeur par jour");
	    System.out.println("7 - Les clients les plus dépensier");

	    int c5 = readInt();
	    System.out.println("\033c");
	    try{
		switch(c5){
		case 1:
		    connecte.changePrice();
		    break;
		case 2:
		    connecte.licenciementEmployeTauxDerreur();
		    break;
		case 3:
		    connecte.licenciement();
		    break;
		case 4:
		    connecte.listeEmploye();
		    break;
		case 5:
		    connecte.listeClient();
		    break;
		case 6:
		   
		
		case 7:
		    connecte.clientDepensier();
		    break;
		}
	    }
	    catch(SQLException e){
		System.err.println(e.getMessage());
	    }
	    
     	}
    
 return a;
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
   

    public static void usage() {
	System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
	System.out.println("usage : java Client <nomUtilisateur>");
	System.exit(1);
    }
    



    public static void main(String[] args) {
	if (args.length != 1)
	    usage();

	try 
	    {
		// -------------------
		// Connexion a la base
		// --------------------
		String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
		connecte = new ConnectionClient(args[0], password);
		
		
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
 









