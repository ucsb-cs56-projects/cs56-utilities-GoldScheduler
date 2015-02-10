import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *This class will display schedule
 * @author Hanna Vigil
 * @version Feb8, 2015
 */

public class ScheduleGUI{
    private Scheduler s;
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
        
        panelHolder[11][2].add(courseEx);
        panelHolder[12][2].add(courseEx2);
        panelHolder[13][2].add(courseEx3);
        panelHolder[11][4].add(courseEx4);
        panelHolder[12][4].add(courseEx5);
        panelHolder[13][4].add(courseEx6);
        panelHolder[11][2].setBackground(Color.blue);
        panelHolder[12][2].setBackground(Color.blue);
        panelHolder[13][2].setBackground(Color.blue);
        panelHolder[11][4].setBackground(Color.blue);
        panelHolder[12][4].setBackground(Color.blue);
        panelHolder[13][4].setBackground(Color.blue);
        
        
        frame.add(panel);
        frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame. setSize(900,1200);
        frame. setVisible(true);
    }
}