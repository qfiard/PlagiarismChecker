import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class ContenuDetailColis extends JPanel{

	private Fenetre fenetre = null;



    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;



	private long dernierClicTable = 0;
	private int dernierColonneTable = 0;
	private int dernierLigneTable = 0;

	private int idColis;
	private boolean aVerdict;



	public ContenuDetailColis(Fenetre fen,int id){

		fenetre = fen;
		idColis = id;
		aVerdict = Requetes.aUnVerdict(id);
      

        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();

        jButton4.setText("jButton4");

        //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            Requetes.detailColis1(idColis),
            new String [] {
                "Produit", "Prix", "Quantité"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);

        jLabel1.setText("Détail du colis numéro : "+idColis);

        jLabel2.setText("Décision: ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "En attente", "Valide", "Non Valide" }));


        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clicComboBox(evt);
            }
        });


        jButton1.setText("Valider");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                validation_decision(evt);
            }
        });


        jLabel3.setText("dans la palette numéro : "+Requetes.idPaletteDeIdColis(idColis));

        jLabel4.setText("dans "+Requetes.nomVehiculeDeIdColis(idColis)+" numéro: "+Requetes.idVehiculeDeIdColis(idColis));

        jButton2.setText("Prec.");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                precedente(evt);
            }
        });


        jButton3.setText("Suiv.");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suivante(evt);
            }
        });

        jButton5.setText("Retour à l'accueil");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueil(evt);
            }
        });

        jScrollPane4.setVisible(true);

        jTextPane1.setVisible(true);
        jTextPane1.setText("Motif du renvoi");
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                motifRenvoi(evt);
            }
        });
        jScrollPane4.setViewportView(jTextPane1);

        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("Erreur durant l'ajout à la base.");
	jLabel5.setVisible(false);


	int larg = fenetre.getLargeur()-325;












	if (aVerdict) {
		if (Requetes.verdict(idColis).charAt(0) == 'V') jComboBox1.setSelectedIndex(1);
		else jComboBox1.setSelectedIndex(2);
		jTextPane1.setText(Requetes.motifDeIdColis(idColis));
		
	}













        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(larg, larg, larg)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(larg, larg, larg))
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );


	}

    private void precedente(java.awt.event.MouseEvent evt) {
        fenetre.precedentHistorique();
    }

    private void suivante(java.awt.event.MouseEvent evt) {
        fenetre.suivantHistorique();
    }

    private void accueil(java.awt.event.MouseEvent evt) {
        fenetre.racine();
    }                       

    private void validation_decision(java.awt.event.MouseEvent evt) {
	//A faire
	//jLabel5.setVisible(true);
	if ((((String) jComboBox1.getSelectedItem()).charAt(0)=='E') || ((((String) jComboBox1.getSelectedItem()).charAt(0)=='N')&&(jTextPane1.getText().length()<20))){
		jLabel5.setVisible(true);
	}
	else{
		try{
			Requetes.modifColisDouane(idColis,((String) jComboBox1.getSelectedItem()).charAt(0),jTextPane1.getText());
			fenetre.setType(111,idColis);
		}
		catch (Exception e){
			System.out.println(e);
			jLabel5.setVisible(true);
		}
	}
    }                                    

    private void clicComboBox(java.awt.event.MouseEvent evt) {
	/*System.out.println("AAAAAAA");
        if (((String) jComboBox1.getSelectedItem()).charAt(0)=='N'){
        jScrollPane4.setVisible(true);
        jTextPane1.setVisible(true);
        }
	else {
        jScrollPane4.setVisible(false);
        jTextPane1.setVisible(false);
	}*/
    }

    private void motifRenvoi(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }


    /*private void actionCaseCG(java.awt.event.MouseEvent evt) {

        long moment = evt.getWhen();
        int ligne = jTable1.getSelectedRow();
        int colonne = jTable1.getSelectedColumn();
        
        if ((dernierColonneTable==colonne)&&(dernierLigneTable==ligne)&&(moment-dernierClicTable<300)){
        
		jButton1.setText("double clic");
		fenetre.racine();
      
        
        }
        else{
        dernierColonneTable=colonne;
        dernierLigneTable=ligne;
        dernierClicTable=moment;
        }
        
    }*/
}