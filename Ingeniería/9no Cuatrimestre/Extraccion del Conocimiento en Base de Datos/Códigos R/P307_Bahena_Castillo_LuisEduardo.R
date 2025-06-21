# Práctica P307 Regresión Lineal Múltiple con Dataset airquality
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Cargar el dataset airquality
data("airquality")

# Explorar el dataset para entender las variables y posibles valores faltantes
summary(airquality)
str(airquality)

# Manejar valores faltantes: Eliminar filas con valores NA
airquality <- na.omit(airquality)

# Definir la variable dependiente (y): Ozone (Concentración de Ozono)
y_concentracion_ozono <- airquality$Ozone

# Definir las variables independientes (X): Temp (Temperatura), Wind (Velocidad del Viento)
# Incluir una columna de 1's para el término independiente
x_airquality <- as.matrix(cbind(1, airquality$Temp, airquality$Wind))
x_airquality_t <- t(x_airquality)

# Paso 1: Multiplicar x por su transpuesta
x_res <- solve(x_airquality_t %*% x_airquality)

# Paso 2: Multiplicar x transpuesta * y
xy_res <- x_airquality_t %*% y_concentracion_ozono

# Paso 3: Multiplicar los resultados
betas <- x_res %*% xy_res

# Extraer los coeficientes de regresión
beta0 <- betas[1]
beta1 <- betas[2]
beta2 <- betas[3]

# Calcular los valores predichos (y_pred) usando la ecuación de regresión
y_prediccion <- beta0 + beta1 * airquality$Temp + beta2 * airquality$Wind

# Calcular R²
ss_total <- sum((y_concentracion_ozono - mean(y_concentracion_ozono))^2)
ss_residual <- sum((y_concentracion_ozono - y_prediccion)^2)
r_cuadrada <- 1 - (ss_residual / ss_total)

# Calcular el Error Cuadrático Medio (MSE)
mse <- mean((y_concentracion_ozono - y_prediccion)^2)

# Definir nuevos valores para las variables independientes
# Ejemplo: Temp = 85, Wind = 10
new_values <- c(1, 85, 10)

# Calcular la predicción de la concentración de Ozono para los nuevos valores
new_pred <- sum(new_values * betas)

# Graficar el scatterplot 3D y el plano de regresión
library(scatterplot3d)
s3d <- scatterplot3d(airquality$Temp, airquality$Wind, airquality$Ozone, 
                     xlab = "Temperature", ylab = "Wind", zlab = "Ozone", 
                     highlight.3d = TRUE, type = "h", main = "Scatterplot 3D con Plano de Regresión")

# Crear una malla de puntos para el plano de regresión
x_grid <- seq(min(airquality$Temp), max(airquality$Temp), length.out = 50)
y_grid <- seq(min(airquality$Wind), max(airquality$Wind), length.out = 50)
z_grid <- outer(x_grid, y_grid, function(x, y) beta0 + beta1 * x + beta2 * y)

# Agregar el plano de regresión al gráfico
for (i in 1:length(x_grid)) {
  s3d$points3d(rep(x_grid[i], length(y_grid)), y_grid, z_grid[i, ], type = "l", col = "blue")
}

# Mostrar los resultados
cat("Coeficientes de Regresión (Beta):\n", betas, "\n")
cat("R-cuadrado (R²):", r_cuadrada, "\n")
cat("Error Cuadrático Medio (MSE):", mse, "\n")
cat("Predicción de la concentración de Ozono para nuevos valores:", new_pred, "\n")