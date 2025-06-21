# Instalar y cargar la librería ggplot2 si no está instalada
if (!require(ggplot2)) {
  install.packages("ggplot2", dependencies = TRUE)
  library(ggplot2)
}

# Cargar dataset diamonds
data(diamonds)

# Seleccionar las variables
x <- diamonds$carat
y <- diamonds$price

# Parte 1: Cálculos en R

## Calcular medias
mean_x <- mean(x)
mean_y <- mean(y)

## Calcular varianza y covarianza
var_x <- var(x)
cov_xy <- cov(x, y)

## Calcular coeficientes de regresión (β0 y β1)
beta1 <- cov_xy / var_x
beta0 <- mean_y - beta1 * mean_x

## Predicción para carat = 0.5
carat_nuevo <- 0.5
price_prediccion <- beta0 + beta1 * carat_nuevo

## Evaluación del modelo: R^2
modelo <- lm(price ~ carat, data = diamonds)
r2 <- summary(modelo)$r.squared
print(modelo)
print(r2)

# Parte 2: Graficar datos y la línea de regresión

## Gráfico de dispersión con línea de regresión
ggplot(diamonds, aes(x = carat, y = price)) +
  geom_point(alpha = 0.3) +
  geom_abline(intercept = beta0, slope = beta1, color = "red") +
  labs(title = "Regresión Lineal Simple: Price vs Carat",
       x = "Carat",
       y = "Price")

# Parte 3: Resultados

## Imprimir resultados
cat("Medias:\n")
cat("Media de carat:", mean_x, "\n")
cat("Media de price:", mean_y, "\n\n")

cat("Varianza y Covarianza:\n")
cat("Varianza de carat:", var_x, "\n")
cat("Covarianza entre carat y price:", cov_xy, "\n\n")

cat("Coeficientes de Regresión:\n")
cat("β1:", beta1, "\n")
cat("β0:", beta0, "\n\n")

cat("Predicción:\n")
cat("Predicción de price para carat = 0.5:", price_prediccion, "\n\n")

cat("Evaluación del Modelo:\n")
cat("R^2 del modelo:", r2, "\n")
