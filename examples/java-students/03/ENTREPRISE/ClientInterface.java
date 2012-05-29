import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class ClientInterface extends Interface 
    implements ActionListener {
    // Connexion à la base
    private ClientConnexion conn;

    private JButton boutonChangementLoginPassword;
    private JLabel labelLogin = new JLabel("Nouveau login: ");
    private JLabel labelPassword = new JLabel("Nouveau mot de passe: ");
    private JTextField fieldLogin;
    private JPasswordField fieldPassword;

    protected static int numChoix;
    
    public ClientInterface(ClientConnexion conn) {
	super();
	this.conn = conn;
    }

    // construction de la fenetre
    public void build(){
	super.build();
	malvoyant.addActionListener(this);
	quitter.addActionListener(this);
	aPropos.addActionListener(this);
    }

    // liste des operations dediees aux clients
    public String[] getListeOperations() {
	String[] operations = {"Choisir une opération",
			       "Afficher l'état des commandes passées",
			       "Lister les produits disponibles",
			       "Changer le login et le mot de passe"};
	return operations;
    }

    // annonce dediee aux clients
    public String getAnnonce() {
	String annonce = 
	    "<html><p><center>SERVICE CLIENT !<br/>" +
	    "Bienvenue !</center></p></html>";
	return annonce;
    }
	 
    // panel d'entete contenant la liste des opérations
    public void buildPanelEntete() {
	super.buildPanelEntete();
	listeOperations.addActionListener(this);
	boutonEntete.addActionListener(this);
    }

    // bouton generique de validation
    public JButton boutonValiderMenu() {
	JButton boutonValiderMenu = new JButton("Valider");
	boutonValiderMenu.addActionListener(this);
	boutonValiderMenu.setBackground(Color.RED);
	return boutonValiderMenu;
    }

    // affiche les champs a remplir pour modification login & password
    public void buildChangementLoginPassword() {
	fieldLogin = zoneSaisie(15);
	fieldPassword = new JPasswordField(10);
	fieldPassword.setEchoChar('*');
	boutonChangementLoginPassword = boutonValiderMenu();
	JComponent[] compo = {labelLogin, fieldLogin, labelPassword,
			      fieldPassword, boutonChangementLoginPassword};
	addToPanelMenu(compo);
	boutonEntete.setVisible(false);
    }

    // fonction permettant l'interactivite de la fenetre
    public void actionPerformed(ActionEvent e) {
	int indice;
	Object source = e.getSource();
	if (source == listeOperations) {
	    boutonEntete.setVisible(true);
	    indice = listeOperations.getSelectedIndex();
	    panelMenu.removeAll();
	    switch(indice) {
	    case 1:
		resultatEnCours = "Affichage de l'état des commandes passées.";
		contenuScroll = conn.etatCommandes();
		break;
	    case 2:
		resultatEnCours = "Liste des produits disponibles.";
		contenuScroll = conn.produitsDisponibles();
		break;
	    case 3:
		buildChangementLoginPassword();
		break;
	    }
	    panelMenu.validate();
	    panelMenu.repaint();
	} 
	else if (source == boutonEntete)
	    repaintPanelResultat();
	else if (source == boutonChangementLoginPassword) {
	    String login = fieldLogin.getText();
	    String password = new String(fieldPassword.getPassword());
	    if (login.equals("") && password.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		resultatEnCours = "Changement du login et du mot de passe.";
		contenuScroll = conn.changeLoginPassword(login, password);
	    }
	    repaintPanelResultat();
	} else if (source == malvoyant)
	    setGoodColors();
	else if (source == aPropos)
	    JOptionPane.showMessageDialog
		(null, "Espace dédié aux clients !");
	else if (source == quitter)
	    System.exit(1);
    } // Fin actionPerformed

}