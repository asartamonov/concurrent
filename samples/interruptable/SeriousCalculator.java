package interruptable;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SeriousCalculator implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		try {
			System.out.println("Starting serious calculator.");
			int result = 2 + 2;
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Exiting serious calculator");
			return result;
		} catch (Exception e) {
			System.out.println("Something caught!");
		}
		return null;
	}

}
