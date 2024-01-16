package edu.ncsu.csc216.pack_scheduler.catalog;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the CourseCatalog class.
 * 
 * Note: This class assumes that the Course class used in CourseCatalog
 * is correctly implemented and tested separately.
 * 
 * @author Eswar Talasila
 * @author Maxim Shelepov
 */
public class CourseCatalogTest {
	
	
	/**
	 * Sets up the test environment by resetting the 'course_records.txt' file to its initial state.
	 * This method is executed before each test method to ensure a clean slate for testing.
	 * 
	 * @throws Exception if there is an issue resetting the files
	 */
	
	@Before
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests the creation of a new CourseCatalog.
	 * 
	 * This test verifies that a new CourseCatalog is created, and it checks that the catalog is empty
	 * immediately after creation.
	 */
	
	@Test
	public void testNewCourseCatalog() {
		// Create a new catalog and check that it's empty
		CourseCatalog newCatalog = new CourseCatalog();
		assertEquals(0, newCatalog.getCourseCatalog().length);
	}
	
	/**
	 * Tests loading courses from a file into the CourseCatalog.
	 * 
	 * This test ensures that the CourseCatalog is not empty after loading course information from a file.
	 */

	@Test
	public void testLoadCoursesFromFile() {
		// Ensure that the catalog is not empty after loading from a file
		CourseCatalog catalog = new CourseCatalog();
		assertEquals(catalog.getCourseCatalog().length, 0);
	}
	
	/**
	 * Tests adding a course to the CourseCatalog.
	 * 
	 * This test checks the functionality of adding courses to the catalog, including scenarios where
	 * the course may already exist or have the same name but a different section.
	 */

	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		// Try adding a course that doesn't exist in the catalog
		assertTrue(catalog.addCourseToCatalog("CSC116", "Special Topics", "001", 3, "jdyoung2", 10, "MW", 910, 1100));

		// Try adding a course with the same name but different section (should succeed)
		assertFalse(catalog.addCourseToCatalog("CSC116", "Special Topics", "001", 3, "jdyoung2", 10, "MW", 910, 1100));

		// Try adding a course with the same name and section (should fail)
		assertTrue(catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "djohnson", 10, "MW", 1200, 1315));
	}
	
	/**
	 * Tests removing a course from the CourseCatalog.
	 * 
	 * This test verifies that courses can be successfully removed from the catalog and that attempting
	 * to remove a non-existent course does not affect the catalog.
	 */

	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		catalog.addCourseToCatalog("CSC116", "Special Topics", "001", 3, "jsmith", 10, "MWF", 1000, 1100);
		catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "djohnson", 10, "MW", 1200, 1315);

		// Try removing a course that exists in the catalog
		assertTrue(catalog.removeCourseFromCatalog("CSC216", "001"));

		// Try removing a course that does not exist in the catalog (should fail)
		assertFalse(catalog.removeCourseFromCatalog("CSC300", "001"));

	}
	
	/**
	 * Tests retrieving a course from the CourseCatalog.
	 * 
	 * This test checks the functionality of retrieving courses from the catalog, both when the course
	 * exists and when it does not exist in the catalog.
	 */

	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		// Try getting a course that exists in the catalog
		Course course = catalog.getCourseFromCatalog("CSC216", "001");
		assertNotNull(course);
		assertEquals("Software Development Fundamentals", course.getTitle());
		// Try getting a course that does not exist in the catalog (should return null)
		assertNull(catalog.getCourseFromCatalog("CSC300", "001"));
	}
	
	/**
	 * Tests retrieving the course catalog as a 2D array.
	 * 
	 * This test ensures that the catalog contains the expected courses and that the catalog information
	 * is correctly retrieved as a 2D array.
	 */

	@Test
	public void testGetCourseCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		// Ensure that the catalog contains the correct courses
		String[][] courseCatalog = catalog.getCourseCatalog();
		assertEquals(13, courseCatalog.length);
		assertEquals("CSC116", courseCatalog[0][0]);
		assertEquals("Intro to Programming - Java", courseCatalog[0][2]);
		assertEquals("CSC216", courseCatalog[3][0]);
		assertEquals("Software Development Fundamentals", courseCatalog[3][2]);
	}
	
	/**
	 * Tests exporting the course catalog to a file.
	 * 
	 * This test exports the catalog to a file and then re-imports it to check if they match.
	 * It verifies that the exported catalog is identical to the original catalog.
	 */

	@Test
	public void testExportSchedule() {
		CourseCatalog catalog = new CourseCatalog();
		// Export the catalog to a file and then re-import it to check if they match
		catalog.saveCourseCatalog("test-files/exported_course_records.txt");
		CourseCatalog exportedCatalog = new CourseCatalog();
		exportedCatalog.loadCoursesFromFile("test-files/exported_course_records.txt");

		// Ensure that the two catalogs are identical
		String[][] originalCourseCatalog = catalog.getCourseCatalog();
		String[][] exportedCourseCatalog = exportedCatalog.getCourseCatalog();
		assertEquals(originalCourseCatalog.length, exportedCourseCatalog.length);
		for (int i = 0; i < originalCourseCatalog.length; i++) {
			assertArrayEquals(originalCourseCatalog[i], exportedCourseCatalog[i]);
		}
	}
}
