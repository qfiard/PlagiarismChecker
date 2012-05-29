import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class FenetreConnexion extends JFrame {

	private JPanel container = new JPanel();
	private JTextField login = new JTextField();
	private JPasswordField  mdp = new JPasswordField ();
	private JLabel label = new JLabel("Login            ");
	private JLabel label2 = new JLabel("Mot de passe");
	private JButton b = new JButton ("Connexion");
	private JButton b2 = new JButton ("Nouveau Client");
	SQL connecte;

	/**
	 * Constructeur de l'objet 
	 */
	public FenetreConnexion(){
		try {
			this.connecte = new SQL();

			this.setTitle("Connexion");
			this.setSize(300, 150);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);

			container.setBackground(Color.white);
			container.setLayout(new BorderLayout());

			JPanel top = new JPanel();    
			this.container = top;

			Font police = new Font("Arial", Font.BOLD, 14);
			login.setFont(police);
			login.setPreferredSize(new Dimension(150, 30));
			login.setForeground(Color.BLUE);
			login.setFocusable(true);

			mdp.setPreferredSize(new Dimension(150, 30));

			top.add(label);
			top.add(login);
			top.add(label2);
			top.add(mdp);
			top.add(b);
			top.add(b2);
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					String log = login.getText().toUpperCase();
					char [] m = mdp.getPassword();
					String MDP = "";
					for(int i = 0 ; i < m.length; i++)
						MDP += m[i];

					try {
						int c = verif(log,MDP);
						switch(c){
						case 1:
							connecte.close();
							new FenetreClient(log,MDP);
							FenetreConnexion.this.setVisible(false);
							break;
						case 2:
							connecte.close();
							new FenetreDouane(log);
							FenetreConnexion.this.setVisible(false);
							break;
						case 3:
							connecte.close();
							new FenetreEmballeur(log);
							FenetreConnexion.this.setVisible(false);
							break;
						case 4:
							connecte.close();
							new FenetreGerant(log);
							FenetreConnexion.this.setVisible(false);
							break;
						case 5:
							connecte.close();
							new FenetreTransporteur(log);
							FenetreConnexion.this.setVisible(false);
							break;
						case -1:
							JOptionPane jo = new JOptionPane();
							if(FenetreConnexion.this.login.getText().equals("")){
								FenetreConnexion.this.label.setForeground(Color.red);
								if(MDP.isEmpty()){
									FenetreConnexion.this.label2.setForeground(Color.black);
									jo.showMessageDialog(null, "Veuillez entrer un login", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
								else{
									FenetreConnexion.this.label2.setForeground(Color.red);
									jo.showMessageDialog(null, "Veuillez entrer un login et un mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
								if(MDP.isEmpty()){
									FenetreConnexion.this.label.setForeground(Color.black);
									FenetreConnexion.this.label2.setForeground(Color.red);
									jo.showMessageDialog(null, "Veuillez entrer un mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
								else{
									FenetreConnexion.this.label.setForeground(Color.black);
									FenetreConnexion.this.label2.setForeground(Color.black);
									jo.showMessageDialog(null, "Erreur Identification", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
							}
							FenetreConnexion.this.login.setText("");
							FenetreConnexion.this.mdp.setText("");
							FenetreConnexion.this.login.requestFocus();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}        	
			});
			b2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {
						connecte.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					new FenetreNouveauClient();
					FenetreConnexion.this.setVisible(false);
				}
			});
			this.setResizable(false);
			this.setContentPane(top);
			this.setVisible(true);  
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}       

	public int verif(String login,String mdp) throws SQLException{
		if(connecte.verif_client(login, mdp))
			return 1;
		else if(connecte.verif_douane(login, mdp))
			return 2;
		else if(connecte.verif_emballeur(login, mdp))
			return 3;
		else if(connecte.verif_gerant(login, mdp))
			return 4;
		else if(connecte.verif_transporteur(login, mdp))
			return 5;
		return -1;
	}
}
