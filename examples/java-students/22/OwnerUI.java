import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class OwnerUI
{
    private static QueryDB      _query;
    private static JPanel       res;
    private static Box          jp;
    private static JPanel       dataConnection;
    private static JTextField   password;
    private static JTextField   lastName;
    private static JComboBox    actions;
    private static JComboBox    listEmployee;
    private static JButton      buttonListEmployee;
    private static JTextField   fieldFireEmployee;
    private static JButton      buttonFireEmployee;
    private static JComboBox    listCustomer;
    private static JButton      buttonListCustomer;
    private static JComboBox    listProduct;
    private static JButton      buttonListProduct;
    private static JTextField   productToModify;
    private static JTextField   newProductPrice;
    private static JButton      buttonProductPrice;

    /* Fonction qui va créer le panneau du gérant */
    public static JPanel buildOwnerPanel()
    {
	// on créé le "récepteur de requête"
	ConnectionDB _connect = new ConnectionDB();
	_query = new QueryDB(_connect);

	// on créé le panneau pour le Gérant
	res = new JPanel();

	// Les champs qui vont permettre l'authentification du Gérant
	dataConnection = new JPanel();
	res.add(dataConnection);
	lastName = new JTextField("Nom ?", 20);
	password = new JTextField("Mot de passe ?", 20);
	dataConnection.add(lastName);
	dataConnection.add(password);
	
	// La boîte Verticale qui va permettre la mise en forme
	jp = Box.createVerticalBox();
	res.add(jp);
	

	/* ******************** ELEMENTS DU PANNEAU ********************* */

	// Menu déroulant des actions possibles Gérant
	String[] _actions = {"     -        ",                        // 0
			     "Liste des Employés",                    // 1
			     "Licencier un employé",                  // 2
			     "Liste des Clients",                     // 3
			     "Liste des Produits",                    // 4
			     "Changer le prix d'un produit"};         // 5
	actions = new JComboBox<String>(_actions);
	ListenerActionsBox lActionsBox = new ListenerActionsBox();
	actions.addActionListener(lActionsBox);
	jp.add(jp.createVerticalStrut(50));
	jp.add(actions);
	jp.add(jp.createVerticalStrut(50));
	
	// Menu déroulant listing des Employés
	String[] _listEmployee = {"Tous",           // 0
				  "Emballeurs",     // 1
				  "Transporteurs"}; // 2
	listEmployee = new JComboBox<String>(_listEmployee);
	listEmployee.setVisible(false);
	jp.add(listEmployee);
	buttonListEmployee = new JButton("Valider");
	buttonListEmployee.setVisible(false);
	ListenerListEmployee lListEmployee = new ListenerListEmployee();
	buttonListEmployee.addActionListener(lListEmployee);
	jp.add(buttonListEmployee);
	
	
	// Champ de texte pour licencier un employé
	fieldFireEmployee = new JTextField("Identifiant de l'employé ?", 25);
	fieldFireEmployee.setVisible(false);
	jp.add(fieldFireEmployee);
	buttonFireEmployee = new JButton("Valider");
	buttonFireEmployee.setVisible(false);
	ListenerFireEmployee lFireEmployee = new ListenerFireEmployee();
	buttonFireEmployee.addActionListener(lFireEmployee);
	jp.add(buttonFireEmployee);


	// Menu déroulant pour le listing des clients
	String[] _listCustomer = {"Tous",                 // 0 
				  "Les plus dépensiers"}; // 1
	listCustomer = new JComboBox<String>(_listCustomer);
	listCustomer.setVisible(false);
	jp.add(listCustomer);
	buttonListCustomer = new JButton("Valider");
	buttonListCustomer.setVisible(false);
	ListenerListCustomer lListCustomer = new ListenerListCustomer();
	buttonListCustomer.addActionListener(lListCustomer);
	jp.add(buttonListCustomer);


	// Menu déroulant pour le listing des produits
	String[] _listProduct = {"Tous",                       // 0
				 "Triés par Nombre de Vente"}; // 1
	listProduct = new JComboBox<String>(_listProduct);
	listProduct.setVisible(false);
	jp.add(listProduct);
	buttonListProduct = new JButton("Valider");
	buttonListProduct.setVisible(false);
	ListenerListProduct lListProduct = new ListenerListProduct();
	buttonListProduct.addActionListener(lListProduct);
	jp.add(buttonListProduct);


	// Champs de texte pour modifier le prix d'un objet
	productToModify = new JTextField("Référence du Produit ?", 25);
	productToModify.setVisible(false);
	jp.add(productToModify);
	newProductPrice = new JTextField("Nouveau Prix ?", 25);
	newProductPrice.setVisible(false);
	jp.add(newProductPrice);
	buttonProductPrice = new JButton("Valider");
	buttonProductPrice.setVisible(false);
	ListenerProductPrice lProductPrice = new ListenerProductPrice();
	buttonProductPrice.addActionListener(lProductPrice);
	jp.add(buttonProductPrice);

	return res;
    }

    
    /* On cache tout pour mieux afficher l'élément qu'on veut */
    private static void hideAll()
    {
	listEmployee.setVisible(false);
	buttonListEmployee.setVisible(false);
	fieldFireEmployee.setVisible(false);
	buttonFireEmployee.setVisible(false);
	listCustomer.setVisible(false);
	buttonListCustomer.setVisible(false);
	listProduct.setVisible(false);
	buttonListProduct.setVisible(false);
	productToModify.setVisible(false);
	newProductPrice.setVisible(false);
	buttonProductPrice.setVisible(false);
    }

    /* Vérifie les identifiants du Gérant */
    private static int checkId()
    {
	try
	    {
		OwnerDB.checkIdOwner(_query, lastName.getText(), 
				     password.getText());
		_query.rst.next();
		String err = _query.rst.getString(1);
		return 1;
	    }
	catch (Exception ae)
	    {
		return 0;
	    }
    }


    /* ====================================================================== */
    /* ============= CLASSES ECOUTEURS POUR LES ELEMENTS ==================== */
    /* ====================================================================== */

    /* Définition de l'écouteur pour la ComboBox "Actions" */
    static class ListenerActionsBox implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    int r = actions.getSelectedIndex();
	    hideAll();
	    switch (r)
		{
		case 1 : // on affiche la boîte combo pour les employés
		    listEmployee.setVisible(true);
		    buttonListEmployee.setVisible(true);
		    break;
		case 2 : // champ de texte pour licencier un employé
		    fieldFireEmployee.setVisible(true);
		    buttonFireEmployee.setVisible(true);
		    break;
		case 3 : // liste des clients
		    listCustomer.setVisible(true);
		    buttonListCustomer.setVisible(true);
		    break;
		case 4 : // liste des Produits
		    listProduct.setVisible(true);
		    buttonListProduct.setVisible(true);
		    break;
		case 5 : // champs de textes pour changer prix produit
		    productToModify.setVisible(true);
		    newProductPrice.setVisible(true);
		    buttonProductPrice.setVisible(true);
		default : // quand on ne sélectionne rien...on ne fait rien !
		    break;
		}
	}
    }
    
    /* Définition de l'écouteur pour la ComboBox "listEmployee" */
    static class ListenerListEmployee implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    int r = listEmployee.getSelectedIndex();
	    DisplayRqstUI dr;
	    String[] col = {"IDENTIFIANT", "NOM", "PRENOM","SPECIALITE",
			   "COMMANDES TRAITEES"};
	    switch (r)
		{
		case 0 : 
		    EmployeeDB.listAllEmployees(_query);
		    dr = new DisplayRqstUI(_query, 
					   "Liste de tous les Employés",
					   col);
		    break;
		case 1 : 
		    EmployeeDB.listSpecificEmployees(_query, "emballeur");
		    
		    dr = new DisplayRqstUI(_query,
					   "Liste des Emballeurs",
					   col);
		    break;
		case 2 : 
		    EmployeeDB.listSpecificEmployees(_query, "transporteur");
		    dr = new DisplayRqstUI(_query,
					   "Liste des Transporteurs",
					   col);
		    break;
		}
	}
    }

    /* écouteur pour le champ "fireEmployee" */
    static class ListenerFireEmployee implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    EmployeeDB.fireEmployee(_query, fieldFireEmployee.getText());
	    fieldFireEmployee.setText("Identifiant de l'employé ?");
	}
    }

    /* écouteur pour le listing des Clients "listCustomer" */
    static class ListenerListCustomer implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    int r = listCustomer.getSelectedIndex();
	    DisplayRqstUI dr;
	    String[] col = {"NOM", "PRENOM", "MDP", "NOMBRE COMMANDES", "LOGIN",
			    "IDENTIFIANT"};
	    switch (r)
		{
		case 0:
		    CustomerDB.listAllCustomer(_query);
		    dr = new DisplayRqstUI(_query,
					   "Liste de tous les clients",
					   col);
		    break;
		case 1:
		    CustomerDB.listCustomerBySold(_query);
		    dr = new DisplayRqstUI(_query,
					   "Liste des Clients par nombre "
					   + "d'achats",
					   col);
		    break;
		}
	}
    }
    
    /* écouteur pour le listing des Produits "listProduct" */
    static class ListenerListProduct implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    int r = listProduct.getSelectedIndex();
	    DisplayRqstUI dr;
	    String[] col = {"REFERENCE", "NOM", "TYPE", "STOCK", "NOMBRE VENTE",
			    "PRIX"};
	    switch (r)
		{
		case 0: 
		    ProductDB.listAllProduct(_query);
		    dr = new DisplayRqstUI(_query,
					   "Liste des Produits disponibles",
					   col);
		    break;
		case 1:
		    ProductDB.mostSold(_query);
		    dr = new DisplayRqstUI(_query,
					   "Liste des Produits triée par nombre"+ 
					   " de vente",
					   col);
		    break;
		}
	}
    }
    
    /* écouteur pour la modification du prix d'un produit "ProductPrice" */
    static class ListenerProductPrice implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if (checkId() == 0) return;
	    ProductDB.alterPrice(_query, productToModify.getText(),
				 newProductPrice.getText());
	    productToModify.setText("Référence du Produit ?");
	    newProductPrice.setText("Nouveau Prix ?");
	}
    }
}