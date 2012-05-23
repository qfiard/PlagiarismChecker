package streams;

import structures.*;

public class HashStream implements Stream<Hash> {
	
	private CharacterStream stream;
	private Hash hash = null;
	
	public HashStream(CharacterStream stream)
	{
		this.stream = stream;
		
		Kgram kgram = new Kgram();
		
		for(int i=0 ; i<Kgram.kgramSize && stream.hasNext() ; i++)
		{
			kgram.add(stream.next());
		}
		
		if(kgram.size()>0)
		{
			hash = new Hash(kgram);
		}
	}

	@Override
	public boolean hasNext() {
		return (hash!=null);
	}

	@Override
	public Hash next() {
		Hash res = hash.unmutableClone();
		
		if(stream.hasNext())
		{
			hash.updateHash(stream.next());
		}
		else
		{
			hash = null;
		}
		
		return res;
	}

}
