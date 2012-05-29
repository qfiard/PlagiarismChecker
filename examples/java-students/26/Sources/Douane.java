import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Douane {
	static ConnectionBase connecte;
	static Scanner in = new Scanner(System.in);
	String nomutil;
	String client;
	public Douane(ConnectionBase conn) throws SQLException, ClassNotFoundException, IOException{
		connecte=conn;
		boolean go=true;
		String mdp;
		String entre;
		while(go){
			System.out.println("Quel est votre login Douane?");
			nomutil=in.next();
			ResultSet pass = connecte.connutil("douane WHERE login = '"+nomutil+"'");
			pass.last();
			int i = pass.getRow();  
			pass.beforeFirst(); 
			if(i!=0){
				System.out.print("\033c");
				System.out.println("Veuillez entrer votre mot de passe.");
				entre = in.next();
				pass.next();
				mdp = pass.getString("mdp");
				client= pass.getString("login");
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
		System.out.println("Bienvenue dans le menu Douane:");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Deconnexion");
		System.out.println("1 - Renvoyer un colis");
		System.out.println("2 - Lister un conteneur");
		System.out.println("3 - Lister une palette");
		System.out.println("4 - Lister un colis.");
		System.out.println("5 - Changer son login");
		System.out.println("6 - Changer son mdp");
		System.out.println("7 - Prix des produits");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();
		switch(c){
		case 0 :
			break;
		case 1 :
			System.out.println("N¡ de Colis a retourner?");
			int id = Chaines.readInt();
			connecte.douaneColis(id);
			System.out.println("La commande "+id+" est maintenant en retour a l'envoyeur");
			break;
		case 2 :
			System.out.println("Numero de conteneur?");
			int cont = Chaines.readInt();
			ResultSet commandes = connecte.connutil("palette WHERE conteneur_id = '"+cont+"'");
			System.out.println("Palettes presentes sur le coneneur");
			while (commandes != null && commandes.next()) {
				Chaines.print("|", 2);
				Chaines.print(commandes.getString("id"), 3);
				Chaines.print("|", 2);
				System.out.println();
			}
			break;
		case 3 :
			System.out.println("Numero de palette?");
			int palette = Chaines.readInt();
			ResultSet paletteR = connecte.connutil("colis WHERE palette_id = '"+palette+"'");
			System.out.println("Colis dans cette palette:");
			while (paletteR != null && paletteR.next()) {
				Chaines.print("|", 2);
				Chaines.print(paletteR.getString("id"), 3);
				Chaines.print("|", 2);
				System.out.println();
			}
			break;
		case 4 :
			System.out.println("Numero de colis?");
			int colis = Chaines.readInt();
			ResultSet colisScan = connecte.connutil("produit LEFT OUTER JOIN exemplaire on (ref_produit=ref) WHERE commande_id='"+colis+"';");
			System.out.println("Produits dans le colis:");
			while (colisScan != null && colisScan.next()) {
				Chaines.print("|", 2);
				Chaines.print(colisScan.getString("ref"), 10);
				Chaines.print("|", 2);
				Chaines.print(colisScan.getString("description"), 100);
				System.out.println();
			}
			break;
			
		case 5:
			boolean go=true;
			System.out.println("Nouveau login?");
			String log= in.next();
			while(go){
				ResultSet verif = connecte.connutil("douane WHERE login = '"+log+"'");
				verif.last();
				int i = verif.getRow();  
				verif.beforeFirst();
				if(i==1){
					System.out.println("Impossible, login dejˆ utilise, rentrer un autre login");
					log=in.next();
				}
				else{
					connecte.changervaleur("douane", nomutil, "login", log);
					System.out.println("Nouveau login applique");
					nomutil=log;
					go=false;
				}
			}
			break;
		case 40:

			
			break;
		case 6:
			System.out.println("Nouveau mdp?");
			String mdp= in.next();
			connecte.changervaleur("douane", nomutil, "mdp", mdp);
			System.out.println("Nouveau mdp applique");
			break;
		}
		return c;

	}

	public void instructions(){

	}


}
