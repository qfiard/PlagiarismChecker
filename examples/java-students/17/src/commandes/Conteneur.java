package commandes;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import donnees.ConnexionBD;
import donnees.Constantes;

public class Conteneur {

	private String destination = null;

	private int idConteneur;

	LinkedList<Palette> palettes = new LinkedList<Palette>();

	private int capacite = 100;
	
	public ConnexionBD conn;

	public Conteneur(int idConteneur){
		this.idConteneur = idConteneur;
		
		conn = GenerateurAleatoire.conn;
	}

	public boolean peutAjouter(Palette p){
		if(destination == null){
			return true;
		}

		return destination.equals(p.getDestination());
	}

	public void ajouterPalette(Palette p){	

		if(!peutAjouter(p)){
			return;
		}

		if(destination == null){
			destination = p.getDestination();
		}

		palettes.add(p);
	}

	public boolean estPlein(){
		return capacite == 0;
	}

	public String toString(){
		StringBuffer str = new StringBuffer("ID [Conteneur]: " + idConteneur +
				"(" + destination + ")\n");

		for(Palette p: palettes){
			str.append("\t" + p.toString() + "\n");
		}

		return str.toString();
	}

	public void insererConteneur(String idTransporteur){
		
		try {

			Random r = new Random();
			String moyenTransport;
			
			String date = "current_date-interval '" + (r.nextInt(3)+1) + " day'";

			for(Palette p: palettes){
				
				p.insererPalette(date);

				if(r.nextInt(2) == 0){
					moyenTransport = "avion";
				}
				else{
					moyenTransport = "camion";
				}
				
				//ConnexionBD conn = new ConnexionBD();
				Statement st = conn.createStatement();

				st.execute("INSERT INTO " + Constantes.base_conteneur + " VALUES(" +
						"'" + moyenTransport + "', " +
						p.getIdPalette() + ", " +
						idConteneur + ", " +
						"'" + idTransporteur + "', " +
						"'" + destination + "'" +
						");");
				
				st.close();
				//conn.close();
			}				

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
