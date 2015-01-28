from scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

deptScraper = Scraper(deptsURL)
courseScraper = Scraper(deptUrlStub+"ECON")

deptList = deptScraper.getDepts()
deptCodes = deptScraper.getDeptStubs()

anthCourses = courseScraper.getCourses()

courseDict = deptScraper.getAllCourses(deptCodes)

# for item in deptList:
# 	print item

# for item in deptCodes:
# 	print item

#for item in anthCourses:
 #	print item

for dept, classes in courseDict.iteritems():
	print dept
	for course in classes:
		print course