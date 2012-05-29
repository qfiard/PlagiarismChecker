public class    Ajout {

	public static void main(String[] args) {
		for (int i = 1; i <= 50; i++) {
			//String requete = "insert into DOUANE(ID_DOU,MAIL,ID_PAY,MDP) values (" + i + ",'douane" + i + "@douane.fr'," + i + ",'11111111');";
			System.out.println(Remplissage.nomAleatoire() + "    " + Remplissage.alea(50, 200));
		}
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;



public class    Contenu {
	private String  titre;;
	private int     id;
	private int     type;

	public          Contenu(String ti, int ty, int idd) {
		titre = ti;
		id = idd;
		type = ty;
	}
	public String   getTitre() {
		return titre;
	}
	public int      getId() {
		return id;
	}
	public int      getType() {
		return type;
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;

//Probleme au niveau des boutons...



class ContenuClient extends JPanel
{

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JButton jButton5;
	private         javax.swing.JButton jButton6;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JSeparator jSeparator1;




	public          ContenuClient(Fenetre fen) {


		fenetre = fen;


		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();



		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jLabel2.setText("Numéro de client: " + Requetes.getIdentifiant());

		jButton1.setText("Passer une commande");


		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					passerCommande(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});



		jButton2.setText("Suivre vos commandes en cours");

		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suiviCommande(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Modifier votre login et/ou mot de passe");

		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					compte(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton4.setText("Préc.");

		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton5.setText("Suiv.");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		jButton6.setText("Déconnexion");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					deconnexion(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					  .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
				    .addGroup(layout.createSequentialGroup()
					      .addGap(106, 106, 106)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addContainerGap())
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addGap(40, 40, 40)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton4)
						    .addComponent(jButton5))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton6))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addComponent(jButton1)
					      .addGap(18, 18, 18)
					      .addComponent(jButton2)
					      .addGap(18, 18, 18)
					      .addComponent(jButton3)
				     .addContainerGap(124, Short.MAX_VALUE))
			);



	}


	private void    passerCommande(java.awt.event.ActionEvent evt) {
		fenetre.setType(407, 0);
	}
	private void    suiviCommande(java.awt.event.ActionEvent evt) {
		fenetre.setType(402, 0);
	}
	private void    compte(java.awt.event.ActionEvent evt) {
		fenetre.setType(401, 0);
	}
	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    deconnexion(java.awt.event.ActionEvent evt) {
		fenetre.connexion();
	}


}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuCommande extends JPanel
{

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JScrollPane jScrollPane2;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTable jTable1;
	private         javax.swing.JTextField jTextField1;
	private         javax.swing.JTextPane jTextPane1;


	private         Object[][] tableau;


	public          ContenuCommande(Fenetre fen) {

		tableau = Requetes.produit();
		fenetre = fen;


		jLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextPane1 = new javax.swing.JTextPane();
		jButton1 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jLabel2.setText("Numéro de client: " + Requetes.getIdentifiant());

		jScrollPane2.setBorder(null);

		jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
								    tableau,
							      new String[] {
			"Nom", "Prix", "Quantité"
		}
		) {
			Class[] types = new Class[] {
				java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, true
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.setColumnSelectionAllowed(true);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(jTable1);
		jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(2).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setPreferredWidth(5);

		jScrollPane1.setBorder(null);

		jTextPane1.setBackground(new java.awt.Color(240, 240, 240));
		jTextPane1.setBorder(null);
		jTextPane1.setEditable(false);
		jTextPane1.setText("Pour passer une commade, entrez la quantité souhaitée sur la ou les lignes de votre choix.");
		jScrollPane1.setViewportView(jTextPane1);

		jButton1.setText("Valider");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					valider(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel3.setText("Date souhaitée pour la livraison");

		jButton2.setText("Préc.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Suiv.");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				suivante(evt);
			}
		});

		jButton4.setText("Retour à l'accueil");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						  .addComponent(jSeparator1)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
								  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
									    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
									    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
						 .addComponent(jScrollPane2)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
								  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						      .addComponent(jLabel2)
						    .addComponent(jButton4))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel3)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton1)
					      .addGap(18, 18, 18)
					      .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
				      .addContainerGap(23, Short.MAX_VALUE))
			);

	}

	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}

	private void    valider(java.awt.event.ActionEvent evt) {
		Requetes.setTab(obtenirCommande());
		fenetre.setType(403, 0);
	}

	private         Object[][] obtenirCommande() {
		//System.out.print("PPPPPPPPPPPPPPPPPPPPPPPPPPP\n");
		int             compteur = 0;
		for             (int i = 0; i < tableau.length; i++) {
			if (Integer.parseInt(jTable1.getValueAt(i, 2).toString()) != 0)
				compteur++;
		}
		              //System.out.print("PPPPPPPPPPPPPPPPPPPPPPPPPPP\n");
		int             k = 0;
		Object[][] t = new Object[compteur][3];
		for (int j = 0; j < tableau.length; j++) {
			if (Integer.parseInt(jTable1.getValueAt(j, 2).toString()) != 0) {
				t[k] = tableau[j];
				t[k][2] = new Integer(Integer.parseInt(jTable1.getValueAt(j, 2).toString()));
				k++;
			}
		}
		/*
		 * System.out.print(((Integer) tableau[0][2]).intValue()+"
		 * PPPPPPPPP  "+compteur+"\n"); for (int l =
		 * 0;l<t.length;l++){ for (int m = 0; m<3;m++){
		 * System.out.print(" "+t[l][m]); } System.out.print("\n"); }
		 */
		return t;
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuConnexion extends JPanel
{

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JComboBox jComboBox1;
	private         javax.swing.JDialog jDialog1;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JPasswordField jPasswordField1;
	private         javax.swing.JTextField jTextFieldEmail;



	public          ContenuConnexion(Fenetre fen) {


		fenetre = fen;


		jDialog1 = new javax.swing.JDialog();
		jLabel4 = new javax.swing.JLabel();
		jPasswordField1 = new javax.swing.JPasswordField();
		jButton1 = new javax.swing.JButton();
		jTextFieldEmail = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setVisible(false);

		//jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		jDialog1.setTitle("Autre");
		jDialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		jLabel4.setText("AUTRE");





		javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
		jDialog1.getContentPane().setLayout(jDialog1Layout);
		jDialog1Layout.setHorizontalGroup(
						  jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addGroup(jDialog1Layout.createSequentialGroup()
				      .addGap(172, 172, 172)
				      .addComponent(jLabel4)
				      .addContainerGap(500, 500))
		);
		jDialog1Layout.setVerticalGroup(
						jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addGroup(jDialog1Layout.createSequentialGroup()
				      .addGap(103, 103, 103)
				      .addComponent(jLabel4)
				      .addContainerGap(500, 500))
		);

		//fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//setTitle("Connexion");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				jPasswordField1ActionPerformed(evt);
			}
		});

		                jButton1.setText("Connexion");
		                jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					jButton1ActionPerformed(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jTextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldEmailActionPerformed(evt);
			}
		});

		jLabel1.setText("Adresse e-mail");

		jLabel2.setText("Mot de passe");

		jLabel3.setText("Type de connexion");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"Gérant", "Douane", "Client", "Emballeur", "Transporteur"
		}));
		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox1ActionPerformed(evt);
			}
		});

		jLabel5.setForeground(new java.awt.Color(255, 0, 0));
		jLabel5.setText("Echec de l'autentification");
		jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));











		jTextFieldEmail.setText("ptutoissipr.ropor@gmail.com");
		jPasswordField1.setText("11111111");











		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGap(40, 40, 40)	/* Position horizontale */
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					      .addGap(50, 50, 50)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
				     .addContainerGap(412, Short.MAX_VALUE))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGap(80, 80, 80)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel3)
							.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addGap(19, 19, 19)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel1))
					      .addGap(18, 18, 18)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton1)
						     .addComponent(jLabel5))
				     .addContainerGap(195, Short.MAX_VALUE))
			);

	}

	public void     paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("", 25, 25);
	}

	/*
	 * class MyListener implements ActionListener { public void
	 * actionPerformed(ActionEvent event) { fenetre.dispose(); } }
	 */

	public void     envoi(String email, String mdp, String typeS) throws SQLException,
	                ClassNotFoundException {
		int             type = 0;


		if              (typeS.charAt(0) == 'D')
			                type = 1;
		if              (typeS.charAt(0) == 'E')
			                type = 2;
		if              (typeS.charAt(0) == 'T')
			                type = 3;
		if              (typeS.charAt(0) == 'C')
			                type = 4;
		if              (typeS.charAt(0) == 'G')
			                type = 5;


		if              (Requetes.connexion(email, mdp, type)) {

			//System.out.println("ici" + typeS.charAt(0));
			fenetre.setType(type, -1);

		} else {
			jPasswordField1.setText("");
			jLabel5.setVisible(true);
		}
		//jTextArea1.setText(email + " " + mdp);
		//System.out.println(email + " " + mdp);
	}


	private void    jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void    jTextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void    jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void    jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws SQLException,
	                ClassNotFoundException {
		//ici ! !!
		//jButton1.setText("");
		envoi(jTextFieldEmail.getText(), jPasswordField1.getText(), (String) jComboBox1.getSelectedItem());
	}


}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;


class ContenuDetailColis extends JPanel {

	private Fenetre fenetre = null;



	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JButton jButton5;
	private         javax.swing.JComboBox jComboBox1;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JScrollPane jScrollPane4;
	private         javax.swing.JTable jTable1;
	private         javax.swing.JTextPane jTextPane1;



	private long    dernierClicTable = 0;
	private int     dernierColonneTable = 0;
	private int     dernierLigneTable = 0;

	private int     idColis;
	private boolean aVerdict;



