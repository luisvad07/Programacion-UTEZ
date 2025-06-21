# Algoritmos No Supervisados
# KNN
# Jerarquico
# DBSCAN
set.seed(123)
data(iris)
iris_subset <- iris[,c("Sepal.Length","Sepal.Width","Petal.Length","Petal.Width")];
head(iris_subset)

#KNN
knn_iris <- kmeans(iris_subset, centers=3);
iris$KNN <- factor(knn_iris$cluster)
ggplot(iris,aes(Sepal.Length,Sepal.Width,color=KNN))+ geom_point() + labs(title="KMEDIAS");
#Jerarquico
distacias <- dist(iris_subset)
arbol<- hclust(distacias,method = "complete")
head(arbol)
#Podar el arbol
arbol_podado <- cutree(arbol,k=3);
iris$HC <- factor(arbol_podado)
ggplot(iris,aes(Sepal.Length,Sepal.Width,color=HC))+ geom_point() + labs(title="Jerarquico");
#DBSCAN
library(dbscan);
iris_dbscan <- dbscan(iris_subset,eps=0.5,minPts = 5)
iris$DBSCAN <- factor(iris_dbscan$cluster)
