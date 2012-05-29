import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;


public class Requete {
	public Requete(){
		
	}
	
	
	
	public static JTable executeQuery(String Query) throws SQLException{
		
		ResultSet rs = Main.state.executeQuery(Query);
		
		/////// ON VA PRENDRE LES TITRES 
		ResultSetMetaData rmd = rs.getMetaData();
		String[] title = new String [rmd.getColumnCount()];
		
		for(int i = 1; i <=  rmd.getColumnCount(); i++){
			title [i-1]=rmd.getColumnName(i).toUpperCase();
		}
		
		
		/////////PRENDS LE CONTENUE
		int l=1;
		Object[][] data =new Object[rmd.getColumnCount()][2000];
		
		
		while(rs.next()){
		for (int i=1; i<=rmd.getColumnCount();i++){
			data[0][i-1]=rs.getObject(i);
			}
		
		
		rs.first();
		//Tant qu'on arrive pas a la fin de notre resultat 
		while(rs.next()){
			for (int j=1; j<=rmd.getColumnCount();j++){
				data[l][j-1]=rs.getObject(j);
				}
			l++;
		}
		
		}
		
		JTable jtb = new JTable(data,title);
		rs.close();
		return jtb;
	}

	
	
public static JTable executeQuery2(String Query) throws SQLException{
		
		ResultSet rs = Main.state.executeQuery(Query);
		
		/////// ON VA PRENDRE LES TITRES 
		ResultSetMetaData rmd = rs.getMetaData();
		String[] title = new String [rmd.getColumnCount()];
		
		for(int i = 1; i <=  rmd.getColumnCount(); i++){
			title [i-1]=rmd.getColumnName(i).toUpperCase();
		}
		
		
		/////////PRENDS LE CONTENUE
		int l=0;
		Object[][] data =new Object[rmd.getColumnCount()][2000];
		
		rs.first();
		while(rs.next()){
			for (int j=1; j<=rmd.getColumnCount();j++){
				data[l][j-1]=rs.getObject(j);
				}
			l++;
		}
		
		JTable jtb = new JTable(data,title);
		rs.close();
		return jtb;
	}

	
	public static String RequeteDetail(int det,String ref,String Pays){
		
		String Query="";
		
		if (det==1){
			Query="SELECT refclient,date_livraison,etat_com,prix_com,controle_com "+
					"FROM commande natural join client " +
					" WHERE cast(refcommande as varchar)='"+ref+"'" +
					" AND pays='"+Pays+"';";
					
		}
		
		else if (det==2){
			Query="SELECT refcolis,etat_col,date_emb " + 
			" FROM colis natural join commande natural join client" +
			" WHERE cast(refcommande as varchar)='"+ref+"'"+
			" AND pays='"+Pays+"';";
			
		}
		
		else if(det==3){
			Query="SELECT refprod,description,quantite " +
				" FROM produit natural join qtecommande natural join commande natural join client" +
				" WHERE cast(refcommande as varchar)='"+ref+"'"+
				" AND pays='"+Pays+"';";
		}
		
		else if(det==4){
			Query="SELECT refprod,description,quantite " +
				" FROM produit natural join qtecolis natural join colis natural join palette natural join conteneur" +
				" WHERE cast(refcolis as varchar)='"+ref+"'"+
				" AND destination='"+Pays+"';";
		}
		
		
		else if(det==5){
			Query="SELECT refcolis,etat_col,date_emb" +
				  "	FROM colis natural join palette natural join conteneur" +
				  " WHERE cast(refpalette as varchar) = '"+ref+"'"+
				  " AND destination='"+Pays+"';";
		}
		
		
		else if(det==6){
			Query="SELECT refpalette " +
					 " FROM palette natural join conteneur " +
					 " WHERE cast(refconteneur as varchar) ='"+ref+"'"+
					 " AND destination='"+Pays+"';";
		}		
		
		
		
		return Query;
	}
	
	
	
	
	
	public static int com_corresp(int refcol) throws SQLException{
		int refcom = 0;
		
		ResultSet rs = Main.state.executeQuery
				("SELECT refcommande FROM colis where refcolis="+refcol+")");
		
		while(rs.next()){
			refcom = rs.getInt("refcommande");
		}
		
		rs.close();
		
		return refcom;
	}
	
	
	
	
	
	
	public static boolean all_rejete(int refcom) throws SQLException{
		boolean b=true;
		
		ResultSet rs = Main.state.executeQuery
				("SELECT etat_col FROM colis where refcommande="+refcom+")");
		
		while(rs.next()){
			if (rs.getString("etat_col")!="rejete"){
				b=false;
			}
		}
		
		rs.close();
		return b;
	}
	
	
}
