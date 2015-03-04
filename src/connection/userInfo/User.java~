package connection.userInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import Course.Course;

//import Course

/**
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
//	private LinkedList<Schedule> mySchedules;
	
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
	
	public String getUsername(){return this.username;}
	public String getPassword(){return this.password;}
	public String getEmail(){return this.email;}
	public String getMajor(){return this.major;}
	public int getID(){return this.id;}

	public void setPassword(String password) throws SQLException{
		this.password=password;
		UsersConnection.setPassword(id, password);
	}
	
	public void setEmail(String email) throws SQLException{
		this.email=email;
		UsersConnection.setEmail(id, email);
	}
	
	public void setMajor(String major) throws SQLException{
		this.major=major;
		UsersConnection.setMajor(id, major);
	}
	

	public void addCourse(Course c) throws SQLException {
		UsersConnection.saveCourse(this, c);
	}
	
	public void deleteCourse(Course c) throws SQLException {
		UsersConnection.deleteCourse(this, c);
	}
	
	public ArrayList<Course> getSchedule() throws SQLException {
		return UsersConnection.getSchedule(this);
	}
	
	public void deleteSchedule() throws SQLException {
		UsersConnection.deleteSchedule(this);
	}
	
	/*
	public void addCourse(Schedule s, Course c) {
		
	}
	 */
}
