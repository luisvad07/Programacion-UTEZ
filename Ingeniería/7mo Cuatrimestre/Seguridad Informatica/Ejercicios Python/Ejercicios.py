# 1.- Realizar una funcion que pida un numero desde teclado e imprima su factorial

def factorial(numero):
    factorial = 1
    if numero < 0:
        return "¡Es un número negativo, por lo tanto no se puede calcular factorial!"
    elif numero == 0:
        return 1
    else:
        for s in range(1, numero + 1):
            factorial *= s
        return factorial

letrero = int(input("Ingresa un número para calcular el factorial: "))
print("El factorial de "+ str(letrero)+" es: "+ str(factorial(letrero)))