import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DataBaseConnection {

	Connection conn;
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	String login = "matthieu";
	String motPasse = "matthieu1";

	public DataBaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/"
				+ login, login, motPasse);
	}

	/**
	 * 
	 * @return a client
	 * @throws SQLException
	 */
	public Client getClient() throws SQLException {
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String query = "SELECT client_id, country FROM client;";
		ResultSet rs = st.executeQuery(query);
		int index = (int) (Math.random() * 100 + 1);
		if (rs.first()) {
			rs.absolute(index);
			Client cl = new Client(rs.getString("client_id"),
					rs.getString("country"));
			return cl;
		} else {
			System.out.println("Erreur");
		}
		return (new Client("", ""));
	}

	/**
	 * 
	 * @return a packer
	 * @throws SQLException
	 */
	public Packer getPacker() throws SQLException {
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String query = "SELECT packer_id, error_rate FROM packer;";
		ResultSet rs = st.executeQuery(query);
		int index = (int) (Math.random() * 5 + 1);
		if (rs.first()) {
			rs.absolute(index);
			return (new Packer(rs.getString("packer_id"),
					rs.getInt("error_rate")));
		}
		return (new Packer("", 0));
	}

	/**
	 * 
	 * This method returns a list of n products
	 * 
	 * @param n
	 *            number of products
	 * @return list of products
	 * @throws SQLException
	 */
	public LinkedList<ProductInformation> getProductRef(int n)
			throws SQLException {
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String query = "SELECT product_ref, price, stock, quantity_per_carton FROM product";
		ResultSet rs = st.executeQuery(query);
		LinkedList<ProductInformation> prodInfList = new LinkedList<ProductInformation>();
		ProductInformation prodInf;
		int index;
		for (int i = 0; i < n; i++) {
			index = (int) (Math.random() * 2000 + 1);
			if (rs.first()) {
				rs.absolute(index);
				prodInf = new ProductInformation(rs.getString("product_ref"),
						rs.getDouble("price"),
						rs.getInt("quantity_per_carton"), rs.getInt("stock"));
				prodInfList.add(prodInf);
			}
		}
		return prodInfList;
	}

	public void execute(String query) throws SQLException {
		st = conn.createStatement();
		st.execute(query);
	}

	/**
	 * 
	 * Function that gives the next index from a table
	 * 
	 * @param table
	 *            table which we want the next index
	 * @return the next index (int) of the table 'table'
	 * @throws SQLException
	 */
	public int getNextIndex(String table) throws SQLException {
		String columnFirstName;
		if (table.equals("parcel")) {
			columnFirstName = "parcel_ref";
		} else if (table.equals("order_form")) {
			columnFirstName = "order_ref";
		} else if (table.equals("pallet")) {
			columnFirstName = "pallet_ref";
		} else if (table.equals("container")) {
			columnFirstName = "container_ref";
		} else {
			return -1;
		}
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String query = "SELECT " + columnFirstName + " FROM " + table + ";";
		ResultSet rs = st.executeQuery(query);
		if (rs.last()) {
			return (rs.getInt(columnFirstName) + 1);
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * This method is used when a packer is delayed
	 * 
	 * @param pack_id
	 *            id of the packer
	 * @param late
	 *            add delay
	 * @throws SQLException
	 */
	public void addLate(String pack_id, int late) throws SQLException {
		st = conn.createStatement();
		String query = "UPDATE packer SET late=(late+" + late
				+ ") WHERE packer_id='" + pack_id + "';";
		st.execute(query);
	}

	/**
	 * 
	 * This method is used for updating
	 * 
	 * @param queryUpdate
	 *            query update
	 * @throws SQLException
	 */
	public void updateQuery(String queryUpdate) throws SQLException {
		update = conn.prepareStatement(queryUpdate);
		update.execute();
	}

	/**
	 * 
	 * This method is used for insertion
	 * 
	 * @param queryInsert
	 *            insert query
	 * @throws SQLException
	 */
	public void executeInsert(String queryInsert) throws SQLException {
		insert = conn.prepareStatement(queryInsert);
		insert.execute();

	}
}
