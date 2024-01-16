package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Faculty is a model class which represents a faculty in the PackScheduler system
 * 
 * The Faculty object encapsulates field to uniquely identify each faculty and store useful information about the faculty. 
 * This information includes:
 * <ul>
 * <li>first name of the faculty.
 * <li>last name of the faculty.
 * <li>unity id of the faculty (unique identifier).
 * <li>email of the faculty.
 * <li>hashed password.
 * <li>max courses the faculty can take.
 * </ul>
 * The object provides getters and setters (with proper validation) for the mentioned fields.
 * 
 * @author Nicolas Bechard
 */
public class Faculty extends User {
	
	/** faculty specific max courses */
	private int maxCourses;
	
	/** minimum possible courses for any faculty */
	public static final int MIN_COURSES = 1;
	
	/** maximum possible courses for any faculty */
	public static final int MAX_COURSES = 3;
	/**
	 * schedule for faculty schedule
	 */
	private FacultySchedule schedule;
	/**
	 * Constructs a Faculty object with values for all fields.
	 * @param firstName the faculty's first name
	 * @param lastName the faculty's last name
	 * @param id the faculty's unique identifier
	 * @param email the faculty's email
	 * @param password the faculty's hashed password
	 * @param maxCourses the faculty's max courses
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		this.schedule = new FacultySchedule(id);
	}
	/**
	 * Returns Faculty schedule
	 * @return schedule of faculty
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Returns the maximum courses possible for a faculty.
	 * @return maximum courses possible for faculty
	 */
	public int getMaxCourses() {
		return maxCourses;
	}
	/**
	 * Returns whether scheduled courses is greater than max courses or not
	 * @return  true if the number of scheduled courses is greater than the Faculty’s maxCourses.
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}
	
	/**
	 * Sets the maximum possible courses for a faculty.
	 * @param maxCourses the maximum courses a faculty can have
	 */
	public void setMaxCourses(int maxCourses) {
		
		if(maxCourses > MAX_COURSES || maxCourses < MIN_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		
		this.maxCourses = maxCourses;
	}

	/**
	 * Generates the hash code for the faculty class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCourses);
		return result;
	}

	/**
	 * Compares two Faculty objects.
	 * @param obj the object to compare to this Faculty instance.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

	/**
	 * Returns a comma separated value String of all Faculty fields.
	 * @return String representation of Faculty
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCourses;
	}
}
