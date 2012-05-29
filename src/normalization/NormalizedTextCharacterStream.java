package normalization;

import streams.*;
import structures.*;

import java.text.*;
import java.util.regex.Pattern;

public class NormalizedTextCharacterStream extends CharacterStream {
	
	private CharacterStream rawStream;
	
	CharacterWithPosition character = null;
	
	// Méthode utilisé pour supprimer les accents d'une chaîne de caractères
	public CharacterWithPosition normalizedCharacter(CharacterWithPosition c) {
		// On supprime les accents
	    String normalizedString = Normalizer.normalize(c.character().toString(), Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    String unaccentuatedString = pattern.matcher(normalizedString).replaceAll("");
	    // On enlève les majuscules
	    Character character = unaccentuatedString.charAt(0);
	    character = character.toLowerCase(character);
	    return new CharacterWithPosition(character,c.position());
	}
	
	protected void updateNextCharacter()
	{
		while(character==null && rawStream.hasNext())
		{
			CharacterWithPosition newChar = rawStream.next();
			
			if(Character.isDigit(newChar.character()))
			{
				this.character = newChar;
			}
			else if(Character.isLetter(newChar.character()))
			{
				this.character = this.normalizedCharacter(newChar);
			}
		}
	}

	public NormalizedTextCharacterStream(CharacterStream stream)
	{
		this.rawStream = stream;
	}

	@Override
	public boolean hasNext() {
		this.updateNextCharacter();
		return (character != null);
	}

	@Override
	public CharacterWithPosition next() {
		CharacterWithPosition res = this.character;
		this.character = null;
		return res;
	}
	
}
