package Search;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.sql.SQLException;
import Course.Course;
import Course.Lecture;
import Schedule.Scheduler;
import connection.courseInfo.CourseConnection;


//TODOs:

//1. make scrollable
//2. Auto-generated catch block
//3. Loading screen while searching


/**
 This class will implement the simple search function, which takes a keyword
 from a textbar and searches the database for matches.
 */
public class SimpleSearch{
    //TODO make scrollable
     
    /*
     Initializing components
     */
    private JPanel display;
    private JPanel control;
    private JPanel cDisplay;
    private JTextField searchField;
    private JButton searchButton;
    private Scheduler schedule;
    private JPanel sDisplay;
    private final Color darkerColor = new Color(165,188,238);
    private final Color lighterColor = new Color(201,212,237);
    
    //CONSTRUCOTRS
    public SimpleSearch(){
        this.schedule = new Scheduler();
    }
    /**
     Constructor to use saved schedule
     */
    public SimpleSearch(Scheduler s){
        this.schedule = s;
    }
    
    //MAIN DISPLAY
    /**
     Initializes the display
     */
    public void setDisplay(){
        //Initialize panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(910,600));
        panel.setLayout(new BorderLayout());
        
        //Make control panel
        panel.add(this.getControl(), BorderLayout.NORTH);
        panel.add(this.getCourses(), BorderLayout.SOUTH);
        this.display = panel;
    }
    /**
     @return returns the full set display with both the control and course panels
     */
    public JPanel getDisplay(){
        this.setDisplay();
        return this.display;
    }
    
    
    //CONTROL DISPLAY
    /**
     Sets the display panel and then returns it.
     Creates the display panel that includes a control panel that you can type a keyword into
     */
    public void setControl(){
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(500,33));
        controlPanel.setLayout(new GridLayout(1, 3));
        JPanel[] panelHolder = new JPanel[3];
        for(int i = 0; i<3; i++){
            panelHolder[i] = new JPanel();
            panelHolder[i].setBackground(darkerColor);
            controlPanel.add(panelHolder[i]);
        }
        //Make components
        JLabel searchLabel = new JLabel("Enter course:");
        searchField = new JTextField(20);
        searchButton = new JButton("Submit");
        searchButton.addActionListener(new showResults(this.searchField, this));
        //Add components
        panelHolder[0].add(searchLabel);
        panelHolder[1].add(searchField);
        panelHolder[2].add(searchButton);
        this.control = controlPanel;
    }

    /**
     @return returns the control panel with the textField and submit button
     */
    public JPanel getControl(){
        this.setControl();
        return this.control;
    }

    
    //COURSE DISPLAY
    /**
     Sets courses to a blank screen
     */
    public void setCourses(){
        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(500,567));
        blank.setBackground(this.darkerColor);
        this.cDisplay = blank;
    }
    
    /**
     Sets the course display according to a keyword
     */
    public void setCourses(String key){
        this.setCourses(this.getResults(key));
    }

    /**
     Sets the course display according to an ArrayList of Courses
     */
    public void setCourses(ArrayList<Course> list){
        JPanel courses = new JPanel();
        courses.setPreferredSize(new Dimension(500,567));
        courses.setBackground(this.darkerColor);
        ArrayList<Course> courseList = list;
        int numResults = courseList.size();
        if(numResults == 0){
            courses.setPreferredSize(new Dimension(500,567));
            courses.setBackground(this.darkerColor);
            JLabel noResults = new JLabel("There are no courses that match what you're looking for");
            Font font = noResults.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            noResults.setFont(boldFont);
            courses.add(noResults);
            this.cDisplay = courses;
        }
        //Sets up panel as a grid by how many courses there are
        JPanel[] panels = new JPanel[numResults];
        for(int index = 0 ; index<numResults; index++){
            panels[index] = new JPanel();
            panels[index].setBackground(this.darkerColor);
            courses.add(panels[index]);
        }
        //Puts them into a display
        for(int n = 0; n<numResults; n++){
            Course c = courseList.get(n);
            Lecture thisLecture = c.getLect();
            Lecture thisSection = c.getSect();
            JPanel coursePanel = new JPanel();
            coursePanel.setPreferredSize(new Dimension(910,125));
            /*rows: 1. title
                  2. header
                  3. Lecture info
                  4+. Section info
            columns: 5
            (Days, times, instrucors, location, addButton)
             */
            int rows = 4;
            int columns = 5;
            /*
             Right now works for one section per course.
             TODO: Check if course name matches then put them together
             
             int numSections = ;
             for(int i = 0; i<numSections; i++){
                rows++;
             }
             */
            coursePanel.setLayout(new GridLayout(rows, columns));
            JPanel[][] panelNum = new JPanel[rows][columns];
            for(int y = 0 ; y<rows; y++){
                for(int x = 0; x<columns; x++){
                    panelNum[y][x] = new JPanel();
                    panelNum[y][x].setBackground(this.lighterColor);
                    coursePanel.add(panelNum[y][x]);
                }
            }
            
            //Row 1: Title and view button
            JLabel t = new JLabel(c.courseID);
            Font font = t.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            t.setFont(boldFont);
            JButton view = new JButton("View");
            
            view.addActionListener(new viewListener(c,this, list));
            panelNum[0][0].add(t);
            panelNum[0][1].add(view);
            
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
            panelNum[1][3].add(loc);
            
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
            panelNum[2][3].add(lectLocation);
            
            
            //Row 4+: Section Info
            JLabel sectDay = new JLabel(thisSection.dayStringShort());
            JLabel sectTime = new JLabel(thisSection.timeString());
            JLabel sectInstructor = new JLabel("N/A");
            JLabel sectLocation = new JLabel(thisSection.location);
            panelNum[3][0].add(sectDay);
            panelNum[3][1].add(sectTime);
            panelNum[3][2].add(sectInstructor);
            panelNum[3][3].add(sectLocation);
            JButton addToSchedule = new JButton("Add");
            addToSchedule.addActionListener(new addListener(this.schedule,c));
            panelNum[3][4].add(addToSchedule);
            
            panels[n].add(coursePanel);
        }
        this.cDisplay = courses;
    }

    /**
     @return sets panel to a blank panel and returns it
     */
    public JPanel getCourses(){
        this.setCourses();
        return this.cDisplay;
    }
    /**
     @return Calls the setCourses using a keyword and returns the resulting panel
     */
    public JPanel getCourses(String key){
        this.setCourses(key);
        return this.cDisplay;
    }
    
    /**
     @return Calls the setCourses using an arrayList of Courses and returns the resulting panel
     */
    public JPanel getCourses(ArrayList<Course> list){
        this.setCourses(list);
        return this.cDisplay;
    }
    
    
    //RESULTS
    /**
     Communicates with the database using a desired keyword
     @param key A string that contains a keyword that will be used to talk to the database
     @return An arrayList of courses, which are the results.
     @throws SQLException
     */
    public ArrayList<Course> getResults(String key) {
    	//Course Code is not the real course code. It's my course code :)
    	//Location is empty String. preReqs is empty Course array. restrictions is empty String array
    	try {
			return CourseConnection.SearchFullTitle(key);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    //SCHEDULE
    /**
     @return returns the schedule.
     */
    public Scheduler getSchedule(){
        return this.schedule;
    }
    
    /**
     sets the schedule to an empty one.
    */
    public void resetSchedule(){
        this.schedule = new Scheduler();
    }
    
    /** 
     this is a temporary method to test the add button and view the schedule
     */
    public JPanel displaySchedule(){
        Scheduler s = this.schedule;
        JPanel display = new JPanel();
        display.add(s.getMain());
        return display;
        
    }
    
    
    //ACTION LISTENER CLASSES
    class viewListener implements ActionListener{
        private Course c1;
        private SimpleSearch p;
        private ArrayList<Course> cList;
        public viewListener(Course cIn1, SimpleSearch p, ArrayList<Course> cList){
            this.c1 = cIn1;
            this.p = p;
            this.cList = cList;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.p.display.removeAll();
            this.p.display.revalidate();
            this.p.display.repaint();
            this.p.display.add(this.c1.getPanel(), BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton back = new JButton("Back");
            buttonPanel.add(back);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            this.p.display.add(buttonPanel, BorderLayout.SOUTH);
            class backListener implements ActionListener{
                private SimpleSearch outer;
                private ArrayList<Course> cList1;
                public backListener(SimpleSearch outerIn, ArrayList<Course> cList1){
                    this.outer = outerIn;
                    this.cList1 = cList1;
                }
                public void actionPerformed(ActionEvent e){
                    this.outer.display.removeAll();
                    this.outer.display.revalidate();
                    this.outer.display.repaint();
                    this.outer.display.add(this.outer.getControl(), BorderLayout.NORTH);
                    this.outer.display.add(this.outer.getCourses(this.cList1), BorderLayout.SOUTH);
                }
            }
            back.addActionListener(new backListener(this.p,this.cList));
            this.p.display.add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    
    class showResults implements ActionListener{
        private JTextField text;
        private SimpleSearch s;
        
        public showResults(JTextField text, SimpleSearch s){
            this.text = text;
            this.s = s;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = this.text.getText();
            this.s.display.removeAll();
            this.s.display.revalidate();
            this.s.display.repaint();
             //TODO Loading screen while searching
            this.s.display.add(this.s.getControl(), BorderLayout.NORTH);
            this.s.display.add(this.s.getCourses(keyword), BorderLayout.SOUTH);
        }
    }

    class addListener implements ActionListener{
        private Scheduler sch;
        private Course c;
        
        private JPanel display;
        public addListener(Scheduler sch, Course c){
            this.sch = sch;
            this.c = c;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.sch.add(c);
        }
    }
    
}


