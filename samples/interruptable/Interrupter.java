package interruptable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Interrupter {

	public static void main(String[] args) {
//		/* 1. Thread object */
//		Thread thread = new Thread(new Interruptable());
//		thread.start();
//		System.out.println("Sleeping 5 seconds in caller");
//		try {
//			TimeUnit.SECONDS.sleep(5);
//		} catch (InterruptedException e) {
//			// TODO: handle exception
//		}
//		thread.interrupt();
//		
//		/* 2. Future object */
		ExecutorService executor = Executors.newCachedThreadPool();
//		Future<?> future = executor.submit(new Interruptable());
//		System.out.println(String.format("Are we done? %s\nTask cancelled? %s", future.isDone(), future.isCancelled()));
//		future.cancel(true);
//		System.out.println(String.format("Are we done? %s\nTask cancelled? %s", future.isDone(), future.isCancelled()));
//		Thread t = new Thread(new ObjectLocked());
//		t.start();
//		t.interrupt();
//		
		/* Can we interrupt callable? */
		// 1. sleep
		Future<Integer> calcResult = executor.submit(new SeriousCalculator());
		try {
			System.out.println("Submitted... Sleping for 5 secs and calling get()");
			System.out.println("Cancelling");
			calcResult.cancel(true);
			TimeUnit.SECONDS.sleep(5);
			System.out.println("We didn't call get yet!");
			TimeUnit.SECONDS.sleep(2);
			if (!calcResult.isCancelled()) {
				System.out.println("Result " + calcResult.get());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
		
		// 2. Lock
//		System.out.println("Lock ");
//		Future<Integer> calcLockResult = executor.submit(new SeriousCalculatorWithLock());
//		System.out.println("Calculated? " + calcLockResult.isDone());
//		System.out.println("Cancelling task .." + calcLockResult.cancel(true));
	}
}
