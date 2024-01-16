package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class tests the functionality of the SortedList class. Includes test
 * methods for multiple functions such as adding, removing, clearing, checking
 * for emptiness, checking for containment, and testing equality and hash code.
 *
 * @author Saloni Kumari
 */
public class SortedListTest {

	/**
	 * Tests method for the constructor SortedList() of the SortedList class. Checks
	 * if the constructor initializes an empty SortedList correctly.
	 */
	@Test
	public void testSortedList() {

		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("apple"));

		for (int i = 1; i <= 11; i++) {
			list.add("element" + i);
		}
		assertEquals(11, list.size());

		for (int i = 1; i <= 11; i++) {
			assertTrue(list.contains("element" + i));
		}

		list.add("anotherelement");

		assertEquals(12, list.size());

		// Check that the list contains the added elements
		for (int i = 1; i <= 11; i++) {
			assertTrue(list.contains("element" + i));
		}

		// Checking that the list is empty when using an incorrect type
		SortedList<Integer> integerList = new SortedList<Integer>();
		assertEquals(0, integerList.size());
		assertTrue(integerList.isEmpty());

	}

	/**
	 * This method adds an element to the front. This method adds an element to the
	 * end. This method adds an element to the middle.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		assertTrue(list.add("apple"));
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		assertTrue(list.add("cherry"));
		assertEquals(3, list.size());
		assertEquals("cherry", list.get(2));

		assertTrue(list.add("grape"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("cherry", list.get(2));
		assertEquals("grape", list.get(3));

		try {
			list.add(null);
			fail("Should throw NullPointerException");
		} catch (Exception e) {
			System.out.print("");
		}

		try {
			list.add("banana");
			fail("Should throw an IllegalArgumentException for duplicate element");
		} catch (IllegalArgumentException e) {
			System.out.print("");
		}

	}

	/**
	 * This method gets an element from an empty list adds some elements to the
	 * list, and getting an element at an index less than 0 gets an element at index
	 * size().
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		try {
			list.get(0);
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");

		}

		list.add("apple");
		list.add("banana");
		list.add("cherry");

		try {
			list.get(-1);
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");
		}

		try {
			list.get(list.size());
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");
		}

	}

	/**
	 * This method removes an element from an empty list. Adds at least four
	 * elements and removing an element at an index less than 0. Removes an element
	 * at index size() Removes middle, last, first, and last element.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		try {
			list.remove(0);
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");
		}

		list.add("apple");
		list.add("banana");
		list.add("cherry");
		list.add("orange");

		try {
			list.remove(-1);
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");
		}

		try {
			list.remove(list.size());
			fail("Should throw an IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			System.out.print("");
		}

		String removedElement1 = list.remove(1);
		assertEquals("banana", removedElement1);
		assertEquals(3, list.size());
		assertFalse(list.contains("banana"));

		String removedElement2 = list.remove(list.size() - 1);
		assertEquals("orange", removedElement2);
		assertEquals(2, list.size());
		assertFalse(list.contains("orange"));

		String removedElement3 = list.remove(0);
		assertEquals("apple", removedElement3);
		assertEquals(1, list.size());
		assertFalse(list.contains("apple"));

		String removedElement4 = list.remove(0);
		assertEquals("cherry", removedElement4);
		assertEquals(0, list.size());
		assertFalse(list.contains("cherry"));
	}

	/**
	 * This method gets the index of an element in an empty list. Adds some elements
	 * and getting the index of elements in various positions in the list. Gets the
	 * index of elements not in the list Gets the index of a null element.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(-1, list.indexOf("element"));

		list.add("apple");
		list.add("banana");
		list.add("cherry");

		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("banana"));
		assertEquals(2, list.indexOf("cherry"));

		assertEquals(-1, list.indexOf("grape"));
		assertEquals(-1, list.indexOf("orange"));

		// Tests checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));

	}

	/**
	 * This method adds at least one element to the list Clears the list Checks that
	 * the list is empty.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		list.add("apple");
		list.add("banana");

		list.clear();

		assertTrue(list.isEmpty());
	}

	/**
	 * This method checks a new list is empty Adds an element Checks that the list
	 * is now not empty.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		assertTrue(list.isEmpty());

		list.add("apple");

		assertFalse(list.isEmpty());

	}

	/**
	 * This method checks if an empty list contains an element Adds elements and
	 * checks if the list contains an element in the list Checks if a list contains
	 * an element not in the list.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		assertFalse(list.contains("apple"));

		list.add("apple");
		list.add("banana");
		list.add("cherry");

		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertTrue(list.contains("cherry"));
		assertFalse(list.contains("grape"));
		assertFalse(list.contains("mango"));

	}

	/**
	 * This method makes two lists the same and one list different Tests for
	 * equality and non-equality.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("cherry");

		list2.add("apple");
		list2.add("banana");
		list2.add("cherry");

		list3.add("apple");
		list3.add("grape");
		list3.add("cherry");

		assertEquals(list1, list2);
		assertNotEquals(list1, list3);
	}

	/**
	 * This method makes two lists the same and one list different Tests for the
	 * same and different hashCodes.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("cherry");

		list2.add("apple");
		list2.add("banana");
		list2.add("cherry");

		list3.add("apple");
		list3.add("grape");
		list3.add("cherry");

		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());

	}

}
