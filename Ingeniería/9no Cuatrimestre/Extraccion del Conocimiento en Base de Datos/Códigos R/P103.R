# Autor: Luis Eduardo Bahena Castillo
# Fecha: 15-Mayo-2024
# Descripción: Este script calcula el Índice de Masa Corporal (IMC) 
# y lo clasifica en diferentes categorías.

# Función para calcular el IMC
calcular_imc <- function(peso, altura) {
  # Calcular el IMC usando la fórmula proporcionada
  imc <- peso / (altura^2)
  
  # Clasificación del IMC
  if (imc < 18.5) {
    categoria <- "Bajo peso"
  } else if (imc >= 18.5 && imc < 25) {
    categoria <- "Peso normal"
  } else if (imc >= 25 && imc < 30) {
    categoria <- "Sobrepeso"
  } else {
    categoria <- "Obesidad"
  }
  
  # Imprimir el valor del IMC y la categoría
  cat("El IMC calculado es:", imc, "\n")
  cat("Categoría del IMC:", categoria, "\n")
  
  # Retornar el valor del IMC
  return(imc)
}

# Pruebas de la función con diferentes valores de peso y altura
# Ejemplo 1
peso1 <- 70  # kg
altura1 <- 1.75  # metros
imc1 <- calcular_imc(peso1, altura1)

# Ejemplo 2
peso2 <- 50  # kg
altura2 <- 1.60  # metros
imc2 <- calcular_imc(peso2, altura2)

# Ejemplo 3
peso3 <- 90  # kg
altura3 <- 1.80  # metros
imc3 <- calcular_imc(peso3, altura3)

# Ejemplo 4
peso4 <- 110  # kg
altura4 <- 1.70  # metros
imc4 <- calcular_imc(peso4, altura4)
