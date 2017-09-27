package interruptable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectLocked implements Runnable {
	
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		lock.lock();
		System.out.println("Lock locked.");
		int counter = 1000;
		while (counter-- > 0) {
			// doing something forever
		}
		lock.unlock();
	}

}
