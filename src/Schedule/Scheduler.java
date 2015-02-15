//package Schedule;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
//import Course.Course;
//import Course.Lecture;

//import java.awt.ItemSelectable;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;



/**
 *This class should just take care of adding and removing courses to and from a schedule
 *It will also has a panel instance that will be used for displayins
 */
public class Scheduler {
    ArrayList<Course> courseList;
    private JPanel panel;
    private JPanel controlPanel;
    
    //2-arg constructor (Not sure if we'll even need this, but maybe we'll want to copy a schedule?
    public Scheduler(ArrayList<Course> courseList, JPanel panel){
        this.courseList = courseList;
        this.panel = panel;
    }
    //No-arg constructor. Generates an empty schedule
    public Scheduler(){
        this.courseList = new ArrayList<Course>();
        this.panel = new JPanel();
        this.controlPanel = new JPanel();
    }
    
    /**
     @return this returns the main panel that includes the schedule with classes
     */
    public JPanel getPanel(){
        this.schedulerGUI();
        return this.panel;
    }
    /**
     @return this returns a control panel that allows you to delete, view, and change the color of classes
     */
    public JPanel getControl(){
        this.setControl();
        return this.controlPanel;
    }
    
    /**
     @param the course that needs to be added
     @return whether or not the course was added
     */
    public boolean add(Course c){
        if(c==null)
            return false;
        for(Course d: this.courseList){
            if(this.timeConflict(c,d)==true)
                return false;
        }
        //No time conflict
        //TODO: Check for other restrictions?
        courseList.add(c);
        this.schedulerGUI();
        this.setControl();
        return true;
    }
    
    /**
     @param the course that needs to be removed
     @return whether or not the course was removed
     */
    public boolean remove(Object o){
        if(!(o instanceof Course))
            return false;
        this.courseList.remove(o);
        this.schedulerGUI();
        this.setControl();
        return true;
    }
    
    /**
     @param Course to be checked for time conflict
     @param Course to be checked against
     @return true if there is time conflict and false if not
     */
    //Also, not sure if this should be somewhere else or re-written
    //This is just a temporary location. Make static?
    public boolean timeConflict(Course c, Course d){
       //First, check if c starts before d
        if(c.getLect().timeStart<=d.getLect().timeStart){
            //Check if they start at the same time
            if(c.getLect().timeStart==d.getLect().timeStart)
                return true;
            else{
                //c starts first, so we need d to start at the same time or
                //after c.timeEnd
                if(d.getLect().timeStart>=c.getLect().timeEnd)
                    return false;
                else
                    return true;
            }
        }
        else{
            //c.timeStart>d.timeStart, so it must start later
            if(c.getLect().timeStart>=d.getLect().timeEnd)
                return false;
            else
                return true;
        }
    }
    
    /**
     @param day character you want converted to column it belongs in
     @return the column that corresponds to the day
     */
    public int daySlot(char day){
        int slot;
        switch(day){
            case 'M':
                slot = 1;
                break;
            case 'T':
                slot=2;
                break;
            case 'W':
                slot = 3;
                break;
            case 'R':
                slot=4;
                break;
            case 'F':
                slot = 5;
                break;
            default:
                slot = 0;
                break;
        }
        if(slot==0){
            //TODO: Error not a valid day
        }
        return slot;
    }
    
    /**
     @param time the time you want converted to an int of which row it belongs in
     @return the row that a particular time belongs in. If it is something:30, it will return the row\
     corresponding to where the 30 is.
     */
    public int timeSlot(int time){
        int slot;
        switch(time){
            case 800:
                slot=1;
                break;
            case 830:
                slot=2;
                break;
            case 900:
                slot=3;
                break;
            case 930:
                slot=4;
                break;
            case 1000:
                slot=5;
                break;
            case 1030:
                slot=6;
                break;
            case 1100:
                slot=7;
                break;
            case 1130:
                slot=8;
                break;
            case 1200:
                slot=9;
                break;
            case 1230:
                slot=10;
                break;
            case 1300:
                slot=11;
                break;
            case 1330:
                slot=12;
                break;
            case 1400:
                slot=13;
                break;
            case 1430:
                slot=14;
                break;
            case 1500:
                slot=15;
                break;
            case 1530:
                slot=16;
                break;
            case 1600:
                slot=17;
                break;
            case 1630:
                slot=18;
                break;
            case 1700:
                slot=19;
                break;
            case 1730:
                slot=20;
                break;
            case 1800:
                slot=21;
                break;
            case 1830:
                slot=22;
                break;
            case 1900:
                slot=23;
                break;
            case 1930:
                slot=24;
                break;
            case 2000:
                slot=25;
                break;
            case 2030:
                slot=26;
                break;
            case 2100:
                slot=27;
                break;
            case 2130:
                slot=28;
                break;
            case 2200:
                slot=29;
                break;
            default:
                slot=0;
                break;
        }
        if(slot==0){
            //TODO: ERROR
        }
        return slot;
    }
    
