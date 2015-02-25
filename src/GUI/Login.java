package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import connection.userInfo.User;
import connection.userInfo.UsersConnection;

/**
 * @author Wesley Pollek
 * @author Forrest Sun
 * @version Feb 10 2015
 */


public class Login extends JPanel{
    JTextField username;
    JLabel userLabel;
    JPasswordField password;
    JLabel passLabel;
    JButton loginButton;
    JButton forpass;
    JLabel wrong;
    JButton createAcc;
    
    
    public Login(){
    	username= new JTextField(20);
    	username.addKeyListener(new KeyValidator());
        userLabel= new JLabel("Enter Username:");
        password= new JPasswordField(20);
        password.addKeyListener(new KeyValidator());
        passLabel= new JLabel("Enter Password:");
        loginButton= new JButton("Login");
        loginButton.addActionListener(new LoginButtonValidator());        
	forpass = new JButton ("Forgot Password?");
	forpass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Golder.goToForgot();
			}
        	
        });
        wrong = new JLabel();
        createAcc = new JButton("Create Account");
        createAcc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Golder.goToCreate();
			}
        	
        });
        
        go();
    }
    
    
    class KeyValidator extends KeyAdapter {
    	public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 10)
				try {
					Validator();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        }
    	/*
        public void keyTyped(KeyEvent e) {
            // Do something for the keyTyped event
        }

        public void keyPressed(KeyEvent e) {
            // Do something for the keyPressed event
        }
        */
    }
    
    class LoginButtonValidator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				Validator();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    class NewUserButtonValidator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//public void NewUser();go to new user registration page;
		}
    }

    
    public void Validator() throws SQLException {
    	String userinfo = username.getText();
	    char[] passinfo = password.getPassword();
	    wrong.setText("Connecting...");
	    int ID = UsersConnection.getID(userinfo,new String(passinfo));
	    if(ID == -3) wrong.setText("No Connection");
	    else if (ID==-2) wrong.setText("Incorrect Password");
	    else if (ID==-1) wrong.setText("Not a Valid Username");
	    else {
	    	User u = connection.userInfo.UsersConnection.getInfo(ID);
	    	Golder.goToMain(u);
	    }
	    
    }
    /*
	public static void main (String[] args){
	    JFrame window = new JFrame();
		Login frame = new Login();
		window.setContentPane(frame);
		window.setSize(1080,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	*/
    
    public void clean() {
    	wrong.setText("");
    	username.setText("");
    	password.setText("");
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
		GridLayout innergrid = new GridLayout(4, 4);
		JPanel innerpanel = new JPanel();
		innerpanel.setLayout(innergrid);
		JPanel[][] spot= new JPanel[4][4];
		for(int m=0;m<4;m++){
		    for (int k=0;k<4;k++){
			spot[m][k]=new JPanel();
			spot[m][k].setBackground(Color.YELLOW);
			spot[m][k].setOpaque(true);
			innerpanel.add(spot[m][k]);
		    }
		}
		BufferedImage pic;
		Image rpic;
		JLabel logo=new JLabel();
		try{
		    pic = ImageIO.read(new File("src/GUI/theLogo.png"));
		    rpic = pic.getScaledInstance(400,303, Image.SCALE_SMOOTH);
		    logo = new JLabel(new ImageIcon(rpic));
		}catch(IOException e){System.out.println("no image");}
		spot[1][0].add(userLabel);
		spot[1][1].add(username);
		spot[2][0].add(passLabel);
		spot[2][1].add(password);
		spot[3][0].add(loginButton);
		spot[3][1].add(forpass);
		spot[3][2].add(createAcc);
		spot[0][0].add(wrong);
	       	rows[0].add(logo);
		rows[1].add(innerpanel);
    }
}
