package Connection.UserInfo;

import java.sql.*;
//import Course;

/**
 * Database Connection For User Information
 * @author Forrest
 *
 */
public class UsersConnection {
	
	/**
	 * Check connection
	 * @return true if connected; false otherwise
	 */
	static boolean check() {
		return conn != null;
	}
	
	/**
	 * Registration for users
	 * @param username
	 * @param password
	 * @param email
	 * @param major
	 * @return User ID
	 */
	static int Register(String username, String password, String email, String major) {
		if (email == "")
			email = "null";
		else
			email = "'" + email + '\'';

		if (major == "")
			major = "null";
		else
			major = "'" + major + '\'';
		
		try {
			stmt = conn.createStatement();
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
	static int getID(String username, String password) {
		int id = 0;
		
		try {
			stmt = conn.createStatement();
			
			if (stmt.execute(String.format("SELECT ID, user_password FROM `users` WHERE user_name='%s';", username))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) {
			        if (rs.getString("user_password") == password) return -2; //password wrong
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
	static int getIdByEmail(String email) {
		int id = 0;
		
		try {
			stmt = conn.createStatement();
			
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
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection conn;
	static {
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://"+Config.host+"/"+ Config.table,
		    		   Config.username, Config.password);
		
		    // Do something with the Connection
		    
		
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} 
	}
}