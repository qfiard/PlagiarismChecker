import java.sql.*;

public class QueryDB
{
    public Statement   st;
    public ResultSet   rst;
    public int         res;
    
    /* Classe qui nous permet d'ouvrir un Statement qui va pouvoir recevoir
       des requêtes */
    public QueryDB(ConnectionDB c)
    {
	try
	    {
		st = c.conn.createStatement();
	    }
	catch (Exception e)
	    {
		System.out.println("Enable to connect database :\n\t" + e);
	    }
    }



    /* Fonction qui permet de manipuler des tables dans la base */
    public void operationDB(String odb)
    {
	try
	    {
		st.execute(odb);
	    }
	catch (Exception e)
	    {
		System.out.println("Enable to perform operation :\n\t" + e);
	    }
    }


    /* Fonction qui permet de stocker le résultat d'une requête */
    public void requestDB(String rq)
    {
	try
	    {
		rst = st.executeQuery(rq);
	    }
	catch (Exception e)
	    {
		System.out.println("Request failed :\n\t" + e);
	    }
    }
        

    /* Fonction qui permet de faire des màj dans les tables */
    public void updateDB(String upt)
    {
	try
	    {
		res = st.executeUpdate(upt);
	    }
	catch (Exception e)
	    {
		System.out.println("Enable to proceed update :\n\t" + e);
	    }
    }
    


    /* Fonction fermant un Statement donc le QueryDB */
    public void close()
    {
	try
	    {
		this.st.close();
	    }
	catch (Exception e)
	    {
		System.out.println("Error while trying to close QueryDB\n\t"
				   + e);
	    }
    }
}