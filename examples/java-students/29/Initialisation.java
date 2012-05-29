import java.sql.*;

/**
 * 
 * @author nadine
 *
 */

public class Initialisation {

	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		System.out.println("Génération des emballages");
		BDD bdd = BDD.connect();
		int debut = 1;

		ResultSet rs = bdd.getSelect("SELECT MAX(numero_emballage) AS nb FROM emballages;");
		while (rs.next()) {
			debut = rs.getInt("nb");
			debut++;
		}

		// conversion en nombre décimaux pour avoir l'entier au-dessus, après division
		rs = bdd.getSelect("SELECT P.q, P.poids, P.prix, C.date_commande, "+
				" L.ligne_commande, quantite, qpc, cpp, "+
				" ceil((quantite::decimal)/(qpc::decimal)) AS cartons, "+
				"ceil((quantite::decimal)/(qpc::decimal)/(cpp::decimal)) AS palettes "+
				" FROM LIGNE_COMMANDE L, PRODUITS P, COMMANDES C "+
				" WHERE L.numero_produit=P.numero_produit AND L.numero_commande=C.numero_commande AND C.etat='total';");

		while (rs.next()) {
			int ligne_commande = rs.getInt("ligne_commande");
			String q = rs.getString("q");
			String date_commande = rs.getString("date_commande");
			int total = rs.getInt("quantite");
			int qpc = rs.getInt("qpc");
			int cpp = rs.getInt("cpp");
			int poids = rs.getInt("poids");
			int prix = rs.getInt("prix");
			int cartons = rs.getInt("cartons");
			int palettes = rs.getInt("palettes");

			int fin = debut + cartons -1 ;
			int nb_carton_pas_plein = total - (cartons-1)*qpc;
			int nb_palette_pas_pleine = total - (palettes-1)*cartons*qpc;

			int poidsCarton = qpc*poids;
			int valeurCarton = qpc*prix;
			int poidsDernierCarton = nb_carton_pas_plein*poids;
			int valeurDernierCarton = nb_carton_pas_plein*prix;

			int poidsPalette = cpp*poidsCarton;
			int valeurPalette = cpp*valeurCarton;
			int poidsDernierePalette = nb_palette_pas_pleine*poids;
			int valeurDernierePalette = nb_palette_pas_pleine*prix;

			String query = "INSERT INTO EMBALLAGES (numero_emballage, type, ligne_commande, etat_colis, qualifiant, poids, valeur, date_preparation, numero_emballeur)  SELECT generate_series(?, ?), (?::type_emballage), ?, 'expedie', (?::type_transport), ?, ?, (?::date), (SELECT numero_emballeur FROM EMBALLEURS ORDER BY RANDOM() LIMIT 1)";

			PreparedStatement pst = bdd.getPreparedStatement(query);
			pst.setInt(1, debut);
			pst.setInt(2, fin);
			pst.setString(3, "carton");
			pst.setInt(4, ligne_commande);
			pst.setString(5, q);
			pst.setInt(6, poidsCarton);
			pst.setInt(7, valeurCarton);
			pst.setString(8, date_commande);
			pst.executeUpdate();

			int debut2 = debut + cartons;
			int fin2 = debut2+1;

			pst = bdd.getPreparedStatement(query);
			pst.setInt(1, debut2);
			pst.setInt(2, fin2);
			pst.setString(3, "carton");
			pst.setInt(4, ligne_commande);
			pst.setString(5, q);
			pst.setInt(6, poidsCarton);
			pst.setInt(7, valeurCarton);
			pst.setString(8, date_commande);
			pst.executeUpdate();

			int debut3 = fin2+1;
			int fin3 = (palettes-1)+debut3;

			pst = bdd.getPreparedStatement(query);
			pst.setInt(1, debut3);
			pst.setInt(2, fin3);
			pst.setString(3, "palette");
			pst.setInt(4, ligne_commande);
			pst.setString(5, q);
			pst.setInt(6, poidsPalette);
			pst.setInt(7, valeurPalette);
			pst.setString(8, date_commande);
			pst.executeUpdate();

			int debut4 = fin3+1;
			int fin4 = debut4+1;

			pst = bdd.getPreparedStatement(query);
			pst.setInt(1, debut4);
			pst.setInt(2, fin4);
			pst.setString(3, "palette");
			pst.setInt(4, ligne_commande);
			pst.setString(5, q);
			pst.setInt(6, poidsDernierePalette);
			pst.setInt(7, valeurDernierePalette);
			pst.setString(8, date_commande);
			pst.executeUpdate();

			debut = fin4+1;
		}

		bdd.close();
	}
}
