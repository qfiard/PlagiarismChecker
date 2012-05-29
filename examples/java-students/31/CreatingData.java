import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Scanner;



public class CreatingData {
	
	static File data = new File("data4.txt");
	static File emballeurFile = new File("emballeur.txt");
	static File clientFile = new File("client.txt");
	static File produitFile = new File("produit.txt");
	static File transporteurFile = new File("transporteur.txt");
	static File douaneFile = new File("douane.txt");
	static File commandeFile = new File("commande.txt");
	static File colisFile = new File("colis.txt");
	static File paletteFile = new File("palette.txt");
	static File conteneurFile = new File("conteneur.txt");
	static File achatFile= new File("achat.txt");
	
	static ArrayList<String> emballeurs = new ArrayList<String>();//10
	static ArrayList<String> clients = new ArrayList<String>();//20
	static ArrayList<String> produits = new ArrayList<String>();//30
	static ArrayList<String> transporteurs = new ArrayList<String>();//40
	static ArrayList<String> douane = new ArrayList<String>();//50
	static ArrayList<String> commandes = new ArrayList<String>();
	static ArrayList<String> colis = new ArrayList<String>();
	static ArrayList<String> palettes = new ArrayList<String>();
	static ArrayList<String> conteneurs = new ArrayList<String>();
	static ArrayList<String> achats= new ArrayList<String>();
	static String gerant;//60
	
