package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * ArrayStack class
 * @param <E> element object type
 * @author Amrish Naranappa 
 */
public class ArrayStack<E> implements Stack<E> {
	/** Stack that is delegating to custom made ArrayList**/
    private ArrayList<E> list;
    /**
     * integer representing capacity of stack
     */
    private int capacity;
    /**
     * size representing size of stack
     */
    private int size;
    
	/**
	 * Constructs a ArrayStack with a capacity
	 * @param capacity of the stack
	 */
	public ArrayStack(int capacity) {
		setCapacity(capacity);
		this.list = new ArrayList<>();
		this.size = 0;	
		
	}
	/**
	 * Adds the element to the top of the stack
	 * @throws IllegalArgumentException if cap has been reached
	 */
	@Override
	public void push(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("Capacity has been reached");
		}
		list.add(element);
		size++;
		
	}
	/**
	 * Removes and returns the element at the top of the stack
	 * @throws EmptyStackException if list is empty
	 */
	@Override
	public E pop() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		size--;
		return list.remove(list.size() - 1);
	}
	/**
	 * Returns true or false based on if stack is empty or not 
	 * @return true if stack is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * returns size of stack
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 * sets the stack’s capacity
	 * @throws IllegalArgumentException if actual parameter
	 *  is negative or if it is less than the number of elements in the stack
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException("Invalid capacity");
		}
		this.capacity = capacity;
		
	}
	

}
