package Search;
import java.awt.*;
import javax.swing.*;
//import javax.swing.JFrame;
import Course.Course;
import Course.Lecture;

public class SearchGUI {
    
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(910,627);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        SimpleSearch s = new SimpleSearch();
        
        mainPanel.add(s.getDisplay(), BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame. setVisible(true);
    }

   /* static JFrame window;
    static SimpleSearch s;
    static AdvancedSearch a;

    JButton advancedSearch;
    JButton simpleSearch;

    public SearchGUI() {
	simpleSearch = new JButton("Simple Search");
	advancedSearch = new JButton("Advanced Search");
	simpleSearch.addActionListener(new ActionListener(goToSimpleSearch()));
	advancedSearch.addActionListener(new ActionListener(goToAdvancedSearch()));

    public static void goToSimpleSearch() { 
	public void actionPerformed(ActionEvent e) {
	    s = new SimpleSearch();
	}
    } 

    public static void goToAdvancedSearch() {
	public void actionPerformed(ActionEvent e) {
	    a = new AdvancedSearch();
	}
    }

    public static void main (String[] args) {
	window = new JFrame();
	window.setSize(1080,720);
	window.setDefaultCloseOperation(JFrame, EXIT_ON_CLOSE);
	window.setVisible(true);
 
    }*/

}