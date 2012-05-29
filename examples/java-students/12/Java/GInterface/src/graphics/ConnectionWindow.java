package graphics;

import java.awt.*;
import javax.swing.*;

public class ConnectionWindow extends JFrame {
	private static final long serialVersionUID = 3061420912734427771L;
	public JTextField loginField;
	public JPasswordField passField;
	private JLabel label;
	private JButton bouton;

	public ConnectionWindow() {
		super();
		build();
	}

	private void build() {
		// On donne un titre à l'application
		setTitle("Connection");
		// On donne une taille à notre fenêtre
		setSize(160, 140);
		// On centre la fenêtre sur l'écran
		setLocationRelativeTo(null);
		// On autorise la redimensionnement de la fenêtre
		setResizable(true);
		// On dit à l'application de se fermer lors du clic sur la croix
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);

		loginField = new JTextField(10);
		passField = new JPasswordField(10);
		bouton = new JButton(new ConnectAction(this, "Connection"));
		panel.add(loginField);
		panel.add(passField);
		panel.add(bouton);

		label = new JLabel("");
		panel.add(label);

		return panel;
	}

	public JTextField getLoginField() {
		return loginField;
	}

	public JTextField getPassField() {
		return passField;
	}

	public JLabel getLabel() {
		return label;
	}
}
