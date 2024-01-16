
/**
 * The CourseCatalog class represents a catalog of courses. It allows users to
 * add, remove, and retrieve courses, as well as load courses from a file and
 * export the catalog to a file.
 */
package edu.ncsu.csc216.pack_scheduler.catalog;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * The CourseCatalog class manages a list of courses and provides various
 * methods for interacting with a SortedList of catalog Courses.
 * 
 * The class encapsulates the Course SortedList catalog. Provides add, remove, get behavior for
 * a catalog Course. In addition, global behavior of resetting, saving, and loading in Course catalog.
 * 
 * @author Eswar Talasila
 */

public class CourseCatalog {
	/** Catalog of Courses */
	private SortedList<Course> catalog;
	
	/**
     * Constructs a new CourseCatalog with an empty catalog.
     */
	public CourseCatalog() {
		newCourseCatalog();
		
	}
	
	/**
     * Initializes the catalog as a new empty SortedList.
     */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
		
	}
	
	/**
     * Loads courses from a file into the catalog.
     *
     * @param fileName the name of the file to load courses from
     * @throws IllegalArgumentException if the specified file cannot be found
     */
	public void loadCoursesFromFile(String fileName) {
		
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}

		
	}
	
	 /**
     * Adds a course to the catalog with the specified attributes. Checks if a
     * course with the same name and section already exists in the catalog. If not,
     * it adds the course and returns true. If the course is already in the catalog,
     * it returns false. If there is a scheduling conflict, it throws an
     * IllegalArgumentException.
     *
     * @param name the name of the course
     * @param title the title of the course
     * @param section the section of the course
     * @param credits the number of credits for the course
     * @param instructorId the instructor's ID for the course
     * @param enrollmentCap max students in course
     * @param meetingDays the meeting days for the course
     * @param startTime the start time of the course
     * @param endTime the end time of the course
     * @return true if the addition of the course to the catalog is successful, false otherwise.
     * @throws IllegalArgumentException if a course with the same name already exists in the catalog or if there is a scheduling conflict                            
     */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		// check if course exists in catalog
		//Course catalogCourse = null;
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				//catalogCourse = course;
				return false;
			}
		}

		return catalog.add(new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime)); // add course to the end of the schedule and return true on success
	}
	
	/**
     * Removes a course from the catalog with the specified name and section.
     * @param name    the name of the course to remove
     * @param section the section of the course to remove
     * @return true if the removal of the course was successful, false otherwise
     */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
     * Retrieves a course from the catalog with the specified name and section.
     * @param name the name of the course to retrieve
     * @param section the section of the course to retrieve
     * @return the Course object from the catalog identified uniquely by the name and section parameters, or null if the course doesn't exist
     */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				return course;
			}
		}

		return null;
	}
	
	/**
     * Returns the course catalog as a 2-dimensional String array with rows for each catalog course and columns for name, section, title, and meeting string.
     * @return a 2-dimensional String array representing the course catalog
     */
	public String[][] getCourseCatalog() {
		String[][] courseCatalog = new String[catalog.size()][5];

		for (int i = 0; i < catalog.size(); i++) {
			courseCatalog[i][0] = catalog.get(i).getName();
			courseCatalog[i][1] = catalog.get(i).getSection();
			courseCatalog[i][2] = catalog.get(i).getTitle();
			courseCatalog[i][3] = catalog.get(i).getMeetingString();
			courseCatalog[i][4] = String.valueOf(catalog.get(i).getCourseRoll().getOpenSeats());
		}
		
		return courseCatalog;
	}
	
	/**
	 * Exports the schedule to the specified file name. If file cannot be exported
	 * @param fileName the export file name for course schedule
	 * @throws IllegalArgumentException if the file cannot be exported
	 */
	
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
	}

}
