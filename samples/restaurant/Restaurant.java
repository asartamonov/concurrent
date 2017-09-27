//: concurrency/Restaurant.java
// The producer-consumer approach to task cooperation.
package restaurant;

import java.util.concurrent.*;

class Meal {
	private final int orderNum;

	public Meal(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "Meal " + orderNum;
	}
}

class BusBoy implements Runnable {
	private Restaurant restaurant;

	public BusBoy(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.dirtyTable == null) {
						wait();
					}
				}
				System.out.println("Boy cleaning the table");
				synchronized (restaurant.chef) {
					restaurant.dirtyTable = null;
					restaurant.chef.notifyAll(); // Ready for another
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Boy interrupted");
		}
	}
}	

class WaitPerson implements Runnable {
	private Restaurant restaurant;

	public WaitPerson(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal == null)
						wait(); // ... for the chef to produce a meal and boy to clean a table
				}
				System.out.println("Waitperson got " + restaurant.meal);
				synchronized (restaurant.chef) {
					restaurant.meal = null;
					restaurant.chef.notifyAll(); // Ready for another
				}
			}
		} catch (InterruptedException e) {
			System.out.println("WaitPerson interrupted");
		}
	}
}

class Chef implements Runnable {
	private Restaurant restaurant;
	private int count = 0;

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal != null && restaurant.dirtyTable != null)
						wait(); // ... for the meal to be taken
				}
				if (++count == 10) {
					System.out.println("Out of food, closing");
					restaurant.exec.shutdownNow();
				}
				System.out.println("Order up! ");
				synchronized (restaurant.busBoy) {
					restaurant.dirtyTable = new Object();
					restaurant.busBoy.notifyAll(); // Clean the table, boy!
				}
				synchronized (restaurant.waitPerson) {
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef interrupted");
		}
	}
}

public class Restaurant {
	public Object dirtyTable = new Object();
	public Meal meal;
	public ExecutorService exec = Executors.newCachedThreadPool();
	public WaitPerson waitPerson = new WaitPerson(this);
	public Chef chef = new Chef(this);
	public BusBoy busBoy = new BusBoy(this);

	public Restaurant() {
		exec.execute(chef);
		exec.execute(waitPerson);
		exec.execute(busBoy);
	}

	public static void main(String[] args) {
		new Restaurant();
	}
}
