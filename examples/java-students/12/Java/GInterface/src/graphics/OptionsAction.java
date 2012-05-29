package graphics;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class OptionsAction extends AbstractAction {
	private static final long serialVersionUID = -7924527130717946234L;
	ConnectionWindow fenetre;
	ConnectedWindow connected;
	RequestDB bdd;
	String texte;

	public OptionsAction(ConnectedWindow connected, ConnectionWindow fenetre,
			RequestDB bdd, String texte) {
		super(texte);
		this.texte = texte;
		this.fenetre = fenetre;
		this.connected = connected;
		this.bdd = bdd;
	}

	public void actionPerformed(ActionEvent e) {
		if (texte.equals("Déconnection")) {
			connected.dispose();
			fenetre.setVisible(true);
			fenetre.loginField.setText("");
			fenetre.passField.setText("");
		} else if (texte.equals("Quitter")) {
			connected.dispose();
			fenetre.dispose();
		}
		
		try {
			bdd.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}