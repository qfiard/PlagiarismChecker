import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class gerant {
	static ConnectionBase connecte;
	static Scanner in = new Scanner(System.in);

	public gerant(ConnectionBase conn) throws SQLException, ClassNotFoundException, IOException{
		connecte=conn;
		String entre = null;
		System.out.print("\033c");
		System.out.println("Veuillez entrer votre mot de passe.");
		entre = in.next();
		ResultSet pass = connecte.connutil("gerant");
		pass.next();
		String mdp = pass.getString(4);

		while(!mdp.equals(entre)){
			System.out.println("Veuillez reessayer.");
			entre = in.next();

		}
		System.out.println("Mot de passe correct");

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
		System.out.println("Bienvenue dans le menu Gerant:");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Deconnexion");
		System.out.println("1 - Changer le prix d'un produit");
		System.out.println("2 - Supprimer un emballeur");
		System.out.println("3 - Supprimer un transporteur");
		System.out.println("4 - Afficher les Emballeurs & transporteurs");
		System.out.println("5 - Afficher les Clients");
		System.out.println("6 - Afficher les produits les plus vendus");
		System.out.println("7 - Afficher le nombre de colis d'un emballeur / jour");
		System.out.println("8 - Clients les plus depensiers");
		System.out.println("");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = Chaines.readInt();
		switch(c){
		case 0 :
			break;
		case 1 :
			System.out.println("Ref du produit?");
			String refproduit = Chaines.readString();
			System.out.println("Nouveau prix?");
			int nouveauprix = Chaines.readInt();
			connecte.majPrix(nouveauprix, refproduit);
			System.out.println("Prix mis a jour");
			break;
		case 2 :
			System.out.println("Code de l'emballeur a licencier?");
			String id = in.next();
			connecte.supprimerE(id);
			System.out.println("Emballeur supprime");
			break;
		case 3 :
			System.out.println("Code du transporteur a licencier?");
			String ident = in.next();
			connecte.supprimerT(ident);
			System.out.println("Transporteur supprime");
			break;
		case 5 :
			System.out.println("Visualisation du contenu de la table Client");	
			ResultSet contenu = connecte.affichage("client");
			Chaines.print("id", 10);
			Chaines.print("Scte", 10);
			Chaines.print("suffixe", 9);
			Chaines.print("adresse", 40);
			Chaines.print("ville", 10);
			Chaines.print("cp", 7);
			Chaines.print("pays", 10);
			Chaines.print("login", 10);
			System.out.println();
			while (contenu != null && contenu.next()) {
				Chaines.print(contenu.getString("id"), 10);
				Chaines.print(contenu.getString("societe"), 10);
				Chaines.print(contenu.getString("sufscte"), 9);
				Chaines.print(contenu.getString("adresse"), 40);
				Chaines.print(contenu.getString("ville"), 10);
				Chaines.print(contenu.getString("codepostal"), 7);
				Chaines.print(contenu.getString("pays"), 10);
				Chaines.print(contenu.getString("login"), 10);
				System.out.println();
			}
			break;
		case 4:
			System.out.println("Visualisation du contenu de la table emballeur");	
			ResultSet contenu2 = connecte.affichage("emballeur");
			Chaines.print("id", 10);
			Chaines.print("nom", 10);
			Chaines.print("prenom", 10);
			Chaines.print("Taux", 5);
			Chaines.print("mdp", 10);
			System.out.println();
			while (contenu2 != null && contenu2.next()) {
				Chaines.print(contenu2.getString("id"), 10);
				Chaines.print(contenu2.getString("nom"), 10);
				Chaines.print(contenu2.getString("prenom"), 10);
				Chaines.print(String.valueOf(contenu2.getInt("tauxerreur")), 5);
				Chaines.print(contenu2.getString("mdp"), 10);
				System.out.println();
			}
			System.out.println("Visualisation du contenu de la table transport");	
			contenu2 = connecte.affichage("transport");
			Chaines.print("scac", 10);
			Chaines.print("nomtrans", 10);
			Chaines.print("Mot de passe", 13);
			System.out.println();
			while (contenu2 != null && contenu2.next()) {
				Chaines.print(contenu2.getString("scac"), 10);
				Chaines.print(contenu2.getString("nomtrans"), 10);
				Chaines.print(contenu2.getString("mdp"), 13);
				System.out.println();
			}
			break;
		case 7:
			System.out.println("Identitfiant de l'emballeur?");
			String s = Chaines.readString();
			ResultSet cparj = connecte.gerantSpec1(s);
			System.out.print("L'emballeur "+s+"emballe ");
			Chaines.print(cparj.getString(1), 10); 
			System.out.print(" par jour");
			System.out.println();
			break;
		case 8:
			ResultSet cdpcier = connecte.gerantSpec3();
			Chaines.print("Prix", 10);
			Chaines.print("Client", 10);
			System.out.println();
			while (cdpcier != null && cdpcier.next()) {
				Chaines.print(cdpcier.getString(1), 10);
				Chaines.print(cdpcier.getString(2), 10);
				System.out.println();
			}
			break;
		case 6:
			ResultSet colis = connecte.gerantSpec2();
			while (colis != null && colis.next()){
				Chaines.print(colis.getString(1), 10);
				Chaines.print(" | ", 3);
				Chaines.print(String.valueOf(colis.getInt(2)), 5);
				System.out.println();
			}
			break;
		}
		return c;

	}

	public void instructions(){

	}


}
