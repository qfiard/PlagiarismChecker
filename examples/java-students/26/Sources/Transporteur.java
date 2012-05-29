import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Transporteur{
	static ConnectionBase connecte;
	static Scanner in = new Scanner(System.in);
	String nomutil;
	
	public Transporteur(ConnectionBase conn) throws SQLException, ClassNotFoundException, IOException{
		connecte=conn;
		boolean go=true;
		String mdp;
		String entre;
		while(go){
			System.out.println("Quel est votre scac transporteur?");
			nomutil=in.next();
			ResultSet pass = connecte.connutil("transport WHERE scac = '"+nomutil+"'");
			pass.last();
			int i = pass.getRow();  
			pass.beforeFirst(); 
			if(i!=0){
				System.out.print("\033c");
				System.out.println("Veuillez entrer votre mot de passe.");
				entre = in.next();
				pass.next();
				mdp = pass.getString(3);
				while(!mdp.equals(entre)){
					System.out.println("Veuillez reessayer.");
					entre = in.next();				
				}
				System.out.println("Mot de passe correct");
				go=false;
			}
			
		}
		int c = -1;
		while(c != 0){
			c = printMenu();
			if (c != 0){
				System.out.println("Appuyez sur entree.");
				System.in.read();
			}

		}
	}

	public int printMenu() throws SQLException{
		int c=-1;
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------
		System.out.println("Bienvenue dans le menu Transporteur:");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Deconnexion");
		System.out.println("1 - Lister les colis fragiles et dangereux NF");
		System.out.println("2 - Date limite de livraison des colis");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();
		switch(c){
		case 0 :
			break;
		case 1 :
			System.out.println("Colis fragiles");
			ResultSet go = connecte.connutil("colis WHERE ");
			break;
		case 2 :
			ResultSet colis = connecte.connutil("colis;");
			Chaines.print("Id", 3);
			Chaines.print("|", 2);
			Chaines.print("Adresse", 50);
			Chaines.print("|", 2);
			Chaines.print("Date limite", 16);
			System.out.println();
			while (colis != null && colis.next()) {
				Chaines.print(colis.getString("id"), 3);
				Chaines.print("|", 2);
				Chaines.print(colis.getString("adresse"), 50);
				Chaines.print("|", 2);
				Chaines.print(colis.getString("date"), 16);
				System.out.println();
			}
			break;
		case 3:
			break;
		case 4:
			break;
		}
		return c;

	}

	public void instructions(){

	}


}
