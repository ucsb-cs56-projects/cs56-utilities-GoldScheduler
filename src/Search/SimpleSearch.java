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
/**
 *This class will implement the simple search function, which takes a keyword
 *from a textbar and searches the database for matches.
 */
public class SimpleSearch{
    private JPanel display;
    private JScrollPane scrollableDisplay;
    private JPanel control;
    private JPanel cDisplay;
    private JTextField searchField;
    private JButton searchButton;
    private Scheduler schedule;
    private final Color darkerColor = new Color(165,188,238);
    private final Color lighterColor = new Color(201,212,237);
    //CONSTRUCTORS
    public SimpleSearch(){
        this.schedule = new Scheduler();
    }
    /**
     *Constructor to use saved schedule
     *@param s Saved schedule from database
     */
    public SimpleSearch(Scheduler s){
        this.schedule = s;
    }
    //MAIN DISPLAY
    /**
     *Initializes the display
     */
    public void setDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JScrollPane scrollSearch = new JScrollPane(this.getCourses());
        panel.add(this.getControl(), BorderLayout.NORTH);
        panel.add(scrollSearch, BorderLayout.SOUTH);
        this.display = panel;
    }
    /**
     *@return returns the full set display with both the control and course panels
     */
    public JPanel getDisplay(){
        this.setDisplay();
        return this.display;
    }
    /**
     *@return returns the full display in a scrollPane
     */
    public JScrollPane getScrollDisplay(){
        this.scrollableDisplay = new JScrollPane(this.getDisplay());
        return this.scrollableDisplay;
    }
    //CONTROL DISPLAY
    /**
     *Sets the display panel and then returns it.
     *Creates the display panel that includes a control panel that you can type a keyword into
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
     *@return returns the control panel with the textField and submit button
     */
    public JPanel getControl(){
        this.setControl();
        return this.control;
    }
    //COURSE DISPLAY
    /**
     *Sets courses to a blank screen
     */
    public void setCourses(){
        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(500,565));
        blank.setBackground(this.darkerColor);
        this.cDisplay = blank;
    }
    /**
     *Sets the course display according to a keyword
     *@param key Keyword indicating search requirements
     */
    public void setCourses(String key){
        this.setCoursesBy3DArray(SimpleSearch.getGroupedResults(groupCourseIDResults(this.getResults(key))));
    }
    /**
     *Sets course display according to an unsorted courseList
     */
    public void setCourses(ArrayList<Course> courseList){
        this.setCoursesBy3DArray(SimpleSearch.getGroupedResults(groupCourseIDResults(courseList)));
    }
    /**
     *Sets the course display according to an ArrayList of Courses
     */
    public void setCoursesBy3DArray(ArrayList<ArrayList<ArrayList<Course>>> courseList){
        JPanel courses = new JPanel();
        courses.setBackground(this.darkerColor);
        courses.setLayout(new BoxLayout(courses, BoxLayout.Y_AXIS));
        int numResults = courseList.size();
        if(numResults == 0){
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
            (Days, times, instrucors, [location], addButton)
             */
            int rows = 2;
            int columns = 4;
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
                currentRow++;
                for(Course c:courseList.get(n).get(i)){
                    currentCourse = c;
                    thisLecture = currentCourse.getLect();
                    JButton addToSchedule = new JButton("Add");
                    addToSchedule.addActionListener(new addListener(this.schedule,currentCourse));
                    if(currentCourse.getSect()==null){
                        panelNum[currentRow-1][3].add(addToSchedule);
                    }
                    else{
                        thisSection = currentCourse.getSect();
                        //Row 4+: Section Info
                        JLabel sectDay = new JLabel(thisSection.dayStringShort());
                        JLabel sectTime = new JLabel(thisSection.timeString());
                        JLabel sectInstructor = new JLabel("N/A");
                        panelNum[currentRow][0].add(sectDay);
                        panelNum[currentRow][1].add(sectTime);
                        panelNum[currentRow][2].add(sectInstructor);
                        panelNum[currentRow][3].add(addToSchedule);
                        currentRow++;
                    }
                }
            }
        }
        for(int index = 0 ; index<numResults; index++){
            courses.add(panels[index]);
        }
        this.cDisplay = courses;
    }
    /**
     *@return sets panel to a blank panel and returns it
     */
    public JPanel getCourses(){
        this.setCourses();
        return this.cDisplay;
    }
    /**
     *@param key Keyword to be used to search through the database
     *@return Calls the setCourses using a keyword and returns the resulting panel
     */
    public JPanel getCourses(String key){
        this.setCourses(key);
        return this.cDisplay;
    }
    /**
     *@param list a list of Courses that match the keyword given by the user
     *@return Calls the setCourses using an arrayList of Courses and returns the resulting panel
     */
    public JPanel getCourses(ArrayList<Course> list){
        this.setCoursesBy3DArray(SimpleSearch.getGroupedResults(groupCourseIDResults(list)));
        return this.cDisplay;
    }
    /**
     *@param list A 3D ArrayList that has courses sourted by CourseID and Lecture
     *@return Calls the setCourses using the list returns the resulting panel
     */
    public JPanel getCoursesBy3DArray(ArrayList<ArrayList<ArrayList<Course>>> list){
        this.setCoursesBy3DArray(list);
        return this.cDisplay;
    }
    //RESULTS
    /**
     *Communicates with the database using a desired keyword
     *@param key A string that contains a keyword that will be used to talk to the database
     *@return An arrayList of courses, which are the results.
     *@throws SQLException
     */
    public ArrayList<Course> getResults(String key) {
    	//Course Code is not the real course code. It's my course code
    	//Location is empty String. preReqs is empty Course array. restrictions is empty String array
    	try {
			return CourseConnection.SearchFullTitle(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     *@param fullResultsList an ArrayList of all the courses that satyisfy the user's specifications
     *@return a 2D ArrayList where courses with the same courseID are grouped together
     */
    public static ArrayList<ArrayList<Course>> groupCourseIDResults(ArrayList<Course> fullResultsList){
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
     *@param groupedCourseIDResult an ArrayList of grouped courses by courseID
     *@return a 2D ArrayList where courses with the same time are grouped together
     */
    public static ArrayList<ArrayList<Course>> groupLectResults(ArrayList<Course> groupedCourseIDResult){
        //Just using times to differentiate between courses with the same ID
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
     *@param groupedCourseIDResults an ArrayList of grouped courses by courseID
     *@return a 3D ArrayList wher courses are grouped by courseID then by time
     */
    public static ArrayList<ArrayList<ArrayList<Course>>> getGroupedResults(ArrayList<ArrayList<Course>> groupedCourseIDResults){
        ArrayList<ArrayList<ArrayList<Course>>> groupedResults = new ArrayList<ArrayList<ArrayList<Course>>>();
        for(int i = 0; i<groupedCourseIDResults.size(); i++){
            groupedResults.add(SimpleSearch.groupLectResults(groupedCourseIDResults.get(i)));
        }
        return groupedResults;
    }
    //ACTION LISTENER CLASSES
    /**
     *Class to view a specific course upon a button being pressed
     */
    class viewListener implements ActionListener{
        private Course c1;
        private SimpleSearch p;
        private ArrayList<ArrayList<ArrayList<Course>>> cList;
        public viewListener(Course cIn1, SimpleSearch p, ArrayList<ArrayList<ArrayList<Course>>> cList){
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
            back.addActionListener(new backListener(this.p,this.cList));
            this.p.display.add(buttonPanel, BorderLayout.SOUTH);
            this.p.scrollableDisplay.removeAll();
            this.p.scrollableDisplay.revalidate();
            this.p.scrollableDisplay.repaint();
            this.p.scrollableDisplay.add(this.p.display);
             
        }
    }
    /**
     *Allows the user to return to the populated search view after viewing a specific course
     */
    class backListener implements ActionListener{
        private SimpleSearch outer;
        private ArrayList<ArrayList<ArrayList<Course>>> cList1;
        public backListener(SimpleSearch outerIn, ArrayList<ArrayList<ArrayList<Course>>> cList1){
            this.outer = outerIn;
            this.cList1 = cList1;
        }
        public void actionPerformed(ActionEvent e){
            this.outer.display.removeAll();
            this.outer.display.revalidate();
            this.outer.display.repaint();
            this.outer.display.add(this.outer.getControl(), BorderLayout.NORTH);
            this.outer.display.add(new JScrollPane(this.outer.getCoursesBy3DArray(this.cList1)),
                                   BorderLayout.CENTER);
            this.outer.scrollableDisplay.removeAll();
            this.outer.scrollableDisplay.revalidate();
            this.outer.scrollableDisplay.repaint();
            this.outer.scrollableDisplay.add(this.outer.display);
        }
    }
    /**
     *Upon the press of a button, this class calls the function that searches the database using a keyword.
     */
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
            this.s.display.add(this.s.getControl(),BorderLayout.NORTH);
            this.s.display.add(new JScrollPane(this.s.getCourses(keyword)), BorderLayout.CENTER);
            this.s.scrollableDisplay.removeAll();
            this.s.scrollableDisplay.revalidate();
            this.s.scrollableDisplay.repaint();
            this.s.scrollableDisplay.add(this.s.display);
        }
    }
    /**
     *Allows the user to add classes to their schedule
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
