/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * The ActivityTest class tests Activity objects, specifically the comparable interface to spot conflicts.
 * The test class provides two methods to test if two activities are conflicting:
 * <ul>
 * <li>the <code>testCheckConflict()</code> checks for non-conflicting activities.
 * <li>the <code>testCheckConflictWithConflict()</code> checks for conflicting activities.
 * </ul>
 * 
 * Note: the <code>testCheckConflictWithConflict()</code> checks for the CustomException if conflict.
 * 
 * @author Maxim Shelepov
 */
class ActivityTest {

	/**
	 * Tests non-conflicting Activities. Expects the custom exception to not be thrown.
	 * Expects to be non-conflicting due to: non-matching meeting day and matching day but not matching time or if arranged meeting days.
	 */
	@Test
	public void testCheckConflict() {
		// Not conflicting by...
		// Day
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "TH", 1330, 1445);
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	    
	    // Time
	    Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1025, 1230);
	    Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "TW", 1255, 1345);
	    
	    assertDoesNotThrow(() -> a3.checkConflict(a4));
	    assertDoesNotThrow(() -> a4.checkConflict(a3));
	    
	    // If Arranged
	    Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "A", 0, 0);
	    Activity a6 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "A", 0, 0);
	    
	    assertDoesNotThrow(() -> a5.checkConflict(a6));
	    assertDoesNotThrow(() -> a6.checkConflict(a5));
	}
	
	/**
	 * Test conflicting Activities. Expects for CustomException to be thrown with the default message.
	 * Expects to be conflicting due to: left overlap, right overlap, center overlap, edge overlap.
	 */
	@Test
	public void testCheckConflictWithConflict() {
		// Overlap...
		// Left
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "M", 1300, 1425);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    // Right
	    Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1330, 1445);
	    Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "M", 1430, 1555);
		
	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e3.getMessage());
		
	    Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
	    assertEquals("Schedule conflict.", e4.getMessage());
	    
	    // Center
	    Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1200, 1430);
	    Activity a6 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "M", 1330, 1400);
		
	    Exception e5 = assertThrows(ConflictException.class, () -> a5.checkConflict(a6));
	    assertEquals("Schedule conflict.", e5.getMessage());
		
	    Exception e6 = assertThrows(ConflictException.class, () -> a6.checkConflict(a5));
	    assertEquals("Schedule conflict.", e6.getMessage());
	    
	    // Edge
	    Activity a7 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "MW", 1330, 1445);
	    Activity a8 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 50, "M", 1445, 1555);
		
	    Exception e7 = assertThrows(ConflictException.class, () -> a7.checkConflict(a8));
	    assertEquals("Schedule conflict.", e7.getMessage());
		
	    Exception e8 = assertThrows(ConflictException.class, () -> a8.checkConflict(a7));
	    assertEquals("Schedule conflict.", e8.getMessage());
	}

}
