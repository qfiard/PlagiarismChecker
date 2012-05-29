package accueil;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.WindowConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;

import bdd.Acces_bdd;


public class TPInfoCommande extends javax.swing.JPanel {
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JPanel jPanel3;
	private JTable jTable1;
	private JPanel jPanel2;
	private JComboBox jComboBox1;
	private JLabel jLabel1;
	String id_user;
	String[][] mobj ;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TPInfoCommande());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void detail_commande(String id_user, String id_colis) {
		this.setVisible(false);
		new DetailCommande(id_user,id_colis);
		
	}
	
	public TPInfoCommande() {
		super();
		initGUI();
	}
	
	public TPInfoCommande(String sid_user) {
		super();
		id_user= sid_user;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
			this.setSize(800, 600);
			
			{
				jPanel3 = new JPanel();
				this.add(jPanel3);
				jPanel3.setPreferredSize(new java.awt.Dimension(213, 25));
				{
					jLabel2 = new JLabel();
					jPanel3.add(jLabel2);
					jLabel2.setText("ID_Douanier : ");
				}
				{
					jLabel3 = new JLabel();
					jPanel3.add(jLabel3);
					jLabel3.setText(id_user);
				}
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1);
				jPanel1.setPreferredSize(new java.awt.Dimension(489, 38));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("Afficher :");
				}
				{
					ComboBoxModel jComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "Faites votre choix", "Liste commandes expédiées pays douane", 
										"Liste commandes controlées",
										"Liste commandes expédiées mais non-controlées"
								});
					jComboBox1 = new JComboBox();
					jPanel1.add(jComboBox1);
					jComboBox1.setModel(jComboBox1Model);
					jComboBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("jComboBox1.getSelectedIndex = "+ jComboBox1.getSelectedIndex());
							
							Acces_bdd abdd = new Acces_bdd();
							
							mobj = abdd.liste_commande(id_user, jComboBox1.getSelectedIndex() );
							
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
				jPanel2 = new JPanel();
				this.add(jPanel2);
				jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));
				{
				
					jTable1 = new JTable();
					jPanel2.add(jTable1);

					jTable1.setPreferredSize(new java.awt.Dimension(800, 600));

					jTable1.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                          if (e.getClickCount() == 2) {
                              int ligne_select = jTable1.getSelectedRow();
                              System.out.println("mouseDblClicked  sur id_commande saisi control : " + mobj[ligne_select][0]);
                             
                      
                              detail_commande(id_user, mobj[ligne_select][0] );

                          }
                        }
                      });
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
