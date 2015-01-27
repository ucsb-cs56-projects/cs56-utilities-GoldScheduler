import requests
from lxml import html


class Scraper(object):

	def __init__(self, url):
	# Instance of Scraper object has its own url and its own DOM tree
	# This means you must use a new Scraper instance for each web page
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

#"//*[@id='deptlist-left']/li[18]"
#


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


	def getCourses(self): #Returns a list of all courses in a single department
		courseList = []		 
		courseList.append("trash")
		i = 0
		xpath = '//*[@id="dept-course-list"]/li[' + str(i+1) + ']/a/text()'
		while(self.getData(xpath) != '[]'): #While there are still courses to get
			
			i = i + 1
			xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/a/text()'
			str1 = self.getData(xpath)
			str1 = str1[2:len(str1)-2]
			courseList.append(str1)

			xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/text()' #xpath taken from course ninja
			str2 = self.getData(xpath)
			str2 = str2[2:len(str2)-2]
			courseList[i] = courseList[i] + str2

		courseList.pop(0)
		courseList.pop(-1)
		return courseList


	def getDeptStubs(self): 
		#Gets the dept short codes e.g. "ANTH"
		#need to figure out a way to bypass ("Creative studies")
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


	"""def getAllCourses(deptCodes):
		# This function gets all the courses on ninja courses for UCSB
		# It uses the shortcodes of the departments to access each URL
		# There are two issues:
		# 1) Departments with spaces need to have '%20' in place of the space
		# 2) getDeptStub() needs to be updated to get the departments from creative studies
		data = {}
		url = 'https://ninjacourses.com/explore/4/department/'
		
		#This for-loop should loop through all departments and get courses for each one
		for i in range (0, 53):
			url = url + deptCodes[i] + '/'
			data{deptCodes[i] : getCourses(url)}
			url = 'https://ninjacourses.com/explore/4/department/'
	"""


path = '//*[@id="deptlist-left"]/li[18]/a/text()'


