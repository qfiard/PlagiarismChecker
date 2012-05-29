import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class FenetreDouane extends JFrame{
	SQL connecte;
	private JRadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
	private String login;
	JPanel content;

	public FenetreDouane(String log){
		try {
			this.connecte = new SQL();
			this.setLogin(log);
			JPanel menu = new JPanel();
			menu.setPreferredSize(new Dimension(520, 300));
			menu.setBorder(BorderFactory.createTitledBorder("Menu"));

			rb1 = new JRadioButton("1 - Visualiser une liste des commandes expediees dans votre pays         ");
			rb2 = new JRadioButton("2 - Visualiser une liste des commandes controlees par votre douane       ");
			rb3 = new JRadioButton("3 - Visualiser une liste des commandes non-controlees dans votre pays    ");
			rb4 = new JRadioButton("4 - Rechercher une/des commande(s) par son numero, destination ou contenu");
			rb5 = new JRadioButton("5 - Visualiser les details d'une commande                                ");
			rb6 = new JRadioButton("6 - Entrer les resultats de votre controle                               ");
			rb7 = new JRadioButton("7 - Visualiser les statistiques sur les controles                        ");
			ButtonGroup bg = new ButtonGroup();
			rb1.setName("1");rb2.setName("2");rb3.setName("3");rb4.setName("4");rb5.setName("5");rb6.setName("6");rb7.setName("7");
			bg.add(rb1);bg.add(rb2);bg.add(rb3);bg.add(rb4);bg.add(rb5);bg.add(rb6);bg.add(rb7);
			menu.add(rb1);menu.add(rb2);menu.add(rb3);menu.add(rb4);menu.add(rb5);menu.add(rb6);menu.add(rb7);

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
							rtm = new ResultSetTableModel( connecte.douaneListerCommandeExpedie(getLogin()));
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 2:
						try {
							rtm = new ResultSetTableModel( connecte.douaneListerCommandeControle(getLogin()));
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 3:
						try {
							rtm = new ResultSetTableModel( connecte.douaneListerCommandeNonControle(getLogin()));
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 4:
						new FenetreDemandeDouane(FenetreDouane.this,4,connecte);
						FenetreDouane.this.setVisible(false);
						break;
					case 5:
						new FenetreDemandeDouane(FenetreDouane.this,5,connecte);
						FenetreDouane.this.setVisible(false);
						break;
					case 6:
						new FenetreDemandeDouane(FenetreDouane.this,6,connecte);
						FenetreDouane.this.setVisible(false);
						break;
					case 7:
						try {
							rtm = new ResultSetTableModel( connecte.douaneStatistique(getLogin()));
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case -1:
						break;
					}
				}

				public String getChoix(){
					return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (rb3.isSelected()) ? rb3.getName() : (rb4.isSelected()) ? rb4.getName() : (rb5.isSelected()) ? rb5.getName() : (rb6.isSelected()) ? rb6.getName() : (rb7.isSelected()) ? rb7.getName() : (new Integer(-1)).toString();   
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
			this.setTitle("Douane");
			this.setSize(525, 310);
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

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void affRes(ResultSetTableModel rtm){
		TablePanel tablePanel = new TablePanel( rtm );
		new FenetreResultat(tablePanel, FenetreDouane.this);
		FenetreDouane.this.setVisible(false);
	}
}
