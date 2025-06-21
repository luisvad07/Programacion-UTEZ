# Examen de Clasificación con KNN y Bayes en R
# Luis Eduardo Bahena Castillo 9CIDGS

# Cargar librería WDI para el dataset "World Development Indicators"
install.packages("WDI")
library(WDI)

# Buscar datos relacionados con GDP
aa <- WDIsearch('gdp')

# Filtrar la Query para descargar el dataset en 2020
data <- WDI(country = "all", 
            indicator = c("SP.POP.TOTL", "NY.GDP.MKTP.CD", "SL.TLF.CACT.MA.ZS"), 
            start = 2020, 
            end = 2020)

# Crear vectores con países de diferentes regiones
latin_america_countries <- c("Argentina", "Brazil", "Mexico", "Chile", "Peru", "Costa Rica")  
sub_saharan_africa_countries <- c("Nigeria", "South Africa", "Kenya", "Chad", "Cameroon", "Angola")
europe_asia_central_countries <- c("Germany", "France", "Russia", "Turkey", "Spain", "Georgia")
middle_east_north_africa_countries <- c("Egypt", "Saudi Arabia", "Morocco", "Libya", "Algeria", "Tunisia")

# Crear una nueva columna llamada 'region' para clasificar los países en las regiones definidas
data$region <- NA  # Inicializar la columna 'region' con NA

# Asignar la región correspondiente a cada país según los vectores definidos
data$region[data$country %in% latin_america_countries] <- "Latin America & Caribbean"
data$region[data$country %in% sub_saharan_africa_countries] <- "Sub-Saharan Africa"
data$region[data$country %in% europe_asia_central_countries] <- "Europe & Central Asia"
data$region[data$country %in% middle_east_north_africa_countries] <- "Middle East & North Africa"

# Eliminar filas con valores NA en la columna 'region' para obtener solo los países con regiones definidas
data <- na.omit(data)  

# Verificar y eliminar nuevamente filas con valores NA en caso de que existan en otras columnas
data <- na.omit(data)

# Función para estandarizar datos: resta la media y divide por la desviación estándar
standardize <- function(x) {
  (x - mean(x, na.rm = TRUE)) / sd(x, na.rm = TRUE)
}

# Aplicar la estandarización a las columnas numéricas seleccionadas
data$NY.GDP.MKTP.CD <- standardize(data$NY.GDP.MKTP.CD)
data$SL.TLF.CACT.MA.ZS <- standardize(data$SL.TLF.CACT.MA.ZS)
data$SP.POP.TOTL <- standardize(data$SP.POP.TOTL)

# Dividir el dataset en conjunto de entrenamiento y prueba (80% entrenamiento, 20% prueba)
set.seed(123) # Establecer semilla para reproducibilidad
train_indices <- sample(seq_len(nrow(data)), size = 0.8 * nrow(data))
train_data <- data[train_indices, ]  # Datos de entrenamiento
test_data <- data[-train_indices, ]   # Datos de prueba

# Verificar los primeros registros del conjunto de entrenamiento y prueba
head(train_data)
head(test_data)

# Implementación del algoritmo KNN (k-nearest neighbors)
knn_classifier <- function(train_data, test_data, k) {
  # Función para calcular la distancia euclidiana entre dos puntos
  euclidean_distance <- function(a, b) {
    return(sqrt(sum((a - b)^2)))
  }
  
  # Inicializar vector para las predicciones
  predictions <- character(nrow(test_data))
  
  for (i in 1:nrow(test_data)) {
    test_point <- as.numeric(test_data[i, c("NY.GDP.MKTP.CD", "SL.TLF.CACT.MA.ZS", "SP.POP.TOTL")])
    
    # Calcular distancias
    distances <- apply(train_data, 1, function(train_point) {
      train_point_numeric <- as.numeric(train_point[c("NY.GDP.MKTP.CD", "SL.TLF.CACT.MA.ZS", "SP.POP.TOTL")])
      euclidean_distance(test_point, train_point_numeric)
    })
    
    # Encontrar los k vecinos más cercanos
    nearest_indices <- order(distances)[1:k]
    nearest_labels <- train_data$region[nearest_indices]
    
    # Asignar la clase mayoritaria
    predictions[i] <- names(sort(table(nearest_labels), decreasing = TRUE))[1]
  }
  
  return(predictions)
}

# Ejecutar la función knn_classifier con diferentes valores de k
k_values <- c(1, 3, 5, 7)
for (k in k_values) {
  predictions <- knn_classifier(train_data, test_data, k)
  accuracy <- mean(predictions == test_data$region)
  cat("Precisión para k =", k, "es", accuracy, "\n")
}

# Preprocesamiento para Bayes
# Calcular las probabilidades previas de cada región
prior_prob <- table(train_data$region) / nrow(train_data)

# Implementación del clasificador de Bayes
bayes_classifier <- function(train_data, test_data) {
  conditional_prob <- function(feature, region) {
    subset_data <- train_data[train_data$region == region, feature]
    return(mean(subset_data, na.rm = TRUE))
  }
  
  predictions <- character(nrow(test_data))
  
  for (i in 1:nrow(test_data)) {
    test_point <- as.numeric(test_data[i, c("NY.GDP.MKTP.CD", "SL.TLF.CACT.MA.ZS", "SP.POP.TOTL")])
    
    posterior_prob <- sapply(names(prior_prob), function(region) {
      likelihood <- prod(sapply(c("NY.GDP.MKTP.CD", "SL.TLF.CACT.MA.ZS", "SP.POP.TOTL"), function(feature) {
        conditional_prob(feature, region)
      }))
      return(likelihood * prior_prob[region])
    })
    
    predictions[i] <- names(posterior_prob)[which.max(posterior_prob)]
  }
  
  return(predictions)
}

# Ejecutar la función bayes_classifier
predictions <- bayes_classifier(train_data, test_data)
accuracy <- mean(predictions == test_data$region)
cat("Precisión del clasificador de Bayes es", accuracy, "\n")

# Comparar la precisión del modelo KNN y Bayes
# Ya calculado anteriormente para KNN y Bayes en las secciones anteriores

# Visualizar los resultados de la clasificación usando un gráfico de dispersión
library(ggplot2)

# Crear un data frame con las predicciones
results <- data.frame(
  true_region = test_data$region,
  knn_predictions = knn_classifier(train_data, test_data, k = 5),
  bayes_predictions = bayes_classifier(train_data, test_data)
)

# Graficar resultados para KNN
ggplot(results, aes(x = knn_predictions, fill = true_region)) +
  geom_bar(position = "dodge") +
  labs(title = "Comparación de Predicciones KNN", x = "Predicción KNN", y = "Conteo")

# Graficar resultados para Bayes
ggplot(results, aes(x = bayes_predictions, fill = true_region)) +
  geom_bar(position = "dodge") +
  labs(title = "Comparación de Predicciones Bayes", x = "Predicción Bayes", y = "Conteo")