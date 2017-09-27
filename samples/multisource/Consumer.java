package multisource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
	private static volatile AtomicInteger instanceCounter = new AtomicInteger(0);
	private Holder holder;
	private int id;

	private List<String> consString;
	private List<String> vowString;

	private Set<Character> vowSet;

	{
		Character[] vowels = { 'a', 'e', 'i', 'o', 'u' };
		vowSet = new HashSet<>(Arrays.asList(vowels));
	}

	public Consumer(int id, Holder holder, List<String> consString, List<String> vowString) {
		super();
		this.id = id;
		this.holder = holder;
		this.consString = consString;
		this.vowString = vowString;
		instanceCounter.incrementAndGet();
	}

	private void sortString(String string) {
		if (string != null) {
			if (vowSet.contains(string.charAt(0))) {
				synchronized (vowString) {
					vowString.add(string);
				}
			} else {
				synchronized (consString) {
					consString.add(string);
				}
			}
		}
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Consumer #" + id);
		try {
			while (!Thread.interrupted()) {
					sortString(holder.getItem());
			}
		} catch (InterruptedException e) {
			System.out.println("Consumer interrupted..." + Thread.currentThread().getName() + ". Remaining instances count: " + instanceCounter.decrementAndGet());
		}
	}

}
