package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import connection.courseInfo.CourseConnection;
import connection.userInfo.User;
import connection.userInfo.UsersConnection;

/**
 * Additional screen that allows the user to change a forgotten password.
 * User must have the username and email of the account.
 * @author Wesley Pollek
 * @author Forrest Sun
 * @author Jonathan Easterman
 * @version Feb 12 2015
 */

public class ForgotUser extends JPanel{
	
    	static private ForgotUser hahaha;
	
	static {
		try {
			hahaha = new ForgotUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
    JTextField username;
    JLabel userLabel;
    JLabel userWrong;
    JTextField emailf;
    JLabel emailLabel;
    JLabel emailWrong;
    JPasswordField password2;
    JLabel passLabel2;
    JLabel pass2Wrong;
    JPasswordField password1;
    JLabel passLabel1;
    JLabel pass1Wrong;

    JButton changeButton;
    JButton backButton;

    
    JLabel update;
    
    private User u;
    
    /**
     * Constructor
     * @throws SQLException 
     */
    public ForgotUser() throws SQLException {
    	
    	update = new JLabel();
    	update.setForeground(Color.RED);
    	
    	username= new JTextField(20);

        userLabel= new JLabel("Username:");
        userWrong = new JLabel();
        userWrong.setForeground(Color.RED);

        
        emailf= new JTextField(20);
        emailLabel= new JLabel("Enter Email:");
        emailWrong = new JLabel("<html> *You must enter email <br>to update your password</html>");
        emailWrong.setForeground(Color.RED);
        
        password1= new JPasswordField(20);
        passLabel1= new JLabel("New Password:");
        pass1Wrong = new JLabel();
        pass1Wrong.setForeground(Color.RED);
        
        password2= new JPasswordField(20);
        passLabel2= new JLabel("New Password again:");
        pass2Wrong = new JLabel();
        pass2Wrong.setForeground(Color.RED);

        
        changeButton= new JButton("Submit");
        changeButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			try {
    				Validator();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
    		}
        });
        
        backButton = new JButton ("Back");
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Golder.goToLogin();
			}
        	
        });
        
        
        
        String[] majorStrings = CourseConnection.getMajor();
        
        
        
        

        go();
    }
    
    /**
     * Initializes all of the text fields to empty string ""
     * @throws SQLException
     */
    void init() throws SQLException {
    	if (u!=null) {
	    	userWrong.setText("");   
	    	pass1Wrong.setText("");
	    	pass2Wrong.setText("");
	    	emailWrong.setText("");
	    	
	    	emailf.setText("");
	    	password1.setText("");
	    	password2.setText("");
    	}
    }
    
    /**
     * Sets user member variable and
     * initializes all of the text fields to empty string ""
     * @throws SQLException
     */
    void init(User user) throws SQLException {
    	u = user;
    	init();
    }
    
    
    /**
     * @param u desired User instance to set user
     * member variable
     */
    void setUser(User u) {
    	this.u=u;
    }
    
    /**
     * @return The static member which holds the user panel
     */
    public static ForgotUser getUserPanel() {
    	return hahaha;
    }
    
    /**
     * Validator Check all the information in the TextArea
     * Create account if no error
     * @throws SQLException 
     */
    

    /**
     * Validates that inputted info is correct,
     * resets password info in database
     * resets textfields to empty string ""
     * @throws SQLException
     */
    public void Validator() throws SQLException {
    	
    	update.setText("");
    	setUser(UsersConnection.getInfo(username.getText(), emailf.getText(), 5));
    	
    	
    	
    	boolean validInfo = true;
    	
    	String emailinfo = new String(emailf.getText());
    	
    	if (emailinfo.equals("")) {emailWrong.setText("<html>*You must enter email to<br>update your password<html/>"); validInfo=false;}
    	else if (!emailinfo.equals(u.getEmail())) {System.out.println("here!"); emailWrong.setText("*Wrong email"); validInfo=false;}
    	else emailWrong.setText("");
    
    	String passinfo1 = new String(password1.getPassword());
	    if (!passinfo1.equals("") && passinfo1.length() < 4) {pass1Wrong.setText("Password must at least 4 charactor"); validInfo=false;}
	    else pass1Wrong.setText("");
	    
	    String passinfo2 = new String(password2.getPassword());
	    if (passinfo2.equals(passinfo1)) pass2Wrong.setText("");
	    else {pass2Wrong.setText("*Password does not match"); validInfo=false;}


	    if (validInfo) {

	    	if (!passinfo1.equals("")) u.setPassword(passinfo1);
	    	init();
	    	update.setText("Your information is up-dated");
	    	emailWrong.setText("");
	    }
    	


    }
    /** Test fucntion
     *       
     * @param args
     * @throws SQLException
     */
     
	public static void main (String[] args) throws SQLException{
	    JFrame window = new JFrame();
		ForgotUser frame = getUserPanel();
		frame.init(connection.userInfo.UsersConnection.getInfo(8));
		window.setContentPane(frame);
		window.setSize(1080,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);

	}
	
	/**
	 * resets all textfields to empty string ""
	 */
    public void clean() {
    update.setText("");
	username.setText("");
	userWrong.setText("");
	emailf.setText("");
	password2.setText("");
	pass2Wrong.setText("");
	password1.setText("");
	pass1Wrong.setText("");
	emailWrong.setText("");


    }


    
    /**
     * Initializes graphics window
     */
    public void go(){
		GridLayout basegrid = new GridLayout(2, 1);
		this.setLayout(basegrid);
		JPanel[] rows = new JPanel[2];
		for(int i=0;i<2; i++){
			rows[i]= new JPanel();
			rows[i].setOpaque(true);
			rows[i].setBackground(Color.BLUE);
			this.add(rows[i]);
		}
		GridLayout innergrid = new GridLayout(8, 5);
		JPanel innerpanel = new JPanel();
		innerpanel.setLayout(innergrid);
		JPanel[][] spot= new JPanel[10][5];
		for(int m=0;m<8;m++){
		    for (int k=0;k<5;k++){
			spot[m][k]=new JPanel();
			spot[m][k].setBackground(Color.YELLOW);
			spot[m][k].setOpaque(true);
			innerpanel.add(spot[m][k]);
		    }
		}
		
		spot[0][1].add(update);
		
		spot[1][1].add(userLabel);
		spot[1][2].add(username);
		spot[1][3].add(userWrong);
		
		spot[2][1].add(passLabel1);
		spot[2][2].add(password1);
		spot[2][3].add(pass1Wrong);
		
		spot[3][1].add(passLabel2);
		spot[3][2].add(password2);
		spot[3][3].add(pass2Wrong);
		

		
		spot[4][1].add(emailLabel);
		spot[4][2].add(emailf);
		spot[4][3].add(emailWrong);
		
		spot[6][1].add(changeButton);
		spot[6][2].add(backButton);

		rows[1].add(innerpanel);

    }
}

