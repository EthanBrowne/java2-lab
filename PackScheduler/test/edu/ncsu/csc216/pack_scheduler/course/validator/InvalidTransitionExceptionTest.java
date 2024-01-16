/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the InvalidTransitionException class.
 * 
 * @author Nicolas Brechar
 * @author Maxim Shelepov
 */
class InvalidTransitionExceptionTest {
	/**
	 * Test method for creating a custom exception.
	 */
	@Test
	void testInvalidTransitionException() {
		Exception eMessage = new InvalidTransitionException("Error");
		assertEquals("Error", eMessage.getMessage());
		
	}

	/**
	 * Test method for creating the default message exception.
	 */
	@Test
	void testInvalidTransitionExceptionString() {
		Exception e = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", e.getMessage());
	}
}
