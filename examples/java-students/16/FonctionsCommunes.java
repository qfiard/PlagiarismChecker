import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FonctionsCommunes {
	SQL connecte;
	Scanner in = new Scanner(System.in);

	public FonctionsCommunes(SQL co){
		this.connecte=co;
	}
	
	// Fonction qui lit un String
	public String readString(){
		try{
			return in.next();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	// Fonction qui lit une ligne
	public String readLine(){
		try{
			return in.nextLine();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	//Fonction qui lit un entier
	public int readInt(){
		try{
			return in.nextInt();   //lecture du choix utilisateur    
		}
		catch(Exception e){
			in.nextLine();
			e.printStackTrace();
			return -1;
		}
	}

	//Fonction qui retourne une date sous forme AAAA-mm-JJ
	public String returnDate(int jour,int mois, int annee) {
		String j=(new Integer(jour)).toString() , m=(new Integer(mois)).toString() , a=(new Integer(annee)).toString();
		if(j.length() == 1)
			j = "0"+j;
		if(m.length() == 1)
			m = "0"+m;
		return a+"-"+m+"-"+j;
	}

	//Fonction qui retourne la date d aujourdhui
	public java.sql.Date dateToday(){
		String date;
		DateFormat format = new SimpleDateFormat("yyyy");
		date = format.format(new Date());
		format = new SimpleDateFormat("MM");
		date += "-" + format.format(new Date());
		format = new SimpleDateFormat("dd");
		date += "-" + format.format(new Date());
		return java.sql.Date.valueOf(date);
	}

	public void afficheResultat(ResultSet r) throws SQLException{
		ResultSetMetaData rm = r.getMetaData();
		List<String> l = new ArrayList<String>();
		String tmp = "";

		while(r.next()){			
			for(int i = 1; i <= rm.getColumnCount() ; i++)
				if(i == rm.getColumnCount()+1)
					tmp += r.getObject(i).toString();
				else
					tmp += r.getObject(i).toString()+"|";
			l.add(tmp);
			tmp = "";
		}
		r.close();

		String[] nomcolonne = new String[rm.getColumnCount()];
		String[][] res = new String[l.size()][rm.getColumnCount()];
		int[] taille = new int[rm.getColumnCount()];

		//recup nom colonne
		for(int i = 1; i <=  rm.getColumnCount(); i++)
			nomcolonne[i-1]=rm.getColumnName(i).toUpperCase();

		for(int i = 0; i < l.size(); i++){
			res[i] = l.get(i).split("\\|");
		}

		for(int i = 0; i < nomcolonne.length; i++){
			taille[i] = maxTaille(nomcolonne[i].length(),res, i);
		}

		int tailleTot=nomcolonne.length;
		for(int i = 0; i < nomcolonne.length; i++){
			tailleTot += taille[i];
		}

		for(int j = 0; j <= tailleTot; j++){
			System.out.print("-");
		}
		System.out.println();
		System.out.print("|");
		//affichage nom colonne
		for(int i = 0; i < nomcolonne.length; i++){
			System.out.print(nomcolonne[i]);
			for(int k = 0; k<taille[i]-nomcolonne[i].length();k++)
				System.out.print(" ");
			System.out.print("|");
		}
		System.out.println();
		for(int j = 0; j <= tailleTot; j++)
			System.out.print("-");
		System.out.println();

		//affichage resultat
		for(int i = 0; i < res.length; i++){
			System.out.print("|");
			for(int j = 0; j< res[i].length;j++){
				System.out.print(res[i][j]);
				for(int k = 0; k<taille[j]-res[i][j].length();k++)
					System.out.print(" ");
				System.out.print("|");
			}
			System.out.println("");
		}

		for(int j = 0; j <= tailleTot; j++){
			System.out.print("-");
		}
		System.out.println();
	}

	public int maxTaille(int tailleCol, String[][] s, int indice){
		int max = tailleCol;

		for(int i = 0; i < s.length; i++){
			if(s[i][indice].length() > max)
				max = s[i][indice].length();
		}

		return max;
	}

	//programme de lancement de toutes les classes
	public void prog(int i, Object n) throws ClassNotFoundException{
		// ---------------------------------------
		// Impression du menu. Pour finir, tapez 0
		// ---------------------------------------
		int c = -1;
		while(c != 0){
			try{
				switch(i){
				case 1:
					c = ((Client)n).printMenu();
					break;
				case 2:
					c = ((Douane)n).printMenu();
					break;
				case 3:
					c = ((Emballeur)n).printMenu();
					break;
				case 4:
					c = ((Gerant)n).printMenu();
					break;
				case 5:
					c = ((Transporteur)n).printMenu();
					break;
				case 98:
					try {
						c = ((Tables)n).printMenu();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
					break;
				default : 
					System.out.println("ERREUR!");
				}
			}
			catch (SQLException  e) {
				System.err.println(e.getMessage());
			}

			if (c != 0){
				System.out.println("Appuyez sur entree.");
				try {
					System.in.read();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		try {
			connecte.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String randomIDEmballeur(){
		String rep = "";
		for(int i = 0; i < 7; i++){
			if(i<3){
				int lettre = (int) (Math.random()*26+65); //code ASCII de A = 65
				rep += (char)lettre;
			}
			else{
				int chiffre = (int)(Math.random()*10);
				rep += chiffre;
			}
		}
		return rep;
	}

	public String randomIDTransporteur(){
		String rep = "";
		for(int i = 0; i < 4; i++){
			int lettre = (int) (Math.random()*26+65); //code ASCII de A = 65
			rep += (char)lettre;
		}
		return rep;
	}

	public String randomIDClient(){
		String rep = "";
		for(int i = 0 ; i < 5 ; i++){
			int lettre = (int)(Math.random()*26+65); // code ASCII de A = 65
			rep += (char)lettre;
		}
		for(int i = 0 ; i < 5 ; i++){
			int chiffre = (int)(Math.random()*10);
			rep += chiffre;
		}
		return rep;
	}

	public void affProduit(boolean c){
		String reponse = "";
		boolean b = false;

		while(!b){

			System.out.println("Afficher les produits disponibles ? (O/N)");
			reponse = this.readString();
			reponse = reponse.toUpperCase();

			if(reponse.equals("O")){
				try {
					if(c)
						this.afficheResultat(connecte.clientListerProduits());
					else
						this.afficheResultat(connecte.gerantListerProduits());
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
				b = true;
			}

			else if(reponse.equals("N")){
				System.out.println("");
				b = true;
			}

			else
				System.out.println("Erreur");

		}
	}

}
