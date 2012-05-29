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


public class emballeur implements ActionListener{
	public JFrame frame;
	public JPanel panel;

	public static JButton deconnecter;
	public static JButton colis;
	public static JButton emballer;
	public static JButton produit;
	public static JButton precedent;
	public static JButton precedent01;
	public static JButton precedent11;
	public static JButton palette;
	public static JButton colisp;
	public static JButton emballerp;


	public static JComboBox j;
 
	public String ceque = "";

	static int n = 0;
	static int p = 0;


	public static String[] c;


	public emballeur(){	
		menu();
	}	
	public void menu(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("embaleur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		FlowLayout gl = new FlowLayout();
		gl.setHgap(1000); 
		gl.setVgap(20); 

  		panel.setLayout(gl);

 		palette = new JButton("PALETTES");
	        palette.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(palette);
		palette.addActionListener(this);

 		colis = new JButton("COLIS");
	        colis.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(colis);
		colis.addActionListener(this);

  		deconnecter = new JButton("Se déconnecter");
	        deconnecter.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(deconnecter);
		deconnecter.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}

	public void palette(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("embaleur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("liste palettes non emballees");
		this.panel.add(messageAceuil);

		try{

			ResultSet rs = connection.st.executeQuery("select num , num_mode_transport from palette where date_emballage = null or (extract(year from current_date)*100000 + extract(month from current_date)*100 + extract(day from current_date)) <= (extract(year from date_emballage)*100000 + extract(month from date_emballage)*100 + extract(day from date_emballage));");	
			int size = 0;
			while(rs.next()){
				size++;
			}
			c = new String[size + 1];
			int i = 1;
			c[0] = " NUM PALETTE | MODE TRANSPORT ";
		 	rs = connection.st.executeQuery("select num , num_mode_transport from palette where date_emballage = null or (extract(year from current_date)*100000 + extract(month from current_date)*100 + extract(day from current_date)) <= (extract(year from date_emballage)*100000 + extract(month from date_emballage)*100 + extract(day from date_emballage));");
			while(rs.next()){
				p =rs.getInt("num");
				String ddd =rs.getString("num_mode_transport");
				c[i++] = p+"|"+ddd;

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

  		colisp = new JButton("voir colis");
	        colisp.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(colisp);
		colisp.addActionListener(this);

  		precedent = new JButton("precedent");
	        precedent.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent);
		precedent.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}
	public void colisp(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("embaleur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("liste des colis de la palette");
		this.panel.add(messageAceuil);

		try{

			ResultSet rs = connection.st.executeQuery("select num from colis where num_palette = "+ceque+";");	
			int size = 0;
			while(rs.next()){
				size++;
			}
			c = new String[size + 1];
			int i = 1;
			c[0] = " NUM COLIS ";
		 	rs = connection.st.executeQuery("select num from colis where num_palette = "+ceque+";");
			while(rs.next()){
				int nn =rs.getInt("num");;
				c[i++] = nn+"";
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

  		emballerp = new JButton("emballer");
	        emballerp.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(emballerp);
		emballerp.addActionListener(this);

  		precedent11 = new JButton("precedent");
	        precedent11.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent11);
		precedent11.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
		
	}
	public void colis(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("embaleur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("liste de colis non emballee triee par client et par commande");
		this.panel.add(messageAceuil);

		try{

			ResultSet rs = connection.st.executeQuery("select cm.login_client, cl.num_commande, cl.num , cl.type_emballage from colis cl join commande cm on cl.num_commande = cm.num where cl.etat_colis = 'produit' order by cm.login_client, cl.num_commande, cl.num;");	
			int size = 0;
			while(rs.next()){
				size++;
			}
			c = new String[size + 1];
			int i = 1;
			c[0] = " CLIENT | COMMANDE | NUM COLIS | QUALIFIANT ";
		 	rs = connection.st.executeQuery("select cm.login_client, cl.num_commande, cl.num , cl.type_emballage from colis cl join commande cm on cl.num_commande = cm.num where cl.etat_colis = 'produit' order by cm.login_client, cl.num_commande, cl.num;");
			String kelClient = ""; int kelCommande=0; int nclient = 0; int ncommande = 0;
			while(rs.next()){
				n =rs.getInt("num");
				int oldKelCommande = kelCommande;
				kelCommande = rs.getInt("num_commande");
				if(oldKelCommande != kelCommande) ncommande++;
				
				String oldKelClient = kelClient;
				kelClient =rs.getString("login_client");
				if(!oldKelClient.equals(kelClient)){ nclient++; ncommande = 1;}
				String ddd =rs.getString("type_emballage");
				c[i++] = "CLIENT "+nclient+"| COMMANDE "+ncommande+"|"+n+"|"+ddd;

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

  		produit = new JButton("voir produits");
	        produit.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(produit);
		produit.addActionListener(this);

  		precedent = new JButton("precedent");
	        precedent.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent);
		precedent.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}
	
	public void produit(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("embaleur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("liste des produits du colis");
		this.panel.add(messageAceuil);

		try{

			ResultSet rs = connection.st.executeQuery("select num_produit, quantite from produit_colis where num_colis = '"+n+"';");	
			int size = 0;
			while(rs.next()){
				size++;
			}
			c = new String[size + 1];
			int i = 1;
			c[0] = " NUM PRODUIT | QUANTITE ";
		 	rs = connection.st.executeQuery("select num_produit, quantite from produit_colis where num_colis = '"+n+"';");
			while(rs.next()){
				int nn =rs.getInt("num_produit");;
				int nnn = rs.getInt("quantite");
				c[i++] = nn+" | "+nnn;

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

  		emballer = new JButton("emballer");
	        emballer.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(emballer);
		emballer.addActionListener(this);

  		precedent01 = new JButton("precedent");
	        precedent01.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent01);
		precedent01.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
		
			new connection();
	}

	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
		if(e.getSource() == deconnecter){
		}
		if(e.getSource() == palette || e.getSource() == precedent11){
			palette();
		}
		if(e.getSource() == colis){
			colis();
		}
		if(e.getSource() == colisp){

			String cequi = c[j.getSelectedIndex()];
			int fin = cequi.indexOf ("|");
			ceque = cequi.substring(0,fin);
			colisp();
		}
		if(e.getSource() == emballerp){
			try{
				connection.st.execute("update palette set date_emballage = CURRENT_DATE where num = "+ceque+";");		
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			palette();
		}
		if(e.getSource() == produit){
			String cequi = c[j.getSelectedIndex()];
			int fin = cequi.indexOf ("|");
			String cequii = cequi.substring(fin+1,cequi.length());
			fin = cequii. indexOf ("|");
			String cequiii = cequii.substring(fin+1,cequii.length());
			fin = cequiii. indexOf ("|");
			ceque = cequiii.substring(0,fin);
			produit();
		}
		if(e.getSource() == precedent){
			menu();
		}
		if(e.getSource() == precedent01){
			colis();
		}
		if(e.getSource() == emballer){

			try{
				connection.st.execute("update colis set date_emballage = CURRENT_DATE, etat_colis = 'emballe' where num = "+ceque+";");		
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			colis();
		}
		




	}


	public static void main(String[] args){
		emballeur g = new emballeur();
	}
}







