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

//Douane peut entrer le resultat du controle
public class EntrerResultat extends JFrame {
	CreationBase connecteBD;
	String login;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField colis=new JTextField();
	public JTextField res=new JTextField();
	
    
	private JLabel label_colis = new JLabel("Numero du colis : ");
	private JLabel label_res = new JLabel("Entrer le resultat(accepter/refuser) : ");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public EntrerResultat(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("Resultat");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        colis.setPreferredSize(new Dimension(150,25));
        res.setPreferredSize(new Dimension(150,25));
        
        container.add(label_colis);
        container.add(colis);
        container.add(label_res);
        container.add(res);
        container.setLayout(new GridLayout(2,2));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				try {
					connecteBD.requete6(login, colis.getText(), res.getText());
					setVisible(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}				
		});
        
	}
}
