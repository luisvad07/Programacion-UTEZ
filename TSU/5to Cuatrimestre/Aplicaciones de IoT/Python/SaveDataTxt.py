import serial
SerialArduino = serial.Serial(port='COM18', baudrate=9600)

while 'joel' == 'joel':
    
    lectureSensor=SerialArduino.readline().decode('ascii')
    print(lectureSensor)

    with open('./datosExamen.txt','a') as f:
        f.write(lectureSensor)