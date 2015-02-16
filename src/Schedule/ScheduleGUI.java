package Schedule;
//import java.awt.Container;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import Course.Course;
import Course.Lecture;

/**
 *This class will display schedule. Mostly just used for testing
 */

public class ScheduleGUI{
    private Scheduler s;
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(910,627);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //JPanel buttonP = new JPanel();
        //mainPanel.add(buttonP, BorderLayout.NORTH);
        
        //Make course c
        char [] a = {'M','W'};
        Course [] b = {};
        String [] d = {};
        
        //Make course pre
        Lecture newLect = new Lecture("CONRAD, P", 900, 950, a, "PHELP 1110", "93874", new Color(129,190,247));
        Course pre = new Course("CMPSC 8", "INTRO TO COMP SCI", "Introduction to Computer Science", "CMPSC", 4.0, b, d, d, newLect);
        Course [] cs = {pre};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159", new Color(73,90,252));
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, cs, e, d, l);
        //Make course r
        Lecture q = new Lecture("CONRAD, P", 930, 1045, a, "PHELP 3526", "09191", new Color(169,226,195));
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", 4.0, b, d, e, q);
        //Make course w
        char [] n = {'T','R'};
        String [] engl = {"A2 - English & Reading Composition"};
        Lecture v = new Lecture("RALEY, M", 1730, 1845, n, "BRDA 1610", "19372", new Color(129,190,247));
        Course w = new Course("ENGL 10", "INTRO TO LIT STUDY", "Introduction to Literary Study",
                              "ENGL", 4.0, b, d, engl, v);
        
        //Make schedule
        Scheduler s = new Scheduler();
        //Add courses
        s.add(c);
        s.add(r);
        s.add(w);
        
        //Display it
        mainPanel.add(s.getMain(), BorderLayout.SOUTH);
        /*JButton refresh = new JButton("Refresh page");
        refresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();
                mainPanel.add(s.getMain(), BorderLayout.SOUTH);
                mainPanel.add(buttonP, BorderLayout.NORTH);
            }
        });
        
        buttonP.add(refresh);*/
        frame.add(mainPanel);
        frame. setVisible(true);
    }
}