package Search;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Course.Course;
import Course.Lecture;package Search;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Course.Course;
import Course.Lecture;

public class AdvancedSearch extends JPanel {
    private JPanel display;
    private Jpanel control;
    private Scheduler schedule;
    
    String[] searchOptions = {"Department", "Professor", "General Education"};

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
	Jpanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(910,600));
	panel.setLayout(new BorderLayout());

	//make control panel
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

	//make components
       	JLabel searchLabel = new JLabel("Choose an option");
	JRadioButton radioButtons = new JRadioButton[3];
	for(int i=0;i<3;i++){
	    radioButtons[i] = new JRadioButton(searchOptions[i]);
	}
	class populateOptions implements ActionListener{
	    private JRadioButton button;
	    private AdvancedSearch a;

	    public populateOptions(JRadioButton button, AdvancedSearch a;){
		this.button = button;
		this.a = a;
	    }

	    //populates the next menu with items related to that search option 
	    public void actionPerformed(ActionEvent e){
		"Stub";
	    }
	}
	panelHolder[0].add(searchLabel);
        for(int i=0; i<3; i++){
	    panelHolder[1].add(radioButtons[i]);
	}
    }
}
			  	       	       		    		      	       
			       
			       
    
   