package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreListeConteneur extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JTextField id_conteneur;

	private JPanel champs, panelConteneur, cont;

	private ImageIcon icon2 = new ImageIcon("src/ic_menu_back.png");
	private JButton back = new JButton(icon2);
	private JButton ok= new JButton(new ImageIcon("src/ic_menu_forward.png"));

	private String[] columnNames = { "id_palette", "mode_transport", "code_SCAC"};
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;

	private JTable jt;

	private String pays;


	public FenetreListeConteneur( String id_douane, String dest){

		this.pays = dest;

		JLabel conteneur = new JLabel("ID_conteneur : ");
		id_conteneur = new JTextField();

		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.addActionListener(this);

		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.addActionListener(this);

		panelConteneur = new JPanel();
		panelConteneur.setLayout(new GridLayout(0,2));
		panelConteneur.add(conteneur);
		panelConteneur.add(id_conteneur);

		champs = new JPanel();
		champs.setLayout(new BorderLayout());
		champs.add(panelConteneur, BorderLayout.CENTER);
		champs.add(ok, BorderLayout.SOUTH);

		this.add(champs);
		this.setVisible(true);


	}

	public boolean affichageConteneur(int id){
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {

			ResultSet rs = st.executeQuery("SELECT id_palette, mode_transport FROM " + Constantes.base_conteneur + 
					" WHERE pays = '" + pays + "' AND id_conteneur = " + id + " ORDER BY id_palette;" );

			if(rs.next()){
				rs.previous();

				while(rs.next()){
					Object[] data = new Object[2];

					data[0] = rs.getInt(1);
					data[1] = rs.getString(2);

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

				cont = new JPanel();
				cont.setLayout(new BorderLayout());

				//JPanel p = new JPanel();
				JLabel commande = new JLabel("ID : " + id);

				JPanel button = new JPanel();
				button.add(back);

				cont.add(commande, BorderLayout.NORTH);
				cont.add(new JScrollPane(jt), BorderLayout.CENTER);
				cont.add(button, BorderLayout.SOUTH);


				this.add(cont);

				save.clear();
				return true;
			}

			else{
				JOptionPane.showMessageDialog(null, "Aucun conteneur ne correspond à votre demande",
						"--Information--", JOptionPane.INFORMATION_MESSAGE);

			}

			rs.close();
			st.close();
			bd.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok){
			ok.setVisible(false);
			champs.setVisible(false);
			this.revalidate();
			int id_cont = Integer.parseInt(id_conteneur.getText());
			if(affichageConteneur(id_cont)){
				this.revalidate();
			}
			else{
				ok.setVisible(true);
				champs.setVisible(true);
				this.revalidate();
			}

		}
		else if(e.getSource() == back){
			cont.setVisible(false);
			cont.removeAll();
			cont.validate();
			//comm.remove(panelTable);
			this.remove(cont);
			this.validate();
			champs.setVisible(true);

			ok.setVisible(true);
		}
	}
}
