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

public class TPRechercheProduit extends javax.swing.JPanel {
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JTextField jTextField3;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JTable jTable1;
	private JPanel jPanel2;
	private JLabel jLabel1;
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
	
	public TPRechercheProduit() {
		super();
		initGUI();
	}

	public TPRechercheProduit(String sid_user) {
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
				jPanel1.setPreferredSize(new java.awt.Dimension(726, 60));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("N° Conteneur : ");
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1);
					jTextField1.setPreferredSize(new java.awt.Dimension(100, 22));
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("Palette :");
				}
				{
					jTextField2 = new JTextField();
					jPanel1.add(jTextField2);
					jTextField2.setPreferredSize(new java.awt.Dimension(100, 22));
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText("Colis : ");
				}
				{
					jTextField3 = new JTextField();
					jPanel1.add(jTextField3);
					jTextField3.setPreferredSize(new java.awt.Dimension(100, 22));
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
						jButton1.setText("Rechercher");
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("jButton1.actionPerformed, event="+evt);
								
								Acces_bdd abdd = new Acces_bdd();
								
								String[][] mobj = abdd.recherche_multi_produits(id_user, jTextField1.getText(), jTextField2.getText(), jTextField3.getText() );
								 
								TableModel jTable1Model1 = 
										new DefaultTableModel(
												mobj,
												new String[] { "Column 1", "Column 2" , "Column 2" , "Column 2" , "Column 2" , "Column 2" , "Column 2"  });
								jTable1.setModel(jTable1Model1);
								
									for(int i = 0; mobj[i][1] != null ; i++){
										System.out.print("String[][] : ");
										for(int k = 1; mobj[i][k] != null ; k++){
											System.out.print( mobj[i][k] + "\t");
										}
										System.out.print( "\n");
												
									}
							}
						});
					}
				}
				{
					jTable1 = new JTable();
					jPanel2.add(jTable1);
					jTable1.setSize(800, 600);
					jTable1.setPreferredSize(new java.awt.Dimension(800, 600));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

