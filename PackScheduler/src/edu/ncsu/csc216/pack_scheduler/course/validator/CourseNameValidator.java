/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * CourseNameValidator is an object that provides a method to validate a course name. The object implements the State Pattern
 * with 4 concrete states: Initial, Letter, Digit, and Suffix. The object can be in only one state at a time, beginning with the 
 * initial state and ending in valid digit or suffix state.
 * 
 * @author Maxim Shelepov
 */
public class CourseNameValidator {
	/** A FSM has reached a valid end state */
	private boolean validEndState;
	
	/** The letter counter of name under validation */
	private int letterCount;
	/** The letter counter of name under validation */
	private int digitCount;
	
	/** The initial state */
	private final State initialState = new InitialState();
	/** The letter state */
	private final State letterState = new LetterState();
	/** The digit state */
	private final State digitState = new DigitState();
	/** The suffix state */
	private final State suffixState = new SuffixState();
	/** The current state */
	private State currState;
	
	/**
	 * Indicates whether a course name is valid or not. The course name should follow the pattern (in this order):
	 * <ul>
	 * <li>At least 1 and at most 4 letters.
	 * <li>Exactly 3 digits.
	 * <li>Optional letter suffix.
	 * </ul>
	 * Notes: Spaces are not allowed.
	 * @param courseName the name of the Course to validate
	 * @return true indicate a Course name is valid and follows allowed pattern
	 * @throws InvalidTransitionException if a transition is disallowed
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		// Reset values
		currState = initialState;
		letterCount = 0;
		digitCount = 0;
		
		int charIndex = 0;
		char c;
		while (charIndex < courseName.length()) {
			c = courseName.charAt(charIndex);
			
			if (Character.isLetter(c)) {
				currState.onLetter();
			} else if (Character.isDigit(c)) {
				currState.onDigit();
			} else {
				currState.onOther();
			}
			charIndex++;
		}
		
		return validEndState;
	}
	
	/**
	 * The State abstract class which provides the method state transition blueprint and shared behavior
	 * through incorrect transition. The class requires its classes to implement:
	 * <ul>
	 * <li><code>onLetter()</code>
	 * <li><code>onDigit()</code>
	 * </ul>
	 * The class provides a <code>onOther()</code> shared method which executes the common response for all states
	 * transition to an incorrect state.
	 * 
	 * @author Maxim Shelepov
	 */
	private abstract class State {
		/**
		 * Require transition implementation for moving to letter state from particular state.
		 * @throws InvalidTransitionException if conditions not met for transition to letter state
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * Require transition implementation for moving to digit state from particular state.
		 * @throws InvalidTransitionException if conditions not met for transition to digit state
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Common response to an invalid character transition
		 * @throws InvalidTransitionException which states that the Course name can only have letters and digits
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * The initial state for a Course Name and must transition to a letter as its the first required
	 * state.
	 * 
	 * @author Maxim Shelepov
	 */
	private class InitialState extends State {
		/**
		 * Transition to letter state.
		 */
		@Override
		public void onLetter() {
			currState = letterState;
			letterCount++;
		}

		/**
		 * Disallow transition to digit state.
		 * @throws InvalidTransitionException due to this transition being not allowed
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
			
		}
		
	}
	
	/**
	 * The required letter state which has to occur at least 1 and at most 4 times sequentially in the beginning 
	 * of a valid Course name.
	 * 
	 * @author Maxim Shelepov
	 */
	private class LetterState extends State {
		/** The maximum prefix letters allowed in course name */
		private static final int MAX_PREFIX_LETTERS = 4;
		
		/**
		 * Transition to letter state if below max prefix of letters.
		 * @throws InvalidTransitionException if more than the max prefix letters
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_PREFIX_LETTERS) {
				currState = letterState;
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
			
		}

		/**
		 * Transition to digit state.
		 */
		@Override
		public void onDigit() {
			currState = digitState;
			digitCount++;
		}
		
	}
	
	/**
	 * The required digit state which has to occur exactly 3 times sequentially in a valid Course name
	 * following 1-4 letters.
	 * 
	 * @author Maxim Shelepov
	 */
	private class DigitState extends State {
		/** The exact number of course digits in course name */
		private static final int COURSE_NUMBER_LENGTH = 3;
		
		/**
		 * Transition to suffix state above course digits length, otherwise throw
		 * an InvalidTransitionException.
		 * 
		 * @throws InvalidTransitionException if below the course digit length
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currState = suffixState;
				validEndState = true;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		/**
		 * Transition to digit state if less than the course digits length.
		 * If transitioning to final digit state, mark as a valid state. Otherwise,
		 * thrown an InvalidTransitionException.
		 * 
		 * @throws InvalidTransitionException if above the course number length.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH - 1) {
				validEndState = true;
			} 
			
			if (digitCount < COURSE_NUMBER_LENGTH) {
				currState = digitState;
				digitCount++;
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
		
	}
	
	/**
	 * The optional suffix state which is one of the valid end states for a valid Course name.
	 * 
	 * @author Maxim Shelepov
	 */
	private class SuffixState extends State {
		/**
		 * Disallow transition to letter state.
		 * 
		 * @throws InvalidTransitionException because this transition 
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
			
		}

		/**
		 * Disallow transition to digit state.
		 * 
		 * @throws InvalidTransitionException due to this transition being not allowed
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
		
	}
	
	
	
}
