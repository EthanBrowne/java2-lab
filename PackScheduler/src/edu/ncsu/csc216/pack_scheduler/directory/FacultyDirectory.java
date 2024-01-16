/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Faculty Directory Class
 */
public class FacultyDirectory {
	/** Constant that Represents the hash algorithm*/
	static final String HASH_ALGORITHM = "SHA-256";
	
	/** Linked List that represents the faculty directory for the university*/
	private LinkedList<Faculty> faculty;
	
	/**
	 * Constructor for FacultyDirectory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	/**
	 * Creates a new FacultyDirectory
	 */
	public void newFacultyDirectory() {
		faculty = new LinkedList<Faculty>();
	}
	/**
	 * Reads faculty members from a file and adds them to the linkedList
	 * @param fileName the name of the file
	 * @throws IllegalArgumentException if file is not found
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			faculty = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	/**
	 * Constructs a faculty member and adds it to list
	 * @throws IllegalArgumentException if Faculty member cannot be constructed
	 * @param firstName the first name of the faculty member
	 * @param lastName the last name of the faculty member
	 * @param id the id of the faculty member
	 * @param email the email of the faculty member
	 * @param password the password of the faculty member
	 * @param repeatPassword the repeated password of the faculty member
	 * @param maxCourses the maximum amount of courses that a faculty member can have
	 * @return if the faculty member is added
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
		String hashPW;
		String repeatHashPW;
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			hashPW = hashString(password);
			repeatHashPW = hashString(repeatPassword);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid password");
		}
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		Faculty f = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		return faculty.add(f);
	}
	/**
	 * Removes corresponding faculty member
	 * @param name name of the faculty member to remove
	 * @return if the faculty member was removed
	 */
	public boolean removeFaculty(String name) {
		for (int i = 0; i < faculty.size(); i++) {
			if (faculty.get(i).getId().equals(name)) {
				faculty.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns members of faculty directory
	 * @return String[][] of the faculty members
	 */
	public String[][] getFacultyDirectory(){
		String[][] stringList = new String[faculty.size()][3];
		for (int i = 0; i < faculty.size(); i++) {
			Faculty f = faculty.get(i);
			stringList[i][0] = f.getFirstName();
			stringList[i][1] = f.getLastName();
			stringList[i][2] = f.getId();
		}
		return stringList;
	}
	/**
	 * Saves the faculty directory to a file
	 * @param fileName the name of the file to save to
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, faculty);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	/**
	 * Returns a faculty member with a given id
	 * @param id the id of the faculty member to return
	 * @return Faculty member with the id
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < faculty.size(); i++) {
			if (id.equals(faculty.get(i).getId())) {
		        return faculty.get(i);
		    }
		}
		return null;
	}

	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if cannot hash password
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
}
