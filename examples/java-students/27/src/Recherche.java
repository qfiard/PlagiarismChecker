import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;




public class Recherche extends JFrame implements ActionListener{
	
	
	// Variables
	public String Pays;
	public String Query;
	
	
	//Panel principal de la fenetre 
	private JPanel container = new JPanel();
		
	//Panel NORD
	private JPanel nord = new JPanel();
	private JTable jtbl=new JTable();
		
		
	//Panel CENTRE
	private JPanel centre = new JPanel();
	private JLabel label = new JLabel("Id de la Commande");
	private JTextField jt_id = new JTextField();
	private JLabel label2 = new JLabel("Ville");
	private JTextField jt_ville = new JTextField();
	private JLabel label3= new JLabel("Contenu");
	private JTextField jt_contenu = new JTextField();
	
	
	private JPanel centre1 = new JPanel();
	private JPanel centre2= new JPanel();
	private JPanel centre3 = new JPanel();
	
	
		
		
	//Panel SUD 
	private JPanel sud = new JPanel();
	private JButton valider = new JButton("Valider");
	private JButton effacer = new JButton("Effacer");
	private JButton quitter = new JButton("Quitter");
	
	public Recherche(String pays){
		
		this.Pays=pays;
		this.Query="SELECT DISTINCT refcommande,refclient,etat_com,prix_com " +
		"FROM client natural join commande natural join qtecommande " +
		"WHERE pays = '"+this.Pays+"'";
		
		this.setTitle("Recherche");
		this.setSize(300,200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		contenu();
		this.setContentPane(container);
		this.setVisible(true);
		
	}
		
	public void contenu(){
		
		container.setLayout(new BorderLayout());
		
		
		jt_id.setPreferredSize(new Dimension(150, 25));
        jt_ville.setPreferredSize(new Dimension(150, 25));
        jt_contenu.setPreferredSize(new Dimension(150, 25));
        
        valider.addActionListener(this);
        effacer.addActionListener(this);
        quitter.addActionListener(this);
        
		centre1.add(label);
		centre1.add(jt_id);
		
		centre2.add(label2);
		centre2.add(jt_ville);
		
		centre3.add(label3);
		centre3.add(jt_contenu);
		
		centre.add(centre1);
        centre.add(centre2);
        centre.add(centre3);
        
        
        sud.add(valider);
		sud.add(effacer);
		sud.add(quitter);
		
		container.add(nord, BorderLayout.NORTH);	
		container.add(centre,BorderLayout.CENTER);
		container.add(sud, BorderLayout.SOUTH);
		
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		if(e.getSource()==effacer){
			jt_id.setText(null);
			jt_ville.setText(null);
			jt_contenu.setText(null);	
		}
		
		else if(e.getSource()==quitter ){
			this.dispose();
			Douane d = new Douane(this.Pays);
		}
		
		else if(e.getSource()==valider){
			this.Query+=" AND cast(refcommande as varchar) LIKE '%"+jt_id.getText()+"%' " +
						" AND ville LIKE '%"+jt_ville.getText()+"%' "+
						"AND refprod LIKE '%"+jt_contenu.getText()+"%'";
			
			System.out.println(Query);
			
			try {
				this.jtbl=Requete.executeQuery(Query);
				this.setSize(600,600);
				
				
				sud.remove(effacer);
				sud.remove(valider);
				
				centre.removeAll();
				centre.add(new JScrollPane(this.jtbl));
				this.validate();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	
	
}
