package Course;
import java.awt.Color;

/**
* Lecture will include all of the information that we could potentially want to change
*If that's the case, we could just create a new instance of Lecture, so all of these
*are public and final to make things easy
 */
public class Lecture{
    public final String professor;
    //Time can be represented by military time, 4 digits
    public final int timeStart;
    public final int timeEnd;
    public final char[] days;
    public final String location;
    public final String courseCode;
    public Color col;
    public final int id;
    
    
    public Lecture(int id, String professor, int timeStart, int timeEnd, char[] days, String location, String courseCode, Color col){
    	this.id = id;
        this.professor = professor;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.days = days;
        this.location = location;
        this.courseCode = courseCode;
        this.col = col;
    }
    /**
     @return The time start as a string
     */
    public String timeStartString(){
        String min = "";
        if(this.timeStart%100==0)
            min = "00";
        else
            min += this.timeStart%100;
        if(this.timeStart<1200){
            return this.timeStart/100 + ":" + min + " AM";
        }
        else if(this.timeStart==1200||this.timeStart==1230||this.timeStart==1250){
            return this.timeStart/100 + ":" + min + " PM";
        }
        else{
            return (this.timeStart/100)-12 + ":" + min + " PM";
        }
    }
    /**
     @return The time end as a string
     */
    public String timeEndString(){
        String min = "";
        if(this.timeEnd%100==0)
            min = "00";
        else
            min += this.timeEnd%100;
        if(this.timeEnd<1200){
            return this.timeEnd/100 + ":" + min + " AM";
        }
        else if(this.timeEnd==1200||this.timeEnd==1230){
            return this.timeEnd/100 + ":" + min + " PM";
        }
        else{
            return (this.timeEnd/100)-12 + ":" + min + " PM";
        }
    }
    
    
    public String timeString(){
        return this.timeStartString() + " - " + this.timeEndString();
    }
    
    /**
     @return The day list as a string
     */
    public String dayString(){
        String s = "";
        for(int i=0; i<days.length; i++){
            switch(days[i]){
                case 'M':
                    s+="Monday";
                    break;
                case 'T':
                    s+="Tuesday";
                    break;
                case 'W':
                    s+="Wednesday";
                    break;
                case 'R':
                    s+="Thursday";
                    break;
                default:
                    s+="Friday";
                    break;
            }
            if(i!=days.length-1){
                s+=", ";
            }
        }
        return s;
    }
    
    public String dayStringShort(){
        String s = "";
        for(int i=0; i<days.length; i++){
            switch(days[i]){
                case 'M':
                    s+="M";
                    break;
                case 'T':
                    s+="T";
                    break;
                case 'W':
                    s+="W";
                    break;
                case 'R':
                    s+="R";
                    break;
                default:
                    s+="F";
                    break;
            }
            if(i!=days.length-1){
                s+=", ";
            }
        }
        return s;
    }

}