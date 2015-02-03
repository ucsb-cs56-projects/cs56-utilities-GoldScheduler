import Tkinter as tk

class SimpleSearchGUI(tk.Tk):
    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        self.om_variable = tk.StringVar(self)

        #Creating the frame and grid
        #frame = tk.Frame(self)
        #frame.grid()

        self.om = tk.OptionMenu(self, self.om_variable, ())

        #setting grid size and giving it a title

        #Creating the welcome label
        #welcome = tk.Label(self, text="Welcome to GOLDER!",fg="blue")
        #welcome.grid(row=0, column=1)

        #Creating an option menu
        self.useDept()
        self.makeButtons()
        self.makeSearchBar()
        self.makeOptionMenu()

    def makeButtons(self):
        var = tk.IntVar()
        dept = tk.Radiobutton(self, text="Department", variable=var, value=1,
                              command=self.useDept)
        prof = tk.Radiobutton(self, text="Professor", variable=var, value=2,
                              command=self.useProf)
        dept.grid(row=1, column=0)
        prof.grid(row=1, column=1)

    def makeOptionMenu(self):
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
        self._reset_option_menu(["Anthropology","Biology","Computer Science",
                                 "Feminist Studies"], 0)

    def useProf(self):
        '''Switch the option menu to display professor info'''
        self._reset_option_menu(["Conrad", "Costanzo", "Turk"], 0)

class AdvancedSearchGUI(tk.Tk):
    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        frame = tk.Frame(self)
        frame.grid()

        self.geometry("500x500")
        self.title("GOLDER")

        self.makeGenEdSearch()

    def makeGenEdSearch(self):
        searchByGenEd = tk.Label(self, text="Search by General Ed")
        searchByGenEd.grid(row=0, column=1)

        generalEd = ["Area A", "Area B", "Area C", "Area D", "Area F",
                     "Area G", "Area H", "Writing", "European Traditions",
                     "World Cultures", "Quantitative Relationships",
                     "Ethnicity", "Depth"]
        length = len(generalEd)
        for i in range(length):
            var = tk.IntVar()
            if(i < 7):
                genEdCheckButton = tk.Checkbutton(self, text=generalEd[i],
                                                  variable=var)
                genEdCheckButton.grid(row=i+1, column=0)
            else:
                genEdCheckButton = tk.Checkbutton(self, text=generalEd[i],
                                                      variable=var)
                genEdCheckButton.grid(row=(length-i+1), column=1)
        pressToSearch = tk.Button(self,text="search", width=10)
        pressToSearch.grid(row=4, column=3)

class Page(tk.Frame):
    def __init__(self, *args, **kwargs):
        tk.Frame.__init__(self, *args, **kwargs)
    def show(self):
        self.lift()

class Page1(Page):
   def __init__(self, *args, **kwargs):
       tk.Frame.__init__(self, *args, **kwargs)
       SimpleSearchGUI().grid()
       label = tk.Label(self, text="Simple Search")
       label.pack(side="top", fill="both", expand=True)

class Page2(Page):
   def __init__(self, *args, **kwargs):
       tk.Frame.__init__(self, *args, **kwargs)
       AdvancedSearchGUI().grid()
       label = tk.Label(self, text="Advanced Search")
       label.pack(side="top", fill="both", expand=True)

class MainView(tk.Frame):
    def __init__(self, *args, **kwargs):
        tk.Frame.__init__(self, *args, **kwargs)
        simpleSearchPage = Page1(self)
        advancedSearchPage = Page2(self)

        buttonframe = tk.Frame(self)
        container = tk.Frame(self)

        buttonframe.pack(side="top", fill="x", expand=False)
        container.pack(side="top", fill="both", expand=True)

        simpleSearchPage.place(in_=container, x=0, y=0, relwidth=1,
                               relheight=1)
        advancedSearchPage.place(in_=container, x=0, y=0, relwidth=1,
                                 relheight=1)

        advancedSearch = tk.Button(buttonframe, text="Advanced Search",
                                   command=lambda: advancedSearchPage.lift())

        advancedSearch.pack(side="left")

        simpleSearchPage.show()


root = tk.Tk()
root.geometry('800x700')
root.title("GOLDER")
main = MainView(root)
main.pack(side="top", fill="both", expand=True)

root.mainloop()
