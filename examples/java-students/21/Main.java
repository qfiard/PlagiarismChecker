
public class Main {
	
	static String login;
	static String mdp;
	
	public static void demandeLogMdp() {
		login = null;
		mdp = null;
		
		while (true) {
			if (login == null) {
				System.out.print("Entrez votre login\n");
				login = System.console().readLine();
			}
			if (mdp == null) {
				System.out.print("Entrez votre mot de passe\n");
				mdp = new String(System.console().readPassword());
			}
			if (login != null && mdp != null) break;
		}
	}
	
	public static void main(String args[]) {
		try {
		
			/*On se connecte sur la bdd*/
			ConnectionProjet conn = new ConnectionProjet("leclercq","dubstep");
			
			/*On demande un mdp et un login a l'utilisateur*/
			demandeLogMdp();
		
			/*On recupere l'autorisation de l'utilisateur*/
			char autorisation = conn.connexion(login, mdp);
			
			/*maintenant on switch l*/
			boolean t = true;
			
			while(true) {
				switch (autorisation) {
				case 'G':
					Gerant g = new Gerant(login,mdp,conn);
					while(t) {
						t = g.gestion();
					}
					break;
				case 'D' :
					Douane d = new Douane(login,mdp,conn);
					while (t) {
						t = d.gestion();
					}
					break;
				case 'T' : 
					Transporteur trans = new Transporteur(login,mdp,conn);
					while (t) {
						t = trans.gestion();
					}
					break;
				case 'C' :
					Client client = new Client(login,mdp,conn);
					while(t) {
						t = client.gestion();
					}
					break;
					
				case 'E' :
					Emballeur e = new Emballeur(login,mdp,conn);
					while(t) {
						t = e.gestion();
					}
					break;
				default :
					//ce login/mdp est pas dans la bdd, on redemande...
					System.out.println("Login ou Mot de passe erronné");
					demandeLogMdp();
					autorisation = conn.connexion(login, mdp);
					continue;
				}
				break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
