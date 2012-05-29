package commandes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import donnees.ConnexionBD;
import donnees.Constantes;
import donnees.Produit;

public class GenerateurAleatoire {

	public static ConnexionBD conn = new ConnexionBD();

	public GenerateurAleatoire(){
		Random r = new Random();

		LinkedList<Commande> commandes = new LinkedList<Commande>();
		LinkedList<String> idsClients = new LinkedList<String>();

		int limiteClients = 1,  limiteProduits = 1;
		int statut = Commande.EXPEDIEE;

		//ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {

			ResultSet rsClients = st.executeQuery("SELECT id_client FROM " + Constantes.base_client + ";");

			while(rsClients.next()){
				idsClients.add(rsClients.getString(1));
			}

			limiteClients = idsClients.size();

			ResultSet rsProduits = null, rsCountProduits = null;
			boolean updateRsProduits = true;

			//for(int i=0; i<20; i++){
			for(int i=1; i<=250; i++){
				switch(i){

				case 1:
					statut = Commande.EXPEDIEE;
					break;

				case 181:
					statut = Commande.MOITIE_EXPEDIEE;
					break;

				case 201:
					statut = Commande.NON_EXPEDIEE;
					break;

				}

				LinkedList<Produit> produits = new LinkedList<Produit>();
				LinkedList<String> idsProduits = new LinkedList<String>();
				LinkedList<Integer> quantites = new LinkedList<Integer>();

				String idClient;


				//ID du client au hasard
				int indiceClient = r.nextInt(limiteClients);
				idClient = idsClients.get(indiceClient);


				//Nombre de produits dans la commande
				int nbProduits = r.nextInt(10)+1;				

				if(updateRsProduits){

					limiteProduits = 1;

					rsCountProduits = st.executeQuery("SELECT count(*) FROM " + Constantes.base_produit + " WHERE " +
							"reserve>0;");

					if(rsCountProduits.next()){
						limiteProduits = rsCountProduits.getInt(1);
					}

					rsCountProduits.close();

					rsProduits = st.executeQuery("SELECT * FROM " + Constantes.base_produit + " WHERE " +
							"reserve>0;");

					updateRsProduits = false;
				}

				for(int j=0; j<nbProduits; j++){

					int indiceProduit = r.nextInt(limiteProduits)+1;
					boolean nouveauProduit = false;

					//On cherche un id de produit qui n'est pas dans la liste
					while(rsProduits.absolute(indiceProduit) && !nouveauProduit){

						indiceProduit = r.nextInt(limiteProduits)+1;

						String idProduit = rsProduits.getString(1);

						//Si le produit n'est pas deja dans la liste des ids
						if(!idsProduits.contains(idProduit)){	  

							int poids = rsProduits.getInt(2);
							char qualifiant = rsProduits.getString(3).charAt(0);
							int cout = rsProduits.getInt(4);
							String descProduit = rsProduits.getString(5);
							int qteCarton = rsProduits.getInt(6);
							int cartonPalette = rsProduits.getInt(7);
							int reserve = rsProduits.getInt(8);
							int tauxAugmentation = rsProduits.getInt(9);


							Produit p = new Produit(idProduit, poids, qualifiant, cout, descProduit,
									qteCarton, cartonPalette, reserve, tauxAugmentation);

							produits.add(p);								


							//int qteProduit = r.nextInt(p.reserve)+1;
							//int qteProduit = r.nextInt((int)(p.reserve* (0.10))+1) + 1;
							int qteProduit = r.nextInt(Math.min(10, r.nextInt(p.reserve)+1) + 1) + 1;
							//int qteProduit = r.nextInt(p.reserve)+1;
							//int qteProduit = r.nextInt(Math.min(20, r.nextInt(p.reserve)+1) + 1) + 1;

							if(p.reserve == qteProduit){
								updateRsProduits = true;
							}

							quantites.add(qteProduit);
							idsProduits.add(idProduit);

							Statement stUpdate = conn.createStatement();
							
							//UPDATE produits SET reserve=reserve-qteProduit WHERE id_produit=''
							stUpdate.execute("UPDATE " + Constantes.base_produit + " SET " +
									"reserve=reserve-" + qteProduit + " WHERE id_produit='" + idProduit + "';");

							stUpdate.close();
							
							//On sort de la boucle (on a trouve un nouveau produit)								
							nouveauProduit = true;								

						}					

					}

				}

				Commande cmd = Commande.creerCommande(i, idClient, produits, quantites, statut);
				cmd.insererCommande();
				commandes.add(cmd);


			}	//Fin des commandes aleatoires



			//Ajout des colis aux palettes
			LinkedList<Palette> palettes = new LinkedList<Palette>();

			//Vaudra true si on a pu ajouter le colis a une des palettes
			//deja crees
			boolean ajoute = false;

			int idConteneur = 1;
			int idPalette = 1;

			Palette palette = new Palette(idPalette);
			palettes.add(palette);

			idPalette++;

			for(Commande cmd: commandes){

				if(cmd.estExpediable()){

					//On recupere les colis de la commande
					LinkedList<Colis> colis = cmd.getColis();					

					for(Colis c: colis){
						for(Palette p: palettes){
							if(p.peutAjouter(c)){
								p.ajouterColis(c);
								ajoute = true;
								break;
							}
						}

						if(!ajoute){
							//On cree une nouvelle palette
							//Palette pal = new Palette(idPalette, c.getDestination());
							Palette pal = new Palette(idPalette);

							pal.ajouterColis(c);
							palettes.add(pal);

							idPalette++;
						}

						ajoute = false;
					}				

				}
			}

			/*for(Palette p: palettes){
				System.out.println(p);
			}*/

			//Ajout des palettes aux conteneurs
			LinkedList<Conteneur> conteneurs = new LinkedList<Conteneur>();

			//Vaudra true si la palette courante a pu etre ajoutee a un conteneur existant
			ajoute = false;

			Conteneur conteneur = new Conteneur(idConteneur);
			conteneurs.add(conteneur);

			idConteneur++;

			for(Palette p: palettes){		

				for(Conteneur c: conteneurs){
					if(c.peutAjouter(p)){
						c.ajouterPalette(p);
						ajoute = true;
						break;
					}
				}

				if(!ajoute){
					Conteneur cont = new Conteneur(idConteneur);

					cont.ajouterPalette(p);
					conteneurs.add(cont);

					idConteneur++;
				}

				ajoute = false;

			}

			int limiteTransporteur = 1;

			ResultSet rsCountTransporteur = st.executeQuery("SELECT count(*) FROM " +
					Constantes.base_transporteur + ";");

			if(rsCountTransporteur.next()){
				limiteTransporteur = rsCountTransporteur.getInt(1);				
			}

			rsCountTransporteur.close();

			ResultSet rsTransporteur = st.executeQuery("SELECT code_SCAC FROM " +
					Constantes.base_transporteur + ";");			



			for(Conteneur c: conteneurs){

				int indiceTransporteur = r.nextInt(limiteTransporteur)+1;

				if(rsTransporteur.absolute(indiceTransporteur)){
					c.insererConteneur(rsTransporteur.getString(1));
				}

			}

			/*for(Conteneur c: conteneurs){
				System.out.println(c);
			}*/

			/*for(Commande cmd: commandes){
				LinkedList<Colis> colis = cmd.getColis();

				for(Colis c: colis){
					c.insererColis(cmd.idCommande, Commande.EXPEDIEE);
				}
				cmd.insererCommande();
			}*/

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		conn.close();


		/*for(Commande c: commandes){
			System.out.println(c);
			//c.insererCommande();
		}*/
	}

	public static void main(String[] args){

		GenerateurAleatoire gen = new GenerateurAleatoire();

	}

}
