package structures;

import utilities.FastExponentiation;

public class UnmutableHash extends Hash
{
	private Position start;
	private Position end;
	
	public UnmutableHash(Hash hash)
	{
		super(null,hash.hash(),hash.basePowk);
		
		start = hash.start();
		end = hash.end();
	}
	
	public void updateHash(CharacterWithPosition toAdd)
	{
		throw new RuntimeException("Hash is not mutable.");
	}
	
	public Kgram kgram()
	{
		throw new RuntimeException("Unmutable hashes don't keep k-gram data.");
	}
	
	public Position start()
	{
		return start;
	}
	
	public Position end()
	{
		return end;
	}
	
	public Hash clone()
	{
		throw new RuntimeException("Unmutable hashes don't need to be cloned.");
	}
}
