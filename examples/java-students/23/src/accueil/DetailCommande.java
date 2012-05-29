package accueil;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import bdd.Acces_bdd;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class DetailCommande extends javax.swing.JFrame {

	String id_user = "";
	String id_commande = "";
	private JTextPane jTextPane1;
	private JLabel jLabel1;
	String[][] mobj = null;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DetailCommande inst = new DetailCommande();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public DetailCommande() {
		super();
		initGUI();
	}

	public DetailCommande(String sid_user, String sid_commande) {
		super();
		id_user = sid_user;
		id_commande = sid_commande;
		initGUI();
		this.setVisible(true);
		
		Acces_bdd abdd = new Acces_bdd();
		
		mobj = abdd.detail_commande(id_user, id_commande);		
		
		String resulttmp ="";
		for(int i = 0; mobj[0][i] != null ; i++){
			System.out.print("String[][] : ");
			for(int k = 0; mobj[k][i] != null ; k++){
				System.out.print( mobj[k][i] + "\t");
				resulttmp = resulttmp + mobj[k][i] + "\t";
			}
			
			resulttmp = resulttmp + "\n";
			
					
		}
		
		jTextPane1.setText(resulttmp);
		System.out.print( "\n");
		
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1, BorderLayout.CENTER);
			}
			{

			}
			pack();
			this.setSize(600, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
