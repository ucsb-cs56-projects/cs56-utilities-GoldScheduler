//package Schedule;
//import java.awt.Container;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
//import Course.Course;
//import Course.Lecture;

/**
 *This class will display schedule. Mostly just used for testing
 */

public class ScheduleGUI{
    private Scheduler s;
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(900,600);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        JPanel sPanel = new JPanel();
        JPanel bp1 = new JPanel();
        JPanel buttonP = new JPanel();
        mainPanel.add(sPanel, BorderLayout.WEST);
        mainPanel.add(bp1, BorderLayout.EAST);
        mainPanel.add(buttonP, BorderLayout.NORTH);
        
        //Make course c
        char [] a = {'M','W'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159", new Color(73,90,252));
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        //Make course r
        Lecture q = new Lecture("CONRAD, P", 930, 1045, a, "PHELP 3526", "09191", new Color(169,226,195));
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", 4.0, b, d, e, q);
        //Make course w
        char [] n = {'T','R'};
        Lecture v = new Lecture("RALEY, M", 1730, 1845, n, "BRDA 1610", "19372", new Color(129,190,247));
        Course w = new Course("ENGL 10", "INTO TO LIT STUDY", "Introduction to Literary Study",
                              "ENGL", 4.0, b, d, d, v);
        //Make schedule
        Scheduler s = new Scheduler();
        //Add courses
        s.add(c);
        s.add(r);
        s.add(w);
        
        //Display it
        bp1.add(s.getControl());
        sPanel.add(s.getPanel());
        
        JButton refresh = new JButton("Refresh page");
        refresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sPanel.removeAll();
                sPanel.revalidate();
                sPanel.repaint();
                bp1.removeAll();
                bp1.revalidate();
                bp1.repaint();
                mainPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();
                bp1.add(s.getControl());
                sPanel.add(s.getPanel());
                mainPanel.add(sPanel, BorderLayout.WEST);
                mainPanel.add(bp1, BorderLayout.EAST);
                mainPanel.add(buttonP, BorderLayout.NORTH);
            }
        });
        buttonP.add(refresh);
        frame.add(mainPanel);
        frame. setVisible(true);
    }
}