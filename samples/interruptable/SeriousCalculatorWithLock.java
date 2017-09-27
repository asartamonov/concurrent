package interruptable;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SeriousCalculatorWithLock implements Callable<Integer> {
	
	private Lock lock = new ReentrantLock();

	@Override
	public Integer call() throws Exception {
		lock.lock();
		System.out.println("Calc locked!");
		int result = 2 + 2;
		TimeUnit.SECONDS.sleep(2);
		lock.unlock();
		System.out.println("Calc unlocked.");
		return result;
	}

}
