import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class UpdateDatabase 
{
    /* Dans ce fichier on va créer le contenu des tables (en supprimant
       toutes les anciennes données pour éviter des collisions) */
    public static void main(String[] args)
    {
	try
	    {
		System.out.println("Do you want to update the database ? ");
		System.out.print("[y/n] : ");
		Scanner sc    = new Scanner(System.in);
		String choice = sc.next();
		if (choice.equals("y"))
		    {
			QueryDB _query = new QueryDB(new ConnectionDB());
			
			/* On supprime le contenu au cas où */
			String[] rq    = {"delete from colis *;",
					  "delete from commande *;",
					  "delete from client *;",
					  "delete from employe *;",
					  "delete from palette *;",
					  "delete from conteneur *;",
					  "delete from produit *;",
					  "delete from gerant *;",
					  "delete from douane *;"};
			for (int i=0; i<rq.length; i++)
			    _query.updateDB(rq[i]);
			
			/* On ajoute nos fichiers aux différentes tables */
			System.out.println("\tUpdating table ... Client");
			updateCustomerDB(_query);
			System.out.println("\tUpdating table ... Employe");
			updateEmployeeDB(_query);
			System.out.println("\tUpdating table ... Produit");
			updateProductDB(_query);
			System.out.println("\tUpdating table ... Commande");
			updateCommandDB(_query);
			System.out.println("\tUpdating table ... Conteneur");
			updateContainerDB(_query);
			System.out.println("\tUpdating table ... Palette");
			updatePaletteDB(_query);
			System.out.println("\tUpdating table ... Colis");
			updatePackageDB(_query);
			System.out.println("\tUpdating table ... Gerant");
			updateOwnerDB(_query);
			System.out.println("\tUpdating table ... Colis");
			updateCustomsDB(_query);
			
			_query.close();
			System.out.println();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in CreateDatabase:main");
		System.out.println("\t"+e);
		return;
	    }
    }
	

    public static void updateCustomerDB(QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_client.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into client (nom, prenom, mdp, "+
			    "login, id_client) values (\'"+data[0]+"\', \'"+
			    data[1]+"\', \'"+data[2]+"\', \'"+data[3]+"\', "+
			    data[4]+");";
			_query.updateDB(rq);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateCustomerDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updateEmployeeDB(QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_employe.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into employe (id_employe, "+
			    "nom, prenom, specialite, mdp) VALUES ("+
			    data[0]+", \'"+data[1]+"\', \'"+data[2]+"\', \'"+
			    data[3]+"\', "+"\'"+data[4]+"\');";
			_query.updateDB(rq);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateEmployeeDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updateProductDB (QueryDB _query)
    {
	try
	    {
		String file = "Data/bd_produit.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine(); // ligne lue par readLine()
		String[] data; // tableau retourné par String.split()
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into produit (id_produit, nom,"+ 
			    " type, stock, prix, nb_vente) VALUES ("+
			    data[0]+", \'"+data[1]+"\', \'"+data[2]+"\', " +
			    data[3]+", "+data[4]+", "+data[5]+");";
			_query.updateDB(rq); /* On envoie la requête ! */
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateProductDB");
		System.out.println("\t"+e);
		return;
	    }
    }
    
    public static void updateCommandDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_commande.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			// On récupère le prix de la commande
			ProductDB.getProductPrice(_query, data[1]);
			_query.rst.next();
			String prix = _query.rst.getString(1);
			
			/* On obtient la requête */
			String rq = "insert into commande (id_commande,"+
			    " id_produit, id_client, prix_total, date_livraison"
			    +", id_emballeur, etat, id_colis) values ("+
			    data[0]+", "+data[1]+", "+data[2]+", "+prix+
			    ", \'"+data[3]+"\', "+data[4]+", \'"
			    +data[5]+"\', "+data[6]+");";
			_query.updateDB(rq);

			// On en profite pour mettre à jour la table client...
			CustomerDB.updateNbCmd(_query, data[2]);
			// ...et la table employe
			EmployeeDB.updateNbProd(_query, data[4]);
			
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateCommandDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updateContainerDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_conteneur.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into conteneur (id_conteneur, "+
			    "mode) values ("+data[0]+", \'"+data[1]+"\');";
			_query.updateDB(rq);
			//System.out.println(rq);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateContainerDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updatePaletteDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_palette.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into palette (id_palette, "+
			    "id_conteneur, id_transporteur) values ("+data[0]+
			    ", "+data[1]+", "+data[2]+");";
			_query.updateDB(rq);
			// on en profite pour mettre à jour le nombre de
			// produit traité par les transporteurs
			EmployeeDB.addProduitTraite(_query, data[2]);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updatePaletteDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updatePackageDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_colis.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			// on récupère le type du colis
			ProductDB.getTypeProduct(_query, data[1]);
			_query.rst.next();
			String type = _query.rst.getString(1);
			// on récupère l'état
			CommandDB.getStatusCmd(_query, data[1]);
			_query.rst.next();
			String etat = _query.rst.getString(1);
			if (etat.equals("expedie"))
			    etat = "envoye";
			else if (etat.equals("en cours"))
			    etat = "emballe";
			else if (etat.equals("recu"))
			    etat = "arrive";
			// on obtient la date d'emballage
			CommandDB.deliveryDate(_query, data[1]);
			_query.rst.next();
			String date = _query.rst.getString(1);

			/* Et finalement la requête */
			String rq = "insert into colis (id_colis, id_commande,"+
			    " type, statut, date_emballage, id_palette) values ("
			    + data[0]+", "+data[1]+", \'"+type+"\', \'"+etat+
			    "\', \'"+date+"\', "+data[2]+");";
			_query.updateDB(rq);

			// on en profite pour mettre à jour la table commande
			// avec les colis
			CommandDB.updatePackage(_query, data[1], data[0]);
			
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updatePackageDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updateOwnerDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_gerant.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into gerant (nom, mdp)"+
			    " values (\'"+data[0]+"\', \'"+data[1]+"\');";
			_query.updateDB(rq);
			//System.out.println(rq);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateOwnerDB");
		System.out.println("\t"+e);
		return;
	    }
    }

    public static void updateCustomsDB (QueryDB _query)
    {
	try
	    {
		String file       = "Data/bd_douane.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line       = in.readLine();
		String[] data;
		while (line != null)
		    {
			data      = line.split("\t");
			String rq = "insert into douane (id_douanier, "+
			    "mdp) values ("+data[0]+", \'"+data[1]+"\');";
			_query.updateDB(rq);
			//System.out.println(rq);
			line = in.readLine();
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("An error ocurred in : updateCustomsDB");
		System.out.println("\t"+e);
		return;
	    }
    }
    
}