	public          ContenuDetailColis(Fenetre fen, int id) {

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
							      new String[] {
			"Produit", "Prix", "Quantité"
		}
		) {
			Class[] types = new Class[] {
				java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
			};
			boolean[] canEdit = new boolean[] {
				false, true, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setResizable(false);

		jLabel1.setText("Détail du colis numéro : " + idColis);

		jLabel2.setText("Décision: ");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"En attente", "Valide", "Non Valide"
		}));


		jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				clicComboBox(evt);
			}
		});


		jButton1.setText("Valider");
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				validation_decision(evt);
			}
		});


		jLabel3.setText("dans la palette numéro : " + Requetes.idPaletteDeIdColis(idColis));

		jLabel4.setText("dans " + Requetes.nomVehiculeDeIdColis(idColis) + " numéro: " + Requetes.idVehiculeDeIdColis(idColis));

		jButton2.setText("Prec.");
		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				precedente(evt);
			}
		});


		jButton3.setText("Suiv.");
		jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				suivante(evt);
			}
		});

		jButton5.setText("Retour à l'accueil");
		jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				accueil(evt);
			}
		});

		jScrollPane4.setVisible(true);

		jTextPane1.setVisible(true);
		jTextPane1.setText("Motif du renvoi");
		jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				motifRenvoi(evt);
			}
		});
		jScrollPane4.setViewportView(jTextPane1);

		jLabel5.setForeground(new java.awt.Color(255, 0, 0));
		jLabel5.setText("Erreur durant l'ajout à la base.");
		jLabel5.setVisible(false);


		int             larg = fenetre.getLargeur() - 325;












		if (aVerdict) {
			if (Requetes.verdict(idColis).charAt(0) == 'V')
				jComboBox1.setSelectedIndex(1);
			else
				jComboBox1.setSelectedIndex(2);
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

	private void    precedente(java.awt.event.MouseEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.MouseEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.MouseEvent evt) {
		fenetre.racine();
	}

	private void    validation_decision(java.awt.event.MouseEvent evt) {
		//A faire
		// jLabel5.setVisible(true);
		if ((((String) jComboBox1.getSelectedItem()).charAt(0) == 'E') || ((((String) jComboBox1.getSelectedItem()).charAt(0) == 'N') && (jTextPane1.getText().length() < 20))) {
			jLabel5.setVisible(true);
		} else {
			try {
				Requetes.modifColisDouane(idColis, ((String) jComboBox1.getSelectedItem()).charAt(0), jTextPane1.getText());
				fenetre.setType(111, idColis);
			}
			catch(Exception e) {
				System.out.println(e);
				jLabel5.setVisible(true);
			}
		}
	}

	private void    clicComboBox(java.awt.event.MouseEvent evt) {
		/*
		 * System.out.println("AAAAAAA"); if (((String)
		 * jComboBox1.getSelectedItem()).charAt(0)=='N'){
		 * jScrollPane4.setVisible(true);
		 * jTextPane1.setVisible(true); } else {
		 * jScrollPane4.setVisible(false);
		 * jTextPane1.setVisible(false); }
		 */
	}

	private void    motifRenvoi(java.awt.event.MouseEvent evt) {
		//TODO add your handling code here:
	}


	/*
	 * private void actionCaseCG(java.awt.event.MouseEvent evt) {
	 * 
	 * long moment = evt.getWhen(); int ligne = jTable1.getSelectedRow();
	 * int colonne = jTable1.getSelectedColumn();
	 * 
	 * if
	 * ((dernierColonneTable==colonne)&&(dernierLigneTable==ligne)&&(moment
	 * -dernierClicTable<300)){
	 * 
	 * jButton1.setText("double clic"); fenetre.racine();
	 * 
	 * 
	 * } else{ dernierColonneTable=colonne; dernierLigneTable=ligne;
	 * dernierClicTable=moment; }
	 * 
	 * } */
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;


class ContenuDetailColis2 extends JPanel {

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JScrollPane jScrollPane3;
	private         javax.swing.JTable jTable1;
	private         javax.swing.JTextArea jTextArea1;



	private long    dernierClicTable = 0;
	private int     dernierColonneTable = 0;
	private int     dernierLigneTable = 0;

	private int     idColis;



	public          ContenuDetailColis2(Fenetre fen, int id) {

		fenetre = fen;
		idColis = id;



		jButton3 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel4 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jButton4 = new javax.swing.JButton();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jButton3.setText("Retour à l'accueil");
		jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				accueil(evt);
			}
		});

		                jButton2.setText("Suiv.");
		                jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				suivante(evt);
			}
		});

		jScrollPane1.setBorder(null);

		jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
					     Requetes.detailColis1(idColis),
							      new String[] {
			"Produit", "Prix", "Quantité"
		}
		) {
			Class[] types = new Class[] {
				java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setResizable(false);

		jLabel4.setText("Colis " + Requetes.verdict(idColis));

		jButton1.setText("Prec.");
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				precedente(evt);
			}
		});

		jLabel3.setText("dans " + Requetes.nomVehiculeDeIdColis(idColis) + " numéro: " + Requetes.idVehiculeDeIdColis(idColis));

		jLabel2.setText("dans la palette numéro : " + Requetes.idPaletteDeIdColis(idColis));

		jLabel1.setText("Détail du colis numéro : " + idColis);

		jButton4.setText("Modifier");



		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					modifier(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jTextArea1.setColumns(20);
		jTextArea1.setEditable(false);
		jTextArea1.setRows(5);
		jTextArea1.setText(Requetes.motifDeIdColis(idColis));
		jScrollPane3.setViewportView(jTextArea1);


		int             larg = fenetre.getLargeur() - 325;

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								  .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(larg, larg, larg))
								  .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
						 .addComponent(jScrollPane1)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jScrollPane3))
				     .addContainerGap(304, Short.MAX_VALUE))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton1)
						     .addComponent(jButton2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel4)
							.addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addGap(18, 18, 18)
					      .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
				      .addContainerGap(37, Short.MAX_VALUE))
			);


		/*
		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		        setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addContainerGap()
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                    .addGroup(layout.createSequentialGroup()
		                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                        .addComponent(jButton2)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGroup(layout.createSequentialGroup()
		                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(153, 153, 153))
		                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(jScrollPane1)
		                    .addGroup(layout.createSequentialGroup()
		                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(jScrollPane3))
		                .addContainerGap(164, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addContainerGap()
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(jButton1)
		                    .addComponent(jButton2)
		                    .addComponent(jButton3))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(18, 18, 18)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(jLabel4)
		                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
		                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(23, 23, 23)
		                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
		        );
		
		*/


	}

	private void    precedente(java.awt.event.MouseEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.MouseEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.MouseEvent evt) {
		fenetre.racine();
	}
	private void    modifier(java.awt.event.ActionEvent evt) {
		fenetre.setType(110, idColis);
	}


	/*
	    private void actionCaseCG(java.awt.event.MouseEvent evt) {
	
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
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuDetailTransporteur extends JPanel
{

	private Fenetre fenetre = null;


	              //METRE LES CHAMPS
	                private javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JLabel jLabel6;
	private         javax.swing.JLabel jLabel7;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JSeparator jSeparator2;
	private         javax.swing.JTextField jTextField1;


	private int     id;
	private int     type;

	public          ContenuDetailTransporteur(Fenetre fen, int id, int type) {


		fenetre = fen;
		this.id = id;
		this.type = type;

		//2) METRE INIT COMPOSANT
		                jSeparator1 = new javax.swing.JSeparator();
		                jLabel1 = new javax.swing.JLabel();
		                jLabel2 = new javax.swing.JLabel();
		                jLabel5 = new javax.swing.JLabel();
		                jLabel4 = new javax.swing.JLabel();
		                jButton3 = new javax.swing.JButton();
		                jButton2 = new javax.swing.JButton();
		                jButton1 = new javax.swing.JButton();
		                jButton4 = new javax.swing.JButton();
		                jLabel6 = new javax.swing.JLabel();
		                jSeparator2 = new javax.swing.JSeparator();
		                jLabel7 = new javax.swing.JLabel();
		                jTextField1 = new javax.swing.JTextField();
		                jLabel3 = new javax.swing.JLabel();

		              //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		                jLabel1.setText("Détails de la livraison");

		if              (type == 1)
			                jLabel2.setText("Colis numéro " + id);
		if              (type == 2)
			                jLabel2.setText("Palette numéro " + id);
		if              (type == 3)
			                jLabel2.setText("Conteneur numéro " + id);

		                jLabel5.setText("Modifier le statut:");

		                jLabel4.setText("Adresse de livraison :");

		                jButton3.setText("Retour à l'accueil");
		                jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton4.setText("En cours de livraison");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					valider(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel6.setForeground(new java.awt.Color(255, 0, 0));
		jLabel6.setText("Non expédié");

		jLabel7.setText("Date d'expédition");

		jLabel3.setText("Contient des produits de type :");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
						  .addComponent(jSeparator1)
							.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						  .addComponent(jSeparator2)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addContainerGap())
							.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						      .addComponent(jLabel1)
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton1)
						   .addComponent(jButton2)))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton3)
						     .addComponent(jLabel6))
					      .addGap(9, 9, 9)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel2)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel4)
					      .addGap(59, 59, 59)
					      .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jLabel5)
					      .addGap(27, 27, 27)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel7)
						     .addComponent(jButton4)
							.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addGap(48, 48, 48))
			);




	}

	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}
	private void    valider(java.awt.event.ActionEvent evt) {
		try {
			//Requetes.? ? ?
			if (type == 1)
				fenetre.setType(302, id);
			if (type == 2)
				fenetre.setType(303, id);
			if (type == 3)
				fenetre.setType(304, id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuDetailTransporteur2 extends JPanel
{

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JLabel jLabel6;
	private         javax.swing.JLabel jLabel7;
	private         javax.swing.JLabel jLabel8;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JSeparator jSeparator2;
	private         javax.swing.JTextField jTextField1;


	private int     id;
	private int     type;



	public          ContenuDetailTransporteur2(Fenetre fen, int id, int type) {


		fenetre = fen;
		this.id = id;
		this.type = type;


		jLabel7 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jButton4 = new javax.swing.JButton();
		jLabel6 = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jButton1 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jLabel8 = new javax.swing.JLabel();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel7.setText("Date d'arrivée");

		jButton4.setText("Délivré");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					valider(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel6.setForeground(new java.awt.Color(0, 0, 255));
		jLabel6.setText("En cours de livraison");

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel1.setText("Détails de la livraison");

		jButton3.setText("Retour à l'accueil");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		if (type == 1)
			jLabel2.setText("Colis numéro " + id);
		if (type == 2)
			jLabel2.setText("Palette numéro " + id);
		if (type == 3)
			jLabel2.setText("Conteneur numéro " + id);

		jLabel3.setText("Date d'expédition");

		jLabel5.setText("Modifier le statut:");

		jLabel4.setText("Adresse de livraison :");

		jLabel8.setText("Contient des produits de type : ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
						  .addComponent(jSeparator1)
							.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						  .addComponent(jSeparator2)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							 .addContainerGap())
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(0, 0, Short.MAX_VALUE))
							.addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						      .addComponent(jLabel1)
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton1)
						   .addComponent(jButton2)))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton3)
						     .addComponent(jLabel6))
					      .addGap(9, 9, 9)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(10, 10, 10)
					      .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel8)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addGap(30, 30, 30)
					      .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel5)
					      .addGap(18, 18, 18)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel7)
						     .addComponent(jButton4)
							.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				      .addContainerGap(43, Short.MAX_VALUE))
			);






	}

	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}
	private void    valider(java.awt.event.ActionEvent evt) {
		try {
			//Requetes.? ? ?
			if (type == 1)
				fenetre.setType(302, id);
			if (type == 2)
				fenetre.setType(303, id);
			if (type == 3)
				fenetre.setType(304, id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuDetailTransporteur3 extends JPanel
{

	private Fenetre fenetre = null;



	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JLabel jLabel6;
	private         javax.swing.JLabel jLabel7;
	private         javax.swing.JLabel jLabel8;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JSeparator jSeparator2;
	private         javax.swing.JTextField jTextField1;


	private int     id;
	private int     type;


	public          ContenuDetailTransporteur3(Fenetre fen, int id, int type) {


		fenetre = fen;
		this.id = id;
		this.type = type;

		jLabel7 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jButton4 = new javax.swing.JButton();
		jLabel6 = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jButton1 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jLabel8 = new javax.swing.JLabel();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel7.setText("Date d'arrivée");

		jButton4.setText("Délivré");

		jLabel6.setForeground(new java.awt.Color(0, 153, 0));
		jLabel6.setText("Délivré le xxxxxxx");

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel1.setText("Détails de la livraison");

		jButton3.setText("Retour à l'accueil");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		if (type == 1)
			jLabel2.setText("Colis numéro " + id);
		if (type == 2)
			jLabel2.setText("Palette numéro " + id);
		if (type == 3)
			jLabel2.setText("Conteneur numéro " + id);

		jLabel3.setText("Date d'expédition");

		jLabel5.setText("Modifier le statut:");

		jLabel4.setText("Adresse de livraison");

		jLabel8.setText("Contient des produits de type : ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
						  .addComponent(jSeparator1)
							.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						  .addComponent(jSeparator2)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(0, 0, Short.MAX_VALUE))
							.addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						      .addComponent(jLabel1)
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton1)
						   .addComponent(jButton2)))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton3)
						     .addComponent(jLabel6))
					      .addGap(9, 9, 9)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel2)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel8)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addGap(29, 29, 29)
					      .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel5)
					      .addGap(18, 18, 18)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel7)
						     .addComponent(jButton4)
							.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				      .addContainerGap(43, Short.MAX_VALUE))
			);



	}

	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;


class ContenuDouane extends JPanel {

	private Fenetre fenetre = null;

	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JButton jButton5;
	private         javax.swing.JComboBox jComboBox1;
	private         javax.swing.JComboBox jComboBox2;
	private         javax.swing.JComboBox jComboBox3;
	private         javax.swing.JFormattedTextField jFormattedTextField1;
	private         javax.swing.JFormattedTextField jFormattedTextField2;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JSeparator jSeparator2;
	private         javax.swing.JTable jTable1;



	private long    dernierClicTable = 0;
	private int     dernierColonneTable = 0;
	private int     dernierLigneTable = 0;





	public          ContenuDouane(Fenetre fen) {

		fenetre = fen;


		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jButton1 = new javax.swing.JButton();
		jComboBox1 = new javax.swing.JComboBox();
		jButton5 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		jFormattedTextField1 = new javax.swing.JFormattedTextField();
		jFormattedTextField2 = new javax.swing.JFormattedTextField();
		jLabel2 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jComboBox2 = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jComboBox3 = new javax.swing.JComboBox();
		jButton4 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(850, 472));

		jScrollPane1.setBorder(null);

		jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
							 Requetes.douane1(),
							      new String[] {
			"Colis", "Palette", "Type de véhicule", "Numéro du véhicule", "Date de passage", "Verdict"
		}
		) {
			Class[] types = new Class[] {
				java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, false, false, false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				actionCaseCG(evt);
			}
		});
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(2).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(3).setResizable(false);
		jTable1.getColumnModel().getColumn(3).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(4).setResizable(false);
		jTable1.getColumnModel().getColumn(4).setPreferredWidth(5);
		jTable1.getColumnModel().getColumn(5).setResizable(false);
		jTable1.getColumnModel().getColumn(5).setPreferredWidth(5);

		jButton1.setText("Rechercher");
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				rechercher(evt);
			}
		});

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"Colis", "Palette", "Véhicule"
		}));

		jButton5.setText("Menu");
		jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				menu(evt);
			}
		});

		jButton3.setText("Suiv.");
		jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				suivante(evt);
			}
		});

		jButton2.setText("Prec.");
		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				precedente(evt);
			}
		});

		jLabel1.setText("Date de passage :");

		jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
		jFormattedTextField1.setText("jj/mm/aa");
		jFormattedTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				date_debut(evt);
			}
		});

		jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
		jFormattedTextField2.setText("jj/mm/aa");
		jFormattedTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				date_fin(evt);
			}
		});

		jLabel2.setText("et le");

		jLabel4.setText("Afficher");

		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"Tous verdicts", "En attente", "Valide", "NON Valide"
		}));

		jLabel3.setText("entre le");

		jLabel5.setText("Douane de " + Requetes.nomPaysDeDouane());

		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"Tous véhicules", "Conteneur", "Avion", "Camion"
		}));

		jButton4.setText("Déconnexion");
		jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				deconnexion(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGap(29, 29, 29)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(32, 32, 32)
					      .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(33, 33, 33)
					      .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel2)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel1)
					      .addGap(18, 18, 18)
					      .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton1))
						  .addComponent(jSeparator1)
						  .addComponent(jSeparator2)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
				     .addContainerGap(748, Short.MAX_VALUE))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						     .addComponent(jButton2)
						     .addComponent(jButton3)
						     .addComponent(jButton5)
						      .addComponent(jLabel5)
							.addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					      .addGap(18, 18, 18)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						      .addComponent(jLabel4)
							.addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jLabel3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						      .addComponent(jLabel2)
							.addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(115, 115, 115))
			);

	}

	private void    actionCaseCG(java.awt.event.MouseEvent evt) {

		long            moment = evt.getWhen();
		int             ligne = jTable1.getSelectedRow();
		int             colonne = jTable1.getSelectedColumn();

		if              ((dernierColonneTable == colonne) && (dernierLigneTable == ligne) && (moment - dernierClicTable < 300)) {


			//Integer in = jTable1.getValueAt(ligne, 0);
			int             nColis = Integer.parseInt(jTable1.getValueAt(ligne, 0).toString());
			              //jLabel1.setText("ouverture de la fenetre des details du colis numero : " + nColis);



			                fenetre.setType(106, nColis);
			/*
			 * Fenetre fenetre = new Fenetre(6);
			 * fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLO
			 * SE); fenetre.show();
			 */




			              //jTable1.getValueAt(ligne, 0)
			//              jButton1.setText("Damien!!! (" + colonne + "," + ligne + ") ->  " + obj);


		} else {
			dernierColonneTable = colonne;
			dernierLigneTable = ligne;
			dernierClicTable = moment;
		}

	}


	private void    rechercher(java.awt.event.MouseEvent evt) {
		Requetes.douane2( /* arguments */ );
	}

	private void    precedente(java.awt.event.MouseEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.MouseEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    deconnexion(java.awt.event.MouseEvent evt) {
		fenetre.connexion();
	}
	private void    menu(java.awt.event.MouseEvent evt) {
		//fenetre.co();
	}


	private void    date_debut(java.awt.event.MouseEvent evt) {
		jFormattedTextField1.setText("");
	}

	private void    date_fin(java.awt.event.MouseEvent evt) {
		jFormattedTextField2.setText("");
	}

	private void    jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO add your handling code here:
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;



class ContenuEmballeur extends JPanel {

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTable jTable1;


	public          ContenuEmballeur(Fenetre fen) {


		fenetre = fen;


		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel2.setText("Numéro d'emballeur: xxxxx ");

		jLabel1.setText("Nom Prenom");

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				//jButton1ActionPerformed(evt);
			}
		});

		                jButton2.setText("Suiv.");

		                jButton3.setText("Déconnexion");
		                jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				//jButton3ActionPerformed(evt);
			}
		});

		jLabel3.setText("Liste des commandes à traiter");

		jScrollPane1.setBorder(null);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
							    new Object[][] {
			{
				null, null
			},
			{
				null, null
			},
			{
				null, null
			},
			{
				null, null
			}
		},
			new String[] {
			"Numéro commande", "Date souhaitée"
		}
		) {
			Class[] types = new Class[] {
				java.lang.Integer.class, java.lang.Object.class
			};
			boolean[] canEdit = new boolean[] {
				false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setResizable(false);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
									    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
								  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
							.addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
							.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						      .addComponent(jLabel2)
							.addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel3)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
				     .addContainerGap(129, Short.MAX_VALUE))
			);




	}



}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;

