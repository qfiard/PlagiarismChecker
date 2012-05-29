import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FenetreDemandeClient extends JFrame{
	SQL connecte;
	FonctionsCommunes fonc;
	JButton retour = new JButton("Retour"), ok = new JButton("OK");
	JTextField jf = new JTextField();
	private JPasswordField  mdp = new JPasswordField ();
	private JPasswordField  mdpVerif = new JPasswordField ();
	private JFormattedTextField date;
	FenetreClient FenetreAvant;
	JTextField jp1 = new JTextField(),jp2 = new JTextField(),jp3 = new JTextField(),
	jp4 = new JTextField(),jp5 = new JTextField(),jp6 = new JTextField();
	IntTextField jp7 = new IntTextField(),jp8 = new IntTextField(),jp9 = new IntTextField(),
	jp10 = new IntTextField(),jp11 = new IntTextField(),jp12 = new IntTextField();
	JLabel l = new JLabel();JLabel l2 = new JLabel();
	JOptionPane jo = new JOptionPane();
	int ii;
	ResultSetTableModel rtm = null;
	TablePanel tablePanel;

	public FenetreDemandeClient(FenetreClient f, int i, SQL co){
		this.connecte = co; this.setTitle(""); this.FenetreAvant = f;
		this.fonc = new FonctionsCommunes(co);
		jf.setText("");jf.setPreferredSize(new Dimension(150,20));
		mdp.setPreferredSize(new Dimension(150,20));
		mdpVerif.setPreferredSize(new Dimension(150,20));
		JPanel bou = new JPanel(), bouton = new JPanel();
		bouton.add(ok);bouton.add(retour);
		bouton.setPreferredSize(new Dimension(100, 50));
		this.add(bouton, BorderLayout.SOUTH);
		this.ii=i;

		if(i == 2){
			this.setSize(new Dimension(700,650));
			bou.setPreferredSize(new Dimension(300,300));
			try {
				rtm = new ResultSetTableModel(connecte.clientListerProduits());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tablePanel = new TablePanel( rtm );
			l.setText("Veuillez remplir les cases selon le nombre de produits voulu :");
			bou.add(l);

			JLabel lp1= new JLabel(),lp2= new JLabel(),lp3= new JLabel(),lp4= new JLabel(),lp5= new JLabel(),
			lp6= new JLabel(),lp7= new JLabel(),lp8= new JLabel(),lp9= new JLabel(),lp10= new JLabel(),
			lp11= new JLabel();
			l2.setText("ID Produit 1");lp1.setText("ID Produit 2");lp2.setText("ID Produit 3");
			lp3.setText("ID Produit 4");lp4.setText("ID Produit 5");lp5.setText("ID Produit 6");
			lp6.setText("Quantite 1");lp7.setText("Quantite 2");lp8.setText("Quantite 3");
			lp9.setText("Quantite 4");lp10.setText("Quantite 5");lp11.setText("Quantite 6");

			jp1.setPreferredSize(new Dimension(150,20));jp7.setPreferredSize(new Dimension(75,20));
			jp2.setPreferredSize(new Dimension(150,20));jp8.setPreferredSize(new Dimension(75,20));
			jp3.setPreferredSize(new Dimension(150,20));jp9.setPreferredSize(new Dimension(75,20));
			jp4.setPreferredSize(new Dimension(150,20));jp10.setPreferredSize(new Dimension(75,20));
			jp5.setPreferredSize(new Dimension(150,20));jp11.setPreferredSize(new Dimension(75,20));
			jp6.setPreferredSize(new Dimension(150,20));jp12.setPreferredSize(new Dimension(75,20));

			bou.add(l2);bou.add(jp1);bou.add(lp6);bou.add(jp7);
			bou.add(lp1);bou.add(jp2);bou.add(lp7);bou.add(jp8);
			bou.add(lp2);bou.add(jp3);bou.add(lp8);bou.add(jp9);
			bou.add(lp3);bou.add(jp4);bou.add(lp9);bou.add(jp10);
			bou.add(lp4);bou.add(jp5);bou.add(lp10);bou.add(jp11);
			bou.add(lp5);bou.add(jp6);bou.add(lp11);bou.add(jp12);

			try {
				MaskFormatter d = new MaskFormatter("####-##-##");
				date =  new JFormattedTextField(d);
				date.setPreferredSize(new Dimension(150,20));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JLabel d = new JLabel("Date livraison voulue (AAAA-MM-JJ):");
			bou.add(d);
			bou.add(date);

			JPanel west = new JPanel(),east = new JPanel(), mid = new JPanel();
			west.setPreferredSize(new Dimension(300,100));
			tablePanel.setPreferredSize(new Dimension(300,340));
			east.setPreferredSize(new Dimension(200,100));
			bou.setPreferredSize(new Dimension(400,350));
			mid.add(bou,BorderLayout.CENTER);
			mid.add(west,BorderLayout.WEST);
			mid.add(east,BorderLayout.EAST);
			this.add(tablePanel, BorderLayout.NORTH);
			this.add(mid, BorderLayout.CENTER);


			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String[][] commande = new String[6][3];
					java.sql.Date d = null;
					if(!date.getText().equals("    -  -  ")){
						try{
							d = java.sql.Date.valueOf(date.getText());
						}
						catch(Exception e){
							jo.showMessageDialog(null, "Date Invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					}
					int i = 0;
					try {
						if(!date.getText().equals("    -  -  ") && d.after(fonc.dateToday())){
							if((!jp1.getText().equals("") && !jp7.getText().equals(""))){
								if(connecte.verifIDProduit(jp1.getText())&& connecte.verif_quantite(jp1.getText(), Integer.parseInt(jp7.getText()))){
									commande[i][0] = jp1.getText();
									commande[i][1] = jp7.getText();
									commande[i][2] = connecte.recupQualifiant(jp1.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}
							if((!jp2.getText().equals("") && !jp8.getText().equals(""))&& !date.getText().equals("")){
								if(connecte.verifIDProduit(jp2.getText())&& connecte.verif_quantite(jp2.getText(), Integer.parseInt(jp8.getText()))){
									commande[i][0] = jp2.getText();
									commande[i][1] = jp8.getText();
									commande[i][2] = connecte.recupQualifiant(jp2.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}
							if((!jp3.getText().equals("") && !jp9.getText().equals(""))&& !date.getText().equals("")){
								if(connecte.verifIDProduit(jp3.getText())&& connecte.verif_quantite(jp3.getText(), Integer.parseInt(jp9.getText()))){
									commande[i][0] = jp3.getText();
									commande[i][1] = jp9.getText();
									commande[i][2] = connecte.recupQualifiant(jp3.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}
							if((!jp4.getText().equals("") && !jp10.getText().equals(""))&& !date.getText().equals("")){
								if(connecte.verifIDProduit(jp4.getText())&& connecte.verif_quantite(jp4.getText(), Integer.parseInt(jp10.getText()))){
									commande[i][0] = jp4.getText();
									commande[i][1] = jp10.getText();
									commande[i][2] = connecte.recupQualifiant(jp4.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}
							if((!jp5.getText().equals("") && !jp11.getText().equals(""))&& !date.getText().equals("")){
								if(connecte.verifIDProduit(jp5.getText())&& connecte.verif_quantite(jp5.getText(), Integer.parseInt(jp11.getText()))){
									commande[i][0] = jp5.getText();
									commande[i][1] = jp11.getText();
									commande[i][2] = connecte.recupQualifiant(jp5.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}
							if((!jp6.getText().equals("") && !jp12.getText().equals("")) && !date.getText().equals("")){
								if(connecte.verifIDProduit(jp6.getText())&& connecte.verif_quantite(jp6.getText(), Integer.parseInt(jp12.getText()))){
									commande[i][0] = jp6.getText();
									commande[i][1] = jp12.getText();
									commande[i][2] = connecte.recupQualifiant(jp6.getText());
									i++;
								}
								else{
									jo.showMessageDialog(null, "Verifiez l'ID et la quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
									FenetreDemandeClient.this.tablePanel.invalidate();
									FenetreDemandeClient.this.tablePanel.validate();
								}
							}

							else if(commande[0][0] != null){
								FenetreDemandeClient.this.newCommande(commande, FenetreAvant.ID_client, d);
								jo.showMessageDialog(null, "Commande validee", "Information", JOptionPane.INFORMATION_MESSAGE);
								FenetreAvant.setVisible(true);
								FenetreDemandeClient.this.setVisible(false);
							}
							else{
								jo.showMessageDialog(null, "Veuillez entrer au moins un ID et une quantite", "Erreur", JOptionPane.ERROR_MESSAGE);
								FenetreDemandeClient.this.tablePanel.invalidate();
								FenetreDemandeClient.this.tablePanel.validate();
							}
						}
						else{
							jo.showMessageDialog(null, "Date Invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
							FenetreDemandeClient.this.tablePanel.invalidate();
							FenetreDemandeClient.this.tablePanel.validate();
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}	
			});
		}

		else if (i == 4){
			try {
				MaskFormatter d = new MaskFormatter("####-##-##");
				date =  new JFormattedTextField(d);
				date.setPreferredSize(new Dimension(150,20));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			this.setSize( 700, 550 );
			try {
				rtm = new ResultSetTableModel(connecte.clientSuiviCommande(FenetreAvant.ID_client));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			TablePanel tablePanel = new TablePanel( rtm );
			l = new JLabel("ID_commande      ");
			l2 = new JLabel("Date Livraison (AAAA-MM-JJ)");
			bou.add(l);bou.add(jf);bou.add(l2);bou.add(date);
			this.add(tablePanel, BorderLayout.NORTH);
			this.add(bou, BorderLayout.CENTER);
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					ResultSetTableModel rtm = null;
					String ID = jf.getText().toUpperCase();
					String d = date.getText();
					try {
						if(!ID.isEmpty() && !d.isEmpty() && connecte.verifIdCommande(ID) && connecte.verifEtatCommande(ID)){
							connecte.clientChoisirDate(ID,d);
							jo.showMessageDialog(null, "Date changee", "Information", JOptionPane.INFORMATION_MESSAGE);
							jf.setText("");date.setText("");
							try {
								rtm = new ResultSetTableModel(connecte.clientListerProduits());
							} catch (SQLException e) {
								e.printStackTrace();
							}
							TablePanel tablePanel = new TablePanel( rtm );
							tablePanel = new TablePanel( rtm );
							FenetreDemandeClient.this.add(tablePanel, BorderLayout.NORTH);
							FenetreDemandeClient.this.invalidate();
							FenetreDemandeClient.this.validate();
						}
						else
							jo.showMessageDialog(null, "Veuillez entrer un ID ou une date valide", "Erreur", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}	
			});
		}

		else if(i == 5 || i == 6){
			if(i == 5){
				this.setSize(new Dimension(300,200));
				bou.setPreferredSize(new Dimension(300,150));
				l.setText("nouveau nom de societe ");
				jf.setText("");
				bou.add(l); bou.add(jf);
			}
			else{
				this.setSize(new Dimension(300,300));
				bou.setPreferredSize(new Dimension(300,250));
				l.setText("nouveau mot de passe");
				l2.setText("retatpez le encore ");
				mdp.setPreferredSize(new Dimension(150,20));
				mdpVerif.setPreferredSize(new Dimension(150,20));
				mdp.setText("");mdpVerif.setText("");
				bou.add(l);bou.add(mdp);
				bou.add(l2);bou.add(mdpVerif);
			}
			this.add(bou, BorderLayout.NORTH);
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					if(ii == 5){
						String rep = jf.getText().toUpperCase();
						if(!rep.isEmpty())
							try {
								connecte.clientChangerLogin(rep, FenetreAvant.ID_client);
								jo.showMessageDialog(null, "nom societe change", "Information", JOptionPane.INFORMATION_MESSAGE);
								FenetreAvant.login = rep;
								FenetreAvant.setVisible(true);
								FenetreDemandeClient.this.setVisible(false);

							} catch (SQLException e) {
								e.printStackTrace();
							}
							else
								jo.showMessageDialog(null, "Veuillez remplir le champs", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else if(ii == 6){
						char [] m = mdp.getPassword(), m2 = mdpVerif.getPassword();
						String MDP = "", MDP2 = "";
						for(int i = 0 ; i < m.length; i++)
							MDP += m[i];
						for(int i = 0 ; i < m2.length; i++)
							MDP2 += m2[i];
						if(!MDP.isEmpty() && MDP.equals(MDP2))
							try {
								connecte.clientChangerMdp(MDP, FenetreAvant.ID_client);
								jo.showMessageDialog(null, "mot de passe change", "Information", JOptionPane.INFORMATION_MESSAGE);
								FenetreAvant.mdp = MDP;
								FenetreAvant.setVisible(true);
								FenetreDemandeClient.this.setVisible(false);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							else
								jo.showMessageDialog(null, "Erreur, veuillez mettre deux fois le meme mot de passe non vide", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		else{

		}

		retour.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				try {
					connecte.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				new FenetreClient(FenetreAvant.getLogin(),FenetreAvant.mdp);
				FenetreDemandeClient.this.setVisible(false);
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
		new FenetreResultat(tablePanel, FenetreDemandeClient.this);
		this.setVisible(false);
	}

	public void setMdp(JPasswordField mdp) {
		this.mdp = mdp;
	}

	public JPasswordField getMdp() {
		return mdp;
	}

	public void setMdpVerif(JPasswordField mdpVerif) {
		this.mdpVerif = mdpVerif;
	}

	public JPasswordField getMdpVerif() {
		return mdpVerif;
	}

	public void newCommande(String[][] commande, String ID_cli, Date date_livraison) throws SQLException {
		String emballeur = connecte.IDEmballeur();
		String transporteur = connecte.IDtransporteur();
		String ID_commande = "";
		String qualifiant = ""; //en fonction des produits soit D soit N soit F soit X
		String pays = "";
		String ID_douane = "";
		int prix = 0, nbProd = 0, x = 0;

		//code ASCII de A = 65
		//ID_commande
		for(int i = 0; i < 10; i++){
			int lettre = (int) (Math.random()*26+65);
			int chiffre = (int)(Math.random()*10);
			int choix = (int)(Math.random()*2);
			if(choix == 0)
				ID_commande += (char)lettre;
			else
				ID_commande += chiffre;
		}	

		nbProd = 0;
		int reserve;
		for(int i = 0 ; i < commande.length; i++){
			if(commande[i][0] != null){
				nbProd ++;
				prix += Integer.parseInt(commande[i][1])*Integer.parseInt(connecte.recupPrix(commande[i][0]));
				// UPDATE reserve reserve-quantite
				reserve = connecte.recupReserve(commande[i][0]);
				connecte.updateReserveProduit(reserve-Integer.parseInt(commande[i][1]), commande[i][0]);
			}
		}

		// CHECK Random ID_produit, random quantite par rapport a la reserve, additionner les prix
		// CHECK QUALIFIANT en fonction des produits choisis
		for(int i = 0; i < commande.length; i++){
			if(commande[x][2].equals("D")){
				if(qualifiant.equals("F"))
					qualifiant = "X";
				else
					qualifiant = "D";
			}
			else if(commande[x][2].equals("F")){
				if(qualifiant.equals("D"))
					qualifiant = "X";
				else
					qualifiant = "F";
			}
		}
		if(qualifiant.equals(""))
			qualifiant = "N";

		java.sql.Date d1 = fonc.dateToday();

		pays = connecte.recupPaysClient(ID_cli);
		ID_douane = connecte.recupDouane(pays);
		// CHECK REMPLIR table commande
		connecte.remplirCommande(ID_commande, ID_cli, qualifiant, "NON_EXP", prix, d1, date_livraison, emballeur, transporteur);
		connecte.remplirControleCommande(ID_commande, ID_douane, "null");

		// CHECK REMPLIR table Commande_Contient_Produit
		for(int i = 0; i < nbProd; i++)
			connecte.remplirCommandeContientProduit(ID_commande,commande[i][0],commande[i][1]);
	}

}
