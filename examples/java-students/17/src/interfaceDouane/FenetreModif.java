package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreModif extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelId_colis = new JLabel("ID_colis : ");
	private JLabel labelId_commande = new JLabel("ID_commande : ");
	private JLabel labelDate_emballage = new JLabel("Date d'emballage : ");
	private JLabel labelStatut = new JLabel("Statut : ");
	//private JLabel labelQualifiant = new JLabel("Qualifiant : ");

	private JLabel colis = new JLabel();
	private JLabel commande = new JLabel();
	private JLabel date = new JLabel();
	//private JLabel qual = new JLabel();
	private JTextField stat = new JTextField();

	private ImageIcon icon = new ImageIcon("src/ic_menu_save.png");
	private JButton ok = new JButton(icon);

	private ImageIcon icon2 = new ImageIcon("src/ic_menu_close_clear_cancel.png");
	private JButton annuler = new JButton(icon2);

	private int id;

	public FenetreModif(int ID_colis){

		this.setTitle("---Resultat du controle---");
		this.setSize(250, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.id = ID_colis;


		updateInfos();

		this.setLayout(new BorderLayout());

		JPanel champs = new JPanel();
		champs.setLayout(new GridLayout(0, 2));

		champs.add(labelId_colis);
		champs.add(colis);

		champs.add(labelId_commande);
		champs.add(commande);

		champs.add(labelDate_emballage);
		champs.add(date);

		champs.add(labelStatut);
		champs.add(stat);

		//champs.add(labelQualifiant);
		//champs.add(qual);


		JPanel button = new JPanel();

		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.addActionListener(this);

		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.addActionListener(this);

		button.add(ok);
		button.add(annuler);

		this.add(champs, BorderLayout. CENTER);
		this.add(button, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void updateInfos(){

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery("SELECT id_colis, id_commande, cast(date_emballage as varchar) FROM " 
					+ Constantes.base_colis +" natural join " + Constantes.base_emballage+ " WHERE id_colis =" + id +";");

			int col, com ;
			String embal;

			if(rs.next()){
				//rs.previous();

				col = rs.getInt(1);
				com = rs.getInt(2);
				embal = rs.getString(3);
				//q = rs.getInt(4);

				colis.setText(String.valueOf(col));
				commande.setText(String.valueOf(com));
				date.setText(embal);
				//qual.setText(String.valueOf(q));



			}
			rs.close();
			st.close();
			bd.close();



		} catch (SQLException e) {
			e.printStackTrace();
		}



	}

	public int miseAJour(String statutC){

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		int rs=0;
		try {
			rs = st.executeUpdate("UPDATE " + Constantes.base_colis + " SET statut = '" + statutC + "' WHERE id_colis = " + id +";");
			miseAJourStatutCommande();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	public int miseAJourStatutCommande(){


		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();
		int rs=0;
		try {

			//on regarde combie de colis à la commande correspondant au colis controle
			ResultSet i = st.executeQuery("SELECT id_commande, count(id_colis) FROM " + Constantes.base_colis + " WHERE id_colis = " + id + " GROUP BY id_commande;");

			if(i.next()){

				int numCommande = i.getInt(1);
				int nbColis = i.getInt(2);

				//i.close();

				//on récupère les colis donc le statut à été controle, cad passer par la douane et approuvé
				ResultSet g = st.executeQuery(" SELECT id_colis FROM " + Constantes.base_colis + " WHERE id_commande = " + numCommande + " AND statut IN ('controle', 'emballe') GROUP BY id_colis;");
				if(g.next()){
					g.last();
					int nombreColis = g.getRow();
					g.beforeFirst();
					//si le nombre de colis de la commande est égal au nombre de colis controle de la commande, on peut changer le statut de la commande a expedie
					if(nbColis == nombreColis){
						rs = st.executeUpdate("UPDATE " + Constantes.base_commande + " SET statut = 'expediee' WHERE id_commande = " + numCommande + ";");
					}

				}
				else{
					System.out.println("NOOOON");
				}

			}
			else{
				System.out.println("RIEEEEN");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == ok){

			String statutColis = stat.getText();

			if(miseAJour(statutColis) != 0){

				JOptionPane.showMessageDialog(null, "Le statut du colis a bien ete mis a jour",
						"--Modifications Reussies--", JOptionPane.INFORMATION_MESSAGE);
				updateInfos();
				this.dispose();
			}
			else{
				JOptionPane.showMessageDialog(null, "Le statut du colis n'a pas ete mis a jour",
						"--Erreur--", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			this.dispose();
		}
	}

	/*public static void main(String[] args){
		FenetreModif fd = new FenetreModif(4);
	}*/

}
