package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;
/**
 *
 * StudentRecordIO is a utility class for reading and writing Faculty records
 * from and to files.
 * 
 * The FacultyRecordIO class provides the following methods (actions): import
 * Faculty directory records from an external plain text CSV file. export
 * Faculty directory records to an external plain text file.
 */
public class FacultyRecordIO {
	
	/**
	 * Reads Faculty records from a file and returns them as a LinkedList.
	 * Note: Duplicates of Student records are ignored.
	 * @param filename is the name of the file to read facultyS records from
	 * @return linked list containing faculty records
	 * @throws FileNotFoundException if the specified file is not found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		LinkedList<Faculty> faculty = new LinkedList<>();
		while (fileReader.hasNextLine()) {
			try {
				Faculty student = processFaculty(fileReader.nextLine());

				boolean duplicate = false;

				for (int i = 0; i < faculty.size(); i++) {
					User current = faculty.get(i);
					if (student.getId().equals(current.getId())) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) {
					faculty.add(student);
				}
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}

		fileReader.close();
		return faculty;
		
	}
	
	private static Faculty processFaculty(String nextLine) {
		Scanner scanner = new Scanner(nextLine);
		scanner.useDelimiter(",");	

		try {
			String firstName = scanner.next();
			String lastName = scanner.next();
			String id = scanner.next();
			String email = scanner.next();
			String hashedPassword = scanner.next();
			int maxCredits = scanner.nextInt();
			
			if (scanner.hasNext()) {
				scanner.close();
				throw new IllegalArgumentException("Invalid input format.");
			}
			scanner.close();
	
			return new Faculty(firstName, lastName, id, email, hashedPassword, maxCredits);
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Invalid input format.");
		}
	}
	/**
	 *
	 * Writes the given list of Faculty (faculty directory) to a specified file.
	 * With each student being stored on a separate line and following the toString
	 * format in the external file.
	 * @param fileName  the file name to write the faculty directory to
	 * @param facultyDirectory the students to write to the specified file
	 * @throws IOException if file does not exist or unable to write to file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
	
		fileWriter.close();
	}
	

}
