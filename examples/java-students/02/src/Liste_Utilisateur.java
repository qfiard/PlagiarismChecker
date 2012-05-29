
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Liste_Utilisateur {

	LienBD link;
	
	public Liste_Utilisateur(LienBD l) throws SQLException{
		JFrame frame = new JFrame();
		JTable aTable;
		Object[][] utilisateurs;
		frame.setSize(600,200);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("Liste des Produits :");
 		this.link=l;
 		
 		
 		String[] entetes = {"Type","Login","Pays","taux d Erreur"};
 		ResultSet rs = this.link.querySQL("select type,login,pays,taux_erreur ;");

		rs.last();
		int nbrows=rs.getRow();
		utilisateurs=new Object[nbrows][4];
		rs.first();
		
		try{
		
		
		for(int i=0;i<nbrows;i++){
			rs.next();
			utilisateurs[i][0]=rs.getString("type");
			utilisateurs[i][1]=rs.getString("login");
			utilisateurs[i][2]=rs.getString("pays");
			utilisateurs[i][3]=rs.getString("taux_erreur");
		
		
			//TODO 6 colonnes avec produit et Qte
		}
		
		
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
		
		aTable = new JTable(utilisateurs,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
		
		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
		  
	}
}
