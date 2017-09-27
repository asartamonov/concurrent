package ornamental_cntdwnlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Entrance implements Runnable {
	
	private static final Count count = new Count();
	private static CountDownLatch cdl;
	private static final List<Entrance> entrances = new ArrayList<Entrance>();
	private int number = 0;
	// Doesn’t need synchronization to read:
	private final int id;

	public Entrance(int id, CountDownLatch cdl) {
		this.id = id;
		Entrance.cdl = cdl;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		entrances.add(this);
	}

	public synchronized int getValue() {
		return number;
	}
	
	public void run() {
		try {
			while (true) {
				synchronized (this) {
					++number;
				}
				System.out.println(this + " Total: " + count.increment());
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting.. " + this);
		} finally {
			cdl.countDown();
			System.out.println(this + " has stopped.");
		}
	}

	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntrances() {
		return entrances.stream().mapToInt(e -> e.getValue()).sum();
	}

}
