CREATE DATABASE SIGESU;
USE SIGESU;

CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `id_cliente` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `id_cliente_idx` (`id_cliente`),
  CONSTRAINT `id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `tipo_cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `empleados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `curp` varchar(45) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `curp_UNIQUE` (`curp`),
  KEY `id_usurio_idx` (`id_usuario`),
  CONSTRAINT `id_usurio` FOREIGN KEY (`id_usuario`) REFERENCES `tipo_usurio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `productos` (
  `id` int NOT NULL auto_increment,
  `name_product` varchar(45) NOT NULL,
  `codigo_product` varchar(45) NOT NULL,
  `precio` double NOT NULL,
  `cantidad` decimal(5,0) NOT NULL,
  `id_tipo_product` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_product_UNIQUE` (`codigo_product`),
  KEY `id_tipo_product_idx` (`id_tipo_product`),
  CONSTRAINT `id_tipo_product` FOREIGN KEY (`id_tipo_product`) REFERENCES `tipo_producto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `ventas` (
  `id_venta` int NOT NULL AUTO_INCREMENT,
  `codigoVenta` varchar(45) NOT NULL,
  `id_producto` int NOT NULL,
  `productosVendidos` decimal(5,0) NOT NULL,
  `total` double,
  `nomCajero` varchar(45) NOT NULL,
  PRIMARY KEY (`id_venta`),
  UNIQUE KEY `codigoVenta_UNIQUE` (`codigoVenta`),
  KEY `id_producto_idx` (`id_producto`),
  CONSTRAINT `id_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `tipo_cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo_cliente` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tipo_producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tipo_usurio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

