package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.io.FileNotFoundException;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for StudentRecordIO class. StudentRecordIO class contains the expected student records as its fields. The 
 * class tests the following:
 * <ul>
 * <li>reading valid student records.
 * <li>reading invalid student records.
 * <li>reading from invalid destination file.
 * <li>writing to file and comparing actual results to expected results.
 * <li>writing to invalid destination file.
 * </ul>
 * In addition, the test class provides some file test data as its fields.
 * Before running the tests, the class converts the valid expected student records "pw" passwords to hashed passwords.
 * 
 * @author Saloni Kumari
 * @author Maxim Shelepov
 */
class StudentRecordIOTest {
	/** Expected results for valid student in student_records.txt - line 1 */
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Expected results for valid student in student_records.txt - line 2 */
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Expected results for valid student in student_records.txt - line 3 */
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Expected results for valid student in student_records.txt - line 4 */
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Expected results for valid student in student_records.txt - line 5 */
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Expected results for valid student in student_records.txt - line 6 */
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Expected results for valid student in student_records.txt - line 7 */
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Expected results for valid student in student_records.txt - line 8 */
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** Expected results for valid student in student_records.txt - line 9 */
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Expected results for valid student in student_records.txt - line 10 */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	
	/** Array with all expected results */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };
	
	/** Valid student records file */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records file */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	/** Out-of-scope (non-existent) student records file */
	private final String outOfScopeFile = "~/DNE_file.txt";
	
	/** String container to store hashed password */
	private String hashPW;

	/** Hashing Algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Prepare expected valid student records by replacing "pw" passwords with hashed passwords.
	 */
	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@BeforeEach
	public void fileSetUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
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
	 * Test StudentRecordsIO.readStudentRecords with valid student records (student_records.txt).
	 * 
	 * Note: Order of the sorted student_records.txt student must match the expected alphanumeric ordering provided
	 * by SortedList.
	 * 
	 * Citation: The code for this method is based on the GP1 CourseRecordIOTest class which can be found on the 
	 * CSC 216 website GP1 instructions -- Step 10 Eclipse Quick Fix Tool (Creating IO Test Structure section).
	 */
	@Test
	public void testReadValidStudentRecords() {
		// testing valid files
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}

		} catch (FileNotFoundException e) {
			fail("Unable to read file: " + validTestFile);
		}
	}
	
	/**
	 * Test StudentRecordIO.readStudentRecords with invalid student records (invalid_student_records.txt).
	 * 
	 * Citation: The code for this method is based on the GP1 CourseRecordIOTest class which can be found on the 
	 * CSC 216 website GP1 instructions -- Step 10 Eclipse Quick Fix Tool (Creating IO Test Structure section).
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		// testing invalid files
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {
			fail("Unable to read file: " + invalidTestFile);
		}
	}
	
	/**
	 * Test StudentRecordsIO.readStudentRecords with invalid file destination.
	 * 
	 * Citation: The code for this method is based on the GP1 CourseRecordIOTest class which can be found on the 
	 * CSC 216 website GP1 instructions -- Step 10 Eclipse Quick Fix Tool (Creating IO Test Structure section).
	 */
	@Test
	public void testReadInvalidFile() {
		// throws FileNotFoundException
		assertThrows(FileNotFoundException.class, () -> {
			StudentRecordIO.readStudentRecords(outOfScopeFile);
		});
	}

	/**
	 * Test StudentRecordsIO.writeStudentRecords with valid Student records.
	 * 
	 * Citation: The code for this method is based on the GP1 CourseRecordIOTest class which can be found on the 
	 * CSC 216 website GP1 instructions -- Step 10 Eclipse Quick Fix Tool (Creating IO Test Structure section).
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 15));
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Unable to write student records.");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Test StudentRecordIO.writeStudentRecords with invalid file destination.
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

		Exception exception = assertThrows(IOException.class,
				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}

	/**
	 * Helper method to compare two files for the same contents.
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
