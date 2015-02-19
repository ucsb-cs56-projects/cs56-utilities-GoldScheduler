package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import connection.courseInfo.CourseConnection;
import connection.userInfo.UsersConnection;
/**
* Register Panel
* @author Wesley Pollek
* @author Forrest Sun
* @version Feb 12 2015
*/
public class MainPage extends JPanel{
    JPanel control;
    JPanel[] buttons;
    JPanel display;
    JButton search;
    JButton viewSched;
    JButton changeInfo;
    JButton logout;
    public MainPage(){
	control=new JPanel();
	buttons = new JPanel[4];
	control.setLayout(new GridLayout(4,0));
	for(int i=0;i<4;i++){
	    buttons[i]= new JPanel();
	    control.add(buttons[i]);
	}

	search= new JButton("Search");
	viewSched = new JButton("View My Schedule");
	changeInfo = new JButton("Edit User Information");
	logout = new JButton("Log out");
        logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Golder.goToLogin();
			}
        	
        });

       	buttons[0].add(search);
	buttons[1].add(viewSched);
	buttons[2].add(changeInfo);
	buttons[3].add(logout);
	this.setLayout(new BorderLayout());
	this.add(control, BorderLayout.WEST);
	//TODO display features in display panel
    }
    /* public static void main(String[]args){
	JFrame frame = new JFrame("test");
	
	frame.setSize(1080,720);
	frame.setContentPane(new MainPage());
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
	}*/
}
