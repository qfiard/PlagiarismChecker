import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Math;


public class Tables {
	SQL connecte;
	FonctionsCommunes fonc;
	
	public Tables(SQL co){
		this.connecte = co;
	}

	public int printMenu() throws IOException, SQLException, ClassNotFoundException {
		int c = -1; // le choix de l'utilisateur
		connecte = new SQL();
		fonc = new FonctionsCommunes(this.connecte);
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Quitter");
		System.out.println("1 - Creer toutes les tables");
		System.out.println("2 - Drop toutes les tables");
		System.out.println("3 - Creer une table");
		System.out.println("4 - Creer une table existant dans le projet de base");
		System.out.println("5 - Supprimer une table");
		System.out.println("6 - Parser un fichier .csv");
		System.out.println("7 - Random commande");
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
				connecte.creationTable();
				break;
				
			case 2:
				connecte.dropTable();
				break;
				
			case 3:
				System.out.println("Entrez le code pour la creation de la table");
				String code = fonc.readString();
				connecte.creationTable(code);
				break;
				
			case 4:
				System.out.println("Entrez le nom de la table a recreer");
				String nom = fonc.readString();
				nom.toLowerCase();
				if(nom.equals("client"))
					connecte.creationClient();
				else if(nom.equals("produit"))
					connecte.creationProduit();
				else if(nom.equals("transporteur"))
					connecte.creationTransporteur();
				else if(nom.equals("douane"))
					connecte.creationDouane();
				else if(nom.equals("gerant"))
					connecte.creationGerant();
				else if(nom.equals("emballeur"))
					connecte.creationEmballeur();
				else if(nom.equals("commande"))
					connecte.creationCommande();
				else if(nom.equals("palette"))
					connecte.creationPalette();
				else if(nom.equals("carton"))
					connecte.creationCarton();
				else if(nom.equals("carton_contient_produit"))
					connecte.creationCartonContientProduit();
				else if(nom.equals("controlecommande"))
					connecte.creationControleCommande();
				else if(nom.equals("commande_contient_produit"))
					connecte.creationCartonContientProduit();
				else
					System.out.println("Table inexistante");
				break;
				
			case 5:
				System.out.println("Entrez le nom de la table a supprimer");
				String table = fonc.readString();
				connecte.suppressionTable(table);
				break;

			case 6:
				System.out.println("Entrez le nom du fichier a parser");
				String fichier = fonc.readString();
				parsingCSV(fichier);
				break;

			case 7:
				randomCommande();
				break;

			case 0: 
				System.out.println("FIN");
				break;

			}
			connecte.close();
		}
		catch (SQLException  e) {
			System.err.println(e.getMessage());
		}

		return c;
	}


	// Parsing du fichier csv fourni en fonction du premier nombre recupere
	public void parsingCSV(String fichier) throws NumberFormatException, IOException, SQLException{
		try{
			BufferedReader fichier_source = new BufferedReader(new FileReader(fichier));
			String chaine;

			while((chaine = fichier_source.readLine())!= null){
				String[] tabChaine = chaine.split("\\|");
				int rep = Integer.parseInt(tabChaine[0]);
				switch(rep){
				case 10:
					connecte.ajouterDonneesEmballeur(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5], "0");
					break;
				case 20:
					connecte.ajouterDonneesClient(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5], tabChaine[6], tabChaine[7], tabChaine[8], tabChaine[9]);
					break;
				case 30:
					connecte.ajouterDonneesProduit(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5], tabChaine[6], tabChaine[7], tabChaine[8], tabChaine[9]);
					break;
				case 40:
					connecte.ajouterDonneesTransporteur(tabChaine[1], tabChaine[2], tabChaine[3], "0");
					break;
				case 50:
					connecte.ajouterDonneesDouane(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4]);
					break;
				case 60:
					connecte.ajouterDonneesGerant(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4]);
					break;
				}
			}
			fichier_source.close();					
		}
		catch (FileNotFoundException e){
			System.out.println("Le fichier est introuvable !");
		}
	}

	// Retourne le nombre de clients
	public int nbClient() throws SQLException{
		ResultSet rss = connecte.countClient();
		ResultSetMetaData resultMetaa = rss.getMetaData();
		int nbLignes = 0;
		while(rss.next()){			
			for(int i = 1; i <=  resultMetaa.getColumnCount(); i++)
				nbLignes = Integer.parseInt(rss.getObject(i).toString());
		}
		rss.close();
		return nbLignes;
	}

	// Retourne un tableau contenant les IDs des clients
	public String[] ID_client() throws SQLException{
		String[] clients = new String[nbClient()];
		ResultSet rs = connecte.clientID();
		ResultSetMetaData resultMeta = rs.getMetaData();
		int compt = 0;
		while(rs.next()){			
			for(int i = 1; i <=  resultMeta.getColumnCount(); i++){
				clients[compt] = rs.getObject(i).toString();
			}
			compt++;
		}
		rs.close();
		return clients;
	}

	// Retourne le nombre d'emballeurs
	public int nbEmballeur() throws SQLException{
		ResultSet rss = connecte.countEmballeur();
		ResultSetMetaData resultMetaa = rss.getMetaData();
		int nbLignes = 0;
		while(rss.next()){			
			for(int i = 1; i <=  resultMetaa.getColumnCount(); i++)
				nbLignes = Integer.parseInt(rss.getObject(i).toString());
		}
		rss.close();
		return nbLignes;
	}

	// Retourne le nombre de produits
	public int nbProduit() throws SQLException{
		ResultSet rss = connecte.countProduit();
		ResultSetMetaData resultMetaa = rss.getMetaData();
		int nbLignes = 0;
		while(rss.next()){			
			for(int i = 1; i <=  resultMetaa.getColumnCount(); i++)
				nbLignes = Integer.parseInt(rss.getObject(i).toString());
		}
		rss.close();
		return nbLignes;
	}

	// Retourne les informations sur les produits
	public String[][] infoProduit() throws SQLException{
		//indice 0: ID_produit, indice 1: cout, indice 2: reserve, indice 3: qualifiant
		String[][] produits = new String[nbProduit()][4];
		ResultSet rs = connecte.produitInfo();
		ResultSetMetaData resultMeta = rs.getMetaData();
		int compt = 0;
		while(rs.next()){			
			for(int i = 1; i <=  resultMeta.getColumnCount(); i++){
				produits[compt][i-1] = rs.getObject(i).toString();
			}
			compt++;
		}
		rs.close();
		return produits;
	}

	/* Generation d'une date aleatoire :
	 *	-Si expedie == true, retourne une date "loin" dans le passe
	 *	-Sinon retourne une date "proche" (<15 jours)
	 */
	public String randomDateExpedition(boolean expedie){
		int annee, mois, jour;

		if(expedie){
			annee = (int)(Math.random()*10 + 2001);
			mois = (int)(Math.random()*12 + 1);
			jour = 0;

			// Test d'existence de la date
			if(mois == 1 || mois == 3 || mois == 5 || mois == 7 || mois == 8 || mois == 10 || mois == 12)
				jour = (int)(Math.random()*31 + 1);
			else if(mois == 4 || mois == 6 || mois == 9 | mois == 11)
				jour = (int)(Math.random()*30 + 1);
			else if(mois == 2){
				if(annee % 400 == 0 || annee % 4 == 0 && annee % 100 != 0)
					jour = (int)(Math.random()*29 + 1);
				else
					jour = (int)(Math.random()*28 + 1);
			}

		}else{

			// Utilisation de la date d'aujourd'hui
			DateFormat format = new SimpleDateFormat("yyyy");
			annee = Integer.parseInt(format.format(new Date()));
			format = new SimpleDateFormat("MM");
			mois = Integer.parseInt(format.format(new Date()));
			format = new SimpleDateFormat("dd");
			jour = Integer.parseInt(format.format(new Date()));

			// Generation d'un nombre aleatoire entre 0 et 15 qu'on retranche au jour
			jour -= (int)(Math.random() * 15);

			// Correction de la date
			if(jour < 1){
				mois--;
				if(mois == 2){
					if(annee % 400 == 0 || annee % 4 == 0 && annee % 100 != 0)
						jour += 29;
					else
						jour += 28;
				}

				else if(mois == 1 || mois == 3 || mois == 5 || mois == 7 || mois == 8 || mois == 10)
					jour += 31;

				else if(mois == 4 || mois == 6 || mois == 9 || mois == 11)
					jour += 30;

				else if(mois == 0){
					jour += 31;
					mois = 12;
					annee--;
				}
			}
		}
		return fonc.returnDate(jour,mois,annee);
	}


	/* Generation d'une date aleatoire
	 * -Si expedie == true, on utilise la date envoyee en parametre comme date de reference
	 * -sinon utilisation de la date d'aujourd'hui
	 * 
	 * On ajoute ensuite un nombre aleatoire de jours entre 0 et 14
	 */
	public String randomDateLivraisonVoulue(String dateDepart, boolean expedie){
		String[] d = dateDepart.split("-");
		int jour, mois, annee;

		// Recuperation de la date passee en parametre
		if(expedie){
			jour = Integer.parseInt(d[2]);
			mois = Integer.parseInt(d[1]);
			annee = Integer.parseInt(d[0]);

		}

		// Utilisation de la date d'aujourd'hui
		else{
			DateFormat format = new SimpleDateFormat("yyyy");
			annee = Integer.parseInt(format.format(new Date()));
			format = new SimpleDateFormat("MM");
			mois = Integer.parseInt(format.format(new Date()));
			format = new SimpleDateFormat("dd");
			jour = Integer.parseInt(format.format(new Date()));
		}

		// Ajout d'un nombre de jours aleatoire entre 0 et 14
		jour += (int)(Math.random() * 14);

		// Correction de la date
		if(jour > 28){
			if(mois == 2){
				if(annee % 400 == 0 || annee % 4 == 0 && annee % 100 != 0){
					jour = jour % 29;

					if(jour == 0)
						jour = 29;
					if(jour < 20)
						mois++;
				}else{
					jour = jour % 28;

					if(jour == 0)
						jour = 28;
					if(jour < 20)
						mois++;
				}
			}
			else if(mois == 1 || mois == 3 || mois == 5 || mois == 7 || mois == 8 || mois == 10){
				jour = jour % 31;

				if(jour == 0)
					jour = 31;
				if(jour < 20)
					mois++;
			}

			else if(mois == 4 || mois == 6 || mois == 9 | mois == 11){
				jour = jour % 30;

				if(jour == 0)
					jour = 30;
				if(jour < 20)
					mois++;
			}
			else if(mois == 12){
				jour = jour % 31;

				if (jour == 0)
					jour = 31;
				if(jour < 20){
					mois = 1;
					annee++;
				}
			}

		}
		return fonc.returnDate(jour,mois,annee);
	}


	// Renvoie un resultat du controle de douane en fonction des taux de controle
		public String returnControleCommande(String ID_emballeur,String ID_douane) throws SQLException{
			int taux_emballeur = connecte.recupTauxEmballeur(ID_emballeur);
			int taux_douane = connecte.recupTauxDouane(ID_douane);
			int t1 = (int)(Math.random()*100);
			int t2 = (int)(Math.random()*100);
			if(t1 <= taux_emballeur){
				if(t2 <= taux_douane)
					return "REJETE";
				else
					return "NON_CONTROLE";
			}
			else{
				if(t2 <= taux_douane)
					return "CONTROLE";
				else
					return "NON_CONTROLE";
			}
		}

		//GENERE un ID
		public String randomID(int taille){
			String rep = "";
			for(int i = 0; i < taille; i++){
				int lettre = (int) (Math.random()*26+65); //code ASCII de A = 65
				int chiffre = (int)(Math.random()*10);
				int choix = (int)(Math.random()*2);
				if(choix == 0)
					rep += (char)lettre;
				else
					rep += chiffre;
			}
			return rep;
		}

		// GENERE UN COLIS
		//int expedier: 0=commande expedie 1=commande partiellement expedie 2=commande non expedie
		public void random1Commande(String suivi, String ID_client, String[][] prods, int expedie) throws SQLException{			
			String ID_commande = "";
			String [][] ID_produit;
			ArrayList<Carton> Cartons = new ArrayList<Carton>();
			ArrayList<Palette> Palettes = new ArrayList<Palette>();
			ArrayList<Palette_contient_Carton> pcc = new ArrayList<Palette_contient_Carton>();
			String qualifiant = ""; //en fonction des produits soit D soit N soit F soit X
			String date_livraison = "", date = "";
			int prix = 0, nbProd = 0, x = 0, quantite = 0;
			boolean exp;
			String ID_emballeur = connecte.IDEmballeur();
			String ID_transporteur = connecte.IDtransporteur();
			String destination = connecte.recupPaysClient(ID_client);


			ID_commande = randomID(10);


			// CHECK Choisir combien de produits on veut 
			// on va dire 6 produits differents max par commande
			nbProd = (int)(Math.random()*6+1);
			ID_produit = new String[nbProd][2]; // [x][0]=ID_produit [x][1]= quantite de ce produit

			// CHECK Random ID_produit, random quantite par rapport a la reserve, additionner les prix
			// CHECK QUALIFIANT en fonction des produits choisis
			for(int i = 0; i < ID_produit.length; i++){
				x = (int)(Math.random()*prods.length);
				ID_produit[i][0] = prods[x][0];
				int d = Integer.parseInt(prods[x][2]);
				quantite = (int)(Math.random()*(d-1)+1);
				// UPDATE reserve d-quantite
				connecte.updateReserveProduit(d-quantite, ID_produit[i][0]);
				ID_produit[i][1] = (new Integer(quantite)).toString();
				prix += (Integer.parseInt(prods[x][1])*quantite);
				if(prods[x][3].equals("D")){
					if(qualifiant.equals("F"))
						qualifiant = "X";
					else
						qualifiant = "D";
				}
				else if(prods[x][3].equals("F")){
					if(qualifiant.equals("D"))
						qualifiant = "X";
					else
						qualifiant = "F";
				}
				
				prodDansCarton(Palettes,Cartons,pcc, prods[x][0],quantite,prods[x][3]);
			}
			if(qualifiant.equals(""))
				qualifiant = "N";
			if(expedie == 0)
				exp = true;
			else
				exp = false;

			date = randomDateExpedition(exp);
			java.sql.Date d1 = java.sql.Date.valueOf(date);
			date_livraison = randomDateLivraisonVoulue(date, exp);
			java.sql.Date d2 = java.sql.Date.valueOf(date_livraison);
			String ID_douane = connecte.douanierSelectionne(ID_client);
			String controle = "";
			if(expedie != 2)
				controle = returnControleCommande(ID_emballeur,ID_douane);
			else
				controle = null;

			// CHECK REMPLIR table commande
			connecte.remplirCommande(ID_commande, ID_client, qualifiant, suivi, prix, d1, d2, ID_emballeur, ID_transporteur);
			//CHECK REMPLIR table commandeControle
			connecte.remplirControleCommande(ID_commande, ID_douane, controle);

			// CHECK REMPLIR table Commande_Contient_Produit
			for(int i = 0; i < nbProd; i++)
				connecte.remplirCommandeContientProduit(ID_commande,ID_produit[i][0],ID_produit[i][1]);
			//CHECK REMPLIR table palettes
			for(int i = 0; i < Palettes.size(); i++)
				connecte.remplirPalette(Palettes.get(i).ID_palette, ID_commande, destination, Palettes.get(i).qualifiant, d1, d2);

			//CHECK REMPLIR table cartons
			for(int i = 0; i < Cartons.size(); i++)
				connecte.remplirCartons(Cartons.get(i).ID_carton, ID_commande, Cartons.get(i).ID_produit, Cartons.get(i).quantite_produit, Cartons.get(i).qualifiant,Cartons.get(i).ID_palette, destination);

		}


		public void prodDansCarton(ArrayList<Palette> palettes,ArrayList<Carton> cartons,ArrayList<Palette_contient_Carton> pcc, String ID_produit, int quantite, String qualifiant) {
			String ID_palette = randomID(10);
			int quantite_par_carton = 0;
			int cartons_par_palette = 0;
			try {
				quantite_par_carton = connecte.produitQteParCarton(ID_produit);
				cartons_par_palette = connecte.produitCartonsParPalette(ID_produit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int nbCartons = (int) Math.ceil(quantite*1.0/quantite_par_carton*1.0);
			int q = quantite , nbc =0;

			palettes.add(new Palette(ID_palette, qualifiant));
			
			for(int i = 0; i < nbCartons ; i++){
				if(q < quantite_par_carton)
					cartons.add(new Carton(ID_produit,q,qualifiant,ID_palette));
				else{
					cartons.add(new Carton(ID_produit,quantite_par_carton,qualifiant,ID_palette));
					q -= quantite_par_carton;
				}
				if(nbc == cartons_par_palette){
					ID_palette = randomID(10);
					palettes.add(new Palette(ID_palette, qualifiant));
					nbc = 0;
				}
				nbc++;
				pcc.add(new Palette_contient_Carton(ID_palette,cartons.get(i).ID_carton));
				
			}

		}


		// GENERE TOUT LES COLIS 
		public void randomCommande() throws SQLException{
			String [] clients = ID_client(); 
			String [][] produits = infoProduit();
			int i=0, ind = 0;
			while(i<180){
				if(i<20){
					//les 20 commandes partiellement expediee
					ind = (int)(Math.random()*clients.length);
					random1Commande("PARTIELLEMENT_EXP",clients[ind],produits,1);
				}
				if(i<50){
					//les 50 commandes non expediees
					ind = (int)(Math.random()*clients.length);
					random1Commande("NON_EXP",clients[ind],produits,2);
				}
				//les 180 commandes 100% expediees
				ind = (int)(Math.random()*clients.length);
				random1Commande("EXPEDIE",clients[ind],produits,0);
				i++;
			}
		}
	}
