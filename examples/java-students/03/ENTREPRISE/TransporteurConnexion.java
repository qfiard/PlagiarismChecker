import java.awt.Dimension;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class TransporteurConnexion {
    private Connection conn;
    Statement st;
    
    private String code_scac_transporteur = "";
    private String sep = "@@"; // separateur pour les donnees
    
    public TransporteurConnexion(PersonneConnexion conn, String login) {
	this.conn = conn.getConnection();
	setCodeScacTransporteur(login);
    }

    // enregistre le code scac du transporteur connecte
    public void setCodeScacTransporteur(String login) {
	try {
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				      ResultSet.CONCUR_READ_ONLY);
	    String request = 
		"SELECT code_scac_transporteur "+ 
		"FROM transporteur "+
		"WHERE login_transporteur='"+login+"';";
	    ResultSet rs = st.executeQuery(request);
	    while (rs.next()) {
		this.code_scac_transporteur = 
		    rs.getString("code_scac_transporteur");
	    }
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
    }

    // retourne le code scac du transporteur connecte
    public String getCodeScacTransporteur() {
	return this.code_scac_transporteur;
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

    // affiche les dates de livraison souhaitees par les clients, par ordre de priorite
    public JScrollPane datesLivraisonSouhaitees() {
	String result = "N°"+sep+"num_colis"+sep+"date_livraison"+sep+
	    "id_client"+sep+"adresse"+sep+"code_postal"+sep+"ville"+sep+"pays\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_colis, date_livraison, id_client, "+
				 "adresse_client, code_postal_client, "+
				 "ville_client, pays "+
				 "FROM colis NATURAL JOIN client NATURAL JOIN "+
				 "commande "+
				 "ORDER BY date_livraison;");
	    while (rs.next()) {
		int row = rs.getRow();
		int colis = rs.getInt("num_colis");
		String date = rs.getString("date_livraison");
		String id_client = rs.getString("id_client");
		String addr = rs.getString("adresse_client");
		String code_postal = rs.getString("code_postal_client");
		String ville = rs.getString("ville_client");
		String pays = rs.getString("pays");
		result += row + sep + colis + sep + date + sep + id_client +
		    sep + addr + sep + code_postal + sep + 
		    ville + sep + pays + "\n";
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);

    }

}
