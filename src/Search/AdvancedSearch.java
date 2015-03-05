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
//1. Loading screen while searching
/**
 This class will allow the user to select from: Department, Professor, or General Education
 and indicate from a combobox, populated by category, what they are searching for.
 */
public class AdvancedSearch{
    private JPanel display;
    private JScrollPane scrollableDisplay;
    private JPanel control;
    private JPanel cDisplay;
    private Scheduler schedule;
    private final Color darkerColor = new Color(235,215,128);
    private final Color lighterColor = new Color(236,226,178);
    private String[] searchOptions = {"Department", "Professor", "General Education"};
    //CONSTRUCTORS
    public AdvancedSearch(){
	   this.schedule = new Scheduler();
    }
    /**
     @param s Schedule saved in database
     */
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
     @return returns the full display in a scrollPane
     */
    public JScrollPane getScrollDisplay(){
        this.scrollableDisplay = new JScrollPane(this.getDisplay());
        return this.scrollableDisplay;
    }
    /**
     Initializes the display
     */
    public void setDisplay(){
        //Initialize panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.getControl(), BorderLayout.NORTH);
        panel.add(this.getCourses(), BorderLayout.SOUTH);
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
        blank.setPreferredSize(new Dimension(600,533));
        blank.setBackground(this.darkerColor);
        this.cDisplay = blank;
    }
    /**
     Sets course display according to an unsorted courseList
     */
    public void setCourses(ArrayList<Course> courseList){
        this.setCoursesBy3DArray(SimpleSearch.getGroupedResults(SimpleSearch.groupCourseIDResults(courseList)));
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
             columns: 4
             (Days, times, instrucors, [location,] addButton)
             */
            int rows = 2;
            int columns = 4;
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
            coursePanel.setPreferredSize(new Dimension(600,33*rows));
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
            //panelNum[0][2].add(numCrs);
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
            //panelNum[1][3].add(loc);
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
                //panelNum[currentRow][3].add(lectLocation);
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
                    //panelNum[currentRow][3].add(sectLocation);
                    JButton addToSchedule = new JButton("Add");
                    addToSchedule.addActionListener(new addListener(this.schedule,currentCourse));
                    panelNum[currentRow][3].add(addToSchedule);
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
        this.setCoursesBy3DArray(SimpleSearch.getGroupedResults(SimpleSearch.groupCourseIDResults(list)));
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
    /**
     @param s Indicates which option to populate the combobox with
     @return a String array to select from that relates to the option chosen
     */
    public String[] getList(String s){
        if(s=="Department"){
            String[] m;
			try {
				m = connection.courseInfo.CourseConnection.getMajor();
			} catch (SQLException e) {
				m = new String[0];
			}
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
    /**
     Class to view a specific course upon a button being pressed
     */
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
            this.p.scrollableDisplay.removeAll();
            this.p.scrollableDisplay.revalidate();
            this.p.scrollableDisplay.repaint();
            this.p.scrollableDisplay.add(this.p.display);
        }
    }
    /**
     Allows the user to return to the populated search view after viewing a specific course
     */
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
            this.outer.display.add(new JScrollPane(this.outer.getCoursesBy3DArray(this.cList1)), BorderLayout.CENTER);
            this.outer.scrollableDisplay.removeAll();
            this.outer.scrollableDisplay.revalidate();
            this.outer.scrollableDisplay.repaint();
            this.outer.scrollableDisplay.add(this.outer.display);
        }
    }
    /**
     Populates a combobox according to a selcted radioButton
     */
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
    /**
     Searches the database according to the choice selected from the combobox
     */
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
            result = getResults(selectedItem, this.optionString);
            this.a.display.removeAll();
            this.a.display.revalidate();
            this.a.display.repaint();
            this.a.display.add(this.a.getControl(), BorderLayout.NORTH);
            this.a.display.add(new JScrollPane(getCourses(result)), BorderLayout.CENTER);
            this.a.scrollableDisplay.removeAll();
            this.a.scrollableDisplay.revalidate();
            this.a.scrollableDisplay.repaint();
            this.a.scrollableDisplay.add(this.a.display);
            
        }
    }
    /**
     Allows the user to add classes to their schedule
     */
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




   
