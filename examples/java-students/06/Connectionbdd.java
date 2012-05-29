

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;




public class Connectionbdd {
	public String ODBpilote = "jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/";
	public String login ="bara";
	public String mdp   ="sect87";
	
	Connection conn;
	ResultSet results;
	 Statement st;
	    PreparedStatement insert;
	    PreparedStatement delete;
	    PreparedStatement update;
	ResultSetMetaData rsmd;
	String sql;
	
	public ResultSet getResult() { return results; }

	// CONNEXION ET FERMETURE
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	public Connectionbdd(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login, login,motPasse);
		st = conn.createStatement();
	   
	   }
	

	public boolean open(String piloteODBC, String id, String pass) {
		boolean ok = false;
		try {
			conn = DriverManager.getConnection(piloteODBC + id, id, pass);
			ok = true;
		} catch (SQLException e) {
			System.out.println("Echec d'ouverture:" + e.getMessage());
			ok = false;
		}
		return (ok);
	}

	public boolean close() {
		boolean ok = false;
		try {
			conn.close();
			ok = true;
		} catch (SQLException e) {
			System.out.println("Echec lors de la fermeture:" + e.getMessage());
			ok = false;
		}
		return ok;
	}

	// REQUETE
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	public ResultSet retourneRequeteSELECT(String requete) {
		ResultSet donnees = null;
		try {
			open(ODBpilote,login,mdp);
			Statement stmt = conn.createStatement();
			donnees = stmt.executeQuery(requete);
			close();
		} catch (SQLException e) {
			System.out.println("Erreur requête : " + e.getMessage());
		}
		return donnees;
	}

	public void executeRequeteSELECT(String requete) {
		results = retourneRequeteSELECT(requete);
	}

	public void executeRequete(String requete) {
		try {
			open(ODBpilote,login,mdp);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(requete);
			close();
		} catch (SQLException e) {
			System.out.println("Erreur requête : " + e.getMessage());
		}
	}

	 public void insertionTuplesPredefinis() throws SQLException{
	    	sql="INSERT INTO tutilisateur values(2,'Idelhadj','paris','4','Mehdi')";
	    	insert = conn.prepareStatement(sql);
	    	insert.execute();
	    	System.out.println("C' bon");
	    }

	 public ResultSet contenuTable() throws SQLException{
	    	results = st.executeQuery("SELECT * FROM tcolis where tcolis.statut = 'en_livraison';"); 
	    	
	    	return results; 
	    }
	 public ResultSet contenuTable1() throws SQLException{
	    	results = st.executeQuery("SELECT * FROM tdouane ;"); 
	    	
	    	return results; 
	    }
}

/*import java.io.*;
	import java.sql.*;
public class Connectionbdd {
	

	    Connection conn; // la connexion a la base
	    Statement st;
	    PreparedStatement insert;
	    PreparedStatement delete;
	    PreparedStatement update;
	    String sql;
	    ResultSet rs;
	    // connection a la base
	    public Connectionbdd(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login,motPasse);
	    st = conn.createStatement();
	   
	   }

	    // fermeture de la connection
	    public void close() throws SQLException{ 
	    	st.close();
	    	conn.close();
		
	    }

	    public  void creationTable() throws SQLException {
		    sql = "CREATE TABLE hotel(num numeric(6) not null primary key,nom varchar(40) not null,ville varchar(9) not null," +
		    		"etoiles numeric(1),directeur varchar(40) not null unique,constraint ch_etoiles check(etoiles>=0 and etoiles<=5)" +
		    		",constraint ch_ville check (ville in ('paris','marseille','lyon')))";
		    
		    st.executeUpdate(sql);
		    System.out.println("C' bon");
	    }	 
	                    
	    public void suppressionTable() throws SQLException {
	    	sql = "DROP TABLE hotel";
		    st.executeUpdate(sql);
		    System.out.println("C' bon");
	    }
	    
	    public void insertionTuplesPredefinis() throws SQLException{
	    	sql="INSERT INTO hotel values(2,'Idelhadj','paris','4','Mehdi')";
	    	insert = conn.prepareStatement(sql);
	    	insert.execute();
	    	System.out.println("C' bon");
	    }
	    
	    public ResultSet contenuTable() throws SQLException{
	    	rs = st.executeQuery("SELECT * FROM hotel;"); 
	    	
	    	return rs; // a remplacer par le resultat
	    }
		   
	    public void insertionTuplesUtilisateur
		(int num,String nom,String ville,int etoiles,String directeur) throws SQLException{
	    }

	} 


*/