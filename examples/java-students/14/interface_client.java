import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class interface_client extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu m_fichier = new JMenu("Fichier");
	private JMenu m_help = new JMenu("?");
	private JMenu m_compte = new JMenu("Mon Compte");
	
	private JMenuItem sm_update = new JMenuItem("Mise ˆ jour");
	private JMenuItem sm_login = new JMenuItem("Modifier mon login");
	private JMenuItem sm_passwd = new JMenuItem("Modifier mon mot de passe");
	private JMenuItem sm_quitter = new JMenuItem("Quitter");
	private JMenuItem sm_apropos = new JMenuItem("A Propos");
	private JMenuItem sm_commandes = new JMenuItem("Mes commandes");
	private JButton b_validation = new JButton("Valider ma commande et choisir les quantitées");
	private JComboBox c_fdp = new JComboBox();
    private JLabel l_fdp = new JLabel("Frais de port :");
	
	JPanel pan = new JPanel();
	
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	
	
	private String s_name;
	
    public interface_client(String name) throws SQLException, ClassNotFoundException {
        super();
        s_name = name;
        System.out.println("le nomde l'utilisateur est "+name + s_name);
        format_fenetre(name);
		toolbar(name);

		database_connection con_2 = new database_connection();
		sql = con_2.getConn().createStatement();
		ResultSet rs = sql.executeQuery("SELECT * FROM produit;");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_produit")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab.add(rs.getString("qualif")); 
		tab.add(rs.getString("prix"));
		tab.add(rs.getString("description"));
		tab.add(rs.getString("poids"));
		tab.add(rs.getString("taux_augmentation"));
		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_produit"); 
		names.addElement("qualif");
		names.addElement("prix");
		names.addElement("description");
		names.addElement("poids");
		names.addElement("taux_augmentation");
		DefaultTableModel model = new DefaultTableModel(values, names); 
		
		
		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        final JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

        getContentPane().add(l_fdp, BorderLayout.SOUTH);
        getContentPane().add(c_fdp, BorderLayout.SOUTH);
        
        getContentPane().add(b_validation, BorderLayout.SOUTH);
        b_validation.addActionListener(new ActionListener(){
     			public void actionPerformed(ActionEvent arg0) {
  
     				int[] row_indexes=tableau.getSelectedRows();
     				int taille =row_indexes.length;
	
     				String panier[ ] = new String[taille];

     				for(int i=0;i<row_indexes.length;i++){
     				  panier[i]=tableau.getValueAt(row_indexes[i], 0).toString();
//     				  System.out.println("Les lignes selectionnée sont"+ domain);
     				}
     				for(int i=0;i<panier.length;i++){

       				  System.out.print(" article "+ panier[i]);
     				}
     				
     				try {
						i_panier pan1 = new i_panier(panier, s_name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
     				//
     			}				
     		});

        
        this.setVisible(true);
    }




    private void format_fenetre(String name) {
    	this.setTitle("Bienvenue "+name);
        this.setSize(500, 500);
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

         this.m_compte.add(sm_commandes);
         sm_commandes.addActionListener(new interface_commande(name));
         this.m_compte.addSeparator();
         this.m_compte.add(sm_passwd);
         sm_passwd.addActionListener(new change_passwd(name));
         this.m_compte.add(sm_login);
         sm_login.addActionListener(new change_login(name));
         this.m_help.add(sm_apropos);
         sm_apropos.addActionListener(new apropos(){
 			private static final long serialVersionUID = 1L;
 		});
 		
         this.menuBar.add(m_fichier);
         this.menuBar.add(m_compte);
 		this.menuBar.add(m_compte);
 		this.menuBar.add(m_help);
 		this.setJMenuBar(menuBar);
         
    }






    
    
    
 
    
}
