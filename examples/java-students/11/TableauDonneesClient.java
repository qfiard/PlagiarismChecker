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
import java.security.*;

import General.Connexion;


public class TableauDonneesClient extends JFrame implements ActionListener{

	String log;
	String password;
	protected JTable table;
	protected JScrollPane scroll;
	private JPanel jp;
	private JButton fermer = new JButton("fermer");
	
	protected static final long serialVersionUID = 1L;

	
	
	public TableauDonneesClient(){
		//super();
		
		this.setTitle("Liste des clients");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		TableauDonneesClient();
	}

	public void TableauDonneesClient(){


		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs = connec.listerClients();


			if(rs.next()){	

				rs.previous();

		

				String[] nomColonnes = { "num_client", "nom_societe", "suffixe_societe", "adr",
										"ville", "code_postale", "pays", "tel"};

				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;


				while(rs.next()){
					Object[] data = new Object[8];

					data[0] = rs.getString(1);
					data[1] = rs.getString(2);
					data[2] = rs.getString(3);
					data[3] = rs.getString(4);
					data[4] = rs.getString(5);
					data[5] = rs.getString(6);
					data[6] = rs.getString(7);
					data[7] = rs.getString(8);

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
				panel.add(fermer, BorderLayout.SOUTH)
				;
				this.setContentPane(panel);
				this.setVisible(true);
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
