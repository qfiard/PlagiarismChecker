package General;

import javax.swing.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.security.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import Client.ListeProduitsDispo;
import Douane.ListeColisPalette;
import Douane.ListePaletteConteneur;
import Douane.ListeProduitsColis;
import Douane.RechercheDetailsCommande;
import Douane.TableauDonneesCommandeExpediees;
import Douane.TableauDonneesCommandesControlees;
import Douane.TableauDonneesCommandesExpedieesNonControlees;
import Emballeur.ListeCommandeClient;
import Gerant.ListeClientPlusDepensies;
import Gerant.TableauDonneesClient;
import Gerant.ListeProduitsPlusVendus;
import Gerant.TableauDonneesEmployes;



public class MenuGraphique extends JFrame implements ActionListener {

	/** Boutons accueil */

	protected JButton boutonValider = new JButton("Valider");  
	
	/** ACTIONS QUI SUIVENT L'ACTION BOUTON VALIDER */
	protected int g = 0;
	protected int d = 0;
	protected int em = 0;
	protected int t = 0;
	protected int c=0;
	protected int v=0;
	protected int com=0;
	protected int lm=0;
	protected int etatColis=0;
	protected int bc=0;
	protected int bc2=0;
	protected int changerLog = 0;
	protected int changerMdp = 0;
	protected int changerLogMdp = 0;
	protected int qualifiant=0;
	protected int dateLimite=0;
	protected int detailCommande=0;
	protected int dateLivraison=0;
	protected int licencierDouane=0;
	protected int licencierTransporteur=0;
	protected int licencierEmballeur=0;
	protected int rechercheClient=0;
	protected int produitColis=0;
	protected int colisPalette=0;
	protected int paletteConteneur=0;
	protected int resultatControle=0;
	protected int colisPal=0;
	protected int dateColis=0;
	protected int datePalette=0;
	
	protected String vTxtLogin;
	protected String vTxtMdp;

	/** INTERFACE GRAPHIQUE DOUANE */
	protected JButton boutonCommande = new JButton("Chercher une commande");
	protected JButton boutonPalette = new JButton("Liste des palettes d'un conteneur");
	protected JButton boutonColis = new JButton("Liste des colis d'une palette");
	protected JButton boutonProduit = new JButton("Liste des produits d'un colis");

	protected JButton boutonListerCommandeExpedie = new JButton("Commandes expediees par le pays de votre douane");
	protected JButton boutonListerCommandeControlees = new JButton("Commandes controlees par votre douane");
	protected JButton boutonListerCommandeExpNControlees = new JButton("Commandes expediees mais non controlees par votre douane");
	protected JButton boutonRechercheCommandeDouane = new JButton("Rechercher commande :");
	protected JButton boutonDetailsCommandes = new JButton("Visualiser details d'une commande");
	protected JButton boutonResultatControle = new JButton("Entrer resultat d'un controle");
	protected JButton boutonStatistiquesControles = new JButton("Visualiser statistiques sur les controles");
	protected JButton boutonProduitsTransportes = new JButton("Acceder au prix des produits transportes");
	protected JButton boutonRetourDouane = new JButton("Retour au menu precedent");
	
	protected JLabel numeroColis = new JLabel("Entrez le numero du colis : ");
	protected JFormattedTextField txtNumeroColis = new JFormattedTextField();
	
	protected JLabel controleDouane = new JLabel("Entrez les resultats de controle : (accepte, non_controle ou refuse");
	protected JFormattedTextField txtControleDouane = new JFormattedTextField();
	
	/** INTERFACE GRAPHIQUE GERANT */
	protected JButton boutonChangerPrixProduits = new JButton("Changer le prix des produits");
	protected JButton boutonLicencierEmployes = new JButton("Licencier un employe ");
	protected JButton boutonListerClient = new JButton("Liste des clients");
	protected JButton boutonListerEmployes = new JButton("Liste des employes");
	protected JButton boutonNbColisParEmballeur = new JButton("Nombre de colis qu'un emballeur traite par jour");
	protected JButton boutonProduitPlusVendus = new JButton("Produits les plus vendus");
	protected JButton boutonClientsPlusDepensies = new JButton("Clients les plus depensies");
	
	protected JButton boutonLicencierDouane = new JButton("Licencier une douane");
	protected JButton boutonLicencierTransporteur = new JButton("Licencier un transporteur");
	protected JButton boutonLicencierEmballeur = new JButton("Licencier un emballeur");
	
	protected JLabel NumeroProduit = new JLabel("Entrez le numero du produit : ");
	protected JFormattedTextField txtNumeroProduit = new JFormattedTextField();
	protected JLabel NouveauPrix = new JLabel("Entrez le nouveau prix : ");
	protected JFormattedTextField txtNouveauPrix = new JFormattedTextField();
	protected JLabel NomEmploye = new JLabel("Entrez le nom de l'employe a licencie : ");
	protected JFormattedTextField txtNomEmploye = new JFormattedTextField();
	protected String vTxtNumeroProduit;
	protected int vTxtNouveauPrix;
	protected String vTxtNomEmploye;
	
	
	
	/** INTERFACE GRAPHIQUE EMBALLEUR */ 
	protected JButton boutonListeCommandeClient = new JButton("Liste des commandes d'un client");
	protected JButton boutonConditionne = new JButton("Entrez les colis et palettes conditionnes");
	
