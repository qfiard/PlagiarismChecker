import java.sql.*;
import java.util.Date;

class SQL{
	Connection conn;
	static Statement st;
	ResultSet rs;
	FonctionsCommunes fonc = new FonctionsCommunes(this);
  
  
	// Fonction pour connexion a nivose
	public SQL(/*String login, String motPasse*/) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
    
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + "diep", "diep", "lidosdun");
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Declare ici pour toute la classe
	}
  
	/*
   // Test sans passer par le reseau
   public SQL() throws SQLException, ClassNotFoundException{
   // -------------------
   // Connexion a la base
   // --------------------
   
   Class.forName("org.postgresql.Driver");
   conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/projet", "postgres", "nd221290");
   st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Declare ici pour toute la classe
   }
	 */
  
  
	// Fermeture de la connection
	public void close() throws SQLException{ 
		conn.close();
	}
  
	// Appel aux methodes de creation de tables
	public void creationTable() throws SQLException {
		creationClient();
		creationProduit();
		creationTransporteur();
		creationDouane();
		creationEmballeur();
		creationGerant();
		creationCommande();
		creationPalette();
		creationCarton();
		creationCartonContientProduit();
		creationControleCommande();
		creationCommande_Contient_Produit();
	}
  
  
	// Appel aux methodes de suppression de tables
	public void dropTable() throws SQLException{
		suppressionClient();
		suppressionProduit();
		suppressionTransporteur();
		suppressionDouane();
		supressionEmballeur();
		suppressionGerant();
		suppressionCommande();
		suppressionPalette();
		suppressionCarton();
		suppressionControleCommande();
		suppressionCarton_Contient_Produit();
		suppressionCommande_Contient_Produit();
	}
  
	// Verifications des couples login / mdp pour identification de chaque utilisateur
  
	public boolean verif_client(String login, String mdp) throws SQLException {
		ResultSet Rs = st.executeQuery("SELECT ID_client FROM client WHERE nom_societe='"+login.toUpperCase()+"' AND mdp='"+mdp+"';");
		while (Rs.next()) {
			Rs.close();
			return true;
		}
		return false;
	}
  
	public boolean verif_gerant(String login, String mdp) throws SQLException {
		ResultSet Rs = st.executeQuery("SELECT nom FROM gerant WHERE login='"+login.toUpperCase()+"' AND mdp='"+mdp+"';");
		while (Rs.next()) {
			Rs.close();
			return true;
		}
		return false;
	}
  
	public boolean verif_douane(String login, String mdp) throws SQLException {
		ResultSet Rs = st.executeQuery("SELECT pays FROM douane WHERE login='"+login.toUpperCase()+"' AND mdp='"+mdp+"';");
		while (Rs.next()) {
			Rs.close();
			return true;
		}
		return false;
	}
  
	public boolean verif_emballeur(String login, String mdp) throws SQLException {
		ResultSet Rs = st.executeQuery("SELECT ID_emballeur FROM emballeur WHERE ID_emballeur='"+login.toUpperCase()+"' AND mdp='"+mdp+"' AND licencie = '0';");
		while (Rs.next()) {
			Rs.close();
			return true;
		}
		return false;
	}
  
	public boolean verif_transporteur(String login, String mdp) throws SQLException {
		ResultSet Rs = st.executeQuery("SELECT ID_transporteur FROM transporteur WHERE nom='"+login.toUpperCase()+"' AND mdp='"+mdp+"' AND licencie = '0';");
		while (Rs.next()) {
			Rs.close();
			return true;
		}
		return false;
	}
  
	public void creationClient() throws SQLException {
    
		st.execute("CREATE TABLE client(" +
               "ID_client VARCHAR(20) NOT NULL PRIMARY KEY, "+
               "nom_societe VARCHAR(40) NOT NULL, " +
               "suffixe_societe VARCHAR(40) NOT NULL, " +
               "adresse VARCHAR(150) NOT NULL, " +
               "ville VARCHAR(40) NOT NULL, " +
               "code_postal VARCHAR(10) NOT NULL, " +
               "pays VARCHAR(40) check(pays IN('USA','CHINE', 'JAPON', 'FRANCE', 'CANADA', 'INDE')), " +
               "telephone VARCHAR(20) NOT NULL, " +
               "mdp VARCHAR(20) NOT NULL" +
               ");"
               );
	}
  
	/* Qualifiant :
	 *N : S/O
	 *D : Dangereux
	 *F : Fragile
	 *X : Dangereux ET Fragile
	 */
  
	public void creationProduit() throws SQLException{
		st.execute("CREATE TABLE produit("+
               "ID_produit VARCHAR(20) NOT NULL PRIMARY KEY, "+
               "description VARCHAR(100) NOT NULL, " +
               "quantite_par_carton NUMERIC(6) NOT NULL, " +
               "cartons_par_palette NUMERIC(5) NOT NULL, " +
               "qualifiant VARCHAR(1) CHECK(qualifiant IN('N', 'D', 'F', 'X')), " +
               "cout NUMERIC(10) NOT NULL, " +
               "taux_augmentation NUMERIC(6) NOT NULL, " +
               "poids NUMERIC(10) NOT NULL, " +
               "reserve NUMERIC(10) NOT NULL" +
               ");"
               );
	}
  
	public void creationTransporteur() throws SQLException{
		st.execute("CREATE TABLE transporteur(" +
               "ID_transporteur VARCHAR(20) NOT NULL PRIMARY KEY, " +
               "nom VARCHAR(40) NOT NULL, " +
               "mdp VARCHAR(20) NOT NULL, " +
               "licencie VARCHAR(1) CHECK(licencie IN('0', '1'))" +
               ");"
               );
	}
  
  
	public void creationDouane() throws SQLException{
		st.execute("CREATE TABLE douane(" +
               "pays VARCHAR(40) NOT NULL, " +
               "taux_commande_verifies NUMERIC(5) NOT NULL, " +
               "login VARCHAR(40) NOT NULL PRIMARY KEY, " +
               "mdp VARCHAR(40) NOT NULL" +
               ");"
               );
	}
  
	public void creationEmballeur() throws SQLException{
		st.execute("CREATE TABLE emballeur(" +
               "ID_emballeur VARCHAR(40) NOT NULL PRIMARY KEY, " +
               "nom VARCHAR(40) NOT NULL, " +
               "prenom VARCHAR(40) NOT NULL, " +
               "taux_erreur VARCHAR(40) NOT NULL, " +
               "mdp VARCHAR(40) NOT NULL, " +
               "licencie VARCHAR(1) CHECK(licencie IN('0', '1'))" +
               ");");
	}
  
	public void creationGerant() throws SQLException{
		st.execute("CREATE TABLE gerant(" +
               "prenom VARCHAR(40) NOT NULL, " +
               "nom VARCHAR(40) NOT NULL, " +
               "login VARCHAR(40) NOT NULL PRIMARY KEY, " +
               "mdp VARCHAR(40) NOT NULL" +
               ");"
               );
	}
  
	/* Qualifiant :
	 *	N : S/O
	 *	D : Dangereux
	 *	F : Fragile
	 *	X : Dangereux ET Fragile
	 *
	 *	Suivi :
	 *	NON_EXP
	 *	PARTIELLEMENT_EXP
	 *	EXPEDIE
	 */
	public void creationCommande() throws SQLException{
		st.execute("CREATE TABLE commande(" +
               "ID_commande VARCHAR(20) NOT NULL PRIMARY KEY, " +
               "ID_client VARCHAR(20) REFERENCES client(ID_client), " +
               "qualifiant VARCHAR(1) CHECK(qualifiant IN('N', 'D', 'F', 'X')) NOT NULL, " +
               "suivi VARCHAR(20) CHECK(suivi IN('NON_EXP', 'PARTIELLEMENT_EXP', 'EXPEDIE')) NOT NULL, " +
               "prix NUMERIC(10) NOT NULL, " +
               "dernier_traitement DATE, " +
               "date_livraison DATE, " +
               "ID_emballeur VARCHAR(40) REFERENCES emballeur(ID_emballeur), " +
               "ID_transporteur VARCHAR(40) REFERENCES transporteur(ID_transporteur)" +
               ");"
               );
	}
  
	public void creationCarton() throws SQLException{
		st.execute("CREATE TABLE carton(" +
               "ID_carton VARCHAR(20) PRIMARY KEY, " +
               "ID_commande VARCHAR(20) NOT NULL REFERENCES commande(ID_commande), " +
               "ID_produit VARCHAR(20) REFERENCES produit(ID_produit), " +
               "quantite_produit numeric(4) NOT NULL, " +
               "qualifiant VARCHAR(1) CHECK(qualifiant IN('N', 'D', 'F', 'X')) NOT NULL, " +
               "ID_palette VARCHAR(20) REFERENCES palette(ID_palette), " +
               "destination VARCHAR(40) NOT NULL" +
               ");"
               );
	}
  
	public void creationPalette() throws SQLException{
		st.execute("CREATE TABLE palette(" +
               "ID_palette VARCHAR(20) NOT NULL PRIMARY KEY, " +
               "ID_commande VARCHAR(20) NOT NULL, " +
               "destination VARCHAR(40) NOT NULL, " +
               "qualifiant VARCHAR(1) NOT NULL, " +
               "dernier_traitement DATE, " +
               "date_livraison DATE" +
               ");"
               );
	}
  
	/*controle_douane :
	 *	CONTROLE
	 *	NON_CONTROLE
	 *	REJETE
	 *	null = commande pas encore envoyee
	 */
  
	public void creationControleCommande() throws SQLException{
		st.execute("CREATE TABLE controleCommande(" +
               "ID_commande VARCHAR(20) REFERENCES commande(ID_commande), " +
               "douanier VARCHAR(20) REFERENCES douane(login), " +
               "controle_douane VARCHAR(15) CHECK(controle_douane IN('CONTROLE', 'NON_CONTROLE', 'REJETE', null))" +
               ");"
               );
	}
  
  
	// Palette > Carton > Produit
  
  
	public void creationCommande_Contient_Produit() throws SQLException{
		st.execute("CREATE TABLE commande_contient_produit(" +
               "ID_commande VARCHAR(20) REFERENCES commande(ID_commande) , " +
               "ID_produit VARCHAR(20) REFERENCES produit(ID_produit), " +
               "quantite NUMERIC(10) NOT NULL" +
               ");"
               );
	}
  
	public void creationCartonContientProduit() throws SQLException {
		st.execute("CREATE TABLE carton_contient_produit("+
               "ID_carton VARCHAR(20) PRIMARY KEY, "+
               "ID_commande VARCHAR(20) REFERENCES commande(ID_commande), "+
               "ID_produit VARCHAR(20) REFERENCES produit(ID_produit), "+
               "quantite NUMERIC(10) NOT NULL"+
               ");"
               );
	}
  
	// Creation d'une table particuliere (l'utilisateur doit entrer le code exact)
  
	public void creationTable(String code) throws SQLException{
		st.execute(code);
	}
  
  
	// Client
	public ResultSet clientListerProduits() throws SQLException{
		return st.executeQuery("SELECT ID_produit, description, cout, poids, reserve FROM produit WHERE reserve > '0';");
	}
  
	public ResultSet clientRecupID(String nom_societe, String mdp) throws SQLException{
		return st.executeQuery("SELECT ID_client FROM client WHERE nom_societe = '" + nom_societe.toUpperCase() + "' AND mdp = '" + mdp +"';");
	}
  
	public ResultSet clientSuiviCommande(String ID_client) throws SQLException{
		return st.executeQuery("SELECT ID_commande, prix, suivi, dernier_traitement, date_livraison FROM commande WHERE ID_client = '" + ID_client.toUpperCase() + "' ORDER BY date_livraison;");
	}
  
	public ResultSet clientSuiviCommandeNonExp(String ID_client) throws SQLException{
		return st.executeQuery("SELECT ID_commande, prix, suivi, dernier_traitement, date_livraison FROM commande WHERE ID_client = '" + ID_client.toUpperCase() + "' AND suivi = 'NON_EXP' ORDER BY date_livraison;");
	}
  
	public void clientChangerLogin(String newl, String log) throws SQLException{
		st.execute("UPDATE client SET nom_societe='"+newl.toUpperCase()+"' WHERE ID_client='"+log.toUpperCase()+"';");
	}
  
	public void clientChangerMdp(String mdp, String log) throws SQLException{
		st.execute("UPDATE client SET mdp='"+mdp+"' WHERE ID_client='"+log.toUpperCase()+"';");
	}
  
	public void clientChoisirDate(String ID_commande, String date) throws SQLException{
		st.execute("UPDATE commande SET date_livraison = '" + java.sql.Date.valueOf(date) + "' WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
  
  
	// Emballeur
  
	public ResultSet emballeurPreparerCommande(String ID_commande) throws SQLException{
		return st.executeQuery("SELECT ID_produit, description, qualifiant, quantite AS quantite_commandee, quantite_par_carton, cartons_par_palette FROM commande_contient_produit NATURAL JOIN produit WHERE ID_commande= '" + ID_commande.toUpperCase() + "';");
	}
  
	// Retourne les commandes qui n'ont pas encore ete traitees a la date donnee en parametre pour un certain emballeur
	public ResultSet emballeurListerCommande(String ID_emballeur, Date date) throws SQLException{
		return st.executeQuery("SELECT ID_commande, ID_client, qualifiant, pays AS destination, date_livraison FROM commande NATURAL JOIN client WHERE dernier_traitement <= '" + date + "' AND suivi = 'NON_EXP' AND ID_emballeur = '" + ID_emballeur.toUpperCase() + "' ORDER BY date_livraison;");
	}
  
	public void emballeurValiderCommande(String ID_commande) throws SQLException{
		st.execute("UPDATE commande SET dernier_traitement='" +fonc.dateToday() +"' WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
  
	public void emballeurValiderPalette(String ID_palette) throws SQLException{
		st.execute("UPDATE palette SET dernier_traitement='" +fonc.dateToday()+"' WHERE ID_palette = '" + ID_palette.toUpperCase() + "';");
	}
  
  
	// Douane
	public ResultSet douaneListerCommande(String pays) throws SQLException{
		return st.executeQuery("SELECT ID_commande, qualifiant FROM commande NATURAL JOIN client WHERE suivi = 'PARTIELLEMENT_EXP' AND pays = '" + pays.toUpperCase() + "' ORDER BY dernier_traitement;");
	}
	public ResultSet douaneListerPalette(String pays) throws SQLException{
		return st.executeQuery("SELECT ID_palette, qualifiant FROM commande NATURAL JOIN palette NATURAL JOIN client WHERE suivi = 'PARTIELLEMENT_EXP' AND pays = '" + pays.toUpperCase() + "' ORDER BY dernier_traitement;");
	}
	public void douaneRefuserCommande(String ID_commande) throws SQLException{
		st.execute("UPDATE commande SET suivi='NON_EXP' WHERE ID_commande = '" + ID_commande.toUpperCase() +"';");
		st.execute("UPDATE controlecommande SET controle_douane = 'REJETE' WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
	public ResultSet douaneListerCartonInPalette(String ID_palette, String pays) throws SQLException{
		return st.executeQuery("SELECT ID_carton, ID_produit, quantite_produit, qualifiant FROM carton NATURAL JOIN palette NATURAL JOIN commande NATURAL JOIN client WHERE ID_palette = '" + ID_palette.toUpperCase() + "' AND commande.suivi = 'PARTIELLEMENT_EXP' AND client.pays = '" + pays.toUpperCase() + "' ORDER BY commande.dernier_traitement DESC;");
	}
	public ResultSet douaneListerProduitInCommande(String ID_commande) throws SQLException{
		return st.executeQuery("SELECT ID_produit FROM commande_contient_produit WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
	public ResultSet douanePrixPalette(String ID_palette) throws SQLException{
		return st.executeQuery("SELECT sum(coutPalette) AS prix_palette FROM (SELECT ID_produit, (produit.cout*SUM(carton.quantite_produit)) AS coutPalette FROM carton NATURAL JOIN produit WHERE carton.ID_palette = '" + ID_palette.toUpperCase() + "' GROUP BY ID_produit, produit.cout) AS prix_palette;");
	}
	public ResultSet douanePrixCommande(String ID_commande) throws SQLException{
		return st.executeQuery("SELECT SUM(cout) as prix_commande FROM commande_contient_produit NATURAL JOIN produit WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
	public ResultSet douaneRechercherCommandeParIDCommande(String ID_commande) throws SQLException {
		return st.executeQuery("SELECT ID_commande, ID_client, qualifiant, dernier_traitement FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"';");
	}
	public ResultSet douaneRechercherCommandeParIDProduit(String ID_produit) throws SQLException {
		return st.executeQuery("SELECT ID_commande, quantite FROM commande_contient_produit WHERE ID_produit='"+ID_produit.toUpperCase()+"';");
	}
	public ResultSet douaneRechercherCommandeParDestination(String dest) throws SQLException {
		return st.executeQuery("SELECT ID_commande, ID_client FROM commande NATURAL JOIN client WHERE pays='"+dest.toUpperCase()+"';");
	}
	public ResultSet douaneDetailCommande(String rep) throws SQLException {
		return st.executeQuery("SELECT ID_produit, quantite, qualifiant FROM commande_contient_produit NATURAL JOIN produit WHERE ID_commande='"+rep+"';");
	}
	public void douaneUpdateControleCommande(String ID_commande, String controle)throws SQLException {
		st.execute("UPDATE controleCommande SET controle_douane = '" + controle.toUpperCase() + "' WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
		st.execute("UPDATE commande SET dernier_traitement = '"+fonc.dateToday()+"', suivi = 'EXPEDIE' WHERE ID_commande = '" + ID_commande.toUpperCase() + "';");
	}
	public ResultSet douaneListerPaletteInCommande(String ID_commande, String pays) throws SQLException {
		return st.executeQuery("SELECT ID_palette, qualifiant FROM palette NATURAL JOIN commande NATURAL JOIN client WHERE ID_commande='"+ID_commande+"' AND commande.suivi = 'PARTIELLEMENT_EXP' AND client.pays = '" + pays + "' ORDER BY commande.dernier_traitement;");
	}
  
  
	// Transporteur
  
	public ResultSet transporteurAfficherCommande(Date date) throws SQLException{
		return st.executeQuery("SELECT ID_commande, qualifiant, dernier_traitement, date_livraison FROM commande WHERE suivi = 'PARTIELLEMENT_EXP' AND dernier_traitement < '" + date + "' ORDER BY date_livraison ASC;");
	}
	public ResultSet transporteurAfficherCommande(String ID_transporteur, Date date) throws SQLException{
		return st.executeQuery("SELECT ID_commande, qualifiant, dernier_traitement, date_livraison FROM commande WHERE suivi = 'PARTIELLEMENT_EXP' AND ID_transporteur = '" + ID_transporteur.toUpperCase() + "' AND dernier_traitement < '" + date + "' ORDER BY date_livraison ASC;");
	}
	public void transporteurUpdateDate(String ID_commande, Date date) throws SQLException {
		st.execute("UPDATE commande SET dernier_traitement = '" + date + "' WHERE ID_commande = '" + ID_commande.toUpperCase() +"';" );
	}
	public ResultSet transporteurRecupID(String nom) throws SQLException{
		return st.executeQuery("SELECT ID_transporteur FROM transporteur WHERE nom = '" + nom.toUpperCase() + "' AND licencie = '0';");
	}
  
  
	// Gerant
  
	public void gerantModifierPrix(String ID_produit, int prix) throws SQLException{
		st.execute("UPDATE produit SET cout = '" + prix + "' WHERE ID_produit = '" + ID_produit.toUpperCase() + "';");
	}
  
	public void gerantModifierStock(String ID_produit, int stock) throws SQLException{
		st.execute("UPDATE produit SET reserve = '" + stock + "' WHERE ID_produit = '" + ID_produit.toUpperCase() + "';");
	}
  
	public void gerantLicencierEmballeur(String ID_emballeur) throws SQLException{
		st.execute("UPDATE emballeur SET licencie = '1' WHERE ID_emballeur = '" + ID_emballeur.toUpperCase() + "';");
	}
  
	public void gerantLicencierTransporteur(String ID_transporteur) throws SQLException{
		st.execute("UPDATE transporteur SET licencie = '1' WHERE ID_transporteur = '" + ID_transporteur.toUpperCase() + "';");
	}
  
	public ResultSet gerantConsulterEmballeur() throws SQLException{
		return st.executeQuery("SELECT ID_emballeur, nom, prenom, taux_erreur FROM emballeur WHERE licencie = '0';");
	}
  
	public ResultSet gerantConsulterTransporteur() throws SQLException{
		return st.executeQuery("SELECT ID_transporteur, nom FROM transporteur WHERE licencie = '0';");
	}
  
	public ResultSet gerantConsulterClient() throws SQLException{
		return st.executeQuery("SELECT ID_client, nom_societe, suffixe_societe, adresse, ville, code_postal, pays, telephone FROM client;");
	}
  
	public ResultSet gerantNombreCommande() throws SQLException{
		return st.executeQuery("SELECT dernier_traitement, emballeur.nom, COUNT(commande.ID_emballeur)AS nombre_commande FROM emballeur NATURAL JOIN commande GROUP BY emballeur.nom, dernier_traitement ORDER BY dernier_traitement, nombre_commande DESC;");
	}
  
	public ResultSet gerantProduitsPlusVendus() throws SQLException{
		return st.executeQuery("SELECT ID_produit, SUM(quantite) as quantite_vendue FROM commande_contient_produit GROUP BY(ID_produit) ORDER BY(quantite_vendue) DESC;");
	}
  
	public ResultSet gerantClientPlusDepensier() throws SQLException{
		return st.executeQuery("SELECT client.ID_client, client.nom_societe, SUM(commande.prix) AS somme_depense FROM client, commande WHERE client.ID_client=commande.ID_client GROUP BY client.ID_client, client.nom_societe ORDER BY somme_depense DESC;");
	}
  
	public void gerantEmbaucherEmballeur(String ID_emballeur, String nom_emballeur, String prenom_emballeur, int taux_erreur, String mdp, String licencie) throws SQLException{
		st.execute("INSERT INTO emballeur VALUES ('" + ID_emballeur.toUpperCase() + "', '" + nom_emballeur.toUpperCase() + "', '" + prenom_emballeur.toUpperCase() + "', '" + taux_erreur + "', '" + mdp+ "', '" + licencie + "');");
	}
  
	public void gerantEmbaucherTransporteur(String ID_transporteur, String nom_transporteur, String mdp, String licencie) throws SQLException{
		st.execute("INSERT INTO transporteur VALUES ('" + ID_transporteur.toUpperCase() + "', '" + nom_transporteur.toUpperCase() + "', '" + mdp + "', '" + licencie + "');");
	}
  
	public ResultSet gerantListerProduits() throws SQLException{
		return st.executeQuery("SELECT ID_produit, description, reserve FROM produit;");
	}
  
	public void gerantReattribuerEmballeur(String ID_nouvel_emballeur, String ID_ancien_emballeur) throws SQLException {
		st.execute("UPDATE commande SET ID_emballeur = '" + ID_nouvel_emballeur.toUpperCase() + "' WHERE ID_emballeur = '" + ID_ancien_emballeur.toUpperCase() + "' AND suivi = 'NON_EXP';");
	}
  
	public void gerantReattribuerTransporteur(String ID_nouveau_transporteur, String ID_ancien_transporteur) throws SQLException {
		st.execute("UPDATE commande SET ID_transporteur = '" + ID_nouveau_transporteur.toUpperCase() + "' WHERE ID_transporteur = '" + ID_ancien_transporteur.toUpperCase() + "' AND suivi = 'NON_EXP';");
	}
  
  
	// Suppression des tables
  
	public void suppressionClient() throws SQLException{
		st.execute("DROP TABLE client CASCADE;");
	}
  
	public void suppressionProduit() throws SQLException{
		st.execute("DROP TABLE produit CASCADE;");
	}
  
	public void suppressionTransporteur() throws SQLException{
		st.execute("DROP TABLE transporteur CASCADE;");
	}
  
	public void suppressionDouane() throws SQLException{
		st.execute("DROP TABLE douane CASCADE;");
	}
  
	public void supressionEmballeur() throws SQLException{
		st.execute("DROP TABLE emballeur CASCADE;");
	}
  
	public void suppressionGerant() throws SQLException{
		st.execute("DROP TABLE gerant CASCADE;");
	}
  
	public void suppressionPalette() throws SQLException{
		st.execute("DROP TABLE palette CASCADE;");
	}
  
	public void suppressionConteneur() throws SQLException{
		st.execute("DROP TABLE conteneur CASCADE;");
	}
  
	public void suppressionCommande() throws SQLException{
		st.execute("DROP TABLE commande CASCADE;");
	}
  
	public void suppressionControleCommande() throws SQLException {
		st.execute("DROP TABLE controleCommande CASCADE;");
	}
  
	public void suppressionCartonProduit() throws SQLException{
		st.execute("DROP TABLE carton_contient_produit CASCADE;");
	}
	public void suppressionCarton() throws SQLException {
		st.execute("DROP TABLE carton CASCADE;");
	}
	public void suppressionTable(String table) throws SQLException{
		st.execute("DROP TABLE" + table + " CASCADE;");
	}
  
	public void suppressionCarton_Contient_Produit() throws SQLException {
		st.execute("DROP TABLE carton_contient_produit CASCADE;");
	}
	public void suppressionCommande_Contient_Produit() throws SQLException {
		st.execute("DROP TABLE commande_contient_produit CASCADE;");
	}
  
  
  
	// Ajout de donnees en fonction du fichier csv
	// Emb, cli, prod, trans, douane, gerant
  
	public void ajouterDonneesEmballeur(String ID_emballeur, String nom, String prenom, String taux_erreur, String mdp, String licencie) throws SQLException{
		st.execute("INSERT INTO emballeur VALUES ('" + ID_emballeur.toUpperCase() + "', '" + nom.toUpperCase() + "', '" + prenom.toUpperCase() + "', '"+ taux_erreur + "', '" + mdp + "', '" + licencie + "');");
	}
  
	public void ajouterDonneesClient(String ID_client, String nom_soc, String suffixe_soc, String adresse, String ville, String code_postal, String pays, String telephone, String mdp) throws SQLException{
		st.execute("INSERT INTO client VALUES ('" + ID_client.toUpperCase() + "', '" + nom_soc.toUpperCase() + "', '" + suffixe_soc.toUpperCase() + "', '" + adresse.toUpperCase() + "', '" + ville.toUpperCase() + "', '" + code_postal.toUpperCase() + "', '" + pays.toUpperCase() + "', '" + telephone + "', '" + mdp + "');");
	}
  
	public void ajouterDonneesProduit(String ID_produit, String description, String qte_par_carton, String cartons_par_palette, String qualifiant, String cout, String taux_augmentation, String poids, String reserve) throws SQLException{
		st.execute("INSERT INTO produit VALUES ('" + ID_produit.toUpperCase() + "', '" + description.toUpperCase() + "', '" + qte_par_carton + "', '" + cartons_par_palette + "', '" + qualifiant.toUpperCase() + "', '" + cout + "', '" + taux_augmentation + "', '" + poids + "', '" + reserve+ "');");
	}
  
	public void ajouterDonneesTransporteur(String ID_transporteur, String nom, String mdp, String licencie) throws SQLException{
		st.execute("INSERT INTO transporteur VALUES ('" + ID_transporteur.toUpperCase() + "', '" + nom.toUpperCase() + "', '" + mdp+ "', '" + licencie +"');");
	}
  
	public void ajouterDonneesDouane(String pays, String taux_verifie, String login, String mdp) throws SQLException{
		st.execute("INSERT INTO douane VALUES ('" + pays.toUpperCase() + "', '" + taux_verifie + "', '" + login.toUpperCase() + "', '" + mdp+ "');");
	}
  
	public void ajouterDonneesGerant(String prenom, String nom, String login, String mdp) throws SQLException{
		st.execute("INSERT INTO gerant VALUES ('" + prenom.toUpperCase() + "', '" + nom.toUpperCase() + "', '" + login.toUpperCase() + "', '" + mdp+ "');");
	}
  
  
	// Retourne les IDs des clients
	public ResultSet clientID() throws SQLException{
		return st.executeQuery("SELECT ID_client FROM client;");
	}
  
	// Retourne le nombre de clients
	public ResultSet countClient() throws SQLException{
		return st.executeQuery("SELECT count(ID_client) FROM client;");
	}
  
	// Retourne le nombre de produits
	public ResultSet countProduit() throws SQLException{
		return st.executeQuery("SELECT count(ID_produit) FROM produit;");
	}
  
	// Retourne les informations sur un produit
	public ResultSet produitInfo() throws SQLException{
		return st.executeQuery("SELECT ID_produit, cout, reserve, qualifiant FROM produit;");
	}
  
	// Retourne le nombre d'emballeurs
	public ResultSet countEmballeur() throws SQLException {
		return st.executeQuery("SELECT count(ID_emballeur) FROM emballeur WHERE licencie = '0';");
	}
  
	// Retourne les IDs des emballeurs qui n'ont pas encore emballe quoique ce soit
	public ResultSet emballeurID() throws SQLException {
		return st.executeQuery("SELECT ID_emballeur FROM emballeur WHERE licencie = '0' EXCEPT SELECT ID_emballeur FROM commande;");
	}
  
	public ResultSet transporteurID() throws SQLException {
		return st.executeQuery("SELECT ID_transporteur FROM transporteur WHERE licencie = '0' EXCEPT SELECT ID_transporteur FROM commande;");
	}
  
	// Retourne l'ID du premier emballeur qui n'a jamais travaille trouve
	public String verifEmballeurNonUtilise() throws SQLException{
		ResultSet rs = emballeurID();
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}		
		return null;
    
	}
  
	// Retourne le premier ID d'emballeur trouve
	public String IDEmballeur() throws SQLException{
		String rep = verifEmballeurNonUtilise();
		if(rep != null)
			return rep;
		else{
			ResultSet rs = st.executeQuery("SELECT ID_emballeur, COUNT(ID_emballeur) AS nb FROM commande GROUP BY(ID_emballeur) ORDER BY nb ASC ;");
			while(rs.next()){
				rep = rs.getObject(1).toString();
				rs.close();
				return rep;
			}
			return rep;
		}
	}
  
	// Retourne le premier ID d'emballeur trouve, sauf s'il s'agit de celui passe en parametre
	public String IDEmballeurSauf(String ID_emballeur) throws SQLException{
		String rep = verifEmballeurNonUtilise();
		if(rep != null)
			return rep;
		else{
			ResultSet rs = st.executeQuery("SELECT ID_emballeur, COUNT(ID_emballeur) AS nb FROM commande GROUP BY(ID_emballeur) ORDER BY nb ASC ;");
			while(rs.next()){
				rep = rs.getObject(1).toString();
				if (!rep.equals(ID_emballeur)){
					rs.close();
					return rep;
				}
				else
					continue;
			}
			return rep;
		}
	}
  
	// Retourne l'ID du premier transporteur trouve qui n'a jamais travaille
	public String verifTransporteurNonUtilise() throws SQLException{
		ResultSet rs = transporteurID();
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}		
		return null;
    
	}
  
	// Retourne le premier ID de transporteur trouve
	public String IDtransporteur() throws SQLException{
		String rep = verifTransporteurNonUtilise();
		if(rep != null)
			return rep;
		else{
			ResultSet rs = st.executeQuery("SELECT ID_transporteur, COUNT(ID_transporteur) AS nb FROM commande GROUP BY(ID_transporteur) ORDER BY nb ASC ;");
			while(rs.next()){
				rep = rs.getObject(1).toString();
				rs.close();
				return rep;
			}
			return rep;
		}
	}
	
	// Retourne le premier ID de transporteur trouve, sauf s'il s'agit de celui passe en parametre
	public String IDtransporteurSauf(String ID_transporteur) throws SQLException{
		String rep = verifTransporteurNonUtilise();
		if(rep != null)
			return rep;
		else{
			ResultSet rs = st.executeQuery("SELECT ID_transporteur, COUNT(ID_transporteur) AS nb FROM commande GROUP BY(ID_transporteur) ORDER BY nb ASC ;");
			while(rs.next()){
				rep = rs.getObject(1).toString();
				if (!rep.equals(ID_transporteur)){
					rs.close();
					return rep;
				}
				else
					continue;
			}
			return rep;
		}
	}
  
	// Ajout de donnees dans la table commande_contient_produit
	public void remplirCommandeContientProduit(String ID_commande, String ID_produit, String quantite) throws SQLException {
		st.execute("INSERT INTO commande_contient_produit VALUES ('"+ID_commande.toUpperCase()+"', '"+ID_produit.toUpperCase()+"', '"+quantite+"');");
	}
  
	// Ajout de donnees dans la table commande
	public void remplirCommande(String ID_commande, String ID_client, String qualifiant, String suivi, int prix, Date dernier_traitement, Date date_livraison, String ID_emballeur, String ID_transporteur) throws SQLException {
		st.execute("INSERT INTO commande VALUES ('"+ID_commande.toUpperCase()+"', '"+ID_client.toUpperCase()+"', '"+qualifiant.toUpperCase()+"', '"+suivi.toUpperCase()+"', '"+prix+"', '"+dernier_traitement+"', '"+date_livraison+"', '"+ID_emballeur.toUpperCase()+"', '" + ID_transporteur.toUpperCase() + "');");
	}
  
	// Ajout de donnees dans la table controleCommande
	public void remplirControleCommande(String ID_commande, String ID_douane, String controle) throws SQLException{
		st.execute("INSERT INTO controleCommande VALUES ('"+ID_commande.toUpperCase()+"', '"+ID_douane.toUpperCase()+"', '"+controle+"');");
	}
  
	// Mise a jour de la reserve d'un produit
	public void updateReserveProduit(int newReserve, String ID_produit) throws SQLException {
		st.execute("UPDATE produit SET reserve = '" + newReserve + "' WHERE ID_produit = '" + ID_produit.toUpperCase() + "';");
    
	}
  
	// Renvoie true si l'ID du produit existe, false sinon
	public boolean verifIDProduit(String ID_produit) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_produit FROM produit WHERE ID_produit = '" + ID_produit.toUpperCase() + "';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si le produit existe dans la quentite demandee, false sinon
	public boolean verif_quantite(String ID_produit, int quantite) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT reserve FROM produit WHERE ID_produit = '" + ID_produit.toUpperCase() + "' AND reserve >= '" +quantite +"';");
		while(rs.next()){
			if(quantite <= Integer.parseInt(rs.getObject(1).toString())){
				rs.close();
				return true;
			}
			return false;
		}
		return false;
	}
  
	// Renvoie le qualifiant du produit dont l'ID est donne en parametre
	public String recupQualifiant(String ID_produit) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT qualifiant FROM produit WHERE ID_produit='"+ID_produit.toUpperCase()+"';");
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return "";
	}
  
	// Renvoie l'ID de douanier correspondant au pays envoye en parametre
	public String recupDouane(String pays) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT login FROM douane WHERE pays='"+ pays.toUpperCase() +"';");
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return "";
	}
  
	// Recuperation du prix d'un produit
	public String recupPrix(String ID_produit) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT cout FROM produit WHERE ID_produit='"+ID_produit.toUpperCase()+"';");
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return "";
	}
  
	// Recuperation de la reserve disponible pour un produit
	public int recupReserve(String ID_produit) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT reserve FROM produit WHERE ID_produit='"+ID_produit.toUpperCase()+"';");
		int rep;
		while(rs.next()){
			rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return 0;
	}
  
	// Renvoie true si l'ID commande existe, false sinon
	public boolean verifIdCommande(String ID_commande) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_commande FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si l'ID commande existe ET que la commande est en cours d'expedition, false sinon
	public boolean verifIdCommandeRefuse(String ID_commande) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_commande FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"' AND suivi = 'PARTIELLEMENT_EXP';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si l'ID palette existe, false sinon
	public boolean verifIdPalette(String ID_palette) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_palette FROM palette WHERE ID_palette='"+ID_palette.toUpperCase()+"';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si l'ID conteneur existe, false sinon
	public boolean verifIdConteneur(String ID_conteneur) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_conteneur FROM conteneur WHERE ID_conteneur='"+ID_conteneur.toUpperCase()+"';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si l'ID emballeur existe, false sinon
	public boolean verifIdEmballeur(String ID_emballeur) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_emballeur FROM emballeur WHERE ID_emballeur='"+ID_emballeur.toUpperCase()+"' AND licencie = '0';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Renvoie true si l'ID transporteur existe, false sinon
	public boolean verifIdTransporteur(String ID_transporteur) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT ID_transporteur FROM transporteur WHERE ID_transporteur='"+ID_transporteur.toUpperCase()+"' AND licencie = '0';");
		while(rs.next()){
			rs.close();
			return true;
		}
		return false;
	}
  
	// Retourne true si le commande dont l'ID est passe en paremetre n'a pas encore ete expedie, false sinon
	public boolean verifEtatCommande(String ID_commande) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT suivi FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"';");
		while(rs.next()){
			if(rs.getObject(1).toString().equals("NON_EXP")){
				rs.close();
				return true;
			}
		}
		return false;
	}
  
	// Retourne l'ID de tous les commande et leur qualifiant pour la douane concernee
	public ResultSet douaneListerCommandeExpedie(String login) throws SQLException {
		return st.executeQuery("SELECT commande.ID_commande, commande.qualifiant, commande.dernier_traitement, commande.suivi FROM commande, douane, client WHERE commande.ID_client=client.ID_client AND douane.login='"+login.toUpperCase()+"' AND client.pays=douane.pays AND (suivi = 'PARTIELLEMENT_EXP' OR suivi = 'EXPEDIE') ORDER BY commande.dernier_traitement;");	
	}
  
	// Retourne l'ID de tous les commande et leur qualifiant pour la douane concernee
	public ResultSet douaneListerCommandeExpedie2(String login) throws SQLException {
		return st.executeQuery("SELECT commande.ID_commande, commande.qualifiant FROM commande, douane, client WHERE commande.ID_client=client.ID_client AND douane.login='"+login.toUpperCase()+"' AND client.pays=douane.pays AND suivi = 'PARTIELLEMENT_EXP' ORDER BY commande.dernier_traitement;");	
	}
  
	// Retourne l'ID des commande controles et leur qualifiant pour la douane concernee
	public ResultSet douaneListerCommandeControle(String login) throws SQLException {
		return st.executeQuery("SELECT commande.ID_commande, commande.qualifiant FROM commande, douane, client, controleCommande WHERE commande.ID_client=client.ID_client AND douane.login='"+login.toUpperCase()+"' AND client.pays=douane.pays AND controleCommande.ID_commande=commande.ID_commande AND controleCommande.controle_douane='CONTROLE';");	
	}
  
	// Retourne l'ID des commande non-controles et leur qualifiant pour la douane concernee
	public ResultSet douaneListerCommandeNonControle(String login) throws SQLException {
		return st.executeQuery("SELECT commande.ID_commande, commande.qualifiant FROM commande, douane, client, controleCommande WHERE commande.ID_client=client.ID_client AND douane.login='"+login.toUpperCase()+"' AND client.pays=douane.pays AND controleCommande.ID_commande=commande.ID_commande AND controleCommande.controle_douane='NON_CONTROLE';");	
	}
  
	//TODO faux!
	public ResultSet douaneStatistique(String login) throws SQLException{
		return st.executeQuery("SELECT commande.dernier_traitement, (COUNT(c1.douanier)*100) FROM commande, douane, client, controleCommande AS c1, controleCommande AS c2 WHERE commande.ID_commande=c1.ID_commande AND commande.ID_commande=c2.ID_commande AND douane.login='"+login.toUpperCase()+"' AND douane.login=c1.douanier AND douane.pays=client.pays AND c1.controle_douane='NON_EXP' AND c2.controle_douane!='NON_EXP' GROUP BY(commande.dernier_traitement);");
	}
  
	// Retourne l'ID de la douane correspondant a l'ID client envoye
	public ResultSet IDdouane(String ID_client)throws SQLException {
		return st.executeQuery("SELECT douane.login FROM douane, client WHERE douane.pays='"+ID_client.toUpperCase()+".pays';");
	}
  
	// Retourne les IDs des emballeurs qui n'ont pas encore emballe quoique ce soit
	public ResultSet douanierNonUtilise(String ID_client) throws SQLException {
		return st.executeQuery("SELECT douane.login FROM douane, client WHERE douane.pays=client.pays AND ID_client='"+ID_client.toUpperCase()+"' EXCEPT SELECT douanier FROM controleCommande;");
	}
  
	// Retourne le nombre de douaniers
	public int nbDouane(String ID_client)throws SQLException {
		ResultSet rs = st.executeQuery("SELECT count(douane.login) FROM douane, client WHERE douane.pays='"+ID_client.toUpperCase()+".pays';");
		while(rs.next()){
			int rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return -1;
	}
  
	// Retourne l'ID du premier douanier de la liste des douaniers non utilises
	public String verifDouanierNonUtilise(String ID_client) throws SQLException{
		ResultSet rs = douanierNonUtilise(ID_client);
		String rep;
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}		
		return null;
    
	}
  
	public String douanierSelectionne(String ID_client) throws SQLException{
		String rep = verifDouanierNonUtilise(ID_client);
		if(rep != null)
			return rep;
		else{
			ResultSet rs = st.executeQuery("SELECT douanier, COUNT(douanier) AS nb FROM controleCommande GROUP BY(douanier) ORDER BY nb ASC ;");
			while(rs.next()){
				rep = rs.getObject(1).toString();
				rs.close();
				return rep;
			}
			return rep;
		}
	}
  
	// Renvoie le taux d'erreur d'un emballeur
	public int recupTauxEmballeur(String ID_emballeur) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT taux_erreur FROM emballeur WHERE ID_emballeur='"+ID_emballeur.toUpperCase()+"';");
		int rep;
		while(rs.next()){
			rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return 0;
	}
  
	// Retourne le taux de commande verifies pour un ID de douane donne
	public int recupTauxDouane(String ID_douane) throws SQLException {
		ResultSet rs = st.executeQuery("SELECT taux_commande_verifies FROM douane WHERE login='"+ID_douane.toUpperCase()+"';");
		int rep;
		while(rs.next()){
			rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return 0;
	}
  
	// Recupere l'ID du client auquel le commande dont l'ID est passe en parametre doit etre livre
	public String commandeRecupClient(String ID_commande) throws SQLException {
		String rep = "";
		ResultSet rs = st.executeQuery("SELECT ID_client FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"';");
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return rep;
	}
  
	// Recuperation de l'ID de l'emballeur qui a emballe le commande dont l'ID est passe en parametre
	public String commandeRecupEmballeur(String ID_commande) throws SQLException {
		String rep = "";
		ResultSet rs = st.executeQuery("SELECT ID_emballeur FROM commande WHERE ID_commande='"+ID_commande.toUpperCase()+"';");
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return rep;
	}
  
	// Retourne la nombre de produits d'un ID donne qu'on peut mettre dans un carton
	public int produitQteParCarton(String ID_produit) throws SQLException{
		int rep = -1;
		ResultSet rs = st.executeQuery("SELECT quantite_par_carton FROM produit WHERE ID_produit='" + ID_produit.toUpperCase() + "';");
		while(rs.next()){
			rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return rep;
	}
  
	// Retourne le nombre de cartons qu'on peut mettre par palette
	public int produitCartonsParPalette(String ID_produit) throws SQLException{
		int rep = -1;
		ResultSet rs = st.executeQuery("SELECT cartons_par_palette FROM produit WHERE ID_produit='" + ID_produit.toUpperCase() + "';");
		while(rs.next()){
			rep = Integer.parseInt(rs.getObject(1).toString());
			rs.close();
			return rep;
		}
		return rep;
	}
  
	// Retourne les commandes qui n'ont pas encore ete traitees a la date donnee en parametre
	public ResultSet commandeNonTraitee(Date date) throws SQLException{
		return st.executeQuery("SELECT ID_commande, ID_client, qualifiant, pays AS destination, ID_emballeur FROM commande NATURAL JOIN client WHERE dernier_traitement <= '" + date + "' AND suivi = 'NON_EXP';");
	}
  
	public void remplirCartons(String ID_carton, String ID_commande, String ID_produit, int quantite, String Qualifiant, String ID_palette, String destination) throws SQLException{
		st.execute("INSERT INTO carton VALUES('" + ID_carton.toUpperCase() + "', '" + ID_commande.toUpperCase() + "', '" + ID_produit.toUpperCase() +"', '" + quantite +"', '" + Qualifiant.toUpperCase() +"', '" +ID_palette.toUpperCase()+"', '"+destination.toUpperCase()+ "');");
	}
  
	public void remplirPalette(String ID_palette, String ID_commande, String destination, String qualifiant, Date date, Date d2) throws SQLException{
		st.execute("INSERT INTO palette VALUES('" + ID_palette.toUpperCase() + "', '" + ID_commande.toUpperCase() + "', '" + destination.toUpperCase() + "', '" + qualifiant.toUpperCase() +"', '" + date +"', '" + d2 +"');");
	}
  
  
	public String recupPaysClient(String ID_client) throws SQLException {
		String rep = "";
		ResultSet rs = st.executeQuery("SELECT pays FROM client WHERE ID_client='"+ID_client.toUpperCase()+"';");
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return rep;
	}
  
	public String recupPaysDouane(String ID_douane) throws SQLException {
		String rep = "";
		ResultSet rs = st.executeQuery("SELECT pays FROM douane WHERE login='"+ID_douane.toUpperCase()+"';");
		while(rs.next()){
			rep = rs.getObject(1).toString();
			rs.close();
			return rep;
		}
		return rep;
	}
  
	public void nouveauClient(String ID_client, String nom_societe, String suffixe_societe, String adresse, String ville, String code_postal, String pays, String telephone, String mdp) throws SQLException {
		st.execute("INSERT INTO client VALUES('" + ID_client.toUpperCase() + "', '" + nom_societe.toUpperCase() + "', '" + suffixe_societe.toUpperCase() + "', '" + adresse.toUpperCase() + "', '" + ville.toUpperCase() + "', '" + code_postal.toUpperCase() + "', '" + pays.toUpperCase() + "', '" + telephone.toUpperCase() + "', '" + mdp + "');");
	}
  
  public ResultSet pays() throws SQLException{
    return st.executeQuery("SELECT pays FROM douane;");
  }
  
  public int nbPays()throws SQLException {
    ResultSet rs = st.executeQuery("SELECT count(pays) FROM douane;");
    while(rs.next()){
      int rep = Integer.parseInt(rs.getObject(1).toString());
      rs.close();
      return rep;
    }
    return -1;
  }
  
  
}	 