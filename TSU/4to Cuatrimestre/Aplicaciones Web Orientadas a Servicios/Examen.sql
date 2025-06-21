/*Creamos la Base de datos*/
create database CURP;

/*Usamos la Base de datos*/
use CURP;

/*Creamos la tabla para gestionar las CURP*/
create table registros (
id bigint auto_increment,
Nombre varchar(50) not null,
    Apellido_Pat varchar(50) not null,
    Apellido_Mat varchar(50) not null,
    Sexo char(1) not null,
    Estado_Nac varchar(50) not null,
    Fecha_Nac datetime not null default now(),
    primary key(id)
);

select * from registros;