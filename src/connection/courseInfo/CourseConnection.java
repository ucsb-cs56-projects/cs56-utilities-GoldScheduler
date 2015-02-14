package connection.courseInfo;

import java.sql.SQLException;

import connection.GolderConnection;

/**
 * Connection to golder/courses Table and get data
 * @author Forrest Sun
 * @version Feb 12 2015
 */
public class CourseConnection extends GolderConnection{
	
	public static String[] getMajor() throws SQLException {
		String [] m = null;
		

			
			if (stmt.execute(String.format("SELECT * FROM `depts`;"))) {
		        rs = stmt.getResultSet();
		        
		        rs.last();
		        
		        m = new String[rs.getRow() + 1];
		        
		        rs.beforeFirst();
		        
		        m[0] = "";
		        
		        for (int i = 1; i < m.length; i++) {
		        	rs.next();
		        	m[i] = rs.getString(2) + " (" + rs.getString(1) + ")";
		        }
		        
		    }
 	
		
		return m;
	}
	
	
	
}
