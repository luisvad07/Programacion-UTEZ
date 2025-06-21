# Cargar librerías
library(readxl)

# Leer datos desde Excel
Valores_P1 <- read_excel("Descargas/Valores P1.xlsx")

# Ver los datos
View(Valores_P1)

# Calcular medias
mean_temp <- mean(Valores_P1$temperature)
mean_pressure <- mean(Valores_P1$pressure)

# Calcular varianza y covarianza
var_temp <- var(Valores_P1$temperature)
cov_temp_pressure <- cov(Valores_P1$temperature, Valores_P1$pressure)

# Calcular coeficientes de regresión
beta1 <- cov_temp_pressure / var_temp
beta0 <- mean_pressure - beta1 * mean_temp

# Imprimir resultados
cat("Media de la temperatura:", mean_temp, "\n")
cat("Media de la presión:", mean_pressure, "\n")
cat("Varianza de la temperatura:", var_temp, "\n")
cat("Covarianza entre temperatura y presión:", cov_temp_pressure, "\n")
cat("Coeficiente beta1:", beta1, "\n")
cat("Coeficiente beta0:", beta0, "\n")

# Calcular R^2
model <- lm(Valores_P1$pressure ~ Valores_P1$temperature, data = Valores_P1)
summary(model)

# Graficar los datos y la línea de regresión
plot(Valores_P1$temperature, Valores_P1$pressure, main="Regresión Lineal Simple",
     xlab="Temperatura (°C)", ylab="Presión (atm)", pch=19)
abline(model, col="red")

