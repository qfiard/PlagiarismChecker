package commandes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import donnees.ConnexionBD;
import donnees.Constantes;
import donnees.Produit;

public class Colis {

	//IDs des produits
	private LinkedList<String> produits = new LinkedList<String>();
	//Quantites des produits
	private LinkedList<Integer> quantites = new LinkedList<Integer>();

	//100% d'espace disponible dans le colis
	double pourcentRestant = 100.0;

	private int idColis;

	private int prixColis = 0;
	private int statutColis;

	private String destination;
	
	public ConnexionBD conn;

	public Colis(int idColis, int statut, String destination){
		this.idColis = idColis;
		this.statutColis = statut;
		this.destination = destination;
		
		conn = GenerateurAleatoire.conn;
	}

	public int ajouterProduit(Produit p, int quantite){

		//Pourcentage occupe par un seul produit p
		double pourcentProduit = (1.0/p.qteCarton) * 100;
		//Quantite a ajouter au depart
		int qteMin = 1;

		while(pourcentRestant - pourcentProduit >= 0){

			//On ajoute un produit a la fois
			pourcentRestant -= pourcentProduit;

			qteMin++;

			//Si on a ajoute la quantite voulue
			if(qteMin == quantite+1){
				break;
			}	

		}

		prixColis += (p.cout * (qteMin-1)) * (100 + p.tauxAugmentation/100.0);
		produits.add(p.numeroProduit);
		quantites.add(qteMin-1);

		return (quantite-(qteMin-1));
	}

	/**
	 * Verifie si on peut ajouter au moins 1 produit p au colis
	 * @param p le produit a ajouter
	 * @return true si possible, false sinon
	 * */
	public boolean peutAjouter(Produit p){
		if(estPlein()){
			return false;
		}

		double pourcentProduitUnitaire = (1.0/p.qteCarton) * 100;

		return (pourcentRestant - pourcentProduitUnitaire) >= 0;		
	}	

	public String getDestination(){
		return destination;
	}

	public int getStatut(){
		return statutColis;
	}

	public boolean estPlein(){
		return (pourcentRestant == 0);
	}	

	public boolean estVide(){
		return (pourcentRestant == 100);
	}
	
	public int getIdColis(){
		return idColis;
	}

	public int getPrixColis(){
		return prixColis;
	}

