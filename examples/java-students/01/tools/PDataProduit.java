import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;

public class PDataProduit {

    public static void main(String[] arguments) {
	FileReader mydatafile = null;

	File file = new File("./data/produit.sql");
	
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

		String id_produit,description,prix,stock,type,quantite_carton,carton_palette,taux_augmentation_prix,poid;
	    
		while (true) {

		    // Lit une ligne de data.csv
		    String line = buff.readLine();
		    // Vérifie la fin de fichier
		    if (line == null)
			break;

		    datatab[i]=line.split(delimiter);

		    if((Integer.parseInt(datatab[i][0]))==30){
			id_produit=datatab[i][1];
			description=datatab[i][2];
			prix=datatab[i][6];
			stock=datatab[i][9];
			type=datatab[i][5];
			quantite_carton=datatab[i][3];
			carton_palette=datatab[i][4];
			taux_augmentation_prix=datatab[i][7];
			poid=datatab[i][8];
			/** generer requete d'insertion **/
			str_data =
			    "INSERT INTO produit VALUES ('"
			    +id_produit
			    +"','"+description
			    +"',"+prix
			    +","+stock
			    +",'"+type
			    +"','"+quantite_carton
			    +"','"+carton_palette
			    +"','"+taux_augmentation_prix
			    +"',"+poid
			    +");\n";
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