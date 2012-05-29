import java.util.*;
import java.sql.*;
import java.io.*;

public class Inter {
	static ConnectionBase table;
    
    public static void fin_requete (String user) throws IOException, InstantiationException, IllegalAccessException, SQLException {
	try
	    {            
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("clear");
	    } 
	catch (Throwable t){
	    t.printStackTrace();
	}
	if (user.equals("gerant"))
	    interface_gerant();
	else if (user.equals("client"))
	    interface_client();
	else if (user.equals("emballeur"))
	    interface_emballeur();
	else if (user.equals("douane"))
	    interface_douane();
	else if (user.equals("transporteur"))
	    interface_transporteur();
    }	

    public static void interface_gerant() throws IOException, InstantiationException, IllegalAccessException, SQLException { 

	Scanner scan = new Scanner(System.in);
	System.out.println("Voulez-vous:");
	System.out.println("");
	System.out.println("(1) Changer le prix d'un produit");
	System.out.println("(2) Licensier un employer");
	System.out.println("(3) Lister les employes/clients");
	System.out.println("(4) Lister les colis traites par un emballeur");
	System.out.println("(5) Voir les produit les plus vendus");
	System.out.println("(6) Voir les clients les plus depensies");
	int rep = scan.nextInt();
	switch (rep) {
	case 0:
	    System.exit(0);
	    break;
	case 1:
	    System.out.println("Quel produit ?");
	    String produit = scan.next();
	    System.out.println("Quel prix ?");
	    String prix = scan.next();	   
	    table.update("update produit set prix = "+prix+" where nom_produit = "+produit+";"); 
	    fin_requete ("gerant");
	    break;
	case 2:
	    System.out.println("(1) Par id ?");
	    System.out.println("(2) Par nom/prenom ?");
	    int rep2 = scan.nextInt();
	    if ( rep2 == 1) {
		System.out.println("Quel id ?");
		String id = scan.next();
		table.supr("delete * from personel where  id_personnel="+id+";"); 
	    }
	    else if (rep2 == 2) {
		System.out.println("Quel nom ?");
		String nom = scan.next();
		System.out.println("Quel prenom ?");
		String prenom = scan.next();
		table.supr("delete * from personel where  prenom="+prenom+" and nom="+nom+";"); 
	    }
	    fin_requete("gerant");
	    break;
	case 3:
	    System.out.println("(1) Les employes");
	    System.out.println("(2) Ou les clients ?");
	    int rep3 = scan.nextInt();
	    if ( rep3 == 1) {
		ResultSet res = table.res("select nom,prenom from personnel;");
	System.out.println(res.getString("nom")+" "+res.getString("prenom"));
	    }
	    else if (rep3 == 2) {
		ResultSet res = table.res("select nom,prenom from client;");
	System.out.println(res.getString("nom")+" "+res.getString("prenom"));
	    }
	    fin_requete("gerant");
	    break;
	case 4:
	    System.out.println("(1) Par id ?");
	    System.out.println("(2) Par nom/prenom ?");
	    int rep4 = scan.nextInt();
	    if ( rep4 == 1) {
		System.out.println("Quel id ?");
		String id = scan.next();
		table.supr("delete * from personel where  id_personnel="+id+";"); 
	    }
	    else if (rep4 == 2) {
		System.out.println("Quel nom ?");
		String nom = scan.next();
		System.out.println("Quel prenom ?");
		String prenom = scan.next();
		table.supr("delete * from personel where  prenom="+prenom+" and nom="+nom+";"); 
	    }
	    fin_requete("gerant");
	    break;
	case 5:
	    ResultSet res5 = table.res("SELECT nom_produit FROM (select nom_produit, count (nom_produit) as MAX from produit group by nom_produit )  ORDER BY MAX DESC;");
	System.out.println(res5.getString("nom_produit"));
	    fin_requete("gerant");
	    break;
	case 6:
	    ResultSet res6 = table.res("select nom from ( colis as T1 NATURAL JOIN colis as T2) where prix.T1 >prix.T2;");
		System.out.println(res6.getString("nom_produit"));
	    fin_requete("gerant");
	    break;
	default:
	    System.out.println("blabla");
	    break;
	}
	scan.close();
    }

