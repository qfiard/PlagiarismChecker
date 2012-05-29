package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Emballeur extends Compte{

	public String numeroEmballeur;
	public String nom;
	public String prenom;
	public int tauxErreur;
	public String mdp;
	public static String pseudo = "E0";
	
	public Emballeur(String[] champs){
		
		numeroEmballeur = champs[1];
		nom = champs[2];
		prenom = champs[3];
		tauxErreur = Integer.parseInt(champs[4]);
		mdp = champs[5];
		
	}
	
	public void insererEmballeur(Statement st){
		
		super.ajouterCompte(st, 10, numeroEmballeur, nouveauPseudo(), mdp);
		
		String requeteEmballeur = "INSERT INTO " + Constantes.base_emballeur + " VALUES(" +
								"'" + numeroEmballeur + "', " +
								//numeroEmballeur + ", " +	INT
								"'" + nom + "', " +
								"'" + prenom + "', " +
								tauxErreur + "," +
								0 +	");" ;
		
		try {
			
			//st.execute(requeteId);
			st.execute(requeteEmballeur);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static String nouveauPseudo(){
		
		String tmp = pseudo;  
		
		int numeroEmballeur = Integer.parseInt(pseudo.substring(1, pseudo.length()));
		
		pseudo = "E" + (numeroEmballeur+1);
		
		return tmp;
	}
	
}
