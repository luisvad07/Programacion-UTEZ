def mostrar_calificaciones(calificaciones):
    for asignatura, calificacion in calificaciones.items():
        if calificacion < 7:
            print(str(asignatura) + ": A recusar")
        elif 7 <= calificacion < 9:
            print(str(asignatura) + ": Aprobado con calificación media")
        else:
            print(str(asignatura) + ": Aprobado con excelencia")

def cifrar_cesar_modificado(texto):
    alfabeto = "abcdefghijklmnñopqrstuvwxyz"
    clave = 5
    #Longitud del abecedario normal
    longitud = len(alfabeto)
    alfabeto_clave = alfabeto[longitud-clave:] + alfabeto[:longitud-clave]
    
    cifrado = ''
    for letter in texto:
        if letter in alfabeto:
            indice = alfabeto.index(letter)
            cifrado += alfabeto_clave[indice]
        else:
            cifrado += letter
    
    print("Alfabeto normal:", alfabeto)
    print("Alfabeto aplicado con clave:", alfabeto_clave)
    print("Texto Normal:", texto)
    print("Texto Cifrado:", cifrado)

def comparar_archivos(archivo1, archivo2):
    with open(archivo1, "rb") as file1:
        contenido1 = file1.read()
        hash1 = hash(contenido1)
    
    with open(archivo2, "rb") as file2:
        contenido2 = file2.read()
        hash2 = hash(contenido2)
    
    return hash1 == hash2

def general():
    # Problema 1: Mostrar calificaciones
    calificaciones_alumno = {
        "Matemáticas": 8,
        "Historia": 6,
        "Ciencias": 9,
        "Literatura": 7
    }
    print("Problema 1: Mostrar calificaciones")
    mostrar_calificaciones(calificaciones_alumno)
    print("\n" + "=" * 50 + "\n")
    
    # Problema 2: Cifrar texto con método Cesar modificado
    texto_a_cifrar = input("Problema 2: Introduce el texto a cifrar: ")
    print("\nProblema 2: Cifrar texto con método Cesar modificado")
    cifrar_cesar_modificado(texto_a_cifrar)
    print("\n" + "=" * 50 + "\n")
    
    # Problema 3: Comparar archivos por su hash
    archivo1 = "hola.txt"
    archivo2 = "no.txt"
    
    with open(archivo1, "w") as file1:
        file1.write("Hola a todos")
    
    with open(archivo2, "w") as file2:
        file2.write("No estoy de buenas")
    
    print("Problema 3: Comparar archivos por su hash")
    if comparar_archivos(archivo1, archivo2):
        print("Los archivos son iguales.")
    else:
        print("Los archivos son diferentes.")

# Llamada a la función general
general()
