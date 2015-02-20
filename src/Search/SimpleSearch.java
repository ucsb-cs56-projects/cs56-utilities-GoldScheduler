package Search;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.sql.SQLException;
import Course.Course;
import Course.Lecture;
import Schedule.Scheduler;
import connection.courseInfo.CourseConnection;;


/**
 This class will implement the simple search function, which takes a keyword
 from a textbar and searches the database for matches.
 */
public class SimpleSearch{
    //TODO make scrollable
     
    //Initializing Components
    private JPanel display;
    private JPanel control;
    private JPanel cDisplay;
    private JTextField searchField;
    private JButton searchButton;
    private Scheduler schedule;
    
    private JPanel sDisplay;
    
    public SimpleSearch(){
        this.schedule = new Scheduler();
    }
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
    
    /**
     Sets the display panel and then returns it.
     @return the display panel that includes a control panel that you can type a keyword into
     */
    public void setControl(){
        JPanel controlPanel = new JPanel();
        //Probably want the control panel to be a horizontal row of buttons
        controlPanel.setPreferredSize(new Dimension(500,33));
        controlPanel.setLayout(new GridLayout(1, 3));
        JPanel[] panelHolder = new JPanel[3];
        for(int i = 0; i<3; i++){
            panelHolder[i] = new JPanel();
            panelHolder[i].setBackground(new Color(184,194,247));
            controlPanel.add(panelHolder[i]);
        }

        //Make components
        JLabel searchLabel = new JLabel("Enter course:");
        searchField = new JTextField(20);
        searchButton = new JButton("Submit");
        //Action listener for showing results
        class showResults implements ActionListener{
            private JTextField text;
            private SimpleSearch s;
    
            public showResults(JTextField text, SimpleSearch s){
                this.text = text;
                this.s = s;
            }
            //populates the page with search results
            //Calls set courses function
            public void actionPerformed(ActionEvent e) {
                String keyword = this.text.getText();
                this.s.display.removeAll();
                this.s.display.revalidate();
                this.s.display.repaint();
                this.s.display.add(this.s.getControl(), BorderLayout.NORTH);
                this.s.display.add(this.s.getCourses(keyword), BorderLayout.SOUTH);
            }
        }
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

    /**
     Sets courses to a blank screen
     */
    public void setCourses(){
        //TODO: No results to show?
        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(500,567));
        blank.setBackground(new Color(184,194,247));
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
        //Call getResults
        ArrayList<Course> courseList = list;
        int numResults = courseList.size();
        //Sets up panel as a grid by how many courses there are
        //courses.setLayout(new GridLayout(numResults, 1));
        JPanel[] panels = new JPanel[numResults];
        for(int index = 0 ; index<numResults; index++){
            panels[index] = new JPanel();
            panels[index].setBackground(new Color(184,194,247));
            courses.add(panels[index]);
        }
        
        //Puts them into a display
        for(int n = 0; n<numResults; n++){
            Course c = courseList.get(n);
            JPanel coursePanel = new JPanel();
            coursePanel.setPreferredSize(new Dimension(910,100));
            //rows: 1. title
            //      2. header
            //      3. Lecture info
            //TODO! 4+. Section info
            //columns: 5
            //(Days, times, instrucors, location, addButton)
            //TODO Count rows
            int rows = 3;
            int columns = 5;
            //int numSections = ;
            //for(int i = 0; i<numSections; i++){
            //rows++;
            //}
            coursePanel.setLayout(new GridLayout(rows, columns));
            JPanel[][] panelNum = new JPanel[rows][columns];
            for(int y = 0 ; y<rows; y++){
                for(int x = 0; x<columns; x++){
                    panelNum[y][x] = new JPanel();
                    panelNum[y][x].setBackground(new Color(217,220,245));
                    coursePanel.add(panelNum[y][x]);
                }
            }
            
            //Row 1: Title and view button
            //JLabel t = new JLabel(c.title);
            JLabel t = new JLabel(c.courseID);
            Font font = t.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            t.setFont(boldFont);
            JButton view = new JButton("View");
            //Action listener
            class viewListener implements ActionListener{
                private Course c1;
                private SimpleSearch p;
                private ArrayList<Course> cList;
                public viewListener(Course cIn1, SimpleSearch p, ArrayList<Course> cList){
                    this.c1 = cIn1;
                    this.p = p;
                    this.cList = cList;
                }
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
            
            view.addActionListener(new viewListener(c,this, list));
            panelNum[0][0].add(t);
            panelNum[0][1].add(view);
            
            //Row 2: Header
            JLabel d = new JLabel("Day(s)");
            JLabel times = new JLabel("Times");
            JLabel inst = new JLabel("Instructor");
            JLabel loc = new JLabel("Location");
            //Font font = d.getFont();
            //Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            d.setFont(boldFont);
            times.setFont(boldFont);
            inst.setFont(boldFont);
            loc.setFont(boldFont);
            panelNum[1][0].add(d);
            panelNum[1][1].add(times);
            panelNum[1][2].add(inst);
            panelNum[1][3].add(loc);
            //TODO Add button
            JButton addToSchedule = new JButton("Add");
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
            addToSchedule.addActionListener(new addListener(this.schedule,c));
            panelNum[1][4].add(addToSchedule);
            
            
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
    
    
    /**
     Communicates with the database using a desired keyword
     @param key A string that contains a keyword that will be used to talk to the database
     @return An arrayList of courses, which are the results.
     * @throws SQLException 
     */
    //TODO DATABASE!!!!
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
    	
    	
    	
    	
    	/*
        ArrayList<Course> resultList = new ArrayList<Course>();


       //Temporary code
        //Make course c
        char [] a = {'M','W'};
        Course [] b = {};
        String [] d = {};

        //Make course pre
        Lecture newLect = new Lecture("CONRAD, P", 900, 950, a, "PHELP 1110", "93874", new Color(129,190,247));
        Course pre = new Course("CMPSC 8", "INTRO TO COMP SCI", "Introduction to Computer Science", "CMPSC", 4.0, b, d, d, newLect);
        Course [] cs = {pre};
        String [] e = {"CMPSC"};
        //Make course c
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
        
        resultList.add(pre);
        resultList.add(r);
        resultList.add(w);
        resultList.add(c);
        
        
        return resultList;
        */
    }
    
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
}
        

