/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue Class
 * Implements queue interface
 * @param <E> generic type
 */
public class LinkedQueue<E> implements Queue<E> {
	/** LinkedAbstractList field */
	private LinkedAbstractList<E> list;
	/** Capacity Field */
	private int capacity;
	
	/**
	 * Constructs an LinkedQueue
	 * @param capacity the capacity of the LinkedQueue
	 */
	public LinkedQueue(int capacity){
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	
	@Override
	public void enqueue(E element) {
		list.add(element);
	}

	@Override
	public E dequeue() {
		if (list.size() == 0) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		this.capacity = capacity;
		
	}
	/**
	 * Returns the capacity of the list
	 * @return capacity of list
	 */
	public int getCapacity() {
		return this.capacity;
	}
}
