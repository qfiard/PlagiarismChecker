import java.awt.Dimension;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class DouaneConnexion {
    private Connection conn;
    Statement st;
    PreparedStatement enregistrementControle;

    private String pays = "";
    private String sep = "@@"; // separateur pour les donnees
    
    public DouaneConnexion(PersonneConnexion conn, String login) {
	this.conn = conn.getConnection();
	setPaysDouane(login);
    }

    // enregistre dans cette classe le pays de la douane connectee
    public void setPaysDouane(String login) {
	try {
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				      ResultSet.CONCUR_READ_ONLY);
	    String request = 
		"SELECT pays "+ 
		"FROM douane "+
		"WHERE login_douane='"+login+"';";
	    ResultSet rs = st.executeQuery(request);
	    while (rs.next()) {
		this.pays = rs.getString("pays");
	    }
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
    }
    
    // renvoie le pays de la douane connectee
    public String getPays() {
	return this.pays;
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

    // affiche la liste des commandes expediees dans le pays de la douane connectee
    public JScrollPane commandesExpediees() {
	String result = 
	    "N°"+sep+"num_commande"+sep+"date_commande"+sep+"date_livraison"+
	    sep+"id_client\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT * FROM commande "+
				 "WHERE num_commande IN ("+
				 "SELECT num_commande "+
				 "FROM commande NATURAL JOIN client "+
				 "WHERE pays='"+ pays + "');");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		String date_commande = rs.getString("date_commande");
		String date_livraison = rs.getString("date_livraison");
		String id_client = rs.getString("id_client");
		result += (row + sep + num_commande + sep + date_commande + 
			   sep + date_livraison + sep + id_client +"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // affiche la liste des commandes controlees dans le pays de la douane connectee
    public JScrollPane commandesControlees() {
	String result = 
	    "N°"+sep+"num_commande"+sep+"date_commande"+sep+"date_livraison"+
	    sep+"id_client\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT  num_commande, date_commande,  "+
				 "date_livraison, id_client FROM commande "+
				 "NATURAL JOIN colis "+
				 "NATURAL JOIN client NATURAL JOIN douane "+
				 "WHERE pays='" + pays + "' and controle IS NOT NULL;");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		String date_commande = rs.getString("date_commande");
		String date_livraison = rs.getString("date_livraison");
		String id_client = rs.getString("id_client");
		result += (row + sep + num_commande + sep + date_commande + 
			   sep + date_livraison + sep + id_client +"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // affiche la liste des commandes non controlees au pays de la douane connectee
    public JScrollPane commandesNonControlees() {
	String result = 
	    "N°"+sep+"num_commande"+sep+"date_commande"+sep+"date_livraison"+
	    sep+"id_client\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT  num_commande, date_commande, "+
				 "date_livraison, id_client FROM commande "+
				 "NATURAL JOIN colis "+
				 "NATURAL JOIN client NATURAL JOIN douane "+
				 "WHERE pays='" + pays + "' and controle IS NULL;");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		String date_commande = rs.getString("date_commande");
		String date_livraison = rs.getString("date_livraison");
		String id_client = rs.getString("id_client");
		result += (row + sep + num_commande + sep + date_commande + 
			   sep + date_livraison + sep + id_client +"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // affiche la liste des commandes correspondant aux champs remplis
    public JScrollPane rechercheCommande(String dest, String comm, 
					    String contenu) {
	String result =
	    "N°"+sep+"num_commande"+sep+"date_commande"+sep+"date_livraison"+
	    sep+"id_client\n";
	String where = "";
	try {
	    ResultSet rs = null;
	    if (!dest.equals("") && !comm.equals("") && !contenu.equals(""))
		where = "WHERE pays='" + dest + "'" +
		    " and num_commande='" + comm + "'" +
		    " and num_produit='" + contenu + "'";
	    else if (!dest.equals("") && !comm.equals(""))
		where = "WHERE pays='" + dest + "'" +
		    " and num_commande='" + comm + "'";
	    else if (!dest.equals("") && !contenu.equals(""))
		where = "WHERE pays='" + dest + "'" +
		    " and num_produit='" + contenu + "'";
	    else if (!comm.equals("") && !contenu.equals(""))
		where = "WHERE num_commande= '" + comm + "'" +
		    " and num_produit='" + contenu + "'";
	    else if (!contenu.equals(""))
		where = "WHERE num_produit='" + contenu + "'";
	    else if (!dest.equals(""))
		where = "WHERE pays='" + dest + "'";
	    else if (!comm.equals(""))
		where = "WHERE num_commande='" + comm + "'";
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT DISTINCT * "+
				 "FROM client NATURAL JOIN commande " +
				 "NATURAL JOIN produit " +
				 "NATURAL JOIN ligne_de_commande " +
				 where + ";");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		String date_commande = rs.getString("date_commande");
		String date_livraison = rs.getString("date_livraison");
		String id_client = rs.getString("id_client");
		result += (row + sep + num_commande + sep + date_commande + 
			   sep + date_livraison + sep + id_client +"\n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // met a jour le champ controle d'un colis
    public JScrollPane enregistrerControle(int num_colis, 
					   boolean resultatControle) {
	String result = "L'enregistrement a échoué.";
	try {
	    enregistrementControle =
		conn.prepareStatement("UPDATE colis "+
				      "SET controle=? "+
				      "WHERE num_colis=?;");
	    enregistrementControle.setBoolean(1, resultatControle);
	    enregistrementControle.setInt(2, num_colis);
	    if (0 != enregistrementControle.executeUpdate())
		result = "Le résultat du contrôle a été bien "+
		    "enregistré dans la base!";
	    enregistrementControle.clearParameters();
	} catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // affiche le contenu detaille d'une commande
    public JScrollPane detailsCommande(String num_comm) {
	String result = 
	    "N°"+sep+"num_commande"+sep+"id_client"+sep+
	    "pays"+sep+"prix_total"+sep+
	    "date_commande"+sep+"date_livraison"+"\n";
	try {
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    ResultSet rs = st.executeQuery("SELECT DISTINCT num_commande, "+
					   "id_client, pays, prix_total,"+
					   "date_commande, date_livraison "+
					   "FROM client " +
					   "NATURAL JOIN commande NATURAL "+
					   "JOIN ligne_de_commande NATURAL "+
					   "JOIN prix_commande NATURAL JOIN "+
					   "produit WHERE num_commande="+ num_comm+
					   " AND pays='"+pays+"';");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		String id_client = rs.getString("id_client");
		String pays = rs.getString("pays");
		String prix_total = rs.getString("prix_total");
		String date_commande = rs.getString("date_commande");
		String date_livraison = rs.getString("date_livraison");
		result += (row + sep + num_commande + sep + id_client +
			   sep + pays + sep + prix_total +
			   sep + date_commande + sep + date_livraison +"\n");
	    }
	    result += " "+sep+" "+sep+" "+sep+" "+sep+" "+sep+" "+sep+" \n";
	    result += 
		"N°"+sep+"num_produit"+sep+"quantite"+sep+
		"prix_produit"+sep+"type_produit"+sep+" "+sep+" \n";
	    rs = st.executeQuery("SELECT num_produit, quantite, prix_produit, "+
				 "type_produit FROM client NATURAL JOIN "+
				 "commande NATURAL JOIN ligne_de_commande "+
				 "NATURAL JOIN prix_commande NATURAL JOIN "+
				 "produit WHERE num_commande=" + num_comm +
				 "AND pays='" + pays + "';");
	    while (rs.next()) {
		int row = rs.getRow();
		String num_produit = rs.getString("num_produit");
		int quantite = rs.getInt("quantite");
		int prix_produit = rs.getInt("prix_produit");
		String type_produit = rs.getString("type_produit");
		result += (row + sep + num_produit + sep + quantite +
			   sep + prix_produit + sep + type_produit +
			   sep + " " + sep +" \n");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // affiche le pourcentage de colis traites et non traites de la douane connectee
    public JScrollPane statistiquesControles() {
	String result = 
	    "N°"+sep+"Pays"+sep+"Colis traités"+sep+"Colis non traités\n";
	try {
	    st = conn.createStatement();
	    st.executeUpdate("CREATE VIEW non_traites AS "+
			     "SELECT pays, ((COUNT(*)*100)/(SELECT COUNT(*) "+
			     "FROM colis NATURAL JOIN commande "+
			     "NATURAL JOIN client "+
			     "WHERE pays='" + pays + "')) \"non_traites\" "+
			     "FROM colis_non_traites "+
			     "WHERE pays='" + pays + "' "+
			     "GROUP BY pays;");
	    st.executeUpdate("CREATE VIEW traites AS "+
			     "SELECT pays, ((COUNT(*)*100)/(SELECT COUNT(*) "+
			     "FROM colis NATURAL JOIN commande "+
			     "NATURAL JOIN client "+
			     "WHERE pays='" + pays + "')) \"traites\" "+
			     "FROM colis_traites "+
			     "WHERE pays='" + pays + "' "+
			     "GROUP BY pays;");
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT T.pays, traites, non_traites "+
				 "FROM traites T CROSS JOIN non_traites NT;");
	    while (rs.next()) {
		int row = rs.getRow();
		String pays = rs.getString("pays");
		String traites = rs.getString("traites");
		String non_traites = rs.getString("non_traites");
		result += (row + sep + pays + sep + traites + 
			   sep + non_traites +"\n");
	    }
	    st.executeUpdate("DROP VIEW non_traites");
	    st.executeUpdate("DROP VIEW traites");
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

}
