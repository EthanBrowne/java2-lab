/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class tests the LinkedQueue implementation by enqueue items to the linked list and dequeuing.
 */
class LinkedQueueTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#enqueue(java.lang.Object)}.
	 */
	@Test
	void testEnqueue() {
		String str = "Hello";
		String str2 = "World";
		
		LinkedQueue<String> aq = new LinkedQueue<String>(4);
		aq.enqueue(str);
		assertEquals(1, aq.size());
		aq.enqueue(str2);
		assertEquals(2, aq.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#dequeue()}.
	 */
	@Test
	void testDequeue() {
		String str = "Hello";
		String str2 = "World";
		
		LinkedQueue<String> aq = new LinkedQueue<String>(3);
		aq.enqueue(str);
		aq.enqueue(str2);
		
		assertEquals(str, aq.dequeue());
		assertEquals(str2, aq.dequeue());
		assertEquals(0, aq.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		LinkedQueue<String> aq = new LinkedQueue<String>(0);
		assertTrue(aq.isEmpty());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#size()}.
	 */
	@Test
	void testSize() {
		String str = "Hello";
		String str2 = "World";
		
		LinkedQueue<String> aq = new LinkedQueue<String>(3);
		aq.enqueue(str);
		assertEquals(1, aq.size());
		aq.enqueue(str2);
		assertEquals(2, aq.size());
		aq.dequeue();
		assertEquals(1, aq.size());
		aq.dequeue();
		assertEquals(0, aq.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		LinkedQueue<String> aq = new LinkedQueue<String>(3);
		assertEquals(3, aq.getCapacity());
		aq.setCapacity(1);
		assertEquals(1, aq.getCapacity());
	}

}
