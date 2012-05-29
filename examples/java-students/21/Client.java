import java.sql.SQLException;


public class Client extends Users {
	
	public Client(String log, String mdp, ConnectionProjet p) {
		super(log,mdp,p);
	}

	public boolean gestion() throws SQLException {
		out.clear();
		
		int choix = listeChoix();
		
		switch (choix) {
		case 1 :
			out.clear();
			out.afficheRes(conn.infoColis(lireEntier("Entrez l'Id du colis dont vous voulez les infos\n","vert"), this.id));
			break;
		case 2 : 
			out.clear();
			out.afficheRes(conn.listeProduits(false));
			break;
		case 3 :
			out.clear();
			conn.setDateLivraison(lireEntier("Entrez l'Id d'une commande","vert"), lireDate());
			break;
		case 4 :
			String login,mdp;
			do {
				out.ecrire("Verifions vos identifiants actuels, Entrez votre login: \n","vert");
				if (!this.login.equals(System.console().readLine())) {
					out.ecrire("Erreur, votre login ne correspond pas !\n","violet");
					break;
				}
				out.ecrire("Votre mot de passe actuel ?");
				if (!this.mdp.equals(System.console().readLine())) {
					out.ecrire("Erreur, votre mot de passe ne correspond pas !\n","violet");
					break;
				}
				out.ecrire("Entrez votre nouveau login\n","vert");
				login = System.console().readLine();
				out.ecrire("Entrez votre nouveau mot de passe\n","vert");
				mdp = new String(System.console().readPassword());
				
			} while (conn.changeIdentifiants(this.id, mdp, login) != 0);
			break;
			
		case 5 :
			int idProd = lireEntier("Entrez l'ID du produit que vous voulez commander\n","vert");
			int quantite = lireEntier("Entrez la quantité voulu\n","vert");
			out.ecrire("Date de livraison\n","bleu");
			String date = lireDate();
			if (conn.creerCommande(this.id,idProd ,quantite , date) != 0) {
				out.ecrire("Pas assez de produits disponibles","violet");
				break;
			}
			out.ecrire("Commande passée !\n","rouge");
			break;
			
		default : 
			out.ecrire("Veuillez choisir parmis les choix proposés\n");
			break;	
		}
		return fin();
	}

	public int listeChoix() {
		out.ecrire("Liste de vos possibilitées :\n","rouge",true);
		out.ecrire("1. Obtenir des informations à propos d'un colis\n","bleu");
		out.ecrire("2. Lister les produits disponibles\n","bleu");
		out.ecrire("3. Choisir une date de livraison\n","bleu");
		out.ecrire("4. Changer login et mot de passe\n","bleu");
		out.ecrire("5. Creer une commande\n","bleu");
		return lireEntier("Votre choix ?\n","vert");
	}
}