    public static void interface_client() throws IOException, InstantiationException, IllegalAccessException, SQLException {
	Scanner scan = new Scanner(System.in);
	System.out.println("Voulez-vous:");
	System.out.println("");
	System.out.println("(1) Savoir ou en est votre colis");
	System.out.println("(2) Lister les produits diponibles");
	System.out.println("(3) Choisir une date de livraison");
	System.out.println("(4) Changer votre login/mot de passe");
	int rep = scan.nextInt();
	switch (rep) {
	case 0:
	    System.exit(0);
	    break;
	case 1:
	    System.out.println("Veillez entrer la reference de votre colis (id)");
	    String coli_id1 = scan.next();
	    ResultSet res1 = table.res("select lieu from colis where (num_client =client_courant and num_colis = "+coli_id1+";");
		System.out.println(res1.getString("lieu"));
	    fin_requete("client");
	    break;
	case 2:
	    ResultSet res2 = table.res("select nom_prduit from produit where stock > 0;");
	System.out.println(res2.getString("nom_produit"));
	    fin_requete("client");
	    break;
	case 3:
	    System.out.println("Veillez entrer la reference de votre colis (id)");
	    String coli_id3 = scan.next();
	    System.out.println("Avant quelle date doit etre effectuee la livraison ? (JJ-MM-AAAA)");
	    String date3 = scan.next();
	    table.update("update colis set date = "+date3+" where colis= "+coli_id3+";");
		int prix = 123;
	    table.update("update produit set prix = "+prix+" where num_colis="+coli_id3+";");
	    fin_requete("client");
	    break;
	case 4:
	    System.out.println("Veuillez entrer votre nouveau login");
	    String login = scan.next();
	    System.out.println("Veuillez entrer votre nouveau mot de passe");
	    String mdp = scan.next();
	    table.update("update clients set login = "+login+";");
	    table.update("update clients set mot_de_passe = "+mdp+";");
	    fin_requete("client");
	    break;
	default:
	    System.out.println("c'est pas bon");
	    break;
	}
	scan.close();
	    
    }
    public static void interface_emballeur() throws IOException, InstantiationException, IllegalAccessException, SQLException {
	Scanner scan = new Scanner(System.in);
	System.out.println("Voulez-vous:");
	System.out.println("");
	System.out.println("(1) Lister les colis d'un client");
	System.out.println("(2) entrer un coli/ une palette dans le systeme");
	int rep1 = scan.nextInt();
	switch (rep1) {
	case 0:
	    System.exit(0);
	    break;
	case 1:
	    System.out.println("Veuillez entrer l'id du client");
	    String id1 = scan.next();
	    ResultSet res1 = table.res("select num_commande from colis where num_client = "+id1+") group by colis;");
		System.out.println(res1.getString("num_commande"));
	    fin_requete("emballeur");
	    break;
	case 2:
	    System.out.println("(1) Un coli ?");
	    System.out.println("(2) Une palette ?");
	    int rep2 = scan.nextInt();
	    if ( rep2 == 1) {
		System.out.println("Veuillez entrer l'id du client");
		String id2 = scan.next();
		System.out.println("Veuillez entrer le date d'aujourd'hui (JJ-MM-AAAA)");
		String date = scan.next();
		table.update("update colis set date_courante = "+date+" where id_client = "+id2+";");
		table.update("update colis set lieu = 'emballe';");
	    }
	    else if (rep2 == 2 ) {
		System.out.println("Entrez les id des colis mit sur la palette: ('fin' une fois termine)");
		String id_palette = GenereType.genereId(8);
		String id_coli ="";
		while (!(id_coli.equals("fin"))) {
			id_coli = scan.next();
		    table.update("update colis set id_palette = "+id_palette+" where id_coli = "+id_coli+";");
		}
		System.out.println("La palette a bien ete enregistree, son id sera: +"+id_palette+";");
		fin_requete("emballeur");
	    }
	    break;
	default:
	    System.out.println(" pb emballeur");
	}
	scan.close();
    }		
	    
