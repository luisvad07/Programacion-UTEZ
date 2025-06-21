# Función para calcular la distancia euclidiana
euclidean_distance <- function(point1, point2) {
  sqrt(sum((point1 - point2)^2))
}

# Función principal del clasificador KNN
knn_classifier <- function(train_data, train_labels, test_point, k) {
  distances <- sapply(1:nrow(train_data), function(i) {
    euclidean_distance(test_point, train_data[i,])
  })
  
  k_nearest_indices <- order(distances)[1:k]
  k_nearest_labels <- train_labels[k_nearest_indices]
  
  predicted_label <- names(which.max(table(k_nearest_labels)))
  return(predicted_label)
}

# Datos de entrenamiento: [goles_por_temporada, asistencias_por_temporada]
train_data <- matrix(c(
  20, 5,   # Jugador 1 del Equipo A
  15, 10,  # Jugador 2 del Equipo A
  18, 7,   # Jugador 3 del Equipo A
  22, 3,   # Jugador 4 del Equipo A
  12, 12,  # Jugador 5 del Equipo A
  8, 15,   # Jugador 1 del Equipo B
  10, 12,  # Jugador 2 del Equipo B
  6, 18,   # Jugador 3 del Equipo B
  9, 14,   # Jugador 4 del Equipo B
  7, 16    # Jugador 5 del Equipo B
), ncol=2, byrow=TRUE)

# Etiquetas de los equipos
train_labels <- c(rep("Equipo A", 5), rep("Equipo B", 5))

# Nuevo jugador a clasificar
nuevo_jugador <- c(14, 9)  # 14 goles, 9 asistencias por temporada
k <- 3

# Predicción
equipo_predicho <- knn_classifier(train_data, train_labels, nuevo_jugador, k)

cat("Predicción para el nuevo jugador (", 
    nuevo_jugador[1], "goles,", nuevo_jugador[2], "asistencias):\n",
    "El jugador probablemente pertenece al", equipo_predicho, "\n")

# Visualización de los datos
plot(train_data[,1], train_data[,2], 
     col = ifelse(train_labels == "Equipo A", "red", "blue"),
     pch = 16, 
     xlab = "Goles por temporada", 
     ylab = "Asistencias por temporada",
     main = "Clasificación de jugadores")

# Añadir el nuevo jugador con una etiqueta
points(nuevo_jugador[1], nuevo_jugador[2], col = "green", pch = 8, cex = 2)
text(nuevo_jugador[1], nuevo_jugador[2], 
     labels = paste("Nuevo\n", equipo_predicho), 
     pos = 3, col = "green", offset = 1)

legend("topright", 
       legend = c("Equipo A", "Equipo B", "Nuevo jugador"),
       col = c("red", "blue", "green"),
       pch = c(16, 16, 8))