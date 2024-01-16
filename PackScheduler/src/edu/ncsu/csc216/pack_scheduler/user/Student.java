package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Student is a model class which represents a student in the PackScheduler system. 
 * 
 * The Student object encapsulates field to uniquely identify each student and store useful information about the student. 
 * This information includes:
 * <ul>
 * <li>first name of the student.
 * <li>last name of the student.
 * <li>unity id of the student (unique identifier).
 * <li>email of the student.
 * <li>hashed password.
 * <li>max credits the student can take.
 * </ul>
 * The object provides getters and setters (with proper validation) for the mentioned fields. In addition to an ability to
 * check if the Student can be enrolled in a particular Course.
 * 
 * @author Eswar Talasila
 * @author Maxim Shelepov
 */
public class Student extends User implements Comparable<Student> {
	/** student's specific max credits */
	private int maxCredits;

	/** maximum possible credits for any student */
	public static final int MAX_CREDITS = 18;
	
	/** student schedule **/
	private Schedule schedule;

	/**
	 * Constructs a Student object with values for all fields and creates a fresh new schedule.
	 * @param firstName the student's first name
	 * @param lastName the students's last name
	 * @param id the student's unique identifier
	 * @param email the student's email
	 * @param password the student's hashed password
	 * @param maxCredits the student's max credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		
		//creates a empty schedule for student
		schedule = new Schedule();
		setMaxCredits(maxCredits);
	}

	/**
	 * Constructs a Student without specifying max credits.
	 * @param firstName the student's first name
	 * @param lastName the student's last name
	 * @param id the student's unique identifier
	 * @param email the student's email
	 * @param password the student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Returns the student's max credits.
	 * @return the student's max credits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the student's max credits if not null or empty.
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if null or empty
	 */

	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}

		this.maxCredits = maxCredits;
	}


	/**
	 * Returns a comma separated value String of all Student fields.
	 * @return String representation of Student
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
	}

	/**
	 * Compares this Student object to another Student object for ordering based on last name, 
	 * first name, and unity ID.
	 *
	 * This method defines the natural ordering for Student objects. Students are first compared 
	 * based on their last names in lexicographical order. If the last names are equal, the comparison 
	 * proceeds to compare their first names. If the first names are also equal, the comparison is 
	 * based on their unity IDs. This ordering allows a collection of Student objects to be sorted 
	 * by last name, and in cases of last name equality, further sorted by first name and unity ID.
	 *
	 * @param s the Student object to compare to
	 * @return a negative integer, zero, or a positive integer if this Student object is less than, 
	 * equal to, or greater than the specified Student object, respectively, based on the 
	 * last name, first name, and unity ID.
	 */
	@Override
	public int compareTo(Student s) {
		// Compare students based on last name
		int lastNameComparison = this.getLastName().compareTo(s.getLastName());

		if (lastNameComparison != 0) {
			// If last names are not equal, return the result
			return lastNameComparison;
		}

		// Last names are equal, so compare by first name
		int firstNameComparison = this.getFirstName().compareTo(s.getFirstName());

		if (firstNameComparison != 0) {
			// If first names are not equal, return the result
			return firstNameComparison;
		}

		// First names are equal, so compare by unity id
		return this.getId().compareTo(s.getId());
	}

	/**
	 * Generates the hash code for the student class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}

	/**
	 * Compares two Student objects.
	 * @param obj the object to compare to this Student instance.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * Returns the current Student's Schedule.
	 * @return schedule of student
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	/**
	 * Determines if course can be added to student
	 * schedule. If course is null, has time conflict, is
	 * duplicate, or goes over max credits, the method returns 
	 * false. 
	 * @param c course to be checked
	 * @return boolean if course can be added. 
	 */
	public boolean canAdd(Course c) {
		if(c == null) {
			return false;
		}
		
		//check for course going over credits
		int currentCredits = this.schedule.getScheduleCredits();
		if(currentCredits + c.getCredits() > this.maxCredits) {
			return false;
		}
		
		//use schedule canAdd to check for null, duplicate, or conflict
		return this.schedule.canAdd(c);
		
	}

}
