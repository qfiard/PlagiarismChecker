import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Client_Prod implements ActionListener {
	//interface du client pour selectionner des produits
	
	protected LienBD link;
	protected JFrame frame;
	protected JPanel panel;
	protected JTable tableau;
	protected Object[][] produits;
	protected String id_client;
	
	public Client_Prod(LienBD lien,String client) throws SQLException{
		//on ne recree pas de nouveau lien, on le file a tous les objets qui pointe sur le meme LienBD
		
		link= lien;
		System.out.println("link cree");
		this.id_client=client;
		this.frame = new JFrame();
		this.frame.setTitle("Client Produits");
		this.frame.setSize(800,700);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		
		this.panel = new JPanel(new BorderLayout());
		JButton valider = new JButton("Valider la commande");
		valider.setActionCommand("valide");
		valider.addActionListener(this);
		
		this.panel.add(valider,BorderLayout.SOUTH);
		String[] entetes = {"id_produit","Description","Cout","Poids","Qualifiant","Reserve","Quantite"};
		
		
 		ResultSet rs = this.link.querySQL("select * from produit where reserve > 0 ;");
		
		rs.last();
		int nbrows=rs.getRow();//numero de la derniere colonne
		
		this.produits = new Object[nbrows][7];
		rs.first();
		for(int i=0;i<nbrows;i++){
			if(rs.next()){
				this.produits[i][0]=rs.getString("id_produit");
				this.produits[i][1]=rs.getString("description");
				this.produits[i][2]=rs.getString("cout");
				this.produits[i][3]=rs.getString("poids");
				this.produits[i][4]=rs.getString("qualifiant");
				this.produits[i][5]=rs.getString("reserve");
				this.produits[i][6]=0;
			}
		}
	
		
		
		//Fait que tout le tableau devien non editable sauf la derniere colonne
		this.tableau = new JTable(produits,entetes){ 

			public boolean isCellEditable(int row, int column) { 
				if(column == 6){
					return true;
				}		
				return false;
			} 
		};
			
		
		
		JScrollPane ascenseur = new JScrollPane(this.tableau);
		 
		
		this.panel.add(valider,BorderLayout.SOUTH);
		this.panel.add(ascenseur,BorderLayout.CENTER);
		this.frame.setContentPane(this.panel);
		this.frame.setVisible(true);
		
	}
 	


	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if("valide".equals(cmd)){
	
			//linkedList car on n'a pas de moyen simple d'avoir la longeur;
			LinkedList<String> produits= new LinkedList<String>();
			LinkedList<Integer> quantite= new LinkedList<Integer>();
			
			for(int i=0;i<this.produits.length-1;i++){	
				if(!this.tableau.getValueAt(i, 6).toString().matches("0")){
					produits.add(this.tableau.getValueAt(i, 0).toString());
					quantite.add(Integer.parseInt(this.tableau.getValueAt(i, 6).toString()));
					
				}
			}

			JOptionPane.showMessageDialog(frame,
			"Entrez la date de livraison");
			
			Date_commande dc= new Date_commande(this.link,produits,quantite,this.id_client);
	
		}
	}
}








