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


public class FenetreGerant extends JFrame{
	SQL connecte;
	JRadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7 , rb8, rb9, rb10;
	String login;
	JPanel content;

	public FenetreGerant(String log){
		try {
			this.connecte = new SQL();

			this.login = log;
			JPanel menu = new JPanel();
			menu.setPreferredSize(new Dimension(340, 350));
			menu.setBorder(BorderFactory.createTitledBorder("Menu"));

			rb1 = new JRadioButton("1 - Changer prix produit                 ");
			rb2 = new JRadioButton("2 - Liste emballeurs                     ");
			rb3 = new JRadioButton("3 - Liste transporteurs                  ");
			rb4 = new JRadioButton("4 - Embaucher personnel                  ");
			rb5 = new JRadioButton("5 - Licencier personnel                  ");
			rb6 = new JRadioButton("6 - Nombre de commande traites par emballeur");
			rb7 = new JRadioButton("7 - Produits les plus vendus             ");
			rb8 = new JRadioButton("8 - Liste clients                        ");
			rb9 = new JRadioButton("9 - Clients les plus depensier           ");
			rb10 = new JRadioButton("10 - Reassort du stock                    ");
			ButtonGroup bg = new ButtonGroup();
			rb1.setName("1");rb2.setName("2");rb3.setName("3");rb4.setName("4");rb5.setName("5");
			rb6.setName("6");rb7.setName("7");rb8.setName("8");rb9.setName("9");rb10.setName("10");
			bg.add(rb1);bg.add(rb2);bg.add(rb3);bg.add(rb4);bg.add(rb5);
			bg.add(rb6);bg.add(rb7);bg.add(rb8);bg.add(rb9);bg.add(rb10);
			menu.add(rb1);menu.add(rb2);menu.add(rb3);menu.add(rb4);menu.add(rb5);
			menu.add(rb6);menu.add(rb7);menu.add(rb8);menu.add(rb9);menu.add(rb10);

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
							connecte.close();
							new FenetreDemandeGerant(FenetreGerant.this,1);
							FenetreGerant.this.setVisible(false);
						} catch (SQLException e4) {
							e4.printStackTrace();
						}
						break;
					case 2:
						try {
							rtm = new ResultSetTableModel( connecte.gerantConsulterEmballeur());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 3:
						try {
							rtm = new ResultSetTableModel( connecte.gerantConsulterTransporteur());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 4:
						try {
							connecte.close();
							new FenetreDemandeGerant(FenetreGerant.this,4);
							FenetreGerant.this.setVisible(false);
						} catch (SQLException e3) {
							
							e3.printStackTrace();
						}
						break;
					case 5:
						try {
							connecte.close();
							new FenetreDemandeGerant(FenetreGerant.this,5);
							FenetreGerant.this.setVisible(false);
						} catch (SQLException e2) {
							
							e2.printStackTrace();
						}
						break;
					case 6:
						try {
							rtm = new ResultSetTableModel( connecte.gerantNombreCommande());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 7:
						try {
							rtm = new ResultSetTableModel( connecte.gerantProduitsPlusVendus());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 8:
						try {
							rtm = new ResultSetTableModel( connecte.gerantConsulterClient());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 9:
						try {
							rtm = new ResultSetTableModel( connecte.gerantClientPlusDepensier());
							affRes(rtm);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						break;
					case 10:
						try {
							connecte.close();
							new FenetreDemandeGerant(FenetreGerant.this,10);
							FenetreGerant.this.setVisible(false);
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						break;
					case -1:
						break;
					}
				}

				public String getChoix(){
					return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (rb3.isSelected()) ? rb3.getName() : (rb4.isSelected()) ? rb4.getName() : (rb5.isSelected()) ? rb5.getName() : (rb6.isSelected()) ? rb6.getName() : (rb7.isSelected()) ? rb7.getName(): (rb8.isSelected()) ? rb8.getName(): (rb9.isSelected()) ? rb9.getName(): (rb10.isSelected()) ? rb10.getName() : (new Integer(-1)).toString();   
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
			this.setTitle("Gerant");
			this.setSize(350, 400);
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

	public void affRes(ResultSetTableModel rtm){
		TablePanel tablePanel = new TablePanel( rtm );
		new FenetreResultat(tablePanel, FenetreGerant.this);
		FenetreGerant.this.setVisible(false);
	}
}