//Probleme au niveau des boutons...



class ContenuExemple extends JPanel {

	private Fenetre fenetre = null;


	/** Champs  **/



	public          ContenuExemple(Fenetre fen) {
		/** Mettre en commentaire ca setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); **/

		fenetre = fen;

		/** Copier/Coller de l'interieur de initcomponent() **/

		/** mettre comme ca :
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		**/

	}

	/** Metre les autres fonction sauf le main bien sur **/


}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuMenuAutreDouane extends JPanel
{

	private Fenetre fenetre = null;


	              //METRE LES CHAMPS
	                private javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JButton jButton5;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JSeparator jSeparator1;





	public          ContenuMenuAutreDouane(Fenetre fen) {


		fenetre = fen;

		//2) METRE INIT COMPOSANT
		                jSeparator1 = new javax.swing.JSeparator();
		                jButton1 = new javax.swing.JButton();
		                jButton2 = new javax.swing.JButton();
		                jButton3 = new javax.swing.JButton();
		                jLabel1 = new javax.swing.JLabel();
		                jButton4 = new javax.swing.JButton();
		                jButton5 = new javax.swing.JButton();

		              //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		                jButton1.setText("Préc.");

		                jButton2.setText("Suiv.");

		                jButton3.setText("Retour à l'accueil");

		                jLabel1.setText("Douane de France");

		                jButton4.setText("Recherche avancée");

		                jButton5.setText("Statisques");
		                jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				//statistiques(evt);
			}
		});

		                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		                setLayout(layout);
		                layout.setHorizontalGroup(
							                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		     .               addGroup(layout.createSequentialGroup()
					   .               addContainerGap()
				  .               addComponent(jSeparator1))
		     .               addGroup(layout.createSequentialGroup()
					      .               addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		     .               addGroup(layout.createSequentialGroup()
					      .               addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .               addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .               addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
								       .               addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					  .               addGap(91, 91, 91)
					      .               addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
		     .               addGroup(layout.createSequentialGroup()
				       .               addGap(121, 121, 121)
					      .               addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								       .               addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								       .               addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
			      .               addGap(0, 0, Short.MAX_VALUE))
		);
		                layout.setVerticalGroup(
							                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		     .               addGroup(layout.createSequentialGroup()
					      .               addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		     .               addGroup(layout.createSequentialGroup()
					      .               addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				      .               addComponent(jButton1)
				     .               addComponent(jButton2))
					      .               addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				     .               addComponent(jButton3))
		     .               addGroup(layout.createSequentialGroup()
					  .               addGap(20, 20, 20)
				     .               addComponent(jLabel1)))
					  .               addGap(11, 11, 11)
					      .               addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					  .               addGap(83, 83, 83)
				      .               addComponent(jButton4)
					  .               addGap(18, 18, 18)
				      .               addComponent(jButton5)
		      .               addContainerGap(129, Short.MAX_VALUE))
		);


	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuMesTransports extends JPanel
{

	private Fenetre fenetre = null;



	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTable jTable1;

	private long    dernierClicTable = 0;
	private int     dernierColonneTable = 0;
	private int     dernierLigneTable = 0;





	public          ContenuMesTransports(Fenetre fen) {


		fenetre = fen;


		jSeparator1 = new javax.swing.JSeparator();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel3.setText("Historique des livraison");

		jScrollPane1.setBorder(null);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
							    new Object[][] {
			{
				"Palette", new Integer(428), null, "02/06/11", "04/06/11"
			}              ,
			{
				"Colis", new Integer(33493), null, "01/09/11", "27/09/11"
			}
		}              ,
		                new String[] {
			"Contenant", "Numéro ", "Pays", "Date souhaitée", "Date d'arrivée"
		}
		) {
			Class[] types = new Class[] {
				java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, false, false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setResizable(false);
		jTable1.getColumnModel().getColumn(3).setResizable(false);
		jTable1.getColumnModel().getColumn(4).setResizable(false);

		jLabel2.setText("Numéro de transporteur: xxxxx ");

		jLabel1.setText("Nom Prenom");

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Retour à l'accueil");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});


		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				actionCaseCG(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
						  .addComponent(jSeparator1)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel3)
				     .addContainerGap(456, Short.MAX_VALUE))
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
				      .addContainerGap(92, Short.MAX_VALUE))
			);



	}


	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}
	private void    actionCaseCG(java.awt.event.MouseEvent evt) {

		long            moment = evt.getWhen();
		int             ligne = jTable1.getSelectedRow();
		int             colonne = jTable1.getSelectedColumn();

		if              ((dernierColonneTable == colonne) && (dernierLigneTable == ligne) && (moment - dernierClicTable < 300)) {

			int             id = Integer.parseInt(jTable1.getValueAt(ligne, 1).toString());

			if              (jTable1.getValueAt(ligne, 0).toString().charAt(3) == 'i')
				                fenetre.setType(302, id);
			if              (jTable1.getValueAt(ligne, 0).toString().charAt(3) == 'e')
				                fenetre.setType(303, id);
			if              (jTable1.getValueAt(ligne, 0).toString().charAt(3) == 't')
				                fenetre.setType(304, id);
		} else {
			dernierColonneTable = colonne;
			dernierLigneTable = ligne;
			dernierClicTable = moment;
		}

	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuModificationCompteClient extends JPanel
{

	private Fenetre fenetre = null;



	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JLabel jLabel6;
	private         javax.swing.JLabel jLabel7;
	private         javax.swing.JPasswordField jPasswordField1;
	private         javax.swing.JPasswordField jPasswordField2;
	private         javax.swing.JPasswordField jPasswordField3;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTextField jTextField1;





	public          ContenuModificationCompteClient(Fenetre fen) {


		fenetre = fen;



		jLabel2 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jPasswordField1 = new javax.swing.JPasswordField();
		jPasswordField2 = new javax.swing.JPasswordField();
		jPasswordField3 = new javax.swing.JPasswordField();
		jButton4 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jLabel2.setText("Numéro de client: " + Requetes.getIdentifiant());

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Retour à l'accueil");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel4.setText("Nouveau login:");

		jLabel5.setText("Ancien mot de passe:");

		jLabel6.setText("Nouveau mot de passe:");

		jLabel7.setText("Confirmez mot de passe:");

		jButton4.setText("Valider");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGap(177, 177, 177)
					      .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(0, 0, Short.MAX_VALUE))
					  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				       .addContainerGap(28, Short.MAX_VALUE)
						    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
							.addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						  .addComponent(jTextField1)
					      .addComponent(jPasswordField1)
					      .addComponent(jPasswordField2)
							.addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
							      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									.addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addGap(79, 79, 79)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(18, 18, 18)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel4)
							.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						      .addComponent(jLabel5)
							.addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						      .addComponent(jLabel6)
							.addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						      .addComponent(jLabel7)
							.addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
					      .addComponent(jButton4)
					      .addGap(30, 30, 30))
			);
	}


	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}




}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuSuiviCommandeClient extends JPanel {

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTable jTable1;

	public          ContenuSuiviCommandeClient(Fenetre fen) {


		fenetre = fen;

		jLabel2 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel3 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel2.setText("Numéro de client: " + Requetes.getIdentifiant());

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jScrollPane1.setBorder(null);

		jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				       Requetes.commandeEnCoursDeIdClient(),
							      new String[] {
			"Numéro de commande", "Date"
		}
		) {
			Class[] types = new Class[] {
				java.lang.Integer.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean[] {
				false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(0).setPreferredWidth(6);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setPreferredWidth(6);

		jLabel3.setText("Liste des commandes");

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});



		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Retour à l'accueil");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					accueil(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						 .addComponent(jScrollPane1)
						  .addComponent(jSeparator1)
							.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
				    .addGroup(layout.createSequentialGroup()
					      .addGap(187, 187, 187)
					      .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addGap(12, 12, 12)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
				     .addContainerGap(102, Short.MAX_VALUE))
			);




	}

	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    accueil(java.awt.event.ActionEvent evt) {
		fenetre.racine();
	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;


class ContenuTransporteur extends JPanel
{

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JButton jButton5;
	private         javax.swing.JComboBox jComboBox1;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JScrollPane jScrollPane1;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JSeparator jSeparator2;
	private         javax.swing.JTable jTable1;

	private long    dernierClicTable = 0;
	private int     dernierColonneTable = 0;
	private int     dernierLigneTable = 0;

	private int     type = 1;
	              //colis pallette ou conteneurs




	                public ContenuTransporteur(Fenetre fen, int type) {


		fenetre = fen;
		this.type = type;

		jLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel2 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jButton5 = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JSeparator();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel4 = new javax.swing.JLabel();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setFocusable(false);

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jLabel2.setText("Numéro de transporteur: " + Requetes.getIdentifiant());

		jButton1.setText("Préc.");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					precedente(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					suivante(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton3.setText("Déconnexion");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					deconnexion(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jButton4.setText("Historique de mes livraisons");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					historique(evt);
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		jLabel3.setText("Afficher les:");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
			"Colis", "Palette", "Conteneur"
		}));

		jButton5.setText("OK");

		jScrollPane1.setBorder(null);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
						   Requetes.transporteur1(),
							      new String[] {
			"Numéro de colis", "Type", "Pays", "Code postal", "Date souhaitée"
		}
		) {
			Class[] types = new Class[] {
				java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, false, false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		jTable1.getColumnModel().getColumn(0).setResizable(false);
		jTable1.getColumnModel().getColumn(1).setResizable(false);
		jTable1.getColumnModel().getColumn(2).setResizable(false);
		jTable1.getColumnModel().getColumn(3).setResizable(false);
		jTable1.getColumnModel().getColumn(4).setResizable(false);

		jLabel4.setText("Liste des colis à transporter");

		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void     mouseClicked(java.awt.event.MouseEvent evt) {
				actionCaseCG(evt);
			}
		});


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						  .addComponent(jSeparator1)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addContainerGap())
						  .addComponent(jSeparator2)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					      .addGap(0, 0, Short.MAX_VALUE)
								  .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel3)
							.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						    .addComponent(jButton5))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(1, 1, 1)
					      .addComponent(jLabel4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
				      .addContainerGap(22, Short.MAX_VALUE))
			);



	}
	private void    precedente(java.awt.event.ActionEvent evt) {
		fenetre.precedentHistorique();
	}

	private void    suivante(java.awt.event.ActionEvent evt) {
		fenetre.suivantHistorique();
	}

	private void    deconnexion(java.awt.event.ActionEvent evt) {
		fenetre.connexion();
	}
	private void    historique(java.awt.event.ActionEvent evt) {
		fenetre.setType(301, -1);
	}
	private void    actionCaseCG(java.awt.event.MouseEvent evt) {

		long            moment = evt.getWhen();
		int             ligne = jTable1.getSelectedRow();
		int             colonne = jTable1.getSelectedColumn();

		if              ((dernierColonneTable == colonne) && (dernierLigneTable == ligne) && (moment - dernierClicTable < 300)) {

			int             id = Integer.parseInt(jTable1.getValueAt(ligne, 0).toString());

			if              (type == 1)
				                fenetre.setType(302, id);
			if              (type == 2)
				                fenetre.setType(303, id);
			if              (type == 3)
				                fenetre.setType(304, id);
		} else {
			dernierColonneTable = colonne;
			dernierLigneTable = ligne;
			dernierClicTable = moment;
		}

	}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;
