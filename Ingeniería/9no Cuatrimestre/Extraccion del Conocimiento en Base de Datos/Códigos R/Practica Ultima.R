# Cargar librerías necesarias
library(dbscan)
library(cluster)
library(stats)

# Función para realizar clustering y manejar errores
realizar_clustering <- function(data, k = 3, eps = 0.5, minPts = 5) {
  tryCatch({
    # Verificar si los datos son válidos
    if (nrow(data) == 0 || ncol(data) == 0) {
      stop("El dataset está vacío o no tiene columnas válidas.")
    }
    
    # Escalado de datos
    data_scaled <- scale(data)
    
    # K-Means Clustering
    set.seed(123)
    km_res <- kmeans(data_scaled, centers = k)
    data$ClusterKMeans <- factor(km_res$cluster)
    print("K-Means clustering realizado con éxito.")
    
    # Clustering Jerárquico
    distancias <- dist(data_scaled, method = "euclidean")
    hc_res <- hclust(distancias, method = "complete")
    data$ClusterJerarquico <- factor(cutree(hc_res, k = k))
    print("Clustering jerárquico realizado con éxito.")
    
    # DBSCAN
    dbscan_res <- dbscan(data_scaled, eps = eps, minPts = minPts)
    data$ClusterDBSCAN <- factor(dbscan_res$cluster)
    print("DBSCAN realizado con éxito.")
    
    return(data)
    
  }, error = function(e) {
    print(paste("Ocurrió un error:", e$message))
    return(NULL)
  })
}

# Ejemplo de uso con el dataset USArrests
data("USArrests")
resultados_clustering <- realizar_clustering(USArrests)

# Visualización básica (si no ocurrió un error)
if (!is.null(resultados_clustering)) {
  print(head(resultados_clustering))
}