    public static void interface_douane() throws IOException, InstantiationException, IllegalAccessException, SQLException {
	Scanner scan = new Scanner(System.in);
	System.out.println("Voulez-vous:");
	System.out.println("");
	System.out.println("(1) Renvoyer un coli");
	System.out.println("(2) Lister des elements");
	System.out.println("(3) Acceder aux prix des produits transportes");
	int rep = scan.nextInt();
	switch (rep) {
	case 0:
	    System.exit(0);
	    break;
	case 1:
	    fin_requete("douane");
	    break;
	case 2:
	    System.out.println("(1) Les palettes d'un conteneur");
	    System.out.println("(2) Les colis d'une palette");
	    System.out.println("(3) Les produits d'un coli");
	    int elmt2 = scan.nextInt();
	    switch (elmt2) {
	    case 1:
		System.out.println("Entrez l'id du conteneur");
		String id_conteneur = scan.next();
		ResultSet res21 = table.res("select id_palette from palette where id_conteneur = "+id_conteneur+";");
		System.out.println(res21.getString("id_palette"));	
		break;
	    case 2:
		System.out.println("Entrez l'id de la palette");
		String id_palette = scan.next();
		ResultSet res22 = table.res("select id_colis from colis where id_palette = "+id_palette+";");
		System.out.println(res22.getString("id_coli"));
		break;
	    case 3:
		System.out.println("Entrez l'id du coli");
		String id_coli = scan.next();
		ResultSet res23 = table.res("select nom_produit from produit where id_coli = "+id_coli+";");
		System.out.println(res23.getString("nom_produit"));
		break;
	    }
	    fin_requete("douane");
	    break;
	case 3:
	    System.out.println("(1) Les produits d'un colis");
	    System.out.println("(2) Les produits d'une palette");
	    System.out.println("(3) Les produits d'un conteneur");
	    int elmt3 = scan.nextInt();
	    switch (elmt3) {
	    case 1:
		System.out.println("Entrez l'id du coli");
		String id_coli = scan.next();
		ResultSet res31 = table.res("select nom_produit,prix from produit where id_coli = "+id_coli+";");
		System.out.println(res31.getString("nom_produit")+" "+res31.getString("prix"));
		break;
	    case 2:
		System.out.println("Entrez l'id de la palette");
		String id_palette = scan.next();
		ResultSet res32 = table.res("select nom_produit,prix from produit where id_coli = (select id_coli from coli where id_palette = "+id_palette+");");
		System.out.println(res32.getString("nom_produit")+" "+res32.getString("prix"));
		break;
	    case 3:
		System.out.println("Entrez l'id du conteneur");
		String id_conteneur = scan.next();
		ResultSet res33 = table.res("select nom_produit,prix from produit where id_coli = (select id_coli from coli where id_palette = (select id_palette from palette where id_conteneur = "+id_conteneur+");");
		System.out.println(res33.getString("nom_produit")+" "+res33.getString("prix"));
		break;
	    }
	    fin_requete("douane");
	    break;
	}
	scan.close();
    }
    public static void interface_transporteur() throws IOException, InstantiationException, IllegalAccessException, SQLException {
	Scanner scan = new Scanner(System.in);
	System.out.println("Voulez-vous:");
	System.out.println("");
	System.out.println("(1) Connaitre l'etat d'un produit");
	System.out.println("(2) Connaitre la date limite de livraison d'un coli");
	int rep = scan.nextInt();
	switch (rep) {
	case 0:
	    System.exit(0);
	    break;
	case 1:
	    System.out.println("Entrez l'id du coli");
	    String id_coli1 = scan.next();
	    ResultSet res1 = table.res("select etat from produit where id_coli = "+id_coli1+";");
		System.out.println(res1.getString("etat"));
	fin_requete("transporteur");
	    break;
	    	case 2:
	    System.out.println("Entrez l'id du coli");
	    String id_coli2 = scan.next();
	    ResultSet res2 = table.res("select date_limite from coli where id_coli = "+id_coli2+";");
		System.out.println(res2.getString("date_limite"));
	fin_requete("transporteur");
	    break;
	    	}
	scan.close();
    }
    
    public static void main (String [] args) throws IOException, InstantiationException, IllegalAccessException, SQLException {
	Scanner scan = new Scanner(System.in);
	
	System.out.println("login:");
	String login = scan.next();
	String mdp = "erreur de mot de passe";
	try {
	    mdp = new String(System.console().readPassword("%s", "mot de passe"));
	}
	catch (Exception ioe) {
	    ioe.printStackTrace();
	}
	try {	
	table = new ConnectionBase(login,mdp);
	}	
catch(Exception e) {	
	    e.printStackTrace();
	}
	System.out.println("tapez 0 pour arreter");
	
	System.out.println("quelle interface voulez-vous ?");
	System.out.println("(1) gerant");
	System.out.println("(2) client");
	System.out.println("(3) emballeur");
	System.out.println("(4) douane");
	System.out.println("(5) transporteur");

	int rep = scan.nextInt();
	    
	switch (rep) {
	case 1:
	    interface_gerant(); 
	    break;
	case 2:
	    interface_client();
	    break;
	case 3:
	    interface_emballeur();
	    break;
	case 4:
	    interface_douane();
	    break;
	case 5:
	    interface_transporteur();
	    break;
	}
	scan.close();
    }
    
}
