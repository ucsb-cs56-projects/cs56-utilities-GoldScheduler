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

public class AdvancedSearch extends JPanel {
    private JPanel display;
    private JPanel control;
    private JPanel cDisplay;
    private Scheduler schedule;
    
    private String[] searchOptions = {"Department", "Professor", "General Education"};

    public AdvancedSearch(){
	   this.schedule = new Scheduler();
    }
    public AdvancedSearch(Scheduler s){
	   this.schedule = s;
    }
    /**
     Initializes the display
     */
    public void setDisplay(){
        //Initialize panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(910,600));
        panel.setLayout(new BorderLayout());
        
        //make control panel
        panel.add(this.getControl(), BorderLayout.NORTH);
        //panel.add(this.getCourses(), BorderLayout.SOUTH);
        panel.add(this.displaySchedule(), BorderLayout.SOUTH);
        this.display = panel;
    }
    
    /**
     @return returns the full set display with both the control and course panels
     */
    public JPanel getDisplay(){
        this.setDisplay();
        return this.display;
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
        blank.setPreferredSize(new Dimension(500,567));
        blank.setBackground(new Color(184,194,247));
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
    
    
    //CONTROL
    public JPanel getControl() {
        return this.control;
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

	//make components
        JLabel searchLabel = new JLabel("Choose an option");
        JRadioButton [] radioButtons = new JRadioButton[3];
        ButtonGroup options = new ButtonGroup();
        
        class radioListener implements ActionListener{
            private JRadioButton button;
            private JPanel p;
            private AdvancedSearch a;
            
            public radioListener(JRadioButton button, JPanel p, AdvancedSearch a){
                this.button = button;
                this.p = p;
                this.a = a;
            }
            
            //populates the next menu with items related to that search option
            public void actionPerformed(ActionEvent e){
                //TODO
                //String optionString = WHICH BUTTON WAS CLICKED
                String optionString = "";
                String [] menuList = getList(optionString);
                JComboBox cMenu = new JComboBox(menuList);
                //Action listener
                class menuListener implements ActionListener{
                    //private JPanel p;
                    private AdvancedSearch a;
                    private String optionString;
                    public menuListener(AdvancedSearch a, String optionString){
                        this.a = a;
                        this.optionString = optionString;
                    }
                    public void actionPerformed(ActionEvent e){
                        JPanel resultPanel = new JPanel();
                        JComboBox comboBox = (JComboBox) e.getSource();
                        String selectedItem = (String)comboBox.getSelectedItem();
                        if(optionString == "Department"){
                            resultPanel = a.getDeptResults(optionString);
                        }
                        if(optionString == "Professor"){
                            resultPanel = a.getProfResults(optionString);
                        }
                        if(optionString == "General Education"){
                            resultPanel = a.getGEResults(optionString);
                        }
                        this.a.cDisplay.removeAll();
                        this.a.cDisplay.revalidate();
                        this.a.cDisplay.repaint();
                        this.a.cDisplay.add(resultPanel, BorderLayout.WEST);
                    }
                    
                }
                cMenu.addActionListener(new menuListener(a, optionString));

            }
            
            /*panelHolder[0].add(searchLabel);
             for(int i=0; i<3; i++){
             panelHolder[1].add(radioButtons[i]);
             }*/
        }
        for(int i=0;i<3;i++){
            radioButtons[i] = new JRadioButton(this.searchOptions[i]);
            options.add(radioButtons[i]);
            radioButtons[i].addActionListener(new radioListener(radioButtons[i], controlPanel, this));
        }
        
        
        
        this.control = controlPanel;
    }
    
    

    //GETTING RESULTS
    //TODO based on the option selected return a list of profesors, departments, or GE requirements
    public String[] getList(String s){
        String [] m= {"a","b", "c"};
        return m;
    }
    
    /*
     public ArrayList<Course> getCourses() {
     return this.schedule.getCourses();
     }
     */
    
    public JPanel getDeptResults(String key){
        return getCourses(getResults(key, "Department"));
    }
    public JPanel getProfResults(String key){
        return getCourses(getResults(key, "Professor"));
    }
    public JPanel getGEResults(String key){
        return getCourses(getResults(key, "General Education"));
    }



    //TODO: Currently stole the method from the simple search, but it will need to be re-implemented
    //for a different set of arguments
    //Also will not work because the contrustor needs to be fixed in connection
    //Option will represent what capegory to search through Department, Professor, or GE Req
    public ArrayList<Course> getResults(String key, String option){
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
}

			       
			       
    
   