import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//Cree une fenetre pour connecter a base de donnee
public class FenetreLogin extends JFrame {
	CreationBase connecteBD;//ou static
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	public JTextField login = new JTextField("SO78210");
	public JPasswordField password=new JPasswordField("IWT87FKG6HB");
	
    
	private JLabel label_login = new JLabel("Login : ");
	private JLabel label_password = new JLabel("Mot de passe : ");
	private JLabel label_who = new JLabel("Vous etes : ");
	private JComboBox combo = new JComboBox();
    
	JButton b=new JButton("Connecter");
	JOptionPane jop;

    
    public FenetreLogin(String nom_bd){
    	String passwordBD = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
		try {
			connecteBD = new CreationBase(nom_bd,passwordBD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	this.setTitle("Login");
        this.setSize(500, 180);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        login.setPreferredSize(new Dimension(150,25));
        password.setPreferredSize(new Dimension(150,25));
        combo.setPreferredSize(new Dimension(150,25));
        
        combo.addItem("Gerant");
        combo.addItem("Douane");
        combo.addItem("Client");
        combo.addItem("Emballeur");
        combo.addItem("Transporteur");
       
        container.add(label_login);
        container.add(login);
        container.add(label_password);
        container.add(password);
        container.add(label_who);
        container.add(combo);
        container.setLayout(new GridLayout(3,2));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new BoutonListener());
    }
    
    public static void main(String[] args){
    	FenetreLogin fe_login=new FenetreLogin(args[0]);
    }
    class BoutonListener implements ActionListener{
    	 
        public void actionPerformed(ActionEvent e) {
        		//Si utilisateur est un Gerant
        		if(combo.getSelectedItem().equals("Gerant")){
        			try {
						if(connecteBD.NombreLigne(connecteBD.getLoginPassword("gerant",login.getText(),password.getText())) == 1){
							System.out.println("Login :  " + login.getText());
		        			System.out.println("Password :  "+password.getText());
		        			System.out.println("Vous etes "+combo.getSelectedItem());
							FenetreGerant fe_g=new FenetreGerant(connecteBD,login.getText());
							setVisible(false);
						}
						else{
							jop = new JOptionPane();
							jop.showMessageDialog(null, "Login et mot de passe sont incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		//Si utilisateur est une Douane
        		else if(combo.getSelectedItem().equals("Douane")){
        			try {
						if(connecteBD.NombreLigne(connecteBD.getLoginPassword("douane",login.getText(),password.getText())) == 1){
							System.out.println("Login :  " + login.getText());
		        			System.out.println("Password :  "+password.getText());
		        			System.out.println("Vous etes "+combo.getSelectedItem());
							FenetreDouane fe_d=new FenetreDouane(connecteBD,login.getText());
							setVisible(false);
						}
						else{
							jop = new JOptionPane();
							jop.showMessageDialog(null, "Login et mot de passe sont incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		//Si utilisateur est un Client
        		else if(combo.getSelectedItem().equals("Client")){
        			try {
						if(connecteBD.NombreLigne(connecteBD.getLoginPassword("client",login.getText(),password.getText())) == 1){
							System.out.println("Login :  " + login.getText());
		        			System.out.println("Password :  "+password.getText());
		        			System.out.println("Vous etes "+combo.getSelectedItem());
							FenetreClient fe_c=new FenetreClient(connecteBD,login.getText(),password.getText());
							setVisible(false);
						}
						else{
							jop = new JOptionPane();
							jop.showMessageDialog(null, "Login et mot de passe sont incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		//Si utilisateur est un Emballeur
        		else if(combo.getSelectedItem().equals("Emballeur")){
        			try {
						if(connecteBD.NombreLigne(connecteBD.getLoginPassword("emballeur",login.getText(),password.getText())) == 1){
							System.out.println("Login :  " + login.getText());
		        			System.out.println("Password :  "+password.getText());
		        			System.out.println("Vous etes "+combo.getSelectedItem());
		        			FenetreEmballeur fe_e=new FenetreEmballeur(connecteBD);
							setVisible(false);
						}
						else{
							jop = new JOptionPane();
							jop.showMessageDialog(null, "Login et mot de passe sont incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		//Si utilisateur est un Transporteur
        		else if(combo.getSelectedItem().equals("Transporteur")){
        			try {
						if(connecteBD.NombreLigne(connecteBD.getLoginPassword("transporteurs",login.getText(),password.getText())) == 1){
							System.out.println("Login :  " + login.getText());
		        			System.out.println("Password :  "+password.getText());
		        			System.out.println("Vous etes "+combo.getSelectedItem());
		        			FenetreTransporteur fe_t=new FenetreTransporteur(connecteBD);
							setVisible(false);
						}
						else{
							jop = new JOptionPane();
							jop.showMessageDialog(null, "Login et mot de passe sont incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        }
        
    }

}
