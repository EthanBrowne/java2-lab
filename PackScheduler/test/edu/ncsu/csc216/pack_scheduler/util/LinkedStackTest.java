/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Testing Linked stack class
 */
class LinkedStackTest {

	/**
	 * Test method for push method of linked stack class
	 */
	@Test
	void testPush() {
		Stack<Integer> s = new LinkedStack<Integer>(3);
		
		s.push(1);
		s.push(2);
		s.push(24);
		assertThrows(IllegalArgumentException.class, () -> {
        	s.push(32);
		});
		assertEquals(3, s.size());
		
	}

	/**
	 * Test method for pop method
	 */
	@Test
	void testPop() {
		Stack<Integer> s = new LinkedStack<Integer>(3);
		assertThrows(EmptyStackException.class, () -> {
        	s.pop();
		});
		s.push(1);
		s.push(2);
		s.push(24);
		assertEquals(24, s.pop());
		assertEquals(2, s.pop());
		assertEquals(1, s.size());

	}

	/**
	 * Test method for test.isEmpty()method 
	 */
	@Test
	void testIsEmpty() {
		Stack<Integer> s = new LinkedStack<Integer>(3);
		assertTrue(s.isEmpty());
		s.push(12);
		assertFalse(s.isEmpty());
	}


	/**
	 * Test method for set capacity method
	 */
	@Test
	void testSetCapacity() {
		Stack<Integer> s = new LinkedStack<Integer>(3);
		s.push(1);
        s.push(2);
        s.push(3);
        assertThrows(IllegalArgumentException.class, () -> {
        	s.setCapacity(-1);
		});
        s.setCapacity(5);
        assertEquals(3, s.size());
        
        
	}

}
