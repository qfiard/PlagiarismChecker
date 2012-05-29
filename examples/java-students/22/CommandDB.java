import java.sql.*;

public class CommandDB 
{
    /* getMaxIdCommand() : renvoie l'indice maximum d'une commande */
    public static void getMaxIdCommand(QueryDB q) 
    {
	String rq = "SELECT MAX(id_commande) FROM Commande;";
	q.requestDB(rq);
    }

    /* addCommand() : prend en argument tous les elements necessaire a
       l'insertion d'un nouveau champ dans la table Commande */
    public static void addCommand(QueryDB q, String[] d) 
    {
	String rq    = "INSERT INTO commande (id_commande, id_produit, "
	    +"id_client, prix_total, date_livraison, id_employe, etat, id_colis)"
	    +" VALUES (";
	rq += d[0]+",";
	rq += d[1]+",";
	rq += d[2]+",";
	rq += d[3]+",";
	rq += "\'"+d[4]+"\',";
	rq += d[5]+",";
	rq += "\'"+d[6]+"\',";
	rq += d[7]+");";
	q.updateDB(rq);
    }
    

    /* listCommand() : transmet la liste des commandes d'un client. */
    public static void listCommand(QueryDB q, String idClient){
	String rq= "SELECT * FROM commande WHERE id_client="
	    + idClient +";";
	q.requestDB(rq);
    }

    /* getCommandStatus() : donne l'état d'une commande */
    public static void getCommandStatus(QueryDB q, String idCustomer)
    {
	String rq = "SELECT * FROM commande WHERE id_client="
	    + idCustomer+" order by id_commande;";
	q.requestDB(rq);
    }

    /* listProduct(): liste les produits d'un colis */
    public static void listProduct(QueryDB q, String idPackage)
    {
	String rq="SELECT nom, id_produit FROM (commande NATURAL JOIN produit) WHERE id_colis=" + idPackage +";";
	q.requestDB(rq);
    }

    /* deliveryDate(): transmet la date limite de livraison d'un colis */
    public static void deliveryDate(QueryDB q, String idCmd)
    {
	String rq="SELECT date_livraison FROM commande WHERE id_commande=" +
	    idCmd +";";
	q.requestDB(rq);
    }

    /* updatePackage() : met à jour le champ id_colis */
    public static void updatePackage(QueryDB q, String idCmd, String idPack)
    {
	String rq = "update commande set id_colis="+idPack+"where id_commande="+
	    idCmd+";";
	q.updateDB(rq);
    }

    /* getStatusCmd() : transmet l'état de la commande */
    public static void getStatusCmd(QueryDB q, String idCmd)
    {
	String rq = "select etat from commande where id_commande="+idCmd+";";
	q.requestDB(rq);
    }
}