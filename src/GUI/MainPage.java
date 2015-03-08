package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import connection.courseInfo.CourseConnection;
import connection.userInfo.UsersConnection;
import connection.userInfo.User;
import Course.*;
import Search.*;
import Schedule.*;
/**
* Register Panel
* @author Wesley Pollek
* @author Forrest Sun
* @version Feb 12 2015
*/
public class MainPage {
    public JPanel main;
    private JPanel control;
    private JPanel[] buttons;
    private JPanel display;
    private JButton search;
    private JButton advancedSearch;
    private JButton viewSched;
    private JButton save; // Added new button for saving schedule
    private JButton changeInfo;
    private JButton logout;
    private Scheduler mySchedule;
    private UserInfo userpanel;
    private User u;
    /**
     * @param u the current user
     */
    public MainPage(User u){
    	try {
    		userpanel = UserInfo.getUserPanel();
    		userpanel.init(u);
	    if (u.getSchedule() == null)
       		this.mySchedule = new Scheduler(u);
	    else 
       		this.mySchedule = new Scheduler(u,u.getSchedule());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.u = u;
    }
    /**
     * @return the current user
     */
	public User getUser() {
		return this.u;
	}
    /** 
     * Sets the main display and allows the user to travel between pages
     */
    public void setDisplay(){
        main = new JPanel();
        main.setBackground(new Color(217,221,235));
        display =new JPanel();
        display.setBackground(new Color(217,221,235));
        control=new JPanel();
        control.setBackground(new Color(217,221,235));
        buttons = new JPanel[5];
        control.setLayout(new GridLayout(5,0));
        for(int i=0;i<5;i++){
            buttons[i]= new JPanel();
            control.add(buttons[i]);
            buttons[i].setBackground(new Color(217,221,235));
        }
        search= new JButton("Keyword Search");
        search.addActionListener(new searchListener(this, new SimpleSearch(mySchedule)));
        advancedSearch= new JButton("Search");
        advancedSearch.addActionListener(new advSearchListener(this, new AdvancedSearch(mySchedule)));
        changeInfo = new JButton("Edit User Information");
        changeInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainPage.this.main.removeAll();
				MainPage.this.main.revalidate();
				MainPage.this.main.repaint();
				MainPage.this.main.add(MainPage.this.userpanel, BorderLayout.NORTH);
	            JPanel buttonPanel = new JPanel();
	            JButton back = new JButton("Main Page");
	            buttonPanel.add(back);
	            buttonPanel.setBackground(Color.LIGHT_GRAY);
	            MainPage.this.main.add(buttonPanel, BorderLayout.SOUTH);
	            back.addActionListener(new backListener(MainPage.this));
			}
        });
        logout = new JButton("Log out");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Golder.goToLogin();
            }
        });
        viewSched = new JButton("View My Schedule");
        viewSched.addActionListener(new schedListener(this, this.mySchedule));
        BufferedImage pic;
        Image rpic;
        JLabel logo=new JLabel();
        try{
            pic = ImageIO.read(new File("src/GUI/theLogo.png"));
            rpic = pic.getScaledInstance(400,303, Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(rpic));
        }catch(IOException e){System.out.println("no image");}
        display.add(logo);
        buttons[0].add(search);
        buttons[1].add(advancedSearch);
        buttons[2].add(viewSched);
        buttons[3].add(changeInfo);
        buttons[4].add(logout);
        this.main.setLayout(new BorderLayout());
        this.main.add(control, BorderLayout.WEST);
        this.main.add(display, BorderLayout.EAST);
    }
    /**
     * @return Sets the main display and returns the result
     */
    public JPanel getDisplay(){
        this.setDisplay();
        return this.main;
    }
    //USER
    /**
     * @param u User that the user variable will be changed to
     */
    void setUser(User u) {
        this.u=u;
        try {
			userpanel.init(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    void clean() {
        //TODO anything need to be cleaned up before another user login?
    }
    //ACTION LISTENER CLASSES
    /**
     * Opens a simpleSeach page
     */
    class searchListener implements ActionListener{
        private MainPage p;
        private SimpleSearch s;
        public searchListener(MainPage p, SimpleSearch s){
            this.p = p;
            this.s = s;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.p.main.removeAll();
            this.p.main.revalidate();
            this.p.main.repaint();
            this.p.main.add(this.s.getScrollDisplay(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Main Page");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.main.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.p));
        }
    }
    /**
     * Opens an advanced search page
     */
    class advSearchListener implements ActionListener{
        private MainPage p;
        private AdvancedSearch s;
        public advSearchListener(MainPage p, AdvancedSearch s){
            this.p = p;
            this.s = s;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.p.main.removeAll();
            this.p.main.revalidate();
            this.p.main.repaint();
            this.p.main.add(this.s.getScrollDisplay(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Main Page");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.main.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.p));
        }
    }
    /**
     * Opens up a schedule page
     */
    class schedListener implements ActionListener{
        private MainPage p;
        private Scheduler s;
        public schedListener(MainPage p, Scheduler s){
            this.p = p;
            this.s = s;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.p.main.removeAll();
            this.p.main.revalidate();
            this.p.main.repaint();
            this.p.main.add(this.s.getScrollMain(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Main Page");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.main.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.p));
        }
    }
    /**
     * When the user clicks one of the buttons, this allows the user to return to the main page
     */
    class backListener implements ActionListener{
        private MainPage outer;
        public backListener(MainPage outerIn){
            this.outer = outerIn;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.outer.main.removeAll();
            this.outer.main.revalidate();
            this.outer.main.repaint();
            this.outer.main.add(this.outer.getDisplay());
        }
    }
}