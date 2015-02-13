//import java.awt.Container;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *This class will display schedule. Mostly just used for testing
 */

public class ScheduleGUI{
    private Scheduler s;
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(900,1200);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        JPanel sPanel = new JPanel();
        JPanel bp1 = new JPanel();
        bp1.setLayout(new GridLayout(10,2));
        JPanel[][] panelHolder = new JPanel[10][2];
        for(int m = 0; m < 10; m++) {
            for(int n = 0; n < 2; n++) {
                panelHolder[m][n] = new JPanel();
                bp1.add(panelHolder[m][n]);
            }
        }
        bp1.setPreferredSize(new Dimension(300, 400));
        mainPanel.add(sPanel, BorderLayout.WEST);
        mainPanel.add(bp1, BorderLayout.EAST);
        
        //Make course c
        char [] a = {'M','W'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159", Color.blue);
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        //Make course r
        Lecture q = new Lecture("CONRAD, P", 930, 1045, a, "PHELP 3526", "09191", Color.yellow);
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                                "CMPSC", 4.0, b, d, e, q);
        //Make course w
        char [] n = {'T','R'};
        Lecture v = new Lecture("RALEY, M", 1730, 1845, n, "BRDA 1610", "19372", Color.gray);
        Course w = new Course("ENGL 10", "INTO TO LIT STUDY", "Introduction to Literary Study",
                              "ENGL", 4.0, b, d, d, v);
        //Make schedule
        Scheduler s = new Scheduler();
        
        //TODO: Add action listeners
        //Add courses
        s.add(c);
        JLabel title1 = new JLabel(c.title);
        String [] colorList = {"Yellow", "Light Blue", "Blue", "Light Gray", "Gray"};
        JComboBox cMenu = new JComboBox(colorList);
        cMenu.setSelectedIndex(0);
        //cMenu.addActionListener(listener);
        panelHolder[0][0].add(title1);
        panelHolder[0][1].add(cMenu);
        JButton view1 = new JButton("View");
        panelHolder[1][0].add(view1);
        JButton delete1 = new JButton("Remove Course");
        panelHolder[1][1].add(delete1);
        
        
        s.add(r);
        JLabel title2 = new JLabel(r.title);
        String [] colorList2 = {"Yellow", "Light Blue", "Blue", "Light Gray", "Gray"};
        JComboBox cMenu2 = new JComboBox(colorList2);
        cMenu2.setSelectedIndex(2);
        //cMenu.addActionListener(listener);
        panelHolder[2][0].add(title2);
        panelHolder[2][1].add(cMenu2);
        JButton view2 = new JButton("View");
        panelHolder[3][0].add(view2);
        JButton delete2 = new JButton("Remove Course");
        panelHolder[3][1].add(delete2);

        
        s.add(w);
        JLabel title3 = new JLabel(w.title);
        String [] colorList3 = {"Yellow", "Light Blue", "Blue", "Light Gray", "Gray"};
        JComboBox cMenu3 = new JComboBox(colorList3);
        cMenu3.setSelectedIndex(4);
        //cMenu.addActionListener(listener);
        panelHolder[4][0].add(title3);
        panelHolder[4][1].add(cMenu3);
        JButton view3 = new JButton("View");
        panelHolder[5][0].add(view3);
        JButton delete3 = new JButton("Remove Course");
        panelHolder[5][1].add(delete3);


        
        //Make GUI
        s.schedulerGUI();
        //Display it
        JPanel panel = s.getPanel();
        sPanel.add(panel);
        frame.add(mainPanel);
        frame. setVisible(true);
    }
}