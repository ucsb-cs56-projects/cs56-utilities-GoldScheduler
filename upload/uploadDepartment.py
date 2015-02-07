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

		#print("INSERT INTO depts (department, description) VALUES ('%s','%s');" % (dept, dscp.replace("(","").replace(")","")))
		cursor.execute("INSERT INTO depts (department, full_name) VALUES ('%s','%s');" % (dept.replace("(","").replace(")",""), dscp))

		

cnx.commit()
cursor.close()
cnx.close()
