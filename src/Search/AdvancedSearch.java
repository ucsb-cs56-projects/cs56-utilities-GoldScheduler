package Search;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.ButtonGroup;
import java.sql.SQLException;
import Course.Course;
import Course.Lecture;
import Schedule.Scheduler;
import connection.courseInfo.CourseConnection;


//TODOs:
//*Note: these comments are also in the code. This is just to give us an idea
//of that still needs to be done

//1. What if there are no results to show?
//2. Pick new colors
//3. Database - Based on the option selected return a list of profesors
//4. Database - Currently stole the method from the simple search, but it will need to be re-implemented
//for a different set of arguments
//5. Loading screen while searching
//6. Make scrollable

public class AdvancedSearch{
    private JPanel display;
    private JPanel control;
    private JPanel cDisplay;
    private Scheduler schedule;
    private final Color darkerColor = new Color(235,215,128);
    private final Color lighterColor = new Color(236,226,178);
    
    private String[] searchOptions = {"Department", "Professor", "General Education"};

    public AdvancedSearch(){
	   this.schedule = new Scheduler();
    }
    public AdvancedSearch(Scheduler s){
	   this.schedule = s;
    }
    
    /**
     @return returns the full set display with both the control and course panels
     */
    public JPanel getDisplay(){
        this.setDisplay();
        return this.display;
    }
    
    /**
     Initializes the display
     */
    public void setDisplay(){
        //Initialize panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(910,600));
        panel.setLayout(new BorderLayout());
        
        panel.add(this.getCourses(), BorderLayout.SOUTH);
        panel.add(this.getControl(), BorderLayout.NORTH);
        this.display = panel;
    }

    
    //SCHEDULE
    /**
     This will get the schedule display
     */
    public JPanel displaySchedule(){
        Scheduler s = this.schedule;
        JPanel display = new JPanel();
        display.add(s.getMain());
        return display;
        
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
    
    
    
    //COURSE RESULTS
    /**
     @return sets panel to a blank panel and returns it
     */
    public JPanel getCourses(){
        this.setCourses();
        return this.cDisplay;
    }
    
    /**
     Sets courses to a blank screen
     */
    //TODO Pick new colors?
    public void setCourses(){
        //TODO: No results to show?
        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(900,533));
        blank.setBackground(this.darkerColor);
        this.cDisplay = blank;
    }
    
    /**
     @return Calls the setCourses using an arrayList of Courses and returns the resulting panel.
     It is the result list
     */
    public JPanel getCourses(ArrayList<Course> list){
        this.setCourses(list);
        return this.cDisplay;
    }
    
