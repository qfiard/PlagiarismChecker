package src;

import java.sql.SQLException;

public class DashEmballeur {

	private Connexion c ;
	private String sql = "" ;

	

	public DashEmballeur(){
		c = new Connexion();
	}

	public void getListesCommandes(String id) throws SQLException {
		String s  = ""  ; 
		sql = " select id_commande " +
				" from commande co natural join client cl " +
				" where cl.id_client = co.id_client and id_client = '"+id+"'" ;
		
		System.out.println(sql);
		System.out.println("Listes des Commandes  :");
		System.out.println("Numero Commande  |");
		System.out.println("-----------------+");
		c.doRead(sql);
		while (c.hasNextLine()) {
			s = c.result.getString("id_commande");
			System.out.print(s + "|");
			System.out.println();
		}
	}
	
	public void getEtatsColis() throws SQLException{
		String status = "";
		String sql = "select distinct p.id_colis , qualifiant , id_client , date_livraison" +
				" from colis p , ligne_commande lc , commande c " +
				" where  (p.id_colis = lc.id_colis and lc.id_commande = c.id_commande ) " ;
		System.out.println("Numero Colis (Nom) |  ETAT | Date Livraison");
		System.out.println("-------------------+-------+---------------");
		c.doRead(sql);
		while (c.hasNextLine()) {
			status = c.result.getString("id_colis") ;
			status += " | "+c.result.getString("qualifiant") ;
			status += " | "+c.result.getString("date_livraison") ;
			System.out.print(status + "|");
			System.out.println();
		}
	}
}
