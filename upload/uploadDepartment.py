#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

f = open('output/depts.txt', 'r')

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()
for line in f:
	m = re.match('(.+)(\(.+\))', line)
	if m:
		dept = m.group(2)
		dscp = m.group(1)

		#print("INSERT INTO depts (department, description) VALUES ('%s','%s','%s');" % (dept, dscp.replace("(","").replace(")","")))
		
		dept = dept.replace("(","").replace(")","")
		cursor.execute("INSERT INTO depts (department, department_no_space, description) VALUES ('%s','%s','%s');" % (dept, dept.replace(" ",""), dscp))

		

cnx.commit()
cursor.close()
cnx.close()
