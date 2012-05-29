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


public class i_employe extends JFrame implements ActionListener{ 
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JButton b_validation = new JButton("Fermer");
	private JButton b_licencie = new JButton("Licenci�");
	private JButton b_embauche = new JButton("Embauche");
	JPanel pan = new JPanel();
	
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	
	
	private String s_name;
	
    public i_employe(String name) {
		s_name=name;
	}
	
	
	
	public void actionPerformed(ActionEvent arg0) {


        System.out.println("le nomde l'utilisateur est "+ s_name);
        format_fenetre(s_name);

		database_connection con_2;
		try {
			con_2 = new database_connection();

			sql = con_2.getConn().createStatement();
		
		ResultSet rs;

			rs = sql.executeQuery("SELECT * FROM emballeur;");

		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_emballeur")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab.add(rs.getString("nom_emballeur")); 
		tab.add(rs.getString("prenom_emballeur"));
		tab.add(rs.getString("taux_erreur"));

		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_emballeur"); 
		names.addElement("nom_emballeur");
		names.addElement("prenom_emballeur");
		names.addElement("taux_erreur");

		DefaultTableModel model = new DefaultTableModel(values, names); 
		

		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        final JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
       
        
        getContentPane().add(b_licencie, BorderLayout.EAST);
        b_licencie.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {
 				//licenciment de la personne

 				String s_to_the_trash = tableau.getValueAt((tableau.getSelectedRow()),0).toString();
 				System.out.println("la personne � licenci� est  :"+s_to_the_trash);
 				database_connection con_3;
 				try {
 					con_3 = new database_connection();

 				sql = con_3.getConn().createStatement();
 				sql.executeQuery("DELETE from emballeur where id_emballeur='"+s_to_the_trash+"';");
 				dispose();
 				} catch (Exception e) {
 					
 				}
 				
 				
 				
 			}				
 		});
        
        
        getContentPane().add(b_validation, BorderLayout.SOUTH);
        b_validation.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {
 				dispose();
 			}				
 		});
        
        
        this.setVisible(true);
    
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


    private void format_fenetre(String name) {
    	this.setTitle("Bienvenue "+name);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

    }

    public class i_licenciment implements ActionListener {

    	@Override
    	public void actionPerformed(ActionEvent e) {
    		//tableau.getSelectedRow();
    		
    	}
    
    
    
    }
}
