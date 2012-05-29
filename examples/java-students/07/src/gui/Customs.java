
package gui;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import Share.User;

public class Customs extends Window {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3416044840298530925L;

	private void run() {
    	try {
    		customsChoice();
    	} catch (SQLException e) {
    		if (debug)
    			e.printStackTrace();
    		showInformationMessage("Connexion interrompu avec la base de données", "Erreur");
			this.setVisible(false);
			gui.NewAccountWindow.main(null);
			
    	} catch (NullPointerException ne) {
    		showInformationMessage("Inconnu", "Erreur");
    		System.exit(1);    		
    	} catch (NumberFormatException ne) {
    		if (debug)
    			ne.printStackTrace();
    		else {
    			showInformationMessage("Merci d'entré un chiffrer", "Erreur");
    			this.setVisible(false);
    			new Customs(u);
    		}

    	}
    }
    
    public Customs(User user) {
    	super("Service douane", user);
    	if (this.u.level == 2) {
    		// ajout de l'accès à la recherche par numéro de commande   	
    		lookWithOrder = new JMenuItem("Commande");
    		file.add(lookWithOrder);
    		lookWithOrder.addActionListener(this);

    		// ajout de l'accès à la recherche par destination
    		lookWithDestination = new JMenuItem("Destination");
    		file.add(lookWithDestination);
    		lookWithDestination.addActionListener(this);

    		// ajout de l'accès à la recherche pas contenu 
    		lookWithContents = new JMenuItem("Avec");
    		file.add(lookWithContents);
    		lookWithContents.addActionListener(this);
    	}
    	logout = new JMenuItem("Déconnexion");
    	file.add(logout);
    	file.add(quit);
    	logout.addActionListener(this);
    	run();
    }

    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonReturn;
    private javax.swing.JButton jButton;
    private javax.swing.JComboBox jComboBox;
    private javax.swing.JLabel jLabelCustoms;
    private String[] data = {"Test", "ok"};

    /**
     * Bouton ok 
     */
    private void next() {
		int buf = jComboBox.getSelectedIndex();
		if (buf == 0) 
			showInformationMessage("Il faut choisir l'un des choix possible", "Faire un choix");
		else {
			String[] buffer = data[jComboBox.getSelectedIndex()].split(" ");
			u.history.add(buffer[0]);
			++u.level;
			this.setVisible(false);
			new Customs(u);
		}
    }
    /**
     * Permet de revenir à la fenêtre précédente
     * La fonction permet de supprimer de la liste le dernier résultat
     * et de décrémenter level
     */
    public void previousWindow() {
		if (u.level == 0)
			showInformationMessage("Choisir un type de Conteneur", "Aide");
		else {
			// On retourne au niveau précédent et on reprend l'ancien choix
			--u.level;
			u.history.remove(u.history.size() - 1);
			this.setVisible(false);
			new Customs(u);
		}
    }
    
    /**
     * On crée une fenêtre sendMessage
     * qui va décrementer level et supprimer la dernière donnée de history
     */
    public void badPackage() {
    	this.setVisible(false);
    	new SendMessage(u);
    }
    
