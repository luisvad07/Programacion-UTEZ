#Crear un programa que cree un diccionario desde teclado llamado frutas, el usuario debe dar minimo
#4 frutas con sus respectivos costos, seguido se le preguntara cuantos kilos compro de cada fruta, 
#y se le aplicara un descuento de la siguiente manera: 10% si compro mas de 3 kg, 5% si compro por 
#lo menos 1kg, 0% si compro menos de 1kg, al finalizar el script indicara cual es el total a pagar y
#los descuentos que se aplicaran 


def aplicar_descuento(cantidad, costo, descuento):
    if cantidad > 3:
        return cantidad * costo * (1 - 0.1)
    elif cantidad >= 1:
        return cantidad * costo * (1 - 0.05)
    else:
        return cantidad * costo
    
frutas = {}  #Diccionario de frutas

for i in range(4):
    fruta = input("Ingrese el nombre de la fruta: ")
    costo = float(input("Ingrese el costo del " + str(fruta)+ ": $"))
    frutas[fruta] = costo

print("Menu de Frutas -> " + str(frutas))

compras = {} #Diccionarios de frutas

for fruta in frutas:
    compras[fruta] = float(input("Ingrese la cantidad de kilos de " + str(fruta) + " que compró: "))

print("Ticket de Compra -> " + str(frutas))

total = 0
descuento = 0

for fruta, costo in frutas.items():
    total += aplicar_descuento(compras[fruta], costo, descuento)
    if compras[fruta] > 3:
        descuento += compras[fruta] * costo * 0.1
    elif compras[fruta] >= 1:
        descuento += compras[fruta] * costo * 0.05

print("El total a pagar es: " + str(total) + " y el descuento aplicado es: " + str(descuento))
