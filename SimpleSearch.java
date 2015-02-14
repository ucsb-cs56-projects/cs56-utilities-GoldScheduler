import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
 
public class SimpleSearch extends JFrame {
     
    //Initializing Components
    JLabel searchLabel, label1;
    JTextField searchField;
    JButton searchButton;

    public SimpleSearch(){
        label1 = new JLabel("Welcome to GOLDER");
        searchLabel = new JLabel("Enter course:");
        searchField = new JTextField(20);
        searchButton = new JButton("Submit");
        searchButton.addActionListener(showResults());  
    }

    public void showResults(){
	//populates the page with search results 
        TextField text = new TextField(20);
        public void actionPerformed(Action e) {
	    text.setText("This worked");
	    text.setBounds(10, 90, 100, 20);
	    add(text);
	}
    }

    public static void main(String args[]){
        new SimpleSearch();
    }
}
        

