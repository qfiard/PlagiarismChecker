import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FenetreDemandeTransporteur extends JFrame{
	SQL connecte;
	FonctionsCommunes fonc;
	JButton retour = new JButton("Retour"), ok = new JButton("OK");
    JTextField jt = new JTextField();
	FenetreTransporteur FenetreAvant;
	JRadioButton rb1,rb2;
	JLabel l = new JLabel();
	TablePanel tablePanel;
	ResultSetTableModel rtm = null;
	JOptionPane jo = new JOptionPane();
	int ii;

	public FenetreDemandeTransporteur(FenetreTransporteur f, int i){
		try {
			this.connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);
			this.setTitle("");
			this.FenetreAvant = f;
			jt.setPreferredSize(new Dimension(150,20));
			JPanel bouton = new JPanel();
			bouton.add(ok);
			bouton.add(retour);
			bouton.setPreferredSize(new Dimension(100, 50));
			this.add(bouton, BorderLayout.SOUTH);
			this.setSize( 700, 550 );
			try {
				rtm = new ResultSetTableModel(connecte.transporteurAfficherCommande(FenetreAvant.ID_transporteur, fonc.dateToday()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tablePanel = new TablePanel( rtm );
			JPanel jtf = new JPanel();
			l = new JLabel("ID_commande      ");

			jtf.add(l);
			jtf.add(jt);
			jtf.setPreferredSize(new Dimension(150, 50));
			this.add(tablePanel, BorderLayout.NORTH);
			this.add(jtf, BorderLayout.CENTER);
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					String rep = jt.getText();
					try {
						if(!rep.equals("") && connecte.verifIdCommande(rep)){
							connecte.transporteurUpdateDate(rep,fonc.dateToday());
							jo.showMessageDialog(null, "Changement effectue", "Information", JOptionPane.INFORMATION_MESSAGE);
							FenetreDemandeTransporteur.this.connecte.close();
							new FenetreDemandeTransporteur(FenetreAvant,ii);
							FenetreDemandeTransporteur.this.setVisible(false);
						}
						else
							jo.showMessageDialog(null, "Veuillez entrer un ID d'une commande valide", "Erreur", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}	
			});
			retour.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						connecte.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					new FenetreEmballeur(FenetreAvant.login);
					FenetreDemandeTransporteur.this.setVisible(false);
					setVisible(false);
				}	
			});
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setVisible( true );
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}
	public void affRes(ResultSetTableModel rtm){
		TablePanel tablePanel = new TablePanel( rtm );
		new FenetreResultat(tablePanel, FenetreAvant);
		this.setVisible(false);
	}
}
