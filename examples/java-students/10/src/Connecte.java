
import java.sql.*;

class Connecte {
	Connection conn; // la connexion a la base
	Statement st;

	// connection a la base
	public Connecte(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		try{
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e){
			System.out.println("Erreur !\nNe pas oublier de modifier la CLASSPATH");
			System.out.println("CLASSPATH=/ens/renaultm/Public/BD6/TP5/postgresql.jdbc.jar:$CLASSPATH");
			System.exit(1);
		}
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}

	// fermeture de la connection
	public void close() throws SQLException{ 
		conn.close();
	}
} 
