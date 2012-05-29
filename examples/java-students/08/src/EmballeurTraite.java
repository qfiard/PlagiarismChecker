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

//Affiche liste de colis,1 emballeur qui traite par 1 jour
public class EmballeurTraite extends JFrame {
	CreationBase connecteBD;
	String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField em=new JTextField();
	
	private JLabel label_em = new JLabel("Numero d'emballeur : ");
	
	public JTextField date=new JTextField();
	
    
	private JLabel label_date = new JLabel("Date d'emballage(annee-mois-jour) :");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public EmballeurTraite(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("Emballeur traite par jour");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
      
        em.setPreferredSize(new Dimension(150,25));
        
        container.add(label_em);
        container.add(em);
        container.add(label_date);
        container.add(date);
        container.setLayout(new GridLayout(2,2));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        //final CreationBase connecteBD,final String login
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				FenetreGerant fe=new FenetreGerant(connecteBD,login,em.getText(),date.getText());
			}				
		});
        
	}
	public static void main(String[] args){
		//EmballeurTraite l=new EmballeurTraite();
	}
}
