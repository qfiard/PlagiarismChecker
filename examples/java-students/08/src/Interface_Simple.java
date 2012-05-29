import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Interface_Simple {
    //static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Scanner in = new Scanner(System.in);
    static CreationBase connecte;


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
	System.out.println("1 - creation du systeme de gestion");
	System.out.println("2 - suppression de la table ...");
	System.out.println("3 - Nombre de ligne de la table client");
	System.out.println("4 - remplie les tables");
	System.out.println("5 - ...");
	System.out.println("6 - ...");
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
		    System.out.println("Creation des tables du systeme de gestion");
		    connecte.creationTable();
		    break;

		case 2 :
		    System.out.println("Suppression des tables du systeme de gestion");
		    connecte.suppressionTable();
		    break;

		case 3 :
		    System.out.println("Nombre de ligne de la table client");	
		    int contenu = connecte.NombreLigne(connecte.contenuTable("client"));
		    System.out.println("Nombre de Ligne = "+ contenu);
		    break;
		    
		case 4 :
		    System.out.println("Remplissage");
		    Commande250();
		    Quantite250();
		    colis200();
		    palette200();
		    emballage200();
		    transport200();
		    controledesdouanes200();
		    break;

		case 5 :
		    break;
		    
		case 6 :
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

    public static void Commande250(){
    	 try {
			ResultSet rsclient = connecte.contenuTable("client");
			int id_commande = 1;
			int ligneclient = 0;
			int maxclient = connecte.NombreLigne(rsclient);
			System.out.println(maxclient);
			System.out.println(rsclient == null);	
			String numeroduclient = "";
			String pays = "";
			while (rsclient != null && rsclient.first() && id_commande <=250){
				ligneclient = (int)(Math.random() * (maxclient));
				rsclient.relative(ligneclient);
				numeroduclient = rsclient.getString("numeroduclient");
				pays = rsclient.getString("pays");
				connecte.InsertCommande(numeroduclient,id_commande,dateRandom(1,12),pays);
				id_commande++;
			}
			rsclient.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    }
    
    public static void Quantite250(){
    	 try {
    		ResultSet rsproduit = connecte.contenuTable("produit");
    		int i = 1;
    		int ligneproduit = 0;
    		int maxproduit = connecte.NombreLigne(rsproduit);
    		String numeroduproduit = "";
    		int nbr_produit = 0;
    		int quantite_produit = 0;
 			while (rsproduit != null && rsproduit.first() && i <=250){
 				nbr_produit = (int)(Math.random() * (3)) + 1;
 				for(int j = 0; j < nbr_produit; j++){
 					ligneproduit = (int)(Math.random() * (maxproduit));
 					rsproduit.relative(ligneproduit);
 					numeroduproduit = rsproduit.getString("numeroduproduit");
 					quantite_produit = (int)(Math.random() * (10)) + 1;
 					connecte.InsertQuantite(String.valueOf(i), numeroduproduit, quantite_produit);
 					rsproduit.first();
 				}
 				i++;
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
    }
    
    public static int Colis1Commande(int id_colis, int id_commande,String suivi){
    	int id = 0;
    	try {
        	id = id_colis;
			ResultSet rsquantite = connecte.contenuCommande(id_commande,"quantitedeproduit");
			ResultSet rscommande = connecte.contenuCommande(id_commande,"commande");
			rscommande.first();
			String numeroduproduit = "";
			Date d = rscommande.getDate("datedelivraison");
			String date = d.toString();
			int quantite = 0;
			int quantiteparcarton = 0;
			boolean cartonencours = false;
			while(rsquantite != null && rsquantite.next()){
				numeroduproduit = rsquantite.getString("numeroduproduit");
				quantite = rsquantite.getInt("quantite");
				quantiteparcarton = connecte.QtProd(numeroduproduit);
				if((quantite < quantiteparcarton)  && (quantite > (quantiteparcarton/2))){
					if(cartonencours == true){
						id++;
					}
					connecte.InsertColis(String.valueOf(id), quantite, dateRandomEmballage(date), suivi, "0", numeroduproduit, id_commande, "null");
					id++;
					cartonencours = false;
				}else if((quantite > 0)  && (quantite <= (quantiteparcarton/2))){
					connecte.InsertColis(String.valueOf(id), quantite, dateRandomEmballage(date), suivi, "0", numeroduproduit, id_commande, "null");
					cartonencours = true;
				}else if(quantite >= quantiteparcarton){
					if(cartonencours == true){
						id++;
					}
					for(int i = 0 ; i < quantite/quantiteparcarton; i++){
						connecte.InsertColis(String.valueOf(id), quantiteparcarton, dateRandomEmballage(date), suivi, "0", numeroduproduit, id_commande, "null");
						id++;
					}
					int temp = quantite%quantiteparcarton;
					if(temp<= (quantiteparcarton/2) &&
						temp !=0 ){
						connecte.InsertColis(String.valueOf(id), temp, dateRandomEmballage(date), suivi, "0", numeroduproduit,id_commande, "null");
						cartonencours = true;
					} else if(temp >(quantiteparcarton/2)){
						connecte.InsertColis(String.valueOf(id), temp, dateRandomEmballage(date), suivi, "0", numeroduproduit, id_commande, "null");
						id++;
						cartonencours = false;
					}else cartonencours =false;
				}
			}
			if(cartonencours == true){
				id++;
			}
			rscommande.close();
			rsquantite.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return id;
    }
    
    public static void colis200(){
    	int id_colis = 1;
    	String suivi = "100 expedie";
    	try {
			connecte.InsertPalette("0");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	for(int i = 1; i <= 200; i++){
    		id_colis = Colis1Commande(id_colis, i,suivi);   	
    		if(i == 180){
    			suivi = "en cours de transport";
    		}
    		if(i == 190){
    			suivi = "emballe";
    		}
    	}
    }
    
    public static String dateRandom(int i,int j){ // periode de entre i et j mois
    	String jour = "";
    	String mois = "";
    	jour = String.valueOf((int)(Math.random() * (28)) + 1);
    	mois = String.valueOf((int)(Math.random() * ((j+1)-i)) + i);
    	return "2012-"+mois+"-"+jour;
    }
    
    public static String dateRandomEmballage(String date){
    	String[]res = date.split("-");
    	int mois = Integer.valueOf(res[1]);
    	int jour = Integer.valueOf(res[2]);
    	jour = (int)(Math.random() * (28)) + 1;
    	mois = mois - 2;
    	if(mois <= 0){
    		mois = 1;
    	}
    	return "2012-"+String.valueOf(mois)+"-"+String.valueOf(jour);
    }
    
    public static String dateRandomDouane(String date){
    	String[]res = date.split("-");
    	int mois = Integer.valueOf(res[1]);
    	int jour = Integer.valueOf(res[2]);
    	jour = (int)(Math.random() * (28)) + 1;
    	mois = mois + 1;
    	if(mois >= 13){
    		mois = 12;
    	}
    	return "2012-"+String.valueOf(mois)+"-"+String.valueOf(jour);
    }
  
    
    public static int Palette1Commande(int id_palette,int id_commande){
    	int id = 1;
    	try{
    		id = id_palette;
    		String numeroduproduit = "";
        	String numeroduproduitprec = "";
        	String numerodecolis = "";
        	int quantiteparpalette = 0;
        	int quantiteencours = 1;
        	boolean nouvellepalette = true;
    		ResultSet rscolis = connecte.contenuCommande(id_commande, "colis");
    		while(rscolis != null && rscolis.next()){
    			numeroduproduit = rscolis.getString("numeroduproduit");
    			numerodecolis = rscolis.getString("numerodecolis");
    			quantiteparpalette = connecte.QtCart(numeroduproduit);
    			if(numeroduproduitprec.equals(numeroduproduit)){
    				quantiteencours++;
    				if(quantiteencours > quantiteparpalette){
    					id++;
    					quantiteencours = 0;
    					nouvellepalette = true;
    				}
    			}
    			if(nouvellepalette == true){
    			connecte.InsertPalette(String.valueOf(id));
    				nouvellepalette =false;
    			}
    			connecte.UpdateColisPalette(String.valueOf(id),numerodecolis);
    			numeroduproduitprec = numeroduproduit;
    		
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    	id++;
    	return id;
    }
    
    public static void palette200(){
    	int id_palette = 1;
    	for(int i = 1; i <= 190; i++){
    		id_palette = Palette1Commande(id_palette,i);
    	}
    }
    
    public static void Emballage1Commande(int id_commande){
    	try {
			ResultSet rsquantite = connecte.contenuCommande(id_commande, "quantitedeproduit");
			ResultSet rsemballeur = connecte.contenuTable("emballeur");
			ResultSet rsproduit = null;
			String numeroduproduit = "";
			String numerodemballeur = "";
			String qualifiant = "";
			int ligneemballeur = 0;
			int maxemballeur = connecte.NombreLigne(rsemballeur);
			ligneemballeur = (int)(Math.random() * (maxemballeur));
			rsemballeur.first();
			rsemballeur.relative(ligneemballeur);
			numerodemballeur = rsemballeur.getString("numerodemballeur");
			while(rsquantite != null && rsquantite.next()){
				numeroduproduit = rsquantite.getString("numeroduproduit");
				rsproduit = connecte.contenuTableproduit(numeroduproduit);
				rsproduit.next();
				qualifiant = rsproduit.getString("qualifiant");
				if(qualifiant.equals("N")){
					qualifiant = "carton";
				} else if(qualifiant.equals("D")){
					qualifiant = "emballage special";
				} else if (qualifiant.equals("F")){
					qualifiant = "papier bulle";
				}
				
				connecte.InsertEmballage(qualifiant, numerodemballeur, id_commande, numeroduproduit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void emballage200(){
    	for(int i = 1; i <= 200; i++){
    		Emballage1Commande(i);
    	}
    }
    
    public static void transport200(){
    	try {
			ResultSet rspalette = connecte.contenuTable("palette");
			ResultSet rstransporteurs = connecte.contenuTable("transporteurs");
			rspalette.next();
			String []type = {"camion","avion","conteneur"};
			int lignetransporteurs = 0;
			int maxlignetransporteurs = connecte.NombreLigne(rstransporteurs);
			String nomdetransporteur = "";
			String numerodelapalette = "";
			String typedetransport = "";
			while(rspalette != null && rspalette.next()){
				rstransporteurs.first();
				lignetransporteurs = (int)(Math.random() * (maxlignetransporteurs));
				rstransporteurs.relative(lignetransporteurs);
				nomdetransporteur = rstransporteurs.getString("nomdetransporteur");
				numerodelapalette = rspalette.getString("numerodelapalette");
				typedetransport = type[(int)(Math.random() * (3))];
				connecte.InsertTransport(typedetransport, nomdetransporteur, numerodelapalette);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public static void controledesdouanes200(){
    	try{
	    	int nombredecoliscontrole = (int)(Math.random() * (101)) + 50;
	    	int numerodecontrole = 1;
	    	ResultSet rscolis = connecte.contenuTable("colis");
			ResultSet rsdouane = null;
			ResultSet rscommande = null;
			ResultSet rsemballage = null;
			ResultSet rsemballeur = null;
	    	int lignecolis = 0;
	    	int maxlignecolis = connecte.NombreLigne(rscolis);
	    	String numeroducolis = "";
	    	String resultat = "";
	    	Date d = null;
	    	String date = "";
	    	String numerodelapalette = "";
	    	String logindeladouane = "";
	    	int controlecolis = 0;
	    	String destination = "";
	    	int tauxerreur = 0;
	    	int tauxcolis = 0;
	    	int tauxrefus= 0;
	    	int chance = 0;
	    	while(numerodecontrole <= nombredecoliscontrole){
	    		do{
		    		rscolis.first();
		    		lignecolis = (int)(Math.random() * (maxlignecolis));
		    		rscolis.relative(lignecolis);
		    		controlecolis = rscolis.getInt("numeroducontrole");
		    		numeroducolis = rscolis.getString("numerodecolis");
		    		numerodelapalette = rscolis.getString("numerodelapalette");
	    		}while(controlecolis != 0 || numerodelapalette.equals("0"));
	    		rscommande = connecte.contenuCommande(rscolis.getInt("numerodecommande"), "commande");
	    		rscommande.first();
	    		destination = rscommande.getString("destination");
	    		rsdouane = connecte.contenuTableDouane(destination);
	    		rsdouane.first();
	    		logindeladouane = rsdouane.getString("login");
	    		d = rscolis.getDate("datedemballage");
	    		date = d.toString();
	    		date = dateRandomDouane(date);
	    		rsemballage = connecte.contenuTableEmballage(rscolis.getInt("numerodecommande"));
	    		rsemballage.first();
	    		rsemballeur = connecte.contenuTableEmballeur(rsemballage.getString("numerodemballeur"));
	    		rsemballeur.first();
	    		tauxerreur = rsemballeur.getInt("tauxderreur");
	    		tauxcolis = rsdouane.getInt("tauxdecolisverifie");
	    		tauxrefus = (int)((((double)tauxerreur/100.0)*((double)tauxcolis/100.0))*100.0);
	    		chance = (int)(Math.random() * (101));
	    		if(chance < tauxrefus){
	    			resultat = "refuser";
	    		}else {
	    			resultat = "accepter";
	    		}
	    		connecte.InsertControleDouane(numerodecontrole, resultat, date, logindeladouane);
	    		connecte.UpdateColisControle(numerodecontrole, numeroducolis);
	    		numerodecontrole++;
    			}
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}
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
		connecte = new CreationBase(args[0], password);
		
		
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
