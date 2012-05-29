package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Compte {

	public static String loginRandom = "a";
	public static int indiceChar = 0;
	public static boolean premierLogin = true;
	
	//public void ajouterCompte(Statement st, int type, int id, String login, String mdp){
	public void ajouterCompte(Statement st, int type, String id, String login, String mdp){
	//public void ajouterCompte(int type, int id, String login, String mdp){
		
		StringBuffer requete = new StringBuffer("INSERT INTO "+ Constantes.base_identifiant 
				+ " VALUES(");
		
		requete.append(type + ", '" + id + "', '");
		
		if(login == null){
			requete.append(nouveauLogin());
		}
		else{
			requete.append(login);
		}
		
		requete.append("', ");
		requete.append("'" + mdp + "');");
		
		try {
			st.execute(requete.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println(requete.toString());
	}
	
	public static String nouveauLogin(){
		
		char dernierChar = loginRandom.charAt(indiceChar);
		
		if(premierLogin){
			premierLogin = false;
			return loginRandom;
		}
		
		if(dernierChar == 'z'){
			loginRandom += "a";
			indiceChar++;
		}
		else{
			String tmp = loginRandom.substring(0, indiceChar);
			tmp += (char)((int)dernierChar + 1);
			loginRandom = tmp;
		}
		
		return loginRandom;
	}
	
	public static void main(String[] args){
		
		/*for(int j=0; j<120; j++){
			System.out.println(nouveauLogin());
		}
		
		Compte c = new Compte();
		c.ajouterCompte(20, 4, null, "bidon");
		c.ajouterCompte(10, 7, "login", "mdp");*/
		
	}
	
}
