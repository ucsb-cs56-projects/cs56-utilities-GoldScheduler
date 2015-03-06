from Scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

ts = Scraper(deptsURL)

deptList = ts.getDeptStubs()


for dept in deptList:
	print dept
	courseList = ts.getCourses(dept)
	courseTimes = ts.getCourseTimes(courseList,dept)
	for course in courseTimes:
		print course
		for time in courseTimes[course]:
			if ("M" in time and "W" in time) or ("T" in time and "R" in time):
				print "*"
			print time
#courseList = ts.getCourses()

#courseTimes = ts.getCourseTimes(courseDict)

#courseUnitsAndProfessor = ts.getCourseUnitsAndProfessors(courseDict)

