package interruptable;

import java.util.concurrent.TimeUnit;

public class Interruptable implements Runnable {

	@Override
	public void run() {
		foo();
	}

	private void foo() {
		try {
			System.out.println("Entering sleep state.");
			TimeUnit.SECONDS.sleep(45);
		} catch (InterruptedException e) {
			System.out.println("Interruptable has been interrupted.");
		} finally {
			System.out.println("Exiting gracefully...");
		}
	}

}
