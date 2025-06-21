#Práctica P501 Visualización de Datos del Conjunto diamonds
#Hecho por Luis Eduardo Bahena Castillo 9CIDGS

# Cargar las librerías necesarias
library(ggplot2)  # Librería para la visualización de datos

# Función para crear visualizaciones del conjunto de datos diamonds
visualizar_diamonds <- function() {
  # Manejo de errores para evitar interrupciones en caso de problemas
  tryCatch({
    # 1. Preparación y Exploración de Datos
    # Cargar el conjunto de datos 'diamonds' de ggplot2
    data("diamonds")
    
    # Realizar una exploración básica de las primeras filas del conjunto de datos
    print(head(diamonds))
    
    # 2. Visualización Básica
    # Crear un gráfico de dispersión para explorar la relación entre 'price' y 'carat'
    scatter_plot <- ggplot(diamonds, aes(x = carat, y = price)) +
      geom_point(alpha = 0.5) +  # Ajustar la transparencia para manejar la sobreimpresión
      ggtitle("Relación entre Precio y Quilates de Diamantes") +
      xlab("Peso en Quilates (Carat)") +
      ylab("Precio en USD") +
      theme_minimal()  # Aplicar un tema minimalista al gráfico
    
    # Mostrar el grResultadosáfico de dispersión básico
    print(scatter_plot)
    
    # 3. Aplicación de Sistemas de Coordenadas
    # Probar con 'coord_flip' para invertir los ejes
    scatter_plot_flip <- scatter_plot + coord_flip()
    print(scatter_plot_flip)
    
    # Probar con 'coord_fixed' para escalas fijas
    scatter_plot_fixed <- scatter_plot + coord_fixed(ratio = 1)
    print(scatter_plot_fixed)
    
    # 4. Personalización de Ejes
    # Personalizar los ejes del gráfico de dispersión
    scatter_plot_custom_axes <- scatter_plot +
      scale_x_continuous(name = "Peso en Quilates (Carat)", limits = c(0, 5)) +  # Limitar el eje x
      scale_y_continuous(name = "Precio en USD", limits = c(0, 20000)) +  # Limitar el eje y
      theme(
        axis.title.x = element_text(color = "blue", size = 14),
        axis.title.y = element_text(color = "red", size = 14),
        panel.grid.major = element_line(size = 0.5, linetype = 'dashed')
      )
    
    # Mostrar el gráfico con ejes personalizados
    print(scatter_plot_custom_axes)
    
    # 5. Exploración de Esquemas de Colores
    # Modificar el gráfico para incluir el corte del diamante (cut) como una tercera dimensión
    scatter_plot_color <- scatter_plot +
      aes(color = cut) +  # Usar 'cut' para diferenciar con colores
      scale_color_brewer(palette = "Set1") +  # Aplicar un esquema de colores
      ggtitle("Relación entre Precio y Quilates con Corte de Diamante")
    
    # Mostrar el gráfico con esquemas de colores
    print(scatter_plot_color)
    
    # 6. Tarea Avanzada - Visualización Multivariable
    # Crear un gráfico que combine múltiples atributos utilizando facetas y color
    multivariable_plot <- ggplot(diamonds, aes(x = carat, y = price, color = cut)) +
      geom_point(alpha = 0.5) +
      facet_wrap(~ cut) +  # Usar facetas para cada tipo de corte de diamante
      scale_color_brewer(palette = "Set2") +
      ggtitle("Precio vs Quilates con Facetas por Corte de Diamante")
    
    # Mostrar el gráfico multivariable
    print(multivariable_plot)
    
  }, error = function(e) {
    # Manejar cualquier error que ocurra durante la ejecución de la función
    message("Ocurrió un error: ", e$message)
  })
}

# Llamar a la función para ejecutar el análisis y visualización
visualizar_diamonds()
