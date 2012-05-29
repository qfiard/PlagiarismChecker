import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class TransporteurInterface extends Interface 
    implements ActionListener {
    // Connexion à la base
    private TransporteurConnexion conn;
    
    public TransporteurInterface(TransporteurConnexion conn) {
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

    // liste des operations dediees aux transporteurs
    public String[] getListeOperations() {
	String[] operations = {"Choisir une opération",
			       "Afficher les dates limite de livraison"};
	return operations;
    }

    // annonce dediee aux transporteurs
    public String getAnnonce() {
	String annonce = 
	    "<html><p><center>SERVICE TRANSPORTEURS !<br/>" +
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
		resultatEnCours = "Dates limite de livraison souhaitées.";
		contenuScroll = conn.datesLivraisonSouhaitees();
		break;
	    }
	    panelMenu.validate();
	    panelMenu.repaint();
	} 
	else if (source == boutonEntete)
	    repaintPanelResultat();
	else if (source == malvoyant)
	    setGoodColors();
	else if (source == aPropos)
	    JOptionPane.showMessageDialog
		(null, "Espace dédié aux transporteurs !");
	else if (source == quitter)
	    System.exit(1);
    } // Fin actionPerformed

}
