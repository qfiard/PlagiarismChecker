import java.sql.SQLException;

/**
 * @author Joossen & Mathou
 * 
 */
public class CompleteOrder extends Order {

	private int numberOrder = 180;

	public CompleteOrder(DataBaseConnection c) {
		super(c);
	}

	public void creationOrder() throws SQLException {
		super.creationOrder(this.numberOrder, 'y', 'y');
	}
}
