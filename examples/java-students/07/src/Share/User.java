package Share;

import java.sql.SQLException;
import java.util.ArrayList;

import db.*;

public class User {
	private String id;
	private String prestige;
	private String login, password;
	/** Permet de faire les requêtes sql */
	public Query q;
    /** Niveau de l'interface où on se trouve (0 pour le début) */
    public int level = 0;
    /** 
     * Historique des anciens choix, on ne peut arriver à ArrayIndexOutOfBoundsException 
     * puisque si on arrive à la fin level = 0 et donc on ne peut plus retourner
     * 
     */
    public ArrayList<String> history = new ArrayList<String>();
	
	/**
	 * Déclare l'identifiant et le prestige uniquement si le login et le mot de passe sont valides 
	 * sinon déclare une exception 
	 * @param login
	 * @param password
	 * @throws IdentificationException Si le mot de passe est incorrecte ou le login introuvable
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws FiredException 
	 */
	public User(String login, String password) throws IdentificationException, SQLException, ClassNotFoundException, FiredException {
		q = new Query("evrard", "BEDE2011");
		CoupleString c = q.lookUser(login, password);
		if (c.getA().equals("-2")) 
			throw new FiredException();
		if (c.getA().equals("-1"))
			throw new IdentificationException();
		this.id = c.getA();
		this.prestige = c.getB();
		this.login = null; 
		this.password = null;
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	
	/**
	 * 0 Gérant
	 * 1 client
	 * 2 emballeur
	 * 3 douane 
	 * 4 transporteur
	 * @return le statut du compte
	 */
	public String getPrestige() {
		return prestige;
	}
	public String getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}
	
	/**
	 * N'enregistre le login seulement si l'utilisateur est un Client
	 * @param login à enregistrer
	 */
	public void setLogin(String login) {
		if (getPrestige().equals("1")) {
			this.login = login;
		}
	}
	public String getPassword() {
		return password;
	}

	/**
	 * N'enregistre le password seulement si l'utilisateur est un Client
	 * @param password
	 */
	public void setPassword(String password) {
		if (getPrestige().equals("1")) {
			this.password = password;
		}
	}
	

}