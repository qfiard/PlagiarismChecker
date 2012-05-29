package graphics;

import javax.swing.*;
import java.awt.event.*;

public class AproposAction extends AbstractAction {
	private static final long serialVersionUID = -8874263368273380218L;
	ConnectedWindow connected;

	public AproposAction(ConnectedWindow connected, String texte) {
		super(texte);
		this.connected = connected;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Programme réalisé par François"
				+ " Colas et Yaoyao Yang");
	}
}