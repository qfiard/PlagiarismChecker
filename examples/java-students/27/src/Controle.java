import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;


public class Controle extends JFrame implements ActionListener{
	
		// Variables
		public String Pays;
		public String Query;
		public int refcol;
		
		//Panel principal de la fenetre 
		private JPanel container = new JPanel();
			
		//Panel NORD
		private JPanel nord = new JPanel();
			
		//Panel CENTRE
		private JPanel centre = new JPanel();
		private JPanel centre1 = new JPanel();
		private JPanel centre2 = new JPanel();
		private JLabel label = new JLabel("Reference Colis");
		private JLabel label2= new JLabel("Motif");
		private JTextField jt_colis = new JTextField();
		private JTextField jt_motif = new JTextField();
		
		//Panel SUD 
		private JPanel sud = new JPanel();
		private JButton autoriser = new JButton("Autoriser");
		private JButton refuser = new JButton("Refuser");
		private JButton quitter = new JButton("Quitter");
		private JButton ok = new JButton("Envoye");
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	public Controle(String pays){
		this.Pays=pays;
		
		
		
		this.setTitle("Controle");
		this.setSize(500,150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		contenu();
		this.setContentPane(container);
		this.setVisible(true);
	}



	public void contenu(){
		
		
		container.setLayout(new BorderLayout());
		
		
		jt_colis.setPreferredSize(new Dimension(150, 25));
		jt_motif.setPreferredSize(new Dimension(200, 25));
		
		autoriser.addActionListener(this);
        refuser.addActionListener(this);
        quitter.addActionListener(this);
		ok.addActionListener(this);
		
		centre1.add(label);
		centre1.add(jt_colis);
		
		
		centre2.add(label2);
		centre2.add(jt_motif);
		
		
		
		centre.add(centre1);
		
		
		
		
		sud.add(autoriser);
		sud.add(refuser);
		sud.add(quitter);
		
		
		container.add(nord, BorderLayout.NORTH);	
		container.add(centre,BorderLayout.CENTER);
		container.add(sud, BorderLayout.SOUTH);
		
		
	}













	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if (e.getSource()==autoriser){
			Query = "SELECT refcolis " +
					" FROM conteneur natural join palette natural join colis" +
					" WHERE destination='"+this.Pays+"'" +
					" AND controle_col=0" +
					" AND etat_col='emballe'" +
					" AND cast(refcolis as varchar)='"+jt_colis.getText()+"';";
			
			System.out.println(Query);
			
			if (jt_colis.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez remplir le champs SVP", "Attention", JOptionPane.WARNING_MESSAGE);
			}
			
			
			else {
				try {
					
					ResultSet result  = Main.state.executeQuery(Query);
					
					
					if(result.next()) {
						this.refcol = result.getInt("refcolis");
						result.close();
						
						System.out.println(this.refcol);
						
						
						
						
						
						//CONTROLE DU COLIS PASSE A 1 et ETAT COL PASSE A ENVOYE
						Main.state.executeUpdate
						("UPDATE colis SET etat_col='envoye',controle_col=1 WHERE refcolis="+this.refcol);
						
						
						//CONTROLE COMMANDE PASSE A 1
						Main.state.executeUpdate
						("UPDATE commande SET controle_com=1 WHERE refcommande=" +
								" (SELECT refcommande FROM colis where refcolis="+this.refcol+")");

						JOptionPane.showMessageDialog(null, "Vous avez autoriser le colis"+this.refcol, "Autoriser", JOptionPane.WARNING_MESSAGE);
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Mauvaise Reference Colis", "Erreur", JOptionPane.ERROR_MESSAGE);
					}		
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}				
				
				
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		else if (e.getSource()==refuser){
			
			
			Query = "SELECT refcolis " +
					" FROM conteneur natural join palette natural join colis" +
					" WHERE destination='"+this.Pays+"'" +
					" AND controle_col=0" +
					" AND etat_col='emballe'" +
					" AND cast(refcolis as varchar)='"+jt_colis.getText()+"';";
			
			
			
			System.out.println(Query);
			
			if (jt_colis.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez remplir le champs SVP", "Attention", JOptionPane.WARNING_MESSAGE);
			}
			
			else{
				
				try {
					
					ResultSet result  = Main.state.executeQuery(Query);
					
					
					if(result.next()) {
						this.refcol = result.getInt("refcolis");
						result.close();
						
						System.out.println(this.refcol);
						
						
						
						//CONTROLE DU COLIS PASSE A 1 et ETAT COL PASSE A REJETE
						Main.state.executeUpdate
						("UPDATE colis SET etat_col='rejete',controle_col=1 WHERE refcolis="+this.refcol);
						
						int refcom=Requete.com_corresp(this.refcol);
						
						//CONTROLE COMMANDE PASSE A 1
						Main.state.executeUpdate
						("UPDATE commande SET controle_com=1 WHERE refcommande="+refcom);
						
						
						
						//SI TOUT LES COLIS DE LA COMMANDE ST REJETER ALORS ON PASSE A NON EXPEDIEE
						
						if(Requete.all_rejete(refcom)){
							Main.state.executeUpdate
							("UPDATE commande SET etat_com='non expediee' WHERE refcommande="+refcom);
							
							
						}
						
						centre.removeAll();
						centre.add(centre2);
						
						
						sud.removeAll();
						sud.add(ok);
						
							
							
					}
					else {
						JOptionPane.showMessageDialog(null, "Mauvaise Reference Colis", "Erreur", JOptionPane.ERROR_MESSAGE);
					}		
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}				
				
			}
				
		}
		
		
		
		
		
		
		
		
		
		else if (e.getSource()==quitter){
			this.dispose();
			Douane d=new Douane(this.Pays);
		}
		
		
		else if(e.getSource()==ok){
			
			
			//Motif dans la table colis pour expliker le refus
			try {
				Main.state.executeUpdate("UPDATE colis SET motif="+jt_motif.getText()+" WHERE refcolis="+this.refcol);
				this.dispose();
				Douane d=new Douane(this.Pays);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
}
