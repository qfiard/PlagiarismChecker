import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class InitBase {
    static Statement stmt;
    static Scanner in = new Scanner(System.in);
    static ConnectionBase connecte;

    

    public static void main(String[] args) throws SQLException {
	
	try {
	    // Connection a la base
	    Scanner scan = new Scanner(System.in);
	    System.out.println("Entrez votre login psql");
	    String login = scan.next();
	    String mdp = PasswordField.readPassword("Entrer votre password psql: ");
	    connecte = new ConnectionBase(login,mdp);

	    //creation de la table
		
	    connecte.creaTable ("create table gerant (nom Varchar (30) not null,prenom Varchar(20) not null,login Varchar (20),mdp Varchar (20));");
	    connecte.creaTable ("create table douane (id_pays Varchar(20) primary key, login Varchar (20), mdp Varchar (20));");
	    connecte.creaTable ("create table personnel (id_personnel int primary key, nom Varchar (30), prenom Varchar (20), metier Varchar (15));");
	    connecte.creaTable ("create table emballeur (login Varchar (20), mdp Varchar (20))inherits (personnel);");
	    connecte.creaTable ("create table transporteur (login Varchar (20), mdp Varchar (20))inherits (personnel);");
	    connecte.creaTable ("create table palette ( id_palette int primary key, id_conteneur int);");
	    connecte.creaTable ("create table conteneur ( id_conteneur int primary key);");
	    connecte.creaTable ("create table client ( id_client int primary key, nom Varchar (20), prenom Varchar (30), login Varchar (20), mdp Varchar (20));");
	    connecte.creaTable ("create table colis ( id_coli int primary key, id_client int references client(id_client), id_pays Varchar (20) references douane(id_pays), id_emballeur int references personnel(id_personnel), id_palette int references palette(id_palette), destination Varchar(20) not null, date_limite date not null, jour_emballage date, lieu Varchar(20) not null, etat Varchar (20), rejete boolean);");
	    connecte.creaTable ("create table produit ( id_produit int primary key, nom_produit Varchar(20), prix int, etat Varchar (20), stock int, id_coli int references colis (id_coli));");
		

		
	}
	catch(Exception e) {
	    e.printStackTrace();
	}

	// fermeture de la connexion
	connecte.close();
	in.close();

    }
    
}
