package interfaceClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import donnees.ConnexionBD;
import donnees.Constantes;

public class InformationsClient extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel labelLogin = new JLabel("Login:");
	private JLabel labelMdp = new JLabel("Mot de passe:");
	private JLabel labelNouveauLogin = new JLabel("Nouveau Login:");
	private JLabel labelAncienMdp = new JLabel("Ancien Mot de passe:");
	private JLabel labelNouveauMdp = new JLabel("Nouveau Mot de passe:");
	private JLabel labelConfirmMdp = new JLabel("Confirmez mot de passe:");

	private JLabel login = new JLabel();	//contiendra le login courant
	private JLabel mdp = new JLabel();	//contiendra le mot de passe courant
	private JTextField nouveauLogin = new JTextField();
	private JTextField ancienMdp = new JTextField();
	private JTextField nouveauMdp = new JTextField();
	private JTextField confirmMdp = new JTextField();

	private JButton modifier = new JButton("Modifier");
	private JButton annuler = new JButton("Annuler");

	private String idClient;

	public InformationsClient(String idClient){			

		/*ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();
		String loginClient, mdpClient;*/

		this.setTitle("Informations");
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


		this.idClient = idClient;
		
		updateInfos();


		//--Ajout des composants

		this.setLayout(new BorderLayout());

		//Champs
		JPanel champs = new JPanel();
		champs.setLayout(new GridLayout(0, 2));

		champs.add(labelLogin);
		champs.add(login);

		champs.add(labelMdp);
		champs.add(mdp);

		champs.add(labelNouveauLogin);
		champs.add(nouveauLogin);

		champs.add(labelAncienMdp);
		champs.add(ancienMdp);

		champs.add(labelNouveauMdp);
		champs.add(nouveauMdp);

		champs.add(labelConfirmMdp);
		champs.add(confirmMdp);

		//Boutons
		JPanel boutons = new JPanel();				
		modifier.addActionListener(this);
		annuler.addActionListener(this);
		boutons.add(modifier);
		boutons.add(annuler);

		this.add(champs, BorderLayout.CENTER);
		this.add(boutons, BorderLayout.SOUTH);

		//this.add(comp)
		this.setVisible(true);

	}

	public boolean mdpValide(){

		String mdp = ancienMdp.getText(),
				nouvMdp = nouveauMdp.getText(),
				confMdp = confirmMdp.getText();

		String message = "";
		boolean valide = false;

		if(mdp.isEmpty() && nouvMdp.isEmpty() && confMdp.isEmpty()){
			return true;
		}

		if(mdp.isEmpty()){
			message = "Ancien mot de passe vide";
			valide = false;
		}
		else if(!nouvMdp.isEmpty() && confMdp.isEmpty()){
			message = "Veuillez confirmer le mot de passe";
			valide = false;
		}
		else if(nouvMdp.isEmpty() && !confMdp.isEmpty()){
			message = "Nouveau mot de passe vide";
			valide = false;			
		}
		else{
			if(!nouvMdp.equals(confMdp)){	//Nouveau mdp != Confirmation
				message = "Nouveau mot de passe et confirmation differents";
				valide = false;
			}
			else if(mdp.equals(nouvMdp)){	//Ancien mdp = Nouveau mdp
				message = "Ancien et nouveau mot de passe identiques";
				valide = false;
			}
			else{	//Verification de l'ancien mot de passe dans la BD

				ConnexionBD conn = new ConnexionBD();
				Statement st = conn.createStatement();

				try {
					ResultSet rs = st.executeQuery("SELECT mdp FROM "+ Constantes.base_identifiant +
							" WHERE id='" + idClient + "';");

					if(rs.next()){
						if(!rs.getString(1).equals(mdp)){
							message = "L'ancien mot de passe ne correspond pas";
							valide = false;
						}
						else{
							valide = true;
						}
					}

					rs.close();
					st.close();

				} catch (SQLException e) {
					valide = false;
					e.printStackTrace();
				}

			}
		}

		if(!valide){
			JOptionPane.showMessageDialog(null, message, "--Erreur--",
					JOptionPane.ERROR_MESSAGE);
		}

		return valide;	
	}

	public void updateInfos(){
		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		String loginClient, mdpClient;

		try {
			ResultSet rs = st.executeQuery("SELECT login, mdp FROM " + Constantes.base_identifiant +
					" WHERE id='" + idClient + "';");

			if(rs.next()){

				loginClient = rs.getString(1);
				mdpClient = rs.getString(2);

				login.setText(loginClient);

				StringBuffer mdpStr = new StringBuffer();
				for(int i=0; i<mdpClient.length(); i++){
					mdpStr.append('*');
				}
				mdp.setText(mdpStr.toString());
			}


			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean miseAJour(){
		if(!mdpValide()){
			return false;
		}

		//Rien a modifier
		if(nouveauLogin.getText().isEmpty() && nouveauMdp.getText().isEmpty()){
			return false;
		}

		String requete = "UPDATE " + Constantes.base_identifiant + " SET ";
		boolean modifLogin = false;

		if(!nouveauLogin.getText().isEmpty()){
			modifLogin = true;
			requete += "login='" + nouveauLogin.getText() + "'";
		}
		if(!nouveauMdp.getText().isEmpty()){
			if(modifLogin){
				requete += ", ";
			}
			requete += "mdp='" + nouveauMdp.getText() + "'";
		}

		requete += " WHERE id='" + idClient + "';";

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {
			st.execute(requete);			
			st.close();
			conn.close();			

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		return false;

	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modifier){
			if(miseAJour()){
				JOptionPane.showMessageDialog(null, "Vos informations ont ete mises a jour correctement",
						"--Modifications Reussies--", JOptionPane.INFORMATION_MESSAGE);
				updateInfos();
				//this.revalidate();
				this.validate();
			}
		}
		else if(e.getSource() == annuler){
			this.dispose();
		}
	}

	public static void main(String[] args){

		//InformationsClient ic = new InformationsClient("SEGZE03368");

	}

}
