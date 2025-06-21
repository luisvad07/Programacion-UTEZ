# Práctica P309 Regresión Polinomial con el Dataset Trees
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Lo siguiente implemente la retroalimentación de la práctica P306

# Podrías mejorar la organización del código creando una función para encapsular la lógica de la 
# regresión lineal múltiple. Esto haría el código más reutilizable y fácil de mantener.

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

# Sería recomendable incluir algún tipo de manejo de errores, por ejemplo, 
# para verificar que las dimensiones de las matrices son correctas.

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

# Cargar el dataset 'trees'
data(trees)

# Realizar la regresión polinomial de grado 2
resultado <- realizar_regresion_polinomial(trees, "Girth", "Volume", grado = 2)

# Imprimir el resumen del modelo ajustado
print(resultado$resumen)

# Imprimir el valor del R-cuadrado
print(paste("R-cuadrado: ", resultado$r_squared))

# Crear un nuevo data frame con valores de 'Girth' para la predicción
nuevo_girth <- data.frame(Girth = c(10.5, 11.0, 11.5))

# Realizar la predicción de nuevos valores usando el modelo ajustado
prediccion <- predecir_valores(resultado$modelo, nuevo_girth)

# Imprimir los valores predichos
print(prediccion)

# Visualizar el modelo ajustado y los datos originales
plot(trees$Girth, trees$Volume, main = "Regresión Polinomial",
     xlab = "Circunferencia del Árbol (Girth)",
     ylab = "Volumen del Árbol (Volume)")

# Graficar la línea del modelo ajustado
lines(sort(trees$Girth), fitted(resultado$modelo)[order(trees$Girth)], col = "red")

# Crear un nuevo data frame con un valor específico de 'Girth' para la predicción
nuevo_girth_especifico <- data.frame(Girth = 11.0)

# Realizar la predicción del nuevo valor específico usando el modelo ajustado
prediccion_volumen <- predecir_valores(resultado$modelo, nuevo_girth_especifico)

# Imprimir la predicción del nuevo valor específico
print(prediccion_volumen)