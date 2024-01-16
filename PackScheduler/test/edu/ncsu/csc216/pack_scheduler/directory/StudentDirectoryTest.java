package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * Tests for StudentDirectory class. StudentDirectoryTest class contains some generally used fake 
 * information as its field. Each method with a "Test" suffix in the class corresponds to an identical method
 * in the StudentDirectory class for which the test is written. The class tests the following:
 * <ul>
 * <li>the initial empty Student directory on StudentDirectory class initialization.
 * <li>clearing the StudentDirectory after loading in Student records.
 * <li>loading in Student records from valid and invalid destination file.
 * <li>adding valid and invalid Student record.
 * <li>removing a Student record.
 * <li>exporting the Student records to a valid and invalid destination file.
 * </ul>
 * In addition, the test class provides some valid and invalid file destinations and test data as its fields.
 * Before running the tests, the class copies the contents of the expected students records file
 * to the tested student records file to safeguard from unwanted results.
 * 
 * @author Sarah Heckman
 * @author Maxim Shelepov
 */
public class StudentDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Non-existent load and write destination for Student records */
	private final String absentTestFile = "~/non_existent.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws IOException if unable to reset student_records.txt.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory() a parameterless constructor.
	 */
	@Test
	public void testStudentDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.newStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
				
		//Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		// Test invalid file
		Exception e = assertThrows(IllegalArgumentException.class,
			() -> sd.loadStudentsFromFile(absentTestFile)
		);
		assertEquals("Unable to read file " + absentTestFile, e.getMessage());
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test invalid Student
		Exception e1 = assertThrows(IllegalArgumentException.class, 
			() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS)
		);
		Exception e2 = assertThrows(IllegalArgumentException.class, 
			() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS)
		);
		Exception e3 = assertThrows(IllegalArgumentException.class, 
			() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS)
		);
		Exception e4 = assertThrows(IllegalArgumentException.class, 
			() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS)
		);
		Exception e5 = assertThrows(IllegalArgumentException.class, 
			() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "notpw", MAX_CREDITS)
		);
		String errorMsg = "Invalid password";
		assertAll("Student invalid password should throw: " + errorMsg,
				() -> assertEquals(errorMsg, e1.getMessage()),
				() -> assertEquals(errorMsg, e2.getMessage()),
				() -> assertEquals(errorMsg, e3.getMessage()),
				() -> assertEquals(errorMsg, e4.getMessage()),
				() -> assertEquals("Passwords do not match", e5.getMessage())
		);
		
		
		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		// Test adding valid but already existing Student in directory
		assertFalse(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 100));
	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();
				
		//Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		
		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);

		assertEquals(1, sd.getStudentDirectory().length);
		
		// Save to invalid file
		Exception e = assertThrows(IllegalArgumentException.class, 
			() -> sd.saveStudentDirectory(absentTestFile)
		);
		assertEquals("Unable to write to file " + absentTestFile, e.getMessage());
		
		// Save to valid file
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
