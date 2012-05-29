import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Date_commande implements ActionListener{
	//Fenetre quand on a selectionner une quantite sur des produits (Client_prod), on va mtn donner la date de cette commande
	
	protected LienBD link;
	protected JFrame frame;
	protected JTextArea d;
	protected LinkedList<String> produits;
	protected LinkedList<Integer> quantite;
	protected String id_client;
	
	public Date_commande (LienBD l,LinkedList<String> pr,LinkedList<Integer> q,String client){
		this.produits=pr;
		this.quantite=q;
		this.link=l;
		this.id_client=client;
		
		JButton valider = new JButton("Valider la date");
		valider.setActionCommand("valide");
		valider.addActionListener(this);
		
		this.d=new JTextArea("annee-mois-jour");
		
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(this.d,BorderLayout.NORTH);
		panel.add(valider,BorderLayout.SOUTH);
		
		this.frame= new JFrame();
		this.frame.setTitle("Client Produits");
		this.frame.setSize(300,100);
		this.frame.setLocationRelativeTo(null);
		this.frame.setContentPane(panel);
		this.frame.setVisible(true);
		
		
	}
	
	public boolean format_date(String s){
		Date mtn = new Date(System.currentTimeMillis());
		String[] coupure=s.split("-");
		int[] tab=new int[coupure.length];
		try {
			for(int i=0;i<coupure.length;i++){
				tab[i]=Integer.parseInt(coupure[i]);
			}
			if(tab.length!=3){
				return false;
			}
			//Souci car toutes les fonctions dont on a besoin sont deprecated, pas une priorité
			
			
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if("valide".equals(cmd)){
			try {
				// on doit recuperer le num_commande a chaque fois, car si on utilise serial
				// on n'a pas de moyen de le recuperer pour un client, car il peut avoir 
				// plusieur commandes;
				
				int num_commande=1;
				ResultSet rs = this.link.querySQL("select max(num_commande) from commande;");

				if(rs.next()){
					if(rs.getInt("max")!=0){	
						num_commande= Integer.parseInt(rs.getString("max"))+1;	
					}
				}
				if(format_date(this.d.getText())){
				
					this.link.st.execute("insert into commande values (" +
							"\'"+this.id_client + "\'," +
							num_commande+"," +
							"\'"+ this.d.getText()+"\'," +
							"\'non expediee\'" +
							");");
					
					for(int i =0; i<this.produits.size();i++){
						this.link.st.execute("insert into commande_produit values (" +
								num_commande+"," +
								"\'"+this.produits.get(i) + "\',"+
								this.quantite.get(i) +
								");");
						
						
						this.link.st.execute("update produit SET " +
								"reserve = reserve - "+this.quantite.get(i) +
								" where id_produit = \' "+this.produits.get(i)+"\'" +
								";");
					}
				} else {
					JOptionPane.showMessageDialog(frame,
					"Format de date non respécté !");
				}
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			this.frame.setVisible(false);
		}
		
	}
}
