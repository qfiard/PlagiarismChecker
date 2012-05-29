import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;


public class FenetreNouveauClient extends JFrame{
	SQL connecte ;
	FonctionsCommunes fonc;
	JOptionPane jo = new JOptionPane();
	JButton valider = new JButton("Valider");
	JButton retour = new JButton("Retour");
	JLabel ns = new JLabel("Nom societe"), ss= new JLabel("Suffixe societe"),
	ad= new JLabel("Adresse"), vi= new JLabel("Ville"), cp= new JLabel("Code Postal"),
	pa= new JLabel("Pays"), tel= new JLabel("Telephone");
	JTextField nom = new JTextField(), suff= new JTextField(), adr= new JTextField(),
	ville= new JTextField(), codeP= new JTextField(), tele= new JTextField();
	JComboBox pays = new JComboBox();
	String[] Toutpays;
	public FenetreNouveauClient(){
		try {
			this.connecte = new SQL();
			fonc = new FonctionsCommunes(this.connecte);
			Toutpays = new String[connecte.nbPays()];
			this.setTitle("Nouveau Client");
			this.setLocationRelativeTo(null);
			JPanel info = new JPanel();
			info.setPreferredSize(new Dimension(500, 100));
			info.setBorder(BorderFactory.createTitledBorder("Informations"));
			JPanel nomSociete = new JPanel();
			JPanel suffSociete = new JPanel();
			JPanel adresse = new JPanel();
			JPanel villee = new JPanel();
			JPanel codePostal = new JPanel();
			JPanel payss = new JPanel();
			JPanel telephone = new JPanel();
			nom.setPreferredSize(new Dimension(150,20));
			suff.setPreferredSize(new Dimension(150,20));
			adr.setPreferredSize(new Dimension(150,20));
			ville.setPreferredSize(new Dimension(150,20));
			codeP.setPreferredSize(new Dimension(150,20));
			pays.setPreferredSize(new Dimension(150,20));
			tele.setPreferredSize(new Dimension(150,20));
			pays.setPreferredSize(new Dimension(100,20));
			Pays();
			pays = new JComboBox(Toutpays);
			nomSociete.add(ns); nomSociete.add(nom);
			suffSociete.add(ss);suffSociete.add(suff);
			adresse.add(ad);adresse.add(adr);
			villee.add(vi);villee.add(ville);
			codePostal.add(cp);codePostal.add(codeP);
			payss.add(pa);payss.add(pays);
			telephone.add(tel);telephone.add(tele);

			info.add(nomSociete);info.add(suffSociete);info.add(adresse);info.add(villee);info.add(codePostal);
			info.add(payss);info.add(telephone);
			this.add( info, BorderLayout.CENTER );
			JPanel bouton = new JPanel();
			bouton.add(valider);bouton.add(retour);
			this.add(bouton, BorderLayout.SOUTH);
			valider.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					if(!nom.getText().equals("") && !suff.getText().equals("") && !adr.getText().equals("") &&
							!ville.getText().equals("") && !codeP.getText().equals("")  &&!tele.getText().equals("")){
						String ID_client = fonc.randomIDClient();
						Tables t = new Tables(connecte);
						String mdp = t.randomID(11);
						try {
							connecte.nouveauClient(ID_client,nom.getText().toUpperCase(),suff.getText().toUpperCase(),adr.getText().toUpperCase(),ville.getText().toUpperCase(),codeP.getText().toUpperCase(),pays.getSelectedItem().toString(),tele.getText().toUpperCase(), mdp);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						jo.showMessageDialog(null, "Votre ID: "+ID_client+" et votre mot de passe:"+mdp, "Information", JOptionPane.INFORMATION_MESSAGE);
						new FenetreConnexion();
						FenetreNouveauClient.this.setVisible(false);
						try {
							connecte.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					else{
						jo.showMessageDialog(null, "Veuillez remplir tout les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}					
			});
			retour.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {	
					new FenetreConnexion();
					FenetreNouveauClient.this.setVisible(false);
					setVisible(false);
				}	
			});

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );	
			this.setResizable(false);
			this.setSize( 515, 250 );
			this.setVisible( true );
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void Pays(){
		int i = 0;
		try {
			ResultSet rs = connecte.pays();
			while(rs.next()){
				this.Toutpays[i] = rs.getObject(1).toString();
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
