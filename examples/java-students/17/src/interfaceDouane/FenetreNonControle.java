package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreNonControle extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;
	private maTable model;
	private JScrollPane scrollJT;
	private JPanel panelTable;
	private JTable jt;
	
	private ImageIcon icon = new ImageIcon("src/ic_menu_refresh.png");
	private JButton refresh = new JButton(icon);
	
	
	private String[] columnNames = {"id_commande", "id_clien", "quantite", "date_livraison", "prix_livraison"};
	
	
	public FenetreNonControle(){
		build();
	}
	
	public void build(){
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			/*ResultSet rs = st.executeQuery("SELECT id_commande, id_produit , quantite, cast(date_livraison as varchar), prix_livraison FROM " + 
					Constantes.base_commande + " natural join " + Constantes.base_colis + " WHERE statut IN('non-spec', 'expedie') ORDER BY id_commande;");*/
			
			ResultSet rs = st.executeQuery("SELECT id_commande FROM " + Constantes.base_commande + " WHERE etat IN ('partiellement-expediee', 'non-expediee') ORDER BY id_commande");

			if(rs.next()){
				rs.previous();
				while(rs.next()){

					int id = rs.getInt(1);
					
					ConnexionBD bd3 = new ConnexionBD();
					Statement stmt2 = bd3. createStatement();
					ResultSet rs3 = stmt2.executeQuery("SELECT id_commande, id_client, id_produit, quantite, cast(date_livraison as varchar), prix_livraison FROM " + 
							Constantes.base_commande + " natural join " + Constantes.base_contenuCommande + " WHERE id_commande = " + id + " ORDER BY id_commande ASC ;");
					
					
					
					
					
					if(rs3.next()){

						Object[] data = new Object[6];
						
						data[0] = rs3.getInt(1);			
						data[1] = rs3.getString(2);
						data[2] = rs3.getString(3);
						data[3] = rs3.getInt(4);
						data[4] = rs3.getString(5);
						data[5] = rs3.getInt(6);
						save.add(data);
					}
					
					bd3.close();
					stmt2.close();
					rs3.close();

				}
			}
			affichageTable(save);
			
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void affichageTable(LinkedList<Object[]> save){
		donnees = new Object[save.size()][];

		for(int i=0; i<donnees.length; i++){
			donnees[i] = save.get(i);
		}

		model = new maTable();
		model.setHeader(columnNames);
		model.setData(donnees);

		jt = new JTable(model);

		//panel de la table
		panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.add(jt.getTableHeader(), BorderLayout.PAGE_START);
		scrollJT = new JScrollPane(jt);
		panelTable.add(scrollJT, BorderLayout.CENTER);					
		panelTable.revalidate();
		panelTable.setPreferredSize(new Dimension(600,300));

		//bouton refresh
		refresh.setBorderPainted(false);
		refresh.setContentAreaFilled(false);
		refresh.addActionListener(this);

		//panel principal
		this.add(panelTable, BorderLayout.CENTER);
		this.add(refresh, BorderLayout.SOUTH);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == refresh){
			panelTable.setVisible(false);
			panelTable.remove(jt);
			model.clear();
			//this.remove(panelTable);
			this.revalidate();
			build();
			this.revalidate();

		}

	}
}
