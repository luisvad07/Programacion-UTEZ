#Práctica P402 Exploración de Patrones de Crimen en EE.UU. con Clustering
#Hecho por Luis Eduardo Bahena Castillo 9CIDGS

# Cargar librerías necesarias para clustering
library(dbscan)    # Para el algoritmo DBSCAN
library(cluster)   # Para métodos de clustering jerárquico
library(stats)     # Para funciones estadísticas y K-Means

# Visualización inicial (PCA, gráficos de pares)
pairs(USArrests, main = "Gráfico de Pares de USArrests")

# Función para realizar clustering y manejar errores
realizar_clustering <- function(data, k = 3, eps = 0.5, minPts = 5) {
  # Usamos tryCatch para capturar y manejar errores durante la ejecución
  tryCatch({
    # Verificar si los datos son válidos (no vacíos)
    if (nrow(data) == 0 || ncol(data) == 0) {
      stop("El dataset está vacío o no tiene columnas válidas.")  # Lanzar un error si el dataset es inválido
    }
    
    # Escalar los datos para que todas las variables tengan igual importancia
    data_scaled <- scale(data)
    
    # Aplicar K-Means clustering
    set.seed(123)  # Fijar semilla para reproducibilidad
    km_res <- kmeans(data_scaled, centers = k)  # Realizar clustering con k clústeres
    data$ClusterKMeans <- factor(km_res$cluster)  # Guardar los resultados de K-Means en una nueva columna
    print("K-Means clustering realizado con éxito.")  # Imprimir confirmación
    
    # Calcular la matriz de distancias utilizando la distancia euclidiana
    distancias <- dist(data_scaled, method = "euclidean")
    
    # Aplicar clustering jerárquico con el método de "complete linkage"
    hc_res <- hclust(distancias, method = "complete")
    
    # Cortar el dendrograma en k clústeres y guardar los resultados
    data$ClusterJerarquico <- factor(cutree(hc_res, k = k))
    print("Clustering jerárquico realizado con éxito.")  # Imprimir confirmación
    
    # Aplicar DBSCAN con los parámetros eps y minPts
    dbscan_res <- dbscan(data_scaled, eps = eps, minPts = minPts)
    
    # Guardar los resultados de DBSCAN en una nueva columna
    data$ClusterDBSCAN <- factor(dbscan_res$cluster)
    print("DBSCAN realizado con éxito.")  # Imprimir confirmación
    
    # Devolver el dataset con los resultados de clustering
    return(data)
    
  }, error = function(e) {
    # Si ocurre un error, imprimir un mensaje de error
    print(paste("Ocurrió un error:", e$message))
    
    # Devolver NULL para indicar que algo salió mal
    return(NULL)
  })
}

# Cargar el dataset USArrests incluido en R
data("USArrests")

# Llamar a la función para realizar clustering con el dataset USArrests
resultados_clustering <- realizar_clustering(USArrests)

# Verificar si el clustering fue exitoso antes de intentar visualizar los resultados
if (!is.null(resultados_clustering)) {
  # Mostrar las primeras filas del dataset con los clústeres asignados
  print(head(resultados_clustering))
}
