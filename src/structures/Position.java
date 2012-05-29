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
	
	public boolean equals(Position pos2)
	{
		return (this.document.equals(pos2.document) && (this.position == pos2.position));
	}
	
	public int compareTo(Position pos2)
	{
		if(!this.document.equals(pos2.document))
		{
			throw new IllegalArgumentException("Les documents ne correspondent pas.");
		}
		
		if(position < pos2.position)
		{
			return -1;
		}
		else if(position > pos2.position)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public void addToPosition(int n)
	{
		this.position += n;
	}
}
