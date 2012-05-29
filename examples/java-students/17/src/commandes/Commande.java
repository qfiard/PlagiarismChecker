package commandes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import donnees.ConnexionBD;
import donnees.Constantes;
import donnees.Produit;

public abstract class Commande {

	protected LinkedList<Colis> colis = new LinkedList<Colis>();
	protected LinkedList<String> idsProduits = new LinkedList<String>();
	protected LinkedList<Integer> qtesProduits = new LinkedList<Integer>();

	protected int idCommande;
	protected String idClient;
	
	protected int statutCommande;
	
	protected String destination;

	public static final int EXPEDIEE = 0;
	public static final int MOITIE_EXPEDIEE = 1;
	public static final int NON_EXPEDIEE = 2;
	
	public static ConnexionBD conn;

	public Commande(int idCommande, String idClient, LinkedList<Produit> produits,
			LinkedList<Integer> quantites, int statut){	
		
		try {

			this.idCommande = idCommande;	
			this.idClient = idClient;
			this.statutCommande = statut;
			
			conn = GenerateurAleatoire.conn;

			int nbProduits = produits.size();
			int idColis = 1;

			//int i=0;

			//ConnexionBD conn = new ConnexionBD();
			Statement st = conn.createStatement();

			ResultSet rsPays = st.executeQuery("SELECT pays FROM " + Constantes.base_client +
					" WHERE id_client='" + idClient + "';");
			
			if(rsPays.next()){
				this.destination = rsPays.getString(1);
			}
			
			ResultSet rs = st.executeQuery("SELECT  max(id_colis) FROM " +
					Constantes.base_colis + ";");

			if(rs.next()){
				idColis = rs.getInt(1)+1;
			}
			
			rs.close();

			qtesProduits.addAll(quantites);

			//for(int i=0; i<nbProduits; i++){

			//Tant qu'il n'y a plus de produit a ajouter
			while(!produits.isEmpty()){	

				//Le produit que l'on va ajouter
				Produit p = produits.get(0);		


				int qte = quantites.get(0);	
				int qteRestante;

				//On ajoute un nouveau colis vide
				Colis c = new Colis(idColis, statut, this.destination);
				idColis++;
				
				colis.add(c);


				for(Colis col: colis){

					//On regarde si on peut ajouter le produit dans un colis
					if(col.peutAjouter(p)){

						qteRestante = col.ajouterProduit(p, qte);

						//Si on a reussi a ajouter tous les produits,
						//On retire le produit de la liste des produits a ajouter
						if(qteRestante == 0){
							produits.remove(0);
							quantites.remove(0);
							idsProduits.add(p.numeroProduit);
							break;
						}
						//Sinon, on cherche a caser la quantite restante
						//dans le prochain colis
						else{
							quantites.set(0, qteRestante);
						}
					}
				}

				
			}

			//On supprime les possibles colis vides			
			for(int i=0; i<colis.size(); i++){
				if(colis.get(i).estVide()){
					colis.remove(i);
					i--;
				}
			}
			
			/*for(Colis c: colis){
				System.out.println(c + "/" + c.getStatut());
			}*/

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<Colis> getColis(){
		return colis;
	}

	public static Commande creerCommande(int idCommande, String idClient, LinkedList<Produit> produits, LinkedList<Integer> quantites, int statut){

		switch(statut){

		case EXPEDIEE:
			return new CommandeExpediee(idCommande, idClient, produits, quantites, statut);
			
		case MOITIE_EXPEDIEE:
			return new CommandePartiellementExpediee(idCommande, idClient, produits, quantites, statut);

		case NON_EXPEDIEE:
			return new CommandeOuverte(idCommande, idClient, produits, quantites, statut);

		default:
			return null;

		}

	}
	
	public boolean estExpediable(){
		return this.statutCommande != NON_EXPEDIEE;
	}
	
	public abstract void insererCommande();

	public String toString(){
		StringBuffer str = new StringBuffer("ID: " + idCommande + " (" + destination +")\r");

		for(Colis c: colis){
			str.append(c + "\r");
		}

		return str.toString();
	}

}
