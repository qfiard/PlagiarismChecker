import java.io.*;
import java.sql.*;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Container;
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





public class connectionDouane extends JFrame {
    static Scanner in = new Scanner(System.in);
    //static connectionBDD connecte;
	Connection conn; // la connexion a la base
	connectionBDD connecte;
	
	public JFrame frame;			
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu menu_cmd_simple = new JMenu("Lister les Commandes");
	private JMenu menu_recherche = new JMenu("Rechercher dans les Commandes");
	private JMenu menu_resultat = new JMenu("Resultat sur les controles");
	private JMenu menu_lister = new JMenu("Lister les transports");
	private JMenu menu_produit = new JMenu("Verification produit");

	
	
	private JMenuItem cmd_simple1 = new JMenuItem("Toutes les Commandes expediees au Pays de la Douane");
	private JMenuItem cmd_simple2 = new JMenuItem("Commandes que vous avez controlees");
	private JMenuItem cmd_simple3 = new JMenuItem("Commandes que vous n'avez pas controlees");
	private JMenuItem cmd_simple4 = new JMenuItem("Statistique ");
	
	
	private JMenuItem cmd_recherche1 = new JMenuItem("Une commande par son numero de commande");
	private JMenuItem cmd_recherche2 = new JMenuItem("Les commandes par la destination");
	private JMenuItem cmd_recherche3 = new JMenuItem("Les commandes par le contenu");
	private JMenuItem cmd_recherche4 = new JMenuItem("Detail d'une commande");

	private JMenuItem controle1 = new JMenuItem("Entrer le resultat d'un controle");

	private JMenuItem cmd_lister1 = new JMenuItem("Les palettes d'un conteneur");
	private JMenuItem cmd_lister2 = new JMenuItem("Les colis d'une palette");
	private JMenuItem cmd_lister3 = new JMenuItem("Les produit d'un colis");

	private JMenuItem cmd_produit1 = new JMenuItem("Prix des produits transporte");

	
	
	final JPanel container = new JPanel();
	final JPanel confirmecont = new JPanel();

	
	public JTextArea affiche_resultat =new JTextArea("########################## RESULTAT REQUETE #######################");
	public JScrollPane scroll;
	
	private JTextField selection = new JTextField();
	JLabel label_selection ;
	
	
	
	//
	//
	//

    private JTextField NumeroCommande=new JTextField();
	private JLabel label_num_cmd = new JLabel("    Numero de la commande :");
	
	private JTextField destination = new JTextField();
	private JLabel label_destination = new JLabel("    Destination :");

	private JTextField contenu = new JTextField();
	private JLabel label_contenu = new JLabel("    Contenu :");
	
	private JTextField NumeroPalette=new JTextField();
	private JLabel label_palette = new JLabel("    Numero de la palette :");
	
	private JTextField NumeroContainer=new JTextField();
	private JLabel label_container = new JLabel("    Numero du container :");
	
	private JTextField NumeroColis=new JTextField();
	private JLabel label_colis = new JLabel("    Numero du colis :");
	
	private JButton confirme = new JButton("Affiche la Requete");
	
	
	
