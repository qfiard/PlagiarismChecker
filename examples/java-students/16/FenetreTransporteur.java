import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class FenetreTransporteur extends JFrame{
	SQL connecte;
	JRadioButton rb1, rb2, rb3;
	String login, ID_transporteur;
	JPanel content;
	FonctionsCommunes fonc;

	public FenetreTransporteur(String log){
		try {
			this.connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);

			this.login = log;
			ID_transporteur = recupID(connecte.transporteurRecupID(login));
			JPanel menu = new JPanel();
			menu.setPreferredSize(new Dimension(550, 250));
			menu.setBorder(BorderFactory.createTitledBorder("Menu"));

			rb1 = new JRadioButton("1 - Lister les commandes");
			rb2 = new JRadioButton("2 - Obtenir les informations sur sur les commandes a transporter");
			rb3 = new JRadioButton("3 - Mettre a jour la date de dernier traitement");
			ButtonGroup bg = new ButtonGroup();
			rb1.setName("1");rb2.setName("2");rb3.setName("3");
			bg.add(rb1);bg.add(rb2);bg.add(rb3);
			menu.add(rb1);menu.add(rb2);menu.add(rb3);

			content = new JPanel();
			content.setBackground(Color.white);
			content.add(menu);

			JPanel control = new JPanel();
			JButton okBouton = new JButton("OK");

			okBouton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					ResultSetTableModel rtm = null;
					switch(Integer.parseInt(getChoix())){
					case 1:
						try {
							rtm = new ResultSetTableModel(connecte.transporteurAfficherCommande(fonc.dateToday()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						TablePanel tablePanel = new TablePanel( rtm );
						new FenetreResultat(tablePanel, FenetreTransporteur.this);
						FenetreTransporteur.this.setVisible(false);
						break;
					case 2:
						try {
							rtm = new ResultSetTableModel(connecte.transporteurAfficherCommande(ID_transporteur, fonc.dateToday()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						TablePanel tablePanell = new TablePanel( rtm );
						new FenetreResultat(tablePanell, FenetreTransporteur.this);
						FenetreTransporteur.this.setVisible(false);
						break;
					case 3:
						try {
							connecte.close();
							new FenetreDemandeTransporteur(FenetreTransporteur.this,3);
							FenetreTransporteur.this.setVisible(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case -1:
						break;
					}
				}

				public String getChoix(){
					return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (rb3.isSelected()) ? rb3.getName() :(new Integer(-1)).toString();   
				}	
			});

			JButton cancelBouton = new JButton("Se deconnecter");
			cancelBouton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						connecte.close();
						new FenetreConnexion();
						setVisible(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}			
			});

			control.add(okBouton);
			control.add(cancelBouton);

			this.getContentPane().add(menu, BorderLayout.WEST);
			this.getContentPane().add(content, BorderLayout.CENTER);
			this.getContentPane().add(control, BorderLayout.SOUTH);

			control.setVisible(true);
			content.setVisible(true);
			this.add(content);
			this.setTitle("Emballeur");
			this.setSize(550, 300);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String recupID(ResultSet rs) throws SQLException{
		ResultSetMetaData rm = rs.getMetaData();
		String ID = "";
		while(rs.next()){
			for(int i = 1 ; i <= rm.getColumnCount() ; i++){
				ID = rs.getObject(i).toString();
			}
		}
		rs.close();
		return ID;
	}
}
