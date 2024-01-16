/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the ArrayList generic type linear data structure. The class specifically tests the
 * add, remove, get, and set functionality.
 * 
 * @author Maxim Shelepov
 */
class ArrayListTest {

	/**
	 * Test method for ArrayList constructor.
	 */
	@Test
	void testArrayList() {
		ArrayList<String> empty = new ArrayList<String>();
		assertEquals(0, empty.size());
	}
	
	/**
	 * Test method for add().
	 */
	@Test
	void testAdd() {
		ArrayList<String> list = new ArrayList<String>();
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
		
		// increase capacity
		String[] testArr = {"4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th"};
		assertDoesNotThrow(() -> {
			for (int i = 0; i < testArr.length; i++) {
				list.add(i, testArr[i]);
			}
		});
		assertEquals(3 + testArr.length, list.size());
		
	}
	
	/**
	 * Test method for remove().
	 */
	@Test
	void testRemove() {
		ArrayList<String> list = new ArrayList<String>();
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
	 * Test method for set().
	 */
	@Test
	void testSet() {
		ArrayList<String> list = new ArrayList<String>();
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
	 * Test method for get().
	 */
	@Test
	void testGet() {
		// out of bounds
		ArrayList<String> list = new ArrayList<String>();
		Exception indexOutOfBounds = assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertEquals("Index out of bounds.", indexOutOfBounds.getMessage());
		
		// an item
		assertDoesNotThrow(() -> list.add(0, "1st"));
		assertEquals("1st", list.get(0));
	}
}
