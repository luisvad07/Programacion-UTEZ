# Simular datos
set.seed(123)
n <- 100
clase <- rep(c("Masculino", "Femenino"), each = n/2)
altura <- c(rnorm(n/2, mean = 175, sd = 10), rnorm(n/2, mean = 165, sd = 10))
peso <- c(rnorm(n/2, mean = 70, sd = 10), rnorm(n/2, mean = 60, sd = 10))
datos <- data.frame(clase, altura, peso)
head(datos)


# Calcular probabilidades previas
P_Masculino <- sum(datos$clase == "Masculino") / n
P_Femenino <- sum(datos$clase == "Femenino") / n

# Calcular medias y desviaciones estándar por clase
mean_altura_m <- mean(datos$altura[datos$clase == "Masculino"])
sd_altura_m <- sd(datos$altura[datos$clase == "Masculino"])
mean_peso_m <- mean(datos$peso[datos$clase == "Masculino"])
sd_peso_m <- sd(datos$peso[datos$clase == "Masculino"])

mean_altura_f <- mean(datos$altura[datos$clase == "Femenino"])
sd_altura_f <- sd(datos$altura[datos$clase == "Femenino"])
mean_peso_f <- mean(datos$peso[datos$clase == "Femenino"])
sd_peso_f <- sd(datos$peso[datos$clase == "Femenino"])




# Función de densidad de la normal
dnorm_custom <- function(x, mean, sd) {
  (1 / (sqrt(2 * pi) * sd)) * exp(-0.5 * ((x - mean) / sd)^2)
}

# Nueva observación
nuevo_dato <- data.frame(altura = 170, peso = 65)

# Calcular verosimilitudes
P_X_given_Masculino <- dnorm_custom(nuevo_dato$altura, mean_altura_m, sd_altura_m) * 
  dnorm_custom(nuevo_dato$peso, mean_peso_m, sd_peso_m)
P_X_given_Femenino <- dnorm_custom(nuevo_dato$altura, mean_altura_f, sd_altura_f) * 
  dnorm_custom(nuevo_dato$peso, mean_peso_f, sd_peso_f)

# Calcular probabilidades posteriores
P_Masculino_given_X <- P_X_given_Masculino * P_Masculino
P_Femenino_given_X <- P_X_given_Femenino * P_Femenino





# Normalizar las probabilidades posteriores
P_total <- P_Masculino_given_X + P_Femenino_given_X
P_Masculino_given_X <- P_Masculino_given_X / P_total
P_Femenino_given_X <- P_Femenino_given_X / P_total

# Asignar clase basada en la mayor probabilidad posterior
prediccion <- ifelse(P_Masculino_given_X > P_Femenino_given_X, "Masculino", "Femenino")
print(prediccion)








