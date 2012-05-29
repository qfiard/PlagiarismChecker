package structures;

import streams.*;
import java.util.*;
import java.io.*;

import normalization.*;

public class Document extends CharacterStream {

	private File file;
	private FileReader reader;
	private Position currentPosition;
	
	private CharacterWithPosition nextCharacter = null;
	
	private List<Fingerprint> fingerprintList = null;
	
	private StreamNormalizer normalizer = null;
	
	public Document(File file)
	{
		this.file = file;
		
		try
		{
			reader = new FileReader(file);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Le fichier indiqué n'existe pas : "+file.getAbsolutePath());
		}
		
		currentPosition = new Position(this,-1);
	}
	
	public Document(File file, StreamNormalizer normalizer)
	{
		this(file);
		
		this.normalizer = normalizer;
	}
	
	public String name()
	{
		return file.getName();
	}
	
	public void updateNextCharacter()
	{
		if(nextCharacter == null)
		{
			int nextRead;
			try
			{
				nextRead = reader.read();
			}
			catch(IOException e)
			{
				throw new RuntimeException("Impossible de lire le fichier "+file.getAbsolutePath());
			}
			
			if(nextRead!=-1)
			{
				currentPosition = currentPosition.nextPosition();
				nextCharacter = new CharacterWithPosition((char)nextRead,currentPosition);
			}
		}
	}

	@Override
	public boolean hasNext() {
		this.updateNextCharacter();
		return (nextCharacter != null);
	}

	@Override
	public CharacterWithPosition next() {
		CharacterWithPosition res = this.nextCharacter;
		this.nextCharacter = null;
		return res;
	}
	
	public void createFingerprintList()
	{
		CharacterStream normalizedStream;
		
		if(normalizer != null)
		{
			normalizedStream = this.normalizer.normalize(this);
		}
		else
		{
			normalizedStream = this;
		}
		
		HashStream hashStream = new HashStream(normalizedStream);
		
		FingerprintStream fingerprintStream = new FingerprintStream(hashStream);
		
		// On construit la liste des empreintes du document
		
		fingerprintList = new LinkedList<Fingerprint>();
		
		while(fingerprintStream.hasNext())
		{
			fingerprintList.add(fingerprintStream.next());
		}
		
		this.reset();
	}
	
	public List<Fingerprint> fingerprintList()
	{
		if(fingerprintList==null)
		{
			this.createFingerprintList();
		}
		return this.fingerprintList;
	}
	
	public ComparisonResult compareWith(Document doc2)
	{
		// On récupère la liste des empreintes des documents 1 et 2
		
		List<Fingerprint> fingerprintList1 = this.fingerprintList();
		List<Fingerprint> fingerprintList2 = doc2.fingerprintList();
		
		// On construit l'objet qui contiendra le résultat de la comparaison
		
		ComparisonResult res = new ComparisonResult(this,doc2);
		
		// On compare les empreintes deux à deux pour trouver les empreintes communes
		
		for(Fingerprint f1 : fingerprintList1)
		{
			for(Fingerprint f2 : fingerprintList2)
			{
				if(f1.equals(f2))
				{
					SharedFingerprint shared = new SharedFingerprint(this, f1, doc2, f2);
					
					res.add(shared);
				}
			}
		}
		
		res.setNbOfExtractedFingerprints(this, fingerprintList1.size());
		res.setNbOfExtractedFingerprints(doc2, fingerprintList2.size());
		
		return res;
	}
	
	public void skip(int nbOfCharacters) throws IOException
	{
		this.reader.skip(nbOfCharacters);
		this.currentPosition.addToPosition(nbOfCharacters);
	}
	
	public String extract(Position start, Position end)
	{
		if(!start.document().equals(this) || !end.document().equals(this))
		{
			throw new IllegalArgumentException("Les documents ne correspondent pas !");
		}
		
		this.reset();
		
		try
		{
			this.skip(start.position());
		}
		catch(IOException e)
		{
			throw new RuntimeException("Une erreur s'est produite : "+e.getLocalizedMessage());
		}
		
		String res = "";
		
		Position current = new Position(this,start.position());
		
		while(current.compareTo(end)<=0 && this.hasNext())
		{
			CharacterWithPosition tmp = this.next();
			
			current = tmp.position();
			res += tmp.character();
		}
		
		return res;
	}
	
	public void reset()
	{
		try
		{
			reader.close();
			reader = new FileReader(file);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Une erreur s'est produite : "+e.getLocalizedMessage());
		}
		
		this.currentPosition = new Position(this,-1);
		this.nextCharacter = null;
	}
}
