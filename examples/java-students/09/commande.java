import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
public class commande{
	static int id;
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		copy cop = new copy();
		Statement stt ;
		Connection connn;
		try{
			Class.forName("org.postgresql.Driver");
		}catch(Exception ee){
			System.err.println("Probleme driver");
			System.exit(1);
		}			
        	connn= DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/benelhad","benelhad","18011990");
        	stt=connn.createStatement();
		String chaine ="";
		id = 1;
		for(int i = 0; i < 5; i++){
			for (int j = 0; j < 50; j++) {
	
				chaine +="(\'"+(id++)+"\',\'"+cop.clients[j][0]+"\',\'"+cop.douanes[i][2]+"\',CURRENT_DATE,CURRENT_DATE  + interval'1 week' ,\'"+etatCommande()+"'),";			
			}

		}
		try{

		stt.execute("INSERT INTO commande VALUES "+chaine.substring(0, chaine.length() -1) + ";");
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}

	}
	public static String etatCommande() {
		if (id <= 180)
			return "100% expediees";
		if (id > 180 && id <= 200) 
			return "partialement expediees";
		return "non expediees";
		
	}
}
