/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * ConflictException is a custom exception class that inherits from Exception. The class
 * provides two constructors one for a default message and one for a provided one. The class has a single
 * field with a default serial version unique identifier.
 * 
 * @author Maxim Shelepov
 */
public class ConflictException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the ConflictException with a provided message to display.
	 * @param message the message to display as part of the exception
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Constructs the ConflictException with a default message of "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
