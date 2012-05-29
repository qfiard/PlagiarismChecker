import java.sql.*; 

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
	
public class i_session extends JFrame{

	private static final long serialVersionUID = 1L;
	/**
	 * Interface Variables
	 */
    private JButton bouton = new JButton("Ouvrir ma session");
    private JButton b_init = new JButton("Initialisation");
    private JLabel l_name = new JLabel("Nom utilisateur :");
    private JTextField jtf_name = new JTextField();
    private JLabel l_passwd = new JLabel("Mot de Passe   :");
    private JTextField jtf_passwd = new JTextField();
	
    
    /**
     * variable de champs
     */
    private String t_name;
    private String t_pass;
    private String true_pass;
    /**
     * variables SQL
     */
    public static Connection connection;
	private Connection 		con;
	private Statement       sql;
	private DatabaseMetaData dbmd;


	
	/**
	 * code
	 * @throws ClassNotFoundException
	 */
	public i_session() throws ClassNotFoundException {
        this.setTitle("Ouverture de la session");
        this.setSize(320, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        

        jtf_name.setPreferredSize(new Dimension(150, 30));
        jtf_passwd.setPreferredSize(new Dimension(150, 30));
        

        bouton.addActionListener(new BoutonOKListener());
        b_init.addActionListener(new bou_ses_init());
        JPanel pan = new JPanel();

        pan.add(l_name);
        pan.add(jtf_name);
        pan.add(l_passwd);
        pan.add(jtf_passwd);
        pan.add(bouton);
        pan.add(b_init);
    
        //System.out.println("l'utilisateur est : " +jtf_name.getText()+ " et le mot de passe est : "+jtf_passwd.getText()  );
        

        this.setContentPane(pan);
        this.setVisible(true);
	}
	
	/**
	 * bouton Connection
	 * @param arg
	 * @throws ClassNotFoundException
	 */
    class BoutonOKListener  implements ActionListener{
   	 
        /**
         * Red�finition de la m�thode actionPerformed
         */
        public void actionPerformed(ActionEvent arg0) {
            t_name = jtf_name.getText();
            t_pass = jtf_passwd.getText();

            try {
				database_connection con_1 = new database_connection();
				 //System.out.println("SELECT password from user_db where login = '"+t_name+"';");
				sql = con_1.getConn().createStatement();
				
				ResultSet rs = sql.executeQuery("SELECT * from user_db where login = '"+t_name+"';");
				
				
				if(rs.next())
				{
					
				  // la requete a trouve d'enregistrement
					String pass = (rs.getString("password")).trim();
					
				  if( t_pass.equals(pass) ){
					  dispose();
					  JOptionPane jop1 = new JOptionPane();
					  jop1.showMessageDialog(null, "Connexion R�ussie, Bienvenue "+t_name, "Information", JOptionPane.INFORMATION_MESSAGE);
					  
					  
					  String s_statut = (rs.getString("type")).trim();
					  System.out.println("l'usre est un "+s_statut);
						if(s_statut.equals("CLI")){
							interface_client g_client = new interface_client(t_name);
						}else if(s_statut.equals("GER")) {
							interface_gerant g_gerant = new interface_gerant(t_name);
						}else if(s_statut.equals("EMB")) {
							i_emballeur g_emballeur = new i_emballeur(t_name);
						}else if(s_statut.equals("TRA")) {
							interface_transporteur g_transporteur = new interface_transporteur(t_name);
						}else if(s_statut.equals("DOU")) {
							interface_douane ed = new interface_douane(t_name);
						}
					  
					  

				  }else{
					  JOptionPane jop3 = new JOptionPane();
					  jop3.showMessageDialog(null, "ERREUR : Mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);  
				  }
				}
				else
				{
					  JOptionPane jop3 = new JOptionPane();
					  jop3.showMessageDialog(null, "ERREUR : Login Incorrect", "Erreur", JOptionPane.ERROR_MESSAGE); 
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("erreur dans la connection � labase de donnee");
			}
            
        	
        }
    }
	
	

	
	
	
		
	public static void main(String[] arg) throws ClassNotFoundException { 
		i_session ses_1 = new i_session();
	}






	public String getT_name() {
		return t_name;
	}






	public void setT_name(String t_name) {
		this.t_name = t_name;
	}






	public String getT_pass() {
		return t_pass;
	}






	public void setT_pass(String t_pass) {
		this.t_pass = t_pass;
	}
	
}
