package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * User is a abstract class which represents any user with access to PackScheduler system. The class encapsulates
 * the common fields needed for any type of User (Student or Registrar): first name, last name, unique user identifier, 
 * student email, and hashed password. The class provides the standard getter and setter behavior for those fields. In addition to
 * hashcode and equals for object comparison.
 * 
 * @author Nick Bechar
 */
public abstract class User {

	/** user's first name */
	private String firstName;
	/** user's last name */
	private String lastName;
	/** unique identifier of the user */
	private String id;
	/** user's email */
	private String email;
	/** user's hashed password */
	private String password;
	
	/**
	 * The default constructor for the User class.
	 * @param firstName the first name of the student
	 * @param lastName the last name of the student
	 * @param id the id of the student
	 * @param email the email of the student
	 * @param password the password of the student
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Returns the first name of the user.
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student if not null or empty.
	 * @param firstName the student's first name
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setFirstName(String firstName) {
		if(firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
	
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the user.
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user if not null or empty.
	 * @param lastName the last name of the student.
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
	
		this.lastName = lastName;
	
	}

	/**
	 * Returns the user's id.
	 * @return the student's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the user's unique identifier if not null or empty.
	 * @param id the student id.
	 * @throws IllegalArgumentException if null or empty
	 */
	protected void setId(String id) {
		if(id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
	
		this.id = id;
	}

	/**
	 * Returns the users's email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email if not null, empty, and contains "@" and "." in that order.
	 * @param email the email of the student.
	 * @throws IllegalArgumentException if null, empty, doesn't contain @ or ., and 
	 * doesn't contain last . after the first @ sign.
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		int atIndex = email.indexOf("@");
		int dotIndex = email.lastIndexOf(".");
	
		if (atIndex == -1 || dotIndex == -1 || dotIndex < atIndex) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		this.email = email;
	}

	/**
	 * Returns the users password.
	 * @return the student's hashed password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the users password if not null or empty.
	 * @param password the student's hashed password
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
	
		this.password = password;
	
	}

	/**
	 * Generates the hash code for User.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * Compares an object to the current instance of User.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	
}