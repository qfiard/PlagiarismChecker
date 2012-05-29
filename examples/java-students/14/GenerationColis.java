import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class GenerationColis {
	private Statement sql = null;
	private ArrayList<Integer> commandes;
	private ArrayList<String> emballeurs;
	private ArrayList<Integer> emballeurs_compt;
	private ArrayList<Palette> palettes;
	
	public GenerationColis(Statement sql) {
		this.sql = sql;
		
		emballeurs = new ArrayList<String>();
		emballeurs_compt = new ArrayList<Integer>();
		ResultSet rs;
		try {
			rs = sql.executeQuery( "select id_emballeur from emballeur ;");
			while(rs.next())
			{
			   emballeurs.add( (rs.getString("id_emballeur")).trim() );
			   emballeurs_compt.add(new Integer(0));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		commandes = new ArrayList<Integer>();
		rs = null;
		try {
			rs = sql.executeQuery( "select id_commande from commande where etat='EXP';");
			while(rs.next())
			{
			   commandes.add(rs.getInt("id_commande"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		palettes = new ArrayList<Palette>();
	}
	
	public String getDateEmballage() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c1 = Calendar.getInstance();

	    int lower = 3;
	    int higher = 20;

	    int random = -((int)(Math.random() * (higher-lower)) + lower);
	    
	    c1.add(Calendar.DATE,random);

		return sdf.format(c1.getTime());
	}
	
	public Palette getPalette(String id_produit, int id_commande) {
		//String date = getDate(id_commande);
		String destination = getDestination(id_commande);
		Palette p;
		for(int i=0; i<palettes.size(); ++i) {
			p = palettes.get(i);
			if( !(p.isFull()) && (p.getPays() == destination) /* && DATE_COLIS > DATE_LIMITE_PALETTE */) {
				return p;
			}
		}
		p = new Palette(sql, id_produit, destination);
		return p;
	}
	
	public String getDestination(int id_commande) {
		String pays = "";
		ResultSet rs;
		try {
			rs = sql.executeQuery("select pays from client cl, commande co where cl.id_client=co.id_client and id_commande= '"+id_commande+"';");
			if(rs.next())
			{
				pays = (rs.getString("pays")).trim();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return pays;
	}
	
	public String getDate(int id_commande) {
		String date = "";
		ResultSet rs;
		try {
			rs = sql.executeQuery("select date_prevue from commande where id_commande= '"+id_commande+"';");
			if(rs.next())
			{
				date = (rs.getString("date_prevue")).trim();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		return date;
	}
	
	public void emballer(String id_produit, int id_commande, String id_emballeur) {
		Palette p = getPalette(id_produit, id_commande);
		int palette = p.getId();
		String req = "insert into emballage (id_produit, id_commande, id_emballeur, date_emballage, id_palette)"+
				"values ('"+id_produit+"', '"+id_commande+"', '"+id_emballeur+"', '"+getDateEmballage()+"', '"+palette+"');";
		//System.out.println(req);
		try {
			p.addProduit();
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		//System.out.println("Emballage ok");
	}
	
	public void emballer_today(String id_produit, int id_commande, String id_emballeur) {
		Palette p = getPalette(id_produit, id_commande);
		int palette = p.getId();
		String req = "insert into emballage (id_produit, id_commande, id_emballeur, date_emballage, id_palette)"+
				"values ('"+id_produit+"', '"+id_commande+"', '"+id_emballeur+"', '"+getDate()+"', '"+palette+"');";
		//System.out.println(req);
		try {
			p.addProduit();
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		//System.out.println("Emballage ok");
	}
	
	public String getDate() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c1 = Calendar.getInstance();

		return sdf.format(c1.getTime());
	}
	
	public void traiter_today(int id_commande, String id_emballeur) {
		ArrayList<String> produits = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = sql.executeQuery("select id_produit from produit_commande where id_commande='"+id_commande+"' ;");
			while(rs.next())
			{
			   produits.add( (rs.getString("id_produit")).trim() );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		//String id_emballeur=getEmballeur();
		
		for(int i=0; i<produits.size();++i) {
			emballer_today(produits.get(i), id_commande, id_emballeur);
			maj_emballeur(id_emballeur);
		}
		//palette
		//conteneur
	}
	
	public void traiter(int id_commande) {
		ArrayList<String> produits = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = sql.executeQuery("select id_produit from produit_commande where id_commande='"+id_commande+"' ;");
			while(rs.next())
			{
			   produits.add( (rs.getString("id_produit")).trim() );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		String id_emballeur=getEmballeur();
		
		for(int i=0; i<produits.size();++i) {
			emballer(produits.get(i), id_commande, id_emballeur);
			maj_emballeur(id_emballeur);
		}
		//palette
		//conteneur
	}
	
	public void maj_emballeur(String id_emballeur) {
		for(int i = 0; i< emballeurs.size(); ++i) {
			if(id_emballeur == emballeurs.get(i)) {
				emballeurs_compt.set(i, emballeurs_compt.get(i) + 1);
			}
		}
	}
	
	public String getEmballeur() {
		int min = 0;
		for(int i=1; i<emballeurs_compt.size(); ++i) {
			if(emballeurs_compt.get(i)<emballeurs_compt.get(min)) {
				min = i;
			}
		}
		return emballeurs.get(min);
		/*String e = emballeurs.get(emballeur);
		if(++emballeur>=emballeurs.size()) {
			emballeur=0;
		}
		return e;*/
	}
	
	public void generateColis() {
		
		for(int i = 0; i<commandes.size(); ++i) {
			traiter(commandes.get(i));
		}
		
		System.out.println("\nGeneration des colis terminee\n");
	}
}
