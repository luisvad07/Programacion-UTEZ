# Definición de la función de regresión lineal
linear_regression <- function(X, Y) {
  # Calcular medias
  mean_x <- mean(X)  # Calcula la media de X
  mean_y <- mean(Y)  # Calcula la media de Y
  
  # Calcular varianza y covarianza
  variance_x <- sum((X - mean_x)^2)  # Calcula la varianza de X
  covariance_xy <- sum((X - mean_x) * (Y - mean_y))  # Calcula la covarianza entre X y Y
  
  # Calcular coeficientes beta0 y beta1
  beta1 <- covariance_xy / variance_x  # Calcula el coeficiente beta1 (pendiente de la línea de regresión)
  beta0 <- mean_y - (beta1 * mean_x)  # Calcula el coeficiente beta0 (intersección con el eje Y)
  
  # Calcular predicciones
  y_pred <- beta0 + beta1 * X  # Calcula los valores predichos de Y usando los coeficientes obtenidos
  
  # Calcular R^2
  ss_total <- sum((Y - mean_y)^2)  # Calcula la suma total de cuadrados (variabilidad total en Y)
  ss_res <- sum((Y - y_pred)^2)  # Calcula la suma de cuadrados de los residuos (variabilidad no explicada por el modelo)
  r_squared <- 1 - (ss_res / ss_total)  # Calcula el coeficiente de determinación R^2 (proporción de variabilidad explicada por el modelo)
  
  # Ajustar tamaño de la ventana gráfica
  dev.new(width = 7, height = 7)  # Abre una nueva ventana gráfica con tamaño específico
  
  # Graficar la dispersión y la línea de regresión
  plot(X, Y, main = "Dispersión de X y Y con línea de regresión", xlab = "X", ylab = "Y")  # Crea un gráfico de dispersión de X y Y
  abline(a = beta0, b = beta1, col = "red")  # Añade la línea de regresión al gráfico
  
  # Devolver resultados
  return(list(beta0 = beta0, beta1 = beta1, r_squared = r_squared))  # Devuelve una lista con los coeficientes y el R^2
}

# Aplicación de la función a un dataset de ejemplo (usaremos el dataset 'mtcars')
data(mtcars)  # Carga el dataset 'mtcars'
X <- mtcars$wt  # Asigna la columna 'wt' (peso del coche) a la variable X
Y <- mtcars$mpg  # Asigna la columna 'mpg' (millas por galón) a la variable Y

# Llamada a la función
resultados <- linear_regression(X, Y)  # Llama a la función de regresión lineal con los datos de 'mtcars'
print(resultados)  # Imprime los resultados obtenidos (coeficientes y R^2)

# Verificación con lm()
modelo <- lm(Y ~ X)  # Ajusta un modelo de regresión lineal utilizando la función lm()
summary(modelo)  # Muestra un resumen del modelo ajustado, incluyendo los coeficientes, errores estándar y el R^2
