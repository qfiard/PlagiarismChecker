import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EmployeeUI 
{
    private static JPanel                         res;
    private static Box                            jp;
    private static JPanel                         dataConnection;
    private static JTextField                     password;
    private static JTextField                     id;
    private static QueryDB                        _query;
    private static JComboBox                      status;
    private static JComboBox                      actionsPacker;
    private static JComboBox                      actionsTransporter;
    
    private static JTextField                     typePackage;
    private static JButton                        buttonTypePackage;
    
    private static JTextField                     deliveryDate;
    private static JButton                        buttonDeliveryDate;

    private static JTextField                     listCommand;
    private static JButton                        buttonListCommand;
    
    private static JTextField                     packingDate;
    private static JButton                        buttonEnterPackage;

    private static JTextField                     paletteNumber;
    private static JTextField                     packingDate2;
    private static JButton                        buttonEnterPalette;

    /* Fonction qui creee le panneau Employe */
    public static JPanel buildEmployeePanel()
    {
	// Creation recepteur requetes
	ConnectionDB _connect = new ConnectionDB();
	_query = new QueryDB(_connect);
	
	// Creation du panneau
	res = new JPanel();
	
	// Les champs qui vont permettre l'authentification du douanier
	dataConnection = new JPanel();
	res.add(dataConnection);
	id = new JTextField("Numero d'employe ?", 20);
	password = new JTextField("Mot de passe ?", 20);
	dataConnection.add(id);
	dataConnection.add(password);
	
	// La boite verticale qui va permettre la mise en forme
	jp = Box.createVerticalBox();
	res.add(jp);
	
	/* ***        Menu deroulant des status possibles d'un employe *** */
	String[] _status = {" - ",              // 
			    "Emballeur",        // 1
			    "Transporteur"};    // 2
	status = new JComboBox<String>(_status);
	ListenerStatusBox lStatusBox = new ListenerStatusBox();
	status.addActionListener(lStatusBox);
	res.add(status);



       	/* ***   Menu deroulant des actions possibles d'un transporteur  *** */


	String[] _actionsTransporter = {" - ",                                   // 0
					"Consulter le type d'un colis",                       // 1
					"Date limite de livraison d'un colis"};  // 2
	actionsTransporter = new JComboBox<String>(_actionsTransporter);
	ListenerActionTransporterBox lActionsTransporterBox = new ListenerActionTransporterBox();
	actionsTransporter.addActionListener(lActionsTransporterBox);
	res.add(actionsTransporter);
	actionsTransporter.setVisible(false);




	/* ***        Menu deroulant des actions possibles d'un emballeur  *** */
	String[] _actionsPacker = {" - ",                                   // 0
				   "Lister les commandes d'un client",                       // 1
				   "Entrer un colis dans le systeme",    //2
				   "Entrer une palette dans le systeme"}; //3
	actionsPacker = new JComboBox<String>(_actionsPacker);
	ListenerActionPackerBox lActionsPackerBox = new ListenerActionPackerBox();
	actionsPacker.addActionListener(lActionsPackerBox);
	res.add(actionsPacker);
	actionsPacker.setVisible(false);

	/* ***          UI pour consulter le type d'un colis           *** */
	typePackage = new JTextField("Numero du colis ?", 25);
	typePackage.setVisible(false);
	res.add(typePackage);
	buttonTypePackage = new JButton("Valider");
	buttonTypePackage.setVisible(false);
	ListenerButtonTypePackage lButtonTypePackage 
	    = new ListenerButtonTypePackage();
	buttonTypePackage.addActionListener(lButtonTypePackage);
	res.add(buttonTypePackage);

	/* ***          UI pour consulter la date limite de livraison d'un colis           *** */
	deliveryDate = new JTextField("Numero du colis ?", 25);
	deliveryDate.setVisible(false);
	res.add(deliveryDate);
	buttonDeliveryDate = new JButton("Valider");
	buttonDeliveryDate.setVisible(false);
	ListenerButtonDeliveryDate lButtonDeliveryDate 
	    = new ListenerButtonDeliveryDate();
	buttonDeliveryDate.addActionListener(lButtonDeliveryDate);
	res.add(buttonDeliveryDate);

	/* ***          UI pour lister les commandes d'un client          *** */
	listCommand = new JTextField("Identifiant du client ?", 25);
	listCommand.setVisible(false);
	res.add(listCommand);
	buttonListCommand = new JButton("Valider");
	buttonListCommand.setVisible(false);
	ListenerButtonListCommand lButtonListCommand 
	    = new ListenerButtonListCommand();
	buttonListCommand.addActionListener(lButtonListCommand);
	res.add(buttonListCommand);


	/* ***          UI pour entrer un colis dans le systeme          *** */
	packingDate  = new JTextField("Date d'emballage ? (format AAAAMMJJ)",
				      25);
	packingDate.setVisible(false);
	res.add(packingDate);

	buttonEnterPackage = new JButton("Entrer");
	buttonEnterPackage.setVisible(false);
	ListenerButtonEnterPackage lButtonEnterPackage 
	    = new ListenerButtonEnterPackage();
	buttonEnterPackage.addActionListener(lButtonEnterPackage);
	res.add(buttonEnterPackage);


	/* ***          UI pour entrer une palette dans le systeme          *** */
	paletteNumber  = new JTextField("Numero de la palette ?", 25);
	paletteNumber.setVisible(false);
	res.add(paletteNumber);

	packingDate2  = new JTextField("Date d'emballage au format AA-MM-JJ", 25);
	packingDate2.setVisible(false);
	res.add(packingDate2);

	buttonEnterPalette = new JButton("Entrer");
	buttonEnterPalette.setVisible(false);
	ListenerButtonEnterPalette lButtonEnterPalette 
	    = new ListenerButtonEnterPalette();
	buttonEnterPalette.addActionListener(lButtonEnterPalette);
	res.add(buttonEnterPalette);


	return res;
    }

    /* Tout cacher pour mieux afficher l'interessant... ! */
    private static void hideAll()
    {
	actionsPacker.setVisible(false);
	actionsTransporter.setVisible(false);
	typePackage.setVisible(false);
	buttonTypePackage.setVisible(false);
	deliveryDate.setVisible(false);
	buttonDeliveryDate.setVisible(false);
	listCommand.setVisible(false);
	buttonListCommand.setVisible(false);
	packingDate.setVisible(false);
	buttonEnterPackage.setVisible(false);
	paletteNumber.setVisible(false);
	packingDate2.setVisible(false);
	buttonEnterPalette.setVisible(false);
    }

    /* Fonction qui va verifier l'identite de l'employe dans la base*/
    private static int verifID() {
	try{
	    EmployeeDB.checkID(_query, id.getText(), password.getText());
	    _query.rst.next();
	    String err = _query.rst.getString(1);
	    return 1;
	}
	catch(Exception e){
	    return 0;
	}
    }


    /* ==================================================================== */
    /* ======================= CLASSES ECOUTEURS ========================== */
    /* ==================================================================== */
    
    /* Ecouteur pour la boite combo "status" */
    static class ListenerStatusBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    hideAll();
	    int r = status.getSelectedIndex();
	    switch (r)
		{
		case 1:   // Statut d'emballeur
		   actionsPacker.setVisible(true);
		    break;
		case 2:   // Statut de transporteur
		    actionsTransporter.setVisible(true);
		    break;
		}
	}
    }

