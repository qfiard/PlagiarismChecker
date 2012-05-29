package gui;

import javax.swing.*;

import Share.User;
import Share.UserException;

public class GreetingsWindow extends Window {
	
	public GreetingsWindow(String name, User u) throws UserException {
		super(name, 300, 500);
		this.u = u;
		this.setVisible(false);
		if (debug) 
			System.out.println(u.getId());
		
		switch (Integer.parseInt(this.u.getPrestige())) {
		case 0 : initComponentsBoss();
		break;
		case 1 : initComponentsCustomer(); 
		break;
		case 2 : initComponentsPacker();
		break;
		case 3 : initComponentsCustoms();
		break;
		case 4 : initComponentsTransporter();
		break;
		default : 
			throw new UserException();
		}
		
	}
	
	// On va dire que c'est l'accueil, on fera un switch et selon l'utilisateur on affichera des chose diff�rente 
	// on va essayer d'avoir différent type d'init selon le type de compte 
	
	
	private void initComponentsBoss() {


	}
	private void initComponentsCustomer() {

	}    
	
	private void initComponentsPacker() {

	}   

	private void initComponentsCustoms() {
		new Customs(this.u);
	}    

	private void initComponentsTransporter() {

	}    
	



}
