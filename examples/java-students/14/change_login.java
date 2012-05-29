
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
import javax.swing.JPanel;
import javax.swing.JTextField;
 
 
public class change_login extends JFrame implements ActionListener{
        
	private static final long serialVersionUID = 1L;
		private JButton b_validation = new JButton("Valider mes changement");
        private JLabel l_old_login = new JLabel("Ancien Login :");
        private JTextField jtf_old_login = new JTextField();
        private JLabel l_new_login = new JLabel("Nouveau Login");
        private JTextField jtf_new_login = new JTextField();
        private String s_login;
        private String s_oldlogin;
        private String s_newlogin;
        public static Connection connection;
    	private Connection 		con;
    	private Statement       sql;
    	private DatabaseMetaData dbmd;
    	
    	
    	
        public change_login(String login) {
			s_login=login;
		}
        
        private void format_window() {
            this.setTitle("Changement du Login");
            this.setSize(288, 130);
           // this.setDefaultCloseOperation(dispose());
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setAlwaysOnTop(true);
            this.setBackground(Color.white);
            this.setLayout(new BorderLayout());
        }
        
        
        public void actionPerformed(ActionEvent arg0) {
                
        
        	format_window();
                jtf_old_login.setPreferredSize(new Dimension(150, 30));
                jtf_new_login.setPreferredSize(new Dimension(150, 30));
                
                
                b_validation.addActionListener(new BoutonListener());
                
                JPanel pan = new JPanel();
                //Ajout du bouton � notre contentPane
                pan.add(l_old_login);
                pan.add(jtf_old_login);
                pan.add(l_new_login);
                pan.add(jtf_new_login);
                pan.add(b_validation);
                
                this.setContentPane(pan);
                this.setVisible(true);
                
        }       
	
        class BoutonListener  implements ActionListener{

            public void actionPerformed(ActionEvent arg0) {   
            	System.out.println("l'utilisateur est : " +jtf_old_login.getText()+ " et le mot de logine est : "+jtf_new_login.getText()  );
            	dispose();
            	
            	
            	s_oldlogin= jtf_old_login.getText();
            	s_newlogin = jtf_new_login.getText();

            	try {
    				database_connection con_3 = new database_connection();

    				sql = con_3.getConn().createStatement();
    				
    				ResultSet rs1 = sql.executeQuery("SELECT password from user_db where login = '"+s_login+"';");
    				
    				if(rs1.next())
    				{

    					  ResultSet rs2 = sql.executeQuery("UPDATE user_db set login = '"+s_newlogin+"' where login='"+s_login+"';");

      				  }
    				
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				//System.out.println("erreur dans la connection � labase de donnee");
    			}

				  dispose();
            	
            	
            	
            	
            	
            	
            	
            }
        }
}
