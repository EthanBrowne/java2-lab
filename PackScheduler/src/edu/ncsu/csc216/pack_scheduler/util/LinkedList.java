/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Doubly Linked list implementation of the list interface. 
 * Contains inner classes representing list nodes of the list and an iterator that will traverse over 
 * elements in list and do specific actions needed by computer. 
 * @param <E> Generic Type
 * @author Amrish Naranappa 
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** Size field */
	private int size;
	/** The last node reference of the linked list */
	private ListNode back;
	/** The first node reference of the linked list */
	private ListNode front;
	
	/**
	 * Constructs a LinkedList
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	/**
	 * Keeps track of size of list
	 * @return size of list
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 * Returns a list iterator over the elements in this list 
	 * @param index the starting index of the iterator
	 * @return a list Iterator over the elements in this list
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}
	
	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * @return element previously at specified position
	 * @param index of element to be replaced
	 * @param element  previously at the specified position
	 * @throws IllegalArgumentException if the element is a duplicate of an element already in the list
     * @throws NullPointerException     if the specified element is null
     * @throws IndexOutOfBoundsException  if the index is out of range
	 */
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException("Cannot set an element to a duplicate value in the list.");
		}       
		return super.set(index, element);
		
	}
	
	/**
	 *  Inserts the specified element at the specified position in this list.
	 *  @param index to add element 
	 *  @param element to be inserted
	 *  @throws IllegalArgumentException if the element is a duplicate of an element already in the list
	 *  @throws IndexOutOfBoundsException if index out of range
	 */
	@Override
	public void add(int index, E element) {
		if(contains(element)) {
			throw new IllegalArgumentException("Duplicate elements not allowed");
		}
		super.add(index, element);
	}
	
	/**
	 * Inner class representing list nodes that make up the doubly linked list
	 * Since its doubly linked, each node will have a next reference to next node in list and
	 * a previous reference to the previous node in the list. 
	 */
	private class ListNode {
		/** Data object stored by node */
		public E data;
		/** Pointer to next node in list */
		public ListNode next;
		/** Pointer to previous node in list */
		public ListNode prev;
		
		/**
		 * Constructs a ListNode with data and pointer fields.
		 * @param data the data to store in the node
		 * @param next the pointer to the next node in the list
		 * @param prev the pointer to the previous node in the list
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		
		/**
		 * Constructs a ListNode with just data and sets next to null.
		 * Note: used for list appending.
		 * @param data the data to store in the node
		 */
		public ListNode(E data) {
			this(data, null, null);
		}
	}
	/**
	 * Private inner class representing an iterator for the LinkedList
	 * implements the ListIterator interface
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** Previous Index in the list */
		public int previousIndex;
		/** Next Index in the list */
		public int nextIndex;
		/** Last Returned Value */
		private ListNode lastRetrieved;
		/** previous List Node*/
		public ListNode previous;
		/** next list Node*/
		public ListNode next;
		
		/**
		 * LinkedListIterator constructor
		 * @param index the index in the list that the iterator will be created
		 * @throws IndexOutOfBoundsException if index is invalid
		 */
		public LinkedListIterator(int index) {
			if (index > size || index < 0) {
				throw new IndexOutOfBoundsException();
			}
			previous = front;
			next = front.next;
			for (int i = 0; i < index; i++) {
				previous = next;
				next = previous.next;
			}
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
			
		}
		/**
		 * Returns true if there is a next node in list
		 * @return true if next node exists in list
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;

		}

		@Override
		public E next() {
			E data = next.data;
			if (data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			next = next.next;
			previous = previous.next;
			previousIndex++;
			nextIndex++;
			return data;
			
		}
		/**
		 * Return true if there is a previous node in list
		 * @return true if previous node exists
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;

		}
		/**
		 * Returns data of the previous node and moves the iterator backward. 
		 * @return data of the previous node
		 * @throws NoSuchElementException if there is no previous element 
		 */
		@Override
		public E previous() {
			E data = previous.data;
			if (data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = next.prev;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return data;
		}
		/**
		 * Returns index of next node
		 * @return index of next Node 
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}
		/**
		 * Returns index of previous node
		 * @return index of previous node
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}
		/**
		 * Removes the last retrieved element  returned by either a call to 
		 * next() or previous()
		 */
		@Override
		public void remove() {
			if(lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (lastRetrieved.prev != null) {
				lastRetrieved.prev.next = lastRetrieved.next;
			} else {
				// If the element being removed is the head
				front = lastRetrieved.next;
			}

			if (lastRetrieved.next != null) {
				lastRetrieved.next.prev = lastRetrieved.prev;
			} else {
				// If the element being removed is the tail
				back = lastRetrieved.prev;
			}

			lastRetrieved = null; // Reset lastRetrieved to indicate removal
			size--;
			
			
		}
		/**
		 * sets the last retrieved element to the specific element 
		 * @param e the element to set
		 * @throws IllegalStateException if lastRetrieved is null
		 * @throws NullPointerException if the specified element is null
		 */
		@Override
		public void set(E e) {
			if(lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if(e == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}
		/**
		 * Inserts the specified element into the list
		 * @param e the element to add
		 * @throws NullPointerException if element is null
		 */
		@Override
		public void add(E e) {
			if(e == null) {
				throw new NullPointerException();
			}
			
			ListNode newNode = new ListNode(e, previous, next);
			previous.next = newNode;
			next.prev = newNode;
			previous = newNode;
			
			size++;
			previousIndex++;
			nextIndex++;
			lastRetrieved = null;
		}
		
	}
}



