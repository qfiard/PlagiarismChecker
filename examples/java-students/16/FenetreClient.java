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


public class FenetreClient extends JFrame{
	SQL connecte;
	JRadioButton rb1, rb2, rb3, rb4, rb5, rb6;
	String login, ID_client, mdp;
	JPanel content;

	public FenetreClient(String log, String mdp){
		try {
			this.connecte = new SQL();
			this.setLogin(log); this.mdp = mdp;
			this.ID_client = recupID(connecte.clientRecupID(login,mdp));
			JPanel menu = new JPanel();
			menu.setPreferredSize(new Dimension(520, 300));
			menu.setBorder(BorderFactory.createTitledBorder("Menu"));

			rb1 = new JRadioButton("1 - Lister les produits disponibles");
			rb2 = new JRadioButton("2 - Passer une commande");
			rb3 = new JRadioButton("3 - Ou en sont mes commandes ?   ");
			rb4 = new JRadioButton("4 - Modifier une date de livraison");
			rb5 = new JRadioButton("5 - Changer de login (nom societe)");
			rb6 = new JRadioButton("6 - Changer de mot de passe");
			ButtonGroup bg = new ButtonGroup();
			rb1.setName("1");rb2.setName("2");rb3.setName("3");rb4.setName("4");rb5.setName("5");rb6.setName("6");
			bg.add(rb1);bg.add(rb2);bg.add(rb3);bg.add(rb4);bg.add(rb5);bg.add(rb6);
			menu.add(rb1);menu.add(rb2);menu.add(rb3);menu.add(rb4);menu.add(rb5);menu.add(rb6);

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
							rtm = new ResultSetTableModel( connecte.clientListerProduits());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 2:
						new FenetreDemandeClient(FenetreClient.this,2,connecte);
						FenetreClient.this.setVisible(false);
						break;
					case 3:
						try {
							rtm = new ResultSetTableModel(connecte.clientSuiviCommande(ID_client));
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 4:
						new FenetreDemandeClient(FenetreClient.this,4,connecte);
						FenetreClient.this.setVisible(false);
						break;
					case 5:
						new FenetreDemandeClient(FenetreClient.this,5,connecte);
						FenetreClient.this.setVisible(false);
						break;
					case 6:
						new FenetreDemandeClient(FenetreClient.this,6,connecte);
						FenetreClient.this.setVisible(false);
						break;
					case -1:
						break;
					}
				}

				public String getChoix(){
					return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (rb3.isSelected()) ? rb3.getName() : (rb4.isSelected()) ? rb4.getName() : (rb5.isSelected()) ? rb5.getName() : (rb6.isSelected()) ? rb6.getName() : (new Integer(-1)).toString();   
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
						// TODO Auto-generated catch block
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
			this.setTitle("Douane");
			this.setSize(525, 310);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void affRes(ResultSetTableModel rtm){
		TablePanel tablePanel = new TablePanel( rtm );
		new FenetreResultat(tablePanel, this);
		this.setVisible(false);
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
