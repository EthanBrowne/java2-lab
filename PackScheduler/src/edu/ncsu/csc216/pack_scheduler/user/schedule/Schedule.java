/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Schedule is a data object representing a student schedule in the PackScheduler system. The Schedule encapsulates its name and a 
 * custom implementation of ArrayList of Courses as its fields. The object provides add, remove, get, and reset schedule actions. As well as, 
 * get and set actions for name of the schedule. In addition, an ability to validate if the Course can be added to the Schedule.
 * 
 * @author Gabriel Perri
 */
public class Schedule {
	/** custom list of courses **/
	ArrayList<Course> schedule;
	/** schedule title **/
	String title;
	
	/**
	 * Constructs a basic schedule object, setting
	 * title to default and creating a empty
	 * list of courses.
	 */
	public Schedule() {
		//create an empty list of courses
		schedule = new ArrayList<Course>();
		
		//set title to default
		setTitle("My Schedule");
	}
	
	/**
	 * Adds a new course to the schedule after checking
	 * for any conflicts. If the course is already in the schedule
	 * or there is a time conflict, an IllegalArgumentException
	 * is thrown. 
	 * @param newCourse course to be added
	 * @return boolean if course was added successfully
	 * @throws IllegalArgumentException if newCourse has conflict
	 */
	public boolean addCourseToSchedule(Course newCourse) {
		//check if course already exists or has conflict
		for(int i = 0; i < schedule.size(); i++) {
			if(newCourse.equals(schedule.get(i)) || newCourse.getName().compareTo(schedule.get(i).getName()) == 0) {
				throw new IllegalArgumentException("You are already enrolled in " + newCourse.getName());
			}
			else {
				try {
					newCourse.checkConflict(schedule.get(i));
				}
				catch (Exception e) {
					throw new IllegalArgumentException("The course cannot be added due to a conflict");
				}
			}
		}
		
		//if all above passes, add course to schedule
		schedule.add(newCourse);
		return true;
		
	}
	/**
	 * Searches for and deletes a given course
	 * from the schedule
	 * @param removedCourse course to remove
	 * @return boolean if course was removed successfully
	 */
	public boolean removeCourseFromSchedule(Course removedCourse) {
		if(removedCourse != null) {
			for(int i = 0; i < schedule.size(); i++) {
				//check for the course to delete
				if(removedCourse.equals(schedule.get(i))) {
					schedule.remove(i);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Resets the current schedule to a empty
	 * schedule object
	 */
	public void resetSchedule() {
		//resets the schedule to a new schedule
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Returns a string array of course information
	 * from the scheduled courses. 
	 * @return 2d array of course info
	 */
	public String[][] getScheduledCourses() {
		String[][] courseSchedule = new String[this.schedule.size()][4];
		
		if(this.schedule.size() == 0) {
			return courseSchedule;
		}
		
		//fill each row
		for(int i = 0; i < this.schedule.size(); i++) {
			Course c = (Course)schedule.get(i);
			courseSchedule[i] = c.getShortDisplayArray();
		}
		
		//returns full 2d array
		return courseSchedule;
		
	}
	
	/**
	 * Sets the schedule title, if input is null
	 * an IllegalArgumentException is thrown. 
	 * @param newTitle new title of schedule
	 * @throws IllegalArgumentException if title is null
	 */
	public void setTitle(String newTitle) {
		if(newTitle == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = newTitle;
	}
	
	/**
	 * Returns a string of the course title.
	 * @return string of the course title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the cumulative sum of the total 
	 * class credits in the schedule. 
	 * @return total sum of credits
	 */
	public int getScheduleCredits() {
		int sum = 0;
		for(int i = 0; i < this.schedule.size(); i++) {
			sum += this.schedule.get(i).getCredits();
		}
		return sum;
	}
	
	/**
	 * Checks if given course may be added to the schedule. If
	 * the course is null, already exists, or there is a conflict
	 * the method returns false. 
	 * @param c course to be checked for validity
	 * @return true if course can be added and false otherwise
	 */
	public boolean canAdd(Course c) {
		//check for null
		if(c == null) {
			return false;
		}
		//check if course is already in schedule, checking by title, then check for conflict
		for(int i = 0; i < this.schedule.size(); i++) {
			if(c.isDuplicate(this.schedule.get(i))) {
				return false;
			}
			//check for time conflict
			try {
			    this.schedule.get(i).checkConflict(c);
			}
			catch (Exception e){
				return false;
			}
		}
		
		return true;
		
	}
 	
}
