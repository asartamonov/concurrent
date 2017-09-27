//: concurrency/OrnamentalGarden.java
package ornamental;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class OrnamentalGardenMod {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		List<Future<?>> resList = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			resList.add(exec.submit(new Entrance(i)));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(3);
		resList.forEach(item -> item.cancel(true));
		exec.shutdown();
		if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			System.out.println("Some tasks were not terminated!");
		System.out.println("Total: " + Entrance.getTotalCount());
		System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
	}
}
