#!/usr/bin/python
#An example to show how database works

#Connector/Python: http://dev.mysql.com/downloads/connector/python/
import mysql.connector
from mysql.connector import errorcode

'''
My database, check it if you want, LAN access only(On campus or in UCSB offered house)
http://169.231.51.23/phpmyadmin/
username: root
password: P@ssw0rd
'''

#Info to access database
config = {
  'user': 'cs48',          #username we are going to use
  'password': 'P@ssw0rd',  #password we are going to use
  'host': '169.231.51.23', #my ip now (not sure if it changes when I restart)
  'database': 'testdatabase',  #database table
  'raise_on_warnings': True,  #not sure what this is
}

cnx = mysql.connector.connect(**config)  #connect database
cursor = cnx.cursor()


cursor.execute("SELECT * FROM `writers`") #database syntax

for somthing in cursor:
	print somthing
	
''' output
C:\Users\Forrest\Desktop>python databaseTest.py
(1, u'Jack London')
(2, u'Honore de Balzac')
(3, u'Lion Feuchtwanger')
(4, u'Emile Zola')
(5, u'Truman Capote')
'''

for (Id, Name) in cursor:
	print Id, Name
	
''' output
C:\Users\Forrest\Desktop>python databaseTest.py
1 Jack London
2 Honore de Balzac
3 Lion Feuchtwanger
4 Emile Zola
5 Truman Capote
'''