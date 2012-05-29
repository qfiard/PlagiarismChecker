import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class EmballeurInterface extends Interface 
    implements ActionListener {
    // Connexion à la base
    private EmballeurConnexion conn;

    private JButton boutonListeCommandes, boutonEnregistrementColis;
    private JComboBox listeTypes;
    private JLabel labelIdClient = new JLabel("Id du client: ");
    private JLabel labelNumColis = new JLabel("N° du colis: ");
    private JLabel labelNumCommande = new JLabel("N° de la commande: ");
    private JLabel labelDateEmballage = new JLabel("Date (jj/mm/aaaa): ");
    private JLabel labelNumPalette = new JLabel("N° de la palette: ");
    private JRadioButton radioNormal, radioFragile, radioDangereux,
	radioFragileDangereux;
    private JTextField fieldIdClient, fieldNumColis, fieldNumCommande,
	fieldJours, fieldMois, fieldAnnee, fieldNumPalette;
    
    public EmballeurInterface(EmballeurConnexion conn) {
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

    // liste des operations dediees aux emballeurs
    public String[] getListeOperations() {
	String[] operations = {"Choisir une opération",
			       "Afficher la liste des commandes d'un client",
			       "Enregistrer un emballage"};
	return operations;
    }

    // annonce dediee aux emballeurs
    public String getAnnonce() {
	String annonce = 
	    "<html><p><center>SERVICE EMBALLEURS !<br/>" +
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

    // affiche les champs a remplir pour liste des commandes d'un client
    public void buildListeCommandesClient() {
	fieldIdClient = zoneSaisie(15);
	boutonListeCommandes = boutonValiderMenu();
	JComponent[] compo = {labelIdClient, fieldIdClient,
			      boutonListeCommandes};
	addToPanelMenu(compo);
	boutonEntete.setVisible(false);
    }

    // affiche les champs a remplir pour l'enregistrement d'un colis
    public void buildEnregistrementColis() {
	fieldNumColis = zoneSaisie(10);
	fieldNumCommande = zoneSaisie(10);
	fieldJours = zoneSaisie(3);
	fieldMois = zoneSaisie(3);
	fieldAnnee = zoneSaisie(3);

	String[] types = {"Sélectionnez le type du colis", "normal", 
			  "fragile", "dangereux", "fragile & dangereux"};
	listeTypes = new JComboBox(types);
	listeTypes.setSelectedIndex(0);

	fieldNumPalette = zoneSaisie(12);
	boutonEnregistrementColis = boutonValiderMenu();
	JComponent[] compo = {labelNumColis, fieldNumColis,
			      labelNumCommande, fieldNumCommande,
			      labelDateEmballage, fieldJours, fieldMois, 
			      fieldAnnee, listeTypes, labelNumPalette,
			      fieldNumPalette, boutonEnregistrementColis};
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
		buildListeCommandesClient();
		break;
	    case 2:
		buildEnregistrementColis();
		break;
	    }
	    panelMenu.validate();
	    panelMenu.repaint();
	} 
	else if (source == boutonEntete)
	    repaintPanelResultat();
	else if (source == boutonListeCommandes) {
	    String id_client = fieldIdClient.getText();
	    if (id_client.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		resultatEnCours = "Liste des commandes d'un client";
		contenuScroll = conn.listeCommandesClient(id_client);
	    }
	    repaintPanelResultat();
	}
	else if (source == boutonEnregistrementColis) {
	    indice = listeTypes.getSelectedIndex();
	    String type_colis = "";
	    switch(indice) {
	    case 1: type_colis = "N"; break;
	    case 2: type_colis = "F"; break;
	    case 3: type_colis = "D"; break;
	    case 4: type_colis = "DF"; break;
	    default: break;
	    }
	    String num_colis = fieldNumColis.getText();
	    String num_commande = fieldNumCommande.getText();
	    String jour = fieldJours.getText();
	    String mois = fieldMois.getText();
	    String annee = fieldAnnee.getText();
	    String date_emballage = annee+"-"+mois+"-"+jour;
	    String num_palette = fieldNumPalette.getText();
	    if (num_colis.equals("") || num_commande.equals("") 
		|| date_emballage.equals("") || type_colis.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		resultatEnCours = "Enregistrement d'un colis.";
		contenuScroll = 
		    conn.enregistrementColis(num_colis, num_commande, 
					     date_emballage, type_colis, 
					     num_palette);
	    }
	    repaintPanelResultat();
	} 
	else if (source == malvoyant)
	    setGoodColors();
	else if (source == aPropos)
	    JOptionPane.showMessageDialog
		(null, "Espace dédié aux emballeurs !");
	else if (source == quitter)
	    System.exit(1);
    } // Fin actionPerformed

}