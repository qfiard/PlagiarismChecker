import java.sql.SQLException;


public class Emballeur extends Users{

	public Emballeur(String log, String mdp, ConnectionProjet p) {
		super(log,mdp,p);
	}

	public boolean gestion() throws SQLException {
		out.clear();
		int choix = listeChoix();
		
		switch(choix) {
		
		case 1 :
			out.clear();
			out.afficheRes(conn.listeCommandes(false, false, false, true, false, lireEntier("Entrez l'id d'un client\n","vert"), this.id, -1, -1));
			break;
			
		case 2 : 
			conn.setDateEmballage(lireEntier("ID du colis à dater\n","bleu"), lireDate());
			out.ecrire("Colis daté.\n","rouge");
			break;
			
		case 3 : 
			int nbComm = -1;
			int[] tabIdC;
			
			while (nbComm<=0) nbComm = lireEntier("Combien de commandes contient ce colis ?\n","bleu");
			tabIdC = new int[nbComm];
			
			for (int i=0;i<nbComm;i++) {
				tabIdC[i] = lireEntier("Entrez l'ID de la commande "+(i+1)+"\n","bleu");
			}
			conn.creerColis(this.id, tabIdC);
			out.ecrire("Colis enregistrer.\n","rouge");
			break;
			
		case 4 :
			int nbColis = -1;
			int[] tabId;
			while (nbColis<=0) nbColis = lireEntier("Combien de colis contient cette palette ?\n","bleu");
			tabId = new int[nbColis];
			
			for (int i=0;i<nbColis;i++) {
				tabId[i] = lireEntier("Entrez l'ID du colis "+(i+1)+"\n","bleu");
			}
			conn.creerPalette(this.id, tabId);
			out.ecrire("Palette enregistrée.\n","rouge");
			break;
			
		case 5:
			out.afficheRes(conn.listeCommandes(false, false, false, true, false, -1, -1, -1, -1));
			break;
			
		default :
			out.ecrire("Veuillez choisir parmis les choix proposés\n");
			break;
		}
		return fin();
	}

	public int listeChoix() {
		out.ecrire("Liste de vos possibilitées\n","rouge",true);
		out.ecrire("1. Lister les commandes d'un client\n","bleu");
		out.ecrire("2. Noter date emballage\n","bleu");
		out.ecrire("3. Enregistrer colis\n","bleu");
		out.ecrire("4. Enregistrer palette\n","bleu");
		out.ecrire("5. Lister commandes\n","bleu");
		
		return lireEntier("Votre choix ?\n","vert");
	}
	
	
}
