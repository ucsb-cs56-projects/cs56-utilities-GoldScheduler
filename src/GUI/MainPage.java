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
     * @param u
     */
    // Updated ctor to load a schedule if user already has one
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
    
	public User getUser() {
		return this.u;
	}

    /** 
     Sets the main display and allows the user to travel between pages
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
        //Get saved schedule or make new
        //if(saved schedule)...
        
        
        search= new JButton("Keyword Search");
        search.addActionListener(new searchListener(this, new SimpleSearch(mySchedule)));
        
        advancedSearch= new JButton("Search");
        advancedSearch.addActionListener(new advSearchListener(this, new AdvancedSearch(mySchedule)));
        
        
        changeInfo = new JButton("Edit User Information");
<<<<<<< HEAD
        changeInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
        
=======
	changeInfo.addActionListener(new changeInfoListener(this));
	//changeInfo.addActionListener(new changeInfoListener(this, new UserInfo()));
>>>>>>> e0df0c45dfcbff5ddce44619c1c15ed0fd9bc9e3
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
    
    public JPanel getDisplay(){
        this.setDisplay();
        return this.main;
    }
    
    //USER
    
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
            this.p.main.add(this.s.getDisplay(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Main Page");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.main.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.p));
        }
    }
    
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
            this.p.main.add(this.s.getMain(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Main Page");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.main.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.p));
            
        }
    }
    //TODO:Implement UserInfo once finished
    class changeInfoListener implements ActionListener{
	private MainPage m; 
	//private UserInfo updateInfo;
	public changeInfoListener(MainPage m){
	//public changeInfoListener(MainPage m, UserInfo updateInfo){
	    this.m = m;
	    //this.updateInfo = updateInfo;
	}

	public void actionPerformed(ActionEvent e){
	    this.m.main.removeAll();
	    this.m.main.revalidate();
	    this.m.main.repaint();
	    //this.p.main.add(this.p.
	    JPanel buttonPanel = new JPanel();
	    JButton back = new JButton("Main Page");
	    buttonPanel.add(back);
	    buttonPanel.setBackground(Color.LIGHT_GRAY);
	    this.m.main.add(buttonPanel, BorderLayout.SOUTH);
	    back.addActionListener(new backListener(this.m));
	}
    }
}


