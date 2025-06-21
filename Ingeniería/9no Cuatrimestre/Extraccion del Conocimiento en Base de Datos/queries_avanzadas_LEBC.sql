-- Crear tablas de ejemplo
CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    departamento_id INT
);

CREATE TABLE departamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50)
);

-- Insertar datos
INSERT INTO empleados (nombre, departamento_id) VALUES ('Juan', 1), ('Ana', 2), ('Pedro', 1);
INSERT INTO departamentos (nombre) VALUES ('Recursos Humanos'), ('IT');

-- Consulta INNER JOIN
SELECT empleados.nombre AS Empleado, departamentos.nombre AS Departamento
FROM empleados
INNER JOIN departamentos ON empleados.departamento_id = departamentos.id;

-- LEFT JOIN
SELECT empleados.nombre AS Empleado, departamentos.nombre AS Departamento
FROM empleados
LEFT JOIN departamentos ON empleados.departamento_id = departamentos.id;

-- RIGHT JOIN
SELECT empleados.nombre AS Empleado, departamentos.nombre AS Departamento
FROM empleados
RIGHT JOIN departamentos ON empleados.departamento_id = departamentos.id;


-- Agrupamiento (GROUP BY)
SELECT departamento_id, COUNT(*) AS NumeroEmpleados
FROM empleados
GROUP BY departamento_id;


-- Ordenamiento (ORDER BY)
SELECT nombre, departamento_id
FROM empleados
ORDER BY nombre ASC;


-- DISTINCT
SELECT DISTINCT departamento_id
FROM empleados;


-- HAVING
SELECT departamento_id, COUNT(*) AS NumeroEmpleados
FROM empleados
GROUP BY departamento_id
HAVING COUNT(*) > 1;

