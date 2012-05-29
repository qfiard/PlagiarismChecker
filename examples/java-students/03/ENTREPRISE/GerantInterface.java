import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class GerantInterface extends Interface 
    implements ActionListener{
    // Connexion a là base
    private GerantConnexion conn;

    private static int numChoix;
    private JButton boutonPrix, boutonLicenciement;
    private JComboBox listeProduits;
    private JLabel labelPrix = new JLabel("Nouveau prix: ");
    private JLabel labelLicenciement = new JLabel("Id de l'employé: ");
    private JTextField fieldPrix, fieldLicenciement;

    public GerantInterface(GerantConnexion conn) {
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

    // liste des operations dediees au gerant
    public String[] getListeOperations() {
	String[] operations = 
	    {"Choisir une opération",
	     "Changer le prix d'un produit",
	     "Licencier un personnel inefficace",
	     "Accéder à la liste des emballeurs",
	     "Accéder à la liste des transporteurs",
	     "Accéder à la liste des clients",
	     "Lister le nombre de colis traités par jour par un emballeur",
	     "Afficher les produits les plus vendus",
	     "Accéder à la liste des clients les plus dépensiers"};
	return operations;
    }

    // annonce dediee au gerant
    public String getAnnonce() {
	String annonce = 
	    "<html><p><center>SERVICE GERANT !<br/>" +
	    "Bienvenue !<br/>" +
	    "Servir et non se servir ...</center></p></html>";
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

    // affiche les champs a remplir pour la recherche d'une commande 
    public void buildChangementPrix() {
	listeProduits = new JComboBox(conn.getListeProduits());
	listeProduits.setSelectedIndex(0);
	listeProduits.addActionListener(this);
	fieldPrix = zoneSaisie(15);
	boutonPrix = boutonValiderMenu();
	JComponent[] compo = {listeProduits, labelPrix, 
			      fieldPrix, boutonPrix};
	addToPanelMenu(compo);
	boutonEntete.setVisible(false);
    }

    // affiche les champs a remplir pour le licenciement d'un employe
    public void buildLicenciement() {
	fieldLicenciement = zoneSaisie(15);
	boutonLicenciement = boutonValiderMenu();
	JComponent[] compo = {labelLicenciement, fieldLicenciement,
			      boutonLicenciement};
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
		buildChangementPrix();
		break;
	    case 2:
		buildLicenciement();
		break;
	    case 3:
		resultatEnCours = "Affichage de la liste des emballeurs";
		contenuScroll = conn.listeEmballeurs();
		break;
	    case 4:
		resultatEnCours = "Affichage de la liste des transporteurs";
		contenuScroll = conn.listeTransporteurs();
		break;
	    case 5:
		resultatEnCours = "Affichage de la liste des clients";
		contenuScroll = conn.listeClients();
		break;
	    case 6:
		resultatEnCours = 
		    "Quota de colis traités par jour pour chaque emballeur";
		contenuScroll = conn.quotaEmballeur();
		break;
	    case 7:
		resultatEnCours =
		    "Affichage de la liste des produits les plus vendus.";
		contenuScroll = conn.produitsLesPlusVendus();
		break;
	    case 8:
		resultatEnCours =
		    "Affichage de la liste des clients les plus dépensiers.";
		contenuScroll = conn.clientsLesPlusDepensiers();
		break;
	    }
	    panelMenu.validate();
	    panelMenu.repaint();
	} 
	else if (source == boutonEntete)
	    repaintPanelResultat();
	else if (source == boutonPrix) {
	    String prix = fieldPrix.getText();
	    String produit = listeProduits.getSelectedItem().toString();
	    if (produit.equals("Sélectionnez un produit") || prix.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		try {
		    int prix2 = Integer.parseInt(prix);
		    resultatEnCours = "Changement du prix d'un produit";
		    contenuScroll = conn.changementPrix(produit, prix2);
		} catch (NumberFormatException ex) {
		    contenuScroll = conn.champsNonRenseignes();
		}
	    }
	    repaintPanelResultat();
	}
	else if (source == boutonLicenciement) {
	    String id = fieldLicenciement.getText();
	    if (id.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else{
		resultatEnCours = "Licenciement d'un employé";
		contenuScroll = conn.licenciementPersonnel(id);
	    }
	    repaintPanelResultat();
	}
	else if (source == malvoyant)
	    setGoodColors();
	else if (source == aPropos)
	    JOptionPane.showMessageDialog
		(null, "Espace dédié au gérant !");
	else if (source == quitter)
	    System.exit(1);
    } // Fin actionPerformed

}