	protected JButton boutonDateColis = new JButton("Entrez un colis conditionne ");
	protected JButton boutonDatePalettee = new JButton("Entrez une palette conditionne ");

	
	protected JLabel date = new JLabel("Entrez une date : ");
	protected JFormattedTextField txtDate = new JFormattedTextField();
	
	protected JLabel NumeroClient = new JLabel("Entrez le numero du client : ");
	protected JFormattedTextField txtNumeroClient = new JFormattedTextField();
	protected String vTxtNumeroClient;
	
	
	/** INTERFACE GRAPHIQUE TRANSPORTEUR */
	protected JButton boutonQualifiantColis = new JButton("Connaitre le qualifiant d'un colis");
	protected JButton boutonDateLimite = new JButton("Connaitre la date limite de livraison pour un colis");
	protected JLabel qualifiantColis = new JLabel(" Entrez le numero du colis : ");
	protected JFormattedTextField txtQualifiantColis = new JFormattedTextField();


	/** INTERFACE CLIENT */
	
	protected String AncienLogin;
	protected String AncienMdp;
	
	protected JButton boutonEtatColis = new JButton("Connaitre l'avancement du colis");
	protected JButton boutonProduitsDisponibles = new JButton("Lister les produits disponibles");
	protected JButton boutonChoisirDateLivraison = new JButton("Choisir une date de livraison");
	protected JButton boutonChangerLoginMdp = new JButton("Changer le login et/ou mot de passe");

	protected JButton boutonChangerQueLogin = new JButton("Changer seulement le login");
	protected JButton boutonChangerQueMdp = new JButton("Changer seulement le mot de passe");
	protected JButton boutonChangerLoginETMdp = new JButton("Changer le login et le mot de passe");
	protected JButton boutonRetourClient = new JButton("Retour au menu precedent");
	
	protected JLabel NouveauLogin = new JLabel("Entrer le nouveau login");
	protected JFormattedTextField txtNouveauLogin = new JFormattedTextField();
	protected JLabel NouveauMdp = new JLabel("Entrer le nouveau Mdp");
	protected JFormattedTextField txtNouveauMdp = new JFormattedTextField();
	
	protected JLabel NumeroCommande = new JLabel("Entrer le numero de la commande");
	protected JFormattedTextField txtNumeroCommande = new JFormattedTextField();
	protected String vTxtNumeroCommande;
	
	protected JLabel jdateLivraison = new JLabel("Entrer la date de livraison souhaite ");
	protected JFormattedTextField txtDateLivraison = new JFormattedTextField();
	protected String vTxtDateLivraison;

