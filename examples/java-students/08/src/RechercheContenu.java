import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//Recherche une commande par numero du produit

public class RechercheContenu extends JFrame {
	CreationBase connecteBD;
	String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField produit=new JTextField();
	
    
	private JLabel label_pro = new JLabel("Numero du produit : ");
	
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public RechercheContenu(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("Recherche Contenu");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        produit.setPreferredSize(new Dimension(150,25));
      
        
        container.add(label_pro);
        container.add(produit);
        container.setLayout(new GridLayout(1,1));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				FenetreDouane fe=new FenetreDouane(login,produit.getText(),connecteBD);
		
			}				
		});
        
	}
}
