package interfaceClient;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreColis extends JPanel{

	private static final long serialVersionUID = 1L;

	private String requete;
	private String idClient;
	
	private JPanel panelCommande;

	public FenetreColis(String idClient){

		this.idClient = idClient;

		requete = "SELECT id_commande, cast(date_livraison AS varchar), etat FROM " + Constantes.base_commande +
		" WHERE id_client ='" + idClient + "';";

		afficherListeCommandes();		

	}

	public void afficherListeCommandes(){

		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();

		try {
			
			ResultSet rs = st.executeQuery(requete);
			
			panelCommande = new JPanel();
			panelCommande.setLayout(new BoxLayout(panelCommande, BoxLayout.PAGE_AXIS));
			
			//panelCommande.setPreferredSize(new Dimension(400, 200));
			
			//this.setLayout(new GridLayout(0, 1));
			
			while(rs.next()){
				panelCommande.add(new InfosCommande(rs.getInt(1),
						rs.getString(2),
						rs.getString(3).toUpperCase()));
			}
			
			this.add(panelCommande);

			rs.close();
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();

	}


}
