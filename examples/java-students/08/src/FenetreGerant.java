import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//Cree une interface graphique pour utilisateur Gerant
public class FenetreGerant extends JFrame {
	CreationBase connecteBD;
	String login;
	String numerodemballeur;
	String date;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu_visual = new JMenu("Visualiser");
	private JMenu menu_licence = new JMenu("Licencier");
	private JMenu menu_acces = new JMenu("Lister");
	private JMenu menu_change = new JMenu("Changer");
		
	private JMenuItem it_visual7 = new JMenuItem("Produit_plus_vent");
	private JMenuItem it_visual8 = new JMenuItem("Cleint_plus_depense");
	
	private JMenuItem it_liste1 = new JMenuItem("Liste des employes");
	private JMenuItem it_liste2 = new JMenuItem("Liste des clients");
	private JMenuItem it_liste3 = new JMenuItem("Nombre de Colis 1 emballeur traite par Jour");
	
	private JMenuItem it_change = new JMenuItem("Le prix du produit");
	
	private JMenuItem it_licence = new JMenuItem("Un personnel inefficace");
	
	private JTextArea zone_affiche=new JTextArea();
	private JScrollPane zoneScrolable;
	
	
	public FenetreGerant(final CreationBase connecteBD,final String login){
		this.connecteBD=connecteBD;
		this.login=login;
		this.setTitle("Gerant");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
       
        menu_visual.add(it_visual7);menu_visual.add(it_visual8);
        
        menu_licence.add(it_licence);
        
        menu_acces.add(it_liste1);menu_acces.add(it_liste2);menu_acces.add(it_liste3);
        
        menu_change.add(it_change);
        
        it_licence.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Licencier l=new Licencier(connecteBD,login);
			}				
		});
        
        it_liste3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EmballeurTraite l=new EmballeurTraite(connecteBD,login);
			}				
		});
        
        it_change.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ChangePrix cp=new ChangePrix(connecteBD);
			}				
		});
        it_liste2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.table_client());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_liste1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete_emploi(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.plus_vendu(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.plus_depense(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        menuBar.add(menu_visual); menuBar.add(menu_licence); 
        menuBar.add(menu_acces); menuBar.add(menu_change);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
	public FenetreGerant(final CreationBase connecteBD,final String login,String numerodemballeur,String date){
		this.connecteBD=connecteBD;
		this.login=login;
		this.numerodemballeur=numerodemballeur;
		this.date=date;
		this.setTitle("Gerant");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
       
        menu_visual.add(it_visual7);menu_visual.add(it_visual8);
        
        menu_licence.add(it_licence);
        
        menu_acces.add(it_liste1);menu_acces.add(it_liste2);menu_acces.add(it_liste3);
        
        menu_change.add(it_change);
        
        it_licence.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Licencier l=new Licencier(connecteBD,login);
			}				
		});
        
        it_liste3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EmballeurTraite l=new EmballeurTraite(connecteBD,login);
			}				
		});
        
        it_change.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ChangePrix cp=new ChangePrix(connecteBD);
			}				
		});
        it_liste2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.table_client());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_liste1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete_emploi(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.plus_vendu(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_visual8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.plus_depense(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        menuBar.add(menu_visual); menuBar.add(menu_licence); 
        menuBar.add(menu_acces); menuBar.add(menu_change);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        try {
			zone_affiche.setText(connecteBD.requete_emballeur_traite(login, numerodemballeur, date));
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
