package structures;

import utilities.FastExponentiation;
import java.util.*;

public class Hash implements Cloneable {
	
	public static final int base = 1001; // A large prime

	private Kgram kgram;
	
	protected long hash;
	protected long basePowk; //  = base^k où k est la longueur du k-gramme
	
	public Hash(Kgram kgram)
	{
		this.kgram = kgram;
		// Algorithme de Horner pour le calcul du polynôme
		for(CharacterWithPosition c : kgram)
		{
			hash *= base;
			hash += c.character();
		}
		
		basePowk = FastExponentiation.pow(base, kgram.size());
	}
	
	public Hash(Kgram kgram, long hash, long basePowk)
	{
		this.kgram = kgram;
		this.hash = hash;
		this.basePowk = basePowk;
	}
	
	public Hash(Hash hash)
	{
		this.kgram = hash.kgram;
		this.hash = hash.hash;
		this.basePowk = hash.basePowk;
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
	
	public Kgram kgram()
	{
		return this.kgram;
	}
	
	public Position start()
	{
		return kgram.start();
	}
	
	public Position end()
	{
		return kgram.end();
	}
	
	public Hash clone()
	{
		return new Hash(kgram.clone(),hash,basePowk);
	}
	
	public UnmutableHash unmutableClone()
	{
		return new UnmutableHash(this);
	}
}
