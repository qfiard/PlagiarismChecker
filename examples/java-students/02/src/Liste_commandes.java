import java.awt.*;
import java.beans.Statement;
 import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
 


public class Liste_commandes  {

	LienBD link;

 	
	public Liste_commandes(LienBD l) throws ClassNotFoundException, SQLException{
		JFrame frame = new JFrame();
		JTable aTable;
		Object[][] commandes;
		frame.setSize(600,200);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("Liste des commandes expediees :");
 		this.link=l;
  
		
   		String[] entetes = {"num_commande","utilisateur","date","statut"};
 	 
 		
 		
 		
 		// select * from command where client = login
 		// pour les produits de chque commande : boucle sur toutes les commandes du clients : pour chaque cmd : 
 		// select produit,queantite from command natural joint comm-prod natural join prod where 
 		//numeroComm = numeroComm (sur lekel je boucle)
  		
 		 
 		
 		//TODO /Liste des commandes par PAYS izi
 		ResultSet rs = this.link.querySQL("select * from commande ;");
 		
 		rs.last();
		int nbrows=rs.getRow();
 		commandes=new Object[nbrows-1][4];
 		rs.first();
 
 		try{
 			
 			
 	 		for(int i=0;i<nbrows-1;i++){
 	 			if(rs.next()){
	 	 			commandes[i][0]=rs.getString("id_client");
	 	 			commandes[i][1]=rs.getString("num_commande");
	 	 			commandes[i][2]=rs.getString("date");
	 	 			commandes[i][3]=rs.getString("status");
 	 			}

 	 			//TODO 6 colonnes avec produit et Qte
 	 		}
 	 		
 			 
 		 } catch(Exception e){
 			 e.printStackTrace();
 			 System.out.println("SQLException");
 		 }
 		 
 		aTable = new JTable(commandes,entetes);
 		JScrollPane scroll = new JScrollPane(aTable);
  		
  	/*	ResultSet rs2 = 
 			this.link.querySQL("select produit,quantite from command natural join commande_produit natural join produit "+
 					"where num_commande = \'numC"); */
  		
  		
 		
 		panel.add(titre);
 		panel.add(scroll);
 
 
 		frame.setLayout(new BorderLayout());
  		frame.setContentPane(panel);
		frame.setVisible(true);
		
 
             
       
	}

	 

}
