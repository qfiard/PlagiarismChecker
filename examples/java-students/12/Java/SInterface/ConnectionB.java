import java.sql.*;

class ConnectionB {
	Connection conn; // la connexion a la base
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;

	// connection a la base
	public ConnectionB(String user, String motPasse) throws SQLException,
			ClassNotFoundException {
		// -------------------
		// Connexion a la base
		// --------------------
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost/Projet", user, motPasse);

		} catch (ClassNotFoundException e) {
			System.out.println("Impossible de charge le Pilot");
			System.exit(1);
		}
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}

	public String Verif_fonc(String login, String pass) throws SQLException {
		String sql = "select fonction from utilisateur where login='" + login
				+ "'and " + "mdp='" + pass + "';";
		ResultSet res = st.executeQuery(sql);
		if (res.next()) {
			return res.getString("fonction");
		}
		return "eurreur";
	}

	// fermeture de la connection
	public void close() throws SQLException {
		conn.close();
	}

	public void MAJ(String s, int p) throws SQLException {
		update = conn
				.prepareStatement("UPDATE produit SET prix = ? WHERE num_produit = ?;");
		update.setInt(1, p);
		update.setString(2, s);
		update.execute();

	}

	public ResultSet list_employe() throws SQLException {
		String sql = "Select * from emballeur";
		return st.executeQuery(sql);
	}

	public ResultSet list_client() throws SQLException {
		String sql = "select * from client";
		return st.executeQuery(sql);
	}

	public ResultSet max_vend() throws SQLException {
		String sql = "select * from produit"
				+ " where vendu=(select max(vendu) from produit) and vendu>0;";
		return st.executeQuery(sql);
	}

	public ResultSet max_dep() throws SQLException {
		String sql = " select id,NUM_CLIENT,sum "
				+ "from (select id_clients,sum(total)"
				+ "from commande group by id_clients)as T "
				+ "left join client on(T.id_clients=client.id) "
				+ "where sum=(select max(sum) from (select id_clients,sum(total) "
				+ "from commande group by id_clients)as T );";
		return st.executeQuery(sql);

	}

	public ResultSet list_prod() throws SQLException {
		String sql = "select * from produit where dispo>0;";
		return st.executeQuery(sql);
	}

	public ResultSet lis_colis(String log) throws SQLException {
		String sql = "Select id,num_colis from colis where id_emb =(Select id from emballeur "
				+ "where login='" + log + "');";
		return st.executeQuery(sql);
	}

	public ResultSet lis_cmd() throws SQLException {
		String sql = "Select *from commande where traite=0";
		return st.executeQuery(sql);

	}

	public ResultSet etat_coli() throws SQLException {
		String sql = "select id,qualif from colis;";
		return st.executeQuery(sql);
	}

	public ResultSet coli_nE() throws SQLException {
		String sql = "select id,num_colis from colis where exped=0 or exped=2;";
		return st.executeQuery(sql);
	}

	public void emb(String num, String nom, String prenom, int eurr, String mdp)
			throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO EMBALLEUR VALUES (?,?,'emballeur',default,?,?,?,?);");
		insert.setString(1, nom);
		insert.setString(2, mdp);
		insert.setString(3, num);
		insert.setString(4, nom);
		insert.setString(5, prenom);
		insert.setInt(6, eurr);
		insert.execute();
		insert.close();
	}

	public void client(String num, String soc, String suff, String adr,
			String ville, String cp, String pays, String tel, String mdp)
			throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO Client VALUES (?,?,'client',default,?,"
						+ "?,?,?,?,?,?,?);");
		insert.setString(1, suff);
		insert.setString(2, mdp);
		insert.setString(3, num);
		insert.setString(4, adr);
		insert.setString(5, ville);
		insert.setString(6, cp);
		insert.setString(7, pays);
		insert.setString(8, tel);
		insert.setString(9, soc);
		insert.setString(10, suff);
		insert.execute();
		insert.close();
	}

	public void prod(String num, String desc, String prix, String poids)
			throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO produit VALUES (default,?,100,?,?,?,2);");
		insert.setString(1, num);
		int p = Integer.parseInt(prix);
		insert.setInt(2, p);
		insert.setString(3, desc);
		int pd = Integer.parseInt(poids);
		insert.setInt(4, pd);
		insert.execute();
		insert.close();
	}

	public void transp(String num, String nom, String mdp) throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO transporteur VALUES (?,?,'transporteur',default,?,?);");
		insert.setString(1, nom);
		insert.setString(2, mdp);
		insert.setString(3, num);
		insert.setString(4, nom);
		insert.execute();
		insert.close();
	}

	public void douane(String pays, String verif, String log, String mdp)
			throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO douane VALUES (?,?,'douane',default,?,?);");
		insert.setString(1, log);
		insert.setString(2, mdp);
		insert.setString(3, pays);
		int ve = Integer.parseInt(verif);
		insert.setInt(4, ve);
		insert.execute();
		insert.close();
	}

	public void geant(String nom, String prenom, String log, String mdp)
			throws SQLException {
		insert = conn
				.prepareStatement("INSERT INTO Gerant VALUES (?,?,'gerant',?,?);");
		insert.setString(1, log);
		insert.setString(2, mdp);
		insert.setString(3, nom);
		insert.setString(4, prenom);
		insert.execute();
		insert.close();
	}

	public ResultSet cmd(String num_client) throws SQLException {
		String sql = "select * from commande where id_clients="
				+ "(select id from client where num_client='" + num_client
				+ " ');";
		return st.executeQuery(sql);
	}

	public String getnum(String log, String mdp) throws SQLException {
		String sql = "select * from client where login='" + log + "'and "
				+ "mdp='" + mdp + "';";
		ResultSet res = st.executeQuery(sql);
		if (res.next()) {

			return res.getString("num_client");
		}
		return "eurreur";
	}

	public ResultSet list_pal(int i) throws SQLException {
		String sql = "select * from palette where id_cont=" + i + ";";
		return st.executeQuery(sql);
	}

	public ResultSet list_colis_c(int i) throws SQLException {
		String sql = "select * from colis where id_pale=" + i + ";";
		return st.executeQuery(sql);
	}

	public void changer_login(String num, String log, String mdp)
			throws SQLException {
		update = conn
				.prepareStatement("UPDATE Client SET login = ? , mdp=? WHERE num_client = ?;");
		update.setString(1, log);
		update.setString(2, mdp);
		update.setString(3, num);
		update.execute();
	}

	public void licencier(int i) throws SQLException {
		delete = conn.prepareStatement("delete from emballeur where err>?;");
		delete.setInt(1, i);
		delete.execute();
		delete.close();
	}
}