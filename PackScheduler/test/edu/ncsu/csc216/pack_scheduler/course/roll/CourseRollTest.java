/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll class, a student provided class which validates a provided course roll. This class tests each method for adding,
 * setting, and removing courses from the course roll.
 */
class CourseRollTest {

	/**
	 * Tests the default constructor and tests the maximum and minimum for setting the enrollment cap.
	 */
	@Test
	void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		Course c2 = null;
		
		CourseRoll cr = new CourseRoll(c, 50);
		
		assertThrows(IllegalArgumentException.class, 
				() -> new CourseRoll(c2, 50));
		
		assertEquals(50, cr.getEnrollmentCap());
		assertEquals(50, cr.getOpenSeats());

		assertThrows(IllegalArgumentException.class, 
				() -> cr.setEnrollmentCap(1));
		assertThrows(IllegalArgumentException.class, 
			() -> cr.setEnrollmentCap(251));
	}
	
	/**
	 * Tests if the enrollment cap was set correctly.
	 */
	@Test
	void testSetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		
		CourseRoll roll = new CourseRoll(c, 10);
		roll.setEnrollmentCap(11);
		assertEquals(11, roll.getEnrollmentCap());
	}
	
	/**
	 * Tests course enrollment for exceptions when adding a course to the course roll.
	 */
	@Test
	void testCourseEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		
		CourseRoll cr = new CourseRoll(c, 50);
		
		Student s1 = new Student("Josh", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s2 = new Student("Josh", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		
		assertTrue(cr.canEnroll(s1));
		cr.enroll(s1);
		assertEquals(49, cr.getOpenSeats());
		
		Student s3 = null;
		assertThrows(IllegalArgumentException.class, 
				() -> cr.enroll(s3));
		assertThrows(IllegalArgumentException.class, 
				() -> cr.enroll(s2));
		
		CourseRoll cr2 = new CourseRoll(c, 10);
		Student s4 = new Student("Josh1", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s5 = new Student("Josh2", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s6 = new Student("Josh3", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s7 = new Student("Josh4", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s8 = new Student("Josh5", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s9 = new Student("Josh6", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s10 = new Student("Josh7", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s11 = new Student("Josh8", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s12 = new Student("Josh9", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s13 = new Student("Josh10", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s14 = new Student("Josh11", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s15 = new Student("Josh12", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s16 = new Student("Josh16", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		cr2.enroll(s4);
		cr2.enroll(s5);
		cr2.enroll(s6);
		cr2.enroll(s7);
		cr2.enroll(s8);
		cr2.enroll(s9);
		cr2.enroll(s10);
		cr2.enroll(s11);
		cr2.enroll(s12);
		cr2.enroll(s13);
		cr2.enroll(s14);
		cr2.enroll(s15);
		cr2.drop(s7);
		cr2.enroll(s16);
		cr2.drop(s16);

		assertEquals(cr2.getOpenSeats(), 0);
		assertThrows(IllegalArgumentException.class, 
				() -> cr2.enroll(s14));
	}
	
	/**
	 * Tests dropping courses from the course roll.
	 */
	@Test
	void testCourseDrop() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		
		CourseRoll cr = new CourseRoll(c, 50);
		
		Student s1 = new Student("Josh", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		
		Student s2 = null;

		cr.enroll(s1);
		cr.drop(s1);
		//cr.enroll(s3);
		//cr.drop(s3);;
		
		assertEquals(50, cr.getOpenSeats());
		
		assertThrows(IllegalArgumentException.class, 
				() -> cr.drop(s2));
		assertThrows(IllegalArgumentException.class, 
				() -> cr.enroll(s2));
		
		CourseRoll cr2 = new CourseRoll(c, 10);
		Student s4 = new Student("Josh1", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s5 = new Student("Josh2", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s6 = new Student("Josh3", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s7 = new Student("Josh4", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s8 = new Student("Josh5", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s9 = new Student("Josh6", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s10 = new Student("Josh7", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s11 = new Student("Josh8", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s12 = new Student("Josh9", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s13 = new Student("Josh10", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s14 = new Student("Josh11", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s15 = new Student("Random1", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s16 = new Student("Random2", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		Student s17 = new Student("Random3", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		cr2.enroll(s4);
		cr2.enroll(s5);
		cr2.enroll(s6);
		cr2.enroll(s7);
		cr2.enroll(s8);
		cr2.enroll(s9);
		cr2.enroll(s10);
		cr2.enroll(s11);
		cr2.enroll(s12);
		cr2.enroll(s13);
		cr2.enroll(s14);
		cr2.enroll(s15);
		cr2.enroll(s16);
		cr2.enroll(s17);
		assertEquals(4, cr2.getNumberOnWaitlist());
		cr2.drop(s17);
		cr2.drop(s16);
		assertEquals(2, cr2.getNumberOnWaitlist());
		cr2.drop(s13);
		assertEquals(1, cr2.getNumberOnWaitlist());
		cr2.drop(s6);
		assertEquals(0, cr2.getNumberOnWaitlist());
		cr2.drop(s15);
		
		 //cr2.drop(s15);
		
	}
	
	/**
	 * Tests the method that checks whether a course can be enrolled or not.
	 */
	@Test
	void testCanEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		
		CourseRoll cr = new CourseRoll(c, 50);
		
		Student s1 = new Student("Josh", "Wilkins", "jwilk",
				"jwilk@ncsu.edu", "123");
		
		assertTrue(cr.canEnroll(s1));
	}

}
