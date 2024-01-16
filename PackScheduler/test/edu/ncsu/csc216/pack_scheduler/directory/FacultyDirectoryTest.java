/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the Faculty Directory Class
 */
class FacultyDirectoryTest {
	/** The first name of the faculty member */
	private static final String FIRST_NAME = "Harper";
	/** The last name of the faculty member */
	private static final String LAST_NAME = "Sandman";
	/** The id of the faculty member */
	private static final String ID = "hsandman";
	/** The email of the faculty member */
	private static final String EMAIL = "hsandman@ncsu.edu";
	/** The password of the faculty member */
	private static final String PASSWORD = "SandMoney$$$";
	/** The max courses of the faculty member */
	private static final int MAX_COURSES = 2;
	
	/** The first name of the faculty member */
	private static final String FIRST_NAME2 = "Anthony";
	/** The last name of the faculty member */
	private static final String LAST_NAME2 = "Fink";
	/** The id of the faculty member */
	private static final String ID2 = "afink";
	/** The email of the faculty member */
	private static final String EMAIL2 = "afink.edu";
	/** The password of the faculty member */
	private static final String PASSWORD2 = "TreeHugger42";
	/** The max courses of the faculty member */
	private static final int MAX_COURSES2 = 3;
	

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#FacultyDirectory()}.
	 */
	@Test
	void testFacultyDirectory() {
		FacultyDirectory f = new FacultyDirectory();
		assertEquals(0, f.getFacultyDirectory().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#newFacultyDirectory()}.
	 */
	@Test
	void testNewFacultyDirectory() {
		FacultyDirectory f = new FacultyDirectory();
		assertEquals(0, f.getFacultyDirectory().length);
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		assertEquals(1, f.getFacultyDirectory().length);
		f.newFacultyDirectory();
		assertEquals(0, f.getFacultyDirectory().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#loadFacultyFromFile(java.lang.String)}.
	 */
	@Test
	void testLoadFacultyFromFile() {
		FacultyDirectory f = new FacultyDirectory();
		String validFile = "test-files/faculty_records.txt";
		assertDoesNotThrow(() -> f.loadFacultyFromFile(validFile));
		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> f.loadFacultyFromFile(""));
		assertEquals("Unable to read file ", exception.getMessage());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#addFaculty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	void testAddFaculty() {
		FacultyDirectory f = new FacultyDirectory();
		assertEquals(0, f.getFacultyDirectory().length);
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		assertEquals(1, f.getFacultyDirectory().length);
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> f.addFaculty(FIRST_NAME2, LAST_NAME2, ID2, EMAIL2, PASSWORD2, "Im a Goober", MAX_COURSES2));
		assertEquals("Passwords do not match", e.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#removeFaculty(java.lang.String)}.
	 */
	@Test
	void testRemoveFaculty() {
		FacultyDirectory f = new FacultyDirectory();
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		assertEquals(1, f.getFacultyDirectory().length);
		assertTrue(f.removeFaculty(ID));
		assertEquals(0, f.getFacultyDirectory().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#getFacultyDirectory()}.
	 */
	@Test
	void testGetFacultyDirectory() {
		FacultyDirectory f = new FacultyDirectory();
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		f.addFaculty(FIRST_NAME2, LAST_NAME2, ID2, EMAIL, PASSWORD2, PASSWORD2, MAX_COURSES2);
		assertEquals(FIRST_NAME, f.getFacultyDirectory()[0][0]);
		assertEquals(LAST_NAME, f.getFacultyDirectory()[0][1]);
		assertEquals(ID, f.getFacultyDirectory()[0][2]);
		
		assertEquals(FIRST_NAME2, f.getFacultyDirectory()[1][0]);
		assertEquals(LAST_NAME2, f.getFacultyDirectory()[1][1]);
		assertEquals(ID2, f.getFacultyDirectory()[1][2]);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#saveFacultyDirectory(java.lang.String)}.
	 */
	@Test
	void testSaveFacultyDirectory() {
		FacultyDirectory f = new FacultyDirectory();
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String filename = "test-files/testFaculty.txt";
		assertDoesNotThrow(() -> f.saveFacultyDirectory(filename));
		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> f.saveFacultyDirectory(""));
		assertEquals("Unable to write to file ", exception.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#getFacultyById(java.lang.String)}.
	 */
	@Test
	void testGetFacultyById() {
		FacultyDirectory f = new FacultyDirectory();
		f.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		f.addFaculty(FIRST_NAME2, FIRST_NAME2, ID2, EMAIL, PASSWORD2, PASSWORD2, MAX_COURSES2);
		assertEquals(FIRST_NAME, f.getFacultyById(ID).getFirstName());
		assertEquals(FIRST_NAME2, f.getFacultyById(ID2).getFirstName());
	}

}
