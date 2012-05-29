import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.StringTokenizer;

public class integrationData {
	int c;
	static Connecte connecte;
	Statement st;

	@SuppressWarnings("static-access")
	public integrationData(int c, Connecte connecte, String id)
			throws SQLException {
		this.c = c;
		this.connecte = connecte;
		st = connecte.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}

	public static void main(String[] args) {
		if (args.length != 2)
			usage();

		try {
			String password = PasswordField
					.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connecte = new Connecte(args[0], password);
			integrationFichier(args[1]);
			connecte.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out
				.println("usage : java integrationData <nomUtilisateur> <fichier>");
		System.exit(1);
	}

	private static void integrationFichier(String fichier) {
		int i = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fichier)));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				i++;
				StringTokenizer token = new StringTokenizer(ligne, "|");
				while (token.hasMoreTokens()) {
					switch (Integer.parseInt(token.nextToken())) {
					case 10:
						emballeur(token);
						break;
					case 20:
						client(token);
						break;
					case 30:
						produit(token);
						break;
					case 40:
						transporteur(token);
						break;
					case 50:
						douane(token);
						break;
					case 60:
						gerant(token);
						break;
					case 110:
						commande(token);
						break;
					case 120:
						colis(token);
						break;
					case 130:
						exemplaire(token);
						break;
					case 140:
						palette(token);
						break;
					case 150:
						transport(token);
						break;
					default:
						System.exit(1);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(i + " requetes engegistres");
		try {
			System.out.println("Mise a jour du prix des commandes...");
			calculerPrixCommandes();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private static void calculerPrixCommandes() throws SQLException {
		int maxcommande;
		Statement st1 = connecte.conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT MAX(id) FROM commande;";
		ResultSet rs = st1.executeQuery(query);
		if (rs != null && rs.next()) {
			maxcommande = rs.getInt("max");
		} else {
			maxcommande = 0;
		}
		rs.close();
		for (int i = 0; i < maxcommande; i++) {
			try {
				query = "UPDATE commande SET prix=0 WHERE id=" + i + ";";
				st1.execute(query);
				query = "UPDATE commande " + "SET prix="
						+ "(SELECT SUM(P.prix) "
						+ "FROM exemplaire E, produit P,colis C "
						+ "WHERE E.produit_ref=P.ref AND E.colis_id=C.id "
						+ "GROUP BY commande_id " + "HAVING commande_id=" + i
						+ ") WHERE id=" + i + ";";
				if (st1.execute(query)){
					query = "UPDATE commande SET prix=0 WHERE id=" + i + ";";
					st1.execute(query);
				}
			} catch (SQLException e) {

			}
		}
	}

	private static void transport(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO transport(id, type)" + "VALUES (?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setInt(1, Integer.parseInt(token.nextToken()));
		ps.setString(2, token.nextToken());
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void palette(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO palette(id, emballeur_id, emballage_date,etat,transport_id)"
				+ "VALUES (?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setInt(1, Integer.parseInt(token.nextToken()));
		ps.setString(2, token.nextToken());
		ps.setTimestamp(3, new Timestamp(Long.parseLong(token.nextToken())*1000L));
		ps.setString(4, token.nextToken());
		ps.setInt(5, Integer.parseInt(token.nextToken()));
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void commande(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO commande(id, client, prix, livraison_date)"
				+ "VALUES (?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setInt(1, Integer.parseInt(token.nextToken()));
		ps.setString(2, token.nextToken());
		ps.setInt(3, 0);
		token.nextToken();
		ps.setTimestamp(4, new Timestamp(Long.parseLong(token.nextToken())*1000L));
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void exemplaire(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO exemplaire(id, produit_ref, colis_id)"
				+ "VALUES (?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setInt(1, Integer.parseInt(token.nextToken()));
		ps.setString(2, token.nextToken());
		ps.setInt(3, Integer.parseInt(token.nextToken()));
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void colis(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO colis(id, emballeur_id, emballage_date,etat,palette_id,commande_id)"
				+ "VALUES (?,?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setInt(1, Integer.parseInt(token.nextToken()));
		ps.setString(2, token.nextToken());
		ps.setTimestamp(3, new Timestamp(Long.parseLong(token.nextToken())*1000L));
		ps.setString(4, token.nextToken());
		String temp = token.nextToken();
		if (temp.contains("NULL")) {
			ps.setNull(5, Types.INTEGER);
		} else {
			ps.setInt(5, Integer.parseInt(temp));
		}
		ps.setInt(6, Integer.parseInt(token.nextToken()));
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void gerant(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO employe(prenom, nom, login, mdp,type)"
				+ "VALUES (?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setString(1, token.nextToken());
		ps.setString(2, token.nextToken());
		ps.setString(3, token.nextToken());
		ps.setString(4, token.nextToken());
		ps.setString(5, "gerant");
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void douane(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO douane(pays, login, mdp)"
				+ "VALUES (?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		String pays = token.nextToken();
		ps.setString(1, pays);
		int txverif = Integer.parseInt(token.nextToken());
		ps.setString(2, token.nextToken());
		ps.setString(3, token.nextToken());
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
		int max;
		Statement st1 = connecte.conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		query = "SELECT MAX(id) FROM controle;";
		ResultSet rs = st1.executeQuery(query);
		if (rs != null && rs.next()) {
			max = rs.getInt("max");
		} else {
			max = 0;
		}
		rs.close();
		for (int i = 0; i < 10; i++) {
			query = "INSERT INTO controle(id, pays, resultat)"
					+ "VALUES (?,?,?);";
			ps = connecte.conn.prepareStatement(query);
			ps.setInt(1, max + i + 1);
			ps.setString(2, pays);
			if (i > txverif/10) {
				ps.setString(3, "Renvoi");
			} else {
				ps.setString(3, "Valide");
			}
			ps.execute();
			ps.close();
		}
	}

	private static void transporteur(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO transporteur(login, nom, mdp)"
				+ "VALUES (?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setString(1, token.nextToken());
		ps.setString(2, token.nextToken());
		ps.setString(3, token.nextToken());
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void produit(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO produit(ref, description, qte_carton, carton_palette, type, prix, poids, nb_dispo)"
				+ "VALUES (?,?,?,?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setString(1, token.nextToken());
		ps.setString(2, token.nextToken());
		ps.setInt(3, Integer.parseInt(token.nextToken()));
		ps.setInt(4, Integer.parseInt(token.nextToken()));
		ps.setString(5, token.nextToken());
		ps.setInt(6, Integer.parseInt(token.nextToken()));
		token.nextToken(); // Taux d'augmentation du prix
		ps.setInt(7, Integer.parseInt(token.nextToken()));
		ps.setInt(8, Integer.parseInt(token.nextToken()));
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void client(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO client(login, societe, suffixe, adresse, ville, cp, pays, tel, mdp)"
				+ "VALUES (?,?,?,?,?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		ps.setString(1, token.nextToken());
		ps.setString(2, token.nextToken());
		ps.setString(3, token.nextToken());
		ps.setString(4, token.nextToken());
		ps.setString(5, token.nextToken());
		ps.setString(6, token.nextToken());
		ps.setString(7, token.nextToken());
		ps.setString(8, token.nextToken());
		ps.setString(9, token.nextToken());
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
	}

	private static void emballeur(StringTokenizer token) throws SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO employe(login, nom, prenom, mdp, type)"
				+ "VALUES (?,?,?,?,?);";
		ps = connecte.conn.prepareStatement(query);
		String login = token.nextToken();
		ps.setString(1, login);
		ps.setString(2, token.nextToken());
		ps.setString(3, token.nextToken());
		int txerr = Integer.parseInt(token.nextToken());
		ps.setString(4, token.nextToken());
		ps.setString(5, "emballeur");
		ps.execute();
		ps.close();
		if (token.hasMoreTokens())
			System.exit(1);
		int max;
		Statement st1 = connecte.conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		query = "SELECT MAX(id) FROM controle;";
		ResultSet rs = st1.executeQuery(query);
		if (rs != null && rs.next()) {
			max = rs.getInt("max");
		} else {
			max = 0;
		}
		rs.close();
		for (int i = 0; i < 10; i++) {
			query = "INSERT INTO controle(id, emballeur_id, resultat)"
					+ "VALUES (?,?,?);";
			ps = connecte.conn.prepareStatement(query);
			ps.setInt(1, max + i + 1);
			ps.setString(2, login);
			if (i < txerr/10) {
				ps.setString(3, "Renvoi");
			} else {
				ps.setString(3, "Valide");
			}
			ps.execute();
			ps.close();
		}
	}
}
