import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Clients {
	static ConnectionBase connecte;
	static Scanner in = new Scanner(System.in);
	String nomutil;
	String client;
	public Clients(ConnectionBase conn) throws SQLException, ClassNotFoundException, IOException{
		connecte=conn;
		boolean go=true;
		String mdp;
		String entre;
		while(go){
			System.out.println("Quel est votre login client?");
			nomutil=in.next();
			ResultSet pass = connecte.connutil("client WHERE login = '"+nomutil+"'");
			pass.last();
			int i = pass.getRow();  
			pass.beforeFirst(); 
			if(i!=0){
				System.out.print("\033c");
				System.out.println("Veuillez entrer votre mot de passe.");
				entre = in.next();
				pass.next();
				mdp = pass.getString("mdp");
				client= pass.getString("id");
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
		System.out.println("Bienvenue dans le menu Client:");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Deconnexion");
		System.out.println("1 - Suivre un colis");
		System.out.println("2 - Resume des commandes");
		System.out.println("3 - Lister les produits");
		System.out.println("4 - Passer une commande. PAS FINI");
		System.out.println("5 - Changer son login");
		System.out.println("6 - Changer son mdp");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();
		switch(c){
		case 0 :
			break;
		case 1 :
			System.out.println("Reference de la commande lie au colis?");
			String cmd = in.next();
			ResultSet colis = connecte.connutil("colis WHERE commande='"+cmd+"'");
			colis.next();
			String rep = colis.getString("etat");
			System.out.println("La commande "+cmd+" est "+rep);
			break;
		case 2 :
			ResultSet commandes = connecte.connutil("commande WHERE id_client = '"+client+"'");
			while (commandes != null && commandes.next()) {
				Chaines.print(commandes.getString("id"), 2);
				Chaines.print("|", 2);
				Chaines.print(commandes.getString("datecmd"), 15);
				System.out.println();
			}
			break;
		case 3 :
			System.out.println("Visualisation des produits");	
			ResultSet contenu2 = connecte.affichage("produit");
			Chaines.print("ref", 10);
			Chaines.print("Description", 100);
			Chaines.print("Prix", 5);
			System.out.println();
			while (contenu2 != null && contenu2.next()) {
				Chaines.print(contenu2.getString("ref"), 10);
				Chaines.print("|", 2);
				Chaines.print(contenu2.getString("description"), 100);
				Chaines.print("|", 2);
				Chaines.print(String.valueOf(contenu2.getInt("prix")), 5);
				System.out.println();
			}
			break;
		case 5:
			boolean go=true;
			System.out.println("Nouveau login?");
			String log= in.next();
			while(go){
				ResultSet verif = connecte.connutil("client WHERE login = '"+log+"'");
				verif.last();
				int i = verif.getRow();  
				verif.beforeFirst();
				if(i==1){
					System.out.println("Impossible, login deja utilise, rentrez un autre login");
					log=in.next();
				}
				else{
					connecte.changervaleur("client", nomutil, "login", log);
					System.out.println("Nouveau login applique");
					nomutil=log;
					go=false;
				}
			}
			break;
		case 4:
			System.out.println("Combien de produits?");
			int nbrprdt = Chaines.readInt();
			String[] prdt=new String[nbrprdt];
			int i=0,j;
			ResultSet tmp;
			for(i=0;i<nbrprdt;i++){
				System.out.println("Produit n¡"+i+"?");
				prdt[i]=in.next();
				tmp=connecte.connutil("produit WHERE ref='"+prdt[i]+"'");
				tmp.last();
				j = tmp.getRow();  
				tmp.beforeFirst(); 
				if(j==0){
					System.out.println("Erreur, produit non existant dans la base");
					i--;
				}
				else
					System.out.println("Ok!");
			}
			System.out.println("A quelle date voulez vous recevoir le colis?");
			//SET DATE = local time
			//JE SAIS PAS COMMENT CONTINUER, je recup comment la date??
			
			break;
		case 6:
			System.out.println("Nouveau mdp?");
			String mdp= in.next();
			connecte.changervaleur("client", nomutil, "mdp", mdp);
			System.out.println("Nouveau mdp applique");
			break;
		}
		return c;

	}

	public void instructions(){

	}


}
