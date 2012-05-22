package structures;

public class Position {

	private Document document;
	private int position;
	
	public Position(Document document, int position)
	{
		this.document = document;
		this.position = position;
	}
	
	public int position()
	{
		return position;
	}
	
	public Document document()
	{
		return document;
	}
	
	public Position nextPosition()
	{
		return new Position(document,position+1);
	}
}
