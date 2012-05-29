package donnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBD{

	Connection conn;
	Statement st;

	public ConnexionBD(){
		
		//conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);

		//Scanner sc = new Scanner(System.in);
		//String login = "dimcea", mdp = "BEDE2011";
		//String login = "lebeau", mdp = "BEDE2011";
		//String login = "databaka.ro", mdp = "jankie";

		/*System.out.println("Login: ");
		login = sc.nextLine();
		System.out.println("Mot de passe: ");
		mdp = sc.nextLine();*/
		
		try {
			Class.forName("org.postgresql.Driver");
			
			
			//mdp = new String(System.console().readPassword("%s", 
					//"Entrer votre mot de passe pour vous connecter a Postgres: "));
			//conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login, login, mdp);
			//conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/projet", "postgres", "elena31fr");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BD6", "postgres", "BEDE2011");
			
			//conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, mdp);

			//st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception ioe) {			
			ioe.printStackTrace();
			System.exit(0);
		}
		
	}
	
	public Statement createStatement(){
		//return st;
		try {
			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
