package connection.courseInfo;


import java.sql.SQLException;

import connection.GolderConnection;


public class CourseConnection extends GolderConnection{
	
	public static String[] getMajor() {
		String [] m = null;
		
		try {
			
			if (stmt.execute(String.format("SELECT * FROM `depts`;"))) {
		        rs = stmt.getResultSet();
		        
		        rs.last();
		        
		        m = new String[rs.getRow()];
		        
		        rs.beforeFirst();
		        
		        for (int i = 0; i < m.length; i++) {
		        	rs.next();
		        	m[i] = rs.getString(2) + " (" + rs.getString(1) + ")";
		        }
		        
		    }
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());

		} 	
		
		return m;
	}
	
	
	
}