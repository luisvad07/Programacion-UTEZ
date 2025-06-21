--1. Realizar un pa que permita insertar un nuevo departamento.
--2. Realizar un pa que permita actualizar el director de un departamento.
  --Para ello será necesario proveer el número de departamento y el DNI del nuevo director.
--3 Realizar un pa que permita eliminar un empleado de forma definitiva. 
 -- Para ello tendrá que verificar que dicho empleado no esta siendo referenciado en las tablas
 --DEPARTAMENTO y TRAABAJA_EN. En el caso de que si exista una referencia enivar un mensaje 
 -- de error indicando que el empleado con el DNI XXXXXXXXX esta siendo referenciado en la tabla YYYYYYYYY.
--4. Tomando el ejercicio anterior, realice el siguiente cambio, si el empleado esta siendo referenciado en la tabla 
 --TRABAJA_EN, proceda con la eliminación de las dependencias. Y en caso de la tabla DEPARTAMENTO, 
  --si el empleado a eliminar es director de algún departamento, entonces establezca como NULL
  --la referencia a dicho empleado esto con el objetivo de asignar posteriormente un director a dicho departamento.


--******************************PROCESOS ALMACENADOS*****************************************

--EJERCICIO 1

CREATE PROCEDURE EJERCICIO_INSERTAR_NUEVO_DEPARTAMENTO
(
Nombre IN VARCHAR,
Numero IN INT,
DniDirector IN CHAR,
FechaIngreso IN DATE

)
AS
BEGIN
INSERT INTO DEPARTAMENTO (NombreDpto, NumeroDpto,DniDirector,FechaIngresoDirector)
VALUES(Nombre, Numero, DniDirector, FechaIngreso);
END;

DROP PROCEDURE EJERCICIO_INSERTAR_NUEVO_DEPARTAMENTO;
describe departamento;
EXECUTE EJERCICIO_INSERTAR_NUEVO_DEPARTAMENTO ('RecursosHumanos',3,'123456789',TO_DATE('03-07-1996','DD-MM-YYYY'));
select * from departamento;

--PREGUNTA 2

CREATE PROCEDURE EJERCICIO_ACTUALIZAR_DIRECTOR 
(
NoDpto IN INT,
DniNuevo IN CHAR
)
AS
BEGIN
UPDATE DEPARTAMENTO SET DniDirector=DniNuevo WHERE NumeroDpto=NoDpto;
END;
DESCRIBE DEPARTAMENTO;
DROP PROCEDURE EJERCICIO_ACTUALIZAR_DIRECTOR;
EXECUTE EJERCICIO_ACTUALIZAR_DIRECTOR (6,'842024922');
SELECT * FROM DEPARTAMENTO;

--PREGUNTA 3

CREATE OR REPLACE PROCEDURE PA_ELIMINAR_EMPLEADO(
    PADniEmpleado IN CHAR
)
AS
    PAcoincidenciaDepartamento NUMBER;
    PAcoincidenciaTrabajaEn NUMBER;
BEGIN
	
	SELECT  COUNT(E.Dni) INTO PAcoincidenciaDepartamento from EMPLEADO E 
	INNER JOIN DEPARTAMENTO D ON E.Dni IN(D.DniDirector)
	WHERE e.Dni=PADniEmpleado;
	
	SELECT COUNT(E.Dni) into PAcoincidenciaTrabajaEn FROM EMPLEADO E 
	INNER JOIN TRABAJA_EN T ON E.Dni IN(PADniEmpleado)
	WHERE e.Dni=PADniEmpleado;

	IF PAcoincidenciaDepartamento=0 AND PAcoincidenciaTrabajaEn=0
		THEN
		DELETE FROM EMPLEADO WHERE EMPLEADO.Dni=PADniEmpleado;
	ELSE
		DBMS_OUTPUT.PUT_LINE ('Error al eliminar a nuestro empleado ');
	END IF;
END;

DROP PROCEDURE PA_ELIMINAR_EMPLEADO;
EXECUTE PA_ELIMINAR_EMPLEADO('987654321');

--PREGUNTA 4

CREATE or replace PROCEDURE PA_EJERCICIO_CUATRO(
PADniEmpleado in CHAR
)
AS
PAcoincidenciaEliminarDirector INT;
PAcoincidenciaEliminarDependencias INT;
BEGIN
	SELECT COUNT(E.Dni) into PAcoincidenciaEliminarDirector FROM EMPLEADO E 
	INNER JOIN DEPARTAMENTO D ON E.Dni IN(D.DniDirector)
	where e.Dni=PADniEmpleado;
	
	SELECT COUNT(E.Dni) into PAcoincidenciaEliminarDependencias FROM EMPLEADO E 
	INNER JOIN TRABAJA_EN T ON E.Dni IN(T.DniEmpleado)
	where E.Dni=PADniEmpleado;

	IF PAcoincidenciaEliminarDirector>0
		THEN
		UPDATE DEPARTAMENTO SET DniDirector=NULL WHERE DEPARTAMENTO.DniDirector = PADniEmpleado;
		DBMS_OUTPUT.PUT_LINE  ('SE ELIMINO EL DIRECTOR');
    end if;
	
	IF PAcoincidenciaEliminarDependencias>0 then
		DELETE TRABAJA_EN WHERE TRABAJA_EN.DniEmpleado = PADniEmpleado;
		DBMS_OUTPUT.PUT_LINE  ('SE ELIMINARON DEPENDENCIAS');
		END if;
END;	
END

DROP PROCEDURE PA_EJERCICIO_CUATRO;
EXECUTE PA_EJERCICIO_CUATRO ('987654321');

SELECT *FROM TRABAJA_EN;