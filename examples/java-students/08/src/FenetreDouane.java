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

//Cree une interface graphique pour utilisateur Douane
public class FenetreDouane extends JFrame {
	CreationBase connecteBD;
	String login;
	int numerodecommande;
	String numeroduproduit;
	String destination;
	String numerodelapalette;
	String numeroducolis;
	
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu_visual = new JMenu("Visualiser");
	private JMenu menu_recherche = new JMenu("Rechercher");
	private JMenu menu_result = new JMenu("Resultat_controle");
	private JMenu menu_stat = new JMenu("Statistiques");
	private JMenu menu_autre = new JMenu("Autres");
		
	private JMenuItem it_visual1 = new JMenuItem("Commandes expedies au pays de la douane");
	private JMenuItem it_visual2 = new JMenuItem("Commandes controlees par cette douane");
	private JMenuItem it_visual3 = new JMenuItem("Commandes expedies au pays de la douane ms non controle");
	
	private JMenuItem it_recherche1 = new JMenuItem("Commandes par numero");
	private JMenuItem it_recherche2 = new JMenuItem("Commandes par destination");
	private JMenuItem it_recherche3 = new JMenuItem("Commandes par contenu");
	
	private JMenuItem it_result = new JMenuItem("Affiche resultat");
	
	private JMenuItem it_stat = new JMenuItem("Affiche statistiques sur les controles");
	
	private JMenuItem it_autre2 = new JMenuItem("Lister les colis d'une palette");
	private JMenuItem it_autre3 = new JMenuItem("Lister les produits d'un colis");
	private JMenuItem it_autre4 = new JMenuItem("Prix produit de transport");
	
