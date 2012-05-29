package Gerant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.*;

import General.Connexion;


public class TableauDonneesEmployes extends JFrame implements ActionListener{

	protected JTable table;
	protected JTable table1;
	protected JTable table2;
	private JPanel jp1;
	private JPanel jp2;
	private JPanel jp3;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JButton fermer = new JButton("fermer");
	JPanel panel = new JPanel();
	
	
	protected static final long serialVersionUID = 1L;

	
	
	public TableauDonneesEmployes(){
		
		this.setTitle("Liste des employes ");
		this.setSize(1000, 1000);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fermer.addActionListener(this);
		fermer.setPreferredSize(new Dimension(50, 20));
		
		TableauDonneesDouane();
		TableauDonneesTransporteur();
		TableauDonneesEmballeur();
		
		panel = new JPanel(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		/*panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(fermer);*/
		this.setContentPane(panel);
		this.setVisible(true);
		
	}

	public void TableauDonneesDouane (){


		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs = connec.listerEmployesDouane();


			if(rs.next()){	

				rs.previous();

				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;
				String[] nomColonnes = { "login_douane"};

				while(rs.next()){
					
					Object[] data = new Object[1];
					data[0] = rs.getString(1);
					liste.add(data);
				}
				
				donnees = new Object[liste.size()][];


				for(int i=0; i<donnees.length; i++){
					donnees[i] = liste.get(i);
				}

				table = new JTable(donnees, nomColonnes);
				
				/*jp1 = new JPanel();
				
				jp1.setLayout(new BorderLayout());
				jp1.add(table.getTableHeader());
				jp1.add(table, BorderLayout.CENTER);
				jp1.setVisible(true);
				//jp1.setSize(600, 200);

				
				panel1 = new JPanel(new BorderLayout());
				panel1.add(jp1);
				panel1.setVisible(true);*/
				panel.add(table.getTableHeader());
				panel.add(table, BorderLayout.CENTER);
				//jp1.setVisible(true);
				
				
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


	public void TableauDonneesTransporteur(){

		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs1 = connec.listerEmployesTransporteur();
			
			if(rs1.next()){	
				
				rs1.previous();
		
				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;
				String[] nomColonnes = {"login_transporteur"};
		
				while(rs1.next()){
					Object[] data = new Object[1];
					data[0] = rs1.getString(1);
					liste.add(data);
				}
				
				donnees = new Object[liste.size()][];
		
		
				for(int i=0; i<donnees.length; i++){
					donnees[i] = liste.get(i);
				}
		
				table1 = new JTable(donnees, nomColonnes);
		
		
				/*jp2 = new JPanel();
				
				jp2.setLayout(new BorderLayout());
				jp2.add(table1.getTableHeader());
		
				jp2.add(table1);
				jp2.setVisible(true);
				//jp2.setSize(600, 200);
				
				panel2 = new JPanel(new BorderLayout());
				panel2.add(jp2);
				
				panel2.setVisible(true);*/
				
				panel.add(table1.getTableHeader());
		
				panel.add(table1);
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
	
	public void TableauDonneesEmballeur(){

		try {

			Connexion connec = new Connexion(Connexion.log,Connexion.password);
			ResultSet rs2 = connec.listerEmployesEmballeur();
			
			if(rs2.next()){	
				
				rs2.previous();
		
				LinkedList<Object[]> liste = new LinkedList<Object[]>();
				Object[][] donnees;
				String[] nomColonnes = {"login_emballeur"};
		
				while(rs2.next()){
					Object[] data = new Object[1];
					data[0] = rs2.getString(1);
					liste.add(data);
				}
				
				donnees = new Object[liste.size()][];
		
		
				for(int i=0; i<donnees.length; i++){
					donnees[i] = liste.get(i);
				}
		
				table2 = new JTable(donnees, nomColonnes);
		
		
				/*jp3 = new JPanel();
				
				jp3.setLayout(new BorderLayout());
				jp2.add(table2.getTableHeader());
		
				jp3.add(table2);
				jp3.setVisible(true);
				//jp3.setSize(600, 200);
				
				panel3 = new JPanel(new BorderLayout());
				panel3.add(jp3);
				panel3.setVisible(true);*/
				panel.add(table2.getTableHeader());		
				panel.add(table2);
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
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() ==  fermer){
			this.dispose();
		}
		
	}
}