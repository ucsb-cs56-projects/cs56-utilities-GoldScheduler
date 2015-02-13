package GUI;
/**
 * @author Wesley Pollek
 * @author Forrest Sun
 * @version Feb 12 2015
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

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
    
    
    public CreateAccount() {
    	username= new JTextField(20);
    	//username.addKeyListener(new KeyValidator());
        userLabel= new JLabel("Enter Username:");
        userWrong = new JLabel("*");
        userWrong.setForeground(Color.RED);

        
        password= new JPasswordField(20);
        //password.addKeyListener(new KeyValidator());
        passLabel= new JLabel("Enter Password:");
        passWrong = new JLabel("*");
        passWrong.setForeground(Color.RED);
        
        password2= new JPasswordField(20);
        //password2.addKeyListener(new KeyValidator());
        passLabel2= new JLabel("Enter Password again:");
        pass2Wrong = new JLabel("*");
        pass2Wrong.setForeground(Color.RED);
        
        email= new JTextField(20);
        //emailLabel.addKeyListener(new KeyValidator());
        emailLabel= new JLabel("Enter email:");
        emailWrong = new JLabel();
        
        createButton= new JButton("Create");
        //createButton.addActionListener(new LoginButtonValidator());
        backButton = new JButton ("Back");
        
        majorLabel = new JLabel("Select Major:");
    }
    
    /*
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

    heres where we should implement go to newuser class
    public void NewUser(){
    	link to RegisterNewUser page
    }


    public void Validator() {
    	String userinfo = username.getText();
	    char[] passinfo = password.getPassword();
	    wrong.setText("Connecting...");
	    int ID = UsersConnection.getID(userinfo,new String(passinfo));
	    if(ID == -3) wrong.setText("No Connection");
	    else if (ID==-2) wrong.setText("Incorrect Password");
	    else if (ID==-1) wrong.setText("Not a Valid Username");
	    else {
	    	wrong.setText("Welcome! " + userinfo + "!");
	    	//heres where we would transfer to user home page
	    }
    }
    */
	public static void main (String[] args){
	    JFrame window = new JFrame();
		CreateAccount frame = new CreateAccount();
		window.setContentPane(frame);
		window.setSize(720,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);
	    frame.go();
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

		spot[6][1].add(createButton);
		spot[6][2].add(backButton);

		rows[1].add(innerpanel);

    }
}