	private JTextArea zone_affiche=new JTextArea("Zone Affiche.....");
	private JScrollPane zoneScrolable;
	
	
	public FenetreDouane(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		
		this.setTitle("Douane");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        
        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
        
        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
        menu_recherche.add(it_recherche3);
        
        menu_result.add(it_result);
        menu_stat.add(it_stat);
        
        menu_autre.add(it_autre2);menu_autre.add(it_autre3);
        menu_autre.add(it_autre4);
        
        it_visual1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete1(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete2(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        it_visual3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete3(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_result.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					EntrerResultat e=new EntrerResultat(connecteBD,login);
				
			}				
		});
        it_stat.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete7(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_recherche1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
				RechercheCommande c=new RechercheCommande(connecteBD,login);
			}				
		});
        it_recherche2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheDest d=new RechercheDest(connecteBD,login);
			}				
		});
        it_recherche3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
			}				
		});
        it_autre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Palette l=new Palette(connecteBD,login);
			}				
		});
        it_autre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Colis l=new Colis(connecteBD,login);
			}				
		});
        it_autre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PrixProduit l=new PrixProduit(connecteBD,login);
			}				
		});
        
        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
        menuBar.add(menu_result); menuBar.add(menu_stat);
        menuBar.add(menu_autre);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
	public FenetreDouane(final CreationBase connecteBD,final String login,final int numerodecommande ){
		this.connecteBD=connecteBD;
		this.login=login;
		this.numerodecommande=numerodecommande;
		
		
		this.setTitle("Douane");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        it_visual1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete1(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete2(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        it_visual3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete3(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_result.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					EntrerResultat e=new EntrerResultat(connecteBD,login);
				
			}				
		});
        it_stat.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete7(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_recherche1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
				RechercheCommande c=new RechercheCommande(connecteBD,login);
			}				
		});
        it_recherche2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheDest d=new RechercheDest(connecteBD,login);
			}				
		});
        it_recherche3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
			}				
		});
        it_autre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Palette l=new Palette(connecteBD,login);
			}				
		});
        it_autre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Colis l=new Colis(connecteBD,login);
			}				
		});
        it_autre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PrixProduit l=new PrixProduit(connecteBD,login);
			}				
		});
        
        
        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
   
        
        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
        menu_recherche.add(it_recherche3);
        
        menu_result.add(it_result);
        menu_stat.add(it_stat);
        
        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
       
        
       
        
        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
        menuBar.add(menu_result); menuBar.add(menu_stat);
        menuBar.add(menu_autre);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        try {
			zone_affiche.setText(connecteBD.requete5_commande(login, numerodecommande));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
	public FenetreDouane(final CreationBase connecteBD,final String login,final String destination ){
		this.connecteBD=connecteBD;
		this.login=login;
		this.destination=destination;
		
		
		this.setTitle("Douane");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        it_visual1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete1(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete2(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        it_visual3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete3(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_result.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					EntrerResultat e=new EntrerResultat(connecteBD,login);
				
			}				
		});
        it_stat.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete7(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_recherche1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
				RechercheCommande c=new RechercheCommande(connecteBD,login);
			}				
		});
        it_recherche2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheDest d=new RechercheDest(connecteBD,login);
			}				
		});
        it_recherche3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
			}				
		});
        it_autre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Palette l=new Palette(connecteBD,login);
			}				
		});
        it_autre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Colis l=new Colis(connecteBD,login);
			}				
		});
        it_autre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PrixProduit l=new PrixProduit(connecteBD,login);
			}				
		});
        
        
        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
   
        
        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
        menu_recherche.add(it_recherche3);
        
        menu_result.add(it_result);
        menu_stat.add(it_stat);
        
        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
       
        
       
        
        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
        menuBar.add(menu_result); menuBar.add(menu_stat);
        menuBar.add(menu_autre);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        try {
			zone_affiche.setText(connecteBD.requete5_dest(login, destination));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
	public FenetreDouane(final String login,final String numeroduproduit,final CreationBase connecteBD ){
		this.connecteBD=connecteBD;
		this.login=login;
		this.numeroduproduit=numeroduproduit;
		
		
		this.setTitle("Douane");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        it_visual1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete1(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete2(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        it_visual3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete3(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_result.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					EntrerResultat e=new EntrerResultat(connecteBD,login);
				
			}				
		});
        it_stat.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete7(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_recherche1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
				RechercheCommande c=new RechercheCommande(connecteBD,login);
			}				
		});
        it_recherche2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheDest d=new RechercheDest(connecteBD,login);
			}				
		});
        it_recherche3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		setVisible(false);
        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
			}				
		});
        it_autre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Palette l=new Palette(connecteBD,login);
			}				
		});
        it_autre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Colis l=new Colis(connecteBD,login);
			}				
		});
        it_autre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PrixProduit l=new PrixProduit(connecteBD,login);
			}				
		});
        
        
        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
   
        
        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
        menu_recherche.add(it_recherche3);
        
        menu_result.add(it_result);
        menu_stat.add(it_stat);
        
        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
       
        
       
        
        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
        menuBar.add(menu_result); menuBar.add(menu_stat);
        menuBar.add(menu_autre);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        try {
			zone_affiche.setText(connecteBD.requete5_contenu(login, numeroduproduit));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
	public FenetreDouane(final CreationBase connecteBD,final String login,String objet,String type ){
		if(type.equals("palette")){
			this.connecteBD=connecteBD;
			this.login=login;
			this.numerodelapalette=objet;
			
			
			this.setTitle("Douane");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
	        this.setLayout(new BorderLayout());
	        
	        it_visual1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete1(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_visual2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete2(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        
	        it_visual3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete3(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_result.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
						EntrerResultat e=new EntrerResultat(connecteBD,login);
					
				}				
			});
	        it_stat.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete7(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_recherche1.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
					RechercheCommande c=new RechercheCommande(connecteBD,login);
				}				
			});
	        it_recherche2.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheDest d=new RechercheDest(connecteBD,login);
				}				
			});
	        it_recherche3.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
				}				
			});
	        it_autre2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Palette l=new Palette(connecteBD,login);
				}				
			});
	        it_autre3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Colis l=new Colis(connecteBD,login);
				}				
			});
	        it_autre4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					PrixProduit l=new PrixProduit(connecteBD,login);
				}				
			});
	        
	        
	        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
	   
	        
	        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
	        menu_recherche.add(it_recherche3);
	        
	        menu_result.add(it_result);
	        menu_stat.add(it_stat);
	        
	        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
	       
	        
	       
	        
	        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
	        menuBar.add(menu_result); menuBar.add(menu_stat);
	        menuBar.add(menu_autre);
	        
	        this.setBounds(0,0,800,700);
	        zone_affiche.setEditable(false);
	        try {
				zone_affiche.setText(connecteBD.requete_palette(login, numerodelapalette));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Font police=new Font("Monospaced",Font.PLAIN,12);
	        zone_affiche.setFont(police);
	        
	        zoneScrolable=new JScrollPane(zone_affiche);
	        this.setLayout(new BorderLayout());
	        this.add(zoneScrolable,BorderLayout.CENTER);
	        this.setJMenuBar(menuBar);
		}
		else if(type.equals("colis")){
			this.connecteBD=connecteBD;
			this.login=login;
			this.numeroducolis=objet;
			
			
			this.setTitle("Douane");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
	        this.setLayout(new BorderLayout());
	        
	        it_visual1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete1(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_visual2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete2(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        
	        it_visual3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete3(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_result.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
						EntrerResultat e=new EntrerResultat(connecteBD,login);
					
				}				
			});
	        it_stat.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete7(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_recherche1.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
					RechercheCommande c=new RechercheCommande(connecteBD,login);
				}				
			});
	        it_recherche2.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheDest d=new RechercheDest(connecteBD,login);
				}				
			});
	        it_recherche3.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
				}				
			});
	        it_autre2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Palette l=new Palette(connecteBD,login);
				}				
			});
	        it_autre3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Colis l=new Colis(connecteBD,login);
				}				
			});
	        it_autre4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					PrixProduit l=new PrixProduit(connecteBD,login);
				}				
			});
	        
	        
	        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
	   
	        
	        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
	        menu_recherche.add(it_recherche3);
	        
	        menu_result.add(it_result);
	        menu_stat.add(it_stat);
	        
	        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
	       
	        
	       
	        
	        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
	        menuBar.add(menu_result); menuBar.add(menu_stat);
	        menuBar.add(menu_autre);
	        
	        this.setBounds(0,0,800,700);
	        zone_affiche.setEditable(false);
	        try {
				zone_affiche.setText(connecteBD.requete_colis(login,numeroducolis));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Font police=new Font("Monospaced",Font.PLAIN,12);
	        zone_affiche.setFont(police);
	        
	        zoneScrolable=new JScrollPane(zone_affiche);
	        this.setLayout(new BorderLayout());
	        this.add(zoneScrolable,BorderLayout.CENTER);
	        this.setJMenuBar(menuBar);
		}
		else if(type.equals("produit")){
			this.connecteBD=connecteBD;
			this.login=login;
			this.numeroduproduit=objet;
			
			
			this.setTitle("Douane");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
	        this.setLayout(new BorderLayout());
	        
	        it_visual1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete1(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_visual2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete2(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        
	        it_visual3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete3(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_result.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
						EntrerResultat e=new EntrerResultat(connecteBD,login);
					
				}				
			});
	        it_stat.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
					try {
						zone_affiche.setText(connecteBD.requete7(login));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			});
	        it_recherche1.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
					RechercheCommande c=new RechercheCommande(connecteBD,login);
				}				
			});
	        it_recherche2.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheDest d=new RechercheDest(connecteBD,login);
				}				
			});
	        it_recherche3.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		setVisible(false);
	        		RechercheContenu ct=new RechercheContenu(connecteBD,login);
				}				
			});
	        it_autre2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Palette l=new Palette(connecteBD,login);
				}				
			});
	        it_autre3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Colis l=new Colis(connecteBD,login);
				}				
			});
	        it_autre4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					PrixProduit l=new PrixProduit(connecteBD,login);
				}				
			});
	        
	        
	        menu_visual.add(it_visual1); menu_visual.add(it_visual2); menu_visual.add(it_visual3);
	   
	        
	        menu_recherche.add(it_recherche1);menu_recherche.add(it_recherche2);
	        menu_recherche.add(it_recherche3);
	        
	        menu_result.add(it_result);
	        menu_stat.add(it_stat);
	        
	        menu_autre.add(it_autre2);menu_autre.add(it_autre3); menu_autre.add(it_autre4);
	       
	        
	       
	        
	        menuBar.add(menu_visual); menuBar.add(menu_recherche); 
	        menuBar.add(menu_result); menuBar.add(menu_stat);
	        menuBar.add(menu_autre);
	        
	        this.setBounds(0,0,800,700);
	        zone_affiche.setEditable(false);
	        try {
				zone_affiche.setText(connecteBD.requete_prixproduit(login,numeroduproduit));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Font police=new Font("Monospaced",Font.PLAIN,12);
	        zone_affiche.setFont(police);
	        
	        zoneScrolable=new JScrollPane(zone_affiche);
	        this.setLayout(new BorderLayout());
	        this.add(zoneScrolable,BorderLayout.CENTER);
	        this.setJMenuBar(menuBar);
		}
		
        
	}
}
