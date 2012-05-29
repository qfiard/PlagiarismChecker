import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FenetreDemandeDouane extends JFrame{
	SQL connecte;
	JButton retour = new JButton("Retour"), ok = new JButton("OK");
	JTextField jf = new JTextField();
	FenetreDouane FenetreAvant;
	JRadioButton rb1,rb2,rb3;
	JLabel l = new JLabel();
	JOptionPane jo = new JOptionPane();
	int ii;

	public FenetreDemandeDouane(FenetreDouane f, int i, SQL co){
		this.connecte = co;
		this.setTitle("");
		this.FenetreAvant = f;
		jf.setText("");
		JPanel bou = new JPanel(); 
		jf.setPreferredSize(new Dimension(150,20));
		JPanel bouton = new JPanel();
		bouton.add(ok);
		bouton.add(retour);
		bouton.setPreferredSize(new Dimension(100, 50));
		this.add(bouton, BorderLayout.SOUTH);
		this.ii=i;
		if(i==4){
			this.setSize( 400, 200 );
			rb1 = new JRadioButton("Numero commande");
			rb2 = new JRadioButton("Destination commande");
			rb3 = new JRadioButton("Contenu commande");
			ButtonGroup bg = new ButtonGroup();
			rb1.setName("1");rb2.setName("2");rb3.setName("3");
			bou.add(rb1);bou.add(rb2);bou.add(rb3);
			bg.add(rb1);bg.add(rb2);bg.add(rb3);
			JPanel jtf = new JPanel();
			jtf.add(jf);
			jtf.setPreferredSize(new Dimension(150, 50));
			bou.setPreferredSize(new Dimension(150, 80));
			this.add(bou, BorderLayout.NORTH);
			this.add(jtf, BorderLayout.CENTER);

			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					ResultSetTableModel rtm = null;
					String rep = jf.getText().toUpperCase();
					try {
						switch(Integer.parseInt(getChoix())){
						case 1:
							if(!rep.equals("") && connecte.verifIdCommande(rep)){
								rtm = new ResultSetTableModel( connecte.douaneRechercherCommandeParIDCommande(rep));
								affRes(rtm);
								jf.setText("");
							}
							else{
								jo.showMessageDialog(null, "Veuillez entrer un ID de commande valide", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							break;
						case 2:
							if(!rep.equals("")){
								rtm = new ResultSetTableModel( connecte.douaneRechercherCommandeParDestination(rep));
								affRes(rtm);
								jf.setText("");
							}
							else{
								jo.showMessageDialog(null, "Veuillez entrer une destination", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							break;
						case 3:
							if(!rep.equals("") && connecte.verifIDProduit(rep)){
								rtm = new ResultSetTableModel( connecte.douaneRechercherCommandeParIDProduit(rep));
								affRes(rtm);
								jf.setText("");
							}
							else{
								jo.showMessageDialog(null, "Veuillez entrer un ID de produit valide", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							break;
						case -1:
							break;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}	

				public String getChoix(){
					return (rb1.isSelected()) ? rb1.getName() : (rb2.isSelected()) ? rb2.getName() : (rb3.isSelected()) ? rb3.getName() : (new Integer(-1)).toString();   
				}	
			});
		}
		else{
			this.setSize( 200, 170 );
			l.setText("ID_commande: ");
			bou.add(l);
			bou.setPreferredSize(new Dimension(150, 100));
			bou.add(jf);
			if(i == 6){
				rb1 = new JRadioButton("Accepte");
				rb2 = new JRadioButton("Refuse");
				ButtonGroup bg = new ButtonGroup();
				bg.add(rb1);bg.add(rb2);
				rb1.setName("1");rb2.setName("2");
				bou.add(rb1);bou.add(rb2);
			}
			this.add(bou, BorderLayout.NORTH);
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					ResultSetTableModel rtm = null;
					String rep = jf.getText().toUpperCase() , controle = "";
					try {
						if(connecte.verifIdCommande(rep)){
							if(FenetreDemandeDouane.this.ii == 5){
								rtm = new ResultSetTableModel( connecte.douaneDetailCommande(rep));
								affRes(rtm);
							}
							else{
								switch(Integer.parseInt(getChoix())){
								case 1:
									controle = "CONTROLE";
									break;
								case 2:
									controle = "REJETE";
									break;
								case -1:
									jo.showMessageDialog(null, "Veuillez valider votre controle", "Erreur", JOptionPane.ERROR_MESSAGE);
									break;
								}
								connecte.douaneUpdateControleCommande(rep,controle);
								jo.showMessageDialog(null, "Controle valide!", "Information", JOptionPane.INFORMATION_MESSAGE);
								jf.setText("");
							}
						}
						else
							jo.showMessageDialog(null, "Veuillez entrer un ID valide", "Erreur", JOptionPane.ERROR_MESSAGE);

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
				new FenetreDouane(FenetreAvant.getLogin());
				FenetreDemandeDouane.this.setVisible(false);
				setVisible(false);
			}	
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible( true );
	}

	public void affRes(ResultSetTableModel rtm){
		TablePanel tablePanel = new TablePanel( rtm );
		new FenetreResultat(tablePanel, FenetreDemandeDouane.this);
		this.setVisible(false);
	}

}
