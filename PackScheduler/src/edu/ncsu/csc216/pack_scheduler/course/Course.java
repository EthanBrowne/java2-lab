/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Course is a model class which represents a university course found in the course directory or 
 * course schedule of the WolfScheduler system.
 * 
 * A Course object encapsulates the state information needed to uniquely identify a course
 * in the system and to provide useful details about the course. The information includes:
 * <ul>
 * <li>The name of the course.
 * <li>The title (full name) of the course.
 * <li>The section number for the course.
 * <li>The credit hours for the course.
 * <li>The unity id of the instructor for the course.
 * <li>The days of the week the course meets.
 * <li>The start and end times for the course.
 * </ul>
 * <p>
 * Course object performs getter and setter operations for retrieval and setting of the above state 
 * information with proper validation before committing the data. In addition, the Course object provides 
 * actions to display said state information in a human interpretable format.
 * 
 * @author Maxim Shelepov
 */
public class Course extends Activity implements Comparable<Course> {
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor unity id */
	private String instructorId;
	/** Course name minimum total character length */
	private static final int MIN_NAME_LENGTH = 4;
	/** Course name maximum total character length */
	private static final int MAX_NAME_LENGTH = 8;
	/** Course section exact character length */
	private static final int SECTION_LENGTH = 3;
	/** Course minimum allowed credits  */
	private static final int MIN_CREDITS = 1;
	/** Course maximum allowed credits  */
	private static final int MAX_CREDITS = 5;
	
	/** Course name validator object */
	private CourseNameValidator validator;
	
	/** Course roll of registered students */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of the Course
	 * @param title title of the Course
	 * @param section section of the Course
	 * @param credits credit hours for the Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max students in course
	 * @param meetingDays meeting days for course as a series of chars
	 * @param startTime start time for the Course
	 * @param endTime end time for the Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        
        //construct course roll given enrollmentCap
        roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Constructs a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged.
	 * Note: the start and end times are defaulted to 0.
	 * @param name name of the Course
	 * @param title title of the Course
	 * @param section section of the Course
	 * @param credits credit hours for the Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max students in course
	 * @param meetingDays meeting days for the course as a series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}
	
	/**
	 * Required abstract method enforced by Activity. Returns a shortened String array representation 
	 * of the Course using the fields: name, section, title, and the meeting string.
	 * 
	 * @return String array of length 4 consistent of the Course's name, section, title, and meeting string
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		
		shortDisplay[0] = name;
		shortDisplay[1] = section;
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		
		//new for lab 8; open seats in course roll
		shortDisplay[4] = String.valueOf(this.roll.getOpenSeats());
		
		return shortDisplay;
	}
	
	/**
	 * Required abstract method enforced by Activity. Returns a longer String array representation 
	 * of the Course using the fields: name, section, title, credits, instructor unity id, meeting string, and buffer String
	 * for other getLongDisplayArray implementations.
	 * 
	 * @return String array of length 7 consistent of the Course's name, section, title, credits, instructor unity id, meeting string, and 
	 * empty buffer string
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		
		longDisplay[0] = name;
		longDisplay[1] = section;
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(credits);
		longDisplay[4] = instructorId;
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		
		return longDisplay;
	}
	
	/**
	 * Required abstract method enforced by Activity. Returns a boolean indicating whether 
	 * a provided Activity is an instance of Course and has a matching name with this Course object.
	 * 
	 * @param activity the Activity to compare this Course to
	 * @return true if name of the provided Activity equals this Course's name and false otherwise
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course course = (Course) activity;
			
			return name.equals(course.getName());
		}
		
		return false;
	}

	/**
	 * Returns the Course's name.
	 * @return the name of the Course
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. 
	 * 
	 * If the provided name meets the criteria:
	 * <ul>
	 * <li>not null.
	 * <li>total length between 5 and 8 characters (inclusive).
	 * <li>contains a space between the letter characters and the number characters.
	 * <li>has between 1 and 4 letter characters (inclusive) in the beginning.
	 * <li>has exactly three trailing digit characters.
	 * </ul>
	 * <p>
	 * Otherwise throws an IllegalArgumentException.
	 * @param name the name to set
	 * @throws IllegalArgumentException if the provided name parameter is invalid
	 */
	private void setName(String name) {
		
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		validator = new CourseNameValidator();
		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * @return the section of the Course
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section.
	 * 
	 * If the provided section meets the criteria: is not null, empty, and consists of all digits.
	 * Otherwise throws an IllegalArgumentException.
	 * @param section the section to set
	 * @throws IllegalArgumentException if the provided section parameter is invalid
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		for (int i = 0; i < section.length(); i++) {
			char charac = section.charAt(i);
			if (!Character.isDigit(charac)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}

	/**
	 * Returns the Course's credit.
	 * @return the credits for the Course
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credit if the credit doesn't exceed the minimum value of 1 and the maximum value 5,
	 * otherwise throws an IllegalArgumentException.
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the credit parameter is invalid
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructor id.
	 * @return the unity id of the instructor for the Course
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor id.
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if is length 0
	 */
	public void setInstructorId(String instructorId) {
		if ("".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Set the Course meeting days, start time, and end time.
	 * 
	 * If the provided meeting days, start time, and end time meet the criteria:
	 * <ul>
	 * <li>meeting days is a string that consists of characters in array of set options.
	 * <li>no duplicates for meeting days.
	 * </ul>
	 * <p>
	 * Otherwise throws an IllegalArgumentException. The method passes the checked parameters to the abstract Activity class 
	 * for common checks and assignment.
	 * Note: If meeting day is 'A' or "Arranged", the start and end times are set to 0. 
	 * @param meetingDays the meeting days for the Course
	 * @param startTime the start time for the Course
	 * @param endTime the end time for the Course
	 * @throws IllegalArgumentException if the provided meeting days, start time, or end time parameters are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
		} else {
			char[] meetingDayOptions = {'M', 'T', 'W', 'H', 'F'};
			int[] meetingDayCounts = new int[meetingDayOptions.length];
			
			for (int i = 0; i < meetingDays.length(); i++) {
				char charac = meetingDays.charAt(i);
				
				boolean validChar = false;
				for (int j = 0; j < meetingDayOptions.length; j++) {
					if (charac == meetingDayOptions[j]) {
						meetingDayCounts[j]++;
						validChar = true;
						break;
					}
				}
				
				// check char validity
				if (!validChar) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			// check duplicates
			for (int meetingDayCount : meetingDayCounts) {
				if (meetingDayCount > 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Generates a hashCode for Course using all fields.
	 * @return hashCode for the Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}
	
	/**
	 * Compares this Course object to provided object for equality in all fields.
	 * @return true if this and provided object is the same in all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + String.valueOf(this.roll.getEnrollmentCap()) + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + String.valueOf(this.roll.getEnrollmentCap()) + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/**
	 * Compares a given Course object to this object first by name then section.
	 * @return integer value indicating lexicographical precedence (-1), equality (0), or subsequence (1).
	 */
	@Override
	public int compareTo(Course c) {
		int nameComparison = name.compareTo(c.getName());
		
		if (nameComparison != 0) {
			return nameComparison;
		}
		
		return section.compareTo(c.getSection());
	}
	
	/**
	 * Returns the CourseRoll object for the given
	 * course, consisting of a list of registered students
	 * in the course. 
	 * @return CourseRoll of students
	 */
	public CourseRoll getCourseRoll() {
		return this.roll;
	}

}
