	import java.io.*;
	import java.sql.*;
	import java.util.Scanner;
public class Bdd {


	/*
	 *  Connexion ` un compte de douane ;

 		Visualiser une liste de toutes les commandes qui  ́taient exp ́di ́es au pays de la douane ;


 		Visualiser une liste de toutes les commandes contrˆl ́es par cette douane ;

 		Visualiser une liste de toutes les commandes exp ́di ́es au pays de la douane mais
	
		non-contrˆl ́es ;

 		Rechercher parmi les commandes par le num ́ro de la commande, destination, et

		contenu ;
 		Visualiser les d ́tails d’une commande ;

 		Entrer les r ́sultats d’un contrˆle ;

	  */
	    
	    static Scanner in = new Scanner(System.in);
	    static Connectionbdd connecte;


	    
	    public static int printMenu() {
		int c = -1; 
		
		System.out.print("\033c"); //nettoyage de l'ecran
		
		// -------------------
		// Impression du menu
		// -------------------
		
		System.out.println("-----------------------------INTERFACE GRAPHIQUE-------------------------------- ");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - lister les commandes par pays");
		System.out.println("1 - Visualiser une liste de toutes les commandes contrˆl ́es par cette douane ");
		System.out.println("2 - Rechercher parmi les commandes par le num ́ro de la commande, destination, et contenu ;");
		System.out.println("3 - Visualiser les d ́tails d’une commande ;");
		System.out.println("4 - Entrer les r ́sultats d’un contrˆle");
	
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
			    System.out.println("Visualisation du contenu de la table des colis en_transite");	
			    ResultSet contenu = connecte.contenuTable();
			    print("num_colis", 6);
			    print("client", 40);
			    print("type_acheminement", 9);
			    print("numero_conteneur", 40);
			    print("numero_palette", 60);
			    print("description_produit", 80);
			    print("provenance_colis", 100);
			    print("destination_colis", 120);
			    print("statut", 160);
			    print("date_commande", 200);
			    System.out.println();
			    while (contenu != null && contenu.next()) {
				print(String.valueOf(contenu.getInt("num_colis")), 6);
				print(String.valueOf(contenu.getInt("C")), 1);
				print(contenu.getString("Type_acheminement"), 9);
				print(contenu.getString("numero_conteneur"), 40);
				print(contenu.getString("numero_palette"), 60);
				print(contenu.getString("description_produit"), 80);
				print(contenu.getString("description_produit"), 100);
				print(contenu.getString("description_produit"), 120);
				print(contenu.getString("description_produit"), 160);
				print(contenu.getString("description_produit"), 200);
				
				
				System.out.println();
			    }
			    break;

			case 2 :	
			    System.out.println("Visualisation du contenu de la table des douanes ");	
			    ResultSet contenu1 = connecte.contenuTable1();
			    print("C", 6);
			    print(" resultat_controle", 40);
			    print("type_acheminement", 9);
			    print(" resultat_controle", 40);
			    
			    
			    System.out.println();
			    while (contenu1 != null && contenu1.next()) {
				print(String.valueOf(contenu1.getInt("C")), 6);
				
				print(contenu1.getString("type_acheminement"), 9);
				print(contenu1.getString("resultat_controle"), 40);
				
				
				
				System.out.println();
			    }
			    break;
				/*System.out.println("Insertion tuples definis par l'utilisateur dans la table hotel.");		    
			    System.out.println("A implementer");
			    //connecte.insertionTuplesUtilisateur(num, nom, ville,etoiles, directeur);
			    break;*/
			    
			case 3 :
			    
			    break;
			
			case 4 :
			   
			    break;
			    
			case 5 : 
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
	    
	    
	    public static void main(String[] args) {
		
	
		
		
		try 
		    {
			
			
			connecte = new Connectionbdd(args[0], args[1]);
			
			
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


