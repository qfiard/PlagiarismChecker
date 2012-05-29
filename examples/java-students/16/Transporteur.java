import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Transporteur {
	SQL connecte;
	String login;
	String ID_transporteur;
	FonctionsCommunes fonc;

	public Transporteur(String log) {
		try {
				connecte = new SQL();
				fonc = new FonctionsCommunes(this.connecte);
				this.login = log;
				this.ID_transporteur = recupID(connecte.transporteurRecupID(login));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int printMenu() throws SQLException, ClassNotFoundException {
		int c = -1; // le choix de l'utilisateur
		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------
		// Impression du menu
		// -------------------

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("0 - Quitter");
		System.out.println("1 - Lister les commandes");
		System.out.println("2 - Obtenir les informations sur sur les commandes a transporter");
		System.out.println("3 - Mettre a jour la date de dernier traitement");
		System.out.println("-------------------------------------------------------------");

		// ----------------------------
		// Lecture du choix utilisateur
		// ----------------------------

		c = fonc.readInt();

		System.out.print("\033c"); //nettoyage de l'ecran

		// -------------------------------
		// traitement du choix utilisateur
		// -------------------------------
		try {
			switch(c){

			case 1 :
				fonc.afficheResultat(connecte.transporteurAfficherCommande(fonc.dateToday()));
				break;

			case 2 : 
				fonc.afficheResultat(connecte.transporteurAfficherCommande(ID_transporteur, fonc.dateToday()));
				break;

			case 3 :
				int cmp = 0;
				boolean sortir = false;
				while(!sortir && cmp < 3){
					fonc.afficheResultat(connecte.transporteurAfficherCommande(ID_transporteur, fonc.dateToday()));
					System.out.println("Quelle commande livrez-vous ?");
					String ID_commande = fonc.readString();
					if(connecte.verifIdCommande(ID_commande)){
						connecte.transporteurUpdateDate(ID_commande,fonc.dateToday());
						sortir = true;
					}
					else{
						System.out.println("ID de commande incorrect");
						cmp++;
					}
				}
				cmp = 0;
				break;

			case 0 : 
				System.out.println("FIN");
				break;

			default : 
				System.out.println("ERREUR!");
			}
		}
		catch (SQLException  e) {
			System.err.println(e.getMessage());
		}
		return c;
	}

	public String recupID(ResultSet rs) throws SQLException{
		ResultSetMetaData rm = rs.getMetaData();
		String ID = "";
		while(rs.next()){
			for(int i = 1 ; i <= rm.getColumnCount() ; i++){
				ID = rs.getObject(i).toString();
			}
		}
		rs.close();
		return ID;
	}

}
