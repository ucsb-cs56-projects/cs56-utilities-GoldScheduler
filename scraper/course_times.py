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
		i = 0
		print "\n" + course + " " + courseTimes[course][2][0]
		for time in courseTimes[course][0]:
			if ("M" in time and "W" in time) or ("T" in time and "R" in time) or (len(courseTimes[course]) == 1):
				print courseTimes[course][1][i] + " " + time
				i = i + 1
			else:
				print time
#courseList = ts.getCourses()

#courseTimes = ts.getCourseTimes(courseDict)

#courseUnitsAndProfessor = ts.getCourseUnitsAndProfessors(courseDict)

