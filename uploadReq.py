#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

area = ('b','d','e','f','g','h','ethnic','euro','writ')



cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()

for ar in area:
	f = open("output/" + ar + "Reqs.txt", 'r')
	for line in f:
			#print (("UPDATE courses SET %s" + "_reqs=%s WHERE course_name='%s';") % (ar, "1", line[:-1]))
			cursor.execute(("UPDATE courses SET %s" + "_reqs=%s WHERE course_name='%s';") % (ar, "1", line[:-1]))

cnx.commit()
cursor.close()
cnx.close()
