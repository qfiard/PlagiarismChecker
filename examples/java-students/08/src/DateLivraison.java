import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Client peut choisir une date livraison grace a cette classe
public class DateLivraison extends JFrame {
CreationBase connecteBD;//ou static
String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField date=new JTextField();
	
    
	private JLabel label_date = new JLabel("Date livraison(annee-mois-jour) :");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public DateLivraison(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.setTitle("Date de Livraison");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        date.setPreferredSize(new Dimension(150,25));
        
        container.add(label_date);
        container.add(date);
        container.setLayout(new GridLayout(1,1));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				try {
					connecteBD.requete_datelivraison(login, date.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			}				
		});
        
	}
}
