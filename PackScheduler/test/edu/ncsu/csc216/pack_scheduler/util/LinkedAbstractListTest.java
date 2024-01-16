/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests the LinkedAbstractList generic type linear data structure linked list. The class specifically tests the
 * add, remove, get, and set functionality. In addition to the set functionality for capacity.
 * 
 * @author Maxim Shelepov
 */
class LinkedAbstractListTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#LinkedAbstractList(int)}.
	 */
	@Test
	void testLinkedAbstractList() {
		LinkedAbstractList<String> empty = new LinkedAbstractList<String>(0);
		assertEquals(0, empty.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(1);
		assertDoesNotThrow(() -> {
			list.setCapacity(10);
			list.add(0, "C1");
			list.setCapacity(5);
			list.setCapacity(1);
		});
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> list.setCapacity(-1));
		assertEquals("Invalid capacity.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
			list.setCapacity(0);
		});
		assertEquals("Invalid capacity.", e2.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#add(int, java.lang.Object)}.
	 */
	@Test
	void testAdd() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		assertEquals(0, list.size());
		
		// add end
		list.add(0, "1st");
		assertEquals(1, list.size());
		assertEquals("1st", list.get(0));
		
		// add front
		list.add(0, "2nd");
		assertEquals(2, list.size());
		assertEquals("2nd", list.get(0));
		
		// add middle
		list.add(1, "3rd");
		assertEquals(3, list.size());
		assertEquals("3rd", list.get(1));
		
		// add another to middle
		list.add(2, "4th");
		assertEquals(4, list.size());
		assertEquals("4th", list.get(2));
		
		// index out bounds
		Exception indexOutOfBounds = assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "out of bounds"));
		assertEquals("Index out of bounds.", indexOutOfBounds.getMessage());
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(100, "out of bounds"));
		
		// null pointer
		Exception nullPointer = assertThrows(NullPointerException.class, () -> list.add(0, null));
		assertEquals("Item cannot be null.", nullPointer.getMessage());
		
		// duplicate
		Exception duplicate = assertThrows(IllegalArgumentException.class, () -> list.add(0, "1st"));
		assertEquals("Item already exists in the list.", duplicate.getMessage());
		
		// reached capacity
		Exception reachCapacity = assertThrows(IllegalArgumentException.class, () -> {
			list.add(0, "0th");
			list.add(0, "5th");
		});
		assertEquals("Reached capacity.", reachCapacity.getMessage());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#remove(int)}.
	 */
	@Test
	void testRemove() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(4);
		assertDoesNotThrow(() -> {
			list.add(0, "1st");
			list.add(1, "2nd");
			list.add(2, "3rd");
			list.add(3, "4th");
		});
		
		// remove middle
		assertEquals("3rd", list.remove(2));
		assertEquals(3, list.size());
		
		// remove front
		assertEquals("1st", list.remove(0));
		assertEquals(2, list.size());
		
		// remove end
		assertEquals("4th", list.remove(1));
		assertEquals(1, list.size());
		
		// index out bounds
		Exception indexOutOfBounds = assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertEquals("Index out of bounds.", indexOutOfBounds.getMessage());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(100));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#set(int, java.lang.Object)}.
	 */
	@Test
	void testSet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(3);
		assertDoesNotThrow(() -> {
			list.add(0, "1st");
			list.add(1, "2nd");
			list.add(2, "3rd");
		});
		
		// set middle
		assertEquals("1st", list.set(0, "1st - updated"));
		assertEquals("1st - updated", list.get(0));
		
		// set front
		assertEquals("2nd", list.set(1, "2nd - updated"));
		assertEquals("2nd - updated", list.get(1));
		
		// set end
		assertEquals("3rd", list.set(2, "3rd - updated"));
		assertEquals("3rd - updated", list.get(2));
		
		assertEquals(3, list.size());
		
		// index out bounds
		Exception indexOutOfBounds = assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "out of bounds."));
		assertEquals("Index out of bounds.", indexOutOfBounds.getMessage());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(100, "out of bounds"));
		
		// null pointer
		Exception nullPointer = assertThrows(NullPointerException.class, () -> list.set(0, null));
		assertEquals("Item cannot be null.", nullPointer.getMessage());
		
		// duplicate
		Exception duplicate = assertThrows(IllegalArgumentException.class, () -> list.set(0, "1st - updated"));
		assertEquals("Item already exists in the list.", duplicate.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#get(int)}.
	 */
	@Test
	void testGet() {
		// out of bounds
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(1);
		Exception indexOutOfBounds = assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertEquals("Index out of bounds.", indexOutOfBounds.getMessage());
		
		// an item
		assertDoesNotThrow(() -> list.add(0, "1st"));
		assertEquals("1st", list.get(0));
	}

}
