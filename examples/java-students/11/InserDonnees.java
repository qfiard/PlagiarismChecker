package General;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


public class InserDonnees {

public static void InserDonnees(String fichier) throws NumberFormatException, IOException, SQLException{
	
		try{
			
			Connexion co = new Connexion("postgres","562662");
			BufferedReader fichier_source = new BufferedReader(new FileReader(fichier));
			String chaine;

			while((chaine = fichier_source.readLine())!= null){
				
				String[] tabChaine = chaine.split("\\|");
				int rep = Integer.parseInt(tabChaine[0]);
				
				switch(rep){
				
				case 10:
					co.ecrireEmballeur(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5]);
					break;
					
				case 20:
					co.ecrireClient(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5], tabChaine[6], tabChaine[7], tabChaine[8], tabChaine[9]);
					break;
					
				case 30:
					co.ecrireProduit(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4], tabChaine[5], tabChaine[6], tabChaine[7], tabChaine[8], tabChaine[9]);
					break;
					
				case 40:
					co.ecrireTransporteur(tabChaine[1], tabChaine[2], tabChaine[3]);
					break;
					
				case 50:
					co.ecrireDouane(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4]);
					break;
					
				case 60:
					co.ecrireGerant(tabChaine[1], tabChaine[2], tabChaine[3], tabChaine[4]);
					break;
				}
			}
			fichier_source.close();		
			co.close();
		}
		catch (FileNotFoundException e){
			System.out.println("Le fichier est introuvable !");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public static void main(String[]args) throws NumberFormatException, IOException, SQLException{
	InserDonnees("data.csv");
}

}