

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

//Cree une interface graphique pour utilisateur Transporteur
public class FenetreTransporteur extends JFrame {
	CreationBase connecteBD;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Menu");
	
		
	private JMenuItem it1 = new JMenuItem("Type de produit");
	private JMenuItem it2 = new JMenuItem("Contenu d'un colis");
	private JMenuItem it3 = new JMenuItem("Date de livraison");
	
	
	private JTextArea zone_affiche=new JTextArea("Zone Affiche.....");
	private JScrollPane zoneScrolable;
	
	
	public FenetreTransporteur(final CreationBase connecteBD){
		this.connecteBD=connecteBD;
		this.setTitle("Transporteur");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        
        menu.add(it1); menu.add(it2); menu.add(it3);
        
        it3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					zone_affiche.setText(connecteBD.table_client());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		});
        
        menuBar.add(menu);
        
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


