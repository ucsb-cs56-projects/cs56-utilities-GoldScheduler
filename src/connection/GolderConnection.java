package connection;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

/**
 * A class to hold the configuration information for a particular database
 */
class Config{
	public static String host;
	public static String username;
	public static String password;
	public static String table;
}
/**
 * A class that is used to establish a connection with the Golder Database
 */
public class GolderConnection {
	//variables
	public static Statement stmt;
	public static ResultSet rs;
	public static Connection conn;
	// A static initialization for all the static variables
	static {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("config/config.config"));
			Config.host = br.readLine();
			Config.username =  br.readLine();
			Config.password = br.readLine();
			Config.table = br.readLine();
	        br.close();
	    } catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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