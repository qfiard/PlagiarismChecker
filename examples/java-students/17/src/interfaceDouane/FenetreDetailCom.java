package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;



public class FenetreDetailCom  extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private JTextField id_commande;

	private JPanel champs, comm;



	private JTable jt; 
	

	private ImageIcon icon2 = new ImageIcon("src/ic_menu_back.png");
	private JButton back = new JButton(icon2);
	private JButton ok = new JButton(new ImageIcon("src/ic_menu_forward.png"));

	private String[] columnNames = { "id_produit", "quantite", "prix", "id_client", "date livraison", "prix_livraison" };

	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;


	public FenetreDetailCom(){

		JLabel jl = new JLabel("Id_commande : ");
		id_commande = new JTextField();

		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.addActionListener(this);
			
		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.addActionListener(this);

		champs = new JPanel();
		champs.setLayout(new GridLayout(0,2));
		champs.add(jl);
		champs.add(id_commande);

		this.add(champs, BorderLayout.CENTER);
		this.add(ok, BorderLayout.SOUTH);
		this.setVisible(true);

	}


	public void affichageCommande(int id_commande){
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery("SELECT id_produit, quantite, prix, id_client, cast(date_livraison as varchar), prix_livraison FROM " + Constantes.base_commande + 
					" natural join (" + Constantes.base_produit + " natural join " + Constantes.base_contenuCommande +" ) WHERE id_commande = " + id_commande + " ORDER BY id_commande;" );

			if(rs.next()){
				rs.previous();



				while(rs.next()){
					Object[] data = new Object[6];

					data[0] = rs.getString(1);
					data[1] = rs.getInt(2);
					data[2] = rs.getInt(3);
					data[3]= rs.getString(4);
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

				jt = new JTable(tableModel);

				this.setLayout(new BorderLayout());
				
				comm = new JPanel();
				comm.setLayout(new BorderLayout());
				
				//JPanel p = new JPanel();
				JLabel commande = new JLabel("ID : " + id_commande);
				
				JPanel button = new JPanel();
				button.add(back);
				
				comm.add(commande, BorderLayout.NORTH);
				comm.add(new JScrollPane(jt), BorderLayout.CENTER);
				comm.add(button, BorderLayout.SOUTH);
				
				
				this.add(comm);

				rs.close();
				st.close();
				bd.close();
				
				save.clear();
			}
			else{
				JOptionPane.showMessageDialog(null, "Aucune commande ne correspond a votre demande",
						"--Information--", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ok){
			ok.setVisible(false);
			champs.setVisible(false);
			this.revalidate();
			int id_com = Integer.parseInt(id_commande.getText());
			affichageCommande(id_com);
			this.revalidate();

		}
		else if(e.getSource() == back){
			comm.setVisible(false);
			comm.removeAll();
			comm.validate();
			//comm.remove(panelTable);
			this.remove(comm);
			this.validate();
			champs.setVisible(true);
			
			ok.setVisible(true);
		}

	}


}
