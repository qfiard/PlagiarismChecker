import java.io.*;
import java.sql.*;

class connectionBDD {
    Connection conn; // la connexion a la base
    Statement st;
    PreparedStatement insert;
    PreparedStatement delete;
    PreparedStatement update;
    
    // connection a la base
    public connectionBDD(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login,login, motPasse);
    }//localhost/
	
	public ResultSet verifieLoginPassword(String nomTable,String log , String pass){
		ResultSet s =null;
		try{
			st = conn.createStatement();
			s = st.executeQuery("select login,password,pays from "+nomTable + " where login ='"+log + "' and password = '" +pass+"';");
		}catch(SQLException e){
		}
		return s;
	}
	
    // fermeture de la connection
    public void close() throws SQLException{ 
		conn.close();
    }
	
    public ResultSet contenuTable() throws SQLException{
		ResultSet  s = null;
		try{
			st = conn.createStatement();
			s = st.executeQuery("select * from produit");
		}catch(SQLException e){
		}
		return s;
    }
	
	public ResultSet contenuTable(String nomTable) throws SQLException{
		ResultSet  s = null;
		try{
			st = conn.createStatement();
			s = st.executeQuery("select * from "+ nomTable);
		}catch(SQLException e){
		}
		return s;
    }
	
	//-------------------------------------------------------
	// FONCTION D'AFFICHAGE 
	//-------------------------------------------------------
	
	public String affichage(String s,int i){
    	String res="";
		res = s;
    	for (i -= s.length(); i >= 0; i --)
			res=res+" ";	
    	return res;
    }
	
	public String affiche_commande(ResultSet contenu){
		String res ="";
		res += affichage("NUMERO COMMANDE",32);
		res += "|" + affichage("NUMERO CLIENT",32) + "\n";
		try {
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("numero_cmd"),32);
				res += affichage(contenu.getString("c_numero"),32);
				res += "\n";
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return res;				
	}
	
	
	
	

	/*#####################################################################################################################################
	 
	 								REQUETE SQL       REQUETE SQL         REQUETE SQL

	#####################################################################################################################################*/

	
	
	

	
	//-------------------------------------------------------
	// REQUETE SQL DOUANE
	//-------------------------------------------------------
	
	//Visualiser toute les commandes du Pays de la Douane			
	public String print_commande(String Pays){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,numero_cmd from commande natural join client where pays = '" + Pays + "';");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NUMERO COMMANDE",32) + "\n";
			
		
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//Visualiser une liste de toutes les commandes controlees par cette Douane  
	public String print_commande2(String Pays){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,numero_cmd,numero_prod,quantite,status_douane,numero_colis from (client natural join commande) natural join (produit_commande natural join colis) where pays ='"+Pays+"' and status_douane is not null;");
			res += affichage("NUMERO CLIENT",16);
			res += affichage("NUMERO COMMANDE",16);
			res += affichage("NUMERO PRODUIT",20);
			res += affichage("QUANTITE",16);
			res += affichage("STATUS DOUANE",16);
			res += affichage("NUMERO DE COLIS",16)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),16);
				res += affichage(contenu.getString("numero_cmd"),16);
				res += affichage(contenu.getString("numero_prod"),20);
				res += affichage(contenu.getString("quantite"),16);
				res += affichage(contenu.getString("status_douane"),16);
				res += affichage(contenu.getString("numero_colis"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//Visualiser une liste de toutes les commandes expediees au Pays de la Douane mais non controlees
	public String print_commande3(String Pays ){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,numero_cmd,numero_prod,quantite,numero_colis from (client natural join commande) natural join (produit_commande natural join colis)  where pays = '"+ Pays +"' and status_douane is null;");
			res += affichage("NUMERO CLIENT",16);
			res += affichage("NUMERO COMMANDE",16);
			res += affichage("NUMERO PRODUIT",20);
			res += affichage("QUANTITE",16);
			res += affichage("NUMERO DE COLIS",16)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),16);
				res += affichage(contenu.getString("numero_cmd"),16);
				res += affichage(contenu.getString("numero_prod"),20);
				res += affichage(contenu.getString("quantite"),16);
				res += affichage(contenu.getString("numero_colis"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st !=null){try{st.close();}catch(Exception e){e.printStackTrace();} }
		}
		return res;
	}
	
	
	//STATISTIQUES SUR LES CONTROLES
	public String statistique(String Pays ){
		ResultSet  contenu = null;
		String res ="";
		int accepter = 0;
		int refuser = 0;
		double pourcentageA = 0;
		double pourcentageR = 0;
		int nbcolis = 0;
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select numero_colis from (client natural join commande) natural join (produit_commande natural join colis) where pays ='"+Pays+"' and status_douane = 'accepter';");
			res += affichage("PAYS",16);
			res += affichage("NOMBRE COLIS ACCEPTER",30);
			res += affichage("NOMBRE COLIS REFUSER",30);
			res += affichage("STATISTIQUE REFUSER",30);
			res += affichage("STATISTIQUE ACCEPTER",30)+ "\n";
			contenu.first();


			while (contenu !=null && contenu.next() ) {
				accepter += 1;
			}
			Statement st2 = conn.createStatement();
			contenu = st2.executeQuery("select numero_colis from (client natural join commande) natural join (produit_commande natural join colis) where pays ='"+Pays+"' and status_douane = 'refuser';");
			while (contenu !=null && contenu.next() ) {
				refuser += 1;
			}
			
			Statement st3 = conn.createStatement();
			contenu = st3.executeQuery("select numero_colis from (client natural join commande) natural join (produit_commande natural join colis) where pays ='"+Pays+"';");
			while (contenu !=null && contenu.next() ) {
				nbcolis += 1;
			}
			pourcentageA = accepter / nbcolis;
			pourcentageR = refuser / nbcolis;
			res += affichage(Pays,16);
			res += affichage(String.valueOf(accepter),30);
			res += affichage(String.valueOf(refuser),30);
			res += affichage(String.valueOf((int)(pourcentageA)),30);
			res += affichage(String.valueOf((int)(pourcentageR)),30);


		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st !=null){try{st.close();}catch(Exception e){e.printStackTrace();} }
		}
		return res;
	    
	}
	
	
	
	
	
	
	
	
	
	//-------------------------------------------------------
	// REQUETE SQL DOUANE AVEC RECHERCHE 
	//-------------------------------------------------------
	
	
	
	//RECHERCHE LES PRIX DES PRODUIT TRANSPORTER
	public String recherche_produit1(String numero_colis){
		ResultSet  contenu = null;
		ResultSet  contenu2 = null;	
		
		String res ="";
		String prod ="";
			
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query = "select c_numero,numero_cmd,numero_prod,quantite,numero_colis from (client natural join commande) natural join produit_commande where numero_colis ="+numero_colis+";";
			contenu = st.executeQuery(query);
			res += affichage("NUMERO CLIENT",20);
			res += affichage("NUMERO COMMANDE",16);
			res += affichage("NUMERO PRODUIT",20);
			res += affichage("PRIX", 16);
			res += affichage("QUANTITE",16);
			res += affichage("NUMERO DE COLIS",16)+ "\n";
				
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),20);
				res += affichage(contenu.getString("numero_cmd"),16);
				prod = contenu.getString("numero_prod");
				res += affichage(prod,20);
					
				String query2 = "select prix from produit where numero_prod = '"+prod+"';";
				contenu2 = st.executeQuery(query2);
				while (contenu2 !=null && contenu2.next() ) {
					res += affichage(contenu2.getString("prix"),16);
				}
				res += affichage(contenu.getString("quantite"),16);
				res += affichage(contenu.getString("numero_colis"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
		
	
	
	//RECHERCHE PARMI LES COMMANDES PAR LE NUMERO CMD       
	public String recherche_commande1(String numero_cmd){ 
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			contenu = st.executeQuery("select c_numero,numero_cmd from (client natural join commande) where numero_cmd = "+numero_cmd+";");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NUMERO COMMANDE",32) + "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st !=null){try{st.close();}catch(Exception e){e.printStackTrace();} }
		}
		return res;
	}
	
	
	//RECHERCHE PARMI LES COMMANDES LA DESTINATION       
		public String recherche_commande2(String destination){ // numero_prod = contenu
			ResultSet  contenu = null;
			String res ="";
			
			try{
				st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				contenu = st.executeQuery("select c_numero,numero_cmd from (client natural join commande) where pays = '"+destination+"';");
				res += affichage("NUMERO CLIENT",32);
				res += affichage("NUMERO COMMANDE",32) + "\n";
				
				while (contenu !=null && contenu.next() ) {
					res += affichage(contenu.getString("c_numero"),32);
					res += affichage(contenu.getString("numero_cmd"),32);
					res += "\n";
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(st !=null){try{st.close();}catch(Exception e){e.printStackTrace();} }
			}
			return res;
		}
		
		
		//RECHERCHE PARMI LES COMMANDES PAR LE CONTENU      
		public String recherche_commande3(String produits){ // numero_prod = contenu
			ResultSet  contenu = null;
			String res ="";
			
			try{
				st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				contenu = st.executeQuery("select c_numero,numero_cmd,numero_prod,quantite,numero_colis from (client natural join commande) natural join produit_commande where numero_prod ='"+produits+"';");
				res += affichage("NUMERO CLIENT",16);
				res += affichage("NUMERO COMMANDE",16);
				res += affichage("NUMERO PRODUIT",20);
				res += affichage("QUANTITE",16);
				res += affichage("NUMERO DE COLIS",16)+ "\n";
				
				while (contenu !=null && contenu.next() ) {
					res += affichage(contenu.getString("c_numero"),16);
					res += affichage(contenu.getString("numero_cmd"),16);
					res += affichage(contenu.getString("numero_prod"),20);
					res += affichage(contenu.getString("quantite"),16);
					res += affichage(contenu.getString("numero_colis"),16);
					res += "\n";
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
	
	//RECHERCHE LES DETAILS DES COMMANDES    
	public String recherche_commande4(String numero_cmd){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query = "select c_numero,numero_cmd,numero_prod,quantite,numero_colis from (client natural join commande) natural join produit_commande where numero_cmd ="+numero_cmd+";";
			contenu = st.executeQuery(query);
			res += affichage("NUMERO CLIENT",20);
			res += affichage("NUMERO COMMANDE",16);
			res += affichage("NUMERO PRODUIT",20);
			res += affichage("QUANTITE",16);
			res += affichage("NUMERO DE COLIS",16)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),20);
				res += affichage(contenu.getString("numero_cmd"),16);
				res += affichage(contenu.getString("numero_prod"),20);
				res += affichage(contenu.getString("quantite"),16);
				res += affichage(contenu.getString("numero_colis"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	//-------------------------------------------------------
	// MODIFICATION DE LA BASE PAR LA DOUANE
	//-------------------------------------------------------
	
	//ENTRER LES RESULTAT D'UN CONTROLE							
	public void modificationControle(String status,String numero_colis){
		try{
			st = conn.createStatement();
			if(status.equals("refuser")){
				st.executeUpdate("update colis set suivi = 'renvoyer' where numero_colis = '"+numero_colis+"'");
			}
			String modif = "update colis set status_douane ="+status+" where numero_colis = "+numero_colis+";";
			st.executeUpdate(modif);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
    }	
	
	
	
	
	//-------------------------------------------------------
	// RECHERCHE POUR LISTAGE
	//-------------------------------------------------------
	
	//RECHERCHE LES DETAILS D UN CONTENEUR
	public String recherche_lister1(String numero_cont){
		ResultSet  contenu = null;
		String res ="";
			
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query = "select numero_cont,numero_pal,codetransporteur from palette where numero_cont = "+numero_cont+";";
			contenu = st.executeQuery(query);
			res += affichage("NUMERO CONTAINER",20);
			res += affichage("NUMERO PALETTE",16);
			res += affichage("CODE TRANSPORTEURS",16)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("numero_cont"),20);
				res += affichage(contenu.getString("numero_pal"),16);
				res += affichage(contenu.getString("codetransporteur"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}	
	

	
	//RECHERCHE LES DETAILS D UNE PALETTE
	public String recherche_lister2(String numero_pal){

		ResultSet  contenu = null;
		String res ="";
			
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query = "select numero_colis,pays,numero_emballeur,numero_pal from colis where numero_pal ="+numero_pal+";";
			contenu = st.executeQuery(query);
			res += affichage("NUMERO COLIS",16);
			res += affichage("PAYS DE LA DOUANE",16);
			res += affichage("NUMERO EMBALLEUR",20);
			res += affichage("NUMERO PALETTE",16)+ "\n";
				
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("numero_colis"),16);
				res += affichage(contenu.getString("pays"),16);
				res += affichage(contenu.getString("numero_emballeur"),20);
				res += affichage(contenu.getString("numero_pal"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//RECHERCHE LES DETAILS D UN COLIS
	public String recherche_lister3(String numero_colis){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query = "select c_numero,numero_cmd,numero_prod,quantite,numero_colis from (client natural join commande) natural join produit_commande where numero_colis ="+numero_colis+";";
			contenu = st.executeQuery(query);
			res += affichage("NUMERO CLIENT",20);
			res += affichage("NUMERO COMMANDE",16);
			res += affichage("NUMERO PRODUIT",20);
			res += affichage("QUANTITE",16);
			res += affichage("NUMERO DE COLIS",16)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),20);
				res += affichage(contenu.getString("numero_cmd"),16);
				res += affichage(contenu.getString("numero_prod"),20);
				res += affichage(contenu.getString("quantite"),16);
				res += affichage(contenu.getString("numero_colis"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------------------------
	// REQUETE SQL GERANT  
	//-------------------------------------------------------
	
	//CHANGER LE PRIX DES PRODUIT
	public void modificationProduit(String prix,String numero_prod){
		try{
			st = conn.createStatement();
			String modif = "update produit set prix ="+ prix +" where numero_prod = " + numero_prod +";";
			st.executeUpdate(modif);
		}catch(SQLException e){
		}
    }	
	
	//LISTE DES CLIENT								
	public String gerant_SQL1(){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,nomsoc,phone from client;");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NOM SOCIETE",32);
			res += affichage("TELEPHONE",32) + "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("nomsoc"),32);
				res += affichage(contenu.getString("phone"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//LISTER LA LISTE DES EMPLOYES                            
	public String gerant_SQL2(){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select numero_employe,nom from employe;");
			res += affichage("NUMERO EMPLOYE",20);
			res += affichage("NOM ",32)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("numero_employe"),20);
				res += affichage(contenu.getString("nom"),24);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
	
	//LISTER LE NOMBRE DE COLIS QU'UN EMBALLEUR TRAITE PAR JOURS				
	public String gerant_SQL2(String Date,String numeroEmballeur){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select numero_colis,pays,date_emballage,numero_emballeur,numero_pal from colis where date_emballage ='"+Date +"' and numero_emballeur = '"+numeroEmballeur + "';");
			res += affichage("NUMERO COLIS",8);
			res += affichage("PAYS",12);
			res += affichage("date_emballage",16);
			res += affichage("numero_emballeur",16);
			res += affichage("numero_pal",16) + "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("numero_colis"),8);
				res += affichage(contenu.getString("pays"),12);
				res += affichage(contenu.getString("date_emballage"),16);
				res += affichage(contenu.getString("numero_emballeur"),16);
				res += affichage(contenu.getString("numero_pal"),16);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//
	//		EMBALLEUR
	//
	//
	//		TRANSPORTEUR
	//
	
	
	//LISTER DES COMMANDES D'UN CLIENT SANS CONNAITRE SON IDENTITE
	public String emballeur_SQL1(String client_numero){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,numero_cmd from (client natural join commande) natural join (produit_commande natural join colis) where status_colis = 'non expediees' and c_numero='"+client_numero+"';");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NUMERO COMMANDE",32)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//LISTER DES COMMANDES D'UN CLIENT SANS CONNAITRE SON IDENTITE
	public String transporteur_SQL1(String client_numero){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select c_numero,numero_cmd from (client natural join commande) natural join (produit_commande natural join colis) where status_colis = 'non expediees' and c_numero='"+client_numero+"';");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NUMERO COMMANDE",32)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	//SAVOIR SI UN COLIS EST FRAGILE
	public String emballeur_SQL2(String numero){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select emballage from colis where status_colis = 'non expediees' and numero_colis="+numero+";");
			res += affichage("NUMERO CLIENT",32);
			res += affichage("NUMERO COMMANDE",32)+ "\n";
			
			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("c_numero"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//SAVOIR LA DATE LIVRAISON D UNE COMMANDE SOUHAITE PAR LE CLIENT
	public String emballeur_SQL3(String numero_commande){
		ResultSet  contenu = null;
		String res ="";
		
		try{
			st = conn.createStatement();
			contenu = st.executeQuery("select date, numero from colis where status_colis = 'non expediees' and numero_commande="+numero_commande+";");
			res += affichage("DATE ",32);;
			res += affichage("NUMERO COMMANDE",32)+"\n";

			while (contenu !=null && contenu.next() ) {
				res += affichage(contenu.getString("date"),32);
				res += affichage(contenu.getString("numero_cmd"),32);
				res += "\n";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	
	
	
} 
