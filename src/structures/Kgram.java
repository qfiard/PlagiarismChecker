package structures;

import java.util.*;

public class Kgram extends LinkedList<CharacterWithPosition> {
	
	public static final int kgramSize = 32;
	
	public void roll(CharacterWithPosition nextCharacter)
	{
		this.removeFirst();
		this.add(nextCharacter);
	}
	
	public Position start()
	{
		return this.getFirst().position();
	}
	
	public Position end()
	{
		return this.getLast().position();
	}
	
	public Kgram clone()
	{
		Kgram res = (Kgram)super.clone();
		
		return res;
	}
	
}
