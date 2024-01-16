/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Sets custom message and default message for exception.
 * 
 * @author Nicolas Brechar
 */
public class InvalidTransitionException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets custom message for exception.
	 * @param message custom exception message
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Sets the default exception message.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
}
