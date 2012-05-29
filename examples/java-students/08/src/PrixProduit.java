
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Entrer un produit et affiche le prix correspondant
public class PrixProduit extends JFrame {
	CreationBase connecteBD;
	String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField pro=new JTextField();
	
	private JLabel label_pro = new JLabel("Entrer un produit : ");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public PrixProduit(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("PrixProduit");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
      
        pro.setPreferredSize(new Dimension(150,25));
        
        container.add(label_pro);
        container.add(pro);
        container.setLayout(new GridLayout(1,1));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        //final CreationBase connecteBD,final String login
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				FenetreDouane fe=new FenetreDouane(connecteBD,login,pro.getText(),"produit");
			}				
		});
        
	}
	public static void main(String[] args){
		//Licencier l=new Licencier();
		
	}
}
