package connection.courseInfo;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	
	/**
	 * 
	 * @return an array list of Majors
	 * @throws SQLException
	 */
	public static String[] getMajor() throws SQLException {
		String [] m = null;
		

			
			if (stmt.execute(String.format("SELECT * FROM `depts`;"))) {
		        rs = stmt.getResultSet();
		        
		        rs.last();
		        
		        m = new String[rs.getRow() + 1];
		        
		        rs.beforeFirst();
		        
		        m[0] = "------";
		        
		        for (int i = 1; i < m.length; i++) {
		        	rs.next();
		        	m[i] = rs.getString(3) + " (" + rs.getString(1) + ")";
		        }
		        
		    }
 	
		
		return m;
	}
	
	/**
	 * 
	 * @return An array list of professors
	 * @throws SQLException
	 */
	
	public static String[] getProfessor() throws SQLException {
		String [] m = null;
		

		
		if (stmt.execute(String.format("SELECT DISTINCT instructor_name FROM `spring_15_lecture`;"))) {
	        rs = stmt.getResultSet();
	        
	        rs.last();
	        
	        m = new String[rs.getRow()];
	        
	        rs.beforeFirst();
	        
	        m[0] = "------";
	        
	        for (int i = 1; i < m.length; i++) {
	        	rs.next();
	        	if (!rs.getString(1).equals("TBA")) m[i] = rs.getString(1);
	        	else --i;
	        }
	    }
	
	
		return m;
		
	}
	
	/**
	 * 
	 * @param lectureId
	 * @param sectionId
	 * @return
	 * @throws SQLException
	 */
	
	public static Course getCourse(int lectureId, int sectionId) throws SQLException {
		Course c = null;

		
		if (stmt.execute(String.format("SELECT * FROM `spring_15_section` "
				+ "INNER JOIN `spring_15_lecture` ON spring_15_section.corresponding_id=spring_15_lecture.id "
				+ "INNER JOIN `courses` ON spring_15_lecture.course_name=courses.course_name "
				+ "LEFT JOIN `b_reqs` ON spring_15_lecture.course_name=b_reqs.coursename "
				+ "LEFT JOIN `c_reqs` ON spring_15_lecture.course_name=c_reqs.coursename "
				+ "LEFT JOIN `d_reqs` ON spring_15_lecture.course_name=d_reqs.coursename "
				+ "LEFT JOIN `e_reqs` ON spring_15_lecture.course_name=e_reqs.coursename "
				+ "LEFT JOIN `f_reqs` ON spring_15_lecture.course_name=f_reqs.coursename "
				+ "LEFT JOIN `g_reqs` ON spring_15_lecture.course_name=g_reqs.coursename "
				+ "LEFT JOIN `h_reqs` ON spring_15_lecture.course_name=h_reqs.coursename "				
				+ "LEFT JOIN `ethnic_reqs` ON spring_15_lecture.course_name=ethnic_reqs.coursename "
				+ "LEFT JOIN `euro_reqs` ON spring_15_lecture.course_name=euro_reqs.coursename "
				+ "LEFT JOIN `quantitative_reqs` ON spring_15_lecture.course_name=quantitative_reqs.coursename "
				+ "LEFT JOIN `world_culture_reqs` ON spring_15_lecture.course_name=world_culture_reqs.coursename "
				+ "LEFT JOIN `writ_reqs` ON spring_15_lecture.course_name=writ_reqs.coursename "
				+ "WHERE spring_15_lecture.id = %s AND spring_15_section.id = %s;", lectureId, sectionId))) {

				rs = stmt.getResultSet();
				
				rs.next();
				
				Lecture le = new Lecture(rs.getInt("spring_15_lecture.id"), rs.getString("spring_15_lecture.instructor_name"), rs.getInt("spring_15_lecture.start_time"),  rs.getInt("spring_15_lecture.end_time"), deCodeWeek(rs.getInt("spring_15_lecture.week")), "", rs.getString("spring_15_lecture.id"), new Color(169,226,195));
        		Lecture se = new Lecture(rs.getInt("spring_15_section.id"), "TBA", rs.getInt("spring_15_section.start_time"),  rs.getInt("spring_15_section.end_time"), deCodeWeek(rs.getInt("spring_15_section.week")), "", rs.getString("spring_15_section.id"), new Color(169,226,195));
        		
                c = new Course(rs.getString("spring_15_lecture.course_name"), rs.getString("full_title"), rs.getString("full_title"),
                		rs.getString("department"), rs.getString("units"), new Course [0], new String [0], 
                		deCodeGEFill(rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28))
                		, le, se);
		}
		return c;
	}
	/**
	 * get course with color
	 * @param lectureId
	 * @param sectionId
	 * @param lectureColor
	 * @param sectionColor
	 * @return
	 * @throws SQLException
	 */
	
	public static Course getCourse(int lectureId, int sectionId,int lectureColor, int sectionColor) throws SQLException {
Course c = null;

		
		if (stmt.execute(String.format("SELECT * FROM `spring_15_section` "
				+ "INNER JOIN `spring_15_lecture` ON spring_15_section.corresponding_id=spring_15_lecture.id "
				+ "INNER JOIN `courses` ON spring_15_lecture.course_name=courses.course_name "
				+ "LEFT JOIN `b_reqs` ON spring_15_lecture.course_name=b_reqs.coursename "
				+ "LEFT JOIN `c_reqs` ON spring_15_lecture.course_name=c_reqs.coursename "
				+ "LEFT JOIN `d_reqs` ON spring_15_lecture.course_name=d_reqs.coursename "
				+ "LEFT JOIN `e_reqs` ON spring_15_lecture.course_name=e_reqs.coursename "
				+ "LEFT JOIN `f_reqs` ON spring_15_lecture.course_name=f_reqs.coursename "
				+ "LEFT JOIN `g_reqs` ON spring_15_lecture.course_name=g_reqs.coursename "
				+ "LEFT JOIN `h_reqs` ON spring_15_lecture.course_name=h_reqs.coursename "				
				+ "LEFT JOIN `ethnic_reqs` ON spring_15_lecture.course_name=ethnic_reqs.coursename "
				+ "LEFT JOIN `euro_reqs` ON spring_15_lecture.course_name=euro_reqs.coursename "
				+ "LEFT JOIN `quantitative_reqs` ON spring_15_lecture.course_name=quantitative_reqs.coursename "
				+ "LEFT JOIN `world_culture_reqs` ON spring_15_lecture.course_name=world_culture_reqs.coursename "
				+ "LEFT JOIN `writ_reqs` ON spring_15_lecture.course_name=writ_reqs.coursename "
				+ "WHERE spring_15_lecture.id = %s AND spring_15_section.id = %s;", lectureId, sectionId))) {

				rs = stmt.getResultSet();
				
				rs.next();
				
				Lecture le = new Lecture(rs.getInt("spring_15_lecture.id"), rs.getString("spring_15_lecture.instructor_name"), rs.getInt("spring_15_lecture.start_time"),  rs.getInt("spring_15_lecture.end_time"), deCodeWeek(rs.getInt("spring_15_lecture.week")), "", rs.getString("spring_15_lecture.id"), new Color(lectureColor));
        		Lecture se = new Lecture(rs.getInt("spring_15_section.id"), "TBA", rs.getInt("spring_15_section.start_time"),  rs.getInt("spring_15_section.end_time"), deCodeWeek(rs.getInt("spring_15_section.week")), "", rs.getString("spring_15_section.id"), new Color(sectionColor));
        		
                c = new Course(rs.getString("spring_15_lecture.course_name"), rs.getString("full_title"), rs.getString("full_title"),
                		rs.getString("department"), rs.getString("units"), new Course [0], new String [0], 
                		deCodeGEFill(rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28))
                		, le, se);
		}
		return c;
		
		
	}
	
	/**
	 * Simple Search Support
	 * @param s Search String
	 * @return All courses contain s
	 * @throws SQLException
	 */
	public static ArrayList<Course> SearchFullTitle(String s) throws SQLException{

		
		ArrayList<Course> ca = new ArrayList<Course>();

		
		if (stmt.execute(String.format("SELECT * FROM `spring_15_section` "
				+ "INNER JOIN `spring_15_lecture` ON spring_15_section.corresponding_id=spring_15_lecture.id "
				+ "INNER JOIN `courses` ON spring_15_lecture.course_name=courses.course_name "
				+ "LEFT JOIN `b_reqs` ON spring_15_lecture.course_name=b_reqs.coursename "
				+ "LEFT JOIN `c_reqs` ON spring_15_lecture.course_name=c_reqs.coursename "
				+ "LEFT JOIN `d_reqs` ON spring_15_lecture.course_name=d_reqs.coursename "
				+ "LEFT JOIN `e_reqs` ON spring_15_lecture.course_name=e_reqs.coursename "
				+ "LEFT JOIN `f_reqs` ON spring_15_lecture.course_name=f_reqs.coursename "
				+ "LEFT JOIN `g_reqs` ON spring_15_lecture.course_name=g_reqs.coursename "
				+ "LEFT JOIN `h_reqs` ON spring_15_lecture.course_name=h_reqs.coursename "				
				+ "LEFT JOIN `ethnic_reqs` ON spring_15_lecture.course_name=ethnic_reqs.coursename "
				+ "LEFT JOIN `euro_reqs` ON spring_15_lecture.course_name=euro_reqs.coursename "
				+ "LEFT JOIN `quantitative_reqs` ON spring_15_lecture.course_name=quantitative_reqs.coursename "
				+ "LEFT JOIN `world_culture_reqs` ON spring_15_lecture.course_name=world_culture_reqs.coursename "
				+ "LEFT JOIN `writ_reqs` ON spring_15_lecture.course_name=writ_reqs.coursename "
				+ "WHERE courses.full_title LIKE '%%%s%%' OR courses.course_name LIKE '%%%s%%';", s, s))) {

				rs = stmt.getResultSet();
		        
		        
		        
		        
		        while (rs.next()) {
			        		
			        		Lecture le = new Lecture(rs.getInt("spring_15_lecture.id"), rs.getString("spring_15_lecture.instructor_name"), rs.getInt("spring_15_lecture.start_time"),  rs.getInt("spring_15_lecture.end_time"), deCodeWeek(rs.getInt("spring_15_lecture.week")), "", rs.getString("spring_15_lecture.id"), new Color(169,226,195));
			        		Lecture se = new Lecture(rs.getInt("spring_15_section.id"), "TBA", rs.getInt("spring_15_section.start_time"),  rs.getInt("spring_15_section.end_time"), deCodeWeek(rs.getInt("spring_15_section.week")), "", rs.getString("spring_15_section.id"), new Color(169,226,195));
			        		
			                Course r = new Course(rs.getString("courses.course_name"), rs.getString("courses.full_title"), rs.getString("courses.full_title"),
			                		rs.getString("department"), rs.getString("units"), new Course [0], new String [0], 
			                		deCodeGEFill(rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28))
			                		, le, se);
			        		
			        		ca.add(r);

		        }

		}
		
		

		return ca;
		
	}
	
	
	public static ArrayList<Course> getResults(String key, String option) throws SQLException{
		if (option.equals("Department")){
			Pattern pattern = 
				    Pattern.compile("\\A.+\\((.+)\\)\\Z");

			Matcher matcher = 
				    pattern.matcher(key);
				    
			if (matcher.find()) key = matcher.group(1);

			option = "department='"+key+'\'';
		}
		else if (option.equals("Professor"))
			option = "spring_15_lecture.instructor_name='"+key+'\'';
		else if (option.equals("General Education")) {
			option = "0";
		}
		
		
		ArrayList<Course> ca = new ArrayList<Course>();

		
		if (stmt.execute("SELECT * FROM `spring_15_section` "
				+ "INNER JOIN `spring_15_lecture` ON spring_15_section.corresponding_id=spring_15_lecture.id "
				+ "INNER JOIN `courses` ON spring_15_lecture.course_name=courses.course_name "
				+ "LEFT JOIN `b_reqs` ON spring_15_lecture.course_name=b_reqs.coursename "
				+ "LEFT JOIN `c_reqs` ON spring_15_lecture.course_name=c_reqs.coursename "
				+ "LEFT JOIN `d_reqs` ON spring_15_lecture.course_name=d_reqs.coursename "
				+ "LEFT JOIN `e_reqs` ON spring_15_lecture.course_name=e_reqs.coursename "
				+ "LEFT JOIN `f_reqs` ON spring_15_lecture.course_name=f_reqs.coursename "
				+ "LEFT JOIN `g_reqs` ON spring_15_lecture.course_name=g_reqs.coursename "
				+ "LEFT JOIN `h_reqs` ON spring_15_lecture.course_name=h_reqs.coursename "				
				+ "LEFT JOIN `ethnic_reqs` ON spring_15_lecture.course_name=ethnic_reqs.coursename "
				+ "LEFT JOIN `euro_reqs` ON spring_15_lecture.course_name=euro_reqs.coursename "
				+ "LEFT JOIN `quantitative_reqs` ON spring_15_lecture.course_name=quantitative_reqs.coursename "
				+ "LEFT JOIN `world_culture_reqs` ON spring_15_lecture.course_name=world_culture_reqs.coursename "
				+ "LEFT JOIN `writ_reqs` ON spring_15_lecture.course_name=writ_reqs.coursename "
				+ "WHERE " + option)) {

				rs = stmt.getResultSet();
		        
		        
		        
		        
		        while (rs.next()) {
			        		
			        		Lecture le = new Lecture(rs.getInt("spring_15_lecture.id"), rs.getString("spring_15_lecture.instructor_name"), rs.getInt("spring_15_lecture.start_time"),  rs.getInt("spring_15_lecture.end_time"), deCodeWeek(rs.getInt("spring_15_lecture.week")), "", rs.getString("spring_15_lecture.id"), new Color(169,226,195));
			        		Lecture se = new Lecture(rs.getInt("spring_15_section.id"), "TBA", rs.getInt("spring_15_section.start_time"),  rs.getInt("spring_15_section.end_time"), deCodeWeek(rs.getInt("spring_15_section.week")), "", rs.getString("spring_15_section.id"), new Color(169,226,195));
			        		
			                Course r = new Course(rs.getString("courses.course_name"), rs.getString("courses.full_title"), rs.getString("courses.full_title"),
			                		rs.getString("department"), rs.getString("units"), new Course [0], new String [0], 
			                		deCodeGEFill(rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28))
			                		, le, se);
			        		
			        		ca.add(r);

		        }

		}

		return ca;
	}
	
	/**
	 * 
	 * @param w
	 * @return
	 */
	
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
	
	private static String[] deCodeGEFill(String b, String c, String d, String e, String f, String g, String h, String ethnic, String euro, String quantitative, String world_culture, String writ) {
		
		
		int count = 0;
		
		
		if (b != null) count ++;
		if (c != null) count ++;
		if (d != null) count ++;
		if (e != null) count ++;
		if (f != null) count ++;
		if (g != null) count ++;
		if (h != null) count ++;
		if (ethnic != null) count ++;
		if (euro != null) count ++;
		if (quantitative != null) count ++;
		if (world_culture != null) count ++;
		if (writ != null) count ++;
		
		String [] req = new String[count];
		
		if (b != null) {req[req.length-count] = "B"; count--;}
		if (c != null) {req[req.length-count] = "C"; count--;}
		if (d != null) {req[req.length-count] = "D"; count--;}
		if (e != null) {req[req.length-count] = "E"; count--;}
		if (f != null) {req[req.length-count] = "F"; count--;}
		if (g != null) {req[req.length-count] = "G"; count--;}
		if (h != null) {req[req.length-count] = "H"; count--;}
		if (ethnic != null) {req[req.length-count] = "ethnic".toUpperCase(); count--;}
		if (euro != null) {req[req.length-count] = "euro".toUpperCase(); count--;}
		if (quantitative != null) {req[req.length-count] = "QUANTITATIVE"; count--;}
		if (world_culture != null) {req[req.length-count] = "world_culture".toUpperCase(); count--;}
		if (writ != null) {req[req.length-count] = "writ".toUpperCase(); count--;}
		
		return req;
		
	}
	
	/**/
	public static void main(String[] args) throws SQLException {

		getCourse(1, 1);
	}
	
	
	
}
