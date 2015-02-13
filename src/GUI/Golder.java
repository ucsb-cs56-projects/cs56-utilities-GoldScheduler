package GUI;
import javax.swing.JFrame;

import GUI.*;


public class Golder {
	
	static JFrame window;
	
	static Login l = new Login();;
	static CreateAccount ca = new CreateAccount();;

	public static void main (String[] args){
	    window = new JFrame();
		
		window.setContentPane(l);
		window.setSize(1080,720);
	    window.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public static void goToLogin() {
		window.setContentPane(l);
		window.validate();
	}
	
	public static void goToCreate() {
		window.setContentPane(ca);
		window.validate();
	}
	
}