package connection.userInfo;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.GolderConnection;
import Course.*;
/**
 * Database Connection For User Information
 * Registers and retrieves user info
 * @author Forrest Sun
 * @author Wesley Pollek
 * @version Feb 10 2015
 *
 */
public class UsersConnection extends GolderConnection{
	/**
	 * Registration for users
	 * @param username desired Username
	 * @param password desired password
	 * @param email user's email
	 * @param major user's email
	 * @return User ID ID number from created user
	 * @throws SQLException throws exception if user could not be created
	 */
	public static int Register(String username, String password, String email, String major) throws SQLException {
		Statement stmt = conn.createStatement();

		if (email.equals(""))
			email = "null";
		else
			email = "'" + email + '\'';

		if (major.equals(""))
			major = "null";
		else
			major = "'" + major + '\'';
		stmt.executeUpdate(String.format("INSERT INTO users (user_name,user_password,email_address,major) VALUES ('%s','%s',%s,%s);",
					username, password, email, major));
		return getID(username,password);
	}
	/**
	 * Login with username and password
	 * @param username input username
	 * @param password input password to match username
	 * @return User ID ID number for the user
	 * -1 if no username
	 * -2 if wrong password
	 * @throws SQLException  throws exception if connection fails
	 */
	public static int getID(String username, String password) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs;
		int id = 0;
			if (stmt.execute(String.format("SELECT ID, user_password FROM `users` WHERE user_name='%s';", username))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) {
			        if (!rs.getString("user_password").equals(password)) return -2; //password wrong
			        id = rs.getInt("ID");
		        } else {
		        	return -1; //no username
		        }
		    }
		return id;
	}
	/**
	 * Check if email is used
	 * @param email User's email
	 * @return User Id
	 * -1 if no username
	 * @throws SQLException throws exception if connection fails
	 */
	public static int getIdByEmail(String email) throws SQLException {
		int id = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs;
			if (stmt.execute(String.format("SELECT ID FROM `users` WHERE email_address='%s';", email))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) 
			        return rs.getInt("ID");
		        else
		        	return -1; //no username
		    }
		return id;
	}
	/**
	 * get User Infomation
	 * @param ID User ID
	 * @return User
	 * @throws SQLException  throws exception if connection fails
	 */
	public static User getInfo(int ID) throws SQLException{
		User u = null;
		Statement stmt = conn.createStatement();
		ResultSet rs;
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE ID='%s';", ID))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"),
                             rs.getString("email_address"), rs.getString("major"),
                             rs.getInt("ID"));
		    }
		return u;
	}
	/**
	 * Used to reset the password
	 * @param userName Input username
	 * @param email Corresponding email
	 * @param t used to make the signature different
	 * @return User 
	 * @throws SQLException throws exception if connection fails
	 */
	public static User getInfo(String userName, String email, int t) throws SQLException{
		User u = null;
		Statement stmt = conn.createStatement();
		ResultSet rs;
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE user_name='%s' AND email_address='%s';", userName, email))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"), rs.getString("email_address"), rs.getString("major"), rs.getInt("ID"));
		    }
		return u;
	}
	/**
	 * Used to get the user with username and password
	 * @param userName Input username
	 * @param password Corresponding password
	 * @return User
	 * @throws SQLException throws exception if connection fails
	 */
	public static User getInfo(String userName, String password) throws SQLException{
		User u = null;
		Statement stmt = conn.createStatement();
		ResultSet rs;
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE user_name='%s' AND user_password='%s';", userName, password))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"), rs.getString("email_address"), rs.getString("major"), rs.getInt("ID"));
		    }
		return u;
	}
	/**
	 * update password
	 * @param ID User ID number
	 * @param pw new Password
	 * @throws SQLException throws exception if connection fails
	 */
	public static void setPassword(int ID, String pw) throws SQLException {
		Statement stmt = conn.createStatement();

		stmt.executeUpdate(String.format("UPDATE `users` SET user_password='%s' WHERE ID='%s';",
                                         pw,ID));
	}
	/**
	 * update email
	 * @param ID User ID number
	 * @param email new Email
	 * @throws SQLException throws exception if connection fails
	 */
	public static void setEmail(int ID, String email) throws SQLException {
		Statement stmt = conn.createStatement();

		stmt.executeUpdate(String.format("UPDATE `users` SET email_address='%s' WHERE ID='%s';",email,ID));
	}
	/**
	 * update major
	 * @param ID User ID number
	 * @param mj new Email
	 * @throws SQLException throws exception if connection fails
	 */
	public static void setMajor(int ID, String mj) throws SQLException  {
		Statement stmt = conn.createStatement();

		stmt.executeUpdate(String.format("UPDATE `users` SET major='%s' WHERE ID='%s';",mj,ID));
	}
	/**
	 * Save the User Data
	 * @param u User
	 * @param c Course to be added
     * @throws SQLException throws exception if course could not be saved
	 */
	public static void saveCourse(User u, Course c) throws SQLException {
		Statement stmt = conn.createStatement();
		int sectionID;
		int sectionColor;

		int userId = u.getID();
		int lectureID = c.getLect().id;
		int lectureColor = c.getLect().col.getRGB();
		if (c.getSect() == null) {
			sectionID = 0;
			sectionColor = 0;
		} else {
			sectionID = c.getSect().id;
			sectionColor = c.getSect().col.getRGB();
		}
			stmt.executeUpdate(String.format("INSERT INTO `student_schedule` (user_id,lecture_id,lecture_color,section_id,section_color) VALUES (%s,%s,%s,%s,%s);", 
					userId, lectureID, lectureColor, sectionID,sectionColor));
	}
	/**
	 * delete single course in the schedule
	 * @param u User
	 * @param c Course to be deleted
	 * @throws SQLException throws exception if connection fails
	 */
	public static void deleteCourse(User u, Course c) throws SQLException {
		Statement stmt = conn.createStatement();
        int userId = u.getID();
		int lectureID = c.getLect().id;
		int sectionID = c.getSect().id;
		try {
			stmt.executeUpdate(String.format("DELETE FROM `student_schedule` WHERE user_id=%s AND lecture_id=%s AND section_id=%s;", 
					userId, lectureID, sectionID));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * get all the schedules saved in DB
	 * @param u User
	 * @return An ArrayList containing all the Courses in the user's schedule
	 * @throws SQLException throws exception if connection fails
	 */
	public static ArrayList<Course> getSchedule(User u) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs;
		ArrayList<Course> ca = new ArrayList<Course>();
		if (stmt.execute(String.format("SELECT * FROM `student_schedule` WHERE user_id = %s;", u.getID()))) {
			rs = stmt.getResultSet();
			rs.last();
			int [][] m = new int[rs.getRow()][4];
			rs.beforeFirst();
			for  (int i = 0; i < m.length; i++) {
				rs.next();
				m[i][0] = rs.getInt("lecture_id");
				m[i][1] = rs.getInt("section_id");
				m[i][2] = rs.getInt("lecture_color");
				m[i][3] = rs.getInt("section_color");
        	}
			for  (int i = 0; i < m.length; i++) 
				ca.add(connection.courseInfo.CourseConnection.getCourse(m[i][0],m[i][1],m[i][2],m[i][3]));
		}
		return ca;
	}
	/**
	 * delete entire schedule 
	 * @param u User
	 * @throws SQLException throws exception if connection fails
	 */
	public static void deleteSchedule(User u) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(String.format("DELETE FROM `student_schedule` WHERE user_id=%s;", u.getID()));
	}
}
