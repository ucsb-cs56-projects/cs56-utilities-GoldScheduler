from Scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

ts = Scraper(deptUrlStub+"CMPSC")



courseDict = ts.getCourses()

courseTimes = ts.getCourseTimes(courseDict)

for key in courseTimes:
	print "\n",key, "\n"
	for time in courseTimes[key]:
		print time
