package edu.ncsu.csc216.pack_scheduler.util;
/**
 * This class develops a linked list that is implemented through recursion
 * @param <E> type of object that this list will utilize
 */
public class LinkedListRecursive<E> {
	/**
	 * List node representing front of list
	 */
	private ListNode front;
	/**
	 * Represents size of the list
	 */
	private int size;
	/**
	 * Constructs a linked list with it empty and front = null
	 */
	public LinkedListRecursive() {
		size = 0;
		front = null;
	}
	/**
	 * Returns the size of the list
	 * @return size of the list
	 */
	public int size() {
		return size;
	}
	/**
	 * Returns if the list is empty
	 * @return if list is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * Looks in the list to see if it contains an element
	 * @param element that is check if contained
	 * @return whether the list contains the element
	 */
	public boolean contains(E element) {
		if(!isEmpty()) {
			return front.contains(element);
		}
		return false;
	}
	/**
	 * Adding to the end of the list
	 * @param element the element to add
	 * @return whether the element was added
	 */
	public boolean add(E element) {
		if (element == null){
			throw new NullPointerException();
		}
		if(contains(element)) {
			throw new IllegalArgumentException();
		}
		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		} else {
			return front.add(element);
		
		}
	}
	/**
	 * Adding to a index in the list
	 * @param index the index to add to
	 * @param element the element to add to
	 */
	public void add(int index, E element) {
		if (element == null){
			throw new NullPointerException();
		}
		if(contains(element)) {
			throw new IllegalArgumentException();
		} else if (index == 0){
			front = new ListNode(element, front);
			
		} else if (size < index || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			front.add(index - 1, element);
			
		}
		size++;
	}
	/**
	 * Returns element at given index
	 * @param index to return element at 
	 * @return element at given index
	 */
	public E get(int index) {
		
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();	
		}
		
		return front.get(index);
			
		
	}
	/**
	 * Returns the element to be removed at index
	 * @param index that the element is to be removed at
	 * @return element to be removed
	 */
	public E remove(int index) {
		if (index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			E temp = front.data;
			front = front.next;
			size--;
			return temp;
		} else {
			return front.remove(index - 1);
		}
		
	}
	/**
	 * Returns boolean representation whether given element can be be removed from list
	 * @param element to be removed
	 * @return true if element can be removed, false otherwise
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
		if(isEmpty()) {
			return false;
		}
		if (element.equals(front.data)) {
			front = front.next;
			size--;
			return true;
		} else {
			return front.remove(element);
		}
	}
	/**
	 * Sets an element at given index
	 * @param index index that element should be set at
	 * @param element element to be set
	 * @return element previously in  the ListNode
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is be set is duplicate of existing element in list
	 */
	public E set(int index, E element) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		return front.set(index, element);
	}
	/**
	 * ListNode class
	 */
	private class ListNode {
		/** The data contained inside a ListNode */
		private E data;
		/** The next ListNode in the list */
		private ListNode next;
		
		/**
		 * Constructor for the ListNode class.
		 * @param data the data of the listnode
		 * @param next the next listnode
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Checks if an element is inside the list.
		 * @param element the element to check the list for
		 * @return whether the list contains the element
		 */
		public boolean contains(E element) {
			if(data.equals(element)) {
				return true;
			}
			else if (next == null) {
				return false;
			}
			else {
				return next.contains(element);
			}
			//.LinkedListRecursive false;
		}
		
		/**
		 * Adds to the end of the list.
		 * @param element the element to add
		 * @return whether the element was added
		 */
		public boolean add(E element) {
		
			if (next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			}
			else {
				return next.add(element);
			}
		}
		/**
		 * Adding to index in list
		 * @param index the index to add to
		 * @param element the element to add
		 */
		public void add(int index, E element) {
			if (index == 0) {
				next = new ListNode(element, next);
				
			} else {
				if (next == null) {
					throw new IndexOutOfBoundsException();
				}
				next.add(index - 1, element);
			}
		}
		/**
		 * Gets element at given index
		 * @param index to get element
		 * @return element at given index
		 */
		public E get(int index) {
			if (index == 0) {
				return data;
			} else {
				if (next == null) {
					throw new IndexOutOfBoundsException();
				}
				return next.get(index - 1);
			}
			
		}
		/**
		 * Removes an element at an index
		 * @param index to remove from
		 * @return the element
		 */
		public E remove(int index) {
			if (index == 0) {
				E temp = next.data;
				next = next.next;
				size--;
				return temp;
			} else {
				return next.remove(index - 1);
			}
			
		}
		/**
		 * Removes element
		 * @param element to remove
		 * @return whether it was removed
		 */
		public boolean remove(E element) {
			if (next == null) {
				return false;
			}
			if (next.data == element) {
				next = next.next;
				size--;
				return true;
			} else {
				return next.remove(element);
				
			}
			
		}
		/**
		 * Sets an item at an index
		 * @param index the index to set
		 * @param element to set
		 * @return the element
		 */
		public E set(int index, E element) {
			if (index == 0) {
				E prevData = data;
				data = element;
				return prevData;
			} else {
				return next.set(index - 1, element);
			}
		}
		
	}
}
