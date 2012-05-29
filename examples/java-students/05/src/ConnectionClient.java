import java.io.*;
import java.sql.*;
import java.util.*;
import java.sql.Date;

class ConnectionClient {
    ResultSet rs;
    PreparedStatement suivi; 
    PreparedStatement disponible;
    Connection conn;
    Scanner sc = new Scanner(System.in);


    public ConnectionClient(String login, String motPasse) throws SQLException, ClassNotFoundException{
	// -------------------
	// Connexion a la base
	// --------------------
	
	Class.forName("org.postgresql.Driver");
	conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);
    }
    
    public void suiviColis() throws SQLException{
	
	suivi = conn.prepareStatement("select num_commande, etats from commande natural join colis where num_commande = ?");
	System.out.println("Entrez votre numero commande : ");
	int num_commande = sc.nextInt();
	suivi.setInt(1,num_commande);
	rs=suivi.executeQuery();
	while(rs.next()){
	    int num = rs.getInt("num_commande");
	    String etats = rs.getString("etats");
	    System.out.println("numero commande : "+num+"\netats du colis : "+etats);
	}
	suivi.close();
	

    }
    public void produitDisponible() throws SQLException{
	
	disponible = conn.prepareStatement("select description, quantite, caracteristique, disponible from produit where disponible = 'DISPONIBLE'");
	rs=disponible.executeQuery();
	while(rs.next()){
	    String desc = rs.getString("description");
	    String caracteristique = rs.getString("caracteristique");
	    String stock = rs.getString("disponible");
	    int quantite = rs.getInt("quantite");
	    System.out.println("Description du produit : "+desc);
	    System.out.println("Caractéristique du produit :"+caracteristique);
	    System.out.println("Quantité : "+quantite);
	    System.out.println("Stock : "+stock);
	}
	disponible.close();
    }
    public void changementDate() throws SQLException{

	suivi = conn.prepareStatement("update commande set date_de_livraison = ? where num_client = ? and num_commande = ?");
	System.out.println("Entrez votre numero de client : ");
	int num_client = sc.nextInt();
	suivi.setInt(2,num_client);
	System.out.println("Entrez le numero de commande sur laquelle vous souhaitez changer la date de livraison : ");
	int num_commande = sc.nextInt();
	suivi.setInt(3,num_commande);
	System.out.println("Choisissez une date de livraison :");
	String date = sc.next();
	suivi.setString(1,date);
	rs=suivi.executeQuery();
	while(rs.next()){
	    String date_livr = rs.getString("date_de_livraison");
	    int commande = rs.getInt("num_commande");
	    int client = rs.getInt("num_client");
	    System.out.println("La nouvelle date de livraison de la commande "+commande+" est "+date_livr);

	}
	suivi.close();
    }
    public void  listeCommand() throws SQLException{
	suivi = conn.prepareStatement("select nombre_de_commande from commande where num_client = ?");
	System.out.println("Entrez le numero de client : ");
	int num_client = sc.nextInt();
	suivi.setInt(1,num_client);
	rs=suivi.executeQuery();
	while(rs.next()){
	    int nbr_commande = rs.getInt("num_client");
	    int client = rs.getInt("num_client");
	    System.out.println("Liste des commande du client "+client+" : "+nbr_commande);
	}
	

    }
    
    public void etatColis() throws SQLException{
	suivi = conn.prepareStatement("select etats, num_commande from colis");
	rs = suivi.executeQuery();
	while(rs.next()){
	    int num = rs.getInt("num_commande");
	    String etats = rs.getString("etats");
	    System.out.println("Etats du colis : "+etats+" de la commande "+num);
	}
    }
     
    
    public void dateLivraison() throws SQLException{
	suivi = conn.prepareStatement("select num_commande, num_client, date_de_livraison from colis where num_client = ?");
	System.out.println("Entrez le numéro d'un client :");
	int num = sc.nextInt();
	suivi.setInt(1,num);
	rs=suivi.executeQuery();
	while(rs.next()){
	    int com = rs.getInt("num_commande");
	    int client = rs.getInt("num_client");
	    String date = rs.getString("date_de_livraison");
	    System.out.println("Numero de commande : "+com);
	    System.out.println("Numero de client : "+client);
	    System.out.println("Date de limite de livraison : "+date);
	}

    }



    //Methode de la douane
    public void listeDesCommandes() throws SQLException{
	suivi = conn.prepareStatement("select code_colis from colis natural join douane where etats = 'EXPEDIE' and pays = ?");
	System.out.println("Entrez le pays : ");
	String pays = sc.next();
	suivi.setString(1,pays);
	rs = suivi.executeQuery();
	while(rs.next()){
	    String colis = rs.getString("code_colis");
	    System.out.println("Le colis expedié est : "+colis);
	}
     
	
    }
    public void commandeCon() throws SQLException{
	suivi = conn.prepareStatement("select code_colis from douane natural join verifier where controle = 'OUI' ");
	rs = suivi.executeQuery();
	while(rs.next()){
	    String com = rs.getString("code_colis");
	    System.out.println("Commande verifiée : "+com);
	}
    }
    public void commandeNONCon() throws SQLException{
	suivi = conn.prepareStatement("select code_colis from colis natural join verifier where etats = 'EXPEDIE' and controle = 'NON'");
	rs = suivi.executeQuery();
	while(rs.next()){
	    String com = rs.getString("code_colis");
	    System.out.println("Commande non controlées : "+com);
	}
    }
    public void rechercheParContenu() throws SQLException{
	suivi = conn.prepareStatement("select * from commande where description LIKE  '%?%' ");
	System.out.println("Entrez un mot du contenu : ");
	String contenu = sc.next();
	suivi.setString(1,contenu);
	rs = suivi.executeQuery();
	while(rs.next()){
	    int num_prod = rs.getInt("num_produit");
	    String desc = rs.getString("description");
	    int quan = rs.getInt("quantite");
	    float prix = rs.getFloat("prix");
	    int num_comm = rs.getInt("num_commande");
	    int num_client = rs.getInt("num_client");
	    int code  = rs.getInt("code_colis");
	    int nombre_com  = rs.getInt("nb_commande");
	    String date_co = rs.getString("date_commande");
	    String date_livr  = rs.getString("date_livraison");	    
	    
	    System.out.println("Numero de produit : " +num_prod);
	    System.out.println("Contenu : " +desc);
	    System.out.println("Quantité : " +quan);
	    System.out.println("Prix : " +prix);
	    System.out.println("Numero de commande : " +num_comm);
	    System.out.println("Numero de client : " +num_client);
	    System.out.println("Code colis : "+code);
	    System.out.println("Nombre de commande : "+nombre_com);
	    System.out.println("Date de commande : "+date_co);
	    System.out.println("Date de livraison : "+date_livr);
	}

    }
    public void rechercheParCommande() throws SQLException{
	suivi = conn.prepareStatement("select * from commande where num_commande = ?");
	System.out.println("Entrez le numéro de la commande");
	int num_com = sc.nextInt();
	suivi.setInt(1,num_com);
	rs = suivi.executeQuery();
	while(rs.next()){
	    int num_prod = rs.getInt("num_produit");
	    String desc = rs.getString("description");
	    int quan = rs.getInt("quantite");
	    float prix = rs.getFloat("prix");
	    int num_comm = rs.getInt("num_commande");
	    int num_client = rs.getInt("num_client");
	    int code  = rs.getInt("code_colis");
	    int nombre_com  = rs.getInt("nb_commande");
	    String date_co = rs.getString("date_commande");
	    String date_livr  = rs.getString("date_livraison");	    
	    
	    System.out.println("Numero de produit : " +num_prod);
	    System.out.println("Description : " +desc);
	    System.out.println("Quantité : " +quan);
	    System.out.println("Prix : " +prix);
	    System.out.println("Numero de commande : " +num_comm);
	    System.out.println("Numero de client : " +num_client);
	    System.out.println("Code colis : "+code);
	    System.out.println("Nombre de commande : "+nombre_com);
	    System.out.println("Date de commande : "+date_co);
	    System.out.println("Date de livraison : "+date_livr);
	}
    }
    //Methode du gerant
    public void changePrice() throws SQLException {
	suivi = conn.prepareStatement("update produit set prix = ? where mum_produit = ? ");
	System.out.println("Entrez un numéro de produit est : ");
	int numprod = sc.nextInt();
	suivi.setInt(2,numprod);
	System.out.println("Entrez le nouveau prix du produit :");
	int prix = sc.nextInt();
	suivi.setInt(1,prix);
	rs = suivi.executeQuery();
	while(rs.next()){
	    
	    int price = rs.getInt("prix");
	    System.out.println("Le nouveau prix du produit est : "+price);
	    
	}
	
    }
    public void licenciement() throws SQLException{
	suivi = conn.prepareStatement("delete from employe where nom = ?");
	System.out.println("Entrer le nom de l'employé que vous voulez supprimer :");
	String nom = sc.next();
	suivi.setString(1,nom);
	rs = suivi.executeQuery();
	while(rs.next()){
      	    String nom_client = rs.getString("nom");
	    System.out.println("L'employé supprimé est :"+nom_client);
	}
	
    }
    public void licenciementEmployeTauxDerreur() throws SQLException{
		suivi = conn.prepareStatement("delete from emballeur where taux_erreurs > 50");
		rs = suivi.executeQuery();
		while(rs.next()){
		int client = rs.getInt("num_client");
		System.out.println("L'employé licencié est "+client);
		}
    }
    public void listeEmploye() throws SQLException{
	suivi = conn.prepareStatement("select nom, prenom from employe");
	rs = suivi.executeQuery();
	while(rs.next()){
	    String nom = rs.getString("nom");
	    String prenom = rs.getString("prenom");
	    System.out.println("NOM : "+nom+" PRENOM :"+prenom);

	}
    }
    public void listeClient() throws SQLException{
	suivi = conn.prepareStatement("select nom_societe from client");
	rs = suivi.executeQuery();
	while(rs.next()){
	    String nom = rs.getString("nom_societe");
	    System.out.println("ENTREPRISE : "+nom);
	}
    }
   
  
    public void clientDepensier() throws SQLException{
	suivi = conn.prepareStatement("select nom_societe from client natural join commande where nb_commande  = (select max (nb_commande) from commande)");
	rs = suivi.executeQuery();
	while(rs.next()){
	    String nom = rs.getString("nom_societe");
	    System.out.println("Client le plus dépensier : "+nom);
	}
    }
    public void close() throws SQLException{ 
	conn.close();
    }
    
}