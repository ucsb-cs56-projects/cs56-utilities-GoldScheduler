/**
 * @author Hanna Vigil
 * @version 0.0
 * 02/07/2015
 */

//Lecture will include all of the information that we could potentially want to change
//If that's the case, we could just create a new instance of Lecture, so all of these
//are public and final to make things easy
public class Lecture{
    public final String professor;
    //Time can be represented by military time, 4 digits
    public final int timeStart;
    public final int timeEnd;
    public final char[] days;
    public final String location;
    public final String courseCode;
    //TODO make section
    
    
    public Lecture(String professor, int timeStart, int timeEnd, char[] days, String location, String courseCode){
        this.professor = professor;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.days = days;
        this.location = location;
        this.courseCode = courseCode;
    }
    //Returns the times as strings
    public String timeStartString(){
        if(this.timeStart<1200){
            return this.timeStart/100 + ":" + this.timeStart%100 + " AM";
        }
        else{
            return (this.timeStart/100)-12 + ":" + this.timeStart%100 + " PM";
        }
    }
    public String timeEndString(){
        if(this.timeStart<1200){
            return this.timeEnd/100 + ":" + this.timeEnd%100 + " AM";
        }
        else{
            return (this.timeEnd/100)-12 + ":" + this.timeEnd%100 + " PM";
        }
    }
}