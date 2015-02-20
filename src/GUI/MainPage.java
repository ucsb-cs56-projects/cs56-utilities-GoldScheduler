package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import connection.courseInfo.CourseConnection;
import connection.userInfo.UsersConnection;
import Course.*;
import Schedule.*;

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
        //Get saved schedule or make new
        //if(saved schedule)...
        //else
        Scheduler mySchedule = new Scheduler();
        
        
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
        
        class schedListener implements ActionListener{
            private MainPage p;
            private Scheduler s;
            
            public schedListener(MainPage p, Scheduler s){
                this.p = p;
                this.s = s;
            }
            
            @Override
            public void actionPerformed(ActionEvent e){
                this.p.removeAll();
                this.p.revalidate();
                this.p.repaint();
                this.p.add(this.s.getMain(), BorderLayout.NORTH);
                JPanel buttonPanel = new JPanel();
                JButton back = new JButton("Back");
                buttonPanel.add(back);
                buttonPanel.setBackground(Color.LIGHT_GRAY);
                this.p.add(buttonPanel, BorderLayout.SOUTH);
                class backListener implements ActionListener{
                    private MainPage outer;
                    public backListener(MainPage outerIn){
                        this.outer = outerIn;
                    }
                    public void actionPerformed(ActionEvent e){
                        this.outer.removeAll();
                        this.outer.revalidate();
                        this.outer.repaint();
                        this.outer.add(new MainPage(), BorderLayout.NORTH);
                    }
                }
                back.addActionListener(new backListener(this.p));

            }
        }
        
        viewSched.addActionListener(new schedListener(this, mySchedule));
        
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