    private void customsChoice() throws SQLException, NumberFormatException {
    
        jComboBox = new javax.swing.JComboBox();
        jButtonOk = new javax.swing.JButton();
        jButtonReturn = new javax.swing.JButton();
        jLabelCustoms = new javax.swing.JLabel();
        jButton = new javax.swing.JButton();
        
        jButtonOk.setText("Valider");

        jButtonReturn.setText("Retour");

        jLabelCustoms.setText("Douane");
        jButton.setText("Refuser");
        jButton.setVisible(false);
        
        jButtonReturn.revalidate();
        jButtonReturn.setVisible(true);
        
        switch (u.level) {
        case 0 :
        	jButtonReturn.setText("Aide");
        	this.setTitle("Choix conteneur");
        	data = new String[]{"CONTENEURS", "avion", "bateau", "camion"};
        	break;
        case 1 : 
        	this.setTitle("Choix de la palette");
        	if (debug)
        		System.out.println("Choix précedent " + u.history.get(u.history.size() - 1));
        	data = u.q.getByTransportType(u.history.get(u.history.size() - 1), this.u.getId());
        	break;
        case 2 :
        	try {
        		Integer.parseInt(u.history.get(u.history.size() - 1));
        	} catch (NumberFormatException e) {
        		u.history.remove(u.history.size() - 1);
        	}
        	jButton.setVisible(true);
        	this.setTitle("Choix ou refus d'un colis");
        	if (debug)
        		System.out.println("case 2 : " + u.history.get(u.history.size() - 1));
        	data = u.q.getPalletContent(Integer.parseInt(u.history.get(u.history.size() - 1)));
        	break;
        case 3 : 
        	this.setTitle("Choix d'un produit");
        	if (debug) 
        		System.out.println("case 3 : " + u.history.get(u.history.size() - 1));
        	data = u.q.getPackageContent(Integer.parseInt(u.history.get(u.history.size() - 1)));
        	
        	break;
        case 4 :
        	data = u.q.getProduct(u.history.get(u.history.size() - 1));
        	if (debug)
        	System.out.println("case 4 : " + u.history.get(u.history.size() - 1));
        	this.setTitle("Produit");
        	jComboBox.setVisible(false);
        	jButton.setVisible(false);
        	jButtonOk.setVisible(false);
        	jLabelCustoms.setVisible(false);
        	jButtonReturn.setVisible(false);
        	this.setBounds(450, 300, 500, 350);
        	lastLevel();
        	break;
        case 10 : 
           	this.setTitle("A partir du numero de commande");
           	int id_order = Integer.parseInt(u.history.get(u.history.size() - 1));
           	this.u.level = 2;
        	if (debug) 
        		System.out.println("case 10 : " + id_order);
        	data = u.q.getOrderContent(id_order);
        	break;
        case 11 : 
           	this.setTitle("A partir d'une partie de l'adresse");
           	String country= u.history.get(u.history.size() - 1);
           	this.u.level = 2;
        	if (debug) 
        		System.out.println("case 10 : " + country);
        	data = u.q.getPackageTo(country);
        	break;
        case 12 : 
           	this.setTitle("A partir de ce qu'il y a dans la commande");
           	String content = u.history.get(u.history.size() - 1);
           	this.u.level = 2;
        	if (debug) 
        		System.out.println("case 10 : " + content);
        	data = u.q.getPackageIn(content);
        	break;
        	default : 
        		showInformationMessage("Programme Corrompu", "ERREUR");
        		System.exit(1);
        }
        if (data.length == 0) {
        	showInformationMessage("Il n'y a pas de données pour cette requête", "Pas de donnée");
        	previousWindow();
        }
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(data));
        
        // Cas du refus
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				badPackage();
			}
		});
        
        // Cas du retour
		jButtonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				previousWindow();

			}
		});
        
		// Cas du bouton ok
		jButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				next();
			}
		});


        


        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(jButtonReturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(319, Short.MAX_VALUE)
                        .addComponent(jLabelCustoms)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelCustoms)
                .addGap(28, 28, 28)
                .addComponent(jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonReturn)
                    .addComponent(jButton))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    private void lastLevel() {

        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel numberProduct = new javax.swing.JLabel();
        JLabel description = new javax.swing.JLabel();
        JLabel price = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        jLabel1.setText("Douane");

        numberProduct.setText("Numéro produit : " + data[3] + "Caractèristique : " + data[2]);

        description.setText("Description :\n" + data[1]);

        price.setText("Prix : " + data[0]);

        jButton1.setText("Retour");
        
        // Cas du retour
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				previousWindow();

			}
		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(353, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(price, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numberProduct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(description, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(numberProduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(price)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }

}
