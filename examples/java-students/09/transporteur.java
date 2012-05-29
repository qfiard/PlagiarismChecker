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


public class transporteur implements ActionListener{
	public JFrame frame;
	public JPanel panel;

	public static JButton deconnecter;

	public static JComboBox j;


	public static String[] c;


	public transporteur() {	
		menu();
	}	
	public void menu(){
		this.frame = new JFrame("interface graphique");
		this.panel = new JPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		this.frame.setTitle("transporteur");
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null); 
		this.frame.setResizable(true); 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		this.frame.setBounds(0,0, dimScreen.width,dimScreen.height);

		JLabel messageAceuil = new JLabel("liste de colis");
		this.panel.add(messageAceuil);

		try{

			ResultSet rs = connection.st.executeQuery("select cl.num, cm.date_livraison, cl.type_emballage from colis cl join commande cm on cl.num_commande = cm.num where cm.etat_commande = '100% expediees';;");	
			int size = 0;
			while(rs.next()){
				size++;
			}
			c = new String[size + 1];
			int i = 1;
			c[0] = " NUM COLIS | DATE LIMITE | QUALIFIANT ";
		 	rs = connection.st.executeQuery("select cl.num as num, cm.date_livraison as date_livraison, cl.type_emballage as type_emballage from colis cl join commande cm on cl.num_commande = cm.num where cm.etat_commande = '100% expediees';");
			while(rs.next()){
				int n =rs.getInt("num");
				String d =rs.getString("date_livraison");
				String dd =rs.getString("type_emballage");
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

  		deconnecter = new JButton("Se déconnecter");
	        deconnecter.setPreferredSize(new Dimension(200, 80));
     		this.panel.add(deconnecter);
		deconnecter.addActionListener(this);

  		this.frame.setContentPane(panel);

		this.frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
		if(e.getSource() == deconnecter){
			new connection();
		}



	}


	public static void main(String[] args){
		transporteur g = new transporteur();
	}
}
