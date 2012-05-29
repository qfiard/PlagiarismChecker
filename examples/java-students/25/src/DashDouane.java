package src;

import java.sql.*;
import java.util.*;


public class DashDouane {
	private Connexion c ;


	public DashDouane(){
		c = new Connexion();
	}

	public static void main(String[] args){
			DashDouane d = new DashDouane();
			
			try {
				d.getListesCommandesPaysDouane();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean checkUser(String idUser, String pass, String table) {
		c.doRead("select * from "+table+" where login ='" 
				+idUser+ "'and mot_de_passe = '"+pass + "'");
		if ( c.hasNextLine()) {
			return true;
		}
		return false ;
	}

	public String getPaysDouane(String idUser, String table) {
		String pays = "" ;
		c.doRead("select pays_douane from douane where login ='" 
				+idUser+ "' ");
		if(c.hasNextLine()){
			try{
				pays = c.result.getString("pays_douane");
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return  pays;
	}

	public void getListsProduits() throws SQLException {
		c.doRead("select * from produit");
		ResultSetMetaData rsmd = c.result.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		while (c.hasNextLine()) {
			for (int i = 1; i<=numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i);
				if (colName.equalsIgnoreCase("login")|| colName.equalsIgnoreCase("mot_de_passe")  ) break;
				String s = c.result.getString(i);
				System.out.print(s + "|");
			}
			System.out.println();
		}
	}


	public void getListsClients() throws SQLException {
		c.doRead("select * from client");
		ResultSetMetaData rsmd = c.result.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		while (c.hasNextLine()) {
			for (int i = 1; i<=numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i);
				if (colName.equalsIgnoreCase("login")|| colName.equalsIgnoreCase("mot_de_passe")  ) break;
				String s = c.result.getString(i);
				System.out.print(s + "|");
			}
			System.out.println();
		}
	}
	
	public String searchCommmandeById(String idx) throws SQLException{
		String cmd = "Commande indisponible !" ;
		c.doRead("select * from commande where id_commande ='" +idx+ "'");
		ResultSetMetaData rsmd = c.result.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		while (c.hasNextLine()) {
			for (int i = 1; i<=numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i);
				if (colName.equalsIgnoreCase("login")|| colName.equalsIgnoreCase("mot_de_passe")  ) break;
				String s = c.result.getString(i);
				System.out.print(s + "|");
			}
			
			
			try {
			cmd = c.result.getString(1);
			cmd = c.result.getString(2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(cmd);
		return cmd;
		
	}
	
	public void getListesCommandesPaysDouane() throws SQLException {
		String liste = "" ;
		String sql ="select id_commande , pays_douane " +
				" from commande co   " +
				" where pays_douane IN ( select pays_douane from douane )" ;
		System.out.println(sql);
		c.doRead(sql);
		System.out.println("Numéro du commande | Pays Douane");
		while(c.hasNextLine()){
			liste = c.result.getString("id_commande");
			liste += " | "+c.result.getString("pays_douane");
			System.out.println(liste + " | ");
		}
	}

	public void getCommandeByDouane() {
		
	}

	
	


}