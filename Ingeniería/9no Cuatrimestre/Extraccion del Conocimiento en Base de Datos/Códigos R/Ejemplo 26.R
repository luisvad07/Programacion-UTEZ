# Cargar el dataset
data("mtcars")

#Definir conjuntos de entrenamiento
y_entrenamiento <- mtcars$mpg;
#Variables independientes
x1_entrenamiento <- mtcars$wt; #Peso
x2_entrenamiento<- mtcars$hp #Caballos de Fuerza

#Obtener las medias
x1_media <- mean(x1_entrenamiento);
x2_media <- mean(x2_entrenamiento);
y_media <- mean(y_entrenamiento);

#Obtener varianza y covarianza
var_x1 <- sum((x1_entrenamiento - x1_media)^2)
var_x2 <- sum((x2_entrenamiento - x2_media)^2)
cov_x1y <- sum((x1_entrenamiento - x1_media)*(y_entrenamiento - y_media))
cov_x2y <- sum((x2_entrenamiento - x2_media)*(y_entrenamiento - y_media))

#Beta 1
beta1 <- cov_x1y/var_x1;
#Beta 2
beta2 <- cov_x2y/var_x2;
#Beta 0
beta0 <- y_media - (beta1 * x1_media) - (beta2*x2_media)

#Predecir el conjunto y con lo obtenido en las betas
y_prediccion <- beta0 + (beta1 * x1_entrenamiento) + (beta2 * x2_entrenamiento)

#Calcular R²
ss_total <- sum((y_entrenamiento - y_media)^2)
ss_residual <- sum ((y_entrenamiento - y_prediccion)^2)
print((y_prediccion - y_media))
r_cuadrada <- 1 - (ss_residual/ss_total)

# Evaluación del modelo
modelo_lineal_simple <- lm(mpg ~ wt + hp, data = mtcars)
summary(modelo_lineal_simple)
