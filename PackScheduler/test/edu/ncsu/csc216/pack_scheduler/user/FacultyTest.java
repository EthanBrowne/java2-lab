package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FacultyTest {

	@Test
	void testHashCode() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		Faculty f2 = new Faculty("Charles", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		assertEquals(f.hashCode(), f.hashCode());
		assertNotEquals(f.hashCode(), f2.hashCode());
	}

	@Test
	void testEqualsObject() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		Faculty f2 = new Faculty("Charles", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		Faculty f3 = new Faculty("John", "Doe", "xyz", "john.doe@example.com", "CS", 3);
		assertTrue(f.equals(f));  // Reflexivity
		assertFalse(f.equals(f2));
		assertFalse(f.equals(f3));

		assertTrue(f2.equals(f2));  // Reflexivity
		assertFalse(f2.equals(f));
		assertFalse(f2.equals(f3));
		    
		assertTrue(f3.equals(f3));  // Reflexivity
		assertFalse(f3.equals(f));
		assertFalse(f3.equals(f2));

	
		
		
	}

	@Test
	void testFaculty() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		assertEquals(f.getFirstName(), "Ethan");
		assertThrows(IllegalArgumentException.class, () -> {
			f.setMaxCourses(0);
	        });
		
		assertThrows(IllegalArgumentException.class, () -> {
			f.setMaxCourses(15);
		});
	}

	@Test
	void testGetMaxCourses() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		assertEquals(2, f.getMaxCourses());
	}

	@Test
	void testSetMaxCourses() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		f.setMaxCourses(3);
		assertEquals(3, f.getMaxCourses());
	}

	@Test
	void testToString() {
		Faculty f = new Faculty("Ethan", "Browne", "awdaw", "akldnwla@kdn.awdawd", "IDK", 2);
		assertEquals("Ethan,Browne,awdaw,akldnwla@kdn.awdawd,IDK,2", f.toString());
	}
	

		
}
