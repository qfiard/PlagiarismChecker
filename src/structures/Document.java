package structures;

import streams.*;
import java.io.*;

public class Document extends CharacterStream {

	private File file;
	private FileReader reader;
	private Position currentPosition;
	
	private CharacterWithPosition nextCharacter = null;
	
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
	
	public ComparisonResult compareWith(Document doc2)
	{
		this.reset();
		doc2.reset();
		
		HashStream hashStream1 = new HashStream(this);
		HashStream hashStream2 = new HashStream(doc2);
		
		FingerprintStream fingerprint1 = new FingerprintStream(hashStream1);
		FingerprintStream fingerprint2 = new FingerprintStream(hashStream2);
		
		//TODO Finir la fonction de comparaison de 2 documents
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
			this.reader.skip(start.position());
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
			reader.reset();
		}
		catch(IOException e)
		{
			throw new RuntimeException("Une erreur s'est produite : "+e.getLocalizedMessage());
		}
		
		this.currentPosition = new Position(this,-1);
		this.nextCharacter = null;
	}
}
