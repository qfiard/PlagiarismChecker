import java.sql.*;

public class ContainerDB
{

    /* listPrice(): liste les prix des produits transportes dans un conteneur*/
    public static void listPrice(QueryDB q, String idContainer)
    {
	String rq="SELECT p.nom, p.prix FROM (((conteneur NATURAL JOIN palette) NATURAL JOIN colis) NATURAL JOIN commande) NATURAL JOIN produit AS p WHERE id_conteneur=" + idContainer +";";
	q.requestDB(rq);
    }
}