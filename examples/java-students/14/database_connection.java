import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class database_connection {


	
	private Connection conn;
	
	public database_connection() throws ClassNotFoundException {
		conn = null;
		
		try{ 
		Class.forName("org.postgresql.Driver"); 

		conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/",Config.getDbLogin(), Config.getDbPassword()); 

		} catch (SQLException e) { 
		System.out.println("Connection Failed! Check output console"); 
		e.printStackTrace(); 
		
		} 

		if (conn != null) 
		System.out.println("You made it, take control your database now!"); 
		else 
		System.out.println("Failed to make connection!");
		
		
		}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
