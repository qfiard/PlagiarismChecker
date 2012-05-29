import java.io.*;
import java.util.ArrayList;


public class Data{
	ArrayList <String> packers = new ArrayList<String> ();
	ArrayList <String> clients = new ArrayList<String> ();
	ArrayList <String> products = new ArrayList<String> ();
	ArrayList <String> deliverers = new ArrayList<String> ();
	ArrayList <String> customs = new ArrayList<String> ();
	String manager;


	public void getData() throws IOException{
    	InputStream instream = new FileInputStream("sql/data.csv");
    	InputStreamReader inreader = new InputStreamReader(instream);
		BufferedReader br = new BufferedReader(inreader); 
		String s; 
		while((s = br.readLine()) != null ) { 
			switch(Integer.parseInt(s.split("\\|")[0])){
				case 10:
					packers.add(s);
					break;
				case 20:
					clients.add(s);
					break;
				case 30:
					products.add(s);
					break;
				case 40:
					deliverers.add(s);
					break;
				case 50:
					customs.add(s);
					break;
				case 60:
					manager = s;
					break;
				default:
					System.out.println("error");
					break;
			} 
		} 
	}



}
