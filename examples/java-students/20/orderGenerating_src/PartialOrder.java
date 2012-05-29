import java.sql.SQLException;

/**
 * 
 * @author Joossen & Mathou
 *
 */
public class PartialOrder extends Order{
	
	private int numberOrder = 20;
	
	public PartialOrder(DataBaseConnection c) {
		super(c);
	}
	
	public void creationOrder() throws SQLException {
		super.creationOrder(this.numberOrder, 'n', 'y');
	}
	
}
