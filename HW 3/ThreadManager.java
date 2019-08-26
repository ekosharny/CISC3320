//AUTHOR: Tristen Pellot & Elona Kosharny

import java.util.concurrent.Semaphore;

public class ThreadManager extends PidManager implements Runnable{

	//The pid value
	private int p;
	//create new semaphore with access count of 1
	static Semaphore semaphore = new Semaphore(1);
	
	
	//Constructor
	//Parameter is the pid value
	public ThreadManager (int x) {
		this.p=x;
	}
	
	//Method in Runnable Interface to execute thread tasks
	public void run() {
		
		//Print the number of locks available, and assign to one pid
		//Only one thread can access a resource at once
		try {
			System.out.println(semaphore.availablePermits() + " permit available right now");
			System.out.println(p + " is acquiring lock...");
			semaphore.acquire();
			System.out.println(p + " got the permit!");

			try {
				//creates a random sleep time(milliseconds)
				int t = (int)(Math.random()*1000);
				System.out.println("Pid " + p + " is sleeping for " + t + " milliseconds");
		
				//puts thread to sleep for t time
				Thread.sleep(t);
			} finally {
				//pid will release a lock, making it available for use for next pid
				System.out.println(p + " is releasing lock...");
				semaphore.release();
				System.out.println(semaphore.availablePermits() + " permit available right now");
			} 
		}catch (InterruptedException ie) {
			System.out.println("Thread has been interrupted " + ie);
		}
		
		//releases pid after t time
		System.out.println("Pid " + p + " is released");
		
		release_pid(p);
	}

	public static void main(String[] args) {

		//create new HashMap of pid values
		PidManager pids = new PidManager();
		pids.allocate_map();
		
		//Allocates the pid to a thread. If pid unavailable, different value assigned
		for(int i=0; i<20; i++) {
			int p = pids.allocate_pid();
			while(p==-1) {
				p=pids.allocate_pid();
			}
			System.out.println("Pid " + p + " is in use");
			Thread thread = new Thread(new ThreadManager(p));
			thread.start();
		}
	}
}