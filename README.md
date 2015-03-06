Golder
======
Gaucho On-line Data Effortless Registration
------
![alt tag](https://github.ucsb.edu/jdogg5566/Golder/blob/master/python_old_files/theLogo.png)

An improved version of the class search/scheduler on Gold built with Java:  [Source Directory](https://github.ucsb.edu/jdogg5566/Golder/tree/master/src)

AUTHORS
-------
Hanna Vigil
Forrest Sun
Wesley Pollek
Johnathan Easterman
Chloe Pounds

INTRODUCTION
------------
 This program uses data from the UCSB course catalog that is taken off the ninjacourses.com website. Golder works by scraping data from ninjacourses to insert in our database along with user information, and then displaying the course info on screen for specific users. This program allows students to search for classes and build preview schedules on a blank slate so that they do not need to worry about being swift when their pass time is up. Built with a simplistic, lightweight design, and allowing users to search for classes in a better way than Gold currently offers, our program is designed to make registration less of a pain.

INSTRUCTIONS
------------
To run the system, simply navigate to the repository and type "ant run". We use a build.xml file for testing and running different parts of our program. Your system must have the capabillity to run Java programs from the swing.javax package. A good connection to the internet is also required for accessing the database information quickly. This system uses J2SE 1.8 and Ant 1.9. 

SPECIAL INFORMATION
-------------------
 Our current iteration has gathered a lot of the information from the ninjacourses website, and has a working login/new user page that interacts with our user database to verify if the person trying to log-in has the right username and password, and a new user does not make a username that already exists in our database. Once logged in, it takes you to a main page, where you can then log out, view your schedule, or start a search for classes. The search currently accesses courses in the computer science department, and lets you select a class and view its details, then add to your schedule. We have yet to instantiate an advanced search, allowing for restrictions on which fields are parsed, this along with a user info reset will be some of the features added unto our next iteration.

USE
---
Using this program should be relatively self-explanatory. If you are new to it, you must first set up an account to log-in. Once logged in you can either search for classes or view your schedule. Once you find the course that you are interested in, you can add it to your schedule by clicking the add course button. If you decide that a selected course is not for you, you can remove courses from your schedule page, and change colors of the current classes to allow for easier distinguishing and aesthetic appeal.

KNOWN BUGS
----------
*****write known bugs here******

DOCUMENTATION
-------------
******put link to javadoc here*******