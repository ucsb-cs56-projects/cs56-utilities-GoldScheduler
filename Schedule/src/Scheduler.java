import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;

/**
 *This class should just take care of adding and removing courses to and from a schedule
 * @author Hanna Vigil
 * @version Feb7, 2015
 */
public class Scheduler extends ArrayList<Course>{
    //No-arg constructor just generates an empty schedule
    public Scheduler(){
        super(0);
    }
    /**
     @param the course that needs to be added
     @return whether or not the course was added
     */
    public boolean add(Course c){
        if(c==null)
            return false;
        //Check if times conflict: if so, return false or throw exception
        //TODO: right now returns false
        for(Course d: this){
            if(this.timeConflict(c,d)==true)
                return false;
        }
        //No time conflict
        //TODO: Check for restrictions?
        super.add(c);
        return true;
    }
    
    /**
     @param Course to be checked for time conflict
     @param Course to be checked against
     @return true is there is time conflict and false if not
     */
    //TODO: This part needs to be tested still
    public boolean timeConflict(Course c, Course d){
        //First, check if c starts before d
        if(c.getLect().timeStart<=d.getLect().timeStart){
            //Check if they start at the same time
            if(c.getLect().timeStart==d.getLect().timeStart)
                return true;
            else{
                //c starts first, so we need d to start at the same time or
                //after c.timeEnd
                if(d.getLect().timeStart>=c.getLect().timeEnd)
                    return false;
                else
                    return true;
            }
        }
        else{
            //c.timeStart>d.timeStart, so it must start later
            if(c.getLect().timeStart>=d.getLect().timeEnd)
                return false;
            else
                return true;
        }
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        GridLayout grid = new GridLayout(30, 6);
        JPanel panel = new JPanel();
        panel.setLayout(grid);
        //So apparently, you can't select a specific cell that you'd like to fill, so
        //instead you just make a bunch of blank ones and keep references to them.
        //That's what this is.
        JPanel[][] panelHolder = new JPanel[30][6];
        for(int m = 0; m < 30; m++) {
            for(int n = 0; n < 6; n++) {
                panelHolder[m][n] = new JPanel();
                panel.add(panelHolder[m][n]);
            }
        }
        
        JLabel holder = new JLabel();
        String temp;
        //Make a label for times 8:00 AM-10:00 PM at 30 minute intervals
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        //8:00 AM - 11:30 AM
        for (int i = 8; i < 12; i++){
            for(int j=0; j<2;j++){
                if(j==0)
                    temp = i + ":00 AM";
                else
                    temp = i + ":30 AM";
                holder = new JLabel();
                holder.setText(temp);
                labels.add(holder);
            }
        }
        //12:00 PM - 9:30 PM
        temp = "12:00 PM";
        holder = new JLabel();
        holder.setText(temp);
        labels.add(holder);
        for (int i = 1; i < 10; i++){
            for(int j=0; j<2;j++){
                if(j==0)
                    temp = i + ":00 PM";
                else
                    temp = i + ":30 PM";
                holder = new JLabel();
                holder.setText(temp);
                labels.add(holder);
            }
        }
        //10:00 PM - Separate because there's no 10:30
        temp = "10:00 PM";
        holder = new JLabel();
        holder.setText(temp);
        labels.add(holder);
        
        //Display labels
        for(int i=1; i<labels.size();i++){
            panelHolder[i][0].add(labels.get(i));
        }
        
        JLabel m = new JLabel();
        m.setText("Monday");
        JLabel t = new JLabel();
        t.setText("Tuesday");
        JLabel w = new JLabel();
        w.setText("Wednesday");
        JLabel r = new JLabel();
        r.setText("Thursday");
        JLabel f = new JLabel();
        f.setText("Friday");
        panelHolder[0][1].add(m);
        panelHolder[0][2].add(t);
        panelHolder[0][3].add(w);
        panelHolder[0][4].add(r);
        panelHolder[0][5].add(f);
        
        
        //test course
        //This is just an example of what I was thinking it would look like
        //If you guys have any other suggestions I'm open
        //Ideally, once the database is populated this is where we would get the
        //info then store it in a class instance then display it.
        JLabel courseEx = new JLabel();
        courseEx.setText("CMPSC 48");
        JLabel courseEx2 = new JLabel();
        courseEx2.setText("PHELP 1160");
        JLabel courseEx3 = new JLabel();
        courseEx3.setText("2:00-3:15");
        courseEx.setBackground(Color.blue);
        courseEx.setOpaque(true);
        courseEx2.setBackground(Color.blue);
        courseEx2.setOpaque(true);
        courseEx3.setBackground(Color.blue);
        courseEx3.setOpaque(true);
        
        JLabel courseEx4 = new JLabel();
        courseEx4.setText("CMPSC 48");
        JLabel courseEx5 = new JLabel();
        courseEx5.setText("PHELP 1160");
        JLabel courseEx6 = new JLabel();
        courseEx6.setText("2:00-3:15");
        courseEx4.setBackground(Color.blue);
        courseEx4.setOpaque(true);
        courseEx5.setBackground(Color.blue);
        courseEx5.setOpaque(true);
        courseEx6.setBackground(Color.blue);
        courseEx6.setOpaque(true);
        
        panelHolder[13][2].add(courseEx);
        panelHolder[14][2].add(courseEx2);
        panelHolder[15][2].add(courseEx3);
        panelHolder[13][4].add(courseEx4);
        panelHolder[14][4].add(courseEx5);
        panelHolder[15][4].add(courseEx6);
        panelHolder[13][2].setBackground(Color.blue);
        panelHolder[14][2].setBackground(Color.blue);
        panelHolder[15][2].setBackground(Color.blue);
        panelHolder[13][4].setBackground(Color.blue);
        panelHolder[14][4].setBackground(Color.blue);
        panelHolder[15][4].setBackground(Color.blue);
        
        
        frame.add(panel);
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(900,1200);
        frame. setVisible(true);
    }
}

