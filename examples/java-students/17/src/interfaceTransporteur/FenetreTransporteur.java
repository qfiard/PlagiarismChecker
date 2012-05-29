package interfaceTransporteur;

import interfaceClient.JSelectionButton;
import interfaceGraphique.FenetreConnexion2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreTransporteur extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;
	private JPanel panelTable;
	private JTable jt;
	//private maTable model;

	private ImageIcon icon = new ImageIcon("src/ic_menu_close_clear_cancel.png");
	private JButton close = new JButton(icon);


	private String[] columnNames = {"id_client", "id_colis", "id_commande", "qualifiant", "date_livraison","statut", "Livraison"};

	private String[] pays;

	public FenetreTransporteur(String id, LinkedList<String> dest){
		
		this.setTitle("---Transporteur---");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		this.setResizable(false);


		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		
		this.pays = new String[dest.size()];
		for(int i=0; i<pays.length; i++){
			pays[i] = dest.get(i);
		}	

		try {
			for( int j = 0; j<pays.length; j++){
				System.out.println(pays[j]);
				ResultSet rs = st.executeQuery(" SELECT id_client, id_colis, id_commande, qualifiant_colis, cast(date_livraison as varchar), statut FROM " + Constantes.base_colis + " natural join " 
						+ Constantes.base_commande + " WHERE statut = 'controle' AND id_commande IN ( SELECT id_commande FROM " 
						+ Constantes.base_commande + " natural join " + Constantes.base_client + " WHERE pays ='" + pays[j] + "') ORDER BY id_client ASC ;");

				if(rs.next()){
					rs.previous();
					while(rs.next()){

						Object[] data = new Object[7];

						data[0] = rs.getString(1);
						data[1] = rs.getInt(2);
						data[2] = rs.getInt(3);
						data[3] = rs.getInt(4);
						data[4] = rs.getString(5);
						data[5] = rs.getString(6);
						data[6] = new JButton("Livrer");	

						save.add(data);

					}
				}
			}
			affichageTable(save);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void affichageTable(LinkedList<Object[]> save){
		donnees = new Object[save.size()][];

		for(int i=0; i<donnees.length; i++){
			donnees[i] = save.get(i);
		}

		DefaultTableModel model = new DefaultTableModel (donnees, columnNames){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int iRowIndex, int iColumnIndex)
			{
				return false;
			}
			
		};
		
		jt=new JTable(model);

		jt.getColumn(columnNames[6]).setCellRenderer(new JSelectionButton());
		jt.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				int col= jt.columnAtPoint(e.getPoint());


				if(col != 6){
					return;
				}

				int row = jt.rowAtPoint(e.getPoint());

				Integer id_colis = (Integer)(jt.getValueAt(row, 1));
				Integer id_commande = (Integer)(jt.getValueAt(row, 2));
				
				ConnexionBD bd = new ConnexionBD();
				Statement st = bd.createStatement();
				
				try {
					
					System.out.println(id_colis);
					System.out.println(id_commande);
					st.executeUpdate("UPDATE colis set statut = 'livre' where id_colis = "+ id_colis.intValue() +";");
					
					updateCommande(id_commande);	
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				//jt.removeLigne(row);
				((DefaultTableModel)jt.getModel()).removeRow(jt.getSelectedRow());
				
				
			}
		});


		//panel de la table
		panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());

		panelTable.add(new JScrollPane(jt), BorderLayout.CENTER);					


		//bouton fermer
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.addActionListener(this);

		//panel principal
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(panelTable, BorderLayout.CENTER);
		jp.add(close, BorderLayout.SOUTH);
		jp.validate();
		
		this.setContentPane(jp);
		this.setVisible(true);
		
	}
	
	public void updateCommande(int id_commande){
		
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		
		try {
			
			ResultSet m = st.executeQuery("SELECT count(id_colis) FROM " + 	Constantes.base_colis + " WHERE id_commande = " + id_commande + " GROUP BY id_commande;");
			
			if(m.next()){
				int nbColis = m.getInt(1);
				
				ResultSet l = st.executeQuery("SELECT id_colis FROM " + Constantes.base_colis + " WHERE id_commande = " + id_commande + " AND statut = 'livre' ;");
				
				if(l.next()){
					l.last();
					int nb = l.getRow();
					
					if(nbColis == nb){
						st.executeUpdate("Update " + Constantes.base_commande + " set etat = 'expediee' WHERE id_commande = " + id_commande +";");
					}
					/*else{
						System.out.println("pas réussi");
					}*/
				}
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == close ){
			this.dispose();
			new FenetreConnexion2();
		}

	}

}
