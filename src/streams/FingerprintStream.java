package streams;

import structures.*;
import java.util.*;

public class FingerprintStream implements Stream<Fingerprint> {

	public static int guarantyThreshold = 64;
	
	private LinkedList<Hash> window;
	private HashStream stream;
	
	public Fingerprint fingerprint = null;
	
	public Fingerprint newMinimum()
	{
		Hash min = null;
		
		for(Hash hash : window)
		{
			if(min == null)
			{
				min = hash;
			}
			else if(min.hash()>=hash.hash()) // We select the right most minimum
			{
				min = hash;
			}
		}
		
		if(min != null)
		{
			return new Fingerprint(min);
		}
		else
		{
			return null;
		}
	}
	
	public FingerprintStream(HashStream stream)
	{
		this.stream = stream;
		
		window = new LinkedList<Hash>();
		
		for(int i=0 ; i<windowSize() && stream.hasNext() ; i++)
		{
			window.add(stream.next());
		}
		
		fingerprint = newMinimum();
	}
	
	@Override
	public boolean hasNext() {
		return (fingerprint != null);
	}

	@Override
	public Fingerprint next() {
		Fingerprint res = fingerprint;
		
		Fingerprint min = fingerprint;
		
		while(fingerprint.equals(min) && stream.hasNext())
		{
			window.removeFirst();
			window.add(stream.next());
			
			min = newMinimum();
		}
		
		if(fingerprint.equals(min))
		{
			fingerprint = null;
		}
		else
		{
			fingerprint = min;
		}
		
		return res;
	}
	
	public static int windowSize()
	{
		return guarantyThreshold - Kgram.kgramSize + 1;
	}

}
