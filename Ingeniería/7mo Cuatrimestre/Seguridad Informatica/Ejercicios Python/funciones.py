#Función que permite crear un diccionario con nombre y la edad de la persona
def edades(personas):
    for nombre, edad in personas.items():
        if 1 <= edad < 17:
            print(str(nombre) + " esta en el rango de 1 a 17 años")
        elif 18 <= edad < 30:
            print(str(nombre) + " esta en el rango de 18 a 30 años")
        else:
            print(str(nombre) + " esta en un rango de mayores de 30 años")

#Función que permite cifrar texto de acuerdo con el método cesar con clave 4    
def cifrar(texto):
    alfabeto = "abcdefghijklmnñopqrstuvwxyz"
    clave = 4
    #Longitud del alfabeto normal
    longitud = len(alfabeto)
    alfabeto_clave = alfabeto[longitud-clave:] + alfabeto[:longitud-clave]
    
    cifrado = ''
    for letra in texto:
        if letra in alfabeto:
            i = alfabeto.index(letra)
            cifrado += alfabeto_clave[i]
        else:
            cifrado += letra
    
    print("Alfabeto normal:", alfabeto)
    print("Alfabeto aplicado con la clave 4:", alfabeto_clave)
    print("Texto Ingresado:", texto)
    print("Texto Cifrado:", cifrado)

#Función recursiva que permite ordenar los elementos de una lista
def recursiva(elementos):
    for x in range(len(elementos)):
        print("Recursiva", elementos)


        