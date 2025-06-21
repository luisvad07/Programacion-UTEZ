import serial

arduino = serial.Serial(port='COM9', baudrate=9600) #Aqui va el COM del Bluetooth NO MOVER

while True:
    #print("Dato Bluetooth: ")
    #inte = input()
    #arduino.write(inte.encode())
    rawString = arduino.read_all().decode()
    print(rawString)

arduino.close()
