import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;

public class Parser {
    
    public static void main(String[] arguments) {
	FileReader mydatafile = null;

	File file_emballeur = new File("./emballeur.dat");
	File file_client = new File("./client.dat");
	File file_produit = new File("./produit.dat");
	File file_transporteur = new File("./transporteur.dat");
	File file_douane = new File("./douane.dat");
	File file_gerant = new File("./gerant.dat");
	
	try{
	    FileWriter fw1 = new FileWriter(file_gerant, false);

	    BufferedReader buff = null;

	    String[][] tabemballeur = new String[5][6]; int i1=0;
	    String[][] tabclient = new String[100][10]; int i2=0;
	    String[][] tabproduit = new String[2000][10]; int i3=0;
	    String[][] tabtransporteur = new String[10][4]; int i4=0;
	    String[][] tabdouane = new String[6][5]; int i5=0;
	    String[][] tabgerant = new String[1][5]; int i6=0;
	    int i = 0;

	    try {
		mydatafile = new FileReader("./data/data.csv");
		buff = new BufferedReader(mydatafile);
		String delimiter = "\\|";
		String str_data = "";
		
		while (true) {

		    // Lit une ligne de data.csv
		    String line = buff.readLine();
		    // Vérifie la fin de fichier
		    if (line == null)
			break;

		    datatab[i]=line.split(delimiter);

		    switch(Integer.parseInt(datatab[i][0])){
		    case(10): // Emballeur - 10  :
			str_data = tabemballeur[i][1]
			    +'#'+tabemballeur[i][2]
			    +'#'+tabemballeur[i][3]
			    +'#'+tabemballeur[i][4]
			    +'#'+tabemballeur[i][5]
			    +'\n';
			i1++;
			break;
		    case(20): // Client - 20 :
			str_data = datatab[i][1]
			    +'#'+datatab[i][2]
			    +'#'+datatab[i][3]
			    +'#'+datatab[i][4]
			    +'#'+datatab[i][5]
			    +'#'+datatab[i][6]
			    +'#'+datatab[i][7]
			    +'#'+datatab[i][8]
			    +'#'+datatab[i][9]
			    +'\n';
			i2++;
			break;
		    case(30): // Produit - 30 :
			str_data = datatab[i][1]
			    +'#'+datatab[i][2]
			    +'#'+datatab[i][3]
			    +'#'+datatab[i][4]
			    +'#'+datatab[i][5]
			    +'#'+datatab[i][6]
			    +'#'+datatab[i][7]
			    +'#'+datatab[i][8]
			    +'#'+datatab[i][9]
			    +'\n';
			i3++;
			break;
		    case(40): // Transporteur - 40 :
			str_data = datatab[i][1]
			    +'#'+datatab[i][2]
			    +'#'+datatab[i][3]
			    +'\n';
			i4++;
			break;
		    case(50): // Douane - 50 :
			str_data = datatab[i][1]
			    +'#'+datatab[i][2]
			    +'#'+datatab[i][3]
			    +'#'+datatab[i][4]
			    +'\n';
			i5++;
			break;
		    case(60): // Gérant - 60 :
			str_data = datatab[i][1]
			    +'#'+datatab[i][2]
			    +'#'+datatab[i][3]
			    +'#'+datatab[i][4]
			    +'\n';
			break;
			System.out.print(str_data);
		    }
		    i++;
		    // System.out.println(line);
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
