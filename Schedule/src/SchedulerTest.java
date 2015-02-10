import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 *Test class for Scheduler class
 * @author Hanna Vigil
 * @version Feb7, 2015
 * @see Scheduler
 */
public class SchedulerTest{
    /**
     */
    
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
        //Make course m
        char [] n = {'M','W'};
        Lecture q = new Lecture("CONRAD, P", 930, 1045, n, "PHELP 3526", "09191");
        Course r = new Course("CMPSC 56", "ADV APP PROGRAM", "Advanced Applications Programming",
                              "CMPSC", 4.0, b, d, e, q);
        
        Scheduler s = new Scheduler();
        s.add(c);
        
        
        //assertEquals(c, s.get(0));
    }
}