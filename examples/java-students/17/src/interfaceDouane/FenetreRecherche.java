package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;





public class FenetreRecherche extends JPanel implements ActionListener, ItemListener{

	private static final long serialVersionUID = 1L;

	private Statement st;

	private String premRequete = "SELECT id_commande, id_client, pays, id_produit, quantite, cast(date_livraison as varchar), prix_livraison FROM " +
			Constantes.base_commande +" natural join " + Constantes.base_client + " natural join " + Constantes.base_contenuCommande;
	private String requete;
	private String[] columnNames = { "id_commande", "id_client", "pays", "id_produit", "quantite", "date_livraison", "prix_livraison" };
	private String[] champs = {"id_commande", "pays", "id_produit"};

	private JPanel panelRecherche, panelTable;
	private JPanel panelCom = new JPanel();
	private JPanel panelPays = new JPanel();
	private JPanel panelContenu = new JPanel();
	private JTextField[] recherche = new JTextField[3];
	private JTable jt;
	private JScrollPane scroll;

	private LinkedList<JRadioButton> save = new LinkedList<JRadioButton>();

	private ImageIcon icon = new ImageIcon("src/ic_menu_forward.png");
	private JButton ok = new JButton(icon);

	private ImageIcon icon2 = new ImageIcon("src/ic_menu_back.png");
	private JButton back = new JButton(icon2);

	private JRadioButton rien;
	
	public FenetreRecherche(){
		ConnexionBD bd = new ConnexionBD();
		st = bd.createStatement();

		requete= premRequete;
		ajouterPanelRecherche();

	}

	public void updateRequest(){

		requete = premRequete;
		StringBuffer where = new StringBuffer();
		boolean champsAvant = false;

		if(!recherche[0].getText().isEmpty()){
			champsAvant = true;
			where.append("cast( " + champs[0] + " as varchar) LIKE '%" +  recherche[0].getText() + "%'");
		}
		for(int i=1; i<champs.length; i++){
			if(!recherche[i].getText().isEmpty()){
				if(champsAvant){
					where.append(" AND ");
				}
				else{
					champsAvant = (true);
				}
				where.append(champs[i] + "  LIKE '%" + recherche[i].getText() + "%' ");
			}
		}

		if(where.length() > 0){
			requete += " WHERE ";
			requete += where.toString();
			
		}

		requete+= " ORDER BY id_commande;";
	}

	public boolean affichageCommande(ResultSet rs){

		try {
			if(rs.next()){
				rs.previous();
				LinkedList<Object[]> save = new LinkedList<Object[]>();
				while(rs.next()){

					Object[] data = new Object[7];

					data[0] = rs.getInt(1);	
					data[1] = rs.getString(2);
					data[2] = rs.getString(3);
					data[3]= rs.getString(4);
					data[4]= rs.getInt(5);
					data[5]= rs.getString(6);
					data[6] = rs.getInt(7);

					save.add(data);
				}

				Object[][] donnees;

				donnees = new Object[save.size()][];

				for(int i=0; i<donnees.length; i++){
					donnees[i] = save.get(i);
				}

				DefaultTableModel tableModel = new DefaultTableModel (donnees, columnNames){
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int iRowIndex, int iColumnIndex)
					{
						return false;
					}
				};

				back.setBorderPainted(false);
				back.setContentAreaFilled(false);
				back.addActionListener(this);

				jt = new JTable(tableModel);
				scroll = new JScrollPane(jt);
				
				panelTable = new JPanel();
				panelTable.setLayout(new BorderLayout());


				//panelTable.add(jt.getTableHeader(), BorderLayout.PAGE_START);
				
				panelTable.add(scroll, BorderLayout.CENTER);
				panelTable.add(back, BorderLayout.SOUTH);
				panelTable.setVisible(true);
				panelTable.revalidate();
			}
			else{
				String message = "Aucun resultat";
				//FenetreErreur fr = new FenetreErreur(message);
				//ajouterPanelRecherche();
				JOptionPane.showMessageDialog(null, message);


				return false;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}


	public void ajouterPanelRecherche(){

		panelRecherche = new JPanel();
		panelRecherche.setLayout(new BoxLayout(panelRecherche, BoxLayout.PAGE_AXIS));

		//REcherche
		JPanel pan = new JPanel();
		JLabel enteteRecherche = new JLabel("Recherche");	
		pan.add(enteteRecherche);

		//JPanel champsRech = new JPanel();
		//champsRech.setLayout(new BorderLayout());	

		for(int i=0; i<recherche.length; i++){
			recherche[i] = new JTextField();
			recherche[i].setPreferredSize(new Dimension(150,20));
		}

		//panel commande
		JLabel rechID = new JLabel("ID_commande :");
		panelCom.add(rechID);
		panelCom.add(recherche[0]);
		/*champsRech.add(rechID);
		champsRech.add(recherche[0]);*/

		//panel pays
		JLabel rechPays = new JLabel("Pays :");
		//champsRech.add(rechPays);
		panelPays.add(rechPays);

		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		try {
			ResultSet pays = st.executeQuery("SELECT DISTINCT pays FROM " + Constantes.base_client + ";");
			ButtonGroup j = new ButtonGroup();
			while(pays.next()){
				JRadioButton button = new JRadioButton(pays.getString(1));
				button.addItemListener(this);
				save.add(button);
				j.add(button);
				jp.add(button);
			}
			 rien = new JRadioButton("aucun");
			rien.addItemListener(this);
			save.add(rien);
			j.add(rien);
			jp.add(rien);
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		panelPays.add(jp);


		//panel produit
		JLabel rechProd = new JLabel("Produit :");		
		panelContenu.add(rechProd);
		panelContenu.add(recherche[2]);

		/*champsRech.add(rechProd);
		champsRech.add(recherche[2]);*/

		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.addActionListener(this);

		//panelRech.add(enteteRecherche, BorderLayout.NORTH);
		panelRecherche.add(pan);
		panelRecherche.add(panelCom);
		panelRecherche.add(panelPays);
		panelRecherche.add(panelContenu);			
		panelRecherche.add(ok);

		this.add(panelRecherche);		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok){

			panelRecherche.setVisible(false);
			//this.remove(ok);
			this.revalidate();
			updateRequest();

			try {

			
				ResultSet rs = st.executeQuery(requete);

				boolean b = affichageCommande(rs);

				if(b){
					this.add(panelTable);
					this.revalidate();
				}
				else{
					panelRecherche.setVisible(true);
				}
				//MOD
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		else if(e.getSource() ==  back){
			panelTable.setVisible(false);
			panelRecherche.setVisible(true);

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for(JRadioButton bouton: save){
			if(e.getSource() == bouton){
				String pays = bouton.getText();
				if(pays.equals("aucun")){
					recherche[1].setText("");
				}
				else{
					recherche[1].setText(pays);
				}
				
			}
		}

	}	

}
