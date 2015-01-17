import requests
from lxml import html


# Opens the page, creates a DOM tree of HTML elements, closes page
def openPage(url_):
	page = requests.get(url_,verify=False)
	tree = html.fromstring(page.text)
	page.close()
	return tree

# Extracts data at given xpath
def getData(tree_,xpath_):
	data = tree_.xpath(xpath_)
	return data

#tree = openPage(url)
#"//*[@id='deptlist-left']/li[18]"
#

# Gets all Dept. names from Ninjacourse catalog
def getDept(url):
	data = []
	tree = openPage(url)
	for i in range (1,53):
		xpath = '//*[@id="deptlist-left"]/li[' + str(i) + ']/a/text()' #xpath taken from course ninja
		data.append(getData(tree, xpath))
	for i in range (1,53):
		xpath = '//*[@id="deptlist-right"]/li[' + str(i) + ']/a/text()'
		data.append(getData(tree, xpath))
	return data


def getCourses(url):
	data = []
	tree = openPage(url)
	data.append("trash")
	for i in range (1,217):
		xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/a/text()'
		data.append(getData(tree, xpath))
		xpath = '//*[@id="dept-course-list"]/li[' + str(i) + ']/text()' #xpath taken from course ninja
		data[i] = data[i] + getData(tree, xpath)
	data.pop(0)
	return data	


url = "http://ninjacourses.com/explore/4/"
path = '//*[@id="deptlist-left"]/li[18]/a/text()'

deptList = getDept(url)

url = 'https://ninjacourses.com/explore/4/department/ANTH/'

anthList = getCourses(url)

for dept in deptList:
	print dept

for course in anthList:
	print course

