package version1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CommandeAleatoire {
	
	private static Connection conn = null;

	
	
	
	static int refcommande = 1;
	static int refconteneur = 1;
	static int refpalette = 1;
	static int refcolis = 1;
	static int refemballage = 1;
	
	public CommandeAleatoire(){

	}
	
	public static void PartiellementExpediees(int ii) {
		int nombre_produit = aleatoire(20,1);
		int [] tab_produit = new int[nombre_produit]; 
		int [] tab_stock = new int[nombre_produit];
		int nombre_produit_final = 0;
		
		int nombre_colis;
		int nombre_palette;
		int nombre_conteneur;
		
		for (int i = 0; i < tab_produit.length; i++) {
			tab_produit[i] = aleatoire(2000,1);
			tab_stock[i] = aleatoire(20,1);
			
			nombre_produit_final += tab_stock[i];
		}
		
		nombre_colis = nombre_produit_final / 10;
		if (nombre_produit_final % 10 > 0)
			nombre_colis += 1;
		
		nombre_palette = nombre_colis / 10;
		if (nombre_colis % 10 > 0)
			nombre_palette += 1;
		
		nombre_conteneur = nombre_palette / 10;
		if (nombre_palette % 10 > 0)
			nombre_conteneur += 1;

		String [] tab_transport = {"avion", "camion"};
		int transport = aleatoire(2,1);
		
		// conteneur OK
		
		
		
	}
	
	public static String client() {
		int nclient = aleatoire(99,0);
		String refclient = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select refclient from client limit 1 offset "+nclient);
			if(result.next())
				refclient = result.getString("refclient");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return refclient;
	}
	
	public static String emballeur() {
		int nemballeur = aleatoire(4,0);
		String refemballeur = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select refemballeur from emballeur limit 1 offset "+nemballeur);
			if(result.next())
				refemballeur = result.getString("refemballeur");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return refemballeur;
	}
	
	public static String transporteur() {
		int ntransporteur = aleatoire(9,0);
		String reftransporteur = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select reftransp from transporteur limit 1 offset "+ntransporteur);
			if(result.next())
				reftransporteur = result.getString("reftransp");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reftransporteur;
	}
	
	public static String produit() {
		int nproduit = aleatoire(1999,0);
		String refproduit = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select refprod from produit limit 1 offset "+nproduit);
			if(result.next())
				refproduit = result.getString("refprod");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return refproduit;
	}
	
	public static String pays_client(String refclient) {
		String pays = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select pays from client where refclient='"+ refclient +"'");
			if(result.next())
				pays = result.getString("pays");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pays;
	}
	
	public static int prix_produit(String refprod) {
		int prix = 0;
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select prix from produit where refprod='" + refprod + "'");
			if(result.next())
				prix = result.getInt("prix");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prix;
	}
	
	public static String qualifiant_produit(String refprod) {
		String qualifiant = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select qualifiant from produit where refprod='" + refprod + "'");
			if(result.next())
				qualifiant = result.getString("qualifiant");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return qualifiant;
	}
	
	public static boolean emballage_colis(int refc) {
		String refproduit = "";
		String q = "N";
		boolean b = true;
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select refprod, qualifiant from produit natural join qtecommande where refcommande="+ refc);
			while(result.next() && q.equals("N")) {
				refproduit = result.getString("refprod");
				q = result.getString("qualifiant");
				b = false;
			}
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public static String date(int jour) {
		String adate = "";
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select date(NOW() +'1 day'::INTERVAL * ROUND(RANDOM() * "+jour+"))");
			if(result.next())
				adate = result.getString("date");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adate;
	}
	   
	public static int date2(String d) {
		int adate = 0;
		Statement state;
		ResultSet result;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = state.executeQuery("select date '"+ d +"' - date(NOW()) as date");
			if(result.next())
				adate = result.getInt("date");
			else
				System.out.println("error");
			
			result.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adate;
	}

	public static void commandeInitiale(){
		Statement state;
		
		String c = client();
		String d = date(365);
		int bool = 0;
		int commande1 = refcommande++;;
		
		int nombre_produit = aleatoire(20,1);
		int stock = 0;
		String produit1 = "";
		LinkedList<String> list_prod = new LinkedList<String>();
		
		int prix = 0;
		int prix_tt = 0;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			bool = state.executeUpdate("insert into commande values(" + commande1 + ",'" + c + "', '" + d + "', 'non expediee', null, 0)");
			if(bool != 0)
				System.out.println("OK1 !! ");
			else
				System.out.println("error");
			
			for (int i = 0; i < nombre_produit; i++) {
				produit1 = produit();
				list_prod.add(produit1);
				
				stock = aleatoire(20, 1);
				System.out.println(produit1);
				
				while (!list_prod.contains(produit1)) {
					produit1 = produit();
				}
				
				bool = state.executeUpdate("insert into qtecommande values(" + commande1 + ",'" + produit1 + "'," + stock + ")");
				
				if(bool != 0)
					System.out.println("OK2 !! ");
				else
					System.out.println("error");
				
				prix = stock * prix_produit(produit1);
				prix_tt += prix;	
			}
			
			bool = state.executeUpdate("update commande set prix_com="+ prix_tt + " where refcommande=" + commande1);
			if(bool != 0)
				System.out.println("OK3 !! ");
			else
				System.out.println("error");
			
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commandeInitialeAvecColis(){
		Statement state;
		
		String c = client();
		String d = date(365);
		int bool = 0;
		
		int nombre_produit = aleatoire(20,1);
		int stock = 0;
		int stock_tt = 0;
		String produit1 = produit();
		LinkedList<String> list_prod = new LinkedList<String>();
		String [] tab_prod_n = new String[nombre_produit];
		tab_prod_n[0] = produit1;
		list_prod.add(produit1);
		
		int prix = 0;
		int prix_tt = 0;
		
		try {
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			bool = state.executeUpdate("insert into commande values(" + refcommande + ",'" + c + "', '" + d + "', 'non expediee', null, 0)");
			if(bool != 0)
				System.out.println("OK1 !! ");
			
			int [] tab_prod_q = new int[nombre_produit];
			
			for (int i = 0; i < nombre_produit; i++) {
				stock = aleatoire(20, 1);
				tab_prod_q[i] = stock;
				
				stock_tt += stock;
				System.out.println(produit1);
				
				bool = state.executeUpdate("insert into qtecommande values(" + refcommande + ",'" + produit1 + "'," + stock + ")");
				if(bool != 0)
					System.out.println("OK2 !! ");
				
				tab_prod_n[i] = produit1;
				while (list_prod.contains(produit1)) {
					produit1 = produit();
				}
				list_prod.add(produit1);
				
				prix = stock * prix_produit(produit1);
				prix_tt += prix;	
			}
			
			bool = state.executeUpdate("update commande set prix_com="+ prix_tt + " where refcommande=" + refcommande);
			if(bool != 0)
				System.out.println("OK3 !! ");
			else
				System.out.println("error");
			
			int nombre_colis;
			int nombre_palette;
			int nombre_conteneur;
			
			nombre_colis = stock_tt / 10;
			if (stock_tt % 10 > 0)
				nombre_colis += 1;
			
			nombre_palette = nombre_colis / 10;
			if (nombre_colis % 10 > 0)
				nombre_palette += 1;
			
			nombre_conteneur = nombre_palette / 10;
			if (nombre_palette % 10 > 0)
				nombre_conteneur += 1;
			
			System.out.println(nombre_colis + " " + nombre_palette + " " + nombre_conteneur);
			
			String [] tab_transport = {"avion", "camion"};
			int transport = aleatoire(1,0);
			
			bool = state.executeUpdate("insert into conteneur values("+ refconteneur +", '"+ tab_transport[transport] +"', '"+ pays_client(c) +"')");
			if(bool != 0)
				System.out.println("OK4 !! ");
			
			int [] tab_palette = new int[nombre_palette];
			for (int i = 0; i < nombre_palette; i++) {
				tab_palette[i] = refpalette;
				bool = state.executeUpdate("insert into palette values("+ refpalette +", "+ refconteneur +")");
				if(bool != 0)
					System.out.println("OK5 !! ");
				
				refpalette++;
			}
			
			int [] tab_colis = new int[nombre_colis];
			int p = 0;
			String emb = "Normal";
			
			
			
			for (int i = 0; i < nombre_colis; i++) {
				tab_colis[i] = refcolis;
				p = tab_palette[i/10];
				
				bool = state.executeUpdate("insert into colis values("+ refcolis +", "+ refcommande +", 'initial','" + date(date2(d) - 1) + "', "+ p +",'"+ transporteur() +"',0, null)");
				if(bool != 0)
					System.out.println("OK6 !! ");
				
				
				
//				if(!emballage_colis(refcommande)) {
//					emb = "Special";
//				}
//				
//				bool = state.executeUpdate("insert into emballage values("+ refemballage +", '"+ emballeur() +"', '" + emb + "', " + refcolis +")");
//				if(bool != 0)
//					System.out.println("OK7 !! ");
				
				refcolis++;
//				refemballage++;
			}
			
//			int [] tab_colis_q = new int[nombre_colis];
//			// tab_prod_q
//			// tab_prod_n
//			int i = 0, j = 0;
//			
//			while (i < nombre_colis){
//				while (tab_colis_q[i] <= 10){
//					while (tab_prod_q[j] != 0){
//						tab_colis_q[i]++;
//						tab_prod_q[j]--;
//					}
//					j++;
//				}
//				i++;
//			}
			
			
			
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		refcommande++;
		refconteneur++;
	}
	
	

	
	public static int aleatoire(int h, int l) {
		int random = (int)(Math.random() * (h+1-l)) + l;
		return random;
	}
	
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("DRIVER OK ! ");
			
			String url = "jdbc:postgresql://localhost:5432/";
			String user = "postgres";
			String passwd = "gruszka";
			
			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connection effective !");	
			
			//Fenetre f = new Fenetre();
			
			//TraitementDonnee t = new TraitementDonnee("/Users/Patoch_9/Documents/workspace/Base de donnŽes/src/data.csv");
			for (int i = 0; i < 1; i++)
				commandeInitialeAvecColis();
			
			//System.out.println(date2(date(365)));
			//System.out.println(emballage_colis(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
