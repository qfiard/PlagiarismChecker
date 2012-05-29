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

//Cree une interface graphique pour utilisateur Client
public class FenetreClient extends JFrame {
	CreationBase connecteBD;
	String login;
	String mdp;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu_suivre = new JMenu("Suivre_Colis");
	private JMenu menu_lister = new JMenu("Lister");
	private JMenu menu_choisir = new JMenu("Choisir");
	private JMenu menu_changer = new JMenu("Changer");
	
	private JMenuItem it_suivre = new JMenuItem("Etat mon colis");
	private JMenuItem it_lister = new JMenuItem("Produits disponibles");
	private JMenuItem it_choisir = new JMenuItem("Date de livraison");
	private JMenuItem it_changer = new JMenuItem("Mot de passe");
	
	private JTextArea zone_affiche=new JTextArea("Zone Affiche.....");
	private JScrollPane zoneScrolable;
	
	
	public FenetreClient(final CreationBase connecteBD,final String login,final String mdp){
		this.connecteBD=connecteBD;
		this.login=login;
		this.mdp=mdp;
		this.setTitle("Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        menu_suivre.add(it_suivre);
        menu_lister.add(it_lister);
        menu_choisir.add(it_choisir);
        menu_changer.add(it_changer);
        
        it_suivre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.requete_suivi(login));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        it_changer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				ChangePassword cp=new ChangePassword(connecteBD,login,mdp);
			}				
		});
        it_choisir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				DateLivraison d=new DateLivraison(connecteBD,login);
			}				
		});
        it_lister.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.table_produit_dispo());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        menuBar.add(menu_suivre); menuBar.add(menu_lister); 
        menuBar.add(menu_choisir); menuBar.add(menu_changer);
        
        this.setBounds(0,0,800,700);
        zone_affiche.setEditable(false);
        Font police=new Font("Monospaced",Font.PLAIN,12);
        zone_affiche.setFont(police);
        
        zoneScrolable=new JScrollPane(zone_affiche);
        this.setLayout(new BorderLayout());
        this.add(zoneScrolable,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
	}
}
