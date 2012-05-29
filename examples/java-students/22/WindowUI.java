import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class WindowUI extends JFrame implements ActionListener
{
    
    private JPanel top;  // panel en haut permet naviguation dans panel main
    private JPanel main; // panel principal sert à interaction utilisateur

    private CardLayout cl;    // boîte permettant navigation 

    private JPanel _welcome;  // panneau affichage accueil
    private JPanel _owner;    // UI gérant
    private JPanel _customer; // UI client
    private JPanel _employee; // UI employé
    private JPanel _customs;  // UI douane

    private JButton bOwner;    // bouton gérant
    private JButton bCustomer; // bouton client
    private JButton bEmployee; // bouton employé
    private JButton bCustoms;  // bouton douane
    
    public WindowUI()
    {
	super();
	/* Définition des paramètres globaux de la fenêtre  */
	setTitle("Base De Données - Projet");
	_setSizeToScreen(); // taille de fenêtre adaptée à celle de l'écran
	setLocationRelativeTo(null); // on centre la fenêtre
	setResizable(false); // on interdit la redimension
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	
	Container _window = getContentPane(); // on récupère le conteneur
	buildMainPanel(_window); // création panel "main"
	buildTopPanel(_window); // on créé le panel "top"

	_window.validate(); // actualise affichage
    }


    
    /* On fait en sorte que la fenêtre prenne la moitié de la taille 
       de l'écran  */
    private void _setSizeToScreen()
    {
	Toolkit tk            = Toolkit.getDefaultToolkit();
	Dimension dimScreen   = tk.getScreenSize();
	int w = dimScreen.width / 2;
	int h = dimScreen.height;
	setSize (w, h);
    }


    /* Construction panel principal qui va permettre de faire les requêtes */
    private void buildMainPanel(Container _window)
    {
	main = new JPanel();
	_window.add(main);
	
	cl   = new CardLayout(20, 20);
	main.setLayout(cl);

	_welcome  = WelcomeUI.buildWelcomePanel();
	main.add(_welcome, ""); // panel affiché en premier
	_owner    = OwnerUI.buildOwnerPanel();
	main.add(_owner, "Gérant");
	_customer = CustomerUI.buildCustomerPanel();
	main.add(_customer, "Client");
	_employee = EmployeeUI.buildEmployeePanel();
	main.add(_employee, "Employé");
	_customs  = CustomsUI.buildCustomsPanel();
	main.add(_customs, "Douane");
    }
	

    /* On construit le panel supérieur qui va permettre la naviguation entre 
       les différents utilisateurs possibles */
    private void buildTopPanel(Container _window)
    {
	top = new JPanel();
	_window.add(top, "North"); // panel affiché en haut de la fenêtre
	top.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
	
	bCustomer = new JButton("Client"); // Création des boutons
	top.add(bCustomer); // on l'ajoute au panel
	bCustomer.addActionListener(this); // le bouton sera son "ecouteur"
	
	bOwner    = new JButton("Gérant");
	top.add(bOwner);
	bOwner.addActionListener(this);
	
	bEmployee = new JButton("Employé");
	top.add(bEmployee);
	bEmployee.addActionListener(this);
	
	bCustoms  = new JButton("Douane");
	top.add(bCustoms);
	bCustoms.addActionListener(this);
    }

    /* Action à effectuer en fonction du bouton cliqué */
    public void actionPerformed(ActionEvent e)
    {
	JButton src = (JButton)e.getSource();
	
	if (src == bOwner)
	    cl.show(main, "Gérant");
	if (src == bEmployee)
	    cl.show(main, "Employé");
	if (src == bCustomer)
	    cl.show(main, "Client");
	if (src == bCustoms)
	    cl.show(main, "Douane");
    }

    

}