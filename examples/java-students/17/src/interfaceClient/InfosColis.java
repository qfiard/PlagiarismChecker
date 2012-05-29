package interfaceClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class InfosColis extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JButton derouler;
	private JTable jt;

	private JPanel entete;
	
	private boolean appuye = false;

	private JPanel panelTable;
	private Object[][] donnees;
	private String[] columnNames = { "ID", "Quantite" };

	public InfosColis(int idColis, String statut){

		
		
		
		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();
		ResultSet rs;

		try {
			
			rs = st.executeQuery("SELECT id_produit, quantite FROM " +
					Constantes.base_contenuColis + " WHERE id_colis=" + idColis + ";");

			LinkedList<Object[]> save = new LinkedList<Object[]>();

			while(rs.next()){
				
				Object[] data = new Object[2];

				data[0] = rs.getString(1);
				data[1] = rs.getInt(2);
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		panelTable = new JPanel();
		//panelTable.setVisible(true);

		panelTable.setLayout(new BorderLayout());

		panelTable.add(jt.getTableHeader(), BorderLayout.PAGE_START);
		//panelTable.add(new JScrollPane(jt), BorderLayout.CENTER);
		panelTable.add(jt, BorderLayout.CENTER);
		panelTable.setVisible(false);

		//panelTable.repaint();
		//scrollJT.revalidate();
		//panelTable.revalidate();
		
		ImageIcon iconeDerouler = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_add.png"));
			Image resize = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			iconeDerouler.setImage(resize);					
		} catch (IOException e) {
			e.printStackTrace();
		}
		derouler = new JButton(iconeDerouler);
		derouler.addActionListener(this);
		derouler.setContentAreaFilled(false);
		derouler.setBorderPainted(false);
		
		JLabel infos = new JLabel("ID: " + idColis + " [" + statut + "]");
		
		
		entete = new JPanel();		
		entete.add(derouler);
		entete.add(infos);
		
		this.setLayout(new BorderLayout());
		this.add(entete, BorderLayout.NORTH);
		this.add(panelTable, BorderLayout.CENTER);
		//this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == derouler){			
			appuye = !appuye;

			if(appuye){
				panelTable.setVisible(true);
			}
			else{
				panelTable.setVisible(false);
			}
		}

	}

}
