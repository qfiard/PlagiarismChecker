package donnees;

import java.sql.SQLException;
import java.sql.Statement;

public class Produit {

	public String numeroProduit;
	public String descProduit;
	public int qteCarton;
	public int cartonPalette;
	//public String qualifiant;	//char
	public char qualifiant;
	public int cout;
	public int tauxAugmentation;
	public int poids;
	public int reserve;

	public Produit(String[] champs) throws Exception{

		try{

		numeroProduit = champs[1];
		descProduit = champs[2];
		qteCarton = Integer.parseInt(champs[3]);
		cartonPalette = Integer.parseInt(champs[4]);
		qualifiant = champs[5].charAt(0);	//char
		cout = Integer.parseInt(champs[6]);
		tauxAugmentation = Integer.parseInt(champs[7]);
		poids = Integer.parseInt(champs[8]);
		reserve = Integer.parseInt(champs[9]);

		}catch(Exception e){
			throw e;
		}

	}
	
	public Produit(String idProduit, int poids, char qualifiant, int prix, String descProduit,
			int qteParCarton, int cartonPalette, int reserve, int taux){
		
		numeroProduit = idProduit;
		this.poids = poids;
		this.qualifiant = qualifiant;
		this.cout = prix;
		this.descProduit = descProduit;
		this.qteCarton = qteParCarton;
		this.cartonPalette = cartonPalette;
		this.reserve = reserve;
		this.tauxAugmentation = taux;
		
	}

	public void insererProduit(Statement st){

		String requeteProduit = "INSERT INTO " + Constantes.base_produit + " VALUES(" +
				"'" + numeroProduit + "', " +
				//numeroProduit + ", " +	INT
				poids + ", " +
				"'" + qualifiant + "', " +
				cout + ", " +
				"'" + descProduit + "', " +				
				qteCarton + ", " +
				cartonPalette + ", " +
				reserve + ", " +
				tauxAugmentation + ");";

		try {

			//st.execute(requeteId);
			st.execute(requeteProduit);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
