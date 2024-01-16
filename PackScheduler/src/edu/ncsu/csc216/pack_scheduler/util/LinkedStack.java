package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;


/**
 * LinkedStack class that delegates to Linked Abstract class
 * LinkedList class implements Stack interface
 * @param <E> of type of object we are working with- will be defined later in lab
 * @author Amrish Naranappa 
 */
public class LinkedStack<E> implements Stack<E> {
	/** 
	 * Delegating to a Linked abstract list
	 */
	private LinkedAbstractList<E> list;
    
    /**
	 * Constructs a ArrayStack with a capacity
	 * @param capacity of the stack
	 */
	public LinkedStack(int capacity) {
		this.list = new LinkedAbstractList<E>(capacity);
		
	}
	/**
	 * Adds the element to the top of the stack
	 * @throws IllegalArgumentException if cap has been reached
	 */
	@Override
	public void push(E element) {
		list.add(0, element);
	
	}
	/**
	 * Removes and returns the element at the top of the stack
	 * @throws EmptyStackException if list is empty
	 */
	@Override
	public E pop() {
		 if (isEmpty()) {
			 throw new EmptyStackException();
		 }
		 return list.remove(0);

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
	 * Returns size of stack
	 * @return size of stack
	 */
	@Override
	public int size() {
		return list.size();
	}
	/**
	 * Sets the stack’s capacity
	 * @throws IllegalArgumentException if actual parameter
	 * is negative or if it is less than the number of elements in the stack
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		
		
	}

}
