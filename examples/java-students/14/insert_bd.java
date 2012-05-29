import java.io.*;
import java.sql.Statement;

public class insert_bd {
	
	  public static void insert(Statement sql, String path) throws IOException
	  {
	    BufferedReader lecteurAvecBuffer = null;
	    String ligne;

	    try
	      {
		lecteurAvecBuffer = new BufferedReader(new FileReader(path));
	      }
	    catch(FileNotFoundException exc)
	      {
		System.out.println("Erreur d'ouverture");
	      }
	    while ((ligne = lecteurAvecBuffer.readLine()) != null) {

	    	
	    	String [] items = ligne.split("\\|");
	    	
		    //produits  
    		if(items[0].equals("30")) {
    	    	//for(int i = 1; i< items.length; ++i) {
        			//System.out.print(items[i] + " ");
    	    		try {
    	    			String req = "insert into produit values ('"+items[1]+"', '"+items[4]+"', '"+items[5]+"', "+items[6]+", '"+items[2]+"', "+items[8]+", "+items[7]+");";
    	    			//System.out.println(req);
    	    			sql.executeQuery(req);
					} catch (Exception e) {	}//*/
    	    	//}
    	    	//System.out.println("");
    		}
    		else if (items[0].equals("20")) {
	    		try {
	    			String req = "insert into client (login, password, type, id_client, nom_societe, suffixe_societe, adresse, ville, code_postal, pays, telephone) values ('"+items[1]+"', '"+items[9]+"', 'CLI', '"+items[1]+"','"+items[2]+"', '"+items[3]+"', '"+items[4]+"', '"+items[5]+"', '"+items[6]+"', '"+items[7]+"', '"+items[8]+"');";
	    			//System.out.println(req);
	    			sql.executeQuery(req);
				} catch (Exception e) {	}
    			
    		}
    		else if (items[0].equals("10")) {
	    		try {
	    			String req = "insert into emballeur (login, password, type, id_emballeur, nom_emballeur, prenom_emballeur, taux_erreur) values ('"+items[1]+"', '"+items[5]+"', 'EMB', '"+items[1]+"','"+items[2]+"', '"+items[3]+"', "+items[4]+");";
	    			//System.out.println(req);
	    			sql.executeQuery(req);
				} catch (Exception e) {	}
    			
    		}
    		else if (items[0].equals("40")) {
	    		try {
	    			String req = "insert into transporteur (login, password, type, code_transporteur, nom_transporteur) values ('"+items[1]+"', '"+items[3]+"', 'TRA', '"+items[1]+"','"+items[2]+"');";
	    			//System.out.println(req);
	    			sql.executeQuery(req);
				} catch (Exception e) {	}
    		}
    		else if (items[0].equals("50")) {
	    		try {
	    			String req = "insert into douane (login, password, type, pays, taux_verif) values ('"+items[3]+"', '"+items[4]+"', 'DOU', '"+items[1]+"',"+items[2]+");";
	    			//System.out.println(req);
	    			sql.executeQuery(req);
				} catch (Exception e) {	}
    		}
    		else if (items[0].equals("60")) {
	    		try {
	    			String req = "insert into gerant (login, password, type, login_gerant, prenom_gerant, nom_gerant) values ('"+items[3]+"', '"+items[4]+"', 'GER', '"+items[3]+"', '"+items[1]+"','"+items[2]+"');";
	    			sql.executeQuery(req);
	    		} catch (Exception e) {	}
    		}
    		
	    }
	    System.out.println("\nInsertion du jeu de donnees terminee\n");
	    lecteurAvecBuffer.close();
	  }
}
