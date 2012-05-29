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


public class client implements ActionListener{
	public JFrame frame;
	public JPanel panel;

	static LinkedList<String[]> liste = new LinkedList<String[]>();

	static JTextField ism;
	static  JPasswordField ks;

	static int max1;
	static int combiendsColis = 0;
	public static JTextField quant;
	public static JButton produitd;
	public static JButton cmdp;
	public static JButton colis;
	public static JButton deconnecter;
	public static JButton precedent;

	public static JButton fini;
	public static JButton changer;
	public static JButton ajouter;
	public static JComboBox j;
	public static JTextField prix; 


	public static String[] c;
	public static String[] p;

	public client() {	
		menu();
	}	
	public void menu(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("client");
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

  		colis = new JButton("ou est mon colis");
	       	colis.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(colis);
		colis.addActionListener(this);

  		produitd = new JButton("produit dispo !!");
	        produitd.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(produitd);
		produitd.addActionListener(this);

  		cmdp = new JButton("changer login et password");
	        cmdp.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(cmdp);
		cmdp.addActionListener(this);


  		deconnecter = new JButton("Se déconnecter");
	        deconnecter.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(deconnecter);
		deconnecter.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}
	
	public void colis(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("client");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("liste de mes colis");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select cl.num as nnn, etat_colis from colis cl left join commande cm on cl.num_commande = cm.num where cm.login_client = '"+connection.qui+"';");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				c = new String[size + 1];
				int i = 1;
				c[0] = " NUM COLIS | ETAT ";
		 		rs = connection.st.executeQuery("select cl.num as nnn, etat_colis from colis cl left join commande cm on cl.num_commande = cm.num where cm.login_client = '"+connection.qui+"';");
				while(rs.next()){
					int n =rs.getInt("nnn");
					String d =rs.getString("etat_colis");

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

	
	public void produit(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("client");
			this.frame.setSize(600,600); 
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true); 
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("liste produit disponible");
			this.panel.add(messageAceuil);
	

			try{
				ResultSet rs = connection.st.executeQuery("select num, description, poids, cout from produit where reserve > 0;");	
				int size = 0;
				while(rs.next()){
					size++;
				}
				p = new String[size + 1];
				int i = 1;
				p[0] = " NUM PRODUIT | DESCTIPTION | POIDS | PRIX ";
		 		rs = connection.st.executeQuery("select num, description, poids, cout from produit where reserve > 0;");
				while(rs.next()){
					String d =rs.getString("num");
					String dd =rs.getString("description");
					int n =rs.getInt("poids");;
					int nn =rs.getInt("cout");

					p[i++] =d+"|"+dd+"|"+n+"|"+nn;

				}	

				j = new JComboBox(p);
				this.panel.add(j);


			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}


			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);


			messageAceuil = new JLabel("composer la contite :");
		 	quant = new JTextField(8);
			this.panel.add(messageAceuil);
			this.panel.add(quant);

  			ajouter = new JButton("ajouter");
		        ajouter.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(ajouter);
			ajouter.addActionListener(this);
	
  			fini = new JButton("j'ai fini ma commande");
		        fini.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(fini);
			fini.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);

	}

	public void cmdp(){
			this.frame = new JFrame("interface graphique");
			this.panel = new JPanel();
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setTitle("client");
			this.frame.setSize(600,600);
			this.frame.setLocationRelativeTo(null); 
			this.frame.setResizable(true);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

			JLabel messageAceuil = new JLabel("changer login et mot de passe ");
			this.panel.add(messageAceuil);
	
			messageAceuil = new JLabel("composer nouveau login ");
			this.panel.add(messageAceuil);
			ism = new JTextField(8);
			this.panel.add(ism);		
			messageAceuil = new JLabel("composez nouveau mot de passe ");
			this.panel.add(messageAceuil);
			ks = new JPasswordField(8);
			this.panel.add(ks);

			FlowLayout gl = new FlowLayout();
			gl.setHgap(1000); 
			gl.setVgap(20); 
	
  			panel.setLayout(gl);

  			changer = new JButton("changer");
		        changer.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(changer);
			changer.addActionListener(this);

  			precedent = new JButton("Page précedente");
		        precedent.setPreferredSize(new Dimension(200, 80));
     			this.panel.add(precedent);
			precedent.addActionListener(this);
	
  			this.frame.setContentPane(panel);

			this.frame.setVisible(true);
	}

/**********************************************************************************************************************************************************************************************/

/**********************************************************************************************************************************************************************************************/
	
