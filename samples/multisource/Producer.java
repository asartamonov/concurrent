package multisource;

import java.util.concurrent.atomic.AtomicInteger;

import multisource.generator.StrGenerator;

public class Producer implements Runnable {

	private static volatile AtomicInteger instanceCounter = new AtomicInteger(0);
	private StrGenerator generator;
	private Holder holder;
	private int id;

	public Producer(int id, StrGenerator generator, Holder holder) {
		super();
		this.id = id;
		this.generator = generator;
		this.holder = holder;
		instanceCounter.incrementAndGet();
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Producer #" + id);
		System.out.println("Producer started "+ Thread.currentThread().getName() + ". Remaining instances count: " + instanceCounter.get());
		try {
			while (!Thread.interrupted()) {
					holder.addItem(generator.generate());
			}
		} catch (InterruptedException e) {
			System.out.println("Producer interrupted..." + Thread.currentThread().getName() + ". Remaining instances count: " + instanceCounter.decrementAndGet());
		}
	}
}
