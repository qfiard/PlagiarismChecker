import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class i_dou_produit extends JFrame{ 
	
	private static final long serialVersionUID = 1L;
	private JButton b_accepter = new JButton("Accepter ce produit");
	private JButton b_renvoi = new JButton("Renvoyer ce produit");
	JPanel pan = new JPanel();
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;
	private String s_name;
	private String id_commande;
	private String s_pays;
	private GenerationControles gct;
	
    public i_dou_produit(final String name, String produit) {
		s_name=name;


        System.out.println("le nomde l'utilisateur est "+ s_name);
        format_fenetre(s_name);

		database_connection con_2;
		try {
			con_2 = new database_connection();
			sql = con_2.getConn().createStatement();
			ResultSet rs;
			rs = sql.executeQuery("SELECT * from emballage WHERE id_palette = '"+produit+"';");

			Vector values = new Vector(); 

			while(rs.next()) 
				{ 
					Vector tab = new Vector(); 
					tab.add(rs.getString("id_emballage")); 
					tab.add(rs.getString("id_produit")); 
					tab.add(rs.getString("id_commande"));
					tab.add(rs.getString("id_emballeur")); 
					values.add(tab); 
				} 
				Vector names= new Vector(); 
		
		
				names.addElement("id_emballage"); 
				names.addElement("id_produit"); 
				names.addElement("id_commande");
				names.addElement("id_emballeur");
				
				try {
					id_commande =rs.getString("id_commande");
				}
				catch(Exception e) {
					
				}
				
				DefaultTableModel model = new DefaultTableModel(values, names); 
		
		        final JTable tableau = new JTable(model);
		
		        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		        getContentPane().add(b_accepter, BorderLayout.WEST);
		        b_accepter.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
	     				 try {
	     					database_connection con_3;
	     					con_3 = new database_connection();
	     					sql = con_3.getConn().createStatement();
	     					ResultSet rs;
	     					
	     					rs = sql.executeQuery("SELECT pays from douane WHERE login ='"+name+"';");
	     					
	     					s_pays = rs.getString("pays");
		     				} catch (Exception e) {

		     					
	     				}	     					
	     							
	     					gct.valider(s_pays,Integer.parseInt(id_commande));

					}
	        			
		});
		        getContentPane().add(b_renvoi, BorderLayout.EAST);
		        b_renvoi.addActionListener(new ActionListener(){
			 			public void actionPerformed(ActionEvent arg0) {
			 				

			 				int index_commande =tableau.getSelectedRow();
		     				  String id_commande = tableau.getValueAt(index_commande, 2).toString();
		     				 try {
		     					database_connection con_3;
		     					con_3 = new database_connection();
		     					sql = con_3.getConn().createStatement();
		     					ResultSet rs;
		     					sql.executeQuery("DELETE from commande WHERE etat='"+id_commande+"';");
			     				} catch (Exception e) {
			     					dispose();
			     					interface_douane id = new interface_douane(s_name);
			     				}
		     				 try {
		     					sql.executeQuery("UPDATE commande SET etat='NEXP' WHERE etat='"+id_commande+"';");
			     				} catch (SQLException e) {
			     					dispose();
			     					interface_douane id = new interface_douane(s_name);
			     				}
		     				 	
		     				JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		     				String s_commentaire = jop.showInputDialog(null, "Veuillez noter vos observations !", "Motif de refus !", JOptionPane.QUESTION_MESSAGE);
		     				 

		     					gct.refuser(id_commande,Integer.parseInt(id_commande),s_commentaire);




		     
		     				  //supprimer commande de l'emballage
			 			}				
			 		});
			        
			        
			        this.setVisible(true);
			    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		gct = new GenerationControles(sql);
	}


    private void format_fenetre(String name) {
    	this.setTitle("Bienvenue "+name);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

    }

 
}
