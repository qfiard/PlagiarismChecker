import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;

public class Douane implements ActionListener{
	public JFrame frame;
	public JPanel panel;

	private static JButton listeToutesCommandes;
	private static JButton listeCommandes;
	private static JButton listeToutesCommandesNC;
	private static JButton recherche;
	private static JButton Statistiques;
	private static JButton precedent;
	private static JButton precedent01;
	private static JButton precedent02;
	private static JButton precedent03;
	private static JButton deconnecter;
	private static JButton voirCommande;
	private static JButton controlColis;
	private static JButton refuser;
	private static JButton accepter;

	public static JTextField motif;	

	private static JButton lproduit;
	public static String[] t;
	public static String[] c;
	public static String[] p;

	public static String idCommande;
	public static String idColis;

	static int CN;

	public static JComboBox j;

	public Douane() {
		menu();
	}
	public void menu(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("Douane");
		this.frame.setSize(1000,1000); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);
		try{
		
		ResultSet rrr = connection.st.executeQuery("select pays from douane where login = '"+connection.qui+"';");
		rrr.next();	
		JLabel messageAceuil = new JLabel("DOUANE "+rrr.getString("pays"));
		this.panel.add(messageAceuil);

		}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
		FlowLayout gl = new FlowLayout();
		gl.setHgap(1000); 
		gl.setVgap(20); 

  		panel.setLayout(gl);

  		listeToutesCommandes = new JButton("commandes expédiées au pays");
	        listeToutesCommandes.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(listeToutesCommandes);
		listeToutesCommandes.addActionListener(this);

  		listeCommandes = new JButton("commandes controlées");
	        listeCommandes.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(listeCommandes);
		listeCommandes.addActionListener(this);


  		listeToutesCommandesNC = new JButton("commandes expédiées non controlées");
	        listeToutesCommandesNC.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(listeToutesCommandesNC);
		listeToutesCommandesNC.addActionListener(this);

  		recherche = new JButton("Recherche commande");
	        recherche.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(recherche);
		recherche.addActionListener(this);

  		Statistiques = new JButton("Statistiques sur les controles");
	        Statistiques.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(Statistiques);
		Statistiques.addActionListener(this);

