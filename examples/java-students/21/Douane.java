import java.sql.ResultSet;
import java.sql.SQLException;


public class Douane extends Users{

	public Douane(String log, String mdp, ConnectionProjet connexion) {
		super(log, mdp, connexion);
	}
	
	public int listeChoix() {
		out.ecrire("Liste de vos possibilitées :\n","rouge",true);
		out.ecrire("1. Refuser un colis\n","bleu");
		out.ecrire("2. Lister palettes d'un conteneur -- Non implémentée --\n","bleu");
		out.ecrire("3. Lister colis palette\n","bleu");
		out.ecrire("4. Lister produits d'un colis\n","bleu");
		out.ecrire("5. Lister les commandes contrôlées (ou non) par votre douane\n","bleu");
		out.ecrire("6. Lister toutes les commandes expédiées à votre douane\n","bleu");
		out.ecrire("7. Rechercher parmis les commandes\n","bleu");
		out.ecrire("8. Accepter un colis ou commande\n","bleu");
		out.ecrire("9. Statistiques sur les commandes\n","bleu");

		out.nLine();
		return lireEntier("Votre choix","vert");
	}

	public boolean gestion() throws SQLException {
		out.clear();
		int choix = listeChoix();
		int tmp;
		boolean b;
		String w;
		
		switch (choix) {
		case 1 : 
			out.clear();
			tmp = lireEntier("ID du colis a refuser ?\n","bleu");
			out.ecrire("Motif de refus ?\n","bleu");
			if ((w = System.console().readLine()) == null) w = "null";
			conn.refuseColis(tmp, w);
			out.clear();
			out.ecrire("Opération effectuée, colis d'id "+tmp+" est refusé (avec toutes les commandes qu'il contenait\n","rouge");
			break;
			
		case 2 :
			out.clear();
			//tmp = lireEntier("Lister palettes d'un conteneur dont on liste les palettes ? ( non implémenté )\n","bleu");
			//out.afficheRes(conn.);
			break;
			
		case 3 :
			out.clear();
			ResultSet res = conn.listePalette(this.id,lireEntier("Entrez l'Id de la palette dont vous voulez lister le contenu\n","bleu"));
			out.afficheRes(res);
			break;
		case 4 : 
			out.clear();
			out.afficheRes(conn.listeCommandes(false, true, false, false, false, -1, -1, lireEntier("Entrez l'Id du colis a lister\n","bleu"), -1));
			break;
		case 5 :
			out.clear();
			/*affiche les comme soient controlées soit non controlées mais jamais les deux ici (case 6)*/
			b = lireEntier("Souhaitez vous lister les commandes NON contrôlées ? [1 pour oui/ 0 pour non]\n","bleu")==0 ? false : true;
			out.afficheRes(conn.listeCommandes(-1,!b, b, -1, -1, this.id,null));
			break;
			
		case 6 : 
			/*Affiche toutes les comm*/
			out.clear();
			out.afficheRes(conn.listeCommandes(-1,false, false, -1, -1, this.id,null));
			break;
			
		case 7 :
			/*Rechercher parmis commandes*/
			out.clear();
			String[] adr = null;
			int id_comm = lireEntier("Entrez l'Id d'une commande pour spécifier une commande particuliere, ou 0 pour toutes ?\n","bleu");
			if (lireEntier("Voulez vous choisir les commandes en fonction d'une destination ? [1 pour oui, 0 pour non]\n","bleu") != 0) {
				/*on lit une adresse*/
				adr = new String[3];
				out.ecrire("Ville ?\n","vert");
				adr[2] = System.console().readLine();
				out.ecrire("Code postal ?\n","vert");
				adr[1] = System.console().readLine();
				out.ecrire("Adresse ?\n","vert");
				adr[0] = System.console().readLine();
			}
			int id_prod = lireEntier("Si vous voulez trier en fonction d'un produit particulier, entrez son ID, sinon 0\n","bleu");
			out.afficheRes(conn.listeCommandes((id_prod == 0 ? -1 : id_prod),false, false, -1, (id_comm == 0 ? -1 : id_comm), this.id,adr));
			break;
			
		case 8 :
			out.clear();
			int c = lireEntier("Voulez vous accepter un colis ou une commande ? [0: colis et 1: commande]\n","bleu");
			if (c == 0) {
				int id = lireEntier("Entrez l'ID d'un colis à accepter\n","bleu");
				conn.controlerColis(id);
				out.ecrire("Colis d'ID "+id+" accepté.\n","rouge");
			}
			else {
				int id = lireEntier("Entrez l'ID d'une commande à accepter\n","bleu");
				conn.controlerCommande(id);
				out.ecrire("Commande d'ID "+id+" accepté.\n","rouge");
			}
			break;
			
		case 9 : 
			
			/*on affiche les statistiques*/
			out.clear();
			int tab[] = conn.statsDouane(lireEntier("Entrez l'ID d'un produit sur lequel vous voulez des statistiques.\n","bleu"));
			if (tab == null || tab.length != 6) {
				out.ecrire("Aucun résultats !\n","violet");
			}
			out.ecrire("Nombre de colis controlés: "+tab[0],"rouge");
			out.nLine();
			out.ecrire("Nombre de colis non controlés: "+tab[1]);
			out.nLine();
			out.ecrire("Nombre de commande controlés: "+tab[2],"rouge");
			out.nLine();
			out.ecrire("Nombre de commande non controlés: "+tab[3],"rouge");
			out.nLine();
			out.ecrire("Nombre de colis refusés: "+tab[4],"rouge");
			out.nLine();
			out.ecrire("Nombre de commandes refusées: "+tab[5],"rouge");
			out.nLine();
			break;
			
		default :
			out.ecrire("Veuillez choisir parmis les choix proposés\n");
			break;
		}
		
		/*On veut savoir si on continue ou pas*/
		return fin();
	}
}
