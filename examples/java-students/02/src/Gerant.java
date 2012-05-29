import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;


public class Gerant implements ActionListener{

	
		LienBD link;
 		JTable table;
		JPanel panel;
		JFrame controllingFrame;
		JButton bouton ;
		JButton bouton1;
		JButton bouton2;
 
		private static final String b = "Clients";
		private static final String b1 = "Employes";
		private static final String b2 = "B2";
		//private static final String b3 = "B3";
		//private static final String b4 = "B4";

		
		
		public Gerant(LienBD l){
			controllingFrame = new JFrame();
			controllingFrame.setSize(500,250);
			controllingFrame.setTitle("Interface Gerant");
			controllingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			controllingFrame.setLocationRelativeTo(null);
 
			this.link=l;
	        JComponent buttonPane = createButton();
	        panel = new JPanel();
			panel.add(buttonPane);
			controllingFrame.setContentPane(panel);
			controllingFrame.setVisible(true);
 
		}
		
		
		public JComponent createButton(){
 	        JPanel p = new JPanel(new BorderLayout());
			
	        bouton = new JButton("Liste des Clients");
			bouton1 = new JButton("Liste des Employes");
			bouton2 = new JButton("Liste des Commandes");
		//	bouton3 = new JButton("Clients les plus depensiers");
		//	bouton4 = new JButton("Licensier un trainard");
	        
	     

	        bouton.setActionCommand(b);
	        bouton.addActionListener(this);
	        
	        bouton1.setActionCommand(b1);
	        bouton1.addActionListener(this);
	        
	        bouton2.setActionCommand(b2);
	        bouton2.addActionListener(this);
	        
	        
	         
	        p.add(bouton,BorderLayout.NORTH);
	        p.add(bouton1,BorderLayout.CENTER);
	        p.add(bouton2,BorderLayout.SOUTH);
	        return p;
	    }
		
		
		
		public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        try {
	        	if (b.equals(cmd)) {       
	        		JOptionPane.showMessageDialog(controllingFrame,
	        		"Veuillez patientez... Chargement de la liste des Clients.");
	        		// interface qui liste les client
	        		new Liste_Client(this.link);
	        	}
	        	
				else if (b1.equals(cmd)){
					JOptionPane.showMessageDialog(controllingFrame,
			        "Veuillez patientez... Chargement de la liste des Employes.");
					//interface qui liste les employe
					new Liste_Employes(this.link);
			  
				} else {
					JOptionPane.showMessageDialog(controllingFrame,
			        "Veuillez patientez... Chargement de la liste des Commandes.");
					//interface qui liste les commandes
				    new Liste_commandes(this.link);
				}
		
	        } catch (ClassNotFoundException e1) {
				e1.printStackTrace();
	        } catch (SQLException e1) {
				e1.printStackTrace();
	        }
			
		}	
}
