import funciones

personas = {} #Diccionario de personas

elementos = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}  #Elementos de la lista

while True: 
    print("\n================================")
    print("======= MENÚ DE PROBLEMAS =======")
    print(" 1. FUNCIÓN DICCIONARIO")
    print(" 2. FUNCIÓN MÉTODO CÉSAR")
    print(" 3. FUNCIÓN RECURSIVA")
    opc = int(input("Elige una opción: "))

    if opc == 1:
            print("Problema 1: Crear un diccionario con nombre y edad de la persona")
            ul = int(input("¿Cuantos datos quieres ingresar a la lista? -> "))
            for i in range(ul):
                nombre = input("Ingresa el nombre de la persona: ")
                edad = int(input("Ingresa la edad de la persona: "))
                personas.setdefault(nombre, edad)
                print("\n")
            funciones.edades(personas)
            print("\n")
    elif opc == 2:
        print("Problema 2: Cifrar texto con el Método Cesar modificado con CLAVE 4")
        texto = input("Introduce el texto a cifrar: ")
        funciones.cifrar(texto)
    elif opc == 3:
        print("Problema 3: Crear una función recursiva que permita ordenar los elementos de una lista") 
        funciones.recursiva(elementos)
    else:
        print("Error al teclear otra opción vuelve a intentarlo!!")