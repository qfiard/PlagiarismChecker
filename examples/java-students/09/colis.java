import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
public class colis{
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
		int palette = 100;
		int i = 0;
		for(i = 0; i < 50; i++){
			chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'expedie'),"; 	
		}
		for(; i < 200; i++){
			if (i < 180 ){
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'expedie'),"; 	
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'expedie'),";
			}
			if (i < 190 && i >= 180){ 
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'emballe'),"; 	
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'expedie'),";
			}
			if (i >= 190){ 
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'produit'),"; 
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'expedie'),";	
			}
		}
		for (; i < 250; i++) {type_emballage
			if (i < 230) 
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'produit'),"; 	
			else 
				chaine +="("+(id++)+","+ (i+1) +",'"+etatColis()+"',CURRENT_DATE + interval'1 day',"+(palette++/100 )+",'N',null,null,'emballe'),"; 	
		}
		try{

		stt.execute("INSERT INTO colis VALUES "+chaine.substring(0, chaine.length() -1) + ";");
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}

	}
	
	public static char etatColis() {
		int res = ((int)(Math.random() * (4-1)) + 1);
		switch (res) {
			case 1 : return 'N';
			case 2 : return 'F';
			case 3 :return 'D';
		}
		return 'N';
	}

	public static String etatCommande() {
		if (id <= 180)
			return "100% expediees";
		if (id > 180 && id <= 200) 
			return "partialement expediees";
		return "non expediees";
		
	}
}
