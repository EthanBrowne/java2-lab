package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Testing recursive  linked list class
 */
public class LinkedListRecursiveTest {
	@Test
	void testLinkedListRecursiveConstructor() {
		LinkedListRecursive<String> empty = new LinkedListRecursive<String>();
		assertEquals(0, empty.size());
		assertTrue(empty.isEmpty());
	}
	@Test
	void testAddToList() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("ItemOne");
		list.add("ItemTwo");
		assertEquals(2, list.size());
		list.add(0, "Item");
		list.add(1, "New Item");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(132, "OutofBounds");
        });
		assertEquals(list.get(1), "New Item");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(145);
        });
		assertThrows(NullPointerException.class, () -> {
			list.add(null);
        });
		assertThrows(NullPointerException.class, () -> {
			list.add(0, null);
        });
		
		assertThrows(IllegalArgumentException.class, () -> {
			list.add("ItemOne");
        });
		assertThrows(IllegalArgumentException.class, () -> {
			list.add(0, "ItemOne");
        });
		
		assertEquals(4, list.size());
		
	

	}
	
	@Test
	void testAddToMiddleOfList() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("ItemOne");
		list.add("ItemTwo");
		list.add("Item3");
		assertEquals(3, list.size());
		list.add(1, "New Item");
		assertEquals("ItemOne", list.get(0));
		assertEquals("ItemTwo", list.get(2));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(132, "OutofBounds");
        });
		assertEquals(list.get(1), "New Item");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(145);
        });
		assertThrows(NullPointerException.class, () -> {
			list.add(null);
        });
		assertThrows(NullPointerException.class, () -> {
			list.add(0, null);
        });
		
		assertThrows(IllegalArgumentException.class, () -> {
			list.add("ItemOne");
        });
		assertThrows(IllegalArgumentException.class, () -> {
			list.add(0, "ItemOne");
        });
		
		assertEquals(4, list.size());
		
	

	}
	
	@Test
	void testRemoveElementFromStart() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertFalse(list.remove("Nonexistent"));
		list.add("ItemOne");
		list.add("ItemTwo");
		list.add("ItemThree");
		assertTrue(list.remove("ItemOne"));
		assertFalse(list.remove("asdlfj"));
		assertFalse(list.remove(null));
		assertEquals(2, list.size());
		assertEquals("ItemTwo", list.get(0));
		
	}

	@Test
	void testRemoveElementFromMiddle() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertFalse(list.remove("Nonexistent"));
		list.add("ItemOne");
		list.add("ItemTwo");
		list.add("ItemThree");
		assertTrue(list.remove("ItemTwo"));
		assertFalse(list.remove("asdlfj"));
		assertFalse(list.remove(null));
		assertEquals(2, list.size());
		assertEquals("ItemOne", list.get(0));
		assertEquals("ItemThree", list.get(1));

		
	}
	
	@Test
	void testRemoveFromIndex() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("ItemOne");
		list.add("ItemTwo");
		list.add("ItemThree");
		list.add("Item4");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-12);
        });
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(14);
        });
		assertEquals("ItemTwo", list.remove(1));
		assertEquals("ItemOne", list.get(0));
		
		assertEquals(3, list.size());
		
		
	}
	@Test
	void testRemoveFromStart() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("ItemOne");
		list.add("ItemTwo");
		list.add("ItemThree");
		list.add("Item4");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-12);
        });
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(14);
        });
		assertEquals("ItemOne", list.remove(0));
		assertEquals("ItemTwo", list.get(0));
		
		assertEquals(3, list.size());
		
		
	}
	
	@Test
	void testSetMethod() {
		 LinkedListRecursive<String> list = new LinkedListRecursive<>();
	        list.add("ItemOne");
	        list.add("ItemTwo");
	        list.add("ItemThree");

	        // Test setting an element at a valid index
	        String previousElement = list.set(1, "NewValue");
	        assertEquals("ItemTwo", previousElement);
	        assertEquals("NewValue", list.get(1));

	        // Test setting an element at the first index
	        previousElement = list.set(0, "FirstItem");
	        assertEquals("ItemOne", previousElement);
	        assertEquals("FirstItem", list.get(0));

	        // Test setting an element at the last index
	        previousElement = list.set(2, "LastItem");
	        assertEquals("ItemThree", previousElement);
	        assertEquals("LastItem", list.get(2));

	        // Test setting an element at an invalid index (expecting IndexOutOfBoundsException)
	        assertThrows(IndexOutOfBoundsException.class, () -> {
	            list.set(3, "InvalidIndex");
	        });

	        // Test setting an element at a negative index (expecting IndexOutOfBoundsException)
	        assertThrows(IndexOutOfBoundsException.class, () -> {
	            list.set(-1, "NegativeIndex");
	        });
	}
	
	
	

}
