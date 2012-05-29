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

public class Licencier extends JFrame {
	CreationBase connecteBD;
	String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField lic=new JTextField();
	
	private JLabel label_lic = new JLabel("Employeur inefficace : ");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public Licencier(CreationBase connecteBD,String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("Licencier");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
      
        lic.setPreferredSize(new Dimension(150,25));
        
        container.add(label_lic);
        container.add(lic);
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
		
			}				
		});
        
	}
	public static void main(String[] args){
		//Licencier l=new Licencier();
	}
}