/**********************************************************************************************************************************************************************************************/
	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
     		if(e.getSource() == colis){
			colis();
		}
		else if(e.getSource() == produitd ){	
			produit();
		}
		else if(e.getSource() == deconnecter){
			new connection();
		}
		else if(e.getSource() == precedent){
			liste = new LinkedList<String[]>(); 
			menu();
		} 



		else if(e.getSource() == ajouter){
			combiendsColis++;
			int fin = (p[j.getSelectedIndex()]).indexOf("|");
			String ppp = (p[j.getSelectedIndex()]).substring(0,fin);
			String qqq = quant.getText();
			String[] pq = new String[2];
			pq[0] = ppp;
			pq[1] = qqq;
			int reserves = 0;
			try{
				ResultSet rs = connection.st.executeQuery("select reserve from produit where num = '"+ppp+"' ;");	
				rs.next(); 
				reserves = rs.getInt("reserve");

			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			if(reserves >= Integer.parseInt(qqq))
				liste.add(pq);
			else{
				JOptionPane.showConfirmDialog(null, "la quantite demandee de ce produit ne peut etre fournie", 
						"", JOptionPane.INFORMATION_MESSAGE);
			}			
		}
		else if(e.getSource() == fini){
			int maxcm = 0; int maxcl = 0;
			try{
				ResultSet rs = connection.st.executeQuery("select max(num) from commande");	
				rs.next(); 
				maxcm = rs.getInt("max")+1;
				rs = connection.st.executeQuery("select max(num) from colis");	
				rs.next(); 
				maxcl = rs.getInt("max")+1;

				rs = connection.st.executeQuery("select login from douane where pays = (select pays from client where login = '"+connection.qui+"');");				
				rs.next(); 
				String doudou = rs.getString("login");

				connection.st.execute("insert into commande values ("+maxcm+",'"+connection.qui+"', '"+doudou+"', CURRENT_DATE, CURRENT_DATE + INTERVAL'1 week', 'non expediees');");
				for(int k = 0; k < liste.size(); k++){
					int marqueur = 0;
					int oldmaxcl = maxcl;
					maxcl = maxcl + (k+1)/5; //dans un colis nous avons 5 produits max
					if(oldmaxcl != maxcl){
						rs = connection.st.executeQuery("select num_palette , count(num_palette) as somme from colis group by num_palette order by num_palette desc;");
						rs.next(); int numpal = rs.getInt("num_palette"); int numpalq = rs.getInt("somme");
						if(numpalq >= 100) numpal++;

						connection.st.execute("insert into colis values ("+maxcl+","+maxcm+",'N',null,"+numpal+",'N',null,null,'produit');");
					}
					rs = connection.st.executeQuery("select qualifiant from produit where num = "+liste.get(k)[0]+";");
					rs.next(); String qualif = rs.getString("qualifiant");
					if(qualif.equals("D")){
   						connection.st.execute("update colis set type_emballage = 'D' where num = "+maxcl+";");
						marqueur = 1;
   					}
					else if(qualif.equals("F") && marqueur == 0){
   						connection.st.execute("update colis set type_emballage = 'F' where num = "+maxcl+";");
						marqueur = 1;
   					}
					
					connection.st.execute("insert into produit_colis values ('"+liste.get(k)[0]+"',"+maxcl+", "+liste.get(k)[1]+", "+maxcm+");");
					
				}
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
			

			liste = new LinkedList<String[]>();
			menu();
			 
		}
		else if(e.getSource() == cmdp){cmdp();}
		else if(e.getSource() == changer){
				String nism = ism.getText();
				String nks = new String(ks.getPassword());


			try{
				ResultSet rs = connection.st.executeQuery("select count(*) from client where login <> '"+connection.qui+"' and login = '"+nism+"';");	
				rs.next(); int assurance = rs.getInt("count");
				if(assurance != 0 || nism.equals("")){
						JOptionPane.showConfirmDialog(null, "login existant ou incorrecte, reessayez un autre","", JOptionPane.INFORMATION_MESSAGE);	
				}
				else if(nks.equals("")){
						JOptionPane.showConfirmDialog(null, "composer un mot de passe","", JOptionPane.INFORMATION_MESSAGE);	
				}
				else{
					connection.st.execute("update client set login = '"+nism+"', mdp = '"+nks+"' where login = '"+connection.qui+"';");	
					menu();
				}	
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);}
				

			}

}


	public static void main(String[] args){
		client g = new client();
	}
}
