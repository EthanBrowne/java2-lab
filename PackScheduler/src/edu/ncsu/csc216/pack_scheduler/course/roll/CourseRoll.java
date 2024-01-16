/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Maintains a course roll of all students added to the list.
 * All students have a unique id and are stored in a linked abstract list of Student's. The class allows
 * the user to drop and enroll students.
 * 
 * @author Nick Bechard
 */
public class CourseRoll {
	
	/** The linked abstract list of students in the course roll. */
	LinkedAbstractList<Student> roll;
	
	/** The maximum amount of students allowed to be enrolled. */
	private int enrollmentCap;
	
	/** The minimum number that the enrollment cap can be set as. */
	public static final int MIN_ENROLLMENT = 10;
	
	/** The maximum number that the enrollment cap can be set as. */
	public static final int MAX_ENROLLMENT = 250;
	
	/** The wait-list for course enrollment. */
	private ArrayQueue<Student> waitlist;
	
	/** Course for the course roll. */
	private Course course;
	
	/**
	 * Default constructor for CourseRoll, sets the maximum capacity of students allowed in the course list.
	 * @param capacity the maximum capacity of students allowed in the course roll
	 * @param c the course to check for size
	 */
	public CourseRoll(Course c, int capacity) {
		setEnrollmentCap(capacity);
		roll = new LinkedAbstractList<Student>(capacity);
		waitlist = new ArrayQueue<Student>(10);
		if(c == null) {
			throw new IllegalArgumentException();
		}
		this.course = c;
	}
	
	/**
	 * Gets the enrollment cap of the course list.
	 * @return enrollmentCap the maximum amount of students allowed in the course roll.
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Sets the maximum amount of students in the course roll.
	 * @param enrollmentCap is the number for the max amount of students
	 * @throws IllegalArgumentException if greater than MAX_ENROLLMENT, less than MIN_ENROLLMENT, or enrollment capacity is less than 
	 * current enrollment size
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if(enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if(enrollmentCap < MIN_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if(roll != null) {
			roll.setCapacity(enrollmentCap);
		}
		
		this.enrollmentCap = enrollmentCap;
		
	}
	
	/**
	 * Enrolls a student in the course roll.
	 * @param s the student to be enrolled
	 * @throws IllegalArgumentException if student is null, cannot be enrolled, or issues with 
	 * adding student to roll
	 */
	public void enroll(Student s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
				
		if(!canEnroll(s)) {
			throw new IllegalArgumentException();
		}
	
		try {
			if(roll.size() == enrollmentCap) {
				if(waitlist.size() == 10) {
					throw new IllegalArgumentException();
				}
				waitlist.enqueue(s);
			}
			else {
				roll.add(roll.size(), s);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Drops a student from the course roll.
	 * @param s the student to be dropped
	 * @throws IllegalArgumentException if student is null or issues with adding student to roll
	 */
	public void drop(Student s) {
		try {
			 if (s == null) {
			        throw new IllegalArgumentException("Student cannot be null");
			 }
			// boolean throwException = true;
			 for (int i = 0; i < roll.size(); i++) {
			    if (s.equals(roll.get(i))) {
			    	roll.remove(i);
			    	if (waitlist.size() > 0) {
			    		Student waitlistStudent = waitlist.dequeue();
			    		enroll(waitlistStudent);
			    		waitlistStudent.getSchedule().addCourseToSchedule(course);
			    	}
			    	//throwException = false;
			    	break;
				} else if ( waitlist.contains(s)) {
					ArrayQueue<Student> p = new ArrayQueue<Student>(waitlist.getCapacity());
					Student h = null;
					while (waitlist.size() > 0) {
						h = waitlist.dequeue();
						if (!s.equals(h)) {
							p.enqueue(h);
						}
					}
					waitlist = p;
					//throwException = false;
				}
			 }
			 
		}
			 catch (Exception e) {
				 throw new IllegalArgumentException("Error dropping the student");
				 
			 }
			
		
//		 if (s == null) {
//		        throw new IllegalArgumentException("Student cannot be null");
//		 }
//		 boolean throwException = true;
//		 for (int i = 0; i < roll.size(); i++) {
//		    if (s.equals(roll.get(i))) {
//		    	roll.remove(i);
//		    	if (waitlist.size() > 0) {
//		    		enroll(waitlist.dequeue());
//		    	}
//		    	throwException = false;
//		    	break;
//			} else if (throwException && waitlist.contains(s)) {
//				ArrayQueue<Student> p = new ArrayQueue<Student>(waitlist.getCapacity());
//				Student h = null;
//				while (waitlist.size() > 0) {
//					h = waitlist.dequeue();
//					if (!s.equals(h)) {
//						p.enqueue(h);
//					}
//				}
//				waitlist = p;
//				throwException = false;
//			}
//		}
//		if (throwException) throw new IllegalArgumentException("Error dropping the student");
//		
		
	}

	/**
	 * Checks if a student can be enrolled in the course list.
	 * @param s the student to be checked
	 * @return true if the student can be enrolled, false if the student can't
	 */
	public boolean canEnroll(Student s) {
		if(roll.size() + 1 > enrollmentCap && waitlist.size() == 10) {
			return false;
		}
		
		for(int i = 0; i < roll.size(); i++) {
			if(roll.get(i).equals(s)) {
				return false;
			}
		}
		return !waitlist.contains(s);
	}
	
	/**
	 * Returns the amount of seats left open in the course roll.
	 * @return number of students allowed to be added
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Returns the number of students on the wait-list.
	 * @return the number of students on the wait-list
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
