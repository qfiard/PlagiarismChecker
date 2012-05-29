package interfaceClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreCommande extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	//Table
	private JTable jt;
	private JScrollPane scrollJT;
	private JPanel panelTable = null;
	private JPanel panelRech = null;
	private JPanel panelCommande = new JPanel();
	private JTextField[] quantiteProduits;

	//Recherche
	private JButton rechercher;
	private JTextField[] recherche = new JTextField[6];	//A modifier;

	private String[] champs = { "id_produit", "poids", "qualifiant", "prix", "description_produit", "reserve" };
	private String[] columnNames = { "ID", "Poids", "Qualifiant", "Prix", "Description", "Reserve", "Ajout" };
	private Object[][] donnees;

	private LinkedList<Integer> indiceProduits = new LinkedList<Integer>();	//Liste des id des produits
	//private LinkedList<String> idsProduits = new LinkedList<String>();
	//private LinkedList<JButton> selectionProduit = new LinkedList<JButton>();	//Boutons pour ajouter un produit a la commande
	private HashMap<String, JButton> produitsAjoutes = new HashMap<String, JButton>();
	private HashMap<String, Integer> produitsQuantite = new HashMap<String, Integer>();
	private boolean initMap = true;


	private JComboBox qualifiant;
	//private LinkedList<>

	private Statement st;

	private String premiereRequete = "SELECT id_produit, poids, qualifiant, prix, description_produit, reserve FROM " +
	Constantes.base_produit + " WHERE reserve > 0";
	private String requete;

	public FenetreCommande(String idClient){

		ajouterProduits();

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

		if(where.length() > 0){
			requete += " WHERE ";
			requete += where.toString();
		}

		requete += ";";
		//System.out.println(requete);

	}

	public void ajouterProduits(){
		String[] typeProduit = { "Tous", "N: S/O", "D: Dangereux", "F: Fragile" };
		qualifiant = new JComboBox(typeProduit);
		qualifiant.addActionListener(this);

		ConnexionBD conn = new ConnexionBD();
		st = conn.createStatement();

		requete = premiereRequete;

		try {
			ResultSet rs = st.executeQuery(requete+";");

			ajouterPanelRecherche();
			afficherProduits(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(requete+";");		
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
				Object[] data = new Object[7];

				data[0] = rs.getString(1);
				data[1] = rs.getInt(2);
				data[2] = rs.getString(3).charAt(0);
				data[3]= rs.getInt(4);
				data[4]= rs.getString(5);
				data[5]= rs.getInt(6);
				data[6] = new JButton(">>");
				//((JButton)data[6]).addActionListener(this);	

				//JButton bouton = (JButton)data[6];

				save.add(data);

				//selectionProduit.add((JButton)data[6]);
				//idsProduits.add((String)data[0]);


				if(!produitsAjoutes.isEmpty()){

					String id = (String)data[0];

					if(produitsAjoutes.containsKey(id)){
						JButton bouton = produitsAjoutes.get(id);

						bouton.setText("<<");
						bouton.setBackground(Color.RED);
						data[6] = bouton;
					}
				}

				//idProduits.add((String)data[0]);

				/*for(int i=0; i<6; i++){
					System.out.print(data[i]+" ");
				}
				System.out.println();*/

			}		

			if(initMap){
				initMap = false;
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
			jt.getColumn(columnNames[6]).setCellRenderer(new JSelectionButton());
			//jt.addMouseListener(new MouseListener(){
			jt.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){

					int col= jt.columnAtPoint(e.getPoint());					

					if(col != 6){
						return;
					}

					int row = jt.rowAtPoint(e.getPoint());

					JButton bouton = (JButton)jt.getValueAt(row, 6);

					if(bouton.getBackground() != Color.RED){
						String idProd = (String)jt.getValueAt(row, 0);

						/*JOptionPane.showMessageDialog(null, "Id ajoute: " + getID(row), "--Erreur--",
								JOptionPane.ERROR_MESSAGE);*/
						indiceProduits.add(row);
						bouton.setText("<<");
						bouton.setBackground(Color.RED);

						produitsAjoutes.put((String)jt.getValueAt(row, 0), bouton);
						produitsQuantite.put(idProd, 1);
						

					}
					else{
						retirerProduits(row);
					}
				}
			});

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

	public void retirerProduits(int row){

		JButton bouton = (JButton)jt.getValueAt(row, 6);

		//indiceProduits.add(row);
		bouton.setText(">>");
		produitsAjoutes.remove((String)jt.getValueAt(row, 0));
		//panelCommande.revalidate();
	}

	public void majPanelCommande(){

		panelCommande = new JPanel();
		JLabel labelCommande = new JLabel("Commande");
		
		panelCommande.setLayout(new BorderLayout());
		panelCommande.add(labelCommande);
		
		if(!produitsQuantite.isEmpty()){

			Set<String> listeProduits = produitsQuantite.keySet();
			quantiteProduits = new JTextField[listeProduits.size()];
			
			int i=0;

			for(String id: listeProduits){
				JLabel labelId = new JLabel(id);
				quantiteProduits[i] = new JTextField(produitsQuantite.get(id));

				i++;
			}
			
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

		for(int i=0; i<6; i++){
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
