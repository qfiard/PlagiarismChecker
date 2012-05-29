import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class GenerationControles {
	private Statement sql = null;
	private ArrayList<Integer> commandes_exp;
	private ArrayList<Integer> commandes_nexp;
	private ArrayList<String> douane;
	
	public GenerationControles(Statement sql) {
		this.sql = sql;
		
		commandes_exp = new ArrayList<Integer>();
		ResultSet rs = null;
		try {
			rs = sql.executeQuery( "select id_commande from commande where etat='EXP';");
			while(rs.next())
			{
				commandes_exp.add(rs.getInt("id_commande"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		commandes_nexp = new ArrayList<Integer>();
		rs = null;
		try {
			rs = sql.executeQuery( "select id_commande from commande where etat='NEXP';");
			while(rs.next())
			{
				commandes_nexp.add(rs.getInt("id_commande"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		douane = new ArrayList<String>();
		rs = null;
		try {
			rs = sql.executeQuery( "select pays from douane;");
			while(rs.next())
			{
				douane.add( (rs.getString("pays")).trim() );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String getPaysClient(int commande) {
		String pays = "";
		ResultSet rs;
		try {
			rs = sql.executeQuery("select pays from client natural join commande where id_commande='"+commande+"';");
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
	
	public void controler(String douane) {
		for(int i = 0; i<commandes_exp.size(); ++i) {
			if(getPaysClient(commandes_exp.get(i)).equals(douane)) {
				valider(douane, commandes_exp.get(i));
			}
		}
		
		for(int i = 0; i<commandes_nexp.size(); ++i) {
			if(getPaysClient(commandes_nexp.get(i)).equals(douane)) {
				refuser(douane, commandes_nexp.get(i));
			}
		}
	}
	
	public void generateControles() {
		for(int i = 0; i<douane.size(); ++i) {
			controler(douane.get(i));
		}
		
		System.out.println("\nGeneration des controles douaniers terminee\n");
	}
	
	public void refuser(String douane, int commande) {
		String commentaire ="Cette commande a ete controlee par la douane de "+douane+ ". Elle a ete refusee.";
		refuser(douane, commande, commentaire);
	}
	
	public void valider(String douane, int commande) {
		String commentaire ="Cette commande a ete controlee par la douane de "+douane+ ". Elle a ete acceptee.";
		valider(douane, commande, commentaire);
	}

	public void refuser(String douane, int commande, String commentaire) {
		String date = getDateControle();
		String valide = "f";
		String req = "insert into controle_douane (date_controle, valide, commentaire, pays, id_commande)"+
				"values ('"+date+"', '"+valide+"', '"+commentaire+"', '"+douane+"', '"+commande+"');";
		//System.out.println(req);
		try {
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
	}

	public void valider(String douane, int commande, String commentaire) {
		String date = getDateControle();
		String valide = "t";

		String req = "insert into controle_douane (date_controle, valide, commentaire, pays, id_commande)"+
				"values ('"+date+"', '"+valide+"', '"+commentaire+"', '"+douane+"', '"+commande+"');";
		//System.out.println(req);
		try {
			sql.executeQuery(req);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
	}
	
	public String getDateControle() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c1 = Calendar.getInstance();
		return sdf.format(c1.getTime());
	}
}
