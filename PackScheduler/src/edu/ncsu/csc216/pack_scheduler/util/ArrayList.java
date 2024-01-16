/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * ArrayList is a custom linear data structure generic type class which contains and manages the array the dynamic functionality of a
 * specified type. The class encapsulates the fields describing the list and contents: initial size, size of the list, and the list itself.
 * The class implements the following list behaviors:
 * <ul>
 * <li>adding to list at a specified index.
 * <li>removing item at index.
 * <li>setting an item at index. 
 * <li>retrieving an item at an index.
 * <li>accessing the size of the list.
 * </ul>
 * 
 * @param <E> generic type object
 * @author Maxim Shelepov
 */
public class ArrayList<E> extends AbstractList<E> {
	/** Initial list size **/
	private static final int INIT_SIZE = 10;
	/** E generic type list **/
	private E[] list;
	/** Size of the list **/
	private int size;
	
	/**
	 * Constructs the list object. Sets the list initial size and size to 0.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}

	/**
	 * Add an element to the list at the specified index.
	 * @param idx the index of the insertion.
	 * @param elem the element to insert.
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	@Override
	public void add(int idx, E elem) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		if (elem == null) {
			throw new NullPointerException("Item cannot be null.");
		}
		if (isDuplicate(elem)) {
			throw new IllegalArgumentException("Item already exists in the list.");
		}
		if (list.length == size()) {
			growArray();
		}
		
		if (idx == size()) {
			list[idx] = elem;
			size++;
			return;
		}
		
		for (int i = size() - 1; i >= idx; i--) {
			list[i + 1] = list[i];
		}
		
		list[idx] = elem;
		size++;
	}
	
	/**
	 * Helper method to increase the list capacity by 2.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		int newCapacity = list.length * 2;
		E[] newList = (E[]) new Object[newCapacity];
		
		for (int i = 0; i < size; i++) {
			newList[i] = list[i];
		}
		
		list = newList;
	}
	
	/**
	 * Helper method to determine if a particular element is a duplicate of another already in the list.
	 * @param elem the element to check for duplicate
	 * @return true if the element is a duplicate of another currently in the list, false otherwise
	 */
	private boolean isDuplicate(E elem) {
		for (int i = 0; i < size; i++) {
			if (list[i].equals(elem)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes an element at a specified index.
	 * @param idx the index of the element to remove
	 * @return the element which was removed
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		E removed = list[idx];
		for (int i = idx; i < size() - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size() - 1] = null;
		size--;
		
		return removed;
		
	}
	
	/**
	 * Sets an element of the list to the provided element at the specified index.
	 * @param idx the index to set
	 * @param elem the element to set
	 * @return the element being replaced at the index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	@Override
	public E set(int idx, E elem) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		if (elem == null) {
			throw new NullPointerException("Item cannot be null.");
		}
		if (isDuplicate(elem)) {
			throw new IllegalArgumentException("Item already exists in the list.");
		}
		E previousItem = list[idx];
		list[idx] = elem;
		
		return previousItem;
	}

	/**
	 * Retrieves an element at the specified index.
	 * @param idx the index of the element to retrieve
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		return list[idx];
	}

	/**
	 * Retrieves the size of dynamic size of the list.
	 * @return the size of the list as an integer
	 */
	@Override
	public int size() {
		return size;
	}
}
