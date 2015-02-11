package GUI;
/**
 * @author Wesley Pollek
 * @author Forrest Sun
 * @version Feb 10 2015
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Connection.UserInfo.UsersConnection;


public class Login extends JPanel{
	
	JTextField username;
    JLabel userLabel;
    JPasswordField password;
    JLabel passLabel;
    JButton loginButton;
    JButton forpass;
    JLabel wrong;
    
    public Login() {
    	username= new JTextField(20);
        userLabel= new JLabel("Enter Username:");
        password= new JPasswordField(20);
        passLabel= new JLabel("Enter Password:");
        loginButton= new JButton("Login");
        loginButton.addActionListener(new Validator());
        forpass = new JButton ("Forgot Password?");
        wrong = new JLabel();
    }
    
    
    class Validator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	
		    String userinfo = username.getText();
		    char[] passinfo = password.getPassword();
		    wrong.setText("Connecting...");
		    int ID = UsersConnection.getID(userinfo,new String(passinfo));
		    if(ID == -3) wrong.setText("No Connection");
		    else if (ID==-2) wrong.setText("Incorrect Password");
		    else if (ID==-1) wrong.setText("No Username");
		    else wrong.setText("Welcome! " + userinfo + "!");
		}
    }
    
	public static void main (String[] args){
	    JFrame window = new JFrame();
		Login frame = new Login();
		window.setContentPane(frame);
		window.setSize(720,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);
	    frame.go();
	}
	
    public void go(){
		GridLayout basegrid = new GridLayout(3, 3);
		this.setLayout(basegrid);
		JPanel[] rows = new JPanel[3];
		for(int i=0;i<3; i++){
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
		spot[1][1].add(userLabel);
		spot[1][2].add(username);
		spot[2][1].add(passLabel);
		spot[2][2].add(password);
		spot[3][1].add(loginButton);
		spot[3][2].add(forpass);
		spot[0][1].add(wrong);
		rows[1].add(innerpanel);
    }
}
