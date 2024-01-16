/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * LinkedAbstractList is a custom linear data structure generic type linked list class. 
 * The class encapsulates the fields: front (first node), size, and capacity. The class implements 
 * the following list behaviors:
 * <ul>
 * <li>adding to list at a specified index.
 * <li>removing item at index.
 * <li>setting an item at index. 
 * <li>retrieving an item at an index.
 * <li>accessing the size of the list.
 * </ul>
 * In addition to setting the list capacity.
 * 
 * @param <E> generic type object
 * @author Maxim Shelepov
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** The last node reference of the linked list */
	private ListNode back;
	/** The first node reference of the linked list */
	private ListNode front;
	/** The size of linked list */
	private int size;
	/** The max capacity for the list **/
	private int capacity;
	
	/**
	 * Constructs a LinkedAbstractList with a max capacity (upper bound) provided field. 
	 * First item of the linked list is null and the size is set to 0.
	 * @param capacity the max capacity of items in the linked list.
	 */
	public LinkedAbstractList(int capacity) {
		back = null;
		front = null;
		size = 0;
		setCapacity(capacity);
	}
	
	/**
	 * Sets the capacity for the linked list.
	 * @param capacity the list size upper bound
	 * @throws IllegalArgumentException if the capacity is less than 0
	 * or less than the current size of the list
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException("Invalid capacity.");
		}
		
		this.capacity = capacity;
	}
	
	/**
	 * Adds a generic type object at a specified index; increments size.
	 * @param idx the index to add at
	 * @param elem the generic type object to add
	 * @throws IndexOutOfBoundsException if idx is less than 0 or greater than size
	 * @throws NullPointerException if the provided generic type object is null
	 * @throws IllegalArgumentException if the object is duplicate of another in the list
	 */
	@Override
	public void add(int idx, E elem) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		if (elem == null) {
			throw new NullPointerException("Item cannot be null.");
		}
		if (size() == capacity) {
			throw new IllegalArgumentException("Reached capacity.");
		}
		if (isDuplicate(elem)) {
			throw new IllegalArgumentException("Item already exists in the list.");
		}
		
		ListNode curr = front;
		if (idx == 0) {
			// add to start
			front = new ListNode(elem, curr);
		} else if (idx == size()) {
			setBackNode();
			back.next = new ListNode(elem);
		} else {
			// add to middle
			for (int i = 0; i < idx - 1; i++) {
				curr = curr.next;
			}
			
			ListNode saveNext = curr.next;
			curr.next = new ListNode(elem, saveNext);
		}
		size++;
	}
	
	/**
	 * Helper method that determines if a provided generic type object is a duplicate of another
	 * in the linked list.
	 * @param elem the object to check duplicate for
	 * @return true if the object is a duplicate, false otherwise
	 */
	private boolean isDuplicate(E elem) {
		ListNode curr = front;
		while (curr != null) {
			if (elem.equals(curr.data)) {
				return true;
			}
			curr = curr.next;
		}
		
		return false;
	}
	
	private void setBackNode() {
		back = front;
		while (back.next != null) {
			back = back.next;
		}
	}
	
	/**
	 * Removes a linked list item at the provided index and returns the
	 * the item removed; decrements size.
	 * @param idx the index at which to remove an item
	 * @return the removed generic type object
	 * @throws IndexOutOfBoundsException if idx is less than 0 or greater than or equal to size
	 */
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		ListNode curr = front;
		E removed;
		if (idx == 0) {
			removed = front.data;
			front = curr.next;
		} else {
			for (int i = 0; i < idx - 1; i++) {
				curr = curr.next;
			}
			
			removed = curr.next.data;
			curr.next = curr.next.next;
		}
		size--;
		
		return removed;
	}
	
	/**
	 * Sets an item in the linked list at the provided index to the provided
	 * generic type object. Note: changes the data of a node at an index.
	 * @param idx the index of the node to set data for
	 * @param elem the element to set to
	 * @return the overwritten generic type object
	 * @throws IndexOutOfBoundsException if idx is less than 0 or greater than or equal to size
	 * @throws NullPointerException if the provided generic type object is null
	 * @throws IllegalArgumentException if the object is duplicate of another in the list
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
		
		ListNode curr = front;
		for (int i = 0; i < idx; i++) {
			curr = curr.next;
		}
		E overrittenData = curr.data;
		curr.data = elem;
		
		return overrittenData;
	}
	
	/**
	 * Returns the linked list item at a provided index.
	 * @param idx the index of the item to retrieve
	 * @return the item of the linked list at the index
	 * @throws IndexOutOfBoundsException if idx is less than 0 or greater than or equal to size
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		ListNode curr = front;
		for (int i = 0; i < idx; i++) {
			curr = curr.next;
		}
		
		return curr.data;
	}
	
	/**
	 * Returns the linked list size.
	 * @return a integer representing the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * ListNode is an object that represents a node in this LinkedList. The ListNode manages
	 * its generic type object data and the pointer to the next ListNode. ListNode provides 2 constructors one with 
	 * a next pointer option and the other with a null next pointer (appended to end of list).
	 * 
	 * @author Maxim Shelepov
	 */
	private class ListNode {
		/** Data object stored by node */
		public E data;
		/** Pointer to next node in list */
		public ListNode next;
		
		/**
		 * Constructs a ListNode with data and pointer fields.
		 * @param data the data to store in the node
		 * @param next the pointer to the next node in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Constructs a ListNode with just data and sets next to null.
		 * Note: used for list appending.
		 * @param data the data to store in the node
		 */
		public ListNode(E data) {
			this(data, null);
		}
	}
}
