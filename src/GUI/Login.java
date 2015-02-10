import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Login extends JFrame {
    JPanel loginBox = new JPanel(new BorderLayout());
    JTextField username= new JTextField(20);    
    JLabel userLable= new JLable("Enter Username:");
    JTextField password= new JTextField(20);
    JLabel passLable= new JLable("Enter Password:");
    loginBox.add(userLable, BorderLayout.WEST);
    loginBox.add(username, BorderLayout.CENTER);
    loginBox.add(passLable, BorderLayout.WEST);
    loginBox.add(password, BorderLayout.CENTER);
    loginBox.setBackgroundColor(Color.BLUE);
    loginBox.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));
    public Login(){
            super("Enter Login Information");
            setSize(400,400);
            setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
            setBackground(Color.YELLOW)
            add(loginBox);
            pack();
    }
    public static void main (String[] args){
        Login frame = new Login();
        frame.setVisible(true);
    }
}
