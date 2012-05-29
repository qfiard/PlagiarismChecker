import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * 
 * Functions to create commands
 * 
 * @author Joossen & Mathou
 * 
 */

public class Order {

	protected DataBaseConnection dbc;

	public Order(DataBaseConnection c) {
		this.dbc = c;
	}

	/**
	 * 
	 * @return a random date, day is between 1 and 28
	 */
	public java.sql.Date randomDate() {
		int mois = (int) (Math.random() * 12 + 1);
		int jour = (int) (Math.random() * 28 + 1);
		String stringDate = "2011-" + mois + "-" + jour;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null;
		try {
			java.util.Date date = formatter.parse(stringDate);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

	/**
	 * 
	 * @param date
	 *            delivery date
	 * @param country
	 *            country where to sent the parcel
	 * @param pack
	 *            a packer (maybe add late)
	 * @return a date a few days before param date
	 * @throws SQLException
	 */
	public java.sql.Date randomDate(java.sql.Date date, String country,
			Packer pack) throws SQLException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null;
		java.util.Date utilDate = null;

		int delivery;
		if (country.equals("France")) {
			delivery = 1;
		} else {
			delivery = 3;
		}

		String stringDate = date.toString();
		int random = (int) (Math.random() * 10);
		if ((random % 2) == 0 || (random % 3) == 0) {
			delivery += 0;
		} else if (random == 1 || random == 7) {
			dbc.addLate(pack.getLogin(), 1);
			delivery += 1;
		} else {
			dbc.addLate(pack.getLogin(), 2);
			delivery += 2;
		}

		String[] tabDate = stringDate.split("-");
		if (Integer.parseInt(tabDate[2]) > delivery) {
			tabDate[2] = "" + (Integer.parseInt(tabDate[2]) - delivery);
		} else {
			delivery -= Integer.parseInt(tabDate[2]);
			delivery = 28 - delivery;
			tabDate[2] = "" + delivery;
			if (Integer.parseInt(tabDate[1]) > 1) {
				tabDate[1] = "" + (Integer.parseInt(tabDate[1]) - 1);
			} else {
				tabDate[1] = "12";
				tabDate[0] = "" + (Integer.parseInt(tabDate[0]) - 1);
			}
		}
		stringDate = tabDate[0] + "-" + tabDate[1] + "-" + tabDate[2];
		try {
			utilDate = formatter.parse(stringDate);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

	/**
	 * 
	 * Create numberOrder orders
	 * 
	 * @param numberOrder
	 *            number of orders
	 * @param deliv
	 *            'y' if products are delivered, else 'n'
	 * @param send
	 *            'y' if products are sent, else 'n'
	 * @throws SQLException
	 */
	public void creationOrder(int numberOrder, char deliv, char send)
			throws SQLException {

		String productRef = "", prodDeliv = "";
		Double price;
		int numberProducts, quantity, orderFormRef;
		java.sql.Date deliveryDate;
		LinkedList<ProductInformation> prodInf;
		Client client;
		Packer packer;
		String queryInsert, queryUpdate;

		orderFormRef = dbc.getNextIndex("order_form");

		for (int cptOrder = 0; cptOrder < numberOrder; cptOrder++) {
			productRef = "";
			prodDeliv = "";
			price = 0.0;
			// selection of the client
			client = dbc.getClient();
			// product selection
			numberProducts = (int) (Math.random() * 5 + 1);
			prodInf = dbc.getProductRef(numberProducts);
			for (ProductInformation pi : prodInf) {
				queryUpdate = "BEGIN;\n";
				do {
					quantity = (int) (Math.random() * 3 + 1);
				} while (quantity > pi.getStock());
				for (int j = 1; j <= quantity; j++) {
					productRef += (pi.getRef() + "/");
					prodDeliv += (deliv + "/");
				}
				price += (quantity * pi.getPrice());
				// query to update the stock
				queryUpdate = "UPDATE product SET stock=(stock - " + quantity
						+ ") WHERE product_ref='" + pi.getRef() + "';";
				dbc.updateQuery(queryUpdate);

			}
			if (productRef.length() > 0) {
				productRef = productRef.substring(0, productRef.length() - 1);
			}
			if (prodDeliv.length() > 0) {
				prodDeliv = prodDeliv.substring(0, prodDeliv.length() - 1);
			}

			// to have an order date
			deliveryDate = randomDate();

			// to have a packer
			packer = dbc.getPacker();

			// query to insert into the command in the table
			queryInsert = "INSERT INTO order_form VALUES ";
			queryInsert += "(" + (cptOrder + orderFormRef) + ",'"
					+ client.getLogin() + "','" + productRef + "','"
					+ prodDeliv + "'," + price + ",'" + deliveryDate + "','"
					+ packer.getLogin() + "');";
			dbc.executeInsert(queryInsert);

			// packing
			int degreeOfFilling = 0;
			String queryPacking = "INSERT INTO parcel VALUES ";
			java.sql.Date packingDate = randomDate(deliveryDate,
					client.getCountry(), packer);

			int parcelRef = dbc.getNextIndex("parcel");
			String products = "", parcelsRef = "";

			for (ProductInformation pi : prodInf) {
				if (degreeOfFilling + (100 / pi.getQuantityPerCarton()) > 100) {

					queryPacking += "(" + parcelRef + ","
							+ (cptOrder + orderFormRef) + ",'"
							+ packer.getLogin() + "','" + packingDate
							+ "',NULL,'" + client.getCountry() + "','"
							+ products + "');";
					dbc.executeInsert(queryPacking);

					queryPacking = "INSERT INTO parcel VALUES ";
					products = pi.getRef() + "/";
					parcelsRef += parcelRef + "/";
					parcelRef++;
					degreeOfFilling = (100 / pi.getQuantityPerCarton());

				} else {
					degreeOfFilling += (100.0 / pi.getQuantityPerCarton());
					products += pi.getRef() + "/";
				}
			}
			queryPacking += "(" + parcelRef + "," + (cptOrder + orderFormRef)
					+ ",'" + packer.getLogin() + "','" + packingDate
					+ "',NULL,'" + client.getCountry() + "','" + products
					+ "');";
			dbc.executeInsert(queryPacking);
			parcelsRef += parcelRef;

			// pallet : max 5 parcels per pallet
			int palletRef = dbc.getNextIndex("pallet");
			String palletsRef = "";
			String[] parcelsRefTab = parcelsRef.split("/");
			String queryPallet = "INSERT INTO pallet VALUES (" + palletRef
					+ ",'" + send + "','" + client.getCountry() + "','";
			for (int i = 1; i <= parcelsRefTab.length; i++) {
				if ((i % 5) != 0 && i != parcelsRefTab.length) {
					queryPallet += (parcelsRefTab[i - 1] + "/");
				} else {
					queryPallet += parcelsRefTab[i - 1];
					queryPallet += "');";
					dbc.executeInsert(queryPallet);
					parcelsRef += (palletsRef + "/");
				}
			}

			// container :
			int containerRef = dbc.getNextIndex("container");
			while (palletsRef.endsWith("/")) {
				palletsRef = palletsRef.substring(0, palletsRef.length() - 1);
			}
			String containerQuery = "INSERT INTO container VALUES ("
					+ containerRef + ",'" + client.getCountry() + "','"
					+ packingDate + "','" + palletsRef + "','" + deliv + "');";
			dbc.executeInsert(containerQuery);

		}
	}

}
