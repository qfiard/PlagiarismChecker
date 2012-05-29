import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class Liste_Palette{

	LienBD link;
	
	public Liste_Palette(LienBD l, int numC) throws ClassNotFoundException, SQLException{
		JFrame frame = new JFrame();
 		frame.setSize(300,300);
 		JTable aTable;
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel(" Liste des Conteneurs :");
 		this.link=l;
 		
 		 
 		String[] entetes = {"Numero ID","Pays"};
 		//System.out.println("Avant le RS");
 		
 		ResultSet rs = this.link.querySQL("select num_palette from conteneur where num_conteneur ="+numC+" ;");  
				  
 		//System.out.println("Apres le RS");

		rs.last();
		int nbrows=rs.getRow();
		Object [][] conteneurs=new Object[nbrows-1][2];
		rs.first();
		
		try{
		
		
		for(int i=0;i<nbrows-1;i++){
			if(rs.next()){
				conteneurs[i][0]=rs.getString("num_conteneur");
				conteneurs[i][1]=rs.getString("destination");
			}
		}
		
		
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
		
		aTable = new JTable(conteneurs,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre);
		panel.add(scroll);
 		
		
 		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
	
	
	}

}
