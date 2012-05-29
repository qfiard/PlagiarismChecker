import java.sql.*;

public class EmployeeDB 
{
    /* Affiche la liste complete de tous les employes. Choix possible du tri */
    public static void listAllEmployees(QueryDB q) 
    {
	String rq = "SELECT * FROM employe ORDER BY nom, prenom;";
	q.requestDB(rq);
    }

    /* Affiche soit les emballeurs soit les transporteurs. On peut choisir
       par rapport a quelle colonne on trie */
    public static void listSpecificEmployees(QueryDB q, String spec) 
    {
	String rq = "SELECT * FROM employe WHERE specialite=\'" + spec+"\'";
	rq       += " ORDER BY nom, prenom;";
	q.requestDB(rq);
    }

    /* Licencie un employe */
    public static void fireEmployee(QueryDB q, String idEmployee)
    {
	String rq = "DELETE FROM employe WHERE id_employe=" + idEmployee + ";";
	q.updateDB(rq);
    }

    /* verifie si l'employe appartient a la base*/
    public static void checkID(QueryDB q, String idEmployee, String mdp)
    {
	String rq= "SELECT * FROM employe WHERE (id_employe=" +idEmployee +" AND mdp=\'"+ mdp +"\');";
	q.requestDB(rq);
    }


    /*addProduitTraite(): incremente le champ prod_traite de la table employe de 1*/
    public static void addProduitTraite(QueryDB q, String idEmployee)
    {
	String rq="UPDATE employe SET prod_traite = prod_traite+1 WHERE id_employe=" + idEmployee +";";
	q.updateDB(rq);
    }

    /* addError(): incremente de 1 le nb d'erreurs commises par l'employe*/
    public static void addError(QueryDB q, String idColis)
    {
	String rq="UPDATE employe SET erreur = erreur + 1 WHERE id_employe IN (SELECT id_employe FROM commande NATURAL JOIN colis WHERE id_colis="+ idColis + ");";
	q.updateDB(rq);
    }

    /* updateNbProd() : +1 le nombre de "produit" traité par un employe */
    public static void updateNbProd(QueryDB q, String id_employe)
    {
	String rq = "update employe set prod_traite=prod_traite+1 where "+
	    "id_employe="+id_employe+";";
	q.updateDB(rq);
    }

    /* selectIdPacking() : renvoie l'id de l'emballeur ayant le moins de
       produits traités à son actif */
    public static void getIdPacker(QueryDB q)
    {
	String rq = "select id_employe from employe where "+
	    "specialite=\'emballeur\' and prod_traite=(select min(prod_traite)"
	    +"from employe where specialite=\'emballeur\');";
	q.requestDB(rq);
    }
}