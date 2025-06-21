def my_function():
    print("Inside my function()")
    x = range(5,50,2)
    for n in x:
        print(n, end=".")

print("Out of the function")
my_function()
print("\nOther Operations")
my_function()
print("\nEnd of the program")

def my_function_B(colors):
    for x in colors:
        print(x)

my_colors=["blue", "red", "green", "yellow"]

my_function_B(my_colors)

def my_function_2(fname):
    print(fname + " Student")

my_function_2("Luis")
my_function_2("Gina")
my_function_2("Carlos")
my_function_2("Diana")

def my_function_3(fname, quarter):
    if quarter > 6:
        print(fname + " is going to be an engineer")
    else:
        print(fname + " is going to be an technician")

my_function_3("Luis",4)
my_function_3("Gina",8)
my_function_3("Carlos",10)
my_function_3("Diana",3)

def my_function4(student1, student2, student3):
    print("The youngest child is "+student3)

my_function4(student3="Gaby", student1="Alberto", student2="Angel")

def my_function5(language="Python"):
    print("I am programming in "+language)

my_function5("Java")
my_function5()
my_function5("C")
my_function5()
my_function5("Ruby")

def my_function6(x=1):
    return 5*x

variable1=my_function6(3)
variable2=my_function6(7)

print(variable1, variable2, my_function6(9))