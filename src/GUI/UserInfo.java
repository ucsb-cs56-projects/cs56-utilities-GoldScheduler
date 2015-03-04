package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;

import connection.courseInfo.CourseConnection;
import connection.userInfo.User;
import connection.userInfo.UsersConnection;

/**
 * Change User Info Panel
 * @author Wesley Pollek
 * @author Forrest Sun
 * @version Feb 12 2015
 */

public class UserInfo extends JPanel{
	
	static private UserInfo hahaha;
	
	static {
		try {
			hahaha = new UserInfo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
    JTextField username;
    JLabel userLabel;
    JLabel userWrong;
    JPasswordField password;
    JLabel passLabel;
    JLabel passWrong;
    JPasswordField password2;
    JLabel passLabel2;
    JLabel pass2Wrong;
    JPasswordField password1;
    JLabel passLabel1;
    JLabel pass1Wrong;
    JTextField email;
    JLabel emailLabel;
    JLabel emailWrong;

    JLabel majorLabel;

    JButton changeButton;
    JButton backButton;
    JComboBox<String> majorList;
    
    JLabel update;
    
    private User u;
    
    /**
     * Constructor
     * @throws SQLException 
     */
    private UserInfo() throws SQLException {
    	
    	update = new JLabel();
    	update.setForeground(Color.RED);
    	
    	username= new JTextField(20);

    	username.setEditable(false);
        userLabel= new JLabel("Username:");
        userWrong = new JLabel();
        userWrong.setForeground(Color.RED);

        
        password= new JPasswordField(20);
        passLabel= new JLabel("Enter Password:");
        passWrong = new JLabel("<html> *You must enter password <br>to update your information</html>");
        passWrong.setForeground(Color.RED);
        
        password1= new JPasswordField(20);
        passLabel1= new JLabel("New Password:");
        pass1Wrong = new JLabel();
        pass1Wrong.setForeground(Color.RED);
        
        password2= new JPasswordField(20);
        passLabel2= new JLabel("New Password again:");
        pass2Wrong = new JLabel();
        pass2Wrong.setForeground(Color.RED);
        
        email= new JTextField(20);
        emailLabel= new JLabel("Email:");
        emailWrong = new JLabel();
        emailWrong.setForeground(Color.RED);
        
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
				//TODO goto mainpage
			}
        	
        });
        
        majorLabel = new JLabel("Select Major:");        
        String[] majorStrings = CourseConnection.getMajor();
        majorList = new JComboBox<String>(majorStrings);
        go();
    }
    
    
    
    void init() throws SQLException {
    	password.setText("");
    	password1.setText("");
    	password2.setText("");
    	pass1Wrong.setText("");
    	pass2Wrong.setText("");
    	emailWrong.setText("");
    	userWrong.setText("");
    	
    	if (u!=null) {

	    	username.setText(u.getUsername());
	    	passWrong.setText("<html> *You must enter password <br>to update your information</html>");
	    	
	    	email.setText(u.getEmail());
	    	majorList.setSelectedItem(u.getMajor());
    	} else {
    		username.setText("");
    		email.setText("");
    		majorList.setSelectedIndex(0);
    	}
    }
    
    void init(User user) throws SQLException {
    	u = user;
    	init();
    }
    
    void setUser(User u) {
    	this.u=u;
    }
    
    public static UserInfo getUserPanel() {
    	return hahaha;
    }
        
    
    /**
     * Validator Check all the information in the TextArea
     * Create account if no error
     * @throws SQLException 
     */
    public void Validator() throws SQLException {
    	
    	update.setText("");

    	boolean validInfo = true;
    	
    	String passinfo = new String(password.getPassword());
    	
    	if (passinfo.equals("")) {passWrong.setText("<html> *You must enter password <br>to update your information</html>"); validInfo=false;}
    	else if (!passinfo.equals(u.getPassword())) { passWrong.setText("*Wrong password"); validInfo=false;}
    	else passWrong.setText("");

    	String passinfo1 = new String(password1.getPassword());
	    if (!passinfo1.equals("") && passinfo1.length() < 4) {pass1Wrong.setText("Password must at least 4 charactor"); validInfo=false;}
	    else pass1Wrong.setText("");
	    
	    String passinfo2 = new String(password2.getPassword());
	    if (passinfo2.equals(passinfo1)) pass2Wrong.setText("");
	    else {pass2Wrong.setText("*Password does not match"); validInfo=false;}
	    
	    String emailInfo = email.getText();
	    if (emailInfo.equals("") || emailInfo.equals(u.getEmail())) emailWrong.setText("");

	    else {
	    	//TODO (or not) email checking. Now only allow a-z A-Z 0-9 _ - .
		    Pattern pattern = 
		    Pattern.compile("\\A[\\w\\-\\.]+@\\w+\\.\\w+\\Z");

		    Matcher matcher = 
		    pattern.matcher(emailInfo);
		    
	    	if (!matcher.find()) {emailWrong.setText("Email format: example@example.com"); validInfo=false;}
	    	else if (UsersConnection.getIdByEmail(emailInfo) > 0) emailWrong.setText("Email in use");
	    	else emailWrong.setText("");
	    	
	    }
	    if (validInfo) {

	    	if (!emailInfo.equals("") && !emailInfo.equals(u.getEmail())) u.setEmail(emailInfo);
	    	if (!passinfo1.equals("")) u.setPassword(passinfo1);
	    	if (!majorList.getSelectedItem().equals(u.getMajor())) u.setMajor((String) majorList.getSelectedItem());
	    	
	    	init();
	    	update.setText("Your information is up-dated");
	    	passWrong.setText("");
	    }
    }
    /** Test fucntion
     *       
     * @param args
     * @throws SQLException
     */
     
	public static void main (String[] args) throws SQLException{
	    JFrame window = new JFrame();
		UserInfo userpanel = getUserPanel();
		userpanel.init(connection.userInfo.UsersConnection.getInfo(8));
		window.setContentPane(userpanel);
		window.setSize(910,650);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);

	}
    /**
     * The graphic part
     */
    public void go(){
		
		
		GridLayout innergrid = new GridLayout(8, 5);
		innergrid.preferredLayoutSize(this);
		JPanel innerpanel = new JPanel();
		this.setBackground(new Color(217,221,235));
		innerpanel.setBackground(new Color(217,221,235));
		innerpanel.setLayout(innergrid);
		JPanel[][] spot= new JPanel[8][4];
		for(int m=0;m<8;m++){
		    for (int k=0;k<3;k++){
			spot[m][k]=new JPanel();
			spot[m][k].setBackground(new Color(217,221,235));
			spot[m][k].setOpaque(true);
			innerpanel.add(spot[m][k]);
		    }
		}

		this.add(innerpanel);
		innergrid.setVgap(25);
		spot[0][0].add(update);
		
		spot[1][0].add(userLabel);
		spot[1][1].add(username);
		spot[1][2].add(userWrong);
		
		spot[2][0].add(passLabel1);
		spot[2][1].add(password1);
		spot[2][2].add(pass1Wrong);
		
		spot[3][0].add(passLabel2);
		spot[3][1].add(password2);
		spot[3][2].add(pass2Wrong);
		
		spot[4][0].add(emailLabel);
		spot[4][1].add(email);
		spot[4][2].add(emailWrong);
		
		
		spot[5][0].add(majorLabel);
		spot[5][1].add(majorList);


		
		
		spot[6][0].add(passLabel);
		spot[6][1].add(password);
		spot[6][2].add(passWrong);
		
		spot[7][1].add(changeButton);

		


    }
}
