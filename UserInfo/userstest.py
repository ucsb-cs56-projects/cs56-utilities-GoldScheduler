import users

print "Get user ID by username and password"
print users.getID("sxh19911230", "P@ssw0rd")
print "Get user info by ID"
print users.getInfo(1)
print "Register"
users.Register("hehe8","qwe",None,None)