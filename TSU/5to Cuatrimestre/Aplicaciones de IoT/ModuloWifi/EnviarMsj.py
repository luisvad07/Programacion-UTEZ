import socket

ip='192.168.252.51'
port=8080

wifi=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

wifi.connect((ip,port))

while 1:
    data=input("Enviar texto: ")
    if data ==  'q' or data ==  'Q' :
        break
    else:
        wifi.send(data.encode())