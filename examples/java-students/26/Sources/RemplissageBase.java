import java.io.*;
import java.util.*;


public class RemplissageBase {
	
	/* Class du parseur d'un fichier .csv qui recupere les donnees
	 * ˆ inserer dans la base selon les differentes tables
	 *  .ouverture d'un ficher csv : main
	 *  .Lecture ligne par ligne : recupereTuple
	 *  .Scanne des valeurs retenues et creation de l'argument de VALUES :
	 *   recupereValeur
	 *  .Insertion des valeurs dans les tables correspondantes : insertTuples
	 */

	/**
	 * @param args
	 */
	public static void main(String... args) throws FileNotFoundException {
		// /!\ adresse du fichier  .csv a modifier /!\

		RemplissageBase parseur = new RemplissageBase ("data.csv");
		parseur.recupereTuple();
		System.out.println("Base remplie.");
	}

	public RemplissageBase(String nomFichier){
		fFile = new File(nomFichier);  
	}


	/* 
	 * Methode finale de recuperation des Tuples
	 * 
	 *  . Utilisation d'un scanner qui parse chaque ligne du fichier fFile 
	 *    (dont l adresse est donnee dans le main)
	 */
	public final String recupereTuple() throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(fFile));
		String resultat=" ";
		try { //utilisation du scan pour chaque ligne, avec un test d'existance d'une prochaine ligne
			while ( scan.hasNextLine() ){
				resultat=resultat+recupereValeurs( scan.nextLine());
			}
		}
		finally {scan.close(); } 
		// fermeture du scan quand tout le fichier est scanne
		// System.out.println(resultat);
		return resultat;
	}


	/* 
	 * Methode de recuperation des valeurs ˆ inserer dans les tables de la BD
	 * 
	 *  . Utilisation d'un scanner qui parse le contenue de chaque ligne
	 *    en utlisant le delemiteur de valeurs "|"
	 *  . Utilisation de la methode d'insertion des des donnees { insertTuples}
	 *    en tenant compte du typage des donnees donne en debut de ligne
	 */	
	public String recupereValeurs(String aLine){
		Scanner scan = new Scanner(aLine);
		scan.useDelimiter("\\|");
		String type="";
		String valeurs="";
		String insert="base vide";
		type=scan.next(); //recuperation du typage du tuple (lie a la table)
		try{
			String id,nom,prenom,tauxerr,mdp,soc,suf,adr,ville,cdp,
			       ref,des,qpc,cpp,qual,prix,txaug,poids,stock,scac,
			       pays,txres,log;
				
		
			switch(Integer.parseInt(type)){
			case 10 : // Emballeur
				id  = quote(scan.next());
				nom = quote(scan.next());
				prenom = quote(scan.next());
				tauxerr = scan.next();
				mdp = quote(scan.next());
				
				valeurs=id+","+nom+","+prenom+","+tauxerr+","+mdp;
				
				insert=insertTuples("emballeur",valeurs);
				break;

			case 20 : // Client 
				id  = scan.next();
				soc = quote(scan.next());
				suf = scan.next();
				adr = quote(scan.next());
				ville = quote(scan.next());
				cdp = quote(scan.next());
				pays = quote(scan.next());
				// log  = id+suf;
				scan.next(); //argument 8 non retenus
				mdp = quote(scan.next());
				
				valeurs=quote(id)+","+soc+","+quote(suf)+","+adr+","+ville+","
				+cdp+","+pays+","+quote(id+suf)+","+mdp;
				
				insert=insertTuples("client",valeurs);
				break;

			case 30 : // Produit 
				ref = quote(scan.next());
				des = quote(scan.next());
				qpc = scan.next();
				cpp = scan.next();
				qual = quote(scan.next());
				prix = scan.next();
				txaug = scan.next();
				poids = scan.next();
				stock = scan.next();
				//nvend = "0";
				
				valeurs=ref+","+des+","+qpc+","+cpp+","+qual+","
				+prix+","+txaug+","+poids+","+stock+","+"0";
				insert=insertTuples("produit",valeurs);
				break;

			case 40 : // Transporteurs
				scac  = quote(scan.next());
				nom  = quote(scan.next());
				mdp  = quote(scan.next());
				
				valeurs=scac+","+nom+","+mdp;
				
				insert=insertTuples("transport",valeurs);
				break;

			case 50 : // Douane
			        pays = quote(scan.next());
				txres = quote(scan.next());
				log = quote(scan.next());
				mdp = quote(scan.next());
				
				valeurs=pays+","+txres+","+log+","+mdp;			
				insert=insertTuples("douane",valeurs);
				break;

			case 60 : // Gerant	
			        nom = quote(scan.next());
				prenom = quote(scan.next());
				log = quote(scan.next());
				mdp = quote(scan.next());
				
				valeurs=nom+","+prenom+","+log+","+mdp;
				insert=insertTuples("gerant",valeurs);
				break;

			default :
				System.out.println("erreur de typage");
			}
		}
		catch (Exception e) {
			System.err.println("erreur de scanne");
		}
		return insert;
	}


	private  File fFile;

	/* Methode de mise entre quote,
	 * utile lors de la conversion des donnees scannees en varchar */ 
	private String quote(String aText){
		String QUOTE = "'";
		return QUOTE + aText + QUOTE;
	}

	// Methode d'insertion des donnees scannees dans la table correspondante
	private String insertTuples(String table,String valeurs) {
		String sql = "INSERT INTO "+ table+" VALUES("+valeurs+");";
		return sql;
	}

}