	protected JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER));


	protected JPanel south = new JPanel();
	protected JPanel south1 = new JPanel();
	protected JPanel tab = new JPanel();

	/** MENU BARRE */
	private static JMenuBar menuBar;
	private JMenu connect;
	private JMenuItem gerant, douane, emballeur, transporteur, client, deconnexion;

	/** CONNEXION */
	protected JLabel login = new JLabel("Login");
	protected JLabel mdp = new JLabel("Mot de Passe");
	protected JFormattedTextField txtLogin = new JFormattedTextField();
	protected JPasswordField txtMdp = new JPasswordField();

	public MenuGraphique(String log, String password){

		this.setTitle("Connexion");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
		this.setLayout(new GridLayout(0, 1));

		menuBar = new JMenuBar();

		connect = new JMenu("Connexion");
		menuBar.add(connect);
		
		gerant = new JMenuItem("Gerant");
		this.connect.add(gerant);

		douane = new JMenuItem("Douane");
		this.connect.add(douane);


		emballeur = new JMenuItem("Emballeur");
		this.connect.add(emballeur);

		transporteur = new JMenuItem("Transporteur");
		this.connect.add(transporteur);

		client = new JMenuItem("Client");
		this.connect.add(client);

		deconnexion = new JMenuItem("Deconnexion");
		menuBar.add(deconnexion);

		this.setJMenuBar(menuBar);

		/** ACTIONS MENU BARRE */
		this.connect.addActionListener(this);
		this.douane.addActionListener(this);
		this.gerant.addActionListener(this);
		this.emballeur.addActionListener(this);
		this.transporteur.addActionListener(this);
		this.client.addActionListener(this);
		this.deconnexion.addActionListener(this);
		
		this.boutonValider.addActionListener(this);
		
		/** ACTIONS GERANT */
		this.boutonCommande.addActionListener(this);
		this.boutonLicencierEmployes.addActionListener(this);
		this.boutonProduitPlusVendus.addActionListener(this);
		this.boutonListerClient.addActionListener(this);
		this.boutonClientsPlusDepensies.addActionListener(this);
		this.boutonListerEmployes.addActionListener(this);
		
		this.boutonLicencierDouane.addActionListener(this);
		this.boutonLicencierTransporteur.addActionListener(this);
		this.boutonLicencierEmballeur.addActionListener(this);
		
		/** ACTIONS CLIENT */
		this.boutonChangerQueLogin.addActionListener(this);
		this.boutonChangerQueMdp.addActionListener(this);
		this.boutonChangerLoginETMdp.addActionListener(this);
		this.boutonEtatColis.addActionListener(this);
		this.boutonChangerLoginMdp.addActionListener(this);
		this.boutonChangerPrixProduits.addActionListener(this);
		this.boutonProduitsDisponibles.addActionListener(this);
		this.boutonRetourClient.addActionListener(this);
		this.boutonChoisirDateLivraison.addActionListener(this);
		this.boutonProduitPlusVendus.addActionListener(this);
		
		/** ACTIONS DOUANE */
		this.boutonProduitsTransportes.addActionListener(this);
		this.boutonDetailsCommandes.addActionListener(this);
		this.boutonRechercheCommandeDouane.addActionListener(this);
		this.boutonListerCommandeControlees.addActionListener(this);
		this.boutonListerCommandeExpedie.addActionListener(this);
		this.boutonListerCommandeExpNControlees.addActionListener(this);
		this.boutonRetourDouane.addActionListener(this);
		this.boutonProduit.addActionListener(this);
		this.boutonColis.addActionListener(this);
		this.boutonPalette.addActionListener(this);
		
		/** ACTIONSTRANSPORTEUR */
		this.boutonQualifiantColis.addActionListener(this);
		this.boutonDateLimite.addActionListener(this);
		
		/** EMBALLEUR */
		this.boutonListeCommandeClient.addActionListener(this);
		this.boutonConditionne.addActionListener(this);
		this.boutonDateColis.addActionListener(this);
		this.boutonDatePalettee.addActionListener(this);
		
		this.setContentPane(this.container);
		this.container.setPreferredSize(new Dimension(600,200));
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		

		try{

			Connexion co = new Connexion(Connexion.log,Connexion.password);
			
			if(e.getSource() == this.douane){

				d++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtLogin.setPreferredSize(new Dimension(50, 20));
				this.txtMdp.setPreferredSize(new Dimension(50, 20));
				south.add(login);
				south.add(txtLogin);
				south.add(mdp);
				south.add(txtMdp);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();


			}

			else if(e.getSource() == this.gerant){

				g++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtLogin.setPreferredSize(new Dimension(50, 20));
				this.txtMdp.setPreferredSize(new Dimension(50, 20));
				south.add(login);
				south.add(txtLogin);
				south.add(mdp);
				south.add(txtMdp);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();


			}
			
			else if(e.getSource() == this.emballeur){

				em++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtLogin.setPreferredSize(new Dimension(50, 20));
				this.txtMdp.setPreferredSize(new Dimension(50, 20));
				south.add(login);
				south.add(txtLogin);
				south.add(mdp);
				south.add(txtMdp);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();


			}

			else if(e.getSource() == this.transporteur){

				t++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtLogin.setPreferredSize(new Dimension(50, 20));
				this.txtMdp.setPreferredSize(new Dimension(50, 20));
				south.add(login);
				south.add(txtLogin);
				south.add(mdp);
				south.add(txtMdp);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();


			}

			else if(e.getSource() == this.client){

				c++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtLogin.setPreferredSize(new Dimension(50, 20));
				this.txtMdp.setPreferredSize(new Dimension(50, 20));
				south.add(login);
				south.add(txtLogin);
				south.add(mdp);
				south.add(txtMdp);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();


			}

			if (e.getSource() == this.deconnexion){
				this.container.remove(this.south); 
				this.south.removeAll();
				this.south1.removeAll();
				this.container.repaint();
				this.container.revalidate();

			}
			
			if(e.getSource() == this.boutonValider){

				v++; 
				vTxtLogin = txtLogin.getText();
				vTxtMdp = txtMdp.getText();
				txtMdp.setText("");
				txtLogin.setText("");
				
				/** Si l'utilisateur est une douane */
				if(d != 0){
					this.d=0;
					if(co.reccupLogin(vTxtLogin, vTxtMdp ,"douane")){
						AncienLogin = vTxtLogin;
						this.container.remove(this.south); 
						this.south.removeAll();
						south.add(boutonCommande);
						south.add(boutonPalette);
						south.add(boutonColis);
						south.add(boutonProduit);
						this.add(south);
						this.pack();
					}
					else{
						JOptionPane.showMessageDialog(this,"Login ou mot de passe incorrect","Erreur", JOptionPane.ERROR_MESSAGE);
					}

				}
				
				/** Si l'utilisateur est le gerant */
				else if(g !=0){
					this.g=0;
					if(co.reccupLogin(vTxtLogin, vTxtMdp , "gerant")){
						this.container.remove(this.south); 
						this.south.removeAll();
						south.add(boutonChangerPrixProduits);
						south.add(boutonLicencierEmployes);
						south.add(boutonListerClient);
						south.add(boutonListerEmployes);
						south.add(boutonNbColisParEmballeur);
						south.add(boutonProduitPlusVendus);
						south.add(boutonClientsPlusDepensies);
						this.add(south);
						this.pack();	
					}
					else{
						g=0;
						JOptionPane.showMessageDialog(this,"Login ou mot de passe incorrect","Erreur", JOptionPane.ERROR_MESSAGE);
					}

				}

				/** Si l'utilisateur est un emballeur */
				else if(this.em != 0){
					if(co.reccupLoginEmballeur(vTxtLogin, vTxtMdp, "emballeur")){
						this.container.remove(this.south);
						this.south.removeAll();
						south.add(boutonListeCommandeClient);
						south.add(boutonConditionne);
						this.add(south);
						this.pack();
						this.em=0;
					}
					else{
						em=0;
						JOptionPane.showMessageDialog(this,"Login ou mot de passe incorrect","Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				else if(c != 0){
					
					if(co.reccupLoginClient(vTxtLogin, vTxtMdp,"client")){
						AncienLogin = vTxtLogin;
						AncienMdp = vTxtMdp;
						c=0;
						this.container.remove(this.south);
						this.south.removeAll();
						south.add(boutonEtatColis);
						south.add(boutonProduitsDisponibles);
						south.add(boutonChoisirDateLivraison);
						south.add(boutonChangerLoginMdp);
						this.add(south);
						this.pack();
						this.c=0;
					}
					else{
						JOptionPane.showMessageDialog(this,"Login ou mot de passe incorrect","Erreur", JOptionPane.ERROR_MESSAGE);
					}

				}
				
				/** Si l'utilisateur est un transporteur */
				else if(t != 0){
					if(co.reccupLoginTransporteur(vTxtLogin, vTxtMdp, "transporteur")){
						this.t=0;
						this.container.remove(this.south);
						this.south.removeAll();
						south.add(boutonQualifiantColis);
						south.add(boutonDateLimite);
						this.add(south);
						this.pack();
						
					}
					else{
						t=0;
						JOptionPane.showMessageDialog(this,"Login ou mot de passe incorrect","Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				/** GERANT */
				if(bc != 0){
					
					JTable table1;
					bc2++;
					vTxtNumeroProduit = this.txtNumeroProduit.getText();
					this.container.remove(this.south);
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.setSize(200, 100);
					
					ResultSet rs = co.rechercheProduit(this.vTxtNumeroProduit);
					
					if(rs.next()){
					
						rs.previous();

						LinkedList<Object[]> liste = new LinkedList<Object[]>();
						Object[][] donnees;
						String[] nomColonnes = { "num_produit","description", "cout"};

						while(rs.next()){
							
							Object[] data = new Object[3];
							data[0] = rs.getString(1);
							data[1] = rs.getString(2);
							data[2] = rs.getString(6);
							liste.add(data);
						}
						
						donnees = new Object[liste.size()][];


						for(int i=0; i<donnees.length; i++){
							donnees[i] = liste.get(i);
						}

						table1 = new JTable(donnees, nomColonnes);
						south.add(table1);
						south.add(this.NouveauPrix);
						south.add(this.txtNouveauPrix);
						south.add(this.boutonValider);
						this.add(south);
						this.pack();
						this.bc=0;
						

					}

				}
			
			if(bc2 != 0){
				try {
					vTxtNouveauPrix = Integer.parseInt(this.txtNouveauPrix.getText());
					co.changerPrixProduits(vTxtNouveauPrix, vTxtNumeroProduit);
					JOptionPane.showMessageDialog(this,"Prix change ","Valider", JOptionPane.ERROR_MESSAGE);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.add(boutonChangerPrixProduits);
					south.add(boutonListerClient);
					south.add(boutonListerEmployes);
					south.add(boutonNbColisParEmballeur);
					south.add(boutonProduitPlusVendus);
					south.add(boutonClientsPlusDepensies);
					this.add(south);
					this.pack();
					
				}
				
				catch(SQLException e1){
					JOptionPane.showMessageDialog(this,"Erreur sur l'entree du prix ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
						
			}
			
			if(licencierDouane != 0){
				try{
					this.vTxtNomEmploye = this.txtNomEmploye.getText();
					co.licencierDouane(vTxtNomEmploye);
					JOptionPane.showMessageDialog(this,"Employe licencie ! ","OK", JOptionPane.INFORMATION_MESSAGE);
					licencierDouane=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur licenciement non effectue ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(licencierEmballeur != 0){
				try{
					this.vTxtNomEmploye = this.txtNomEmploye.getText();
					co.licencierEmballeur(vTxtNomEmploye);
					JOptionPane.showMessageDialog(this,"Employe licencie ! ","OK", JOptionPane.INFORMATION_MESSAGE);
					licencierEmballeur=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur licenciement non effectue ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(licencierTransporteur != 0){
				try{
					this.vTxtNomEmploye = this.txtNomEmploye.getText();
					co.licencierTransporteur(vTxtNomEmploye);
					JOptionPane.showMessageDialog(this,"Employe licencie ! ","OK", JOptionPane.INFORMATION_MESSAGE);
					licencierTransporteur=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur licenciement non effectue ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
			/** CLIENT */
			
			if(etatColis != 0){
				try{
					this.vTxtNumeroCommande  = this.txtNumeroCommande.getText();
					int intTxtNumeroCommande = Integer.parseInt(this.vTxtNumeroCommande);
					ResultSet rs = co.etatColis(intTxtNumeroCommande);
					if(rs.next()){
						JOptionPane.showMessageDialog(this,"Numero commande :" + rs.getString(1) + "Etat colis : " + rs.getString(2) ,"Valider", JOptionPane.ERROR_MESSAGE);
	
						this.container.remove(this.south);
						this.south.removeAll();
						south.setLayout(new GridLayout(0,1));
						south.setSize(200, 100);
						south.add(boutonEtatColis);
						south.add(boutonProduitsDisponibles);
						south.add(boutonChoisirDateLivraison);
						south.add(boutonChangerLoginMdp);
						this.add(south);
						this.pack();
						this.etatColis=0;
					}

			}	 catch (NumberFormatException ee){
				JOptionPane.showMessageDialog(this,"Erreur changement non effectuer ","Erreur", JOptionPane.ERROR_MESSAGE);
			}
			}
			
			if(changerLog != 0){
				
				String NouveauLogin=txtNouveauLogin.getText();
				
				try {
					co.changerQueLogin(AncienLogin, NouveauLogin);
					JOptionPane.showMessageDialog(this,"Login change ! ","Valider", JOptionPane.ERROR_MESSAGE);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.add(boutonEtatColis);
					south.add(boutonProduitsDisponibles);
					south.add(boutonChoisirDateLivraison);
					south.add(boutonChangerLoginMdp);
					this.add(south);
					this.pack();
					this.changerLog=0;
					
				} catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur changement non effectuer ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(changerMdp != 0){
				
				String NouveauMdp=txtNouveauMdp.getText();
				
				try {
					co.changerQueMdp(AncienMdp, NouveauMdp);
					JOptionPane.showMessageDialog(this,"Mot de passe change ! ","Valider", JOptionPane.ERROR_MESSAGE);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.add(boutonEtatColis);
					south.add(boutonProduitsDisponibles);
					south.add(boutonChoisirDateLivraison);
					south.add(boutonChangerLoginMdp);
					this.add(south);
					this.pack();
					this.changerMdp=0;
					
				} catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur changement non effectuer ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(changerLogMdp != 0){
				
				String NouveauLog=txtNouveauLogin.getText();
				String NouveauMdp=txtNouveauMdp.getText();
				
				try {
					co.changerLoginETMdp(AncienLogin,NouveauLog,AncienMdp, NouveauMdp);
					JOptionPane.showMessageDialog(this,"Login et Mot de passe change ! ","Valider", JOptionPane.ERROR_MESSAGE);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.add(boutonEtatColis);
					south.add(boutonProduitsDisponibles);
					south.add(boutonChoisirDateLivraison);
					south.add(boutonChangerLoginMdp);
					this.add(south);
					this.pack();
					this.changerLogMdp=0;
					
				} catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur changement non effectuer ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		
			if(dateLivraison != 0){
				try{
					this.vTxtNumeroCommande  = this.txtNumeroCommande.getText();
					int intTxtNumeroCommande = Integer.parseInt(this.vTxtNumeroCommande);
					String vtxtDateLivraison  = this.txtDateLivraison.getText();
					co.choisirDateLivraison(intTxtNumeroCommande,AncienLogin,vtxtDateLivraison);
					JOptionPane.showMessageDialog(this,"Date enreregistree ","OK", JOptionPane.INFORMATION_MESSAGE);
					dateLivraison=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Date non enreregistree ","erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			/** DOUANE */
			if(detailCommande != 0){
				try{
					this.vTxtNumeroCommande  = this.txtNumeroCommande.getText();
					int intTxtNumeroCommande = Integer.parseInt(this.vTxtNumeroCommande);
					RechercheDetailsCommande bd = new RechercheDetailsCommande(intTxtNumeroCommande);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonListerCommandeExpedie);
					south.add(boutonListerCommandeControlees);
					south.add(boutonListerCommandeExpNControlees);
					south.add(boutonDetailsCommandes);
					south.add(boutonResultatControle);
					south.add(boutonStatistiquesControles);
					south.add(boutonRetourDouane);
					this.add(south);
					this.pack();
					this.detailCommande=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur numero de commande ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(produitColis != 0){
				try{
					String vTxtNumeroColis  = this.txtNumeroColis.getText();
					int intTxtNumeroColis = Integer.parseInt(vTxtNumeroColis);
					ListeProduitsColis bd = new ListeProduitsColis(intTxtNumeroColis);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonListerCommandeExpedie);
					south.add(boutonListerCommandeControlees);
					south.add(boutonListerCommandeExpNControlees);
					south.add(boutonDetailsCommandes);
					south.add(boutonResultatControle);
					south.add(boutonStatistiquesControles);
					south.add(boutonRetourDouane);
					this.add(south);
					this.pack();
					this.produitColis=0;
				}
				catch (NumberFormatException ee){
					this.produitColis=0;
					JOptionPane.showMessageDialog(this,"Erreur numero de colis ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(colisPalette != 0){
				try{
					String vTxtNumeroColis  = this.txtNumeroColis.getText();
					int intTxtNumeroColis = Integer.parseInt(vTxtNumeroColis);
					ListeColisPalette bd = new ListeColisPalette(intTxtNumeroColis);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonListerCommandeExpedie);
					south.add(boutonListerCommandeControlees);
					south.add(boutonListerCommandeExpNControlees);
					south.add(boutonDetailsCommandes);
					south.add(boutonResultatControle);
					south.add(boutonStatistiquesControles);
					south.add(boutonRetourDouane);
					this.add(south);
					this.pack();
					this.colisPalette=0;
				}
				catch (NumberFormatException ee){
					this.colisPalette=0;
					JOptionPane.showMessageDialog(this,"Erreur numero de colis ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(paletteConteneur != 0){
				try{
					String vTxtNumeroColis  = this.txtNumeroColis.getText();
					int intTxtNumeroColis = Integer.parseInt(vTxtNumeroColis);
					ListePaletteConteneur bd = new ListePaletteConteneur(intTxtNumeroColis);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonListerCommandeExpedie);
					south.add(boutonListerCommandeControlees);
					south.add(boutonListerCommandeExpNControlees);
					south.add(boutonDetailsCommandes);
					south.add(boutonResultatControle);
					south.add(boutonStatistiquesControles);
					south.add(boutonRetourDouane);
					this.add(south);
					this.pack();
					this.paletteConteneur=0;
				}
				catch (NumberFormatException ee){
					this.paletteConteneur=0;
					JOptionPane.showMessageDialog(this,"Erreur numero de commande ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(resultatControle != 0){
				try{
					String vTxtNumeroCommande  = this.txtNumeroCommande.getText();
					int intTxtNumeroCommande = Integer.parseInt(vTxtNumeroCommande);
					String vTxtControleDouane  = this.txtControleDouane.getText();
					co.controleDouane(intTxtNumeroCommande, vTxtControleDouane);
					JOptionPane.showMessageDialog(this,"Controle ok ! ","OK", JOptionPane.INFORMATION_MESSAGE);
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonListerCommandeExpedie);
					south.add(boutonListerCommandeControlees);
					south.add(boutonListerCommandeExpNControlees);
					south.add(boutonDetailsCommandes);
					south.add(boutonResultatControle);
					south.add(boutonStatistiquesControles);
					south.add(boutonRetourDouane);
					this.add(south);
					this.pack();
					this.paletteConteneur=0;
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur numero de commande ","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			/** TRANSPORTEUR */
			if(qualifiant != 0 ){
				
				int vIdColis=Integer.parseInt(txtQualifiantColis.getText());
				
				try {
					ResultSet rs = co.qualifiantProduit(vIdColis);
					if(rs.next()){
						JOptionPane.showMessageDialog(this,rs.getString(1) ,"Valider", JOptionPane.ERROR_MESSAGE);
						this.container.remove(this.south); 
						this.south.removeAll();
						south.add(boutonQualifiantColis);
						south.add(boutonDateLimite);
						this.add(south);
						this.pack();
						this.qualifiant=0;
					}
				} catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur colis non trouve","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(dateLimite != 0){
				
				int vIdColis=Integer.parseInt(txtQualifiantColis.getText());

				try {
					ResultSet rs = co.dateLimiteLivraison(vIdColis);
					if(rs.next()){
						JOptionPane.showMessageDialog(this,"Date limite livraison : " + rs.getString(1) ,"Valider", JOptionPane.ERROR_MESSAGE);
						this.container.remove(this.south); 
						this.south.removeAll();
						south.add(boutonQualifiantColis);
						south.add(boutonDateLimite);
						this.add(south);
						this.pack();
						this.qualifiant=0;
					}
				} catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur date non trouve","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			/** EMBALLEUR */
			if(rechercheClient != 0){
				try{
					vTxtNumeroClient = this.txtNumeroClient.getText();
					ListeCommandeClient bd = new ListeCommandeClient(vTxtNumeroClient);
					this.container.remove(this.south);
					this.south.removeAll();
					south.add(boutonListeCommandeClient);
					south.add(boutonConditionne);
					this.add(south);
					this.pack();
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur commandes non trouve","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(dateColis != 0){
				
				try{
					String vTxtQualifiantColis = this.txtQualifiantColis.getText();
					int intTxtNumeroColis = Integer.parseInt(vTxtQualifiantColis);
					String vTxtDate = this.txtDate.getText();
					System.out.println(intTxtNumeroColis);
					co.conditionnerColis(intTxtNumeroColis, vTxtDate);
					JOptionPane.showMessageDialog(this,"OK","OK", JOptionPane.INFORMATION_MESSAGE);
					this.container.remove(this.south);
					this.south.removeAll();
					south.add(boutonListeCommandeClient);
					south.add(boutonConditionne);
					this.add(south);
					this.pack();
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur commandes non trouve","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		
			if(colisPalette != 0){
				try{
					String vTxtQualifiantColis = this.txtQualifiantColis.getText();
					int intTxtNumeroColis = Integer.parseInt(vTxtQualifiantColis);
					String vTxtDate = this.txtDate.getText();
					co.conditionnerPalette(intTxtNumeroColis, vTxtDate);
					JOptionPane.showMessageDialog(this,"OK","OK", JOptionPane.INFORMATION_MESSAGE);
					this.container.remove(this.south);
					this.south.removeAll();
					south.add(boutonListeCommandeClient);
					south.add(boutonConditionne);
					this.add(south);
					this.pack();
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur commandes non trouve","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			}

			/** DOUANE */
			
			if(e.getSource() == this.boutonCommande ){

				com++; 
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(boutonListerCommandeExpedie);
				south.add(boutonListerCommandeControlees);
				south.add(boutonListerCommandeExpNControlees);
				south.add(boutonDetailsCommandes);
				south.add(boutonResultatControle);
				south.add(boutonStatistiquesControles);
				south.add(boutonRetourDouane);
				this.add(south);
				this.pack(); 

			}
			
			if(e.getSource() == this.boutonListerCommandeExpedie){

				TableauDonneesCommandeExpediees bd = new TableauDonneesCommandeExpediees(AncienLogin);
				
			} 

			if(e.getSource() == this.boutonListerCommandeControlees){
				
				TableauDonneesCommandesControlees bd = new TableauDonneesCommandesControlees(AncienLogin);
			}
			
			if(e.getSource() == this.boutonListerCommandeExpNControlees){
				
				try{
				TableauDonneesCommandesExpedieesNonControlees bd = new TableauDonneesCommandesExpedieesNonControlees(AncienLogin);
				}
				catch (NumberFormatException ee){
					JOptionPane.showMessageDialog(this,"Erreur commade","Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(e.getSource() == this.boutonDetailsCommandes){
				
				detailCommande++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroCommande.setPreferredSize(new Dimension(50, 20));
				south.add(NumeroCommande);
				south.add(txtNumeroCommande);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			
			if(e.getSource() == this.boutonResultatControle){
				resultatControle++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroCommande.setPreferredSize(new Dimension(50, 20));
				this.txtControleDouane.setPreferredSize(new Dimension(50, 20));
				south.add(NumeroCommande);
				south.add(txtNumeroCommande);
				south.add(controleDouane);
				south.add(txtControleDouane);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			
			if(e.getSource() == this.boutonProduitsTransportes){
				
			}
			
			if(e.getSource() == this.boutonProduit){
				produitColis++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroColis.setPreferredSize(new Dimension(50, 20));
				south.add(numeroColis);
				south.add(txtNumeroColis);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			
			if(e.getSource() == this.boutonColis){
				colisPalette++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroColis.setPreferredSize(new Dimension(50, 20));
				south.add(numeroColis);
				south.add(txtNumeroColis);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			
			if(e.getSource() == this.boutonPalette){
				paletteConteneur++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroColis.setPreferredSize(new Dimension(50, 20));
				south.add(numeroColis);
				south.add(txtNumeroColis);
				south.add(boutonValider);

				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			

			
			if(e.getSource() == this.boutonRetourDouane){
				this.container.remove(this.south); 
				this.south.removeAll();
				south.add(boutonCommande);
				south.add(boutonPalette);
				south.add(boutonColis);
				south.add(boutonProduit);
				this.add(south);
				this.pack();
			}
			
			
			/** GERANT */

			if(e.getSource() == boutonLicencierEmployes){
				this.container.remove(this.south);
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(boutonLicencierDouane);
				south.add(boutonLicencierTransporteur);
				south.add(boutonLicencierEmballeur);
				this.add(south);
				this.pack();
			}
			
			if(e.getSource() == boutonLicencierDouane){
				JTable table;
				licencierDouane++;
				ResultSet rs = co.listerEmployesDouane();
				this.container.remove(this.south);
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.setSize(600, 300);
				if(rs.next()){
				
					rs.previous();

					LinkedList<Object[]> liste = new LinkedList<Object[]>();
					Object[][] donnees;
					String[] nomColonnes = { "login"};

					while(rs.next()){
						
						Object[] data = new Object[1];
						data[0] = rs.getString(1);
						liste.add(data);
					}
					
					donnees = new Object[liste.size()][];


					for(int i=0; i<donnees.length; i++){
						donnees[i] = liste.get(i);
					}

					table = new JTable(donnees, nomColonnes);
					
					south.add(table);
					south1.add(this.NomEmploye);
					south1.add(this.txtNomEmploye);
					this.txtNomEmploye.setPreferredSize(new Dimension(70, 20));
					this.boutonValider.setPreferredSize(new Dimension(80, 20));
					south1.add(this.boutonValider);
					this.add(south);
					this.add(south1);
					this.pack();
					

				}
			}
			
			if(e.getSource() == boutonLicencierEmballeur){
				JTable table;
				licencierEmballeur++;
				ResultSet rs = co.listerEmployesEmballeur();
				this.container.remove(this.south);
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.setSize(600, 300);
				if(rs.next()){
				
					rs.previous();

					LinkedList<Object[]> liste = new LinkedList<Object[]>();
					Object[][] donnees;
					String[] nomColonnes = { "login"};

					while(rs.next()){
						
						Object[] data = new Object[1];
						data[0] = rs.getString(1);
						liste.add(data);
					}
					
					donnees = new Object[liste.size()][];


					for(int i=0; i<donnees.length; i++){
						donnees[i] = liste.get(i);
					}

					table = new JTable(donnees, nomColonnes);
					
					south.add(table);
					south1.add(this.NomEmploye);
					south1.add(this.txtNomEmploye);
					this.txtNomEmploye.setPreferredSize(new Dimension(70, 20));
					this.boutonValider.setPreferredSize(new Dimension(80, 20));
					south1.add(this.boutonValider);
					this.add(south);
					this.add(south1);
					this.pack();
					

				}
			}
			
			if(e.getSource() == boutonLicencierTransporteur){
				JTable table;
				licencierTransporteur++;
				ResultSet rs = co.listerEmployesTransporteur();
				this.container.remove(this.south);
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.setSize(600, 300);
				if(rs.next()){
				
					rs.previous();

					LinkedList<Object[]> liste = new LinkedList<Object[]>();
					Object[][] donnees;
					String[] nomColonnes = { "login"};

					while(rs.next()){
						
						Object[] data = new Object[1];
						data[0] = rs.getString(1);
						liste.add(data);
					}
					
					donnees = new Object[liste.size()][];


					for(int i=0; i<donnees.length; i++){
						donnees[i] = liste.get(i);
					}

					table = new JTable(donnees, nomColonnes);
					
					south.add(table);
					south1.add(this.NomEmploye);
					south1.add(this.txtNomEmploye);
					this.txtNomEmploye.setPreferredSize(new Dimension(70, 20));
					this.boutonValider.setPreferredSize(new Dimension(80, 20));
					south1.add(this.boutonValider);
					this.add(south);
					this.add(south1);
					this.pack();
					

				}
			}
			
			if(e.getSource() == boutonChangerPrixProduits){
				
				bc++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(NumeroProduit);
				south.add(txtNumeroProduit);
				south.add(boutonValider);
				this.add(south);
				this.pack(); 
				
				
			}

			if(e.getSource() == boutonListerClient){
				
				TableauDonneesClient bd = new TableauDonneesClient();
				
			}
			
			if(e.getSource() == this.boutonProduitPlusVendus){

				ListeProduitsPlusVendus bd = new ListeProduitsPlusVendus();
				
			}
			
			if(e.getSource() == this.boutonClientsPlusDepensies){
				
				ListeClientPlusDepensies bd = new ListeClientPlusDepensies();
				
			}
			
			if(e.getSource() == this.boutonListerEmployes){

				TableauDonneesEmployes bd = new TableauDonneesEmployes();

			}

			/** CLIENT */
			
			if(e.getSource() == this.boutonProduitsDisponibles){
				
				ListeProduitsDispo bd = new ListeProduitsDispo();
				
			}
			
			if(e.getSource() == this.boutonChangerLoginMdp){
				
					lm++;
					this.container.remove(this.south); 
					this.south.removeAll();
					south.setLayout(new GridLayout(0,1));
					south.add(boutonChangerQueLogin);
					south.add(boutonChangerQueMdp);
					south.add(boutonChangerLoginETMdp);
					south.add(boutonRetourClient);
					this.add(south);
					this.pack();
				
			}
			
			if(e.getSource() == this.boutonRetourClient){
				
				this.container.remove(this.south); 
				this.south.removeAll();
				south.add(boutonEtatColis);
				south.add(boutonProduitsDisponibles);
				south.add(boutonChoisirDateLivraison);
				south.add(boutonChangerLoginMdp);
				this.add(south);
				this.pack();
				
			}
			
			if(e.getSource() == this.boutonChangerQueLogin){
				
				changerLog++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(NouveauLogin);
				south.add(txtNouveauLogin);
				south.add(boutonValider);
				this.add(south);
				this.pack();
				
			}

			if(e.getSource() == this.boutonChangerQueMdp){
				
				changerMdp++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(NouveauMdp);
				south.add(txtNouveauMdp);
				south.add(boutonValider);
				this.add(south);
				this.pack();
				
			}

			if(e.getSource() == this.boutonChangerLoginETMdp){
				
				changerLogMdp++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(NouveauLogin);
				south.add(txtNouveauLogin);
				south.add(NouveauMdp);
				south.add(txtNouveauMdp);
				south.add(boutonValider);
				this.add(south);
				this.pack();
				
			}

			if(e.getSource() == this.boutonEtatColis){

				etatColis++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroCommande.setPreferredSize(new Dimension(50, 20));
				south.add(NumeroCommande);
				south.add(txtNumeroCommande);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();

			}
			
			if(e.getSource() == this.boutonChoisirDateLivraison){
				
				dateLivraison++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				this.txtNumeroCommande.setPreferredSize(new Dimension(50, 20));
				south.add(NumeroCommande);
				south.add(txtNumeroCommande);
				south.add(jdateLivraison);
				south.add(txtDateLivraison);
				south.add(boutonValider);
				
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}
			
			/** TRANSPORTEUR */
			
			if(e.getSource() == this.boutonQualifiantColis){
				
				qualifiant++;
				this.container.remove(this.south);
				this.south.removeAll();
				south.add(qualifiantColis);
				south.add(txtQualifiantColis);
				south.add(boutonValider);


				this.add(south, BorderLayout.CENTER);
				this.pack();
			}
			
			if(e.getSource() == this.boutonDateLimite){
				
				dateLimite++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.add(qualifiantColis);
				south.add(txtQualifiantColis);
				south.add(boutonValider);
				
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}
			
			/** EMBALLEUR */
			
			if(e.getSource() == this.boutonListeCommandeClient){
				System.out.println("lol1");
				rechercheClient++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.add(NumeroClient);
				south.add(txtNumeroClient);
				south.add(boutonValider);
				
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}
			
			if(e.getSource() == this.boutonConditionne){
		
				colisPal++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(boutonDateColis);
				south.add(boutonDatePalettee);
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}
			
			if(e.getSource() == this.boutonDateColis){
				
				dateColis++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(qualifiantColis);
				south.add(txtQualifiantColis);
				south.add(date);
				south.add(txtDate);
				south.add(boutonValider);
				
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}

			if(e.getSource() == this.boutonDatePalettee){
				
				datePalette++;
				this.container.remove(this.south); 
				this.south.removeAll();
				south.setLayout(new GridLayout(0,1));
				south.add(qualifiantColis);
				south.add(txtQualifiantColis);
				south.add(date);
				south.add(txtDate);
				south.add(boutonValider);
				
				this.add(south, BorderLayout.CENTER);
				this.pack();
				
			}
			
			co.close();
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		}
}


