package normalization;

import streams.CharacterStream;

public class TextNormalizer extends StreamNormalizer {

	@Override
	public CharacterStream normalize(CharacterStream toNormalize)
	{
		return new NormalizedTextCharacterStream(toNormalize);
	}

}
