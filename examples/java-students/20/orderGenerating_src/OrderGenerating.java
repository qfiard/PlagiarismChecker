import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Joossen & Mathou
 * 
 */

public class OrderGenerating {

	public static void main(String[] args) throws IOException {
		
		String chemin;
		
		if(args.length >= 1) {
			chemin = args[0];
		} else {
			chemin = "/home/matthieu/Bureau/";
		}
		if(!chemin.endsWith("/")) {
			chemin += "/";
		}
		
		try {
			DataBaseConnection dbc = new DataBaseConnection();
			
			CompleteOrder cc = new CompleteOrder(dbc);
			cc.creationOrder();
			
			PartialOrder po = new PartialOrder(dbc);
			po.creationOrder();
			
			OpenOrder oo = new OpenOrder(dbc);
			oo.creationOrder();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
