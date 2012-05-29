import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;

public class PDataClient {
    
    public static void main(String[] arguments) {
	FileReader mydatafile = null;

	File file = new File("./data/client.sql");
	
	try{
	    FileWriter fw1 = new FileWriter(file, false);

	    BufferedReader buff = null;

	    String[][] datatab = new String[2500][10];
	    int i = 0;

	    try {
		mydatafile = new FileReader("./data/data.csv");
		buff = new BufferedReader(mydatafile);
		String delimiter = "\\|";
		String str_data = "";

		String login,motdepasse,nom,adresse,ville,code_postale,pays,telephone;
	    

		
		while (true) {

		    // Lit une ligne de data.csv
		    String line = buff.readLine();
		    // Vérifie la fin de fichier
		    if (line == null)
			break;

		    datatab[i]=line.split(delimiter);
  
      
      
		    if((Integer.parseInt(datatab[i][0]))==20){
			login = datatab[i][1];
			motdepasse = datatab[i][9];
			nom = datatab[i][2];
			adresse = datatab[i][4];
			ville = datatab[i][5];
			code_postale = datatab[i][6];
			pays = datatab[i][7];
			telephone = datatab[i][8];
			/** gerener requete d'insertion **/
			str_data =
			    "INSERT INTO intervenant values ('"
			    +login
			    +"','"+motdepasse
			    +"','"+nom
			    +"','"+adresse
			    +"','"+ville
			    +"','"+code_postale
			    +"','"+pays
			    +"','"+telephone
			    +"');\nINSERT INTO client values ('"
			    +login
			    +"');\n";
			// System.out.print(str_data);
			fw1.write(str_data);
			fw1.flush();
			i++;
		    }
		}

		// Fin du while
	    } catch (IOException exception) {
		exception.printStackTrace();
	    } finally {
		try {
		    buff.close();
		    mydatafile.close();
		} catch(IOException exception1) {
		    exception1.printStackTrace();
		}
	    }
	} catch (IOException exception0) {
	    exception0.printStackTrace();
	}
    }
}
