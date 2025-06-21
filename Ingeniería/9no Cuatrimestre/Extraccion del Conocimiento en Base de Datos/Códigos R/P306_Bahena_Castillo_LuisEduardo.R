# Práctica P306 Regresión Lineal Múltiple con Dataset Iris
# Hecho por Luis Eduardo Bahena Castillo del 9°CIDGS

# Cargar el dataset iris
data(iris)

# Definir la variable dependiente (y): Petal.Length (Longitud del Pétalo)
y_longitud_petalo <- iris$Petal.Length

# Definir las variables independientes (X): 
# Sepal.Length (Longitud del Sépalo), Sepal.Width (Ancho del Sépalo) y Petal.Width (Ancho del Pétalo)
# Incluir una columna de 1's para el término independiente
x_total <- as.matrix(cbind(1, iris$Sepal.Length, iris$Sepal.Width, iris$Petal.Width))
x_total_t <- t(x_total)

# Paso 1: Multiplicar x por su transpuesta
x_res <- solve(x_total_t %*% x_total)

# Paso 2: Multiplicar x transpuesta * y
xy_res <- x_total_t %*% y_longitud_petalo

# Paso 3: Multiplicar los resultados
betas <- x_res %*% xy_res

# Extraer los coeficientes de regresión
beta0 <- betas[1]
beta1 <- betas[2]
beta2 <- betas[3]
beta3 <- betas[4]

# Calcular los valores predichos (y_pred) usando la ecuación de regresión
y_prediccion <- beta0 + beta1 * iris$Sepal.Length + beta2 * iris$Sepal.Width + beta3 * iris$Petal.Width

# Calcular R²
ss_total <- sum((y_longitud_petalo - mean(y_longitud_petalo))^2)
ss_residual <- sum((y_longitud_petalo - y_prediccion)^2)
r_cuadrada <- 1 - (ss_residual / ss_total)

# Calcular el Error Cuadrático Medio (MSE)
mse <- mean((y_longitud_petalo - y_prediccion)^2)

# Definir nuevos valores para las variables independientes
# Ejemplo: Sepal.Length = 5.8, Sepal.Width = 3.0, Petal.Width = 1.2
new_values <- c(1, 5.8, 3.0, 1.2)

# Calcular la predicción de la Longitud del Pétalo para los nuevos valores
new_pred <- sum(new_values * betas)

# Graficar el scatterplot 3D y el plano de regresión
install.packages("scatterplot3d") #Instalación de scatterplot3d
library(scatterplot3d)
s3d <- scatterplot3d(iris$Sepal.Length, iris$Sepal.Width, iris$Petal.Length, 
                     xlab = "Sepal Length", ylab = "Sepal Width", zlab = "Petal Length", 
                     highlight.3d = TRUE, type = "h", main = "Scatterplot 3D con Plano de Regresión")

# Crear una malla de puntos para el plano de regresión
x_grid <- seq(min(iris$Sepal.Length), max(iris$Sepal.Length), length.out = 50)
y_grid <- seq(min(iris$Sepal.Width), max(iris$Sepal.Width), length.out = 50)
z_grid <- outer(x_grid, y_grid, function(x, y) beta0 + beta1 * x + beta2 * y + beta3 * mean(iris$Petal.Width))

# Agregar el plano de regresión al gráfico
for (i in 1:length(x_grid)) {
  s3d$points3d(rep(x_grid[i], length(y_grid)), y_grid, z_grid[i, ], type = "l", col = "blue")
}

# Mostrar los resultados
cat("Coeficientes de Regresión (Beta):\n", betas, "\n")
cat("R-cuadrado (R²):", r_cuadrada, "\n")
cat("Error Cuadrático Medio (MSE):", mse, "\n")
cat("Predicción de la Longitud del Pétalo para nuevos valores:", new_pred, "\n")