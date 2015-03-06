package Course;
import java.awt.Color;
/**
* Lecture will include all of the information that we could want to change
* If that's the case, we could just create a new instance of Lecture, so all of these
* are public and final to make things easy
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
    /**
     *@param id Lecture Integer id corresponding to Golder's database
     *@param professor String of the name of the teacher of this lecture
     *@param timeStart Integer representation of the starting time
     *@param timeEnd Integer representation of the ending time
     *@param days char array of the days of the week this lecture occurs
     *@param location String that tells the building and room # of the lecture
     *@param courseCode String representation of the courseCode
     *@param col Color that the lecture will be viewed as in a user's schedule
    */
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
        else if(this.timeStart>=1200 && this.timeStart<=1259){
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
        else if(this.timeEnd>=1200 && this.timeEnd<=1259){
            return this.timeEnd/100 + ":" + min + " PM";
        }
        else{
            return (this.timeEnd/100)-12 + ":" + min + " PM";
        }
    }
    /**
     @return A string that represents the time in an easy-to-read manner
     */
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
		/**
         *@return The day character as a string
         */
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