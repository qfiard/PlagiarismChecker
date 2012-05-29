package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Client extends Compte{	
	
	public String numeroClient;
	public String nomSociete;
	public String suffixeSociete;
	public String adresse;
	public String ville;
	//public int codePostal;
	public String codePostal;
	public String pays;
	public String telephone;
	public String mdp;
	
	public Client(String[] champs){
		
		numeroClient = champs[1];
		nomSociete = champs[2];
		suffixeSociete = champs[3];
		adresse = champs[4];
		ville = champs[5];
		codePostal = champs[6];
		pays = champs[7];
		telephone = champs[8];
		mdp = champs[9];
		
	}
	
	public void insererClient(Statement st){
		
		/*String requeteId = "INSERT INTO " + Constantes.base_identifiant + " VALUES(20, " +
							numeroClient + ", " +
							"NULL, " +
							"'" + mdp + "');";*/
		
		super.ajouterCompte(st, 20, numeroClient, null, mdp);
		
		String requeteClient = "INSERT INTO " + Constantes.base_client + " VALUES(" +
								"'" + numeroClient + "', " +
								//numeroClient + ", " +	INT
								"'" + adresse + "', " +
								"'" + ville + "', " +
								"'" + codePostal + "', " +
								//codePostal + ", " +	INT
								"'" + pays + "', " +
								"'" + telephone + "', " +
								"'" + suffixeSociete + "', " +
								"'" + nomSociete + "');";
		
		try {
			
			//st.execute(requeteId);
			st.execute(requeteClient);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}	

}
