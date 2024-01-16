package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests for Student object data class. 
 * 
 * The class tests all the object instantiation scenarios with the Student object constructor. 
 * As well as, the setters for the class, testing both the validation failure and successes. The class also
 * goes to test the equals, toString, and hashCode methods.
 * 
 * The class encapsulates some static dummy data fields for a plausible student and Student object.
 * 
 * @author Eswar Talasila
 */
class StudentTest {
	/** Student First Name*/
	private static final String FIRST_NAME = "ESWAR";
	/** Student Last Name*/
	private static final String LAST_NAME = "TALASILA";
	/** Student ID */
	private static final String UNITY_ID = "ectalasi";
	/** Student Email ID*/
	private static final String EMAIL_ID = "ectalasi@ncsu.edu";
	/** Student Password  id */
	private static final String PASSWORD = "12345";
	/** Max credits*/
	private static final int MAX_CREDITS = 12;
	/** Default credits*/
	private static final int DFLT_CREDITS = 18;	

	/**
	 * Test the Student object with all fields present.
	 */
	@Test
	public void testStudentConstructorValid1() {
		// Test a valid construction
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS),
				"Should not throw exception");

		assertAll("Student",
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(UNITY_ID, s.getId(), "incorrect unity ID"),
				() -> assertEquals(EMAIL_ID, s.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "incorrect password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "incorrect credits")
				);
	}

	/**
	 * Test the Student object using default max credits (missing max credits).
	 */
	@Test
	public void testStudentConstructorValid2() {
		// Test a valid construction
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD),
				"Should not throw exception");

		assertAll("Student", 
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"), 
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(UNITY_ID, s.getId(), "incorrect unity ID"), 
				() -> assertEquals(EMAIL_ID, s.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "incorrect password"),
				() -> assertEquals(DFLT_CREDITS, s.getMaxCredits(), "incorrect credits")				
				);
	}

	/**
	 * Test invalid first name for Student object.
	 */
	@Test
	public void testStudentConstructorInvalid1() {
		// Test a valid construction
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("", LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD),
				"Should not throw exception");

		assertEquals("Invalid first name", e1.getMessage(), "Incorrect exception thrown with invalid first name - ");
	}

	/**
	 * Test invalid id for Student object.
	 */
	@Test
	public void testStudentConstructorInvalid2() {
		// Test a valid construction
		//Checking for invalid Id (private method setId)
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, "", EMAIL_ID, PASSWORD),
				"Should not throw exception");

		assertEquals("Invalid id", e1.getMessage(), "Incorrect exception thrown with invalid id - ");

	}

	/**
	 * Test valid setter for Student object first name.
	 */
	@Test
	public void testSetFirstNameValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		s.setFirstName("John");
		assertEquals("John", s.getFirstName(), "incorrect first name");
	}


	/**
	 * Test setter invalid for Student object  first name.
	 */
	@Test
	public void testSetFirstNameInValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(null) ); 
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"); //Check that first name didn't change

	} 

	/**
	 * Test setter valid for Student object last name.
	 */
	@Test
	public void testSetLastNameValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		s.setLastName("Doe");
		assertEquals("Doe", s.getLastName(), "incorrect last name");	
	}

	/**
	 * Test setter invalid for Student object last name.
	 */
	@Test
	public void testSetLastNameInValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(null) ); 
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"); //Check that last name didn't change	
	}	

	/**
	 * Test setter valid for Student object email.
	 */
	@Test
	public void testSetEmailValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		s.setEmail("ectalasi1@ncsu.edu");
		assertEquals("ectalasi1@ncsu.edu", s.getEmail(), "incorrect email");		
	}

	/**
	 * Test setter invalid for Student object email (null parameter value).
	 */
	@Test
	public void testSetEmailInValid1() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail(null) ); 
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals(EMAIL_ID, s.getEmail(), "incorrect email"); //Check that email didn't change	
	}	

	/**
	 * Test setter invalid for Student object email (wrong email formatting).
	 */
	@Test
	public void testSetEmailInValid2() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("ectalasiNcsu.edu")); 
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals(EMAIL_ID, s.getEmail(), "incorrect email"); //Check that email didn't change	
	}	


	/**
	 * Test setter valid for Student object password.
	 */
	@Test
	public void testSetPasswordValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		s.setPassword("xyz123");
		assertEquals("xyz123", s.getPassword(), "incorrect password");		
	}

	/**
	 * Test setter invalid for Student object password.
	 */
	@Test
	public void testSetPasswdInValid() {
		User s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(null) ); 
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals(PASSWORD, s.getPassword(), "incorrect password"); //Check that email didn't change	
	}	


	/**
	 * Test setter valid for Student object max credits.
	 */
	@Test
	public void testSetMaxCreditsValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		s.setMaxCredits(10);
		assertEquals(10, s.getMaxCredits(), "incorrect credits");			
	}

	/**
	 * Test setter invalid for Student object max credits.
	 */
	@Test
	public void testSetMaxCreditsInvalid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setMaxCredits(25) ); 
		assertEquals("Invalid max credits", e1.getMessage()); //Check correct exception message
		assertEquals(MAX_CREDITS, s.getMaxCredits(), "invlid credits"); //Check that email didn't change	
	}	

	/**
	 * Test Student object equals() method.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s3 = new Student("DIFFERENT", LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "DIFFERENT", UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "DIFFERENT", EMAIL_ID, PASSWORD, MAX_CREDITS);


		assertEquals(s1.toString(), s2.toString());
		assertEquals(s2.toString(), s1.toString());

		assertNotEquals(s1, s3);
		assertNotEquals(s1, s3);
		assertNotEquals(s1, s4);
		assertNotEquals(s1, s5);
		assertNotEquals(s1, s5);

		assertEquals(s1, s2, "s1 and s2 should be equal");
		assertEquals(s2, s1, "s2 and s1 should be equal");
		s2.setEmail("different@ncsu.edu");
		assertNotEquals(s1, s2, "s1 and s2 should not be equal");
		assertNotEquals(s2, s1, "s2 and s1 should not be equal");

	}

	/**
	 * Test Student object hashCode() method.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s3 = new Student("DIFFERENT", LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, LAST_NAME, "DIFFERENT", EMAIL_ID, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, 12);

		assertEquals(s1.hashCode(), s2.hashCode());

		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s2.hashCode(), s3.hashCode());
		assertNotEquals(s2.hashCode(), s4.hashCode());
		assertNotEquals(s4.hashCode(), s5.hashCode());
		assertNotEquals(s3.hashCode(), s5.hashCode());
		assertNotEquals(s3.hashCode(), s5.hashCode());

	}	

	/**
	 * Test Student toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, UNITY_ID, EMAIL_ID, PASSWORD, MAX_CREDITS);
		String str1 = "ESWAR,TALASILA,ectalasi,ectalasi@ncsu.edu,12345,12";
		assertEquals (s1.toString(), str1);
	}
	
	/**
	 * Tests Student compareTo() method.
	 */
	@Test
	public void testCompareTo() {
		Student  student1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password");
		Student  student2 = new Student("Alice", "Smith", "asmith", "asmith@ncsu.edu", "password");
		Student  student3 = new Student("Bob", "Smith", "bsmith", "bsmith@ncsu.edu", "password");
		assertTrue(student1.compareTo(student2) < 0); 
		assertTrue(student2.compareTo(student3) < 0); 
		assertTrue(student1.compareTo(student3) < 0); 
		assertEquals(0, student1.compareTo(student1)); 
	}
	
	/**
	 * Tests Student canAdd() method.
	 */
	@Test
	public void testCanAdd() {
		//note, default max credits is 18
		Student  student1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password");
		Course c1 = new Course("CSC217", "Lab Java", "001", 1, "sbob", 10, "W", 1020, 1230);
		Course c2 = new Course("CSC230", "CLanguage", "004", 4, "rhill", 10, "MF", 1030, 1130);
		Course c3 = new Course("CSC333", "Automata", "003", 4, "ebob", 10, "MW", 845, 945);
		//invalid
		Course c4 = new Course("CSC332", "Automata", "003", 3, "ebob", 10, "MW", 1030, 1130);
		
		Course c5 = new Course("CSC336", "Automata", "003", 4, "ebob", 10, "TH", 845, 945);
		Course c6 = new Course("CSC337", "Automata", "003", 4, "ebob", 10, "TH", 1030, 1130);
		
		Course c7 = new Course("CSC338", "Automata", "003", 3, "ebob", 10, "TH", 330, 430);
		
		
		//course is null
		assertFalse(student1.canAdd(null));
		
		//course is already in schedule
		student1.getSchedule().addCourseToSchedule(c1);
		assertFalse(student1.canAdd(c1));
		
		//course has a conflict
		assertFalse(student1.canAdd(c4));
		
		//course exceeds max credits
		student1.getSchedule().addCourseToSchedule(c2);
		student1.getSchedule().addCourseToSchedule(c3);
		student1.getSchedule().addCourseToSchedule(c5); 
		student1.getSchedule().addCourseToSchedule(c6); 
		
		//course exceeds max credits
		assertFalse(student1.canAdd(c7));
	}
	
	
}
