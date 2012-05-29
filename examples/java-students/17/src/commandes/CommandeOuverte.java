package commandes;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import donnees.ConnexionBD;
import donnees.Constantes;
import donnees.Produit;

public class CommandeOuverte extends Commande{

	public CommandeOuverte(int idCommande, String idClient,
			LinkedList<Produit> produits, LinkedList<Integer> quantites, int statut) {
		super(idCommande, idClient, produits, quantites, statut);

	}

	@Override
	public void insererCommande() {
		int prixCommande = 0;

		try {

			//ConnexionBD conn = new ConnexionBD();
			Statement st = conn.createStatement();

			Random r = new Random();

			/*String dateLivraison = "current_date + interval '" + (r.nextInt(20) + 1) +
					"day'";*/


			st.execute("INSERT INTO " + Constantes.base_commande + " VALUES(" +
					idCommande + ", " +
					"'" + idClient + "', " +
					//dateLivraison + ", " +
					"NULL, " +
					prixCommande + ", " +
					"'non-expediee'" +
					");");

			st.close();
			//conn.close();

			for(Colis c: colis){
				c.insererColis(this.idCommande, NON_EXPEDIEE);
				prixCommande += c.getPrixColis();
			}

			//conn = new ConnexionBD();
			st = conn.createStatement();

			st.execute("UPDATE " + Constantes.base_commande + " SET prix_livraison=" +
					prixCommande + " WHERE id_commande=" + idCommande + ";");

			int nbProduits = idsProduits.size(); 

			for(int i=0; i<nbProduits; i++){
				String idProd = idsProduits.get(i);
				int qteProd = qtesProduits.get(i);

				st.execute("INSERT INTO " + Constantes.base_contenuCommande + " VALUES(" +
						idCommande + ", " +
						"'" + idProd + "', " +
						qteProd +
						");");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

}
