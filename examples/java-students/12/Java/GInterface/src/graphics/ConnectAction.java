package graphics;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ConnectAction extends AbstractAction {
	private static final long serialVersionUID = 1312624864862241770L;
	private ConnectionWindow fenetre;
	public ConnectedWindow test;
	public RequestDB bdd;
	public String fonction;
	public int id;

	public ConnectAction(ConnectionWindow fenetre, String texte) {
		super(texte);
		this.fenetre = fenetre;
	}

	public void actionPerformed(ActionEvent e) {
		String texteLogin = fenetre.getLoginField().getText();
		String textePass = fenetre.getPassField().getText();

		try {
			bdd = new RequestDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			if (bdd.CheckLogin(texteLogin, textePass)) {
				fenetre.setVisible(false);
				fonction = bdd.getFunction(texteLogin);
				if (fonction.equals("douane")) {
					id = Integer.parseInt(bdd.getId(texteLogin));
					test = new ConnectedWindow(fenetre, id, fonction);
				} else if (fonction.equals("gerant")) {
					test = new ConnectedWindow(fenetre, 0, fonction);
				}
				test.setVisible(true);
			} else {
				fenetre.loginField.setText("");
				fenetre.passField.setText("");
				fenetre.getLabel().setText("Mauvais log/mdp");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}