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


public class Gerant implements ActionListener{
	public JFrame frame;
	public JPanel panel;

	static int CN;

	public static JButton employes;
	public static JButton clients;
	public static JButton produits;
	public static JButton deconnecter;
	public static JButton precedent;
	public static JButton precedent12;
	public static JButton precedent13;
	public static JButton precedent22;
	public static JButton precedent32;
	public static JButton transporteurs;
   	public static JButton embaleurs;
	public static JButton listeProduitsPlusVendus;
	public static JButton listeToutProduits; 
	public static JButton modifierPrix;
	public static JButton modifierp;
	public static JComboBox j;
	public static JTextField prix; 

	public static JButton listeToutClients;
	public static JButton listeMeilleursClients;

	public static String[] t;

	public Gerant() {	
		menu();
	}	
	public void menu(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("Gerant");
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

  		produits = new JButton("Produits");
	        produits.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(produits);
		produits.addActionListener(this);

  		employes = new JButton("Employés");
	        employes.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(employes);
		employes.addActionListener(this);


  		clients = new JButton("Clients");
	        clients.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(clients);
		clients.addActionListener(this);

  		deconnecter = new JButton("Se déconnecter");
	        deconnecter.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(deconnecter);
		deconnecter.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}
	
	public void selectionProduits(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Gerant");
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
	
  			listeToutProduits = new JButton("Liste de tout les produits");
		        listeToutProduits.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(listeToutProduits);
			listeToutProduits.addActionListener(this);
	
  			listeProduitsPlusVendus = new JButton("Liste des produits les plus vendus");
		        listeProduitsPlusVendus.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(listeProduitsPlusVendus);
			listeProduitsPlusVendus.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}

