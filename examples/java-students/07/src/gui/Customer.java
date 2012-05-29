package gui;

import javax.swing.*;
public class Customer extends Window {

    /** Creates new form Customer */
    public Customer() {
    	super("Client", 300, 500);
        initComponents();
    }


    private void initComponents() {

        JLabel jLabel1 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Client");

        jButton1.setText("Liste produit");

        jButton2.setText("Suivi commande");
        
        // TODO à activer que lorsque la liste d'achat est non vide dans User
        jButton4.setText("Confirmer achats");
        jButton4.setVisible(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(363, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(135, Short.MAX_VALUE))
        );


        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer().setVisible(true);
            }
        });
    }
    /**
     * 
     * @param c 0 pour produit 1 pour rechercher un client ou un employ�
     */
    public void choice(int c) {

        JComboBox jComboBox1 = new javax.swing.JComboBox();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JLabel jLabel1 = new javax.swing.JLabel();

        // TODO récuperer depuis la base de donnée le tableau de String
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Valider");

        jButton2.setText("Retour");

        jLabel1.setText("Gérant");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(323, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }

    private void product() {


    	JLabel jLabel1 = new javax.swing.JLabel();
    	JLabel jLabel2 = new javax.swing.JLabel();
    	JLabel jLabel3 = new javax.swing.JLabel();
    	JLabel jLabel4 = new javax.swing.JLabel();
    	JButton jButton1 = new javax.swing.JButton();
    	JButton jButton2 = new javax.swing.JButton();

    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    	jLabel1.setText("Client");

    	jLabel2.setText("Numéro produit :");

    	jLabel3.setText("Description ");

    	jLabel4.setText("Prix : ");

    	jButton1.setText("Retour");

    	jButton2.setText("Acheter");

    	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    	getContentPane().setLayout(layout);
    	layout.setHorizontalGroup(
    			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    			.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
    					.addContainerGap(363, Short.MAX_VALUE)
    					.addComponent(jLabel1)
    					.addContainerGap())
    					.addGroup(layout.createSequentialGroup()
    							.addContainerGap()
    							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
    									.addGroup(layout.createSequentialGroup()
    											.addComponent(jButton2)
    											.addGap(18, 18, 18)
    											.addComponent(jButton1))
    											.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
    													.addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    													.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    													.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
    													.addContainerGap(50, Short.MAX_VALUE))
    			);
    	layout.setVerticalGroup(
    			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    			.addGroup(layout.createSequentialGroup()
    					.addComponent(jLabel1)
    					.addGap(18, 18, 18)
    					.addComponent(jLabel2)
    					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
    					.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
    					.addGap(18, 18, 18)
    					.addComponent(jLabel4)
    					.addGap(18, 18, 18)
    					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
    							.addComponent(jButton1)
    							.addComponent(jButton2))
    							.addContainerGap(42, Short.MAX_VALUE))
    			);

    	pack();

    }
    
    /*
     Pour le suivi vhoix d'une commande puis qimple showInformation pour dire où ça en est
     */

}
