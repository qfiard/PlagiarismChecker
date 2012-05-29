import java.awt.Dimension;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class GerantConnexion {
    private Connection conn;
    private Statement st;
    private PreparedStatement changementPrix, licenciementEmballeur, 
	licenciementTransporteur;
    
    private String sep = "@@"; // separateur pour les donnees
    
    public GerantConnexion(PersonneConnexion conn) {
	this.conn = conn.getConnection();
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

    // recupere la liste des produits
    public String[] getListeProduits() {
	String result = "Sélectionnez un produit"+sep;
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_produit FROM produit;");
	    while (rs.next()) {
		String num_produit = rs.getString("num_produit");
		result += num_produit + sep;
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return result.split(sep);
    }

    // change le prix d'un produit
    public JScrollPane changementPrix(String produit, int prix) {
	String result = "Echec : le prix du produit n'a pas été modifié.";
	try {
	    changementPrix = 
		conn.prepareStatement("UPDATE produit "+
				      "SET prix_produit=? "+
				      "WHERE num_produit=?;");
	    changementPrix.setInt(1, prix);
	    changementPrix.setString(2, produit);
	    if (0 != changementPrix.executeUpdate())
		result = "Le prix du produit a bien été modifié.";
	    changementPrix.clearParameters();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // licenciement d'un personnel
    public JScrollPane licenciementPersonnel(String id) {
	String result = "Echec : l'employé n'a pas été licencié.";
	try {
	    licenciementEmballeur =
		conn.prepareStatement("DELETE FROM identification "+
				      "WHERE login=("+
				      "SELECT login_emballeur "+
				      "FROM emballeur "+
				      "WHERE id_emballeur=?);");
	    licenciementEmballeur.setString(1, id);
	    if (0 != licenciementEmballeur.executeUpdate())
		result = "L'emballeur a bien été licencié.";
	    else{
		licenciementTransporteur = 
		    conn.prepareStatement("DELETE FROM identification "+
					  "WHERE login=("+
					  "SELECT login_transporteur "+
					  "FROM transporteur "+
					  "WHERE code_scac_transporteur=?);");
		licenciementTransporteur.setString(1, id);
		if (0 != licenciementTransporteur.executeUpdate())
		    result = "Le transporteur a bien été licencié.";
		licenciementTransporteur.clearParameters();
	    }
	    licenciementEmballeur.clearParameters();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // recupere la liste des clients
    public JScrollPane listeClients() {
	String result = "N°"+sep+"id_client"+sep+"nom_societe"+sep+"pays\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT id_client, nom_societe, "+
				 "pays FROM client");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_client = rs.getString("id_client");
		String nom_societe = rs.getString("nom_societe");
		String pays = rs.getString("pays");
		result += (row + sep + id_client + sep + nom_societe + sep +
			   pays+"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    public JScrollPane listeEmballeurs() {
	String result = "N°"+sep+"id_emballeur"+sep+"nom"+sep+"prenom\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT id_emballeur, "+
				 "nom_emballeur,  prenom_emballeur  "+
				 "FROM emballeur");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_emballeur = rs.getString("id_emballeur");
		String nom_emballeur = rs.getString("nom_emballeur");
		String prenom_emballeur = rs.getString("prenom_emballeur");
		result += (row + sep + id_emballeur + sep + nom_emballeur +
			   sep + prenom_emballeur+"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }
   
    // recupere la liste des transporteurs
    public JScrollPane listeTransporteurs() {
	String result = "N°"+sep+"code_scac_transporteur"+sep+
	    "nom_transporteur\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT code_scac_transporteur, "+
				 "nom_transporteur FROM transporteur;");
	    while (rs.next()) {
		int row = rs.getRow();
		String code_scac_transporteur = 
		    rs.getString("code_scac_transporteur");
		String nom_transporteur = rs.getString("nom_transporteur");
		result += (row + sep + code_scac_transporteur + sep +
			   nom_transporteur + "\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }
   
    // calcule le quota journalier de chaque emballeur
    public JScrollPane quotaEmballeur() {
	String result = "N°"+sep+"id_emballeur"+sep+
	    "date_emballage"+sep+"colis_traites\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT id_emballeur, date_emballage, "+
				 "count(*) \"colis_traites\" FROM emballeur "+
				 "NATURAL JOIN colis GROUP BY id_emballeur, "+
				 "date_emballage ORDER BY id_emballeur;");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_emballeur = rs.getString("id_emballeur");
		String date_emballage = rs.getString("date_emballage");
		String colis_traites = rs.getString("colis_traites");
		result += (row + sep + id_emballeur + sep +
			   date_emballage + sep + colis_traites + "\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }
   
    // recupere la liste des produits les plus vendus
    public JScrollPane produitsLesPlusVendus() {
	String result = "N°"+sep+"num_produit"+sep+"total\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_produit, total "+
				 "FROM produits_stars "+
				 "ORDER BY total DESC "+
				 "LIMIT 10;");
	    while (rs.next()) {
		int row = rs.getRow();
		String num_produit = rs.getString("num_produit");
		String total = rs.getString("total");
		result += (row + sep + num_produit + sep +
			   total + "\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }
   
    // recupere la liste des produits les plus vendus
    public JScrollPane clientsLesPlusDepensiers() {
	String result = "N°"+sep+"id_client"+sep+"total\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT id_client, total "+
				 "FROM clients_stars "+
				 "ORDER BY total DESC "+
				 "LIMIT 10;");
	    while (rs.next()) {
		int row = rs.getRow();
		String id_client = rs.getString("id_client");
		String total = rs.getString("total");
		result += (row + sep + id_client + sep +
			   total + "\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

}
