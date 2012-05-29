import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Felling_tables {

	Connection conn;
	String login = "matthieu";
	String motPasse = "matthieu1";

	public static void felling_tables(String nomFichier, String chemin)
			throws IOException, SQLException, ClassNotFoundException {

		if (!chemin.endsWith("/")) {
			chemin += "/";
		}
		// recuperation des donnees du fichier nomFichier
		// et creation des fichiers .txt contennant ces donnees

		String ligne, type;
		String[] valLigne;
		String ligneActor, lignePacker, ligneCarrier, ligneClient, ligneCustoms;
		String ligneProduct;

		String fichierActor = chemin + "actor.txt";
		String fichierPacker = chemin + "packer.txt";
		String fichierCarrier = chemin + "carrier.txt";
		String fichierClient = chemin + "client.txt";
		String fichierCustoms = chemin + "douane.txt";
		String fichierProduct = chemin + "produit.txt";

		try {

			PrintWriter pwActor = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierActor)));
			PrintWriter pwPacker = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierPacker)));
			PrintWriter pwCarrier = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierCarrier)));
			PrintWriter pwClient = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierClient)));
			PrintWriter pwCustoms = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierCustoms)));
			PrintWriter pwProduct = new PrintWriter(new BufferedWriter(
					new FileWriter(fichierProduct)));

			Scanner sc = new Scanner(new File(nomFichier));
			while (sc.hasNextLine()) {

				ligne = sc.nextLine();
				valLigne = ligne.split("\\|");
				type = valLigne[0];

				if (type.equals("10")) {

					ligneActor = valLigne[1] + "|" + valLigne[5] + "|emballeur";
					pwActor.println(ligneActor);

					lignePacker = valLigne[1] + "|" + valLigne[3] + "|"
							+ valLigne[2] + "|0|0|" + valLigne[4] + "|false";
					pwPacker.println(lignePacker);

				} else if (type.equals("20")) {

					ligneActor = valLigne[1] + "|" + valLigne[9] + "|client";
					pwActor.println(ligneActor);

					ligneClient = valLigne[1] + "|" + valLigne[2] + "|"
							+ valLigne[3] + "|" + valLigne[4] + "|"
							+ valLigne[5] + "|" + valLigne[6] + "|"
							+ valLigne[7] + "|" + valLigne[8] + "|0.0";
					pwClient.println(ligneClient);

				} else if (type.equals("30")) {
					ligneProduct = valLigne[1] + "|" + valLigne[2] + "|"
							+ valLigne[3] + "|0|" + valLigne[9] + "|"
							+ valLigne[5] + "|" + valLigne[6] + "|"
							+ valLigne[7];
					pwProduct.println(ligneProduct);

				} else if (type.equals("40")) {

					ligneActor = valLigne[1] + "|" + valLigne[3]
							+ "|transporteur";
					pwActor.println(ligneActor);

					ligneCarrier = valLigne[1] + "|" + valLigne[2] + "|false";
					pwCarrier.println(ligneCarrier);

				} else if (type.equals("50")) {
					ligneActor = valLigne[3] + "|" + valLigne[4] + "|douane";
					pwActor.println(ligneActor);

					ligneCustoms = valLigne[3] + "|" + valLigne[1] + "|"
							+ valLigne[2];
					pwCustoms.println(ligneCustoms);

				} else if (type.equals("60")) {

					ligneActor = valLigne[3] + "|" + valLigne[4] + "|gerant";
					pwActor.println(ligneActor);

				} else {
					System.out
							.println("La ligne suivante n'est pas bien formee : ");
					System.out.println("\t\"" + ligne + "\"");
				}

			}

			pwProduct.close();
			pwCustoms.close();
			pwClient.close();
			pwCarrier.close();
			pwPacker.close();
			pwActor.close();

			// insertion des fichiers dans les tables correspondantes.

			Connection conn;
			String login = "matthieu";
			String motPasse = "matthieu1";
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"
					+ login, login, motPasse);
			Statement st = conn.createStatement();
			st.execute("COPY actor FROM '" + fichierActor
					+ "' WITH DELIMITER '|';");
			st.execute("COPY packer FROM '" + fichierPacker
					+ "' WITH DELIMITER '|';");
			st.execute("COPY carrier FROM '" + fichierCarrier
					+ "' WITH DELIMITER '|';");
			st.execute("COPY client FROM '" + fichierClient
					+ "' WITH DELIMITER '|';");
			st.execute("COPY customs FROM '" + fichierCustoms
					+ "' WITH DELIMITER '|';");
			st.execute("COPY product FROM '" + fichierProduct
					+ "' WITH DELIMITER '|';");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
