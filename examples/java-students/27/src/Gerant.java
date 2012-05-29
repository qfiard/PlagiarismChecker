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


public class Gerant extends JFrame implements ActionListener{
	
	// Variables
	public String Pays;
	public String Query;
	
	public int detail;
	
	
	//////////////////////////////Menu ////////////////////////////////////
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu menu1 = new JMenu("Fichier");
	private JMenuItem m1item1 = new JMenuItem("Deconnexion");
	private JMenuItem m1item2 = new JMenuItem("Fermer");
	
	private JMenu menu2= new JMenu("Personnel-Client");
	private JMenuItem m2item1 = new JMenuItem("Gerant");
	private JMenuItem m2item2 = new JMenuItem("Emballeur");
	private JMenuItem m2item3 = new JMenuItem("Transporteur");
	private JMenuItem m2item4 = new JMenuItem("Client");
		
	private JMenu menu3= new JMenu("Gerer");
	private JMenuItem m3item1 = new JMenuItem("Prix");
	private JMenuItem m3item2 = new JMenuItem("Licencier emballeur");
	private JMenuItem m3item3 = new JMenuItem("Licencier transporteur");
	
	
	
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
	
	
	
	
	public Gerant(){
	
		this.Pays=Pays;
		
		
		
		this.setTitle("Bienvenue Gerant");
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
		this.menu3.add(m3item1);
		this.menu3.add(m3item2);
		
		
		
		//On ajoute les menus a la MenuBar
		this.menuBar.add(menu1);
		this.menuBar.add(menu2);
		this.menuBar.add(menu3);
		this.setJMenuBar(menuBar);
		
		
		//
		m1item1.addActionListener(this);
		m1item2.addActionListener(this);
		m2item1.addActionListener(this);
		m2item2.addActionListener(this);
		m2item3.addActionListener(this);
		m2item4.addActionListener(this);
		m3item1.addActionListener(this);
		m3item2.addActionListener(this);
		m3item3.addActionListener(this);
		
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
			Query = "SELECT nom,prenom,login" +
					" FROM gerant;";
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
			Query ="SELECT * " +
					" FROM emballeur;";
					
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
			
			Query ="SELECT * " +
					" FROM transporteur;";
					
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
		else if(e.getSource()==m2item4){
			Query ="SELECT * " +
					" FROM client;";
				
			System.out.println(Query);
			try {
				
				this.jtbl=Requete.executeQuery2(Query);
				sud.setVisible(false);
				centre.removeAll();
				centre.add(new JScrollPane(jtbl));
				this.validate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource()==m3item1){
			
		}
		
		
		
		
		
		
		
		
		
		else if(e.getSource()==valider){
			
		}
	}
	
}

