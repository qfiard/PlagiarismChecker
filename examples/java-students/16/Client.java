import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
	SQL connecte;
	String ID_client;
	String login;
	FonctionsCommunes fonc;

	public Client(String log, String mdp) {

		try {
			connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);
			this.login = log;
			this.ID_client = recupID(connecte.clientRecupID(login,mdp));
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
		System.out.println("1 - Lister les produits disponibles");
		System.out.println("2 - Passer une commande");
		System.out.println("3 - Ou en sont mes commandes ?");
		System.out.println("4 - Modifier une date de livraison");
		System.out.println("5 - Changer de login");
		System.out.println("6 - Changer de mot de passe");
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
				System.out.println("Nous allons afficher les produits en reserve");
				fonc.afficheResultat(connecte.clientListerProduits());
				break;
			
			case 2:
				String reponse = "";
				int cmp = 0;
				boolean b = false , bb = false, bbb = false;
				fonc.affProduit(true);
				int i = 0;
				String[][] commande = new String[6][3];
				String ID_produit = "";
				int quantite = -1;
				b = false;

				while(!b && cmp < 3){
					System.out.print("ID du produit n " + (i+1) + " que vous souhaitez commander : ");
					ID_produit = fonc.readString();
					ID_produit = ID_produit.toUpperCase();

					if(!connecte.verifIDProduit(ID_produit)){
						System.out.println("Erreur : ID de produit errone");
						cmp++;
					}

					else{
						cmp = 0;
						bb = false;
						commande[i][0] = ID_produit;
						while(!bb && cmp < 3){
							System.out.print("Quantite voulue : ");
							quantite = fonc.readInt();

							if(!connecte.verif_quantite(ID_produit,quantite) || quantite < 1){
								System.out.println("Erreur : Quantite impossible");
								cmp++;
							}
							else{
								bb = true;
								commande[i][1] = (new Integer(quantite)).toString();
								reponse ="";
								commande[i][2] = connecte.recupQualifiant(ID_produit);
								bbb = false;

								while(!bbb){
									System.out.print("Ajouter un nouveau produit a la commande ? (O/N) ");
									reponse = fonc.readString();
									reponse = reponse.toUpperCase();

									if(reponse.equals("N")){
										b = true;
										bbb = true;
									}
									else if(reponse.equals("O")){
										bbb = true;
										System.out.println("Produit suivant");
										i++;
										if(i > 6)
											b = true;
									}else{
										System.out.println("Erreur veuillez taper O pour Oui ou N pour Non");
									}
								}
							}
						}
					}
				}

				if(commande[0][0]!=null){
					i = 0;
					newCommande(commande, ID_client);
				}
				cmp = 0;
				break;			

			case 3 :
				fonc.afficheResultat(connecte.clientSuiviCommande(ID_client));
				break;

			case 4 :
				int cmp2 = 0;
				String date = null, ID_commande = null;
				boolean test = false;
				fonc.afficheResultat(connecte.clientSuiviCommandeNonExp(ID_client));
				while (true && cmp2 < 3){
					test = false;
					if(ID_commande == null){
						System.out.println("Entrez l'ID de la commande");
						ID_commande = fonc.readString();
					}

					if(connecte.verifIdCommande(ID_commande) && connecte.verifEtatCommande(ID_commande)){
						test = true;
						date = choisirDate(ID_commande);
					}
					else{
						System.out.println("Soit la commande n'existe pas, soit elle est deja envoyee");
						cmp2++;
						ID_commande = null;
					}

					if(date != null && test){
						connecte.clientChoisirDate(ID_commande,date);
						break;
					}
				}
				cmp2 = 0;
				break;

			case 5 :
				System.out.print("Nouveau nom de societe: ");
				String rep = fonc.readString();
				connecte.clientChangerLogin(rep, ID_client);
				System.out.println("Changement effectue");
				break;

			case 6 :
				System.out.print("Nouveau mot de passe: ");
				String newMdp = fonc.readString();
				connecte.clientChangerMdp(newMdp, ID_client);
				System.out.println("Changement effectue");
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

	public String choisirDate(String ID_commande) throws SQLException {
		System.out.println("Entrez la date de livraison souhaitee (AAAA-MM-JJ)");
		String date = fonc.readString();

		if(date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-')
			System.out.println("Mauvais format, recommencez");

		else{
			String[] d = date.split("-");

			int j = Integer.parseInt(d[2]);
			int m = Integer.parseInt(d[1]);
			int a = Integer.parseInt(d[0]);

			DateFormat formatj = new SimpleDateFormat("dd");
			DateFormat formatm = new SimpleDateFormat("MM");
			DateFormat formata = new SimpleDateFormat("yyyy");

			int jour = Integer.parseInt(formatj.format(new Date()));
			int mois = Integer.parseInt(formatm.format(new Date()));
			int annee = Integer.parseInt(formata.format(new Date()));

			if(annee < a || (annee == a && mois < m) || (annee == a && mois == m && jour < j)){
				if(((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12 ) && j <= 31) || ((m == 4 || m == 6 || m == 8 || m == 11 ) && j <=30) || (((a % 400 == 0 || a % 4 == 0 && a % 100 != 0) && m == 2) && j <= 29) || m == 2 && j <=28){
					return date;
				}
				else
					System.out.println("Date inexistante");
			}
			else
				System.out.println("Date depassee");
		}
		return null;
	}

	public void newCommande(String[][] commande, String ID_cli) throws SQLException {
		String emballeur = connecte.IDEmballeur();
		String transporteur = connecte.IDtransporteur();
		String ID_commande = "";
		String qualifiant = ""; //en fonction des produits soit D soit N soit F soit X
		String date_livraison = "";
		String pays = "";
		String ID_douane = "";
		int prix = 0, nbProd = 0, x = 0;

		//code ASCII de A = 65
		//ID_commande
		for(int i = 0; i < 10; i++){
			int lettre = (int) (Math.random()*26+65);
			int chiffre = (int)(Math.random()*10);
			int choix = (int)(Math.random()*2);
			if(choix == 0)
				ID_commande += (char)lettre;
			else
				ID_commande += chiffre;
		}	

		nbProd = 0;
		int reserve;
		for(int i = 0 ; i < commande.length; i++){
			if(commande[i][0] != null){
				nbProd ++;
				prix += Integer.parseInt(commande[i][1])*Integer.parseInt(connecte.recupPrix(commande[i][0]));
				// UPDATE reserve reserve-quantite
				reserve = connecte.recupReserve(commande[i][0]);
				connecte.updateReserveProduit(reserve-Integer.parseInt(commande[i][1]), commande[i][0]);
			}
		}

		// CHECK Random ID_produit, random quantite par rapport a la reserve, additionner les prix
		// CHECK QUALIFIANT en fonction des produits choisis
		for(int i = 0; i < commande.length; i++){
			if(commande[x][2].equals("D")){
				if(qualifiant.equals("F"))
					qualifiant = "X";
				else
					qualifiant = "D";
			}
			else if(commande[x][2].equals("F")){
				if(qualifiant.equals("D"))
					qualifiant = "X";
				else
					qualifiant = "F";
			}
		}
		if(qualifiant.equals(""))
			qualifiant = "N";

		java.sql.Date d1 = fonc.dateToday();
		do{
			date_livraison = choisirDate(ID_client);
		}while(date_livraison == null);
		java.sql.Date d2 = java.sql.Date.valueOf(date_livraison);
		
		pays = connecte.recupPaysClient(ID_cli);
		ID_douane = connecte.recupDouane(pays);
		// CHECK REMPLIR table commande
		connecte.remplirCommande(ID_commande, ID_cli, qualifiant, "NON_EXP", prix, d1, d2, emballeur, transporteur);
		connecte.remplirControleCommande(ID_commande, ID_douane, "null");

		// CHECK REMPLIR table Commande_Contient_Produit
		for(int i = 0; i < nbProd; i++)
			connecte.remplirCommandeContientProduit(ID_commande,commande[i][0],commande[i][1]);
	}


	public void setLogin(String l){
		this.login = l;
	}

	public String recupID(ResultSet rs) throws SQLException{
		ResultSetMetaData rm = rs.getMetaData();
		String ID = "";
		while(rs.next()){
			for(int i = 1 ; i <= rm.getColumnCount() ; i++){
				ID = rs.getObject(i).toString();
			}
		}
		rs.close();
		return ID;
	}
}
