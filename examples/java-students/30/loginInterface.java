import java.io.*;
import java.sql.*;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridLayout;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;



public class loginInterface extends JFrame {
	
	connectionBDD connecteBDD;
	
	private JPanel container = new JPanel();
	private JPanel confirmecont = new JPanel();

	private JComboBox liste_session = new JComboBox();
	private JLabel label = new JLabel("    Type Session : ");
	
	private JTextField loginTF = new JTextField("");
	private JLabel label_login = new JLabel("    Login :  ");
	
	private JPasswordField passTF = new JPasswordField("");
	private JLabel label_pass = new JLabel("    Password : ");
	 
	private JButton bouton = new JButton("Connecte !!");
	private JLabel label_bouton = new JLabel("abc");
	
	private JOptionPane erreur;
	
	public loginInterface(String nom) {
		
		String passwordBDD = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
		try {
			connecteBDD = new connectionBDD(nom,passwordBDD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR DE CONNECTION A LA BASE");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("ERROR DE CONNECTION A LA BASE");
		}
		
		//-------------------------------------------------------
		//Fenetre Principale Login
		//-------------------------------------------------------
		this.setTitle("...... Interface Swing : Login ......");
		this.setSize(350,150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        
		//-------------------------------------------------------
		// LISTE BOX SESSION
		//-------------------------------------------------------
		String[] tab = {"Douane", "Gerant", "Client"};
		liste_session = new JComboBox(tab);
		
		liste_session.setPreferredSize(new Dimension(180,25));
		
		//Ajout du button listener
		bouton.addActionListener(new BoutonListener());
		
		loginTF.setText("SO78210");
		passTF.setText("IWT87FKG6HB");
		
		//-------------------------------------------------------
		// SESSION 
		//-------------------------------------------------------
		container.add(label);
		container.add(liste_session);
		
		//-------------------------------------------------------
		// LOGIN
		//-------------------------------------------------------
		loginTF.setPreferredSize(new Dimension(200,25));
        loginTF.setForeground(Color.BLUE);

		container.add(label_login);
		container.add(loginTF);
		
		//-------------------------------------------------------
		// PASS 
		//-------------------------------------------------------
		passTF.setPreferredSize(new Dimension(200,25));
		container.add(label_pass);
		container.add(passTF);
		
		//-------------------------------------------------------
		// MISE EN PLACE
		//-------------------------------------------------------
        container.setLayout(new GridLayout(3,2));
    	confirmecont.add(bouton);

		this.setLayout(new BorderLayout());
		this.getContentPane().add(container,BorderLayout.NORTH);
		this.getContentPane().add(confirmecont,BorderLayout.SOUTH);
		this.setVisible(true);  
	}
	
	//-------------------------------------------------------
	// ACTION LISTENER BOUTON CONNECT 
	//-------------------------------------------------------
	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (liste_session.getSelectedItem().equals("Douane")){
				try {
					System.out.println(loginTF.getText());
					System.out.println(passTF.getText());
					ResultSet contenu = connecteBDD.verifieLoginPassword("douane",loginTF.getText(),passTF.getText() );
					boolean reussiPas = true;
					while (contenu !=null && contenu.next() ) {
						if (contenu.getString("login").equals(loginTF.getText()) && contenu.getString("password").equals(passTF.getText()) ) {
							connectionDouane CD = new connectionDouane(connecteBDD,contenu.getString("pays"));
							reussiPas = false;
							setVisible(false);
						}

					}
					if (contenu == null || reussiPas == true) {
						erreur = new JOptionPane();
						erreur.showMessageDialog(null, "Erreur sur le Login ou le password", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (SQLException e2 ) {
					e2.printStackTrace();
				}
			}else if (liste_session.getSelectedItem().equals("Gerant")){
				try {
					System.out.println(loginTF.getText());
					System.out.println(passTF.getText());
					ResultSet contenu = connecteBDD.verifieLoginPassword("douane",loginTF.getText(),passTF.getText() );
					boolean reussiPas = true;
					while (contenu !=null && contenu.next() ) {
						if (contenu.getString("login").equals(loginTF.getText()) && contenu.getString("password").equals(passTF.getText()) ) {
							connectionDouane CD = new connectionDouane(connecteBDD,contenu.getString("pays"));
							reussiPas = false;
							setVisible(false);
						}

					}
					if (contenu == null || reussiPas == true) {
						erreur = new JOptionPane();
						erreur.showMessageDialog(null, "Erreur sur le Login ou le password", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (SQLException e2 ) {
					e2.printStackTrace();
				}
			}
			
		}
	}

	public static void main(String [] args){
		loginInterface loginView = new loginInterface(args[0]);
	}
}