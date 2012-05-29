package commandes;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import donnees.ConnexionBD;
import donnees.Constantes;

public class Palette {

	private String destination = null;
	private int capacite = 10;

	private LinkedList<Colis> colis = new LinkedList<Colis>();

	private int idPalette;

	public ConnexionBD conn;

	public Palette(int idPalette){
		this.idPalette = idPalette;
		conn = GenerateurAleatoire.conn;
	}
	
	public Palette(int idPalette, String destination){
		this(idPalette);
		this.destination = destination;
	}

	public boolean estPleine(){
		return capacite == 0;
	}

	public boolean estVide(){
		return capacite == 10;
	}

	public String getDestination(){
		return destination;
	}	

	public int getIdPalette(){
		return idPalette;
	}

	public boolean peutAjouter(Colis c){

		if(c.getStatut() == Commande.NON_EXPEDIEE){
			return false;
		}
		
		if(destination == null){
			return true;
		}

		if(estPleine()){
			return false;
		}		

		return destination.equals(c.getDestination());
	}

	public void ajouterColis(Colis c){

		if(!peutAjouter(c)){
			return;
		}

		if(destination == null){
			destination = c.getDestination();
		}

		colis.add(c);
		capacite--;
	}

	public String toString(){
		StringBuffer str = new StringBuffer("ID [Palette]: " + idPalette +
				"(" + destination +")\n");

		for(Colis c: colis){
			str.append("\t" + c.toString() + "\n");
		}

		return str.toString();
	}

	public void insererPalette(String date){

		Statement st = conn.createStatement();
		
		try {

			for(Colis c: colis){

				st.execute("INSERT INTO " + Constantes.base_palette + " VALUES(" +
						idPalette + ", " +
						c.getIdColis() + ", " +
						date + ", " +
						"'" + destination + "'" +
 						");");				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
