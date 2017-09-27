package multisource.generator;

import java.util.concurrent.atomic.AtomicInteger;

public interface StrGenerator {
	AtomicInteger cnt = new AtomicInteger();
	char[] vowels = { 'a', 'e', 'i', 'o', 'u' };
	char[] consonants = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x',
			'z', 'w', 'y', };
	String generate();
}
