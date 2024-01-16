/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack class 
 * @author Amrish Naranappa
 * @param <E> type of parameter working with - will be defined later in lab
 */
public interface Stack<E> {
	/**
	 * Adds element to top of stack
	 * @param element to be pushed to top of stack
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	void push(E element);
	
	/**
	 * Removes the element at top of stack
	 * @return E to be removed
	 * @throws EmptyStackException if stack is empty
	 */
	E pop();
	/**
	 * Returns true if stack is empty, false otherwise
	 * @return true or false based on stack emptiness
	 */
	boolean isEmpty();
	/**
	 * Returns number of elements in stack
	 * @return size of stack
	 */
	int size();
	/**
	 * Sets the stack's capacity
	 * @param capacity of stack
	 * @throws IllegalArgumentException if parameter is negative or less than number of elements in stack.
	 */
	void setCapacity(int capacity);
		
	
	
}
