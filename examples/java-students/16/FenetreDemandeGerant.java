import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FenetreDemandeGerant extends JFrame{
	SQL connecte;
	FonctionsCommunes fonc;
	JButton retour = new JButton("Retour"), ok = new JButton("OK");
	JTextField jf = new JTextField(),num = new JTextField();
	FenetreGerant FenetreAvant;
	JRadioButton rb1,rb2;
	JLabel l = new JLabel();
	TablePanel tablePanel;
	ResultSetTableModel rtm = null;
	IntTextField nume = new IntTextField();
	JOptionPane jo = new JOptionPane();
	int ii;

	public FenetreDemandeGerant(FenetreGerant f, int i){
		try {
			this.connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);
			this.setTitle("");
			this.FenetreAvant = f;
			jf.setText("");
			jf.setPreferredSize(new Dimension(150,20));
			JPanel bouton = new JPanel();
			bouton.add(ok);
			bouton.add(retour);
			bouton.setPreferredSize(new Dimension(100, 50));
			this.add(bouton, BorderLayout.SOUTH);
			this.ii = i;
			if(i == 1 || i == 10){
				this.setSize( 700, 550 );
				try {
					rtm = new ResultSetTableModel( connecte.clientListerProduits());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePanel = new TablePanel( rtm );
				JPanel jtf = new JPanel();
				l = new JLabel("ID Produit      ");
				JLabel l2;
				if(i == 1)
					l2 = new JLabel("Nouveau prix");
				else
					l2 = new JLabel("Nouveau stock");
				
				jtf.add(l);
				jtf.add(jf);
				jtf.add(l2);
				jtf.add(nume);
				jtf.setPreferredSize(new Dimension(150, 50));
				this.add(tablePanel, BorderLayout.NORTH);
				this.add(jtf, BorderLayout.CENTER);
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {	
						String rep = jf.getText().toUpperCase();
						int j = -1;
						if(!nume.getText().equals(""))
							j = Integer.parseInt(nume.getText().toString());
						try {
							if(FenetreDemandeGerant.this.ii == 1)
								if(!rep.equals("") && connecte.verifIDProduit(rep)){
									if(j != -1){
										connecte.gerantModifierPrix(rep, j);
										jo.showMessageDialog(null, "Changement effectue", "Information", JOptionPane.INFORMATION_MESSAGE);
										FenetreAvant.setVisible(true);
										FenetreDemandeGerant.this.setVisible(false);
									}
									else{
										jo.showMessageDialog(null, "Veuillez entrer un prix", "Erreur", JOptionPane.ERROR_MESSAGE);
										FenetreDemandeGerant.this.tablePanel.invalidate();
										FenetreDemandeGerant.this.tablePanel.validate();
									}
								}
								else{
									jo.showMessageDialog(null, "Veuillez entrer un ID d'une commande valide", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeGerant.this.tablePanel.invalidate();
									FenetreDemandeGerant.this.tablePanel.validate();
								}
							else
								if(!rep.equals("") && connecte.verifIDProduit(rep)){
									if(j != -1){
									connecte.gerantModifierStock(rep, j);
									jo.showMessageDialog(null, "Changement effectue", "Information", JOptionPane.INFORMATION_MESSAGE);
									FenetreAvant.setVisible(true);
									FenetreDemandeGerant.this.setVisible(false);
									}
									else{
										jo.showMessageDialog(null, "Veuillez entrer un stock", "Erreur", JOptionPane.ERROR_MESSAGE);
										FenetreDemandeGerant.this.tablePanel.invalidate();
										FenetreDemandeGerant.this.tablePanel.validate();
									}
								}			
								else{
									jo.showMessageDialog(null, "Veuillez entrer un ID d'un produit valide", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeGerant.this.tablePanel.invalidate();
									FenetreDemandeGerant.this.tablePanel.validate();
								}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}	
				});
			}
			else if(i == 5){
				JPanel bou = new JPanel();
				this.setSize( 240, 200 );
				rb1 = new JRadioButton("Emballeur");
				rb2 = new JRadioButton("Transporteur");
				ButtonGroup bg = new ButtonGroup();
				bg.add(rb1);bg.add(rb2);
				rb1.setName("1");rb2.setName("2");
				bou.add(rb1);bou.add(rb2);
				this.add(bou, BorderLayout.NORTH);
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						switch(Integer.parseInt(getChoix())){
						case 1:
							try {
								FenetreDemandeGerant.this.connecte.close();
								new FenetreDemandeGerant(FenetreDemandeGerant.this.FenetreAvant,51);
								FenetreDemandeGerant.this.setVisible(false);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							break;
						case 2:
							try {
								FenetreDemandeGerant.this.connecte.close();
								new FenetreDemandeGerant(FenetreDemandeGerant.this.FenetreAvant,52);
								FenetreDemandeGerant.this.setVisible(false);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							break;
						case -1:
							jo.showMessageDialog(null, "Veuillez selectionner", "Erreur", JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
					public String getChoix(){
						return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (new Integer(-1)).toString();   
					}
				});
			}
			else if(i == 51 || i == 52){
				this.setSize( 700, 550 );
				try {
					if(i == 51)
						rtm = new ResultSetTableModel( connecte.gerantConsulterEmballeur());
					else
						rtm = new ResultSetTableModel( connecte.gerantConsulterTransporteur());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				TablePanel tablePanel = new TablePanel( rtm );
				JPanel jtf = new JPanel();
				l = new JLabel("ID ");
				jtf.add(l);
				jtf.add(jf);
				jtf.setPreferredSize(new Dimension(150, 50));
				this.add(tablePanel, BorderLayout.NORTH);
				this.add(jtf, BorderLayout.CENTER);

				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {	
						String rep = jf.getText().toUpperCase();
						try {
							if(!rep.equals("") && connecte.verifIdEmballeur(rep) && FenetreDemandeGerant.this.ii == 51){
								connecte.gerantLicencierEmballeur(rep);
								jo.showMessageDialog(null, "Licenciement effectue", "Information", JOptionPane.INFORMATION_MESSAGE);
								jf.setText("");
							}
							else if(!rep.equals("") && connecte.verifIdTransporteur(rep)&& FenetreDemandeGerant.this.ii == 52){
								connecte.gerantLicencierTransporteur(rep);
								jo.showMessageDialog(null, "Licenciement effectue", "Information", JOptionPane.INFORMATION_MESSAGE);
								jf.setText("");
							}
							else{
								jo.showMessageDialog(null, "Veuillez entrer un ID valide", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}		
				});
			}
			else if(i == 4){
				JPanel bou = new JPanel();
				this.setSize( 240, 200 );
				rb1 = new JRadioButton("Emballeur");
				rb2 = new JRadioButton("Transporteur");
				ButtonGroup bg = new ButtonGroup();
				bg.add(rb1);bg.add(rb2);
				rb1.setName("1");rb2.setName("2");
				bou.add(rb1);bou.add(rb2);

				l.setText("Nom   ");
				bou.add(l);
				bou.add(jf);
				JLabel l2 = new JLabel("Prenom");
				num = new JTextField();
				num.setText("");
				num.setPreferredSize(new Dimension(150,20));
				bou.add(l2);
				bou.add(num);

				bou.setPreferredSize(new Dimension(150, 100));
				this.add(bou, BorderLayout.NORTH);
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {	
						String rep = jf.getText().toUpperCase() , rep2 = num.getText().toUpperCase();
						Tables t = new Tables(FenetreDemandeGerant.this.connecte);
						String ID, mdp = t.randomID(11);
						try {
							switch(Integer.parseInt(getChoix())){
							case 1:
								if(!rep.isEmpty() && !rep2.isEmpty()){
									ID = fonc.randomIDEmballeur();
									connecte.gerantEmbaucherEmballeur(ID, rep, rep2, 0, mdp, "0");
									jo.showMessageDialog(null, "ID_Emballeur: "+ID+" Mot de passe: "+mdp, "Information", JOptionPane.INFORMATION_MESSAGE);
									jf.setText("");num.setText("");

								}
								else{
									jo.showMessageDialog(null, "Veuillez remplir tout les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
								break;
							case 2:
								if(!rep.isEmpty()){
									ID = fonc.randomIDTransporteur();
									connecte.gerantEmbaucherTransporteur(ID, rep, mdp, "0");
									jo.showMessageDialog(null, "ID_transporteur: "+ID+" Mot de passe: "+mdp, "Information", JOptionPane.INFORMATION_MESSAGE);
									jf.setText("");num.setText("");
								}
								else{
									jo.showMessageDialog(null, "Veuillez entrer un nom", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
								break;
							case -1:
								jo.showMessageDialog(null, "Veuillez selectionner", "Erreur", JOptionPane.ERROR_MESSAGE);
								break;

							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					public String getChoix(){
						return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (new Integer(-1)).toString();   
					}
				});
			}

			retour.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						connecte.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					new FenetreGerant(FenetreAvant.login);
					FenetreDemandeGerant.this.setVisible(false);
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
}
