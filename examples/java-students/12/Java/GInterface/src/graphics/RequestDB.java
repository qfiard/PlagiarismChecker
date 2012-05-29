package graphics;

import java.sql.*;

public class RequestDB {
	Connection conn;
	Statement st;
	PreparedStatement select;

	public RequestDB() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost/Projet", "rymex", "azerty");
		} catch (ClassNotFoundException e) {
			System.out.println("Impossible de charge le Pilot");
			System.exit(1);
		}
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}

	public boolean CheckLogin(String user, String pass) throws SQLException {
		String sql = "SELECT login, mdp FROM utilisateur where " + "login='"
				+ user + "' and " + "mdp='" + pass + "';";
		ResultSet res = st.executeQuery(sql);

		return (res.next() ? true : false);
	}

	public String getId(String user) throws SQLException {
		String sql = "SELECT id FROM douane where " + "login='" + user + "';";
		ResultSet res = st.executeQuery(sql);

		return (res.next() ? (res.getString("id")) : "erreur");
	}

	public String getFunction(String user) throws SQLException {
		String sql = "SELECT fonction FROM utilisateur where " + "login='"
				+ user + "';";
		ResultSet res = st.executeQuery(sql);

		return (res.next() ? (res.getString("fonction")) : "erreur");
	}

	public String getCountry(int id) throws SQLException {
		String sql = "SELECT pays FROM douane where " + "id='" + id + "';";
		ResultSet res = st.executeQuery(sql);

		return (res.next() ? (res.getString("pays")) : "erreur");
	}

	public ResultSet getArrived(String pays) throws SQLException {
		String sql = "SELECT num_cmd, adr_liv, date_cmd, date_liv, total, traite"
				+ " FROM commande where pays='" + pays + "';";

		return st.executeQuery(sql);
	}

	public ResultSet getControled(String pays) throws SQLException {
		String sql = "SELECT num_cmd, adr_liv, date_cmd, date_liv, total, traite "
				+ "FROM commande, colis WHERE commande.id = colis.id_cmd AND commande.pays ='"
				+ pays + "' AND traite=1;";

		return st.executeQuery(sql);
	}

	public ResultSet getNotControled(String pays) throws SQLException {
		String sql = "SELECT num_cmd, adr_liv, date_cmd, date_liv, total, traite "
				+ "FROM commande, colis WHERE commande.id = colis.id_cmd AND commande.pays ='"
				+ pays + "' AND traite=0;";

		return st.executeQuery(sql);
	}

	public ResultSet getSearch(String cmd) throws SQLException {
		String sql = "SELECT num_cmd, quantite, adr_liv, date_cmd, date_liv, total"
				+ " FROM commande where num_cmd='" + cmd + "';";

		return st.executeQuery(sql);
	}

	public int getStats(String pays) throws SQLException {
		ResultSet rs, rs2;
		int stat = 0;
		int stat2 = 0;
		
		String sql = "SELECT COUNT(*) as s FROM commande WHERE pays='"+pays+"';";
		//String sql2 = "SELECT verf FROM douane WHERE pays='"+pays+"';";
		String sql3 = "SELECT COUNT(*)from colis where id_cmd=(SELECT id from commande WHERE pays='"+pays+"');";
		rs = st.executeQuery(sql);
		rs2 = st.executeQuery(sql3);

		if(rs != null && rs.next())
			stat = rs.getInt("s");
		if(rs2 != null && rs2.next())
			stat2 = rs.getInt("verf");
		
		return (int)stat/stat2;
	}
	
	public String getNbCmd(String pays) throws SQLException {
		String sql = "SELECT COUNT(*) as nb FROM commande WHERE pays='"+pays+"';";
		ResultSet res = st.executeQuery(sql);
		
		return (res.next() ? (res.getString("nb")) : "erreur");
	}

	public String getNbControl(String pays) throws SQLException {
		String sql = "SELECT verf FROM douane WHERE pays='"+pays+"';";
		ResultSet res = st.executeQuery(sql);
		
		return (res.next() ? (res.getString("verf")) : "erreur");
	}
	
	public ResultSet getClient() throws SQLException {
		String sql = "SELECT num_client, adr, ville, cp, pays, tel, societe from client;";

		return st.executeQuery(sql);
	}

	public ResultSet getEmba() throws SQLException {
		String sql = "SELECT num_emba, nom, prenom, err from emballeur;";

		return st.executeQuery(sql);
	}

	public ResultSet getTrans() throws SQLException {
		String sql = "SELECT scac, nom from transporteur;";

		return st.executeQuery(sql);
	}

	public ResultSet getProd() throws SQLException {
		String sql = "SELECT num_produit, descr, poids, dispo, prix, vendu from produit;";

		return st.executeQuery(sql);
	}

	public ResultSet getCmd() throws SQLException {
		String sql = "SELECT num_cmd, quantite, adr_liv, pays, date_cmd, date_liv, total, traite from commande;";

		return st.executeQuery(sql);
	}

	public ResultSet getColis() throws SQLException {
		String sql = "SELECT num_colis, date_emball, verif, qualif, exped from colis;";

		return st.executeQuery(sql);
	}

	public void close() throws SQLException {
		conn.close();
	}
}