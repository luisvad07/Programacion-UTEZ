#Listas en Python

lista = [1, 2.5, 'cadena', [5,6], 4]

print(lista[0]) #1
print(lista[1]) #2.5
print(lista[2]) #cadena
print(lista[3]) #5,6
print(lista[3][0]) #5
print(lista[3][1]) #6
print(lista[1:3]) #[2.5, 'cadena']
print(lista[1:6]) #[2.5, 'cadena', [5, 6], 4]

print("\n")

for element in lista:
    print(element) #Imprime elementos de la lista

print("\n")

lista.append(10) #[1, 2.5, 'cadena', [5, 6], 4, 10] - Método que tiene toda lista en el cual permite agregar un elemento al final de la lista
print(lista)
lista.append([2,5]) # [1, 2.5, 'cadena', [5, 6], 4, 10, [2, 5]]
print(lista)

print("\n")

lista.extend([88,99]) #[1, 2.5, 'cadena', [5, 6], 4, 10, [2, 5], 88, 99] - Método que permite agregar todos los elementos de un iterable(lista, tupla, cadena, etc) al final de una lista
print(lista)

print("\n")

myCount = lista.count(7)
print(myCount)

print("\n")

lista.remove('cadena') #[1, 2.5, [5, 6], 4, 10, [2, 5], 88, 99] - Método que elimina la primera aparición del elemento con el valor especificado
print(lista)