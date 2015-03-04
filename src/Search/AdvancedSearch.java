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

//1. What if there are no results to show?
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
     Sets courses to a blank screen
     */
    public void setCourses(){
        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(900,533));
        blank.setBackground(this.darkerColor);
        this.cDisplay = blank;
    }
    
    /**
     Sets course display according to an unsorted courseList
     */
    public void setCourses(ArrayList<Course> courseList){
        this.setCoursesBy3DArray(AdvancedSearch.getGroupedResults(groupCourseIDResults(courseList)));
    }
    
    /**
     Sets the course display according to an ArrayList of Courses.
     @param courseList a 3D ArrayList sorted to make it easy to dispay the course results
     */
    public void setCoursesBy3DArray(ArrayList<ArrayList<ArrayList<Course>>> courseList){
        JPanel courses = new JPanel();
        courses.setBackground(this.darkerColor);
        courses.setLayout(new BoxLayout(courses, BoxLayout.Y_AXIS));
        //Number of CourseIDS
        int numResults = courseList.size();
        if(numResults == 0){
            //courses.setPreferredSize(new Dimension(500,567));
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
        }
        //Puts them into a display
        for(int n = 0; n<numResults; n++){
            JPanel coursePanel = new JPanel();
            panels[n].add(coursePanel);
            Course currentCourse;
            Lecture thisLecture;
            Lecture thisSection;
            /*rows: 1. title
             2. header
             3. Lecture info
             4+. Section info
             columns: 5
             (Days, times, instrucors, location, addButton)
             */
            int rows = 2;
            int columns = 5;
            /*
             Right now works for one section per course.
             TODO: Check if course name matches then put them together
             */
            int numLects = courseList.get(n).size();
            int totalNumSects = 0;
            for(int i = 0; i<numLects; i++){
                totalNumSects += courseList.get(n).get(i).size();
            }
            rows+= numLects;
            rows+= totalNumSects;
            coursePanel.setPreferredSize(new Dimension(910,33*rows));
            coursePanel.setLayout(new GridLayout(rows, columns));
            JPanel[][] panelNum = new JPanel[rows][columns];
            for(int y = 0 ; y<rows; y++){
                for(int x = 0; x<columns; x++){
                    panelNum[y][x] = new JPanel();
                    panelNum[y][x].setBackground(this.lighterColor);
                    coursePanel.add(panelNum[y][x]);
                }
            }
            currentCourse = courseList.get(n).get(0).get(0);
            //Row 1: Title and view button
            JLabel t = new JLabel(currentCourse.courseID);
            Font font = t.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            t.setFont(boldFont);
            JButton view = new JButton("View");
            view.addActionListener(new viewListener(currentCourse,this, courseList));
            panelNum[0][0].add(t);
            panelNum[0][1].add(view);
            JLabel numCrs = new JLabel("Sections: " + totalNumSects);
            panelNum[0][2].add(numCrs);
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
            int currentRow = 2;
            for(int i = 0; i<numLects; i++){
                currentCourse = courseList.get(n).get(i).get(0);
                thisLecture = currentCourse.getLect();
                JLabel lectDay = new JLabel(thisLecture.dayStringShort());
                JLabel lectTime = new JLabel(thisLecture.timeString());
                JLabel lectInstructor = new JLabel(thisLecture.professor);
                JLabel lectLocation = new JLabel(thisLecture.location);
                lectDay.setFont(boldFont);
                lectTime.setFont(boldFont);
                lectInstructor.setFont(boldFont);
                lectLocation.setFont(boldFont);
                panelNum[currentRow][0].add(lectDay);
                panelNum[currentRow][1].add(lectTime);
                panelNum[currentRow][2].add(lectInstructor);
                panelNum[currentRow][3].add(lectLocation);
                currentRow++;
                for(int j = 0; j<courseList.get(n).get(i).size(); j++){
                    currentCourse = courseList.get(n).get(i).get(j);
                    thisLecture = currentCourse.getLect();
                    thisSection = currentCourse.getSect();
                    //Row 4+: Section Info
                    JLabel sectDay = new JLabel(thisSection.dayStringShort());
                    JLabel sectTime = new JLabel(thisSection.timeString());
                    JLabel sectInstructor = new JLabel("N/A");
                    JLabel sectLocation = new JLabel(thisSection.location);
                    panelNum[currentRow][0].add(sectDay);
                    panelNum[currentRow][1].add(sectTime);
                    panelNum[currentRow][2].add(sectInstructor);
                    panelNum[currentRow][3].add(sectLocation);
                    JButton addToSchedule = new JButton("Add");
                    addToSchedule.addActionListener(new addListener(this.schedule,currentCourse));
                    panelNum[currentRow][4].add(addToSchedule);
                    currentRow++;
                }
            }
        }
        for(int index = 0 ; index<numResults; index++){
            courses.add(panels[index]);
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
     @return Calls the setCourses using an arrayList of Courses and returns the resulting panel.
     @param list and ArrayList of unsorted courses
     It is the result list
     */
    public JPanel getCourses(ArrayList<Course> list){
        this.setCoursesBy3DArray(AdvancedSearch.getGroupedResults(groupCourseIDResults(list)));
        return this.cDisplay;
    }
    /**
     @return A panel that uses the sorted ArrayList to display the courses
     @param list a 3D ArrayList of sorted courses
     */
    public JPanel getCoursesBy3DArray(ArrayList<ArrayList<ArrayList<Course>>> list){
        this.setCoursesBy3DArray(list);
        return this.cDisplay;
    }
    //CONTROL
    /**
     Calls setControl for a blank panel to display
     */
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
        controlPanel.setPreferredSize(new Dimension(500,66));
        controlPanel.setLayout(new GridLayout(2, 1));
        //Made two panels because top one won't be broken down as much
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
    //TODO handle the SQLException
    //if it throws SQLException, the return value would be null
    public String[] getList(String s){
        if(s=="Department"){
        	
            String[] m;
			try {
				m = connection.courseInfo.CourseConnection.getMajor();
			} catch (SQLException e) {
				m = new String[0];
			}
            	/*
            	{"------","Anthropology (ANTH)", "Art (Creative Studies) (ART)",
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
                */
            return m;
        }
        else if(s=="Professor"){
            String[] m;
			try {
				m = connection.courseInfo.CourseConnection.getProfessor();
			} catch (SQLException e) {
				m = new String[0];
			}
            return m;
        }
        else { //s==GE
           String [] m= {"------","A1","A2", "AMHI", "AMI", "B", "C", "C1", "C2", "C3", "CSB", "CU", "CUC", "CUD", "D", "D1", "D2", "D3", "D4", "E", "E1", "E2", "ETH", "EUR",
               "F", "F1", "F2A", "F2B", "G", "H", "MAJ", "MG", "MUD", "MUG", "NWC", "QNT", "SUB",
               "UG", "UPU", "USB", "USR", "WRT"};
            return m;
        }
    }
    
    public ArrayList<Course> getDeptResults(String key){
        return getResults(key, "Department");
    }
    public ArrayList<Course> getProfResults(String key){
        return getResults(key, "Professor");
    }
    public ArrayList<Course> getGEResults(String key){
        return getResults(key, "General Education");
    }
    //TODO Refactor if you can think of a better way to do it. This seems kind of excessive
    /*NOTE: Because these are static methods, maybe we only need one of these. Currently, this
     is copy and pasted, but maybe instead we can just call these functions from simpleSearch 
     because it's in the same package
     */
    /**
     @param fullResultsList an ArrayList of all the courses that satyisfy the user's specifications
     @return a 2D ArrayList where courses with the same courseID are grouped together
     */
    public static ArrayList<ArrayList<Course>> groupCourseIDResults(ArrayList<Course> fullResultsList){
        //TODO: Not sure if this is the most efficient way to do things
        ArrayList<ArrayList<Course>> groupedResults = new ArrayList<ArrayList<Course>>();
        ArrayList<String> nameList = new ArrayList<String>();
        for(int i = 0; i< fullResultsList.size(); i++){
            if(!(nameList.contains(fullResultsList.get(i).courseID))){
                nameList.add(fullResultsList.get(i).courseID);
                ArrayList<Course> newList = new ArrayList<Course>();
                newList.add(fullResultsList.get(i));
                groupedResults.add(newList);
            }
            else{
                for(int j = 0; j<groupedResults.size(); j++){
                    if(fullResultsList.get(i).courseID.equals(groupedResults.get(j).get(0).courseID)){
                        groupedResults.get(j).add(fullResultsList.get(i));
                    }
                }
            }
        }
        return groupedResults;
    }
    
    /**
     @param groupedCourseResults an ArrayList of grouped courses by courseID
     @return a 2D ArrayList where courses with the same time are grouped together
     */
    public static ArrayList<ArrayList<Course>> groupLectResults(ArrayList<Course> groupedCourseIDResult){
        //TODO: Might be better to use a hashcode, but for now I'll just use time
        ArrayList<ArrayList<Course>> groupedResults = new ArrayList<ArrayList<Course>>();
        ArrayList<String> timeList = new ArrayList<String>();
        for(int i = 0; i< groupedCourseIDResult.size(); i++){
            if(!(timeList.contains(groupedCourseIDResult.get(i).getLect().timeString()))){
                timeList.add(groupedCourseIDResult.get(i).getLect().timeString());
                ArrayList<Course> newList = new ArrayList<Course>();
                newList.add(groupedCourseIDResult.get(i));
                groupedResults.add(newList);
            }
            else{
                for(int j = 0; j<groupedResults.size(); j++){
                    if(groupedCourseIDResult.get(i).getLect().timeString().equals(groupedResults.get(j).get(0).getLect().timeString())){
                        groupedResults.get(j).add(groupedCourseIDResult.get(i));
                    }
                }
            }
        }
        
        return groupedResults;
    }
    
    /**
     @param groupedCourseResults an ArrayList of grouped courses by courseID
     @return a 3D ArrayList wher courses are grouped by courseID then by time
     */
    public static ArrayList<ArrayList<ArrayList<Course>>> getGroupedResults(ArrayList<ArrayList<Course>> groupedCourseIDResults){
        ArrayList<ArrayList<ArrayList<Course>>> groupedResults = new ArrayList<ArrayList<ArrayList<Course>>>();
        for(int i = 0; i<groupedCourseIDResults.size(); i++){
            groupedResults.add(SimpleSearch.groupLectResults(groupedCourseIDResults.get(i)));
        }
        return groupedResults;
    }
    /**
     @param key A keyword taken from the dropdown menu that represents what the user is looking for
     @param option The button clicked indicating which category the keyword belongs to
     */
    public ArrayList<Course> getResults(String key, String option){
        ArrayList<Course> courseList = null;
		try {
			courseList = connection.courseInfo.CourseConnection.getResults(key, option);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return courseList;

    }
    
    
    //ACTION LISTENER CLASSES
    class viewListener implements ActionListener{
        private Course c1;
        private AdvancedSearch p;
        private ArrayList<ArrayList<ArrayList<Course>>> cList;
        public viewListener(Course cIn1, AdvancedSearch p, ArrayList<ArrayList<ArrayList<Course>>> cList){
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
            back.addActionListener(new backListener(this.p,this.cList));
            this.p.display.add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    class backListener implements ActionListener{
        private AdvancedSearch outer;
        private ArrayList<ArrayList<ArrayList<Course>>> cList1;
        public backListener(AdvancedSearch outerIn, ArrayList<ArrayList<ArrayList<Course>>> cList1){
            this.outer = outerIn;
            this.cList1 = cList1;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            this.outer.display.removeAll();
            this.outer.display.revalidate();
            this.outer.display.repaint();
            this.outer.display.add(this.outer.getControl(), BorderLayout.NORTH);
            this.outer.display.add(this.outer.getCoursesBy3DArray(this.cList1), BorderLayout.SOUTH);
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
        @Override
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
        @Override
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




   
