package connection.courseInfo;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;

import Course.Course;
import Course.Lecture;
import connection.GolderConnection;

/**
 * Connection to golder/courses Table and get data
 * @author Forrest Sun
 * @version Feb 12 2015
 */
public class CourseConnection extends GolderConnection{
	/*
	 * get an array of String of all Majors
	 */
	
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
	
	
	public static ArrayList<Course> SearchFullTitle(String s) throws SQLException{
		ResultSet rsL;
		Statement stmt2 = conn.createStatement();
		
		ArrayList<Course> ca = new ArrayList<Course>();

		
		if (stmt.execute(String.format("SELECT * FROM `courses` WHERE description LIKE '%%%s%%';", s))) {
				rs = stmt.getResultSet();
		        
		        
		        
		        
		        while (rs.next()) {
		        	
		        	
		        	
		        	
			        if (stmt2.execute(String.format("SELECT * FROM `spring_15` WHERE course_name = '%s' AND corresponding_id = 0;", rs.getString("course_name")))) {
			        	
				        
			        	
			        	rsL= stmt2.getResultSet();
			        	

			        	
			        	while (rsL.next()) {
			        		
			        		Lecture q = new Lecture(rsL.getString("instructor_name"), rsL.getInt("start_time"),  rsL.getInt("end_time"), deCodeWeek(rsL.getInt("week")), "", rsL.getString("id"), new Color(169,226,195));
			                Course r = new Course(rs.getString("course_name"), rs.getString("description"), rs.getString("description"),
			                		rs.getString("department"), rs.getString("units"), new Course [0], new String [0], 
			                		deCodeGEFill(rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getString(14), rs.getInt(15), rs.getInt(16))
			                		, q);
			        		
			        		ca.add(r);
			        	}
			        	rsL.close();
			        }
			        
		        }
		        
		}
			
		
		stmt2.close();
		
		return ca;
		
	}
	
	private static char [] deCodeWeek(int w) {
		
		boolean [] d = new boolean[7];
		
		if (w - 64 >= 0) {w-=64; d[0] =true;}
		if (w - 32 >= 0) {w-=32; d[1] =true;}
		if (w - 16 >= 0) {w-=16; d[2] =true;}
		if (w - 8 >= 0) {w-=8; d[3] =true;}
		if (w - 4 >= 0) {w-=4; d[4] =true;}
		if (w - 2 >= 0) {w-=2; d[5] =true;}
		if (w - 1 >= 0) {w-=1; d[6] =true;}
		
		int count =0;
		for (boolean t : d) if (t) count++;
		
		char[] week = new char[count];
		
		while(count != 0) {
			for (int i = 0; i < 7; i++) {
				if (d[i]) {
					switch (i) {
					case 0:
						week[week.length-count] = 'M';
						break;
					case 1:
						week[week.length-count] = 'T';
						break;
					case 2:
						week[week.length-count] = 'W';
						break;
					case 3:
						week[week.length-count] = 'R';
						break;
					case 4:
						week[week.length-count] = 'F';
						break;
					case 5:
						week[week.length-count] = 'S';
						break;
					case 6:
						week[week.length-count] = 'U';
						break;	
					}
					count--;
				}
			}
					
		}
		
		
		return week;
		
	}
	
	private static String[] deCodeGEFill(int b, String c, int d, int e, int f, int g, int h, int ethnic, int euro, String quantitative, int world_culture, int writ) {
		
		int count = 0;
		System.out.println(""+ b + d + e + f + g + h + ethnic + euro + world_culture +writ);
		count = b + d + e + f + g + h + ethnic + euro + world_culture +writ;
		
		if (!c.equals("0")) count ++;
		if (!quantitative.equals("0")) count ++;
		
		String [] req = new String[count];
		
		if (b == 1) {req[req.length-count] = "B"; count--;}
		if (c.equals("1")) {req[req.length-count] = "C"; count--;}
		else if (!c.equals("0")) {req[req.length-count] = "C (must take both this course and " + c + " to statisfy)"; count--;}
		if (d == 1) {req[req.length-count] = "D"; count--;}
		if (e == 1) {req[req.length-count] = "E"; count--;}
		if (f == 1) {req[req.length-count] = "F"; count--;}
		if (g == 1) {req[req.length-count] = "G"; count--;}
		if (h == 1) {req[req.length-count] = "H"; count--;}
		if (ethnic == 1) {req[req.length-count] = "ethnic".toUpperCase(); count--;}
		if (euro == 1) {req[req.length-count] = "euro".toUpperCase(); count--;}
		if (quantitative.equals("1")) {req[req.length-count] = "QUANTITATIVE"; count--;}
		else if (!quantitative.equals("0")) {req[req.length-count] = "QUANTITATIVE (must take both this course and " + c + " to statisfy)"; count--;}
		if (world_culture == 1) {req[req.length-count] = "world_culture".toUpperCase(); count--;}
		if (writ == 1) {req[req.length-count] = "writ".toUpperCase(); count--;}
		
		return req;
		
	}
	
	/*
	public static void main(String[] args) throws SQLException {

		for (Course t : SearchFullTitle("computer"))  System.out.println(t.courseID);
	}
	
	*/
	
}
