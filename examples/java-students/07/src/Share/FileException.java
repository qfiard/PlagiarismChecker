package Share;

public class FileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2528786182152743240L;

	/**
	 * Exception levée lors de la lecture des lignes 
	 * Pour indiquer un nombre de colonne non conforme au type 
	 * @param prestige 0 pour gérant 
	 * @param data la second colonne pour repérer la ligne corrompue
	 * @param c compteur de ligne
	 */
	public FileException(int prestige, String data, int c) {
		String err = "";
		switch (prestige) {
			case 0 : 
				err = "Problème avec le gérant, ligne : " + c + "\n";
				break;
			case 1 : 
				err = "Problème ligne : "+ c + "\nNuméro du client : ";
				break;
			case 2 :
				System.out.println("Problème avec un gérant, ligne : " + c);
				err += "\n Numéro d'emballeur : "; 
				break;
			case 3 :
				err = "Problème douane, ligne : " + c + "\n";
				break;
			case 4 : 
				err = "Problème transporteur : " + c + "\n";
				break;
			case -1 :
			default : 
				err = "Erreur dans le fichier";
				break;
		}
		System.out.println(err + data);
	}
}