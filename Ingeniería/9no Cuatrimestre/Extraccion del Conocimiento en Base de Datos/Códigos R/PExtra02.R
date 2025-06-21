# Definir las variables
edades <- c(25, 32, 18, 45, 61, 28, 19, 53, 37, 22)
nombres <- c("Ana", "Carlos", "Sofía", "Juan", "María", "Pedro", "Laura", "Miguel", "Elena", "David")
sexo <- c("F", "M", "F", "M", "F", "M", "F", "M", "F", "M")

# Definir funciones
categorizar_edad <- function(edad) {
  if (edad < 12) {
    return("Niño")
  } else if (edad <= 18) {
    return("Adolescente")
  } else {
    return("Adulto")
  }
}

calcular_promedio <- function(vector) {
  return(mean(vector))
}

conteo_por_sexo <- function(vector) {
  hombres <- sum(vector == "M")
  mujeres <- sum(vector == "F")
  return(c(Hombres = hombres, Mujeres = mujeres))
}

# Control de Flujo
for (i in 1:length(edades)) {
  categoria <- categorizar_edad(edades[i])
  print(paste(nombres[i], "es", categoria))
  
  if (edades[i] > 65) {
    print(paste(nombres[i], "es mayor de 65 años."))
  }
}

# Calcular edad promedio del grupo
promedio_edad <- calcular_promedio(edades)
print(paste("La edad promedio del grupo es:", promedio_edad))

# Obtener el conteo de hombres y mujeres
conteo_sexo <- conteo_por_sexo(sexo)
print(paste("Conteo de hombres y mujeres:", toString(conteo_sexo)))

# Usar switch para mostrar un mensaje diferente según el conteo de hombres y mujeres
if (conteo_sexo["Mujeres"] > conteo_sexo["Hombres"]) {
  mensaje <- "Hay más mujeres en el grupo."
} else if (conteo_sexo["Hombres"] > conteo_sexo["Mujeres"]) {
  mensaje <- "Hay más hombres en el grupo."
} else {
  mensaje <- "Hay la misma cantidad de hombres y mujeres en el grupo."
}
print(mensaje)

# Operadores Relacionales
mayores_de_30 <- sum(edades > 30)
print(paste("Número de personas mayores de 30 años:", mayores_de_30))

# Operadores Lógicos
mujeres_mayores_de_40 <- sum(sexo == "F" & edades > 40)
print(paste("Número de mujeres mayores de 40 años:", mujeres_mayores_de_40))

# Operadores Aritméticos
diferencia_edad <- max(edades) - min(edades)
print(paste("La diferencia de edad entre la persona más joven y la más vieja es:", diferencia_edad))
