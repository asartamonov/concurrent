package talking;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CommunicatingThread {
	public static void main(String[] args) throws Exception {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(4);
		exec.shutdownNow();
		}

}

class Receiver implements Runnable {
	private BlockingQueue<Character> in;

	public Receiver(Sender sender) throws IOException {
		in = sender.getQueue();
	}

	public void run() {
		try {
			while (true) {
				// Blocks until characters are there:
				System.out.println("Read: " + (char) in.take() + ", ");
			}
		} catch (InterruptedException e) {
			System.out.println(e + " Receiver read exception");
		}
	}
}

class Sender implements Runnable {
	private Random rand = new Random(47);
	private BlockingQueue<Character> out = new LinkedBlockingQueue<>();

	public BlockingQueue<Character> getQueue() {
		return out;
	}

	public void run() {
		try {
			while (true) {
				for (char c = 'A'; c <= 'z'; c++) {
					out.put(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e + " Sender sleep interrupted");
		}
	}
}
