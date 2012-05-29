package accueil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import bdd.Acces_bdd;

public class TPUpdateIdentifiant extends javax.swing.JPanel{
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JTextField jTextField3;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JTable jTable1;
	private JPanel jPanel2;
	private JLabel jLabel1;
	private JLabel jLabel4;
	String id_user;
	private JButton jButton1;
	private JPanel jPanel3;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TPRecherche());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public TPUpdateIdentifiant() {
		super();
		initGUI();
	}

	public TPUpdateIdentifiant(String sid_user) {
		super();
		id_user = sid_user;
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(800, 600));
			this.setSize(800, 600);
			{
				jPanel1 = new JPanel();
				this.add(jPanel1);
				jPanel1.setPreferredSize(new java.awt.Dimension(726, 200));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("Entrez votre ancien mot de passe ansi que le nouveau : ");
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("Ancien mot de passe :");
				}
				{
					jTextField2 = new JTextField();
					jPanel1.add(jTextField2);
					jTextField2
							.setPreferredSize(new java.awt.Dimension(100, 22));
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText("Nouveau mot de passe : ");
				}
				{
					jTextField3 = new JTextField();
					jPanel1.add(jTextField3);
					jTextField3
							.setPreferredSize(new java.awt.Dimension(100, 22));
				}

			}
			{
				jPanel2 = new JPanel();
				this.add(jPanel2);
				jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));
				{
					jPanel3 = new JPanel();
					jPanel2.add(jPanel3);
					jPanel3.setPreferredSize(new java.awt.Dimension(719, 32));
					{
						jButton1 = new JButton();
						jPanel3.add(jButton1);
						jButton1.setText("Valider");
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out
										.println("jButton1.actionPerformed, event="
												+ evt);
								
								Acces_bdd abdd = new Acces_bdd();

								int res = bdd.Acces_bdd.UpdateMdp(id_user, jTextField2.getText(), jTextField3.getText());
								{
									jLabel4 = new JLabel();
									jPanel1.add(jLabel4);
									jLabel4.setPreferredSize(new java.awt.Dimension(200, 32));
									
									if(res==-1){
										jLabel4.setText("Echec mise à jour!");
									}
									else
										jLabel4.setText("Succès mise à jour!");
								}
							}
						});
					}
				}
				{

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
