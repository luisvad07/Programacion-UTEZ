# Problema 1: Aprobado o reprobado

# Definir la variable calificacion
calificacion <- 75

# Determinar si el estudiante aprobó o reprobó
if (calificacion >= 60) {
  cat("El estudiante ha aprobado la materia.\n\n")
} else {
  cat("El estudiante ha reprobado la materia.\n\n")
}

# Problema 2: Número positivo, negativo o cero

# Definir la variable numero
numero <- -7

# Determinar la naturaleza del número
if (numero > 0) {
  cat("El número es positivo.\n\n")
} else if (numero < 0) {
  cat("El número es negativo.\n\n")
} else {
  cat("El número es cero.\n\n")
}

# Problema 3: Descuento en una tienda

# Definir la variable compra_total
compra_total <- 120

# Aplicar un descuento del 15% si el monto total es mayor a 100
if (compra_total > 100) {
  descuento <- compra_total * 0.15
  compra_final <- compra_total - descuento
  cat("Se aplicó un descuento del 15%.\n")
  cat("El monto final de la compra con descuento es:", compra_final, "\n\n")
} else {
  cat("No se aplicó ningún descuento.\n")
  cat("El monto final de la compra sin descuento es:", compra_total, "\n\n")
}

# Problema 4: Número mayor y menor

# Definir las variables num1, num2 y num3
num1 <- 25
num2 <- 40
num3 <- 15

# Determinar el número mayor y menor
numero_mayor <- max(num1, num2, num3)
numero_menor <- min(num1, num2, num3)

cat("El número mayor es:", numero_mayor, "\n")
cat("El número menor es:", numero_menor, "\n")
