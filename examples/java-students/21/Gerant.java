import java.sql.*;

public class Gerant extends Users{
	
	public Gerant(String log,String mdp,ConnectionProjet connexion) {
		super(log,mdp,connexion);
	}
	
	public int listeChoix() {
		out.ecrire("Liste de vos possibilitées :\n","rouge",true);
		out.ecrire("1. Résumé commandes\n","bleu");
		out.ecrire("2. Changer un prix\n","bleu");
		out.ecrire("3. Licencié employé\n","bleu");
		out.ecrire("4. Liste employés\n","bleu");
		out.ecrire("5. Liste clients\n","bleu");
		out.ecrire("6. Nombre de colis traité par un emballeur\n","bleu");
		out.ecrire("7. Produits les plus vendus\n","bleu");
		out.ecrire("8. Clients les plus dépensiers\n","bleu");
		
		return lireEntier("Votre choix ?","vert");
	}
	
	public boolean gestion() throws SQLException {
		int choix = -1;
		int tmp;
		double tmp2;
		
		out.clear();
		out.ecrire("Bienvenue "+login+" ( Compte Gerant )");
		out.nLine();
		choix = listeChoix();
		
		switch (choix) {
		case 1 :
			out.clear();
			boolean b = false;
			if (lireEntier("Voulez vous les commandes refusees uniquement ? [1 pour oui et 0 pour non]\n","bleu") == 0) b = false;
			else b = true;
			int id_client = lireEntier("Si vous voulez spécifier un Id client: [Entrez l'Id ou bien 0 pour ne rien spécifier]","bleu");
			int id_colis = lireEntier("Si vous voulez spécifier un Id colis: [Entrez l'Id ou bien 0 pour ne rien spécifier]","bleu");
			int id_emb = lireEntier("Si vous voulez spécifier un Id emballeur: [Entrez l'Id ou bien 0 pour ne rien spécifier]","bleu");
			int id_comm = lireEntier("Si vous voulez spécifier un Id commande: [Entrez l'Id ou bien 0 pour ne rien spécifier]","bleu");
			out.nLine();
			out.afficheRes(conn.listeCommandes(true, false, false, false, b, ((id_client == 0) ? -1 : id_client), (id_emb==0 ? -1 : id_emb), (id_colis==0 ? -1 : id_colis), (id_comm==0 ? -1 : id_comm)));
			break;
			
		case 2 :
			out.clear();
			tmp = lireEntier("Entrez l'id d'un produit dont le prix doit changer\n","bleu");
			tmp2 = lireDouble("Entrez le nouveau prix du produit\n","bleu");
			conn.updatePrixProd(tmp, tmp2);
			out.ecrire("Mise a jour du prix terminée: nouveau prix : "+tmp2+"\n","rouge");
			break;
		case 3 : 
			out.clear();
			conn.virerEmballeur(lireEntier("Entrer l'id de l'emplyé à licencier","bleu"));
			out.ecrire("Employé " + id + " licencié !","rouge");
			break;
			
		case 4 :
			out.clear();
			out.ecrire("Liste des employes: ","bleu");
			out.nLine();
			out.afficheRes(conn.listeEmployes());
			break;
			
		case 5 :
			out.clear();
			out.ecrire("Liste des clients : ");
			out.nLine();
			out.afficheRes(conn.listeClients());
			break;
			
		case 6 :
			out.clear();
			while(true) {
				tmp = lireEntier("Id de l'emballeur dont on veut connaitre le nombre de colis/ jour","bleu");
				tmp2 = conn.emballeurColisJour(tmp);
				if (tmp2 < 0) {
					out.ecrire("Erreur: l'emballeur n'existe pas dans la bdd !\n","violet");
					continue;
				}
				else {
					out.ecrire("Eballeur d'Id: "+tmp+" traite "+tmp2+" colis par jour...","rouge");
					break;
				}
			}
			break;
			
		case 7 :
			out.clear();
			tmp = lireEntier("Combien de produit ( les plus vendus ) voulez vous afficher ? (0 pour tous)","bleu");
			if (tmp == 0) tmp = -1;
			out.nLine();
			out.afficheRes(conn.produitsVendus(tmp));
			break;
			
		case 8 :
			out.clear();
			tmp = lireEntier("Combien de clients ( les plus depensiers ) voulez vous afficher ? (0 pour tous)","bleu");
			if (tmp == 0) tmp = -1;
			out.nLine();
			out.afficheRes(conn.depensesClients(tmp));
			break;
		
		default :
			out.ecrire("Veuillez choisir parmis les choix proposés\n");
			break;
		}
		
		/*On veut savoir si on continue ou pas*/
		return fin();
	}
}