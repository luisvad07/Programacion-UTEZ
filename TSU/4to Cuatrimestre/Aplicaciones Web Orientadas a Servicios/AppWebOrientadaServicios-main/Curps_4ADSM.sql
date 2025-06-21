create database person;
use person;

create table persons(
id bigint auto_increment primary key,
nombre varchar(30),
primerApellido varchar(30),
segundoApellido varchar(30),
sexo varchar(20),
estado varchar(2),
fechaNac date,
curp varchar(20) unique
);

insert into persons(nombre,primerApellido,segundoApellido,sexo,estado,fechaNac,curp) values(?,?,?,?,?,?,?);
select * from persons;

drop table persons;
