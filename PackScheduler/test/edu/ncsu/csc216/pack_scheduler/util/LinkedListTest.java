package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ListIterator;

import org.junit.jupiter.api.Test;

/**
 * Test class for Linked List class
 * 
 */
public class LinkedListTest {
	/**
	 * Test method for LinkedList constructor.
	 */
	@Test
	void testLinkedList() {
		LinkedList<String> linkedList = new LinkedList<>();
		assertNotNull(linkedList);
		assertEquals(0, linkedList.size());
	}
	/**
	 * Test method for placing iterator in correct place and checking if list node pointers  were updated
	 * along with its index's
	 */
	@Test
	void testListIterator() {
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add(0, "E1");
		linkedList.add(1, "E2");
		linkedList.add(2, "E3");
		assertThrows(IllegalArgumentException.class, () -> {
			 linkedList.add(3, "E2");
	        });
		ListIterator<String> iterator = linkedList.listIterator(1);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 linkedList.listIterator(-1);
	        });
        assertNotNull(iterator);
        assertEquals(1, iterator.nextIndex());
       
        assertTrue(iterator.hasPrevious());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.nextIndex());
        assertEquals("E2", iterator.next());
       // assertEquals("E1", iterator.previous());
        iterator.add("NewElement");
        assertEquals(4, linkedList.size());
        assertTrue(linkedList.contains("NewElement"));
        
        
		
	}
	/**
	 * Testing moving the iterator through previous() method , makes sure correct element is returned
	 */
	@Test
	void testListIteratorPrevious() {
		
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add(0, "E1");
		linkedList.add(1, "E2");
		linkedList.add(2, "E3");
		assertThrows(NullPointerException.class, () -> {
			 linkedList.add(3, null);
	        });
		assertThrows(IllegalArgumentException.class, () -> {
			 linkedList.add(3, "E2");
	        });
		ListIterator<String> iterator = linkedList.listIterator(1);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 linkedList.listIterator(-1);
	        });
        assertNotNull(iterator);
        assertEquals(1, iterator.nextIndex());
       
        assertTrue(iterator.hasPrevious());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.nextIndex());
        assertEquals("E1", iterator.previous());
	}
	/**
	 * Testing removing listNode from list through iterator
	 */
	@Test
	void testListIteratorRemove() {
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add(0, "E1");
		linkedList.add(1, "E2");
		linkedList.add(2, "E3");
        ListIterator<String> iterator = linkedList.listIterator(1);
        assertThrows(IllegalStateException.class, () -> {
            iterator.remove();
        });
        iterator.next();
       
        iterator.previous();
        iterator.remove();
        assertEquals(2, linkedList.size());
        assertEquals("E1", linkedList.get(0));
        assertEquals("E3", linkedList.get(1));
        
		
	}
	/**
	 * Testing setting nodes through iterator
	 */
	@Test
	void testListIteratorSet() {
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add(0, "E1");
		linkedList.add(1, "E2");
		linkedList.add(2, "E3");
		linkedList.set(1, "NewE2");
		assertEquals("NewE2", linkedList.get(1));
		assertEquals(3, linkedList.size());
		
		// Test setting an element at an invalid index (index out of bounds)
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedList.set(5, "InvalidElement");
		});

		assertThrows(NullPointerException.class, () -> {
			linkedList.set(0, null);
		});
			
		assertThrows(IllegalArgumentException.class, () -> {
			linkedList.set(2, "E1"); // Attempting to set a duplicate value
		});

       
	
	
}
	
}


