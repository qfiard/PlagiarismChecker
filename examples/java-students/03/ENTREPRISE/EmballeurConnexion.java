import java.awt.Dimension;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class EmballeurConnexion {
    private Connection conn;
    Statement st;
    PreparedStatement enregistrementColis;
    
    private String id_emballeur = "";
    private String sep = "@@"; // separateur pour les donnees
    
    public EmballeurConnexion(PersonneConnexion conn, String login) {
	this.conn = conn.getConnection();
	setIdEmballeur(login);
    }

    // enregistre l'identifiant de l'emballeur connecte
    public void setIdEmballeur(String login) {
	try {
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				      ResultSet.CONCUR_READ_ONLY);
	    String request = 
		"SELECT id_emballeur "+ 
		"FROM emballeur "+
		"WHERE login_emballeur='"+login+"';";
	    ResultSet rs = st.executeQuery(request);
	    while (rs.next()) {
		this.id_emballeur = rs.getString("id_emballeur");
	    }
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
    }

    // retourne l'identifiant de l'emballeur connecte    
    public String getIdEmballeur() {
	return this.id_emballeur;
    }
    
    // cree une JScrollPane a partir d'une chaine de donnees
    public JScrollPane stringToJsp(String word) {
	Object[][] data;
	String[] titre;
	String[] tmp_data;
	String[] colonnes;
	
	String[] lignes =  word.split("\n");
	colonnes  = lignes[0].split(sep);
	data = new Object[lignes.length -1][colonnes.length];
	titre = new String[colonnes.length];
	for (int i=0; i<colonnes.length; i++){
	    titre[i]= colonnes[i];
	}
	for (int i=1; i<lignes.length; i++){
	    colonnes = lignes[i].split(sep);
	    tmp_data = new String[colonnes.length];
	    for (int j=0; j<colonnes.length; j++){
		tmp_data[j]= colonnes[j];
	    }
	    data[i-1] = tmp_data;
	}
	JTable table = new JTable(data, titre);
	JScrollPane scrol = new JScrollPane(table);
	scrol.setPreferredSize(new Dimension(800, 500));
	return scrol;
    }
    
    // affiche un message d'erreur lorsque des champs sont mal remplis
    public JScrollPane champsNonRenseignes() {
	return stringToJsp("Les champs ne sont pas correctement remplis !");
    }

    // affiche les details d'une commande
    public  String detailsCommande(String num_comm) {
	String result = "";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_produit, quantite, "+
				 "prix_produit, type_produit "+
				 "FROM client NATURAL JOIN commande "+
				 "NATURAL JOIN ligne_de_commande NATURAL "+
				 "JOIN prix_commande NATURAL JOIN produit "+
				 "WHERE num_commande=" + num_comm + ";");
	    while (rs.next()) {
		String num_produit = rs.getString("num_produit");
		int quantite = rs.getInt("quantite");
		int prix_produit = rs.getInt("prix_produit");
		String type_produit = rs.getString("type_produit");
		result += (num_produit + sep + quantite +
			   sep + prix_produit + sep + type_produit +" \n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return result;
    }
    
    // affiche la liste des commandes d'un client
    public JScrollPane listeCommandesClient(String id_client) {
	String result =
	    "nom_societe"+sep+"adresse"+sep+
	    "ville"+sep+"code_postal"+" \n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT nom_societe, "+
				 "adresse_client, ville_client,code_postal_client "+
				 "FROM commande NATURAL JOIN client " +
				 "WHERE id_client='"+id_client+"';");
	    while (rs.next()) {
	        String nom_societe = rs.getString("nom_societe");
		String adresse_client  = rs.getString("adresse_client");
		String ville_client = rs.getString("ville_client");
		String code_postal_client = rs.getString("code_postal_client");
		result += (nom_societe+sep+adresse_client+sep
			   +ville_client+sep+code_postal_client+"\n");
	    }
	    result += (""+sep+" "+sep+" "+sep+" "+"\n");
	    rs = st.executeQuery("SELECT num_commande "+
				 "FROM commande NATURAL JOIN client " +
				 "WHERE id_client='"+id_client+"';");
	    while (rs.next()) {
		int num_commande = rs.getInt("num_commande");
		result += ("num_commande"+sep+"-"+sep+"-"+sep+"-"+"\n");
		result += (num_commande+sep+"-"+sep+"-"+sep+"-"+"\n");
		result += "num_produit"+sep+"quantite"+sep+"prix_produit"+sep+"type_produit"+" \n";
		result += detailsCommande(String.valueOf(num_commande));
		result += (" "+sep+" "+sep+" "+sep+" "+"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // permet l'enregistrement de l'emballage d'une commande
    public JScrollPane enregistrementColis
	(String num_colis, String num_commande, String date_emballage,
	 String type_colis, String num_palette) {
	String result = "L'enregistrement du colis a échoué.";
	try {
	    int colis = Integer.parseInt(num_colis);
	    int commande = Integer.parseInt(num_commande);
	    try {
		enregistrementColis = 
		    conn.prepareStatement("INSERT INTO colis VALUES("+
					  "?, ?, ?, ?, 1, ?, NULL, NULL);");
		enregistrementColis.setInt(1, colis);
		enregistrementColis.setInt(2, commande);
		enregistrementColis.setDate(3, Date.valueOf(date_emballage));
		enregistrementColis.setString(4, type_colis);
		enregistrementColis.setString(5, id_emballeur);
		if (0 != enregistrementColis.executeUpdate())
		    result = "Le colis a bien été enregistré.";
		enregistrementColis.clearParameters();
	    } catch(SQLException e) {
		System.out.println("failed 1 " + e.getMessage());
	    }
	} catch(NumberFormatException ex) {
	    System.out.print("NumberFormatException " + ex.getMessage());
	}
	return stringToJsp(result);
    }

}
