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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for FacultyRecordIO class. FacultyRecordIO class contains the expected faculty records as its fields. The 
 * class tests the following:
 * <ul>
 * <li>reading valid faculty records.
 * <li>reading invalid faculty records.
 * <li>reading from invalid destination file.
 * <li>writing to file and comparing actual results to expected results.
 * <li>writing to invalid destination file.
 * </ul>
 * In addition, the test class provides some file test data as its fields.
 * Before running the tests, the class converts the valid expected student records "pw" passwords to hashed passwords.
 * Citation: The code for this class is based on the Lab1 StudentRecordIO class 

 * @author Saloni Kumari
 * @author Maxim Shelepov
 * @author Amrish Naranappa
 */
class FacultyRecordIOTest {
	/** Expected results for valid student in student_records.txt - line 1 */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
	/** Expected results for valid student in student_records.txt - line 2 */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Expected results for valid student in student_records.txt - line 3 */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Expected results for valid student in student_records.txt - line 4 */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Expected results for valid student in student_records.txt - line 5 */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Expected results for valid student in student_records.txt - line 6 */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Expected results for valid student in student_records.txt - line 7 */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	/** Expected results for valid student in student_records.txt - line 8 */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
	
	
	/** Array with all expected results */
	private String[] validStudents = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7};
	
	/** Valid student records file */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid student records file */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	/** Out-of-scope (non-existent) student records file */
	private final String outOfScopeFile = "~/DNE_file.txt";
	/** invalid file with extra fields */
	private final String invalidTestFileTwo = "test-files/Invalid_records_1.txt";
	
	/** String container to store hashed password */
	private String hashPW;

	/** Hashing Algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Prepare expected valid faculty records by replacing "pw" passwords with hashed passwords.
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
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	

	/**
	 * Test FacultyRecordsIO.readFacultyRecords with valid faculty records (faculty_records.txt).
	 * 
	 * Note: Order of the sorted faculty_records.txt faculty must match the expected alphanumeric ordering provided
	 * by LinkedList.
	 * 
	 * Citation: The code for this method is based on the Lab1 StudentRecordIO class 
	 
	 */
	
	
	@Test
	public void testReadValidFacultyRecords() {
		// testing valid files
		try {
			LinkedList<Faculty> students = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, students.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}

		} catch (FileNotFoundException e) {
			fail("Unable to read file: " + validTestFile);
		}
	}
	
	/**
	 * Test FacultyRecordIO.readFacultyRecords with invalid student records (invalid_faculty_records.txt).
	 * 
	 */
	@Test
	public void testReadInvalidFacultyRecords() {
		// testing invalid files
		try {
			LinkedList<Faculty> students = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {
			fail("Unable to read file: " + invalidTestFile);
		}
	}
	
	
	/**
	 * Test FacultyRecordIO.readFacultyRecords with invalid file destination.
	 * 
	 */
	@Test
	public void testReadInvalidFile() {
		// throws FileNotFoundException
		assertThrows(FileNotFoundException.class, () -> {
			FacultyRecordIO.readFacultyRecords(outOfScopeFile);
		});
			try {
				FacultyRecordIO.readFacultyRecords(invalidTestFileTwo);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		
	}

	/**
	 * Test StudentRecordsIO.writeStudentRecords with valid Student records.
	 * 
	 */
	@Test
	public void testWriteFacultyRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 2));
		faculty.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3));
		faculty.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 1));
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
		} catch (IOException e) {
			fail("Unable to write faculty records.");
		}
		
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
		 
	}
	
	/**
	 * Test FacultyRecordIO.writeFacultyRecords with invalid file destination.
	 */
	@Test
	public void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 2));

		Exception exception = assertThrows(IOException.class,
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_student_records.txt", faculty));
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
