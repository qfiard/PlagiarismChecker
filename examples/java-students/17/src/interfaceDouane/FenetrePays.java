package interfaceDouane;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetrePays extends JPanel{



	private static final long serialVersionUID = 1L;

	private JTable jt;
	private JScrollPane scroll;


	public FenetrePays(String idDouane, String dest){

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		try {

			ResultSet rsIdComm = st.executeQuery("SELECT id_commande, id_client, cast(date_livraison as varchar), prix_livraison FROM " +
					Constantes.base_commande + " natural join " + Constantes.base_client + " WHERE pays = '" + dest + "' AND etat = 'partiellement-expediee';");


			if(rsIdComm.next()){	

				rsIdComm.previous();

				String[] columnNames = { "id_commande", "id_client", "date livraison", "prix_livraison" };

				LinkedList<Object[]> save = new LinkedList<Object[]>();
				Object[][] donnees;

				while(rsIdComm.next()){
					
					Object[] data = new Object[4];

					data[0] = rsIdComm.getInt(1);

					data[1] = rsIdComm.getString(2);

					data[2] = rsIdComm.getString(3);

					data[3] = rsIdComm.getInt(4);


					save.add(data);
				}

				donnees = new Object[save.size()][];

				for(int i=0; i<donnees.length; i++){
					donnees[i] = save.get(i);
				}

				jt = new JTable(donnees, columnNames);	

				scroll = new JScrollPane(jt);

				this.setLayout(new BorderLayout());
				//this.add(jt.getTableHeader(), BorderLayout.PAGE_START);
				this.add(scroll, BorderLayout.CENTER);
				this.revalidate();
				this.setVisible(true);

			}
			
			
			st.close();
			bd.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
