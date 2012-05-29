package accueil;

import bdd.Acces_bdd;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class Auth extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JComboBox jComboBox1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPasswordField jPasswordField1;
	private JButton jButton1;
	private JLabel jLabel4;
	private JPanel jPanel1;
	private JTextField jTextField1;
	String id_user = "";

	public void auth_accueil(String id_user) {
		this.setVisible(false);
		new Accueil(id_user);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Auth inst = new Auth();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Auth() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(
						jPanel1,
						new AnchorConstraint(563, 854, 655, 192,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jPanel1.setPreferredSize(new java.awt.Dimension(324, 34));
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4);
					jLabel4.setPreferredSize(new java.awt.Dimension(200, 32));

				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(
						jButton1,
						new AnchorConstraint(450, 637, 509, 450,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jButton1.setText("Valider");
				jButton1.setPreferredSize(new java.awt.Dimension(92, 22));
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						String slogin = jTextField1.getText();
						String spasswd = jPasswordField1.getText();

						Acces_bdd abdd = new Acces_bdd();
						id_user = abdd.verif_auth(slogin, spasswd);


						if (!id_user.matches("-1")) {
							System.out
									.println("Authentification OK pour id_user : "
											+ id_user);
							auth_accueil(id_user);
						} else {
							System.out.println("Echec authentification !  ");
							jLabel4.setText("Echec authentification !");

						}

					}
				});
			}
			{
				jPasswordField1 = new JPasswordField();
				getContentPane().add(
						jPasswordField1,
						new AnchorConstraint(285, 868, 344, 650,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jPasswordField1
						.setPreferredSize(new java.awt.Dimension(107, 22));
				jPasswordField1.setText("IWT87FKG6HB");

			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(
						jLabel3,
						new AnchorConstraint(293, 670, 333, 488,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jLabel3.setText("Password : ");
				jLabel3.setPreferredSize(new java.awt.Dimension(89, 15));
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(
						jTextField1,
						new AnchorConstraint(279, 464, 339, 280,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jTextField1.setPreferredSize(new java.awt.Dimension(90, 22));
				jTextField1.setText("SO78210");

			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(
						jLabel2,
						new AnchorConstraint(293, 294, 333, 168,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jLabel2.setText("Login : ");
				jLabel2.setPreferredSize(new java.awt.Dimension(62, 15));
			}
			{
				ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
						new String[] { "Douanier" });
				jComboBox1 = new JComboBox();
				getContentPane().add(
						jComboBox1,
						new AnchorConstraint(136, 721, 187, 488,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jComboBox1.setModel(jComboBox1Model);
				jComboBox1.setPreferredSize(new java.awt.Dimension(114, 19));
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(
						jLabel1,
						new AnchorConstraint(147, 382, 187, 168,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
				jLabel1.setText("Type compte :");
				jLabel1.setPreferredSize(new java.awt.Dimension(105, 15));
			}
			pack();
			this.setSize(500, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
