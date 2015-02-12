import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;


//For GUI
//TODO clean up!
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *Test class for Scheduler class
 * @author Hanna Vigil
 * @version Feb7, 2015
 * @see Scheduler
 */
public class SchedulerTest{
    //TODO: when creating a course, I used a bunch of letter variables, which aren't very descriptive
    //During clean-up should rename variables
    /**
    test add course to schedule
     @see Scheduler#add(Course c)
    */
    @Test
    public void testAddToSchdule(){
        //Make course c
        char [] a = {'T','R'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159");
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        
        Scheduler s = new Scheduler();
        s.add(c);
        assertEquals(c, s.courseList.get(0));
    }
    
    /**
     test2 add multiple courses to schedule
     @see Scheduler#add(Course c)
     */
    @Test
    public void testAddToSchdule2(){
        //Make course c
        char [] a = {'T','R'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159");
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        //Make course m
        char [] n = {'M','W'};
        Lecture q = new Lecture("CONRAD, P", 930, 1045, n, "PHELP 3526", "09191");
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", 4.0, b, d, e, q);
        
        Scheduler s = new Scheduler();
        s.add(c);
        s.add(r);
        assertEquals(c, s.courseList.get(0));
    }

    
    /**
     test time conflict
     @see Scheduler#timeConflict(Course c, Course d)
     */
    @Test
    public void testTimeConflict(){
        //Make course c
        char [] a = {'T','R'};
        Course [] b = {};
        String [] d = {};
        String [] e = {"CMPSC"};
        Lecture l = new Lecture("COSTANZO, M", 1400, 1515, a, "PHELP 1160", "09159");
        Course c = new Course("CMPSC 48", "COMP SCI PROJECT", "Computer Science Project", "CMPSC", 4.0, b, e, d, l);
        //Make course r
        char [] n = {'M','W'};
        Lecture q = new Lecture("CONRAD, P", 930, 1045, n, "PHELP 3526", "09191");
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", 4.0, b, d, e, q);
        
        Scheduler s = new Scheduler();
        assertFalse(s.timeConflict(c,r));

    }
    
    /**
     test timeSlot, which returns an int to the row the time belongs to
     @see Scheduler#timeSlot(int time)
     */
    @Test
    public void testTimeSlot(){
        Scheduler s = new Scheduler();
        assertEquals(9, s.timeSlot(1200));
    }
    
    /**
     test timeSlot2, which returns an int to the row the time belongs to
     @see Scheduler#timeSlot(int time)
     */
    @Test
    public void testTimeSlot2(){
        Scheduler s = new Scheduler();
        assertEquals(10, s.timeSlot(1230));
    }
    
    /**
     test daySlot, which returns an int to the column it belongs in
     @see Scheduler#daySlot(char day)
     */
    @Test
    public void testDaySlot(){
        Scheduler s = new Scheduler();
        assertEquals(2, s.daySlot('T'));
    }
    
}