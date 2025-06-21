create database prueEncode;
use prueEncode;


-- Crear Tabla para prueba Cifrado
CREATE TABLE EMPLEADO(
nombre varchar(60) primary key,
contra varchar(50),
metodo varchar (40));

select *from empleado;
insert into empleado values
  ('fulanito',sha1('nofulanito'),'sha1');
   insert into empleado values
  ('periquito',md5('noperiquito'),'md5'); 
     insert into empleado values
  ('periquita','12345','text'); 


select sha1('a');

CREATE USER 'GrupoA'@'localhost' IDENTIFIED BY 'password';
SELECT user FROM mysql. user;
drop user GrupoA;

1. ¿Qué resultado se obtuvo al consultar la contraseña? R= Aparecio una contraseña de una gran longitud de caracteres
2. ¿Cúal es la función de sha1? R= Convierte una cadena de longitud variable en una cadena de 40 caracteres
3. ¿Pára que sirve md5 y 'text'? R= MD5 es un algoritmo de reducción criptográfico de 128 bits y text permite guardar cadenas de caracteres grandes donde puede ocupar 230 Bytes (un byte por caracter)