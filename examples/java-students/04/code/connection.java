import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
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
public class connection implements ActionListener {
	public static String qui;
	public static String code;
	public static String quoi;
	public static JButton ok;
	public static JButton annuler;
	public static JComboBox type;
	public static JPasswordField pwd;
	public static JTextField login;
	public JFrame frame;
	public JPanel panel;
	public static Statement st ;
	public static Connection conn;
	public connection() { menu();}
	public void menu(){		
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Connection");
		this.frame.setSize(600,600); //On donne une taille à notre fenêtre
		this.frame.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		this.frame.setResizable(true); //On permet le redimensionnement

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);
		JLabel messageAceuil = new JLabel("message d'acceuil");
		this.panel.add(messageAceuil);

		//this.frame.setBounds(450, 300, 500, 300);

		JLabel labelLogin = new JLabel("Login :");
		JLabel labelPwd = new JLabel("Password :");
		login = new JTextField(8);
		mdp = new JPasswordField(8);

		String[] nomType = {"gerant","douane","client","embaleur","transporteur"};
		type = new JComboBox(nomType);


  		ok = new JButton("OK");
	        ok.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(ok);
		ok.addActionListener(this);

  		annuler = new JButton("annuler");
	        annuler.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(annuler);
		annuler.addActionListener(this);

		this.panel.add(type);this.panel.add(labelLogin);this.panel.add(login);this.panel.add(labelMdp);this.panel.add(mdp);

     		this.panel.add(ok);
		ok.addActionListener(this);

     		this.panel.add(annuler);
		annuler.addActionListener(this);
		
		
		
			
		  		this.frame.setContentPane(panel);
		this.frame.setVisible(true);
	}
   public static void Connect(String login, String password) throws SQLException, ClassNotFoundException{
	try{
		Class.forName("org.postgresql.Driver");
	}catch(Exception ee){
		System.err.println("Probleme driver");
		System.exit(1);
	}			
        conn= DriverManager.getConnection("jdbc:postgresql://localhost/" + login, login, password);
        st=conn.createStatement();

    }
	public void actionPerformed(ActionEvent e) {
						this.frame.dispose();
     		if(e.getSource() == ok){

qui = login.getText();
code = new String(mdp.getPassword());
			if(type.getSelectedIndex() == 0){quoi ="gerant";}
			if(type.getSelectedIndex() == 1){quoi ="douane";}
			if(type.getSelectedIndex() == 2){quoi ="client";}
			if(type.getSelectedIndex() == 3){quoi ="embaleur";}
			if(type.getSelectedIndex() == 4){quoi ="transporteur";}
			ResultSet rss ;
			try{
				System.out.println(quoi);System.out.println(qui);System.out.println(code);
				
				rss = st.executeQuery("SELECT * FROM "+quoi+" WHERE login = '"+qui+"' AND mot_passe = '"+code+"';");
				int comp = 0;
				while(rss.next()){
					comp++;
				}
				if(comp > 0){
					if(quoi.equals("gerant")) new Gerant();
					if(quoi.equals("douane")) new Douane();
				}
				else{
					menu();
				}
			}catch(SQLException ee){
				System.err.println(ee.getMessage());
				System.exit(2);
			}

		}
		else if(e.getSource() == annuler){	
				this.frame.dispose();	
		}
	}
	public static void main(String[] args){
		String database = "infou012";
		String password = " ";
		try{
		try{
		Connect(database,password);
		}catch(SQLException ee){
			System.err.println(ee.getMessage());
			System.exit(2);
		}
		}catch(ClassNotFoundException eee){
			System.err.println(eee.getMessage());
			System.exit(2);
		}
		connection connn = new connection();
	}
}

