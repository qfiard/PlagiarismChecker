import java.sql.*;

public class OwnerDB
{
    public static void checkIdOwner(QueryDB q, String log, String mdp)
    {
	String rq = "select * from gerant where nom=\'"+log+"\' AND "
	    + "mdp=\'"+mdp+"\';";
	q.requestDB(rq);
    }
}