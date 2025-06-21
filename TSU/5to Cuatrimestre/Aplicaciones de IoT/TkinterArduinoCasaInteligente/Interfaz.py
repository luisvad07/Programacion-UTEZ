import socket
import tkinter as tk


ip='192.168.100.95'
port=80
wifi = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
wifi.connect((ip,port))                
 

colorFrame="#f6fafe"
ColorLetter="#3a004c"
colorBodyLetter="#3a004c"
colorsubtitleLetter="#7a029f"
colorMargin="#7a029f"
buttonOn="#4d25aa"
buttonOf="#c41aec"


ventana = tk.Tk()


ventana.config(bg=colorFrame)
ventana.title("Panel de Control")
ventana.geometry("1150x590+0+0")


title= tk.Label(ventana, text="Panel de control", font=("Arial", 28), fg=ColorLetter,justify="center",pady=15,bg=colorFrame)
title.pack()

frame = tk.Frame(ventana, borderwidth=4, relief="solid",bg=colorMargin,padx=12)
frame.pack()   

frameDistance = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
frameDistance.pack_propagate(0)
frameDistance.grid(row=0, column=0, padx=10, pady=10)

frameTemperatura = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
frameTemperatura.pack_propagate(0)
frameTemperatura.grid(row=0, column=1, padx=10, pady=10)

frameHumedad = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
frameHumedad.pack_propagate(0)
frameHumedad.grid(row=0, column=2, padx=10, pady=10)

frameLuz = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
frameLuz.pack_propagate(0)
frameLuz.grid(row=1, column=0, padx=10, pady=10)

framePuertas = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
framePuertas.pack_propagate(0)
framePuertas.grid(row=1, column=1, padx=10, pady=10)

frameBombas = tk.Frame(frame, width=350, height=230, borderwidth=4, relief="solid",bg=colorFrame)
frameBombas.pack_propagate(0)
frameBombas.grid(row=1, column=2, padx=10, pady=10)

dataDistance = tk.Label(frameDistance, text="0", font=("Arial", 24), fg=ColorLetter,bg=colorFrame)
dataHumed = tk.Label(frameHumedad, text="0", font=("Arial", 24), fg=ColorLetter,bg=colorFrame)
dataLuz = tk.Label(frameLuz, text="0", font=("Arial", 24), fg=ColorLetter,bg=colorFrame)
dataTemp = tk.Label(frameTemperatura, text="0", font=("Arial", 24), fg=ColorLetter,bg=colorFrame)



def actualizar_contador():
    

   data = wifi.recv(1024)
   listaDatos = data.decode().split(","); 
   # print(listaDatos)
   dataDistance.config(text=listaDatos[0])
   dataTemp.config(text=listaDatos[1])
   dataHumed.config(text=listaDatos[2])
   dataLuz.config(text=listaDatos[3])

   ventana.after(9000, actualizar_contador)

actualizar_contador()


def luuz():
    wifi.send(("1").encode())
    print("Foco ")


def puerta1():
    wifi.send(("2").encode())
    print("Puerta ")


def ventanaF():
    wifi.send(("3").encode())
    print("Ventana")

def encenderBomba():
    wifi.send(("4").encode())
    print("Bomba")


lbdistance = tk.Label(frameDistance, text="Distancia",  font=("Arial", 24), fg="black",justify="center",pady=24,bg=colorFrame )
lbdistance.pack()
dataDistance.pack()

lbHUmed = tk.Label(frameHumedad, text="Humedad",  font=("Arial", 24), fg="black",justify="center",pady=24,bg=colorFrame )
lbHUmed.pack()
dataHumed.pack()

lbLuz = tk.Label(frameLuz, text="Luz", font=("Arial", 24),fg="black",justify="center",pady=24,bg=colorFrame)
lbLuz.pack()

luz1 = tk.Label(frameLuz, text="Habitacion",  font=("Arial", 18),fg=colorBodyLetter,bg=colorFrame)
luz1.pack(side="left")
# luz1Estado = tk.Label(frameLuz, text="Encendido",  font=("Arial", 18),fg=colorBodyLetter,bg=colorFrame)
# luz1Estado.pack(side="left")
button1 = tk.Button(frameLuz, text="Encender/Apagar",font=("Arial", 14),bg=buttonOf,fg="#FFFFFF", command=luuz)
button1.pack(side="left")

lbtemperature = tk.Label(frameTemperatura, text="Temperatura",  font=("Arial", 24),fg="black",justify="center",pady=24,bg=colorFrame)
lbtemperature.pack()
dataTemp.pack()

lbPuertas = tk.Label(framePuertas, text="Control de acceso", font=("Arial", 24),fg="black",justify="center",pady=24,bg=colorFrame)
lbPuertas.pack()


puerta1frame = tk.Frame(framePuertas,pady=10,bg=colorFrame)
puerta1frame.pack(side="top")
puerta2frame = tk.Frame(framePuertas,pady=10,bg=colorFrame)
puerta2frame.pack(side="top")

puertalbl1 = tk.Label(puerta1frame, text="Puerta",  font=("Arial", 18),fg=colorBodyLetter,padx=13, bg=colorFrame)
puertalbl1.pack(side="left")
# puerta1Estado = tk.Label(puerta1frame, text="Abierta",  font=("Arial", 18),fg="black",padx=12,bg=colorFrame)
# puerta1Estado.pack(side="left")
puerta1button1 = tk.Button(puerta1frame, text="Abrir/Cerrar", font=("Arial", 14), bg=buttonOf,padx=15,fg="#FFFFFF", command=puerta1)
puerta1button1.pack(side="left")

puertalbl2 = tk.Label(puerta2frame, text="Ventana",  font=("Arial", 18),fg=colorBodyLetter,padx=10,bg=colorFrame)
puertalbl2.pack(side="left")
# puerta2Estado = tk.Label(puerta2frame, text="Cerrada",  font=("Arial", 18),fg="black",padx=10,bg=colorFrame)
# puerta2Estado.pack(side="left")
puerta2button2 = tk.Button(puerta2frame, text="Abrir/Cerrar", font=("Arial", 14), bg=buttonOn,padx=14,fg="#FFFFFF", command=ventanaF)
puerta2button2.pack(side="left")
 

lbbombaDeAgua = tk.Label(frameBombas, text="Bomba de agua", font=("Arial", 24),fg="black",justify="center",pady=24,bg=colorFrame)
lbbombaDeAgua.pack()

bombaDeAgua = tk.Label(frameBombas, text="Estado",  font=("Arial", 18),fg="black",bg=colorFrame)
bombaDeAgua.pack(side="left", padx=10)
# bombaDeAguaEstado = tk.Label(frameBombas, text="Apagado",  font=("Arial", 18),fg="black",bg=colorFrame)
# bombaDeAguaEstado.pack(side="left", padx=10)
bombaDeAguabutton = tk.Button(frameBombas, text="Encender/Apagar",font=("Arial", 14),bg=buttonOn,fg="#FFFFFF", command=encenderBomba)
bombaDeAguabutton.pack(side="left", padx=10)


ventana.mainloop()