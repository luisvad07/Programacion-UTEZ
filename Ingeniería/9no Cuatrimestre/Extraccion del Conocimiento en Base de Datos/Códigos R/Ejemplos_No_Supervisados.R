#Cargar el dataset de IRIS
data("iris")
#Quitar la columna de la especie
iris_subset <- 
iris[, c("Sepal.Length", "Sepal.Width")]
# Cluster Jararquico
distancias <- dist(iris_subset, method = "euclidian")
# Aplicamos clustering jerárquico
hc_res <- hclust(distancias, method = "complete")
# Cortamos el árbol para obtener 3 clústeres
cutree_res <- cutree(hc_res, k = 3)
# KNN
set.seed(123) # Para reproducibilidad
km_res <- kmeans(iris_subset, centers = 3)
iris$ClusterKNN <- factor(km_res$cluster)
#dbscan
library(dbscan)
dbscan_res <- dbscan(iris_subset, eps = 0.5, minPts = 5)
iris$ClusterDBSCAN <- factor(dbscan_res$cluster)




