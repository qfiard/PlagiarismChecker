import java.awt.Dimension;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class ClientConnexion {
    private Connection conn;
    Statement st;
    PreparedStatement changementLogin, changementPassword;
    
    private String pays = "";
    private String id_client = "";
    private String sep = "@@"; // separateur pour les donnees
    
    public ClientConnexion(PersonneConnexion conn, String login) {
	this.conn = conn.getConnection();
	setInfosClient(login);
    }

    // enregistre dans cette classe le pays et l'identifiant du client connecte
    public void setInfosClient(String login) {
	try {
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				      ResultSet.CONCUR_READ_ONLY);
	    String request = 
		"SELECT id_client, pays "+ 
		"FROM client "+
		"WHERE login_client='"+login+"';";
	    ResultSet rs = st.executeQuery(request);
	    while (rs.next()) {
		this.pays = rs.getString("pays");
		this.id_client = rs.getString("id_client");
	    }
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
    }
    
    // retourne le pays du client connecte
    public String getPays() {
	return this.pays;
    }

    // retourne l'identifiant du client connecte
    public String getIdClient() {
	return this.id_client;
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

    // retourne la liste des commandes generees
    public String[] getListeCommandes() {
	String listeCommandes = "";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_commande "+
				 "FROM commande "+
				 "WHERE id_client='" + id_client + "';");
	    while (rs.next()) {
		int row = rs.getRow();
		int num_commande = rs.getInt("num_commande");
		listeCommandes += (num_commande + sep);
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	if (listeCommandes.equals(""))
	    return new String[0];
	return listeCommandes.split(sep);
    }

    // verifie si une commande est totalement expediee
    public boolean estTotalementExpediee(int num_commande) {
	boolean expediee = false;
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_commande "+
				 "FROM commande NATURAL JOIN colis "+
				 "WHERE num_commande=" + num_commande + ";");
	    while (rs.next()) {
		expediee = true;
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return expediee;
    }

    // verifie si une commande est partiellement expediee
    public boolean estPartiellementExpediee(int num_commande) {
	boolean expediee = false;
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_commande "+
				 "FROM colis "+
				 "WHERE controle IS NULL "+
				 "AND num_commande=" + num_commande + ";");
	    while (rs.next()) {
		expediee = true;
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return expediee;
    }

    // verifie si une commande n'a pas encore ete emballee
    public boolean estNonEmballee(int num_commande) {
	boolean nonEmballee = false;
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_commande "+
				 "FROM commande "+
				 "WHERE num_commande=" + num_commande + " "+
				 "AND num_commande NOT IN ("+
				 "SELECT num_commande "+
				 "FROM colis "+
				 "WHERE num_commande=" + num_commande + ");");
	    while (rs.next()) {
		nonEmballee = true;
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return nonEmballee;
    }

    // verifie si une commande a ete rejetee par la douane
    public boolean estRejeteeParLaDouane(int num_commande) {
	boolean rejetee = false;
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT id_client "+
				 "FROM colis NATURAL JOIN commande "+
				 "WHERE controle=false "+
				 "AND num_commande=" + num_commande + ";");
	    while (rs.next()) {
		rejetee = true;
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return rejetee;
    }

    // renvoie l'etat d'une commande (expediee, rejetee, ...)
    public String getEtatCommande(int num_commande) {
	if (estTotalementExpediee(num_commande))
	    return num_commande + sep + "X" + sep + " " +
		sep + " " + sep + " \n";
	if (estPartiellementExpediee(num_commande))
	    return num_commande + sep + " " + sep + "X" +
		sep + " " + sep + " \n";
	if (estNonEmballee(num_commande))
	    return num_commande + sep + " " + sep + " " +
		sep + "X" + sep + " \n";
	if (estRejeteeParLaDouane(num_commande))
	    return num_commande + sep + " " + sep + " " +
		sep + " " + sep + "X\n";
	return num_commande + sep + " " + sep + " " +
	    sep + " " + sep + " \n";
    }

    // affiche l'etat des commandes du client connecte
    public JScrollPane etatCommandes() {
	String result = "N°"+sep+"num_commande"+sep+"100% expédié"+
	    sep+"Emballé"+sep+"Rejeté à la douane"+sep+"Commandé\n";
	String[] listeCommandes = getListeCommandes();
	for (int i=0; i<listeCommandes.length; i++) {
	    try {
		int num = Integer.parseInt(listeCommandes[i]);
		result += String.valueOf(i+1) + sep + 
		    getEtatCommande(num);
	    } catch(NumberFormatException ex) {
		System.out.print("NumberFormatException " + ex.getMessage());
	    }
	}
	return stringToJsp(result);
    }

    // affiche les produits disponibles
    public JScrollPane produitsDisponibles() {
	String result = "N°"+sep+"num_produit"+sep+
	    "desc_produit\n";
	try {
	    ResultSet rs = null;
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
				      ResultSet.CONCUR_READ_ONLY);
	    rs = st.executeQuery("SELECT num_produit, desc_produit "+ 
				 "FROM produit "+
				 "WHERE reserve>0;");
	    while (rs.next()) {
		int row = rs.getRow();
		String num_produit = rs.getString("num_produit");
		String desc_produit = rs.getString("desc_produit");
		result += row + sep + num_produit + sep + desc_produit + "\n";
	    }
	    rs.close();
	    st.close();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return stringToJsp(result);
    }

    // change le login du client
    public boolean changeLogin(String login) {
	boolean reussite = false;
	try {
	    changementLogin =
		conn.prepareStatement("UPDATE identification "+
				      "SET login=? "+
				      "WHERE login=("+
				      "SELECT login_client "+
				      "FROM client "+
				      "WHERE id_client=?);");
	    changementLogin.setString(1, login);
	    changementLogin.setString(2, id_client);
	    if (0 != changementLogin.executeUpdate())
		reussite = true;
	    changementLogin.clearParameters();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return reussite;
    }

    // change le mot de passe du client
    public boolean changePassword(String password) {
	boolean reussite = false;
	try {
	    changementPassword =
		conn.prepareStatement("UPDATE identification "+
				      "SET password=md5(?) "+
				      "WHERE login=("+
				      "SELECT login_client "+
				      "FROM client "+
				      "WHERE id_client=?);");
	    changementPassword.setString(1, password);
	    changementPassword.setString(2, id_client);
	    if (0 != changementPassword.executeUpdate())
		reussite = true;
	    changementPassword.clearParameters();
	} catch(SQLException e) {
	    System.out.println("failed 1 " + e.getMessage());
	}
	return reussite;
    }

    // permet de changer les informations de connexion du client connecte
    public JScrollPane changeLoginPassword(String login, String password) {
	String result = 
	    "La modification des informations de connexion a échoué.";
	boolean changeLogin = false;
	boolean changePassword = false;
	if (!login.equals("")) {
	    changeLogin = changeLogin(login);
	    if (!changeLogin)
		return stringToJsp("Ce login n'est pas disponible !");
	}
	if (!password.equals(""))
	    changePassword = changePassword(password);
	if (!changeLogin && changePassword)
	    result = "Le mot de passe a bien été modifié.";
	else if (changeLogin && !changePassword)
	    result = "Le login a bien été modifié.";
	else if (changeLogin && changePassword)
	    result = "Les informations de connexion ont bien été modifiées.";
	return stringToJsp(result);
    }

}
