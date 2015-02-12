package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Xianghong Sun
 *
 */

class Config{
	public static String host = "169.231.51.149";
	public static String username = "cs48";
	public static String password = "P@ssw0rd";
	public static String table = "golder";
}


public class GolderConnection {
	//variables
	public static Statement stmt = null;
	public static ResultSet rs = null;
	public static Connection conn;
	

	//initial
	static {
		connect();
	}
	
	
	//Functions
	/**
	 * Check connection
	 * @return true if connected; false otherwise
	 */
	public static boolean check() {
		return conn != null;
	}
	
	/**
	 * For Connect or Reconnect
	 */
	public static void connect() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://"
		    		   + Config.host+"/"+ Config.table,
		    		   Config.username, Config.password);
		    stmt = conn.createStatement();
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
