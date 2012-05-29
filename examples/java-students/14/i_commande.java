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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class i_commande extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton b_info = new JButton("Colis pret � expedition");
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private String s_comm;
	private String s_emb;
	
	public i_commande(String comm, String s_emballeur) {
		s_comm = comm;

		this.s_emb=s_emballeur;
		
		
        this.setTitle("Descriptif de la commande");
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
		ResultSet rs = sql.executeQuery("select * from produit_commande where id_commande='"+s_comm+"';");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_produit")); //on met le nom exact de la colonne dans la base 


		values.add(tab); 
		} 
		Vector names= new Vector(); 


		names.addElement("id_produit"); 


		DefaultTableModel model = new DefaultTableModel(values, names); 
		
        final JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

        
        getContentPane().add(b_info, BorderLayout.SOUTH);
        b_info.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {

 				database_connection con_3;
 				try {
 					con_3 = new database_connection();

 				sql = con_3.getConn().createStatement();
 				sql.executeQuery("UPDATE commande set etat='PEXP' where id_commande = '"+ s_comm+"' AND etat='NEXP';");
 				
 				GenerationColis gc = new GenerationColis(sql);
 				gc.traiter_today(Integer.parseInt(s_comm), s_emb);
 				
 				dispose();
 				} catch (Exception e) {
 					
 				}
 				
 			}	
 				
 				
 		});
        this.setVisible(true);
        
		} catch (Exception e) {
			
		}
		
	}
	
	
	
}
