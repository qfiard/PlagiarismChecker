package structures;

public class SharedFingerprint {

	private Document[] documents = new Document[2];
	private Fingerprint[] fingerprints = new Fingerprint[2];
	
	public void display()
	{
		System.out.display("Position dans le fichier "+documents[0].name()+ " : indice "+fingerprints[0].);
		//TODO finir la fonction d'affichage d'une empreinte
	}
}
