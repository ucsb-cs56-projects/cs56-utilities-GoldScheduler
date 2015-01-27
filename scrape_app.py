from scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

deptScraper = Scraper(deptsURL)
courseScraper = Scraper(deptUrlStub+"ANTH")

deptList = deptScraper.getDepts()
deptCodes = deptScraper.getDeptStubs()

anthCourses = courseScraper.getCourses()

for item in deptList:
	print item

for item in deptCodes:
	print item

for item in anthCourses:
	print item