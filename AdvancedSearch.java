import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdvancedSearch extends JPanel {
    JLabel searchLabel;
    JComboBox searchDropMenu;
    JComboBox searchDropMenu2;
    String[] searchOptions = {"Department", "Professor", "General Education"};
    TextField text;

    public AdvancedSearch() {
	searchLavel = new Label("Advanced Search");
	searchDropMenu = new JComboBox(searchOptions);
	searchDropMenu.addActionListener(new ActionListener(mainOptionList())); 
	searchDropMenu2.addActionListener(new ActionListener(subOptionList()));
    }
	
    public void mainOptionList() {
	//gets the selected item from searchDropMenu and passes it into showResults
	String searchOption = (String)searchDropMenu.getSelectedItem();
	public void actionPerformed(ActionEvent e) {
	    "stub";
	}
    }
    
    public void subOptionList() {
	//gets the selected item in searchDropMenu2 and then populates the page with the 
	//results from this query
	String searchOption = (String)searchDropMenu2.getSelectedItem();
	public void actionPerformed(Action e) {
	    "stub";
	}
    }

    public static void main(String[] args){
	new AdvancedSearch();
    }

   