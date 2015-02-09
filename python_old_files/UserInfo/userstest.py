import users

print "Get user ID by username and password"
print users.getID("sxh19911230", "P@ssw0rd")
print "Get user info by ID"
print users.getInfo(1)
print "sxh19911230@gmail.com's ID:"
print users.getIdByEmail("sxh19911230@gmail.com")
#print "Register"
#users.Register("hehe9","qwe")