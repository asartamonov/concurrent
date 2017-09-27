package scheduled;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

class BeeperControl {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public void beepForAnHour() {
		final Runnable beeper = () -> {System.out.println("beep");};
		//final ScheduledFuture<?> beeperHandle = 
		IntStream.range(0, 10).forEach(i -> {scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);});
		scheduler.schedule(() -> {scheduler.shutdownNow();}, 3 * 10, SECONDS);
	}
	public static void main(String[] args) {
		BeeperControl bc = new BeeperControl();
		bc.beepForAnHour();
	}
}