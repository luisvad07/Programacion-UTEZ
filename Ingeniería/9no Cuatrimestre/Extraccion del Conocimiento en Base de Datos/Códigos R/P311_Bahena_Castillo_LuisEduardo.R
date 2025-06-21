# Práctica P311 Regresión Polinomial con el Dataset Rock
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Función para ajustar el modelo polinomial de grado 2 y realizar predicciones
ajustar_modelo_polinomial <- function(dataset, velocidad_nueva) {
  # Manejo de errores: verificar que las variables speed y dist existen en el dataset
  if(!all(c("speed", "dist") %in% names(dataset))) {
    stop("El dataset debe contener las variables 'speed' y 'dist'.")
  }
  
  # Manejo de errores: verificar que la nueva velocidad es numérica
  if(!is.numeric(velocidad_nueva)) {
    stop("La nueva velocidad debe ser un valor numérico.")
  }
  
  # Ajustar el modelo polinomial de grado 2
  modelo_polinomial <- lm(dist ~ poly(speed, 2, raw=TRUE), data=dataset)
  
  # Realizar la predicción para la nueva velocidad
  prediccion <- predict(modelo_polinomial, newdata = data.frame(speed = velocidad_nueva))
  
  # Evaluar el modelo con R^2
  r2 <- summary(modelo_polinomial)$r.squared
  
  # Retornar la predicción y el valor de R^2
  return(list(prediccion = prediccion, r2 = r2, modelo = modelo_polinomial))
}

# Función para visualizar el modelo
visualizar_modelo <- function(dataset, modelo_polinomial) {
  # Crear una secuencia de valores de 'speed' para las predicciones
  x <- seq(min(dataset$speed), max(dataset$speed), length.out = 100)
  y_pred <- predict(modelo_polinomial, newdata = data.frame(speed = x))
  
  # Configurar la ventana gráfica para evitar el error "figure margins too large"
  par(mar = c(5, 5, 2, 2))
  
  # Graficar los datos originales
  plot(dataset$speed, dataset$dist, main = "Regresión Polinomial de Grado 2", 
       xlab = "Velocidad (speed)", ylab = "Distancia (dist)", pch = 16, col = "blue")
  
  # Graficar la curva de la regresión polinomial
  lines(x, y_pred, col = "red", lwd = 2)
  
  # Agregar una leyenda
  legend("topleft", legend = c("Datos Originales", "Modelo Polinomial"),
         col = c("blue", "red"), pch = c(16, NA), lty = c(NA, 1), lwd = c(NA, 2))
}

# Cargar el dataset 'cars' disponible en R
data(cars)

# Uso correcto de la función
resultado <- ajustar_modelo_polinomial(cars, 15)
cat("Predicción para velocidad 15:", resultado$prediccion, "\n")
cat("Valor de R^2 del modelo:", resultado$r2, "\n")

# Visualizar el modelo
visualizar_modelo(cars, resultado$modelo)

# Intento de uso incorrecto: dataset sin las variables adecuadas
data(iris) # Cargando otro dataset que no tiene 'speed' y 'dist'
tryCatch({
  resultado_incorrecto1 <- ajustar_modelo_polinomial(iris, 15)
}, error = function(e) {
  cat("Error atrapado:", e$message, "\n")
})

# Intento de uso incorrecto: velocidad no numérica
tryCatch({
  resultado_incorrecto2 <- ajustar_modelo_polinomial(cars, "quince")
}, error = function(e) {
  cat("Error atrapado:", e$message, "\n")
})