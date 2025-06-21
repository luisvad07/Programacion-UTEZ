import tkinter as tk
import random



# Generar un número entero aleatorio entre 1 y 100


# Generar una lista aleatoria de 5 números enteros entre 1 y 10
lista_aleatoria = random.sample(range(1, 11), 5)

ventana = tk.Tk()



# ------------------------------------------------------
frame = tk.Frame(ventana, bg="#000")
frame.pack(fill="both", expand=True)

frame.columnconfigure(0)
frame.columnconfigure(1)
frame.columnconfigure(2)
frame.columnconfigure(3)
frame.rowconfigure(0)
frame.rowconfigure(1)
frame.rowconfigure(2)
frame.rowconfigure(3)



dataDistance = tk.Label(frame, text="0", font=("Arial", 24), fg="black", bg="#eee")
dataHumed = tk.Label(frame, text="0", font=("Arial", 24), fg="black", bg="#eee")
dataLuz = tk.Label(frame, text="0", font=("Arial", 24), fg="black", bg="#eee")
dataTemp = tk.Label(frame, text="0", font=("Arial", 24), fg="black", bg="#eee")



def actualizar_contador():
    numero_aleatorio = random.randint(1, 100)
    dataDistance.config(text=numero_aleatorio)
    numero_aleatorio = random.randint(1, 100)
    dataHumed.config(text=numero_aleatorio)
    numero_aleatorio = random.randint(1, 100)
    dataLuz.config(text=numero_aleatorio)
    numero_aleatorio = random.randint(1, 100)
    dataTemp.config(text=numero_aleatorio)

    ventana.after(1000, actualizar_contador) # Actualizar cada 1000 milisegundos (1 segundo)

actualizar_contador()
# Función para mostrar el mensaje en el Label

# Crear un botón que llama a la función mostrar_mensaje() al hacer clic
lbdistance = tk.Label(frame, text="Distancia",  font=("Arial", 24), fg="white", bg="blue" )
lbdistance.grid(row=0, column=0 , sticky="ew")
dataDistance.grid(row=0, column=1, sticky="ew")

lbHUmed = tk.Label(frame, text="Humedad",  font=("Arial", 24), fg="white", bg="blue" )
lbHUmed.grid(row=0, column=2, sticky="ew")
dataHumed.grid(row=0, column=3, sticky="ew")

lbLuz = tk.Label(frame, text="Luz", font=("Arial", 24), fg="white", bg="blue" )
lbLuz.grid(row=1, column=0, sticky="ew")
dataLuz.grid(row=1, column=1, sticky="ew")

lbLuz = tk.Label(frame, text="Temperatura",  font=("Arial", 24), fg="white", bg="blue")
lbLuz.grid(row=1, column=2, sticky="ew")
dataTemp.grid(row=1, column=3, sticky="ew")



# -------------------------------------------------------


ventana.mainloop()