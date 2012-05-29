package interfaceClient;

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

public class LigneColis extends JPanel{

	private JTable produits;
	private int idColis;
	private JPanel panelTable;
	private JScrollPane scrollJT;

	private Object[][] donnees;
	private String[] champs = { "id_produit", "poids", "qualifiant", "prix", "description_produit", "reserve" };
	private String[] columnNames = { "ID", "Poids", "Qualifiant", "Prix", "Description", "Reserve" };
	

	public LigneColis(int idColis){

		this.idColis = idColis;

		LinkedList<Object[]> save = new LinkedList<Object[]>();

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();
		
		
		try {
			
			ResultSet rs = st.executeQuery("SELECT id_produit FROM " + Constantes.base_colis + " WHERE id_colis='" +
					idColis +"';");

			while(rs.next()){
				Object[] data = new Object[6];

				data[0] = rs.getString(1);
				data[1] = rs.getInt(2);
				data[2] = rs.getString(3).charAt(0);
				data[3]= rs.getInt(4);
				data[4]= rs.getString(5);
				data[5]= rs.getInt(6);

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
			
			produits = new JTable(tableModel);

			panelTable = new JPanel();
			panelTable.setVisible(false);

			panelTable.setLayout(new BorderLayout());

			panelTable.add(produits.getTableHeader(), BorderLayout.PAGE_START);
			scrollJT = new JScrollPane(produits);
			panelTable.add(scrollJT, BorderLayout.CENTER);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
