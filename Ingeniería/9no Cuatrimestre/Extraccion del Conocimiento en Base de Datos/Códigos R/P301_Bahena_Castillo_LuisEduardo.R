# Cargar el dataset mtcars
data(mtcars)

# Función para ajustar modelo y calcular métricas
fit_lm <- function(data, x_var, y_var) {
  model <- lm(data[[y_var]] ~ data[[x_var]])
  summary(model)
  return(model)
}

# Ajuste de modelos y cálculo de métricas
models <- list()
for (var in c("hp", "qsec", "wt")) {
  models[[var]] <- fit_lm(mtcars, var, "mpg")
}

# Función para calcular R^2 y MSE
calculate_metrics <- function(model, data, x_var, y_var) {
  predictions <- predict(model, newdata = data)
  r_squared <- cor(data[[y_var]], predictions)^2
  mse <- mean((data[[y_var]] - predictions)^2)
  return(list(R2 = r_squared, MSE = mse))
}

# Calcular métricas para cada modelo
metrics <- list()
for (var in c("hp", "qsec", "wt")) {
  metrics[[var]] <- calculate_metrics(models[[var]], mtcars, var, "mpg")
}

# Crear tabla comparativa de resultados
results <- data.frame(
  Variable = c("hp", "qsec", "wt"),
  R2 = sapply(metrics, function(x) x$R2),
  MSE = sapply(metrics, function(x) x$MSE)
)

# Mostrar la tabla
print(results)

# Graficar dispersión con líneas de regresión ajustadas
par(mfrow = c(1, 3))
for (i in 1:length(models)) {
  plot(mtcars[[names(models)[i]]], mtcars$mpg,
       xlab = names(models)[i], ylab = "mpg", main = names(models)[i])
  abline(models[[i]], col = "blue")
}
