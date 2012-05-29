import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;


public class Douane extends JFrame implements ActionListener{
	
	// Variables
	public String Pays;
	public String Query;
	
	public int detail;
	
	
	//////////////////////////////Menu ////////////////////////////////////
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu menu1 = new JMenu("Fichier");
	private JMenuItem m1item1 = new JMenuItem("Deconnexion");
	private JMenuItem m1item2 = new JMenuItem("Fermer");
	
	private JMenu menu2= new JMenu("Commandes");
	private JMenuItem m2item1 = new JMenuItem("Expediées");
	private JMenuItem m2item2 = new JMenuItem("Controlés");
	private JMenuItem m2item3 = new JMenuItem("Expediée et non controlé");
	private JMenuItem m2item4 = new JMenuItem("Recherche");
		
	private JMenu menu4= new JMenu("Details");
	private JMenuItem m4item1 = new JMenuItem("Commande");
	private JMenuItem m4item1bis = new JMenuItem("Colis d'une Commande");
	private JMenuItem m4item2 = new JMenuItem("Produits d'une Commande");
	private JMenuItem m4item3 = new JMenuItem("Produits d'un Colis");
	private JMenuItem m4item4 = new JMenuItem("Colis d'une Palette ");
	private JMenuItem m4item5 = new JMenuItem("Palette d'un Conteneur");
	
	
	private JMenu menu5= new JMenu("Controle");
	private JMenuItem m5item1 = new JMenuItem("Effectuer");
	private JMenuItem m5item2 = new JMenuItem("Statistiques");
	
	
	
	
	//Panel principal de la fenetre 
	private JPanel container = new JPanel();
	
	//Panel NORD
	private JPanel nord = new JPanel();
	private JTable jtbl=new JTable();
	
	
	//Panel CENTRE
	private JPanel centre = new JPanel();
	
	
	
	//Panel SUD 
	private JPanel sud = new JPanel();
	private JLabel reference = new JLabel();
	private JTextField ref = new JTextField();
	private JButton valider = new JButton("Valider");
	
	// New Fenetre
	
	
	
	
	public Douane(){
		
	}
	
	
	//Constructeur
	public Douane (String Pays){
		
		this.Pays=Pays;
		
		
		
		this.setTitle("Douane "+ this.Pays);
		this.setSize(500,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//On stock les sous-menus dans les menus respectifs 
		this.menu1.add(m1item1);
		this.menu1.add(m1item2);
		this.menu2.add(m2item1);
		this.menu2.add(m2item2);
		this.menu2.add(m2item3);
		this.menu2.add(m2item4);
		this.menu4.add(m4item1);
		this.menu4.add(m4item1bis);
		this.menu4.add(m4item2);
		this.menu4.add(m4item3);
		this.menu4.add(m4item4);
		this.menu4.add(m4item5);
		this.menu5.add(m5item1);
		this.menu5.add(m5item2);
		
		//On ajoute les menus a la MenuBar
		this.menuBar.add(menu1);
		this.menuBar.add(menu2);
		this.menuBar.add(menu4);
		this.menuBar.add(menu5);
		this.setJMenuBar(menuBar);
		
		
		//
		m1item1.addActionListener(this);
		m1item2.addActionListener(this);
		m2item1.addActionListener(this);
		m2item2.addActionListener(this);
		m2item3.addActionListener(this);
		m2item4.addActionListener(this);
		m4item1.addActionListener(this);
		m4item1bis.addActionListener(this);
		m4item2.addActionListener(this);
		m4item3.addActionListener(this);
		m4item4.addActionListener(this);
		m4item5.addActionListener(this);
		m5item1.addActionListener(this);
		m5item2.addActionListener(this);
		
		valider.addActionListener(this);
		
		contenu();
		
		this.setContentPane(container);
		this.setVisible(true);
	
		
	}
	
	public void contenu() {
		
		
		
		
		
        container.setLayout(new BorderLayout());
		container.add(nord, BorderLayout.NORTH);
		container.add(centre,BorderLayout.CENTER);
		
		sud.add(reference);
		sud.add(ref);
		
		sud.add(valider);
		ref.setPreferredSize(new Dimension(150, 25));
		
		sud.setVisible(false);
		container.add(sud, BorderLayout.SOUTH);
		
		
		
	}

	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		////////Deconnection ainsi que Fermer le programme 
		if(e.getSource()==m1item1){
			this.dispose();
			Accueil ac = new Accueil();
		}
		else if(e.getSource()==m1item2){
			System.exit(0);
		}
		
		/////// 
		else if(e.getSource()==m2item1){
			Query = "SELECT refcommande,refclient,date_livraison,prix_com " +
					"FROM commande natural join client where pays='"+this.Pays+"' " +
					"AND etat_com='expedies';";
			System.out.println(Query);
			try {
				this.jtbl=Requete.executeQuery(Query);
				sud.setVisible(false);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				this.validate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		else if(e.getSource()==m2item2){
			Query ="SELECT refcommande,refclient,date_livraison,etat_com,prix_com " +
					"FROM commande natural join client where pays = '"+this.Pays+"'" +
					"AND controle_com=1;";
			System.out.println(Query);
			try {
				
				this.jtbl=Requete.executeQuery(Query);
				sud.setVisible(false);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				this.validate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(e.getSource()==m2item3){
			
			Query ="SELECT refcommande,refclient,date_livraison,prix_com " +
					"FROM commande natural join client where pays = '"+this.Pays+"'" +
					"AND controle_com=0 " +
					"AND etat_com='expediee'";
			
			
			System.out.println(Query);
			try{
				this.jtbl=Requete.executeQuery(Query);
				sud.setVisible(false);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				centre.validate();
				this.validate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		}
		else if(e.getSource()==m2item4){
			
			//On lance recherche
			this.dispose();
			Recherche r = new Recherche (this.Pays);
			
		}
		
		else if(e.getSource()==m4item1){
			
			sud.setVisible(true);
			
			detail=1;
			reference.setText("Ref. Commande");
			
		}
		else if(e.getSource()==m4item1bis){
			sud.setVisible(true);
			
			detail=2;
			reference.setText("Ref. Commande");
		}
		else if(e.getSource()==m4item2){
			sud.setVisible(true);
			
			detail=3;
			reference.setText("Ref. Commande");
		}
		else if(e.getSource()==m4item3){
			sud.setVisible(true);
			
			detail=4;
			reference.setText("Ref. Colis");	
		}
		else if(e.getSource()==m4item4){
			sud.setVisible(true);
			
			detail=5;
			reference.setText("Ref. Palette");	
		}
		
		else if(e.getSource()==m4item5){
			sud.setVisible(true);
			
			detail=6;
			reference.setText("Ref. Conteneur");
			
		}
		
		
		
		
		
		
		
		
		
		else if(e.getSource()==m5item1){
			this.dispose();
			Controle c = new Controle(this.Pays);
		}
		
		
		else if(e.getSource()==m5item2){
			Query="SELECT sum(controle_col) as Colis_Controle ,(SELECT count(etat_col) from colis where etat_col='rejete') as Colis_refuser FROM colis where controle_col=1;";
			
			System.out.println(Query);
			try{
				this.jtbl=Requete.executeQuery(Query);
				sud.setVisible(false);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				centre.validate();
				this.validate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
		
		
		
		else if(e.getSource()==valider){
			Query= Requete.RequeteDetail(detail,ref.getText(),this.Pays);
			
			
			System.out.println(Query);
			System.out.println(detail);
			try{
				this.jtbl=Requete.executeQuery2(Query);
				sud.setVisible(true);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				centre.validate();
				this.validate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
