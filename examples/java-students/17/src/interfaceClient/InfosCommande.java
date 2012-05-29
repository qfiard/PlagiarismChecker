package interfaceClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import donnees.ConnexionBD;
import donnees.Constantes;

public class InfosCommande extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JPanel entete = new JPanel();

	private JButton derouler;
	private JButton modifier;

	private boolean appuye = false;

	private JTextField jour = new JTextField("JJ");
	private JTextField mois = new JTextField("MM");
	private JTextField annee = new JTextField("AAAA");

	private JLabel separateur1 = new JLabel("/");
	private JLabel separateur2 = new JLabel("/");

	private JLabel labelJour = new JLabel("JJ");
	private JLabel labelMois = new JLabel("MM");
	private JLabel labelAnnee = new JLabel("AAAA");

	private JPanel colis = new JPanel();
	private JPanel top = new JPanel();

	private int idCommande;

	public InfosCommande(int idCommande, String dateLivraison, String statut){	

		this.idCommande = idCommande;

		//Bouton derouler
		ImageIcon iconeDerouler = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_add.png"));
			Image resize = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			iconeDerouler.setImage(resize);					
		} catch (IOException e) {
			e.printStackTrace();
		}
		derouler = new JButton(iconeDerouler);
		derouler.addActionListener(this);
		derouler.setContentAreaFilled(false);
		derouler.setBorderPainted(false);	

		//Bouton modifier
		ImageIcon iconeModifier = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_save.png"));
			Image resize = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			iconeModifier.setImage(resize);					
		} catch (IOException e) {
			e.printStackTrace();
		}
		modifier = new JButton(iconeModifier);
		modifier.addActionListener(this);
		modifier.setContentAreaFilled(false);
		modifier.setBorderPainted(false);	


		JPanel infosCommande = new JPanel(); 

		if(dateLivraison != null){
			String[] splitDate = dateLivraison.split("-");

			jour.setText(splitDate[2]);
			mois.setText(splitDate[1]);
			annee.setText(splitDate[0]);
		}

		jour.setPreferredSize(new Dimension(30, 20));
		mois.setPreferredSize(new Dimension(30, 20));
		annee.setPreferredSize(new Dimension(60, 20));

		//JLabel infos = new JLabel(idCommande + " [" + dateLivraison + "]");
		JLabel infos = new JLabel("ID: " + idCommande);
		infos.setOpaque(false);
		infos.setBackground(Color.BLUE);

		JLabel labelStatut = new JLabel("[" + statut + "]");


		//ENTETE
		entete.add(derouler);
		entete.add(infos);
		entete.add(jour);
		entete.add(separateur1);
		entete.add(mois);
		entete.add(separateur2);
		entete.add(annee);
		entete.add(modifier);
		entete.add(labelStatut);

		//COLIS
		colis.setLayout(new FlowLayout(FlowLayout.CENTER));

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {

			ResultSet rs = st.executeQuery("SELECT id_colis, statut FROM " +
					Constantes.base_colis + " WHERE id_commande=" + idCommande + ";");
			
			colis.setLayout(new BoxLayout(colis, BoxLayout.PAGE_AXIS));
			
			while(rs.next()){
				colis.add(new InfosColis(rs.getInt(1), rs.getString(2).toUpperCase()));
			}
			
			colis.setVisible(false);
			
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();

		
		

		
		top.setLayout(new BorderLayout());
		top.add(entete, BorderLayout.NORTH);
		top.add(colis, BorderLayout.CENTER);

		if(statut.equals("EXPEDIEE")){
			labelStatut.setForeground(Color.GREEN);
		}
		else if(statut.equals("PARTIELLEMENT-EXPEDIEE")){
			labelStatut.setForeground(Color.ORANGE);
		}
		else{
			labelStatut.setForeground(Color.RED);
		}

		entete.setPreferredSize(new Dimension(600, 50));
		entete.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//top.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		//this.setPreferredSize(new Dimension(400, 50));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(top);
		//this.add(entete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == derouler){			
			appuye = !appuye;

			if(appuye){
				colis.setVisible(true);
				/*top.add(colis, BorderLayout.CENTER);
				top.validate();
				this.validate();*/
				
			}
			else{
				/*top.remove(colis);
				top.validate();
				this.validate();*/
				colis.setVisible(false);
			}
		}
		else if(e.getSource() == modifier){
			if(dateValide()){
				modifierDate();

				String message = "La date de livraison a correctement ete mise a jour";

				JOptionPane.showMessageDialog(null, message, "Mise a jour: Date de livraison",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	public void modifierDate(){
		if(!dateValide()){
			return;
		}

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {

			st.execute("UPDATE " + Constantes.base_commande + " SET " +
					"date_livraison='" + annee.getText() + "-" + mois.getText() + "-" +
					jour.getText() + "';");			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean dateValide(){

		//Jour ou mois ou annee vide
		if(jour.getText().isEmpty() || mois.getText().isEmpty()
				|| annee.getText().isEmpty()){
			String message = "Date incorrecte";
			JOptionPane.showMessageDialog(null, message, "Erreur: Date",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		int jourLivraison=0, moisLivraison=0, anneeLivraison=0;

		//On teste si les valeurs sont entieres
		try{

			jourLivraison = Integer.parseInt(jour.getText());
			moisLivraison = Integer.parseInt(mois.getText());
			anneeLivraison = Integer.parseInt(annee.getText());	

		}catch(Exception e){
			String message = "Format de la date invalide";
			JOptionPane.showMessageDialog(null, message, "Erreur: Date",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		GregorianCalendar today = new GregorianCalendar();

		GregorianCalendar dateLivraison = new GregorianCalendar();

		int jourToday = today.get(Calendar.DATE);
		int moisToday = today.get(Calendar.MONTH);
		int anneeToday = today.get(Calendar.YEAR);

		dateLivraison.add(Calendar.DATE,
				jourToday - (jourToday-jourLivraison));
		dateLivraison.add(Calendar.MONTH,
				moisToday - (moisToday-moisLivraison));
		dateLivraison.add(Calendar.YEAR,
				anneeToday - (anneeToday-anneeLivraison));

		//On regarde si la date de livraison est postérieure a celle d'aujourd'hui
		if(dateLivraison.before(today)){
			String message = "La date est plus ancienne que la date d'aujourd'hui";
			JOptionPane.showMessageDialog(null, message, "Erreur: Date",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;

	}

}
