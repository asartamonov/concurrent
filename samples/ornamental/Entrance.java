package ornamental;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Entrance implements Runnable {
	
	private static Count count = new Count();
	private static final List<Entrance> entrances = new ArrayList<Entrance>();
	private int number = 0;
	// Doesn’t need synchronization to read:
	private final int id;

	public Entrance(int id) {
		this.id = id;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		entrances.add(this);
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
			System.out.println("Exception caught of type: " + e.getClass());
		} finally {
			System.out.println("Stopping " + this);
		}
	}

	public synchronized int getValue() {
		return number;
	}

	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntrances() {
		int sum = 0;
		for (Entrance entrance : entrances)
			sum += entrance.getValue();
		return sum;
	}

}
