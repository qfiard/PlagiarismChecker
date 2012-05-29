package donnees;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class ScannerDonnees {
	
	LinkedList<Produit> produits;
	LinkedList<Transporteur> transporteurs;
	LinkedList<Douane> douanes;
	LinkedList<Gerant> gerants;
	LinkedList<Emballeur> emballeurs;
	LinkedList<Client> clients;
			
	public ScannerDonnees(String fileName){
		
		produits = new LinkedList<Produit>();
		transporteurs = new LinkedList<Transporteur>();
		douanes = new LinkedList<Douane>();
		gerants = new LinkedList<Gerant>();
		emballeurs = new LinkedList<Emballeur>();
		clients = new LinkedList<Client>();
		
		try{
						
			Scanner sc = new Scanner(new File(fileName));
			String tmp;		
			int type;			
			
			while(sc.hasNextLine()){
				tmp = sc.nextLine();
				String[] donnees = tmp.split("\\|");
				
				type = Integer.parseInt(donnees[0]);
				
				switch(type){
				
				//Emballeur
				case 10:
					emballeurs.add(new Emballeur(donnees));
					break;
					
				//Client
				case 20:
					clients.add(new Client(donnees));
					break;
					
				//Produit
				case 30:
					try{
						produits.add(new Produit(donnees));
					}catch(Exception e){

					}
					break;
					
				//Transporteurs
				case 40:
					transporteurs.add(new Transporteur(donnees));
					break;
					
				//Douane
				case 50:
					douanes.add(new Douane(donnees));
					break;
					
				//Gerant
				case 60:
					gerants.add(new Gerant(donnees));
					break;
				
				}
			}
		
			sc.close();
			
			ConnexionBD cn = new ConnexionBD();
			Statement st = cn.createStatement();			
			
			for(Produit p: produits){
				p.insererProduit(st);
			}
			
			for(Emballeur e: emballeurs){
				e.insererEmballeur(st);
			}
			
			for(Transporteur t: transporteurs){
				t.insererTransporteur(st);
			}
			
			for(Client c: clients){
				c.insererClient(st);
			}
			
			for(Douane d: douanes){
				d.insererDouane(st);
			}
			
			for(Gerant g: gerants){
				g.insererGerant(st);
			}
			
		}catch(FileNotFoundException e){
			System.out.println("Fichier inexistant: "+fileName);
		}
		
	}
	
	public static void main(String[] args){
		
		ScannerDonnees sc = new ScannerDonnees(Constantes.fichierData);
		//ScannerDonnees sc = new ScannerDonnees("data.csv");
		
	}

}
