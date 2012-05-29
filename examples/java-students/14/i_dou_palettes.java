import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class i_dou_palettes extends JFrame{ 
	
	private static final long serialVersionUID = 1L;
	private JButton b_validation = new JButton("Selectionner un produit");
	JPanel pan = new JPanel();
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private String s_name;

    public i_dou_palettes(String name, String palette) {
		s_name=name;

        System.out.println("le nomde l'utilisateur est "+ s_name);
        format_fenetre(s_name);

		database_connection con_2;
		try {
			con_2 = new database_connection();
			sql = con_2.getConn().createStatement();
			ResultSet rs;
			rs = sql.executeQuery("SELECT * from palette WHERE id_conteneur = '"+palette+"';");

			Vector values = new Vector(); 

			while(rs.next()) 
				{ 
					Vector tab = new Vector(); 
					tab.add(rs.getString("id_palette")); 
					tab.add(rs.getString("id_produit")); 
					tab.add(rs.getString("nb_produits"));
					values.add(tab); 
				} 
				Vector names= new Vector(); 
		
		
				names.addElement("id_palette"); 
				names.addElement("id_produit");
				names.addElement("nb_produits");
				DefaultTableModel model = new DefaultTableModel(values, names); 
		
		        final JTable tableau = new JTable(model);
		
		        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		
		   
		        getContentPane().add(b_validation, BorderLayout.SOUTH);
		        b_validation.addActionListener(new ActionListener(){
			 			public void actionPerformed(ActionEvent arg0) {
			 				int i_row_indexe =tableau.getSelectedRow();
		     				  String id_palette = tableau.getValueAt(i_row_indexe, 0).toString();
		     				  
		     				  i_dou_produit idp = new i_dou_produit(s_name, id_palette);
		     				  dispose();
			 			}				
			 		});
			        
			        
			        this.setVisible(true);
			    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
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
