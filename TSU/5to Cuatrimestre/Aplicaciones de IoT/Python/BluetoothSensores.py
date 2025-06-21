import serial

arduino = serial.Serial(port='COM9', baudrate=9600) #Aqui va el COM del Bluetooth NO MOVER

print("Sensores con Bluetooth")

while True:
    lectura = arduino.read_all().decode('ascii')
    print(lectura)

arduino.close()