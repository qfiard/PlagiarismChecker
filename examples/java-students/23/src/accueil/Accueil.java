package accueil;


import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class Accueil extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane1;
	String id_user;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Accueil inst = new Accueil();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Accueil() {
		super();
		initGUI();
		this.setVisible(true);

	}
	
	public Accueil(String sid_user) {
		super();
		id_user = sid_user;
		initGUI();
		this.setVisible(true);
		
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
				
				
				TPInfoCommande infocommande= new TPInfoCommande(id_user);
				TPRecherche recherche= new TPRecherche(id_user);
				TPControle controle= new TPControle(id_user);
				TPRecherche stat_controle= new TPRecherche(id_user);
				TPRechercheProduit produit= new TPRechercheProduit(id_user);
				TPUpdateIdentifiant up_iden= new TPUpdateIdentifiant(id_user);
				
				jTabbedPane1.add("Information commandes", infocommande);
				jTabbedPane1.add("Recherche commandes", recherche);
				jTabbedPane1.add("Recherche produits", produit);
				jTabbedPane1.add("Entrer un contrôle", controle);
				jTabbedPane1.add("Statistiques des contrôles", stat_controle);
				jTabbedPane1.add("Changer vos identifiants", up_iden);

				
				
				
		
			}
			pack();
			this.setSize(800, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
