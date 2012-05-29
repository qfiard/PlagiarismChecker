import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Login extends JFrame implements ActionListener{
    // Connexion a la base
    private PersonneConnexion conn;

    JPanel textPanel, panelForTextFields, completionPanel;
    JLabel titleLabel, usernameLabel, passwordLabel, userLabel, passLabel;
    JTextField usernameField;
    JPasswordField loginField;
    JButton loginButton;

    public Login(PersonneConnexion conn) {
	super();
	this.conn = conn;
	build();
    }

    private void build() {
	JFrame.setDefaultLookAndFeelDecorated(false);
	setTitle("Connexion");
	setSize(330,200);
	setLocationRelativeTo(null); 
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	setContentPane(createContentPane());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel createContentPane (){
        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        titleLabel = new JLabel("Connexion au service d'exportation", 
				JLabel.CENTER);
        titleLabel.setLocation(0,0);
        titleLabel.setSize(350, 30);
        titleLabel.setHorizontalAlignment(0);
        panel.add(titleLabel);

        // Creation of a Panel to contain the JLabels
        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setLocation(10, 35);
        textPanel.setSize(100, 80);
        panel.add(textPanel);

        // Username Label
        usernameLabel = new JLabel("Login");
        usernameLabel.setLocation(0, 0);
        usernameLabel.setSize(100, 40);
        usernameLabel.setHorizontalAlignment(4);
        textPanel.add(usernameLabel);

        // Login Label
        passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setLocation(0, 40);
        passwordLabel.setSize(100, 40);
        passwordLabel.setHorizontalAlignment(4);
        textPanel.add(passwordLabel);

        // TextFields Panel Container
        panelForTextFields = new JPanel();
        panelForTextFields.setLayout(null);
        panelForTextFields.setLocation(120, 40);
        panelForTextFields.setSize(100, 70);
        panel.add(panelForTextFields);

        // Username Textfield
        usernameField = new JTextField(8);
        usernameField.setLocation(0, 0);
        usernameField.setSize(100, 30);
        panelForTextFields.add(usernameField);

        // Login Textfield
        loginField = new JPasswordField(8);
	loginField.setEchoChar('*');
        loginField.setLocation(0, 40);
        loginField.setSize(100, 30);
        panelForTextFields.add(loginField);

        // Creation of a Panel to contain the completion JLabels
        completionPanel = new JPanel();
        completionPanel.setLayout(null);
        completionPanel.setLocation(240, 35);
        completionPanel.setSize(70, 80);
        panel.add(completionPanel);

        // Button for Logging in
        loginButton = new JButton("Login");
        loginButton.setLocation(130, 120);
        loginButton.setSize(80, 30);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        panel.setOpaque(true);    
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
	    String login = usernameField.getText().trim();
	    char[] password = loginField.getPassword();
	    
	    String typePersonne = conn.connexionValide(login, password);
	    if (typePersonne.equals(""))
		JOptionPane.showMessageDialog(null, "Mauvais login ou mauvais mot de passe");
	    else {
		dispose(); // fermeture de la fenetre de connexion
		JFrame fenetre = null;
		if (typePersonne.equals("douane")) {
		    DouaneConnexion douaneConn = new DouaneConnexion(conn, login);
		    fenetre = new DouaneInterface(douaneConn);
		} else if (typePersonne.equals("gerant")) {
		    GerantConnexion gerantConn = new GerantConnexion(conn);
		    fenetre = new GerantInterface(gerantConn);
		} else if (typePersonne.equals("transporteur")) {
		    TransporteurConnexion transporteurConn = new TransporteurConnexion(conn, login);
		    fenetre = new TransporteurInterface(transporteurConn);
		} else if (typePersonne.equals("emballeur")) {
		    EmballeurConnexion emballeurConn = new EmballeurConnexion(conn, login);
		    fenetre = new EmballeurInterface(emballeurConn);
		} else if (typePersonne.equals("client")) {
		    ClientConnexion clientConn = new ClientConnexion(conn, login);
		    fenetre = new ClientInterface(clientConn);
		} 
		fenetre.setVisible(true);
	    }
	}
    }
    
} 
