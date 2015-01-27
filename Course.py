#Forrest:   Actually we do not need that much function to return values..
#           Just declare every variable final(constant) and public
#           becuase we do not need change it after it is declared.

class Course:
    courseCode = ""
    courseID=0
    title=""
    dept=""
    professor=""
    units=0
    location=""
    time=""
    days=""
    preReqs={}
    restrictions=""
    geFulfil={}
    def Display():
    def getcourseCode():
        return self.courseCode
    def gettitle():
        return self.title
    def getdept():
        return self.dept
    def getprofessor():
        return self.professor
    def getunits():
        return self.units
    def getcourseID():
        return self.courseID
    def getlocation():
        return self.location
    def gettime():
        return self.time
    def getdays():
        return self.days
    def getpreReqs():
        return self.preReqs
    def getrestrictions():
        return self.restrictions
    def getgeFulfil():
        return self.geFulfil
    def __init__(self, **kwargs):
            self.courseCode=courseCode
            self.courseID=courseID
            self.days=days
            self.dept=dept
            self.geFulfil=geFulfil
            self.location=location
            self.preReqs=preReqs
            self.professor=professor
            self.restrictions=restrictions
            self.time=time
            self.title=title
            self.units=units
