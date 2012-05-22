package structures;

import java.util.*;

public class Kgram extends LinkedList<CharacterWithPosition> {
	
	public void roll(CharacterWithPosition nextCharacter)
	{
		this.removeFirst();
		this.add(nextCharacter);
	}
	
}
