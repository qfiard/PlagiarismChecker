public class CustomsDB
{
    /* checkID(): verifie l'identite du douanier dans la base */
    public static void checkID(QueryDB q, String id, String mdp){
	String rq="SELECT * FROM douane WHERE (id_douanier=" + id +" AND mdp=\'"+ mdp +"\');";
	q.requestDB(rq);
    }
}
