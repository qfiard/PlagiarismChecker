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


public class i_products extends JFrame implements ActionListener{ 
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JButton b_validation = new JButton("Mise a Jour des Prix");

	JPanel pan = new JPanel();
	
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	
	
	private String s_name;
	
    public i_products(String name) {
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

			rs = sql.executeQuery("SELECT * FROM produit;");

		
		
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

   
        getContentPane().add(b_validation, BorderLayout.SOUTH);
        b_validation.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {

 				int[] row_indexes=tableau.getSelectedRows();
 				int taille =row_indexes.length;

 				String prix[ ] = new String[taille];
 				String article[ ] = new String[taille];
 				
 				for(int i=0;i<row_indexes.length;i++){
 					article[i]=tableau.getValueAt(row_indexes[i],0).toString();
 					prix[i]=tableau.getValueAt(row_indexes[i], 2).toString();
// 				  System.out.println("Les lignes selectionn�e sont"+ domain);
 				}
 				
 				

 				for(int i =0;i<taille;i++){

	 					//update for 'article' prix = 
 					int taux=0;
	 					//connection database
	 					database_connection con_3;

	 						try {
								con_3 = new database_connection();

	 						sql = con_3.getConn().createStatement();
	 					
	 					ResultSet rs;
	 						// recuperation de l'ancien prix
	 						rs = sql.executeQuery("SELECT prix from produit WHERE id_produit ='"+article[i]+"';");
	 						String old_price="";
	 						while(rs.next()) 
	 						{ 
	 						
	 						old_price = rs.getString("prix");
	 						}
	 						int old = Integer.valueOf(old_price);
	 						int new_p = Integer.valueOf(prix[i]);
	 						taux = (new_p * old)/100;
	 						System.out.println("update du prix taux = "+taux+"old "+old+"new "+new_p );
	 						
	 						sql.executeQuery("UPDATE produit SET prix ='"+prix[0]+"',taux_augmentation ='"+taux+"' where id_produit ='"+article[i]+"';");

	 						
							} catch (Exception e) {
								dispose();
								//e.printStackTrace();
							}

 				}
 				//
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

 
}
