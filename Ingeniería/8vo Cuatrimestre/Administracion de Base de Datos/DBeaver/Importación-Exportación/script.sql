use shop;

-- Crear la tabla Productos
CREATE TABLE Productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    categoria VARCHAR(50),
    precio DECIMAL(10, 2)
);

-- Crear la tabla Clientes
CREATE TABLE Clientes (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    telefono VARCHAR(20)
);

-- Crear la tabla Pedidos
CREATE TABLE Pedidos (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    id_producto INT,
    fecha DATE,
    cantidad INT,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

select * from clientes;
select * from pedidos;
select * from productos;

-- Consulta para obtener el producto más caro en cada categoría
SELECT categoria, MAX(precio) AS precio_maximo FROM Productos GROUP BY categoria;

-- Consulta para obtener el total de pedidos realizados por cada cliente
SELECT c.nombre AS nombre_cliente, COUNT(p.id_pedido) AS total_pedidos FROM Clientes c 
LEFT JOIN Pedidos p ON c.id_cliente = p.id_cliente GROUP BY c.nombre;

-- Consulta para obtener el total de ventas y el ingreso total por categoría de productos
SELECT pr.categoria, SUM(pe.cantidad) AS total_ventas, SUM(pe.cantidad * pr.precio) AS ingreso_total FROM Pedidos pe
JOIN Productos pr ON pe.id_producto = pr.id_producto GROUP BY pr.categoria;

-- Consulta para realizar un JOIN de la tabla Pedidos con Clientes y Productos para incluir información relevante 
-- como el nombre del cliente y del producto en los resultados del JOIN
SELECT 
    p.id_pedido,
    c.nombre AS nombre_cliente,
    pr.nombre AS nombre_producto,
    pr.categoria,
    p.fecha,
    p.cantidad
FROM Pedidos p JOIN Clientes c ON p.id_cliente = c.id_cliente
JOIN Productos pr ON p.id_producto = pr.id_producto;