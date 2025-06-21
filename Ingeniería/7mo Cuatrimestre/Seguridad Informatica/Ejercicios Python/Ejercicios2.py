#2.- Realizar una funcion que pida desde teclado un número de la tabla de multiplicar que desea realizar 
# y mostrar la tabla (Recursiva)

def tabla_de_multiplicar(n, i):
    if i > 10:
        return
    print(str(n) + " x " + str(i) + " = " + str(n * i))
    tabla_de_multiplicar(n, i + 1)

num = int(input("Ingresa un número para imprimir su tabla de multiplicar: "))
tabla_de_multiplicar(num, 1)
