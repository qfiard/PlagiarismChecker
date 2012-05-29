package interfaceDouane;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JPanel;




public class FenetreDouane extends JFrame  implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	private JPanel jp = new JPanel();
	private JMenuBar menuBar;

	private JMenu commande, controle, lister, deconnexion;
	private JMenuItem menuItemPays, menuItemControle, menuItemNnControle, menuItemRech, menuItemRes, menuItemStat, menuItemCont, menuItemPal, menuItemVisual ;
	
	private JPanel panelCourant = null;

	private String id_douane;
	private String pays;

	public FenetreDouane(String id_D, String dest){
	
		
		this.setTitle("---Douane---");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		this.setResizable(false);
		
		id_douane=id_D;
		pays = dest;
		
		jp.setBackground(Color.lightGray);

		createMenuBar();

	}

	public void createMenuBar(){

		/*on cree le menu*/
		menuBar = new JMenuBar();

		/*premier menu*/
		commande = new JMenu("Commandes");
		menuBar.add(commande);

		/*premier sous-menu*/
		menuItemPays = new JMenuItem("Commandes expediees aux pays de la douane");
		menuItemPays. addActionListener(this);
		commande.add(menuItemPays);

		/*deuxieme sous-menu*/
		menuItemControle = new JMenuItem("Commandes controlees par cette douane");
		menuItemControle. addActionListener(this);	
		commande.add(menuItemControle);
		
		/*troisieme sous-menu*/
		menuItemNnControle = new JMenuItem("Commandes non controlees par cette douane");
		menuItemNnControle. addActionListener(this);	
		commande.add(menuItemNnControle);

		/*quatrieme sous-menu*/
		menuItemRech = new JMenuItem("Recherche");
		menuItemRech. addActionListener(this);
		commande.add(menuItemRech);


		/*deuxieme menu*/
		controle = new JMenu("Controle");

		/*premier sous-menu*/
		menuItemRes = new JMenuItem("Entrer resultat d'un controle");
		menuItemRes. addActionListener(this);
		controle.add(menuItemRes);

		/*deuxieme sous_menu*/
		menuItemStat = new JMenuItem("Statistiques des controles");
		menuItemStat. addActionListener(this);
		controle.add(menuItemStat);		

		menuBar.add(controle);

		
		/*troisieme menu*/
		lister = new JMenu("Lister");
		menuBar.add(lister);
		
		/*premier sous-menu*/
		menuItemVisual = new JMenuItem("Visualiser les details d'une commande");
		menuItemVisual.addActionListener(this);
		lister.add(menuItemVisual);
		
		/*deuxieme sous_menu*/
		menuItemCont = new JMenuItem("Lister les palettes d'un conteneur");
		menuItemCont.addActionListener(this);
		lister.add(menuItemCont);
		
		/*troisieme sous-menu*/
		menuItemPal = new JMenuItem("Lister les colis d'une palette");
		menuItemPal.addActionListener(this);
		lister.add(menuItemPal);
		

		/*quatrieme menu*/
		deconnexion = new JMenu("Deconnexion");
		deconnexion.addItemListener(this);
		menuBar.add(deconnexion);

		this.setJMenuBar(menuBar);

	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
	
		if(arg0.getSource() == deconnexion){
			this.dispose();
			//+FenetreConnexion2 fen = new FenetreConnexion2();
		}
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource() == menuItemPays){
			
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetrePays(id_douane, pays);
			this.setContentPane(panelCourant);
			this.validate();
			

		}
		else if(arg0.getSource() == menuItemControle){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			
			panelCourant = new FenetreControle(pays);
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemNnControle){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			
			panelCourant = new FenetreNonControle();
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemRech){
			
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			
			panelCourant = new FenetreRecherche();
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemVisual){	
			
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetreDetailCom();
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemRes){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetreResControle2(id_douane, pays);
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemStat){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetreStat();
			this.setContentPane(panelCourant);
			this.validate();
		}

		else if(arg0.getSource() == menuItemCont){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetreListeConteneur(id_douane, pays);
			this.setContentPane(panelCourant);
			this.validate();
		}
		
		else if(arg0.getSource() == menuItemPal){
			if(panelCourant != null){
				this.remove(panelCourant);
			}
			panelCourant = new FenetreListerPalette(id_douane, pays);
			this.setContentPane(panelCourant);
			this.validate();
		}



	}

	/*public static void main(String[] args) {
		FenetreDouane fd = new FenetreDouane();
	}*/

}
