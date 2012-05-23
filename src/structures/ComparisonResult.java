package structures;

import java.util.*;

public class ComparisonResult {

	private Document documents[] = new Document[2];
	
	private int[] nbOfExtractedFingerprints = new int[2];
	
	private List<SharedFingerprint> shared;
	
	public ComparisonResult(Document doc1, Document doc2)
	{
		documents[0] = doc1;
		documents[1] = doc2;
		shared = new LinkedList<SharedFingerprint>();
	}
	
	public void add(SharedFingerprint fingerprint)
	{
		shared.add(fingerprint);
	}
	
	public double ratioSharedVSExtracted()
	{
		return shared.size()/(Math.min(nbOfExtractedFingerprints[0], nbOfExtractedFingerprints[1]));
	}
	
	public int nbOfSharedFingerprints()
	{
		return shared.size();
	}
	
	public void display(boolean showRequested)
	{
		System.out.println("Similarités détectées :");
		System.out.println();
		System.out.println("Fichier 1 : "+documents[0].name());
		System.out.println("Fichier 2 : "+documents[1].name());
		System.out.println("Nombre d'empreintes communes : "+this.nbOfSharedFingerprints());
		System.out.println("Ratio du nombre de similarités sur le nombre d'empreintes comparées : "+this.ratioSharedVSExtracted());
		
		if(showRequested)
		{
			int numFingerprint = 1;
			System.out.println();
			System.out.println("Empreinte identique n°"+numFingerprint);
			for(SharedFingerprint fingerprint : shared)
			{
				fingerprint.display();
			}
		}
		
		System.out.println();
		System.out.println();
	}
}