    /**
     Updates the schedule panel to display all classes in ArrayList
     */
    public void schedulerGUI(){
        GridLayout grid = new GridLayout(30, 6);
        JPanel panel = new JPanel();
        panel.setLayout(grid);
        //So apparently, you can't select a specific cell that you'd like to fill, so
        //instead you just make a bunch of blank ones and keep references to them.
        //That's what this is.
        JPanel[][] panelHolder = new JPanel[30][6];
        for(int m = 0; m < 30; m++) {
            for(int n = 0; n < 6; n++) {
                panelHolder[m][n] = new JPanel();
                //TODO
                if(n==0||m==0)
                    panelHolder[m][n].setBackground(Color.white);
                else if(m%2==1)
                    panelHolder[m][n].setBackground(new Color(212,212,212));
                else
                    panelHolder[m][n].setBackground(new Color(220,220,220));
                panel.add(panelHolder[m][n]);
            }
        }
        
        JLabel holder = new JLabel();
        String temp;
        //Make a label for times 8:00 AM-10:00 PM at 30 minute intervals
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        //8:00 AM - 11:30 AM
        for (int i = 8; i < 12; i++){
            for(int j=0; j<2;j++){
                if(j==0)
                    temp = i + ":00 AM";
                else
                    temp = i + ":30 AM";
                holder = new JLabel();
                holder.setText(temp);
                labels.add(holder);
            }
        }
        //12:00 PM - 9:30 PM
        temp = "12:00 PM";
        holder = new JLabel();
        holder.setText(temp);
        labels.add(holder);
        temp = "12:30 PM";
        holder = new JLabel();
        holder.setText(temp);
        labels.add(holder);
        for (int i = 1; i < 10; i++){
            for(int j=0; j<2;j++){
                if(j==0)
                    temp = i + ":00 PM";
                else
                    temp = i + ":30 PM";
                holder = new JLabel();
                holder.setText(temp);
                labels.add(holder);
            }
        }
        //10:00 PM - Separate because there's no 10:30
        temp = "10:00 PM";
        holder = new JLabel();
        holder.setText(temp);
        labels.add(holder);
        
        //Display labels
        for(int i=1; i<labels.size();i++){
            panelHolder[i][0].add(labels.get(i));
        }
        
        JLabel m = new JLabel();
        m.setText("Monday");
        JLabel t = new JLabel();
        t.setText("Tuesday");
        JLabel w = new JLabel();
        w.setText("Wednesday");
        JLabel r = new JLabel();
        r.setText("Thursday");
        JLabel f = new JLabel();
        f.setText("Friday");
        m.setFont(m.getFont().deriveFont(11f));
        t.setFont(t.getFont().deriveFont(11f));
        w.setFont(w.getFont().deriveFont(11f));
        r.setFont(r.getFont().deriveFont(11f));
        f.setFont(f.getFont().deriveFont(11f));
        panelHolder[0][1].add(m);
        panelHolder[0][2].add(t);
        panelHolder[0][3].add(w);
        panelHolder[0][4].add(r);
        panelHolder[0][5].add(f);
        
        for(Course g:this.courseList){
            int start = g.getLect().timeStart;
            int end = g.getLect().timeEnd;
            Lecture lect = g.getLect();
            //Find how many panels needed
            int numPanels = 0;
            if(end-start==50){
                numPanels = 2;
            }
            if(end-start==115){
                numPanels = 3;
            }
            if(end-start==150){
                numPanels = 4;
            }
            if(numPanels==0){
                //The class is not 50 minutes, 1hr and 15min, or 1hr and 50mins (Writing classes).
                //Are there any other class lengths?
            }
            
            int row;
            int column;
            //Title, location, time
            //Day 1
            JLabel day1Title = new JLabel();
            day1Title.setText(g.courseID);
            day1Title.setBackground(lect.col);
            day1Title.setOpaque(true);
            
            JLabel day1Location = new JLabel();
            day1Location.setText(lect.location);
            day1Location.setFont(day1Location.getFont().deriveFont(10f));
            day1Location.setBackground(lect.col);
            day1Location.setOpaque(true);
            
            JLabel day1Time = new JLabel();
            day1Time.setText(lect.timeStartString() + " - " + lect.timeEndString());
            day1Time.setFont(day1Time.getFont().deriveFont(10f));
            day1Time.setBackground(lect.col);
            day1Time.setOpaque(true);
            
            //TODO: Are there any classes that are just one day a week?
            if(g.getLect().days.length>1){
                //DAY 2
                JLabel day2Title = new JLabel();
                day2Title.setText(g.courseID);
                day2Title.setBackground(lect.col);
                day2Title.setOpaque(true);
                
                JLabel day2Location = new JLabel();
                day2Location.setText(lect.location);
                day2Location.setFont(day1Location.getFont().deriveFont(10f));
                day2Location.setBackground(lect.col);
                day2Location.setOpaque(true);
                
                JLabel day2Time = new JLabel();
                day2Time.setText(lect.timeStartString() + " - " + lect.timeEndString());
                day2Time.setFont(day2Time.getFont().deriveFont(10f));
                day2Time.setBackground(lect.col);
                day2Time.setOpaque(true);
                
                
                row = this.timeSlot(lect.timeStart)-1;
                column = 0;
                if(numPanels>=2){
                    //If just 2, show time and course ID for day 1 and 2
                    //Labels
                    column = this.daySlot(lect.days[0]);
                    panelHolder[row][column].add(day1Title);
                    panelHolder[row+1][column].add(day1Time);
                    //Background
                    panelHolder[row][column].setBackground(lect.col);
                    panelHolder[row+1][column].setBackground(lect.col);
                    
                    //Labels
                    column = this.daySlot(lect.days[1]);
                    panelHolder[row][column].add(day2Title);
                    panelHolder[row+1][column].add(day2Time);
                    //Background
                    panelHolder[row][column].setBackground(lect.col);
                    panelHolder[row+1][column].setBackground(lect.col);
                }
                if(numPanels>=3){
                    //If 3, add location
                    column = this.daySlot(lect.days[0]);
                    panelHolder[row+2][column].add(day1Location);
                    panelHolder[row+2][column].setBackground(lect.col);
                    column = this.daySlot(g.getLect().days[1]);
                    panelHolder[row+2][column].add(day2Location);
                    panelHolder[row+2][column].setBackground(lect.col);
                }
                if(numPanels==4){
                    //If four, just make another panel the same color
                    column = this.daySlot(lect.days[0]);
                    panelHolder[row+3][column].setBackground(lect.col);
                    column = this.daySlot(g.getLect().days[1]);
                    panelHolder[row+3][column].setBackground(lect.col);
                }
            }
            if(g.getLect().days.length>2){
               //DAY 3
                JLabel day3Title = new JLabel();
                day3Title.setText(g.courseID);
                day3Title.setBackground(lect.col);
                day3Title.setOpaque(true);
                
                JLabel day3Location = new JLabel();
                day3Location.setText(lect.location);
                day3Location.setFont(day3Location.getFont().deriveFont(10f));
                day3Location.setBackground(lect.col);
                day3Location.setOpaque(true);
                
                JLabel day3Time = new JLabel();
                day3Time.setText(lect.timeStartString() + " - " + lect.timeEndString());
                day3Time.setFont(day1Time.getFont().deriveFont(10f));
                day3Time.setBackground(lect.col);
                day3Time.setOpaque(true);
                
                
                row = this.timeSlot(lect.timeStart)-1;
                column = 0;
               if(numPanels>=2){
                   //Labels
                   column = this.daySlot(lect.days[2]);
                   panelHolder[row][column].add(day3Title);
                   panelHolder[row+1][column].add(day3Time);
                   //Background
                   panelHolder[row][column].setBackground(lect.col);
                   panelHolder[row+1][column].setBackground(lect.col);
                }
                if(numPanels>=3){
                    panelHolder[row+2][column].add(day3Location);
                    panelHolder[row+2][column].setBackground(lect.col);
                }
                if(numPanels==4){
                    panelHolder[row+3][column].setBackground(lect.col);
                }
            }
            if(g.getLect().days.length>3){
                //DAY 4
                JLabel day4Title = new JLabel();
                day4Title.setText(g.courseID);
                day4Title.setBackground(lect.col);
                day4Title.setOpaque(true);
                
                JLabel day4Location = new JLabel();
                day4Location.setText(lect.location);
                day4Location.setFont(day4Location.getFont().deriveFont(10f));
                day4Location.setBackground(lect.col);
                day4Location.setOpaque(true);
                
                JLabel day4Time = new JLabel();
                day4Time.setText(lect.timeStartString() + " - " + lect.timeEndString());
                day4Time.setFont(day4Time.getFont().deriveFont(10f));
                day4Time.setBackground(lect.col);
                day4Time.setOpaque(true);
                
                
                row = this.timeSlot(lect.timeStart)-1;
                column = 0;
                if(numPanels>=2){
                    column = this.daySlot(lect.days[3]);
                    //Labels
                    panelHolder[row][column].add(day4Title);
                    panelHolder[row+1][column].add(day4Time);
                    //Background
                    panelHolder[row][column].setBackground(lect.col);
                    panelHolder[row+1][column].setBackground(lect.col);
                }
                if(numPanels>=3){
                    panelHolder[row+2][column].add(day4Location);
                    panelHolder[row+2][column].setBackground(lect.col);
                }
                if(numPanels==4){
                    panelHolder[row+3][column].setBackground(lect.col);
                }
            }
        }
        panel.setPreferredSize(new Dimension(600, 600));
        this.panel = panel;
    }
    
