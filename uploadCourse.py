#!/usr/bin/python

import mysql.connector
from mysql.connector import errorcode
import io

import config;





cnx = mysql.connector.connect(**config.config)

cursor = cnx.cursor()

cursor.execute("SELECT ID, user_password FROM `users` WHERE user_name='%s';" % username)
user_info = cursor.fetchall()

cursor.close()
cnx.close()