/* Ecouteur pour la boite combo "actionsPacker" */
    static class ListenerActionPackerBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    hideAll();
	    int r = actionsPacker.getSelectedIndex();
	    switch (r)
		{
		case 1:   // Lister la commande d'un client
		    actionsPacker.setVisible(true);
		    listCommand.setVisible(true);
		    buttonListCommand.setVisible(true);
		    break;
		case 2:   // Entrer un colis dans le systeme
		    actionsPacker.setVisible(true);
		    packingDate.setVisible(true);
		    buttonEnterPackage.setVisible(true);
		    break;
		case 3:  // Entrer une palette dans le systeme
		    actionsPacker.setVisible(true);
		    paletteNumber.setVisible(true);
		    packingDate2.setVisible(true);
		    buttonEnterPalette.setVisible(true);
		    break;
		}
	}
    }

/* Ecouteur pour la boite combo "actionsTransporter" */
    static class ListenerActionTransporterBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    hideAll();
	    int r = actionsTransporter.getSelectedIndex();
	    switch (r)
		{
		case 1:   // Consulter le type d'un colis
		    actionsTransporter.setVisible(true);
		    typePackage.setVisible(true);
		    buttonTypePackage.setVisible(true);
		    break;
		case 2:   // Consulter la date de livraison limite
		    actionsTransporter.setVisible(true);
		    deliveryDate.setVisible(true);
		    buttonDeliveryDate.setVisible(true);
		    break;
		}
	}
    }


    /* Ecouteur pour le bouton "buttonTypePackage" */
    static class ListenerButtonTypePackage implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idE   = id.getText();
	    String mdp   = password.getText();
	    String idP   = typePackage.getText();
	    String[] col = {"TYPE DU COLIS"};
	    
	    if(verifID()==0) return;
	    PackageDB.typePackage(_query, idP);// On lance la requete
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Type du Colis",
						 col);
	    typePackage.setText("Numero du colis ?");
	}
    }

    /* Ecouteur pour le bouton "buttonDeliveryDate" */
    static class ListenerButtonDeliveryDate implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idC   = deliveryDate.getText();
	    String[] col = {"DATE DE LIVRAISON"};
	    
	    if(verifID() == 0) return;
	    CommandDB.deliveryDate(_query, idC);// On lance la requete
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Date de Livraison d'un Colis",
						 col);
	    deliveryDate.setText("Numero du colis ?");
	}
    }
    
    /* Ecouteur pour le bouton "buttonListCommand" */
    static class ListenerButtonListCommand implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idE   = id.getText();
	    String mdp   = password.getText();
	    String idC   = listCommand.getText();
	    String[] col = {"REFERENCE", "N° PRODUIT", "N° CLIENT", "PRIX",
			    "DATE LIVRAISON", "N° EMBALLEUR", "STATUT", "COLIS"};
	    
	    if (verifID() == 0) return;
	    CommandDB.listCommand(_query, idC);// On lance la requete
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Liste des Commandes d'un "+
						 "Client",
						 col);
	    listCommand.setText("Identifiant du Client ?");
	}
    }

    /* Ecouteur pour le bouton "buttonEnterPackage" */
    static class ListenerButtonEnterPackage implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idE = id.getText();
	    String mdp= password.getText();
	    String date=packingDate.getText();
	    
	    if(verifID() ==0) return;
	    PackageDB.addPackage(_query, date);// On lance la requete
	    packingDate.setText("Date d'emballage  ? (format : AAAAMMJJ)");
	}
    }

    /* Ecouteur pour le bouton "buttonEnterPalette" */
    static class ListenerButtonEnterPalette implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idE  = id.getText();
	    String mdp  = password.getText();
	    String idP  = paletteNumber.getText();
	    String date = packingDate2.getText();
	    
	    if(verifID() == 0) return; // Si les identifiants sont verifies
	    PaletteDB.addPalette(_query, idP);// On lance la requete
	    paletteNumber.setText("Numero de Palette ?");
	    packingDate2.setText("Date d'emballage ? (format AAAAMMJJ)");
	}
    }
}