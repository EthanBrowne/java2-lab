package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.util.NoSuchElementException;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * StudentRecordIO is a utility class for reading and writing Student records
 * from and to files.
 * 
 * The StudentRecordIO class provides the following methods (actions): import
 * Student directory records from an external plain text CSV file. export
 * Student directory records to an external plain text file.
 * 
 * @author Saloni Kumari
 * @author Maxim Shelepov
 */
public class StudentRecordIO {
	/**
	 * Reads student records from a file and returns them as a SortedList.
	 * 
	 * Note: Duplicates of Student records are ignored.
	 * 
	 * Citation: The code for this method is based on the GP1
	 * CourseRecordsIO.readCourseRecords method and class. The cited code can be
	 * found on the CSC 216 course website under the Guided Project 1 task "Working
	 * with Java Libraries -> Java Collections Framework".
	 * 
	 * @param fileName is the name of the file to read student records from
	 * @return sorted list containing student records
	 * @throws FileNotFoundException if the specified file is not found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<>();

		while (fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());

				boolean duplicate = false;

				for (int i = 0; i < students.size(); i++) {
					User current = students.get(i);
					if (student.getId().equals(current.getId())) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) {
					students.add(student);
				}
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}

		fileReader.close();
		return students;
	}

	/**
	 * Parses a line of text representing a student record and constructs a Student object.
	 * 
	 * Note: Makes sure all Student field are present and no additional tokens, otherwise throws an IllegalArgumentException.
	 * 
	 * Citation: The code for this method is based on the GP1 CourseRecordsIO.readCourse helper method and class. The cited code
	 * can be found on the CSC 216 course website under the Guided Project 1 Task "Finish CourseRecordIO -> Implement CourseRecordIO.readCourse()".
	 * 
	 * @param nextLine of text containing student information
	 * @return Student if all required fields exist
	 * @throws IllegalArgumentException if the String is missing tokens or contains extra tokens.
	 */			
	private static Student processStudent(String nextLine) {
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
	
			return new Student(firstName, lastName, id, email, hashedPassword, maxCredits);
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Invalid input format.");
		}
	}
		
	/**
	 * Writes the given list of students (student directory) to a specified file.
	 * With each student being stored on a separate line and following the toString
	 * format in the external file.
	 *
	 * Citation: The code for this method is based on the GP1
	 * CourseRecordsIO.writeCourseRecords method and class. The cited code can be
	 * found on the CSC 216 course website under the Guided Project 1 Task "Working
	 * with Java Libraries -> File Output".
	 * 
	 * @param fileName         the file name to write the students directory to
	 * @param studentDirectory the students to write to the specified file
	 * @throws IOException if file does not exist or unable to write to file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
	
		fileWriter.close();
	}

}
