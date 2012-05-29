import java.sql.SQLException;

public class Gerant {
	SQL connecte;
	String login;
	FonctionsCommunes fonc;

	public Gerant(String log) {
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

	public int printMenu() throws ClassNotFoundException, SQLException {
		int c = -1; // le choix de l'utilisateur
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Quitter");
		System.out.println("1 - Changer prix produit");
		System.out.println("2 - Liste employes");
		System.out.println("3 - Embaucher personnel");
		System.out.println("4 - Licencier personnel");
		System.out.println("5 - Nombre de commandes traitees par emballeur");
		System.out.println("6 - Produits les plus vendus");
		System.out.println("7 - Liste clients");
		System.out.println("8 - Clients les plus depensiers");
		System.out.println("9 - Modification du stock");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = fonc.readInt();
		Tables t = new Tables(this.connecte);
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------
		try {
			switch(c){
			case 1 : 
				int cmp = 0;
				while(true && cmp < 3){
					fonc.afficheResultat(connecte.clientListerProduits());
					System.out.println("Quel est l'ID du produit dont vous souhaitez modifier le prix ?");
					String ID_produit = fonc.readString();
					if(connecte.verifIDProduit(ID_produit)){
						System.out.println("Quel est le nouveau prix de ce produit ?");
						int prix = fonc.readInt();
						connecte.gerantModifierPrix(ID_produit,prix);
						break;
					}
					else{
						System.out.println("Erreur ID_produit");
						cmp++;
					}
				}
				cmp = 0;
				break;

			case 2 :
				int cmp2 = 0;
				while(true && cmp2 < 3){
					System.out.println("Souhaitez-vous consulter la liste des emballeurs (1) ou des transporteurs (2) ?");
					int rep = fonc.readInt();

					if(rep == 1){
						fonc.afficheResultat(connecte.gerantConsulterEmballeur());
						break;
					}
					else if(rep == 2){
						fonc.afficheResultat(connecte.gerantConsulterTransporteur());
						break;
					}
					else{
						System.out.println("Mauvaise utilisation");
						cmp2++;
					}
				}
				cmp2 = 0;
				break;

			case 3 :
				int cmp3 = 0;
				while(true && cmp3 < 3){
					System.out.println("Souhaitez-vous embaucher un emballeur (1) ou un transporteur (2) ?");
					int rep = fonc.readInt();

					if(rep == 1){
						String ID_emballeur = fonc.randomIDEmballeur();
						System.out.println("Entrez son nom");
						String nom_emballeur = fonc.readString().toUpperCase();
						System.out.println("Entrez son prenom");
						String prenom_emballeur = fonc.readString().toUpperCase();
						String mdp_emballeur = t.randomID(11);

						connecte.gerantEmbaucherEmballeur(ID_emballeur,nom_emballeur,prenom_emballeur, 0 ,mdp_emballeur, "0");

						System.out.println("ID: "+ID_emballeur);
						System.out.println("Mot de passe: "+mdp_emballeur);
						break;
					}
					if(rep == 2){
						String ID_transporteur = fonc.randomIDTransporteur();
						System.out.println("Entrez son nom");
						String nom_transporteur = fonc.readString().toUpperCase();
						String mdp_transporteur = t.randomID(11);

						connecte.gerantEmbaucherTransporteur(ID_transporteur,nom_transporteur,mdp_transporteur, "");
						System.out.println("ID: "+ID_transporteur);
						System.out.println("Mot de passe: "+mdp_transporteur);
						break;
					}
					else{
						System.out.println("Mauvaise utilisation");
						cmp3++;
					}
				}
				cmp3 = 0;
				break;

			case 4 :
				int cmp4 = 0;
				boolean b = true;
				
				while(true && cmp4 < 3){
					System.out.println("Souhaitez-vous licencier un emballeur (1) ou un transporteur (2) ?");
					int rep = fonc.readInt();

					if(rep == 1){
						cmp4 = 0;
						fonc.afficheResultat(connecte.gerantConsulterEmballeur());
						System.out.println("Quel est l'ID de l'emballeur que vous souhaitez licencier ?");
						String ID_emballeur = fonc.readString().toUpperCase();
						if(connecte.verifIdEmballeur(ID_emballeur)){
							connecte.gerantReattribuerEmballeur(connecte.IDEmballeurSauf(ID_emballeur), ID_emballeur);
							connecte.gerantLicencierEmballeur(ID_emballeur);
							break;
						}
						else{
							System.out.println("ID emballeur inexistant");
							cmp4++;
						}
					}
					else if(rep == 2){
						cmp4 = 0;
						fonc.afficheResultat(connecte.gerantConsulterTransporteur());
						System.out.println("Quel est l'ID du transporteur que vous souhaitez licencier ?");
						String ID_transporteur = fonc.readString().toUpperCase();
						if(connecte.verifIdTransporteur(ID_transporteur)){
							connecte.gerantReattribuerTransporteur(connecte.IDtransporteurSauf(ID_transporteur), ID_transporteur);
							connecte.gerantLicencierTransporteur(ID_transporteur);
							break;
						}
						else{
							System.out.println("ID transporteur inexistant");
							cmp4++;
						}
					}
					else{
						System.out.println("Mauvaise utilisation");
						cmp4++;
					}

				}
				cmp4 = 0;
				break;

			case 5 :
				System.out.println("Nombre de commandes traitees par emballeur");
				fonc.afficheResultat(connecte.gerantNombreCommande());
				break;

			case 6 :	
				System.out.println("Les produits les plus vendus");
				fonc.afficheResultat(connecte.gerantProduitsPlusVendus());
				break;	

			case 7 :
				fonc.afficheResultat(connecte.gerantConsulterClient());
				break;

			case 8 :	
				System.out.println("Les clients les plus depensiers: ");
				fonc.afficheResultat(connecte.gerantClientPlusDepensier());
				break;

			case 9:
				fonc.affProduit(false);
				int cmp5 = 0;
				boolean sortir = false;
				while(!sortir && cmp5 < 3){
					System.out.print("ID du produit dont vous souhaitez modifier le stock ? ");
					String ID_produit = fonc.readString();
					if(connecte.verifIDProduit(ID_produit)){
						cmp5 = 0;
						System.out.print("Nouveau stock du produit: ");
						int stock = fonc.readInt();
						connecte.gerantModifierStock(ID_produit,stock);
						while(true && cmp5 < 3){
							System.out.print("Voulez vous modifier le stock d'un autre produit? (O/N) ");
							String rep = fonc.readString().toUpperCase();
							if(rep.equals("N")){
								sortir = true;
								break;
							}
							else if(rep.equals("O"))
								break;
							else
								System.out.println("Erreur, veuillez entrer O pour Oui ou N pour Non");
						}
					}
					else{
						System.out.println("Erreur ID_produit");
						cmp5++;
					}
				}
				cmp5 = 0;
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
