# Configuración  
Modulo WiFi basado en ESP8266

## Comandos AT
Primera vez de configuración el seria debe estar en  115200

Saber si tenemos comunicación con el modulo
`Comando :  AT`

 Cambiar la velocidad de lectura
`AT+UART_DEF=9600,8,1,0,0` 

Modo Acces Point y servidor 
`AT+CWMODE=3`

Ver redes visibles
`AT+CWLAP`

Conectarse a una red
`AT+CWJAP="Nombre_red","Password"`

Mostrar ip del modulo 
`AT+CIFSR`

Posterior a la conexión debemos siempre activar la multiconexión y el puerto 

Activamos Multiconexiones 
`AT+CIPMUX=1`

Abrir puerto
`AT+CIPSERVER=1,80`

Conectarse al servidor 
`AT+CIPSTART=0,"TCP","IP",PORT`

Enviar datos al servidor
`AT+CIPSEND=0,4 NUMERO_DE_CARACTERES_PARA_ENVIAR`

Esperamos el signo _>_ para ingresar la palabra
`>Joel`


Referencia: 
[Comandos AT Wifi](http://https://www.electronicshub.org/esp8266-at-commands/ "Comandos AT Wifi")



![](https://th.bing.com/th?id=OSK.7de7a48bd83d988dd7376d91b6290a67&w=148&h=148&c=7&o=6&dpr=1.3&pid=SANGAM)

>Modulo WIfi

![](https://th.bing.com/th/id/OIP.OI0onNmb8y8iAfO8QpTy6AHaEG?pid=ImgDet&w=169&h=93.56706408345752&c=7https://th.bing.com/th/id/OIP.OI0onNmb8y8iAfO8QpTy6AHaEG?pid=ImgDet&w=169&h=93.56706408345752&c=7)

>Diagrama

     
     
    
     