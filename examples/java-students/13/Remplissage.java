import java.sql.*;

public class Remplissage{


	private static Connection conn = null;
	private static Statement stmt = null;

	private static String loginBD = "farah";//"marielle";
	private static String mdpBD = "swan";//"azerty";



    static String[] vo = {"a","e","i","o","u","y","ou","oi","au","a","e","e","i","o","u","e"};
    static String[] co ={"z","r","b","t","n","m","l","r","tr","t","p","pr","pt","s","ss","ps","d","dr","f","ff","g","j","k","ll","l","m","w","x","c","cl","cr","v","b","bl","br","n","ch","kh"};


	public static void remplis(){
		ajoutAleatoirePays();
		ajoutAleatoireCP();
		ajoutAleatoireClient();
		ajoutAleatoireDouane();
		ajoutAleatoireProduit();
	}

    public static String nomAleatoire(){
        int nbLetres = (int) (Math.random() *(4 - 2)) + 2;
        String s = "";
        if ((int) (Math.random() *(2 - 0))==0) s += co[(int) (Math.random() *(vo.length - 0)) + 0];
        for (int i = 0; i<nbLetres;i ++){
            s += vo[(int) (Math.random() *(vo.length - 0)) + 0];
            s += co[(int) (Math.random() *(vo.length - 0)) + 0];
        }
        return s;
    }
    public static int alea(int m, int M){
        return ((int) (Math.random() *(M - m)) + m);
    }

	public static int nbPays(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from pays;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
	public static int nbProduit(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from produit;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
	public static int nbDouane(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from douane;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}

	public static int nbCP(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from code_p;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
	public static int nbTransporteur(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from transporteur;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
	public static int nbEmballeur(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from emballeur;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
	public static int nbClient(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String requete = "select count(*) from client;";
			ResultSet res;
			res = stmt.executeQuery(requete);
			res.next();
			return res.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;


		}
	}
    public static void ajoutAleatoirePays(){
		int souhait = 50;
		String s = "";
		while (nbPays()<souhait){


			for (int i=0; i<souhait-nbPays();i++){
				s = nomAleatoire();
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String requete = "insert into PAYS(NOM) values ('"+s+"-PA');";
				stmt.executeUpdate(requete);

			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}

    public static void ajoutAleatoireCP(){
		int souhait = 100;
		while (nbCP()<souhait){


			for (int i=0; i<souhait-nbCP();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String requete = "insert into CODE_P(CP) values ('"+alea(10000,99999)+"'); ";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}

    public static void ajoutAleatoireClient(){
		int nbPays = nbPays(); 
		int nbCP = nbCP();
		int souhait = 250;
		while (nbClient()<souhait){


			for (int i=0; i<souhait-nbClient();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String pc = nomAleatoire();
				String nc = nomAleatoire();

				String requete = "insert into CLIENT(MAIL,NOM,PRENOM,ID_PAY,ADRESSE,ID_CP,MDP)values ('"+pc+"."+nc+"@gmail.com','"+nc+"-NC','"+pc+"-PC',"+alea(1,nbPays)+",'"+alea(1,500)+" rue "+nomAleatoire()+"',"+alea(1,nbCP)+",'11111111');";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}

    public static void ajoutAleatoireDouane(){
		int nbPays = nbPays(); 
		int i;
		while (nbPays()>nbDouane()){
			if (alea(0,1)==0) i = alea(1,nbPays);
			else i=1;/*
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String requete = "update douane_id_dou_seq set last_value ="+i+" ;";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}*/
			for (int k=i; k<=nbPays;k++){
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String requete = "insert into DOUANE(ID_DOU,MAIL,ID_PAY,MDP) values ("+i+",'douane"+i+"@douane.fr',"+i+",'11111111');";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
	}
}

    public static void ajoutAleatoireEmballeur(){
		int souhait = 80;
		while (nbEmballeur()<souhait){


			for (int i=0; i<souhait-nbEmballeur();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String pe = nomAleatoire();
				String ne = nomAleatoire();

				String requete = "insert into EMBALLEUR(MAIL,NOM,PRENOM,MDP) values ('"+pe+"."+ne+"@gmail.com','"+ne+"-NE','"+pe+"-PE','11111111');";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}
    public static void ajoutAleatoireTransporteur(){
		int souhait = 80;
		while (nbTransporteur()<souhait){


			for (int i=0; i<souhait-nbTransporteur();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String pe = nomAleatoire();
				String ne = nomAleatoire();

				String requete = "insert into TRANSPORTEUR (MAIL,NOM,PRENOM,MDP) values ('"+pe+"."+ne+"@gmail.com','"+ne+"-NE','"+pe+"-PE','11111111');";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}
    public static void ajoutAleatoireCommande1(){ //50 //20 //180
		int souhait = 20;
		int nbClient = nbClient();
		while (nbTransporteur()<souhait){


			for (int i=0; i<souhait-nbTransporteur();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String requete = "insert into COMMANDE (ID_CLI,ID_EMB,ID_DOU,LIVRAISON,DELIVREE) values ("+alea(1,nbClient)+",null,null,current_date+"+alea(10,50)+",false);";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}




    public static void ajoutAleatoireProduit(){
		int souhait = 200;
		int nbProduit = nbProduit();
		int n =0;
		String s = "";
		while (nbProduit()<souhait){


			for (int i=0; i<souhait-nbProduit();i++){

			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				n = alea(0,4);
				if (n<2) s = "N";
				else if (n==3) s = "D";
				else s = "F";

				String requete = "insert into PRODUIT(NOM,PRIX,PTYPE) values ('"+nomAleatoire()+"-PR',"+alea(10,10000)+".00,'"+s+"');";


				stmt.executeUpdate(requete);
			}
			catch(Exception e){
				System.out.println(e);

			}
			}
		}
	}


}