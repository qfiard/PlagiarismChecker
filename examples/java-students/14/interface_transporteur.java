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


public class interface_transporteur extends JFrame{ 
	
	private static final long serialVersionUID = 1L;
	JPanel pan = new JPanel();
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	
	
	private String s_name;


	public interface_transporteur(String name) {
		s_name=name;

        System.out.println("le nomde l'utilisateur est "+ s_name);
        format_fenetre(s_name);

		database_connection con_2;
		try {
			con_2 = new database_connection();

			sql = con_2.getConn().createStatement();
		
		ResultSet rs;

			rs = sql.executeQuery("select * from conteneur natural join palette natural join emballage natural join commande natural join produit where code_transporteur = '"+s_name+"';");

		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_emballage")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab.add(rs.getString("qualif"));
		tab.add(rs.getString("date_prevue"));		
		tab.add(rs.getString("id_conteneur"));
		tab.add(rs.getString("id_palette"));
		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_emballage"); 
		names.addElement("qualif");
		names.addElement("date_prevue");
		names.addElement("id_conteneur");
		names.addElement("id_palette");

		DefaultTableModel model = new DefaultTableModel(values, names); 
		

		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

   
        
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
