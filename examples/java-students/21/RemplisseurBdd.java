import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class RemplisseurBdd {
	
	
	static Connection conn;
	static Statement st;
	static boolean emballeur = false;
	static boolean client = false;
	static boolean transporteur = false;
	static boolean douanier = false;
	static boolean gerant = false;
	static FileWriter file;
	
	public static void main(String args[]) throws IOException {
		file = new FileWriter(new File("LoginsPasswords.txt"));
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "leclercq", "dubstep");
			FileReader f = new FileReader(new File("data.csv"));
			Scanner sc = new Scanner(f);
			Scanner sc2;
			String ligne;
			String donnee;
			
			/*On vide !*/
			st = conn.createStatement();
			st.executeUpdate("DELETE FROM produits;");
			st.executeUpdate("DELETE FROM employes;");
			st.executeUpdate("DELETE FROM logins;");
			st.executeUpdate("DELETE FROM palettes;");
			st.executeUpdate("DELETE FROM clients;");
			st.executeUpdate("DELETE FROM commandes;");
			st.executeUpdate("DELETE FROM colis;");
			st.executeUpdate("DELETE FROM douaniers;");
			
			System.out.println("Remplissage Bdd, veuillez patienter...");
			
			/*On rempli, pour chaque cas on appel une methode différente*/
			while(sc.hasNextLine()) {
				
				ligne = sc.nextLine();
				sc2 = new Scanner(ligne).useDelimiter("\\|");
				donnee = sc2.next();
				//System.out.println("\nLigne: "+ligne);
				
				switch (donnee.charAt(0)) {
				case '1':
					//System.out.println("case 1");
					emballeur(ligne,sc2,conn);
					break;
				case '2':
					//System.out.println("case 2");
					client(ligne,sc2,conn);
					break;
				case '3' : 
					//System.out.println("case 3");
					produit(ligne,sc2,conn);
					break;
				case '4' :
					//System.out.println("case 4");
					transporteur(ligne,sc2,conn);
					break;
				case '5' : 
					//System.out.println("case 5");
					douane(ligne,sc2,conn);
					break;
				case '6' :
					//System.out.println("case 6");
					gerant(ligne,sc2,conn);
					break;
				}
			}
			System.out.println();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		file.flush();
		file.close();
		System.out.println("Le fichier LoginsPasswords.txt contient maintenant\nun login et un pass pour chaque type d'employes (ou douaniers/clients)");
	}
	
	public static void emballeur(String ligne,Scanner sc2, Connection conn) throws SQLException, IOException {
		st = conn.createStatement();
		ResultSet res;
		String nom, prenom, taux, mdp;
		sc2.next();
		nom = sc2.next();
		prenom = sc2.next();
		taux = sc2.next();
		mdp = sc2.next();
		
		//System.out.println("Emballeur : "+nom+"\n"+prenom+"\n"+taux+"%\n"+mdp);
		
		if (!emballeur) {
			file.write("Emballeur:\nlogin: "+nom+prenom+"\nPassword: "+mdp+"\n\n");
			emballeur = true;
		}
		
		/*Req*/
		st.executeUpdate("INSERT INTO logins (login,mdp,autorisation) VALUES ('"+nom+prenom+"','"+mdp+"','E');");
		res = st.executeQuery("SELECT id FROM logins WHERE login='"+nom+prenom+"';");
		if (res.next()) {
			int id = res.getInt(1);
			st.executeUpdate("INSERT INTO employes (id,nom,prenom,nb_colis,nb_refus,role,actif) VALUES ('"+id+"','"+nom+"','"+prenom+"','100','"+taux+"','emballeur','1');");
		}
		res.close();
	}
	
	public static void client(String ligne, Scanner sc2, Connection conn) throws SQLException, IOException {
		st = conn.createStatement();
		ResultSet res;
		sc2.next();
		String nom, prenom, mdp, addr, ville, codeP, tel, pays, societe;
		societe = sc2.next();
		sc2.next();
		addr = sc2.next();
		ville = sc2.next();
		codeP = sc2.next();
		pays = sc2.next();
		tel = sc2.next();
		mdp = sc2.next();
		prenom = "jean-luc"+(int)(Math.random()*10000);
		nom = societe;
		
		//System.out.println("Client : "+nom+"\n"+prenom+"\n"+mdp+"\nadresse: "+addr+"\nville: "+ville+"\ncodeP: "+codeP+"\npays: "+pays+"\ntel: "+tel+"\n");

		if (!client) {
			file.write("Client:\nlogin: "+nom+prenom+"\nPassword: "+mdp+"\n\n");
			client = true;
		}
		
		/*Requetes*/
		st.executeUpdate("INSERT INTO logins (login,mdp,autorisation) VALUES ('"+nom+prenom+"','"+mdp+"','C');");
		res = st.executeQuery("SELECT id FROM logins WHERE login='"+nom+prenom+"';");
		if (res.next()) {
			int id = res.getInt(1);
			st.executeUpdate("INSERT INTO clients (id,nom,prenom,societe,adresse,pays,telephone,depenses,code_p,ville) VALUES ('"+id+"','"+nom+"','"+prenom+"','"+societe+"','"+addr+"','"+pays+"','"+tel+"','"+(int)(Math.random()*10000)+1000+"','"+codeP+"','"+ville+"');");
		}
		res.close();
	}
	
	public static void produit(String ligne, Scanner sc2, Connection conn) throws SQLException {
		st = conn.createStatement();
		String nom = sc2.next();
		String description = sc2.next();
		//on passe deux fois
		sc2.next();sc2.next();
		String genre = sc2.next();
		String cout = sc2.next();
		String taux = sc2.next();
		String poids = sc2.next();
		String stock = sc2.next();
		
		//System.out.println(genre+"-"+cout+"-"+poids+"-"+stock+"-description: "+description);
		
		/*requetes sql*/
		st.executeUpdate("INSERT INTO produits (nom,description,genre,cout,poids_g,stock,vendus,taux) VALUES ('"+nom+"','"+description+"','"+genre+"','"+cout+"','"+poids+"','"+stock+"','"+(int)(Math.random()*10)+"','"+taux+"');");
	}
	
	public static void transporteur(String ligne, Scanner sc2, Connection conn) throws SQLException, IOException {
		st = conn.createStatement();
		ResultSet res;
		String scac = sc2.next();
		String nom = sc2.next();
		String mdp = sc2.next();
		
		if (!transporteur) {
			file.write("Transporteur:\nlogin: "+scac+"\nPassword: "+mdp+"\n\n");
			transporteur = true;
		}
		
		/*Appel sql*/
		st.executeUpdate("INSERT INTO logins (login,mdp,autorisation) VALUES ('"+scac+"','"+mdp+"','T');");
		res = st.executeQuery("SELECT id FROM logins WHERE login='"+scac+"';");
		if (res.next()) {
			int id = res.getInt(1);
			st.executeUpdate("INSERT INTO employes (id,nom,prenom,role,actif) VALUES ('"+id+"','"+nom+"','"+scac+"','transporteur','1');");
		}
		res.close();
	}
	
	public static void douane(String ligne, Scanner sc2, Connection conn) throws SQLException, IOException {
		st = conn.createStatement();
		ResultSet res;
		String pays = sc2.next();
		String taux = sc2.next();
		String login = sc2.next();
		String mdp = sc2.next();
		
		if (!douanier) {
			file.write("Douanier:\nlogin: "+login+"\nPassword: "+mdp+"\n\n");
			douanier = true;
		}
		
		/*sql*/
		st.executeUpdate("INSERT INTO logins (login,mdp,autorisation) VALUES ('"+login+"','"+mdp+"','D');");
		res = st.executeQuery("SELECT id FROM logins WHERE login='"+login+"';");
		if (res.next()) {
			int id = res.getInt(1);
			st.executeUpdate("INSERT INTO douaniers (id,pays,taux) VALUES ('"+id+"','"+pays+"','"+(100-Integer.parseInt(taux))+"');");
		}
		res.close();
	}
	
	public static void gerant (String ligne, Scanner sc2, Connection conn) throws SQLException, IOException {
		st = conn.createStatement();
		ResultSet res;
		String prenom = sc2.next();
		String nom = sc2.next();
		String login = sc2.next();
		String mdp = sc2.next();
		
		if (!gerant) {
			file.write("Gerant:\nlogin: "+login+"\nPassword: "+mdp+"\n\n");
			gerant = true;
		}
		
		/*Appel sql*/
		st.executeUpdate("INSERT INTO logins (login,mdp,autorisation) VALUES ('"+login+"','"+mdp+"','G');");
		res = st.executeQuery("SELECT id FROM logins WHERE login='"+login+"';");
		if (res.next()) {
			int id = res.getInt(1);
			st.executeUpdate("INSERT INTO employes (id,nom,prenom,role,actif) VALUES ('"+id+"','"+nom+"','"+prenom+"','gerant','1');");
		}
		res.close();
	}
	
	
	
	
	
	
	
	
	
	/*public static void creationTablesBdd(Connection conn) {
		int i = 0;
		String w[] = new String[100];
						
		w[i++] = "CREATE TABLE clients (id_client integer NOT NULL, id numeric(9,0) NOT NULL,nom character varying(40) NOT NULL," +
				"prenom character varying(40) NOT NULL, societe character varying(40),adresse character varying(60) NOT NULL," +
				"pays character varying(40) NOT NULL, telephone character varying(20), depenses numeric(9,0), code_p character varying(20)," +
				"ville character varying(40));";
		
		w[i++] = "CREATE TABLE colis (id_colis integer NOT NULL,id_emb numeric(9,0) NOT NULL,id_palette numeric(9,0) NOT NULL,id_client numeric(9,0)," +
				"motif character varying(255)," +
				"date_emb date);";
		
		w[i++] = "CREATE TABLE commandes (" +
				"id_comm integer NOT NULL," +
				"id_prod numeric(9,0) NOT NULL,id_client numeric(9,0) NOT NULL,id_emb numeric(9,0)," +
				"quantite numeric(9,0) NOT NULL," +
				"id_colis numeric(9,0)," +
				"etat character varying(20)," +
				"date_livraison date," +
				"controle character varying(3)," +
				"CONSTRAINT ch_controle CHECK (((controle)::text = ANY ((ARRAY['oui'::character varying, ',non'::character varying])::text[])))," +
				"CONSTRAINT ch_etat CHECK (((etat)::text = ANY (ARRAY[('expédié'::character varying)::text, ('emballé'::character varying)::text, " +
				"('produit'::character varying)::text, ('refusé'::character varying)::text]))));";
		
		w[i++] = "CREATE TABLE douaniers (" +
				"id_douanier integer NOT NULL," +
				"id numeric(9,0) NOT NULL," +
				"pays character varying(40) NOT NULL," +
				"taux numeric(3,0));";
		
		w[i++] = "CREATE TABLE employes (" +
				"id_employe integer NOT NULL," +
				"id numeric(9,0) NOT NULL," +
				"nom character varying(40) NOT NULL," +
				"prenom character varying(40) NOT NULL," +
				"nb_colis numeric(9,0)," +
				"nb_refus numeric(9,0)," +
				"nb_jours numeric(7,0)," +
				"last_day date," +
				"role character varying(20)," +
				"actif numeric(1,0)," +
				"CONSTRAINT ch_actif CHECK ((actif = ANY (ARRAY[(0)::numeric, (1)::numeric])))," +
				"CONSTRAINT ch_role CHECK (((role)::text = ANY ((ARRAY['gerant'::character varying, 'transporteur'::character varying, 'emballeur'::character varying])::text[]))));";
		
		w[i++] = "CREATE TABLE logins (" +
				"id integer NOT NULL," +
				"login character varying(60) NOT NULL," +
				"mdp character varying(80) NOT NULL," +
				"autorisation character varying(1) NOT NULL," +
				"CONSTRAINT ch_autorisation CHECK (((autorisation)::text = ANY (ARRAY[('G'::character varying)::text, ('C'::character varying)::text, ('E'::character varying)::text, ('D'::character varying)::text, ('T'::character varying)::text]))));";
		
		w[i++] = "CREATE TABLE palettes (" +
				"id_palette integer NOT NULL," +
				"id_trans numeric(9,0));";
		
		w[i++] = "CREATE TABLE produits (" +
				"id_prod integer NOT NULL," +
				"description character varying(255) NOT NULL," +
				"genre character varying(9) NOT NULL," +
				"cout numeric(8,0)," +
				"poids_g numeric(11,0) NOT NULL," +
				"stock numeric(9,0) NOT NULL," +
				"vendus numeric(9,0) NOT NULL," +
				"nom character varying(80)," +
				"taux numeric(3,0)," +
				"CONSTRAINT ch_genre CHECK (((genre)::text = ANY ((ARRAY['N'::character varying, 'D'::character varying, 'F'::character varying])::text[]))));";
	
		try {
			Statement st = conn.createStatement();
			for (int j=0;j<i;j++) {
				try {
					st.execute(w[j]);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			st.close();
		}
		catch(Exception e) {
			System.out.println("Erreur: "+e);
		}
	}*/
}
