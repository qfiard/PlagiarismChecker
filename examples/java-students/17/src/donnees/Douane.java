package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Douane extends Compte{

	public String pays;
	public int tauxVerifie;
	public String login;
	public String mdp;
	
	public static String numeroDouane = "1";
	
	public Douane(String[] champs){
		
		pays = champs[1];
		tauxVerifie = Integer.parseInt(champs[2]);
		login = champs[3];
		mdp = champs[4];
		
	}
	
	public void insererDouane(Statement st){
		
		/*String requeteId = "INSERT INTO " + Constantes.base_identifiant + " VALUES(20, " +
							numeroDouane + ", " +
							"'" + login + "', " +
							"'" + mdp + "');";*/
		
		super.ajouterCompte(st, 50, numeroDouane, login, mdp);
		
		String requeteDouane = "INSERT INTO " + Constantes.base_douane + " VALUES(" +
								numeroDouane + ", " +
								tauxVerifie + ", " +
								"'" + pays + "');";		
		
		try {
			
			//st.execute(requeteId);
			st.execute(requeteDouane);
			updateNumeroDouane();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		//numeroDouane++;				
		
	}
	
	
	public void updateNumeroDouane(){
		numeroDouane = String.valueOf(Integer.parseInt(numeroDouane)+1);
	}
	
}
