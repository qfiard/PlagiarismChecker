import java.util.*;
import java.math.*;
public class RempliTableAlea {
    static String insert;
    static ConnexionBase connecte;

    // Permet de recuperer l'id contenue dans une requete insert to, afin de la reutiliser dans les tables connexes
    public static String recupId(String s) {
	String [] temp = s.split(",");
	String rep = temp[0].substring(temp[0].length()-8,temp[0].length());
	return (rep);
    }

     public static String recupIdConteneur(String s) {
	String [] temp = s.split(",");
	String rep = temp[0].substring(temp[0].length()-10,temp[0].length()-2);
	return (rep);
    }

    public static String recupIdDouane(String s) {
	String [] temp = s.split(",");
	String rep = temp[0].substring(temp[0].length()-22,temp[0].length());
	return (rep);
    }

    // Permer de recuperer l'etat d'un coli afin de lui faire correspondre les tables d'emballeur, transporteur, etc...
    public static String recupEtat (String s) {
	String [] temp = s.split(",");
	String rep = temp[9];
	return (rep);
    }
    
    public static String randomId (String [] s, boolean annulable) {
	double d;
	if (annulable)
	    d = (s.length+1)*Math.random();
	else 
	    d = s.length*Math.random();
	int i = (int) d;
	if (i == s.length)
	    return ("null");
	else 
	    return (s[i]);
    }

    public static void rempliGerant () {
	insert = GenereInsert.genereGerant();
	connecte.creaTable(insert);
    }

