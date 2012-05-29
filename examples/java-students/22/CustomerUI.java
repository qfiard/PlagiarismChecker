import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CustomerUI
{
    private static JPanel                         res;
    private static Box                            jp;
    private static QueryDB                        _query;


    private static JPanel                         dataConnection;
    private static JTextField                     login;
    private static JTextField                     password;

    private static JComboBox                      actions;
    
    private static JButton                        buttonAvailableProducts;
    
    private static Box                            boxCommand;
    private static JTextField                     deliveryDate;
    private static CommandTextFields[]            products;
    private static JPanel                         panelCommandBottom;
    private static JButton                        buttonAddProduct;
    private static int                            maxProducts;
    private static JButton                        buttonCommand;
    
    private static JButton                        buttonCommandStatus;
    
    private static JTextField                     newLogin;
    private static JTextField                     newPassword;
    private static JButton                        buttonChangeLoginPassword;

    /* Fonction qui créée le panneau Client */
    public static JPanel buildCustomerPanel()
    {
	// Création récepteur requêtes
	ConnectionDB _connect = new ConnectionDB();
	_query = new QueryDB(_connect);

	// Création du panneau
	res = new JPanel();

	// Panneau informations de connexion
	dataConnection = new JPanel();
	res.add(dataConnection);
	login    = new JTextField("Login ?", 20);
	password = new JTextField("Mot de passe ?", 20);
	dataConnection.add(login);
	dataConnection.add(password);

	// Box Verticale qui va permettre la mise en forme
	jp = Box.createVerticalBox();
	res.add(jp);
	
	
	/* ******************** ELEMENTS DU PANNEAU *********************** */
	
	/* ***        Menu déroulant des actions possibles Client       *** */
	String[] _actions = {" - ",                                   // 0
			     "Liste des Produits disponibles",        // 1
			     "Passer une Commande",                   // 2
			     "Consulter mes Commande",                // 3
			     "Changer Login et/ou Mot de Passe"};     // 4
	actions = new JComboBox<String>(_actions);
	ListenerActionsBox lActionsBox = new ListenerActionsBox();
	actions.addActionListener(lActionsBox);
	jp.add(jp.createVerticalStrut(50));
	jp.add(actions);
	jp.add(jp.createVerticalStrut(50));
	
	/* *** Bouton pour valider demande listing produits disponibles *** */
	buttonAvailableProducts = new JButton("Valider");
	ListenerAvailableProducts lAvailableProducts = 
	    new ListenerAvailableProducts();
	buttonAvailableProducts.addActionListener(lAvailableProducts);
	buttonAvailableProducts.setVisible(false);
	jp.add(buttonAvailableProducts);
	
	
	/* ***               UI pour passer une commande                *** */
	boxCommand = Box.createVerticalBox();
	jp.add(boxCommand);
	boxCommand.setVisible(false);
	// <products> va permettre d'afficher autant de champs à remplir que
	// nécessaire
	deliveryDate = new JTextField("Livraison ?", 10);
	maxProducts = 1;
	products = new CommandTextFields[1];
	products[0] = new CommandTextFields();
	products[0].addToBoxCommand();
	// panel contenant les boutons situés en bas de la fenêtre
	panelCommandBottom = new JPanel();
	jp.add(panelCommandBottom);
	panelCommandBottom.setVisible(false);
	panelCommandBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
	// Bouton qui, une fois cliqué, va ajouter 2 champs pour commander un
	// nouveau produit
	buttonAddProduct = new JButton("Ajouter un Produit");
	ListenerButtonAddProduct lButtonAddProduct =
	    new ListenerButtonAddProduct();
	buttonAddProduct.addActionListener(lButtonAddProduct);
	panelCommandBottom.add(buttonAddProduct);
	// Bouton qui permettra de valider la commande
	buttonCommand = new JButton("Valider");
	ListenerButtonCommand lButtonCommand = 
	    new ListenerButtonCommand();
	buttonCommand.addActionListener(lButtonCommand);
	panelCommandBottom.add(buttonCommand);
	
	
	/* ***          UI pour consulter état d'une commande           *** */
	buttonCommandStatus = new JButton("Valider");
	buttonCommandStatus.setVisible(false);
	ListenerButtonCommandStatus lButtonCommandStatus 
	    = new ListenerButtonCommandStatus();
	buttonCommandStatus.addActionListener(lButtonCommandStatus);
	jp.add(buttonCommandStatus);


	/* ***             UI pour changer login/mdp                    *** */
	newLogin         = new JTextField("Nouveau Login ?", 20);
	newLogin.setVisible(false);
	jp.add(newLogin);
	newPassword      = new JTextField("Nouveau Mot de Passe ?", 20);
	newPassword.setVisible(false);
	jp.add(newPassword);
	buttonChangeLoginPassword = new JButton("Valider");
	ListenerButtonChange lButtonChange = new ListenerButtonChange();
	buttonChangeLoginPassword.addActionListener(lButtonChange);
	buttonChangeLoginPassword.setVisible(false);
	jp.add(buttonChangeLoginPassword);


	return res;
    }


    /* Tout cacher pour mieux afficher l'intéressant... ! */
    private static void hideAll()
    {
	buttonAvailableProducts.setVisible(false);
	boxCommand.setVisible(false);
	panelCommandBottom.setVisible(false);
	buttonCommandStatus.setVisible(false);
	newLogin.setVisible(false);
	newPassword.setVisible(false);
	buttonChangeLoginPassword.setVisible(false);
    }

    /* Fonction qui reset la liste chaînée products */
    private static void resetProducts()
    {
	maxProducts = 1;
	buttonAddProduct.setVisible(true);
	boxCommand.removeAll();
	deliveryDate = new JTextField("Livraison ?", 10);
	boxCommand.add(deliveryDate);
	products = new CommandTextFields[1];
	products[0] = new CommandTextFields();
	products[0].addToBoxCommand();
    }

    /* Fonction qui teste si le client est bien dans la base */
    private static int checkId()
    {
	try
	    {
		CustomerDB.checkIdCustomer(_query, 
					   login.getText(), password.getText());
		_query.rst.next();
		String err = _query.rst.getString(1);
		return 1;
	    }
	catch (Exception ae)
	    {
		return 0;
	    }
    }



    
    /* ==================================================================== */
    /* ============= DEFINITION CLASSE POUR CHAMP COMMANDE ================ */
    /* ==================================================================== */
    static class CommandTextFields
    {
	private JTextField idProduct;
	private JTextField quantity;
	
	public CommandTextFields()
	{
	    idProduct = new JTextField("Référence du Produit", 20);
	    quantity  = new JTextField("Quantité", 10);
	}

	// Fonction qui ajoute les deux champs au panneau "panelCommand"
	public void addToBoxCommand()
	{
	    JPanel p = new JPanel();
	    p.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    p.add(idProduct);
	    p.add(quantity);
	    boxCommand.add(p);
	    boxCommand.validate();
	}
    }
    





    /* ==================================================================== */
    /* ======================= CLASSES ECOUTEURS ========================== */
    /* ==================================================================== */
    
    /* Ecouteur pour la boîte combo "actions" */
    static class ListenerActionsBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    hideAll();
	    resetProducts();
	    int r = actions.getSelectedIndex();
	    switch (r)
		{
		case 1:   // Listing des produits disponibles
		    buttonAvailableProducts.setVisible(true);
		    break;
		case 2:   // Passer une commande
		    boxCommand.setVisible(true);
		    panelCommandBottom.setVisible(true);
		    break;
		case 3 :  // Consulter une commande
		    buttonCommandStatus.setVisible(true);
		    break;
		case 4 :  // Changer login/mdp
		    newLogin.setVisible(true);
		    newPassword.setVisible(true);
		    buttonChangeLoginPassword.setVisible(true);
		    break; 
		}
	}
    }
    
    /* Ecouteur pour le bouton "buttonAvailableProducts" */
    static class ListenerAvailableProducts implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    ProductDB.listAllAvailableProduct(_query);
	    String[] col = {"REFERENCE", "NOM", "TYPE", "STOCK", "NOMBRE VENTE",
			    "PRIX"};
	    DisplayRqstUI dr = new DisplayRqstUI(_query,
						 "Liste Produits Disponibles",
						 col);
	}
    }

    /* Ecouteur pour le bouton "buttonAddProduct" */
    static class ListenerButtonAddProduct implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (maxProducts < 9)
		maxProducts++;
	    else
		buttonAddProduct.setVisible(false);
	    
	    CommandTextFields[] ctf = new CommandTextFields[products.length+1];
	    for (int i=0; i<products.length; i++)
		ctf[i] = products[i];
	    ctf[products.length] = new CommandTextFields();
	    ctf[products.length].addToBoxCommand();
	    products = ctf;
	    boxCommand.revalidate(); // recalcule affichage panneau
	}
    }
    
    /* Ecouteur pour le bouton "buttonCommand" */
    static class ListenerButtonCommand implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    try
		{
		    // on créé un nouveau numéro de commande
		    CommandDB.getMaxIdCommand(_query);
		    _query.rst.next();
		    int n      = Integer.parseInt(_query.rst.getString(1));
		    int id_cmd = n+1;
		    // on récupère l'identifiant du client
		    CustomerDB.getIdCustomer(_query, login.getText());
		    _query.rst.next();
		    int id_customer = Integer.parseInt(_query.rst.getString(1));
		    // on augmente son nombre de commande
		    CustomerDB.updateNbCmd(_query, ""+id_customer);
		    // on récupère l'emballeur avec le moins de prod_traite
		    EmployeeDB.getIdPacker(_query);
		    _query.rst.next();
		    String idPacker = _query.rst.getString(1);
		    // on augmente donc son nombre de prod_traite
		    EmployeeDB.updateNbProd(_query, idPacker);
		    // Avec le tableau <d> on pourra lancer la requête qui 
		    // créera la commande dans la table
		    String[] d = new String[8];
		    d[0] = ""+id_cmd;               // numéro commande
		    d[2] = ""+id_customer;          // numéro client
		    d[4] = deliveryDate.getText();  // date livraison max
		    d[5] = idPacker;                // numéro employé embal.
		    d[6] = "non expédié";           // statut commande
		    // on créé le colis associé à la commande
		    PackageDB.createPackage(_query, d[0], d[4], d[6]);
		    // on récupère donc l'id_colis
		    PackageDB.getIdPackage(_query, d[0]);
		    _query.rst.next();
		    d[7] = _query.rst.getString(1);
		    for (int i=0; i<products.length; i++)
			{
			    CommandTextFields _cmd = products[i];
			    d[1]    = _cmd.idProduct.getText();
			    int qty = Integer.parseInt(_cmd.quantity.getText());
			    // màj stock du produit
			    ProductDB.updateProductQuantity(_query, d[1], qty);
			    // màj nb_vente du produit
			    ProductDB.updateProductNbSell(_query, d[1], qty);
			    // on récupère prix du produit
			    ProductDB.getProductPrice(_query, 
						      _cmd.idProduct.getText());
			    _query.rst.next();
			    // on déduit prix total produit pour commande
			    double price = qty * 
				Double.parseDouble(_query.rst.getString(1));
			    d[3]    = ""+price;
			    CommandDB.addCommand(_query, d);
			}
		    PackageDB.updateType(_query, d[0]);
		    resetProducts();
		    boxCommand.revalidate();
		}
	    catch (Exception exc)
		{
		    System.out.println("Problème lors de l'ajout d'une" + 
				       " commande :\n\t" + exc);
		}
	}
    }
	
    /* Ecouteur pour le bouton "buttonCommandStatus" */
    static class ListenerButtonCommandStatus implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    try
		{
		    if (checkId() == 0) return;
		    CustomerDB.getIdCustomer(_query, login.getText());
		    _query.rst.next();
		    String id_client = _query.rst.getString(1);
		    CommandDB.getCommandStatus(_query, id_client);
		    String[] col = {"REFERENCE", "N° PRODUIT", "N° CLIENT",
				     "PRIX", "DATE LIVRAISON", "N° EMBALLEUR",
				    "STATUT", "REF COLIS"};
		    DisplayRqstUI dr = new DisplayRqstUI(_query,
							 "Etat de la Commande",
							 col);
		}
	    catch (Exception ea)
		{
		    System.out.println("An error ocurred in CommandStatus");
		    System.out.println("\t"+ea);
		}
	}
    }

    /* Ecouteur pour le bouton "buttonChange" */
    static class ListenerButtonChange implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    CustomerDB.setLoginPswd(_query, login.getText(), newLogin.getText(),
				    newPassword.getText());
	    newLogin.setText("Nouveau Login ?");
	    newPassword.setText("Nouveau Mot de Passe ?");
	    
	}
    }
}