import requests
from lxml import html

# Use this website to get special course information 
# http://my.sa.ucsb.edu/catalog/current/UndergraduateEducation/SpecialSubjectAreaRequirements.aspx

# Opens the page, creates a DOM tree of HTML elements, closes page

def openPage(url_):
	page = requests.get(url_,verify=False)
	tree = html.fromstring(page.text)
	page.close()
	return tree


# Extracts data at given xpath
def getData(tree_,xpath_):
	data = tree_.xpath(xpath_)
	return str(data)

#tree = openPage(url)
#"//*[@id='deptlist-left']/li[18]"
#


# Gets all Dept. names from Ninjacourse catalog
def getDept(url):
	data = []
	tree = openPage(url)
	for i in range (1,53):
		xpath = '//*[@id="deptlist-left"]/li[' + str(i) + ']/a/text()' #xpath taken from course ninja
		dept = getData(tree, xpath)
		dept = dept[2:len(dept)-2]
		data.append(dept)
	for i in range (1,53):
		xpath = '//*[@id="deptlist-right"]/li[' + str(i) + ']/a/text()'
		dept = getData(tree, xpath)
		dept = dept[2:len(dept)-2]
		data.append(dept)
	return data


def getCourses(url): #Returns a list of all courses in a single department
	data = []		 
	tree = openPage(url)
	data.append("trash")
	i = 0
	xpath = '//*[@id="dept-course-list"]/li[' + str(i+1) + ']/a/text()'
	while(getData(tree,xpath) != '[]'): #While there are still courses to get
		i = i + 1
		xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/a/text()'
		str1 = getData(tree,xpath)
		str1 = str1[2:len(str1)-2]
		data.append(str1)
		xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/text()' #xpath taken from course ninja
		str2 = getData(tree, xpath)
		str2 = str2[2:len(str2)-2]
		data[i] = data[i] + str2
	data.pop(0)
	data.pop(-1)
	return data


def getDeptStub(deptList): 
	#Gets the dept short codes e.g. "ANTH"
	#need to figure out a way to bypass ("Creative studies")
	# Meaning, need to look for 2nd occurrence of '(' and ')'
	codes = []
	for i in range (0, 104): 
		index1 = deptList[i].index('(')
		index2 = deptList[i].index(')')
		code = deptList[i][index1+1:index2]
		codes.append(code)
	return codes


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

url = "http://ninjacourses.com/explore/4/"
path = '//*[@id="deptlist-left"]/li[18]/a/text()'

deptList = getDept(url)
codes = getDeptStub(deptList)
anthCourses = getCourses(url+"department/ANTH")

#for item in deptList:
#	print item

#for item in codes:
#	print item

for item in anthCourses:
	print item
