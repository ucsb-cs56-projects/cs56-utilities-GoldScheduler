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
    private JButton viewSched;
    private JButton changeInfo;
    private JButton logout;
    private Scheduler mySchedule;
    
    public MainPage(){
        mySchedule = new Scheduler();
    }
    
    public void setDisplay(){
        main = new JPanel();
        display =new JPanel();
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
        
        
        search= new JButton("Search");
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
                this.p.main.add(this.s.getDisplay(), BorderLayout.NORTH);
                JPanel buttonPanel = new JPanel();
                JButton back = new JButton("Back");
                buttonPanel.add(back);
                buttonPanel.setBackground(Color.LIGHT_GRAY);
                this.p.main.add(buttonPanel, BorderLayout.SOUTH);
                class backListener implements ActionListener{
                    private MainPage outer;
                    public backListener(MainPage outerIn){
                        this.outer = outerIn;
                    }
                    public void actionPerformed(ActionEvent e){
                        this.outer.main.removeAll();
                        this.outer.main.revalidate();
                        this.outer.main.repaint();
                        this.outer.main.add(this.outer.getDisplay());
                    }
                }
                back.addActionListener(new backListener(this.p));
                
            }
        }
        search.addActionListener(new searchListener(this, new SimpleSearch(mySchedule)));
        
        
        changeInfo = new JButton("Edit User Information");
        logout = new JButton("Log out");
        logout.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Golder.goToLogin();
            }
            
        });
        
        viewSched = new JButton("View My Schedule");
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
                JButton back = new JButton("Back");
                buttonPanel.add(back);
                buttonPanel.setBackground(Color.LIGHT_GRAY);
                this.p.main.add(buttonPanel, BorderLayout.SOUTH);
                class backListener implements ActionListener{
                    private MainPage outer;
                    public backListener(MainPage outerIn){
                        this.outer = outerIn;
                    }
                    public void actionPerformed(ActionEvent e){
                        this.outer.main.removeAll();
                        this.outer.main.revalidate();
                        this.outer.main.repaint();
                        this.outer.main.add(this.outer.getDisplay());
                    }
                }
                back.addActionListener(new backListener(this.p));
                
            }
        }
        
        viewSched.addActionListener(new schedListener(this, mySchedule));
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
        buttons[1].add(viewSched);
        buttons[2].add(changeInfo);
        buttons[3].add(logout);
        this.main.setLayout(new BorderLayout());
        this.main.add(control, BorderLayout.WEST);
        this.main.add(display, BorderLayout.EAST);
    }
    
    public JPanel getDisplay(){
        this.setDisplay();
        return this.main;
    }
}
