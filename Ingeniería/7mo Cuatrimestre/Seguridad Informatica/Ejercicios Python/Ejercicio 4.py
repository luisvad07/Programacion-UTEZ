#Crear un script que capture desde teclado 10 edades, y que me indique cual de los valores se repite x veces
#Luis Eduardo Bahena Castillo     7CIDGS   Practica 4 Python

#Inicializamos lista
listedades = []

#Entrada de variables desde teclado
for i in range(10):
    letrero = input("Ingresa la edad de la persona " + str(i+1)+ ": ")
    edad = int(letrero)
    listedades.append(edad)

print(listedades)  
letrero2 = input("Ingresa el numero a buscar: ")
yes = int(letrero2)
cont = listedades.count(yes)
print("Numero de veces que se repite el numero: " + str(cont))  