import          java.sql.*;

//Probleme au niveau des boutons...



class ContenuValidationCommande extends JPanel {

	private Fenetre fenetre = null;


	private         javax.swing.JButton jButton1;
	private         javax.swing.JButton jButton2;
	private         javax.swing.JButton jButton3;
	private         javax.swing.JButton jButton4;
	private         javax.swing.JLabel jLabel1;
	private         javax.swing.JLabel jLabel2;
	private         javax.swing.JLabel jLabel3;
	private         javax.swing.JLabel jLabel4;
	private         javax.swing.JLabel jLabel5;
	private         javax.swing.JScrollPane jScrollPane2;
	private         javax.swing.JSeparator jSeparator1;
	private         javax.swing.JTable jTable1;
	private         javax.swing.JTextField jTextField1;



	public          ContenuValidationCommande(Fenetre fen) {


		fenetre = fen;


		jButton2 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jButton1 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jButton4 = new javax.swing.JButton();

		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jButton2.setText("Suiv.");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void     actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jLabel1.setText(Requetes.nom() + " " + Requetes.prenom());

		jLabel2.setText("Numéro de client: " + Requetes.getIdentifiant());

		jButton3.setText("Retour à l'accueil");


		jScrollPane2.setBorder(null);

		jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jTable1.setModel(new javax.swing.table.DefaultTableModel(
							  Requetes.getTab(),
							      new String[] {
			"Nom", "Prix", "Quantité"
		}
		) {
			Class[] types = new Class[] {
				java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
			};
			boolean[] canEdit = new boolean[] {
				false, false, false
			};

			public Class    getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean  isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.setColumnSelectionAllowed(true);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(jTable1);

		jButton1.setText("Préc.");

		jLabel3.setText("Détail de la commande numéro xxxxx");

		jLabel4.setText("Date de livraison souhaitée: le xxxxx");

		jLabel5.setText("Coût total de la commande: ");

		jTextField1.setEditable(false);
		jTextField1.setText("(cout commande)");

		jButton4.setText("Confirmer");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
					  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						  .addComponent(jSeparator1)
						 .addComponent(jScrollPane2)
							.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				    .addGroup(layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						     .addComponent(jLabel2))
					      .addGap(84, 84, 84)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								  .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
								  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))))
			);
		layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				    .addGroup(layout.createSequentialGroup()
					      .addContainerGap()
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel1)
						     .addComponent(jButton1)
						    .addComponent(jButton2))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel2)
						    .addComponent(jButton3))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jLabel3)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addComponent(jLabel4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						      .addComponent(jLabel5)
							.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					      .addComponent(jButton4)
					      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					      .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
					      .addGap(32, 32, 32))
			);



	}


	private void    jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		//GEN - FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
	}             //GEN - LAST:event_jButton2ActionPerformed


}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;


