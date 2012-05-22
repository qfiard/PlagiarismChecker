package structures;

public class CharacterWithPosition {
	
	private Character character;
	private Position position;
	
	public CharacterWithPosition(Character character, Position position)
	{
		this.character = character;
		this.position = position;
	}
	
	public Position position()
	{
		return position;
	}
	
	public Character character()
	{
		return character;
	}
}
