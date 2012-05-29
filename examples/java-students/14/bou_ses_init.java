import java.awt.event.*;
import java.io.*;
import java.sql.*;


public class bou_ses_init implements ActionListener {

	private Connection con;
	private Statement sql;
	//private DatabaseMetaData dbmd;
	
	private String [] a_name_tables = {
			"user_db",
			"client",
			"emballeur",
			"transporteur",
			"douane",
			"gerant",
			"produit",
			"commande",
			"produit_commande",
			"conteneur",
			"palette",
			"emballage",
			"controle_douane"
	};
	
	private String [] q_user_db = {"CREATE TABLE user_db(id_user serial,login char(15) NOT NULL,password char(20) NOT NULL,type char(3) CHECK ( type IN ('CLI','ADM','GER','DOU','TRA','EMB') ));"
			,"CREATE TABLE client (id_client char(15) PRIMARY KEY,nom_societe char(20),suffixe_societe char(10),adresse char(30),ville char(15),code_postal char(8),pays char(20),telephone char(20)) INHERITS (user_db);"
			,"CREATE TABLE emballeur (id_emballeur char(15) PRIMARY KEY, nom_emballeur char(20), prenom_emballeur char(20), taux_erreur int) INHERITS (user_db);"
			,"CREATE TABLE transporteur	(code_transporteur char(10) PRIMARY KEY,nom_transporteur char(20)) INHERITS (user_db);"
			,"CREATE TABLE douane (pays char(20) PRIMARY KEY,taux_verif int) INHERITS (user_db);"
			,"CREATE TABLE gerant (login_gerant char(15) PRIMARY KEY, prenom_gerant char(20), nom_gerant char(20)) INHERITS (user_db);"
			,"CREATE TABLE produit (id_produit char(20) primary key, quantite_max int, qualif char(1) CHECK ( qualif in ('F','D','N') ), prix int, description char(100), poids int, taux_augmentation int);"
			,"CREATE TABLE commande (id_commande serial, id_client char(20), date_prevue date, prix int, etat char(5) CHECK ( etat IN ('EXP','PEXP','NEXP') ), primary key (id_commande), FOREIGN KEY (id_client) REFERENCES client (id_client));"
			,"CREATE TABLE produit_commande (id_produit char(20), id_commande int, PRIMARY KEY (id_produit, id_commande), FOREIGN KEY (id_produit) REFERENCES produit (id_produit), FOREIGN KEY (id_commande) REFERENCES commande (id_commande));"
			,"CREATE TABLE conteneur(id_conteneur serial PRIMARY KEY, destination char(20), code_transporteur char(10), nb_palettes int, FOREIGN KEY (code_transporteur) REFERENCES transporteur (code_transporteur));"
			,"CREATE TABLE palette(id_palette serial PRIMARY KEY, id_produit char(20), id_conteneur int, nb_produits int, nb_produits_max int, FOREIGN KEY (id_produit) REFERENCES produit (id_produit), FOREIGN KEY (id_conteneur) REFERENCES conteneur (id_conteneur));"
			,"CREATE TABLE emballage(id_emballage serial PRIMARY KEY, id_produit char(20), id_commande int, id_emballeur char(20), date_emballage date, id_palette int, FOREIGN KEY (id_commande) REFERENCES commande (id_commande), FOREIGN KEY (id_emballeur) REFERENCES emballeur (id_emballeur), FOREIGN KEY (id_produit) REFERENCES produit (id_produit), FOREIGN KEY (id_palette) REFERENCES palette (id_palette));"
			,"CREATE TABLE controle_douane (id_controle serial, date_controle date, valide boolean, commentaire char(100), pays char(20), id_commande int, FOREIGN KEY (id_commande) REFERENCES commande (id_commande), FOREIGN KEY (pays) REFERENCES douane (pays));"
	};

	public void creation() {
		//System.out.println("\n** Creation des tables **\n");
		for(int i = 0; i<q_user_db.length ; i++){
			try {
				sql.executeQuery(q_user_db[i]);
			} catch (SQLException e1) {
				//System.out.println("Anomalie lors de l'execution de la requete");
			}
			//System.out.println("Table "+a_name_tables[i]+" cree\n");

		}
		System.out.println("\nCreation des tables terminee\n");
	}
	
	public void suppression() {
		//System.out.println("\n** Suppression des tables **\n");
		for(int i = 0; i<a_name_tables.length ; i++){
			try {
				sql.executeQuery("DROP TABLE "+a_name_tables[i]+" CASCADE;");
			} catch (SQLException e1) {
				//System.out.println("Anomalie lors de la suppression");
			}  
			//System.out.println("Table "+a_name_tables[i]+" supprimee\n");
		}
		System.out.println("\nNettoyage de la base termine\n");
	}
	
	public void insert(Statement sql) {
		try {
			insert_bd.insert(sql, Config.getDataPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
        	try {
				//connexion a la database
				database_connection connection = new database_connection();

			con = connection.getConn();
			con.getMetaData();
			sql = con.createStatement();
			
			suppression();
			
			creation();
			
			insert(sql);
			
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				System.out.println("Erreur");
			}
        	
			GenerationCommandes gcm = new GenerationCommandes(sql);
			gcm.generateCommandes();
        	
			GenerationColis gco = new GenerationColis(sql);
			gco.generateColis();
			
			GenerationControles gct = new GenerationControles(sql);
			gct.generateControles();
	}

}
