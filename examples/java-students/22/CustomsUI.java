import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CustomsUI
{
    private static JPanel                         res;
    private static Box                            jp;
    private static QueryDB                        _query;
    private static JComboBox                      actions;

    private static JPanel                         dataConnection;
    private static JTextField                     password;
    private static JTextField                     id;

    private static JTextField                     returnReason;
    private static JTextField                     idPackage; 
    private static JButton                        buttonReturnReason;

    private static JTextField                     listPalette;
    private static JButton                        buttonListPalette;

    private static JTextField                     listPackage;
    private static JButton                        buttonListPackage;

    private static JTextField                     listProduct;
    private static JButton                        buttonListProduct;

    private static JTextField                     productPrice;
    private static JButton                        buttonProductPrice;

    /* Fonction qui creee le panneau Douane */
    public static JPanel buildCustomsPanel()
    {

	// Creation recepteur requetes
	ConnectionDB _connect = new ConnectionDB();
	_query = new QueryDB(_connect);

	// Creation du panneau
	res = new JPanel();
	

	// Les champs qui vont permettre l'authentification du douanier
	dataConnection = new JPanel();
	res.add(dataConnection);
	id = new JTextField("Numero de douanier ?", 20);
	password = new JTextField("Mot de passe ?", 20);
	dataConnection.add(id);
	dataConnection.add(password);
	
	// La boite verticale qui va permettre la mise en forme
	jp = Box.createVerticalBox();
	res.add(jp);
	
	/* ******************** ELEMENTS DU PANNEAU *********************** */
	
	/* ***        Menu deroulant des actions possibles Douane       *** */
	String[] _actions = {" - ",                                   // 0
			     "Renvoyer un colis",        // 1
			     "Lister les palettes d'un conteneur",                   // 2
			     "Lister les colis d'une palette",              //3
			     "Lister les produit d'un colis",               // 4
			     "Lister les prix des produits transportes"}; // 5
	actions = new JComboBox<String>(_actions);
	ListenerActionsBox lActionsBox = new ListenerActionsBox();
	actions.addActionListener(lActionsBox);
	res.add(actions);

	/* ***          UI pour renvoyer un colis avec un motif         *** */
	idPackage= new JTextField("Numero du colis",20);
	idPackage.setVisible(false);
	res.add(idPackage);
	returnReason = new JTextField("Motif du retour", 30);
	returnReason.setVisible(false);
	res.add(returnReason);
	buttonReturnReason = new JButton("Valider");
	buttonReturnReason.setVisible(false);
	ListenerButtonReturnReason lButtonReturnReason 
	    = new ListenerButtonReturnReason();
	buttonReturnReason.addActionListener(lButtonReturnReason);
	res.add(buttonReturnReason);


	/* ***          UI pour lister les palettes d'un conteneur        *** */
	listPalette = new JTextField("Numero du conteneur ?", 25);
	listPalette.setVisible(false);
	res.add(listPalette);
	buttonListPalette = new JButton("Valider");
	buttonListPalette.setVisible(false);
	ListenerButtonListPalette lButtonListPalette
	    = new ListenerButtonListPalette();
	buttonListPalette.addActionListener(lButtonListPalette);
	res.add(buttonListPalette);


	/* ***          UI pour lister les colis d'une palette        *** */
	listPackage = new JTextField("Numero de la palette ?", 25);
	listPackage.setVisible(false);
	res.add(listPackage);
	buttonListPackage = new JButton("Valider");
	buttonListPackage.setVisible(false);
	ListenerButtonListPackage lButtonListPackage
	    = new ListenerButtonListPackage();
	buttonListPackage.addActionListener(lButtonListPackage);
	res.add(buttonListPackage);


	/* ***          UI pour lister les produits d'un colis        *** */
	listProduct = new JTextField("Numero du colis ?", 25);
	listProduct.setVisible(false);
	res.add(listProduct);
	buttonListProduct = new JButton("Valider");
	buttonListProduct.setVisible(false);
	ListenerButtonListProduct lButtonListProduct
	    = new ListenerButtonListProduct();
	buttonListProduct.addActionListener(lButtonListProduct);
	res.add(buttonListProduct);


	/* ***          UI pour lister les prix des produits transportes        *** */
	productPrice = new JTextField("Numero du conteneur ?", 25);
	productPrice.setVisible(false);
	res.add(productPrice);
	buttonProductPrice = new JButton("Valider");
	buttonProductPrice.setVisible(false);
	ListenerButtonProductPrice lButtonProductPrice
	    = new ListenerButtonProductPrice();
	buttonProductPrice.addActionListener(lButtonProductPrice);
	res.add(buttonProductPrice);

	res.validate();
	return res;
    }

    /* On cache tout pour mieux afficher l'element qu'on veut */
    private static void hideAll()
    {
	idPackage.setVisible(false);
	returnReason.setVisible(false);
	buttonReturnReason.setVisible(false);
	listPalette.setVisible(false);
	buttonListPalette.setVisible(false);
	listPackage.setVisible(false);
	buttonListPackage.setVisible(false);
	listProduct.setVisible(false);
	buttonListProduct.setVisible(false);
	productPrice.setVisible(false);
	buttonProductPrice.setVisible(false);

    }

    /* Fonction qui va verifier l'identite du douanier dans la base*/
    private static int verifID(){
	try{
	    CustomsDB.checkID(_query,id.getText(),password.getText());
	    _query.rst.next();
	    String err=_query.rst.getString(1);
	    return 1;
	}
	catch(Exception e){
	    return 0;
	}
    }


   /* ==================================================================== */
    /* ======================= CLASSES ECOUTEURS ========================== */
    /* ==================================================================== */
    
    /* Ecouteur pour la boite combo "actions" */
    static class ListenerActionsBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    hideAll();
	    int r = actions.getSelectedIndex();
	    switch (r)
		{
		case 1:   // Retour d'un colis
		    idPackage.setVisible(true);
		    returnReason.setVisible(true);
		    buttonReturnReason.setVisible(true);
		    break;
		case 2:   // Lister palettes d'un conteneur
		    listPalette.setVisible(true);
		    buttonListPalette.setVisible(true);
		    break;
		case 3 :  // Lister colis d'une palette
		    listPackage.setVisible(true);
		    buttonListPackage.setVisible(true);
		    break;
		case 4 :  // Lister produits d'un colis
		    listProduct.setVisible(true);
		    buttonListProduct.setVisible(true);
		    break;
		case 5: // Lister prix des produits transportes
		    productPrice.setVisible(true);
		    buttonProductPrice.setVisible(true);
		    break;
		}
	}
    }


    /* Ecouteur pour le bouton "buttonReturnReason" */
    static class ListenerButtonReturnReason implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idC = id.getText();
	    String mdpC= password.getText();
	    String idP = idPackage.getText();
	    String reason= returnReason.getText();
	    
	    if(verifID()==0) return;
	    PackageDB.returnPackage(_query, idP, reason);
	    EmployeeDB.addError(_query, idP);
		
	}
    }

    /* Ecouteur pour le bouton "buttonListPalette" */
    static class ListenerButtonListPalette implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idContainer= listPalette.getText();
	    String idC        = id.getText();
	    String mdpC       = password.getText();
	    String col[]      = {"NUMERO(S) DE(S) PALETTE(S)"};

	    if(verifID()==0) return;
		PaletteDB.listPalette(_query, idContainer);
		DisplayRqstUI dr = new DisplayRqstUI(_query,
						     "Numeros des palettes",
						     col);
   
	}
    }
    
    /* Ecouteur pour le bouton "buttonListPackage" */
    static class ListenerButtonListPackage implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idPalette = listPackage.getText();
	    String idC       = id.getText();
	    String mdpC      = password.getText();
	    String[] col     = {"NUMERO(S) DE(S) COLIS"};

	    if(verifID()==0) return;
	    PackageDB.listPackage(_query, idPalette);
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Numeros des colis",
						 col);
	}
    }

    /* Ecouteur pour le bouton "buttonListProduct" */
    static class ListenerButtonListProduct implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idPackage = listProduct.getText();
	    String idC       = id.getText();
	    String mdpC      = password.getText();
	    String[] col     = {"NOM",
				"NUMERO(S) DE(S) PRODUIT(S)"};

	    if(verifID()==0) return;
	    CommandDB.listProduct(_query, idPackage);
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Numero des produits",
						 col);
	}
    }

    /* Ecouteur pour le bouton "buttonProductPrice" */
    static class ListenerButtonProductPrice implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    String idContainer = productPrice.getText();
	    String idC       = id.getText();
	    String mdpC      = password.getText();
	    String[] col     = {"NOM DU PRODUIT", "PRIX"};

	    if(verifID()==0) return;
	    ContainerDB.listPrice(_query, idContainer);
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 ""+
						 "Liste des prix des produits",
						 col);
	}
    }
}