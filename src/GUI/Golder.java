package GUI;
import javax.swing.*;
import java.sql.SQLException;
import javax.swing.JFrame;
import connection.userInfo.User;
/**
 * The class for start program and switch between panels
 * @author Forrest Sun
 * @author James Yang
 * @version Feb 12 2015
 */
public class Golder {
    public static JFrame window;
    static Login l;
    static CreateAccount ca;
    static JPanel m;
    static MainPage mainp;
    static ForgotUser fu;
    public static void main (String[] args) throws SQLException{
        window = new JFrame("GOLDER");
        window.setSize(750,680);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        l = new Login();
        ca = new CreateAccount();
        fu = new ForgotUser();
        goToLogin();
    }
    /**
     Goes to the login display to allow user to login
     */
    public static void goToLogin() {
        window.setContentPane(l);
        l.clean();
        window.validate();
    }
    /**
     Goes to the create account display to allow user to create account
     */
    public static void goToCreate() {
        window.setContentPane(ca);
        ca.clean();
        window.validate();
    }
    /**
     Goes to the main page
     @param u User
     */
    public static void goToMain(User u) {
	mainp = new MainPage(u);
    	mainp.clean();
        window.setContentPane(mainp.getDisplay());
        window.validate();
    }
    /**
     Goes to the forgotten password display
     */
    public static void goToForgot(){
        window.setContentPane(fu);
        fu.clean();
        window.validate();
    }
}
