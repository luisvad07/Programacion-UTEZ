# Problema 1: Cálculo de área y perímetro

# Definir las variables base y altura
base <- 5
altura <- 8

# Calcular el área
area <- base * altura

# Calcular el perímetro
perimetro <- 2 * (base + altura)

# Mostrar los valores del área y el perímetro
cat("El área del rectángulo es:", area, "\n")
cat("El perímetro del rectángulo es:", perimetro, "\n\n")

# Problema 2: Número par o impar

# Definir la variable numero
numero <- 17

# Determinar si el número es par o impar
if (numero %% 2 == 0) {
  cat("El número", numero, "es par.\n\n")
} else {
  cat("El número", numero, "es impar.\n\n")
}

# Problema 3: Comparación de edades

# Definir las variables edad1 y edad2
edad1 <- 25
edad2 <- 30

# Comparar las edades
if (edad1 > edad2) {
  cat("La persona 1 es mayor que la persona 2.\n\n")
} else if (edad1 < edad2) {
  cat("La persona 2 es mayor que la persona 1.\n\n")
} else {
  cat("Ambas personas tienen la misma edad.\n\n")
}

# Problema 4: Evaluación lógica

# Definir las variables lógicas a, b y c
a <- TRUE
b <- FALSE
c <- TRUE

# Crear una expresión lógica combinando las tres variables
expresion_logica <- a & (b | !c)

# Mostrar el resultado de la expresión lógica
cat("El resultado de la expresión lógica es:", expresion_logica, "\n")
