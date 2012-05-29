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

//Client peut changer son mot de passe grace a cette classe
public class ChangePassword extends JFrame {
	CreationBase connecteBD;
	String login;
	String mdp;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JPasswordField password=new JPasswordField();
	public JPasswordField password_confirm=new JPasswordField();
	
    
	private JLabel label_password = new JLabel("Nouveau mot de passe : ");
	private JLabel label_password_confirm = new JLabel("Confirmer votre mot de passe :");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public ChangePassword(final CreationBase connecteBD,final String login,final String mdp){
		this.connecteBD=connecteBD;
		this.login=login;
		this.mdp=mdp;
		this.setTitle("Changer mot de passe");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        password.setPreferredSize(new Dimension(150,25));
        password_confirm.setPreferredSize(new Dimension(150,25));
        
        container.add(label_password);
        container.add(password);
        container.add(label_password_confirm);
        container.add(password_confirm);
        container.setLayout(new GridLayout(2,2));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				if(password.getText().equals(password_confirm.getText())){
					connecteBD.changer_mdp(login,mdp,password.getText());
					jop = new JOptionPane();
					jop.showMessageDialog(null, "Reussir le changement", "Bravo", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					FenetreClient fe_c=new FenetreClient(connecteBD,login,password.getText());
					
				}
				else{
					jop = new JOptionPane();
					jop.showMessageDialog(null, "Les mots de passe ne correspondent pas", "Erreur", JOptionPane.ERROR_MESSAGE);

				}
			}				
		});
        
	}
}
