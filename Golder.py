#user[]
from Tkinter import *
from PIL import Image, ImageTk
import tkFont
import UserInfo.users
import re


helloPage=None
loginPage=None
mainPage=None
registerPage=None

class HelloPage(Frame):
	def createWidgets(self):
		self.logo = ImageTk.PhotoImage(Image.open("theLogo.png").resize((736, 550)))
		Label(self, image=self.logo).pack()
		
		self.noAccount = Button(self, text = "Continue without Account",command=lambda:goToMain(self))
		self.loginButton = Button(self, text = "Login",command=lambda:goToLgin(self))
		self.createButton = Button(self, text = "Create Account", command=lambda:createAccount(self))
		

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
		self.pwdIn = Entry(self, show='*')
		self.pwdIn.pack()
		
		self.note = Label(self,fg="red", text="")
		self.note.pack()
		
		def submit(t=0):
			miao = UserInfo.users.getID(self.userIn.get(), self.pwdIn.get())
			if ( miao > 0):
				goToMain(self, miao)
			elif (miao == -1):
				#No username
				self.note['text'] = "user doesn't exist"
			else:
				#Password Wrong
				self.note['text'] = "Wrong password"
				
		self.pwdIn.bind('<Key-Return>', submit)
		self.userIn.bind('<Key-Return>', submit)
		
		self.Login = Button(self,  text = "Submit", command=submit)
		self.Login.pack()
		
		self.BackButton = Button(self,  text = "Back", command=lambda:goToHello(self))
		self.BackButton.pack()

	def clean(self):
		self.userIn.delete(0,END)
		self.pwdIn.delete(0,END)
	
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
		self.nameMsg = Label(self, text="*",fg="red")
		self.nameMsg.pack()
		
		self.pwd = Label(self, text="Password: ")
		self.pwd.pack()
		self.pwdIn = Entry(self,show='*')
		self.pwdIn.pack()
		self.pwdMsg = Label(self, text="*",fg="red")
		self.pwdMsg.pack()
		
		self.pwdIn2 = Entry(self,show='*')
		self.pwdIn2.pack()
		self.pwdMsg2 = Label(self, text="*",fg="red")
		self.pwdMsg2.pack()
		
		self.email = Label(self, text="Email: ")
		self.email.pack()
		self.emailIn = Entry(self)
		
		self.emailIn.pack()
		self.emailMsg = Label(self, text="",fg="red")
		self.emailMsg.pack()
		
		self.major = Label(self, text="Major: ")
		self.major.pack()
		self.majorIn = Entry(self)
		
		self.majorIn.pack()
		self.majorMsg = Label(self, text="")
		self.majorMsg.pack()
		
		def submit(t=0):
			u = self.userIn.get()
			p = self.pwdIn.get()
			p2 = self.pwdIn2.get()
			e = self.emailIn.get()
			m = self.majorIn.get()
			
			rgst = True;
			if (u == ""):
				rgst = False
				self.nameMsg["text"] = "* Required"
			elif (not re.match('\A\w+\Z', u)):
				self.nameMsg["text"] = "* Only digits, letters and underscore _ are allowed"
			elif (UserInfo.users.getID(u,"") == -2):
				rgst = False
				self.nameMsg["text"] = "* Username is already in use"	
			else:
				self.nameMsg["text"] = "*"
			
			
			if (p == ""):
				rgst = False
				self.pwdMsg["text"] = "* Required"
			else:
				self.pwdMsg["text"] = "*"

				
			if (p2 != p):
				rgst = False
				self.pwdMsg2["text"] = "* Password does not match"
			else:
				self.pwdMsg2["text"] = "*"
			
			
			if (e != "" and not re.match('\A\w+@\w+\.\w+\Z', e)):
				rgst = False
				self.emailMsg["text"] = "Email address is not legal"
			else:
				self.emailMsg["text"] = ""

			if (rgst):
				goToDoneRegistration(self, UserInfo.users.Register(u,p,e,m))
			
		self.userIn.bind('<Key-Return>', submit)
		self.pwdIn.bind('<Key-Return>', submit)
		self.pwdIn2.bind('<Key-Return>', submit)
		self.emailIn.bind('<Key-Return>', submit)
		self.majorIn.bind('<Key-Return>', submit)

		self.Register = Button(self,  text = "Submit", command=submit)
		self.Register.pack()
		
		self.BackButton = Button(self,  text = "Back", command=lambda:goToHello(self))
		self.BackButton.pack()
	
	def clean(self):
		self.userIn.delete(0,END)
		self.pwdIn.delete(0,END)
		self.pwdIn2.delete(0,END)
		self.emailIn.delete(0,END)
		self.majorIn.delete(0,END)
	
	def __init__(self, master=None):
		Frame.__init__(self, master)
		self.createWidgets()

class RegisterDonePage(Frame):
	
	def createWidgets(self):
		self.header = Label(self, text="Congratulations!\nYou have registered your account!\nPress Continue to go on!",fg="blue",font=tkFont.Font(size=30))
		self.header.pack()
		self.continueButton = Button(self,  text = "Continue", command=lambda:goToMain(self, self.UID))
		self.continueButton.pack()
	
	def __init__(self, UID, master=None):
		Frame.__init__(self, master)
		self.UID = UID
		self.createWidgets()
		
	
class MainPage(Frame):
	def createWidgets(self):
		self.note = Label(self,fg="red", text="Under construction")
		self.note.grid()
		
		self.BackButton = Button(self,  text = "Back", command=lambda:goToHello(self))
		self.BackButton.grid()

	def __init__(self, master=None):
		Frame.__init__(self, master)
		self.createWidgets()


def goToLgin(t):
	t.pack_forget()
	t.grid_forget()
	loginPage.clean()
	loginPage.pack()

def goToHello(t):
	t.pack_forget()
	t.grid_forget()
	helloPage.pack()

def createAccount(t):
	t.pack_forget()
	t.grid_forget()
	registerPage.clean()
	registerPage.pack()

def goToMain(t, ID=0):
	t.pack_forget()
	t.grid_forget()
	MainPage(master=root).grid()

def goToDoneRegistration(t, ID):
	t.pack_forget()
	t.grid_forget()
	RegisterDonePage(master=root,UID=ID).pack()
	

root = Tk()
root.geometry('800x700')
root.title("HELLO GOLDER")

loginPage=LoginPage(master=root)
registerPage=RegisterPage(master=root)

helloPage=HelloPage(master=root)
helloPage.pack()

root.mainloop()