    /**
     Sets the course display according to an ArrayList of Courses.
     Stolen from SimpleSearch, but it should work almost the same
     */
    public void setCourses(ArrayList<Course> list){
        JPanel courses = new JPanel();
        courses.setPreferredSize(new Dimension(900,533));
        courses.setBackground(this.darkerColor);
        //Call getResults
        ArrayList<Course> courseList = list;
        int numResults = courseList.size();
        if(numResults == 0){
            this.setCourses();
        }
        //Sets up panel as a grid by how many courses there are
        //courses.setLayout(new GridLayout(numResults, 1));
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
            coursePanel.setPreferredSize(new Dimension(910,150));
            //rows: 1. title
            //      2. header
            //      3. Lecture info
            //      4+. Section info
            //columns: 5
            //(Days, times, instrucors, location, addButton)
            int rows = 3;
            int columns = 5;
            rows++;
            //int numSections = ;
            //for(int i = 0; i<numSections; i++){
            //rows++;
            //}
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
            //JLabel t = new JLabel(c.title);
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
    
    
    //CONTROL
    public JPanel getControl() {
        this.setControl();
        return this.control;
    }
    
    /**
     Sets the display panel and then returns it.
     Creates the display panel that includes a control panel that you can type a keyword into
    */
    public void setControl(){
        JPanel controlPanel = new JPanel();
        int len = this.searchOptions.length;
        //int len = 3;
        //Probably want the control panel to be a horizontal row of buttons
        controlPanel.setPreferredSize(new Dimension(500,66));
        controlPanel.setLayout(new GridLayout(2, 1));
        JPanel[] panelHolder = new JPanel[2];
        for(int i = 0; i<2; i++){
            panelHolder[i] = new JPanel();
            panelHolder[i].setBackground(this.darkerColor);
            controlPanel.add(panelHolder[i]);
        }
        panelHolder[1].setLayout(new GridLayout(1,len));
        JPanel[] bottomHolder = new JPanel[len];
        for(int j = 0; j<len; j++){
            bottomHolder[j] = new JPanel();
            bottomHolder[j].setBackground(this.darkerColor);
            panelHolder[1].add(bottomHolder[j]);
        }
        
        
        //make labels
        JLabel label = new JLabel("Select an option to search by:");
        panelHolder[0].add(label);
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(darkerColor);
        panelHolder[0].add(menuPanel);
        
        //make buttons
        JRadioButton [] radioButtons = new JRadioButton[len];
        ButtonGroup options = new ButtonGroup();
        for(int i=0;i<len;i++){
            radioButtons[i] = new JRadioButton(this.searchOptions[i]);
            options.add(radioButtons[i]);
            radioButtons[i].addActionListener(new radioListener(menuPanel, this,
                                                                radioButtons[i].getText()));
            bottomHolder[i].add(radioButtons[i]);
        }
        
        
        
        this.control = controlPanel;
    }
    
    

    //GETTING RESULTS
    //TODO based on the option selected return a list of profesors
    //Database
    public String[] getList(String s){
        if(s=="Department"){
            String [] m=  {"------","Anthropology (ANTH)", "Art (Creative Studies) (ART)",
                "Art History (ARTHI)", "Art Studio (ARTST)",
                "Asian American Studies (AS AM)", "Astronomy (ASTRO)",
                "Biology (Creative Studies) (BIOL)",
                "Biomolecular Science and Engineering (BMSE)",
                "Black Studies (BL ST)", "Chemical Engineering (CH E)",
                "Chemistry and Biochemistry (CHEM)", "Chicano Studies (CH ST)",
                "Chinese (CHIN)", "Classics (CLASS)", "Communication (COMM)",
                "Comparative Literature (C LIT)", "Computer Science (CMPSC)",
                "Counseling, Clinical, School Psychology (CNCSP)",
                "Dance (DANCE)", "Dynamical Neuroscience (DYNS)",
                "Earth Science (EARTH)", "East Asian Cultural Studies (EACS)",
                "Ecology, Evolution & Marine Biology (EEMB)",
                "Economics (ECON)", "Education (ED)",
                "Electrical Computer Engineering (ECE)",
                "Engineering Sciences (ENGR)", "English (ENGL)",
                "Environmental Science & Management (ESM)",
                "Environmental Studies (ENV S)", "Exercise & Sport Studies (ESS)",
                "Exercise Sport (ES)", "Feminist Studies (FEMST)",
                "Film and Media Studies (FAMST)", "Film Studies (FLMST)",
                "French (FR)", "General Studies (Creative Studies) (GEN S)",
                "Geography (GEOG)", "Geological Sciences (GEOL)", "German (GER)",
                "Global Peace and Security (GPS)", "Global Studies (GLOBL)",
                "Greek (GREEK)", "Hebrew (HEB)", "History (HIST)",
                "Interdisciplinary (INT)", "Italian (ITAL)", "Japanese (JAPAN)",
                "Korean (KOR)", "Latin (LATIN)",
                "Latin American and Iberian Studies (LAIS)", "Linguistics (LING)",
                "Literature (Creative Studies) (LIT)", "Marine Science (MARSC)",
                "Materials (MATRL)", "Mathematics (MATH)", "Mechanical Engineering (ME)",
                "Media Arts and Technology (MAT)", "Medieval Studies (ME ST)",
                "Middle East Studies (MES)", "Military Science (MS)",
                "Music (MUS)", "Music Performance Laboratories (MUS A)", "Philosophy (PHIL)",
                "Physical Activities (PA)", "Physics (PHYS)", "Political Science (POL S)",
                "Portuguese (PORT)", "Psychology (PSY)", "Religious Studies (RG ST)",
                "Renaissance Studies (RENST)", "Slavic (SLAV)", "Sociology (SOC)",
                "Spanish (SPAN)", "Speech & Hearing Sciences (SHS)",
                "Statistics & Applied Probability (PSTAT)", "Technology Management (TMP)",
                "Theater (THTR)", "Writing (WRIT)"};
            return m;
        }
        else if(s=="Professor"){
            String [] m=  {"------","a","b", "c"};
            return m;
        }
        else { //s==GE
           String [] m= {"------","A1","A2", "AMHI", "AMI", "B", "C", "C1", "C2", "C3", "CSB", "CU", "CUC", "CUD", "D", "D1", "D2", "D3", "D4", "E", "E1", "E2", "ETH", "EUR",
               "F", "F1", "F2A", "F2B", "G", "H", "MAJ", "MG", "MUD", "MUG", "NWC", "QNT", "SUB",
               "UG", "UPU", "USB", "USR", "WRT"};
            return m;
        }
    }
    
    /*
     public ArrayList<Course> getCourses() {
     return this.schedule.getCourses();
     }
     */
    
    public ArrayList<Course> getDeptResults(String key){
        return getResults(key, "Department");
    }
    public ArrayList<Course> getProfResults(String key){
        return getResults(key, "Professor");
    }
    public ArrayList<Course> getGEResults(String key){
        return getResults(key, "General Education");
    }



    //TODO: Currently stole the method from the simple search, but it will need to be re-implemented
    //for a different set of arguments
    //Also will not work because the contrustor needs to be fixed in connection
    //Option will represent what capegory to search through Department, Professor, or GE Req
    public ArrayList<Course> getResults(String key, String option){
        //Course Code is not the real course code. It's my course code :)
        //Location is empty String. preReqs is empty Course array. restrictions is empty String array
        /*try {
            return CourseConnection.SearchFullTitle(key);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
         */
        //Make course c
        char [] a = {'M','W'};
        Course [] b = {};
        String [] d = {};
        
        char[] sectDay = {'T'};
        char[] sectDay2 = {'R'};
        
        Lecture newSect = new Lecture(0, "PREBLE, E", 1800, 1850, sectDay, "PHELP 3525", "00000", new Color(129,190,247));
        Lecture newSect1 = new Lecture(0, "PREBLE, E", 1100, 1150, sectDay, "PHELP 3525", "00000", new Color(73,90,252));
        Lecture newSect2 = new Lecture(0, "PREBLE, E", 900, 950, sectDay, "PHELP 3525", "00000", new Color(169,226,195));
        Lecture newSect3 = new Lecture(0, "PREBLE, E", 1900, 1950, sectDay, "PHELP 3525", "00000", new Color(129,190,247));
        //Make course pre
        Lecture newLect = new Lecture(0, "CONRAD, P", 900, 950, a, "PHELP 1110", "93874", new Color(129,190,247));
        Course pre = new Course("CMPSC 8", "INTRO TO COMP SCI", "Introduction to Computer Science", "CMPSC", "4.0", b, d, d, newLect, newSect);
        Course [] cs = {pre};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture(0, "COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159", new Color(73,90,252));
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", "4.0", cs, e, d, l, newSect1);
        //Make course r
        Lecture q = new Lecture(0, "CONRAD, P", 930, 1045, a, "PHELP 3526", "09191", new Color(169,226,195));
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", "4.0", b, d, e, q,newSect2);
        //Make course w
        char [] n = {'T','R'};
        String [] engl = {"A2 - English & Reading Composition"};
        Lecture v = new Lecture(0, "RALEY, M", 1730, 1845, n, "BRDA 1610", "19372", new Color(129,190,247));
        Course w = new Course("ENGL 10", "INTRO TO LIT STUDY", "Introduction to Literary Study",
                              "ENGL", "4.0", b, d, engl, v, newSect3);
        ArrayList<Course> courseList = new ArrayList<Course>();
        courseList.add(pre);
        courseList.add(c);
        courseList.add(r);
        courseList.add(w);
        return courseList;

    }
    
    
    //ACTION LISTENER CLASSES
    class viewListener implements ActionListener{
        private Course c1;
        private AdvancedSearch p;
        private ArrayList<Course> cList;
        public viewListener(Course cIn1, AdvancedSearch p, ArrayList<Course> cList){
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
            back.addActionListener(new backListener(this.p,this.cList));
            this.p.display.add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    class backListener implements ActionListener{
        private AdvancedSearch outer;
        private ArrayList<Course> cList1;
        public backListener(AdvancedSearch outerIn, ArrayList<Course> cList1){
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

    class radioListener implements ActionListener{
        private JPanel p;
        private AdvancedSearch a;
        private String optionString;
        
        public radioListener(JPanel p, AdvancedSearch a, String optionString){
            this.p = p;
            this.a = a;
            this.optionString = optionString;
        }
        //populates the next menu with items related to that search option
        public void actionPerformed(ActionEvent e){
            String [] menuList = getList(optionString);
            JComboBox cMenu = new JComboBox(menuList);
            cMenu.addActionListener(new menuListener(this.a, this.optionString));
            this.p.removeAll();
            this.p.revalidate();
            this.p.repaint();
            this.p.add(cMenu);
        }
    }

    class menuListener implements ActionListener{
        private AdvancedSearch a;
        private String optionString;
        public menuListener(AdvancedSearch a, String optionString){
            this.a = a;
            this.optionString = optionString;
        }
        public void actionPerformed(ActionEvent e){
            ArrayList<Course> result = new ArrayList<Course>();
            JComboBox comboBox = (JComboBox) e.getSource();
            String selectedItem = (String)comboBox.getSelectedItem();
            if(this.optionString == "Department"){
                result = a.getDeptResults(selectedItem);
            }
            if(this.optionString == "Professor"){
                result = a.getProfResults(selectedItem);
            }
            if(this.optionString == "General Education"){
                result = a.getGEResults(selectedItem);
            }
            this.a.cDisplay.removeAll();
            this.a.cDisplay.revalidate();
            this.a.cDisplay.repaint();
            //TODO Loading screen while searching
            this.a.cDisplay.add(getCourses(result), BorderLayout.SOUTH);
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




   
