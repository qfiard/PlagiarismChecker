import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Liste_Employes {

	LienBD link;

	
	public Liste_Employes(LienBD l) throws SQLException{
		JFrame frame = new JFrame();
		JTable aTable;

		frame.setSize(600,200);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("Liste des employes :");
 		this.link=l;
	
 		String[] entetes = {"Type","Login","Taux d Erreur"};
 		ResultSet rs = this.link.querySQL("select type,login,taux_erreur from utilisateur where " +
 				"type = \'emballeur\' or type = \'transporteur\' ;");  

		rs.last();
		int nbrows=rs.getRow();
		Object [][] employes=new Object[nbrows-1][3];
		rs.first();
		
		try{
		
		
			for(int i=0;i<nbrows-1;i++){
				if(rs.next()){
					employes[i][0]=rs.getString("type");
					employes[i][1]=rs.getString("login");
					employes[i][2]=rs.getString("taux_erreur");
				}
			}
		
		
		} catch(Exception e){
			e.printStackTrace();
		System.out.println("SQLException");
		}
	
		aTable = new JTable(employes,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
		
		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
