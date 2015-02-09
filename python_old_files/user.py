#user[]
from Tkinter import *
from PIL import Image, ImageTk

class User:
    #Class to store user information, so it can interact with other objects and user can login and out
    name = 'No Name'
    email = 'No Email'
    password = 'password'
    major = 'No Major'
    #schedule
    
    def __init__(self, n, m, e, p):
        self.name = n
        self.email = e
        self.password = p
        self.major = m


    def getName(self):
        return self.name
    def getEmail(self):
        return self.email
    def getPassword(self):
        return self.password
    def getMajor(self):
        return self.major


def createAccount():
    #root.destroy() #Kill first window
    #newWin = Tk() #open new one
    #newWin.geometry('{}x{}'.format(810, 1000))
    newWin = root
    createButton.destroy()
    noAccount.destroy()
    
    newWin.title("GOLDER - Create Account")
    newName = ''
    newMajor = ''
    newEmail = ''
    newPassword = ''      
    
    nameLabel = Label(bottomFrame, text="Name: ")
    nameLabel.grid(row = 1)
    majorLabel = Label(bottomFrame, text="Major: ")
    majorLabel.grid(row = 1, column=2)
    emailLabel = Label(bottomFrame, text="Email: ")
    emailLabel.grid(row = 2)
    passwordLabel = Label(bottomFrame, text="Password: ")
    passwordLabel.grid(row = 2, column=2)

    n = Entry(bottomFrame)
    n.grid(row = 1, column = 1)

    m = Entry(bottomFrame)
    m.grid(row = 1, column = 3)

    e = Entry(bottomFrame)
    e.grid(row = 2, column =1 )
    
    p = Entry(bottomFrame)
    p.grid(row = 2, column = 3)
    
    def makeIt():
        newName = n.get()
        newMajor = m.get()
        newEmail = e.get()
        newPassword = p.get()
        newUser = User(newName, newMajor, newEmail, newPassword)
        #Register(newName, newPassword, newEmail, newMajor)
    
    enterButton = Button(bottomFrame, text = "Enter", command = makeIt)
    enterButton.grid(row = 3, column = 2)
    newWin.mainloop()
    


root = Tk()
root.geometry('{}x{}'.format(810, 1000))
root.title("GOLDER - Welcome Window")
topFrame = Frame()
topFrame.pack()
bottomFrame = Frame()
bottomFrame.pack()

img = Image.open("theLogo.png")
img = img.resize((736, 550))
logo = ImageTk.PhotoImage(img)

#Displaying it
imgLabel = Label(topFrame, image=logo).pack()     


createButton = Button(bottomFrame, text = "Create Account", command=createAccount)
noAccount = Button(bottomFrame, text = "Continue without Account")

noAccount.grid(row=2)
createButton.grid(row=1)

root.mainloop()
