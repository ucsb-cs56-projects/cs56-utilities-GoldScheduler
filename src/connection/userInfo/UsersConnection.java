package connection.userInfo;

import java.sql.SQLException;

import connection.GolderConnection;

//import Course;

/**
 * Database Connection For User Information
 * @author Forrest Sun
 * @author Wesley Pollek
 * @version Feb 10 2015
 *
 */
public class UsersConnection extends GolderConnection{
	
	
	
	/**
	 * Registration for users
	 * @param username
	 * @param password
	 * @param email
	 * @param major
	 * @return User ID
	 */
	public static int Register(String username, String password, String email, String major) {
		if (email.equals(""))
			email = "null";
		else
			email = "'" + email + '\'';

		if (major.equals(""))
			major = "null";
		else
			major = "'" + major + '\'';

		try {
			
			stmt.executeUpdate(String.format("INSERT INTO users (user_name,user_password,email_address,major) VALUES ('%s','%s',%s,%s);", 
					username, password, email, major));

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} 
		
		
		return getID(username,password);
	}	
	
	/**
	 * Login with username and password
	 * @param username
	 * @param password
	 * @return User ID 
	 * -3 if no connection
	 * -1 if no username
	 * -2 if wrong password
	 */
	public static int getID(String username, String password) {
		int id = 0;
		
		try {
			
			if (stmt.execute(String.format("SELECT ID, user_password FROM `users` WHERE user_name='%s';", username))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) {
			        if (!rs.getString("user_password").equals(password)) return -2; //password wrong
			        id = rs.getInt("ID");
		        } else {
		        	return -1; //no username
		        }
		    }
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    id = -3;
		} 	
		
		
		return id;
		
	}
	
	/**
	 * Check if email is used
	 * @param email
	 * @return User Id
	 * -3 if no connection
	 * -1 if no username
	 */
	public static int getIdByEmail(String email) {
		int id = 0;
		
		try {
			
			if (stmt.execute(String.format("SELECT ID FROM `users` WHERE email_address='%s';", email))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) 
			        return rs.getInt("ID");
		        else
		        	return -1; //no username
		        
		    }

		} catch (SQLException e){
			System.out.println(e.getMessage());
			id = -3;
		}
		
		return id;
	}
	
	/**
	 * get User Infomation
	 * @param ID User ID
	 * @return User
	 */
	public static User getInfo(int ID){
		
		User u = null;
		
		try {
			
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE ID='%s';", ID))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"), rs.getString("email_address"), rs.getString("major"), rs.getInt("ID"));
		    }

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return u;
	}	
	
	/**
	 * update password
	 * @param ID
	 * @param pw
	 */
	public static void setPassword(int ID, String pw) {
		
		try {
			stmt.executeUpdate(String.format("UPDATE `users` SET user_password='%s' WHERE ID='%s';",pw,ID));
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * update email
	 * @param ID
	 * @param email
	 */
	public static void setEmail(int ID, String email) {
		try {
			stmt.executeUpdate(String.format("UPDATE `users` SET email_address='%s' WHERE ID='%s';",email,ID));
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * update major
	 * @param ID
	 * @param mj
	 */
	public static void setMajor(int ID, String mj)  {
		try {
			stmt.executeUpdate(String.format("UPDATE `users` SET major='%s' WHERE ID='%s';",mj,ID));
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	

	
	/*
	 * test 
	 
	
	public static void main(String[] args) {
		System.out.println(check());
	}
	*/
	

}
