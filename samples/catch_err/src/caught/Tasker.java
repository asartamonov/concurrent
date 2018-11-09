package caught;

public class Tasker implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Running: " + Thread.currentThread().getName());
		throw new RuntimeException("Exception from: " + Thread.currentThread().getName());
	}

}
