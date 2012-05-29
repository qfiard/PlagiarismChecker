import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class RemplissageTables {
    // la connexion a la base
    private Connection conn = null;
    private PreparedStatement paysColis, dateCommande, dateLivraison, 
	dateEmballage, typeColis, insertionCommande, insertionLigne, 
	cryptage, numPalette, insertionColis, controleColis, tauxColis,
	listeColisPays;
    private int nbJours;

    // nombre de commandes aleatoires a generer
    private static final int TOTAL = 250;
    private static final int NB_LIGNES_MAX = 3;

    public RemplissageTables(String login, String motPasse)
	throws SQLException, ClassNotFoundException {
	Class.forName("org.postgresql.Driver");
	this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/"
						+ login, login, motPasse);
    }

    public static void main(String[] args) {
	try {
	    RemplissageTables rt = new RemplissageTables(args[0], args[1]);	    
	    if (args[2].equals("1"))
		rt.cryptageIdentification();
	    else if (args[2].equals("2")) {
		rt.genererCommandes();
		rt.genererContenuCommandes();
		rt.genererConteneurs();
		rt.genererPalettes();
		rt.genererEmballages();
		rt.genererControleColis();
	    }
	} catch(SQLException e1) {
	    System.out.println("Sql " + e1.getMessage());
	} catch(ClassNotFoundException e2) {
	    System.out.println("ClassNotFound " + e2.getMessage());
	}
    }

    // crypte en md5 les mots de passes dans la base de donnees
    public void cryptageIdentification() {
	String contenu = "";
	try {
	    Scanner sc = new Scanner(new File("DATA/identification.csv"));
	    while (sc.hasNextLine()) {
		contenu += sc.nextLine();
		if (sc.hasNextLine()) contenu += "\n";
	    }
	} catch(IOException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	String[] lignes = contenu.split("\n");
	for (int i=0; i<lignes.length; i++) {
	    String[] attributs = lignes[i].split("@");
	    try {		
		cryptage = 
		    conn.prepareStatement("INSERT INTO identification VALUES "+
					  "(?, md5(?), ?);");
		cryptage.setString(1, attributs[0]);
		cryptage.setString(2, attributs[1]);
		cryptage.setString(3, attributs[2]);
		cryptage.executeUpdate();
		cryptage.clearParameters();
	    } catch(SQLException e) {
		System.out.println("Erreur " + e.getMessage());
	    }
	}
    }

    // recuperation du pays de livraison d'un colis
    public String getPaysColis(int num_commande) {
	String pays = "";
	try {
	    ResultSet rs = null;
	    paysColis = 
		conn.prepareStatement("SELECT pays "+
				      "FROM client NATURAL JOIN commande "+
				      "NATURAL JOIN colis "+
				      "where num_colis=?;");
	    paysColis.setInt(1, num_commande);
	    rs = paysColis.executeQuery();
	    paysColis.clearParameters();
	    while (rs.next()) {
		pays = rs.getString("pays");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return pays;
    }

    // generation d'une date de commande remontant a 7 jours au plus
    public String getDateCommande() {
	String date = "";
	nbJours = (int)(1+Math.random()*20);
	try {
	    ResultSet rs = null;
	    dateCommande = 
		conn.prepareStatement("SELECT current_date - ? as commande;");
	    dateCommande.setInt(1, nbJours);
	    rs = dateCommande.executeQuery();
	    dateCommande.clearParameters();
	    while (rs.next()) {
		date = rs.getString("commande");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return date;
    }

    // generation d'une date de livraison souhaitee
    public String getDateLivraison() {
	String date = "";
	int x = (int)(1+Math.random()*20) - nbJours;
	try {
	    ResultSet rs = null;
	    dateLivraison = 
		conn.prepareStatement("SELECT current_date + ? as livraison;");
	    dateLivraison.setInt(1, x);
	    rs = dateLivraison.executeQuery();
	    dateLivraison.clearParameters();
	    while (rs.next()) {
		date = rs.getString("livraison");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return date;
    }

    // generation d'une date d'emballage superieure a la date de commande
    public String genererDateEmballage(int num_commande) {
	String date = "";
	int x = (int)(1+Math.random()*20);
	try {
	    ResultSet rs = null;
	    dateEmballage =
		conn.prepareStatement("SELECT (date_commande + ?) "+
				      "as \"date\" FROM commande "+
				      "WHERE num_commande=?;");
	    dateEmballage.setInt(1, x);
	    dateEmballage.setInt(2, num_commande);
	    rs = dateEmballage.executeQuery();
	    dateEmballage.clearParameters();
	    while (rs.next()) {
		date = rs.getString("date");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return date;
    }

    // recherche du type d'un colis
    public String typeColis(int num_commande) {
	boolean fragile = false;
	boolean dangereux = false;
	try {
	    ResultSet rs = null;
	    typeColis = 
		conn.prepareStatement("SELECT type_produit "+
				      "FROM produit NATURAL JOIN commande "+
				      "NATURAL JOIN ligne_de_commande "+
				      "WHERE num_commande=?;");
	    typeColis.setInt(1, num_commande);
	    rs = typeColis.executeQuery();
	    typeColis.clearParameters();
	    while (rs.next()) {
		String type_produit = rs.getString("type_produit");
		if (type_produit.equals("D")) dangereux = true;
		else if (type_produit.equals("F")) fragile = true;
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	if (fragile && dangereux) return "DF";
	if (fragile) return "F";
	if (dangereux) return "D";
	return "N";
    }

    // recuperation des numeros des produits
    public String[] getNumProduits() {
	String numProduits = "";
	try {
	    Statement st = null;
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_produit FROM produit");
	    while (rs.next()) {
		int row = rs.getRow();
		String num_produit = rs.getString("num_produit");
		numProduits += (num_produit + "@@");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return numProduits.split("@@");
    }

    // recuperation des id des clients
    public String[] getIdClients() {
	String idClients = "";
	try {
	    Statement st = null;
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT id_client FROM client");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_client = rs.getString("id_client");
		idClients += (id_client + "@@");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return idClients.split("@@");
    }

    // recuperation des id des emballeurs
    public String[] getIdEmballeurs() {
	String idEmballeurs = "";
	try {
	    Statement st = null;
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT id_emballeur FROM emballeur");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_emballeur = rs.getString("id_emballeur");
		idEmballeurs += (id_emballeur + "@@");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return idEmballeurs.split("@@");
    }
    
    // recuperation des code SCAC des transporteurs
    public String[] getCodeScacTransporteurs() {
	String codeScacTransporteurs = "";
	try {
	    Statement st = null;
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT code_scac_transporteur FROM transporteur");
	    while (rs.next()) {
		int row = rs.getRow();
		String code_scac_transporteur = rs.getString("code_scac_transporteur");
		codeScacTransporteurs += (code_scac_transporteur + "@@");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return codeScacTransporteurs.split("@@");
    }

    // determine si un produit a deja ete selectionne pour une commande
    public static boolean produitDejaChoisi(int indiceProduit, 
					    int[] produitsDejaChoisis) {
	for (int i=0; i<produitsDejaChoisis.length; i++) 
	    if (indiceProduit == produitsDejaChoisis[i]) return true;
	return false;
    }

    // ----------------------------
    // --- GENERATION ALEATOIRE ---
    // ----------------------------

    // generation de commandes aleatoires
    public void genererCommandes() {
	String[] id_clients = getIdClients();
	int num_commande = 1;
	String[][] commandes = new String[TOTAL][4];
	for (int i=0; i<commandes.length; i++) {
	    try {
		insertionCommande =
		    conn.prepareStatement("INSERT INTO commande VALUES ("+
					  "?, ?, ?, ?);");
		insertionCommande.setInt(1, num_commande++);
		insertionCommande.setString(2, id_clients[(int)(0+Math.random()*id_clients.length)]);
		insertionCommande.setDate(3, Date.valueOf(getDateCommande()));
		insertionCommande.setDate(4, Date.valueOf(getDateLivraison()));
		insertionCommande.executeUpdate();
		insertionCommande.clearParameters();
	    } catch(SQLException e) {
		System.out.println("Erreur " + e.getMessage());
	    }
	}
    }

    // generation du contenu des commandes
    public void genererContenuCommandes() {
	String[] produits = getNumProduits();
	int[] nb_lignes_par_commande = new int[TOTAL];
	int nb_total_lignes = 0;
	for (int i=0; i<TOTAL; i++) {
	    int random = (int)(1+Math.random()*NB_LIGNES_MAX);
	    nb_lignes_par_commande[i] = random;
	    nb_total_lignes += random;
	}
	String[][] lignes_de_commande = new String[nb_total_lignes][4];
	int nb_lignes_generees = 0;
	for (int i=0; i<nb_lignes_par_commande.length; i++) {
	    int limite = nb_lignes_generees;
	    int indice = 0;
	    int[] produitsDejaChoisis = new int[NB_LIGNES_MAX];
	    for (int j=limite; j<limite+nb_lignes_par_commande[i]; j++) {
		int indiceProduit;
		do {
		    indiceProduit = (int)(0+Math.random()*produits.length);
		} while (produitDejaChoisi(indiceProduit, produitsDejaChoisis));
		produitsDejaChoisis[indice] = indiceProduit;
		try {
		    insertionLigne =
			conn.prepareStatement("INSERT INTO ligne_de_commande VALUES ("+
					      "?, ?, ?, ?);");
		    insertionLigne.setInt(1, i+1);
		    insertionLigne.setInt(2, indice+1);
		    insertionLigne.setInt(3, (int)(1+Math.random()*100));
		    insertionLigne.setString(4, produits[indiceProduit]);
		    insertionLigne.executeUpdate();
		    insertionLigne.clearParameters();
		} catch(SQLException e) {
		    System.out.println("Erreur " + e.getMessage());
		}

		nb_lignes_generees++;
		indice++;
	    }
	}
    }

    // genere des conteneurs
    public void genererConteneurs() {
	try {
	    Statement st = conn.createStatement();
	    st.executeUpdate("INSERT INTO conteneur VALUES "+
			     "(1, 'USA'), "+
			     "(2, 'France'), "+
			     "(3, 'Chine'), "+
			     "(4, 'Canada'), "+
			     "(5, 'Japon'), "+
			     "(6, 'Inde');");			     
	    st.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
    }

    // genere des palettes
    public void genererPalettes() {
	try {
	    Statement st = conn.createStatement();
	    st.executeUpdate("INSERT INTO palette VALUES "+
			     "(1, 1), "+
			     "(2, 2), "+
			     "(3, 3), "+
			     "(4, 4), "+
			     "(5, 5), "+
			     "(6, 6);");
	    st.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
    }

    // recupere un numero de palette compatible avec le pays associe a une commande
    public int getNumPalette(int num_commande) {
	int num_palette = 0;
	try {
	    ResultSet rs = null;
	    numPalette =
		conn.prepareStatement("SELECT num_palette "+
				      "FROM commande NATURAL JOIN client C "+
				      "NATURAL JOIN palette_pays P "+
				      "WHERE P.pays=C.pays "+
				      "AND num_commande=?;");
	    numPalette.setInt(1, num_commande);
	    rs = numPalette.executeQuery();
	    numPalette.clearParameters();
	    while (rs.next()) {
		num_palette = rs.getInt("num_palette");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return num_palette;
    }
       
    // generation des colis
    public void genererEmballages() {
	String[] id_emballeurs = getIdEmballeurs();
	String[] code_scac = getCodeScacTransporteurs();
	String[][] emballages = new String[200][8];
	for (int i=0; i<emballages.length; i++) {
	    try {
		insertionColis = 
		    conn.prepareStatement("INSERT INTO colis VALUES "+
					  "(?, ?, ?, ?, ?, ?, ?, NULL);");
		insertionColis.setInt(1, i+1);
		insertionColis.setInt(2, i+1);
		insertionColis.setDate(3, Date.valueOf(genererDateEmballage(i+1)));
		insertionColis.setString(4, typeColis(i+1));
		insertionColis.setInt(5, getNumPalette(i+1));
		insertionColis.setString(6, id_emballeurs[i/40]);
		insertionColis.setString(7, code_scac[(int)(0+Math.random()*code_scac.length)]);
		insertionColis.executeUpdate();
		insertionColis.clearParameters();
	    } catch(SQLException e) {
		System.out.println("Erreur " + e.getMessage());
	    }
	}
    }

    // recupere la liste des pays
    public String[] getListePays() {
	String listePays = "";
	try {
	    Statement st = null;
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT pays FROM douane");
	    while (rs.next()) {
		String pays = rs.getString("pays");		
		listePays += pays + "@@";
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return listePays.split("@@");
    }

    // recupere la liste des colis a destination d'un pays
    public String[] getListeColisPays(String pays) {
	String listeColis = "";
	try {
	    ResultSet rs = null;
	    listeColisPays = 
		conn.prepareStatement("SELECT num_colis "+
				      "FROM commande NATURAL JOIN colis "+
				      "NATURAL JOIN client "+
				      "WHERE pays=?;");
	    listeColisPays.setString(1, pays);
	    rs = listeColisPays.executeQuery();
	    listeColisPays.clearParameters();
	    while (rs.next()) {
		int colis = rs.getInt("num_colis");
		listeColis += colis + "@@";
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return listeColis.split("@@");
    }

    // recupere le taux de colis verifies d'une douane
    public int getTauxColisVerifies(String pays) {
	int taux = -1;
	try {
	    ResultSet rs = null;
	    tauxColis = 
		conn.prepareStatement("SELECT taux_colis_verifies "+
				      "FROM douane "+
				      "WHERE pays=?;");
	    tauxColis.setString(1, pays);
	    rs = tauxColis.executeQuery();
	    tauxColis.clearParameters();
	    while (rs.next()) {
		taux = rs.getInt("taux_colis_verifies");
	    }
	    rs.close();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return taux;
    }

    // generation du controle des colis
    // ne respecte pas le taux d'erreur des emballeurs
    public void genererControleColis() {
	String[] listePays = getListePays();
	for (int i=0; i<listePays.length; i++) {
	    String pays = listePays[i];
	    String[] listeColisPays = getListeColisPays(pays);
	    int taux = getTauxColisVerifies(pays);
	    int nb_colis_a_traiter = (taux * listeColisPays.length) / 100;
	    for (int j=0; j<nb_colis_a_traiter; j++) {
		boolean controle = j%2==0 ? true : false;
		try {
		    controleColis = 
			conn.prepareStatement("UPDATE colis "+
					      "SET controle=? "+
					      "WHERE num_colis=?;");
		    controleColis.setBoolean(1, controle);
		    try {
			int num_colis = Integer.parseInt(listeColisPays[j]);
			controleColis.setInt(2, num_colis);
		    } catch (NumberFormatException ex) {
			System.out.println("NumberFormatException " + ex.getMessage());
		    }
		    controleColis.executeUpdate();
		    controleColis.clearParameters();
		} catch(SQLException e) {
		    System.out.println("Erreur " + e.getMessage());
		}
	    }
	}
    }    
    
}
