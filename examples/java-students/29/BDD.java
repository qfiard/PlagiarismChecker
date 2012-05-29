/**
 * Design pattern : SINGLETON
 * pour ne permettre qu'1 seule instance 
 * de la Connexion à la base
 * 
 */


import java.sql.*;

public class BDD {
	private static BDD instance;
	private Connection conn;
	Statement stmt;
	PreparedStatement pStmt;

	// Connexion a la base Postgresql
	private BDD(String login, String motPasse) throws SQLException, ClassNotFoundException{
		String serveur = "jdbc:postgresql://localhost/" + login;
	//	String serveur2 = "jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/pavot";
		Class.forName("org.postgresql.Driver");
		this.conn = DriverManager.getConnection(serveur, login, motPasse);
	}

	public static BDD connect()  throws SQLException, ClassNotFoundException{
		return BDD.connect("nadine", "123niab");
	}
	
	public static BDD connect(String login, String motPasse) throws SQLException, ClassNotFoundException{
		if (null == instance) {
        	instance = new BDD(login, motPasse);
		}
		return instance;
	}

	// fermeture de la connection
	public void close() throws SQLException{ 
		conn.close();
	}

	public ResultSet getSelect(String query) throws SQLException {
		this.stmt = this.conn.createStatement();
		ResultSet rs =  stmt.executeQuery(query);
		return rs;
	};

	public int execute(String query) throws SQLException {
		this.stmt = this.conn.createStatement();
		int nb = stmt.executeUpdate(query);		
		this.stmt.close();
		return nb;
	}

	public PreparedStatement getPreparedStatement(String query) throws SQLException{
		this.pStmt = conn.prepareStatement(query);
		return pStmt;
	}

	protected void finalize() throws Throwable
	{
		this.stmt.close();
		this.conn.close();
		super.finalize(); 
	} 
}

