package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Gerant extends Compte{

	public String prenom;
	public String nom;
	public String login;
	public String mdp;

	public Gerant(String[] champs){

		prenom = champs[1];
		nom = champs[2];
		login = champs[3];
		mdp = champs[4];

	}

	public void insererGerant(Statement st){

		/*String requeteId = "INSERT INTO " + Constantes.base_identifiant + " VALUES(20, " +
							numeroDouane + ", " +
							"'" + login + "', " +
							"'" + mdp + "');";*/

		super.ajouterCompte(st, 60, "1", login, mdp);

		String requeteGerant = "INSERT INTO " + Constantes.base_gerant + " VALUES(" +
				"'1', " +
				//"1, " +	INT
				"'" + nom + "', " +
				"'" + prenom + "');";		

		try {

			//st.execute(requeteId);
			st.execute(requeteGerant);

		} catch (SQLException e) {
			e.printStackTrace();
		}					

	}
}
