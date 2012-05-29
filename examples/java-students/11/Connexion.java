package General;
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.security.*;
import javax.swing.*;


public class Connexion {
	public static String log;
	public static String password;
	Connection conn; // la connexion a la base
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	
	// connection a la base
	public Connexion(String login, String MotPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		
		Class.forName("org.postgresql.Driver");
		//conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login,login, MotPasse);
		conn=DriverManager.getConnection("jcbc:postgresql://localhost:5432/bd6",login,MotPasse);
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}
	
	public Statement getStatement(){
		return st;
	}

	// fermeture de la connection
	public void close() throws SQLException{ 
		conn.close();
	}

	/** REQUETES DOUANE  */
	
	public ResultSet listerPaletteConteneur(int numero) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void ecrireDouane(String pays, String taux_colis_verif, String login, String mdp) throws SQLException {
		st.executeUpdate("INSERT INTO douane values('"+pays+"','"+taux_colis_verif+"','"+login+"','"+mdp+"');");
	}
	
	public ResultSet rechercheCommande(int num) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT * FROM commande WHERE num_commande="+num+";");
		return rs;
	}
	
	public ResultSet listerCommandeExpedie(String nom) throws SQLException {
		ResultSet rs = st.executeQuery("select pays from douane where login='"+nom+"';");
		String pay = null;
		while(rs.next())
			pay = rs.getObject(1).toString();
		ResultSet rs1 = st.executeQuery("SELECT num_commande, client.pays, passage_douane FROM colis NATURAL JOIN commande NATURAL JOIN client WHERE passage_douane='controle' AND client.pays='"+pay+"';");
		return rs1;
		}
	
	public ResultSet listerCommandeControlee(String nom) throws SQLException {
		ResultSet rs = st.executeQuery("select pays from douane where login='"+nom+"';");
		String pay = null;
		while(rs.next())
			pay = rs.getObject(1).toString();
		ResultSet rs1 = st.executeQuery("SELECT num_commande, client.pays, commande.etat FROM colis NATURAL JOIN commande NATURAL JOIN client WHERE commande.etat='expedie' AND client.pays='"+pay+"';");
		return rs1;
		}
	
	public ResultSet listerCommandeExpedieeNonControlee(String nom) throws SQLException {
		ResultSet rs = st.executeQuery("select pays from douane where login='"+nom+"';");
		String pay = null;
		while(rs.next())
			pay = rs.getObject(1).toString();
		ResultSet rs1 = st.executeQuery("SELECT num_commande, client.pays, passage_douane, etat FROM colis NATURAL JOIN commande NATURAL JOIN client WHERE passage_douane='non_controle' AND etat='expedie' AND client.pays='"+pay+"';");
		return rs1;
		}
	
	public ResultSet reccupNomPays(String login) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT pays FROM douane WHERE login = '"+ login + "';");

		return rs;
	}
	
	public ResultSet listerColisPalette(int numero_palette) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT id_colis FROM palette WHERE id_palette="+numero_palette+";");
		return rs;
	}
	
	public ResultSet listerProduitColis(int id_colis) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT num_produit FROM colis WHERE id_colis="+id_colis+";");
		return rs;
	}
	
	public ResultSet prixProduitTransportes(int id_colis) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT produit.num_produit, prix FROM colis natural join produit WHERE id_colis=" + id_colis + ";");
		return rs;
	}
	
	public void controleDouane(int num_commande, String resultat) throws SQLException {
		st.executeUpdate("UPDATE colis set passage_douane='"+ resultat +"' WHERE num_commande="+num_commande +" ");
	}
	
	/** REQUETES CLIENT */
	
	public void ecrireClient(String num_client, String nom_societe, String suffixe_societe, String adr, String ville, String code_postale, String pays, String tel, String mdp) throws SQLException {
		st.executeUpdate("INSERT INTO client values('"+num_client+"','"+nom_societe+"','"+suffixe_societe+"','"+adr+"','"+ville+"', '"+code_postale+"', '"+pays+"', '"+tel+"', '"+mdp+"');");
	}
	
	public ResultSet listerProduit() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT description, num_produit,reserve FROM produit WHERE reserve>'0';");
		return rs;
	}

	public ResultSet etatColis(int num) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT num_commande, etat FROM commande WHERE num_commande="+num+";");
		return rs;
	}

	
	public boolean reccupLoginClient(String num_client, String mdp, String table) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT num_client FROM " + table + " WHERE num_client='" +num_client+ "' AND mdp='"+mdp+"';");
		if(rs.next()){
			return true;
		}
		return false;
	}
	
	
	public void changerQueLogin(String Ancienlogin, String NouveauLogin) throws SQLException {
		st.executeUpdate("UPDATE client set num_client='"+NouveauLogin+"' WHERE num_client='"+Ancienlogin+"';");
	}
	
	public void changerQueMdp(String AncienMdp, String NouveauMdp) throws SQLException {
		st.executeUpdate("UPDATE client set mdp='"+NouveauMdp+"' WHERE mdp='"+AncienMdp+"';");
	}
	
	public void changerLoginETMdp(String AncienLogin, String NouveauLogin, String AncienMdp, String NouveauMdp) throws SQLException {
		st.executeUpdate("UPDATE client set num_client='"+NouveauLogin+"', mdp='"+NouveauMdp+"'" +
										" WHERE num_client='"+AncienLogin+"' AND mdp='"+AncienMdp+"';");
	}
	
	public void choisirDateLivraison(int num_commande, String num_client,String date) throws SQLException{
		st.executeUpdate("UPDATE commande set date_livraison='"+date+"' WHERE num_commande='"+num_commande+"' AND num_client='"+num_client+"';");
	}
	
	/** REQUETES GERANT */
	
	public void ecrireGerant(String prenom, String nom, String login, String mdp) throws SQLException {
		st.executeUpdate("INSERT INTO gerant values('"+prenom+"','"+nom+"','"+login+"','"+mdp+"');");
	}


	public ResultSet listerClients() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT num_client, nom_societe, suffixe_societe, adr, ville, code_postale, pays, tel" +
										" FROM client;");
		return rs;
	}
	
	public ResultSet listerEmployesDouane() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT login from douane;");
		return rs;
	}
	
	public ResultSet listerEmployesTransporteur() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT code_scac from transporteur;");
		return rs;
	}
	
	public ResultSet listerEmployesEmballeur() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT num_emballeur from emballeur;");
		return rs;
	}
	
	public void licencierDouane(String nom) throws SQLException {
		st.executeUpdate("UPDATE douane SET pays='vire', taux_colis_verif='0', login='vire', mdp='vire' WHERE login='"+nom+"'");
	}
	
	public void licencierTransporteur(String nom) throws SQLException {
		st.executeUpdate("UPDATE transporteur SET code_scac='vire', nom='vire', mdp='vire' WHERE code_scac='"+nom+"'");
	}
	
	public void licencierEmballeur(String nom) throws SQLException {
		System.out.println(nom);
		st.executeUpdate("UPDATE emballeur SET num_emballeur='vire', nom='vire', prenom='vire', taux_err='vire', mdp='vire' WHERE num_emballeur='"+nom+"'");
	}
	
	public void changerPrixProduits(int NouveauPrix, String num_produit) throws SQLException{
		st.executeUpdate("UPDATE produit SET cout="+NouveauPrix+" WHERE num_produit='"+ num_produit +"';");
	}
	
	public ResultSet rechercheProduit(String num_produit) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT * from produit WHERE num_produit='"+num_produit+"';");
		return rs;
	}
	
	public ResultSet listerProduitsPlusVendus() throws SQLException{
		ResultSet rs = st.executeQuery("SELECT num_produit, sum(quantite_prod) from contientCmd NATURAL JOIN produit GROUP BY num_produit");
		return rs;
	}
	
	public ResultSet listerClientsDepensiers() throws SQLException {
		ResultSet rs = st.executeQuery("SELECT num_client, sum(prix) as depense from commande GROUP BY num_client ORDER BY depense DESC;");
		return rs;
	}
	
	/** REQUETES EMBALLEUR  */
	
	public void ecrireEmballeur(String num_emballeur, String nom, String login, String taux_err, String mdp) throws SQLException {
		st.executeUpdate("INSERT INTO emballeur values('"+num_emballeur+"','"+nom+"','"+login+"','"+Integer.parseInt(taux_err)+"','"+mdp+"');");
	}
	
	public ResultSet listerCommande(String num_client) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT * FROM  commande natural join colis WHERE num_client='"+num_client+"';");
		return rs;
	}
	
	public boolean reccupLoginEmballeur(String login, String mdp, String table) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT num_emballeur FROM " + table + " WHERE num_emballeur='" +login+ "' AND mdp='"+ mdp +"';");
		if(rs.next()){
			return true;
		}
		return false;
	}
	
	public void conditionnerColis(int id_colis, String date) throws SQLException {
		st.executeUpdate("UPDATE colis set date_emballage='"+date+"' WHERE colis.id_colis='"+id_colis+"'");
	}
	
	public void conditionnerPalette(int id_colis, String date) throws SQLException {
		st.executeUpdate("UPDATE palette set date_mis_en_palette='"+date+"' WHERE id_palette='"+id_colis+"'");
	}
	
	/** REQUETES TRANSPORTEUR */
	
	public void ecrireTransporteur(String code_scac, String nom, String mdp) throws SQLException {
		st.executeUpdate("INSERT INTO transporteur values('"+code_scac+"','"+nom+"','"+mdp+"');");
	}
	
	public ResultSet qualifiantProduit(int id_colis) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT qualifiant FROM colis WHERE id_colis="+id_colis+";");
		return rs;
	}
	
	public boolean reccupLoginTransporteur(String login, String mdp, String table) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT code_scac FROM " + table + " WHERE code_scac='" +login+ "' AND mdp='"+mdp+"';");
		if(rs.next()){
			return true;
		}
		return false;
	}
	
	public ResultSet dateLimiteLivraison(int id_colis) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT date_livraison FROM colis NATURAL JOIN commande WHERE id_colis="+id_colis+";");
		return rs;
		
	}
	
	/** REQUETES COMMUNES */
	
	public void ecrireProduit(String num_produit, String description, String quantite_par_carton, String carton_par_palette, String qualifiant, String cout, String taux_aug, String poids, String reserve ) throws NumberFormatException, SQLException{
		st.executeUpdate("INSERT INTO produit values('"+num_produit+"','"+description+"','"+Integer.parseInt(quantite_par_carton)+"','"+Integer.parseInt(carton_par_palette)+"', '"+qualifiant+"', '"+Integer.parseInt(cout)+"', '"+Integer.parseInt(taux_aug)+"', '"+Integer.parseInt(poids)+"', '"+Integer.parseInt(reserve)+"');");
	}
	
	public boolean reccupLogin(String login, String mdp, String table) throws SQLException{
		System.out.println(login);
		ResultSet rs = st.executeQuery("SELECT login FROM " + table + " WHERE login='" +login+ "' AND mdp='"+mdp+"';");
		if(rs.next()){
			return true;
		}
		return false;
	}
	
	
public static void main(String[]args){
	
		Scanner sc = new Scanner(System.in);
		//System.out.println("Entrer votre login pour vous connecter a Postgres: ");
		log=args[0];
		System.out.println("Entrer votre mot de passe pour vous connecter a Postgres: ");
		password=sc.next();
		MenuGraphique mg = new MenuGraphique(log,password);

}
  
}