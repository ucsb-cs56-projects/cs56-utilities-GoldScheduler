import tkinter as tk

class SearchGUI(tk.Tk):
    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        self.om_variable = tk.StringVar(self)

        #Creating the frame and grid
        frame = tk.Frame(self)
        frame.grid()
        
        '''firstVariable = tk.StringVar()
        optionList = ('Department', 'Professor')
        firstMenu = tk.OptionMenu(self, firstVariable, *optionList)
        '''
        self.om = tk.OptionMenu(self, self.om_variable, ())
        
        
        #setting grid size and giving it a title
        self.geometry("500x500")
        self.title("GOLDER")
    
        #Creating the welcome label 
        welcome = tk.Label(self, text="Welcome to GOLDER!",fg="blue")
        welcome.grid(row=0, column=1)


        #Creating an option menu
        self.useDept()
        self.makeButtons()
        self.makeSearchBar()
        self.makeOptionMenu()

    def makeButtons(self):
        dept = tk.Button(self, text="Department", width=10, command=self.useDept)
        prof = tk.Button(self, text="Professor", width=10, command=self.useProf)
        dept.grid(row=1, column=0)
        prof.grid(row=2, column=0)
        
    def makeOptionMenu(self):
        '''variable = tk.StringVar(self)
        variable.set("Department")
        firstOptions = tk.OptionMenu(self, variable, *self.optionList, command=self.useDept)
        while option in optionList is "Department":
            firstOptions = tk.OptionMenu(self, variable, *self.optionList, command=self.useDept)
        while option in optionList is "Professor":
            firstOptions = tk.OptionMenu(self,variable,*self.optionList,command=self.useProf)
        firstOptions.grid(row=1, column=0)
        '''
        
        self.om.configure(width=20)
        self.om.grid(row=1, column=2)
        
    def makeSearchBar(self):
        searchHere = tk.Label(self, text="Search Here")
        searchHere.grid(row=4, column=0)

        searchVar = tk.StringVar()
        
        #Creating search bar
        searchBar = tk.Entry(self,textvariable=searchVar)
        searchBar.grid(row=4,column=1)

        searchVar.set("Select")
        searchValue = searchVar.get()
        
        
        #searchBar.insert(0, "Search here")
        #searchBar.delete(0, "end")
        
        
        

    def _reset_option_menu(self, options, index=None):
        '''reset the values in the option menu

        if index is given, set the value of the menu to
        the option at the given index
        '''
        menu = self.om["menu"]
        menu.delete(0, "end")
        for string in options:
            menu.add_command(label=string,
                             command=lambda value=string:
                                  self.om_variable.set(value))
        if index is not None:
            self.om_variable.set(options[index])

    def useDept(self):
        '''switch option menu to display department info'''
        self._reset_option_menu(["Anthropology","Biology","Computer Science","Feminist Studies"], 0)
                
    def useProf(self):
        '''Switch the option menu to display professor info'''
        self._reset_option_menu(["Conrad", "Costanzo", "Turk"], 0)

if __name__ == "__main__":

    '''img = Image.open("theLogo.png")
    img = img.resize((736, 550))
    logo = ImageTk.PhotoImage(img)

    #Displaying it
    imgLabel = Label(topFrame, image=logo).grid()
    '''
    app = SearchGUI()
    app.mainloop()

