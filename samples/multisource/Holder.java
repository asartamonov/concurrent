package multisource;

import java.util.ArrayList;
import java.util.List;

public class Holder {
	private final List<String> buffer = new ArrayList<String>();
	private volatile boolean bufferEmpty = true;
	private volatile boolean bufferFull = false;
	private final int bufferMaxSize;

	public Holder(int bufferMaxSize) {
		super();
		this.bufferMaxSize = bufferMaxSize;
	}

	public synchronized void addItem(String item) throws InterruptedException {
		waitForItemsRemoved();
		buffer.add(item);
		bufferEmpty = false;
		if (buffer.size() == bufferMaxSize) {
			bufferFull = true;
		}
		notifyAll();
	}

	public synchronized int getBufferElemCount() {
		int elemCount = buffer.size();
		return elemCount;
	}

	public synchronized String getItem() throws InterruptedException {
		waitForItemsAdded();
		String item = buffer.remove(0);
		bufferFull = false;
		if (buffer.size() == 0) {
			bufferEmpty = true;
		}
		notifyAll();
		return item;
	}

	private synchronized void waitForItemsAdded() throws InterruptedException {
		while (bufferEmpty) {
			//System.out.println(Thread.currentThread().getName() + " entering wait(). Buff size " + this.getBufferElemCount());
			wait();
		}
		//System.out.println("Exiting wait " + Thread.currentThread().getName());
	}

	private synchronized void waitForItemsRemoved() throws InterruptedException {
		while (bufferFull) {
			//System.out.println(Thread.currentThread().getName() + " entering wait(). Buff size " + this.getBufferElemCount());
			wait();
		}
		//System.out.println("Exiting wait " + Thread.currentThread().getName());
	}
}
