package Schedule;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import Course.Course;
import Course.Lecture;
/**
 * A class to keep track of course conflicts and why the course could not be added
 * Eg. Reason and Course that it conflicted with
 */
public class CourseConflict{
    private Course courseInfo;
    private int error;
    private Course correspondingCourse;
    public CourseConflict(){
        this.error = 0;
    }
    public CourseConflict(Course courseInfo, Course correspondingCourse, int error){
        this.courseInfo = courseInfo;
        this.correspondingCourse = correspondingCourse;
        this.error = error;
    }
    /**
     @param courseInfo the Course that conflicts with the schedule
     Sets the courseInfo to the course that could not be added
     */
    public void setCourse(Course courseInfo){
        this.courseInfo = courseInfo;
    }
    /**
     @param correspondingCourse which Course the conflicting Course conflicts with
     Sets the correspondingCourse to the Course that the root course has an issue with
     */
    public void setCorres(Course correspondingCourse){
        this.correspondingCourse = correspondingCourse;
    }
    /**
     @param error the error number
     Sets the error to the number corresponding to a specific error
     */
    public void setError(int error){
        this.error = error;
    }
    
    /**
     @return The course that could not be added
     */
    public Course getCourse(){
        return this.courseInfo;
    }
    /**
     @return The conflicting course
     */
    public Course getCorres(){
        return this.correspondingCourse;
    }
    /**
     @return The error number
     */
    public int getError(){
        return this.error;
    }
    /**
     @return String stating why the course could not be added
     */
    public String getErrorString(){
        String s = "";
        if(this.error == 1){
            s = "Time conflict with: " + this.correspondingCourse.courseID;
        }
        if(this.error == 2){
            s = s + "Already have: " + this.correspondingCourse.courseID;
        }
        if(this.error == 3){
            s = s + "Problem with the course time.";
        }
        if(this.error == 4){
            s = s + "Problem with day the course is offered.";
        }
        return s;
    }
}