import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Palette {
	//private String id_produit;
	//private int nb_produits;
	//private int nb_produits_max;
	private String destination;
	private String date_livraison;
	private int id_palette;
	private Statement sql = null;
	
	public Palette(Statement sql, String id_produit, String destination) {
		//this.id_produit = id_produit;
		this.destination = destination;
		this.sql = sql;
		
		//nb_produits = 0;
		//nb_produits_max = 0;
		
		ResultSet rs;
		int nb_produits_max = 0;
		try {
			rs = sql.executeQuery("SELECT quantite_max from produit where id_produit = '"+id_produit+"';");
			if(rs.next())
			{
				nb_produits_max = rs.getInt("quantite_max");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		int conteneur = getConteneur();
		String req = "insert into palette(id_produit, id_conteneur, nb_produits, nb_produits_max) values ('"+id_produit+"', '"+conteneur+"', '0','"+nb_produits_max+"');";
		//System.out.println(req);
		try {
			Conteneur.addPalette(sql, conteneur);
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		
		int serialNum = 0;
		  
		// get the postgresql serial field value with this query
		req = "select currval('palette_id_palette_seq');";
		rs=null;
		try {
			rs = sql.executeQuery(req);
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
		
		id_palette = serialNum;
		//System.out.println("Palette ok");
	}
	
	private int getConteneur() {
		int conteneur = -1;
		ResultSet rs = null;
		try {
			String req = "SELECT id_conteneur from conteneur where nb_palettes < '"+Conteneur.getTaille()+"' and destination = '"+destination+"';";
			//System.out.println(req);
			rs = sql.executeQuery(req);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		try {
			if(rs.next())
			{
				conteneur = rs.getInt("id_conteneur");
			}
			else {
				Conteneur c = new Conteneur(destination, sql);
				conteneur = c.getId();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//System.out.println("rs =" +conteneur);
		return conteneur;
	}

	public boolean isFull() {
		return (getNbProduit() == getNbProduitMax());
	}
	
	public int getNbProduit() {
		int nb_produits = 0;
		ResultSet rs;
		try {
			String req = "SELECT nb_produits from palette where id_palette = '"+id_palette+"';";
			//System.out.println(req);
			rs = sql.executeQuery(req);
			if(rs.next())
			{
				nb_produits = rs.getInt("nb_produits");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return nb_produits;
	}
	
	public int getNbProduitMax() {
		int nb_produits_max = 0;
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT nb_produits_max from palette where id_palette = '"+id_palette+"';");
			if(rs.next())
			{
				nb_produits_max = rs.getInt("nb_produits_max");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return nb_produits_max;
	}
	
	public void addProduit() {
		int nb_produits = getNbProduit();
		try {
			String req = "UPDATE palette set nb_produits = '"+(nb_produits+1)+"' where id_palette='"+id_palette+"';";
			//System.out.println(req);
			sql.executeQuery(req);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String getType() {
		String type = "";
		ResultSet rs;
		try {
			rs = sql.executeQuery("select id_produit from palette where id_palette='"+id_palette+"' ;");
			if(rs.next())
			{
			 type = (rs.getString("id_produit")).trim();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return type;
	}
	
	public String getPays() {
		return destination;
	}
	
	public void setDate(String date) {
		date_livraison = date;
	}
	
	public String getDate() {
		return date_livraison;
	}
	
	public int getId() {
		return id_palette;
	}
	

}
