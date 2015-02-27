package connection;

import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Forrest Sun
 * @version Feb 12 2014
 */

class Config{
	public static String host = "169.231.51.149";
	public static String username = "cs48";
	public static String password = "P@ssw0rd";
	public static String table = "golder";
}


public class GolderConnection {
	//variables
	public static Statement stmt;
	public static ResultSet rs;
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
	 * @throws SQLException 
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
		} catch (SQLException e) {
			
			int n = JOptionPane.showConfirmDialog(
					null,
				    "Do you want to reconnect?",
				    "Connection Error",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.ERROR_MESSAGE);
			if (n == 0) connect();
			else System.exit(1);
		}

	}
}