	public connectionDouane(final connectionBDD connecte,final String Pays){
		
		this.connecte = connecte;
		this.setTitle("...... DOUANE ......");
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
		menu_cmd_simple.add(cmd_simple3);
		menu_cmd_simple.add(cmd_simple4);
        
        menu_recherche.add(cmd_recherche1);
		menu_recherche.add(cmd_recherche2);
		menu_recherche.add(cmd_recherche3);
		menu_recherche.add(cmd_recherche4);

		menu_resultat.add(controle1);
		
		menu_lister.add(cmd_lister1);
		menu_lister.add(cmd_lister2);
		menu_lister.add(cmd_lister3);
		
		menu_produit.add(cmd_produit1);
        
        menuBar.add(menu_cmd_simple);
		menuBar.add(menu_recherche);
		menuBar.add(menu_resultat);
		menuBar.add(menu_lister);
		menuBar.add(menu_produit);                

        scroll = new JScrollPane(affiche_resultat);
        this.setLayout(new BorderLayout());
        this.add(scroll);
        this.setJMenuBar(menuBar);
		
        cmd_produit1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche le prix des produits d'un colis : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				NumeroColis.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_colis);
			    container.add(NumeroColis);
			    
			    container.setLayout(new GridLayout(3,2));

			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_produit1(NumeroColis.getText());
			    		affiche_resultat.setText(res);
			    		NumeroColis.setText("");
			    		connectionDouane.this.frame.dispose();
					}				
				});
			} 
		});
        
        
        
        
        cmd_lister1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Lister les palette d'un container : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				NumeroContainer.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_container);
			    container.add(NumeroContainer);
			    
			    container.setLayout(new GridLayout(2,2));
			    
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_lister1(NumeroContainer.getText());
			    		affiche_resultat.setText(res);
			    		NumeroContainer.setText("");
			    		connectionDouane.this.frame.dispose();
					}				
				});
			}     
		});
        
        cmd_lister2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche parmi les commandes : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				NumeroPalette.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_palette);
			    container.add(NumeroPalette);
			    
			    container.setLayout(new GridLayout(2,2));
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_lister2(NumeroPalette.getText());
			    		connectionDouane.this.affiche_resultat.setText(res);
			    		NumeroPalette.setText("");
			    		connectionDouane.this.frame.dispose();
					}		
				});
			}     
		});
        
        cmd_lister3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche les produits d'un colis : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				NumeroColis.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_colis);
			    container.add(NumeroColis);
			    
			    container.setLayout(new GridLayout(3,2));
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_lister3(NumeroColis.getText());
			    		connectionDouane.this.affiche_resultat.setText(res);
			    		NumeroColis.setText("");
			    		connectionDouane.this.frame.dispose();
					}				
				});
			}     
		});
        
        
        cmd_simple1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event) {
        		connectionDouane.this.affiche_resultat.setText(connecte.print_commande(Pays));
				}
		});
		
		cmd_simple2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionDouane.this.affiche_resultat.setText(connecte.print_commande2(Pays));
			}				
		});
		
		cmd_simple3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionDouane.this.affiche_resultat.setText(connecte.print_commande3(Pays));
			}				
		});
		
		cmd_simple4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionDouane.this.affiche_resultat.setText(connecte.statistique(Pays));
			}				
		});
		
		
		
	
		cmd_recherche1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche parmi les commandes : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				NumeroCommande.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_num_cmd);
			    container.add(NumeroCommande);
			    
			    container.setLayout(new GridLayout(2,2));
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_commande1(NumeroCommande.getText());
			    		connectionDouane.this.affiche_resultat.setText(res);
			    		NumeroCommande.setText("");
			    		connectionDouane.this.frame.dispose();
			    		connectionDouane.this.frame = null;

					}				
				});
			}     
		});
		
		
		
		
		cmd_recherche2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
						
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche parmi les commandes : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			        
				
			    destination.setPreferredSize(new Dimension(180,25));

			    container.add(label_destination);
			    container.add(destination);
			    
			    container.setLayout(new GridLayout(2,2));
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_commande2(destination.getText());
			    		connectionDouane.this.affiche_resultat.setText(res);
			    		connectionDouane.this.frame.dispose();
			    		connectionDouane.this.frame = null;
			    		destination.setText("");
					}				
				});
			}     
		});
		
		
		
		cmd_recherche3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				//confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche parmi les commandes : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			    
			    contenu.setPreferredSize(new Dimension(180,25));
			    

			    
			    container.add(label_contenu);
			    container.add(contenu);
			    container.setLayout(new GridLayout(2,2));
			        
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_commande3(contenu.getText());
						//affiche_resultat.setText(res);
						connectionDouane.this.affiche_resultat.setText(res);
						connectionDouane.this.frame.removeAll();
						connectionDouane.this.frame.dispose();
						contenu.setText("");
					}				
				});
			}     
		});
		
		
		cmd_recherche4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				connectionDouane.this.frame = new JFrame();
				container.removeAll();
				//confirmecont.removeAll();
				
				connectionDouane.this.frame.setTitle("Recherche parmi les commandes : ");
				connectionDouane.this.frame.setSize(350,150);
				connectionDouane.this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectionDouane.this.frame.setLocationRelativeTo(null);
				connectionDouane.this.frame.setResizable(false);
			    
				NumeroCommande.setPreferredSize(new Dimension(180,25));
			    
			    container.add(label_num_cmd);
			    container.add(NumeroCommande);
			    
			    container.setLayout(new GridLayout(2,2));
			    
			    confirmecont.add(confirme);
			    connectionDouane.this.frame.setLayout(new BorderLayout());
			    connectionDouane.this.frame.getContentPane().add(container,BorderLayout.NORTH);
			    connectionDouane.this.frame.getContentPane().add(confirmecont,BorderLayout.SOUTH);
			    connectionDouane.this.frame.setVisible(true);
			        
			    confirme.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent arg0) {
			    		String res = connecte.recherche_commande4(NumeroCommande.getText());
						//affiche_resultat.setText(res);
						connectionDouane.this.affiche_resultat.setText(connecte.recherche_commande4(NumeroCommande.getText()));
						connectionDouane.this.frame.removeAll();
						connectionDouane.this.frame.dispose();
					    NumeroCommande.setText("");
					}				
				});
			}     
		});
		
		controle1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event) {
        	//	resultatcontroleDouane ctrl = new resultatcontroleDouane(connecte);
        	}
		});
		
	}
	
	
	
	public static void main(String[] args){
		
		
	}
	
} 
