# Pr�ctica P306 Regresi�n Lineal M�ltiple con Dataset Iris
# Hecho por Luis Eduardo Bahena Castillo del 9�CIDGS

# Cargar el dataset iris
data(iris)

# Definir la variable dependiente (y): Petal.Length (Longitud del P�talo)
y_longitud_petalo <- iris$Petal.Length

# Definir las variables independientes (X): 
# Sepal.Length (Longitud del S�palo), Sepal.Width (Ancho del S�palo) y Petal.Width (Ancho del P�talo)
# Incluir una columna de 1's para el t�rmino independiente
x_total <- as.matrix(cbind(1, iris$Sepal.Length, iris$Sepal.Width, iris$Petal.Width))
x_total_t <- t(x_total)

# Paso 1: Multiplicar x por su transpuesta
x_res <- solve(x_total_t %*% x_total)

# Paso 2: Multiplicar x transpuesta * y
xy_res <- x_total_t %*% y_longitud_petalo

# Paso 3: Multiplicar los resultados
betas <- x_res %*% xy_res

# Extraer los coeficientes de regresi�n
beta0 <- betas[1]
beta1 <- betas[2]
beta2 <- betas[3]
beta3 <- betas[4]

# Calcular los valores predichos (y_pred) usando la ecuaci�n de regresi�n
y_prediccion <- beta0 + beta1 * iris$Sepal.Length + beta2 * iris$Sepal.Width + beta3 * iris$Petal.Width

# Calcular R�
ss_total <- sum((y_longitud_petalo - mean(y_longitud_petalo))^2)
ss_residual <- sum((y_longitud_petalo - y_prediccion)^2)
r_cuadrada <- 1 - (ss_residual / ss_total)

# Calcular el Error Cuadr�tico Medio (MSE)
mse <- mean((y_longitud_petalo - y_prediccion)^2)

# Definir nuevos valores para las variables independientes
# Ejemplo: Sepal.Length = 5.8, Sepal.Width = 3.0, Petal.Width = 1.2
new_values <- c(1, 5.8, 3.0, 1.2)

# Calcular la predicci�n de la Longitud del P�talo para los nuevos valores
new_pred <- sum(new_values * betas)

# Graficar el scatterplot 3D y el plano de regresi�n
install.packages("scatterplot3d") #Instalaci�n de scatterplot3d
library(scatterplot3d)
s3d <- scatterplot3d(iris$Sepal.Length, iris$Sepal.Width, iris$Petal.Length, 
                     xlab = "Sepal Length", ylab = "Sepal Width", zlab = "Petal Length", 
                     highlight.3d = TRUE, type = "h", main = "Scatterplot 3D con Plano de Regresi�n")

# Crear una malla de puntos para el plano de regresi�n
x_grid <- seq(min(iris$Sepal.Length), max(iris$Sepal.Length), length.out = 50)
y_grid <- seq(min(iris$Sepal.Width), max(iris$Sepal.Width), length.out = 50)
z_grid <- outer(x_grid, y_grid, function(x, y) beta0 + beta1 * x + beta2 * y + beta3 * mean(iris$Petal.Width))

# Agregar el plano de regresi�n al gr�fico
for (i in 1:length(x_grid)) {
  s3d$points3d(rep(x_grid[i], length(y_grid)), y_grid, z_grid[i, ], type = "l", col = "blue")
}

# Mostrar los resultados
cat("Coeficientes de Regresi�n (Beta):\n", betas, "\n")
cat("R-cuadrado (R�):", r_cuadrada, "\n")
cat("Error Cuadr�tico Medio (MSE):", mse, "\n")
cat("Predicci�n de la Longitud del P�talo para nuevos valores:", new_pred, "\n")