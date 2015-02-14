import javax.swing.JFrame;

public class SearchGUI {

    static JFrame window;
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
 
    }

}