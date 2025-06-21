create database utez;

----------------------------------

use utez;

---------------------------------

CREATE TABLE alumnos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(150) NOT NULL,
    edad INT NOT NULL,
    sexo VARCHAR(15) NOT NULL,
    fecha_de_nacimiento DATE NOT NULL,
    grado INT NOT NULL,
    grupo VARCHAR(1) NOT NULL,
    generacion INT NOT NULL,
    carrera VARCHAR(50) NOT NULL
);
----------------
INSERT INTO alumnos (nombre_completo, edad, sexo, fecha_de_nacimiento, grado, grupo, generacion, carrera)
VALUES ('Juan Perez', 20, 'Masculino', '2004-08-15', 3, 'A', 2022, 'Ingeniería Civil'),('María García', 22, 'Femenino','2002-05-20', 4, 'B', 2021, 'Administración de Empresas'),('Pedro Rodríguez', 21, 'Masculino', '2003-10-10', 2, 'C', 2023, 'Derecho'),('Ana Martínez', 19, 'Femenino', '2005-02-28', 1, 'D', 2024, 'Psicología'),('Carlos López', 23, 'Masculino', '2001-12-05', 5, 'A', 2020, 'Arquitectura');
