import java.sql.Date;


public class Palette {
	String ID_palette;
	String ID_commande;
	String destination;
	String qualifiant;
	Date date;
	
	public Palette(String ID_palette, String qualifiant){
		this.ID_palette = ID_palette;
		this.qualifiant = qualifiant;
	}
	
}

