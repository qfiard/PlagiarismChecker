package version1;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TraitementDonnee {

	public TraitementDonnee(String path) {

		try {
			supprimeEmballeur();
			supprimeClient();
			supprimeDouane();
			supprimeProduit();
			supprimeTransporteur();
			supprimeGerant();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		InputStreamReader lecture;
		BufferedReader br;

		try {
			lecture = new InputStreamReader(new FileInputStream(path));
			br = new BufferedReader(lecture);

			String line;
			String[] tab;

			while ((line = br.readLine()) != null) {

				tab = line.split("\\|");

				switch (Integer.parseInt(tab[0])) {
				// emballeur
				case 10:
					insertEmballeur(tab);
					break;
				// client
				case 20:
					insertClient(tab);
					break;
				// produit
				case 30:
					insertProduit(tab);
					break;
				// transporteur
				case 40:
					insertTransporteur(tab);
					break;
				// douane
				case 50:
					insertDouane(tab);
					break;
				// gerant
				case 60:
					insertGerant(tab);
					break;
				default:
					System.out.println("error");
					break;
				}

			}

			lecture.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Everything its ok !!");
	}

	// OK
	public void insertEmballeur(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO emballeur (refemballeur, nom, prenom, taux_erreur, mdp) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[2]
							+ "','"
							+ tab[3]
							+ "','"
							+ Integer.parseInt(tab[4])
							+ "','"
							+ tab[5] + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// OK
	public void insertClient(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO client (refclient, login, nom, suffixe, adresse, ville, cpostale, pays, telephone, mdp) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[1]
							+ "','"
							+ tab[2]
							+ "','"
							+ tab[3]
							+ "','"
							+ tab[4]
							+ "','"
							+ tab[5]
							+ "','"
							+ tab[6]
							+ "','"
							+ tab[7]
							+ "','"
							+ tab[8] + "','" + tab[9] + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// OK
	public void insertProduit(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO produit (refprod, description, qtcarton, cartpalette, qualifiant, prix, tva, poids, stock) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[2]
							+ "','"
							+ Integer.parseInt(tab[3])
							+ "','"
							+ Integer.parseInt(tab[4])
							+ "','"
							+ tab[5]
							+ "','"
							+ Integer.parseInt(tab[6])
							+ "','"
							+ Integer.parseInt(tab[7])
							+ "','"
							+ Integer.parseInt(tab[8])
							+ "','"
							+ Integer.parseInt(tab[9]) + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// OK
	public void insertTransporteur(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO transporteur (reftransp, nom, mdp) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[2]
							+ "','"
							+ tab[3] + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// OK
	public void insertDouane(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO douane (pays, mdp, taux_colis_verif) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[4]
							+ "','"
							+ Integer.parseInt(tab[2]) + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// OK mais faudrait inverser le nom et prenom
	public void insertGerant(String[] tab) {
		Statement state;
		try {
			state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			state
					.executeUpdate("INSERT INTO gerant (prenom, nom, login, mdp) "
							+ "VALUES('"
							+ tab[1]
							+ "','"
							+ tab[2]
							+ "','"
							+ tab[3] + "','" + tab[4] + "'" + ")");

			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	// ------------------ SUPPRESSION ET CREATION ---------------------------------------------------
	
	
	public void supprimeEmballeur() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table emballeur cascade");
			System.out.println("SUPPRESSION EMBALLEUR OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE emballeur ("
						+ "refemballeur 	       	varchar(20) PRIMARY KEY,"
						+ "nom		       	varchar(20),"
						+ "prenom		       	varchar(20),"
						+ "taux_erreur        	integer check (taux_erreur>=0),"
						+ "mdp		       	varchar(20)"
						+ ")");
				System.out.println("CREATION EMBALLEUR OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}

	public void supprimeClient() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table client cascade");
			System.out.println("SUPPRESSION CLIENT OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE client ("
						+ "refclient    varchar(20) PRIMARY KEY,"
						+ "login	     varchar(20) UNIQUE,"
						+ "nom 	     varchar(20)," + "suffixe	     varchar(20),"
						+ "adresse	     varchar(40)," + "ville	     varchar(20),"
						+ "cpostale     varchar(10)," + "pays	     varchar(20),"
						+ "telephone    varchar(20)," + "mdp	     varchar(20)"
						+ ")");
				System.out.println("CREATION CLIENT OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeGerant() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table gerant cascade");
			System.out.println("SUPPRESSION GERANT OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("create table gerant ("
						+ "nom    	     varchar(20)," + "prenom       varchar(20),"
						+ "login	     varchar(20)," + "mdp	     varchar(20),"
						+ "primary key (login)" 
						+ ")");
				System.out.println("CREATION GERANT OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeProduit() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table produit cascade");
			System.out.println("SUPPRESSION PRODUIT OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE produit ("
						+ "refprod      	  	varchar(30) PRIMARY KEY,"
						+ "description 		varchar(120),"
						+ "qtcarton	      	integer  check (qtcarton>0),"
						+ "cartpalette	      	integer check (cartpalette>0),"
						+ "qualifiant   	      	char check (qualifiant IN ('N','D','F')),"
						+ "prix	   	   	decimal(10,2) check (prix>0),"
						+ "tva     	   	integer check (tva>=0),"
						+ "poids	     	      	integer check (poids>0),"
						+ "stock	     	      	integer" 
						+ ")");
				System.out.println("CREATION PRODUIT OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeTransporteur() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table transporteur cascade");
			System.out.println("SUPPRESSION TRANSPORTEUR OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE transporteur ("
						+ "reftransp    	  	   varchar(5) PRIMARY KEY,"
						+ "nom			   varchar(20)," + "mdp			   varchar(20)" 
						+ ")");
				System.out.println("CREATION TRANSPORTEUR OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeDouane() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table douane cascade");
			System.out.println("SUPPRESSION DOUANE OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE douane ("
						+ "pays	    varchar(25) PRIMARY KEY,"
						+ "mdp	    varchar(20)," + "taux_colis_verif integer" 
						+ ")");
				System.out.println("CREATION DOUANE OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}

	public void supprimeCommande() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table commande cascade");
			System.out.println("SUPPRESSION COMMANDE OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE commande ("
						+ "refcommande  	integer PRIMARY KEY,"
						+ "refclient		varchar(20),"
						+ "date_livraison 	date,"
						+ "etat_com		varchar(25) check (etat_com IN ('non expediee','partiellement expedies','expedies')),"
						+ "prix_com		decimal(10,2) check (prix_com>0),"
						+ "controle_com	integer check (controle_com IN (0,1)),"
						+ "FOREIGN KEY (refclient) REFERENCES client"
						+ ")");
				System.out.println("CREATION COMMANDE OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeConteneur() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table conteneur cascade");
			System.out.println("SUPPRESSION EMBALLEUR OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE conteneur ("
						+ "refconteneur 	integer PRIMARY KEY,"
						+ "mtransport 		varchar(20) check (mtransport IN ('camion','avion')),"
						+ "destination		varchar"
						+ ")");
				System.out.println("CREATION EMBALLEUR OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimePalette() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table palette cascade");
			System.out.println("SUPPRESSION PALETTE OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE palette ("
						+ "refpalette		integer PRIMARY KEY,"
						+ "refconteneur	integer,"
						+ "FOREIGN KEY(refconteneur) REFERENCES conteneur"
						+ ")");
				System.out.println("CREATION PALETTE OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeColis() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table colis cascade");
			System.out.println("SUPPRESSION COLIS OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE colis ("
						+ "refcolis		integer PRIMARY KEY,"
						+ "refcommande		integer,"
						+ "etat_col	    varchar(25) check (etat_col IN ('initial','emballe','rejete','envoye','arrivee')),"
						+ "date_emb      	date,"
						+ "refpalette    	integer,"
						+ "reftransporteur	varchar(5),"
						+ "controle_col	integer check (controle_col IN (0,1)), "
						+ "motif			varchar(100),"
						+ "FOREIGN KEY(refcommande) REFERENCES commande"
						+ ")");
				System.out.println("CREATION COLIS OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeQtecommande() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table qtecommande cascade");
			System.out.println("SUPPRESSION QTECOMMANDE OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE qtecommande ("
						+ "CREATE TABLE qtecommande("
						+ "refcommande		integer,"
						+ "refprod			varchar(30),"
						+ "quantite		integer check (quantite>0),"
						+ "FOREIGN KEY(refcommande) REFERENCES commande,"
						+ "FOREIGN KEY(refprod) REFERENCES produit,"
						+ "PRIMARY KEY(refcommande,refprod)"
						+ ")");
				System.out.println("CREATION QTECOMMANDE OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeQtecolis() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table qtecolis cascade");
			System.out.println("SUPPRESSION QTECOLIS OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE qtecolis ("
						+ "refcolis		integer,"
						+ "refprod			varchar(30),"
						+ "quantite		integer check (quantite>0),"
						+ "FOREIGN KEY(refcolis) REFERENCES colis,"
						+ "FOREIGN KEY(refprod) REFERENCES produit,"
						+ "PRIMARY KEY(refcolis,refprod)"
						+ ")");
				System.out.println("CREATION QTECOLIS OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
	
	public void supprimeEmballage() throws SQLException {
		Statement state = Main.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		boolean b = true;
		try {
			state.executeUpdate("drop table emballage cascade");
			System.out.println("SUPPRESSION EMBALLAGE OK !!");
			b = false;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		
		if(!b) {
			try {
				state.executeUpdate("CREATE TABLE emballage ("
						+ "refemballage	integer,"
						+"refemballeur 		varchar(20),"
						+ "type_emb	varchar(15) check (type_emb IN ('Normal','Special')),"
						+ "refcolis	integer,"
						+ "FOREIGN KEY (refcolis) REFERENCES colis,"
						+ "FOREIGN KEY(refemballeur) REFERENCES emballeur"
						+ ")");
				System.out.println("CREATION EMBALLAGE OK !!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		state.close();
	}
}