package GUI;
import javax.swing.*;
import java.sql.SQLException;

import javax.swing.JFrame;


/**
 * The class for start program and switch between panels
 * @author Forrest Sun
 * @author Wesley Pollek
 * @version Feb 12 2015
 */
public class Golder {
    
    static JFrame window;
    
    static Login l;
    static CreateAccount ca;
    static JPanel m;

    public static void main (String[] args) throws SQLException{
        window = new JFrame("GOLDER");
        window.setSize(910,650);
        window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        window.setVisible(true);
        
        //TODO Add a picture while Loading
        
        
        l = new Login();
        ca = new CreateAccount();
        MainPage mainp = new MainPage();
        m = mainp.getDisplay();
        goToLogin();
    }
    
    public static void goToLogin() {
        window.setContentPane(l);
        l.clean();
        window.validate();
    }
    
    public static void goToCreate() {
        window.setContentPane(ca);
        ca.clean();
        window.validate();
    }
    
    public static void goToMain() {
        window.setContentPane(m);
        window.validate();
    }
    
}