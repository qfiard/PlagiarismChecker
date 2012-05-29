import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Liste_Client {

	LienBD link;
	
	public Liste_Client(LienBD l) throws ClassNotFoundException, SQLException{
		JFrame frame = new JFrame();
 		frame.setSize(300,300);
 		JTable aTable;
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel(" Liste des Clients :");
 		this.link=l;
	
 		String[] entetes = {"Login","Pays"};
 		//System.out.println("Avant le RS");
 		
 		ResultSet rs = this.link.querySQL("select login,pays from utilisateur where type = \'client\' ;");  
				  
 		//System.out.println("Apres le RS");

		rs.last();
		int nbrows=rs.getRow();
		Object [][] clients=new Object[nbrows-1][2];
		rs.first();
		
		try{
		
		
		for(int i=0;i<nbrows-1;i++){
			if(rs.next()){
				clients[i][0]=rs.getString("login");
 				clients[i][1]=rs.getString("pays");
			}
		}
		
		
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
		
		aTable = new JTable(clients,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
		
		
 		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
	
	
	}
}
