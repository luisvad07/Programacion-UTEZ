import serial 

broad= serial.Serial(port='COM6', baudrate=9600) #start with a console

while True:
    lectureSensor = broad.readline().decode('ascii') #read data from conosle
    print(lectureSensor)    
    dec = int(lectureSensor, 2) #parseo binary to decimal
    dec = str(dec) #parseo to String 
    print(dec) 

    broad.write(dec.encode('ascii')) #send data by console
   

SerialArduino.close()
