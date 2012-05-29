
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import Share.*;

import Share.CoupleInt;

public class Query {
	Statement st ;
	Connection conn;

	public Query(String login, String password) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");

		conn = DriverManager.getConnection("jdbc:postgresql://nivose.informatique.univ-paris-diderot.fr/" + login, login, password);
		
		//conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/evrard", "iskeinder", "");
		st = conn.createStatement();

	}


	/** 
	 * Ferme la connection
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		st.close();
		conn.close();
	}


	/**
	 * Permet de verifier le pwd de l'utilisateur dans la base de donnée
	 * @param login 
	 * @param password
	 * @return couple d'entier, avec id =-1 si login inexistant, id = -2 si mot de passe incorrect
	 * @throws SQLException 
	 */
	public CoupleString lookUser(String login, String password) throws SQLException {
		String id;
		ResultSet res;
		res = st.executeQuery("SELECT number_user, status  FROM users WHERE login = '" + login + "' AND password = '" + password + "';");
		if (!res.next())  
			return new CoupleString("-1", "-2"); 
		String id_user[] = res.getString("status").split("%");
		id = res.getString("number_user");
		// employé renvoyé
		if (id_user[0] == "2 1" || id_user[0] == "4 1") 
			id = "-2";

		return new CoupleString(id, id_user[0].charAt(0) + ""); 


	}

	/**
	 * Change le login et le password actuel par les argument
	 * C'est l'inteface graphique qui vérifie le mot de passe par le biais de lookUser
	 * @param id id de l'utilisateur
	 * @param newLogin Nouveau login souhaiter
	 * @param newPassword Nouveau password souhaiter
	 * @throws SQLException 
	 */
	public void changeLoginOrPassword(String number, String newLogin, String newPassword) throws SQLException {
		st.executeUpdate("UPDATE users SET login="+newLogin+", password="+newPassword+" WHERE number_user='"+number+"';");
	}


	public void changeDate(String date, String id_user,int id_product) throws SQLException {
		ResultSet r= st.executeQuery("SELECT list FROM users WHERE number_user='"+id_user+"';");
		String buff= r.getString(1);
		Tools.changeDate(buff, id_product, date);

	}

	/**
	 * Verifie si le login existe dans la base de donné
	 * @param login le login a vérifier
	 * @return true si le login existe, false sinon.
	 * @throws SQLException
	 */
	public boolean loginExist(String login) throws SQLException {
		ResultSet r= st.executeQuery("SELECT count(*) FROM users WHERE login='"+login+"';");
		if(r.getInt(1)!=0)
			return true;
		return false;
	}

	/**
	 * Verifie si un numero de client existe dans la table
	 * @param number le numéro a tester
	 * @return true si le numero existe, false sinon
	 * @throws SQLException
	 */
	public boolean numberExist(String number) throws SQLException {
		ResultSet r= st.executeQuery("SELECT count(*) FROM users WHERE number_user='"+number+"';");
		if(r.getInt(1)!=0)
			return true;
		return false;
	}


	/*public void addUser(String name, String first_name, String login, String password, String i, String adress, String phone_number, String number) throws SQLException {

		st.executeUpdate("INSERT INTO users VALUES ('"+name+"','"+first_name+"','"+login+"','"+password+"',"+i+",'"+adress+"','"+phone_number+"','"+number+"');");
	}*/

	/**Ajoute un utilisateur dans la table user
	 * @param values La chaine de caractere au format sql
	 * @throws SQLException
	 */
	public void addUser(String values) throws SQLException {
		st.executeUpdate("INSERT INTO users VALUES " + values);
	}

	/**
	 * Ajoute un produit a la base donnée
	 * @param id_product id du produit
	 * @param des la description du produit
	 * @param q la quantité par colis
	 * @param prix le prix du produit
	 * @param cara la caracteristique du produit
	 * @param poid le poid du produit
	 * @param stock le stock de produit
	 * @throws SQLException
	 */
	public void addProduct(String values) throws SQLException {
		st.executeUpdate("INSERT INTO product VALUES "+ values);

	}


	/**
	 * Vire l'utilisateur, ne peux etre utilisé que par le gérant 
	 * ou l'utilisateur qui supprime son compte 
	 * ATTENTION: verifier identité partie graphique avec lookUser
	 * @param id id de l'utilisateur a virer
	 * @throws SQLException 
	 */
	public void fireUser(String id) throws SQLException {
		ResultSet r = st.executeQuery("SELECT status FROM users WHERE number_user='"+id+"';");
		String status = r.getString("status");
		st.executeUpdate("UPDATE users SET status= '" + status + " 1' WHERE number_user='"+id+"';");
	}



	/*
	public String leftSpace(int id) {
		// Une fonction qui calcule la place restante dans un package 
	}

	public boolean testSpace(int id_package, int id_product) {
		// Une fonction qui retourne true si il est possible de placer le produite id_produit dans le package
	}
	 */




	/**
	 * Ajoute une commande dans la table user
	 * @param id id du client
	 * @param order chaine de caractere representant la commande au format "id_order+JJMMAAAA % commande suivante"
	 * @throws SQLException
	 */
	//public void addOrder(String id,String order) throws SQLException {
	//st.executeUpdate("UPDATE users SET list=list+'"+order+"' WHERE id_order="+id+";");
	//}

	/**
	 * Renvoie la moyenne de colis traiter par un emballeur par jour
	 * @param name Nom de l'emballeur
	 * @param first_name prenom de l'emballeur
	 * @param number numero de l'emballeur
	 * @return Un double qui est la moyenne de colis emballés
	 * @throws SQLException
	 */
	public double statPacker(String name, String first_name, String number) throws SQLException {
		ResultSet r= st.executeQuery("SELECT list FROM users WHERE name_user='"+name+"'AND first_name='"+first_name+"'AND number_user='"+number+"';");
		String s = r.getString(1);
		return (new Tools()).getAverageList(s);
	}


	/**
	 * Pour la colonne stock on a stock + nombre de produit vendu
	 * Retourne un string de la forme "id id id id id" contenant les produits les plus vendus
	 * @return
	 * @throws SQLException
	 */
	public String bestSell() throws SQLException {
		int a,max=0;
		String res="", s_max= "";
		String [] buffer= new String[5];
		ResultSet r = st.executeQuery("SELECT count(*) FROM product;");
		a= r.getInt(1);
		String [][] tab= new String [a][2];
		r = st.executeQuery("SELECT stock FROM product;");
		while(r.next()) {
			tab[a][0]=r.getString(1).split("+")[0];
			tab[a][1]=r.getString(1).split("+")[1];
			a--;
		}
		for (int j=0; j<5;j++) {
			max=0;
			for (int i =0; i<tab.length; i++) {
				if (Integer.parseInt(tab[i][1])>max && !Tools.contains(buffer, tab[i][0])) {
					max=Integer.parseInt(tab[i][1]);
					s_max=tab[i][0];
				}
			}
			res+=s_max+" ";
			buffer[j]=s_max;
		}
		return res;
	}

	/**
	 * Retourne les cinq clients les plus depensiers sous forme de tableau de couple d'int
	 * de la forme (id, argent dépenser)
	 * @return
	 * @throws SQLException
	 */
	public CoupleInt[] bestCustomer() throws SQLException {
		ResultSet r= st.executeQuery("SELECT number_user, list FROM users WHERE status=1;");
		HashMap<Integer, String> m =new HashMap<Integer, String>();
		while (r.next()) {
			m.put(r.getInt(1), r.getString(2));
		}
		return Tools.bestCustomer(m);

	}

	/**
	 * Retourne l'etat ("non expedié","expedié","100% expedié") de chaque commande de l'utilisateur
	 * @param number numero du client
	 * @return un String de la forme "Commande : id_order, Statut : "a définir" "
	 * @throws SQLException
	 */
	public String packageStatus(String number) throws SQLException {
		ResultSet r= st.executeQuery("SELECT id_order, status FROM orders WHERE owner='"+number+"';");
		String res="";
		while(r.next()) {
			res+="Commande :"+r.getString(1)+", Statut: "+r.getString(2)+"\n";
		}
		return res;
	}

	/**
	 * Renvoie les produits disponibles et leur prix 
	 * @return Un String de la forme " id: prix \n" a chaque ligne
	 * @throws SQLException
	 */
	public String productAvailable() throws SQLException {
		ResultSet r= st.executeQuery("SELECT id_product, price FROM product WHERE stock !=0");
		String res="";
		while(r.next()) {
			res += r.getString(1)+": "+r.getString(2)+"\n ";
		}
		return res;
	}



	/**
	 * Retourne la liste des clients et des employes de la table user,
	 * ne doit etre utilisable que par le gerant
	 * @return Un string contenant "nom prenom" suivi du caractere de saut de ligne
	 * @throws SQLException
	 */
	public String getListBoss() throws SQLException {
		String res="";
		ResultSet r= st.executeQuery("SELECT name_user, first_name, number_user FROM users WHERE status!=0");
		while(r.next()) {
			res+=r.getString(1)+" "+r.getString(2)+" "+ r.getInt(3)+"\n";
		}
		return res;
	}

	/**
	 * Retourne les infos sur un utilisateur
	 * @param name le nom de l'utilisateur recherché
	 * @param first_name le prenom de l'utilisateur recherché
	 * @param number le numero de l'utilisateur recherché
	 * @return Une chaine de caractere contenant toute les colonnes excepté celles de l'id
	 * @throws SQLException
	 */
	public String getUserInfo(String number_user) throws SQLException {
		ResultSet r= st.executeQuery("SELECT * FROM users WHERE number_user='"+number_user+"';" );
		r.next();
		return r.getString("name_user")+" "+r.getString("first_name")+" "+r.getString("login")+" "+r.getString("password")+" "+r.getString("status")+" "+
		r.getString("adress")+" "+r.getString("phone_number")+" "+r.getString("number_user")+" "+r.getString("list");
	}

	/**
	 * Retourne la liste des commandes d'un client
	 * @param number numero du client concerné
	 * @return Un string de la forme " 1645455 \n "
	 * @throws SQLException
	 */
	public String getCustomerOrder(String number) throws SQLException {
		ResultSet r= st.executeQuery("SELECT list FROM users WHERE number_user='"+number+"';");
		String s= r.getString(1);
		String [] tab=s.split("%");
		s="";
		for (int i=0; i<tab.length; i++) {
			tab[i]=tab[i].split("+")[0];
		}
		for (int i=0; i<tab.length; i++){
			s+=tab[i]+" \n" ;
		}
		return s;
	}

	/**
	 * Retourne la date d'expiration d'une commande
	 * @return
	 * @throws SQLException
	 */
	public String getExpirationDate() throws SQLException {
		ResultSet r= st.executeQuery("SELECT expiration_date FROM orders; ");
		return r.getString(1);
	}

	/**
	 * Retourne le prix, la description, la caracteristique (cassant, toxique, etc...) et l'id d'un produit donné
	 * @param id L'id du produit a chercher 
	 * @return Un tableau de longueur 4 de la forme [prix][description][character][id]
	 * @throws SQLException
	 */
	public String [] getProduct(String id) throws SQLException {
		ResultSet r= st.executeQuery("SELECT price, description, characteristic FROM product WHERE id_product='"+id+"';");
		String [] s = new String[0];
		if (!r.next()) 
			return s;
		s = new String[4];
		for (int i=0; i<3;i++) {
			s[i]=r.getString(i+1);
		}
		s[3] = id;
		return s;
	}



	/**
	 * Change le statut de la commande d'id "id" selon la valeur de "a"
	 * @param id L'id de la commande a modifier
	 * @param a La valeur à donner au statut de la commande. 1 pour "non expédié", 2 pour "partiellement expédié"
	 * , 3 pour "100% expédié"
	 * @throws SQLException
	 */
	public void changeOrderStatus(String id, int a, String motif) throws SQLException {
		switch(a) {
		case 1 :
			st.executeUpdate("UPDATE package SET reason='"+motif+"',status='non expedie' WHERE id_pack="+id+";");
			break;
		case 2 :
			st.executeUpdate("UPDATE package SET reason='"+motif+"',status='partiellement expedie' WHERE id_pack="+id+";");
			break;
		case 3 :
			st.executeUpdate("UPDATE package SET reason='"+motif+"',status='100% expedie' WHERE id_pack="+id+";");
			break;
		}

	}
	
	/**
	 * Permet d'avoir la liste des colis à partir du numéro de commande
	 * @param id_order numéro de la commande
	 * @return liste de colis
	 * @throws SQLException 
	 */
	public String[] getOrderContent(int id_order) throws SQLException {
		ResultSet r;
		r = st.executeQuery("SELECT count(*) FROM package WHERE id_order = '" + id_order + "';");
		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_pack FROM package WHERE id_order = '" + id_order + "';");
		res[0] = "COLIS de " + id_order;
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pack");

		return res;

	}

	/**
	 * Retourne un tableau de String contenant les id des colis contenu dans le colis (ou palette)
	 * @param id_pallet L'id du palette dont on veut savoir le contenu
	 * @return Un tableau de String de la forme [COLIS][id1][id2][id3]..
	 * @throws SQLException géré par <code>Customs</code>
	 */
	public String[] getPalletContent(int id_pallet) throws SQLException {
		
		ResultSet r;
		r = st.executeQuery("SELECT count(*) FROM package WHERE id_pallet="+ id_pallet +" AND status = 'partielement expedie' AND reason = 'non';");

		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_pack, characteristic FROM package WHERE id_pallet="+ id_pallet +" AND status = 'partielement expedie' AND reason = 'non';");
		res[0] = "COLIS";
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pack") + " " + r.getString("characteristic");

		return res;
		
		// Envoie la liste de produit 
		/*
		ResultSet r;
		r = st.executeQuery("SELECT count(*) FROM package WHERE id_pallet="+ id_pallet +" AND status = 'partielement expedie';");

		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_pack FROM package WHERE id_pallet="+ id_pallet +" AND status = 'partielement expedie';");
		res[0] = "COLIS";
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pack");

		return res;
		*/
	}
	/**
	 * Retourne un tableau de String contenant les id des produits  contenu dans le colis (ou palette)
	 * @param id_package L'id du colis/palette dont on veut savoir le contenu
	 * @return Un tableau de String de la forme [PRODUIT/COLIS][id1][id2][id3]..
	 * @throws SQLException
	 */
	public String[] getPackageContent(int id_package) throws SQLException {
		// On recupere les id des produits contenu dans un colis par le biais de la table in_package
		ResultSet r;
		r = st.executeQuery("SELECT count(*) FROM in_package WHERE id_pack="+ id_package +";");

		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_product, amount FROM in_package WHERE id_pack="+ id_package +";");
		res[0] = "PRODUITS";
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_product") + " fois " + r.getString("amount");

		return res;

		/*
		ResultSet r= st.executeQuery("SELECT content FROM package WHERE id_pack="+id_package+";");
		ResultSet x=st.executeQuery("SELECT id_transporter FROM package WHERE id_pack="+id_package+";");
		String buff= r.getString(1);
		String[] a,s;
		a=buff.split("%");
		buff=x.getString(1);
		s=new String[a.length+1];
		if (!(buff.equals("-1") || buff.equals("-2") || buff.equals("-3")))
			s[0]="PRODUIT";
		else 
			s[0]="COLIS";
		for (int i=0; i<a.length;i++) {
			s[i+1]=a[i].split("+")[0];
		}
		return s;
		 */
	}

	/**
	 * Retourne une tableau de string contenant la liste des id des palettes transportés selon un certain mode
	 *  de transport
	 * @param a Le critère de recherche : "-1" pour avion, "-2" pour bateau, "-3" pour train
	 * @return Un tableau de String contenant un id par case
	 * @throws SQLException
	 */
	public String [] getByTransportType(String a, String number_user) throws SQLException {
		if (a.equals("1")) a = "avion";
		if (a.equals("2")) a = "bateau";
		if (a.equals("3")) a = "camion";
		ResultSet r;
		r = st.executeQuery("SELECT adress FROM users WHERE number_user = '" + number_user + "';");
		r.next();	
		String customs = r.getString("adress");
		r = st.executeQuery("SELECT count(*) FROM pallet WHERE transport = '" + a + "' AND country = '" + customs + "';");
		
		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_pallet FROM pallet WHERE transport='"+a+"' AND country = '" + customs + "';" );
		res[0] = "PALETTES";
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pallet");

		return res;
		
	}


	/**
	 * Permet d'ajouter un message à la table Message
	 * @param id_to l'identifiant de l'utilisateur 
	 * @param id_from l'identifiant de l'auteur (-1 si douane)
	 * @param title Titre du message 
	 * @param message 
	 */
	public void sendMessage(int id_to, int id_from, String title, String message) {
		// faire la requête, les id ne peuvent être que correct 
	}


	public String[] getPackageIn(String content) throws SQLException {
		ResultSet r;
		r = st.executeQuery("SELECT DISTINCT in_package.id_pack FROM in_package JOIN product ON description LIKE '%" + content + "%' JOIN package ON status = 'partielement expedie' AND reason = 'non';");
		int size = 0;
		while (r.next())
			++size;
		String [] res = new String[size];
		if (size == 0)
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT DISTINCT in_package.id_pack FROM in_package JOIN product ON description LIKE '%" + content + "%' JOIN package ON status = 'partielement expedie' AND reason = 'non';");
		res[0] = "Avec " + content;
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pack");

		return res;
	}


	public String[] getPackageTo(String country) throws SQLException {
		
		ResultSet r;
		r = st.executeQuery("SELECT count(*) FROM in_package INNER JOIN orders ON adress LIKE '" + country + "';");
		String [] res = new String[0];
		if (!r.next())
			return res;
		res = new String[Integer.parseInt(r.getString(1)) + 1];
		r = st.executeQuery("SELECT id_pack FROM in_package INNER JOIN orders ON adress LIKE '" + country + "';");
		res[0] = "Avec " + country;
		int i = 1;
		while (r.next())
			res[i++] = r.getString("id_pack");

		return res;
	}

}