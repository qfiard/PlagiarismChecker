package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FenetreConnexion extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JButton connexion;
	private JButton quitter;
	
	private JTextField login;
	private JPasswordField password;
	
	public FenetreConnexion(){
		
		this.setTitle("--Connexion--");
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);
		
		
		JPanel panel = new JPanel();
		
		
		panel.setLayout(new BorderLayout());
		
		
		JLabel loginLabel = new JLabel("Login: ");
		
		login = new JTextField();
		
		JLabel passwordLabel = new JLabel("Mot de passe: ");
		
		/*password = new JTextField();
		password.setPreferredSize(new Dimension(100, 40));*/
		password = new JPasswordField();
		password.setPreferredSize(new Dimension(100, 40));
		
		//JPasswordField
		
		connexion = new JButton("Connexion");
		quitter = new JButton("Quitter");
		
		connexion.addActionListener(this);
		quitter.addActionListener(this);
		
		
		
		//texts.add(login);
		//texts.add(password);
		//texts.add(connexion);
		//texts.add(quitter);
		
		JPanel champs = new JPanel();
		
		champs.setLayout(new GridLayout(0, 2));
		
		champs.add(loginLabel);
		champs.add(login);
		champs.add(passwordLabel);
		champs.add(password);
		
		JPanel buttons = new JPanel();
		
		buttons.add(connexion);
		buttons.add(quitter);
		
		
		panel.add(champs, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);		
		
		
		this.setContentPane(panel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == connexion){
			if(login.getText().equals("")){
				
				String message = "Veuillez specifier un login";
				JOptionPane.showMessageDialog(null, message, "Erreur: Login",
				        JOptionPane.ERROR_MESSAGE);

			}
			else if(password.getText().equals("")){

				String message = "Vous n'avez pas specifie de mot de passe";
				JOptionPane.showMessageDialog(null, message, "Erreur: Mot de passe",
				        JOptionPane.ERROR_MESSAGE);
				
			}
		}
		else if(e.getSource() == quitter){
			this.dispose();
		}
		
	}
	
	public static void main(String[] args){
		FenetreConnexion fc = new FenetreConnexion();
		
		
	}

}
