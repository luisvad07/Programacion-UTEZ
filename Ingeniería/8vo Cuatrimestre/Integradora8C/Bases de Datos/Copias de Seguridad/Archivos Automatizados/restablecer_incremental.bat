@echo off
setlocal

REM Variables
set usuario=copias
set password=zWxDCqEX1kPto9B
set host=localhost
set port=33060
set ruta_backup=C:\CopiasBD\Incremental
set base_datos=foodster
set fecha_hora=2022-01-01_12-00-00
set docker_container_id=e7afc2b203c5

REM Descomprimir el archivo de respaldo
7z e -p%password% -o"%ruta_backup%" "%ruta_backup%\%base_datos%_%fecha_hora%.zip"

REM Restaurar la base de datos desde el archivo de respaldo
mysql -h %host% -P %port% -u %usuario% -p%password% %base_datos% < "%ruta_backup%\%base_datos%_%fecha_hora%.sql" || docker exec -i %docker_container_id% mysql -h %host% -P %port% -u %usuario% -p%password% %base_datos% < "%ruta_backup%\%base_datos%_%fecha_hora%.sql"

REM Eliminar el archivo .sql después de la restauración
del "%ruta_backup%\%base_datos%_%fecha_hora%.sql"

endlocal