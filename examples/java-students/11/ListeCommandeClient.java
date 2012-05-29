package Emballeur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.*;

import General.Connexion;


public class ListeCommandeClient extends JFrame implements ActionListener{

	protected JTable table;
	protected JScrollPane scroll;
	private JPanel jp;
	private JButton fermer = new JButton("fermer");
	
	protected static final long serialVersionUID = 1L;

	
	
	public ListeCommandeClient(String num){
		
		this.setTitle("Details de la commande");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		ListeCommandeClient(num);
	}


	public void ListeCommandeClient(String num){


		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs = connec.listerCommande(num);


			if(rs.next()){	

				rs.previous();

		

				String[] nomColonnes = { "num_commande","num_client","date_cmd","date_livraison","prix","etat","id_colis","num_emballeur","num_produit"
										, "quantite_prod_colis","qualifiant","poids_colis","date_emballage","passage_douane"};

				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;


				while(rs.next()){
					Object[] data = new Object[14];

					data[0] = rs.getString(1);
					data[1] = rs.getString(2);
					data[2] = rs.getString(3);
					data[3] = rs.getString(4);
					data[4] = rs.getString(5);
					data[5] = rs.getString(6);
					data[6] = rs.getString(7);
					data[7] = rs.getString(8);
					data[8] = rs.getString(9);
					data[9] = rs.getString(10);
					data[10] = rs.getString(11);
					data[11] = rs.getString(12);
					data[12] = rs.getString(13);
					data[13] = rs.getString(14);
					

					liste.add(data);
				}

				donnees = new Object[liste.size()][];


				for(int i=0; i<donnees.length; i++){
					donnees[i] = liste.get(i);
				}
				table = new JTable(donnees, nomColonnes);
				
				jp = new JPanel();
				
				jp.setLayout(new BorderLayout());
				jp.add(table.getTableHeader(), BorderLayout.PAGE_START);
				scroll = new JScrollPane(table);
				jp.add(scroll, BorderLayout.CENTER);

				jp.setVisible(true);
				jp.setSize(400, 300);

				fermer.addActionListener(this);
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(jp, BorderLayout.CENTER);
				panel.add(fermer, BorderLayout.SOUTH);
				this.setContentPane(panel);
				this.setVisible(true);
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JTable getTable(){
		return table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() ==  fermer){
			this.dispose();
		}
		
	}
}