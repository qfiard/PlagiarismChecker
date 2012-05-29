package interfaceClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreProduits extends JPanel implements ActionListener{


	private static final long serialVersionUID = 1L;

	//Table
	private JTable jt;
	private JScrollPane scrollJT;
	private JPanel panelTable = null;
	private JPanel panelRech = null;

	//Recherche
	private JButton rechercher;
	private JTextField[] recherche = new JTextField[6];	//A modifier;
	private JComboBox qualifiant;

	private String[] champs = { "id_produit", "poids", "qualifiant", "prix", "description_produit", "reserve" };
	private String[] columnNames = { "ID", "Poids", "Qualifiant", "Prix", "Description", "Reserve" };
	private Object[][] donnees;
	private char typeProd;
	//private LinkedList<>

	private Statement st;

	private String premiereRequete = "SELECT id_produit, poids, qualifiant, prix, description_produit, reserve FROM " + Constantes.base_produit;
	private String requete;

	public FenetreProduits(String idClient){

		ConnexionBD conn = new ConnexionBD();
		st = conn.createStatement();

		try {
			String[] typeProduit = { "Tous", "N: S/O", "D: Dangereux", "F: Fragile" };
			qualifiant = new JComboBox(typeProduit);
			qualifiant.addActionListener(this);
			
			requete = premiereRequete;
			ResultSet rs = st.executeQuery(requete+";");
			//System.out.println(requete+";");

			ajouterPanelRecherche();
			afficherProduits(rs);
			//this.add(panelTable);


		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateRequest(){

		requete = premiereRequete;
		StringBuffer where = new StringBuffer();
		boolean champsAvant = false;

		if(!recherche[0].getText().isEmpty()){
			champsAvant = true;
			where.append(champs[0] + " LIKE '%" + recherche[0].getText() + "%'");
		}

		for(int i=1; i<champs.length; i++){
			if(!recherche[i].getText().isEmpty()){
				if(champsAvant){
					where.append(" AND ");
				}
				else{
					champsAvant = true;
				}

				where.append(champs[i]+" LIKE '%" + recherche[i].getText() + "%'");
			}			
		}
		
		String choixType = (String)qualifiant.getSelectedItem();
		
		if(choixType.charAt(0) != 'T'){
			typeProd = choixType.charAt(0);
			if(champsAvant){
				where.append(" AND");
			}
			
			where.append(" qualifiant='" + typeProd +"'");
		}

		if(where.length() > 0){
			requete += " WHERE ";
			requete += where.toString();
		}

		requete += ";";
		//System.out.println(requete);

	}

	public void afficherProduits(ResultSet rs){

		try {

			if(panelTable != null){
				//panelTable.remove(scrollJT);
				this.remove(panelTable);				
			}			

			LinkedList<Object[]> save = new LinkedList<Object[]>();
			//Object[][] donnees;


			while(rs.next()){
				Object[] data = new Object[6];

				data[0] = rs.getString(1);
				data[1] = rs.getInt(2);
				data[2] = rs.getString(3).charAt(0);
				data[3]= rs.getInt(4);
				data[4]= rs.getString(5);
				data[5]= rs.getInt(6);

				save.add(data);

				/*for(int i=0; i<6; i++){
					System.out.print(data[i]+" ");
				}
				System.out.println();*/

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
			scrollJT = new JScrollPane(jt);
			panelTable.add(scrollJT, BorderLayout.CENTER);				

			//panelTable.repaint();
			//scrollJT.revalidate();
			panelTable.revalidate();
			this.add(panelTable);
			this.revalidate();

			//this.setLayout(new BorderLayout());
			/*this.add(panelTable, BorderLayout.WEST);
			this.add(panelRech, BorderLayout.EAST);*/


			//panelTable.add(jt);
			//this.add(panelRech);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ajouterPanelRecherche(){
		//--Recherche

		panelRech = new JPanel();
		panelRech.setLayout(new BorderLayout());

		JPanel pan = new JPanel();
		JLabel enteteRecherche = new JLabel("Recherche");

		ImageIcon iconeRechercher = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_search.png"));
			Image resize = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			iconeRechercher.setImage(resize);					
		} catch (IOException e) {
			e.printStackTrace();
		}
		rechercher = new JButton(iconeRechercher);
		rechercher.addActionListener(this);
		rechercher.setContentAreaFilled(false);
		rechercher.setBorderPainted(false);

		pan.add(enteteRecherche);
		pan.add(rechercher);

		JPanel champsRech = new JPanel();
		champsRech.setLayout(new GridLayout(0, 2));			

		for(int i=0; i<recherche.length; i++){
			recherche[i] = new JTextField();
		}

		JLabel rechID = new JLabel("ID:");
		champsRech.add(rechID);
		champsRech.add(recherche[0]);

		JLabel rechDescription = new JLabel("Description:");
		champsRech.add(rechDescription);
		champsRech.add(recherche[4]);
		
		JLabel rechQualif = new JLabel("Qualifiant:");
		champsRech.add(rechQualif);
		champsRech.add(qualifiant);

		//panelRech.add(enteteRecherche, BorderLayout.NORTH);
		panelRech.add(champsRech, BorderLayout.CENTER);	
		panelRech.add(pan, BorderLayout.NORTH);		

		this.add(panelRech);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rechercher){
			updateRequest();
			try {

				ResultSet rs = st.executeQuery(requete);

				afficherProduits(rs);
				//MOD
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public String getID(int row){
		return (String)donnees[row][0];
	}

}
