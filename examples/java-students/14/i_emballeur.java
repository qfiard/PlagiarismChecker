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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class i_emballeur extends JFrame {
	private JButton b_info = new JButton("Plus d'info sur cette commande");
	private JComboBox c_fdp = new JComboBox();
    private JLabel l_fdp = new JLabel("Frais de port :");
	
	JPanel pan = new JPanel();
	
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private String s_name;
	
	
	public i_emballeur(String name){
		
		s_name=name;
		format_fenetre(name);
		
		database_connection con_2;
		try {
			con_2 = new database_connection();

		sql = con_2.getConn().createStatement();
		ResultSet rs = sql.executeQuery("SELECT * FROM commande where etat='NEXP' ORDER BY id_client;");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_client"));
		tab.add(rs.getString("id_commande"));
		
		tab.add(rs.getString("date_prevue")); 
		tab.add(rs.getString("prix"));

		values.add(tab); 
		} 
		Vector names= new Vector(); 

		names.addElement("id_client");
		names.addElement("id_commande"); 
		names.addElement("date_prevue");
		names.addElement("prix");

		DefaultTableModel model = new DefaultTableModel(values, names); 
		
		
		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        final JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

        getContentPane().add(l_fdp, BorderLayout.SOUTH);
        getContentPane().add(c_fdp, BorderLayout.SOUTH);
        
        getContentPane().add(b_info, BorderLayout.SOUTH);
        b_info.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {

 				String nb_comm = tableau.getValueAt((tableau.getSelectedRow()),1).toString();
 				System.out.println("la commande recherch�e est la :"+nb_comm);
 				new i_commande(nb_comm, s_name);
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
