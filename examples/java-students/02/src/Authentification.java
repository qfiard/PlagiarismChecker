import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;



public class Authentification extends JPanel implements ActionListener {
	// Class main a lancer

	//normalement a ne plus utiliser, mais au cas ou : export CLASSPATH=/ens/renaultm/Public/BD6/TP5/postgresql.jdbc4.jar:$CLASSPATH
	
    private JFrame controllingFrame;  
    protected static JPasswordField passwordField;
    protected static JTextField loginField;
    protected String type;
    private LienBD link;
 
    public Authentification(JFrame f) throws ClassNotFoundException, SQLException {
    	
    	this.link=new LienBD("rouache","helloworld");
        controllingFrame = f;
 
        loginField = new JTextField(10);
        loginField.setActionCommand("ok");
        loginField.addActionListener(this);

        passwordField = new JPasswordField(10);
        passwordField.setActionCommand("ok");
        passwordField.addActionListener(this);
 
        JLabel label_login = new JLabel("Enter the login: ");
        label_login.setLabelFor(loginField);
        
        JLabel label = new JLabel("Enter the password: ");
        label.setLabelFor(passwordField);
 
        JComponent buttonPane = createButtonPanel();
 
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label_login);
        textPane.add(loginField);
        textPane.add(label);
        textPane.add(passwordField);
 
        add(textPane);
        add(buttonPane);
    }
 
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("Help");
 
        okButton.setActionCommand("ok");
        helpButton.setActionCommand("help");
        okButton.addActionListener(this);
        helpButton.addActionListener(this);
 
        p.add(okButton);
        p.add(helpButton);
 
        return p;
    }
    
 
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
 
        if ("ok".equals(cmd)) {  
            char[] pass = passwordField.getPassword();
            String login = loginField.getText();
            try {
				if (isLoginCorrect(login,pass)) {
				    JOptionPane.showMessageDialog(controllingFrame,
				        "Authentification Reussie.");
				    if(this.type.matches("douane")){
				    	System.out.println("Interface douane enclenché");
				    	Douane jtp = new Douane(this.link,login);
				    	controllingFrame.setVisible(false); //on cache la fenetre authentification
				    }
				    if(this.type.matches("client")){
				    	System.out.println("Interface client enclenché");
				    	Client_Prod p=new Client_Prod(this.link,login );
				    	controllingFrame.setVisible(false); //on cache la fenetre authentification
				    }
				    if(this.type.matches("gerant")){
				    	System.out.println("Interface gerant enclenché");
				    	Gerant g=new Gerant(this.link);
				    	controllingFrame.setVisible(false); //on cache la fenetre authentification
				    }
				    //TODO implementer les autres interfaces
				    
				} else {
				    JOptionPane.showMessageDialog(controllingFrame,
				        "Mauvais Login ou Mot de Passe. Recommencez.",
				        "Erreur",
				        JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
 				e1.printStackTrace();
			} catch (SQLException e1) {
 				e1.printStackTrace();
			}
			
 
             
      
        } else {  
            JOptionPane.showMessageDialog(controllingFrame,
                "Vous pouvez trouver le mot de passe en versant\n"
              + "un cheque a l ordre de Peter Habermehl.\n");
        }
    
    }
  
    
    private boolean isLoginCorrect(String login, char[] password) throws SQLException{
    	ResultSet rs =this.link.querySQL("select * from utilisateur where " +
    									 "login = \'"+login+"\';");
    	try {
    		if(rs.next()){
    			System.out.println(rs.getString("login")); //TODO debug a enlever
    		
    			String correctpass=rs.getString("password");
    			System.out.println("correctpass :"+correctpass); //TODO debug a enlever
    			this.type=rs.getString("type").trim();
    			
    			return isPasswordCorrect(password, correctpass);
    				
    		}	
    	} catch (SQLException e) {
    		System.out.println("islogincorrect, exception catched");
			return false;
		}
    	return false;
    	
    }
    
    
    private boolean isPasswordCorrect(char[] input, String correctpass) {
        boolean isCorrect = true;
        correctpass = correctpass.trim();
        char[] correctPassword = correctpass.toCharArray();
        //enlever les espaces apres correctpass
        
        for(int i =0;i<input.length;i++){
        	System.out.print(input[i]);
        }
        System.out.println("\ncorrectpass :"+correctpass);
        
        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }
 
        //remplit le pass de 'O'
        Arrays.fill(correctPassword,'0');
 
        return isCorrect;
    }
 
 
  
    private static void cree_fenetre() throws ClassNotFoundException, SQLException {
    	//cree la fenetre d'authenification et l'affiche
    	
        JFrame frame = new JFrame("Authentification Requise. ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
 
        final Authentification newContentPane = new Authentification(frame);
        newContentPane.setOpaque(true);  
        frame.setContentPane(newContentPane);
 
  
        frame.pack();
        frame.setVisible(true);
    }
    
 
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
         UIManager.put("swing.boldMetal", Boolean.FALSE);
        try {
			cree_fenetre();
		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
		} catch (SQLException e) {
 			e.printStackTrace();
		}
            }
        });
    }
}
