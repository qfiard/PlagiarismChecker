import java.util.Scanner;

public class Projet {

	public static void main(String[] args) throws Exception {
		Utilisateur user = null;
		BDD bdd = BDD.connect();
		Scanner sc = new Scanner(System.in);

		user = Projet.choixUtilisateur(bdd, sc);

		System.out.println("login ?");
		String login = sc.next();
		System.out.println("mot de passe ?");
		String motPasse = sc.next();

		boolean Identification = user.identifier(login, motPasse);

		if (!Identification) {
			System.out.println("Erreur identification");
			user = null;
		}

		else {
			user.showLogin();
			user.afficherMenuTerminal();
			int choix = sc.nextInt();
			boolean continuer = (0!=choix);

			while (continuer && 0!=choix) {
				continuer = user.traiterChoix(choix);
				user.afficherMenuTerminal();
				choix = sc.nextInt();
			}
			System.out.println("Session fermée.");
		}

		bdd.close();
	}

	public static Utilisateur choixUtilisateur(BDD bdd, Scanner sc) {
		System.out.println("Bonjour, veuillez choisir votre statut d'accès :");
		System.out.println("\t1 - Gérant");
		System.out.println("\t2 - Client");
		System.out.println("\t3 - Emballeur");
		System.out.println("\t4 - Douane");
		System.out.println("\t5 - Transporteur");

		// on lit le nombre entré au clavier
		int statut = sc.nextInt();

		System.out.println("choix " + statut);

		Utilisateur user = null;
		switch (statut) {
			case 1: user = new Gerant(bdd);
				break;
			default: 
				System.out.println("Le choix "+statut+" n'est pas disponible.");
				break;
		}
		return user;
	}
}