	public static void lectureData() {
		Scanner sc;
		try {
		sc = new Scanner(data);
		String line;
		String ajout;
		String[] tab;
		int num;
		int count = 0;
		Random random = new Random();
		while(sc.hasNextLine()){
			count++;

			line = sc.nextLine();
			tab = line.split("/");
			num = Integer.parseInt(line.substring(0,2));

			switch(num){
			
			case 10 ://emballeurs
                ajout = tab[1]+"*"+tab[2]+"*"+tab[3]+"*"+tab[4]+"*"+tab[5];
                 /*
                 * create table emballeur(
                 * num_emballeur integer primary key, 
                 * nom varchar(20), prenom varchar(20), 
                 * taux_erreur real,
                 * mot_de_passe varchar(20)
                 * );
                 */
                emballeurs.add(ajout);
                break;
            
                 
			case 20 ://clients 
				ajout = tab[1]+"*"+tab[2]+"*"+tab[3]+"*"+tab[4]+"*"+tab[5]+"*"+tab[6]+"*"+tab[7]+"*"+tab[8]+"*"+tab[9];
				/*
				 * create table client(
				 * num_client char(10), 
				 * nom_societe varchar(20), 
				 * suffixe_societe varchar(10), 
				 * adresse text,
				 * ville varchar(20), 
				 * code_postal varchar(20),
				 * pays varchar(20),
				 * telephone varchar(30),
				 * mot_de_passe varchar(20));
				 */
				clients.add(ajout);
				break;
			
			case 30 ://produits
				ajout = tab[1]+"*"+tab[2];
				if(tab[5].equalsIgnoreCase("D")) ajout+="*y*n*";
				if(tab[5].equalsIgnoreCase("F")) ajout+="*n*y*";
				if(tab[5].equalsIgnoreCase("N")) ajout+="*n*n*";
				ajout+=tab[6]+"*"+tab[7]+"*"+tab[8]+"*"+random.nextInt(200);		
				/*
				 * create table produit (
				 * num_produit char(16) primary key, 
				 * description text, 
				 * dangereux char(1) check( dangereux in ('y','n')),
				 * fragile char(1) check(fragile in ('y','n')),
				 * prix real, 
				 * poids integer,
				 * reserve integer,
				 * quantite_vendue integer);
				 */
				produits.add(ajout);
				break;
				
				
			case 40://transporteur
				ajout = tab[1]+"*"+tab[2]+"*"+tab[3];
				/*
				 * create table transporteur (
				 * code_SCAC char(4) primary key, 
				 * nom varchar(20),
				 * mot_de_passe varchar(30));
				 */
				transporteurs.add(ajout);
				break;

			case 50://douane
				ajout = tab[1]+"*"+tab[2]+"*"+tab[3]+"*"+tab[4];
				/*
				 * create table douane(
				 * pays varchar(20) primary key, 
				 * taux_verif real, 
				 * login varchar(20),
				 * mot_de_passe varchar (20));
				 */
				douane.add(ajout);
				break;
				
			case 60: //gerant
				ajout = tab[1]+"*"+tab[2]+"*"+tab[3]+"*"+tab[4];
				System.out.println(ajout);
				break;

			}
			
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public static void ecritureFichier(File fichier, ArrayList<String> liste){
		try{
			PrintWriter pw; pw = new PrintWriter(new FileWriter(fichier));

			for(int i =0;i<liste.size();i++){
				pw.println(liste.get(i));
			}
			pw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public static String conversionDate(Calendar cal){
		return  "'"+cal.get(cal.YEAR) + "-" + (cal.get(cal.MONTH)+1 )+ "-" + cal.get(cal.DATE)+"'";
	}
	
	public static void creaCommande(){
		
		//parametres
		int nbComExpediees  = 180;
		int nbComPartiellementExpediees  = 20;
		int nbComNonExpediees  = 20;
		
		//compteurs
		int countCommande = 0;
		int countPalette =0;
		int countCont = 0;
		
		//calendriers
		Calendar  calCom = new GregorianCalendar() ; calCom.setLenient(false);
		Calendar caltmp = new GregorianCalendar() ;caltmp.setLenient(false);
		Calendar caltmp2 =new GregorianCalendar() ;caltmp2.setLenient(false);
		
		
		Random random = new Random();
		String com = "";
		String col ="";
		String pal ="";
		String cont="";
		String achat ="";
		String produitsAchat;
		String client;
		String[] tab;
		
		int nbProduit =0;
		int quantite=0;
		int prix =0;
		boolean dangereux;
		boolean fragile;
		
		for(int i=0;i<nbComExpediees+nbComPartiellementExpediees;i++){

			/*
			 * on creer une commande et un colis à chaque tour de boucle
			 * on cree une nouvelle pallette tous les 4 colis
			 * On creer un nouveau contanneur tous les 4 palettes
			 */
			
			if(countCommande%4==0){
				if(countPalette%4==0){
					/*On creer un contenneur
					 * conteneur(num_conteneur integer primary key, 
					 * code_SCAC char(4)  references transporteur(code_SCAC), 
					 * vehicule varchar(5) check( vehicule in ('avion','camion')));
					 */
					cont = countCont+"*"+transporteurs.get(random.nextInt(transporteurs.size())).substring(0,4)+"*";
					if(Math.random()<0.5) cont += "avion";
					else cont += "camion";
					conteneurs.add(cont);
					countCont++;
				}
				/*On creer une palette
				 * create table palette(
				 * num_palette integer primary key, 
				 * num_conteneur integer references conteneur(num_conteneur), 
				 * date_emballage date);
				 */
				pal = countPalette+"*"+(countCont-1)+"*"+conversionDate(calCom);
				palettes.add(pal);
				countPalette++;
				
			}
			
			
		
			if(countCommande%20==0)calCom.add(Calendar.DAY_OF_MONTH,random.nextInt(2));
			caltmp.setTime(calCom.getTime());
			caltmp.add(Calendar.DAY_OF_MONTH,random.nextInt(3)+2);
			
			/*
			 * On Commande des produits au hasard (en meme temps on creer las ligne de la table achat :
			 */
			client = clients.get(random.nextInt(clients.size())).substring(0,10);
			nbProduit = random.nextInt(10)+1;
			prix =0;
			 produitsAchat = "";
			dangereux = false;
			fragile = false;
			
			for(int j=0;j<=nbProduit;j++){
				quantite = random.nextInt(5)+1;
				int indexProduit = random.nextInt(produits.size());
				if(!produitsAchat.contains(produits.get(indexProduit).substring(0,16))){
				tab = produits.get(indexProduit).split("\\*");
					prix+= Integer.parseInt(tab[4])*quantite;
					/*
					 * create table achat(
					 * num_commande integer references commande(num_commande),
					 * num_produit char(16) references produit(num_produit),
					 * quantite integer, primary key(num_commande,num_produit));
					 */
					
					achat = countCommande+"*"+produits.get(indexProduit).substring(0,16)+"*"+quantite;
					achats.add(achat);
					
					/*On regarde si le produit est fragile ou dangereux pour le noter sur le colis
					 * une fois que le colis contient un produit dangereux il est dangereux
					 * de meme pour fragile
					 */
					if(tab[2].contains("y"))dangereux =true;
					if(tab[3].contains("y"))fragile =true;
					produitsAchat+= produits.get(indexProduit).substring(0,16);
				}
			}
			
			/*
			 * create table commande(
			 * num_commande integer primary key, 
			 * num_client varchar(20) references client(num_client), 
			 * date_commande date, 
			 * date_livraison_souhaite date,
			 * date_emballage date, 
			 * prix integer,
			 * etat varchar(4) check(etat in ('NE','PE','100E')));
			 */
			com = countCommande+"*"+client+"*"+conversionDate(calCom)+"*"+conversionDate(caltmp)+"*";
			caltmp2.setTime(calCom.getTime());
			caltmp2.add(Calendar.DAY_OF_MONTH,random.nextInt(2)+1);
			com+=conversionDate(caltmp2)+"*"+prix+"*";
			if(i<nbComExpediees)com+="100E";
			else com +="PE";
			commandes.add(com);
			/*
			 * create table colis(
			 * num_colis integer primary key,
			 * num_commande integer references commande(num_commande),
			 * num_emballeur varchar(6) references emballeur(num_emballeur),
			 * num_palette integer references palette(num_palette), 
			 * date_livraison_souhaite date,
			 * date_emballage date,
			 * date_livraison date,
			 * dangereux char(1) check( dangereux in ('y','n')),
			 * fragile char(1) check(fragile in ('y','n')));
			 */
			
			col = countCommande+"*"+countCommande+"*"+emballeurs.get(random.nextInt(emballeurs.size())).substring(0,7)+"*"+(countPalette-1)+"*";
			//date livraison souhaitee
			col+=conversionDate(caltmp)+"*";
			//date emballage
			col+=conversionDate(caltmp2)+"*";
			//date livraison
			if(i<nbComExpediees){
				caltmp2.add(Calendar.DAY_OF_MONTH,random.nextInt(2)+1);
				col+=conversionDate(caltmp)+"*";
			}else{
				col+="'1111-11-11'*";
			}
			if(dangereux)col+="y*";
			else col+="n*";
			if(fragile)col+="y";
			else col+="n";
			countCommande++;
			colis.add(col);
		}
		for(int i=0;i<nbComNonExpediees;i++){
			
			if(countCommande%20==0)calCom.add(Calendar.DAY_OF_MONTH,random.nextInt(2));
			caltmp.setTime(calCom.getTime());
			caltmp.add(Calendar.DAY_OF_MONTH,random.nextInt(3)+2);
			
			/*
			 * On Commande des produits au hasard (en meme temps on creer las ligne de la table achat :
			 */
			client = clients.get(random.nextInt(clients.size())).substring(0,10);
			nbProduit = random.nextInt(10)+1;
			prix =0;
			 produitsAchat = "";
			dangereux = false;
			fragile = false;
			
			for(int j=0;j<=nbProduit;j++){
				quantite = random.nextInt(5)+1;
				int indexProduit = random.nextInt(produits.size());
				if(!produitsAchat.contains(produits.get(indexProduit).substring(0,16))){
				tab = produits.get(indexProduit).split("\\*");
					prix+= Integer.parseInt(tab[4])*quantite;
					/*
					 * create table achat(
					 * num_commande integer references commande(num_commande),
					 * num_produit char(16) references produit(num_produit),
					 * quantite integer, primary key(num_commande,num_produit));
					 */
					
					achat = countCommande+"*"+produits.get(indexProduit).substring(0,16)+"*"+quantite;
					achats.add(achat);
					
					/*On regarde si le produit est fragile ou dangereux pour le noter sur le colis
					 * une fois que le colis contient un produit dangereux il est dangereux
					 * de meme pour fragile
					 */
					if(tab[2].contains("y"))dangereux =true;
					if(tab[3].contains("y"))fragile =true;
					produitsAchat+= produits.get(indexProduit).substring(0,16);
				}
			}
			
			/*
			 * create table commande(
			 * num_commande integer primary key, 
			 * num_client varchar(20) references client(num_client), 
			 * date_commande date, 
			 * date_livraison_souhaite date,
			 * date_emballage date, 
			 * prix integer,
			 * etat varchar(4) check(etat in ('NE','PE','100E')));
			 */
			com = countCommande+"*"+client+"*"+conversionDate(calCom)+"*"+conversionDate(caltmp)+"*";
			caltmp2.setTime(calCom.getTime());
			caltmp2.add(Calendar.DAY_OF_MONTH,random.nextInt(2)+1);
			com+=conversionDate(caltmp2)+"*"+prix+"*";
			com +="NE";
			commandes.add(com);
			countCommande++;
		}
	}
		
		
	
	
	public static void main(String[] args) {
    	lectureData();
    	creaCommande();
    	
    	ecritureFichier(produitFile,produits);
    	ecritureFichier(clientFile,clients);
    	ecritureFichier(emballeurFile,emballeurs);
    	ecritureFichier(transporteurFile,transporteurs);
    	ecritureFichier(douaneFile,douane);
    	ecritureFichier(achatFile,achats);
    	ecritureFichier(commandeFile,commandes);
    	ecritureFichier(colisFile, colis);
    	ecritureFichier(paletteFile,palettes);
    	ecritureFichier(conteneurFile,conteneurs);
    }
}

