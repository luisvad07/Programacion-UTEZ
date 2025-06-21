import socket
import time

host = "192.168.154.244" #dirección IP para conectar al arduino (Teléfono de Natalia)
port = 80

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((host, port))

while True:
    entra = input("Ingresa Datos: ")
    sock.send(entra.encode())
    time.sleep(2)

arduino.close()