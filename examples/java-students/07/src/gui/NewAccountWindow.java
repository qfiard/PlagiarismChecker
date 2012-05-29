package gui;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.*;

import Share.FiredException;
import Share.IdentificationException;
import Share.User;
import Share.UserException;

import db.Query;

public class NewAccountWindow extends Window {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6832513604551250840L;
	private Query q;
	private JButton erase;
	private JButton account;
	private JButton ok;
	private JLabel jLabelLogin;
	private JLabel jLabelPassword;
	private JLabel jLabelName;
	private JLabel jLabelFirstName;
	private JLabel jLabelPassword2;
	private JPasswordField password;
	private JPasswordField password2;
	private JTextField login;
	private JTextField name;
	private JTextField firstName;
	private JComboBox prestige;
	private Color color;

	public NewAccountWindow() {
		super("Attente de connextion SQL", 500, 350);
		// Couleur rouge
		color = new Color(255,0,0); 
		try {
			q = new Query("evrard", "BEDE2011");
		} catch (SQLException e) {
			showInformationMessage("Problème avec la base de donnée", "Erreur");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			showInformationMessage("Il manque un fichier merci de reovir votre installation !", "Erreur");
			System.exit(1);
		}
		initComponents();
	}
	
	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
		showInformationMessage("String s", "title");
	}                                           

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jButtonAccountActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
		gui.LogWindow.main(null);
	}

	/**
	 * On ne vérifie ici que si chaque champ est rempli et si les mots de passes sont �gaux
	 * @return vrai si l'entrée est correct
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	private boolean check() throws SQLException, ClassNotFoundException {
		boolean d = true;
		d = checkHelper(jLabelLogin, login);
		d = checkHelper(jLabelName, name);
		d = checkHelper(jLabelFirstName, firstName);
		d = checkHelper(jLabelPassword, password);
		d = checkHelper(jLabelPassword2, password2);
		if (!Share.Tools.isSamePassword(password.getPassword(), password2.getPassword())) {
			// Permet de changer la couleur
			password.setForeground(color);
			password2.setForeground(color);
			d = false;
		}
		if (q.loginExist(login.getText())) {
			showInformationMessage("Login déjà existant", "Problème Enregistrement");
			return false;
		}
			
		return d;
	}
	
	private boolean checkHelper(javax.swing.JLabel l, javax.swing.JTextField entry) {
		if (entry.getText().equalsIgnoreCase("")) {
			l.setForeground(color);
			return false;
		}
		return true;
	}
	
	/**
	 * Permet de réinitialiser les couleurs
	 */
	private void newTry() {
		jLabelLogin.setForeground(Color.black);
		jLabelFirstName.setForeground(Color.black);
		jLabelName.setForeground(Color.black);
		jLabelPassword.setForeground(Color.black);
		jLabelPassword2.setForeground(Color.black);
		password.setForeground(Color.black);
		password2.setForeground(Color.black);
	}
	
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		
	}
	
	private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO envoyer les informations pour cr�er un compte
		
		
		newTry();
		
		try {
			if (check()) {
				showInformationMessage("Votre compte a bien été enregistré", "Inscription");
				String adress = "";
				String phone_number = "0";
				if (prestige.getSelectedIndex() == 0) {
					// TODO récuperer l'adresse et numéro de téléphone
					System.out.println("à faire");
				}
				// Permet de chercher un numéro de client non utilisé
				int number = (int) (Math.random() * 1000);
				
				while (q.numberExist(number + "")) 
					number = (int) (Math.random() * 1000);
				
				//q.addUser(name.getText(), firstName.getText(), login.getText(), Share.Tools.string_of_tabChar(password.getPassword()), "1", adress, phone_number, number + "");
				this.setVisible(false);
				new GreetingsWindow("Première visite", new User(login.getText(), Share.Tools.string_of_tabChar(password.getPassword())));
			}
		} catch (SQLException e) {
			showInformationMessage("Problème avec la base de donnée", "Erreur");
		} catch (ClassNotFoundException e) {
			showInformationMessage("Inconnu", "Erreur");;
		} catch (UserException e) {
			showInformationMessage("Connexion impossible", "Erreur");
			System.exit(1);
		} catch (IdentificationException e) {
			showInformationMessage("Connexion impossible problème des programateurs bravo !", "Erreur");
			System.exit(1);
		} catch (FiredException ek) {
			ek.printStackTrace();
		}
	}
	
	// efface le texte
	public void eraseEntry() {
		newTry();
		name.setText("");
		firstName.setText("");
		password.setText("");
		password2.setText("");
		login.setText("");
	}

	private void initComponents() {

		jLabelLogin = new javax.swing.JLabel();
		login = new javax.swing.JTextField(6);
		jLabelPassword = new javax.swing.JLabel();
		password = new javax.swing.JPasswordField(8);
		name = new javax.swing.JTextField(6);
		jLabelName = new javax.swing.JLabel();
		jLabelFirstName = new javax.swing.JLabel();
		firstName = new javax.swing.JTextField(6);
		jLabelPassword2 = new javax.swing.JLabel();
		password2 = new javax.swing.JPasswordField(8);
		erase = new javax.swing.JButton();
		account = new javax.swing.JButton();
		ok = new javax.swing.JButton();
		prestige = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Nouveau compte");
		setBackground(new java.awt.Color(99, 117, 215));
		setForeground(new java.awt.Color(99, 117, 215));
		setMinimumSize(new java.awt.Dimension(501, 300));

		jLabelLogin.setText("Login");

		login.setText("Login." + (int)(Math.random()*10000));
		login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		jLabelPassword.setText("Mot de passe");


		name.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField2ActionPerformed(evt);
			}
		});

		jLabelName.setText("Nom");

		jLabelFirstName.setText("Pr�nom");


		jLabelPassword2.setText("Confirmation");

		erase.setText("Effacer");
		erase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				eraseEntry();
			}
		});

		account.setBackground(new java.awt.Color(255, 102, 102));
		account.setText("Déjà un compte ?");
		account.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonAccountActionPerformed(evt);
			}
		});

		ok.setText("Ok");
		ok.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonOkActionPerformed(evt);
			}
		});

		prestige.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Client", "G�rant", "Emballeur", "Dounier", "Transporteur" }));
		prestige.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showInformationMessage("Pour les autre compte que client il faut un code", "Attention");
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(layout.createSequentialGroup()
														.addComponent(jLabelPassword)
														.addGap(10, 10, 10)
														.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(jLabelPassword2))
														.addGroup(layout.createSequentialGroup()
																.addComponent(prestige, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(erase, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(account)
																				.addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
																				.addGroup(layout.createSequentialGroup()
																						.addComponent(jLabelLogin)
																						.addGap(18, 18, 18)
																						.addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGap(18, 18, 18)
																						.addComponent(jLabelName)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGap(18, 18, 18)
																						.addComponent(jLabelFirstName)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(account)
						.addGap(55, 55, 55)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelLogin)
								.addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabelName)
								.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabelFirstName)
								.addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabelPassword2)
										.addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabelPassword))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(erase, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(prestige, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addContainerGap(91, Short.MAX_VALUE))
				);

		pack();
	}



	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewAccountWindow().setVisible(true);
			}
		});
	}
}