class Fenetre extends JFrame {



	private Toolkit k = Toolkit.getDefaultToolkit();
	private Dimension tailleEcran = k.getScreenSize();
	private int     largeurEcran = tailleEcran.width;
	private int     hauteurEcran = tailleEcran.height;
	private int     type = 0;

	private int     tailleHistorique = 10;
	private Contenu racine = null;
	private         Contenu[] historique = new Contenu[tailleHistorique];
	private int     pointeur = -1;



	JPanel          monContenu = null;
	String          titre = "";

	Container       leContenant = null;


	public int      getLargeur() {
		return largeurEcran;
	}
	public int      getHauteur() {
		return hauteurEcran;
	}

	public          Fenetre(int type) {
		setType(type, -1);
	}

	public void     initialise(int type, int id) {
		if (type == 0) {
			//fenetre de connection

			titre = "Connection";
			setTitle(titre);
			setSize(450, 300);
			racine = null;

			//ou apparait la fenetre
			setLocation((largeurEcran - 450) / 2, (hauteurEcran - 300) / 2);

			monContenu = new ContenuConnexion(this);
			leContenant = getContentPane();
			leContenant.add(monContenu);
			//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");


		}
		if (type == 1) {
			//Douane
			titre = "Douane";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDouane(this);
			//monContenu = new ContenuDetailColis(this, 111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);

		}
		if              (type == 2) {
			//Emballeur
			titre = "Emballeur";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
			setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuEmballeur(this);
			//monContenu = new ContenuDetailColis(this, 111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if              (type == 3) {
			//Transporteur
			titre = "Transporteur";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuTransporteur(this, 1);
			//monContenu = new ContenuDetailColis(this, 111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 4) {
			//Client

				titre = "Client";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuClient(this);
			//monContenu = new ContenuDetailColis(this, 111);
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 5) {
			//Gerant
		}
		if (type == 101) {
			//Menu autre Douane
				titre = "Détails du colis " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuMenuAutreDouane(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 106) {
			//details colis
				if (Requetes.aUnVerdict(id))
				initialise(111, id);
			else
				initialise(110, id);
		}
		if (type == 110) {
			//details colis pas verifi � �


				titre = "Détails du colis " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);


			monContenu = new ContenuDetailColis(this, id);


			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);


		}
		if (type == 111) {
			//details colis deja verifi � �


				titre = "Détails du colis " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);


