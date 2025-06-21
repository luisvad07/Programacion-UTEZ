# Cargar el dataset iris
data(iris)

# Selección de variables
x <- iris$Sepal.Length
y <- iris$Petal.Length

# Calcular medias
mean_x <- mean(x)
mean_y <- mean(y)

# Calcular varianza de x y covarianza entre x e y
var_x <- var(x)
cov_xy <- cov(x, y)

# Calcular coeficientes de regresión
beta1 <- cov_xy / var_x
beta0 <- mean_y - beta1 * mean_x

# Predicción para un nuevo valor de Sepal.Length = 5.5
x_nuevo <- 5.5
y_pred <- beta0 + beta1 * x_nuevo

# Calcular R^2
y_hat <- beta0 + beta1 * x
SST <- sum((y - mean_y)^2)
SSR <- sum((y_hat - mean_y)^2)
SSE <- sum((y - y_hat)^2)
R2 <- SSR / SST

# Resultados
cat("Media de Sepal.Length:", mean_x, "\n")
cat("Media de Petal.Length:", mean_y, "\n")
cat("Varianza de Sepal.Length:", var_x, "\n")
cat("Covarianza entre Sepal.Length y Petal.Length:", cov_xy, "\n")
cat("Coeficiente de Regresión β1:", beta1, "\n")
cat("Coeficiente de Regresión β0:", beta0, "\n")
cat("Predicción de Petal.Length para Sepal.Length = 5.5:", y_pred, "\n")
cat("R^2 del modelo:", R2, "\n")

# Gráfico de dispersión con línea de regresión
plot(x, y, main = "Sepal.Length vs Petal.Length",
     xlab = "Sepal.Length", ylab = "Petal.Length", pch = 19, col = "blue")
abline(a = beta0, b = beta1, col = "red")
legend("topleft", legend = paste("R^2 =", round(R2, 3)), bty = "n")
