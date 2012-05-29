import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

public abstract class Interface extends JFrame {
    protected static String resultatEnCours, titreFenetre = "Service d'exportation";
    protected JButton boutonEntete;
    protected JComboBox listeOperations;
    protected JLabel labelResultatEnCours;
    protected JMenuItem malvoyant, quitter, aPropos;
    protected JPanel panel, panelMenu, panelResultat, panelAnnonce, panelPied, 
	panelEntete, panelTexteEntete, panelDeco1, panelDeco2, panelDeco3;
    protected JScrollPane contenuScroll;
    
    public Interface() {
	super();
	build();
    }

    public abstract String[] getListeOperations();

    public abstract String getAnnonce();

    public abstract JButton boutonValiderMenu();

    public void build(){
	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("Options");
	malvoyant = new JMenuItem("Mal-voyant");
	menu1.add(malvoyant);
	quitter = new JMenuItem("Quitter");
	menu1.add(quitter);
	menuBar.add(menu1);
	JMenu menu2 = new JMenu("?");
	aPropos = new JMenuItem("A propos");
	menu2.add(aPropos);
	menuBar.add(menu2);
	setJMenuBar(menuBar);
	JFrame.setDefaultLookAndFeelDecorated(true);
	setTitle(titreFenetre);
	setSize(1200, 740);
	setLocationRelativeTo(null);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setContentPane(buildContentPane());
    }

    // panel principal qui contient tous les autres JPanel
    public JPanel buildContentPane(){
	panel = new JPanel();
	panel.setLayout(null);
	panel.setBackground(Color.GRAY);
       	buildPanelMenu();
	buildPanelDeco();
	buildPanelAnnonce();
	buildPanelEntete();
	buildPanelTexteEntete();
	buildPanelPied();
	buildPanelResult();
	panel.setOpaque(true);
	return panel;
    }
    
    // panel de gauche pour les requetes interactives
    public void buildPanelMenu() {
	panelMenu = new JPanel();
	panelMenu.setBackground(Color.LIGHT_GRAY);
	panelMenu.setSize(300, 520);
	panelMenu.setLocation(10, 90);
	panel.add(panelMenu);
	panelMenu.setVisible(true);
    }
   
    // panels de decoration
    public void buildPanelDeco() {
	panelDeco1 = new JPanel();
	panelDeco1.setBackground(Color.PINK);
	panelDeco1.setSize(300, 15);
	panelDeco1.setLocation(10, 655);
	panel.add(panelDeco1);
	panelDeco2 = new JPanel();
	panelDeco2.setBackground(Color.LIGHT_GRAY);
	panelDeco2.setSize(300, 15);
	panelDeco2.setLocation(10, 635);
	panel.add(panelDeco2);
	panelDeco3 = new JPanel();
	panelDeco3.setBackground(Color.ORANGE);
	panelDeco3.setSize(300, 15);
	panelDeco3.setLocation(10, 615);
	panel.add(panelDeco3);
    }

    // panel de haut à gauche contenant l'annonce de l'utilisateur
    public void buildPanelAnnonce() {
	panelAnnonce = new JPanel();
	panelAnnonce.setBackground(Color.PINK);
	panelAnnonce.setSize(300, 70);
	panelAnnonce.setLocation(10, 10);
	String annonce = getAnnonce();
	JLabel labelAnnonce = new JLabel(annonce);
	panelAnnonce.add(labelAnnonce);
	panel.add(panelAnnonce);
    }
	 
    // panel d'entete contenant la liste des operations
    public void buildPanelEntete() {
	panelEntete = new JPanel();
	panelEntete.setBackground(Color.PINK);
	panelEntete.setSize(855, 35);
	panelEntete.setLocation(320, 10);
	listeOperations = new JComboBox(getListeOperations());
	listeOperations.setSelectedIndex(0);
	panelEntete.add(listeOperations);
	boutonEntete = new JButton("Valider");
	boutonEntete.setBackground(Color.RED);
	panelEntete.add(boutonEntete);
	panel.add(panelEntete);
	boutonEntete.setVisible(false);
    }

    // panel d'entete contenant le resultat en cours d'affichage
    public void buildPanelTexteEntete() {
	panelTexteEntete = new JPanel();
	panelTexteEntete.setBackground(Color.LIGHT_GRAY);
	panelTexteEntete.setSize(855, 23);
	panelTexteEntete.setLocation(320, 40);
	String affiche = "Sélectionnez une requête dans la liste déroulante";
	labelResultatEnCours = new JLabel(affiche);
	panelTexteEntete.add(labelResultatEnCours);
	panel.add(panelTexteEntete);
    }

    // panel du bas de page
    public void buildPanelPied() {
	panelPied = new JPanel();
	panelPied.setBackground(Color.ORANGE);
	panelPied.setSize(862, 15);
	panelPied.setLocation(320, 725);
	panel.add(panelPied);
    }

    // panel contenant le resultat de la requete choisie
    public void buildPanelResult() {
	panelResultat = new JPanel();
	panelResultat.setBackground(Color.ORANGE);
	panelResultat.setSize(855, 600);
	panelResultat.setLocation(320, 70);
	panel.add(panelResultat);
    }

    // mise a jour du panel affichant le resultat
    public void repaintPanelResultat() {
	panelResultat.removeAll();
	labelResultatEnCours.setText(resultatEnCours);
	panelResultat.add(contenuScroll);
	panelResultat.validate();
	panelResultat.repaint();
    }
    
    // ajout du contenu de compo au JPanel panelMenu
    public void addToPanelMenu(JComponent[] compo) {
	for (int i=0; i<compo.length; i++) panelMenu.add(compo[i]);
    }

    // zone generique de saisie de texte
    public JTextField zoneSaisie(int taille) {
	JTextField zone = new JTextField();
	zone.setColumns(taille);
	return zone;
    }

    // definit les bonnes couleurs de l'interface graphique
    public void setGoodColors() {
	if (boutonEntete.getBackground() == Color.RED) {
	    Component[] panelComponents = panel.getComponents();
	    for (int i=0; i<panelComponents.length; i++)
		setWhite(panelComponents[i]);
	} else {
	    JComponent[] grayCompo = {panelMenu, panelTexteEntete, 
				      panelDeco2};
	    JComponent[] orangeCompo = {panelResultat, panelPied, 
					panelDeco3};
	    JComponent[] pinkCompo = {panelEntete, panelAnnonce, 
				      panelDeco1};
	    JComponent[] redCompo = {boutonEntete};
	    setColor(grayCompo, Color.LIGHT_GRAY);
	    setColor(orangeCompo, Color.ORANGE);
	    setColor(pinkCompo, Color.PINK);
	    setColor(redCompo, Color.RED);
	}
    }

    // affiche les composants en noir et blanc
    private void setWhite(Component compo) {
	if (compo instanceof JComponent) {
	    Component[] components = ((JComponent)compo).getComponents();
	    for (int i=0; i<components.length; i++)
		if (null != components[i]) setWhite(components[i]);
	}
	compo.setBackground(Color.WHITE);
    }

    // affiche les composants en couleur
    private void setColor(JComponent[] compo, Color color) {
	for (int i=0; i<compo.length; i++)
	    if (null != compo[i]) compo[i].setBackground(color);
    }

}
