import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Fenetre extends JFrame{



	private Toolkit k = Toolkit.getDefaultToolkit();
	private Dimension tailleEcran = k.getScreenSize();
	private int largeurEcran = tailleEcran.width;
	private int hauteurEcran = tailleEcran.height;
	private int type = 0;

	private int tailleHistorique = 10;
	private Contenu racine = null;
	private Contenu[] historique = new Contenu[tailleHistorique];
	private int pointeur = -1;



	JPanel monContenu = null;
	String titre="";

	Container leContenant = null;


	public int getLargeur(){ return largeurEcran;}
	public int getHauteur(){ return hauteurEcran;}

	public Fenetre(int type){
		setType(type,-1);
	}

	public void initialise(int type,int id){
		if (type == 0){ // fenetre de connection

			titre = "Connection";
			setTitle(titre);
			setSize(450,300);
			racine = null;

			//ou apparait la fenetre
			setLocation((largeurEcran-450)/2,(hauteurEcran-300)/2); 

			monContenu = new ContenuConnexion(this);
			leContenant = getContentPane();
			leContenant.add(monContenu);
			//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");


		}

		if (type == 1){ //Douane
			titre = "Douane";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDouane(this);
			//monContenu = new ContenuDetailColis(this,111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);

		}
		if (type == 2){ //Emballeur
			titre = "Emballeur";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuEmballeur(this);
			//monContenu = new ContenuDetailColis(this,111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 3){ // Transporteur
			titre = "Transporteur";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuTransporteur(this,1);
			//monContenu = new ContenuDetailColis(this,111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 4){ // Client

			titre = "Client";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuClient(this);
			//monContenu = new ContenuDetailColis(this,111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 5){ // Gerant
		}

		if (type == 101){  // Menu autre Douane
			titre = "Détails du colis "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuMenuAutreDouane(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 106){  // details colis
			if (Requetes.aUnVerdict(id)) initialise(111,id);
			else initialise(110,id);
		}
		if (type == 110){  // details colis pas verifié


			titre = "Détails du colis "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 


			monContenu = new ContenuDetailColis(this,id);


			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);


		}
		if (type == 111){  // details colis deja verifié


			titre = "Détails du colis "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 


			monContenu = new ContenuDetailColis2(this,id);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);


		}
		if (type == 301){  // Les transports du transporteur
			titre = "Mes Transports";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuMesTransports(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 302){  // orientation vers les details des transports pr un colis
			if (Requetes.coliEstExpedie(id)) setType(306,id); 
			else setType(305,id);
		}
		if (type == 303){  // orientation vers les details des transports pour une palette
			// si pas de transporteur -> 308
			// sinon vers 307
		}
		if (type == 304){  // orientation vers les details des transports pour un conteneur
			// si pas de transporteur -> 309
			// sinon vers 316
		}
		if (type == 305){  // detail transporteur (des colis pas pris)
			titre = "Detail du colis "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur(this,id,1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 306){  // orientation vers les details des transports 2eme degres pour les colis
			// si pas de arrivé -> 310
			// sinon vers 311
		}
		if (type == 307){  // orientation vers les details des transports 2eme degres pour les palettes
			// si pas de arrivé -> 312
			// sinon vers 314
		}
		if (type == 308){  // detail transporteur (des palette pas pris)
			titre = "Detail de la palette "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur(this,id,2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 309){  // detail transporteur (des conteneurs pas pris)
			titre = "Detail du conteneur "+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur(this,id,3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 310){  // detail transporteur (des colis pris pas encore arrivés)
			titre = "Detail du colis"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur2(this,id,1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 311){  // detail transporteur (des colis arrivés)
			titre = "Detail du colis"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur3(this,id,1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 312){  // detail transporteur (des palettes pris pas encore arrivés)
			titre = "Detail de la palette"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur2(this,id,2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 313){  // detail transporteur (des conteneurs pris pas encore arrivés)
			titre = "Detail du conteneur"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur2(this,id,3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 314){  // detail transporteur (des palettes arrivés)
			titre = "Detail de la palette"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur3(this,id,2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 315){  // detail transporteur (des conteneur arrivés)
			titre = "Detail du conteneur"+id;
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuDetailTransporteur3(this,id,3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 316){  // orientation vers les details des transports 2eme degres pour les conteneurs
			// si pas de arrivé -> 313
			// sinon vers 315
		}
		if (type == 401){  // Modification du compte client
			titre = "Modifier votre compte";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuModificationCompteClient(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 402){// Appercu des commande du client
			titre = "Vos commandes";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuSuiviCommandeClient(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 403){// Validation de la commande du client
			titre = "Valider votre commande";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuValidationCommande(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 407){  // Commande
			titre = "Faire une commande";
			setTitle(titre);
			setSize(largeurEcran,hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran-1,hauteurEcran-1);
			setSize(largeurEcran,hauteurEcran);
			setLocation(0,0); 

			monContenu = new ContenuCommande(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 408){  // suivi des commandes


	

		}
		if (type == 409){  // compt client


		}



	}

	public int getType(){return type;}
	public void setType(int n,int id){
		//System.out.println("setType("+n+","+id+")");

		type = n;
		leContenant = getContentPane();
		leContenant.removeAll();
		leContenant.repaint();
		initialise(n,id);


		if (racine==null) {
			if (type!=0) setRacine(titre,type,id);
		}
		else {
			if ((n==6)||(n==110)||(n==111)) ajoutHistorique(titre,6,id);
			else ajoutHistorique(titre, type,id);
		}
		//afficheHistorique();
	}
	public void setRacine(String ti, int ty, int id){
		racine = new Contenu(ti,ty,id);
	}
	public void connexion(){
			leContenant = getContentPane();
			leContenant.removeAll();

			leContenant.repaint();
			initialise(0,0);

	}
	public void racine(){
/*
			leContenant = getContentPane();
			leContenant.removeAll();

			leContenant.repaint();


			setTitle(racine.getTitre());

			//ou apparait la fenetre
			//setLocation(0,0); 

			monContenu = racine.getJPanel();
			leContenant = getContentPane();
			leContenant.add(monContenu);
			type = racine.getType();
			*/
			setType(racine.getType(),racine.getId());
	}
	public void ajoutHistorique(String ti, int ty,int id){

		if (pointeur < tailleHistorique -1){
			if ((pointeur!=-1)&&(historique[pointeur].getType()==ty)&&(historique[pointeur].getId()==id)){
			}
			else {
				pointeur++;


				if (historique[pointeur]!=null){
					System.out.println("ici");
					for (int i=pointeur;i<tailleHistorique;i++){
						historique[i] = null;
					}
				}
				System.out.println("oui");
				historique[pointeur] = new Contenu(ti,ty,id);
			}
		}
		else {
		
		}
	}
	public void suivantHistorique(){
		if ((pointeur < tailleHistorique -1 ) && (historique[pointeur+1]!=null)){

			pointeur++;
			/*
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.repaint();

			setTitle(historique[pointeur].getTitre());

			//ou apparait la fenetre
			//setLocation(0,0); 

			monContenu = historique[pointeur].getJPanel();
			leContenant = getContentPane();
			leContenant.add(monContenu);
			type = historique[pointeur].getType();*/
			setType(historique[pointeur].getType(),historique[pointeur].getId());
		}
		else {}
	}
	public void precedentHistorique(){
		if (pointeur==0){
			pointeur --;
			racine();
		}
		else if (pointeur>0){

			pointeur--;
/*
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.repaint();

			setTitle(historique[pointeur].getTitre());
getType
			//ou apparait la fenetre
			//setLocation(0,0); 

			monContenu = historique[pointeur].getJPanel();
			leContenant = getContentPane();
			leContenant.add(monContenu);
			type = historique[pointeur].getType();*/

			initialise(historique[pointeur].getType(),historique[pointeur].getId());
		}
		else {}
	}
	public void afficheHistorique(){
		System.out.print(" ("+pointeur+") -> ");
		for (int i=0; i<tailleHistorique;i++){
			System.out.print(" ["+i+"]");
			if (historique[i]==null) System.out.print("NULL");
			else System.out.print(historique[i].getType());
			System.out.print("]");
		}
		System.out.print("\n");

	}
}

