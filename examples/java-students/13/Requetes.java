import java.sql.*;

public class Requetes{

	private static boolean nonSQL = false;



	private static Connection conn = null;
	private static Statement stmt = null;

	private static String mail = null;
	private static String mdp = null;
	private static int identifiant = -1; 

	private static int typeConnexion = -1; //gerant, client etc..
	private static boolean ok = false;


	private static String loginBD = "farah";//"marielle";
	private static String mdpBD = "swan";//"azerty";

	private static Object[][] tab = null;






	/* Connexion a la base
	public Requetes(String login, String mdp) throws SQLException,ClassNotFoundException{
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+login, login, mdp);
		stmt = conn.createStatement();
	}	*/



	public static void setTab(Object[][] t){ tab = t;}
	public static Object[][] getTab(){return tab;}

	// Connexion d'un utilisateur
	// Vérifiée
	public static boolean connexion(String email,String mot,int typeCompte) throws SQLException,ClassNotFoundException {
		typeConnexion = typeCompte;
		if (nonSQL)return true;
		else{

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			//System.out.println("Connexion en cours");
			String table="", cle="";	

			switch(typeCompte){
			case 1: table = "DOUANE"; cle = "ID_DOU"; break;
			case 2: table = "EMBALLEUR"; cle = "ID_EMB"; break;
			case 3: table = "TRANSPORTEUR"; cle = "ID_TRA"; break;
			case 4: table = "CLIENT"; cle = "ID_CLI"; break;
			default: table = "GERANT"; cle = "ID_GER"; 
			}

			String requete = "select " + cle + " from " + table
					+ " where MAIL = '"+email+"' and MDP = '" + mot + "';";
			//System.out.println("Connexion en cours");
			ResultSet res = stmt.executeQuery(requete);
			//System.out.println("Connexion en cours");
			//System.out.println(res);
			if (res.next()){
				//System.out.println("utilisateur trouvé");
				mail = email;
				mdp = mot;
				ok = true;
				identifiant = idDeMailType(mail,typeCompte);
				//System.out.println(identifiant);
				return true;
			}
			else {
				System.out.println("utilisateur non trouvé !!!");

				return false;
			}
		}
	}

