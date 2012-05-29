package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Transporteur extends Compte{
	
	public String codeSCAC;
	public String nomTransporteur;
	public String mdp;
	
	public Transporteur(String[] champs){
		
		codeSCAC = champs[1];
		nomTransporteur = champs[2];
		mdp = champs[3];
		
	}
	
	public void insererTransporteur(Statement st){

		/*String requeteId = "INSERT INTO " + Constantes.base_identifiant + " VALUES(20, " +
							numeroDouane + ", " +
							"'" + login + "', " +
							"'" + mdp + "');";*/

		super.ajouterCompte(st, 40, codeSCAC, nomTransporteur, mdp);

		String requeteTransporteur = "INSERT INTO " + Constantes.base_transporteur + " VALUES(" +
				"'" + codeSCAC + "', " +
				//codeSCAC + ", " +	INT
				"'" + nomTransporteur + "');";		

		try {

			//st.execute(requeteId);
			st.execute(requeteTransporteur);

		} catch (SQLException e) {
			e.printStackTrace();
		}					

	}

}
