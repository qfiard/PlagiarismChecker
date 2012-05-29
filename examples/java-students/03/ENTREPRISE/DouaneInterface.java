import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class DouaneInterface extends Interface 
    implements ActionListener {
    // Connexion à la base
    private DouaneConnexion conn;

    protected static int resultatControle;
    protected JButton boutonCommande, boutonControle, boutonDetails;
    protected JLabel labelContenu, labelPassage;
    protected JLabel labelColis = new JLabel("N° colis :");
    protected JLabel labelCommande = new JLabel("N° commande :");
    protected JLabel labelDestination = new JLabel("Destination : ");
    protected JRadioButton radioOui, radioNon;
    protected JTextField fieldDestinationCommande, fieldCommandeCommande, 
	fieldCommandeDetails, fieldContenu, fieldControleColis; 
    
    public DouaneInterface(DouaneConnexion conn) {
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

    // liste des operations dediees a la douane
    public String[] getListeOperations() {
	String[] operations = {"Choisir une opération",
			       "Afficher la liste des commandes expédiées",
			       "Afficher la liste des commandes contrôlées",
			       "Afficher la liste des commandes non contrôlées",
			       "Rechercher des commandes",
			       "Entrer les résultats d'un contrôle",
			       "Afficher les détails d'une commande",
			       "Statistiques sur les contrôles"};
	return operations;
    }

    // annonce dediee a la douane
    public String getAnnonce() {
	String annonce = 
	    "<html><p><center>SERVICE DOUANE !<br/>" +
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
    public void buildRechercheCommande() {
	fieldCommandeCommande = zoneSaisie(14);
	fieldDestinationCommande = zoneSaisie(15);
    	labelContenu = new JLabel("Contenu : ");
	fieldContenu = zoneSaisie(17);

	boutonCommande = boutonValiderMenu();
	JComponent[] compo = {labelCommande, fieldCommandeCommande, 
			      labelDestination, fieldDestinationCommande, 
			      labelContenu, fieldContenu, boutonCommande};
	addToPanelMenu(compo);
	boutonEntete.setVisible(false);
    }

    // affiche les champs a remplir pour le controle d'une commande
    public void buildControleColis() {
	resultatControle = 0;
	fieldControleColis = zoneSaisie(14);
	labelPassage = new JLabel("Commande acceptée ? ");
	radioOui = new JRadioButton("oui");
	radioOui.addActionListener(this);
	radioNon = new JRadioButton("non");
	radioNon.addActionListener(this);
	ButtonGroup groupeBouton = new ButtonGroup();
	groupeBouton.add(radioOui);
	groupeBouton.add(radioNon);
	boutonControle = boutonValiderMenu();
	JComponent[] compo = {labelColis, fieldControleColis,
			      labelPassage, radioOui, radioNon, boutonControle};
	addToPanelMenu(compo);
	boutonEntete.setVisible(false);
    }

    // affiche les champs a remplir pour les details d'une commande
    public void buildDetailsCommande() {
	fieldCommandeDetails = zoneSaisie(14);
	boutonDetails = boutonValiderMenu();
	JComponent[] compo = {labelCommande, fieldCommandeDetails, 
			      boutonDetails};
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
		resultatEnCours = "Affichage de la liste des " +
		    "commandes expédiées : " + conn.getPays();
		contenuScroll = conn.commandesExpediees();
		break;
	    case 2:
		resultatEnCours = "Affichage de la liste des " +
		    "commandes contrôlées : " + conn.getPays();
		contenuScroll = conn.commandesControlees();
		break;
	    case 3:
		resultatEnCours = "Affichage de la liste des "+
		    "commandes non contrôlées : " + conn.getPays();
		contenuScroll = conn.commandesNonControlees();
		break;
	    case 4:
		buildRechercheCommande();
		break;
	    case 5:
		buildControleColis();
		break;
	    case 6:
		buildDetailsCommande();
		break;
	    case 7:
		resultatEnCours = "Statistiques sur les contrôles : "+
		    conn.getPays();
		contenuScroll = conn.statistiquesControles();
	    }
	    panelMenu.validate();
	    panelMenu.repaint();
	} 
	else if (source == boutonEntete)
	    repaintPanelResultat();
	else if (source == boutonCommande){
	    String destination = fieldDestinationCommande.getText();
	    String commande = fieldCommandeCommande.getText();
	    String contenuCommande = fieldContenu.getText();
	    if (destination.equals("") 
		&& commande.equals("") && contenuCommande.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		resultatEnCours = "Affichage de la commande recherchée";
		contenuScroll = 
		    conn.rechercheCommande(destination, commande, 
					   contenuCommande);
	    }
	    repaintPanelResultat();
	}
	else if (source == radioOui)
	    resultatControle = 1;
	else if (source == radioNon)
	    resultatControle = 2;
	else if (source == boutonControle) {
	    String num_colis = fieldControleColis.getText();
	    if (0 == resultatControle || num_colis.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		try {
		    int num = Integer.parseInt(num_colis);
		    boolean result = resultatControle==1 ? true : false;
		    resultatEnCours = 
			"Entrée de données: contrôle d'un colis";
		    contenuScroll = 
			conn.enregistrerControle(num, result);
		} catch(NumberFormatException ex) {
		    contenuScroll = conn.champsNonRenseignes();
		}
	    }
	    repaintPanelResultat();
	} else if (source == boutonDetails) {
	    String commande = fieldCommandeDetails.getText();
	    if (commande.equals(""))
		contenuScroll = conn.champsNonRenseignes();
	    else {
		resultatEnCours = 
		    "Affichage des détails d'une commande";
		contenuScroll = conn.detailsCommande(commande);
	    }
	    repaintPanelResultat();
	} else if (source == malvoyant)
	    setGoodColors();
	else if (source == aPropos)
	    JOptionPane.showMessageDialog
		(null, "Espace dédié aux opérations douanières !");
	else if (source == quitter)
	    System.exit(1);
    } // Fin actionPerformed

}