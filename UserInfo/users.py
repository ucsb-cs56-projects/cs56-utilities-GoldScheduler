""" This is for User accessing
	databaseCheck() is used to check if database works. 0 if no, 1 if yes. No exception thrown out.
	getID() is used to login. it returns ID if username and password matches; return -1 id no username; return -2 if password is wrong
	getIdByEmail() is used to get User's Id by its email
	getInfo() is used to get all the information for a student and return into a tuple
	
	Register() is used to register. There are several precautions to use it:
	1. username and email are primary, which means we don't want 2 users have the same username or email. Be sure to check it for the register part.
	       Use getID() and getIdByEmail()
	
	2. I haven't think about it
"""

import mysql.connector
from mysql.connector import errorcode

import config


def getID(username, password):
	
	cnx = mysql.connector.connect(**config.config)
	  
	cursor = cnx.cursor()

	cursor.execute("SELECT ID, user_password FROM `users` WHERE user_name='%s';" % username)
	user_info = cursor.fetchall()
	
	cursor.close()
	cnx.close()
	
	
	#No username
	if len(user_info) == 0:
		return -1
	
	#Return ID
	if user_info[0][1] == password:
		return user_info[0][0]
	
	#Password is Wrong
	return -2	

def getIdByEmail(email):
	cnx = mysql.connector.connect(**config.config)
	  
	cursor = cnx.cursor()

	cursor.execute("SELECT ID FROM `users` WHERE email_address='%s';" % email)
	user_info = cursor.fetchall()
	
	cursor.close()
	cnx.close()
	
	
	#No username
	if len(user_info) == 0:
		return -1
	return user_info[0][0]
		

def getInfo(ID):

	 
	cnx = mysql.connector.connect(**config.config)  #connect database

	cursor = cnx.cursor()
	cursor.execute("SELECT * FROM `users` WHERE ID='%s';" % ID)
	user_info = cursor.fetchall()
	
	
	cursor.close()
	cnx.close()
	
	if len(user_info) == 0:
		return -1

	return user_info[0]

def Register(username, password, email=None, major=None):

	cnx = mysql.connector.connect(**config.config)  #connect database

	cursor = cnx.cursor()
	
	if email:
		email = "'" + email + "'"
	else:
		email = "null"
		
	if major:
		major = "'" + major + "'"
	else:
		major = "null"
	
	#try:
	cursor.execute("INSERT INTO users (user_name,user_password,email_address,major) VALUES ('%s','%s',%s,%s);" % (username, password, email, major))
	
	#except mysql.connector.errors.IntegrityError as err2:
	#	print "mysql.connector.errors.IntegrityError: Duplicate entry"
	cnx.commit()
	cursor.close()
	cnx.close()

def databaseCheck():
	try:
	 #Info to access database
	  cnx = mysql.connector.connect(**config.config)  #connect database
	  
	except mysql.connector.Error as err:
	  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
		print("Something is wrong with your user name or password")
	  elif err.errno == errorcode.ER_BAD_DB_ERROR:
		print("Database does not exists")
	  else:
		print(err)
	  return 0
	return 1
	
