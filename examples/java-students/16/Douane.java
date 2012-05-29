import java.sql.SQLException;

public class Douane {
	SQL connecte;
	String login;
	FonctionsCommunes fonc;

	public Douane(String log) {
		this.login = log;

		try {
			this.connecte = new SQL();
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
		System.out.println("1 - Lister les commandes");
		System.out.println("2 - Inscrire le numero d'une commande refusee");
		System.out.println("3 - Lister les produits d'une commande");
		System.out.println("4 - Lister les cartons d'une palette");
		System.out.println("5 - Lister les palettes d'une commande");
		System.out.println("6 - Prix des produits transportes");
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
			String ID_commande;
			String pays = connecte.recupPaysDouane(login);
			switch(c){
			
			case 1:
				fonc.afficheResultat(connecte.douaneListerCommandeExpedie(login));
				break;

			case 2 : 
				int cmp = 0;
				while(true && cmp < 3){
					fonc.afficheResultat(connecte.douaneListerCommandeExpedie2(login));
					System.out.println("Entrer l'ID de la commande refuse");
					ID_commande = fonc.readString();
					if(connecte.verifIdCommandeRefuse(ID_commande)){
						connecte.douaneRefuserCommande(ID_commande);
						break;
					}
					else{
						System.out.println("ID_commande incorrect");
						cmp++;
					}

				}
				cmp = 0;
				break;

			case 3 :
				int cmp2 = 0;
				while(true && cmp2 < 3){
					fonc.afficheResultat(connecte.douaneListerCommandeExpedie2(login));
					System.out.println("Entrer l'ID de la commande dont vous souhaitez consulter les produits");
					ID_commande = fonc.readString();
					if(connecte.verifIdCommandeRefuse(ID_commande)){
						fonc.afficheResultat(connecte.douaneListerProduitInCommande(ID_commande));
						break;
					}
					else{
						System.out.println("ID_commande incorrect");
						cmp2++;
					}
				}
				cmp2 = 0;
				break;

			case 4 :
				int cmp3 = 0;
				while(true && cmp3 < 3){
					fonc.afficheResultat(connecte.douaneListerPalette(pays));
					System.out.println("Entrer l'ID de la palette dont vous souhaitez consulter les cartons");
					String ID_palette = fonc.readString();
					if(connecte.verifIdPalette(ID_palette)){
						fonc.afficheResultat(connecte.douaneListerCartonInPalette(ID_palette, pays));
						break;
					}
					else{
						System.out.println("ID de palette incorrect");
						cmp3++;
					}
				}
				cmp3 = 0;
				break;

			case 5 :
				int cmp4 = 0;
				while(true && cmp4 < 3){
					fonc.afficheResultat(connecte.douaneListerCommande(pays));
					System.out.println("Entrer l'ID de la commande dont vous souhaitez consulter les palettes");
					ID_commande = fonc.readString();
					if(connecte.verifIdCommande(ID_commande)){
						fonc.afficheResultat(connecte.douaneListerPaletteInCommande(ID_commande, pays));
						break;	
					}
					else{
						System.out.println("ID de commande incorrect");
						cmp4++;
					}
				}
				cmp4 = 0;
				break;

			case 6 :
				int cmp5 = 0;
				boolean sortir = false;
				while(sortir == false && cmp5 < 3){
					System.out.println("Souhaitez-vous consulter le prix total des produits transportes dans une commande (1) ou dans une palette (2) ?");
					String sc = fonc.readString();
					if (sc.charAt(0) == '1'){
						fonc.afficheResultat(connecte.douaneListerCommande(pays));
						System.out.println("Entrez l'ID de la commande");
						ID_commande = fonc.readString();
						if(connecte.verifIdCommande(ID_commande)){
							fonc.afficheResultat(connecte.douanePrixCommande(ID_commande));
							sortir = true;
							break;
						}
					}
					else if (sc.charAt(0) == '2'){
						fonc.afficheResultat(connecte.douaneListerPalette(pays));
						System.out.println("Entrez l'ID de la palette");
						String ID_palette = fonc.readString();
						if(connecte.verifIdPalette(ID_palette)){
							fonc.afficheResultat(connecte.douanePrixPalette(ID_palette));
							sortir = true;
							break;
						}
					}
					else{
						System.out.println("Mauvaise utilisation");
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
