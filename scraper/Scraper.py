import requests
from lxml import html

# Use this website to get special course information 
# http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/SpecialSubjectAreaRequirements.aspx

class Scraper(object):

	def __init__(self, url):
	# Instance of Scraper object has its own url and its own DOM tree
	# This means you must use a new Scraper instance for each web page
	# or you must use the resetURL() method to change the web page you are scraping
		self.url = url
		self.tree = self.openPage()

	#resets the URL and tree atribute of the scraper to value of 'url' parameter
	
	def resetURL(self, url):
		self.url = url
		self.tree = self.openPage()

	# Opens the page, creates a DOM tree of HTML elements, closes page

	def openPage(self):
		page = requests.get(self.url,verify=False)
		tree = html.fromstring(page.text)
		page.close()
		return tree


	# Extracts data at given xpath
	
	def getData(self,xpath_):
		data = self.tree.xpath(xpath_)
		return str(data)


	# Gets all Dept. names from Ninjacourse catalog
	
	def getDepts(self):
		deptList = []
		for i in range (1,53):
			xpath = '//*[@id="deptlist-left"]/li[' + str(i) + ']/a/text()' 
			#xpath taken from course ninja

			dept = self.getData(xpath)
			dept = dept[2:len(dept)-2]
			deptList.append(dept)

		for i in range (1,53):
			xpath = '//*[@id="deptlist-right"]/li[' + str(i) + ']/a/text()'
			dept = self.getData(xpath)
			dept = dept[2:len(dept)-2]
			deptList.append(dept)
		return deptList

	# Returns a list of all courses in a single department 
	# Courses that aren't offered are not in the returned list
	def getCourses(self): 
		courseList = []		 
		courseList.append("trash")
		i = 0
		j = 0
		xpath = '//*[@id="dept-course-list"]/li[' + str(i+1) + ']/a/text()'
		while(self.getData(xpath) != '[]'): #While there are still courses to get
			i = i + 1 # i corresponds to a given course on the list on ninjacourses.com
			j = j + 1 # j corresponds to the index of courseList

			if (self.getData('//*[@id="dept-course-list"]/li[' + str(i) + ']/span/text()') != '[]'):
				# if there is a span element, then the course is not being offered in Spring
				j = j - 1 # update j to match current index of courseList
				continue  # Go to the next iteration because we don't want courses not offered

			xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/a/text()'
			str1 = self.getData(xpath)
			str1 = str1[2:len(str1)-2] # string clean up
			courseList.append(str1)

			# This part addes the course title to the course, e.g. "Advanced Applications Programming"
			# xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/text()' #xpath taken from course ninja
			# str2 = self.getData(xpath)
			# str2 = str2[2:len(str2)-2]
			# courseList[j] = courseList[j] + str2

		courseList.pop(0)
		#courseList.pop(-1)
		return courseList

	# Returns the list of courses that fulfill a particular requirement
	# can only be used on the official UCSB course catalog website

	def getReqCourses(self):
		courseList = []		 
		courseList.append("trash")
		i = 0
		xpath = '//*[@id="content"]/div/p[' + str(i+1) + ']/text()'
		while(self.getData(xpath) != '[]'): #While there are still courses to get
			#get the data according to the xpath
			i = i + 1
			xpath = '//*[@id="content"]/div/p[' + str(i) + ']/text()'
			str1 = self.getData(xpath)

			#Clean string up
			str1 = str1.replace(" ","")
			str1 = str1[6:]
			str1 = str1[:-10]

			#append course string to list
			courseList.append(str1)
		courseList.pop(0)
		courseList.pop(-1)
		return courseList	


	def getDeptStubs(self): 
		# Gets the dept short codes e.g. "ANTH"
		# need to figure out a way to bypass ("Creative studies")
		# Meaning, need to look for 2nd occurrence of '(' and ')'
		deptList = self.getDepts()
		deptCodes = []
		for i in range (0, 104): 
			index1 = deptList[i].index('(')
			index2 = deptList[i].index(')')
			#if index2 != (len(deptList[i]) - 1): This is for handling Creative studies departments
				
			code = deptList[i][index1+1:index2]
			deptCodes.append(code)
		return deptCodes


	def getAllCourses(self,deptCodes):
		# This function gets all the courses on ninja courses for UCSB
		# It uses the shortcodes of the departments to access each URL
		# There is one issue:
		# getDeptStub() needs to be updated to get the departments from creative studies

		courseDict = dict()
		tmpURL = self.url
		
		#This for-loop should loop through all departments and get courses for each one
		for dept in deptCodes:
			# reset url for each new department
			self.url = tmpURL + 'department/' + dept + '/'
			#reset DOM tree for each new department
			self.tree = self.openPage()

			#add the list of courses as the value for each dept-shortcode key
			courseDict[dept] = self.getCourses()
		# Reset url and tree to original values	
		self.url = tmpURL
		self.tree = self.openPage()
		return courseDict

	def getCourseTimes(self, courseList): # Takes a list of courses in a single department
		dept = "CMPSC"
		courseTimes = dict() # Dictionary to hold course times, with the course as the key
		for course in courseList: 
			newURL = "https://ninjacourses.com/explore/4/course/" + dept + "/" + (course[len(dept)+1:]) + "/#sections"
			self.resetURL(newURL)  
		
			# for each course, enter a for-loop that runs 20 times
			for i in range(2,23): # Will loop 20 times to get all lecture and section times
				if i == 2:
					courseTimes[course] = [] # Initialize the list that corresponds to each course

				time = self.getData('//*[@id="tab-sections"]/table/tr[' + str(i) + ']/td[2]/text()') 
				# gets the time at the current table cell
				
				if len(time) > 2: # Basically, "if the table cell is not empty"
					time = time.replace(" ","") # Clean up the string and append to the courselist
					time = time.replace("\\", "")
					time = time.replace("n","")
					courseTimes[course].append(time)
		return courseTimes


