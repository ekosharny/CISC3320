//AUTHOR: ELONA KOSHARNY

import java.util.*;

public class PidManager {
	public static final int MAX=5000;
	public static final int MIN=300;
	public static final int SIZE = MAX-MIN+1;
	public static HashMap <Integer, Integer> pidmap;
	
	// Creates and initializes a data structure(Hashmap) for representing pids 
	// Returns -1 if unsuccessful and 1 if successful
	public static int allocate_map() {
		pidmap = new HashMap<>();
		//fills hashmap
		for (int i=0; i<SIZE; i++) {
			pidmap.put(i+MIN, 0);
		}
		if(pidmap != null) {
			return 1;
		}
		else
			return -1;
	}
	
	//Allocates and returns a pid
	//Returns -1 if if unable to allocate a pid
	public static int allocate_pid() {
		if(!pidmap.containsValue(0)) //if all pids are in use
			return -1;
		//generate random number with constraints which will be assigned to key
		int x = (int)(Math.random() * (SIZE) + MIN);
		//check if pid is already in use
		while(pidmap.get(x)==1) {
			x = (int)(Math.random() * (SIZE) + MIN);
		}
		pidmap.replace(x, 1);
		return x;
	}
	//Release a pid; make the value 0
		public static void release_pid(int x) {
			pidmap.replace(x, 0);
			System.out.println("Value of pid " + x + " is 0");
		}
}
