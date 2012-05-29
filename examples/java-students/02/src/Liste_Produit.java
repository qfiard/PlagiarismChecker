import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Liste_Produit {

	LienBD link;
	
	public Liste_Produit(LienBD l) throws SQLException{
		JFrame frame = new JFrame();
		JTable aTable;
		Object[][] produits;
		frame.setSize(600,200);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("Liste des Produits :");
 		this.link=l;
 		
 		
 		String[] entetes = {"id_produit","Description","Prix","Disponibilite"};
 		ResultSet rs = this.link.querySQL("select id_produit,description,cout,reserve from produit ;");

		rs.last();
		int nbrows=rs.getRow();
		produits=new Object[nbrows][4];
		rs.first();
		
		try{
		
		
		for(int i=0;i<nbrows;i++){
			rs.next();
			produits[i][0]=rs.getString("id_produit");
			produits[i][1]=rs.getString("description");
			produits[i][2]=rs.getString("cout");
			produits[i][3]=rs.getString("reserve");
		
		
			//TODO 6 colonnes avec produit et Qte
		}
		
		
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
		
		aTable = new JTable(produits,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
		
		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);  
	}
}
