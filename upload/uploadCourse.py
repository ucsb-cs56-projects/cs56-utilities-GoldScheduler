#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

f = open('output/course_catalog.txt', 'r')

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()
for line in f:
	m = re.match('([\w+\s]+)\s(\d(\w+)?)\s\-\s(.+)', line)
	if m:
		dept = m.group(1)
		code = m.group(2)
		dscp = m.group(3)
		f2 = m.group(4)
		print dept, code , dscp, f2
		#cursor.execute("INSERT INTO courses (course_name, course_number, department, full_title) VALUES ('%s','%s','%s','%s');" % ("".join(dept.split())+code, code, dept, dscp.replace("\\","\\\\").replace("'","\\'")))

		

cnx.commit()
cursor.close()
cnx.close()
