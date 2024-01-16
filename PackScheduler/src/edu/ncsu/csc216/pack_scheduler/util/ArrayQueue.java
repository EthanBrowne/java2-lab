/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue class
 * Uses queue interfaces
 * @param <E> generic type
 */
public class ArrayQueue<E> implements Queue<E> {
	/** ArrayList field */
	private ArrayList<E> list;
	/** Capacity Field */
	private int capacity;
	/**
	 * Constructs an ArrayQueue
	 * @param capacity the capacity of the ArrayQueue
	 */
	public ArrayQueue(int capacity){
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
	/**
	 * Adds the element to the back of the Queue
	 * @param element the element to add
	 * @throws IllegalArgumentException if there is no capacity
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(element);
	}
	/**
	 * Removes and returns the element at the front of the Queue
	 * @return Element at the front of the queue
	 * @throws NoSuchElementException if queue is empty
	 */
	@Override
	public E dequeue() {
		if (list.size() == 0) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}
	/**
	 * Returns true if the Queue is empty
	 * @return boolean of whether the list is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}
	
	/**
	 * Returns the number of elements in the Queue
	 * @return number of elements in the queue
	 */
	@Override
	public int size() {
		return list.size();
	}
	/**
	 * Sets the Queue’s capacity
	 * @param capacity the capacity of the queue
	 * @throws IllegalArgumentException If the actual parameter is negative or if it is less than the number of elements in the Queue
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	/**
	 * Returns the capacity of the list
	 * @return capacity of list
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Compares two objects together to determine if equal.
	 * @param a the object to compare
	 * @return true if the object is the same, false if different
	 */
	public boolean contains(E a) {
		for(int i = 0; i < size(); i++) {
			if(this.list.get(i).equals(a)) {
				return true;
			}
		}
		return false;
	}
}
