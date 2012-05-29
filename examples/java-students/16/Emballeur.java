import java.sql.*;

public class Emballeur {
	SQL connecte;
	String login;
	FonctionsCommunes fonc;

	public Emballeur(String log) {
		this.login = log;
		try {
			connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int printMenu() throws SQLException, ClassNotFoundException {
		int c = -1; // le choix de l'utilisateur
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Quitter");
		System.out.println("1 - Obtenir la liste des commandes a preparer");
		System.out.println("2 - Obtenir le contenu de la commande a preparer");
		System.out.println("3 - Entrer les commandes et les palettes conditionnes");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = fonc.readInt();

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------
		try {
			switch(c){

			case 1 :
				System.out.println("Commandes a preparer aujourd'hui :");
				fonc.afficheResultat(connecte.emballeurListerCommande(login, fonc.dateToday()));
				break;
				
			case 2 : 
				while(true){
					fonc.afficheResultat(connecte.emballeurListerCommande(login, fonc.dateToday()));
					System.out.println("ID de la commande a preparer :");
					String ID_commande = fonc.readString();
					if(connecte.verifIdCommande(ID_commande)){
						fonc.afficheResultat(connecte.emballeurPreparerCommande(ID_commande));
						break;
					}
					else
						System.out.println("ID_commande incorrect");
				}
				break;

			case 3 :
				int cmp = 0;
				boolean sortir = false;
				while(!sortir && cmp < 3){
					fonc.afficheResultat(connecte.emballeurListerCommande(login, fonc.dateToday()));
					System.out.println("ID de la commande a valider ?");
					String ID_commande = fonc.readString();
					if(connecte.verifIdCommande(ID_commande)){
						connecte.emballeurValiderCommande(ID_commande);
						sortir = true;
					}
					else{
						System.out.println("Erreur : l'ID de la commande entre est incorrect");
						cmp++;
					}
				}
				cmp = 0;
				break;

			case 0 : 
				System.out.println("FIN");
				break;

			default : 
				System.out.println("ERREUR!");
			}
		}

		catch (SQLException  e) {
			System.err.println(e.getMessage());
		}
		return c;
	}
}
