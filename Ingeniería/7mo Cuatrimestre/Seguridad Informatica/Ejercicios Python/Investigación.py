# Ejemplo método sort
lista = [3, 1, 4, 1, 5, 9, 2, 6, 5]
lista.sort()
print(lista)  

# Ejemplo método index
lista = [3, 1, 4, 1, 5, 9, 2, 6, 5]
indice = lista.index(4)
print(indice)

# Ejemplo funciones max y min
lista = [3, 1, 4, 1, 5, 9, 2, 6, 5]
maximo = max(lista)
minimo = min(lista)
print(maximo)
print(minimo)

# Ejemplo método copy
lista = [3, 1, 4, 1, 5, 9, 2, 6, 5]
copia = lista.copy()
print(copia)

frutas = ["Manzana", "Mandarina", "Uva", "Durazno", "Piña"]
indice = frutas.index("Piña")
del frutas[3]
print(indice)
print(frutas[-1])