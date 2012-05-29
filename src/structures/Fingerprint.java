package structures;

public class Fingerprint extends UnmutableHash {

	public Fingerprint(Kgram kgram)
	{
		super(new Hash(kgram));
	}
	
	public Fingerprint(Hash hash)
	{
		super(hash);
	}

	public boolean equals(Fingerprint f) {
		return (this.hash == f.hash);
	}
}
