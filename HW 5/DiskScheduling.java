//AUTHOR: ELONA KOSHARNY AND TRISTEN PELLOT
import java.util.*;
public class DiskScheduling {
	
	public static final int REQUESTS = 50;
	public static final int CYLINDERS = 5000;
	
	public static void main(String [] args) {
		
		//create two arrays for the requests (one will be sorted)
		int schedule[]= new int[REQUESTS];
		int sortedSchedule[] = new int [REQUESTS];
		
		//fill the arrays
		for(int i=0; i<REQUESTS; i++) {
			int x = (int)(Math.random()*(CYLINDERS+1));
			schedule[i]=x;
			sortedSchedule[i]=x;
		}
		
		//sort the requests
		Arrays.parallelSort(sortedSchedule);
		
		//keep track of the starting position
		int initialPos = 0;
		for(int i=0; i<REQUESTS; i++) {
			if(sortedSchedule[i] == schedule[0] )
				initialPos = i;
		}
		
		//Print the sequences and head movements for each algorithm
		System.out.print("Requests: ");
		for(int i=0; i<REQUESTS; i++) {
			System.out.print(schedule[i]+ " ");
		}
		System.out.println();
		System.out.print("Requests (sorted): ");
		for(int i=0; i<REQUESTS; i++) {
			System.out.print(sortedSchedule[i]+ " ");
		}
		System.out.println(); 
		System.out.println("Initial Position of disk head: " + initialPos);
		System.out.println("Number of head movement with SCAN algorithm: " + SCAN(sortedSchedule, initialPos));
		System.out.println("Number of head movement with CSCAN algorithm: " + CSCAN(sortedSchedule, initialPos));
		System.out.println("Number of head movement with CLOOK algorithm: " + CLOOK(sortedSchedule, initialPos));
	}
	
	//SCAN (Elevator) algorithm
	//Scans down towards the nearest end, adding all the head movements between requests, including 
	//the movement from the first request to the first cylinder. 
	//Then it scans up servicing the requests that it didn't get going down
	static int  SCAN (int ss[], int a){
		
		int current=a;
		int movements=0;
		
		//the current position moves to one end of the disk, adding all the head movements between requests
		while(current>0) {
			movements+=Math.abs(ss[current]-ss[current-1]);
			current--;
		}
		
		//number of head movements includes the movement from the first request to the first cylinder
		//also includes the movement from the first cylinder to the next request that hasn't been serviced
		movements+=Math.abs(ss[current]-0);
		movements+=Math.abs(0 + ss[a+1]);
		current = a+1;
		
		//the current position moves from the initial position+1 to the other end of the disk, 
		//adding all the head movements between requests
		while(current<REQUESTS-1) {
			movements+=Math.abs(ss[current+1]-ss[current]);
			current++;
		}
		
		return movements;
		
	}
	
	//CSCAN Algorithm
	//Similar to the elevator, however once the head reaches one end, it jumps to the other end
	//and moves in the same direction
	//The number of head movements does not include the jump 
	static int  CSCAN(int ss[], int a){
		
		int current=a;
		int movements=0;

		//the current position moves to one end of the disk, adding all the head movements between requests
		while(current>0) {
			movements+=Math.abs(ss[current]-ss[current-1]);
			current--;
		}
		
		//number of head movements includes the movement from the first request to the first cylinder
		//also includes the movement from the last cylinder to the next request that hasn't been serviced
		movements+=Math.abs(ss[current]-0);
		movements+=Math.abs((CYLINDERS-1) - ss[REQUESTS-1]);
		
		//current position jumps to other end
		current=REQUESTS-1;

		while(current>a) {
			movements+=Math.abs(ss[current] - ss[current-1]);
			current--;
		}
		
		return movements;
		
	}
	
	//CLOOK algorithm
	//The head moves from the initial position to the first request, then jumps to the last request, 
	//and moves down, servicing the rest of the requests
	static int  CLOOK(int ss[], int a){
		
		int current=a;
		int movements=0;

		//the current position moves to one end of the disk, adding all the head movements between requests
		while(current>0) {
			movements+=Math.abs(ss[current]-ss[current-1]);
			current--;
		}
		
		//current position jumps to last request
		current = REQUESTS-1;

		//current position moves from last request to the rest of the requests, adding each head movement
		while(current>a) {
			movements+=Math.abs(ss[current] - ss[current-1]);
			current--;
		}
		
		return movements;
		
	}
}
