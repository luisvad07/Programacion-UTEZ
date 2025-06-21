import socket #importa la libreria para conectar al modulo wifi
import time #importa la libreria del tiempo

host = "192.168.154.244" #dirección IP para conectar al arduino (Teléfono de Natalia)
port = 80 #puerto en el que se esta ejecutando

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #Permiten conectar dos programas en red para que se puedan intercambiar datos
sock.connect((host, port)) #Realiza la conexión de wifi

while True: #Si la condición es verdadera
    data = sock.recv(1024).decode().strip() #lee los datos desde Arduino
    if data:
        humedad, luz = data.split(",") #Recibe los datos de humedad y luz
        humedad = float(humedad)
        luz = float(luz)
        if humedad < 50 and luz > 500: #Si humedad es menor a 50% y la luz es menor a 500
            #Se manda la palabra encender al Serial de Arduino
            sock.send("encender".encode())
            time.sleep(2)
        else: #de la contrario
            #Se manda la palabra apagar al Serial de Arduino
            sock.send("apagar".encode())
            time.sleep(2)

arduino.close()