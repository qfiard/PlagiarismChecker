import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class CreateDatabase 
{
    /* Ce fichier s'occupe de générer les tables dont on aura besoin */
    public static void main(String[] args)
    {
	try
	    {
		System.out.println("Do you need to create the tables ?");
		System.out.print("WARNING : TABLES NAMED client - employe - ");
		System.out.print("commande - produit - colis - gerant - douane");
		System.out.print(" - palette - conteneur WILL BE EREASED.\n");
		System.out.print("[y/n] : ");
		Scanner sc    = new Scanner(System.in);
		String choice = sc.next();
		if (choice.equals("y"))
		    {
			QueryDB _query = new QueryDB(new ConnectionDB());
			
			/* On supprime les éventuelles tables portant le même 
			   nom */
			String[] rq = {"drop table client cascade;",
				       "drop table employe cascade;",
				       "drop table commande cascade;",
				       "drop table produit cascade;",
				       "drop table colis cascade;",
				       "drop table gerant cascade;",
				       "drop table douane cascade;",
				       "drop table palette cascade;",
				       "drop table conteneur cascade;"};
			for (int i=0; i<rq.length; i++)
			    _query.updateDB(rq[i]);

			/* On peut créer les tables ! */
			// table client
			System.out.println("\tCreating table ... Client");
			String dbCustomer = "create table client ("+
			    "nom    varchar(20), "+
			    "prenom varchar(20), "+
			    "mdp    varchar(20), "+
			    "nb_cmd int default 0, "+
			    "login  varchar(20), "+
			    "id_client serial unique,"+
			    "primary key (login, id_client));";
			_query.updateDB(dbCustomer);

			System.out.println("\tCreating table ... Employe");
			// table employé
			String dbEmployee = "create table employe ("+
			    "id_employe serial primary key, "+
			    "nom        varchar(20), "+
			    "prenom     varchar(20), "+
			    "specialite varchar(20) check (specialite in ("+
			                "\'emballeur\', \'transporteur\')), "+
			    "prod_traite int default 0, "+
			    "mdp        varchar(20));";
			_query.updateDB(dbEmployee);

			System.out.println("\tCreating table ... Conteneur");
			// table conteneur
			String dbContainer = "create table conteneur ("+
			    "id_conteneur serial primary key, "+
			    "mode         varchar(20));";
			_query.updateDB(dbContainer);
			
			System.out.println("\tCreating table ... Palette");
			// table palette
			String dbPalette = "create table palette ("+
			    "id_palette      serial primary key, "+
			    "id_conteneur    int references "+
			                     "conteneur(id_conteneur), "+
			    "id_transporteur int);";
			_query.updateDB(dbPalette);
			
			System.out.println("\tCreating table ... Produit");
			// table produit 
			String dbProduct = "create table produit ("+
			    "id_produit serial primary key, "+
			    "nom        varchar(20), "+
			    "type       varchar(20) check (type in (\'fragile\'"+
			                ",\'dangereux\',\'neutre\')), "+
			    "stock      int, "+
			    "nb_vente   int, "+
			    "prix       numeric);";
			_query.updateDB(dbProduct);
		
			System.out.println("\tCreating table ... Commande");
			// table commande
			String dbCommand = "create table commande ("+
			    "id_commande serial primary key, "+
			    "id_produit     int references produit(id_produit),"+
			    " id_client     int references client(id_client), "+
			    "prix_total     numeric, "+
			    "date_livraison date, "+
			    "id_emballeur   int references employe(id_employe),"+
			    " etat          varchar(20), "+
			    "id_colis       int);";
			_query.updateDB(dbCommand);
			
			System.out.println("\tCreating table ... Colis");
			// table colis 
			String dbPackage = "create table colis ("+
			    "id_colis       serial primary key, "+
			    "type           varchar(20), "+
			    "commentaire    text default \'none\', "+
			    "date_emballage date, "+
			    "id_palette     int references palette(id_palette),"+
			    "statut         varchar(20), "+
			    "id_commande    int references "+
			                    "commande(id_commande));";
			_query.updateDB(dbPackage);
			
			System.out.println("\tCreating table ... Douane");
			// table douane
			String dbCustoms = "create table douane ("+
			    "id_douanier int primary key, "+
			    "mdp         varchar(20));";
			_query.updateDB(dbCustoms);

			System.out.println("\tCreating table ... Gerant");
			// table gérant
			String dbOwner = "create table gerant ("+
			    "nom varchar(20), "+
			    "mdp varchar(20),"+
			    "primary key (nom, mdp));";
			_query.updateDB(dbOwner);
		    }
	    }
	catch (Exception e)
	    {
		System.out.println("\t"+e);
		return;
	    }
    }
}