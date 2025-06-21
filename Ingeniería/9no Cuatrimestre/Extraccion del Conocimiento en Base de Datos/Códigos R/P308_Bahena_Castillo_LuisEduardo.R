# Práctica P308 Regresión Lineal Múltiple con el dataset USArrests
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Cargar el dataset y definir variables
library(datasets)

# Cargar el dataset 'USArrests'
data("USArrests")

# Exploración del dataset para entender las variables
head(USArrests)

# Definir la variable dependiente (y): Murder (Asesinatos por cada 100,000 habitantes)
y <- USArrests$Murder

# Definir las variables independientes (X): UrbanPop, Assault, Rape, y una columna de 1's para el término independiente
X <- as.matrix(cbind(Intercepto = 1, USArrests[, c("UrbanPop", "Assault", "Rape")]))

# Función para calcular los coeficientes de regresión (β) usando álgebra matricial
calcular_coeficientes <- function(X, y) {
  if (nrow(X) != length(y)) {
    stop("El número de filas en X debe ser igual al tamaño de y.") #Verifica que las dimensiones de las matrices sean correctas.
  }
  beta <- solve(t(X) %*% X) %*% t(X) %*% y
  return(beta)
}

# Función para calcular los valores predichos
calcular_predicciones <- function(X, beta) {
  y_pred <- X %*% beta #Calcula los valores predichos.
  return(y_pred) #Devuelve los valores predichos.
}

# Función para calcular el R-cuadrado
calcular_r_cuadrado <- function(y, y_pred) {
  ss_total <- sum((y - mean(y))^2) # Calcula el Suma de Cuadrados Total (SST).
  ss_residual <- sum((y - y_pred)^2) #Calcula el Suma de Cuadrados Residual (SSR).
  r_squared <- 1 - (ss_residual / ss_total) #Calcula el R-cuadrado.
  return(r_squared) #Devuelve el R-cuadrado.
}

# Función para calcular el Error Cuadrático Medio (MSE)
calcular_mse <- function(y, y_pred) {
  mse <- mean((y - y_pred)^2) #Calcula el Error Cuadrático Medio (MSE).
  return(mse) #Devuelve el MSE.
}

# Calcular los coeficientes de regresión (β)
beta <- calcular_coeficientes(X, y)

# Imprimir los coeficientes de regresión
print(beta)

# Calcular los valores predichos (y_pred) usando la ecuación de regresión
y_pred <- calcular_predicciones(X, beta)

# Evaluar el modelo calculando el R-cuadrado
r_squared <- calcular_r_cuadrado(y, y_pred)
print(paste("R-cuadrado: ", r_squared))

# Calcular el Error Cuadrático Medio (MSE)
mse <- calcular_mse(y, y_pred)
print(paste("Error Cuadrático Medio (MSE): ", mse))

# Definir nuevos valores plausibles para las variables independientes
nuevos_valores <- data.frame(Intercepto = 1, UrbanPop = 70, Assault = 200, Rape = 30)

# Convertir a matriz
nuevos_valores <- as.matrix(nuevos_valores)

# Calcular la predicción de la tasa de criminalidad seleccionada para los nuevos valores
nueva_prediccion <- calcular_predicciones(nuevos_valores, beta)
print(paste("Predicción para nuevos valores: ", nueva_prediccion))