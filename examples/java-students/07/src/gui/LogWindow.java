package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPasswordField;

import Share.FiredException;
import Share.IdentificationException;
import Share.User;
import Share.UserException;

/*
 * LogWindow.java
 *
 * Created on 18 avr. 2012, 16:19:07
 */


public class LogWindow extends Window implements ActionListener {
	

	private static final long serialVersionUID = 6126137317809852662L;
	public static int i = 1;
	private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonQuit;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField login;

    public LogWindow() {
    	super("Connection", 0, 0);
        initComponents();
    }
    
	public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (debug) {
            System.out.println("Action avec la chaine de commande = "
                    + e.getActionCommand());
        }
        if (source == jButtonQuit) {
            System.exit(0);
        }
        if (source == jButtonOk) {
        	if (login.getText().equalsIgnoreCase("")) {
        		showInformationMessage("Merci de compléter les champs." , "Erreur");
        		main(null);
        	} else {
        		try {
        			String pwd = Share.Tools.string_of_tabChar(password.getPassword());
        			u = new User(login.getText(), pwd);
        			u.setLogin(login.getText());
        			u.setPassword(pwd);
        			showInformationMessage("Authentification réussite !" , "Bienvenue " + login.getText());
        			this.setVisible(false);
        			new GreetingsWindow("Import - Export", this.u);
        			
        		} catch (IdentificationException e1) {
        			if (i == 3) {
        				showInformationMessage("Vous avez fait 3 tentatives, au revoir !" , "Echec");
        				System.exit(0);
        			}
        			showInformationMessage("Conexion impossible !\nTentative " + i + "/3" , "Tentative connexion");
        			i++;
        			main(null);
        		} catch (SQLException ex) {
        			showInformationMessage("Connexion à la base de donnée impossible" , "Problème base de donnée");
        			gui.NewAccountWindow.main(null);
				} catch (ClassNotFoundException eX) {
					showInformationMessage("Connexion à la base de donnée impossible" , "Problème base de donnée");
					System.exit(1);
				} catch (UserException userException) {
        			showInformationMessage("La base de donnée a été corrompu !" , "Erreur");
        			System.exit(1);
        		} catch (FiredException ek) {
					showInformationMessage("Vous êtes viré" , "Accès refusé");
					System.exit(1);
				} 
				
        		
        		
        	}

        }
	}

    private void initComponents() {

        jLabelLogin = new javax.swing.JLabel();
        login = new javax.swing.JTextField(5);
        jLabelPassword = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jButtonOk = new javax.swing.JButton();
        jButtonQuit = new javax.swing.JButton();

        setTitle("Connection");

        jLabelLogin.setText("Login");

        jLabelPassword.setText("Mot de passe");
        password = new JPasswordField(8);

        if (debug) {
        	System.out.println("Connection pour un douanier");
        	password.setText("GX049ICE2WB");
        	login.setText("Nash");
        }
        
        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(this);

        jButtonQuit.setText("Quitter");
        jButtonQuit.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogin)
                .addGap(18, 18, 18)
                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPassword)
                .addGap(18, 18, 18)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(193, Short.MAX_VALUE)
                .addComponent(jButtonOk)
                .addGap(18, 18, 18)
                .addComponent(jButtonQuit)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogin)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonQuit))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }
    
    

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogWindow().setVisible(true);
                
                /*
        		try {
        			// 60 secondes avant fermeture de la fen�tre
        			Thread.sleep(60000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		System.exit(0);
        		*/
            }
        });
    }


}
