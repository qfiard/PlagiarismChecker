import java.io.*;
import java.sql.*;

class ConnectionBase {
    static Connection conn;
    static Statement stmt;
    PreparedStatement insert;
    PreparedStatement delete;
    PreparedStatement update;
    
    // Connection a la base
    public ConnectionBase(String login, String motPasse) throws SQLException, ClassNotFoundException{	
	Class.forName("org.postgresql.Driver");
	conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login,login, motPasse);
    }



	// Cree une table
    public static void creaTable(String s) throws SQLException {
	try {
	    stmt = conn.createStatement();
	    stmt.executeUpdate(s);
	    stmt.close();
	} 
	catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
    } 
        // Supprime une table
    public void supr (String s) throws SQLException{
	try {
	    stmt = conn.createStatement();
	    stmt.executeUpdate(s);
	    stmt.close();
	} 
	catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
    }
    
	// Retourne les donnees cherchees
    public ResultSet res(String s) throws SQLException{
	ResultSet rs=null;
	try {
	    stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				     ResultSet.CONCUR_READ_ONLY);
	    rs = stmt.executeQuery(s);
	} 
	catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
	return rs;
    }
	   
    
	// Met a jour les donnees
	public void update (String s) throws SQLException{
	try {
	    update = conn.prepareStatement(s);
	    update.execute();
	    update.clearParameters();
	} 
	catch(SQLException e) {
	    System.out.println("Erreur " + e.getMessage());
	}
    }
	// fermeture de la connection
    public void close() throws SQLException{ 
	conn.close();
    }

	    public void insert() throws SQLException{
	System.out.println("A implementer!");
    }

public void insertionTuplesUtilisateur
	(int num,String nom,String ville,int etoiles,String directeur) throws SQLException{
    }
	
	    

} 
