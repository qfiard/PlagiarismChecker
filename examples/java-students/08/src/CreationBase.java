import java.io.*;
import java.sql.*;

public class CreationBase {
    Connection conn; // la connexion a la base
    Statement st;
    PreparedStatement insert;
    PreparedStatement delete;
    PreparedStatement update;
    
    // connection a la base
    public CreationBase(String login, String motPasse) throws SQLException, ClassNotFoundException{
	// -------------------
	// Connexion a la base
	// --------------------
	
    	Class.forName("org.postgresql.Driver");
    	conn = DriverManager.getConnection("jdbc:postgresql://nivose/" + login,login, motPasse);
    }

    // fermeture de la connection
    public void close() throws SQLException{ 
    	conn.close();
    }
    
    
   /**********************************************             QUELQUES FONCTIONS UTILES                *************************************************************************************/
    
    // Creer une variable ResultSet qui contient le contenu d'une table
    public ResultSet contenuTable(String table) throws SQLException{
        ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from "+table);

        }catch(SQLException e){
		e.printStackTrace();
        }
        return rs;
    }
    public int produit_existe(String table,String numeroduproduit) throws SQLException{
        ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from "+table+" where numeroduproduit='"+numeroduproduit+"'");

        }catch(SQLException e){
		e.printStackTrace();
        }
        return NombreLigne(rs);
    }
    
    public ResultSet getLoginPassword(String nom_tab,String login,String password) throws SQLException{
    	ResultSet  s = null;
    	try{
    		
    		if(nom_tab.equals("client")){
    			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        		String res="select numeroduclient,motdepasse from "+nom_tab+" where numeroduclient="+"'"+login+"'"+" and motdepasse="+"'"+password+"'";
        		s = st.executeQuery(res);
    		}
    		else if(nom_tab.equals("emballeur")){
    			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        		String res="select numerodemballeur,motdepasse from "+nom_tab+" where numerodemballeur="+"'"+login+"'"+" and motdepasse="+"'"+password+"'";
        		s = st.executeQuery(res);
    		}
    		else if(nom_tab.equals("transporteurs")){
    			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        		String res="select codescac,motdepasse from "+nom_tab+" where codescac="+"'"+login+"'"+" and motdepasse="+"'"+password+"'";
        		s = st.executeQuery(res);
    		}
    		else{
    			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        		String res="select login,motdepasse from "+nom_tab+" where login="+"'"+login+"'"+" and motdepasse="+"'"+password+"'";
        		s = st.executeQuery(res);
    		}
    		
    
    	}catch(SQLException e){
    	}
    	return s;
    }
    // Renvoie le nombre de ligne d'une table
    public int NombreLigne(ResultSet rs) throws SQLException{
    	int rowCount = 0;
    	try {
			while (rs.next()) {
			    String id = rs.getString(1);
			    rowCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return rowCount;
    }
    
    public static String ajoute_espace(String s,int i){
    	String res="";
    	for (i -= s.length(); i >= 0; i --)
        	   res=res+" ";	
    	return res;
    }
    
    public String table_gerant() throws SQLException{
    	ResultSet contenu = contenuTable("gerant");
    	String type="";
    	String res="";
    	type="PRENOM"+ajoute_espace("PRENOM",25)+"NOM"+ajoute_espace("NOM",25)+"LOGIN"+ajoute_espace("LOGIN",25)+"MOT DE PASSE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("prenom")+ajoute_espace(contenu.getString("prenom"),25);
    		 res=res+contenu.getString("nom")+ajoute_espace(contenu.getString("nom"),25);
    		 res=res+contenu.getString("login")+ajoute_espace(contenu.getString("login"),25);
    		 res=res+contenu.getString("motdepasse")+ajoute_espace(contenu.getString("motdepasse"),25);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    public String table_douane() throws SQLException{
    	ResultSet contenu = contenuTable("douane");
    	String type="";
    	String res="";
    	type="PAYS"+ajoute_espace("PAYS",25)+"TAUX DE COLIS VERIFIE"+ajoute_espace("TAUX DE COLIS VERIFIE",25)+"LOGIN"+ajoute_espace("LOGIN",25)+"MOT DE PASSE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("pays")+ajoute_espace(contenu.getString("pays"),25);
    		 res=res+contenu.getString("tauxdecolisverifie")+ajoute_espace(contenu.getString("tauxdecolisverifie"),25);
    		 res=res+contenu.getString("login")+ajoute_espace(contenu.getString("login"),25);
    		 res=res+contenu.getString("motdepasse")+ajoute_espace(contenu.getString("motdepasse"),25);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    public String table_client() throws SQLException{
    	ResultSet contenu = contenuTable("client");
    	String type="";
    	String res="";
    	type="NUMERO DU CLIENT"+ajoute_espace("NUMERO DU CLIENT",35)
    	+"NOM DE LA SOCIETE"+ajoute_espace("NOM DE LA SOCIETE",35)
    	+"SUFFIX DE LA SOCIETE"+ajoute_espace("SUFFIX DE LA SOCIETE",35)+
    	"ADRESSE"+ajoute_espace("ADRESSE",35)
    	+"VILLE"+ajoute_espace("VILLE",35)
    	+"CODE POSTALE"+ajoute_espace("CODE POSTALE",35)
    	+"PAYS"+ajoute_espace("PAYS",35)+
    	"TELEPHONE"+ajoute_espace("TELEPHONE",35)
    	+"MOT DE PASSE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numeroduclient")+ajoute_espace(contenu.getString("numeroduclient"),35);
    		 res=res+contenu.getString("nomdelasociete")+ajoute_espace(contenu.getString("nomdelasociete"),35);
    		 res=res+contenu.getString("suffixedelasociete")+ajoute_espace(contenu.getString("suffixedelasociete"),35);
    		 res=res+contenu.getString("adresse")+ajoute_espace(contenu.getString("adresse"),35);
    		 res=res+contenu.getString("ville")+ajoute_espace(contenu.getString("ville"),35);
    		 res=res+contenu.getString("codepostale")+ajoute_espace(contenu.getString("codepostale"),35);
    		 res=res+contenu.getString("pays")+ajoute_espace(contenu.getString("pays"),35);
    		 res=res+contenu.getString("telephone")+ajoute_espace(contenu.getString("telephone"),35);
    		 res=res+contenu.getString("motdepasse")+ajoute_espace(contenu.getString("motdepasse"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    public String table_emballeur() throws SQLException{
    	ResultSet contenu = contenuTable("emballeur");
    	String type="";
    	String res="";
    	type="NUMERO EMBALLEUR"+ajoute_espace("NUMERO EMBALLEUR",35)
    	+"NOM"+ajoute_espace("NOM",35)
    	+"PRENOM"+ajoute_espace("PRENOM",35)
    	+"TAUX ERREUR"+ajoute_espace("TAUX ERREUR",35)
    	+"MOT DE PASSE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numerodemballeur")+ajoute_espace(contenu.getString("numerodemballeur"),35);
    		 res=res+contenu.getString("nom")+ajoute_espace(contenu.getString("nom"),35);
    		 res=res+contenu.getString("prenom")+ajoute_espace(contenu.getString("prenom"),35);
    		 res=res+contenu.getString("tauxderreur")+ajoute_espace(contenu.getString("tauxderreur"),35);
    		 res=res+contenu.getString("motdepasse")+ajoute_espace(contenu.getString("motdepasse"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    public String table_transporteur() throws SQLException{
    	ResultSet contenu = contenuTable("transporteurs");
    	String type="";
    	String res="";
    	type="CODE"+ajoute_espace("CODE",35)
    	+"NOM DE TRANSPORTEUR"+ajoute_espace("NOM DE TRANSPORTEUR",35)
    	+"MOT DE PASSE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("codescac")+ajoute_espace(contenu.getString("codescac"),35);
    		 res=res+contenu.getString("nomdetransporteur")+ajoute_espace(contenu.getString("nomdetransporteur"),35);
    		 res=res+contenu.getString("motdepasse")+ajoute_espace(contenu.getString("motdepasse"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    public String table_produit() throws SQLException{
    	ResultSet contenu = contenuTable("produit");
    	String type="";
    	String res="";
    	int cp=0;
    	type="NUMERO DU PRODUIT"+ajoute_espace("NUMERO DU PRODUIT",85)
    	+"DESCRIPTION DU PRODUIT"+ajoute_espace("DESCRIPTION DU PRODUIT",85)
    	+"QUANTITE PAR CARTON"+ajoute_espace("QUANTITE PAR CARTON",85)+
    	"CARTON PAR PALETTE"+ajoute_espace("CARTON PAR PALETTE",85)
    	+"QUALIFIANT"+ajoute_espace("QUALIFIANT",85)
    	+"COUT"+ajoute_espace("COUT",85)
    	+"TAUX AUGMENTATION DES PRIX"+ajoute_espace("TAUX AUGMENTATION DES PRIX",85)+
    	"POIDS"+ajoute_espace("POIDS",85)
    	+"RESERVE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),85);
    		 res=res+contenu.getString("descriptionduproduit")+ajoute_espace(contenu.getString("descriptionduproduit"),85);
    		 res=res+contenu.getString("quantiteparcarton")+ajoute_espace(contenu.getString("quantiteparcarton"),85);
    		 res=res+contenu.getString("cartonsparpalette")+ajoute_espace(contenu.getString("cartonsparpalette"),85);
    		 res=res+contenu.getString("qualifiant")+ajoute_espace(contenu.getString("qualifiant"),85);
    		 res=res+contenu.getString("cout")+ajoute_espace(contenu.getString("cout"),85);
    		 res=res+contenu.getString("tauxdaugmentationdesprix")+ajoute_espace(contenu.getString("tauxdaugmentationdesprix"),85);
    		 res=res+contenu.getString("poids")+ajoute_espace(contenu.getString("poids"),85);
    		 res=res+contenu.getString("reserve")+ajoute_espace(contenu.getString("reserve"),85);
    		 res=res+"\n";
    		 cp++;
    		 System.out.println(cp);
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    /************************************************           CREATION ET REMPLISSAGE TOUS LES TABLES DE DONNEES          ***************************************************************/
 // creation de la table emballeur
    public  void creationTableEmballeur() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Emballeur(NumeroDemballeur varchar not null,nom varchar not null,prenom varchar not null,tauxDerreur numeric,motdepasse varchar not null, primary key(NumeroDemballeur))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table emballeur");
			e.printStackTrace();
		}
    }	 

    // creation de la table client
    public  void creationTableClient() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Client (NumeroduClient varchar not null, NomdeLaSociete varchar not null, SuffixedelaSociete varchar not null, adresse varchar not null,Ville varchar not null, Codepostale varchar not null, Pays varchar not null, Telephone varchar not null, motdepasse varchar not null, primary key(NumeroduClient))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table client");
			e.printStackTrace();
		}
    }	 

    // creation de la table produit
    public  void creationTableProduit() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Produit(NumeroduProduit varchar not null, DescriptionduProduit varchar not null, QuantiteparCarton numeric, CartonsparPalette numeric, Qualifiant varchar not null, Cout numeric, TauxdaugmentationdesPrix numeric, Poids numeric, Reserve numeric ,primary key(NumeroduProduit),check(Qualifiant in ('N','D','F')))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table produit");
			e.printStackTrace();
		}
    }	 

    // creation de la table transporteurs
    public  void creationTableTransporteurs() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Transporteurs (codeSCAC varchar not null unique, nomdetransporteur varchar not null, Motdepasse varchar not null, primary key(nomdetransporteur))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table transporteurs");
			e.printStackTrace();
		}
    }	
    // creation de la table douane 
    public  void creationTableDouane() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Douane(Pays varchar not null, TauxdecolisVerifie numeric , Login varchar not null, Motdepasse varchar not null, primary key(login))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table douane");
			e.printStackTrace();
		}
    }	 
    
    // creation de la table gerant
    public  void creationTableGerant() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Gerant(prenom varchar not null, nom varchar not null, login varchar not null, motdepasse varchar not null)";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table gerant");
			e.printStackTrace();
		}
    }
    
    // creation de la table commande 
    public  void creationTableCommande() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Commande(NumeroDuClient varchar not null references Client(NumeroDuClient),NumerodeCommande int not null,datedeLivraison date,destination varchar not null,primary key(NumerodeCommande))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table commande");
			e.printStackTrace();
		}
    
    }

    // creation de la table transport
    public  void creationTableTransport() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Transport(typedetransport varchar not null,nomdetransporteur varchar not null references transporteurs(nomdetransporteur) ,numerodelapalette varchar not null references palette(numerodelapalette), check(typedetransport in ('conteneur','camion','avion')))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table transport");
			e.printStackTrace();
		}
    }

    // creation de la table emballage
    public  void creationTableEmballage() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table Emballage(typedEmballage varchar not null, NumeroDemballeur varchar not null references Emballeur(NumeroDemballeur),numerodecommande int not null, numeroduproduit varchar not null,foreign key(numerodecommande,numeroduproduit) references quantitedeproduit(numerodecommande,numeroduproduit))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la emballage ");
			e.printStackTrace();
		}
    }

    // creation de la table controledesdouanes
    public  void creationTableControleDesDouanes() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table ControleDesDouanes(numeroducontrole int not null primary key,resultat varchar not null, dateducontrole date not null, logindeladouane varchar references Douane(login))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la controledesdouanes ");
			e.printStackTrace();
		}
    }

    // creation de la table colis
    public  void creationTableColis() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table colis(numerodecolis varchar not null, quantitedeproduit numeric not null,datedemballage date, suivi varchar,numerodelapalette varchar not null references palette(numerodelapalette),check(suivi in ('emballe','100 expedie','en cours de transport')), primary key(numerodecolis,numerodecommande,numeroduproduit),numeroduproduit varchar not null ,numerodecommande int not null ,foreign key(numerodecommande,numeroduproduit) references quantitedeproduit(numerodecommande,numeroduproduit),numeroducontrole int references controledesdouanes(numeroducontrole))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table colis");
			e.printStackTrace();
		}
    }

    // creation de la table palette
    public  void creationTablePalette() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table palette(numerodelapalette varchar not null primary key)";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table palette");
			e.printStackTrace();
		}
    }

    // creation de la table quantitedeproduit
    public  void creationTableQuantitedeProduit() throws SQLException {
		try{
			st = conn.createStatement();
			String create = "create table quantitedeproduit(numerodecommande int not null references commande(numerodecommande), numeroduproduit varchar not null references produit(numeroduproduit),quantite numeric,primary key(numerodecommande,numeroduproduit))";
			st.executeUpdate(create);	
		}catch(SQLException e){
			System.out.println("Erreur dans la creation de la table quantitedeproduit");
			e.printStackTrace();
		}
    }
	

    // creation du systeme de gestion
    public void creationTable() throws SQLException{
		creationTableEmballeur();
		creationTableClient();
		creationTableTransporteurs();
		creationTableDouane();
		creationTableGerant();
		creationTableCommande();
		creationTablePalette();
		creationTableTransport();
		creationTableProduit();
		creationTableQuantitedeProduit();
		creationTableControleDesDouanes();
		creationTableColis();
		creationTableEmballage();
    }

    // suppression du systeme
    public void suppressionTable() throws SQLException {
		try{
			st = conn.createStatement();
			st.executeUpdate("drop table emballeur cascade");
			st.executeUpdate("drop table client cascade");
			st.executeUpdate("drop table produit cascade");
			st.executeUpdate("drop table transporteurs cascade");
			st.executeUpdate("drop table douane cascade");
			st.executeUpdate("drop table gerant cascade");
			st.executeUpdate("drop table transport cascade");
			st.executeUpdate("drop table emballage cascade");
			st.executeUpdate("drop table controledesdouanes cascade");
			st.executeUpdate("drop table commande cascade");
			st.executeUpdate("drop table colis cascade");
			st.executeUpdate("drop table palette cascade");
			st.executeUpdate("drop table quantitedeproduit cascade");
	
		}catch(SQLException e){
		}
    }
      
    
    
    // Creer une variable ResultSet qui contient le contenu d'une commande de la table voulu
    public ResultSet contenuCommande(int numerodecommande,String table) throws SQLException{
        ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from "+table+" where numerodecommande = "+numerodecommande+"");

        }catch(SQLException e){
        		e.printStackTrace();
        }
        return rs;
    }
    
    // Creer une variable ResultSet qui contient le contenu d'un produit
    public ResultSet contenuTableproduit(String numeroduproduit){
    	ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from produit where numeroduproduit = '"+numeroduproduit+"'");

        }catch(SQLException e){
        		e.printStackTrace();
        }
        return rs;
    }

    public ResultSet contenuTableDouane(String pays){
    	ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from douane where pays = '"+pays+"'");

        }catch(SQLException e){
        		e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet contenuTableEmballage(int numerodecommande){
    	ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from emballage where numerodecommande = "+numerodecommande+"");

        }catch(SQLException e){
        		e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet contenuTableEmballeur(String numeroemballeur){
    	ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from emballeur where numerodemballeur = '"+numeroemballeur+"'");

        }catch(SQLException e){
        		e.printStackTrace();
        }
        return rs;
    }
    
    // Renvoie le nombre de produit par carton
    public int QtProd(String numeroduproduit){
    	ResultSet rs = null;
    	int qtprod = 0;
    	try{
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String ss = "select * from produit where numeroduproduit = '"+numeroduproduit+"'";
            rs = st.executeQuery(ss);
            rs.next();
            qtprod = rs.getInt("quantiteparcarton");
            rs.close();
        }catch(SQLException e){
        	e.printStackTrace();
    	}
    	return qtprod;
    }
    
 // Renvoie le nombre de carton par palette
    public int QtCart(String numeroduproduit){
    	ResultSet rs = null;
    	int qtprod = 0;
    	try{
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String ss = "select * from produit where numeroduproduit = '"+numeroduproduit+"'";
            rs = st.executeQuery(ss);
            rs.next();
            qtprod = rs.getInt("cartonsparpalette");
            rs.close();
        }catch(SQLException e){
        	e.printStackTrace();
    	}
    	return qtprod;
    	
    }
    
    // Insert une ligne dans la table commande
    public void InsertCommande(String numeroduclient,int id_commande,String date,String pays) throws SQLException{
    	try{    
            st = conn.createStatement();
		    String ss = "insert into commande values ('"+numeroduclient+"',"+
			String.valueOf(id_commande)+",'"+date+"','"+pays+"')";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertCommande");
    		e.printStackTrace();
        }
    }

    // Insert une ligne dans la table quantitedeproduit
    public void InsertQuantite(String numerodecommande,String numeroduproduit,int quantite) throws SQLException{
    	try{    
            st = conn.createStatement();
		    String ss = "insert into quantitedeproduit values ('"+numerodecommande+"','"+numeroduproduit+"',"+String.valueOf(quantite)+")";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertQuantite");
    		e.printStackTrace();
        }
    }
    
    public void InsertColis(String numerodecolis,int quantitedeproduit,String datedemballage,String suivi,String numerodelapalette,String numeroduproduit,int numerodecommande,String numerodecontrole){
    	try{    
            st = conn.createStatement();
		    String ss = "insert into colis values('"+numerodecolis+"',"+String.valueOf(quantitedeproduit)+",'"+datedemballage+"','"+suivi+"','"+numerodelapalette+"','"+numeroduproduit+"',"+numerodecommande+","+numerodecontrole+")";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertColis");
    		e.printStackTrace();
        }
    }
    
    public void InsertPalette(String numerodepalette) throws SQLException{
    	try{    
            st = conn.createStatement();
		    String ss = "insert into palette values ('"+numerodepalette+"')";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertPalette");
    		e.printStackTrace();
        }
    }
    
    public void UpdateColisPalette(String numerodelapalette, String numerodecolis){
    	try{    
            st = conn.createStatement();
		    String ss = "update colis set numerodelapalette = '"+numerodelapalette+"' where numerodecolis = '"+numerodecolis+"'";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur UpdateColisPalette");
    		e.printStackTrace();
        }
    }
    
    public void InsertEmballage(String typedemballage, String numerodemballeur, int numerodecommande, String numeroduproduit) throws SQLException{
    	try{    
            st = conn.createStatement();
		    String ss = "insert into emballage values ('"+typedemballage+"','"+numerodemballeur+"',"+numerodecommande+",'"+numeroduproduit+"')";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertEmballeur");
    		e.printStackTrace();
        }
    }
    
    public void InsertTransport(String typedetransport, String nomdetransporteur, String numerodelapalette) throws SQLException{
    	try{    
            st = conn.createStatement();
		    String ss = "insert into transport values ('"+typedetransport+"','"+nomdetransporteur+"','"+numerodelapalette+"')";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertTransport");
    		e.printStackTrace();
        }
    }
    
    public void InsertControleDouane(int numeroducontrole, String resultat, String date,String logindeladouane){
    	try{    
            st = conn.createStatement();
		    String ss = "insert into controledesdouanes values ("+numeroducontrole+",'"+resultat+"','"+date+"','"+logindeladouane+"')";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur InsertControleDouane");
    		e.printStackTrace();
        }
    }
    
    public void UpdateColisControle(int numeroducontrole, String numerodecolis){
    	try{    
            st = conn.createStatement();
		    String ss = "update colis set numeroducontrole = "+numeroducontrole+" where numerodecolis = '"+numerodecolis+"'";
		    System.out.println(ss);
		    st.executeUpdate(ss);
    	}catch(SQLException e){
    		System.out.println("Erreur UpdateColisControle");
    		e.printStackTrace();
        }
    }
    

    
    /*****************************************************    LES REQUETES AVEC INTERFACE GRAPHIQUE    ********************************************************************************************/

    //-----------------------------------------------------------------------------------REQUETE DOUANE---------------------------------------------------------------------------------------------
    public String requete1(String login) throws SQLException {
    	 ResultSet rsdouane = null;
    	 ResultSet rscommande = null;
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rsdouane = st.executeQuery("select * from douane where login = '"+login+"'");
                 rsdouane.first();
                 String pays = rsdouane.getString("pays");
                 rscommande = st.executeQuery("select * from commande where destination = '"+pays+"'");
                 
         }catch(SQLException e){
 		e.printStackTrace();
         }
        
        ResultSet contenu=rscommande;
        String type="";
     	String res="";
     	type="NUMERO DU CLIENT"+ajoute_espace("NUMERO DU CLIENT",35)
     	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
     	+"DATE LIVRAISON"+ajoute_espace("DATE LIVRAISON",35)+
     	"\n";
     	
     	 while (contenu != null && contenu.next()) {
     		 res=res+contenu.getString("numeroduclient")+ajoute_espace(contenu.getString("numeroduclient"),35);
     		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
     		 res=res+contenu.getDate("datedelivraison").toString()+ajoute_espace(contenu.getDate("datedelivraison").toString(),35);
     		 res=res+"\n";
     	 }
     	 res=res+"\n";
     	 
     	 return type+res;
    }
    public String requete2(String login) throws SQLException {
      	 ResultSet rs = null;

           try{
                   st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                   rs = st.executeQuery("select distinct numerodecolis,numerodecommande,logindeladouane from colis natural join controledesdouanes where logindeladouane = '"+login+"' and numeroducontrole is not null");
                   
                   
           }catch(SQLException e){
   		e.printStackTrace();
           }
          
          ResultSet contenu=rs;
          String type="";
       	String res="";
       	type="NUMERO DU COLIS"+ajoute_espace("NUMERO DU COLIS",35)
       	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
       	+"LOGIN"+ajoute_espace("LOGIN",35)+
       	"\n";
       	
       	 while (contenu != null && contenu.next()) {
       		 res=res+contenu.getString("numerodecolis")+ajoute_espace(contenu.getString("numerodecolis"),35);
       		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
       		 res=res+contenu.getString("logindeladouane")+ajoute_espace(contenu.getString("logindeladouane"),35);
       		 res=res+"\n";
       	 }
       	 res=res+"\n";
       	 
       	 return type+res;
      }
    //
    public String requete3(String login) throws SQLException {
   	 ResultSet rsdouane = null;
   	 ResultSet rscommande = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rsdouane = st.executeQuery("select * from douane where login = '"+login+"'");
                rsdouane.first();
                String pays = rsdouane.getString("pays");
                rscommande = st.executeQuery("select distinct * from colis natural join commande where destination = '"+pays+"' and numeroducontrole is null");
                
                
        }catch(SQLException e){
		e.printStackTrace();
        }
       
       ResultSet contenu=rscommande;
       String type="";
    	String res="";
    	type="NUMERO DU CLIENT"+ajoute_espace("NUMERO DU CLIENT",35)
    	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
    	+"DATE LIVRAISON"+ajoute_espace("DATE LIVRAISON",35)
    	+"DESTINATION"+ajoute_espace("DESTINATION",35)+
    	"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numeroduclient")+ajoute_espace(contenu.getString("numeroduclient"),35);
    		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
    		 res=res+contenu.getDate("datedelivraison").toString()+ajoute_espace(contenu.getDate("datedelivraison").toString(),35);
    		 res=res+contenu.getString("destination")+ajoute_espace(contenu.getString("destination"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
   }
    public String requete4(String login) throws SQLException {
      	 ResultSet rsdouane = null;
      	 ResultSet rscommande = null;
           try{
                   st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                   rsdouane = st.executeQuery("select * from douane where login = '"+login+"'");
                   rsdouane.first();
                   String pays = rsdouane.getString("pays");
                   rscommande = st.executeQuery("select distinct * from colis natural join commande where destination = '"+pays+"' and numeroducontrole is null");
                   
                   
           }catch(SQLException e){
   		e.printStackTrace();
           }
          
          ResultSet contenu=rscommande;
          String type="";
       	String res="";
       	type="NUMERO DU CLIENT"+ajoute_espace("NUMERO DU CLIENT",35)
       	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
       	+"DATE LIVRAISON"+ajoute_espace("DATE LIVRAISON",35)
       	+"DESTINATION"+ajoute_espace("DESTINATION",35)+
       	"\n";
       	
       	 while (contenu != null && contenu.next()) {
       		 res=res+contenu.getString("numeroduclient")+ajoute_espace(contenu.getString("numeroduclient"),35);
       		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
       		 res=res+contenu.getDate("datedelivraison").toString()+ajoute_espace(contenu.getDate("datedelivraison").toString(),35);
       		 res=res+contenu.getString("destination")+ajoute_espace(contenu.getString("destination"),35);
       		 res=res+"\n";
       	 }
       	 res=res+"\n";
       	 
       	 return type+res;
      }
    public String requete5_commande(String login,int numerodecommande) throws SQLException {
     	 ResultSet rs = null;
     	
          try{
                  st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                  rs = st.executeQuery("select * from commande natural join quantitedeproduit where numerodecommande="+numerodecommande);
              
                  
          }catch(SQLException e){
  		e.printStackTrace();
          }
         
         ResultSet contenu=rs;
         String type="";
      	String res="";
      	type="NUMERO DU PRODUIT "+ajoute_espace("NUMERO DU PRODUIT",35)
      	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
      	+"DESTINATION"+ajoute_espace("DESTINATION",35)+
      	"\n";
      	
      	 while (contenu != null && contenu.next()) {
      		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
      		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
      		 res=res+contenu.getString("destination")+ajoute_espace(contenu.getString("destination"),35);
      		 res=res+"\n";
      	 }
      	 res=res+"\n";
      	 
      	 return type+res;
     }
    public String requete5_dest(String login,String destination) throws SQLException {
    	 ResultSet rs = null;
    	
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rs = st.executeQuery("select * from commande natural join quantitedeproduit where destination='"+destination+"'");
             
                 
         }catch(SQLException e){
 		e.printStackTrace();
         }
        
        ResultSet contenu=rs;
        String type="";
     	String res="";
     	type="NUMERO DU PRODUIT "+ajoute_espace("NUMERO DU PRODUIT",35)
     	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
     	+"DESTINATION"+ajoute_espace("DESTINATION",35)+
     	"\n";
     	
     	 while (contenu != null && contenu.next()) {
     		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
     		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
     		 res=res+contenu.getString("destination")+ajoute_espace(contenu.getString("destination"),35);
     		 res=res+"\n";
     	 }
     	 res=res+"\n";
     	 
     	 return type+res;
    }
    public String requete5_contenu(String login,String numeroduproduit) throws SQLException {
    	 ResultSet rs = null;
    	
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rs = st.executeQuery("select * from commande natural join quantitedeproduit where numeroduproduit='"+numeroduproduit+"'");
             
                 
         }catch(SQLException e){
 		e.printStackTrace();
         }
        
        ResultSet contenu=rs;
        String type="";
     	String res="";
     	type="NUMERO DU PRODUIT "+ajoute_espace("NUMERO DU PRODUIT",35)
     	+"NUMERO DE COMMANDE"+ajoute_espace("NUMERO DE COMMANDE",35)
     	+"DESTINATION"+ajoute_espace("DESTINATION",35)+
     	"\n";
     	
     	 while (contenu != null && contenu.next()) {
     		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
     		 res=res+contenu.getString("numerodecommande")+ajoute_espace(contenu.getString("numerodecommande"),35);
     		 res=res+contenu.getString("destination")+ajoute_espace(contenu.getString("destination"),35);
     		 res=res+"\n";
     	 }
     	 res=res+"\n";
     	 
     	 return type+res;
    }
    public void requete6(String login,String colis,String resultat) throws SQLException {
    	  ResultSet rscontrole = null;
    	  int numerodecontrole = 0;
          try{
        	  	
                  st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                  rscontrole = st.executeQuery("select max(numeroducontrole) as numeroducontrole from controledesdouanes");
                  rscontrole.first();
                  numerodecontrole = rscontrole.getInt("numeroducontrole");
                  numerodecontrole++;
                  System.out.println(numerodecontrole);
                  st.executeUpdate("insert into controledesdouanes values("+String.valueOf(numerodecontrole)+",'"+resultat+"',current_date,'"+login+"')");
                  if(resultat.equals("refuser")){
                	  st.executeUpdate("Update colis set suivi = 'emballe' where numerodecolis = '"+colis+"'");
                  }
                  st.executeUpdate("Update colis set numeroducontrole = "+numerodecontrole+" where numerodecolis = '"+colis+"'");
          }catch(SQLException e){
  		e.printStackTrace();
          }
      
     }
    public String requete7(String login) throws SQLException {
  	  ResultSet rs = null;
  	  int nbraccept = 0;
  	  int nbrrefuse = 0;
  	  int numerodecontrole = 0;
  	  int pourcentagedaccept = 0;
      try{
        	 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        	 rs = st.executeQuery("select count(resultat) as resultat from douane natural join controledesdouanes where resultat = 'accepter' and login = '"+login+"'");
        	 rs.first();
        	 nbraccept = rs.getInt("resultat");
        	 rs = st.executeQuery("select count(resultat) as resultat from douane natural join controledesdouanes where resultat = 'refuser' and login = '"+login+"'");
        	 rs.first();
        	 nbrrefuse = rs.getInt("resultat");
       }catch(SQLException e){
	   e.printStackTrace();
       }
       return "Nombre de colis accepter : "+String.valueOf(nbraccept)+"\nNombre de colis refuser : "+String.valueOf(nbrrefuse)+"\nTaux d'acceptation :"+
       			String.valueOf((int)(((double)(nbraccept)/(double)(nbraccept + nbrrefuse))*100.0))+"%";
    
   }
 
    public String requete_palette(String login,String numerodelapalette) throws SQLException {
   	 ResultSet rs = null;
   	
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select distinct numerodecolis from palette natural join colis where numerodelapalette = '"+numerodelapalette+"'");
            
                
        }catch(SQLException e){
		e.printStackTrace();
        }
       
       ResultSet contenu=rs;
       String type="";
    	String res="";
    	type="NUMERO DU COLIS "+ajoute_espace("NUMERO DU COLIS",35)+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numerodecolis")+ajoute_espace(contenu.getString("numerodecolis"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
   }
    
    public String requete_colis(String login,String numeroducolis) throws SQLException {
      	 ResultSet rs = null;
      	
           try{
                   st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                   rs = st.executeQuery("select * from colis where numerodecolis = '"+numeroducolis+"'");
               
                   
           }catch(SQLException e){
   		e.printStackTrace();
           }
          
          ResultSet contenu=rs;
          String type="";
       	String res="";
       	type="NUMERO DU PRODUIT "+ajoute_espace("NUMERO DU PRODUIT",35)+"\n";
       	
       	 while (contenu != null && contenu.next()) {
       		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
       		 res=res+"\n";
       	 }
       	 res=res+"\n";
       	 
       	 return type+res;
      }
    public String requete_prixproduit(String login,String numeroduproduit) throws SQLException {
     	 ResultSet rs = null;
     	
          try{
                  st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                  rs = st.executeQuery("select numeroduproduit,cout from produit where numeroduproduit='"+numeroduproduit+"'");
              
                  
          }catch(SQLException e){
  		e.printStackTrace();
          }
         
         ResultSet contenu=rs;
         String type="";
      	String res="";
      	type="NUMERO DU PRODUIT"+ajoute_espace("NUMERO DU PRODUIT",35)+
      	"COUT"+ajoute_espace("COUT",35)+"\n";
      	
      	 while (contenu != null && contenu.next()) {
      		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
      		res=res+contenu.getString("cout")+ajoute_espace(contenu.getString("cout"),35);
      		 res=res+"\n";
      	 }
      	 res=res+"\n";
      	 
      	 return type+res;
     }
    //-------------------------------------------------------------------------------REQUETE CLIENT----------------------------------------------------------------------------------------------------
    public ResultSet produit_dispo(String table) throws SQLException{
        ResultSet  rs = null;
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from "+table+" where reserve > 0");

        }catch(SQLException e){
		e.printStackTrace();
        }
        return rs;
    }
    public String table_produit_dispo() throws SQLException{
    	ResultSet contenu = produit_dispo("produit");
    	String type="";
    	String res="";
    	int cp=0;
    	type="NUMERO DU PRODUIT"+ajoute_espace("NUMERO DU PRODUIT",85)
    	+"DESCRIPTION DU PRODUIT"+ajoute_espace("DESCRIPTION DU PRODUIT",85)
    	+"QUANTITE PAR CARTON"+ajoute_espace("QUANTITE PAR CARTON",85)+
    	"CARTON PAR PALETTE"+ajoute_espace("CARTON PAR PALETTE",85)
    	+"QUALIFIANT"+ajoute_espace("QUALIFIANT",85)
    	+"COUT"+ajoute_espace("COUT",85)
    	+"TAUX AUGMENTATION DES PRIX"+ajoute_espace("TAUX AUGMENTATION DES PRIX",85)+
    	"POIDS"+ajoute_espace("POIDS",85)
    	+"RESERVE"+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),85);
    		 res=res+contenu.getString("descriptionduproduit")+ajoute_espace(contenu.getString("descriptionduproduit"),85);
    		 res=res+contenu.getString("quantiteparcarton")+ajoute_espace(contenu.getString("quantiteparcarton"),85);
    		 res=res+contenu.getString("cartonsparpalette")+ajoute_espace(contenu.getString("cartonsparpalette"),85);
    		 res=res+contenu.getString("qualifiant")+ajoute_espace(contenu.getString("qualifiant"),85);
    		 res=res+contenu.getString("cout")+ajoute_espace(contenu.getString("cout"),85);
    		 res=res+contenu.getString("tauxdaugmentationdesprix")+ajoute_espace(contenu.getString("tauxdaugmentationdesprix"),85);
    		 res=res+contenu.getString("poids")+ajoute_espace(contenu.getString("poids"),85);
    		 res=res+contenu.getString("reserve")+ajoute_espace(contenu.getString("reserve"),85);
    		 res=res+"\n";
    		 cp++;
    		 System.out.println(cp);
    	 }
    	 res=res+"\n";
    	 
    	 return type+res;
    }
    
    public void changer_mdp(String login,String password,String nv_password){
    	try{
    		st = conn.createStatement();
    		String mdp="update client set motdepasse='"+nv_password+"' where motdepasse='"+password+"' and numeroduclient='"+login+"'";
    		st.executeUpdate(mdp);	
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public void requete_datelivraison(String login,String date) throws SQLException {
    	 ResultSet rs = null;
       	 ResultSet rsclient = null;
   	  int numerodecommande = 0;
   	  String pays = "";
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rsclient = st.executeQuery("select pays from client where numeroduclient = '"+login+"'");
                 rsclient.first();
                 pays = rsclient.getString("pays");
                 System.out.println(pays);
                 rs = st.executeQuery("select max(numerodecommande) as numerodecommande from commande");
                 rs.first();
                 numerodecommande = rs.getInt("numerodecommande");
                 numerodecommande++;
                 System.out.println(numerodecommande);
                 st.executeUpdate("insert into commande values('"+login+"',"+String.valueOf(numerodecommande)+",'"+date+"','"+pays+"')");
         }catch(SQLException e){
 		e.printStackTrace();
         }
    }
    public String requete_suivi(String login) throws SQLException {
    	 ResultSet rs = null;
    	
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rs = st.executeQuery("select distinct suivi,numerodecolis from (quantitedeproduit natural join colis) natural join commande where numeroduclient='"+login+"'");
             
                 
         }catch(SQLException e){
 		e.printStackTrace();
         }
        
        ResultSet contenu=rs;
        String type="";
     	String res="";
     	type="SUIVI"+ajoute_espace("SUIVI",35)+
     	"NUMERO DE COLIS"+ajoute_espace("NUMERO DE COLIS",35)+"\n";
     	
     	 while (contenu != null && contenu.next()) {
     		 res=res+contenu.getString("suivi")+ajoute_espace(contenu.getString("suivi"),35);
     		res=res+contenu.getString("numerodecolis")+ajoute_espace(contenu.getString("numerodecolis"),35);
     		 res=res+"\n";
     	 }
     	 res=res+"\n";
     	 
     	 return type+res;
    }
    
    //-----------------------------------------------------------------------------REQUETE GERANT------------------------------------------------------------------------------------------------------
    public void changer_prixproduit(String numeroduproduit,String nv_cout){
    	try{
    		st = conn.createStatement();
    		String mdp="update produit set cout='"+nv_cout+"' where numeroduproduit='"+numeroduproduit+"'";
    		st.executeUpdate(mdp);
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public String requete_emploi(String login) throws SQLException {
   	 	String gerant=this.table_gerant();
   	 	String transport=this.table_transporteur();
    	ResultSet rs = null;
   	
        try{
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("select * from emballeur");
            
                
        }catch(SQLException e){
		e.printStackTrace();
        }
       
       ResultSet contenu=rs;
       String type="";
    	String res="";
    	type="NUMERO D'EMBALLEUR"+ajoute_espace("NUMERO D'EMBALLEUR",35)+
    	"NOM"+ajoute_espace("NOM",35)+
    	"PRENOM"+ajoute_espace("PRENOM",35)+"\n";
    	
    	 while (contenu != null && contenu.next()) {
    		 res=res+contenu.getString("numerodemballeur")+ajoute_espace(contenu.getString("numerodemballeur"),35);
    		 res=res+contenu.getString("nom")+ajoute_espace(contenu.getString("nom"),35);
    		 res=res+contenu.getString("prenom")+ajoute_espace(contenu.getString("prenom"),35);
    		 res=res+"\n";
    	 }
    	 res=res+"\n";
    	 
    	 return type+res+"\n"+gerant+"\n"+transport;
   }
    public String plus_vendu(String login) throws SQLException {
      	 ResultSet rs = null;
      	
           try{
                   st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                   rs = st.executeQuery("select sum(quantite) as maxVente,numeroduproduit from quantitedeproduit group by numeroduproduit order by maxVente desc");
               
                   
           }catch(SQLException e){
   		e.printStackTrace();
           }
          
          ResultSet contenu=rs;
          String type="";
       	String res="";
       	type="MAX VENTE"+ajoute_espace("MAX VENTE",35)+
       	"NUMERO DU PRODUIT"+ajoute_espace("NUMERO DU PRODUIT",35)+"\n";
       	
       	 while (contenu != null && contenu.next()) {
       		 res=res+contenu.getString("maxvente")+ajoute_espace(contenu.getString("maxvente"),35);
       		 res=res+contenu.getString("numeroduproduit")+ajoute_espace(contenu.getString("numeroduproduit"),35);
       		 res=res+"\n";
       	 }
       	 res=res+"\n";
       	 
       	 return type+res;
      }
    public String plus_depense(String login) throws SQLException {
     	 ResultSet rs = null;
     	
          try{
                  st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                  rs = st.executeQuery("select sum(quantite * cout) as maxDepense,numeroduclient from (quantitedeproduit natural join commande) natural join produit group by numeroduclient order by maxDepense desc");
              
                  
          }catch(SQLException e){
  		e.printStackTrace();
          }
         
         ResultSet contenu=rs;
         String type="";
      	String res="";
      	type="MAX DEPENSE"+ajoute_espace("MAX DEPENSE",35)+
      	"NUMERO DU CLIENT"+ajoute_espace("NUMERO DU CLIENT",35)+"\n";
      	
      	 while (contenu != null && contenu.next()) {
      		 res=res+contenu.getString("maxdepense")+ajoute_espace(contenu.getString("maxdepense"),35);
      		 res=res+contenu.getString("numeroduclient")+ajoute_espace(contenu.getString("numeroduclient"),35);
      		 res=res+"\n";
      	 }
      	 res=res+"\n";
      	 
      	 return type+res;
     }
    public String requete_emballeur_traite(String login,String numerodemballeur,String date) throws SQLException {
    	 ResultSet rs = null;
    	
         try{
                 st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                 rs = st.executeQuery("select numerodecolis,numerodemballeur,datedemballage from (quantitedeproduit natural join emballage) natural join colis " +
                 		"where numerodemballeur='"+numerodemballeur+"' and datedemballage='"+date+"'");
             
                 
         }catch(SQLException e){
 		e.printStackTrace();
         }
        
        ResultSet contenu=rs;
        String type="";
     	String res="";
     	type="NUMERO DE COLIS"+ajoute_espace("NUMERO DE COLIS",35)+
     	"NUMERO DEMBALLEUR"+ajoute_espace("NUMERO DEMBALLEUR",35)+
     	"DATE EMBALLAGE"+ajoute_espace("DATE EMBALLAGE",35)+"\n";
     	
     	 while (contenu != null && contenu.next()) {
     		 res=res+contenu.getString("numerodecolis")+ajoute_espace(contenu.getString("numerodecolis"),35);
     		 res=res+contenu.getString("numerodemballeur")+ajoute_espace(contenu.getString("numerodemballeur"),35);
     		 res=res+contenu.getDate("datedemballage").toString()+ajoute_espace(contenu.getDate("datedemballage").toString(),35);
     		 res=res+"\n";
     	 }
     	 res=res+"\n";
     	 
     	 return type+res;
    }
}
