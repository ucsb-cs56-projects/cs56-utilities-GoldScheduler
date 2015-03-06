package connection.userInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import Course.Course;
/**
 * A class used to represent a particular user. Contains user information
 * @author Forrest Sun
 * @author Wesley Pollek
 * @version Feb 10 2015
 */
public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private String major;
	/**
	 * Constructor Only can be called by UsersConnection.getInfo
	 * @param u  username
	 * @param p  password
	 * @param e  email
	 * @param m  major
	 * @param id Id
	 */
	User(String u, String p, String e, String m, int id){
		username=u;
		password=p;
		email=e;
		major=m;
		this.id=id;
	}
    /**
     @return username
     */
	public String getUsername(){return this.username;}
    /**
     @return password
     */
	public String getPassword(){return this.password;}
    /**
     @return email
     */
	public String getEmail(){return this.email;}
    /**
     @return major
     */
	public String getMajor(){return this.major;}
    /**
     @return id
     */
	public int getID(){return this.id;}
    /**
     @param password the password the user wishes to be used
     */
	public void setPassword(String password) throws SQLException{
		this.password=password;
		UsersConnection.setPassword(id, password);
	}
    /**
     @param email the email the user wishes to be used
     */
	public void setEmail(String email) throws SQLException{
		this.email=email;
		UsersConnection.setEmail(id, email);
	}
    /**
     @param major the major the user wishes to be used
     */
	public void setMajor(String major) throws SQLException{
		this.major=major;
		UsersConnection.setMajor(id, major);
	}
    /**
     Adds course to a user, so the schedule can be saved
     @param c The course to be added
     */
	public void addCourse(Course c) throws SQLException {
		UsersConnection.saveCourse(this, c);
	}
    /**
     Deletes course from a user
     @param c The course to be deleted
     */
	public void deleteCourse(Course c) throws SQLException {
		UsersConnection.deleteCourse(this, c);
	}
	/**
     @return an ArrayList of all the courses that belong to a user
     */
	public ArrayList<Course> getSchedule() throws SQLException {
		return UsersConnection.getSchedule(this);
	}
    /**
     Deletes all courses that belong to a user
     */
	public void deleteSchedule() throws SQLException {
		UsersConnection.deleteSchedule(this);
	}
}