  		deconnecter = new JButton("Se déconnecter");
	        deconnecter.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(deconnecter);
		deconnecter.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}



		public void selectionStatistiques(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("STATISTIQUES");
			this.panel.add(messageAceuil);
	
			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
			int total = 0;
			int cont = 0;
			int ncont = 0;
			try{
				ResultSet rs = connection.st.executeQuery("select COUNT(*) from colis C where est_controle = 'N' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");
				rs.next(); ncont = rs.getInt("count");
				rs = connection.st.executeQuery("select COUNT(*) from colis C where est_controle = 'Y' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");
				rs.next(); cont = rs.getInt("COUNT");
				rs = connection.st.executeQuery("select COUNT(*) from colis C where ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");
				rs.next(); total = rs.getInt("COUNT");
				
  			panel.setLayout(gl);
			messageAceuil = new JLabel("colis controlés : "+((double)cont/(double)total)*100.0+"%");
			this.panel.add(messageAceuil);

			messageAceuil = new JLabel("colis non controlés : "+((double)ncont/(double)total)*100.0+"%");
			this.panel.add(messageAceuil);


  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);
	


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

	}	
		public void selectionRecherche(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("RECHERCHE");
			this.panel.add(messageAceuil);
	
			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);
			//pas fait
  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}

		public void selectionListeToutesCommandesNC(){
			CN = 1;
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("COMMANDE NON CONTROLEE");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select num, num_commande from colis C where est_controle = 'N' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				t = new String[size + 1];
				int i = 1;
				t[0] = "NUM COLIS| NUM COMMANDE";
		 		rs = connection.st.executeQuery("select num, num_commande from colis C where est_controle = 'N' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");
				while(rs.next()){
					int row = rs.getRow();
					int n =rs.getInt("num");
					String d =rs.getString("num_commande");

					t[i++] =n+"|"+d;
				}	

				j = new JComboBox(t);
				this.panel.add(j);


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);
	
			voirCommande = new JButton("voir commande");
		        voirCommande.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(voirCommande);
			voirCommande.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);


		}
		public void voirCommande(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("LISTE DE COLIS DE LA COMMANDE");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select num, est_controle, etat_colis from colis where num_commande = '"+idCommande+"';");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				c = new String[size + 1];
				int i = 1;
				c[0] = "NUM COLIS | CONTROLER | ARRIVER A LA DOUANE";
		 		rs = connection.st.executeQuery("select num, est_controle, etat_colis from colis where num_commande = '"+idCommande+"' ;");
				while(rs.next()){
					int row = rs.getRow();
					int n =rs.getInt("num");
					String d =rs.getString("est_controle");
					String dd =rs.getString("etat_colis");
					if(dd.equals("expedie")) dd = "OUI";
					else dd = "NON";

					c[i++] =n+"|"+d+"|"+dd;
				}	

				j = new JComboBox(c);
				this.panel.add(j);


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);
	
			controlColis = new JButton("controler colis");
		        controlColis.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(controlColis);
			controlColis.addActionListener(this);

  			lproduit = new JButton("liste des produits");
		        lproduit.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(lproduit);
			lproduit.addActionListener(this); 

  			precedent01 = new JButton("Page précedente");
		        precedent01.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent01);
			precedent01.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);
		}
		

		public void controlColis(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600);
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("CONTOLE");
			this.panel.add(messageAceuil);



			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20);
	
  			panel.setLayout(gl);

			accepter = new JButton("accepter");
		        accepter.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(accepter);
			accepter.addActionListener(this);

			refuser = new JButton("renvoyer");
		        refuser.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(refuser);
			refuser.addActionListener(this);

			JLabel mmm = new JLabel("motif");
			this.panel.add(mmm);
			motif = new JTextField(50);
			this.panel.add(motif);

  			precedent03 = new JButton("Page précedente");
		        precedent03.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent03);
			precedent03.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);
		}

		public void lproduit(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600);
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("LISTE DES PRODUITS DU COLIS");
			this.panel.add(messageAceuil);
	
			try{
				ResultSet rs = connection.st.executeQuery("select num_produit, quantite from produit_colis where num_colis = '"+idColis+"';");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				p = new String[size + 1];
				int i = 1;
				p[0] = "NUM PRODUIT | QUANTITE ";
		 		rs = connection.st.executeQuery("select num_produit, quantite from produit_colis where num_colis = '"+idColis+"';");
				while(rs.next()){
					int row = rs.getRow();
					int n =rs.getInt("quantite");
					String d =rs.getString("num_produit");

				p[i++] =d+"|"+n;
				}	
				for(int k=0; k<p.length;k++){
					JLabel mar = new JLabel(p[k]+"\n");
					this.panel.add(mar);
				}


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

			

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20);
	
  			panel.setLayout(gl);

  			precedent03 = new JButton("Page précedente");
		        precedent03.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent03);
			precedent03.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);


		}


		public void selectionListeToutesCommandes(){
			CN = 0;
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600);
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("LISTE DE TOUTES LES COMMANDES");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select num, login_client ,etat_commande from commande Co where (etat_commande = '100% expediees' or etat_commande = 'partialement expediees') and  ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L where Co.login_client = L.login));");	


				int size = 0;
				while(rs.next()){
					size++;
				}
				t = new String[size + 1];
				int i = 1;
				t[0] = "NUM COMMANDE | LOGIN CLIENT | ETAT COMMANDE ";
		 		rs = connection.st.executeQuery("select num, login_client ,etat_commande from commande Co where (etat_commande = '100% expediees' or etat_commande = 'partialement expediees') and  ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L where Co.login_client = L.login));");
				while(rs.next()){
					int row = rs.getRow();
					int n =rs.getInt("num");
					String d =rs.getString("login_client");
					String c =rs.getString("etat_commande");

					t[i++] =n+"|"+d+"|"+c;
				}	

				j = new JComboBox(t);
				this.panel.add(j);


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);

			voirCommande = new JButton("voir commande");
		        voirCommande.setPreferredSize(new Dimension(200, 80));
	     		this.panel.add(voirCommande);
			voirCommande.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}
	public void selectionListeCommandes(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Douane");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("LISTE DE COMMANDES CONTROLEES");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select num, num_commande from colis C where est_controle = 'Y' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				c = new String[size + 1];
				int i = 1;
				c[0] = "NUM COLIS | NUM COMMANDE ";
		 		rs = connection.st.executeQuery("select num, num_commande from colis C where est_controle = 'Y' AND ((select pays from douane where login = '"+connection.qui+"') =ALL (select distinct pays from client as L join commande as Co on L.login = Co.login_client where C.num_commande = Co.num)) ;");
				while(rs.next()){
					int row = rs.getRow();
					int n =rs.getInt("num");
					int d =rs.getInt("num_commande");
					c[i++] =n+"|"+d;
				}	

				j = new JComboBox(c);
				this.panel.add(j);


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000);
			gl.setVgap(20);
	
  			panel.setLayout(gl);
	
  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}
