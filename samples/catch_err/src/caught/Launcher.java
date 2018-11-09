package caught;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Launcher {
	private static List<String> errList = new CopyOnWriteArrayList<>();

	public static void main(String[] args) {
		IntStream.range(0, 20).boxed().forEach(i -> {
			Tasker t = new Tasker();
			ExceptionCatcher exc = new ExceptionCatcher(errList);
			Thread thread = new Thread(t);
			thread.setName("Thread number " + i);
			thread.setUncaughtExceptionHandler(exc);
			thread.start();
		});
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Errors logged: " + errList);
	}
}
