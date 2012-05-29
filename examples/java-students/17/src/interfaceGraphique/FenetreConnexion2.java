package interfaceGraphique;

import interfaceClient.FenetreClient;
import interfaceDouane.FenetreDouane;
import interfaceEmballeur.FenetreEmballeur;
import interfaceGerant.FenetreGerant;
import interfaceTransporteur.FenetreTransporteur;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreConnexion2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JButton connexion;
	private JButton quitter;

	private JTextField login;
	private JPasswordField password;
	private Statement stmt;
	
	private String dest;
	private ResultSet pays;

	public FenetreConnexion2(){

		this.setTitle("--Connexion--");
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);

		ConnexionBD conn = new ConnexionBD();
		stmt = conn.createStatement();
		
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
			ouvrirCompte();
		}
		else if(e.getSource() == quitter){
			this.dispose();
		}
		
	}

	public void ouvrirCompte(){
		
		if(login.getText().equals("")){

			String message = "Veuillez specifier un login";
			JOptionPane.showMessageDialog(null, message, "Erreur: Login",
					JOptionPane.ERROR_MESSAGE);

		}
		else if(password.getPassword().equals("")){

			String message = "Vous n'avez pas specifie de mot de passe";
			JOptionPane.showMessageDialog(null, message, "Erreur: Mot de passe",
					JOptionPane.ERROR_MESSAGE);

		}
		else{
			
			try {
				ResultSet rs = stmt.executeQuery("SELECT type, id FROM " + Constantes.base_identifiant + " WHERE login='" 
						+ login.getText() + "';");
				
				if(rs.next()){

					int type = rs.getInt(1);
					
					String id = rs.getString(2);
					

					switch(type){
					case 10:
						new FenetreEmballeur(id);
						break;

					case 20:
						this.dispose();
						new FenetreClient(id);
						break;
						
					case 40:
						this.dispose();
						 pays = stmt.executeQuery("SELECT DISTINCT pays FROM " + Constantes.base_conteneur + " WHERE code_SCAC = '" + id + "';" );
						 LinkedList<String> destination = new LinkedList<String>();
						if(pays.next()){
							pays.previous();
							while(pays.next()){
								destination.add(pays.getString(1));
							}
							new FenetreTransporteur(id, destination);
						}
						else{
							System.out.println("NOON");
						}
						break;
						
					case 50:							
						this.dispose();
						 pays = stmt.executeQuery("SELECT pays FROM " + Constantes.base_douane + " WHERE id_douane = '" + id + "';" );
						if(pays.next()){
							dest = pays.getString(1);
							//System.out.println(dest);
							new FenetreDouane(id, dest);
						}
						break;

					/*case 60:
						this.dispose();
						FenetreGerant fg = new FenetreGerant(id);
						break;	*/						

					}		

				}
				else{
					System.out.println("connait pas");
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		FenetreConnexion2 fc = new FenetreConnexion2();
	}


}
