package Search;
import java.awt.*;
import javax.swing.*;
import Course.Course;
import Course.Lecture;
public class AdvancedSearchGUI {
    public static void main(String[] args){
        //Make frame
        JFrame frame = new JFrame();
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(910,650);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        AdvancedSearch s = new AdvancedSearch();
        mainPanel.add(s.getDisplay(), BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame. setVisible(true);
    }
}
