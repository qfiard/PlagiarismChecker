import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

	/**
	 * @param args
	 */
	public static Connection conn=null;
	public static Statement state;
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/";
			String user = "postgres";
			String passwd = "zouzou18";
			
			conn = DriverManager.getConnection(url, user, passwd);			
			state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			//Met les donnée dans les bases 
			TraitementDonnee t=new TraitementDonnee("Desktop/data.csv");
			
			
			//Lance l'accueil (MDP et Login doivent etre Valide )
			//Accueil ac=new Accueil(); 
			Douane d = new Douane("France");
			//Gerant d = new Gerant();
			//state.close();
			//conn.close();
		
	}

}