	public void selectionListeToutProduits(){
		CN = 0;
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);
		JLabel messageAceuil;
		try{
		ResultSet rs = connection.st.executeQuery("select num, description, cout from produit;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		t = new String[size + 1];
		int i = 1;
		t[0] = "ID|DESCREPTION|PRIX";
		 rs = connection.st.executeQuery("select num, description, cout from produit;");
		while(rs.next()){
				int row = rs.getRow();
				String n =rs.getString("num");
				String d =rs.getString("description");
				int c =	rs.getInt("cout");

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

		modifierPrix = new JButton("modifier Prix");
	        modifierPrix.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(modifierPrix);
		modifierPrix.addActionListener(this);		

  		precedent12 = new JButton("Page précedente");
	        precedent12.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent12);
		precedent12.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);

	}
	public void selectionModifierPrix(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("composez nouveau prix");
		this.panel.add(messageAceuil);

		FlowLayout gl = new FlowLayout();
		gl.setHgap(1000);
		gl.setVgap(20); 
	
  		panel.setLayout(gl);

		prix = new JTextField(8);
		this.panel.add(prix);	

  		modifierp = new JButton("modifier");
	        modifierp.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(modifierp);
		modifierp.addActionListener(this);

  		precedent13 = new JButton("Page précedente");
	        precedent13.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent13);
		precedent13.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);

	}
	public void selectionListeProduitsPlusVendus(){ 
		CN = 1;
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("CLASSEMENT VENTE PRODUITS");
		this.panel.add(messageAceuil);

		try{
		ResultSet rs = connection.st.executeQuery("select num_produit, p.cout, sum(quantite) as somme_quantite from produit_colis pc left join produit p on pc.num_produit = p.num group by pc.num_produit,p.cout order by somme_quantite desc;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		t = new String[size + 1];
		int i = 1;
		t[0] = "RANG | NUM PRODUIT | PRIX | QUANTITE ";
		 rs = connection.st.executeQuery("select num_produit, p.cout, sum(quantite) as somme_quantite from produit_colis pc left join produit p on pc.num_produit = p.num group by pc.num_produit,p.cout order by somme_quantite desc;");
		while(rs.next()){
				int row = rs.getRow();
				String d =rs.getString("num_produit");
				int prri = rs.getInt("cout");
				int c =	rs.getInt("somme_quantite");

				t[i++] =row+"|"+d+"|"+prri+"|"+c;
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

		modifierPrix = new JButton("modifier Prix");
	        modifierPrix.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(modifierPrix);
		modifierPrix.addActionListener(this);

  		precedent12 = new JButton("Page précedente");
	        precedent12.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent12);
		precedent12.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);

	}


/**********************************************************************************************************************************************************************************************/

	public void selectionEmployes(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Gerant");
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
	
  			embaleurs = new JButton("Embaleurs");
		        embaleurs.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(embaleurs);
			embaleurs.addActionListener(this);
	
  			transporteurs = new JButton("Transporteurs");
		        transporteurs.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(transporteurs);
			transporteurs.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}
	public void selectionEmbaleurs(){ 
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);


		JLabel messageAceuil = new JLabel("LISTE TOUT LES EMBALEURS");
		this.panel.add(messageAceuil);
		try{
		ResultSet rs = connection.st.executeQuery("select login, nom, taux_erreur from embaleur;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		t = new String[size + 1];
		int i = 1;
		t[0] = "LOGIN|NOM|TAUX ERREUR";
		 rs = connection.st.executeQuery("select login, nom, taux_erreur from embaleur;");
		while(rs.next()){
				int row = rs.getRow();
				String n =rs.getString("login");
				String d =rs.getString("nom");
				int c =	rs.getInt("taux_erreur");

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

  		precedent22 = new JButton("Page précedente");
	        precedent22.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent22);
		precedent22.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	//TODO
	}
	public void selectionTransporteurs(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("LISTE DE TOUT LES TRANSPORTEURS");
		this.panel.add(messageAceuil);
		try{
		ResultSet rs = connection.st.executeQuery("select nom from transporteur;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		t = new String[size + 1];
		int i = 1;
		t[0] = "NOM";
		 rs = connection.st.executeQuery("select nom from transporteur;");
		while(rs.next()){
				int row = rs.getRow();
				String n =rs.getString("nom");

				t[i++] =n;

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

  		precedent22 = new JButton("Page précedente");
	        precedent22.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent22);
		precedent22.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	
	}
/**********************************************************************************************************************************************************************************************/
	public void selectionClients(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("Gerant");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("message d'acceuil");
			this.panel.add(messageAceuil);
	
			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20);
	
  			panel.setLayout(gl);
	
  			listeToutClients = new JButton("Liste de tout les clients");
		        listeToutClients.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(listeToutClients);
			listeToutClients.addActionListener(this);
	
  			listeMeilleursClients = new JButton("Liste des meilleurs clients");
		        listeMeilleursClients.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(listeMeilleursClients);
			listeMeilleursClients.addActionListener(this);
	
  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);

  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);	
	}



	public void selectionListeToutClients(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height); 

		JLabel messageAceuil = new JLabel("LISTE DE TOUT LES CLIENTS");
		this.panel.add(messageAceuil);
		try{
		ResultSet rs = connection.st.executeQuery("select nom_societe, pays, tel from client;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		t = new String[size + 1];
		int i = 1;
		t[0] = "NOM SOCIETE|PAYS|TEL";
		 rs = connection.st.executeQuery("select nom_societe, pays, tel from client;");
		while(rs.next()){
				int row = rs.getRow();
				String n =rs.getString("nom_societe");
				String d =rs.getString("pays");
				String c =rs.getString("tel");

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

  		precedent32 = new JButton("Page précedente");
	        precedent32.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent32);
		precedent32.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
		
	}

	public void selectionListeMeilleursClients(){ 
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Gerant");
		this.frame.setSize(600,600); 
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("10 MEILLEURS CLIENTS");
		this.panel.add(messageAceuil);
		String[] tt;
		try{
		ResultSet rs = connection.st.executeQuery("select login_client, sum(quantite * cout) as somme from ((produit_colis c left join  produit p on c.num_produit = p.num) r right join colis m on m.num = r.num_colis) x right join commande cc on x.num_commande = cc.num group by login_client order by somme desc;");	
		int size = 0;
		while(rs.next()){
			size++;
		}
		tt = new String[11];
		int i = 1;
		tt[0] = "RANG | LOGIN CLIENT | SOMME DEPENSEE";
		 rs = connection.st.executeQuery("select login_client, sum(quantite * cout) as somme from ((produit_colis c left join  produit p on c.num_produit = p.num) r right join colis m on m.num = r.num_colis) x right join commande cc on x.num_commande = cc.num group by login_client order by somme desc;");
		while(rs.next()){
				int row = rs.getRow();
				String n =rs.getString("login_client");
				int d =rs.getInt("somme");

				tt[i++] =row+" | "+n+" | "+d;
				if(i == 11) break;
		}

				j = new JComboBox(tt);
				this.panel.add(j);


		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);}
		FlowLayout gl = new FlowLayout();
		gl.setHgap(1000); 
		gl.setVgap(20); 
	
  		panel.setLayout(gl);

  		precedent32 = new JButton("Page précedente");
	        precedent32.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(precedent32);
		precedent32.addActionListener(this);
	
  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}	

/**********************************************************************************************************************************************************************************************/
	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
     		if(e.getSource() == produits || e.getSource() == precedent12){
			selectionProduits();	
		}
		else if(e.getSource() == employes || e.getSource() == precedent22){	
			selectionEmployes();
		}
		else if(e.getSource() == clients || e.getSource() == precedent32){
			selectionClients();
		}
		else if(e.getSource() == deconnecter){
			new connection();
		}
		else if(e.getSource() == precedent){
			menu();
		} 
		else if(e.getSource() == listeToutProduits){
			selectionListeToutProduits();
		}
		else if(e.getSource() == listeProduitsPlusVendus){
			selectionListeProduitsPlusVendus();
		}
		else if(e.getSource() == transporteurs){
			selectionTransporteurs();	
		}
		else if(e.getSource() == embaleurs){
			selectionEmbaleurs();
		}
		else if(e.getSource() == listeToutClients){
			selectionListeToutClients();
		}
		else if(e.getSource() == listeMeilleursClients){
			selectionListeMeilleursClients();
		}
		else if(e.getSource() == precedent13){
			selectionListeToutProduits();
		}
		else if(e.getSource() == modifierPrix){
				selectionModifierPrix();			
		}
		else if(e.getSource() == modifierp){
			if(CN == 0){
			if((prix.getText()).equals("")){selectionModifierPrix();}
			String n = t[j.getSelectedIndex()];
			int fin = n.indexOf ("|");
			String nn = n.substring(0,fin);
			try{
			connection.st.execute("update produit set cout = '"+prix.getText()+"' where num = '"+nn+"';");
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			selectionListeToutProduits();
			}
			if(CN == 1){
			if((prix.getText()).equals("")){selectionModifierPrix();}
			String n = t[j.getSelectedIndex()];
			int fin = n.indexOf ("|");
			String nn = n.substring(fin+1,n.length());
			fin = nn. indexOf ("|");
			String nnnn = nn.substring(0,fin);
			try{
			connection.st.execute("update produit set cout = '"+prix.getText()+"' where num = '"+nnnn+"';");
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			selectionListeProduitsPlusVendus();
			}
		}
}


	public static void main(String[] args){
		Gerant g = new Gerant();
	}
}
