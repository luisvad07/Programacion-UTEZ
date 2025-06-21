@echo off
setlocal

REM Variables
set usuario=copias
set password=zWxDCqEX1kPto9B
set host=localhost
set port=33060
set ruta_backup=C:\CopiasBD\Completa
set ruta_git=C:\CopiasBD\
set base_datos=foodster

REM Crear la carpeta de respaldo si no existe
if not exist "%ruta_backup%" mkdir "%ruta_backup%"

REM Obtener la fecha y hora actual
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set "fecha_hora=%%I"
set "fecha=%fecha_hora:~0,4%-%fecha_hora:~4,2%-%fecha_hora:~6,2%"
set "hora=%fecha_hora:~8,2%-%fecha_hora:~10,2%-%fecha_hora:~12,2%"

REM Ejecutar mysqldump para realizar la copia de seguridad completa
mysqldump -h %host% -P %port% -u %usuario% -p%password% %base_datos% > "%ruta_backup%\%base_datos%_%fecha%_%hora%.sql"

REM Cifrar la copia de seguridad con AES-256 usando PowerShell
7z a -p%password% -tzip "%ruta_backup%\%base_datos%_%fecha%_%hora%.zip" "%ruta_backup%\%base_datos%_%fecha%_%hora%.sql"
del "%ruta_backup%\%base_datos%_%fecha%_%hora%.sql"
REM Inicializar git si no existe y hacer push
cd %ruta_git%
if not exist ".git" (
  git init
  git add .
  git commit -m "fecha=%fecha_hora:~0,4%-%fecha_hora:~4,2%-%fecha_hora:~6,2%"
  git branch -M main
  git remote add origin https://github.com/CristianRdz/copiasSeguridadFoodster.git
  git pull origin main
  git push -u origin main
) else (
  git pull origin main
  git add .
  git commit -m "fecha=%fecha_hora:~0,4%-%fecha_hora:~4,2%-%fecha_hora:~6,2%"
  git push
)

endlocal