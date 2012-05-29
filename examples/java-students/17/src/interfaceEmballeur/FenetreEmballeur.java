package interfaceEmballeur;

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

public class FenetreEmballeur extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;
	
	private ImageIcon icon = new ImageIcon("src/ic_menu_close_clear_cancel.png");
	private JButton close = new JButton(icon);
	
	private JTable jt;
	
	private JPanel panelTable;


	private String[] columnNames = {"id_colis", "id_commande", "statut", "qualifiant", "date_emballage", "Emballer"};

	private String id_emballeur;
	
	public FenetreEmballeur(String id){
		this.setTitle("---Emballeur---");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		this.setResizable(false);
		
		this.id_emballeur = id;

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		
		try {
			
			ResultSet rs = st.executeQuery("SELECT id_colis, id_commande, statut, qualifiant_colis FROM " + Constantes.base_colis + " WHERE statut = 'produit'");

				if(rs.next()){
					rs.previous();
					while(rs.next()){

						Object[] data = new Object[6];

						data[0] = rs.getInt(1);
						data[1] = rs.getInt(2);
						data[2] = rs.getString(3);
						data[3] = rs.getInt(4);
						data[4] = "AAAA-MM-JJ";
						data[5] = new JButton("Emballer");	

						save.add(data);

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
				return true;
			}
			
		};
		
		jt=new JTable(model);

		jt.getColumn(columnNames[5]).setCellRenderer(new JSelectionButton());
		jt.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				int col= jt.columnAtPoint(e.getPoint());


				if(col != 5){
					return;
				}

				int row = jt.rowAtPoint(e.getPoint());

				Integer id = (Integer)(jt.getValueAt(row, 0));
				int id_colis = id.intValue();
				
				Integer qual = (Integer)(jt.getValueAt(row, 3));
				int qualifiant = qual.intValue();
				
				ConnexionBD bd = new ConnexionBD();
				Statement st = bd.createStatement();
				
				String date = (String)jt.getValueAt(row, 4);
				
				try {
					
					st.executeUpdate("UPDATE colis set statut = 'emballe' where id_colis = "+ id_colis  +";");
					if(!date.equals("AAAA-MM-JJ")){
						st.execute("INSERT INTO " + Constantes.base_emballage + " VALUES (" + id_colis + " , '" +  id_emballeur +"' , " + qualifiant +" , " + "'"+ date + "');" );
					}
					else{
						st.execute("INSERT INTO " + Constantes.base_emballage + " VALUES(" + id_colis +", '" +  id_emballeur +"', " + qualifiant +" , " + "'null');" );
					}
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == close ){
			this.dispose();
			new FenetreConnexion2();
		}
		
	}
}
