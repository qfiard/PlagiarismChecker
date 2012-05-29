import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser un fichier CSV avec StringTokenizer
 */
public class Remplisse {
	ConnectionB conn;

	public Remplisse(ConnectionB connecte) throws SQLException {
		conn = connecte;
		try {

			String delimiter = "|";
			String line = null;
			StringTokenizer strToken = null;
			BufferedReader bufferReader;
			// Ouvrir le fichier CSV
			bufferReader = new BufferedReader(new FileReader("data.csv"));
			// parcourir les lignes du fichier CSV
			while ((line = bufferReader.readLine()) != null) {
				// Parcourir les champs séparés par delimiter
				strToken = new StringTokenizer(line, delimiter);
				String[] s;
				int i;
				String type = strToken.nextToken();
				int c = Integer.parseInt(type);
				switch (c) {
				case 10:
					s = new String[5];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						// System.out.println(st);
						s[i] = st;
						// System.out.println(s[i]);
						i++;
					}
					int e = Integer.parseInt(s[3]);
					conn.emb(s[0], s[1], s[2], e, s[4]);
					break;
				case 20:
					s = new String[9];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						s[i] = st;
						i++;
					}
					conn.client(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7],
							s[8]);
					break;
				case 30:
					s = new String[9];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						s[i] = st;
						i++;
					}
					conn.prod(s[0], s[1], s[5], s[7]);

					break;
				case 40:
					s = new String[3];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						s[i] = st;
						i++;
					}
					conn.transp(s[0], s[1], s[2]);
					break;
				case 50:
					s = new String[4];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						s[i] = st;
						i++;
					}
					conn.douane(s[0], s[1], s[2], s[3]);
					break;
				case 60:
					s = new String[4];
					i = 0;
					while (strToken.hasMoreTokens()) {
						String st = strToken.nextToken();
						s[i] = st;
						i++;
					}
					conn.geant(s[0], s[1], s[2], s[3]);
					break;
				}

			}
		} catch (IOException ex) {
			Logger.getLogger(Remplisse.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
}