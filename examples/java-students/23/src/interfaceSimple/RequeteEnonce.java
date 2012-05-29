package interfaceSimple;
import java.sql.*;

public class RequeteEnonce {
	static Connection conn; // la connexion a la base
	static Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;

	// connection a la base
	public RequeteEnonce(String login, String motPasse) throws SQLException,
			ClassNotFoundException {
		// -------------------
		// Connexion a la base
		// --------------------

		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/booktown", login, motPasse);
		st = conn.createStatement();
	}


	public static void requete(int requetenum){
		String[] requete = new String[14];
		
		requete[1]="select nom_societe from Client";
		requete[2]="select nom_transporteur from Transporteur";
		requete[3]="select nom_emballeur from Emballeur";
		
		requete[4]="select  id_produit, nbre_vente " +
				"from produit " +
				" group by id_produit, nbre_vente" +
				"order by nbre_vente desc " +
				"limit 5";
		
		requete[5]="select nom_societe,id_client,sum(commande.prix_com) as som " +
				"from commande natural join client " +
				"group by id_client,nom_societe " +
				"order by som desc " +
				"limit 5";
		
		requete[6]="select count(id_emballeur) as colis_par_jr ,id_emballeur,date_embal " +
				" from emballeur natural join colis" +
				" group by id_emballeur, date_embal " +
				"order by id_emballeur ";
		
		requete[7]="select id_emballeur,delai_livraison,date_livraison,date_souhait_livraison," +
				"(date_livraison - date_souhait_livraison) as jrs_entre_commande_livraison " +
				" from colis natural join commande natural join palette" +
				" where delai_livraison>=(date_livraison - date_souhait_livraison)";
		
		requete[8]="select id_emballeur,count(id_emballeur) as nb_rejet " +
				" from colis natural join control_colis " +
				" where rejet='TRUE' " +
				" group by id_emballeur having count(rejet)>3 " +
				"order by nb_rejet desc";
		
		requete[9]="select etat_commande " +
				"from commande " +
				"where id_client = 	'UJOXK14368'  and id_commande= '21' ";
		
		requete[10]="select id_produit,description,stocks,prix_pr " +
				" from produit " +
				"where stocks>0";
		
		requete[11]="select id_commande " +
				" from colis natural join commande " +
				" where id_client='SUROK77336' ";
		
		requete[12]="select id_colis,type " +
				"from (produit_commande natural join produit) " +
				"natural join (commande natural join colis) " +
				"where type='D' or type='F'";
		
		afficheRequete(requete[requetenum]);
	}
	
	public static void afficheRequete(String req){

		try {
			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery(req);
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();

			System.out.println("\n**********************************");
			//On affiche le nom des colonnes
			for(int i = 1; i <=  resultMeta.getColumnCount(); i++)
				System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

			System.out.println("\n**********************************");

			while(result.next()){			
				for(int i = 1; i <=  resultMeta.getColumnCount(); i++)
					System.out.print("\t" + result.getObject(i).toString() + "\t |");

				System.out.println("\n---------------------------------");

			}


			result.close();
			state.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}			

	// fermeture de la connection
	public void close() throws SQLException {
		st.close();
		conn.close();
	}

}