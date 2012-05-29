package src;


import java.sql.*;

public class Connexion {

	protected Connection connexion = null;
	protected Statement stat = null;
	protected PreparedStatement pstat = null ;
	protected ResultSet result = null ;
	
	/*
	private String loginUser = "postgres";
	private String loginPasswd = "root";
	private String loginUrl = "jdbc:postgresql://localhost/projectdb2012";
	*/
	
	private String loginUser = "oubr";
	private String loginPasswd = "medb2012";
	private String loginUrl = "jdbc:postgresql://localhost/projectdb2012";
	
	
	/*
	private String loginUser = "oubraim";
	private String loginPasswd = "BEDE2011";
	private String loginUrl = "jdbc:postgresql://localhost/oubraim";
	*/
	
	/**
	 * @author oub
	 * Cette classe e pour but de se connecter
	 * 
	 */
	public Connexion(){
        try{
        	Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException ex){
        	System.err.println("Driver Not Found  !");
        	System.exit(1);
        }
        
        try{
        	connexion = DriverManager.getConnection(loginUrl, loginUser, loginPasswd); 
        	// connexion = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + loginUser,loginUser, loginPasswd);
        }catch(SQLException ex){
        	System.out.println("Connexion echoue");
        	System.out.println(ex.getMessage());
        	System.exit(1);
        }
	}
	
	public void doRead(String sql){
		try{
			stat = connexion.createStatement();
			result = stat.executeQuery(sql);
		}catch(SQLException ex){
			System.out.println(ex.getMessage()); System.exit(1);
		}
	}
	
	public void doWrite(String sql){
		try{
			pstat = connexion.prepareStatement(sql);
			pstat.executeUpdate();
		}catch(SQLException ex){
			System.out.println(ex.getMessage()); System.exit(1);
		}
	}
	
	public boolean hasNextLine(){
		try{
			return result.next();
		}catch(SQLException ex){
			return false ;
		}
	}
	
	public boolean getFirstLine(){
		try{
			return result.first();
		}catch(SQLException ex){
			return false ;
		}
	}
	
	public boolean getLastLine(){
		try{
			return result.last();
		}catch(SQLException ex){
			return false ;
		}
	}
	
	public void stop(){
		try{
			pstat.close();
			//stat.close(); connexion.close();
		}catch(SQLException ex){}
	}
	
	
}
