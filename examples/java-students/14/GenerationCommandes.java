import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class GenerationCommandes {
	private Statement sql = null;
	
	public GenerationCommandes(Statement sql) {
		this.sql = sql;
	}
	
	public String getClient() {
		String client = null;
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT id_client FROM client ORDER BY RANDOM() LIMIT 1;");
			if(rs.next())
			{
				client = (rs.getString("id_client")).trim();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return client;
	}
	
	public String getProduit() {
		String produit = null;
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT id_produit FROM produit ORDER BY RANDOM() LIMIT 1;");
			if(rs.next())
			{
				produit = (rs.getString("id_produit")).trim();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return produit;
	}
	
	public String getDate() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c1 = Calendar.getInstance();

	    int lower = 3;
	    int higher = 50;

	    int random = (int)(Math.random() * (higher-lower)) + lower;
	    
	    c1.add(Calendar.DATE,random);

		return sdf.format(c1.getTime());
	}
	
	public int newCommande(String etat) {
		String req = "insert into commande (id_client, date_prevue, prix, etat) values ('"+getClient()+"', '"+getDate()+"', 0, '"+etat+"');";
		//System.out.println(req);
		try {
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		
		int serialNum = 0;
		  
		// get the postgresql serial field value with this query
		String query = "select currval('commande_id_commande_seq')";
		ResultSet rs;
		try {
			rs = sql.executeQuery(query);
			if ( rs.next() )
			{
				try {
					serialNum = rs.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}

		return serialNum;
	}
	
	public void incPrixCommande(int id_commande, int prix) {
		try {
			sql.executeQuery("UPDATE commande set prix = '"+prix+"' where id_commande='"+id_commande+"';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public int getPrix(String id_produit) {
		int prix = 0;
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT prix from produit where id_produit = '"+id_produit+"';");
			if(rs.next())
			{
				prix = rs.getInt("prix");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		return prix;
	}
	
	public void addProduit(int id_commande) {
		String id_produit=getProduit();
		
		incPrixCommande(id_commande, getPrix(id_produit));
		
		String req = "insert into produit_commande (id_produit, id_commande) values ('"+id_produit+"', '"+id_commande+"');";
		//System.out.println(req);
		try {
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
	}
	
	public void makeCommandes(int nb, String etat) {
		for(int i = 0; i < nb; ++i) {
			int id_commande = newCommande(etat);

			int nb_produits = (int)(Math.random() * (4-1)) + 1;
			
			for(int j=0; j<nb_produits; ++j) {
				addProduit(id_commande);
			}
			
		}
	}
	
	public void generateCommandes() {
		makeCommandes(180, "EXP");
		makeCommandes(20, "PEXP");
		makeCommandes(50, "NEXP");
		System.out.println("\nGeneration des commandes terminee\n");
	}
}
