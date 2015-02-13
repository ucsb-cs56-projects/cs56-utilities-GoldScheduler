package GUI;
/**
 * @author Wesley Pollek
 * @author Forrest Sun
 * @version Feb 12 2015
 */
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import connection.courseInfo.CourseConnection;
import connection.userInfo.UsersConnection;



public class CreateAccount extends JPanel{
	
	JTextField username;
    JLabel userLabel;
    JLabel userWrong;
    JPasswordField password;
    JLabel passLabel;
    JLabel passWrong;
    JPasswordField password2;
    JLabel passLabel2;
    JLabel pass2Wrong;
    JTextField email;
    JLabel emailLabel;
    JLabel emailWrong;

    JLabel majorLabel;

    JButton createButton;
    JButton backButton;
    JComboBox<String> majorList;
    
    
    public CreateAccount() {
    	username= new JTextField(20);
    	username.addKeyListener(new KeyValidator());
        userLabel= new JLabel("Enter Username:");
        userWrong = new JLabel("*");
        userWrong.setForeground(Color.RED);

        
        password= new JPasswordField(20);
        password.addKeyListener(new KeyValidator());
        passLabel= new JLabel("Enter Password:");
        passWrong = new JLabel("*");
        passWrong.setForeground(Color.RED);
        
        password2= new JPasswordField(20);
        password2.addKeyListener(new KeyValidator());
        passLabel2= new JLabel("Enter Password again:");
        pass2Wrong = new JLabel("*");
        pass2Wrong.setForeground(Color.RED);
        
        email= new JTextField(20);
        email.addKeyListener(new KeyValidator());
        emailLabel= new JLabel("Enter email:");
        emailWrong = new JLabel();
        emailWrong.setForeground(Color.RED);
        
        createButton= new JButton("Create");
        createButton.addActionListener(new LoginButtonValidator());
        backButton = new JButton ("Back");
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Golder.goToLogin();
			}
        	
        });
        
        majorLabel = new JLabel("Select Major:");
        
        
        
        String[] majorStrings = CourseConnection.getMajor();


        majorList = new JComboBox<String>(majorStrings);
        majorList.setSelectedIndex(1);
        
        //majorList.addActionListener(this);
        

        go();
    }
    
    
    class KeyValidator extends KeyAdapter {
    	public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 10) Validator();
        }
    	
        public void keyTyped(KeyEvent e) {
            // Do something for the keyTyped event
        }
        
        public void keyPressed(KeyEvent e) {
            // Do something for the keyPressed event
        }
        
    }

    class LoginButtonValidator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Validator();
		}
    }
    class NewUserButtonValidator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//public void NewUser();go to new user registration page;
			
		}

    }
    




    public void Validator() {
    	int tmp;
    	boolean validInfo = true;
    	String userinfo = username.getText();
    	String passinfo = new String(password.getPassword());
    	
    	
    	
    	if (userinfo.equals("")) { userWrong.setText("*Please enter a valid username"); validInfo=false;}
    	else if ((tmp = UsersConnection.getID(userinfo, passinfo)) > 0 || tmp == -2) { userWrong.setText("*Username is in use"); validInfo=false;}
    	else userWrong.setText("*"); 
    	
	    if (passinfo.length() < 4) { passWrong.setText("*Password must at least 4 charactor"); validInfo=false;}
	    else passWrong.setText("*");
	    
	    String passinfo2 = new String(password2.getPassword());
	    if (passinfo2.equals(passinfo)) pass2Wrong.setText("*");
	    else {pass2Wrong.setText("*Password does not match"); validInfo=false;}
	    
	    String emailInfo = email.getText();
	    if (emailInfo.equals("")) emailWrong.setText("*");

	    else {
		    Pattern pattern = 
		    Pattern.compile("\\A\\w+@\\w+\\.\\w+\\Z");

		    Matcher matcher = 
		    pattern.matcher(emailInfo);
		    
	    	if (!matcher.find()) {emailWrong.setText("Email format: example@example.com"); validInfo=false;}
	    	else if (UsersConnection.getIdByEmail(emailInfo) > 0) emailWrong.setText("Email in use");
	    	else emailWrong.setText("");
	    	
	    }
	    

	    if (validInfo) {
	    	UsersConnection.Register(userinfo, passinfo, emailInfo, (String)majorList.getSelectedItem());
	    	//System.out.println(userinfo + passinfo + emailInfo + (String)majorList.getSelectedItem());
	    	//TODO go to main page
	    }
	    
	    
    }

	public static void main (String[] args){
	    JFrame window = new JFrame();
		CreateAccount frame = new CreateAccount();
		window.setContentPane(frame);
		window.setSize(1080,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);

	}
	
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
		GridLayout innergrid = new GridLayout(7, 5);
		JPanel innerpanel = new JPanel();
		innerpanel.setLayout(innergrid);
		JPanel[][] spot= new JPanel[10][5];
		for(int m=0;m<7;m++){
		    for (int k=0;k<5;k++){
			spot[m][k]=new JPanel();
			spot[m][k].setBackground(Color.YELLOW);
			spot[m][k].setOpaque(true);
			innerpanel.add(spot[m][k]);
		    }
		}
		spot[1][1].add(userLabel);
		spot[1][2].add(username);
		spot[1][3].add(userWrong);
		
		spot[2][1].add(passLabel);
		spot[2][2].add(password);
		spot[2][3].add(passWrong);
		
		spot[3][1].add(passLabel2);
		spot[3][2].add(password2);
		spot[3][3].add(pass2Wrong);
		
		spot[4][1].add(emailLabel);
		spot[4][2].add(email);
		spot[4][3].add(emailWrong);
		
		
		spot[5][1].add(majorLabel);
		spot[5][2].add(majorList);

		spot[6][1].add(createButton);
		spot[6][2].add(backButton);

		rows[1].add(innerpanel);

    }
}
