#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io
import re

from UserInfo.config import config

area = ('quantitative','b', 'c', 'd','e','f','g','h','ethnic','euro' ,'world_culture', 'writ')



cnx = mysql.connector.connect(**config)
cursor = cnx.cursor()

for ar in area:
	f = open(ar + "Reqs.txt", 'r')
	cursor.execute("CREATE TABLE %s" % (ar) + "_reqs"  + """
			(
					coursename varchar(40) NOT NULL
			);
			""")
	for line in f:
			
			print ("INSERT INTO `%s" % ar) + ("_reqs` (coursename) VALUES ('%s');" % line[:-1]) 
			cursor.execute(("INSERT INTO `%s" % ar) + ("_reqs` (coursename) VALUES ('%s');" % line[:-1]))

cnx.commit()
cursor.close()
cnx.close()
