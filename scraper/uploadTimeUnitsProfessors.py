#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

def getWeek(w):
	tmp = 0;
	i=0;
	while ( i < len(w)):
		
		d=w[i];

		if d=='M':
			tmp+=64;
		elif d=='T':
			tmp+=32;
		elif d=='W':
			tmp+=16;
		elif d=='R':
			tmp+=8;
		elif d=='F':
			tmp+=4;
		elif d=='S':
			tmp+=2;
		elif d=='U':
			tmp+=1;
		i+=1;
	return tmp;





ftime = open('CS_times.txt', 'r')
fpu = open('CS_professors_and_units.txt', 'r')

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()

resetCourse = True;
resetLecture = True;

nextProInList = False;
ProList = None;
listIt = 0;

corresponding_id = 0;

for line in ftime:

	if re.match("\s+", line):

		resetCourse=True;
		resetLecture=True;
		continue;
		
	line = line[:-1]

	if re.match("\*+", line):

		resetLecture=True;
		continue;
	
	if resetCourse:

		corresponding_id=0;
		course_name=line.replace(" ", "");
		courseName=line;
		resetCourse=False;
		continue;
		
	if resetLecture:

		m = re.match("\[\'(.+)\'\]", line);

		time = m.group(1);
		
		if time != "TBA":
			wocao=re.match('([A-Z]+)(.+)\-(.+)([AP])',time);
			
			week=getWeek(wocao.group(1))
			
			stm=re.match("(.+):(.+)", wocao.group(2));
			if stm:
				st=int(stm.group(1)+stm.group(2));
			else:
				st=int(wocao.group(2))*100;
				
			etm=re.match("(.+):(.+)", wocao.group(3));
			if etm:
				et=int(etm.group(1)+etm.group(2));
			else:
				et=int(wocao.group(3))*100;
			

			if wocao.group(4) == 'P':
				
				if st < et:
					st+=1200;
					et+=1200;
				else:
					et+=1200;
		else:
			week=0;
			st=0;
			et=0;

		
		
		
		if nextProInList:

			listIt+=1;
			instructor_name = ProList[listIt];
			if len(ProList) == listIt+1:
				nextProInList = False;
				ProList=None;
				listIt=0;
		else:
			fpu = open('CS_professors_and_units.txt', 'r')
			for l in fpu:
				m2 = re.match(courseName + "\[(.*)\]\[(.*)\]",l);
				if m2:
					m3 = re.match('.+\*.+',m2.group(1));
					units = m2.group(2);
					if m3:
						ProList = m2.group(1).split('*');
						nextProInList=True;
						
						instructor_name=ProList[0];
					
					else:
						instructor_name=m2.group(1);
					
					break;
		
		if instructor_name == "":
			instructor_name = "TBA";
			
		instructor_name = "'" + instructor_name.replace("'", "") + "'"

		#TODO UPLOAD
		#print course_name, instructor_name, corresponding_id, week, st, et, units;
		#cursor.execute("INSERT INTO `spring_15_lecture` (course_name, instructor_name, week, start_time, end_time) VALUES ('%s',%s,%s,%s,%s);" % (course_name, instructor_name, week,st,et))
		#print ("INSERT INTO spring_15 (corresponding_id, course_name, instructor_name, week, start_time, end_time) VALUES (0,'%s',%s,%s,%s,%s);" % (course_name, instructor_name, week,st,et));
		#cursor.execute("UPDATE `courses` SET units=%s WHERE course_name='%s';" % (units,course_name));
		
		
		#cursor.execute("SELECT * FROM `spring_15_course`")
		#course_info = cursor.fetchall()
		#print course_info
		
		
		#cursor.execute("SELECT id FROM `spring_15_lecture` WHERE course_name='%s'" % course_name)
		#print("SELECT id FROM `spring_15` WHERE course_name='%s;'" % course_name)
		course_info = cursor.fetchall()
		
		corresponding_id = course_info[0][0];
		resetLecture=False
		continue;
		
	instructor_name="'TBA'"
	m = re.match("\[\'(.+)\'\]", line);
	


	
	time = m.group(1);
	
	if time != "TBA":
		wocao=re.match('([A-Z]+)(.+)\-(.+)([AP])',time);

		week=getWeek(wocao.group(1))
		
		stm=re.match("(.+):(.+)", wocao.group(2));
		if stm:
			st=int(stm.group(1)+stm.group(2));
		else:
			st=int(wocao.group(2))*100;
			
		etm=re.match("(.+):(.+)", wocao.group(3));
		if etm:
			et=int(etm.group(1)+etm.group(2));
		else:
			et=int(wocao.group(3))*100;
		

		if wocao.group(4) == 'P':
			
			if st < et:
				st+=1200;
				et+=1200;
			else:
				et+=1200;
	else:
		week=0;
		st=0;
		et=0;
				
	#print course_name, instructor_name, corresponding_id, week, st, et, units;
	#print ("INSERT INTO spring_15 (corresponding_id, course_name, instructor_name, week, start_time, end_time) VALUES ('%s','%s',%s,%s,%s,%s);" % (corresponding_id, course_name, instructor_name, week,st,et))
	cursor.execute("INSERT INTO `spring_15_section` (corresponding_id, course_name, instructor_name, week, start_time, end_time) VALUES ('%s','%s',%s,%s,%s,%s);" % (corresponding_id, course_name, instructor_name, week,st,et))

	
	
	
cnx.commit()
cursor.close()
cnx.close()

"""



	m = re.match('([\w+\s]+)\s(\d\w+)\s\-\s(.+)', line)
	if m:
		dept = m.group(1)
		code = m.group(2)
		dscp = m.group(3)

		cursor.execute("INSERT INTO courses (course_name, course_code, department, description) VALUES ('%s','%s','%s','%s');" % ("".join(dept.split())+code, code, dept, dscp.replace("\\","\\\\").replace("'","\\'")))

		


"""