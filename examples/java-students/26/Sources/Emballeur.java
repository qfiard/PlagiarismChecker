import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Emballeur {
	static ConnectionBase connecte;
	static Scanner in = new Scanner(System.in);
	String nomutil;
	public Emballeur(ConnectionBase conn) throws SQLException, ClassNotFoundException, IOException{
		connecte=conn;
		boolean go=true;
		String mdp;
		String entre;
		while(go){
			System.out.println("Quel est votre id emballeur?");
			nomutil=in.next();
			ResultSet pass = connecte.connutil("emballeur WHERE id = '"+nomutil+"'");
			pass.last();
			int i = pass.getRow();  
			pass.beforeFirst(); 
			if(i!=0){
				System.out.print("\033c");
				System.out.println("Veuillez entrer votre mot de passe.");
				entre = in.next();
				pass.next();
				mdp = pass.getString(5);
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
		System.out.println("Bienvenue dans le menu Emballeur:");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Deconnexion");
		System.out.println("1 - Afficher les commandes d'un client en particulier");
		System.out.println("2 - Noter un colis comme emballe");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();
		switch(c){
		case 0 :
			break;
		case 1 :
			System.out.println("Client a scanner?");
			String client = in.next();
			ResultSet result = connecte.embCC(client);
			Chaines.print("|", 2);
			Chaines.print("idCmd", 10);
			Chaines.print("|", 2);
			Chaines.print("DateDeLivraison", 15);
			Chaines.print("|", 2);
			Chaines.print("DateCommande", 13);
			Chaines.print("|", 2);
			Chaines.print("DateLivVoulue", 14);
			Chaines.print("|", 2);
			Chaines.print("Ref Produit", 15);
			Chaines.print("|", 2);
			Chaines.print("Qualifiant", 13);
			Chaines.print("|", 2);
			Chaines.print("Poids", 13);
			Chaines.print("|", 2);
			Chaines.print("NbrDeProduits", 14);
			Chaines.print("|", 2);
			System.out.println();
			while (result != null && result.next()) {
				Chaines.print("|", 2);
				Chaines.print(String.valueOf(result.getInt(1)), 10);
				Chaines.print("|", 2);
				Chaines.print(result.getString(2), 15);
				Chaines.print("|", 2);
				Chaines.print(result.getString(3), 13);
				Chaines.print("|", 2);
				Chaines.print(result.getString(4), 14);
				Chaines.print("|", 2);
				Chaines.print(result.getString(5), 15);
				Chaines.print("|", 2);
				Chaines.print(result.getString(6), 13);
				Chaines.print("|", 2);
				Chaines.print(String.valueOf(result.getInt(8)), 13);
				Chaines.print("|", 2);
				Chaines.print(String.valueOf(result.getInt(7)), 14);
				Chaines.print("|", 2);
				System.out.println();
			}
			break;
		case 2:
			
			break;
		case 4:
			break;
		}
		return c;

	}

	public void instructions(){

	}


}
