import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class interface_gerant extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu m_fichier = new JMenu("Fichier");
	private JMenu m_fenetre = new JMenu("Fenetre");
	private JMenu m_help = new JMenu("?");
	private JMenu m_compte = new JMenu("Mon Compte");
	
	private JMenuItem sm_update = new JMenuItem("Mise ˆ jour");
	private JTextField jtf_costumer = new JTextField("TEST Vos 5 Meilleurs Clients");
	private JTextField jtf_products = new JTextField("Vos 5 Produits les plus vendus");
	
	private JMenuItem sm_login = new JMenuItem("Modifier mon login");
	private JMenuItem sm_passwd = new JMenuItem("Modifier mon mot de passe");
	private JMenuItem sm_produits = new JMenuItem("Mes Produits");
	private JMenuItem sm_employes = new JMenuItem("Mes Employes");
	private JMenuItem sm_clients = new JMenuItem("Mes Clients");
	
	private JMenuItem sm_quitter = new JMenuItem("Quitter");
	private JMenuItem sm_apropos = new JMenuItem("A Propos");
	
	
	//variables de requete SQL
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private Statement       sql2;
	

    private JPanel pan = new JPanel();
	
	public interface_gerant(String name) throws ClassNotFoundException, SQLException{
        format_fenetre(name);
		toolbar(name);
		

		database_connection con_2 = new database_connection();
		sql = con_2.getConn().createStatement();
		ResultSet rs = sql.executeQuery("select id_client,SUM(prix) as total FROM commande GROUP BY id_client ORDER BY total DESC LIMIT 25;");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_client")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab.add(rs.getString("total"));
		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_client"); 
		names.addElement("total");
		DefaultTableModel model = new DefaultTableModel(values, names); 
		
		
		
		database_connection con_4 = new database_connection();
		sql2 = con_4.getConn().createStatement();
		ResultSet rs2 = sql2.executeQuery("select id_produit, count(*) as occurence from produit_commande GROUP BY id_produit ORDER BY occurence DESC LIMIT 25;");
		
		
		Vector values2 = new Vector(); 
		while(rs2.next()) 
		{ 
		Vector tab1 = new Vector(); 
		tab1.add(rs2.getString("id_produit")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab1.add(rs2.getString("occurence"));
		values2.add(tab1); 
		} 
		Vector names2= new Vector(); 


		names2.addElement("id_produit"); 
		names2.addElement("occurence");
		DefaultTableModel model2 = new DefaultTableModel(values2, names2); 
		
				
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        JTable tableau = new JTable(model);
        JTable tableau2 = new JTable(model2);
        getContentPane().add(new JScrollPane(tableau), BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tableau2), BorderLayout.CENTER);
        this.setVisible(true);
    	}
	
	
	
	
	
	
	
	
	
	
	
	
    private void format_fenetre(String name) {
    	this.setTitle("Bienvenue Gerant "+name);
        this.setSize(500, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

    }

    private void toolbar(String name) {
    	 this.m_fichier.add(sm_update);
         this.m_fichier.addSeparator();
         this.m_fichier.add(sm_quitter);
 		sm_quitter.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {
 				System.exit(0);
 			}				
 		});


         this.m_compte.add(sm_passwd);
         sm_passwd.addActionListener(new change_passwd(name));
         this.m_compte.add(sm_login);
         sm_login.addActionListener(new change_login(name));
         
         this.m_fenetre.add(sm_produits);
         sm_produits.addActionListener(new i_products(name));
         
         this.m_fenetre.add(sm_employes);
         sm_employes.addActionListener(new i_employe(name)); 
         
         this.m_fenetre.add(sm_clients);
         sm_clients.addActionListener(new i_clients(name));          
         
         this.m_help.add(sm_apropos);
         sm_apropos.addActionListener(new apropos(){
 			private static final long serialVersionUID = 1L;
 		});
 		
         this.menuBar.add(m_fichier);
         this.menuBar.add(m_compte);
 		this.menuBar.add(m_fenetre);
 		this.menuBar.add(m_help);
 		this.setJMenuBar(menuBar);
         
    }
}