/*************************************************************************************************************************************************************/
/*************************************************************************************************************************************************************/
/*************************************************************************************************************************************************************/
	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
     		if(e.getSource() == listeCommandes /*|| e.getSource() == precedent12*/){
			selectionListeCommandes();	
		}
		else if(e.getSource() == listeToutesCommandes /*|| e.getSource() == precedent22*/){	
			selectionListeToutesCommandes();
		}
		else if(e.getSource() == listeToutesCommandesNC /*|| e.getSource() == precedent32*/){
			selectionListeToutesCommandesNC();
		}
		else if(e.getSource() == deconnecter){
			new connection();
		}
		else if(e.getSource() == precedent){
			menu();
		} 
		else if(e.getSource() == recherche){
			selectionRecherche();
		}
		else if(e.getSource() == Statistiques){
			selectionStatistiques();
		}
		else if(e.getSource() == voirCommande){
			String nnn = t[j.getSelectedIndex()];
			int fin = nnn.indexOf ("|");
			idCommande = nnn.substring(0,fin);
			voirCommande();
		}
		else if(e.getSource() == controlColis){
			controlColis();
		}
		else if(e.getSource() == precedent01){
			if(CN == 0)
				selectionListeToutesCommandes();
			if(CN == 1)
				selectionListeToutesCommandesNC();
		}
		else if(e.getSource() == precedent02 || e.getSource() == precedent03  ){
			voirCommande();
		}
		else if(e.getSource() == controlColis){
			controlColis();
		}
		else if(e.getSource() == accepter){
			String nnn = c[j.getSelectedIndex()];
			int fin = nnn.indexOf ("|");
			idColis = nnn.substring(0,fin);
			try{
		 		connection.st.execute("update colis set etat_colis = 'expedie', est_controle = 'Y' ,resultat_controle = 'A', motif = '"+motif.getText()+"' where num = "+idColis+" ;");
				MAJ();

			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			
			voirCommande();
		}
		else if(e.getSource() == refuser){
			String nnn = c[j.getSelectedIndex()];
			int fin = nnn.indexOf ("|");
			idColis = nnn.substring(0,fin);
			try{
		 		connection.st.execute("update colis set est_controle = 'Y' ,resultat_controle = 'R', motif = '"+motif.getText()+"' where num = "+idColis+" ;");
				MAJ();

			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			
			voirCommande();
		}
		else if(e.getSource() == lproduit){
			String nnn = c[j.getSelectedIndex()];
			int fin = nnn.indexOf ("|");
			idColis = nnn.substring(0,fin);
			lproduit();
		}
}

public void MAJ(){
	try{
	ResultSet rs = connection.st.executeQuery("update commande C set etat_commande = 'partialement expediees' where 'expedie' =ANY (select etat_colis from colis where num_commande = C.num) AND 'expedie' <>ANY (select etat_colis from colis where num_commande = C.num);");	
	rs = connection.st.executeQuery("update commande C set etat_commande = '100% expediees' where 'expedie' =ALL (select etat_colis from colis where num_commande = C.num);");
	rs = connection.st.executeQuery("update commande C set etat_commande = 'non expediees' where 'expedie' <>ALL (select etat_colis from colis where num_commande = C.num);");
	}catch(SQLException ee){
		System.err.println(ee.getMessage());
		System.exit(2);
	}
}

	public static void main(String[] args){
		Douane g = new Douane();
	}
}
