package src;

import java.awt.peer.SystemTrayPeer;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */

	private int idFonction = -1 ;
	private Scanner sc  ;
	DashDouane dashD ;
	DashGerant dashG ;
	DashClients dashC;
	DashEmballeur dashE ;
	private String idLogin = "" ;
	private String passUser = "" ;
	private int choice  ;
	private String choix = "" ;


	public Main() throws SQLException{
		stepOne();
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		new Main();
	}

	private void stepOne() throws SQLException {
		sc = new Scanner(System.in) ;
		System.out.println("Veuillez choisir votre fonction ? " );
		System.out.println("----------------------------------" );
		System.out.println("0 : Vous etes Douane ? " );
		System.out.println("1 : Vous etes Gerant ? " );
		System.out.println("2 : Vous etes Client ? " );
		System.out.println("3 : Vous etes Emballeur ? " );
		System.out.println("4 : Vous etes Transporteur ? " );
		System.out.println("9 : Pour Quitter l'application  " );
		System.out.println("----------------------------------" );
		idFonction = sc.nextInt();
		switch (idFonction) {
		case 0:
			stepDouane("");
			break;
		case 1:
			stepGerant("");
			break;
		case 2:
			stepClient("");
			break;
		case 3:
			stepEmballeur("");
			break;
		case 4:
			stepTransporteur("");
			break;
		case 9:
			quitterApplication();
			break;
		default:stepOne();
		break;
		}
	}

	private void quitterApplication() {
		System.exit(0);
	}

	private void quitter() throws SQLException {
		stepOne();
	}

	private void stepTransporteur(String err) throws SQLException {
		if (!err.equalsIgnoreCase("")) System.err.println(err);
		sc = new Scanner(System.in) ;
		System.out.println("Veuillez saisair votre login " );
		idLogin = sc.nextLine();

		while (idLogin.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre login (Obligatoire) !! " );
			idLogin= sc.nextLine();
		}

		System.out.println("Veuillez saisir votre mot de passe ? " );
		passUser = sc .nextLine();

		while (passUser.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre mot de passe (Obligatoire)  !! " );
			passUser= sc.nextLine();
		}

		DashDouane dashD = new DashDouane();
		if(dashD.checkUser(idLogin, passUser , "transporteur")){
			System.out.println("Bienvenue Login :"+idLogin  );
			stepsTransporteurChoice(idLogin);
		}else{
			stepTransporteur("ERREUR :  IDENTIFIENTS Transporteur INCORRECT");
		}
	}

	private void stepsTransporteurChoice(String idLogin) throws SQLException {
		System.out.println("Vous souhaitez : ? " );
		System.out.println("0 : Voir les colis , leurs etat et la date de livraison souhaitez par le client  " );
		System.out.println("9 : Quitter  " );
		System.out.println("----------------------------------------------------");
		checkChoiceTransporteur(idLogin);
	}

	private void checkChoiceTransporteur(String id) throws SQLException {
		dashE = new DashEmballeur();
		sc = new Scanner(System.in);
		choice = sc.nextInt() ;
		switch (choice) {
		case 0:
			dashE.getEtatsColis();
			break;
		case 9:
			stepOne();
			break;
		default:
			break;
		}
		
	}

	private void stepEmballeur(String err) throws SQLException {
		if (!err.equalsIgnoreCase("")) System.err.println(err);
		sc = new Scanner(System.in) ;
		System.out.println("Veuillez saisair votre login " );
		idLogin = sc.nextLine();

		while (idLogin.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre login (Obligatoire) !! " );
			idLogin= sc.nextLine();
		}

		System.out.println("Veuillez saisir votre mot de passe ? " );
		passUser = sc .nextLine();

		while (passUser.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre mot de passe (Obligatoire)  !! " );
			passUser= sc.nextLine();
		}

		DashDouane dashD = new DashDouane();
		if(dashD.checkUser(idLogin, passUser , "emballeur")){
			System.out.println("Bienvenue Login :"+idLogin  );
			stepsEmballeurChoice(idLogin);
		}else{
			stepEmballeur("ERREUR :  IDENTIFIENTS Emballeur INCORRECT");
		}

	}

	private void stepsEmballeurChoice(String idLogin) throws SQLException {
		System.out.println("Vous souhaitez : ? " );
		System.out.println("0 : Voir la Listes des commandes d\'un client  " );;
		System.out.println("9 : Quitter  " );
		System.out.println("----------------------------------------------------");
		checkChoiceEmballeur(idLogin);
	}

	private void checkChoiceEmballeur(String id) throws SQLException {
		dashE = new DashEmballeur();
		sc = new Scanner(System.in);
		choice = sc.nextInt() ;
		switch (choice) {
		case 0:
			System.out.println("Tapper le numero du client por affciher les commandes ");
			sc = new Scanner(System.in);
			String idc = sc.nextLine();
			dashE.getListesCommandes(idc);
			break;
		case 9:
			stepOne();
			break;
		default:
			break;
		}

	}

	private void stepClient(String err) throws SQLException {
		System.out.println("----------------Connexion INTERFACE Client------------------" );
		if (!err.equalsIgnoreCase("")) System.err.println(err);

		sc = new Scanner(System.in) ;
		System.out.println("Veuillez saisair votre login " );
		idLogin = sc.nextLine();

		while (idLogin.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre login (Obligatoire) !! " );
			idLogin= sc.nextLine();
		}

		System.out.println("Veuillez saisir votre mot de passe ? " );
		passUser = sc .nextLine();

		while (passUser.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre mot de passe (Obligatoire)  !! " );
			passUser= sc.nextLine();
		}
		DashDouane dashD = new DashDouane();
		if(dashD.checkUser(idLogin, passUser , "client")){
			System.out.println("Bienvenue Login :"+idLogin  );
			stepsClientChoice(idLogin);
		}else{
			stepClient("ERREUR : IDENTIFIENTS Clients INCORRECT");
		}

	}

	private void stepsClientChoice(String idLogin) throws SQLException {
		System.out.println("Vous souhaitez : ? " );
		System.out.println("0 : Voir la Listes des produits disponibles ? " );
		System.out.println("1 : Savoir etat de votre colis ? " );
		System.out.println("2 : Passer une Commande et choisir une date livraison ? " );
		System.out.println("3 : Changer mes identifiants (Login/mot_de_passe) ? " );
		System.out.println("9 : Quitter  " );
		System.out.println("----------------------------------------------------");
		sc = new Scanner(System.in);
		choice = sc.nextInt() ;
		checkChoiceClient(choice);

	}

	private void checkChoiceClient(int idx) throws SQLException {
		dashC = new DashClients();
		switch (idx) {
		/**
		 * TODO
		 */
		case 0:
			getListesProduitsDispo();
			break;
		case 1:
			getEtatsColis(idLogin);
			break;
		case 2:
			passerUneCommande(idLogin);
			break;
		case 3:
			changeIdentiant(idLogin);
			break;
		case 4:
			getListesProduitsDispo();
			break;
		case 9:
			stepOne();
			break;
		default:
			break;
		}

	}

	private void changeIdentiant(String id) throws SQLException {
		String l , m = "";
		sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nouveau login !");
		l = sc.nextLine() ;
		System.out.println("Veuillez saisir le nouveau mote de passe !");
		m = sc.nextLine() ;
		if (l.equalsIgnoreCase("") || m.equalsIgnoreCase("")){
			System.out.println("Veuillez saisir votre login et/ou votre mot de passe :");
		}else {
			dashC.changeIdentifiant(dashC.getIdClient(id), l, m);
			System.out.println("Identifiants changer ");
			stepsClientChoice(id);
		}
	}

	private void passerUneCommande(String client) throws SQLException{
		String prod , pays , date  = "" ;
		int qte = 0 ;
		System.out.println("Veuillez choisir un des produits disponibles :");
		dashC.getListsProduits();
		sc = new Scanner(System.in) ;
		prod = sc.nextLine();
		System.out.println("Veuillez entrer la quantité  :");
		qte = sc.nextInt();
		if (!dashC.checkQteForProduit(prod , qte )) {
			System.err.println("Qantité demandée indisponible !");
			passerUneCommande(client);
		}else {
			System.out.println("Veuillez chosir la date de livraison  :");
			sc = new Scanner(System.in) ;
			date = sc.nextLine();
			System.out.println("Veuillez chosir le pays de destination  :");
			pays  = sc.nextLine();
			try {//doit etre superieur a la date de aujordhui
				dashC.commitCommande(client , prod , qte , date , pays );
			}catch(SQLException e){
				System.out.println("er");
			}
		}
		System.out.println("La commande a bien ete enregistrer");
		stepsClientChoice(client);
	}


	private void getEtatsColis(String idClient) throws SQLException {
		dashC.getEtatsColis(dashC.getIdClient(idClient));
	}

	private void getListesProduitsDispo() throws SQLException {
		dashC.getListsProduits();
		goToPrevoiusEtat('c');
	}

	private void goToPrevoiusEtat(char ide) throws SQLException {
		switch (ide) {
		/**
		 * FIXME
		 */
		case 'c':
			stepClient("");
			break;
		case 'g':
			stepGerant("");
			break;
		case 'p':
			stepGerantChoice(idLogin);
		default:
			break;
		}

	}

	private void stepGerant(String err) throws SQLException {
		if (err != "" ) System.out.println(err);
		System.out.println("----------------Connexion INTERFACE Gerant------------------" );
		sc = new Scanner(System.in) ;
		System.out.println("Veuillez saisair votre login " );
		idLogin = sc.nextLine();

		while (idLogin.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre login (Obligatoire) !! " );
			idLogin= sc.nextLine();
		}

		System.out.println("Veuillez saisir votre mot de passe ? " );
		passUser = sc .nextLine();

		while (passUser.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre mot de passe (Obligatoire)  !! " );
			passUser= sc.nextLine();
		}

		DashDouane dashD = new DashDouane();
		/**
		 * FIXEME
		 */
		if(dashD.checkUser(idLogin, passUser , "gerant")){
			System.out.println("Bienvenue Login :"+idLogin  );
			dashG = new DashGerant();
			stepGerantChoice(idLogin);
		}else{
			stepGerant("Erreur Login et/ou mot de passe est inccorect !");
		}

	}

	private void stepGerantChoice(String idLogin2) throws SQLException {
		String num = dashG.getNomGerant(idLogin , "gerant") ;
		System.out.println("Interface GERANT :# Numero du Gerant "+num);
		System.out.println("----------------------------------------------------");
		System.out.println("Vous souhaitez ? " );
		System.out.println("0 : Changer le prix d'un produit ? " );
		System.out.println("1 : Licencier un personnel ? " );
		/**
		 * TODO Clients Commandé et ceux qui n'ont rien commandé
		 */
		System.out.println("2 : Listes des clients ? " );
		System.out.println("3 : Listes des employes (Transporteur - Emballeur  )? " );
		System.out.println("4 : Listes des colis qu'un emballeur traite/jour  ? " );
		System.out.println("5 : Listes des produits les plus vendus  ? " );
		System.out.println("6 : Listes des clients les plus depensiers  ? " );
		System.out.println("9 : Pour Quitter  " );
		System.out.println("----------------------------------------------------");
		sc = new Scanner(System.in);
		choice = sc.nextInt() ;
		checkChoiceGernat(choice);
	}

	private void checkChoiceGernat(int idx) throws SQLException {
		switch (idx) {
		case 0:
			changePriceProduct();
			break;
		case 1:
			licenicerPersonnel();
			break;
		case 2:
			listesClients();
			break;
		case 3:
			listesEmployes();
			break;
		case 4:
			listesColisEmballeur();
			break;
		case 5:
			listesProduitPlusVendus();
			break;
		case 6:
			listesClientsPlusDepensier();
			break;
		case 9:
			quitter();
			break;
		default:
			break;
		}
	}

	private void listesClientsPlusDepensier() throws SQLException{
		// TODO Auto-generated method stub
		new DashGerant().getClientsPlusDepensiers();
		goToPrevoiusEtat('p');
	}

	private void listesProduitPlusVendus() throws SQLException {
		new DashGerant().getProduitsPlusVendus();
		goToPrevoiusEtat('p');
	}

	private void listesColisEmballeur() throws SQLException {
		String e = "" ;
		System.out.println("Veuillez saisir le Numero de l'emballeur [OU] Taper sur entrer pour afficher le nombre de colis de [tous les emballeur]");
		sc = new Scanner(System.in);
		e = sc.nextLine();
		if(e.equalsIgnoreCase("")) dashG.getNumberColisByEmballeur("");
		else dashG.getNumberColisByEmballeur(e);
		goToPrevoiusEtat('p');
	}

	private void listesEmployes() throws SQLException {
		System.out.println("Nombre Total des Employes #"+dashG.getTotalEmployes()+" \nVous souhaitez afficher la listes des  :");
		System.out.println("0 : Emballeur ?");
		System.out.println("1 : Transporteur ?");
		System.out.println("2 : Quitter !");
		choice = sc.nextInt();
		switch (choice) {
		case 0:
			dashG.getListsEmployesEmballeur();
			listesEmployes();
			break;
		case 1:
			dashG.getListsEmployesTansporteur();
			listesEmployes();
			break;
		case 2:
			goToPrevoiusEtat('p');
			break;
		default:
			break;
		}

	}

	private void listesClients() {
		try {
			dashG.getListsClients();
			goToPrevoiusEtat('p');
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void licenicerPersonnel() {
		// TODO Auto-generated method stub

	}

	private void changePriceProduct() throws SQLException {
		String id , p = "" ;
		id = sc.nextLine() ;
		while (id.equalsIgnoreCase("")) {
			System.out.println("Entrer le l\'ID du produit " );
			id= sc.nextLine();
		}
		System.out.println("Entrer le nouveau prix du Produit " );

		p = sc.nextLine() ;
		while (p.equalsIgnoreCase("")) {
			System.out.println("Entrer le nouveau prix du Produit  " );
			p= sc.nextLine();
		}
		try {
			dashG.changePriceProduct(id , p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		goToPrevoiusEtat('p');
	}



	private void stepDouane(String err) throws SQLException {
		// TODO Auto-generated method stub
		if (err != "" ) System.out.println(err);
		sc = new Scanner(System.in);
		System.out.println("Interface DOUANE");
		System.out.println("-----------------------------------" );
		System.out.println("Veuillez saisir votre login ? " );
		idLogin = sc .nextLine();

		while (idLogin.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre login (Obligatoire) !! " );
			idLogin= sc.nextLine();
		}


		System.out.println("Veuillez saisir votre mot de passe ? " );
		passUser = sc .nextLine();

		while (passUser.equalsIgnoreCase("")) {
			System.out.println("Veuillez saisir votre mot de passe (Obligatoire)  !! " );
			passUser= sc.nextLine();
		}

		DashDouane dashD = new DashDouane();
		if(dashD.checkUser(idLogin, passUser , "douane")){
			System.out.println("Bienvenue Login :"+idLogin  );
			stepDouaneChoice(idLogin);
		}else{
			stepDouane("Vos Coordonnees ne sont pas correct !  ");
		}

	}

	private void stepDouaneChoice(String idLogin) throws SQLException {

		dashD = new DashDouane();
		String p = dashD.getPaysDouane(idLogin, "douane") ;
		System.out.println("Interface DOUANE :# Pays DOUANE "+p);
		System.out.println("-----------------------------------" );
		System.out.println("0 : Visulaiser Listes des Commandes expediees au pays de la douane ? " );
		System.out.println("1 : Visulaiser Listes des Commandes controlees par cette douane  ? " );
		System.out.println("2 : Visulaiser Listes des Commandes expediees au pays de la douane mais non-control�es ? " );
		System.out.println("3 : Recherche Commande ? " );
		System.out.println("4 : Visulaiser les detailes Commande ? " );
		System.out.println("5 : Quitter ? " );
		System.out.println("-----------------------------------" );
		sc = new Scanner(System.in);
		choice = sc.nextInt() ;
		checkDouaneChoice(choice);
	}

	private void checkDouaneChoice(int idx) throws SQLException {
		switch (idx) {
		case 0:
			dashD.getListesCommandesPaysDouane();
			break;
		case 1:
			dashD.getCommandeByDouane();
			break;
		case 2:
			System.out.println("Listes Commandes expediees au pays de la douane non-controlees");
			break;
		case 3:
			rechercheCommande();
			break;
		case 4:
			System.out.println("Visulaiser les detailes Commande");
			break;
		case 5:
			quitter();
			break;
		default:
			break;
		}

	}

	private void rechercheCommande() throws SQLException {
		System.out.println("Veuillez entrer le numero de la commande ");
		sc = new Scanner(System.in);
		choix = sc.nextLine();
		dashD.searchCommmandeById(choix);
	}

}
