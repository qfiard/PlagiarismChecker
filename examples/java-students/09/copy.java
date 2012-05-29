import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.sql.*;
import java.util.*;

//lire le fichier, tocker dans un String[][] puis remplir les table
public class copy{
	public Statement st ;
	public Connection conn;
	public String[][] produits;
	public String[][] embaleurs;
	public String[][] clients;
	public String[][] transporteurs;
	public String[][] douanes;
	public String[] gerant;
	public copy(){
		produits = new String[500][9]; //pas plus de 500 produits
		embaleurs = new String[10][5];
		clients = new String[150][9];
		transporteurs = new String[15][3];
		douanes = new String[10][4];
		gerant = new String[4];
		String fichier ="donnees.txt";
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne,tete;
			int fin;
			int p = 0;
			int e = 0;
			int c = 0;
			int t = 0;
			int d = 0;
			while ((ligne=br.readLine())!=null){
				fin = ligne.indexOf ("|");
				tete = ligne.substring(0,fin); 
				if(tete.equals("10")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					embaleurs[e][0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					embaleurs[e][1] = tete.substring(0,fin);		

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					embaleurs[e][2] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					embaleurs[e][3] = tete.substring(0,fin);	

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					embaleurs[e][4] = tete;

					e++;
				}
				else if(tete.equals("20")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					clients[c][0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][1] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][2] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][3] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][4] = tete.substring(0,fin);


					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][5] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][6] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][7] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					clients[c][8] = tete;

					c++;

				}
				else if(tete.equals("30")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					produits[p][0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][1] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][2] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][3] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][4] = tete.substring(0,fin);


					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][5] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][6] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][7] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					produits[p][8] = tete;

					p++;



				}
				else if(tete.equals("40")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					transporteurs[t][0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					transporteurs[t][1] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					transporteurs[t][2] = tete;

					t++;

				}
				else if(tete.equals("50")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					douanes[d][0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					douanes[d][1] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					douanes[d][2] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					douanes[d][3] = tete;

					d++;


				}
				else if(tete.equals("60")){
					tete = ligne.substring(fin+1,ligne.length());
					fin = tete.indexOf ("|");
					gerant[0] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					gerant[1] = tete.substring(0,fin);			

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					gerant[2] = tete.substring(0,fin);

					tete = tete.substring(fin+1,tete.length());
					fin = tete.indexOf ("|");
					gerant[3] = tete;
				}
			}
			br.close(); 
		}				
		catch (Exception ee){
			System.out.println(ee.toString());
		}		
	}

   public void Connect(String login, String password) throws SQLException, ClassNotFoundException{
	try{
		Class.forName("org.postgresql.Driver");
	}catch(Exception ee){
		System.err.println("Probleme driver");
		System.exit(1);
	}			
        this.conn= DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login, login, password);
        this.st=conn.createStatement();

    }

	public static void main(String[] args)throws SQLException, ClassNotFoundException{

		copy cop = new copy();

		Console c = System.console();
		if(c == null){
			System.err.println("Pas de console");
			System.exit(2);				
		}
		String login = "benelhad";//c.readLine("Entrer login: ");
		//char[] pw = c.readPassword("%s","Enter mot de passe: ");
		String mdp = "18011990";//new String(pw);

		try{
			String chaine = "";
			cop.Connect(login,mdp);
			chaine += "(\'"+cop.gerant[2]+"\',\'"+cop.gerant[3]+"\'),";
			for(int i=0; i<cop.clients.length;i++){
				if(cop.clients[i][0] != null){
					chaine+="(\'"+cop.clients[i][0]+"\',\'"+cop.clients[i][8]+"\'),";
				}
			}
			for(int i=0; i<cop.embaleurs.length;i++){
				if(cop.embaleurs[i][0] != null){
					chaine+="(\'"+cop.embaleurs[i][0]+"\',\'"+cop.embaleurs[i][4]+"\'),";
				}
			}
			for(int i=0; i<cop.transporteurs.length;i++){
				if(cop.transporteurs[i][0] != null){
					chaine+="(\'"+cop.transporteurs[i][0]+"\',\'"+cop.transporteurs[i][2]+"\'),";
				}
			}
			for(int i=0; i<cop.douanes.length;i++){
				if(cop.douanes[i][0] != null){
					chaine+="(\'"+cop.douanes[i][2]+"\',\'"+cop.douanes[i][3]+"\')";
					if(cop.douanes[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO utilisateur VALUES "+chaine+"\n");
			/********************************************/
			chaine = "";
			for(int i=0; i<cop.clients.length;i++){
				if(cop.clients[i][0] != null){
chaine+="(\'"+cop.clients[i][0]+"\',\'"+cop.clients[i][8]+"\',\'"+cop.clients[i][1]+"\',\'"+cop.clients[i][2]+"\',\'"+cop.clients[i][3]+"\',\'"+cop.clients[i][4]+"\',\'"+cop.clients[i][5]+"\',\'"+cop.clients[i][6]+"\',\'"+cop.clients[i][7]+"\',\'client\')";
					if(cop.clients[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO client VALUES "+chaine);
			/*******************************************/
			chaine = "";
			for(int i=0; i<cop.transporteurs.length;i++){
				if(cop.transporteurs[i][0] != null){
chaine+="(\'"+cop.transporteurs[i][0]+"\',\'"+cop.transporteurs[i][2]+"\',\'"+cop.transporteurs[i][1]+"\',\'transporteur\')";
					if(cop.transporteurs[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO transporteur VALUES "+chaine);
			/*******************************************/
			chaine = "";
			for(int i=0; i<cop.embaleurs.length;i++){
				if(cop.embaleurs[i][0] != null){
chaine+="(\'"+cop.embaleurs[i][0]+"\',\'"+cop.embaleurs[i][4]+"\',\'"+cop.embaleurs[i][1]+"\',\'"+cop.embaleurs[i][2]+"\',0,\'embaleur\')";
					if(cop.embaleurs[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO embaleur VALUES "+chaine);
	                /*******************************************/
			cop.st.execute("INSERT INTO gerant VALUES (\'"+cop.gerant[2]+"\',\'"+cop.gerant[3]+"\',\'"+cop.gerant[1]+"\',\'"+cop.gerant[0]+"\',\'gerant\');");
	                /*******************************************/
			chaine = "";
			for(int i=0; i<cop.douanes.length;i++){
				if(cop.douanes[i][0] != null){
chaine+="(\'"+cop.douanes[i][2]+"\',\'"+cop.douanes[i][3]+"\',0,\'"+cop.douanes[i][0]+"\',\'douane\')";
					if(cop.douanes[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO douane VALUES "+chaine);
	                /*******************************************/	
			chaine = "";
			for(int i=0; i<cop.produits.length;i++){
				if(cop.produits[i][0] != null){
chaine+="(\'"+cop.produits[i][0]+"\',null,\'"+cop.produits[i][1]+"\',\'"+cop.produits[i][2]+"\',\'"+cop.produits[i][3]+"\',\'"+cop.produits[i][4]+"\',\'"+cop.produits[i][5]+"\',\'"+cop.produits[i][6]+"\',\'"+cop.produits[i][7]+"\',\'"+cop.produits[i][8]+"\')";
					if(cop.produits[i+1][0] != null){chaine += ",";}
					else chaine += ";";
				}
			}
			cop.st.execute("INSERT INTO produit VALUES "+chaine);
			/*******************************************/		
			cop.st.close();				
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}	


	}	
}


