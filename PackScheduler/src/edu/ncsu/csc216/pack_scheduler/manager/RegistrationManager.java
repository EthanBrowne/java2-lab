package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * RegistrationManager is a singleton object that represents registration system for (Wolf)PackScheduler.
 * The object encapsulates the single instance of itself (singleton); catalog of Courses; directory of all Students. In addition,
 * the object holds a reference to the currently logged-in (active) User (Student or Registrar) and single Registrar.
 * 
 * The objects functionalities include:
 * <ul>
 * <li> Create a registrar.
 * <li> Retrieve the Course catalog.
 * <li> Retrieve the Student directory.
 * <li> Login and logout.
 * <li> Get current logged in User.
 * <li> Enroll a Student in a Course.
 * <li> Drop a Student from a Course.
 * <li> Reset the Student Schedule.
 * <li> Clear Course catalog and Student directory.
 * </ul>
 * 
 * @author Dr. Sarah Heckman
 * @author Maxim Shelepov
 * @author Gabriel Perri
 */
public class RegistrationManager {
	/** Singleton instance of this object */
	private static RegistrationManager instance;
	/** Full catalog of Courses */
	private CourseCatalog courseCatalog;
	/** Directory of all Students */
	private StudentDirectory studentDirectory;
	/** User that is an authenticated Registrar */
	private User registrar;
	/** Current logged in User (Student) */
	private User currentUser;
	/** Instance for the faculty directory */
	private FacultyDirectory faculty;
	
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** File name of the properties file with Registrar information */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructs the singleton instance of the RegistrationManager. Registers the Registrar and instantiates
	 * a fresh Courses catalog, Student directory and Faculty directory.
	 */
	private RegistrationManager() {
		createRegistrar();
		// initialize fields
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		faculty = new FacultyDirectory();
	}
	
	/**
	 * Creates the System Registrar using the hidden env file properties: first name, last name,
	 * id, email, and hashed password.
	 * @throws IllegalArgumentException if cannot create the Registrar.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Helper method which hashes the User object passwords fields.
	 * @param pw the unprotected password of the User to hash
	 * @return the hashed password
	 * @throws IllegalArgumentException if unable to hash password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Returns the singleton instance of this object. If null, instantiates one.
	 * @return the singleton instance of this object.
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the catalog of Courses.
	 * @return the object CourseCatalog representing the catalog of courses
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the directory of Students.
	 * @return the object StudentDirectory representing the directory of students
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Returns the directory of Faculty.
	 * @return the object FacultyDirectory representing the directory of faculty
	 */
	public FacultyDirectory getFacultyDirectory() {
		return faculty;
	}
	
	/**
	 * Adds a faculty from a course.
	 * @param c the course to add faculty
	 * @param f the faculty to add to course
	 * @return true if the faculty was successfully added, false if not added
	 * @throws IllegalArgumentException If user is not a Registrar
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if(currentUser == null) {
			return false;
		}
		else if(currentUser != registrar) {
			throw new IllegalArgumentException();
		}
		
		try {
			FacultySchedule fs = f.getSchedule();
			fs.addCourseToSchedule(c);
			return true;
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Illegal Action");
		}
	}
	
	/**
	 * Removes a faculty from a course.
	 * @param c the course to remove faculty from
	 * @param f the faculty to remove from course
	 * @return true if the faculty was successfully removed, false if not removed
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if(currentUser == null) {
			return false;
		}
		else if(currentUser != registrar) {
			throw new IllegalArgumentException();
		}

		FacultySchedule fs = f.getSchedule();
		return fs.removeCourseFromSchedule(c);
		
	}
	
	/**
	 * Resets the faculty schedule given a faculty.
	 * @param f the faculty to get the faculty schedule from
	 */
	public void resetFacultySchedule(Faculty f) {
		if(currentUser == null) {
			throw new IllegalArgumentException("Illegal Action");
		}
		else if(currentUser != registrar) {
			throw new IllegalArgumentException("Illegal Action");
		}

		FacultySchedule fs = f.getSchedule();
		fs.resetSchedule();
	}
	
	/**
	 * Logs-in a User using the provided id and password. Sets as the current system User.
	 * If the User id matches but the password has doesn not, the login is not successful. If
	 * the User id doesn't match an IllegalArgumentException is thrown with "User doesn't exist.".
	 * @param id the id of the User to login
	 * @param password the hashed password of the User
	 * @return true if the login was successful, false otherwise
	 * @throws IllegalArgumentException if the user doesn't exist
	 */
	public boolean login(String id, String password) {
		String localHashPW = hashPW(password);
		Student stu = studentDirectory.getStudentById(id);
		Faculty fac = faculty.getFacultyById(id);
		
		if(currentUser != null) {
			return false;
		}
		if (stu != null) {
			if (stu.getPassword().equals(localHashPW)) {
				currentUser = stu;
				return true;
			}
			return false;
		}
		else if (fac !=  null) {
			if(fac.getPassword().equals(localHashPW)){ 
				currentUser = fac;
				return true;
			}
		  return false;
		}
		if (id.equals(registrar.getId())) {
			if (currentUser == null && localHashPW.equals(registrar.getPassword())) {
				currentUser = registrar;
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("User doesn't exist.");
	}
	
	/**
	 * Logs-out the current User by setting the current User to the Registrar.
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Returns the current logged in User.
	 * @return the User which is the current logged in User
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Clears the course catalog and student directory.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		faculty.newFacultyDirectory();
	}
	
	/**
	 * Registrar is a private static object which represents a registrar in the system.
	 * Inherits from the User and constructs an object using the shared fields with the super class.
	 * 
	 * @author Dr. Heckman
	 */
	private static class Registrar extends User {
		/**
		 * Construct a private static Registrar User only available within the RegistrationManager class.\
		 * @param firstName the first name of the Registrar User
		 * @param lastName the last name of the Registrar User
		 * @param id the id of the Registrar User
		 * @param email the email of the Registrar User
		 * @param hashPW the hashed password of the Registrar User
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if illegal action by current user
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}


	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if illegal action by current user
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 * @throws IllegalArgumentException if illegal action by current user
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
}