#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()


f = open("output/worldCultureReqs.txt", 'r')
for line in f:
			#print (("UPDATE courses SET %s" + "_reqs=%s WHERE course_name='%s';") % (ar, "1", line[:-1]))
			cursor.execute(("UPDATE courses SET world_culture_reqs=%s WHERE course_name='%s';") % ("1", line[:-1]))

cnx.commit()
cursor.close()
cnx.close()
