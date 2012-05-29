import java.sql.*;

public class ProductDB 
{
    

    /* alterPrice() : modifie le prix du produit passé en argument */
    public static void alterPrice(QueryDB q, String idProduct, String price)
    {
	String rq = "UPDATE produit SET prix = "+price+" WHERE id_produit = " +
	    idProduct + ";";
	q.updateDB(rq);
    }
    
    
    /* mostSold() : établit la liste des produits les plus vendus */
    public static void mostSold(QueryDB q)
    {
	String rq = "SELECT * FROM produit ORDER BY nb_vente DESC;";
	q.requestDB(rq);
    }

    /* listAllProduct() : établit la liste de tous les produits */
    public static void listAllProduct(QueryDB q)
    {
	String rq = "SELECT * FROM produit ORDER BY nom;";
	q.requestDB(rq);
    }

    /* listAllAvailableProduct() : établit la liste de tous les produits 
       disponibles*/
    public static void listAllAvailableProduct(QueryDB q)
    {
	String rq = "SELECT * FROM produit WHERE stock > 0 ORDER BY nom;";
	q.requestDB(rq);
    }

    /* getProductPrice() : renvoie le prix d'un produit */
    public static void getProductPrice(QueryDB q, String item)
    {
	String rq = "select prix from produit where id_produit="+item+";";
	q.requestDB(rq);
    }

    /* updateProductQuantity() : met à jour la quantité d'un produit */
    public static void updateProductQuantity(QueryDB q, String idProd, int qty)
    {
	String rq = "update produit set stock=stock-"+qty+
	    "where id_produit="+idProd+";";
	q.updateDB(rq);
    }

    /* updateProductNbSell() : met à jour le nombre de vente d'un produit */
    public static void updateProductNbSell(QueryDB q, String idProd, int qty)
    {
	String rq = "update produit set nb_vente=nb_vente+"+qty+
	    " where id_produit="+idProd+";";
	q.updateDB(rq);
    }
    
    /* getTypeProduct() : renvoie le type d'un produit */
    public static void getTypeProduct(QueryDB q, String idCmd)
    {
	String rq = "select type from produit natural join commande where "+
	    "id_commande="+idCmd+";";
	q.requestDB(rq);
    }
}