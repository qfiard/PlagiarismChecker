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

public class FenetreListerPalette extends JPanel implements ActionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField id_palette;

	JPanel champs, panelPalette, pal;

	private ImageIcon icon2 = new ImageIcon("src/ic_menu_back.png");
	private JButton back = new JButton(icon2);
	private JButton ok= new JButton(new ImageIcon("src/ic_menu_forward.png"));


	private String[] columnNames = { "id_colis", "date_emballage"};
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;

	private JTable jt;


	//private String ID_douane;
	private String pays;
	//private boolean flag = false;

	public FenetreListerPalette(String id_douane, String dest){

		//this.ID_douane = id_douane;
		this.pays = dest;

		JLabel palette = new JLabel("ID_palette : ;");
		id_palette = new JTextField();

		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.addActionListener(this);

		ok = new JButton(new ImageIcon("src/ic_menu_forward.png"));
		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.addActionListener(this);

		panelPalette = new JPanel();
		panelPalette.setLayout(new GridLayout(0,2));
		panelPalette.add(palette);
		panelPalette.add(id_palette);

		champs = new JPanel();
		champs.setLayout(new BorderLayout());
		champs.add(panelPalette, BorderLayout.CENTER);
		champs.add(ok, BorderLayout.SOUTH);


		this.add(champs, BorderLayout.CENTER);
		this.add(ok, BorderLayout.SOUTH);
		this.setVisible(true);


	}

	public boolean affichagePalette(int id){
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery("SELECT id_colis, cast(date_emballage as varchar) FROM " + Constantes.base_palette + 
					" WHERE pays = '" + pays + "' AND id_palette = " + id + " ORDER BY id_colis;" );

			if(rs.next()){

				rs.previous();

				while(rs.next()){
					Object[] data = new Object[3];

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

				pal = new JPanel();
				pal.setLayout(new BorderLayout());

				JLabel commande = new JLabel("ID : " + id);

				JPanel button = new JPanel();
				button.add(back);

				pal.add(commande, BorderLayout.NORTH);
				pal.add(new JScrollPane(jt), BorderLayout.CENTER);
				pal.add(button, BorderLayout.SOUTH);


				this.add(pal);

				save.clear();
				return true;
			}

			else{
				JOptionPane.showMessageDialog(null, "Aucune palette ne correspond à votre demande",
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
		// TODO Auto-generated method stub

		if(e.getSource() == ok){

			ok.setVisible(false);
			champs.setVisible(false);
			this.revalidate();
			int id_cont = Integer.parseInt(id_palette.getText());
			if(affichagePalette(id_cont)){
				this.revalidate();
			}
			else{
				ok.setVisible(true);
				champs.setVisible(true);
				this.revalidate();
			}


		}
		else if(e.getSource() == back){
			pal.setVisible(false);
			pal.removeAll();
			pal.validate();
			//comm.remove(panelTable);
			this.remove(pal);
			this.validate();
			champs.setVisible(true);

			ok.setVisible(true);
		}

	}
}
