import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conteneur {
	//private String destination;
	//private String date_livraison;
	private static final int TAILLE = 10;
	private int id_conteneur;
	private Statement sql;
	
	public Conteneur(String destination, Statement sql) {
	
		this.sql = sql;
		String code_transporteur=getTransporteur();

		
		String req = "insert into conteneur(destination, code_transporteur, nb_palettes) values ('"+destination+"', '"+code_transporteur+"', '0');";
		//System.out.println(req);
		try {
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		
		int conteneur = 0;
		  
		// get the postgresql serial field value with this query
		String query = "select currval('conteneur_id_conteneur_seq')";

		try {
			ResultSet rs = sql.executeQuery(query);
			if ( rs.next() )
			{
				try {
					conteneur = rs.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		//System.out.println("Conteneur ok");
		id_conteneur = conteneur;
	}
	
	public static int getNbPalettes(Statement sql, int id_conteneur) {
		int nb_palettes = 0;
		ResultSet rs;
		try {
			String req = "SELECT nb_palettes from conteneur where id_conteneur = '"+id_conteneur+"';";
			//System.out.println(req);
			rs = sql.executeQuery(req);
			if(rs.next())
			{
				nb_palettes = rs.getInt("nb_palettes");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return nb_palettes;
	}
	
	public static int getTaille() {
		return TAILLE;
	}
	
	/*public int getNbPalettesMax() {
		int nb_palettes_max = 0;
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT nb_palettes_max conteneur where id_conteneur = '"+id_conteneur+"';");
			if(rs.next())
			{
				nb_palettes_max = rs.getInt("nb_palettes_max");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return nb_palettes_max;
	}*/
	
	/*public boolean isFull() {
		return (getNbPalettes() == getNbPalettesMax());
	}*/
	
	public static void addPalette(Statement sql, int id_conteneur) {
		int nb_palettes = getNbPalettes(sql, id_conteneur);
		try {
			String req = "UPDATE conteneur set nb_palettes = '"+(nb_palettes+1)+"' where id_conteneur='"+id_conteneur+"';";
			//System.out.println(req);
			sql.executeQuery(req);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/*public void addPalette() {
		int nb_palettes = getNbPalettes();
		try {
			String req = "UPDATE conteneur set nb_palettes = '"+(nb_palettes+1)+"' where id_conteneur='"+id_conteneur+"';";
			//System.out.println(req);
			sql.executeQuery(req);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}*/
	
	public String getTransporteur() {
		String transporteur = "";
		ResultSet rs;
		try {
			rs = sql.executeQuery("SELECT code_transporteur FROM transporteur ORDER BY RANDOM() LIMIT 1;");
			if(rs.next())
			{
				transporteur = (rs.getString("code_transporteur")).trim();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return transporteur;
	}
	
	public int getId() {
		return id_conteneur;
	}
}
