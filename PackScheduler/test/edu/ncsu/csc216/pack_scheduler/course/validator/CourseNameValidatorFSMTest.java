package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidatorFSM, a TS provided class which validates a provided Course name. This class specifically tests the one method
 * which takes a Course name input either returning a boolean value or throwing a custom exception for invalid transition.
 * 
 * @author Nicolas Brechar
 */
class CourseNameValidatorFSMTest {
	/**
	 * Tests course names and exceptions.
	 * @throws InvalidTransitionException Custom Exception
	 */
	@Test
	void testIsValid() throws InvalidTransitionException {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		assertTrue(c.isValid("CSC116"));
		assertTrue(c.isValid("CSCA116"));
		assertTrue(c.isValid("ACSC116"));
		assertTrue(c.isValid("CS116"));
		
		Exception e = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("1CSC116"));
		assertEquals("Course name must start with a letter.", e.getMessage());
		
		Exception e2 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("C1SC116"));
		assertEquals("Course name must have 3 digits.", e2.getMessage());
		
		Exception e3 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CSCCC116"));
		assertEquals("Course name cannot start with more than 4 letters.", e3.getMessage());
		
		Exception e4 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CS.,C116"));
		assertEquals("Course name can only contain letters and digits.", e4.getMessage());
		
		Exception e5 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CSC22W"));
		assertEquals("Course name must have 3 digits.", e5.getMessage());
		
		Exception e6 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CSC1116"));
		assertEquals("Course name can only have 3 digits.", e6.getMessage());
		
		Exception e7 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CSC116WW"));
		assertEquals("Course name can only have a 1 letter suffix.", e7.getMessage());
		
		Exception e8 = assertThrows(InvalidTransitionException.class, 
				() -> c.isValid("CSC116W1"));
		assertEquals("Course name cannot contain digits after the suffix.", e8.getMessage());
	}
}
