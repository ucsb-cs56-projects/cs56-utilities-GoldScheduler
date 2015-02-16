//package Course;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
//Course will just store all the data retrieved from the database, so the program doesn't
//have to consistently communicate with the database.
public class Course{
    //All instace variables are public and final, as per Forrest's suggestion
    //Except lecture because I think for functionality we should be able to change
    //the lecture time, professor, etc.
    public final String courseID;
    public final String title;
    public final String fullTitle;
    public final String dept;
    public final double units;
    public final Course[] preReqs;
    public final String[] restrictions;
    public final String[] geFulfill;
    private Lecture lectInfo;
    private JButton view;
    
    
    public Course(String courseID, String title, String fullTitle, String dept, double units,
                  Course[] preReqs, String[] restrictions, String[] geFulfill, Lecture lectInfo){
        this.courseID = courseID;
        this.title = title;
        this.fullTitle = fullTitle;
        this.dept = dept;
        this.units = units;
        this.preReqs = preReqs;
        this.restrictions = restrictions;
        this.geFulfill = geFulfill;
        this.lectInfo = lectInfo;
    }
    //Methods for only private member variable
    public Lecture getLect(){
        return this.lectInfo;
    }
    public void setLect(Lecture lectInfo){
        this.lectInfo = lectInfo;
    }
    
    public void setColor(Color c){
        lectInfo.col = c;
    }
    public JButton getView(){
        return this.view;
    }
    /**
     @return a panel that displays the course information
     */
    public JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 600));
        int countRows = 4;
        for(int i = 0; i<this.preReqs.length; i++){
            countRows++;
        }
        for(int i = 0; i<this.restrictions.length; i++){
            countRows++;
        }
        for(int i = 0; i<this.geFulfill.length; i++){
            countRows++;
        }
        if(this.preReqs.length ==0){
            countRows++;
        }
        if(this.restrictions.length ==0){
            countRows++;
        }
        if(this.geFulfill.length ==0){
            countRows++;
        }
        panel.setLayout(new GridLayout(countRows,2));
        JPanel[][] panelHolder = new JPanel[countRows][2];
        for(int m = 0; m<countRows; m++){
            for(int n= 0; n<2; n++){
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setLayout(new BorderLayout());
                //panelHolder[m][n].setBackground(new Color(193,228,242));
                panel.add(panelHolder[m][n]);
            }
        }
        
        
        JLabel t = new JLabel("Title: ");
        JLabel ft = new JLabel("Full Title: ");
        JLabel u = new JLabel("Units: ");
        JLabel d = new JLabel("Department: ");
        String myUnits = "" + this.units;
        
        JLabel title = new JLabel(this.title);
        JLabel fullTitle = new JLabel(this.fullTitle);
        JLabel units =  new JLabel(myUnits);
        JLabel department =  new JLabel(this.dept);
        
        panelHolder[0][0].add(t, BorderLayout.EAST);
        panelHolder[0][0].setBackground(new Color(193,228,242));
        panelHolder[0][1].add(title, BorderLayout.WEST);
        panelHolder[0][1].setBackground(new Color(193,228,242));
        panelHolder[1][0].add(ft, BorderLayout.EAST);
        panelHolder[1][0].setBackground(new Color(193,228,242));
        panelHolder[1][1].add(fullTitle, BorderLayout.WEST);
        panelHolder[1][1].setBackground(new Color(193,228,242));
        panelHolder[2][0].add(u, BorderLayout.EAST);
        panelHolder[2][0].setBackground(new Color(193,228,242));
        panelHolder[2][1].add(units, BorderLayout.WEST);
        panelHolder[2][1].setBackground(new Color(193,228,242));
        panelHolder[3][0].add(d, BorderLayout.EAST);
        panelHolder[3][0].setBackground(new Color(193,228,242));
        panelHolder[3][1].add(department, BorderLayout.WEST);
        panelHolder[3][1].setBackground(new Color(193,228,242));
        
        JLabel p = new JLabel("PreRequisites: ");
        JLabel g = new JLabel("May apply to GE Requirements: ");
        JLabel r = new JLabel("Restrictions: ");
        
        int newCount = 4;
        JLabel temp;
        panelHolder[4][0].add(p, BorderLayout.EAST);
        panelHolder[4][0].setBackground(new Color(193,228,242));
        if(this.preReqs.length==0){
            temp = new JLabel("None");
            panelHolder[4][1].add(temp, BorderLayout.WEST);
            panelHolder[4][1].setBackground(new Color(193,228,242));
            newCount++;
        }
        else{
            for(Course c:this.preReqs){
                temp = new JLabel(c.courseID);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(new Color(193,228,242));
                newCount++;
            }
        }
        panelHolder[newCount][0].add(g, BorderLayout.EAST);
        panelHolder[newCount][0].setBackground(new Color(193,228,242));
        if(this.geFulfill.length==0){
            temp = new JLabel("None");
            panelHolder[newCount][1].add(temp, BorderLayout.WEST);
            panelHolder[newCount][1].setBackground(new Color(193,228,242));
            newCount++;
        }
        else{
            for(String s:this.geFulfill){
                temp = new JLabel(s);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(new Color(193,228,242));
                newCount++;
            }
        }
        panelHolder[newCount][0].add(r, BorderLayout.EAST);
        panelHolder[newCount][0].setBackground(new Color(193,228,242));
        if(this.restrictions.length==0){
            temp = new JLabel("None");
            panelHolder[newCount][1].add(temp, BorderLayout.WEST);
            panelHolder[newCount][1].setBackground(new Color(193,228,242));
            newCount++;
        }
        else{
            for(String s:this.restrictions){
                temp = new JLabel(s);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(new Color(193,228,242));
                newCount++;
            }
        }
        return panel;
    }
    
}

