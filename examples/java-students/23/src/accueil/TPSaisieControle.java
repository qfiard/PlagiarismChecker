package accueil;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import bdd.Acces_bdd;


public class TPSaisieControle extends javax.swing.JFrame {
	String id_user;
	private JPanel jPanel1;
	private JButton jButton1;
	private JPanel jPanel5;
	private JTextArea jTextArea1;
	private JPanel jPanel4;
	private JComboBox jComboBox1;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JLabel jLabel1;
	String id_colis;
	String [][] mobj = null;

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TPSaisieControle inst = new TPSaisieControle();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public TPSaisieControle() {
		super();
		initGUI();
		this.setVisible(true);
	}
	
	public TPSaisieControle(String sid_user, String sid_colis) {
		super();
		id_user = sid_user;
		id_colis = sid_colis;
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
	
				
				
				Acces_bdd abdd = new Acces_bdd();
				
				jPanel1.setPreferredSize(new java.awt.Dimension(425, 310));
				{
					jPanel2 = new JPanel();
					jPanel1.add(jPanel2);
					jPanel2.setPreferredSize(new java.awt.Dimension(310, 44));
					{
						jLabel1 = new JLabel();
						jPanel2.add(jLabel1);
						jLabel1.setText("Id colis : " + id_colis);
					}
				}
				{
					jPanel3 = new JPanel();
					jPanel1.add(jPanel3);
					jPanel3.setPreferredSize(new java.awt.Dimension(107, 223));
					{
						ComboBoxModel jComboBox1Model = 
							new DefaultComboBoxModel(
									new String[] { "Accepter", "Rejet" });
						jComboBox1 = new JComboBox();
						jPanel3.add(jComboBox1);
						jComboBox1.setModel(jComboBox1Model);
					}
				}
				{
					jPanel4 = new JPanel();
					jPanel1.add(jPanel4);
					jPanel4.setPreferredSize(new java.awt.Dimension(228, 206));
					{
						jTextArea1 = new JTextArea();
						jPanel4.add(jTextArea1);
						jTextArea1.setText("jTextArea1");
						jTextArea1.setPreferredSize(new java.awt.Dimension(177, 185));
					}
				}
				{
					jPanel5 = new JPanel();
					jPanel1.add(jPanel5);
					jPanel5.setPreferredSize(new java.awt.Dimension(344, 50));
					{
						jButton1 = new JButton();
						jPanel5.add(jButton1);
						jButton1.setText("Valider");
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("jButton1.actionPerformed, event="+evt);
								 bdd.Acces_bdd.updateControl(id_user,
								 id_colis,jTextArea1.getText(),
								 jComboBox1.getSelectedIndex() );
								setVisible(false);
								
							}
						});
					}
				}
			}
			pack();
			this.setSize(400, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
