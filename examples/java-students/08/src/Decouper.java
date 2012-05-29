import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Formatter;
import java.util.Scanner;


public class Decouper {

	/**
	 * @param args
	 */
	public static String decouper(String type,String filename) throws IOException{
		String decoupe="";
		Reader r;
		r = new FileReader(filename);
		Scanner s = new Scanner(r);
		while(s.hasNextLine()){
			String ligne=s.nextLine();
			if(ligne.startsWith(type)){
				String ligne_decoupe=ligne.substring(3,ligne.length());
				decoupe=decoupe+ligne_decoupe+"\n";
			}
		}
	
		return decoupe;
	}
	public static void save(String filename,String decoupe) throws IOException {
    	Writer w= new FileWriter(filename);
    	Formatter f = new Formatter(w);
    	try{
    		f.format(decoupe);
    	}
    	finally {
    		f.close();
    	}
    	
    }
	public static void decouper_total(String filename) throws IOException{
		save("emballeur.txt",decouper("10",filename));
		save("client.txt",decouper("20",filename));
		save("produit.txt",decouper("30",filename));
		save("transporteur.txt",decouper("40",filename));
		save("douane.txt",decouper("50",filename));
		save("gerant.txt",decouper("60",filename));
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		decouper_total("data.csv");
	    
	}

}
