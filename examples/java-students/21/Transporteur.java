import java.sql.SQLException;



public class Transporteur extends Users{

	public Transporteur (String login, String mdp, ConnectionProjet conn) {
		super(login, mdp, conn);
	}
	
	public int listeChoix() {
		out.ecrire("Liste de vos choix\n","rouge",true);
		out.ecrire("1. Obtenir informations sur un colis", "bleu");
		
		return lireEntier("Votre choix ?\n","bleu");
	}
	
	public boolean gestion() throws SQLException {
		out.clear();
		int choix = listeChoix();
		
		switch (choix) {
		case 1 :
			out.afficheRes(conn.listeCommandes(false, false, true, false, false, -1, -1, lireEntier("Entrez l'Id du colis à chercher\n","bleu"), -1));
			break;
		
		default :
			out.ecrire("Veuillez choisir parmis les choix proposés\n");
			break;
		}
		return fin();
	}
	
	
}
