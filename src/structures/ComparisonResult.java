package structures;

import java.util.*;

public class ComparisonResult {

	private Document documents[] = new Document[2];
	
	private int[] nbOfExtractedFingerprints = new int[2];
	
	private List<SharedFingerprint> shared = new LinkedList<SharedFingerprint>();
	
	public ComparisonResult(Document doc1, Document doc2)
	{
		documents[0] = doc1;
		documents[1] = doc2;
	}
	
	public void add(SharedFingerprint fingerprint)
	{
		shared.add(fingerprint);
	}
	
	public double ratioSharedVSExtracted()
	{
		return ((double)shared.size())/(Math.min(nbOfExtractedFingerprints[0], nbOfExtractedFingerprints[1]));
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
		System.out.println("Ratio du nombre de similarités sur le nombre d'empreintes comparées : "+(Math.round(this.ratioSharedVSExtracted()*100))+"%");
		
		if(showRequested)
		{
			int numFingerprint = 1;
			for(SharedFingerprint fingerprint : shared)
			{
				System.out.println();
				System.out.println("Empreinte identique n°"+numFingerprint);
				fingerprint.display();
				numFingerprint++;
			}
		}
		
		System.out.println();
		System.out.println();
	}
	
	public void setNbOfExtractedFingerprints(Document doc, int nb)
	{
		if(doc.equals(documents[0]))
		{
			this.nbOfExtractedFingerprints[0] = nb;
		}
		else if(doc.equals(documents[1]))
		{
			this.nbOfExtractedFingerprints[1] = nb;
		}
		else
		{
			throw new IllegalArgumentException("Le document indiqué est incorrect !");
		}
	}
}
