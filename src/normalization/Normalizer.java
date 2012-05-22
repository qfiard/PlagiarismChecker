package normalization;

import streams.CharacterStream;

public abstract class Normalizer {

	abstract public CharacterStream normalize(CharacterStream toNormalize);
	
}
