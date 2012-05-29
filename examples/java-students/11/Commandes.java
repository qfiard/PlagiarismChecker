package General;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;



public class Commandes {

	Connexion conn;
	Statement st;
	ResultSet rs;

	String num_client;
	int num_commande=1; 
	String num_produit;
	int quantite_prod;
	int reserve=1;
	int prix_cmd=0;
	// 50 cmd non expediees

	int id_colis=1;
	int quantite_par_carton;
	int poids_prod;
	int poids_colis;
	int carton_par_palette;
	String num_emballeur;
	String qualifiant;
	// 20 cmd a moitie expediees

	int id_palette=1;
	int colis_par_palette;
	int poids_palette=0;
	int limite_poids_palette;
	int id_conteneur=1;
	String cmd_pays_destination;
	String palette_pays_destination;
	String destination_conteneur;
	String num_transporteur;
	// 180 cmd 100% expediees

	public Commandes(){

		Connexion conn;
		try {
			conn = new Connexion("attia", "nelson");
			st = conn.getStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


	public void genererCmd(){

		int limiteClient=1;
		int limiteProduit=1;
		int nbProduitsCommandes=1;
		int limiteEmballeur=1;
		int limitePalette=1;
		int nbAleatoire;
		java.util.Random rand = new java.util.Random();

		LinkedList<String> num_clients=new LinkedList<String>();
		LinkedList<String> num_produits=new LinkedList<String>();
		LinkedList<String> num_emballeurs=new LinkedList<String>();
		LinkedList<String> num_transporteurs=new LinkedList<String>();

		try {

			ResultSet rsClients = st.executeQuery("SELECT num_client FROM client;");
			while(rsClients.next()) num_clients.add(rsClients.getString(1));
			rsClients.close();
			ResultSet rsProduits = st.executeQuery("SELECT num_produit FROM produit WHERE reserve>0;");
			while(rsProduits.next()) num_produits.add(rsProduits.getString(1));
			rsProduits.close();
			ResultSet rsEmballeurs = st.executeQuery("SELECT num_emballeur FROM emballeur;");
			while(rsEmballeurs.next()) num_emballeurs.add(rsEmballeurs.getString(1));
			rsEmballeurs.close();
			ResultSet rsTransporteurs = st.executeQuery("SELECT num_emballeur FROM emballeur;");
			while(rsTransporteurs.next()) num_transporteurs.add(rsTransporteurs.getString(1));
			rsTransporteurs.close();


			// on compte le nombre de clients pour ensuite utiliser la fonction random jusqu a ce nombre
			limiteClient = num_clients.size();

			// on compte le nombre de produits pour ensuite utiliser la fonction random jusqu a ce nombre
			limiteProduit = num_produits.size();

			// on compte le nombre d emballeur pour ensuite utiliser la fonction random jusqu a ce nombre
			limiteEmballeur = num_emballeurs.size();


			// boucle for pour generer 250 commandes non expediees
			for(int i=0; i<250; i++){

				// un client au hasard
				nbAleatoire = rand.nextInt(limiteClient)+1;

				//  on recupere le numero du client

				num_client = num_clients.get(nbAleatoire);

				// on estime a 10 le nombre de produits differents qu un client peut commander
				nbProduitsCommandes = rand.nextInt(10)+1;
				for(int pc=0; pc<nbProduitsCommandes; pc++ ){
					// un produit au hasard
					nbAleatoire = rand.nextInt(limiteProduit)+1;

					//on recupere le num du produit
					num_produit=num_produits.get(nbAleatoire);

					// on recupere le nombre de produits restants dans reserve pour ensuite utiliser la fonction random jusqu a ce nombre
					ResultSet rsReserve = st.executeQuery("SELECT reserve FROM produit WHERE num_produit='"+num_produit+"';");

					// si la ligne existe on recupere le nombre de produits restants
					if(rsReserve.next()) reserve = rsReserve.getInt(1);
					rsReserve.close();

					// si la reserve est vide il n a pas commande ce produit donc un produit en moins commande
					if(reserve==0){
						pc--;
					}
					// on commande un nombre aleatoire de ce produit dans les limites de la reserve
					else{
						nbAleatoire = rand.nextInt(reserve)+1;
						quantite_prod = nbAleatoire;
						reserve = reserve - quantite_prod;
						rs = st.executeQuery("SELECT cout FROM produit WHERE num_produit='"+num_produit+"';");
						if(rs.next()) prix_cmd += (rs.getInt(1)*quantite_prod);
						rs.close();
						// on remplit la table contientCmd avec num_commande num_produit et quantite_prod
						st.executeUpdate("INSERT INTO contientcmd VALUES ("+num_commande+",'"+num_produit+"',"+quantite_prod+");");
						// et modifier reserve dans produit
						st.executeUpdate("UPDATE produit SET reserve="+reserve+" WHERE num_produit='"+num_produit+"';");
					}



				} // fin de la commande du client

				// on remplit la table commande
				st.executeUpdate("INSERT INTO commande VALUES("+num_commande+",'"+num_client+"',current_date,null,"+prix_cmd+",'non expediee';");


			} // fin boucle for pour generer les 250 commandes non expediees

			// boucle for pour generer 200 commandes partiellement expediees parmi les 250 non expediees
			for(int i=0; i<200; i++){

				// un emballeur au hasard
				nbAleatoire = rand.nextInt(limiteEmballeur)+1;

				num_emballeur = num_emballeurs.get(nbAleatoire);

				// une commande au hasard
				num_commande = rand.nextInt(250)+1;
				rs = st.executeQuery("SELECT num_produit, quantite_prod, quantite_par_carton, qualifiant, poids "+
						"FROM contientcmd "+
						"NATURAL JOIN produit, commande "+
						"WHERE num_commande="+num_commande+" "+
						"AND commande.num_commande="+num_commande+" "+
						"AND commande.etat='non expediee';");

				// on parcours les resultats d une commande
				double a; int j ; int cpt=0; int produits_restants;
				while(rs.next()){
					num_produit = rs.getString(1);
					quantite_prod = rs.getInt(2); 
					quantite_par_carton = rs.getInt(3);
					qualifiant = rs.getString(4);
					poids_prod = rs.getInt(5);
					poids_colis=poids_prod*quantite_par_carton;
					
					// si tous les colis seront pleins
					if(quantite_prod%quantite_par_carton==0){
						// j est la quantite de colis correspondant au meme produit
						j=quantite_prod/quantite_par_carton;
						
						while(cpt!=j){
							st.executeUpdate("INSERT INTO colis VALUES ("+id_colis+","+num_commande+",'"+num_emballeur+"','"+num_produit+"',"+quantite_par_carton+",'"+qualifiant+"',"+poids_colis+",current_date + interval '1 week',null;");
							id_colis++;
							cpt++;
						}
					}
					// s il reste un colis non plein
					else{
						a = quantite_prod/quantite_par_carton;
						j = (int)a;
						while(cpt!=j){
							st.executeUpdate("INSERT INTO colis VALUES ("+id_colis+","+num_commande+",'"+num_emballeur+"','"+num_produit+"',"+quantite_par_carton+",'"+qualifiant+"',"+poids_colis+",current_date+interval '1 week',null;");
							id_colis++;
							cpt++;
						}
						produits_restants=quantite_prod%quantite_par_carton;
						poids_colis = poids_prod*produits_restants;
						st.executeUpdate("INSERT INTO colis VALUES ("+id_colis+","+num_commande+",'"+num_emballeur+"','"+num_produit+"',"+produits_restants+",'"+qualifiant+"',"+poids_colis+",current_date+interval '1 week',null;");
						id_colis++;
					}

				} // fin du while qui parcours les resultats d une commande

				st.executeUpdate("UPDATE commande SET etat='partiellement expediees' WHERE num_commande="+num_commande+";");
				rs.close();

			} // fin boucle for pour generer 200 commandes partiellement expediees parmi les 250 non expediees


			// boucle for pour generer 180 commandes 100% expediees parmi les 200 partiellement expediees
			for(int i=0; i<180; i++){

				// un emballeur au hasard qui met les colis dans une palette
				nbAleatoire = rand.nextInt(limiteEmballeur)+1;
				num_emballeur = num_emballeurs.get(nbAleatoire);

				rs = st.executeQuery("SELECT num_commande, id_colis, poids_colis from colis WHERE id_colis="+id_colis+" ORDER BY num_commande;");
				
				
				ResultSet rsPays, rsPalette;
				// on parcourt les colis
				while(rs.next()){
					num_commande = rs.getInt(1);
					id_colis = rs.getInt(2);
					poids_colis = rs.getInt(3);
					rsPays = st.executeQuery("SELECT pays from client NATURAL JOIN commande WHERE num_commande="+num_commande+";");
					if(rsPays.next()) cmd_pays_destination = rsPays.getString(1);
					rsPays.close();
					// on parcourt les palettes
					rsPalette = st.executeQuery("SELECT id_palette,poids,destination_palette from palette;");
					while(rsPalette.next()){
						// si la palette est vide elle n'a pas encore de champs destination donc on rempli ce champs avec le pays de destination du colis en cours de traitement
						if(rsPalette.getString(3)==null) palette_pays_destination = cmd_pays_destination;
						
						// si la palette n'est pas remplie ou si le pays de destination est toujours le meme alors on continue de remplir la palette
						if(poids_palette!=limite_poids_palette || !cmd_pays_destination.equals(palette_pays_destination)){

							st.executeUpdate("INSERT INTO palette VALUES ("+id_palette+","+id_colis+",current_date + interval '2 week',null,'"+num_emballeur+"',"+palette_pays_destination+");");
							poids_palette+=poids_colis;

						} // fin du if pour remplir une meme palette
						rsPalette.close();
					}
					// on modifie le champs poids_palette dans palette apres avoir ajouter tous les colis
					ResultSet rsPoidsPalette = st.executeQuery("SELECT poids_palette from palette WHERE id_palette="+id_palette+";");
					while(rsPoidsPalette.next()) st.executeUpdate("UPDATE palette SET poids_palette="+poids_palette+";");
					poids_palette=0;
					id_palette++;

					rsPoidsPalette.close();

				} // fin du while qui parcours les colis


			} // fin boucle for pour generer 180 commandes 100% expediees parmi les 200 partiellement expediees

			id_palette=1;
			
			rs = st.executeQuery("SELECT count(*) FROM palette;");
			if(rs.next()) limitePalette = rs.getInt(1);
			rs.close();
			
			// on met les palettes dans les conteneurs
			ResultSet rsPalette = st.executeQuery("SELECT destination from palette ORDER BY destination");
			if(rsPalette.next()){ 
				destination_conteneur = rsPalette.getString(1);
				// on parcourt les palettes

				do{
					palette_pays_destination=rsPalette.getString(1);
					if(destination_conteneur.equals(palette_pays_destination)){
						st.executeUpdate("INSERT INTO conteneur VALUES("+id_conteneur+","+id_palette+",null,'"+destination_conteneur+"';");
					}
					else{
						id_conteneur++;
						destination_conteneur = palette_pays_destination;
						st.executeUpdate("INSERT INTO conteneur VALUES ("+id_conteneur+","+id_palette+",null,'"+destination_conteneur+"';");

					}
					id_palette++;
				}while(rsPalette.next()); // fin du do while 

			} // fin du if

			rsPalette.close();
			
			


		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]){
		Commandes cmd = new Commandes();
		cmd.genererCmd();
	}



}
