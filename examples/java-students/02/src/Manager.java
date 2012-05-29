
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;


public class Manager {
	
	Connection conn;
	Statement st;
	
	public Manager() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		this.conn= DriverManager.getConnection("jdbc:postgresql://nivose/rouache","rouache","helloworld");
		this.st=conn.createStatement();
	}
	
	public void create_base() throws SQLException{
		
		st.execute("create table utilisateur (" +
				"login char(20) primary key," +
				"password char(32) not null," +
				"type char(15) not null," +
				"pays char(15)," +
				"adresse char(40)," +
				"ville char(20)," +
				"code_postal char(10)," +
				"taux_erreur int," +
				"taux_verifie int," +
				"nom char(20)," +
				"prenom char(20)" +
				");");
		System.out.println("created table utilisateur;");
		
		st.execute("create table produit (" +
				"id_produit char(16) primary key," +
				"description varchar(200)," +
				"quantite_cartons int not null," +
				"cartons_palette int not null," +
				"qualifiant char(1)," +
				"cout int not null," +
				"poids int not null," +
				"reserve int not null" +
				");");
		System.out.println("created table produit;");
		
		st.execute("create table commande (" +
				"id_client char(20) not null," +
				"num_commande primary key," +
				"date Date," +
				"status char(20)" +
				");");
		System.out.println("created table commande;");
		
		st.execute("create table conteneur(" +
				"num_conteneur int primary key," +
				"transporteur char(20)," +
				"destination char(15)" +
				");");
		System.out.println("created table conteneur");
		
		st.execute("create table palette (" +
				"num_palette int primary key," +
				"num_conteneur int," +
				"foreign key(num_conteneur) references conteneur(num_conteneur)" +
				");");
		System.out.println("created table palette");
		
		st.execute("create table commande_produit (" +
				"num_commande int," +
				"id_produit char(16)," +
				"quantite int," +
				"foreign key(id_produit) references produit(id_produit)," +
				"foreign key(num_commande) references commande(num_commande)" +
				");");
		System.out.println("created table commande_produit");
		
		st.execute("create table colis (" +
				"num_colis int primary key," +
				"num_palette int," +
				"num_commande int," +
				"foreign key (num_palette) references palette(num_palette)," +
				"foreign key (num_commande) references commande(num_commande)" +
				");");
		System.out.println("created table colis");
		
		st.execute("create table produit_colis (" +
				"num_colis int," +
				"id_produit char(16)," +
				"quantite int," +
				"foreign key(num_colis) references colis(num_colis)," +
				"foreign key(id_produit) references produit(id_produit)"+
				");");
		System.out.println("created table produit_colis;");
		
	}
	
	public void delete_base() throws SQLException{
		st.execute("drop table utilisateur cascade;");
		System.out.println("dropped table utilisateur;");
		
		st.execute("drop table produit cascade;");
		System.out.println("dropped table produit;");

		st.execute("drop table commande cascade;");
		System.out.println("dropped table commande;");
		
		st.execute("drop table conteneur cascade;");
		System.out.println("dropped table conteneur");
		
		st.execute("drop table palette cascade;");
		System.out.println("dropped table palette");
		
		st.execute("drop table commande_produit cascade;");
		System.out.println("dropped table commande_produit");
		
		st.execute("drop table colis cascade;");
		System.out.println("dropped table colis;");
		
		st.execute("drop table produit_colis cascade;");
		System.out.println("dropped table produit_colis");
		
	}
	
	public void insert_example() throws SQLException{
		
		st.execute("insert into commande values(" +
				"\'SUROK77336\'," +
				"1," +
				"\'2012-06-12\'," +
				"\'non expedie\'" +
				");");
		
		st.execute("insert into commande_produit values(" +
				"1," +
				"\'GN-746901-SIY-63\'," +
				"17" +
				");");
		
		st.execute("insert into commande_produit values(" +
				"1," +
				"\'TL-338853-AIN-30\'," +
				"2" +
				");");
		//TODO Ajouter plus pour la soutenance
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		//Rase les tables puis les recree avec le fichier data donnée
		
		Manager m = new Manager();
		m.delete_base();
		m.create_base();
		Parse_file pf= new Parse_file("data.csv",m.conn);
		pf.parse(); // rempli la base avec les données dans data
		m.insert_example();
		m.conn.close();
	}
}
