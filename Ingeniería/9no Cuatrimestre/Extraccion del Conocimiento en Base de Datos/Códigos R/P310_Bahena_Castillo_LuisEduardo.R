# Práctica P310 Regresión Polinomial con el Dataset Rock
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Cargar el dataset y definir variables
library(datasets)

# Cargar el dataset 'rock'
data("rock")

# Explorar el dataset para entender las variables
head(rock)

# Definir la variable independiente (x): area
x <- rock$area

# Definir la variable dependiente (y): perm
y <- rock$perm

# Función para realizar la regresión polinomial de grado 2
realizar_regresion_polinomial <- function(data, var_independiente, var_dependiente, grado = 2) {
  # Verificar que las variables están en el dataset
  if (!(var_independiente %in% colnames(data)) || !(var_dependiente %in% colnames(data))) {
    stop("Las variables especificadas no están presentes en el dataset.")
  }
  
  # Crear la fórmula para la regresión polinomial
  formula <- as.formula(paste(var_dependiente, "~ poly(", var_independiente, ",", grado, ")", sep = ""))
  
  # Ajustar el modelo polinomial de grado especificado
  modelo <- lm(formula, data = data)
  
  # Obtener un resumen del modelo ajustado
  resumen <- summary(modelo)
  
  # Realizar la predicción con el modelo ajustado
  y_pred <- predict(modelo, data)
  
  # Calcular el Suma de Cuadrados Total (SST)
  ss_total <- sum((data[[var_dependiente]] - mean(data[[var_dependiente]]))^2)
  
  # Calcular el Suma de Cuadrados Residual (SSR)
  ss_residual <- sum((data[[var_dependiente]] - y_pred)^2)
  
  # Calcular el R-cuadrado
  r_squared <- 1 - (ss_residual / ss_total)
  
  # Devolver el modelo ajustado, el resumen del modelo y el R-cuadrado
  return(list(modelo = modelo, resumen = resumen, r_squared = r_squared))
}

# Función para encapsular y predecir nuevos valores, así como la implementación del manejo de errores tryCatch
predecir_valores <- function(modelo, nuevos_valores) {
  tryCatch({
    # Realizar la predicción con el modelo ajustado
    prediccion <- predict(modelo, nuevos_valores)
    return(prediccion)
  }, error = function(e) {
    # Manejar posibles errores en la predicción
    stop("Error en la predicción: ", e$message)
  })
}

# Realizar la regresión polinomial de grado 2
resultado <- realizar_regresion_polinomial(rock, "area", "perm", grado = 2)

# Imprimir el resumen del modelo ajustado
print(resultado$resumen)

# Imprimir el valor del R-cuadrado
print(paste("R-cuadrado: ", resultado$r_squared))

# Crear un nuevo data frame con valores de 'area' para la predicción
nuevo_area <- data.frame(area = c(5000, 10000, 15000))

# Realizar la predicción de nuevos valores usando el modelo ajustado
prediccion <- predecir_valores(resultado$modelo, nuevo_area)

# Imprimir los valores predichos
print(prediccion)

# Visualizar el modelo ajustado y los datos originales
plot(rock$area, rock$perm, main = "Regresión Polinomial con el Dataset Rock",
     xlab = "Área (area)",
     ylab = "Permeabilidad (perm)")

# Graficar la línea del modelo ajustado
lines(sort(rock$area), fitted(resultado$modelo)[order(rock$area)], col = "red")

# Crear un nuevo data frame con un valor específico de 'area' para la predicción
nuevo_area_especifico <- data.frame(area = 12000)

# Realizar la predicción del nuevo valor específico usando el modelo ajustado
prediccion_perm <- predecir_valores(resultado$modelo, nuevo_area_especifico)

# Imprimir la predicción del nuevo valor específico
print(prediccion_perm)
