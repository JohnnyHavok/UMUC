/**
 * Author: Justin Smith
 * Course: CMSC335.6380
 * Date: Aug 21, 2010
 * Project: Project 5
 * File: Project5.java
 * Development Environment:  Apple Mac OS X 10.6.4
 *                           Eclipse 3.6 / Java 1.6.0_20
 */
package project5;

class ChopStick {
	boolean lock = false;
	
	public synchronized void take() {
		while(lock)
			try { wait(); } catch(InterruptedException e) {}
		lock = true;
	}
	
	public synchronized void putBack() {
		lock = false;
		notify();
	}
}

class Person implements Runnable {
	//private ChopStick 	stick;
	private int			personID, totalEat, maxTime;
	private long		timer;
	private Person		left, right;
	private boolean		eating, borrowed, borrowedFromLeft, borrowedFromRight;
	
	public Person(int maxTime, int personID) {
		this.personID = personID;
		this.maxTime = maxTime;
		totalEat = 0;
		//stick = new ChopStick();
		eating = false;
		borrowed = false;
	}
	
	public void setLeft(Person p) { left = p; }
	
	public void setRight(Person p) { right = p; }
	
	public boolean isEating() { return eating; }
	
	public void findStick() { 
		if(!left.isEating()) {
			left.borrowStick();
			borrowedFromLeft = true;
		}
		else if(!right.isEating()) {
			right.borrowStick();
			borrowedFromRight = true;
		}
	}
	
	public void giveBackSticks() {
		if(borrowedFromLeft)
			left.returnStick();
		if(borrowedFromRight)
			right.returnStick();
	}
	
	public synchronized void borrowStick() {
		while(borrowed)
			try { wait(); } catch(InterruptedException e) {}
		borrowed = true;
	}
	
	public synchronized void returnStick() {
		borrowed = false;
		notify();
	}
	
	
	@Override
	public void run() {
		while(true) {
			timer = (long)(Math.random() * maxTime + 1) * 1000;
			try { Thread.sleep(timer); } catch (InterruptedException e) {}
			
			// Stop people from taking your stick
			eating = true;
			
			if(!borrowed) {
				// Attempt to lock your own chopstick, wait for it to comeback if needed
				//stick.take();
				
				// Attempt to find another stick from your left or right
				findStick();
				
				totalEat++;
				
				System.out.println("Person " +personID+" begins eating for the "
									+totalEat+" time(s)");
				
				try { Thread.sleep(timer); } catch (InterruptedException e) {}
				
				System.out.println("Person "+personID+" is finished eating");
				
				//stick.putBack();
				giveBackSticks();
			}
			
			// Make your stick available to borrow while you think again
			eating = false;
		}
	}
	
}

public class Project5 {
	/**
	 * Max time to think and eat, or the upper bounds of the random
	 * number.  The lower the number the more 1 or 2 threads will dominate
	 * the "eating".  5 seconds provides a decent amount of random time
	 * for a good distribution in the competitive concurrency.
	 */
	private final static int maxTime = 5; // Translates to seconds

	public static void main(String[] args) {
		// Invite the people
		Person p1 = new Person(maxTime, 1);
		Person p2 = new Person(maxTime, 2);
		Person p3 = new Person(maxTime, 3);
		Person p4 = new Person(maxTime, 4);
		Person p5 = new Person(maxTime, 5);
		
		// Set the table.
		p1.setLeft(p2); p1.setRight(p5);
		p2.setLeft(p3); p2.setRight(p1);
		p3.setLeft(p4); p3.setRight(p2);
		p4.setLeft(p5); p4.setRight(p3);
		p5.setLeft(p1); p5.setRight(p4);
		
		// Begin
		(new Thread(p1)).start();
		(new Thread(p2)).start();
		(new Thread(p3)).start();
		(new Thread(p4)).start();
		(new Thread(p5)).start();
	}
}
