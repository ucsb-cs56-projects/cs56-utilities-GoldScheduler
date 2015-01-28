from scraper import Scraper

deptsURL = "http://ninjacourses.com/explore/4/"
deptUrlStub = deptsURL + "department/"

deptScraper = Scraper(deptsURL)
courseScraper = Scraper(deptUrlStub+"ECON")

#deptList = deptScraper.getDepts()
#deptCodes = deptScraper.getDeptStubs()

#anthCourses = courseScraper.getCourses()

#courseDict = deptScraper.getAllCourses(deptCodes)

# for item in deptList:
# 	print item

# for item in deptCodes:
# 	print item

#for item in anthCourses:
 #	print item

# for dept, classes in courseDict.iteritems():
# 	print dept
# 	for course in classes:
# 		print course



#This part grabs data from http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/SpecialSubjectAreaRequirements.aspx

writURL = 'http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/WritingReqCourses.aspx'
euroURL = 'http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/EurTradCourses.aspx'
worldCultURL = 'http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/WorldCulturesCourses.aspx'
quantURL = 'http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/QuantCourses.aspx'
ethnURL = 'http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/EthnicityCourses.aspx'

bURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaB.aspx" 
cURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaC.aspx"
dURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaD.aspx"
eURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaE.aspx"
fURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaF.aspx"
gURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaG.aspx"
hURL = "http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/AreaH.aspx"


writScraper = Scraper(writURL)
euroScraper = Scraper(euroURL)
worldCultScraper = Scraper(worldCultURL)
quantScraper = Scraper(quantURL)
ethnScraper = Scraper(ethnURL)

bScraper = Scraper(bURL)
cScraper = Scraper(cURL)
dScraper = Scraper(dURL)
eScraper = Scraper(eURL)
fScraper = Scraper(fURL)
gScraper = Scraper(gURL)
hScraper = Scraper(hURL)

writReqs = writScraper.getReqCourses()
euroReqs = euroScraper.getReqCourses()
worldCultReqs = worldCultScraper.getReqCourses()
quantReqs = quantScraper.getReqCourses()
ethnReqs = ethnScraper.getReqCourses()

bReqs = bScraper.getReqCourses()
cReqs = cScraper.getReqCourses()
dReqs = dScraper.getReqCourses()
eReqs = eScraper.getReqCourses()
fReqs = fScraper.getReqCourses()
gReqs = gScraper.getReqCourses()
hReqs = hScraper.getReqCourses()
#for item in writReqs:
	#print item

#for item in euroReqs:
	#print item

#for item in worldCultReqs:
	#print item

#for item in quantReqs:
#	print item

#for item in ethnReqs:
#	print item


#for item in bReqs:
#	print item

#for item in cReqs:
#	print item

#for item in dReqs:
#	print item

#for item in eReqs:
#	print item

#for item in fReqs:
#	print item

#for item in gReqs:
#	print item

for item in hReqs:
	print item