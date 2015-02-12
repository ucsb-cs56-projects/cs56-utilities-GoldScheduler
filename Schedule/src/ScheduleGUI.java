import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *This class will display schedule
 * @author Hanna Vigil
 * @version Feb8, 2015
 */

public class ScheduleGUI{
    private Scheduler s;
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        //Make course c
        char [] a = {'T','R'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159");
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        //Make course r
        char [] n = {'M','W'};
        Lecture q = new Lecture("CONRAD, P", 930, 1045, n, "PHELP 3526", "09191");
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                                "CMPSC", 4.0, b, d, e, q);
        //Make schedule
        Scheduler s = new Scheduler();
        //Add courses
        s.add(c);
        s.add(r);
        
        //Make GUI
        s.schedulerGUI();
        //Display it
        JPanel panel = s.getPanel();
        frame.add(panel);
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(900,1200);
        frame. setVisible(true);
    }
}