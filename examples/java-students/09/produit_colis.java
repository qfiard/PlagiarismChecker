import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
public class produit_colis{
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		int icom=0;
		copy cop = new copy();
		Statement stt  ;
		Connection connn;
		try{
			Class.forName("org.postgresql.Driver");
		}catch(Exception ee){
			System.err.println("Probleme driver");
			System.exit(1);
		}			
        	connn= DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/benelhad","benelhad","18011990");
        	stt=connn.createStatement();
		ResultSet rst = null;
		int total = 0;
		for(int j=0; j<cop.produits.length; j++){
			if(cop.produits[j][0] == null){break;}
			total++;
		}		

		String chaine ="";
		int quant, prod,prodd;
		for(int i = 1; i < 206; i++){
				quant = quantiteQ();
				prod = produitQ(total);
				try{
					rst = stt.executeQuery("select reserve from produit where num = '"+cop.produits[prod][0]+"';");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}	
				rst.next();
				if(quant > rst.getInt("reserve")){quant = 1;} //NON
				
				try{
					stt.execute("update produit set reserve = reserve - "+quant+" where num = '"+cop.produits[prod][0]+"';");
					ResultSet res = stt.executeQuery("select num_commande from colis where num = "+i+";"); 
					res.next();
					icom = res.getInt("num_commande");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}
				chaine +="('"+cop.produits[prod][0]+"',"+i+","+quant+","+icom+"),";			
		}
		for(int i = 206; i <= 410; i++){
				quant = quantiteQ();
				prod = produitQ(total);
				try{
					rst = stt.executeQuery("select reserve from produit where num = '"+cop.produits[prod][0]+"';");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}	
				rst.next();
				if(quant > rst.getInt("reserve")){quant = 1;} //NON
				try{
					stt.execute("update produit set reserve = reserve - "+quant+" where num = '"+cop.produits[prod][0]+"';");
					ResultSet res = stt.executeQuery("select num_commande from colis where num = "+i+";"); res.next();icom = res.getInt("num_commande");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}
				chaine +="('"+cop.produits[prod][0]+"',"+i+","+quant+","+icom+"),";
				quant = quantiteQ();
				prodd = produitQ(total);
				try{
					rst = stt.executeQuery("select reserve from produit where num = '"+cop.produits[prod][0]+"';");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}	
				rst.next();
				if(quant > rst.getInt("reserve")){quant = 1;} //NON
				try{
					stt.execute("update produit set reserve = reserve - "+quant+" where num = '"+cop.produits[prod][0]+"';");
					ResultSet res = stt.executeQuery("select num_commande from colis where num = "+i+";"); res.next();icom = res.getInt("num_commande");
				}catch(SQLException ee){
					System.err.println(ee.getMessage());
					System.exit(2);
				}
				while(prodd == prod){prodd = produitQ(total);}
				chaine +="('"+cop.produits[prodd][0]+"',"+i+","+quant+","+icom+"),";	
		}
		try{

		stt.execute("INSERT INTO produit_colis VALUES "+chaine.substring(0, chaine.length() -1) + ";");
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}
		MAJ();

	}

public static void MAJ(){
	try{
	ResultSet rs = connection.st.executeQuery("update commande C set etat_commande = 'partialement expediees' where 'expedie' =ANY (select etat_colis from colis where num_commande = C.num) AND 'expedie' <>ANY (select etat_colis from colis where num_commande = C.num);");	
	rs = connection.st.executeQuery("update commande C set etat_commande = '100% expediees' where 'expedie' =ALL (select etat_colis from colis where num_commande = C.num);");
	rs = connection.st.executeQuery("update commande C set etat_commande = 'non expediees' where 'expedie' <>ALL (select etat_colis from colis where num_commande = C.num);");

	rs = connection.st.executeQuery("update colis C set etat_colis ='expedie' where '100% expediees' =ALL (select etat_commande from commande where num = C.num_commande);");
	}catch(SQLException ee){
		System.err.println(ee.getMessage());
		System.exit(2);
	}
}
	
	public static int produitQ(int total) {//349 produits
		return ((int)(Math.random() * (total-0)) + 0);
	}
	public static int quantiteQ() { //quantite entre 1 et 5 inclus
		return ((int)(Math.random() * (6-1)) + 1);
	} 
	public static String partialementexpedieesOUnonexpediees() { //quantite entre 1 et 5 inclus
		int tag = ((int)(Math.random() * (3-1)) + 1);
		if(tag == 1) return "non expediees";
		else return "partialement expediees";
	}
}
