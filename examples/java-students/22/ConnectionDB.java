import java.sql.*;

public class ConnectionDB
{
    
    private String      url;
    private String      user;
    private String      pswd;
    public Connection   conn;
    
    public ConnectionDB()
    {
	try
	    {
		/* On lance le driver PostgreSQL */
		Class.forName("org.postgresql.Driver").newInstance();

		/* On établit la connexion avec la table associée au projet */
		user   = "projectUser";
		pswd   = "projectBDD";
		url    = "jdbc:postgresql:projectBDD";
		conn   = DriverManager.getConnection(url, user, pswd);
	    }
	catch (Exception e)
	    {
		System.out.println("Enable to establish connection :\n\t" + e);
	    }
    }

}