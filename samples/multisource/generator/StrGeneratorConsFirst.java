package multisource.generator;

public class StrGeneratorConsFirst implements StrGenerator {

	@Override
	public String generate() {
		int length = 6;
		char[] newString = new char[length];

		for (int i = 0; i < length; i++) {
			newString[i] = (i % 2 == 0 ? StrGenerator.consonants[(int) (Math.random() * StrGenerator.consonants.length)]
					: StrGenerator.vowels[(int) (Math.random() * StrGenerator.vowels.length)]);
		}
		cnt.incrementAndGet();
		return new String(newString);
	}
}
