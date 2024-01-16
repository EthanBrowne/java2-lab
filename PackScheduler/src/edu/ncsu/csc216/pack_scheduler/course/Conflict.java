/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Conflict is an interface class which provides the required methods for dealing with conflicts. 
 * One such class is the Activity object which could have conflicts
 * in the day of the week and times.
 * 
 * The interface requires the following methods for implementation to determine if two objects are 
 * conflicting: <code>checkConflict()</code>.
 * 
 * @author Maxim Shelepov
 */
public interface Conflict {
	/**
	 * Required to check if the provided Activity is conflicting with any other Activity in the schedule. If so,
	 * throws a custom ConflictException to indicate the conflict.
	 * @param possibleConflictingActivity provided Activity to check for conflict
	 * @throws ConflictException a custom Exception to indicate a conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
