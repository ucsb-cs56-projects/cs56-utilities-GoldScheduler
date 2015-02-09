package Connection.UserInfo;

//import Course

/**
 * 
 * @author Wesley Pollek, Forrest Sun
 *
 */

public class User {
	private String username;
	private String password;
	private String email;
	private String major;
	private LinkedList<Schedule> [] courses;
	private int id;
	public User(){
		username=null;
		password=null;
		email=null;
		major=null;
	}
	public User(String u, String p, String e, String m, int id){
		username=u;
		password=p;
		email=e;
		major=m;
		this.id=id;
	}
	public String getUsername(){return this.username;}
	public String getPassword(){return this.password;}
	public String getEmail(){return this.email;}
	public String getMajor(){return this.major;}
	public void setUsername(String username){this.username=username;}
	public void setPassword(String password){this.password=password;}
	public void setEmail(String email){this.email=email;}
	public void setMajor(String major){this.major=major;}

}
