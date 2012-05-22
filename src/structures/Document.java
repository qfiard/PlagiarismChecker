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
		//TODO Programmer la comparaison de documents
		return null;
	}
}
