package structures;

public class DocumentPair {

	Document[] document = new Document[2];
	
	public DocumentPair(Document doc1, Document doc2)
	{
		this.document[0] = doc1;
		this.document[0] = doc2;
	}
	
	public Document left()
	{
		return document[0];
	}
	
	public Document right()
	{
		return document[1];
	}
}
