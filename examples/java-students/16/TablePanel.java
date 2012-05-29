import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;


public class TablePanel extends JPanel{

	private JTable table;
	
	public TablePanel( TableModel model ){
		table = new JTable( model );

		setLayout( new BorderLayout() );
		add( new JScrollPane( table ), BorderLayout.CENTER );


	}
}