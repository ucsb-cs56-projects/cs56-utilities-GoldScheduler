


//Course will just store all the data retrieved from the database, so the program doesn't
//have to consistently communicate with the database.
public class Course{
    //All instace variables are public and final, as per Forrest's suggestion
    //Except lecture because I think for functionality we should be able to change
    //the lecture time, professor, etc.
    public final String courseID;
    public final String title;
    public final String fullTitle;
    public final String dept;
    public final double units;
    public final Course[] preReqs;
    public final String[] restrictions;
    public final String[] geFulfill;
    private Lecture lectInfo;
    
    
    public Course(String courseID, String title, String fullTitle, String dept, double units,
                  Course[] preReqs, String[] restrictions, String[] geFulfill, Lecture lectInfo){
        this.courseID = courseID;
        this.title = title;
        this.fullTitle = fullTitle;
        this.dept = dept;
        this.units = units;
        this.preReqs = preReqs;
        this.restrictions = restrictions;
        this.geFulfill = geFulfill;
        this.lectInfo = lectInfo;
    }
    //Methods for only private member variable
    public Lecture getLect(){
        return this.lectInfo;
    }
    public void setLect(Lecture lectInfo){
        this.lectInfo = lectInfo;
    }
}

