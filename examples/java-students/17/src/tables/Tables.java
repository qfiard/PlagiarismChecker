package tables;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import commandes.GenerateurAleatoire;

import donnees.ConnexionBD;
import donnees.Constantes;
import donnees.ScannerDonnees;

public class Tables {

	public static void insertionDonnees(){
		System.out.println("Lecture des donnees ...");
		
		ScannerDonnees sc = new ScannerDonnees(Constantes.fichierData);
		
		System.out.println("Fin de lecture");
	}

	public static void suppressionTables(){

		try {

			StringBuffer str = new StringBuffer();
			Scanner sc = new Scanner(new File(Constantes.fichierSuppression));

			while(sc.hasNextLine()){
				str.append(sc.nextLine() + "\n");
			}

			ConnexionBD conn = new ConnexionBD();
			Statement st = conn.createStatement();

			st.execute(str.toString());
			
			System.out.println("Suppression effectuee avec succes");

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Erreur: Fichier introuvable, modifiez la variable " +
					"'fichierSuppression' dans le fichier src/donnees/Constantes.java pour specifier le bon chemin");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Aucune table a supprimer");
		}
	}

	public static void creationTables(){
		try {

			StringBuffer str = new StringBuffer();
			Scanner sc = new Scanner(new File(Constantes.fichierCreation));

			while(sc.hasNextLine()){
				str.append(sc.nextLine() + "\n");
			}

			ConnexionBD conn = new ConnexionBD();
			Statement st = conn.createStatement();

			st.execute(str.toString());
			
			System.out.println("Creation effectuee avec succes");

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Erreur: Fichier introuvable, modifiez la variable " +
					"'fichierCreation' dans le fichier src/donnees/Constantes.java pour specifier le bon chemin");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Les tables ont deja ete crees");			
		}
	}
	
	public static void generationCommandes(){
		
		System.out.println("Generation des commandes en cours ...");
		
		GenerateurAleatoire gen = new GenerateurAleatoire();
		
		System.out.println("Fin de la generation");
	}

	public static void afficherMenu(){
		System.out.println("----------MENU----------");
		System.out.println("1. Creation des tables");
		System.out.println("2. Suppression des tables");
		System.out.println("3. Insertion des donnees");
		System.out.println("4. Generation des commandes");
		System.out.println("5. Quitter");
	}

	public static void main(String[] args){

		boolean quitter = false;

		while(!quitter){

			afficherMenu();
			
			Scanner sc = new Scanner(System.in);

			if(sc.hasNextInt()){

				int numero = sc.nextInt();

				switch(numero){

				case 1:
					creationTables();
					break;

				case 2:
					suppressionTables();
					break;

				case 3:
					insertionDonnees();
					break;

				case 4:
					generationCommandes();
					break;
					
				case 5:
					quitter = true;
					break;

				default:
					System.out.println("Numero invalide");
					break;
				}

			}

		}
	}

}
