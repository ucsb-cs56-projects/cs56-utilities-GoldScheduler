#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

<<<<<<< HEAD
f = open('course_catalog.txt', 'r')
=======
f = open('output/course_catalog.txt', 'r')
>>>>>>> 423b85923ebb9db6f4a93e0b49ef4db1aa79f3c7

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()
for line in f:
	m = re.match('([\w+\s]+)\s(\d(\w+)?)\s\-\s(.+)', line)
	if m:
		dept = m.group(1)
		code = m.group(2)
<<<<<<< HEAD
		dscp = m.group(4)
		
		#print dept, code, dscp, f2

		cursor.execute("INSERT INTO courses (course_name, course_number, department, full_title, units) VALUES ('%s','%s','%s','%s', '');" % ("".join(dept.split())+code, code, dept, dscp.replace("\\","\\\\").replace("'","\\'")))
=======
		dscp = m.group(3)

		print ("INSERT INTO courses (course_name, course_code, department, description) VALUES ('%s','%s','%s','%s');" % ("".join(dept.split())+code, code, dept, dscp.replace("\\","\\\\").replace("'","\\'")))
>>>>>>> 423b85923ebb9db6f4a93e0b49ef4db1aa79f3c7

		

cnx.commit()
cursor.close()
cnx.close()
