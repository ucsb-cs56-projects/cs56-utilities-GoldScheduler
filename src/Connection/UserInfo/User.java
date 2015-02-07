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
	public User(){
		username=NULL;
		password=NULL;
		email=NULL;
		major=NULL;
	}
	public User(String u, String p, String e, String m){
		username=u;
		password=p;
		email=e;
		major=m;
	}
	public String getUsername(){return this.username;}
	public String getPassword(){return this.password;}
	public String getEmail(){return this.email;}
	public String getMajor(){return this.major;}
	public void setUsername(string username){this.username=username;}
	public void setPassword(string password){this.password=password;}
	public void setEmail(string email){this.email=email;}
	public void setMajor(string major){this.major=major;}

}
