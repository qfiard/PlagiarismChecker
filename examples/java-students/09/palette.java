import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
public class palette{
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
		for(int i = 0; i < 100; i++){
				chaine +="("+(i+1)+",'"+mode()+"',CURRENT_DATE + interval'1 week'),";			
		}
		try{

		stt.execute("INSERT INTO palette VALUES "+chaine.substring(0, chaine.length() -1) + ";");
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}

	}
	
	public static String mode() {
		int res = ((int)(Math.random() * (4-1)) + 1);
		switch (res) {
			case 1 : return "conteneur";
			case 2 : return "camion";
			case 3 : return "avion";

		}
		return "camion";
	}

}
