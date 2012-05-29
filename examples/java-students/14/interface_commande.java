import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class interface_commande extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private JButton b_information = new JButton("Plus d'information sur cette commande");
	private String s_name;
	
    public interface_commande(String name) {
		s_name=name;
	}
	
	public void actionPerformed(ActionEvent arg0) {
        this.setTitle("Mes commandes");
        this.setSize(400, 310);

        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        
        JPanel pan = new JPanel();
        
        
        database_connection con_2;
		try {
			con_2 = new database_connection();

		sql = con_2.getConn().createStatement();
		ResultSet rs = sql.executeQuery("select * from commande where id_client = '"+s_name+"';");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_commande")); //on met le nom exact de la colonne dans la base 
		//a refaire pour les autres colones 
		tab.add(rs.getString("date_prevue")); 
		tab.add(rs.getString("prix"));
		tab.add(rs.getString("etat"));

		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_commande"); 
		names.addElement("date_prevue");
		names.addElement("prix");
		names.addElement("etat");

		DefaultTableModel model = new DefaultTableModel(values, names); 
		
		
		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

        
        getContentPane().add(b_information, BorderLayout.SOUTH);

        this.setVisible(true);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
