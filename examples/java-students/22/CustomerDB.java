import java.sql.*;

public class CustomerDB
{
    /* addCustomer() : créer un nouveau client dans la base */
    public static void addCustomer(QueryDB q, String[] d)
    {
	String rq = "INSERT INTO client (id_commande, id_produit, id_client,"+ 
	    "prix, date_livre, etat) VALUES (";
	rq += d[0]+ ",";
	rq += d[1]+ ",";
	rq += d[2]+ ",";
	rq += d[3] + ",";
	rq += "\'"+d[4]+"\'"+";";
	rq += "\'"+d[5]+"\');";
	System.out.println("blablablabla"+rq);
	q.updateDB(rq);
    }

    /* listCustomer() : transmet la liste de tous les clients */
    public static void listAllCustomer(QueryDB q)
    {
	String rq = "SELECT * FROM Client ORDER BY nom, prenom;";
	q.requestDB(rq);
    }

    /* listCustomerBySold() : transmet la liste de tous les clients, mais triée
       en fonction du nombre d'achats */
    public static void listCustomerBySold(QueryDB q)
    {
	String rq = "SELECT * FROM Client ORDER BY nb_cmd DESC;";
	q.requestDB(rq);
    }

    /* setLoginPswd() : change le login et/ou le mdp d'un client */
    public static void setLoginPswd(QueryDB q, String oldlog, String log,
				    String pswd)
    {
	String rq = "UPDATE client SET login=\'"+log+"\',mdp=\'"+pswd+"\' "+
	    "WHERE login=\'"+oldlog+"\';";
	q.updateDB(rq);
    }

    /* getIdCustomer() : renvoie l'id_client correspondant au login */
    public static void getIdCustomer(QueryDB q, String login)
    {
	String rq = "SELECT id_client FROM client WHERE login=\'"+login
	    + "\';";
	q.requestDB(rq);
    }

    /* checkIdCustomer() : vérifie que le client est bien présent dans la base*/
    public static void checkIdCustomer(QueryDB q, String log, String mdp)
    {
	String rq = "select * from client where login=\'"+log+"\' AND "
	    + "mdp=\'"+mdp+"\';";
	q.requestDB(rq);
    }

    /* updateNbCmd() : augmente le nombre de commandes faites par le client */
    public static void updateNbCmd(QueryDB q, String id_client)
    {
	String rq = "update client set nb_cmd=nb_cmd+1 where id_client="+
	    id_client+";";
	q.updateDB(rq);
    }
}