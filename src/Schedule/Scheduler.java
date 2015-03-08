package Schedule;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.sql.SQLException;
import Course.Course;
import Course.Lecture;
import connection.userInfo.*;
/**
 *This class should just take care of adding and removing courses to and from a schedule
 *It will also has a panel instance that will be used for displayins
 */
public class Scheduler {
    private ArrayList<Course> courseList;
    private JPanel mainPanel;
    private JPanel panel;
    private JPanel controlPanel;
    private ArrayList<CourseConflict> cantAdd;
    private int conflicts;
    private JPanel conflictPanel;
    private User currentUser;
    //CONSTRUCTORS
    public Scheduler(ArrayList<Course> courseList){
        this.courseList = courseList;
    }	
    /**
     *@param user represents the current user, so a schedule can be retrieved
     */
    public Scheduler(User user) {
       	this.courseList = new ArrayList<Course>();
      	this.cantAdd = new ArrayList<CourseConflict>();
	this.panel = new JPanel();
        this.controlPanel = new JPanel();
        this.conflicts = 0;
       	this.setUser(user);
    }
    /**
     *@param user Current user
     *@param courseList list of courses that belong to the user
     */
    public Scheduler(User user, ArrayList<Course> courseList) {
        this.courseList = courseList;
        this.cantAdd = new ArrayList<CourseConflict>();
        this.panel = new JPanel();
        this.controlPanel = new JPanel();
        this.conflicts = 0;
        this.setUser(user);
    }
    /**
     *No-arg constructor. Generates an empty schedule.
     */
    public Scheduler(){
        this.courseList = new ArrayList<Course>();
        this.cantAdd = new ArrayList<CourseConflict>();
        this.panel = new JPanel();
        this.controlPanel = new JPanel();
        this.conflicts = 0;
    }
    /**
     *@param user sets the current user
     */
    public void setUser(User user) {
       	this.currentUser = user;
    }
    //EDITS COURSELIST
    /**
     *@param c the course that needs to be added
     *@return whether or not the course was added
     */
    public boolean add(Course c){
        if(c==null)
            return false;
        for(Course d: this.courseList){
            if(c.courseID==d.courseID){
                CourseConflict myConflict = new CourseConflict(c, d, 2);
                cantAdd.add(myConflict);
                conflicts++;
                return false;
            }
            else if(this.timeConflict(c,d)==true){
                CourseConflict myConflict = new CourseConflict(c, d, 1);
                cantAdd.add(myConflict);
                conflicts++;
                return false;
            }
            else if(Scheduler.timeSlot(c.getLect().timeStart)==0||
                    Scheduler.timeSlot(c.getSect().timeStart)==0){
                CourseConflict myConflict = new CourseConflict(c, d, 3);
                cantAdd.add(myConflict);
                conflicts++;
                return false;
            }
            else if(Scheduler.daySlot(c.getSect().days[0])==0){
                CourseConflict myConflict = new CourseConflict(c, d, 4);
                cantAdd.add(myConflict);
                conflicts++;
                return false;
            }
            for(char day: c.getLect().days){
                if(Scheduler.daySlot(day)==0){
                     CourseConflict myConflict = new CourseConflict(c, d, 4);
                     cantAdd.add(myConflict);
                     conflicts++;
                     return false;
                }
	    }
        }
        /* No time conflict
           Different course id
	   Valid time and day slots
         */
        courseList.add(c);
        this.schedulerGUI();
        this.setControl();
        return true;
    }
    /**
     *@param o the course that needs to be removed
     *@return whether or not the course was removed
     */
    public boolean remove(Object o){
        if(!(o instanceof Course))
            return false;
        this.courseList.remove(o);
        this.schedulerGUI();
        this.setControl();
        return true;
    }
    //MAIN PANEL
    /**
     *@return this returns the main panel that holds both the control and the schedule panels
     */
    public JPanel getMain(){
        this.setMain();
        return this.mainPanel;
    }
    /**
     *@return this returns the main panel that holds both the control and the schedule panels
     */
    public JScrollPane getScrollMain(){
        return new JScrollPane(this.getMain());
    }
    /**
     *Updates the main panel, so the display reflects current status. Simply holds both the control and schedule panels
     */
    public void setMain(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(this.getPanel(), BorderLayout.WEST);
        mainPanel.add(this.getControl(), BorderLayout.EAST);
        this.mainPanel = mainPanel;
    }
    //SCHEDULE PANEL
    /**
     *@return this returns the main panel that includes the schedule with classes
     */
    public JPanel getPanel(){
        this.schedulerGUI();
        return this.panel;
    }
    /**
     *Updates the schedule panel to display all classes in ArrayList
     */
    public void schedulerGUI(){
        GridLayout grid = new GridLayout(30, 6);
        JPanel panel = new JPanel();
        panel.setLayout(grid);
        JPanel[][] panelHolder = new JPanel[30][6];
        for(int m = 0; m < 30; m++) {
            for(int n = 0; n < 6; n++) {
                panelHolder[m][n] = new JPanel();
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
        for(int i=0; i<labels.size();i++){
            panelHolder[i+1][0].add(labels.get(i));
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
            Lecture sect = g.getSect();
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
            day1Time.setText(lect.timeString());
            day1Time.setFont(day1Time.getFont().deriveFont(10f));
            day1Time.setBackground(lect.col);
            day1Time.setOpaque(true);
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
                day2Time.setText(lect.timeString());
                day2Time.setFont(day2Time.getFont().deriveFont(10f));
                day2Time.setBackground(lect.col);
                day2Time.setOpaque(true);
                row = this.timeSlot(lect.timeStart);
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
                day3Time.setText(lect.timeString());
                day3Time.setFont(day1Time.getFont().deriveFont(10f));
                day3Time.setBackground(lect.col);
                day3Time.setOpaque(true);
                row = this.timeSlot(lect.timeStart);
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
                day4Time.setText(lect.timeString());
                day4Time.setFont(day4Time.getFont().deriveFont(6f));
                day4Time.setBackground(lect.col);
                day4Time.setOpaque(true);
                row = this.timeSlot(lect.timeStart);
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
            //Add section. There will only be one
	    if(sect!=null){ 
		JLabel sectionLabelTitle = new JLabel();
		sectionLabelTitle.setText(g.courseID);
		sectionLabelTitle.setBackground(sect.col);
		sectionLabelTitle.setOpaque(true);
		JLabel sectionLabelTime = new JLabel();
		sectionLabelTime.setText(sect.timeString());
		sectionLabelTime.setFont(sectionLabelTime.getFont().deriveFont(10f));
		sectionLabelTime.setBackground(sect.col);
		sectionLabelTime.setOpaque(true);
		row = this.timeSlot(sect.timeStart);
		column = this.daySlot(sect.days[0]);
		//Labels
		panelHolder[row][column].add(sectionLabelTitle);
		panelHolder[row+1][column].add(sectionLabelTime);
		//Background
		panelHolder[row][column].setBackground(sect.col);
		panelHolder[row+1][column].setBackground(sect.col);
	    }
        }
        panel.setPreferredSize(new Dimension(600, 600));
        this.panel = panel;
    }
    
    
    //CONTROL PANEL
    /**
     @return this returns a control panel that allows you to delete, view, and change the color of classes
     */
    public JPanel getControl(){
        this.setControl();
        return this.controlPanel;
    }
    /**
     Updates control panel to display options for all classes in list
     */
    public void setControl(){
        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(300, 300));
        control.setLayout(new GridLayout(11,2));
        JPanel[][] panelHolder = new JPanel[11][2];
        for(int m = 0; m < 11; m++) {
            for(int n = 0; n < 2; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setBackground(Color.LIGHT_GRAY);
                control.add(panelHolder[m][n]);
            }
        }
        int slot = 0;
        for(int idx = 0; idx<this.courseList.size(); idx++){
            Course g = this.courseList.get(idx);
            JLabel title = new JLabel(g.courseID);
            String [] colorList = { "Red", "Orange", "Yellow","Green", "Light Blue", "Blue","Purple"};
            JComboBox cMenu = new JComboBox(colorList);
            if(g.getLect().col.equals(new Color(183,62,62))){
                cMenu.setSelectedIndex(0);
            } else if(g.getLect().col.equals(new Color(249,194,164))){
                cMenu.setSelectedIndex(1);
            } else if(g.getLect().col.equals(new Color(253,255,181))){
                cMenu.setSelectedIndex(2);
            } else if(g.getLect().col.equals(new Color(169,226,195))){
                cMenu.setSelectedIndex(3);
            } else if(g.getLect().col.equals(new Color(129,190,247))){
                cMenu.setSelectedIndex(4);
            } else if(g.getLect().col.equals(new Color(73,90,252))){
                cMenu.setSelectedIndex(5);
            }
            else{
                cMenu.setSelectedIndex(6);
            }
            cMenu.addActionListener(new menuListener(g,this));
            panelHolder[slot][0].add(title);
            panelHolder[slot][1].add(cMenu);
            JButton view = new JButton("View");
            panelHolder[slot+1][0].add(view);
            view.addActionListener(new viewListener(g,this));
            JButton delete = new JButton("Remove Course");
            panelHolder[slot+1][1].add(delete);
            delete.addActionListener(new deleteListener(g,this));
            slot+=2;
        }
        if(conflicts>0){
            JButton otherCourses = new JButton("Conflicts: "+ conflicts);
            panelHolder[10][0].add(otherCourses);
            otherCourses.addActionListener(new conflictListener(this));
        }
        JButton save = new JButton("Save Schedule");
        panelHolder[10][1].add(save);
        save.addActionListener(new saveListener()); // Save button addition
        this.controlPanel = control;
    }
    //CONFLICT METHODS
    /**
     @return a Panel displaying course conflicts
     */
    public JPanel getConflict(){
        this.setConflict();
        return this.conflictPanel;
    }
    /**
     Sets the conflict panel
     */
    public void setConflict(){
        Color darkerColor = new Color(161,161,161);
        Color lighterColor = new Color(181,181,181);
        JPanel courses = new JPanel();
        courses.setLayout(new BoxLayout(courses, BoxLayout.Y_AXIS));
        courses.setBackground(darkerColor);
        ArrayList<CourseConflict> courseList = this.cantAdd;
        int numResults = courseList.size();
        //Sets up panel as a grid by how many courses there are
        JPanel[] panels = new JPanel[numResults];
        for(int index = 0 ; index<numResults; index++){
            panels[index] = new JPanel();
            panels[index].setBackground(darkerColor);
            courses.add(panels[index]);
        }
        //Puts them into a display
        for(int n = 0; n<numResults; n++){
            Course c = courseList.get(n).getCourse();
            Lecture thisLecture = c.getLect();
            Lecture thisSection = c.getSect();
            JPanel coursePanel = new JPanel();
            coursePanel.setPreferredSize(new Dimension(750,125));
            int rows = 3;
            int columns = 4;
            rows++;
            coursePanel.setLayout(new GridLayout(rows, columns));
            JPanel[][] panelNum = new JPanel[rows][columns];
            for(int y = 0 ; y<rows; y++){
                for(int x = 0; x<columns; x++){
                    panelNum[y][x] = new JPanel();
                    panelNum[y][x].setBackground(lighterColor);
                    coursePanel.add(panelNum[y][x]);
                }
            }
            //Row 1: Title and view button
            JLabel t = new JLabel(c.courseID);
            Font font = t.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            t.setFont(boldFont);
            JButton view = new JButton("View");
            view.addActionListener(new viewListener(c,this));
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new removeListener(courseList.get(n), this));
            JLabel errorLabel = new JLabel(courseList.get(n).getErrorString());
            errorLabel.setForeground(Color.RED);
            panelNum[0][0].add(t);
            panelNum[0][1].add(view);
            panelNum[0][2].add(removeButton);
            panelNum[0][3].add(errorLabel);
            //Row 2: Header
            JLabel d = new JLabel("Day(s)");
            JLabel times = new JLabel("Times");
            JLabel inst = new JLabel("Instructor");
            JLabel loc = new JLabel("Location");
            d.setFont(boldFont);
            times.setFont(boldFont);
            inst.setFont(boldFont);
            loc.setFont(boldFont);
            panelNum[1][0].add(d);
            panelNum[1][1].add(times);
            panelNum[1][2].add(inst);
            //Row 3: Lecture info
            JLabel lectDay = new JLabel(thisLecture.dayStringShort());
            JLabel lectTime = new JLabel(thisLecture.timeString());
            JLabel lectInstructor = new JLabel(thisLecture.professor);
            JLabel lectLocation = new JLabel(thisLecture.location);
            lectDay.setFont(boldFont);
            lectTime.setFont(boldFont);
            lectInstructor.setFont(boldFont);
            lectLocation.setFont(boldFont);
            panelNum[2][0].add(lectDay);
            panelNum[2][1].add(lectTime);
            panelNum[2][2].add(lectInstructor);
            //Row 4+: Section Info
	    if(thisSection!=null){
		JLabel sectDay = new JLabel(thisSection.dayStringShort());
		JLabel sectTime = new JLabel(thisSection.timeString());
		JLabel sectInstructor = new JLabel("N/A");
      		JButton addButton = new JButton("Add");
		addButton.addActionListener(new addListener(this,c));
		panelNum[3][0].add(sectDay);
		panelNum[3][1].add(sectTime);
		panelNum[3][2].add(sectInstructor);
		panelNum[3][3].add(addButton);
		panels[n].add(coursePanel);
	    }
        }
        this.conflictPanel = courses;
    }
    //ACTION LISTENER CLASSES
    /**
     *Class to change color of the courses
     */
    class menuListener implements ActionListener{
        private Course c;
        private Scheduler sch;
        public menuListener(Course cIn, Scheduler schIn){
            this.c = cIn;
            this.sch = schIn;
        }
        public void actionPerformed(ActionEvent e){
            JComboBox comboBox = (JComboBox) e.getSource();
            String selectedItem = (String)comboBox.getSelectedItem();
            if(selectedItem.equals("Green"))
                this.c.setColor(new Color(169,226,195));
            if(selectedItem.equals("Light Blue"))
                this.c.setColor(new Color(129,190,247));
            if(selectedItem.equals("Yellow"))
                this.c.setColor(new Color(253,255,181));
            if(selectedItem.equals("Blue"))
                this.c.setColor(new Color(73,90,252));
            if(selectedItem.equals("Red"))
                this.c.setColor(new Color(183,62,62));
            if(selectedItem.equals("Orange"))
                this.c.setColor(new Color(249,194,164));
            if(selectedItem.equals("Purple"))
                this.c.setColor(new Color(137,109,143));
            this.sch.mainPanel.removeAll();
            this.sch.mainPanel.revalidate();
            this.sch.mainPanel.repaint();
            this.sch.mainPanel.add(this.sch.getPanel(), BorderLayout.WEST);
            this.sch.mainPanel.add(this.sch.getControl(), BorderLayout.EAST);
        }
    }
    /**
     *Class to save schedule to user
     */
     class saveListener implements ActionListener {
       	@Override
       	public void actionPerformed(ActionEvent e) {
       	//TODO: write code that saves course IDs for the user
	    try {
       		if (Scheduler.this.currentUser == null) System.out.println("NULL"); // This currently prints NULL after an exception is thrown
	      		Scheduler.this.currentUser.deleteSchedule(); // First delete the old schedule so there are no conflicts
	     } catch (SQLException ex) { // this exception is being caught everytime thus far
       		ex.printStackTrace();
	     }
	    for (int i = 0; i < courseList.size(); i++) {
	       	try { // now save each course in the current schedule to the database
		    UsersConnection.saveCourse(Scheduler.this.currentUser,courseList.get(i));
	   	} catch (SQLException ex) {
		    ex.printStackTrace();
	       	}
	    }
	}
     }
    /**
     *Class to remove course from schedule
     */
    class deleteListener implements ActionListener{
        private Scheduler sch;
        private Course c;
        public deleteListener(Course c, Scheduler sch){
            this.c = c;
            this.sch = sch;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch.courseList.remove(this.c);
            this.sch.mainPanel.removeAll();
            this.sch.mainPanel.revalidate();
            this.sch.mainPanel.repaint();
            this.sch.mainPanel.add(this.sch.getPanel(), BorderLayout.WEST);
            this.sch.mainPanel.add(this.sch.getControl(), BorderLayout.EAST);
        }
    }
    /**
     *Class to view a particular class
     */
    class viewListener implements ActionListener{
        Course c1;
        Scheduler sch1;
        public viewListener(Course cIn1, Scheduler schIn1){
            this.c1 = cIn1;
            this.sch1 = schIn1;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch1.mainPanel.removeAll();
            this.sch1.mainPanel.revalidate();
            this.sch1.mainPanel.repaint();
            this.sch1.mainPanel.add(this.c1.getPanel(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(900,33));
            JButton back = new JButton("Back");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.sch1.mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.sch1));
        }
    }
    /**
     *After viewing a class, returns to main schedule page
     */
    class backListener implements ActionListener{
        private Scheduler outer;
        public backListener(Scheduler outerIn){
            this.outer = outerIn;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.outer.mainPanel.removeAll();
            this.outer.mainPanel.revalidate();
            this.outer.mainPanel.repaint();
            this.outer.mainPanel.add(this.outer.getPanel(), BorderLayout.WEST);
            this.outer.mainPanel.add(this.outer.getControl(), BorderLayout.EAST);
        }
    }
    /**
     *Displays conflict panel
     */
    class conflictListener implements ActionListener{
        Scheduler sch1;
        public conflictListener(Scheduler schIn1){
            this.sch1 = schIn1;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch1.mainPanel.removeAll();
            this.sch1.mainPanel.revalidate();
            this.sch1.mainPanel.repaint();
            this.sch1.mainPanel.add(new JScrollPane(sch1.getConflict()), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(900,33));
            JButton back = new JButton("Back");
            
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.sch1.mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.sch1));
        }
    }
    /**
     *Remove conflict
     */
    class removeListener implements ActionListener{
        private Scheduler sch;
        private CourseConflict c;
        public removeListener(CourseConflict c, Scheduler sch){
            this.c = c;
            this.sch = sch;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch.cantAdd.remove(this.c);
            conflicts--;
            this.sch.mainPanel.removeAll();
            this.sch.mainPanel.revalidate();
            this.sch.mainPanel.repaint();
            this.sch.mainPanel.add(this.sch.getConflict(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(900,33));
            JButton back = new JButton("Back");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.sch.mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            back.addActionListener(new backListener(this.sch));
        }
    }
    /**
     *Allows the user to add classes to their schedule
     */
    class addListener implements ActionListener{
        private Scheduler sch;
        private Course c;
        public addListener(Scheduler sch, Course c){
            this.sch = sch;
            this.c = c;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch.add(c);
        }
    }
    //STATIC
    /**
     *@param c Course to be checked for time conflict
     *@param d Course to be checked against
     *@return true if there is time conflict and false if not
     */
    public static boolean timeConflict(Course c, Course d){
	for(char thisDay:c.getLect().days){
	    char sectDay = c.getSect().days[0];
	    for(char thisDay2:d.getLect().days){
		if(thisDay==thisDay2){
		    if(Scheduler.timeConflictHelper(c.getLect(), d.getLect())==true){
			return true;
		    }
		}
		else if(thisDay==d.getSect().days[0]){
		    if(Scheduler.timeConflictHelper(c.getLect(), d.getSect())==true){
		        return true;
		    }
		}
		else if(sectDay==d.getLect().days[0]){
		    if(Scheduler.timeConflictHelper(c.getSect(), d.getLect())==true){
			return true;
		    }
		}
		else if(sectDay==d.getSect().days[0]){
		    if(Scheduler.timeConflictHelper(c.getSect(), d.getSect())==true){
			return true;
		    }
		}
	    }
	}
	return false;
    }
    /**
     *@param lect1
     *@param lect2
     *@return if the two Lecture objects overlap
     */
    public static boolean timeConflictHelper(Lecture lect1, Lecture lect2){
	if(lect1.timeStart<=lect2.timeStart){
            //Check if they start at the same time
            if(lect1.timeStart==lect2.timeStart)
                return true;
            else{
                //lect1 starts first, so we need lect2 to start at the same time
                //or after lect1.timeEnd
                if(lect2.timeStart>=lect1.timeEnd)
                    return false;
                else
                    return true;
            }
        }
        //lect1 starts before lect2
        else {
            if(lect1.timeStart>=lect2.timeEnd)
                return false;
            else
                return true;
        }
    }
    /**
     *@param day character you want converted to column it belongs in
     *@return the column that corresponds to the day
     */
    public static int daySlot(char day){
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
        return slot;
    }
    /**
     *@param time the time you want converted to an int of which row it belongs in
     *@return the row that a particular time belongs in. If it is something:30, it will return the row \
     corresponding to where the 30 is.
     */
    public static int timeSlot(int time){
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
        return slot;
    }
}
