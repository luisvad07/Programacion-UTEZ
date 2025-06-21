import socket

ip='192.168.77.51'
port=80
wifi = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
wifi.connect((ip,port))                
 
while True:
   data = wifi.recv(1024)
   listaDatos = data.decode().split()
   luz = listaDatos[0]
   humedad = listaDatos[1]
#   print(data.decode())
   print("----Data--------------")
   print("Humedad ->", humedad)
   print("luz ->", luz)
   print("------------------")

   if(int(luz) < 80  and int(humedad) < 2  ):      
      wifi.send(b"on")
   else:
      wifi.send(b"off")
