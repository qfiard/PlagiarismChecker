package interfaceGerant;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FenetreGerant extends JFrame{

	private static final long serialVersionUID = 1L;

	private JTabbedPane onglets;
	
	public FenetreGerant(String idGerant){
		
		this.setSize(800, 600);
		this.setTitle("--Gerant--");
		
		
		onglets = new JTabbedPane();
		onglets.addTab("Produits", new FenetreProduits());
		onglets.addTab("Emballeurs", new FenetreEmballeur());
		onglets.addTab("Clients", new FenetreClient());
		onglets.addTab("Colis", new FenetreColis());
		
		this.setContentPane(onglets);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args){
		FenetreGerant fg = new FenetreGerant("PV738");
	}
	
}
