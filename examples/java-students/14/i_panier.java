import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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


public class i_panier extends JFrame {

	JPanel pan = new JPanel();
	
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private JButton b_valider = new JButton("Valider ma commande et choisir une date");
	private int i_number_rows;

	
	public i_panier(final String[] panier, final String id_client) throws ClassNotFoundException, SQLException {
	
        format_fenetre();
        
        i_number_rows = panier.length;
        
		String m_query = "";
		//mise en place d'unestring avec OR entre chaque article
		for(int i =0;i<panier.length;i++) {
			if(i==0){
				m_query = "id_produit='"+panier[i].trim()+"'";
			}else{
			m_query = m_query + " OR id_produit='" + panier[i].trim()+"'";
			}
		}
		
		System.out.println("\n");
		System.out.println("le morceau de requete est "+m_query);
		database_connection con_2 = new database_connection();
		sql = con_2.getConn().createStatement();
		
		
		
		ResultSet rs = sql.executeQuery("SELECT id_produit,prix,description FROM produit WHERE "+m_query+";");
		
		
		Vector values = new Vector(); 
		while(rs.next()) 
		{ 
		Vector tab = new Vector(); 
		tab.add(rs.getString("id_produit")); //on met le nom exact de la colonne dans la base 

		tab.add(rs.getString("prix"));
		tab.add(rs.getString("description"));
		tab.add("1");
		values.add(tab); 
		} 
		Vector names= new Vector(); 

		
		names.addElement("id_produit"); 
		names.addElement("prix");
		names.addElement("description");
		names.addElement("Quantitée");
		DefaultTableModel model = new DefaultTableModel(values, names); 
		
		//recuperation du prix total de la facture
		
		
		//recupertation de la date donnée par l'utilisateur
		
		
		
		
		
        //String[] entetes = {"id_produit", "qualif", "prix","description","poids","taux_augmentation"};

        final JTable tableau = new JTable(model);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		
		//interface JCalendar
		
		//boutonvalider avec requete de creation de la commande
        
        getContentPane().add(b_valider, BorderLayout.SOUTH);
        b_valider.addActionListener(new ActionListener(){
     			public void actionPerformed(ActionEvent arg0) {
     				
     				
     				
     				String quantitee = null; 
				int i =0;
				int i_total =0;
     			for(i=0;i<i_number_rows;i++)
     			{
					
					
     				quantitee = tableau.getValueAt(i,3).toString();
     				int i_quant = Integer.parseInt(quantitee); 
     				
     				String for_one = tableau.getValueAt(i,1).toString();
     				int i_one = Integer.parseInt(for_one);
     				
     				i_total= i_total + (i_quant * i_one);
     				
     				interface_date id = new interface_date(id_client,i_total,i_number_rows,panier);
     				     				
     				dispose();
     				}
     			
     			
     			System.out.println("le total de la facture s'éleve à :" +i_total);
     			
     			
     			
     			}});

        
		
        this.setVisible(true);
		
		
	}

	
    private void format_fenetre() {
    	this.setTitle("Bienvenue ");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }
	
	
}
