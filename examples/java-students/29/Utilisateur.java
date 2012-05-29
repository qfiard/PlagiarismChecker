import java.sql.*;
import java.util.ArrayList;

public abstract class Utilisateur {
	protected BDD bdd = null;
	protected ArrayList<String> menu;
	private String login = null;

	public Utilisateur(BDD base) {
		this.bdd = base;
		getMenu();
	}

	public abstract boolean identifier(String login, String motPasse) throws SQLException;

	protected boolean idRequest(String login, String motPasse, String table, String champs) throws SQLException {
		ResultSet rs = null;
		int nb = 0;
		String query = "SELECT "+champs+" AS nom FROM "+table+" WHERE login='"+login+"' AND mot_passe='"+motPasse+"'";
		rs = bdd.getSelect(query);
		while (rs.next()) {
			nb++;
			String nom = rs.getString("nom");
			afficher("Identification réussie pour : "+nom);
			if (1==nb) {this.login=login;}
		}
		return (1==nb);
	}

	public abstract ArrayList<String> getMenu();

	public void afficherMenuTerminal() {
		afficherLigne();
		afficherLigne();
		afficher(menu);
		afficherLigne();
		afficherLigne();
	}

	protected void afficher(ArrayList<String> l) {
		for (int i=0; i<l.size() ; i++) {
			System.out.println(l.get(i));
		}		
	}

	protected void afficher(String message) {
		System.out.println(message);
	}

	public abstract boolean traiterChoix(int choix) throws SQLException;

	public final void fermer() throws SQLException {
		bdd.close();
	}

	public final void showLogin() {
		afficher(this.login);
	}

	protected final void afficherLigne() {
		afficher("-------------------------------------------------------------------");
	}
}
