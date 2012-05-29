package gui;

import java.awt.Color;

import javax.swing.*;

public class Inscription extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5604001202592118968L;
	private JTextField name, firstName, login, address;
	private JLabel labelName, labelFirstName, labelLogin, labelPassword;
	private JLabel labelPassword2, labelAddress;
	private JPasswordField password, password2;;
	private Color color;
	
	public Inscription(){	
		labelName = new JLabel(" Nom : ");
		labelFirstName = new JLabel(" Prénom : ");
		labelLogin = new JLabel(" Login : ");
		labelPassword = new JLabel(" Mot de passe : ");
		labelPassword2 = new JLabel(" Confirmation mot de passe : ");
		labelAddress = new JLabel("\n Adresse : ");

		name = new JTextField(5);
		password = new JPasswordField(8);

		password.setEchoChar('*');
		password2 = new JPasswordField(8);

		password2.setEchoChar('*');
		
		
		firstName = new JTextField(5);
		address = new JTextField(30);
		
		this.add(labelPassword);
		this.add(password);
		this.add(labelPassword2);
		this.add(password2);
		
		this.add(labelName);
		this.add(name);
		this.add(labelFirstName);
		this.add(firstName);
		this.add(labelAddress);
		this.add(address);
	}
	
	// appelé lorsqu'on appuie sur le bouton "ok"
	public void check() {
		
		
		if (!Share.Tools.isSamePassword(password.getPassword(), password2.getPassword())) {
			// Couleur rouge
			color = new Color(255,0,0); 
			// Permet de changer la couleur
			password.setForeground(color);
			password2.setForeground(color);
		}

		
	}
	
	// efface le texte
	public void deleteEntry() {
		name.setText("");
		firstName.setText("");
		address.setText("");
		password.setText("");
		password2.setText("");
		login.setText("");
	}
	


}
