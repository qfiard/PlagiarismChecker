import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.toedter.calendar.JCalendar;


public class interface_date extends JFrame{ 

	private static final long serialVersionUID = 1L;
	private JCalendar calendrier1 = new JCalendar(); 
	private JButton b_conf = new JButton("Confirmer l'achat");
	private JLabel l_date = new JLabel();
	private JLabel l_selDate = new JLabel("Vous avez choisi le : ");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String date;
	private String s_client;
	private int i_prix;
	
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
private int i_nb_article;
	
	
	public String getDate(){
		return date;
	}
	
	
	public interface_date(String id_client,int prix,int article,final String [] panier) {
		

		
        s_client = id_client;
        i_prix=prix;
		i_nb_article = article;
		
		this.setTitle("Choisissez une date de livraison");
        this.setSize(400, 200);

        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        
        JPanel pan = new JPanel();
        
        pan.add(calendrier1);
        pan.add(b_conf);
        
        b_conf.addActionListener(new ActionListener(){
 			public void actionPerformed(ActionEvent arg0) {
        
 		        date = sdf.format(calendrier1.getCalendar().getTime());
 		        System.out.println(date);
 		        
 		     //requete
 				database_connection con_2;
				try {
					con_2 = new database_connection();

 				sql = con_2.getConn().createStatement();
 				
 				
 				System.out.println("INSERT INTO commande (id_client,date_prevue,prix,etat) values ('"+s_client+"','"+date+"','"+i_prix+"','NEXP');");
 				ResultSet rs3 = sql.executeQuery("INSERT INTO commande (id_client,date_prevue,prix,etat) values ('"+s_client+"','"+date+"','"+i_prix+"','NEXP');");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}

 				
 				
 				int serialNum = 0;
 				  
 				// get the postgresql serial field value with this query
 				String query = "select currval('commande_id_commande_seq')";
 				ResultSet rs;
 				try {
 					rs = sql.executeQuery(query);
 					if ( rs.next() )
 					{
 						try {
 							serialNum = rs.getInt(1);
 				
 						
 					} catch (SQLException e) {
 							// TODO Auto-generated catch block
 							//e.printStackTrace();
 						}
 					}
 				} catch (SQLException e1) {
 					// TODO Auto-generated catch block
 					//e1.printStackTrace();
 				}

 				System.out.println("le serial est "+serialNum);
 				
 				for(int i =0;i<i_nb_article;i++){

 					String req = "insert into produit_commande (id_produit, id_commande) values ('"+panier[i].trim()+"', '"+serialNum+"');";
 					try {
 						sql.executeQuery(req);
 					} catch (SQLException e2) {
 						// TODO Auto-generated catch block
 						//e2.printStackTrace();
 					} 					
 					
 					
 				}
 				
 				dispose();
 				
 				
 				 			}});



		pan.add(l_selDate);
		pan.add(l_date);
		
        this.setContentPane(pan);
        this.setVisible(true);
	
	
	}
}