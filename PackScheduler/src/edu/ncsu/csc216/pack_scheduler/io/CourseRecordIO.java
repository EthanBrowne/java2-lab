package edu.ncsu.csc216.pack_scheduler.io;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * CourseRecordIO is a utility class that reads and writes Course records from and to text files.
 * 
 * The CourseRecordIO class provides two methods (actions):
 * <ul>
 * <li>a method to load courses from a specific file.
 * <li>a method to export courses to a specific file.
 * </ul>
 * 
 * @author Sarah Heckman
 * @author Maxim Shelepov
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses. Any invalid
     * Courses are ignored. If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName the file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
    	Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
        SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
        
        while (fileReader.hasNextLine()) { // While we have more lines in the file
            try { // Attempt to do the following
                // Read the line, process it in readCourse, and get the object
                // If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
                Course course = readCourse(fileReader.nextLine()); 

                // Create a flag to see if the newly created Course is a duplicate of something already in the list  
                boolean duplicate = false;
                // Look at all the courses in our list
                for (int i = 0; i < courses.size(); i++) {
                    // Get the course at index i
                    Course current = courses.get(i);
                    // Check if the name and section are the same
                    if (course.getName().equals(current.getName()) &&
                            course.getSection().equals(current.getSection())) {
                        // It's a duplicate!
                        duplicate = true;
                        break; // We can break out of the loop, no need to continue searching
                    }
                }
                // If the course is NOT a duplicate
                if (!duplicate) {
                    courses.add(course); //Add to the ArrayList!
                } // Otherwise ignore
            } catch (IllegalArgumentException e) {
                // The line is invalid b/c we couldn't create a course, skip it!
            }
        }
        
        // Close the Scanner b/c we're responsible with our file handles
        fileReader.close();
        // Return the SortedList with all the courses we read!
        return courses;
    }
    /**
     * A helper method to read a single Course object. 
     * 
     * If meetingDays is "Arranged", then do not expect start and end time, otherwise 
     * throws an IllegalArgumentException. If any additional or missing information, throws an IllegalArgumentException as well.
     * 
     * Note: the provided string consists of comma separated values (CSV)
     * @param nextLine a comma separated value string from a CSV file
     * @return a Course object read from the CSV string
     * @throws IllegalArgumentException if any missing or extra information in the form of Scanner String tokens
     */
    private static Course readCourse(String nextLine) {
		Scanner readLine = new Scanner(nextLine);
		readLine.useDelimiter(",");
		
		try {
			String name = readLine.next();
			String title = readLine.next();
			String section = readLine.next();
			int creditHours = readLine.nextInt();
			String instructorId = readLine.next();
			
			//new for lab 8, read in enrollment cap value from file
			int enrollmentCap = Integer.parseInt(readLine.next());
			String meetingDays = readLine.next();
			
			if ("A".equals(meetingDays)) {
				if (readLine.hasNext()) {
					readLine.close();
					throw new IllegalArgumentException();
				}
				readLine.close();
				Course cource = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays);
				if (instructorId != null && RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId) != null) {
					RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId).getSchedule().addCourseToSchedule(cource);
				}
				return cource;
			}
			
			int startTime = readLine.nextInt();
			int endTime = readLine.nextInt();
			
			if (readLine.hasNext()) {
				readLine.close();
				throw new IllegalArgumentException();
			}
			readLine.close();
			
			Course cource = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays, startTime, endTime);
			if (instructorId != null && RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId) != null) {
				RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId).getSchedule().addCourseToSchedule(cource);
			}
			return cource;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
     * Writes the given list of Courses to a file. If unsuccessful, 
     * throws IOException.
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
    public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
    	PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < courses.size(); i++) {
    	    fileWriter.println(courses.get(i).toString());
    	}

    	fileWriter.close();
        
    }

}