	//Verifiée
	public static Object[][] douane1(){


		if (nonSQL) return new Object [][] {
				{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
				{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
				{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
				{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
				{ new Integer(254),  new Integer(111),null,  new Integer(709), null, null},
				{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
				{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
				{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
				{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
				{ new Integer(254),  new Integer(111),null,  new Integer(709), null, null},
				{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
				{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
				{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
				{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
				{ new Integer(254),  new Integer(111),null,  new Integer(709), null, null},
				{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
				{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
				{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
				{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
				{ new Integer(254),  new Integer(111),null, new Integer(709), null, null},
				{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
				{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
				{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
				{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
				{ new Integer(254),  new Integer(111),null,  new Integer(709), null, null}
		};
		else {
			//System.out.println("dansRequetes.idDeMailType("france@douane.fr",1) le ELSE");

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			catch (Exception e){
				//System.out.println("erreur ici");
				return null;
			}

			int idPays = idPaysDeIdDouane(identifiant);
			//System.out.println(idPays);

			// A completer pr avoir le nb de lignes !
			String requete = "select count(*) from COLIS, PALETTE, TYPE_VEH, VEHICULE where(COLIS.ID_COM= (select COMMANDE.ID_COM from COMMANDE where COMMANDE.ID_CLI= (select CLIENT.ID_CLI from CLIENT where CLIENT.ID_PAY="+idPays+"))) and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH);";
			ResultSet res;
			int nbLigne=0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
				//System.out.println(nbLigne);
			}
			catch(Exception e){
				// Erreur
				//System.out.println("erreur ici2");
				return null;
			}


			// A completer pr avoir le tableau
			requete = "select COLIS.ID_COL, PALETTE.ID_PAL, TYPE_VEH.NOM, VEHICULE.ID_VEH, VEHICULE.PASSAGE,COLIS.VERDICT from COLIS, PALETTE, TYPE_VEH, VEHICULE where(COLIS.ID_COM= (select COMMANDE.ID_COM from COMMANDE where COMMANDE.ID_CLI= (select CLIENT.ID_CLI from CLIENT where CLIENT.ID_PAY="+idPays+"))) and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH);";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e){
				// Erreur
				//System.out.println("erreur ici3");
				return null;

			}

			Object s;
			Object[][] tab = new Object[nbLigne][6];
			try{
				for (int i=0; i<nbLigne;i++){
					res.next();

					tab[i][0] = new Integer(res.getInt(1));
					tab[i][1] = new Integer(res.getInt(2));
					tab[i][2] = res.getString(3);

					tab[i][3] = new Integer(res.getInt(4));
					tab[i][4] = res.getString(5);
					s = res.getObject(6);
					//System.out.println("s"+s);
					if (s==null) tab[i][5] = "En attente";
					else {
						//System.out.println("s +"+s.toString().charAt(0));
						if (s.toString().charAt(0)=='f') tab[i][5] = "NON Valide";
						if (s.toString().charAt(0)=='t')tab[i][5] = "Valide";
						if ((s.toString().charAt(0)!='t')&&(s.toString().charAt(0)!='f')) tab[i][5] = "Erreur";
					}

				}
			}
			catch(Exception e){
				System.out.println(e);

			}
			//System.out.println("->"+tab[0][5]);
			return tab;
		}
	}
	public static int idDeMailType(String mail,int typeCompte){
		if (nonSQL)return 1;
		else{



			try{
				switch(typeCompte){
				case 1: return idDeDouane(mail);
				case 2: return idDeEmballeur(mail);
				case 3: return idDeTransporteur(mail);
				case 4: return idDeClient(mail);
				default: return idDeGerant(mail); 
				}
			}
			catch(Exception e){
				System.out.println(e);
				return -1;
			}

		}
	}
	public static void douane2(){
		// a faire
	}
	public String paysDeIdPays(int id) throws SQLException,ClassNotFoundException {
		if (nonSQL)return "France";
		else{

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select NOM from PAYS where ID_PAY="+id+";";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return null;
			}
		}
	}
	public static int idPaysDeIdDouane(int id){
		if (nonSQL) return 1;
		else{
			try{

				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select ID_PAY from DOUANE where ID_DOU="+id+";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static String nomPaysDeDouane(){
		return nomPaysDeIdPays(idPaysDeIdDouane(identifiant));
	}
	public static String nomPaysDeIdPays(int id){
		if (nonSQL) return "France";
		else{
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select NOM from PAYS where ID_PAY="+id+";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return null;
			}


		}
	}
	public static int idDeDouane(String mail) throws SQLException,ClassNotFoundException {
		if (nonSQL)return 1;
		else{

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select ID_DOU from DOUANE where MAIL='"+mail+"';";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeEmballeur(String mail) throws SQLException,ClassNotFoundException {
		if (nonSQL)return 1;
		else{


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select ID_EMB from EMBALLEUR where MAIL='"+mail+"';";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}
		}
	}
	public static int idDeTransporteur(String mail) throws SQLException,ClassNotFoundException {
		if (nonSQL)return 1;
		else{


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select ID_TRA from TRANSPORTEUR where MAIL='"+mail+"';";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeClient(String mail) throws SQLException,ClassNotFoundException {
		if (nonSQL)return 1;
		else{


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select ID_CLI from CLIENT where MAIL='"+mail+"';";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeGerant(String mail) throws SQLException,ClassNotFoundException {
		if (nonSQL)return 1;
		else{


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String requete = "select ID_GER from GERANT where MAIL='"+mail+"';";
			ResultSet res;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int paletteDuColis(int idColis){
		return 1234;
	}
	public static String verdict(int idColis){
		if (nonSQL)return "Valide";
		else{

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select VERDICT from COLIS where ID_COL="+idColis+";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				Object o = res.getObject(1);

				if (o==null) return "En attente";
				if (o.toString().charAt(0)=='t') return "Valide";
				if (o.toString().charAt(0)=='f') return "NON Valide";
				else return "erreur";
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return "erreur";
			}

		}

	}
	public static int idPaletteDeIdColis(int idColis){
		if (nonSQL)return idColis*3;
		else{

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select ID_PAL from COLIS where ID_COL="+idColis+";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static String nomVehiculeDeIdColis(int idColis){
		if (nonSQL)return "conteneur";
		else{

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select TYPE_VEH.NOM from TYPE_VEH where TYPE_VEH.ID_TYPE_VEH=(select VEHICULE.ID_TYPE_VEH from VEHICULE where VEHICULE.ID_VEH=(select PALETTE.ID_VEH from PALETTE where PALETTE.ID_PAL=(select COLIS.ID_PAL from COLIS where COLIS.ID_COL="+idColis+")));";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return null;
			}

		}
	}
	public static int idVehiculeDeIdColis(int idColis){
		if (nonSQL) return idColis*5;
		else{

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select PALETTE.ID_VEH from PALETTE where PALETTE.ID_PAL=(select COLIS.ID_PAL from COLIS where COLIS.ID_COL="+idColis+");";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static boolean aUnVerdict(int idColis){
		if (nonSQL) {
			if (idColis%2==0) return false;
			else return true;
		}
		else {
			try{
				String s = verdict(idColis);
				if ((s.charAt(0)=='V')||(s.charAt(0)=='N')) return true;
				else return false;
			}
			catch(Exception e){
				System.out.println(e);
				return false;
			}
		}
	}
	public static String motifDeIdColis(int idColis){
		if (nonSQL) return "Je ne sais pas trop pourquoi\n mais j'aime la france.";
		else {
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "select MOTIF from COLIS where ID_COL="+idColis+";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	}








	public static Object[][] detailColis1(int idColis){


		if (nonSQL) return new Object [][] {
				{"Brosse à dent",  new Float(1.5),  new Integer(200)},
				{"Fil dentaire",  new Float(3.2),  new Integer(400)},
				{null, null, null},
				{null, null, null}
		};
		else {


			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			catch (Exception e){
				System.out.println(e);
				return null;
			}



			// A completer pr avoir le nb de lignes !
			String requete = "select count(*) from PRODUIT, DETAIL_COL, COLIS, PALETTE, VEHICULE, TYPE_VEH where PRODUIT.ID_COL="+idColis+" and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH) and (COLIS.ID_COL="+idColis+") and (DETAIL_COL.ID_COL= COLIS.ID_COL);";
			ResultSet res;
			int nbLigne=0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1); 
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return null;
			}


			// A completer pr avoir le tableau
			requete = "select PRODUIT.NOM, PRODUIT.PRIX, DETAIL_COL.QUANTITE from PRODUIT, DETAIL_COL, COLIS, PALETTE, VEHICULE, TYPE_VEH where PRODUIT.ID_COL="+idColis+" and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH) and (COLIS.ID_COL="+idColis+") and (DETAIL_COL.ID_COL= COLIS.ID_COL);";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return null;
			}

			String s;
			Object[][] tab = new Object[nbLigne][3];
			for (int i=0; i<nbLigne;i++){
				try{
					res.next();
					tab[i][0] = res.getString(1);
					tab[i][1] = new Float(res.getFloat(2));
					tab[i][2] = new Integer(res.getInt(3));
				}
				catch(Exception e){
					System.out.println(e);
					return null;
				}
			}

			return tab;
		}
	}


	public static void modifColisDouane(int idColis,char c, String s){
		if (c=='N') {
			colisNONValide(idColis);
			modifVerdictColis(idColis,s);
		}
		if (c=='V') {
			colisValide(idColis);
			modifVerdictColis(idColis,"");
		}

	}
	public static void colisValide(int idColis){
		if (nonSQL) {}
		else {
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "update COLIS set VERDICT=true where COLIS.ID_COL="+idColis+";";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
		}
	}
	public static void colisNONValide(int idColis){
		if (nonSQL) {}
		else {
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "update COLIS set VERDICT=false where COLIS.ID_COL="+idColis+";";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){

			}
		}
	}
	public static void modifVerdictColis(int idColis,String s){
		if (nonSQL) {}
		else {
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String requete = "update COLIS set MOTIF='"+s+"' where COLIS.ID_COL="+idColis+";";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
		}
	}
	public static boolean getOk(){return ok;}

	
	public static String[] typeCompteEtId (int typeCompte){
		String[] tab = new String[2];
		
		switch (typeCompte){
		case 1: tab[0]="DOUANE"; tab[1]="ID_DOU"; break;
		case 2: tab[0]="EMBALLEUR"; tab[1]="ID_EMB"; break;
		case 3: tab[0]="TRANSPORTEUR"; tab[1]="ID_TRA";break;
		case 4: tab[0]="CLIENT"; tab[1]="ID_CLI";break;
		case 5: tab[0]="GERANT"; tab[1]="ID_GER";break;
		default: ;
		}
		return tab;
	}

	public static String nom(){
		if (nonSQL) return "Ligier";
		else {
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String s1 = typeCompteEtId(typeConnexion)[0];
				String s2 = typeCompteEtId(typeConnexion)[1];
				String requete = "select nom from "+ s1 +" where "+ s2 +"="+identifiant +";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	}

	public static String prenom(){
		if (nonSQL) return "Damien";
		else { 
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String s1 = typeCompteEtId(typeConnexion)[0];
				String s2 = typeCompteEtId(typeConnexion)[1];
				String requete = "select prenom from "+ s1 +" where "+ s2 +"="+identifiant +";";
				ResultSet res;


				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			catch(Exception e){
				// Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	} 
	public static int getIdentifiant(){
		return identifiant;
	}
	//le colis a-t-il un transporteur
	public static boolean coliEstExpedie(int idColis){
		if (nonSQL) return false;
		else {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select TRANSPORT from COLIS where ID_COL="+ idColis +";";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			if (res.getObject(1) != null){
				return true;
			}
			else return false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		return false;
	}
	public static boolean paletteEstExpedie(int idPalette){
		if (nonSQL) return false;
		else {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(TRANSPORT) from COLIS where ID_PAL="+ idPalette +";";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			if (res.getInt(1) >0){
				//System.out.print("["+res.getInt(1)+"]\n");
				return true;
			}
			else return false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		return false; 
	}
	public static boolean conteneurEstExpedie(int idConteneur){
		if (nonSQL) return false;
		else {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(TRANSPORT) from COLIS where COLIS.ID_PAL=(select ID_PAL from PALETTE where ID_CON="+ idConteneur +");";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			if (res.getInt(1) >0){
				//System.out.print("["+res.getInt(1)+"]\n");
				return true;
			}
			else return false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		return false;
	}


//remplir la liste des colis a transporter
//numcolis, type, pays, code_pays, date souhaitee
public static Object[][] transporteur1(){


if (nonSQL) return new Object [][] {
{ new Integer(981),  new Integer(231),"avion",  new Integer(187), "01/01/2012", "en attente"},
{ new Integer(123),  new Integer(736),"pute",  new Integer(442), null, null},
{ new Integer(456),  new Integer(129),"camion",  new Integer(187), null, null},
{ new Integer(163),  new Integer(455),null,  new Integer(980), null, null},
{ new Integer(254),  new Integer(111),null,  new Integer(709), null, null}

};
else {
//System.out.println("dansRequetes.idDeMailType("france@douane.fr",1) le ELSE");

try {
Class.forName("org.postgresql.Driver");
conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
stmt = conn.createStatement();
}
catch (Exception e){
//System.out.println("erreur ici");
return null;
}
//System.out.println(idPays);

// A completer pr avoir le nb de lignes !
String requete = "select count (COLIS.ID_COL) from COLIS join PRODUIT on( COLIS.ID_COL=PRODUIT.ID_COL) join COMMANDE on (COLIS.ID_COM=COMMANDE.ID_COM) join PAYS on (PAYS.ID_PAY=(select CLIENT.ID_PAY from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) join CODE_P on (CODE_P.ID_CP=(select CLIENT.ID_CP from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) where COLIS.ID_PAL is null and COMMANDE.ID_TRA is null;";
ResultSet res;
int nbLigne=0;

try {
res = stmt.executeQuery(requete);
res.next();
nbLigne = res.getInt(1);
//System.out.println(nbLigne);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici2");
return null;
}


// A completer pr avoir le tableau
requete = "select COLIS.ID_COL, PRODUIT.PTYPE, PAYS.NOM,CODE_P.CP, COMMANDE.LIVRAISON from COLIS join PRODUIT on( COLIS.ID_COL=PRODUIT.ID_COL) join COMMANDE on (COLIS.ID_COM=COMMANDE.ID_COM) join PAYS on (PAYS.ID_PAY=(select CLIENT.ID_PAY from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) join CODE_P on (CODE_P.ID_CP=(select CLIENT.ID_CP from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) where COLIS.ID_PAL is null and COMMANDE.ID_TRA is null;";

try {
res = stmt.executeQuery(requete);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici3");
return null;

}
Object[][] tab = new Object[nbLigne][5];
try{
for (int i=0; i<nbLigne;i++){
res.next();

tab[i][0] = new Integer(res.getInt(1));
tab[i][1] = res.getString(2);
tab[i][2] = res.getString(3);

tab[i][3] = new Integer(res.getInt(4));
tab[i][4] = res.getString(5);

}
}
catch(Exception e){
System.out.println(e);
}
//System.out.println("->"+tab[0][5]);
return tab;
}

}














//remplir la liste des colis a transporter
//numcolis, type, pays, code_pays, date souhaitee
public static Object[][] produit(){


if (nonSQL) return null;
else {
//System.out.println("dansRequetes.idDeMailType("france@douane.fr",1) le ELSE");

try {
Class.forName("org.postgresql.Driver");
conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
stmt = conn.createStatement();
}
catch (Exception e){
//System.out.println("erreur ici");
return null;
}
//System.out.println(idPays);

// A completer pr avoir le nb de lignes !
String requete = "select count (*) from PRODUIT;";
ResultSet res;
int nbLigne=0;

try {
res = stmt.executeQuery(requete);
res.next();
nbLigne = res.getInt(1);
//System.out.println(nbLigne);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici2");
return null;
}


// A completer pr avoir le tableau
requete = "select nom,prix,ptype from produit;";

try {
res = stmt.executeQuery(requete);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici3");
return null;

}
Object[][] tab = new Object[nbLigne][5];
try{
for (int i=0; i<nbLigne;i++){
res.next();

tab[i][0] = res.getString(1);
tab[i][1] = new Double(res.getInt(2));
tab[i][2] = new Integer(0);
}
}
catch(Exception e){
System.out.println(e);
}
//System.out.println("->"+tab[0][5]);
return tab;
}

}



//remplir la liste des colis a transporter
//numcolis, type, pays, code_pays, date souhaitee
public static Object[][] commandeEnCoursDeIdClient(){


if (nonSQL) return null;
else {
//System.out.println("dansRequetes.idDeMailType("france@douane.fr",1) le ELSE");

try {
Class.forName("org.postgresql.Driver");
conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
stmt = conn.createStatement();
}
catch (Exception e){
//System.out.println("erreur ici");
return null;
}
//System.out.println(idPays);

// A completer pr avoir le nb de lignes !
String requete = "select count (id_com) from commande where id_cli ="+identifiant+";";
ResultSet res;
int nbLigne=0;

try {
res = stmt.executeQuery(requete);
res.next();
nbLigne = res.getInt(1);
//System.out.println(nbLigne);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici2");
return null;
}


// A completer pr avoir le tableau
requete = "select id_com,livraison from commande where delivree = false and id_cli ="+identifiant+";";

try {
res = stmt.executeQuery(requete);
}
catch(Exception e){
// Erreur
//System.out.println("erreur ici3");
return null;

}
Object[][] tab = new Object[nbLigne][5];
try{
for (int i=0; i<nbLigne;i++){
res.next();

tab[i][0] = new Integer(res.getInt(1));
tab[i][1] = res.getString(2);
}
}
catch(Exception e){
System.out.println(e);
}
//System.out.println("->"+tab[0][5]);
return tab;
}

}





}



