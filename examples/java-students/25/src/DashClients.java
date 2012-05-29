package src;

import java.sql.*;

public class DashClients {

	private Connexion c ;
	private String sql = "" ;
	
	/*public static void main(String[] a ) throws SQLException{
			new DashClients().commitCommande( "me" , "GN-746901-SIY-63"  , 5  , "2012-05-15" );
	}*/
	
	public DashClients(){
		c = new Connexion();
	}
	
	public void getListsProduits() throws SQLException {
		String s  = ""  ; String  sql = "select id_produit, description_prod , cout from produit where reserve > 0" ;
		System.out.println("Listes des produits diponibles :");
		System.out.println("Numero Produit |         Description         | Prix ");
		System.out.println("---------------+-----------------------------+------");
		c.doRead(sql);
		while (c.hasNextLine()) {
				 s = c.result.getString("id_produit");
				 s += " | "+c.result.getString("description_prod");
				 s += " | "+c.result.getString("cout");
				System.out.print(s + "|");
				System.out.println();
		}
	}
	
	public void getEtatsColis(String idClient) throws SQLException{
		String status = "";
		String sql = "select distinct  p.id_colis , etats_colis , id_client" +
				" from colis p , ligne_commande lc , commande c " +
				" where  (p.id_colis = lc.id_colis and lc.id_commande = c.id_commande ) " +
				" and id_client = '"+idClient+"' ";
		System.out.println("Numero Colis (Nom) |  ETAT ");
		System.out.println("-------------------+-------");
		c.doRead(sql);
		while (c.hasNextLine()) {
			status = c.result.getString("id_colis") ;
			status += " | "+c.result.getString("etats_colis") ;
			System.out.print(status + "|");
			System.out.println();
		}
	}
	
	public String getIdClient(String str) throws SQLException {
		String s = "" ;
		String sql = "select id_client from client where login = '"+str+"' ";
		c.doRead(sql);
		while (c.hasNextLine()) {
			s = c.result.getString("id_client") ;
		}
		return s ;
	}
	
	public boolean checkQteForProduit(String prod, int qte) throws SQLException {
		boolean good = false ;
			String sql = "select reserve from produit where id_produit ='"+prod+"'"  ;
			int stock = 0 ;
			c.doRead(sql);
			while (c.hasNextLine()) {
				stock = Integer.parseInt(c.result.getString("reserve")) ;
			}
			if (qte > stock ) return false ;
			else return true;
	}
	
	public boolean isExistProduct(String idx){
		String sql = "select id_produit from produit e WHERE id_produit = '"+idx+"'  " ;
		c.doRead(sql);
		while ( c.hasNextLine()) {
			return true;
		}
		return false ;
	}
	
	public void generate(){
	    System.out.println("Random Number: " + (int)(Math.random()*100));
	}
	public void commitCommande(String l, String prod, int qte , String date_livraison , String pays) throws SQLException {
		// Ajouter dans la tbl commande puis modifier le reserve
		String idCLient = getIdClient(l);
		String num_commande = l+"-"+(int)(Math.random()*999)+"-"+idCLient ;
		double m = calculeMontantTotalCommande(prod , qte);
		/**
		 * TODO m+interval date frais livraison
		 */
		try{
			sql = "insert into commande" +
		
				" values ( '"+num_commande+"' , current_date , '"+idCLient+"' , " +
						"'"+date_livraison+"' , '"+pays+"' , 'non expediees' , "+m+" )"; 
		c.doWrite(sql);
		} catch(Exception e){
			System.out.println("Date de livraison doit etre superieur a la date de aujordhui");
		}
		updateReserveProduit(qte , prod);
	}

	private void updateReserveProduit(int qte , String prod) throws SQLException {
		int ancienReserve  = 0 ;
		String sql = "select reserve from produit where id_produit ='"+prod+"'"  ;
		c.doRead(sql);
		if(c.hasNextLine()){
			ancienReserve = c.result.getInt("reserve");
		}
		ancienReserve -= qte ;
		sql = "update produit set reserve = "+ancienReserve+"  where id_produit ='"+prod+"'"  ;
		c.doWrite(sql);
		
	}

	private double calculeMontantTotalCommande(String p, int qte) throws SQLException {
		double price = 0 ;
		String sql = "select cout from produit where id_produit = '"+p+"'  " ;
		c.doRead(sql);
		while( c.hasNextLine()){
			price = c.result.getDouble("cout");
		}
		return price*qte;
		
	}
	
	public void changeIdentifiant(String id, String lo , String mdp) throws SQLException {
		sql = "update client set login ='"+lo+"' , mot_de_passe ='"+mdp+"' where id_client = '"+id+"'  " ;
		System.out.println(sql);
		c.doWrite(sql);
	}
	
}