    public static void rempliTransporteur (int nbr) {
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereTransporteur();
	    connecte.creaTable(insert);
	}
    }

    public static String [] rempliEmballeur (int nbr) {
	String [] rep = new String [nbr]; 
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereEmballeur();
	    connecte.creaTable(insert);
	    rep [i] = recupId(insert);
	}
	return rep;
    }	

    public static String [] rempliDouane (int nbr) {
	String [] rep = new String [nbr]; 
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereDouane();
	    connecte.creaTable(insert);
	    rep [i] = recupIdDouane(insert);
	}
	return rep;
    }	
    
    public static String [] rempliClient ( int nbr ) {
	String [] rep = new String [nbr];
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereClient();
	    connecte.creaTable(insert);
	    rep [i] = recupId(insert);
	}
	return rep;
    }

    public static String [] rempliConteneur ( int nbr ) {
	String [] rep = new String [nbr];
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereConteneur();
	   connecte.creaTable(insert);
	    rep [i] = recupIdConteneur(insert);
	}
	return rep;
    }

    public static String changeIdPalette (String req, String id) {
	String rep = req.substring(0,28)+","+id+req.substring(37,req.length());
	return (rep);
    }

    public static String [] rempliPalette ( int nbr, String [] ids_conteneur ) {
	String [] rep = new String [nbr];
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.generePalette();
	    String id = randomId (ids_conteneur,false);
	    insert = changeIdPalette(insert,id);
	    connecte.creaTable(insert);
	    rep [i] = recupId(insert);
	}
	return rep;
    }

    public static String changeEmballeur (String req, String id_emballeur) {
	System.out.println(req);
	String [] temp = req.split(",");
	temp [3] = id_emballeur;
	String rep_change_e = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_e += temp[i];
	    else 
		rep_change_e += ","+temp[i]; 
	}
	return rep_change_e;
    }

    public static String changeClient (String req,String id_client) {
	String [] temp = req.split(",");
	temp [1] = id_client;
	String rep_change_c = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_c += temp[i];
	    else 
		rep_change_c += ","+temp[i]; 
	}
	return rep_change_c;
    }

    public static String changePalette (String req,String id_palette) {	
	String [] temp = req.split(",");
	temp [4] = id_palette;
	String rep_change_p = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_p += temp[i];
	    else 
		rep_change_p += ","+temp[i]; 
	}	
	return rep_change_p;
    }

    public static String changeDouane (String req,String id_douane) {
	String [] temp = req.split(",");
	temp [2] = id_douane;
	String rep_change_d = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_d += temp[i];
	    else 
		rep_change_d += ","+temp[i]; 
	}
	return rep_change_d;
    }

    public static String changeBooleen (String req, String bool) {
	String [] temp = req.split(",");
	temp [10] = bool;
	String rep_change_b = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_b += temp[i];
	    else 
		rep_change_b += ","+temp[i]; 
	}
	return rep_change_b;
    }
    
    public static String annule (String req, int nbr) {
	String [] temp = req.split(",");
	temp [nbr] = "null";
	String rep_change_b = "";
	for (int i=0; i<temp.length; i++){
	    if (i==0)
		rep_change_b += temp[i];
	    else 
		rep_change_b += ","+temp[i]; 
	}
	return rep_change_b;
    }

    public static String [] rempliColi (int nbr,String [] emballeurs,String [] palettes, String [] douanes, String [] clients) {
	String [] rep = new String [nbr];
	String id_coli = "";
	String palette;
	String douane;
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereColi();
	    String etat = recupEtat(insert);
	    

	    id_coli = recupId (insert);
	    rep [i] = id_coli;
	    String client = randomId(clients,false);

	    insert = changeClient(insert,client);
	    
	    if (etat.equals("'100 % expedie'")) {
		insert = changeEmballeur (insert,randomId(emballeurs,false));
		insert = changePalette (insert,randomId(palettes,false));
		insert = changeDouane (insert,randomId(douanes,true));		    
	    }
	    else if (etat.equals("'partiellement expedie'")) {
		insert = changeEmballeur (insert,randomId(emballeurs,false));
		insert = changePalette (insert,randomId(palettes,false));
 		insert = changeDouane (insert,randomId(douanes,true));		    
	    }
	    else if (etat.equals("'non expedie'")) {		    
		insert = changeEmballeur (insert,randomId(emballeurs,false));
		insert = changePalette (insert,randomId(palettes,false));
		insert = changeDouane (insert,randomId(douanes,true));	
		int or = GenereType.genereStock();
		if (or == 1)
		    insert = changeBooleen (insert,"true");
		else {
		    annule (insert,2);
		    annule (insert,3);
		    annule (insert,4);
		}
	    }
	    else {
		System.out.println("etat non valide");
	    }
	    connecte.creaTable(insert);
	    rep [i] = id_coli;
	}
	    
	return rep;
    }

    public static void rempliProduit (int nbr, String [] ids_colis) {
	String [] rep = new String [nbr];
	for (int i =0; i < nbr;i++) {
	    insert = GenereInsert.genereProduit(randomId(ids_colis,true));
	   connecte.creaTable(insert);
	}
    }

    public static void genereAuto () {
	int nbr;
	nbr = GenereType.genereNbr();
	String [] ids_emballeurs = rempliEmballeur (nbr);
	nbr = GenereType.genereNbr();
	String [] ids_douanes = rempliDouane (nbr);
	nbr = GenereType.genereNbr();
	String [] ids_clients = rempliClient (nbr);
	nbr = GenereType.genereNbr();
	String [] ids_conteneurs = rempliConteneur (nbr);
	nbr = GenereType.genereNbr();
	String [] ids_palettes = rempliPalette (nbr, ids_conteneurs);
	nbr = GenereType.genereNbr();
	String [] ids_colis = rempliColi (nbr,ids_emballeurs,ids_palettes,ids_douanes,ids_clients);
	nbr = GenereType.genereNbr();
	rempliProduit (nbr, ids_colis);
    }

    public static void genereManuel () {
	Scanner scan = new Scanner(System.in);
	int rep;
       
	System.out.println ("Combien d'emballeur ?");
	rep = scan.nextInt();
	String [] ids_emballeurs = rempliEmballeur (rep);

	System.out.println ("Combien de douane ?");
	rep = scan.nextInt();
	String [] ids_douanes = rempliDouane (rep);

	System.out.println ("Combien de client ?");
	rep = scan.nextInt();
	String [] ids_clients = rempliClient (rep);

	System.out.println ("Combien de conteneur ?");
	rep = scan.nextInt();
	String [] ids_conteneurs = rempliConteneur (rep);

	System.out.println ("Combien de palette ?");
	rep = scan.nextInt();
	String [] ids_palettes = rempliPalette (rep, ids_conteneurs);

	System.out.println ("Combien de coli ?");
	rep = scan.nextInt();
	String [] ids_colis = rempliColi (rep,ids_emballeurs,ids_palettes,ids_douanes,ids_clients);

	System.out.println ("Combien de produit ?");
	rep = scan.nextInt();
	rempliProduit (rep, ids_colis);
	
    }


    public static void main ( String [] args) {
	try {
	    // Connection a la base
	    Scanner scan = new Scanner(System.in);
	    System.out.println("Entrez votre login psql");
	    String login = scan.next();
	    String mdp = PasswordField.readPassword("Entrer votre password psql: ");
	    connecte = new ConnectionBase(login,mdp);
		
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
	Scanner scan = new Scanner(System.in);
	System.out.println("(1) tout automatique");
	System.out.println("(2) entrer le nombre d'insertion pour chaque table");
	int rep = scan.nextInt();
	rempliGerant();
	if (rep == 1)
	    genereAuto();
	else if (rep == 2 )
	    genereManuel();
    }
}