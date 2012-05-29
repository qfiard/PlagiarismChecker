import java.sql.*;

public class PackageDB 
{
    /* typePackage(): transmet le type d'un colis */
    public static void typePackage(QueryDB q, String idPackage) 
    {
	String rq="SELECT type FROM colis WHERE id_colis =" + idPackage +";";
	q.requestDB(rq);
    }

    /* deliveryDate(): transmet la date limite de livraison d'un colis */
    public static void deliveryDate(QueryDB q, String idPackage) 
    {
	String rq="SELECT date_livraison FROM colis NATURAL JOIN commande WHERE id_colis=" + idPackage +";";
	System.out.println(rq);
	q.requestDB(rq);
    }

    /* listProduct(): liste les produits d'un colis */
    public static void listProduct(QueryDB q, String idPackage)
    {
	String rq="SELECT nom, id_produit FROM colis NATURAL JOIN commande NATURAL JOIN produit WHERE id_colis=" + idPackage +";";
	q.requestDB(rq);
    }

    /* returnPackage(): renvoie avec un motif la totalite d'un colis */
    public static void returnPackage(QueryDB q, String idPackage, String reason)
    {
	String rq="UPDATE colis SET statut='renvoye', comment=\'" +reason +"\' WHERE id_colis=" + idPackage +";";
	q.updateDB(rq);
    }

    /* addPackage(): ajoute un colis a la base de donnees */
    public static void addPackage(QueryDB q, String dateEmballage)
    {
	String rq="INSERT INTO colis (date_emballage) VALUES(\'"+ 
	    dateEmballage+"\');";
	q.updateDB(rq);
    }

    /* createPackage() : créé un colis associé à une commande */
    public static void createPackage(QueryDB q, String idCmd, String date,
				     String status)
    {
	String rq ="insert into colis (id_commande, date_emballage, statut) "
	    +"values ("+idCmd+", \'"+date+"\',\'"+status+"\');";
	q.updateDB(rq);
    }

    /* listPackage(): liste les colis d'une palette*/
    public static void listPackage(QueryDB q, String idPalette)
    {
	String rq="SELECT id_colis FROM colis WHERE id_palette=" + idPalette 
	    +";";
	q.requestDB(rq);
    }

    /* getIdPackage() : renvoie l'id_colis associé à l'id_commande */
    public static void getIdPackage(QueryDB q, String idCmd)
    {
	String rq = "select id_colis from colis where id_commande="+idCmd+";";
	q.requestDB(rq);
    }

    /* updateType() : met à jour le type du colis d'une commande */
    public static void updateType(QueryDB q, String idCmd)
    {
	boolean isDanger  = false;
	boolean isFragile = false;
	
	String rq = "select type from produit natural join commande"+
	    " where id_commande="+idCmd+";";
	q.requestDB(rq);
	String type;
	try
	    {
		q.rst.next();
		type = q.rst.getString(1);
		while (type != null)
		    {
			if (!isDanger || !isFragile)
			    {
				if (type.equals("fragile"))
				    isFragile = true;
				if (type.equals("dangereux"))
				    isDanger  = true;
				q.rst.next();
				type = q.rst.getString(1);
			    }
			else
			    break;
		    }
	    }
	catch (Exception e)
	    {
		// on appel jusqu'à ce qu'une erreur se produise 
		// => plus de lignes à lire !
	    }
	
	/* On détermine le type du produit ! */
	if (isDanger && isFragile)
	    type = "\'dangereux-fragile\'";
	else if (isDanger)
	    type = "\'dangereux\'";
	else if (isFragile)
	    type = "\'fragile\'";
	else
	    type = "\'neutre\'";
	
	rq = "update colis set type="+type+
	    "where id_commande="+idCmd+";";
	q.updateDB(rq);
    }
}
