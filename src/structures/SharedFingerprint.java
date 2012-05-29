package structures;

public class SharedFingerprint {

	private Document[] documents = new Document[2];
	private Fingerprint[] fingerprints = new Fingerprint[2];
	
	public SharedFingerprint(Document doc1, Fingerprint f1, Document doc2, Fingerprint f2)
	{
		documents[0] = doc1;
		documents[1] = doc2;
		
		fingerprints[0] = f1;
		fingerprints[1] = f2;
	}
	
	public void display()
	{
		System.out.println("Position dans le fichier "+documents[0].name()+" :");
		System.out.println("Début : "+fingerprints[0].start().position());
		System.out.println("Fin : "+fingerprints[0].end().position());
		System.out.println("Extrait : "+documents[0].extract(fingerprints[0].start(), fingerprints[0].end()));
		System.out.println();
		System.out.println("Position dans le fichier "+documents[1].name()+ " : indice "+fingerprints[1].start().position());
		System.out.println("Début : "+fingerprints[1].start().position());
		System.out.println("Fin : "+fingerprints[1].end().position());
		System.out.println("Extrait : "+documents[1].extract(fingerprints[1].start(), fingerprints[1].end()));
		System.out.println();
	}
}
