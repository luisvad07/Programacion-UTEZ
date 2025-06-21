#Cadena del abecedario normal
abecedario = 'abcdefghijklmnñopqrstuvwxyz'

#Longitud del abecedario normal
longitud = len(abecedario)

#Función para cifrar una palabra con el método César
def cifrado_cesar(word, desp, abecedario_cesar):
    cifrado = ''
    for letter in word:
        if letter in abecedario:
            indice = abecedario.index(letter)
            cifrado += abecedario_cesar[indice]
        else:
            cifrado += letter
    return cifrado


palabra = input("Ingresa una palabra a cifrar: ")
num = int(input("Ingrese la clave del desplazamiento para la palabra " + palabra+ ": "))
desp = num  #Número de veces que se va a recorrer (Clave de desplzamiento)
abecedario_cesar = abecedario[longitud-desp:] + abecedario[:longitud-desp]
palabra_cifrada = cifrado_cesar(palabra, desp, abecedario_cesar)

print("Abecedario completo: ", abecedario)
print("Abecedario con el método César: ", abecedario_cesar)
print("Palabra ingresada: ", palabra)
print("Palabra cifrada: ", palabra_cifrada)