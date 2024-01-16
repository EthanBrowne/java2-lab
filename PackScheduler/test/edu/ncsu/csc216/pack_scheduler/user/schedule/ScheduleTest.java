/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test class for Schedule object. Tests the objects get, add, remove, and reset functionality for schedule. In addition to
 * setter behavior for title.
 */
class ScheduleTest {

	/**
	 * Creates a new schedule and tests the default schedule message.
	 */
	@Test
	void testConstructor() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
		String str[][] = s.getScheduledCourses();
		assertEquals(0, str.length);
	}
	
	/**
	 * Adds a course to the schedule and determines if it was successfully added.
	 */
	@Test
	void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 3, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 3, "ebob", 10, "MW", 845, 945);
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		String str[][] = s.getScheduledCourses();
		assertEquals(3, str.length);
		assertEquals("CSC217", str[0][0]);
		assertEquals("CSC230", str[1][0]);
		assertEquals("CSC333", str[2][0]);
	}
	
	/*
	 * Tests getScheduledCredits method. 
	 */
	@Test
	void testGetScheduleCredits() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 3, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 3, "ebob", 10, "MW", 845, 945);
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		
		assertEquals(7, s.getScheduleCredits());
		
	}
	
	/*
	 * Tests canAdd() method. 
	 */
	@Test
	void testCanAdd() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 3, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 3, "ebob", 10, "MW", 845, 945);
		Course c4 = new Course("CSC217", "Lab Java", "002", 1, "ebob", 10, "MW", 845, 945);
		Course c5 = new Course("CSC316", "Data Structures", "003", 3, "ebob", 10, "MW", 1030, 1130);
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		
		//test null course
		assertFalse(s.canAdd(null));
		
		//test course already in schedule different section
		assertFalse(s.canAdd(c4));
		
		//test course already in schedule same section
		assertFalse(s.canAdd(c4));
		
		//test time conflict, one day
		assertFalse(s.canAdd(c5));
		
		//test valid addition
		assertTrue(s.canAdd(c3));
		
	}
	
	/**
	 * Removes a course from the schedule and determines if it was removed from the schedule.
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 3, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 3, "ebob", 10, "MW", 845, 945);
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		s.removeCourseFromSchedule(c3);
		String str[][] = s.getScheduledCourses();
		assertEquals(2, str.length);
	}
	
	/**
	 * Resets the schedule and determines if default message is shown indicating a new schedule.
	 */
	@Test
	void testResetSchedule() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 3, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 3, "ebob", 10, "MW", 845, 945);
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		s.resetSchedule();
		String str[][] = s.getScheduledCourses();
		assertEquals(0, str.length);
		assertEquals("My Schedule", s.getTitle());
	}
	
	/**
	 * Creates a schedule and checks if all the schedules are returned.
	 */
	@Test
	void testGetScheduledCourses() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		s.addCourseToSchedule(c1);
		String str[][] = s.getScheduledCourses();
		assertEquals(1, str.length);
		assertEquals("CSC217", str[0][0]);
		assertEquals("001", str[0][1]);
		assertEquals("Lab Java", str[0][2]);
		assertEquals("W 10:20AM-12:30PM", str[0][3]);
	}
	
	/**
	 * Sets titles and determines if the titles were added successfully.
	 */
	@Test
	void testSetTitle() {
		Schedule s = new Schedule();
		s.setTitle("New Course");
		assertEquals("New Course", s.getTitle());
	}
}
