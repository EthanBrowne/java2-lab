/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activity is an abstract class which represents any activity, such as courses and events, within the WolfScheduler system.
 * 
 * The Activity class encapsulates the shared fields of the Course and Event objects, in addition to 
 * the accessors and modifiers for those fields. The class also provides three abstract methods for required implementation in the child classes
 * (Course, Event):
 * <ul>
 * <li><code>getShortDisplayArray()</code>
 * <li><code>getLongDisplayArray()</code>
 * <li><code>isDuplicate()</code>
 * </ul>
 * 
 * The class also implements the Conflict interface which provides a structure for dealing with conflicting activities in the schedule.
 * 
 * @author Maxim Shelepov
 */
public abstract class Activity implements Conflict {
	/** Activity title. */
	private String title;
	/** Activity meeting days (days of the week) */
	private String meetingDays;
	/** Activity starting time in military time */
	private int startTime;
	/** Activity ending time in military time */
	private int endTime;
	/** Activity start and end times maximum hour */
	private static final int UPPER_HOUR = 23;
	/** Activity start and end times maximum minute */
	private static final int UPPER_MINUTE = 59;
	
	/**
	 * Constructs the shared abstract fields title, meeting days, start time, and end time for any
	 * Activity class child.
	 * 
	 * @param title the title of the Activity
	 * @param meetingDays the meeting days for the Activity (days of the week in a series of chars)
	 * @param startTime the start time for the Activity
	 * @param endTime the end time for the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Checks if the provided Activity is conflicting with this Activity. The two objects
	 * are conflicting if the day and time (by at least one minute) are overlapping.
	 * For example, Activity with meeting days Monday 2:45PM-3:00PM would overlap with Activity which has
	 * Monday 3:00PM-4:00PM meeting days.
	 * 
	 * Required as part implementation of the Conflict Interface.
	 * 
	 * @param possibleConflictingActivity the possibly conflicting Activity with this Activity
	 * @throws ConflictException a custom exception which indicates the provided and this Activity are conflicting.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String possiblyConflictingDays = possibleConflictingActivity.getMeetingDays();
		
		if (!"A".equals(meetingDays) || !"A".equals(possiblyConflictingDays)) {
			boolean matchingDay = false;
			for (int i = 0; i < possiblyConflictingDays.length(); i++) {
				if (meetingDays.indexOf(possiblyConflictingDays.charAt(i)) != -1) {
					matchingDay = true;
					break;
				}
			}
			
			if (matchingDay && 
				possibleConflictingActivity.getStartTime() <= endTime && 
				startTime <= possibleConflictingActivity.getEndTime()
			) {
				throw new ConflictException();
			}
		}
 	}

	/**
	 * Should return a String array containing some identifying model objects fields to display
	 * in the UI.
	 * 
	 * Note: Implementation dependent on child class of Activity. For example, Course object uses
	 * 4 fields to identify the course and display to the user.
	 *  
	 * @return shortened String array representation of data model
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Should return a String array containing most or all model objects fields to display
	 * to the user in the UI. 
	 * 
	 * Note: Implementation dependent on child class of Activity. For example, Course object uses
	 * 6 fields, a more comprehensive amount than the <code>getShortDisplayArray()</code>, to display in the UI.
	 * 
	 * @return longer String array representation of data model
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Should return a boolean indicating if a duplicate of a provided Activity exists in the schedule. For
	 * conflict detection.
	 * 
	 * @param activity the Activity to check for duplicate
	 * @return true if Activity is a duplicate and false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns the Activity title.
	 * @return the title (full name) of the Activity
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity title.
	 * 
	 * If the provided title meets the criteria: is not null or empty. Otherwise throws an
	 * IllegalArgumentException.
	 * @param title the title to set
	 * @throws IllegalArgumentException if the provided title parameter is invalid
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		this.title = title;
	}

	/**
	 * Returns the Activity meeting days.
	 * @return the meeting days for the Activity
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity start time.
	 * @return the start time for the Activity
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity end time.
	 * @return the end time for the Activity
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Get the Activity meeting day, start, and end times formatted as a single string. In case of 'A' meeting day,
	 * returns "Arranged".
	 * 
	 * Note: The times are displayed in standard format. 
	 * @return meeting days with start and end time in standard time format as a String or "Arranged" in the case of 'A' meeting day
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		
		return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}

	/**
	 * Set the Activity meeting days, start time, and end time.
	 * 
	 * If the provided meeting days, start time, and end time meet the criteria:
	 * <ul>
	 * <li>the meeting days is not null or empty
	 * <li>the start and end time is in valid military time format.
	 * <li>start time should not exceed end time.
	 * </ul>
	 * <p>
	 * Otherwise throws an IllegalArgumentException.
	 * @param meetingDays the meeting days for the Activity
	 * @param startTime the start time for the Activity
	 * @param endTime the end time for the Activity
	 * @throws IllegalArgumentException if the provided meeting days, start time, or end time parameters are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// break apart time into hours and minutes
		int startHour = getHourAndMinute(startTime)[0];
		int startMin = getHourAndMinute(startTime)[1];
		
		int endHour = getHourAndMinute(endTime)[0];
		int endMin = getHourAndMinute(endTime)[1];
		
		// validate the start and end times
		if (startHour < 0 || startHour > UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if (startMin < 0 || startMin > UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if (endHour < 0 || endHour > UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if (endMin < 0 || endMin > UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// make sure start time doesn't exceed the end time
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Helper method to extract the hour and minute values from a military time format integer.
	 * @param time the time in military time format
	 * @return hours and minutes for the given time in array format: [hours, minutes]
	 */
	private int[] getHourAndMinute(int time) {
		int[] hoursAndMinutes = new int[2];
		hoursAndMinutes[0] = time / 100;
		hoursAndMinutes[1] = time % 100;
		
		return hoursAndMinutes;
	}

	/**
	 * Helper method to generate a standard time format string from military time format integer.
	 * @param time the time in military time format 
	 * @return the time string in standard time format
	 */
	private String getTimeString(int time) {
		int hour = getHourAndMinute(time)[0];
		int minute = getHourAndMinute(time)[1];
		String timeOfDay = "AM";
		
		if (hour == 0) {
			hour = 12;
		} else if (hour == 12) {
			timeOfDay = "PM";
		} else if (hour > 12) {
			hour -= 12;
			timeOfDay = "PM";
		}
		
		return hour + ":" + (minute < 10 ? "0" : "") + minute + timeOfDay;
	}
	
	/**
	 * Generates a hash code for the Activity using all fields.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	/**
	 * Compares this Activity object to provided object for equality in all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}