package structures;

import utilities.FastExponentiation;
import java.util.*;

public class Hash {

	private Kgram kgram;
	
	private long hash;
	
	private long base;
	private long basePowk; //  = base^k où k est la longueur du k-gramme
	
	public Hash(Kgram kgram, long base)
	{
		this.kgram = kgram;
		
		this.base = base;
		// Algorithme de Horner pour le calcul du polynôme
		for(CharacterWithPosition c : kgram)
		{
			hash *= base;
			hash += c.character();
		}
		
		basePowk = FastExponentiation.pow(base, kgram.size());
	}
	
	public void updateHash(CharacterWithPosition toAdd)
	{
		hash *= base;
		hash += toAdd.character();
		hash -= basePowk*kgram.getFirst().character();
		
		kgram.roll(toAdd);
	}
	
	public long hash()
	{
		return hash;
	}
	
	public Position start()
	{
		return kgram.getFirst().position();
	}
	
	public Position end()
	{
		return kgram.getLast().position();
	}
}
