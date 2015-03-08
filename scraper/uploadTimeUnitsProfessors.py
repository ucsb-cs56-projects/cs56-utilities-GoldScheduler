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





f = open('course_profs_times_units.txt', 'r')


cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()

resetCourse = True;
resetLecture = True;
goThrough = False;

corresponding_id = 0;

for line in f:

	if re.match("\A\s+\Z", line):

		resetCourse=True;
		resetLecture=True;
		goThrough=False;
		continue;
		
	if goThrough:
		continue;
		
	line = line[:-1]
	
	if resetCourse:
		m = re.match("(.+)\s\[(.*)\]", line);
		
		if not m:
			continue;

		course_name=m.group(1).replace(" ", "");
		units=m.group(2);
		resetCourse=False;
		continue;
		
	m = re.match("\[(.*)\]\s\['(.*?)'(,'.*')?\]", line)
	
	if m:
		resetLecture=False;

		time = m.group(2);
		
		if time != "RTBA":
			wocao=re.match('([A-Z]+)([0-9:]+)\-(.+)([AP])',time);
			print time;
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
			

			if (wocao.group(4) == 'P' and et < 1200):
				
				if st < et:
					st+=1200;
					et+=1200;
				else:
					et+=1200;
		else:
			week=0;
			st=0;
			et=0;

		
		instructor_name=m.group(1);
		
		if instructor_name == "":
			instructor_name = "TBA";
			
		instructor_name = "'" + instructor_name.replace("'", "") + "'"

		#TODO UPLOAD
		#print "INSERT INTO `spring_15_lecture` (course_name, instructor_name, week, start_time, end_time) VALUES ('%s',%s,%s,%s,%s);" % (course_name, instructor_name, week,st,et)
		#cursor.execute("INSERT INTO `spring_15_lecture` (course_name, instructor_name, week, start_time, end_time) VALUES ('%s',%s,%s,%s,%s);" % (course_name, instructor_name, week,st,et))
		#print ("INSERT INTO spring_15 (corresponding_id, course_name, instructor_name, week, start_time, end_time) VALUES (0,'%s',%s,%s,%s,%s);" % (course_name, instructor_name, week,st,et));
		cursor.execute("UPDATE `courses` SET units=%s WHERE course_name='%s';" % (units,course_name));
		
		
		#cursor.execute("SELECT * FROM `spring_15_course`")
		#course_info = cursor.fetchall()
		#print course_info
		
		
<<<<<<< HEAD
<<<<<<< HEAD
		#cursor.execute("SELECT id FROM `spring_15_lecture` WHERE course_name='%s' AND week=%s AND start_time=%s AND end_time=%s" % (course_name, week,st,et))
=======
		cursor.execute("SELECT id FROM `spring_15_lecture` WHERE course_name='%s' AND instructor_name='%s'" % (course_name,instructor))
>>>>>>> 423b85923ebb9db6f4a93e0b49ef4db1aa79f3c7
=======
		cursor.execute("SELECT id FROM `spring_15_lecture` WHERE course_name='%s' AND instructor_name='%s'" % (course_name,instructor))
>>>>>>> 423b85923ebb9db6f4a93e0b49ef4db1aa79f3c7
		#print("SELECT id FROM `spring_15` WHERE course_name='%s;'" % course_name)
		#course_info = cursor.fetchall()
		
		#corresponding_id = course_info[0][0];
		#resetLecture=False
		continue;
		
	if resetLecture:
		goThrough=True;
		continue;
		
	
	m = re.match("\[\'(.+)\'\]", line);
	
	
	if not m:
		continue;
		
	time = m.group(1);
	
	if time != "TBA":
	
		wocao=re.match("([A-Z]+)(.+)\-.*[AP]','.+\-(.+)([AP])",time);
		if not wocao:
			wocao=re.match('([A-Z]+)(.+)\-(.+)([AP])',time);
			

		week=getWeek(wocao.group(1))
		
		
		stm=re.match("(.+):(.+)", wocao.group(2));
		if stm:
			print wocao.group(2);
			st=int(stm.group(1)+stm.group(2));
		else:
			st=int(wocao.group(2))*100;
			
		etm=re.match("(.+):(.+)", wocao.group(3));
		if etm:
			et=int(etm.group(1)+etm.group(2));
		else:
			et=int(wocao.group(3))*100;
		

		if (wocao.group(4) == 'P' and et < 1200):
			
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
	#cursor.execute("INSERT INTO `spring_15_section` (corresponding_id, week, start_time, end_time) VALUES (%s,%s,%s,%s);" % (corresponding_id, week,st,et))

	
	
	
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
