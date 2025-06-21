# Cargar el dataset
data("mtcars")
#Definir conjuntos de entrenamiento
y_entrenamiento <- mtcars$mpg;
x_entrenamiento <- as.matrix(cbind(1,mtcars$wt,mtcars$hp))
x_entrenamiento_t <- t(x_entrenamiento)

#Paso 1: Multiplicar x por su transpuesta
x_res <- solve(x_entrenamiento_t %*% x_entrenamiento)

#Paso 2: Multiplicar x transpuesta * y
xy_res <- x_entrenamiento_t %*% y_entrenamiento

#Paso 3: Multiplicar los resultados
betas <- x_res %*% xy_res

beta0 <- betas[1]
beta1 <- betas[2]
beta2 <- betas[3]

y_predictoria <- beta0 + (beta1*mtcars$wt)+(beta2*mtcars$hp)

#Calcular R²
ss_total <- sum((y_entrenamiento - mean(y_entrenamiento))^2)
ss_residual <- sum ((y_entrenamiento - y_predictoria)^2)
r_cuadrada <- 1 - (ss_residual/ss_total)

modelo_lm_multiple <- lm(mpg ~ wt + hp, data = mtcars)
summary(modelo_lm_multiple)