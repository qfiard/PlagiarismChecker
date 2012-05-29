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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreControle extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable jt;
	private String[] columnNames = {"id_commandes", "id_client", "id_produit", "quantite" , "date_livraison", "prix_livraison"};
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private Object[][] donnees;
	private maTable model;
	private JPanel panelTable;

	private ImageIcon icon = new ImageIcon("src/ic_menu_refresh.png");
	private JButton refresh = new JButton(icon);

	private boolean flag = false;
	private String pays;

	public FenetreControle(String dest){
		this.pays = dest;
		build();
	}

	public void build(){
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery(" SELECT DISTINCT id_commande FROM " + Constantes.base_colis + " WHERE id_commande IN (SELECT id_commande FROM " 
					+ Constantes.base_colis + " WHERE statut IN ('livre', 'controle', 'rejete')) AND id_commande IN (SELECT id_commande FROM " + Constantes.base_commande 
					+ " natural join " + Constantes.base_client + " WHERE pays = '" + pays + "') GROUP BY id_commande HAVING count(statut)>0	ORDER BY id_commande ASC ;");

			if(rs.next()){
				rs.previous();
				while(rs.next()){

					int id = rs.getInt(1);

					ConnexionBD bd2 = new ConnexionBD();							
					Statement stmt = bd2.createStatement();
					ResultSet rsC = stmt.executeQuery("SELECT id_commande, id_client, id_produit, quantite, cast(date_livraison as varchar), prix_livraison FROM " + 
							Constantes.base_commande + " natural join " + Constantes.base_contenuCommande + " WHERE id_commande = " + id + " ORDER BY id_commande ASC ;");

					if(rsC.next()){
						rsC.previous();
						while(rsC.next()){

							Object[] data = new Object[6];

							data[0] = rsC.getInt(1);			
							data[1] = rsC.getString(2);
							data[2] = rsC.getString(3);
							data[3] = rsC.getInt(4);
							data[4] = rsC.getString(5);
							data[5] = rsC.getInt(6);
							save.add(data);
							
							flag= true;
						}

					}

					bd2.close();
					stmt.close();
					rsC.close();
				}
				affichageTable(save);	
			}
			else{
				JOptionPane.showMessageDialog(null, "Aucun colis n'est passé par cette douane pour l'instant",
						"--Information--", JOptionPane.INFORMATION_MESSAGE);
			}
			rs.close();
			st.close();
			bd.close();

		} catch (SQLException e) {

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
		panelTable.add(new JScrollPane(jt), BorderLayout.CENTER);					
		panelTable.revalidate();
		//panelTable.setPreferredSize(new Dimension(600,300));

		//bouton refresh
		refresh.setBorderPainted(false);
		refresh.setContentAreaFilled(false);
		refresh.addActionListener(this);

		//panel principal
		JPanel p = new JPanel(new BorderLayout());		
		p.add(panelTable, BorderLayout.CENTER);
		p.add(refresh, BorderLayout.SOUTH);
		this.add(p);
		this.setVisible(true);

		save.clear();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == refresh){
			//panelTable.setVisible(false);
			panelTable.remove(jt);
			this.setVisible(false);
			this.removeAll();
			this.validate();
			build();
			this.revalidate();

		}

	}
}
