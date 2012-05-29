package interfaceGerant;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreClient extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable jt;
	private JPanel panelTable;
	private String[] columnNames = { "ID Client", "Societe", "Depenses" };
	
	private Object[][] donnees;
	
	
	public FenetreClient(){

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {
			
			ResultSet rs = st.executeQuery("SELECT id_client, nom_societe, depenses FROM " + Constantes.base_client + " NATURAL JOIN " +
					"(SELECT id_client, sum(prix_livraison) as depenses FROM " + Constantes.base_commande + " " +
							"GROUP BY id_client) as depense_client ORDER BY depenses DESC;");
			
			try {
			
				LinkedList<Object[]> save = new LinkedList<Object[]>();


				while(rs.next()){
					Object[] data = new Object[6];

					data[0] = rs.getString(1);
					data[1] = rs.getString(2);
					data[2] = rs.getInt(3);

					save.add(data);

				}		

				donnees = new Object[save.size()][];

				for(int i=0; i<donnees.length; i++){
					donnees[i] = save.get(i);
				}

				//--Table

				DefaultTableModel tableModel = new DefaultTableModel (donnees, columnNames){
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int iRowIndex, int iColumnIndex)
					{
						return false;
					}
				};
				jt = new JTable(tableModel);

				//jt = new JTable(donnees, columnNames);
				//jt.repaint();
				//jt.setCellSelectionEnabled(false);
				


				panelTable = new JPanel();
				//panelTable.setVisible(true);

				panelTable.setLayout(new BorderLayout());

				panelTable.add(jt.getTableHeader(), BorderLayout.PAGE_START);
				//scrollJT = new JScrollPane(jt);
				panelTable.add(new JScrollPane(jt), BorderLayout.CENTER);				

				//panelTable.repaint();
				//scrollJT.revalidate();
				panelTable.revalidate();
				this.add(panelTable);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
