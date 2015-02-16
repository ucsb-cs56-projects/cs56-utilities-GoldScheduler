from Scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

ts = Scraper(deptUrlStub+"CMPSC")



courseDict = ts.getCourses()

#courseTimes = ts.getCourseTimes(courseDict)

courseUnitsAndProfessor = ts.getCourseUnitsAndProfessors(courseDict)

