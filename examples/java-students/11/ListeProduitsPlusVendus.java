package Gerant;
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


public class ListeProduitsPlusVendus extends JFrame implements ActionListener{

	protected JTable table;
	protected JScrollPane scroll;
	private JPanel jp;
	private JButton fermer = new JButton("fermer");
	
	protected static final long serialVersionUID = 1L;

	
	
	public ListeProduitsPlusVendus(){
		//super();
		
		this.setTitle("Liste des produits les plus vendus");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		ListeProduitsPlusVendus();
	}

	public void ListeProduitsPlusVendus(){


		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs = connec.listerProduitsPlusVendus();


			if(rs.next()){	

				rs.previous();

		

				String[] nomColonnes = { "num_produit", "quantite_prod"};

				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;


				while(rs.next()){
					Object[] data = new Object[8];

					data[0] = rs.getString(1);
					data[1] = rs.getString(2);

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
				jp.setSize(800, 700);

				fermer.addActionListener(this);
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(jp, BorderLayout.CENTER);
				panel.add(fermer, BorderLayout.SOUTH);
				this.setContentPane(panel);
				this.setVisible(true);
			}
			
			else{
				System.out.println("erreur");
			}
			connec.close();
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
