//AUTHORS: ELONA KOSHARNY AND TRISTEN PELLOT

import java.util.*;

public class DiskScheduling {
	
	public static final int REQUESTS = 50;
	public static final int CYLINDERS = 5000;
	
	public static void main(String [] args) {
		
		//create two arrays for the requests (one will be sorted)
		int sequence[]= new int[REQUESTS];
		int sortedSequence[] = new int [REQUESTS];
		
		//fill the arrays
		for(int i=0; i<REQUESTS; i++) {
			int x = (int)(Math.random()*(CYLINDERS+1));
			sequence[i]=x;
			sortedSequence[i]=x;
		}
		
		//sort the requests
		Arrays.parallelSort(sortedSequence);
		
		//keep track of the starting position
		int initialPos = 0;
		for(int i=0; i<REQUESTS; i++) {
			if(sortedSequence[i] == sequence[0] )
				initialPos = i;
		}
		
		//Print the sequences and head movements for each algorithm
		System.out.print("Requests: ");
		for(int i=0; i<REQUESTS; i++) {
			System.out.print(sequence[i]+ " ");
		}
		System.out.println();
		System.out.print("Requests (sorted): ");
		for(int i=0; i<REQUESTS; i++) {
			System.out.print(sortedSequence[i]+ " ");
		}
		System.out.println(); 
		System.out.println("Inital Position of disk head: " + initialPos);
		System.out.println("Number of head movement with FCFS algorithm: " + FCFS(sequence));
		System.out.println("Number of head movement with SSTF algorithm: " + SSTF(sortedSequence, initialPos));
		System.out.println("Number of head movement with LOOK algorithm: " + LOOK(sortedSequence, initialPos));
	}
	
	//First come first serve algorithm
	//All incoming requests are placed at the end of the queue. 
	//Whatever number that is next in the queue will be the next number served.
	//sorted array isn't necessary for this algorithm
	static int FCFS(int s[]) {
		int movements=0;
		for(int i=0; i<REQUESTS-1; i++) {
			movements+=Math.abs(s[i]-s[i+1]);
		}
		return movements;
	}
	
	//Shortest Seek Time First Algorithm
	//All incoming requests are serviced according to next shortest distance
	//Sorted array necessary for this algorithm
	static int SSTF(int ss[], int a) {
		
		//create variables for the current position and the positions to left 
		//and right of it
		//create movement variables to keep track of the head movements
		int left = a-1;
		int right = a+1;
		int current = a;
		int leftmovements = 0;
		int rightmovements = 0;
		int movements = 0;
		
		//while the request number is less than 50, find the number of head movements
		//to the left and right of the current position
		//whichever side has the smaller number of movements will become the current position
		//and will be added to the total number of head movements  
		int x = 0; 
		while (x<REQUESTS) {
			
			leftmovements=Math.abs(ss[current]-ss[left]);
			rightmovements=Math.abs(ss[current]-ss[right]);
			
			if(leftmovements>=rightmovements || left==0) {
				current=right;
				movements+=rightmovements;
				if(right<REQUESTS-1)
					right++;
				
			}
			else if(leftmovements<=rightmovements || right == REQUESTS-1){
				current=left;
				movements+=leftmovements;
				if(left>0)
					left--;
			}
			x++;
			
		}
		return movements;
	}
	//LOOK algorithm
	//Starting with the first request, the head moves towards the last request
	// servicing all the requests in between. After reaching the last request,
	//the head reverses direction and move towards the first request, servicing
	//all the requests in between
	static int  LOOK(int ss[], int a){
		
		int current=a;
		int movements=0;
		
		//the current position moves to the end of the disk, adding the movements
		//between each request
		while(current<REQUESTS-1) {
			movements+=Math.abs(ss[current+1] - ss[current]);
			current++;
		}
		//the current position moves to the initial position
		movements+=Math.abs(ss[current]-ss[a-1]);
		current = a-1;
		
		//the current position moves to the other end of the disk, adding the
		//movements between each request
		while(current>0) {
			movements+=Math.abs(ss[current]-ss[current - 1]);
			current--;
		}
		return movements;
	}
}
