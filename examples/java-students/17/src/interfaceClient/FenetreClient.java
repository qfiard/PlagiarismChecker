package interfaceClient;

import interfaceGraphique.FenetreConnexion2;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


public class FenetreClient extends JFrame implements ActionListener{
//public class FenetreClient extends JWindow implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel pseudo;
	//private InformationsClient infos;
	private JButton modifierInfos;
	private JButton quitter;

	private JPanel compte;

	private JTabbedPane onglets;
	private JLabel image;

	private String idClient;
	
	public FenetreClient(String idClient){

		this.setTitle("--Client--");
		this.setLocationRelativeTo(null);
		this.setLocation(50, 50);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setSize(800, 600);
		
		this.idClient = idClient;

		//--Compte

		/*nom = rs.getString(1);
				prenom = rs.getString(2);*/

		/*pseudo = new JLabel(nom.toUpperCase()+" "+prenom+" ("+idClient+")");*/

		//JPanel infos = new JPanel();
		//infos.setLayout(new BorderLayout());

		pseudo = new JLabel("ID: "+idClient);
		compte = new JPanel();		

		ImageIcon iconeModifInfos = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_edit.png"));
			Image resize = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			iconeModifInfos.setImage(resize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		modifierInfos = new JButton(iconeModifInfos);
		modifierInfos.addActionListener(this);
		modifierInfos.setContentAreaFilled(false);
		modifierInfos.setBorderPainted(false);

		ImageIcon iconeQuitter = new ImageIcon();
		try {
			BufferedImage img = ImageIO.read(new File("src/ic_menu_close_clear_cancel.png"));
			Image resize = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			iconeQuitter.setImage(resize);					
		} catch (IOException e) {
			e.printStackTrace();
		}
		quitter = new JButton(iconeQuitter);
		quitter.addActionListener(this);
		quitter.setContentAreaFilled(false);
		quitter.setBorderPainted(false);


		compte.setLayout(new BorderLayout());
		//compte.add(pseudo, BorderLayout.WEST);

		image = new JLabel(new ImageIcon("src/avatar-profil-facebook-1.jpg"));

		JPanel boutons = new JPanel();
		boutons.add(modifierInfos);
		boutons.add(quitter);

		JPanel client = new JPanel();
		client.add(image);
		client.add(pseudo);

		/*compte.add(modifierInfos);
				compte.add(quitter);*/
		compte.add(client, BorderLayout.WEST);
		compte.add(boutons, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(compte, BorderLayout.NORTH);

		//--Onglets

		onglets = new JTabbedPane();
		onglets.addTab("Colis", new JScrollPane(new FenetreColis(idClient)));
		onglets.addTab("Produits", new FenetreProduits(idClient));
		onglets.addTab("Commande", new FenetreCommande(idClient));

		this.add(onglets, BorderLayout.CENTER);

		this.setVisible(true);		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modifierInfos){
			InformationsClient infos = new InformationsClient(idClient);
		}
		else if(e.getSource() == quitter){
			this.dispose();
			FenetreConnexion2 fen = new FenetreConnexion2();
		}
	}

}
