//AUTHOR: Tristen Pellot & Elona Kosharny

public class ThreadManager extends PidManager implements Runnable{

	//The pid value
	private final int p;
	
	
	//Constructor
	//Parameter is the pid value
	public ThreadManager (int x) {
		this.p=x;
	}
	
	//Method in Runnable Interface to execute thread tasks
	public void run() {
		
		//creates a random sleep time(milliseconds)
		int t = (int)(Math.random()*100000);
		System.out.println("Pid " + p + " is sleeping for " + t + " milliseconds");
		
		//puts thread to sleep for t time
		try {
			Thread.sleep(t);
		} catch (InterruptedException ie) {
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
		for(int i=0; i<100; i++) {
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