			monContenu = new ContenuDetailColis2(this, id);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);


		}
		if (type == 301) {
			//Les transports du transporteur
				titre = "Mes Transports";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuMesTransports(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 302) {
			//orientation vers les details des transports pr un colis
				if (Requetes.coliEstExpedie(id))
				setType(306, id);
			else
				setType(305, id);
		}
		if (type == 303) {
			//orientation vers les details des transports pour une palette
				// si pas de transporteur->308
				// sinon vers 307
		}
		if (type == 304) {
			//orientation vers les details des transports pour un conteneur
				// si pas de transporteur->309
				// sinon vers 316
		}
		if (type == 305) {
			//detail transporteur(des colis pas pris)
				titre = "Detail du colis " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur(this, id, 1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 306) {
			//orientation vers les details des transports 2em e degres pour les colis
				// si pas de arriv � �->310
				// sinon vers 311
		}
		if (type == 307) {
			//orientation vers les details des transports 2em e degres pour les palettes
				// si pas de arriv � �->312
				// sinon vers 314
		}
		if (type == 308) {
			//detail transporteur(des palette pas pris)
				titre = "Detail de la palette " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur(this, id, 2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 309) {
			//detail transporteur(des conteneurs pas pris)
				titre = "Detail du conteneur " + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur(this, id, 3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 310) {
			//detail transporteur(des colis pris pas encore arriv � �s)
				titre = "Detail du colis" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur2(this, id, 1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 311) {
			//detail transporteur(des colis arriv � �s)
				titre = "Detail du colis" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur3(this, id, 1);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 312) {
			//detail transporteur(des palettes pris pas encore arriv � �s)
				titre = "Detail de la palette" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur2(this, id, 2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 313) {
			//detail transporteur(des conteneurs pris pas encore arriv � �s)
				titre = "Detail du conteneur" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur2(this, id, 3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 314) {
			//detail transporteur(des palettes arriv � �s)
				titre = "Detail de la palette" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur3(this, id, 2);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 315) {
			//detail transporteur(des conteneur arriv � �s)
				titre = "Detail du conteneur" + id;
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuDetailTransporteur3(this, id, 3);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 316) {
			//orientation vers les details des transports 2em e degres pour les conteneurs
				// si pas de arriv � �->313
				// sinon vers 315
		}
		if (type == 401) {
			//Modification du compte client
				titre = "Modifier votre compte";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuModificationCompteClient(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 402) {
			//Appercu des commande du client
				titre = "Vos commandes";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuSuiviCommandeClient(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 403) {
			//Validation de la commande du client
				titre = "Valider votre commande";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuValidationCommande(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 407) {
			//Commande
				titre = "Faire une commande";
			setTitle(titre);
			setSize(largeurEcran, hauteurEcran);

			//ou apparait la fenetre
				setSize(largeurEcran - 1, hauteurEcran - 1);
			setSize(largeurEcran, hauteurEcran);
			setLocation(0, 0);

			monContenu = new ContenuCommande(this);

			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.add(monContenu);
		}
		if (type == 408) {
			//suivi des commandes




		}
		if (type == 409) {
			//compt client


		}
	}

	public int      getType() {
		return type;
	}
	public void     setType(int n, int id) {
		//System.out.println("setType(" + n + "," + id + ")");

		type = n;
		leContenant = getContentPane();
		leContenant.removeAll();
		leContenant.repaint();
		initialise(n, id);


		if (racine == null) {
			if (type != 0)
				setRacine(titre, type, id);
		} else {
			if ((n == 6) || (n == 110) || (n == 111))
				ajoutHistorique(titre, 6, id);
			else
				ajoutHistorique(titre, type, id);
		}
		//afficheHistorique();
	}
	public void     setRacine(String ti, int ty, int id) {
		racine = new Contenu(ti, ty, id);
	}
	public void     connexion() {
		leContenant = getContentPane();
		leContenant.removeAll();

		leContenant.repaint();
		initialise(0, 0);

	}
	public void     racine() {
		/*
					leContenant = getContentPane();
					leContenant.removeAll();
		
					leContenant.repaint();
		
		
					setTitle(racine.getTitre());
		
					//ou apparait la fenetre
					//setLocation(0,0);
		
					monContenu = racine.getJPanel();
					leContenant = getContentPane();
					leContenant.add(monContenu);
					type = racine.getType();
					*/
		setType(racine.getType(), racine.getId());
	}
	public void     ajoutHistorique(String ti, int ty, int id) {

		if (pointeur < tailleHistorique - 1) {
			if ((pointeur != -1) && (historique[pointeur].getType() == ty) && (historique[pointeur].getId() == id)) {
			} else {
				pointeur++;


				if (historique[pointeur] != null) {
					System.out.println("ici");
					for (int i = pointeur; i < tailleHistorique; i++) {
						historique[i] = null;
					}
				}
				System.out.println("oui");
				historique[pointeur] = new Contenu(ti, ty, id);
			}
		} else {

		}
	}
	public void     suivantHistorique() {
		if ((pointeur < tailleHistorique - 1) && (historique[pointeur + 1] != null)) {

			pointeur++;
			/*
			leContenant = getContentPane();
			leContenant.removeAll();
			leContenant.repaint();

			setTitle(historique[pointeur].getTitre());

			//ou apparait la fenetre
			//setLocation(0,0);

			monContenu = historique[pointeur].getJPanel();
			leContenant = getContentPane();
			leContenant.add(monContenu);
			type = historique[pointeur].getType();*/
			setType(historique[pointeur].getType(), historique[pointeur].getId());
		} else {
		}
	}
	public void     precedentHistorique() {
		if (pointeur == 0) {
			pointeur--;
			racine();
		} else if (pointeur > 0) {

			pointeur--;
			/*
						leContenant = getContentPane();
						leContenant.removeAll();
						leContenant.repaint();
			
						setTitle(historique[pointeur].getTitre());
			getType
						//ou apparait la fenetre
						//setLocation(0,0);
			
						monContenu = historique[pointeur].getJPanel();
						leContenant = getContentPane();
						leContenant.add(monContenu);
						type = historique[pointeur].getType();*/

			initialise(historique[pointeur].getType(), historique[pointeur].getId());
		} else {
		}
	}
	public void     afficheHistorique() {
		System.out.print(" (" + pointeur + ") -> ");
		for (int i = 0; i < tailleHistorique; i++) {
			System.out.print(" [" + i + "]");
			if (historique[i] == null)
				System.out.print("NULL");
			else
				System.out.print(historique[i].getType());
			System.out.print("]");
		}
		                System.out.print("\n");

	}
}
import          java.awt.*;
import          java.awt.event.*;
import          javax.swing.*;



public class    FenetreExec {
	public static void main(String[] args) {
		int             type = 0;
		Fenetre         fenetre = nouvelleFenetre(type);
		/*
			while (type == fenetre.getType()){}
		
			fenetre.setVisible(false);
			type = fenetre.getType();
			fenetre.dispose();
			
			fenetre = nouvelleFenetre(type);*/


	}

	public static Fenetre nouvelleFenetre(int type) {
		Fenetre         fenetre = new Fenetre(type);
		                fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		                fenetre.show();
		                return fenetre;
	}
import          java.sql.*;

public class    Remplissage {


	private static Connection conn = null;
	private static Statement stmt = null;

	private static String loginBD = "farah";
	              //"marielle";
	private static String mdpBD = "swan";
	              //"azerty";



	static          String[] vo = {"a", "e", "i", "o", "u", "y", "ou", "oi", "au", "a", "e", "e", "i", "o", "u", "e"};
	static          String[] co = {"z", "r", "b", "t", "n", "m", "l", "r", "tr", "t", "p", "pr", "pt", "s", "ss", "ps", "d", "dr", "f", "ff", "g", "j", "k", "ll", "l", "m", "w", "x", "c", "cl", "cr", "v", "b", "bl", "br", "n", "ch", "kh"};


	public static void remplis() {
		ajoutAleatoirePays();
		ajoutAleatoireCP();
		ajoutAleatoireClient();
		ajoutAleatoireDouane();
		ajoutAleatoireProduit();
	}

	public static String nomAleatoire() {
		int             nbLetres = (int) (Math.random() * (4 - 2)) + 2;
		String          s = "";
		if              ((int) (Math.random() * (2 - 0)) == 0)
			                s += co[(int) (Math.random() * (vo.length - 0)) + 0];
		for             (int i = 0; i < nbLetres; i++) {
			s += vo[(int) (Math.random() * (vo.length - 0)) + 0];
			s += co[(int) (Math.random() * (vo.length - 0)) + 0];
		}
		                return s;
	}
	public static int alea(int m, int M) {
		return ((int) (Math.random() * (M - m)) + m);
	}

	public static int nbPays() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from pays;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static int nbProduit() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from produit;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static int nbDouane() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from douane;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}

	public static int nbCP() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from code_p;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static int nbTransporteur() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from transporteur;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static int nbEmballeur() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from emballeur;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static int nbClient() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();

			String          requete = "select count(*) from client;";
			ResultSet       res;
			                res = stmt.executeQuery(requete);
			                res.next();
			                return res.getInt(1);
		}
		catch(Exception e) {
			System.out.println(e);
			return 0;


		}
	}
	public static void ajoutAleatoirePays() {
		int             souhait = 50;
		String          s = "";
		while           (nbPays() < souhait) {


			for (int i = 0; i < souhait - nbPays(); i++) {
				s = nomAleatoire();
				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          requete = "insert into PAYS(NOM) values ('" + s + "-PA');";
					                stmt.executeUpdate(requete);

				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}

	public static void ajoutAleatoireCP() {
		int             souhait = 100;
		while           (nbCP() < souhait) {


			for (int i = 0; i < souhait - nbCP(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          requete = "insert into CODE_P(CP) values ('" + alea(10000, 99999) + "'); ";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}

	public static void ajoutAleatoireClient() {
		int             nbPays = nbPays();
		int             nbCP = nbCP();
		int             souhait = 250;
		while           (nbClient() < souhait) {


			for (int i = 0; i < souhait - nbClient(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          pc = nomAleatoire();
					String          nc = nomAleatoire();

					String          requete = "insert into CLIENT(MAIL,NOM,PRENOM,ID_PAY,ADRESSE,ID_CP,MDP)values ('" + pc + "." + nc + "@gmail.com','" + nc + "-NC','" + pc + "-PC'," + alea(1, nbPays) + ",'" + alea(1, 500) + " rue " + nomAleatoire() + "'," + alea(1, nbCP) + ",'11111111');";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}

	public static void ajoutAleatoireDouane() {
		int             nbPays = nbPays();
		int             i;
		while           (nbPays() > nbDouane()) {
			if (alea(0, 1) == 0)
				i = alea(1, nbPays);
			else
				i = 1;	/*
			       try{
				       Class.forName("org.postgresql.Driver");
				       conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+loginBD, loginBD, mdpBD);
				       stmt = conn.createStatement();
       
       
				       String requete = "update douane_id_dou_seq set last_value ="+i+" ;";
       
       
				       stmt.executeUpdate(requete);
			       }
			       catch(Exception e){
				       System.out.println(e);
       
			       }*/
			for (int k = i; k <= nbPays; k++) {
				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();


					String          requete = "insert into DOUANE(ID_DOU,MAIL,ID_PAY,MDP) values (" + i + ",'douane" + i + "@douane.fr'," + i + ",'11111111');";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}

	public static void ajoutAleatoireEmballeur() {
		int             souhait = 80;
		while           (nbEmballeur() < souhait) {


			for (int i = 0; i < souhait - nbEmballeur(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          pe = nomAleatoire();
					String          ne = nomAleatoire();

					String          requete = "insert into EMBALLEUR(MAIL,NOM,PRENOM,MDP) values ('" + pe + "." + ne + "@gmail.com','" + ne + "-NE','" + pe + "-PE','11111111');";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}
	public static void ajoutAleatoireTransporteur() {
		int             souhait = 80;
		while           (nbTransporteur() < souhait) {


			for (int i = 0; i < souhait - nbTransporteur(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          pe = nomAleatoire();
					String          ne = nomAleatoire();

					String          requete = "insert into TRANSPORTEUR (MAIL,NOM,PRENOM,MDP) values ('" + pe + "." + ne + "@gmail.com','" + ne + "-NE','" + pe + "-PE','11111111');";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}
	public static void ajoutAleatoireCommande1() {
		//50 // 20 // 180
		int             souhait = 20;
		int             nbClient = nbClient();
		while           (nbTransporteur() < souhait) {


			for (int i = 0; i < souhait - nbTransporteur(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					String          requete = "insert into COMMANDE (ID_CLI,ID_EMB,ID_DOU,LIVRAISON,DELIVREE) values (" + alea(1, nbClient) + ",null,null,current_date+" + alea(10, 50) + ",false);";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}




	public static void ajoutAleatoireProduit() {
		int             souhait = 200;
		int             nbProduit = nbProduit();
		int             n = 0;
		String          s = "";
		while           (nbProduit() < souhait) {


			for (int i = 0; i < souhait - nbProduit(); i++) {

				try {
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
					stmt = conn.createStatement();

					n = alea(0, 4);
					if (n < 2)
						s = "N";
					else if (n == 3)
						s = "D";
					else
						s = "F";

					String          requete = "insert into PRODUIT(NOM,PRIX,PTYPE) values ('" + nomAleatoire() + "-PR'," + alea(10, 10000) + ".00,'" + s + "');";


					                stmt.executeUpdate(requete);
				}
				                catch(Exception e) {
					System.out.println(e);

				}
			}
		}
	}
import          java.sql.*;

public class    Requetes {

	private static boolean nonSQL = false;



	private static Connection conn = null;
	private static Statement stmt = null;

	private static String mail = null;
	private static String mdp = null;
	private static int identifiant = -1;

	private static int typeConnexion = -1;
	              //gerant, client etc..
	                private static boolean ok = false;


	private static String loginBD = "farah";
	              //"marielle";
	private static String mdpBD = "swan";
	              //"azerty";

	private static  Object[][] tab = null;






	/*
	 * Connexion a la base public Requetes(String login, String mdp)
	 * throws SQLException,ClassNotFoundException{
	 * Class.forName("org.postgresql.Driver"); conn =
	 * DriverManager.getConnection("jdbc:postgresql://localhost/"+login,
	 * login, mdp); stmt = conn.createStatement(); }
	 */



	public static void setTab(Object[][] t) {
		tab = t;
	}
	public static   Object[][] getTab() {
		return tab;
	}

	              //Connexion d 'un utilisateur
	//              V � �rifi � �e
	                public static boolean connexion(String email, String mot, int typeCompte) throws SQLException,
	                ClassNotFoundException {
		typeConnexion = typeCompte;
		if (nonSQL)
			return true;
		else {

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			//System.out.println("Connexion en cours");
			String          table = "", cle = "";

			switch          (typeCompte) {
			case 1:
				table = "DOUANE";
				cle = "ID_DOU";
				break;
			case 2:
				table = "EMBALLEUR";
				cle = "ID_EMB";
				break;
			case 3:
				table = "TRANSPORTEUR";
				cle = "ID_TRA";
				break;
			case 4:
				table = "CLIENT";
				cle = "ID_CLI";
				break;
			default:
				table = "GERANT";
				cle = "ID_GER";
			}

			String          requete = "select " + cle + " from " + table
			+ " where MAIL = '" + email + "' and MDP = '" + mot + "';";
			              //System.out.println("Connexion en cours");
			ResultSet       res = stmt.executeQuery(requete);
			              //System.out.println("Connexion en cours");
			              //System.out.println(res);
			if              (res.next()) {
				//System.out.println("utilisateur trouvé");
				mail = email;
				mdp = mot;
				ok = true;
				identifiant = idDeMailType(mail, typeCompte);
				//System.out.println(identifiant);
				return true;
			} else {
				System.out.println("utilisateur non trouvé !!!");

				return false;
			}
		}
	}

	//Verifi � �e
		public static   Object[][] douane1() {


		if (nonSQL)
			return new Object[][] {
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			},
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			},
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			},
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			},
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			}
			};
		else {
			//System.out.println("dansRequetes.idDeMailType(" france @ douane.fr ",1) le ELSE");

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			                catch(Exception e) {
				//System.out.println("erreur ici");
				return null;
			}

			int             idPays = idPaysDeIdDouane(identifiant);
			//System.out.println(idPays);

			//A completer pr avoir le nb de lignes !
				String requete = "select count(*) from COLIS, PALETTE, TYPE_VEH, VEHICULE where(COLIS.ID_COM= (select COMMANDE.ID_COM from COMMANDE where COMMANDE.ID_CLI= (select CLIENT.ID_CLI from CLIENT where CLIENT.ID_PAY=" + idPays + "))) and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH);";
			ResultSet       res;
			int             nbLigne = 0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
				//System.out.println(nbLigne);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici2");
				return null;
			}


			//A completer pr avoir le tableau
				requete = "select COLIS.ID_COL, PALETTE.ID_PAL, TYPE_VEH.NOM, VEHICULE.ID_VEH, VEHICULE.PASSAGE,COLIS.VERDICT from COLIS, PALETTE, TYPE_VEH, VEHICULE where(COLIS.ID_COM= (select COMMANDE.ID_COM from COMMANDE where COMMANDE.ID_CLI= (select CLIENT.ID_CLI from CLIENT where CLIENT.ID_PAY=" + idPays + "))) and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH);";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici3");
				return null;

			}

			Object          s;
			Object[][] tab = new Object[nbLigne][6];
			try {
				for (int i = 0; i < nbLigne; i++) {
					res.next();

					tab[i][0] = new Integer(res.getInt(1));
					tab[i][1] = new Integer(res.getInt(2));
					tab[i][2] = res.getString(3);

					tab[i][3] = new Integer(res.getInt(4));
					tab[i][4] = res.getString(5);
					s = res.getObject(6);
					//System.out.println("s" + s);
					if (s == null)
						tab[i][5] = "En attente";
					else {
						//System.out.println("s +" + s.toString().charAt(0));
						if (s.toString().charAt(0) == 'f')
							tab[i][5] = "NON Valide";
						if (s.toString().charAt(0) == 't')
							tab[i][5] = "Valide";
						if ((s.toString().charAt(0) != 't') && (s.toString().charAt(0) != 'f'))
							tab[i][5] = "Erreur";
					}

				}
			}
			catch(Exception e) {
				System.out.println(e);

			}
			//System.out.println("->" + tab[0][5]);
			return tab;
		}
	}
	public static int idDeMailType(String mail, int typeCompte) {
		if (nonSQL)
			return 1;
		else {



			try {
				switch (typeCompte) {
				case 1:
					return idDeDouane(mail);
				case 2:
					return idDeEmballeur(mail);
				case 3:
					return idDeTransporteur(mail);
				case 4:
					return idDeClient(mail);
				default:
					return idDeGerant(mail);
				}
			}
			                catch(Exception e) {
				System.out.println(e);
				return -1;
			}

		}
	}
	public static void douane2() {
		//a faire
	}
	public String   paysDeIdPays(int id) throws SQLException, ClassNotFoundException {
		if (nonSQL)
			return "France";
		else {

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select NOM from PAYS where ID_PAY=" + id + ";";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return null;
			}
		}
	}
	public static int idPaysDeIdDouane(int id) {
		if (nonSQL)
			return 1;
		else {
			try {

				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select ID_PAY from DOUANE where ID_DOU=" + id + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static String nomPaysDeDouane() {
		return nomPaysDeIdPays(idPaysDeIdDouane(identifiant));
	}
	public static String nomPaysDeIdPays(int id) {
		if (nonSQL)
			return "France";
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select NOM from PAYS where ID_PAY=" + id + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return null;
			}


		}
	}
	public static int idDeDouane(String mail) throws SQLException,
	                ClassNotFoundException {
		if (nonSQL)
			return 1;
		else {

			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select ID_DOU from DOUANE where MAIL='" + mail + "';";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeEmballeur(String mail) throws SQLException,
	                ClassNotFoundException {
		if (nonSQL)
			return 1;
		else {


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select ID_EMB from EMBALLEUR where MAIL='" + mail + "';";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}
		}
	}
	public static int idDeTransporteur(String mail) throws SQLException,
	                ClassNotFoundException {
		if (nonSQL)
			return 1;
		else {


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select ID_TRA from TRANSPORTEUR where MAIL='" + mail + "';";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeClient(String mail) throws SQLException,
	                ClassNotFoundException {
		if (nonSQL)
			return 1;
		else {


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select ID_CLI from CLIENT where MAIL='" + mail + "';";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int idDeGerant(String mail) throws SQLException,
	                ClassNotFoundException {
		if (nonSQL)
			return 1;
		else {


			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
			stmt = conn.createStatement();



			String          requete = "select ID_GER from GERANT where MAIL='" + mail + "';";
			ResultSet       res;

			                try {
				res = stmt.executeQuery(requete);
				res.next();
				return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static int paletteDuColis(int idColis) {
		return 1234;
	}
	public static String verdict(int idColis) {
		if (nonSQL)
			return "Valide";
		else {

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select VERDICT from COLIS where ID_COL=" + idColis + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				Object          o = res.getObject(1);

				if              (o == null)
					                return "En attente";
				if              (o.toString().charAt(0) == 't')
					                return "Valide";
				if              (o.toString().charAt(0) == 'f')
					                return "NON Valide";
				else
					                return "erreur";
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return "erreur";
			}

		}

	}
	public static int idPaletteDeIdColis(int idColis) {
		if (nonSQL)
			return idColis * 3;
		else {

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select ID_PAL from COLIS where ID_COL=" + idColis + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static String nomVehiculeDeIdColis(int idColis) {
		if (nonSQL)
			return "conteneur";
		else {

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select TYPE_VEH.NOM from TYPE_VEH where TYPE_VEH.ID_TYPE_VEH=(select VEHICULE.ID_TYPE_VEH from VEHICULE where VEHICULE.ID_VEH=(select PALETTE.ID_VEH from PALETTE where PALETTE.ID_PAL=(select COLIS.ID_PAL from COLIS where COLIS.ID_COL=" + idColis + ")));";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return null;
			}

		}
	}
	public static int idVehiculeDeIdColis(int idColis) {
		if (nonSQL)
			return idColis * 5;
		else {

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select PALETTE.ID_VEH from PALETTE where PALETTE.ID_PAL=(select COLIS.ID_PAL from COLIS where COLIS.ID_COL=" + idColis + ");";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getInt(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return -1;
			}

		}
	}
	public static boolean aUnVerdict(int idColis) {
		if (nonSQL) {
			if (idColis % 2 == 0)
				return false;
			else
				return true;
		} else {
			try {
				String          s = verdict(idColis);
				if              ((s.charAt(0) == 'V') || (s.charAt(0) == 'N'))
					                return true;
				else
					                return false;
			}
			                catch(Exception e) {
				System.out.println(e);
				return false;
			}
		}
	}
	public static String motifDeIdColis(int idColis) {
		if (nonSQL)
			return "Je ne sais pas trop pourquoi\n mais j'aime la france.";
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "select MOTIF from COLIS where ID_COL=" + idColis + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	}








	public static   Object[][] detailColis1(int idColis) {


		if (nonSQL)
			return new Object[][] {
			{
				"Brosse à dent", new Float(1.5), new Integer(200)
			},
			{
				"Fil dentaire", new Float(3.2), new Integer(400)
			},
			{
				null, null, null
			},
			{
				null, null, null
			}
			};
		else {


			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			                catch(Exception e) {
				System.out.println(e);
				return null;
			}



			//A completer pr avoir le nb de lignes !
				String requete = "select count(*) from PRODUIT, DETAIL_COL, COLIS, PALETTE, VEHICULE, TYPE_VEH where PRODUIT.ID_COL=" + idColis + " and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH) and (COLIS.ID_COL=" + idColis + ") and (DETAIL_COL.ID_COL= COLIS.ID_COL);";
			ResultSet       res;
			int             nbLigne = 0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
			}
			catch(Exception e) {
				//Erreur
					System.out.println(e);
				return null;
			}


			//A completer pr avoir le tableau
				requete = "select PRODUIT.NOM, PRODUIT.PRIX, DETAIL_COL.QUANTITE from PRODUIT, DETAIL_COL, COLIS, PALETTE, VEHICULE, TYPE_VEH where PRODUIT.ID_COL=" + idColis + " and (COLIS.ID_PAL=PALETTE.ID_PAL) and (PALETTE.ID_VEH=VEHICULE.ID_VEH) and (VEHICULE.ID_TYPE_VEH=TYPE_VEH.ID_TYPE_VEH) and (COLIS.ID_COL=" + idColis + ") and (DETAIL_COL.ID_COL= COLIS.ID_COL);";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e) {
				//Erreur
					System.out.println(e);
				return null;
			}

			String          s;
			Object[][] tab = new Object[nbLigne][3];
			for (int i = 0; i < nbLigne; i++) {
				try {
					res.next();
					tab[i][0] = res.getString(1);
					tab[i][1] = new Float(res.getFloat(2));
					tab[i][2] = new Integer(res.getInt(3));
				}
				catch(Exception e) {
					System.out.println(e);
					return null;
				}
			}

			return tab;
		}
	}


	public static void modifColisDouane(int idColis, char c, String s) {
		if (c == 'N') {
			colisNONValide(idColis);
			modifVerdictColis(idColis, s);
		}
		if (c == 'V') {
			colisValide(idColis);
			modifVerdictColis(idColis, "");
		}
	}
	public static void colisValide(int idColis) {
		if (nonSQL) {
		} else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "update COLIS set VERDICT=true where COLIS.ID_COL=" + idColis + ";";


				                stmt.executeUpdate(requete);
			}
			catch(Exception e) {
				System.out.println(e);

			}
		}
	}
	public static void colisNONValide(int idColis) {
		if (nonSQL) {
		} else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "update COLIS set VERDICT=false where COLIS.ID_COL=" + idColis + ";";


				                stmt.executeUpdate(requete);
			}
			catch(Exception e) {

			}
		}
	}
	public static void modifVerdictColis(int idColis, String s) {
		if (nonSQL) {
		} else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();



				String          requete = "update COLIS set MOTIF='" + s + "' where COLIS.ID_COL=" + idColis + ";";


				                stmt.executeUpdate(requete);
			}
			catch(Exception e) {
				System.out.println(e);

			}
		}
	}
	public static boolean getOk() {
		return ok;
	}


	public static   String[] typeCompteEtId(int typeCompte) {
		String[] tab = new String[2];

		switch (typeCompte) {
		case 1:
			tab[0] = "DOUANE";
			tab[1] = "ID_DOU";
			break;
		case 2:
			tab[0] = "EMBALLEUR";
			tab[1] = "ID_EMB";
			break;
		case 3:
			tab[0] = "TRANSPORTEUR";
			tab[1] = "ID_TRA";
			break;
		case 4:
			tab[0] = "CLIENT";
			tab[1] = "ID_CLI";
			break;
		case 5:
			tab[0] = "GERANT";
			tab[1] = "ID_GER";
			break;
		default:;
		}
		                return tab;
	}

	public static String nom() {
		if (nonSQL)
			return "Ligier";
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String          s1 = typeCompteEtId(typeConnexion)[0];
				String          s2 = typeCompteEtId(typeConnexion)[1];
				String          requete = "select nom from " + s1 + " where " + s2 + "=" + identifiant + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	}

	public static String prenom() {
		if (nonSQL)
			return "Damien";
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();


				String          s1 = typeCompteEtId(typeConnexion)[0];
				String          s2 = typeCompteEtId(typeConnexion)[1];
				String          requete = "select prenom from " + s1 + " where " + s2 + "=" + identifiant + ";";
				ResultSet       res;


				                res = stmt.executeQuery(requete);
				                res.next();
				                return res.getString(1);
			}
			                catch(Exception e) {
				//Erreur
				System.out.println(e);
				return "erreur";
			}
		}
	}
	public static int getIdentifiant() {
		return identifiant;
	}
	              //le colis a - t - il un transporteur
	                public static boolean coliEstExpedie(int idColis) {
		if (nonSQL)
			return false;
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String          requete = "select TRANSPORT from COLIS where ID_COL=" + idColis + ";";
				ResultSet       res;
				                res = stmt.executeQuery(requete);
				                res.next();
				if              (res.getObject(1) != null) {
					return true;
				} else
					                return false;
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return false;
	}
	public static boolean paletteEstExpedie(int idPalette) {
		if (nonSQL)
			return false;
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String          requete = "select count(TRANSPORT) from COLIS where ID_PAL=" + idPalette + ";";
				ResultSet       res;
				                res = stmt.executeQuery(requete);
				                res.next();
				if              (res.getInt(1) > 0) {
					//System.out.print("[" + res.getInt(1) + "]\n");
					return true;
				} else
					                return false;
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return false;
	}
	public static boolean conteneurEstExpedie(int idConteneur) {
		if (nonSQL)
			return false;
		else {
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();

				String          requete = "select count(TRANSPORT) from COLIS where COLIS.ID_PAL=(select ID_PAL from PALETTE where ID_CON=" + idConteneur + ");";
				ResultSet       res;
				                res = stmt.executeQuery(requete);
				                res.next();
				if              (res.getInt(1) > 0) {
					//System.out.print("[" + res.getInt(1) + "]\n");
					return true;
				} else
					                return false;
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return false;
	}


	//remplir la liste des colis a transporter
		// numcolis, type, pays, code_pays, date souhaitee
		public static   Object[][] transporteur1() {


		if (nonSQL)
			return new Object[][] {
			{
				new             Integer(981), new Integer(231),
				                "avion", new Integer(187),
				                "01/01/2012", "en attente"
			},
			{
				new             Integer(123), new Integer(736),
				                "pute", new Integer(442),
				                null, null
			},
			{
				new             Integer(456), new Integer(129),
				                "camion", new Integer(187),
				                null, null
			},
			{
				new             Integer(163), new Integer(455),
				                null, new Integer(980),
				                null, null
			},
			{
				new             Integer(254), new Integer(111),
				                null, new Integer(709),
				                null, null
			}

			};
		else {
			//System.out.println("dansRequetes.idDeMailType(" france @ douane.fr ",1) le ELSE");

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			                catch(Exception e) {
				//System.out.println("erreur ici");
				return null;
			}
			//System.out.println(idPays);

			//A completer pr avoir le nb de lignes !
				String requete = "select count (COLIS.ID_COL) from COLIS join PRODUIT on( COLIS.ID_COL=PRODUIT.ID_COL) join COMMANDE on (COLIS.ID_COM=COMMANDE.ID_COM) join PAYS on (PAYS.ID_PAY=(select CLIENT.ID_PAY from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) join CODE_P on (CODE_P.ID_CP=(select CLIENT.ID_CP from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) where COLIS.ID_PAL is null and COMMANDE.ID_TRA is null;";
			ResultSet       res;
			int             nbLigne = 0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
				//System.out.println(nbLigne);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici2");
				return null;
			}


			//A completer pr avoir le tableau
				requete = "select COLIS.ID_COL, PRODUIT.PTYPE, PAYS.NOM,CODE_P.CP, COMMANDE.LIVRAISON from COLIS join PRODUIT on( COLIS.ID_COL=PRODUIT.ID_COL) join COMMANDE on (COLIS.ID_COM=COMMANDE.ID_COM) join PAYS on (PAYS.ID_PAY=(select CLIENT.ID_PAY from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) join CODE_P on (CODE_P.ID_CP=(select CLIENT.ID_CP from CLIENT where (COMMANDE.ID_CLI=CLIENT.ID_CLI))) where COLIS.ID_PAL is null and COMMANDE.ID_TRA is null;";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici3");
				return null;

			}
			Object[][] tab = new Object[nbLigne][5];
			try {
				for (int i = 0; i < nbLigne; i++) {
					res.next();

					tab[i][0] = new Integer(res.getInt(1));
					tab[i][1] = res.getString(2);
					tab[i][2] = res.getString(3);

					tab[i][3] = new Integer(res.getInt(4));
					tab[i][4] = res.getString(5);

				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			//System.out.println("->" + tab[0][5]);
			return tab;
		}

	}














	//remplir la liste des colis a transporter
		// numcolis, type, pays, code_pays, date souhaitee
		public static   Object[][] produit() {


		if (nonSQL)
			return null;
		else {
			//System.out.println("dansRequetes.idDeMailType(" france @ douane.fr ",1) le ELSE");

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			                catch(Exception e) {
				//System.out.println("erreur ici");
				return null;
			}
			//System.out.println(idPays);

			//A completer pr avoir le nb de lignes !
				String requete = "select count (*) from PRODUIT;";
			ResultSet       res;
			int             nbLigne = 0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
				//System.out.println(nbLigne);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici2");
				return null;
			}


			//A completer pr avoir le tableau
				requete = "select nom,prix,ptype from produit;";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici3");
				return null;

			}
			Object[][] tab = new Object[nbLigne][5];
			try {
				for (int i = 0; i < nbLigne; i++) {
					res.next();

					tab[i][0] = res.getString(1);
					tab[i][1] = new Double(res.getInt(2));
					tab[i][2] = new Integer(0);
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			//System.out.println("->" + tab[0][5]);
			return tab;
		}

	}



	//remplir la liste des colis a transporter
		// numcolis, type, pays, code_pays, date souhaitee
		public static   Object[][] commandeEnCoursDeIdClient() {


		if (nonSQL)
			return null;
		else {
			//System.out.println("dansRequetes.idDeMailType(" france @ douane.fr ",1) le ELSE");

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + loginBD, loginBD, mdpBD);
				stmt = conn.createStatement();
			}
			                catch(Exception e) {
				//System.out.println("erreur ici");
				return null;
			}
			//System.out.println(idPays);

			//A completer pr avoir le nb de lignes !
				String requete = "select count (id_com) from commande where id_cli =" + identifiant + ";";
			ResultSet       res;
			int             nbLigne = 0;

			try {
				res = stmt.executeQuery(requete);
				res.next();
				nbLigne = res.getInt(1);
				//System.out.println(nbLigne);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici2");
				return null;
			}


			//A completer pr avoir le tableau
				requete = "select id_com,livraison from commande where delivree = false and id_cli =" + identifiant + ";";

			try {
				res = stmt.executeQuery(requete);
			}
			catch(Exception e) {
				//Erreur
					// System.out.println("erreur ici3");
				return null;

			}
			Object[][] tab = new Object[nbLigne][5];
			try {
				for (int i = 0; i < nbLigne; i++) {
					res.next();

					tab[i][0] = new Integer(res.getInt(1));
					tab[i][1] = res.getString(2);
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			//System.out.println("->" + tab[0][5]);
			return tab;
		}

	}





}
public class    Test {
	public static void main(String[] args) {
		/*
		 * Object[][] t = Requetes.douane1(); for (int i =0;
		 * i<t.length;i++){ for (int j = 0; j<t[0].length;j++){
		 * System.out.print(t[i][j]+" | "); } System.out.print("\n");
		 * }
		 */
		//System.out.println(Requetes.paletteEstExpedie(0));
		//System.out.println(Requetes.idPaysDeIdDouane(Requetes.idDeMailType("france@douane.fr", 1)));

		Remplissage.remplis();

	}
