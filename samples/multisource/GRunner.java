package multisource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import multisource.generator.StrGenerator;
import multisource.generator.StrGeneratorConsFirst;
import multisource.generator.StrGeneratorVowelFirst;

public class GRunner {

	private static volatile Holder holder = new Holder(10);
	private static volatile List<String> consString = new ArrayList<String>();
	private static volatile List<String> vowString = new ArrayList<String>();

	public static void main(String[] args) {
		
		ExecutorService producers = Executors.newCachedThreadPool();
		producers.execute(new Producer(1, new StrGeneratorConsFirst(), holder));
		producers.execute(new Producer(2, new StrGeneratorVowelFirst(), holder));
		
		ExecutorService consumers = Executors.newCachedThreadPool();
		consumers.execute(new Consumer(1, holder, consString, vowString));
		consumers.execute(new Consumer(2, holder, consString, vowString));
		
		for (int i = 0; i < 4; i++) {
			System.out.println(holder.getBufferElemCount() <= 10 && holder.getBufferElemCount() >= 0 ? "Buffer ok" : "Buffer not ok");
			try {
				TimeUnit.SECONDS.sleep(1);;
			} catch (InterruptedException e) {}
		}
		
		producers.shutdownNow();
		
		ExecutorService producers2 = Executors.newCachedThreadPool();
		producers2.execute(new Producer(3, new StrGeneratorVowelFirst(), holder));
		
		for (int i = 0; i < 4; i++) {
			System.out.println(holder.getBufferElemCount() <= 10 && holder.getBufferElemCount() >= 0 ? "Buffer ok" : "Buffer not ok");
			try {
				TimeUnit.SECONDS.sleep(1);;
			} catch (InterruptedException e) {}
		}
		
		producers2.shutdownNow();
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {}
		
		consumers.shutdownNow();
		
		System.out.println("Generated: " + StrGenerator.cnt.get());
		System.out.println("Remains in buffer: " + holder.getBufferElemCount());
		System.out.println("Sorted: " + (consString.size() + vowString.size()));
	}

}
