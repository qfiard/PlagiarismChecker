package src;

import java.sql.SQLException;

public class DashGerant {

	private Connexion c ;
	public String len = "" ;
	private String sql = "" ;


	public DashGerant(){
		c = new Connexion();
	}

	public String getNomGerant(String idUser, String table) {
		String name = "" ;
		c.doRead("select nom,prenom from gerant where login ='" 
				+idUser+ "' ");
		if(c.hasNextLine()){
			try{
				name = c.result.getString("nom");
				name += " "+ c.result.getString("prenom");
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return name;
	}
	public void changePriceProduct(String id , String p) throws SQLException {
		String sql = "update produit set cout = "+p+" where id_produit = '"+id+"'";
		c.doWrite(sql);
	}

	public void licencierPersonnel(String id) throws SQLException {

	}

	public String getCountRows(String table)  throws SQLException{
		int l = 0 ;
		c.doRead("select count(*) as n from "+table);
		if(c.hasNextLine()){
			len = c.result.getString("n");
		}
		return len ;
	}

	public String[][] getListsClients() throws SQLException {
		c.doRead("select count(*) as n from client");
		if(c.hasNextLine()){
			len = c.result.getString("n");
		}
		String[][] t = new String[7][ Integer.parseInt(getCountRows("client"))] ;
		String sql = "select id_client,nom_societe " +
				",suffixe_societe," +
				"adresse_client,ville_client,pays_client,telephone " +
				"from client ";
		System.out.println(sql);
		c.doRead(sql);
		int i=1;
		System.out.println("Numéro du Client | Nom Société  | Suffixe Société | Adresse | Ville | Pays | Téléphone ");
		System.out.println("---------------------------------------------------------------------------------------");
		while(c.hasNextLine()){
			try{
				t[0][i] = c.result.getString("id_client");
				t[1][i] = c.result.getString("nom_societe");
				t[2][i] = c.result.getString("suffixe_societe");
				t[3][i] = c.result.getString("adresse_client");
				t[4][i] = c.result.getString("ville_client");
				t[5][i] = c.result.getString("pays_client");
				t[6][i] = c.result.getString("telephone");
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			System.out.println(t[0][i] + "   |   " + t[1][i] +
					"   |   "+t[2][i] + "   |   " + t[3][i] +
					"   |   "+t[4][i] + "   |   " + t[5][i] + "   |   "+t[6][i] );
		}
		return t;
	}


	public void getListsEmployesEmballeur() throws SQLException {
		c.doRead("select id_emballeur, nom_emballeur , prenom_emballeur from emballeur");
		String liste = "" ;
		System.out.println("Identifiant | Nom   | Prenom | ");
		System.out.println("-------------------------------");

		while(c.hasNextLine()){
			liste = c.result.getString("id_emballeur");
			liste += " | "+c.result.getString("nom_emballeur");
			liste += " | "+c.result.getString("prenom_emballeur");
			System.out.println(liste + " | ");
		}
	}


	public void getListsEmployesTansporteur() throws SQLException {
		c.doRead("select code_scac, nom_transporteur from transporteur");
		String liste = "" ;
		System.out.println("Identifiant | Nom   | ");
		System.out.println("-------------------------------");

		while(c.hasNextLine()){
			liste = c.result.getString("code_scac");
			liste += " | "+c.result.getString("nom_transporteur");
			System.out.println(liste + " | ");
		}
	}

	public int getTotalEmployes() throws SQLException {
		int total = 0 ;
		total = Integer.parseInt(getCountRows("transporteur")) +  Integer.parseInt(getCountRows("emballeur"))  ;
		return total;
	}


	public void getNumberColisByEmballeur(String e) throws SQLException {
		String sql_bis , em = ""  ;
		System.out.println("Emballeur | Nombre_colis_par_jour   | Date_emballage ");
		System.out.println("-------------------------------");
		if (!e.equalsIgnoreCase("")){
			sql_bis = "and id_emballeur = '"+e+"'" ;
			if(isExistEmballeur(e)){
				String sql = "select count(id_emballeur) as Nombre_colis , id_emballeur," +
						" date_emballage from colis c " +
						"natural join emballeur e" +
						" where c.id_emballeur = e.id_emballeur  " + sql_bis +
						"group by id_emballeur ,  date_emballage order by date_emballage ";
				c.doRead(sql);
				while(c.hasNextLine()){
					em = c.result.getString("id_emballeur");
					em += " | "+c.result.getString("Nombre_colis");
					em += " | "+c.result.getString("date_emballage");
					System.out.println(em + " | ");
				}
			}else {
				System.out.println("Idenifiant Emballeur # "+e+" n'existe pas !");
			}
		}else {
			sql_bis = "" ;
			String sql = "select count(id_emballeur) as Nombre_colis , id_emballeur," +
					" date_emballage from colis c " +
					"natural join emballeur e" +
					" where c.id_emballeur = e.id_emballeur  " + sql_bis +
					"group by id_emballeur ,  date_emballage order by date_emballage ";
			c.doRead(sql);
			while(c.hasNextLine()){
				em = c.result.getString("id_emballeur");
				em += " | "+c.result.getString("Nombre_colis");
				em += " | "+c.result.getString("date_emballage");
				System.out.println(em + " | ");
			}
		}



	}

	public boolean isExistEmballeur(String idx){
		String sql = "select * from emballeur e WHERE e.id_emballeur = '"+idx+"'  " ;
		c.doRead(sql);
		while ( c.hasNextLine()) {
			return true;
		}
		return false ;
	}

	public void getProduitsPlusVendus() throws SQLException {
		String liste = "" ;
		String sql ="select sum(quantite) as somme_quantite , lc.id_produit" +
				" from ligne_commande lc left join produit p on lc.id_produit = p.id_produit" +
				" group by lc.id_produit" +
				" order by somme_quantite desc" ;
		c.doRead(sql);
		System.out.println("Numéro du produit | Somme_quantite ");
		while(c.hasNextLine()){
			liste = c.result.getString("id_produit");
			liste += " | "+c.result.getString("somme_quantite");
			System.out.println(liste + " | ");
		}
	}


	public void getClientsPlusDepensiers() throws SQLException {
		String liste = "" ;
		String sql = "  select sum(quantite * cout) as Somme_quantite_prix, co.id_client , nom_societe , pays_client" +
				" from ligne_commande lc , commande co , produit p , client c  where (lc.id_produit = p.id_produit " +
				" and lc.id_commande = co.id_commande) and ( c.id_client = co.id_client ) " +
				" group by co.id_client, nom_societe, pays_client " +
				" order by Somme_quantite_prix desc " ;
	
		c.doRead(sql);
		System.out.println("Somme_quantite_prix | Id_client | Nom_societe | Pays_client ");
		System.out.println("--------------------+-----------+-------------+--------------");
		while(c.hasNextLine()){
			liste = c.result.getString("Somme_quantite_prix");
			liste += " | "+c.result.getString("id_client");
			liste += " | "+c.result.getString("nom_societe");
			liste += " | "+c.result.getString("pays_client");
			System.out.println(liste + " | ");
		}
	}

}
