import java.sql.SQLException;

/**
 * 
 * @author Joossen & Mathou
 *
 */
public class OpenOrder extends Order {
	
private int numberOrder = 50;
	
	public OpenOrder(DataBaseConnection c) {
		super(c);
	}
	
	public void creationOrder() throws SQLException {
		super.creationOrder(this.numberOrder, 'n', 'n');
	}

}
