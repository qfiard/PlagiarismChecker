import java.io.*;
import java.sql.*;

class ConnectionBase {
	Connection conn; // la connexion a la base
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;

	// connection a la base
	public ConnectionBase(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------

		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);
		//Rajouts
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}

	// fermeture de la connection
	public void close() throws SQLException{ 
		conn.close();
	}

	public  void creationTable() throws SQLException {
		String sql = "CREATE TABLE gerant(" +
				"prenom VARCHAR(30) NOT NULL," +
				"nom VARCHAR(30) NOT NULL," +
				"login VARCHAR(20) NOT NULL," +
				"mdp VARCHAR(20) NOT NULL);";
		st.execute(sql);
		System.out.println("table du gerant cree");

		sql="CREATE TABLE douane(" +
				"pays VARCHAR(20) NOT NULL," +
				"txcolisrec INT NOT NULL CHECK (txcolisrec >=0)," +
				"login VARCHAR(20) NOT NULL," +
				"mdp VARCHAR(20) NOT NULL);";
		st.execute(sql);
		System.out.println("table des douane cree");

		sql = "CREATE TABLE client (" +
				"id VARCHAR(30) NOT NULL PRIMARY KEY," +
				"societe VARCHAR(30)," +
				"sufscte VARCHAR(5)," +
				"adresse VARCHAR(50) NOT NULL," +
				"ville VARCHAR(30) NOT NULL," +
				"codepostal VARCHAR(10) NOT NULL," +
				"pays VARCHAR(20) NOT NULL," +
				"login VARCHAR(25) NOT NULL," +
				"mdp VARCHAR(25) NOT NULL);";
		st.execute(sql);
		System.out.println("table du client cree");

		sql="CREATE TABLE emballeur(" +
				"id VARCHAR(30) NOT NULL PRIMARY KEY," +
				"nom VARCHAR(20) NOT NULL," +
				"prenom VARCHAR(20) NOT NULL," +
				"tauxerreur INT CHECK ( tauxerreur >=0 AND tauxerreur <100)," +
				"mdp VARCHAR(25) NOT NULL);";
		st.execute(sql);
		System.out.println("table de l'emballeur cree");

		sql="CREATE TABLE produit (" +
				"ref VARCHAR(30) NOT NULL PRIMARY KEY," +
				"description VARCHAR(100)," +
				"qparc INT NOT NULL," +
				"cparp INT NOT NULL," +
				"qualifiant VARCHAR(20) CHECK(qualifiant IN ('N', 'D','F'))," +
				"prix INT NOT NULL," +
				"txaug INT NOT NULL," +
				"poids INT NOT NULL," +
				"stock INT NOT NULL CHECK(stock >= 0)," +
				"nbrdevendus INT);";
		st.execute(sql);
		System.out.println("table de produit cree");

		sql= "CREATE TABLE transport (" +
				"scac VARCHAR(10) NOT NULL PRIMARY KEY," +
				"nomtrans VARCHAR(30) NOT NULL," +
				"mdp VARCHAR(20));";
		st.execute(sql);
		System.out.println("table du transport cree");

		sql="CREATE TABLE conteneur (id INT NOT NULL PRIMARY KEY," +
				"transport_id VARCHAR(10) NOT NULL," +
				"FOREIGN KEY(transport_id) REFERENCES transport(scac));"; 
		st.execute(sql);
		System.out.println("table des conteneur cree");

		sql="CREATE TABLE palette (id INT NOT NULL PRIMARY KEY," +
				"conteneur_id INT NOT NULL," +
				"FOREIGN KEY(conteneur_id) REFERENCES conteneur(id));"; 
		st.execute(sql);
		System.out.println("table des palettes cree");

		sql="CREATE TABLE commande (id INT NOT NULL PRIMARY KEY," +
				"livraison_date DATE NOT NULL," +
				"emballeur_id VARCHAR(30)," +
				"datecmd DATE," +
				"datevoulue DATE NOT NULL," +
				"id_client VARCHAR(30) NOT NULL," +
				"FOREIGN KEY(emballeur_id) REFERENCES emballeur(id) ON UPDATE CASCADE ON DELETE SET NULL," +
				"FOREIGN KEY(id_client) REFERENCES client(id));";
		st.execute(sql);
		System.out.println("table des commandes cree");

		sql="CREATE TABLE exemplaire (id INT NOT NULL PRIMARY KEY," +
				"commande_id INT NOT NULL," +
				"ref_produit VARCHAR(30) NOT NULL," +
				"FOREIGN KEY(commande_id) REFERENCES commande(id)," +
				"FOREIGN KEY(ref_produit) REFERENCES produit(ref));";
		st.execute(sql);
		System.out.println("table des exemplaires cree");

		sql="CREATE TABLE colis(" +
				"id INT NOT NULL," +
				"commande INT NOT NULL," +
				"adresse VARCHAR(100) NOT NULL," +
				"date DATE," +
				"palette_id INT," +
				"etat VARCHAR(30) CHECK(etat IN ('commande','emballe','entransit','livre'))," +
				"FOREIGN KEY(commande) REFERENCES commande(id)," +
				"FOREIGN KEY(palette_id) REFERENCES palette(id));";
		st.execute(sql);
		System.out.println("table des colis cree");



	}	 

	public void suppressionTable() throws SQLException {
		st.execute("DROP TABLE gerant CASCADE;");
		st.execute("DROP TABLE douane CASCADE;");
		st.execute("DROP TABLE client CASCADE;");
		st.execute("DROP TABLE conteneur CASCADE;");
		st.execute("DROP TABLE palette CASCADE;");
		st.execute("DROP TABLE emballeur CASCADE;");
		st.execute("DROP TABLE produit CASCADE;");
		st.execute("DROP TABLE transport CASCADE;");
		st.execute("DROP TABLE commande CASCADE;");
		st.execute("DROP TABLE exemplaire CASCADE;");
		st.execute("DROP TABLE colis CASCADE;");
		st.execute("DROP TABLE conteneur CASCADE;");

		
	}
	//Creation des exemplaires pour la commande et modif des valeurs du produit.
	public void creationExemplaire(int n, String tmp) throws SQLException{
		//Tmp, ça doit être la reference du produit
		String sql = "UPDATE produit SET stock = stock-"+n+" WHERE num = "+tmp+";";
		st.execute(sql);
	}

	public void insertionTuplesPredefinis() throws SQLException{
		String sql="\\copy emballeur FROM \"/Users/macosxlion/Documents/emballeurs.csv delimiter";
		st.execute(sql);

	}

	public ResultSet contenuTable() throws SQLException{
		String sql = "SELECT * FROM hotel;";
		return st.executeQuery(sql); // a remplacer par le resultat
	}

	public void insertionTuplesUtilisateur
	(int num,String nom,String ville,int etoiles,String directeur) throws SQLException{
		insert= conn.prepareStatement("INSERT INTO hotel VALUES (?,?,?,?,?);");
		insert.setInt(1,num);
		insert.setString(2,nom);
		insert.setString(3,ville);
		insert.setInt(4,etoiles);
		insert.setString(5,directeur);
		insert.execute();
		insert.close();
	}

	public void changervaleur(String util, String id,String logoumdp, String newlogoumdp) throws SQLException{
		String sql = "UPDATE "+util+" SET "+logoumdp+" = '"+newlogoumdp+"' WHERE login = '"+id+"';";
		st.execute(sql);
	}
	public void douaneColis(int id) throws SQLException{
		String sql = "UPDATE colis SET etat = 'commande' WHERE id = '"+id+"';";
		st.execute(sql);
	}


	public ResultSet affichage(String values) throws SQLException{
		return st.executeQuery("SELECT * FROM "+values+";");
	}

	public void majPrix(int prix, String ref) throws SQLException{
		update= conn.prepareStatement("UPDATE produit SET prix = ? WHERE ref = ?;");
		update.setInt(1, prix);
		update.setString(2, ref);
		update.execute();
		update.close();
	}

	public void majVendus(int nbr, String ref) throws SQLException{
		update= conn.prepareStatement("UPDATE produit SET nbrdevendus = ? WHERE ref = ?;");
		update.setInt(1, nbr);
		update.setString(2, ref);
		update.execute();
		update.close();
	}
	
	public ResultSet gerantSpec1(String s) throws SQLException{
		String sql="SELECT count(*) FROM commande cmd"+
				"LEFT OUTER JOIN colis c ON (cmd.id=c.commande)"+
				"GROUP BY emballeur_id"+
				"WHERE emballeur_id ="+s+";"; 
		return st.executeQuery(sql); 
	}

	public ResultSet gerantSpec2() throws SQLException{
		return st.executeQuery("SELECT ref, nbrdevendus FROM produit ORDER BY nbrdevendus DESC LIMIT 10 OFFSET 0;");
	}
	public void inserer(String insert) throws SQLException{
		st.execute(insert);
	}
	public void supprimerT(String id) throws SQLException{
		st.execute("UPDATE transport SET mdp='SUPPRIMER' WHERE scacc='"+id+"';");
	}
	public void supprimerE(String id) throws SQLException{
		st.execute("DELETE FROM emballeur WHERE ID ='"+id+"'");
	}

	public ResultSet connutil(String qui) throws SQLException {
		String sql = "SELECT * FROM "+qui+";";
		return st.executeQuery(sql);
	}

	public ResultSet test(String qui) throws SQLException {
		String sql = "SELECT * FROM "+qui+";";
		return conn.createStatement().executeQuery(sql);
	}
	public ResultSet embCC(String client) throws SQLException {
		return st.executeQuery(" SELECT cmd.id, livraison_date,datecmd, datevoulue,ref,qualifiant,poids,nbrprod FROM commande cmd LEFT OUTER JOIN ( select commande_id,ref_produit, count(*) as nbrprod from exemplaire group by ref_produit,commande_id) AS bite ON cmd.id=commande_id LEFT OUTER JOIN produit ON ref_produit=ref WHERE id_client='"+client+"' ORDER BY id;");
	}
	
	public ResultSet gerantSpec3() throws SQLException {
		return st.executeQuery("SELECT SUM(sum),id_client FROM commande NATURAL JOIN (SELECT SUM(P.prix),commande_id FROM exemplaire E, produit P,colis C WHERE E.ref_produit=P.ref AND E.commande_id=C.id GROUP BY commande_id) AS prout GROUP BY id_client ORDER BY sum DESC;");

	}
} 
