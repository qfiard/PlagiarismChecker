


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextField;
 
/**
 * Bouton change password
 * @author Marc
 *
 */
public class change_passwd extends JFrame implements ActionListener{
    
	private static final long serialVersionUID = 1L;
		private JButton b_validation = new JButton("Valider mes changement");
        private JLabel l_old_pass = new JLabel("Ancien Mot de Passe :");
        private JTextField jtf_old_pass = new JTextField();
        private JLabel l_new_pass = new JLabel("Nouveau Mot de Passe");
        private JTextField jtf_new_pass = new JTextField();
        private String s_name;

        
        private String s_oldpass;
        private String s_newpass;
        public static Connection connection;
    	private Connection 		con;
    	private Statement       sql;
    	private DatabaseMetaData dbmd;
        
        
        public change_passwd(String name) {
			s_name=name;
		}


		public void actionPerformed(ActionEvent arg0) {
        		format_window();
        		System.out.println("Changement du mot depasse de l'utilisateur :"+s_name);

                jtf_old_pass.setPreferredSize(new Dimension(150, 30));
                jtf_new_pass.setPreferredSize(new Dimension(150, 30));
                
                
                b_validation.addActionListener(new BoutonListener());
                
                JPanel pan = new JPanel();
                //Ajout du bouton � notre contentPane
                pan.add(l_old_pass);
                pan.add(jtf_old_pass);
                pan.add(l_new_pass);
                pan.add(jtf_new_pass);
                pan.add(b_validation);
                
                this.setContentPane(pan);
                this.setVisible(true);
                
        }       
	
        
        private void format_window() {
            this.setTitle("Changement du mot de passe");
            this.setSize(350, 130);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setAlwaysOnTop(true);
            this.setBackground(Color.white);
            this.setLayout(new BorderLayout());
        }
        
        
        
        
        class BoutonListener  implements ActionListener{

            public void actionPerformed(ActionEvent arg0) {   

            	s_oldpass= jtf_old_pass.getText();
            	s_newpass = jtf_new_pass.getText();
            	System.out.println("l'ancien mot depasse est : " +jtf_old_pass.getText()+ " et le nouveau mot de passe est : "+jtf_new_pass.getText()  );
            	
            	try {
    				database_connection con_3 = new database_connection();

    				sql = con_3.getConn().createStatement();
    				
    				ResultSet rs1 = sql.executeQuery("SELECT password from user_db where login = '"+s_name+"';");
    				
    				if(rs1.next())
    				{
    					
    				  // la requete a trouve d'enregistrement
    					String pass = (rs1.getString("password")).trim();
    					
    				  if( s_oldpass.equals(pass) ){
    				
    					  ResultSet rs2 = sql.executeQuery("UPDATE user_db set password = '"+s_newpass+"' where login='"+s_name+"';");

      				  }else{
      					  System.out.println("erreur dans l'ancien mot depasse");
      				  }
    				}
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				//System.out.println("erreur dans la connection � labase de donnee");
    			}

				  dispose();
            	
            }
        }

}
