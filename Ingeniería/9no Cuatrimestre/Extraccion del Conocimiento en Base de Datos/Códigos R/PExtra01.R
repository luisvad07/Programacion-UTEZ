# Definir las variables
x <- c(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
y <- c(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
z <- c(TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE)

# Operadores Aritméticos
suma_xy <- x + y
resta_xy <- x - y
multiplicacion_xy <- x * y
division_xy <- x / y
modulo_xy <- x %% y
x_cuadrado <- x^2
raiz_y <- sqrt(y)

# Imprimir resultados aritméticos
print(paste("Suma de x e y:", toString(suma_xy)))
print(paste("Resta de x e y:", toString(resta_xy)))
print(paste("Multiplicación de x e y:", toString(multiplicacion_xy)))
print(paste("División de x e y:", toString(division_xy)))
print(paste("Módulo de x e y:", toString(modulo_xy)))
print(paste("Cuadrado de x:", toString(x_cuadrado)))
print(paste("Raíz cuadrada de y:", toString(raiz_y)))

# Operadores Relacionales
mayor_que <- x > y
menor_o_igual_que <- y <= x
igual_a <- x == y

# Imprimir resultados relacionales
print(paste("x > y:", toString(mayor_que)))
print(paste("y <= x:", toString(menor_o_igual_que)))
print(paste("x == y:", toString(igual_a)))

# Operadores Lógicos
mayor_que_5_y_menor_que_8 <- x[x > 5 & x < 8]
mayor_que_3_o_menor_que_2 <- y[y > 3 | y < 2]
todos_true <- all(z)
al_menos_un_false <- any(!z)

# Imprimir resultados lógicos
print(paste("Elementos de x > 5 y < 8:", toString(mayor_que_5_y_menor_que_8)))
print(paste("Elementos de y > 3 o < 2:", toString(mayor_que_3_o_menor_que_2)))
print(paste("Todos los elementos de z son TRUE:", todos_true))
print(paste("Al menos un elemento de z es FALSE:", al_menos_un_false))

# Funciones
suma_cuadrados <- function(a, b) {
  return(a^2 + b^2)
}

suma_cuadrados_xy <- suma_cuadrados(x, y)
print(paste("Suma de los cuadrados de x e y:", toString(suma_cuadrados_xy)))

media_elementos <- function(vector) {
  return(mean(vector))
}

media_x <- media_elementos(x)
media_y <- media_elementos(y)
print(paste("Media de los elementos de x:", media_x))
print(paste("Media de los elementos de y:", media_y))

elementos_mayores <- function(vector, umbral) {
  return(vector[vector > umbral])
}

elementos_mayores_que_7 <- elementos_mayores(x, 7)
print(paste("Elementos de x mayores que 7:", toString(elementos_mayores_que_7)))
