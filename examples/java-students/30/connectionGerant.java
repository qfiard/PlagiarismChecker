import java.io.*;
import java.sql.*;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class connectionGerant extends JFrame {
	//Connection conn; // la connexion a la base
	connectionBDD connecte;
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu menu_cmd_simple = new JMenu("Acces BDD Simple");
	private JMenu menu_recherche = new JMenu("Rechercher dans la BDD");
	private JMenu menu_changement = new JMenu("Changement dans la BDD");
	
	private JMenuItem cmd_simple1 = new JMenuItem("Liste Employes");
	private JMenuItem cmd_simple2 = new JMenuItem("Liste Client");
	
	private JMenuItem cmd_recherche1 = new JMenuItem("Nombre de Colis traite par jour (avec numero employe )");
	private JMenuItem cmd_recherche2 = new JMenuItem("Produit les plus vendus");
	private JMenuItem cmd_recherche3 = new JMenuItem("Liste des client les plus depensiers");
	
	private JMenuItem cmd_changement1 = new JMenuItem("Changer le prix d'un produit");
	private JMenuItem cmd_changement2 = new JMenuItem("Licencier un personel ineficace");
	
	
	private JTextArea affiche_resultat =new JTextArea("########################## RESULTAT REQUETE #######################");
	private JScrollPane scroll;
	
	private JTextField selection = new JTextField();
	JLabel label_selection ;
	
	public connectionGerant(final connectionBDD connecte,final String Pays){
		
		this.connecte = connecte;
		this.setTitle("...... GERANT ......");
		this.setSize(1080,960);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setBounds(0,0,1080,700);
        affiche_resultat.setEditable(false);
        Font police=new Font("Monospaced",Font.PLAIN,12);
        affiche_resultat.setFont(police);
		
		menu_cmd_simple.add(cmd_simple1);
		menu_cmd_simple.add(cmd_simple2);
        
        menu_recherche.add(cmd_recherche1);
		menu_recherche.add(cmd_recherche2);
        menu_recherche.add(cmd_recherche3);
		
		menu_changement.add(cmd_changement1);
		menu_changement.add(cmd_changement2);
        
        menuBar.add(menu_cmd_simple);
		menuBar.add(menu_recherche);
		menuBar.add(menu_changement);
		
		final JPanel container = new JPanel();
		
        scroll =new JScrollPane(affiche_resultat);
        this.setLayout(new BorderLayout());
        this.add(scroll ,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
		
        cmd_simple1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				affiche_resultat.setText(connecte.gerant_SQL2());
			}				
		});
		
		cmd_simple2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				affiche_resultat.setText(connecte.gerant_SQL1());
			}				
		});
		
		
		

		cmd_recherche1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionGerant.this.setVisible(false);
				connectionGerant.this.getContentPane().removeAll();
				JLabel label_selection = new JLabel("Numero");
				JPanel PanelNumero = new JPanel();	
				PanelNumero.removeAll();
				selection.setPreferredSize(new Dimension(200,25));
				PanelNumero.add(label_selection);
				PanelNumero.add(selection);
				
				container.removeAll();
				container.add(PanelNumero,BorderLayout.NORTH);
				container.add(affiche_resultat,BorderLayout.CENTER);
				affiche_resultat.setText(connecte.print_commande(Pays));
				container.add(affiche_resultat , BorderLayout.CENTER);
				connectionGerant.this.setContentPane(container);
				connectionGerant.this.setVisible(true);
			}				
		});
		
		cmd_recherche2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				JLabel label_selection = new JLabel("Destination");
				affiche_resultat.setText(connecte.recherche_commande2(Pays));
			}				
		});
		
		cmd_recherche3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				label_selection = new JLabel("Numero Produit ");
				
				affiche_resultat.setText(connecte.recherche_commande3());
			}				
		});
		
		
	}
	
	
	    
	
	/*public static void main(String[] args){
		
	}*/
	
} 