    /**
     Updates control panel to display options for all classes in list
     */
    public void setControl(){
        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(300, 400));
        control.setLayout(new GridLayout(10,2));
        JPanel[][] panelHolder = new JPanel[10][2];
        for(int m = 0; m < 10; m++) {
            for(int n = 0; n < 2; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setBackground(Color.LIGHT_GRAY);
                control.add(panelHolder[m][n]);
            }
        }
        int slot = 0;
        for(Course g:this.courseList){
            JLabel title = new JLabel(g.title);
            String [] colorList = {"Yellow", "Light Blue", "Blue", "Light Gray", "Gray"};
            JComboBox cMenu = new JComboBox(colorList);
            //Action listener
            class ColorListener implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    JComboBox comboBox = (JComboBox) e.getSource();
                    String selectedItem = (String)comboBox.getSelectedItem();
                    if(selectedItem.equals("Yellow"))
                        g.setColor(new Color(254,255,121));
                    if(selectedItem.equals("Light Blue"))
                        g.setColor(new Color(129,190,247));
                    if(selectedItem.equals("Blue"))
                        g.setColor(new Color(73,90,252));
                    if(selectedItem.equals("Light Gray"))
                        g.setColor(new Color(192,192,192));
                    if(selectedItem.equals("Gray"))
                        g.setColor(new Color(150,150,150));
                }

            }
            cMenu.addActionListener(new ColorListener());
            if(g.getLect().col.equals(new Color(254,255,121))){
                cMenu.setSelectedIndex(0);
            } else if(g.getLect().col.equals(new Color(129,190,247))){
                cMenu.setSelectedIndex(1);
            } else if(g.getLect().col.equals(new Color(73,90,252))){
                cMenu.setSelectedIndex(2);
            } else if(g.getLect().col.equals(new Color(192,192,192))){
                cMenu.setSelectedIndex(3);
            }
            else{
                cMenu.setSelectedIndex(4);
            }
            
            panelHolder[slot][0].add(title);
            panelHolder[slot][1].add(cMenu);
            JButton view = new JButton("View");
            panelHolder[slot+1][0].add(view);
            JButton delete = new JButton("Remove Course");
            panelHolder[slot+1][1].add(delete);
            delete.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    courseList.remove(g);
                }
            });
            
            slot+=2;
        }
        JButton refresh = new JButton("refresh");
        
        this.controlPanel = control;
    }
    
    
    
}

