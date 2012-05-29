import java.sql.*;

public class ConnectionProjet {
	private Connection conn;
	private Statement st, st1;
	private PreparedStatement pstmt;
	private ResultSet result, result1;
	
	public ConnectionProjet(String login, String motPasse) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", login, motPasse);
	}
	
	public void close() throws SQLException { 
		conn.close();
	}
	
	// Identification
	public char connexion(String login, String mdp) throws SQLException {
		st = conn.createStatement();
		st1 = conn.createStatement();
		result = st.executeQuery("SELECT id, mdp, autorisation FROM logins WHERE login='" + login +"';");
		if (result.next()) {
			if (result.getString(2).equals(mdp)) {
				if (result.getString(3).equals("E") || result.getString(3).equals("T")) {
					result1 = st1.executeQuery("SELECT last_day, CURRENT_DATE FROM employes WHERE id=" + result.getInt(1) +";");
					if (result1.next() && result1.getDate(1) != null && !result1.getDate(2).equals(result1.getDate(1))) {
						st1.executeUpdate("UPDATE employes SET last_day = CURRENT_DATE, nb_jours=nb_jours+1 WHERE id=" + result.getInt(1) + ";");
					} else if (result1.getDate(1) == null) {
						st1.executeUpdate("UPDATE employes SET last_day = CURRENT_DATE, nb_jours=1 WHERE id=" + result.getInt(1) + ";");
					}
				}
				return result.getString(3).charAt(0);
			}
		}
		return 'X';
	}
	
	public int recupId(String login) throws SQLException {
		st = conn.createStatement();
		result = st.executeQuery("SELECT id FROM logins WHERE login='" + login +"';");
		if (result.next())
			return result.getInt(1);
		return -1;
	}
	
	// Gérant
	// Gestion des produits
	public void updatePrixProd(int id, double prix) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE produits SET cout=? WHERE id_prod=?;");
		pstmt.setDouble(1, prix);
		pstmt.setInt(2, id);
		pstmt.execute();
		pstmt.close();
	}
	
	public void updateStockProd(int id, int quantite) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE produits SET stock=stock+? WHERE id_prod=?;");
		pstmt.setInt(1, quantite);
		pstmt.setInt(2, id);
		pstmt.execute();
		pstmt.close();
	}
	
	// Lister
	public ResultSet listeClients() throws SQLException {
		st = conn.createStatement();
		return st.executeQuery("SELECT nom, prenom, societe, adresse, code_p, pays, telephone, depenses " +
				"FROM clients;");
	}
	
	public ResultSet listeEmployes() throws SQLException {
		st = conn.createStatement();
		return st.executeQuery("SELECT id_employe, role, nom, prenom, nb_jours, last_day, nb_colis, nb_refus, actif " +
				"FROM employes;");
	}
	
	/*
	 * /!\ A ne pas mettre ensemble :
	 * 		- douane/transporteur/emballeur + id_client
	 * 		- douane/transporteur/emballeur/client + id_emb
	 * 	Impossible de cumuler les jobs
	 */
	public ResultSet listeCommandes(boolean gerant, boolean douane, boolean transporteur, boolean emballeur, boolean refusee, int id_client, int id_emb, int id_colis, int id_comm) throws SQLException {
		// Gérant
		st = conn.createStatement();
		String requete;
		String ajout = " WHERE ";
		if (gerant)
			requete = "SELECT c.id_comm, c.id_client, cl.nom AS nom_client, cl.prenom, p.id_prod, p.nom AS nom_prod, p.description, " +
							"p.cout*c.quantite AS prix_total, p.poids_g*c.quantite AS poids_total, c.quantite, p.genre, c.id_colis, c.id_emb, c.etat, c.date_livraison, col.date_emb  " +
							"FROM commandes AS c " +
							"LEFT JOIN clients AS cl ON cl.id_client =c.id_client " +
							"LEFT JOIN produits AS p ON p.id_prod=c.id_prod " +
							"LEFT JOIN colis AS col ON col.id_colis=c.id_colis ";
		else if (douane)
			requete = "SELECT c.id_comm, p.nom AS nom_prod, p.description, " +
					"p.cout*c.quantite AS prix_total, p.poids_g*c.quantite AS poids_total,p.cout AS prix_unitaire, p.genre, c.quantite, c.id_colis, c.etat " +
					"FROM commandes AS c " +
					"LEFT JOIN produits AS p ON p.id_prod = c.id_prod ";
		else if (transporteur)
			requete = "SELECT c.id_comm, " +
					"p.poids_g*c.quantite AS poids_total, p.genre, c.id_colis, c.date_livraison " +
					"FROM commandes AS c " +
					"LEFT JOIN produits AS p ON p.id_prod = c.id_prod ";
		else if (emballeur)
			requete = "SELECT c.id_comm, " +
					"p.poids_g*c.quantite AS poids_total, p.genre, c.etat " +
					"FROM commandes AS c " +
					"LEFT JOIN produits AS p ON p.id_prod = c.id_prod ";
		else
			requete = "SELECT c.id_comm, c.id_client, cl.nom AS nom_client, cl.prenom, p.id_prod, p.nom AS nom_prod, p.description, " +
					"p.cout*c.quantite AS prix_total, p.poids_g*c.quantite AS poids_total, c.quantite, p.genre, c.id_colis, c.etat, c.date_livraison " +
					"FROM commandes AS c " +
					"LEFT JOIN clients AS cl ON cl.id_client = c.id_client " +
					"LEFT JOIN produits AS p ON p.id_prod = c.id_prod ";
		if (id_client != -1)
			if (ajout.equals(" WHERE "))
				ajout += "c.id_client=" + id_client + " ";
			else
				ajout += "c.id_client=" + id_client + " ";
		if (id_emb != -1) {
			if (ajout.equals(" WHERE "))
				ajout += "c.id_emb=" + id_emb + " ";
			else
				ajout += "AND c.id_emb=" + id_emb + " ";
		}
		if (id_colis != -1) {
			if (ajout.equals(" WHERE "))
				ajout += "c.id_colis=" + id_colis + " ";
			else
				ajout += "AND c.id_colis=" + id_colis + " ";
		}
		if (id_comm != -1) {
			if (ajout.equals(" WHERE "))
				ajout += "c.id_comm=" + id_comm + " ";
			else
				ajout += "AND c.id_comm=" + id_comm + " ";
		}
		if (refusee) {
			if (ajout.equals(" WHERE "))
				ajout += "c.etat='refusé' ";
			else
				ajout += "AND c.etat='refusé' ";
		}
		if (!ajout.equals(" WHERE "))
			requete += ajout;
		return st.executeQuery(requete);
	}
	
	// Douane uniquement
	public ResultSet listeCommandes(int id_prod, boolean controlee, boolean non_controlee, int id_colis, int id_comm, int id, String[] destination) throws SQLException {
		st = conn.createStatement();
		String requete = "SELECT c.id_comm, p.id_prod, p.nom AS nom_prod, p.description, cl.adresse, cl.code_p, cl.ville, " +
				"p.cout*c.quantite AS prix_total, p.poids_g*c.quantite AS poids_total,p.cout AS prix_unitaire, p.genre, c.quantite, c.id_colis, c.etat " +
				"FROM commandes AS c " +
				"LEFT JOIN produits AS p ON p.id_prod = c.id_prod " +
				"LEFT JOIN clients AS cl ON c.id_client = cl.id_client " +
				"LEFT JOIN douaniers AS d ON cl.pays = d.pays " +
				"WHERE d.id="+ id +" ";
		if (id_colis != -1)
			requete += "AND c.id_colis=" + id_colis + " ";
		if (id_comm != -1)
			requete += "AND c.id_comm=" + id_comm + " ";
		if (id_prod != -1)
			requete += "AND c.id_prod=" + id_prod + " ";
		if (controlee)
			requete += "AND c.controle='oui' ";
		if (non_controlee)
			requete += "AND c.controle='non' ";
		if (destination != null) {
			if (destination[0] != null)
				requete += "AND cl.adresse="+ destination[0];
			if (destination[1] != null)
				requete += "AND cl.code_p="+ destination[1];
			if (destination[2] != null)
				requete += "AND cl.ville="+ destination[2];
		}
		return st.executeQuery(requete);
	}
	
	// Colis par jour d'un emballeur
	public double emballeurColisJour(int id) throws SQLException {
		st = conn.createStatement();
		result = st.executeQuery("SELECT nb_colis/nb_jours FROM employes WHERE id_employe="+ id +";");
		if (!result.next())
			return -1.0;
		return result.getDouble(1);
	}
	
	// Produits les plus vendus
	public ResultSet produitsVendus(int limite) throws SQLException {
		st = conn.createStatement();
		if (limite>=0)
			return st.executeQuery("SELECT id_prod, nom, description, cout, vendus FROM produits ORDER BY vendus DESC LIMIT "+ limite +";");
		return st.executeQuery("SELECT id_prod, nom, description, cout, vendus FROM produits ORDER BY vendus DESC;");
	}
	
	// Clients du plus dépensier au moins
	public ResultSet depensesClients(int limite) throws SQLException {
		st = conn.createStatement();
		if (limite>=0)
			return st.executeQuery("SELECT id_client, nom, prenom, societe, telephone, depenses FROM clients ORDER BY depenses DESC LIMIT "+ limite +";");
		return st.executeQuery("SELECT id_client, nom, prenom, societe, telephone, depenses FROM clients ORDER BY depenses DESC;");
	}
	
	// Virer un emballeur
	public void virerEmballeur(int id) throws SQLException{
		pstmt = conn.prepareStatement("UPDATE employes SET actif=0 WHERE id_employe=?;");
		pstmt.setInt(1, id);
		pstmt.execute();
		pstmt.close();
		st = conn.createStatement();
		st.executeUpdate("DELETE FROM logins WHERE id=(" +
				"SELECT id FROM employes WHERE id_employe=" + id + ");");
	}
	
	// Client
	// Information sur le colis
	public ResultSet infoColis(int id_colis, int id_client) throws SQLException {
		pstmt = conn.prepareStatement("SELECT etat FROM commandes NATURAL JOIN clients WHERE id_colis=? AND id=?;");
		pstmt.setInt(1, id_colis);
		pstmt.setInt(2, id_client);
		pstmt.execute();
		result = pstmt.getResultSet();
		pstmt.close();
		return result;
	}
	
	// Lister les produits
	public ResultSet listeProduits(boolean gerant) throws SQLException {
		st = conn.createStatement();
		String requete;
		if (gerant)
			requete = "SELECT * FROM produits;";
		else
			requete = "SELECT id_prod, nom, description, genre, cout, poids_g, stock FROM produits WHERE stock>0;";
		return st.executeQuery(requete);
	}
	
	// Changer ses identifiants 
	// Code retour :
	// 0 - Réussite
	// 1 - Login déja existant
	public int changeIdentifiants(int id, String nouveau_mdp, String nouveau_login) throws SQLException {
		st = conn.createStatement();
		result = st.executeQuery("SELECT id FROM logins WHERE login='"+ nouveau_login +"';");
		if (result.next() && result.getInt(1) == id) {
			st.executeUpdate("UPDATE logins SET mdp='"+ nouveau_mdp +" WHERE id="+ id +";");
			return 0;
		} else if (!result.next()) {
			st.executeUpdate("UPDATE logins SET mdp='"+ nouveau_mdp +"', login='"+ nouveau_login +"' WHERE id="+ id +";");
			return 0;
		}
		return 1;
	}
	
	// Spécifier la date de livraison
	// date -> format : "YYYY-MM-DD"
	public void setDateLivraison(int id_comm, String date) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE commandes SET date_livraison=? WHERE id_comm=?;");
		pstmt.setDate(1, Date.valueOf(date));
		pstmt.setInt(2, id_comm);
		pstmt.execute();
		pstmt.close();
	}
	
	// Spécifier la date d'emballage
	// date -> format : "YYYY-MM-DD"
	public void setDateEmballage(int id_colis, String date) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE colis SET date_emb=? WHERE id_colis=?;");
		pstmt.setDate(1, Date.valueOf(date));
		pstmt.setInt(2, id_colis);
		pstmt.execute();
		pstmt.close();
	}
	
	// Douane
	// Refuser un colis
	public void refuseColis(int id_colis, String motif) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE commandes SET etat='refusé', controle='oui' WHERE id_colis=?;");
		pstmt.setInt(1, id_colis);
		pstmt.execute();
		pstmt.close();
		st = conn.createStatement();
		result = st.executeQuery("UPDATE colis SET motif='"+ motif +"' WHERE id_colis="+ id_colis +";");
	}
	
	// Valider uncolis
	public void controlerColis(int id_colis) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE commandes SET controle='oui' WHERE id_colis=?;");
		pstmt.setInt(1, id_colis);
		pstmt.execute();
		pstmt.close();
	}
	
	// Valider une commande
	public void controlerCommande(int id_comm) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE commandes SET controle='oui' WHERE id_comm=?;");
		pstmt.setInt(1, id_comm);
		pstmt.execute();
		pstmt.close();
	}
	
	// Lister palette
	public ResultSet listePalette(int id, int id_palette) throws SQLException { 
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		return st.executeQuery("SELECT c.id_comm, p.id_prod, p.nom AS nom_prod, p.description, cl.adresse, cl.code_p, cl.ville, " +
				"p.cout*c.quantite AS prix_total, p.poids_g*c.quantite AS poids_total, p.cout AS prix_unitaire, p.genre, c.quantite, c.id_colis, c.etat " +
				"FROM commandes AS c " +
				"LEFT JOIN produits AS p ON p.id_prod = c.id_prod " +
				"LEFT JOIN clients AS cl ON c.id_client = cl.id_client " +
				"LEFT JOIN douaniers AS d ON cl.pays = d.pays " +
				"LEFT JOIN colis AS col ON col.id_colis = c.id_colis " +
				"WHERE col.id_palette="+ id_palette + " " +
				"AND d.id="+ id +";");
	}
	
	// Statistiques douanes
	// Renvoi :
	// 0 - Combien de colis controlées
	// 1 - Combien de colis non-controlées
	// 2 - Combien de commandes controlées
	// 3 - Combien de commandes non-controlées
	// 4 - Combien de colis refusés
	// 5 - Combien de commades refusés
	public int[] statsDouane(int id) throws SQLException {
		int[] tab = new int[6];
		pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT id_colis) " +
				"FROM commandes AS c " +
				"LEFT JOIN clients AS cl ON cl.id_client=c.id_client " +
				"LEFT JOIN douaniers AS d ON d.pays=cl.pays " +
				"WHERE d.id=? AND c.controle=? ");
		pstmt.setInt(1, id);
		pstmt.setString(2, "oui");
		pstmt.execute();
		result = pstmt.getResultSet();
		if (result.next())
			tab[0] = result.getInt(1);// Combien de colis controlées
		pstmt.clearParameters();
		pstmt.setInt(1, id);
		pstmt.setString(2, "non");
		pstmt.execute();
		result = pstmt.getResultSet();
		if (result.next())
			tab[1] = result.getInt(1);// Combien de colis non-controlées
		pstmt.close();
		pstmt = conn.prepareStatement("SELECT COUNT(id_comm) " +
				"FROM commandes AS c " +
				"LEFT JOIN clients AS cl ON cl.id_client=c.id_client " +
				"LEFT JOIN douaniers AS d ON d.pays=cl.pays " +
				"WHERE d.id=? AND c.controle=? ");
		pstmt.setInt(1, id);
		pstmt.setString(2, "oui");
		pstmt.execute();
		result = pstmt.getResultSet();
		if (result.next())
			tab[2] = result.getInt(1);// Combien de commandes controlées
		pstmt.clearParameters();
		pstmt.setInt(1, id);
		pstmt.setString(2, "non");
		pstmt.execute();
		result = pstmt.getResultSet();
		if (result.next())
			tab[3] = result.getInt(1);// Combien de commandes non-controlées
		pstmt.close();
		st = conn.createStatement();
		result = st.executeQuery("SELECT COUNT(DISTINCT id_colis) " +
				"FROM commandes AS c " +
				"LEFT JOIN clients AS cl ON cl.id_client=c.id_client " +
				"LEFT JOIN douaniers AS d ON d.pays=cl.pays " +
				"WHERE d.id="+ id +" AND c.etat='refusé' ");
		if (result.next())
			tab[4] = result.getInt(1);// Combien de colis refusés
		st = conn.createStatement();
		result = st.executeQuery("SELECT COUNT(id_comm) " +
				"FROM commandes AS c " +
				"LEFT JOIN clients AS cl ON cl.id_client=c.id_client " +
				"LEFT JOIN douaniers AS d ON d.pays=cl.pays " +
				"WHERE d.id="+ id +" AND c.etat='refusé' ");
		if (result.next())
			tab[5] = result.getInt(1);// Combien de commandes refusés
		return tab;
	}
	
	// Emballeur
	// Entrer un colis
	public int creerColis(int id_emb, int[] t_id_comm) throws SQLException {
		int j;
		st = conn.createStatement();
		st.executeUpdate("INSERT INTO colis (id_emb) VALUES ("+ id_emb +");");
		result = st.executeQuery("SELECT MAX(id_colis) FROM colis WHERE id_emb="+ id_emb +";");
		result.next();
		j = result.getInt(1);
		String requete = "UPDATE commandes SET id_colis="+result.getInt(1)+", id_emb="+ id_emb +", date_emb=CURRENT_DATE WHERE id_comm="+ t_id_comm[0] +" ";
		for(int i = 1; i< t_id_comm.length; i++) {
			requete += "OR id_comm="+t_id_comm[i] + " ";
		}
		st.executeUpdate(requete);
		return j;
	}
	
	// Entrer une palette
	public int creerPalette(int id_emb, int[] t_id_palette) throws SQLException {
		int j;
		st = conn.createStatement();
		st.executeUpdate("INSERT INTO palettes (id_trans) VALUES ("+ id_emb +");");
		result = st.executeQuery("SELECT MAX(id_trans) FROM palettes WHERE id_trans="+ id_emb +";");
		result.next();
		j = result.getInt(1);
		String requete = "UPDATE colis SET id_palette="+result.getInt(1)+" WHERE id_colis="+ t_id_palette[0] +" ";
		for(int i = 1; i< t_id_palette.length; i++) {
			requete += "OR id_colis="+ t_id_palette[i] + " ";
		}
		st.executeUpdate(requete);
		st.executeUpdate("UPDATE palettes SET id_trans=NULL WHERE id_palette="+ j +";");
		return j;
	}
	
	// Entrer une commande
	// 1 : Plus de Stock
	public int creerCommande(int id_client, int id_prod, int quantite, String date) throws SQLException {
		int i;
		st = conn.createStatement();
		result1 = st.executeQuery("SELECT nom, prenom FROM clients WHERE id_client="+ id_client +";");
		if (result1.next()) {
			result = st.executeQuery("SELECT stock, cout FROM produits WHERE id_prod="+ id_prod +";");
			if (result.next() && result.getInt(1) > quantite) {
				i = result.getInt(1)-quantite;
				st.executeUpdate("UPDATE clients SET depenses=depenses+"+ quantite*result.getInt(2)+" WHERE id_client="+ id_client +";");
				st.executeUpdate("UPDATE produits SET vendus=vendus+"+ quantite +" stock="+ i +" WHERE id_prod="+ id_prod +";");
			} else
				return 1;
			st.executeUpdate("INSERT INTO commandes (id_client, nom, prenom, id_prod, quantite, date_livraison, etat) VALUES ("+ id_client +", "+ result1.getString(1) +", "+ result1.getString(2) +", "+ id_prod +", "+ quantite +", '"+ date +"', 'produit')");
			return 0;
		}
		return 2;
	}
}
