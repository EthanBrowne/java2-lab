/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * The Queue Interface
 * @param <E> generic type
 */
public interface Queue<E> {
	/**
	 * Adds the element to the back of the Queue
	 * @param element the element to add
	 * @throws IllegalArgumentException if there is no capacity
	 */
	void enqueue(E element);
	/**
	 * Removes and returns the element at the front of the Queue
	 * @return Element at the front of the queue
	 * @throws NoSuchElementException if queue is empty
	 */
	E dequeue();
	/**
	 * Returns true if the Queue is empty
	 * @return boolean of whether the list is empty
	 */
	boolean isEmpty();
	/**
	 * Returns the number of elements in the Queue
	 * @return number of elements in the queue
	 */
	int size();
	/**
	 * Sets the Queue’s capacity
	 * @param capacity the capacity of the queue
	 * @throws IllegalArgumentException If the actual parameter is negative or if it is less than the number of elements in the Queue
	 */
	void setCapacity(int capacity);
}
