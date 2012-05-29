import java.sql.*;


public class LienBD {

	Connection conn;
	Statement st;
	
	public LienBD(String login, String pass) throws ClassNotFoundException, SQLException{
		//Connection a la base
		Class.forName("org.postgresql.Driver");
		
		/* ------------------------------------ ATTENTION ----------------------------------------------------------------
		 * ###############################################################################################################
		 * ###############################################################################################################
		 * -----------------------      connection nivose a changer !              --------------------------------------- */
		this.conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/"+login,login, pass); //TODO
		
		this.st= this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
	}
	
	public void close() throws SQLException{
		this.conn.close();
		
	}
	
	public ResultSet querySQL(String s) throws SQLException{
		return this.st.executeQuery(s);
	}
	
	public void AfficheRS(ResultSet rs) throws SQLException{
		while(rs != null && rs.next()){
			System.out.print(rs.getString("login")+" ");
			System.out.print(rs.getString("pass")+"\n");
			
		}
	}	
}
