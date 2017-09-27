//: concurrency/OrnamentalGarden.java
package ornamental_cntdwnlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGardenMod {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		int entranceNum = 5;
		CountDownLatch cdl = new CountDownLatch(entranceNum);
		for (int i = 0; i < entranceNum; i++)
			exec.execute(new Entrance(i, cdl));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(3);
		exec.shutdownNow();
		cdl.await();
		System.out.println("Total: " + Entrance.getTotalCount());
		System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
	}
}
