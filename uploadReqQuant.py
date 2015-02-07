#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()


f = open("output/quantitativeReqs.txt", 'r')
for line in f:
	m = re.match("(.+)and(.+)-','\(bothcoursesmustbetakentosatisfyrequirem", line)
	if m:
		course1 = m.group(1)
		course2 = m.group(2)

		#print (("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % (course1, course2))
		#print (("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % (course2, course1))
		cursor.execute(("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % (course1, course2))
		cursor.execute(("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % (course2, course1))
	else:
		#print (("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % ("1", line[:-1]))
		cursor.execute(("UPDATE courses SET quantitative_reqs='%s' WHERE course_name='%s';") % ("1", line[:-1]))

		

cnx.commit()
cursor.close()
cnx.close()
