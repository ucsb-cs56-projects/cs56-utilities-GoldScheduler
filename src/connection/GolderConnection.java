package connection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 * @author Forrest Sun
 * @version Feb 12 2014
 */

/**
 * A class to hold the configuration information for a particular database
 */
class Config{
	public static String path;
	public static String table;
}
/**
 * A class that is used to establish a connection with the Golder Database
 */
public class GolderConnection {
	//variables

	public static Connection conn;
	// A static initialization for all the static variables
	static{
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("config/config.config"));
			Config.path = br.readLine();
			Config.table = br.readLine();
	        br.close();
	    } catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        try{
            connect();
        } catch(SQLException ex){
			ex.printStackTrace();
            int n = JOptionPane.showConfirmDialog(
                                                  null,
                                                  "Do you want to reconnect?",
                                                  "Connection Error",
                                                  JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
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
	 * For Connection or Reconnection
	 * @throws SQLException throws exception if a connection could not be made
	 */
	public static void connect() throws SQLException{
		// try {
		//     Class.forName("org.xerial.jdbc.Driver");
		// } catch (ClassNotFoundException e) {
		//     throw new RuntimeException("Cannot find the driver in the classpath!", e);
		// }
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + Config.path);


		} catch (SQLException e) {
			e.printStackTrace();
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
