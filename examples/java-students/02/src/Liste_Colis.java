import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Liste_Colis {

	LienBD link;
	
	public Liste_Colis(LienBD l) throws SQLException{
		JFrame frame = new JFrame();
		JTable aTable;
 		frame.setSize(600,200);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("Liste des Colis :");
 		this.link=l;
 		
 		
 		String[] entetes = {"Id_produit","Prix"};
 		ResultSet rs = this.link.querySQL("select id_produit,cout from colis ;");

		rs.last();
		int nbrows=rs.getRow();
		Object [][]colis=new Object[nbrows][2];
		rs.first();
		
		try{
 
			for(int i=0;i<nbrows;i++){
				rs.next();
				colis[i][0]=rs.getString("id_produit");
				colis[i][1]=rs.getString("cout");
			}
 
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
		
		aTable = new JTable(colis,entetes);
 		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
 		
		
 		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
