#user[]
from Tkinter import *
from PIL import Image, ImageTk
import tkFont
import UserInfo.users



class hellopage(Frame):
	def createWidgets(self):
		self.logo = ImageTk.PhotoImage(Image.open("theLogo.png").resize((736, 550)))
        #Displaying it
		Label(self, image=self.logo).pack()
		
		self.noAccount = Button(self, text = "Continue without Account")
		self.loginButton = Button(self, text = "Login",command=goToLgin)
		self.createButton = Button(self, text = "Create Account", command=createAccount)
		

		self.loginButton.grid(row=2)
		self.noAccount.grid(row=1)
		self.createButton.grid(row=3)

		self.loginButton.pack(fill="x")
		self.noAccount.pack(fill="x")
		self.createButton.pack(fill="x")
	
	def __init__(self, master=None):
		Frame.__init__(self, master)
		self.createWidgets()

		
class LoginPage(Frame):
	def createWidgets(self):
		self.logo = ImageTk.PhotoImage(Image.open("theLogo.png").resize((736, 550)))		
		Label(self, image=self.logo).pack()
		
		self.user = Label(self, text="Username: ")
		self.user.pack()
		self.userIn = Entry(self)
		self.userIn.pack()
		
		self.pwd = Label(self, text="Password: ")
		self.pwd.pack()
		self.pwdIn = Entry(self)
		self.pwdIn.pack()
		
		def submit():
			
			if (UserInfo.users.getID(self.userIn.get(), self.pwdIn.get()) > 0):
				print "Login succeeded"
			else:
				print "Login Failed"
				
		
		self.Login = Button(self,  text = "Submit", command=submit)
		self.Login.pack()
		
		self.BackButton = Button(self,  text = "Back", command=goToHello)
		self.BackButton.pack()
		
		
	
	def __init__(self, master=None):
		Frame.__init__(self, master)
		self.createWidgets()

class RegisterPage(Frame):
	def createWidgets(self):
		self.header = Label(self, text="Create Your Account!",fg="blue",font=tkFont.Font(size=40))
		self.header.pack()
		
		self.user = Label(self, text="Username: ")
		self.user.pack()
		self.userIn = Entry(self)
		self.userIn.pack()
		self.nameMsg = Label(self, text="*")
		self.nameMsg.pack()
		
		self.pwd = Label(self, text="Password: ")
		self.pwd.pack()
		self.pwdIn = Entry(self)
		self.pwdIn.pack()
		self.pwdMsg = Label(self, text="*")
		self.pwdMsg.pack()
		self.pwdIn2 = Entry(self)
		self.pwdIn2.pack()
		self.pwdMsg = Label(self, text="*")
		self.pwdMsg.pack()
		
		self.email = Label(self, text="Email: ")
		self.email.pack()
		self.emailIn = Entry(self)
		self.emailIn.pack()
		self.emailMsg = Label(self, text="")
		self.emailMsg.pack()
		
		self.major = Label(self, text="Major: ")
		self.major.pack()
		self.majorIn = Entry(self)
		self.majorIn.pack()
		self.majorMsg = Label(self, text="")
		self.majorMsg.pack()
		
		def submit():
			print "Not Done Yet!"
		
		self.Register = Button(self,  text = "Submit", command=submit)
		self.Register.pack()
		
		self.BackButton = Button(self,  text = "Back", command=goToHello)
		self.BackButton.pack()
	
	def __init__(self, master=None):
		Frame.__init__(self, master)
		self.createWidgets()
		
def goToLgin():
	hellopage.pack_forget()
	loginpage.pack()
	
def goToHello():
	registerpage.pack_forget()
	loginpage.pack_forget()
	hellopage.pack()

def createAccount():
	hellopage.pack_forget()
	registerpage.pack()

def goToMain(ID=0):
	hellopage.pack_forget()
	loginpage.pack_forget()
	registerpage.pack_forget()
	
	
root = Tk()
root.geometry('{}x{}'.format(810, 1000))
root.title("GOLDER")


hellopage = hellopage(master=root)
loginpage = LoginPage(master=root)
registerpage = RegisterPage(master=root)

hellopage.pack()

root.mainloop()
root.destory()