	public void insererColis(int idCommande, int statutCol){

		//ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		int nbProduits = produits.size();
		String idProduit;
		int quantite, qualifiant;


		try {
			ResultSet rsCountEmballeur = st.executeQuery("SELECT count(*) FROM " + Constantes.base_emballeur + ";");

			if(rsCountEmballeur.next()){

				int limiteEmballeur = rsCountEmballeur.getInt(1);

				rsCountEmballeur.close();

				ResultSet rsEmballeur = st.executeQuery("SELECT id_emballeur, taux_erreur FROM " + Constantes.base_emballeur + ";");

				Random r = new Random();
				int indiceEmballeur = r.nextInt(limiteEmballeur)+1;

				String idEmballeur = "";
				int tauxErreur = 1;

				//On choisit un emballeur au hasard
				if(rsEmballeur.absolute(indiceEmballeur)){
					idEmballeur = rsEmballeur.getString(1);
					tauxErreur = rsEmballeur.getInt(2);
				}

				String dateEmballage, statut;


				//switch(statutCol){
				switch(statutColis){

				case Commande.EXPEDIEE:
					dateEmballage = "current_date-interval '" + (r.nextInt(10)+3) + " day'";
					statut = "livre";	//accepte
					break;

				case Commande.MOITIE_EXPEDIEE:
					dateEmballage = "current_date-interval '" + (r.nextInt(5)+3) + " day'";
					//statut = "expedie";
					statut = "emballe";
					break;

				case Commande.NON_EXPEDIEE:
					dateEmballage = "NULL";
					statut = "produit";
					break;

				default:
					dateEmballage = "NULL";
					statut = "produit";
					break;

				}

				st.execute("INSERT INTO " + Constantes.base_colis + " VALUES(" +
						idColis + ", " +
						idCommande + ", " +
						"'" + statut + "', " +
						1 +
						");");

				//Insertion des produits dans le colis
				for(int i=0; i<nbProduits; i++){
					idProduit = produits.get(i);
					quantite = quantites.get(i);
					
					/*st.execute("UPDATE " + Constantes.base_produit + " SET " +
							"reserve=reserve-" + quantite + " WHERE id_produit='" + idProduit + "';");*/

					st.execute("INSERT INTO " + Constantes.base_contenuColis + " VALUES(" +
							idColis + ", " +
							"'" + idProduit + "', " +
							quantite +
							");");

					st.execute("INSERT INTO " + Constantes.base_declaration + " VALUES(" +
							idColis + ", " +
							"'" + idProduit + "', " +
							quantite +
							");");
				}

				//Emballage des colis expedies et partiellement expedies
				if(statutCol == Commande.EXPEDIEE || statutCol == Commande.MOITIE_EXPEDIEE){
					st.execute("INSERT INTO " + Constantes.base_emballage + " VALUES(" +
							idColis + ", " +
							"'" + idEmballeur + "', " +
							1 + ", " +
							dateEmballage +							
							");");
				}

				st.close();
				//conn.close();				
				
				qualifiant = this.getQualifiant();

				//conn = new ConnexionBD();
				st = conn.createStatement();
				
				//Mise a jour des qualifiants et types d'emballage
				st.execute("UPDATE " + Constantes.base_colis + " SET qualifiant_colis=" + qualifiant +
						" WHERE id_colis=" + idColis +";");

				if(statutCol == Commande.EXPEDIEE){
					st.execute("UPDATE " + Constantes.base_emballage + " SET type_emballage=" + qualifiant +
							" WHERE id_colis=" + idColis +";");
				}
				else if(statutCol == Commande.MOITIE_EXPEDIEE){						

					int erreur = r.nextInt(100)+1;

					if(erreur <= tauxErreur){	//L'emballeur a fait une erreur

						int indiceProduit = r.nextInt(produits.size());

						String idProd = produits.get(indiceProduit);
						int qteProd = 1;

						int qteErreur = r.nextInt(qteProd)+1;	//Quantite a soustraire du colis

						st.execute("UPDATE " + Constantes.base_contenuColis + " SET quantite=quantite-" +
								qteErreur + " WHERE id_colis=" + idColis + " AND id_produit='" + idProd + "';");
						st.execute("UPDATE " + Constantes.base_produit + " SET reserve=reserve+" + qteErreur +
								" WHERE id_produit='" + idProd + "';");

					}

					st.execute("UPDATE " + Constantes.base_emballage + " SET type_emballage=" + qualifiant +
							" WHERE id_colis=" + idColis +";");
				}
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Retourne l'entier qui correspond au qualifiant d'un colis
	 * @return 1: Normal, 2: Dangereux, 4: Fragile, Autre: Somme des autres qualifiants
	 * */
	public int getQualifiant(){
		//ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		int qualifiant = 0;

		try {
			//Normal
			/*ResultSet rsNormal = st.executeQuery("SELECT count(*) FROM " + Constantes.base_contenuColis + 
					" NATURAL JOIN " + Constantes.base_produit + " WHERE qualifiant_colis='N';");

			if(rsNormal.next()){
				if(rsNormal.getInt(1) > 0){
					qualifiant += 1;
				}
			}

			rsNormal.close();

			//Dangereux
			ResultSet rsDangereux = st.executeQuery("SELECT count(*) FROM " + Constantes.base_contenuColis +
					" NATURAL JOIN " + Constantes.base_produit + " WHERE qualifiant_colis='D';");

			if(rsDangereux.next()){
				if(rsDangereux.getInt(1) > 0){
					qualifiant += 2;
				}
			}

			rsDangereux.close();

			//Fragile
			ResultSet rsFragile = st.executeQuery("SELECT count(*) FROM " + Constantes.base_contenuColis +
					" NATURAL JOIN " + Constantes.base_produit + " WHERE qualifiant_colis='F';");

			if(rsDangereux.next()){
				if(rsDangereux.getInt(1) > 0){
					qualifiant += 2;
				}
			}

			rsDangereux.close();*/

			ResultSet rsQualifiant = st.executeQuery("SELECT qualifiant, count(qualifiant) FROM " + Constantes.base_contenuColis + 
					" NATURAL JOIN " + Constantes.base_produit + " WHERE id_colis=" + idColis +" GROUP BY(qualifiant);");

			while(rsQualifiant.next()){

				int nombre = rsQualifiant.getInt(2);

				switch(rsQualifiant.getString(1).charAt(0)){

				case 'N':
					if(nombre > 0){
						qualifiant += 1;
					}
					break;

				case 'D':
					if(nombre > 0){
						qualifiant += 2;
					}
					break;

				case 'F':
					if(nombre > 0){
						qualifiant += 4;
					}
					break;

				}
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}		

		//conn.close();

		return qualifiant;
	}

	public String toString(){
		StringBuffer str = new StringBuffer("\tID Colis: " + idColis + "\n");

		int size = produits.size();

		for(int i=0; i<size; i++){
			str.append("\t\t\t_" + produits.get(i) + ", " + quantites.get(i)+"\n");
		}

		return str.toString();
	}

}
